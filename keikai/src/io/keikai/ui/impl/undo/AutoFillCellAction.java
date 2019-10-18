/* AutofillCellAction.java

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
import io.keikai.api.model.EditableCellStyle;
import io.keikai.api.model.Sheet;
import io.keikai.model.CellRegion;

/**
 * 
 * @author dennis
 * 
 */
public class AutoFillCellAction extends Abstract2DCellDataStyleAction {
	private static final long serialVersionUID = -1425828318191238422L;
	
	private final Range.AutoFillType _fillType;
	
	public AutoFillCellAction(String label, Sheet sheet,int srcRow, int srcColumn, int srcLastRow,int srcLastColumn,
			Sheet destSheet,int destRow, int destColumn, int destLastRow,int destLastColumn,
			Range.AutoFillType fillType){
		super(label,sheet,srcRow,srcColumn,srcLastRow,srcLastColumn,destSheet,destRow,destColumn,destLastRow,destLastColumn,RESERVE_ALL);
		_fillType = fillType;
	}
	
	@Override
	protected boolean isSheetProtected(){
		return isAnyCellProtected(_destSheet, new CellRegion(_destRow, _destColumn, _destLastRow, _destLastColumn));
	}

	protected void applyAction(){
		Range src = Ranges.range(_sheet,_row,_column,_lastRow,_lastColumn);
		Range dest = Ranges.range(_destSheet,_destRow,_destColumn,_destLastRow,_destLastColumn);
		CellOperationUtil.autoFill(src, dest, _fillType);
		if (isDstSheetProtected()){
			//recover overriden locked status  during copying from locked cells
			EditableCellStyle recoveredStyle = dest.getCellStyleHelper().createCellStyle(dest.getCellStyle());
			recoveredStyle.setLocked(false);
			dest.setCellStyle(recoveredStyle);
		}
	}
}
