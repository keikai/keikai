/*

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		
}}IS_NOTE

Copyright (C) 2013 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package io.keikai.model.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import io.keikai.model.CellRegion;
import io.keikai.model.InvalidModelOpException;
import io.keikai.model.PasteOption;
import io.keikai.model.PasteSheetRegion;
import io.keikai.model.SAutoFilter;
import io.keikai.model.SBook;
import io.keikai.model.SCell;
import io.keikai.model.SCellStyle;
import io.keikai.model.SColumn;
import io.keikai.model.SConditionalFormatting;
import io.keikai.model.SDataValidation;
import io.keikai.model.SRow;
import io.keikai.model.SSheet;
import io.keikai.model.SheetRegion;
import org.zkoss.poi.ss.formula.FormulaRenderer;
import org.zkoss.poi.ss.formula.ptg.Ptg;
import io.keikai.model.impl.sys.formula.ParsingBook;
import io.keikai.model.sys.EngineFactory;
import io.keikai.model.sys.formula.FormulaEngine;
import io.keikai.model.sys.formula.FormulaExpression;
import io.keikai.model.sys.formula.FormulaParseContext;
import io.keikai.model.util.Validations;
import io.keikai.range.impl.StyleUtil;
/**
 * 
 * @author Dennis
 * @since 3.5.0
 */
//ZSS-693: promote visibility
public class PasteCellHelper implements Serializable {
	private static final long serialVersionUID = 1420865992143581147L;

	private final SSheet _destSheet;
	private final SBook _book;
	private final SCellStyle _defaultStyle;
	public PasteCellHelper(SSheet destSheet){
		this._destSheet = destSheet;
		this._book = destSheet.getBook();
		_defaultStyle = _book.getDefaultCellStyle();
	}
	//ZSS-1277
	private static class HeightInfo {
		int height;
		boolean custom;
		HeightInfo(int h, boolean c) {
			this.height = h;
			this.custom = c;
		}
	}
	//ZSS-1277
	private CellRegion pasteRowHeights(SheetRegion src, CellRegion dest, int destRowCount) {
		List<HeightInfo> heightBuffer = prepareRowHeight(src);
		int srcRowCount = heightBuffer.size();
		boolean wrongRowMultiple = (destRowCount>1 && destRowCount%srcRowCount!=0);
		if (wrongRowMultiple) {
			throw new InvalidModelOpException("The operation can only be applied on the Paste area which is the Same size and shape of the Copy/Cut area"); //ZSS-988
		}
		int rowMultiple = destRowCount<=1||wrongRowMultiple?1:destRowCount/srcRowCount;
		for(int j=0;j<rowMultiple;j++){
				int rowMultipleOffset = j*srcRowCount;
				CellRegion destRegion = new CellRegion(dest.getRow()+rowMultipleOffset,dest.getColumn(),
						dest.getRow()+srcRowCount-1+rowMultipleOffset,dest.getColumn());
				pasteRowHeight(heightBuffer,destRegion);
		}
		return new CellRegion(dest.getRow(),0,dest.getRow()+srcRowCount*rowMultiple-1,_destSheet.getBook().getMaxColumnIndex());
	}
	//ZSS-1277
	//We don't notify the real effected region; rather we notify the whole 
	// source range to simplified the program (see RangeImpl#pastSpecial0())
	private void cutRowHeights(SheetRegion src, CellRegion dest, int srcRowCount, int destRowCount) {
		boolean wrongRowMultiple = (destRowCount>1 && destRowCount%srcRowCount!=0);
		if (wrongRowMultiple) {
			throw new InvalidModelOpException("The operation can only be applied on the Paste area which is the Same size and shape of the Copy/Cut area"); //ZSS-988			
		}
		dest = destRowCount<=1||wrongRowMultiple ? 
				new CellRegion(dest.getRow(), dest.getColumn(), dest.getRow()+srcRowCount-1, dest.getColumn()) 
				: dest;  

		final SSheet srcSheet  = src.getSheet();
		final int defaultHeight = srcSheet.getDefaultRowHeight();
		final int destRow1 = dest.getRow();
		final int destRow2 = dest.getLastRow();
		for (int j = src.getRow(), len = src.getLastRow(); j <= len; ++j) {
			if (destRow1 <= j && j <= destRow2) { //should skip overlapped row
				continue;
			}
			SRow srow = srcSheet.getRow(j);
			srow.setHeight(defaultHeight);
			srow.setCustomHeight(false);
			srow.setHidden(false);
		}
	}
	//ZSS-717
	private CellRegion pasteColumnWidths(SheetRegion src, CellRegion dest, int destColCount) {
		int[] widthBuffer = prepareColumnWidth(src);
		int srcColCount = widthBuffer.length;
		boolean wrongColMultiple = (destColCount>1 && destColCount%srcColCount!=0);
		//ZSS-717
		if (wrongColMultiple) {
			throw new InvalidModelOpException("The operation can only be applied on the Paste area which is the Same size and shape of the Copy/Cut area"); //ZSS-988			
		}
		int colMultiple = destColCount<=1||wrongColMultiple?1:destColCount/srcColCount;
		for(int j=0;j<colMultiple;j++){
				int colMultipleOffset = j*srcColCount;
				CellRegion destRegion = new CellRegion(dest.getRow(),dest.getColumn()+colMultipleOffset,
						dest.getRow(),dest.getColumn()+srcColCount -1 + colMultipleOffset);
				pasteColumnWidth(widthBuffer,destRegion);
		}
		return new CellRegion(0,dest.getColumn(),_destSheet.getBook().getMaxRowIndex(),dest.getColumn()+srcColCount*colMultiple-1);
	}
	//ZSS-717
	//We don't notify the real effected region; rather we notify the whole 
	// source range to simplified the program (see RangeImpl#pastSpecial0())
	private void cutColumnWidths(SheetRegion src, CellRegion dest, int srcColCount, int destColCount) {
		boolean wrongColMultiple = (destColCount>1 && destColCount%srcColCount!=0);
		if (wrongColMultiple) {
			throw new InvalidModelOpException("The operation can only be applied on the Paste area which is the Same size and shape of the Copy/Cut area"); //ZSS-988			
		}
		dest = destColCount<=1||wrongColMultiple ? 
				new CellRegion(dest.getRow(), dest.getColumn(), dest.getRow(), dest.getColumn()+srcColCount-1) 
				: dest;  

		final SSheet srcSheet  = src.getSheet();
		final int defaultWidth = srcSheet.getDefaultColumnWidth();
		final int destCol1 = dest.getColumn();
		final int destCol2 = dest.getLastColumn();
		for (int j = src.getColumn(), len = src.getLastColumn(); j <= len; ++j) {
			if (destCol1 <= j && j <= destCol2) { //should skip overlapped column
				continue;
			}
			//ZSS-1277: cut should unhide
			SColumn scol = srcSheet.getColumn(j);
			scol.setWidth(defaultWidth);
			scol.setCustomWidth(false);
			scol.setHidden(false);
		}
	}
	
	public CellRegion pasteCell(SheetRegion src, CellRegion dest, PasteOption option) {
		Validations.argNotNull(src);
		Validations.argNotNull(dest);
		final AbstractSheetAdv srcSheet = (AbstractSheetAdv) src.getSheet();
		boolean sameSheet = srcSheet == _destSheet;
		if(!sameSheet && srcSheet.getBook()!= _book){
			throw new IllegalArgumentException("the src sheet must be in the same book");
		}
		if(option==null){
			option = new PasteOption();
		}
		
		int rowOffset = dest.getRow() - src.getRow();
		int columnOffset = dest.getColumn() - src.getColumn();
	
		int destColCount = dest.getColumnCount();
		int destRowCount = dest.getRowCount();
		
		//ZSS-717
		final boolean wrongColSize = (src instanceof PasteSheetRegion ? ((PasteSheetRegion)src).isWholeColumn() : false)
				&& dest.getRow() != 0;
		if (wrongColSize) {
			throw new InvalidModelOpException("The operation can only be applied on the Paste area which is the Same size and shape of the Copy/Cut area"); //ZSS-988			
		}
		//ZSS-1277
		final boolean wrongRowSize = (src instanceof PasteSheetRegion ? ((PasteSheetRegion)src).isWholeRow() : false)
			&& dest.getColumn() != 0;
		if (wrongRowSize) {
			throw new InvalidModelOpException("The operation can only be applied on the Paste area which is the Same size and shape of the Copy/Cut area"); //ZSS-988			
		}
		//ZSS-717
		final boolean wholeColumn = (src instanceof PasteSheetRegion ? ((PasteSheetRegion)src).isWholeColumn() : false)
				&& dest.getRow() == 0;
		//ZSS-1277
		final boolean wholeRow = !wholeColumn && (src instanceof PasteSheetRegion ? ((PasteSheetRegion)src).isWholeRow() : false)
				&& dest.getColumn() == 0;
		if(option.getPasteType()== PasteOption.PasteType.COLUMN_WIDTHS){
			if(option.isCut()){
				throw new InvalidModelOpException("can't do cut when copying column width");
			}else if(option.isTranspose()){
				throw new InvalidModelOpException("can't do transport when copying column width");
			}
			return pasteColumnWidths(src, dest, destColCount); //ZSS-717
		} else if (option.getPasteType() == PasteOption.PasteType.ALL || option.getPasteType() == PasteOption.PasteType.ALL_EXCEPT_BORDERS) {
			//ZSS-717
			if (wholeColumn) {
				pasteColumnWidths(src, dest, destColCount);
			} else if (wholeRow) { //ZSS-1277
				pasteRowHeights(src, dest, destRowCount);
			}
		}
		
		PasteOption.PasteType pasteType = option.getPasteType();
		boolean handleMerge = shouldHandleMerge(pasteType);
		
		CellRegion srcRegion = src.getRegion();
		if(handleMerge){
			Collection<CellRegion> srcOverlaps = srcSheet.getOverlapsMergedRegions(srcRegion,true);
			//zss-401 , allow overlap when cuting, it will unmerge the overlaps
			if(srcOverlaps.size()>0 && !option.isCut()){
				throw new InvalidModelOpException("Can't copy "+srcRegion.getReferenceString()+" which overlaps merge area "+srcOverlaps.iterator().next().getReferenceString());
			}
		}
		
		//the buffer might be transported
		CellBuffer[][] srcBuffer = prepareCellBuffer(src,option);
		Collection<CellRegion> mergeBuffer = null;
		if(handleMerge){
			mergeBuffer = prepareMergeRegionBuffer(src,option);
		}
		
		//the ValidationBuffer might be transported
		List<ValidationBuffer> srcVBuffer = prepareValidationBuffer(src, option);
		
		//ZSS-1251: the ConditionalFormattingBuffer might be transported
		List<ConditionalFormattingBuffer> srcCBuffer = prepareConditionalFormattingBuffer(src, option);
		
		SheetRegion cutFrom = null;
		if(option.isCut()){
			//clear the src's value and merge
			clearCell(src);
			clearMergeRegion(src);
			cutFrom = src;
			srcSheet.deleteDataValidationRegion(srcRegion);
			srcSheet.deleteConditionalFormattingRegion(srcRegion); //ZSS-1251
			
			//ZSS-717
			if (wholeColumn) {
				cutColumnWidths(src, dest, src.getColumnCount(), destColCount);
			} else if (wholeRow) { //ZSS-1277
				cutRowHeights(src, dest, src.getRowCount(), destRowCount);
			}
		}
		
		// ZSS-608: Special Case - Copy a single cell to a merge cell
		if(srcRegion.isSingle()) {
			for (CellRegion mergedRegion : _destSheet.getMergedRegions()) {
				if (mergedRegion.contains(dest)) {
					CellRegion destRegion = new CellRegion(dest.getRow(),dest.getColumn(),dest.getRow(),dest.getColumn());
					pasteCells(srcBuffer,destRegion,cutFrom,option,rowOffset,columnOffset);
					if (option.isCut()) { //ZSS-696: if cut and paste, must unmerge
						pasteDataValidations(srcVBuffer, src, dest, option); // ZSS-694: only when CUT and paste
						pasteConditionalFormatting(srcCBuffer, src, dest, option); //ZSS-1251: only when CUT and paste
						_destSheet.removeMergedRegion(mergedRegion, true); // ZSS-696: should unmerge when cut and paste
					}
					return dest;
				}
			}
		}
		
		int srcColCount = srcBuffer[0].length;
		int srcRowCount = srcBuffer.length;
		
		boolean wrongRowMultiple = (destRowCount>1 && destRowCount%srcRowCount!=0);
		boolean wrongColMultiple = (destColCount>1 && destColCount%srcColCount!=0);

		
		boolean wrongMultiple = wrongRowMultiple||wrongColMultiple;
		int rowMultiple = destRowCount<=1||wrongMultiple?1:destRowCount/srcRowCount;
		int colMultiple = destColCount<=1||wrongMultiple?1:destColCount/srcColCount;

		for(int i=0;i<rowMultiple;i++){
			for(int j=0;j<colMultiple;j++){
				int rowMultpleOffset = i*srcRowCount;
				int colMultipleOffset = j*srcColCount;
				CellRegion destRegion = new CellRegion(dest.getRow()+rowMultpleOffset,dest.getColumn()+colMultipleOffset,
						dest.getRow()+srcRowCount+ -1 + rowMultpleOffset,
						dest.getColumn()+srcColCount -1 + colMultipleOffset);
				pasteCells(srcBuffer,destRegion,cutFrom,option,rowOffset+rowMultpleOffset,columnOffset+colMultipleOffset);
				pasteDataValidations(srcVBuffer, src, destRegion, option); // ZSS-694
				pasteConditionalFormatting(srcCBuffer, src, destRegion, option); //ZSS-1251
				if(mergeBuffer!=null && mergeBuffer.size()>0){
					pasteMergeRegion(mergeBuffer,rowOffset+rowMultpleOffset,columnOffset+colMultipleOffset);
				}
			}
		}
		
		return new CellRegion(dest.getRow(),dest.getColumn(),
				dest.getRow()+srcRowCount*rowMultiple-1,
				dest.getColumn()+srcColCount*colMultiple-1);
	}
	
	// ZSS-694
	List<ValidationBuffer> prepareValidationBuffer(SheetRegion src, PasteOption option) {
		final PasteOption.PasteType type = option.getPasteType();
		if (type != PasteOption.PasteType.ALL && type != PasteOption.PasteType.ALL_EXCEPT_BORDERS && type != PasteOption.PasteType.VALIDATAION)
			return Collections.emptyList();
		
		boolean transpose = option.isTranspose();
		SSheet srcSheet = src.getSheet();
		CellRegion srcRegion = src.getRegion();
		List<ValidationBuffer> vbs = new ArrayList<ValidationBuffer>();
		for (SDataValidation dv : srcSheet.getDataValidations()) {
			final LinkedHashSet<CellRegion> overlaps = new LinkedHashSet<CellRegion>();
			for (CellRegion rgn : dv.getRegions()) {
				CellRegion overlap = srcRegion.getOverlap(rgn);
				if (overlap != null) {
					overlaps.add(transpose ? 
							new CellRegion(overlap.getColumn(), overlap.getRow(),
									overlap.getLastColumn(), overlap.getLastRow()) : overlap);
				}
			}
			if (overlaps.isEmpty()) continue;
			vbs.add(new ValidationBuffer(dv, overlaps));
		}
		return vbs;
	}

	// ZSS-1251
	List<ConditionalFormattingBuffer> prepareConditionalFormattingBuffer(SheetRegion src, PasteOption option) {
		final PasteOption.PasteType type = option.getPasteType();
		if (type != PasteOption.PasteType.ALL && type != PasteOption.PasteType.ALL_EXCEPT_BORDERS
				&& type != PasteOption.PasteType.FORMATS
				&& type != PasteOption.PasteType.FORMULAS_AND_NUMBER_FORMATS
				&& type != PasteOption.PasteType.VALUES_AND_NUMBER_FORMATS)
			return Collections.emptyList();
		
		boolean transpose = option.isTranspose();
		SSheet srcSheet = src.getSheet();
		CellRegion srcRegion = src.getRegion();
		List<ConditionalFormattingBuffer> cbs = new ArrayList<ConditionalFormattingBuffer>();
		for (SConditionalFormatting cv : srcSheet.getConditionalFormattings()) {
			final Set<CellRegion> overlaps = new HashSet<CellRegion>();
			for (CellRegion rgn : cv.getRegions()) {
				CellRegion overlap = srcRegion.getOverlap(rgn);
				if (overlap != null) {
					overlaps.add(transpose ? 
							new CellRegion(overlap.getColumn(), overlap.getRow(),
									overlap.getLastColumn(), overlap.getLastRow()) : overlap);
				}
			}
			if (overlaps.isEmpty()) continue;
			cbs.add(new ConditionalFormattingBuffer(cv, overlaps));
		}
		return cbs;
	}

	// ZSS-694
	private void pasteDataValidations(List<ValidationBuffer> vbs, 
			SheetRegion src, CellRegion dst, PasteOption option) {
		final PasteOption.PasteType type = option.getPasteType();
		if (type != PasteOption.PasteType.ALL && type != PasteOption.PasteType.ALL_EXCEPT_BORDERS && type != PasteOption.PasteType.VALIDATAION)
			return;
		
		if (vbs.isEmpty()) return;
		
		// paste to destination
		final SSheet srcSheet = src.getSheet();
		final int rowOffset = dst.getRow() - src.getRegion().getRow();
		final int colOffset = dst.getColumn() - src.getRegion().getColumn();
		for (ValidationBuffer vb : vbs) {
			LinkedHashSet<CellRegion> destRegions = convertRegions(vb.regions, rowOffset, colOffset);
			// clear Validation at destRegions 
			for (CellRegion rgn : destRegions) {
				_destSheet.deleteDataValidationRegion(rgn);
			}
			
			// past
			SDataValidation dv = vb.validation;
			// different sheets or the validation has been cut away from its owner sheet, make a copy
			if (!srcSheet.equals(_destSheet) || dv.getSheet() == null) { 
				dv = _destSheet.addDataValidation(null, dv);
				((AbstractDataValidationAdv)dv).setRegions(destRegions);
			} else { // same sheet, adjust regions
				AbstractDataValidationAdv adv = ((AbstractDataValidationAdv)dv); 
				for (CellRegion regn : destRegions) {
					adv.addRegion(regn);
				}
			}
		}
	}

	//ZSS-1251
	private void pasteConditionalFormatting(List<ConditionalFormattingBuffer> cbs, 
			SheetRegion src, CellRegion dst, PasteOption option) {
		final PasteOption.PasteType type = option.getPasteType();
		if (type != PasteOption.PasteType.ALL && type != PasteOption.PasteType.ALL_EXCEPT_BORDERS
				&& type != PasteOption.PasteType.FORMATS
				&& type != PasteOption.PasteType.FORMULAS_AND_NUMBER_FORMATS
				&& type != PasteOption.PasteType.VALUES_AND_NUMBER_FORMATS)
			return;
		
		if (cbs.isEmpty()) return;
		
		// paste to destination
		final int dstRow = dst.row;
		final int dstCol = dst.column;
		final AbstractSheetAdv destSheet = (AbstractSheetAdv)_destSheet;
		destSheet.deleteConditionalFormattingRegion(dst);
		
		final int rowOff = dstRow - src.getRow();
		final int colOff = dstCol - src.getColumn();
		final CellRegion srcrgn = src.getRegion();
		for (ConditionalFormattingBuffer cb : cbs) {
			// past
			SConditionalFormatting cfmt = cb.cfmt;
			destSheet.addConditionalFormatting(src.getRegion(), dst, cfmt, rowOff, colOff);
		}
	}
	

	// ZSS-694
	private LinkedHashSet<CellRegion> convertRegions(Set<CellRegion> srcRegions, int rowOffset, int colOffset) {
		LinkedHashSet<CellRegion> dstRegions = new LinkedHashSet<CellRegion>();
		for (CellRegion rgn : srcRegions) {
			final int row1 = rgn.getRow() + rowOffset;
			final int col1 = rgn.getColumn() + colOffset;
			final int row2 = rgn.getLastRow() + rowOffset;
			final int col2 = rgn.getLastColumn() + colOffset;
			dstRegions.add(new CellRegion(row1, col1, row2, col2));
		}
		return dstRegions;
	}
	
	// ZSS-694
	private final static class ValidationBuffer {
		final SDataValidation validation;
		final LinkedHashSet<CellRegion> regions;
		ValidationBuffer(SDataValidation validation, LinkedHashSet<CellRegion> regions) {
			this.validation  = validation;
			this.regions = regions;
		}
	}
	
	//ZSS-1251
	private final static class ConditionalFormattingBuffer {
		final SConditionalFormatting cfmt;
		final Set<CellRegion> regions;
		ConditionalFormattingBuffer(SConditionalFormatting cfmt, Set<CellRegion> regions) {
			this.cfmt = cfmt;
			this.regions = regions;
		}
	}
	
	FormulaEngine formulaEngine;
	private FormulaEngine getFormulaEngine() {
		if(formulaEngine == null){
			formulaEngine = EngineFactory.getInstance().createFormulaEngine();
		}
		return formulaEngine;
	}


	private boolean shouldHandleMerge(PasteOption.PasteType pasteType) {
		return pasteType == PasteOption.PasteType.ALL ||
				pasteType == PasteOption.PasteType.ALL_EXCEPT_BORDERS ||
				pasteType == PasteOption.PasteType.FORMATS;
	}
	//ZSS-1277
	private void pasteRowHeight(List<HeightInfo> heightBuffer, CellRegion dest) {
		int row = dest.getRow();
		int lastRow = dest.getLastRow();
		for (int c = row; c <= lastRow;c++){
			final HeightInfo info = heightBuffer.get(c-row);
			final SRow srow = _destSheet.getRow(c);
			srow.setHeight(info.height);
			srow.setCustomHeight(info.custom);
		}
	}
	//ZSS-1277
	private List<HeightInfo> prepareRowHeight(SheetRegion src){
		int row = src.getRow();
		int lastRow = src.getLastRow();
		List<HeightInfo> heightBuffer = new ArrayList<HeightInfo>();
		SSheet srcSheet = src.getSheet();
		for(int c = row; c <= lastRow;c++){
			final SRow srow = srcSheet.getRow(c);
			heightBuffer.add(new HeightInfo(srow.getHeight(), srow.isCustomHeight()));
		}
		return heightBuffer;
	}

	private int[] prepareColumnWidth(SheetRegion src){
		int column = src.getColumn();
		int lastColumn = src.getLastColumn();
		int srcColCount = src.getColumnCount();
		int[] widthBuffer = new int[srcColCount];
		SSheet srcSheet = src.getSheet();
		for(int c = column; c <= lastColumn;c++){
			widthBuffer[c-column] = srcSheet.getColumn(c).getWidth();
		}
		return widthBuffer;
	}


	private List<CellRegion> prepareMergeRegionBuffer(SheetRegion src,
			PasteOption option) {
		List<CellRegion> mergeBuffer = new LinkedList<CellRegion>();
		boolean transpose = option.isTranspose();
		for(CellRegion region:src.getSheet().getContainsMergedRegions(src.getRegion())){
			if(transpose){
				int rowAnchor = src.getRow();
				int columnAnchor = src.getColumn();
				int r = rowAnchor + region.getColumn()-columnAnchor;
				int c = columnAnchor + region.getRow()-rowAnchor;
				int lr = r + region.getColumnCount()-1;
				int lc = c + region.getRowCount()-1;
				region = new CellRegion(r, c, lr, lc);
			}
			mergeBuffer.add(region);
		}
		return mergeBuffer;
	}

	private static Set<PasteOption.PasteType> handleStylePasteType = new HashSet<PasteOption.PasteType>();
	static{
		handleStylePasteType.add(PasteOption.PasteType.ALL);
		handleStylePasteType.add(PasteOption.PasteType.ALL_EXCEPT_BORDERS);
		handleStylePasteType.add(PasteOption.PasteType.FORMATS);
		handleStylePasteType.add(PasteOption.PasteType.FORMULAS);
		handleStylePasteType.add(PasteOption.PasteType.FORMULAS_AND_NUMBER_FORMATS);
		handleStylePasteType.add(PasteOption.PasteType.VALUES);
		handleStylePasteType.add(PasteOption.PasteType.VALUES_AND_NUMBER_FORMATS);
	}

	private CellBuffer[][] prepareCellBuffer(SheetRegion src, PasteOption option) {
		int row = src.getRow();
		int column = src.getColumn();
		int lastRow = src.getLastRow();
		int lastColumn = src.getLastColumn();
		
		boolean transpose = option.isTranspose();
		int srcRowCount = transpose?src.getColumnCount():src.getRowCount();
		int srcColCount = transpose?src.getRowCount():src.getColumnCount();
		final SSheet sheet = src.getSheet();
		
//		CellBuffer[][] srcBuffer = new CellBuffer[srcRowCount][srcColCount];
		final AbstractSheetAdv srcSheet = (AbstractSheetAdv) src.getSheet();
		
		//ZSS-1229: Whether any rows filtered
		List<List<CellBuffer>> rowBuf = new ArrayList<List<CellBuffer>>();
		final SAutoFilter af = srcSheet.getAutoFilter();
		boolean isFiltered = af == null ? false : af.isFiltered();
		
		for(int r = row; r <= lastRow;r++){
			//ZSS-1229 
			if (isFiltered && srcSheet.getRow(r).isHidden()) {
				continue;
			}
			List<CellBuffer> colBuf = new ArrayList<CellBuffer>();
			
			for(int c = column; c <= lastColumn;c++){
				//ZSS-1229
				if (isFiltered && srcSheet.getColumn(c).isHidden()) {
					continue;
				}
				
				SCell srcCell = srcSheet.getCell(r,c);
				PasteOption.PasteType pt = option.getPasteType();

				boolean handleStyle = handleStylePasteType.contains(pt)
						&& (srcSheet.getRow(r).getCellStyle(true) != null
						|| srcSheet.getColumn(c).getCellStyle(true) != null)
						|| ((AbstractSheetAdv)sheet).getTableByRowCol(r, c) != null; //ZSS-1002
				
				if(srcCell.isNull() && !handleStyle) {//to avoid unnecessary create
					colBuf.add(null); //ZSS-1229: have to put a place holder anyway
					continue;
				}
	
				//ZSS-1229
				//CellBuffer buffer = srcBuffer[transpose?c-column:r-row][transpose?r-row:c-column] = new CellBuffer();
				CellBuffer buffer = new CellBuffer();
				colBuf.add(buffer);
				
				buffer.setType(srcCell.getType());
				
				switch(pt){
				case ALL:
				case ALL_EXCEPT_BORDERS:
					prepareValue(buffer,srcCell,true);
					buffer.setStyle(StyleUtil.prepareStyle(srcCell)); //ZSS-1002
					buffer.setHyperlink(srcCell.getHyperlink());
					buffer.setComment(srcCell.getComment());
					buffer.setValidation(srcSheet.getDataValidation(r, c));
					//ZSS-1251
					buffer.setConditionalFormatting(srcSheet.getConditionalFormatting(r,  c));
					break;
				case COMMENTS:
					buffer.setComment(srcCell.getComment());
					break;
				case FORMATS:
					buffer.setStyle(srcCell.getCellStyle());
					//ZSS-1251
					buffer.setConditionalFormatting(srcSheet.getConditionalFormatting(r,  c));
					break;
				case FORMULAS_AND_NUMBER_FORMATS:
					buffer.setStyle(srcCell.getCellStyle());
					//ZSS-1251
					buffer.setConditionalFormatting(srcSheet.getConditionalFormatting(r,  c));
					//fall thru
				case FORMULAS:
					prepareValue(buffer,srcCell,true);
					break;
				case VALIDATAION:
					//TODO what can we do to keep validation to paste, the structure is not directly assign to cell currently
					buffer.setValidation(srcSheet.getDataValidation(r, c));
				case VALUES_AND_NUMBER_FORMATS:
					buffer.setStyle(srcCell.getCellStyle());
					//ZSS-1251
					buffer.setConditionalFormatting(srcSheet.getConditionalFormatting(r,  c));
					//fall thru
				case VALUES:
					prepareValue(buffer,srcCell,false);
					break;
				case COLUMN_WIDTHS:
					break;
				}
			}
			//ZSS-1229
			rowBuf.add(colBuf);
		}
		
		//ZSS-1229
		if (transpose) {
			final int rowLen = rowBuf.size();
			if (rowLen > 0) {
				final List<CellBuffer> colBuf0 = rowBuf.get(0);
				final int colLen = colBuf0.size();
				CellBuffer[][] srcBuffer = new CellBuffer[colLen][rowLen];
				for (int r = 0; r < rowLen; ++r) {
					final List<CellBuffer> colBuf = rowBuf.get(r);
					for (int c = 0; c < colLen; ++c) {
						final CellBuffer buf = colBuf.get(c);
						srcBuffer[c][r] = buf;
					}
				}
				return srcBuffer;
			} else {
				return new CellBuffer[0][];
			}
		} else {
			final int len = rowBuf.size();
			CellBuffer[][] srcBuffer = new CellBuffer[len][];
			for (int j = 0; j < len; ++j) {
				final List<CellBuffer> colBuf = rowBuf.get(j);
				srcBuffer[j] = colBuf.toArray(new CellBuffer[colBuf.size()]); 
			}
			return srcBuffer;
		}
	}


	private void prepareValue(CellBuffer buffer, SCell srcCell, boolean copyFormula) {
		if(copyFormula && srcCell.getType() == SCell.CellType.FORMULA){
			//ZSS-1002
			final String formula = srcCell.getFormulaValue();
			final ParsingBook parsingBook = new ParsingBook(srcCell.getSheet().getBook());
			final Ptg[] tokens = ((AbstractCellAdv)srcCell).getFormulaExpression().getPtgs();
			final String result = FormulaRenderer.toFormulaCopyText(parsingBook, tokens, formula);
			buffer.setFormula(result);
		}else{
			buffer.setValue(srcCell.getValue());
		}
	}
	
	private void clearMergeRegion(SheetRegion src) {
		src.getSheet().removeMergedRegion(src.getRegion(), true);
	}


	private void clearCell(SheetRegion src) {
		int row = src.getRow();
		int column = src.getColumn();
		int lastRow = src.getLastRow();
		int lastColumn = src.getLastColumn();
		SSheet srcSheet = src.getSheet();
		for(int r = row ; r<=lastRow;r++){
			for(int c= column; c<=lastColumn;c++){
				SCell cell = srcSheet.getCell(r,c);
				if(!cell.isNull()){
					srcSheet.clearCell(new CellRegion(r,c));
				}
			}
		}
	}


	private void pasteMergeRegion(Collection<CellRegion> mergeBuffer, int rowOffset,int columnOffset) {
		for(CellRegion merge:mergeBuffer){
			CellRegion newMerge = new CellRegion(merge.getRow()+rowOffset,merge.getColumn()+columnOffset,
					merge.getLastRow()+rowOffset,merge.getLastColumn()+columnOffset);
			
//			for(CellRegion overlap:destSheet.getOverlapsMergedRegions(newMerge,false)){
//				//unmerge the overlaps
//				destSheet.removeMergedRegion(overlap);
//			}
			_destSheet.removeMergedRegion(newMerge, true);
			
			_destSheet.addMergedRegion(newMerge);
		}
	}


	private void pasteColumnWidth(int[] widthBuffer, CellRegion dest) {
		int col = dest.getColumn();
		int lastColumn = dest.getLastColumn();
		for (int c = col; c <= lastColumn;c++){
			_destSheet.getColumn(c).setWidth(widthBuffer[c-col]);
		}
	}
	
	private void pasteCells(CellBuffer[][] srcBuffer, CellRegion destRegion,SheetRegion cutFrom,PasteOption option, int rowOffset,int columnOffset) {
		int row = destRegion.getRow();
		int col = destRegion.getColumn();
		int lastRow = destRegion.getLastRow();
		int lastColumn = destRegion.getLastColumn();
		boolean transpose = option.isTranspose();
		for(int r = row; r <= lastRow; r++){
			for (int c = col; c <= lastColumn;c++){
				CellBuffer buffer = srcBuffer[r-row][c-col];
				if((buffer==null || buffer.getType()== SCell.CellType.BLANK) && option.isSkipBlank()){
					continue;
				}
				
				//unmerge region if it is overlaps and not at first cell
				CellRegion region = _destSheet.getMergedRegion(r, c);
				if(region!=null && (region.getRow()!=r || region.getColumn()!=c)){
					_destSheet.removeMergedRegion(region, true);
				}
				
				SCell destCell = _destSheet.getCell(r,c);
				if(buffer==null){
					if(!destCell.isNull()){
						_destSheet.clearCell(destCell.getRowIndex(), destCell.getColumnIndex(), destCell.getRowIndex(), destCell.getColumnIndex());
						continue;
					}
					continue;
				}
				
				switch(option.getPasteType()){
				case ALL:
					pasteValue(buffer,destCell,cutFrom,true,rowOffset,columnOffset,transpose,row,col);
					pasteStyle(buffer,destCell,true);//border,comment
					buffer.applyHyperlink(destCell);
					buffer.applyComment(destCell);
					break;
				case ALL_EXCEPT_BORDERS:
					pasteValue(buffer,destCell,cutFrom,true,rowOffset,columnOffset,transpose,row,col);
					pasteStyle(buffer,destCell,false);//border,comment
					buffer.applyHyperlink(destCell);
					buffer.applyComment(destCell);
					break;
				case COMMENTS:
					buffer.applyComment(destCell);
					break;
				case FORMATS:
					//paste format should paste all style
					pasteStyle(buffer,destCell,true);
					break;
				case FORMULAS_AND_NUMBER_FORMATS:
					pasteFormat(buffer,destCell);
				case FORMULAS:
					pasteValue(buffer,destCell,cutFrom,true,rowOffset,columnOffset,transpose,row,col);
					break;
				case VALIDATAION:
				case VALUES_AND_NUMBER_FORMATS:
					pasteFormat(buffer,destCell);
				case VALUES:
					pasteValue(buffer,destCell,cutFrom);
					break;
				case COLUMN_WIDTHS:
					break;				
				}

			}
		}
	}

	private void pasteFormat(CellBuffer buffer, SCell destCell) {
		String srcFormat = buffer.getStyle().getDataFormat();
		SCellStyle destStyle = destCell.getCellStyle();
		String destFormat = destStyle.getDataFormat();
		if(!destFormat.equals(srcFormat)){
			StyleUtil.setDataFormat(_destSheet.getBook(), destCell, srcFormat);
		}
	}

	private void pasteStyle(CellBuffer buffer, SCell destCell, boolean pasteBorder) {
		if(destCell.getCellStyle()==_defaultStyle && buffer.getStyle()==_defaultStyle){
			return;
		}
		if(pasteBorder){
			destCell.setCellStyle(buffer.getStyle());
		}else{
			SCellStyle newStyle = _book.createCellStyle(buffer.getStyle(), true);
			SCellStyle destStyle = destCell.getCellStyle();
			//keep original border
			newStyle.setBorderBottom(destStyle.getBorderBottom());
			newStyle.setBorderBottomColor(destStyle.getBorderBottomColor());
			newStyle.setBorderTop(destStyle.getBorderTop());
			newStyle.setBorderTopColor(destStyle.getBorderTopColor());
			newStyle.setBorderRight(destStyle.getBorderRight());
			newStyle.setBorderRightColor(destStyle.getBorderRightColor());
			newStyle.setBorderLeft(destStyle.getBorderLeft());
			newStyle.setBorderLeftColor(destStyle.getBorderLeftColor());
			destCell.setCellStyle(newStyle);
		}
	}

	private void pasteValue(CellBuffer buffer, SCell destCell,SheetRegion cutFrom) {
		pasteValue(buffer,destCell,cutFrom,false,-1,-1,false,-1,-1);
	}
	//ZSS-693: promote to public so SortHelper can use this
	public void pasteValue(CellBuffer buffer, SCell destCell,SheetRegion cutFrom, boolean pasteFormula, int rowOffset,int columnOffset, boolean transpose, int rowOrigin, int columnOrigin) {
		if(pasteFormula){
			String formula = buffer.getFormula();
			if(formula!=null){
				FormulaEngine engine = getFormulaEngine();

				FormulaParseContext context = new FormulaParseContext(destCell, null); //nodependency, //ZSS-1002
				FormulaExpression expr; 
				FormulaExpression fexpr = engine.parse(formula, context);
				if(cutFrom!=null){
					expr = engine.movePtgs(fexpr,cutFrom,rowOffset, columnOffset, context);//no dependency
				}else{
					expr = engine.shiftPtgs(fexpr,rowOffset, columnOffset, context);//no dependency
				}
				if(!expr.hasError() && transpose){
					expr = engine.transposePtgs(expr, rowOrigin, columnOrigin, context);
				}
				
				if(!expr.hasError()){
					destCell.setValue(expr);
				}//ignore if get parsing error
				return;
			}
		}
		destCell.setValue(buffer.getValue());
	}
}
