/* DeleteCellAction.java

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

import io.keikai.api.CellOperationUtil;
import io.keikai.api.Range;
import io.keikai.api.Ranges;
import io.keikai.api.model.Sheet;
import org.zkoss.lang.Objects;
import org.zkoss.util.resource.Labels;

/**
 * 
 * @author dennis
 *
 */
public class InsertCellAction extends AbstractUndoableAction {
	private static final long serialVersionUID = 2494062898483762218L;
	
	Range.InsertShift _shift;
	Range.InsertCopyOrigin _copyOrigin;
	boolean _doFlag;
	
	
	public InsertCellAction(String label, Sheet sheet,int row, int column, int lastRow,int lastColumn,
			Range.InsertShift shift,
			Range.InsertCopyOrigin copyOrigin){
		super(label,sheet,row,column,lastRow,lastColumn);
		this._shift = shift;
		this._copyOrigin = copyOrigin;
	}

	@Override
	public void doAction() {
		if(isSheetProtected()) return;
		_doFlag = true;
		Range r = Ranges.range(_sheet, _row, _column, _lastRow, _lastColumn);
		CellOperationUtil.insert(r,_shift, _copyOrigin);
	}

	@Override
	public boolean isUndoable() {
		return _doFlag && isSheetAvailable() && !isSheetProtected();
	}

	@Override
	public boolean isRedoable() {
		return !_doFlag && isSheetAvailable() && !isSheetProtected();
	}

	@Override
	public void undoAction() {
		if(isSheetProtected()) return;
		Range r = Ranges.range(_sheet, _row, _column, _lastRow, _lastColumn);
		switch(_shift){
			case DOWN:
				CellOperationUtil.delete(r, Range.DeleteShift.UP);
				break;
			case RIGHT:
				CellOperationUtil.delete(r, Range.DeleteShift.LEFT);
				break;
			case DEFAULT:
				CellOperationUtil.delete(r, Range.DeleteShift.DEFAULT);
				break;
		}
		_doFlag = false;
	}

	@Override
	protected boolean isSheetProtected(){
		final Range range = Ranges.range(_sheet);
		return super.isSheetProtected() && 
				!(Objects.equals(Labels.getLabel("zss.undo.insertColumn"), getLabel()) &&
						range.getSheetProtection().isInsertColumnsAllowed()) &&
				!(Objects.equals(Labels.getLabel("zss.undo.insertRow"), getLabel()) &&
						range.getSheetProtection().isInsertRowsAllowed());
	}

}
