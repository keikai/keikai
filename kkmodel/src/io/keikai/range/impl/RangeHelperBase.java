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
package io.keikai.range.impl;

import java.io.Serializable;

import io.keikai.model.SCell;
import io.keikai.model.SSheet;
import io.keikai.model.sys.EngineFactory;
import io.keikai.model.sys.format.FormatContext;
import io.keikai.model.sys.format.FormatEngine;
import io.keikai.model.sys.formula.FormulaEngine;
import io.keikai.range.SRange;
import org.zkoss.poi.ss.usermodel.ZssContext;

/**
 * 
 * @author Dennis
 * @since 3.5.0
 */
public class RangeHelperBase implements Serializable{
	private static final long serialVersionUID = 1611119134706736160L;
	
	protected final SRange range;
	protected final SSheet sheet;
	private FormatEngine _formatEngine;
	private FormulaEngine _formulaEngine;
	
	public RangeHelperBase(SRange range){
		this.range = range;
		this.sheet = range.getSheet();
	}

	public static boolean isBlank(SCell cell){
		return cell==null || cell.isNull()||cell.getType() == SCell.CellType.BLANK;
	}
	
	protected FormatEngine getFormatEngine(){
		if(_formatEngine==null){
			_formatEngine = EngineFactory.getInstance().createFormatEngine();
		}
		return _formatEngine;
	}
	
	public String getFormattedText(SCell cell){
		return getFormatEngine().format(cell, new FormatContext(ZssContext.getCurrent().getLocale())).getText();
	}
	
	protected FormulaEngine getFormulaEngine(){
		if (_formulaEngine == null){
			_formulaEngine = EngineFactory.getInstance().createFormulaEngine();
		}
		return _formulaEngine;
	}
	
	public int getRow() {
		return range.getRow();
	}

	public int getColumn() {
		return range.getColumn();
	}

	public int getLastRow() {
		return range.getLastRow();
	}

	public int getLastColumn() {
		return range.getLastColumn();
	}

	public boolean isWholeRow(){
		return range.isWholeRow();
	}
	
	public boolean isWholeSheet(){
		return range.isWholeSheet();
	}
	
	public boolean isWholeColumn() {
		return range.isWholeColumn();
	}
}
