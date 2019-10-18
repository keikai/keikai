/* BorderHandler.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2013/8/5 , Created by dennis
}}IS_NOTE

Copyright (C) 2013 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package io.keikai.ui.impl.ua;

import io.keikai.api.AreaRef;
import io.keikai.api.Range;
import io.keikai.api.Ranges;
import io.keikai.api.model.CellStyle;
import io.keikai.api.model.Sheet;
import io.keikai.ui.UserActionContext;
import io.keikai.ui.sys.UndoableActionManager;
import org.zkoss.lang.Strings;
import org.zkoss.util.resource.Labels;
import io.keikai.ui.impl.undo.CellBorderAction;

/**
 * @author dennis
 *
 */
public class ApplyBorderHandler extends AbstractCellHandler {
	private static final long serialVersionUID = 8882420154583823676L;
	private final Range.ApplyBorderType _applyType;
	private final CellStyle.BorderType _borderType;
	public ApplyBorderHandler(
			Range.ApplyBorderType applyType, CellStyle.BorderType borderType) {
		this._applyType = applyType;
		this._borderType = borderType;
	}

	@Override
	protected boolean processAction(UserActionContext ctx) {
		Sheet sheet = ctx.getSheet();
		AreaRef selection = ctx.getSelection();
		Range range = Ranges.range(sheet, selection);
		//ZSS-576: EnhancedProtection
		if (range.isProtected() && !range.getSheetProtection().isFormatCellsAllowed()) {
			showProtectMessage();
			return true;
		}
		//zss-623, Border doesn't support to handle row/column style, can't extends it to whole row,whole style
//		CellSelectionType type = ctx.getSelectionType();
//		switch(type){
//		case ROW:
//			range = range.toRowRange();
//			break;
//		case COLUMN:
//			range = range.toColumnRange();
//			break;
//		case ALL:
//			range = range.toColumnRange().toRowRange();
//		}
//		selection = new AreaRef(range.getRow(),range.getColumn(),range.getLastRow(),range.getLastColumn());
		
		String color = getColor(ctx);
		
		UndoableActionManager uam = ctx.getSpreadsheet().getUndoableActionManager();
		uam.doAction(new CellBorderAction(Labels.getLabel("zss.undo.cellBorder"),sheet, selection.getRow(), selection.getColumn(), 
					selection.getLastRow(), selection.getLastColumn(), 
					_applyType, _borderType, color));
		return true;
	}

	protected String getColor(UserActionContext ctx){
		String color = (String)ctx.getData("color");
		if (Strings.isEmpty(color)) {//CE version won't provide color
			color = getDefaultColor();
		}
		return color;
	}
	
	protected String getDefaultColor() {
		return "#000000";
	}
}
