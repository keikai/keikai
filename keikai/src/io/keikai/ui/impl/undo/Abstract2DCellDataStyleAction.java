/* Abstract2DCellDataStyleAction.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2013/7/25, Created by Dennis.Chen
}}IS_NOTE

Copyright (C) 2013 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under GPL Version 2.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
*/
package io.keikai.ui.impl.undo;


import io.keikai.api.AreaRef;
import io.keikai.api.CellOperationUtil;
import io.keikai.api.Range;
import io.keikai.api.Ranges;
import io.keikai.api.model.Sheet;
import io.keikai.model.CellRegion;
import io.keikai.ui.impl.undo.ReserveUtil.ReservedResult;
/**
 * abstract class handle src and destination content/style reservation
 * @author dennis
 *
 */
public abstract class Abstract2DCellDataStyleAction extends AbstractUndoableAction {
	private static final long serialVersionUID = -187507901641574749L;
	
	public static final int RESERVE_CONTENT = ReserveUtil.RESERVE_CONTENT;
	public static final int RESERVE_STYLE = ReserveUtil.RESERVE_STYLE;
	public static final int RESERVE_MERGE = ReserveUtil.RESERVE_MERGE;
	public static final int RESERVE_ALL = ReserveUtil.RESERVE_ALL;
	
	/**
	 * 
	 * @see #RESERVE_ALL
	 * @see #RESERVE_STYLE
	 * @see #RESERVE_CONTENT
	 * @see #RESERVE_MERGE
	 */
	private final int _reserveType;
	
	private ReservedResult _destOldReserve = null;
	private ReservedResult _srcOldReserve = null;
	
	//doesn't reserve in 2d
	
	protected final Sheet _destSheet;
	protected final int _destRow,_destColumn,_destLastRow,_destLastColumn;
	private Range _pastedRange;
	protected final boolean _destWholeColumn; //ZSS-717
	protected final boolean _destWholeRow; //ZSS-1277
	
	//ZSS-1277
	//@since 3.9.1
	public Abstract2DCellDataStyleAction(String label,Sheet sheet,int srcRow, int srcColumn, int srcLastRow,int srcLastColumn,boolean srcWholeColumn,boolean srcWholeRow,
			Sheet destSheet,int destRow, int destColumn, int destLastRow,int destLastColumn,boolean destWholeColumn, boolean destWholeRow, int reserveType){
		super(label,sheet,srcRow,srcColumn,srcLastRow,srcLastColumn,srcWholeColumn,srcWholeRow);
		_destSheet = destSheet;
		_destRow = destRow;
		_destColumn = destColumn;
		_destLastRow = destLastRow;
		_destLastColumn = destLastColumn;
		_reserveType = reserveType;
		_destWholeColumn = destWholeColumn;
		_destWholeRow = destWholeRow; //ZSS-1277
	}
	//ZSS-717
	//@since 3.8.3
	@Deprecated
	public Abstract2DCellDataStyleAction(String label,Sheet sheet,int srcRow, int srcColumn, int srcLastRow,int srcLastColumn,boolean srcWholeColumn,
			Sheet destSheet,int destRow, int destColumn, int destLastRow,int destLastColumn,boolean destWholeColumn, int reserveType){
		this(label,sheet,srcRow,srcColumn,srcLastRow,srcLastColumn,srcWholeColumn,false,
				destSheet,destRow,destColumn,destLastRow,destLastColumn,destWholeColumn,false,reserveType);
	}
	public Abstract2DCellDataStyleAction(String label,Sheet sheet,int srcRow, int srcColumn, int srcLastRow,int srcLastColumn,
			Sheet destSheet,int destRow, int destColumn, int destLastRow,int destLastColumn,int reserveType){
		this(label,sheet,srcRow, srcColumn, srcLastRow,srcLastColumn,false,false, //ZSS-1277
			destSheet,destRow, destColumn, destLastRow,destLastColumn,false,false,reserveType); //ZSS-1277
	}
	
	@Override
	protected boolean isSheetProtected(){
		return isAnyCellProtected(_sheet, new CellRegion(_row, _column, _lastRow, _lastColumn));
	}
	protected boolean isSrcSheetProtected() {
		return super.isSheetProtected();
	}
	protected boolean isDstSheetProtected(){
		try{
			return _destSheet.isProtected();
		}catch(Exception x){}
		return true;
	}
	
	@Override
	public Sheet getUndoSheet(){
		return _destSheet;
	}
	@Override
	public Sheet getRedoSheet(){
		return _destSheet;
	}
	
	protected int getReservedSrcRow(){
		return _row;
	}
	protected int getReservedSrcColumn(){
		return _column;
	}
	protected int getReservedSrcLastRow(){
		return _lastRow;
	}
	protected int getReservedSrcLastColumn(){
		return _lastColumn;
	}
	protected Sheet getReservedSrcSheet(){
		return _sheet;
	}
	
	protected int getReservedDestRow(){
		return _destRow;
	}
	protected int getReservedDestColumn(){
		return _destColumn;
	}
	protected int getReservedDestLastRow(){
		return _destLastRow;
	}
	protected int getReservedDestLastColumn(){
		return _destLastColumn;
	}
	protected Sheet getReservedDestSheet(){
		return _destSheet;
	}
	

	@Override
	public void doAction() {
		if(isSheetProtected()) return;
		//keep old style/data of src and dest
		
		int srcRow = getReservedSrcRow();
		int srcColumn = getReservedSrcColumn();
		int srcLastRow = getReservedSrcLastRow();
		int srcLastColumn = getReservedSrcLastColumn();
		Sheet srcSheet = getReservedSrcSheet();
		
		int destRow = getReservedDestRow();
		int destColumn = getReservedDestColumn();
		int destLastRow = getReservedDestLastRow();
		int destLastColumn = getReservedDestLastColumn();
		Sheet destSheet = getReservedDestSheet();
		
		_srcOldReserve = ReserveUtil.reserve(srcSheet.getInternalSheet(), srcRow, srcColumn, srcLastRow, srcLastColumn, _reserveType);
		_destOldReserve = ReserveUtil.reserve(destSheet.getInternalSheet(), destRow, destColumn, destLastRow, destLastColumn, _reserveType);
		
		applyAction();
	}

	
	protected void applyAction(){
		Range src = Ranges.range(_sheet,_row,_column,_lastRow,_lastColumn);
		Range dest = Ranges.range(_destSheet,_destRow,_destColumn,_destLastRow,_destLastColumn);
		_pastedRange = CellOperationUtil.cut(src, dest);
	}
	
	@Override
	public boolean isUndoable() {
		return _destOldReserve!=null && isSheetAvailable() && !isSheetProtected();
	}

	@Override
	public boolean isRedoable() {
		return _destOldReserve==null && isSheetAvailable() && !isSheetProtected();
	}

	@Override
	public void undoAction() {
		if(isSheetProtected()) return;
		
		_destOldReserve.restore();
		_srcOldReserve.restore();
		_srcOldReserve = null;
		_destOldReserve = null;
	}
	
	@Override
	public AreaRef getRedoSelection(){
		return new AreaRef(_destRow,_destColumn,_destLastRow,_destLastColumn);
	}
}
