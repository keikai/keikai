/* FontFamilyAction.java

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
import io.keikai.api.CellOperationUtil;
import io.keikai.api.Range;
import io.keikai.api.Ranges;
import io.keikai.api.model.Sheet;
import io.keikai.ui.CellSelectionType;
import io.keikai.ui.UserActionContext;
import io.keikai.ui.sys.UndoableActionManager;
import org.zkoss.lang.Strings;
import org.zkoss.util.resource.Labels;
import io.keikai.ui.impl.undo.CellStyleAction;

/**
 * @author dennis
 * @author henrichen
 * @since 3.7.0
 */
public class BackColorHandler extends AbstractCellHandler {
	private static final long serialVersionUID = 7657041154984979049L;

	@Override
	protected boolean processAction(UserActionContext ctx) {
		Sheet sheet = ctx.getSheet();
		AreaRef selection = ctx.getSelection();
		CellSelectionType type = ctx.getSelectionType();
		Range range = Ranges.range(sheet, selection);
		//ZSS-576
		if (range.isProtected() && !range.getSheetProtection().isFormatCellsAllowed()) {
			showProtectMessage();
			return true;
		}
		//zss-623, extends to row,column area
		switch(type){
		case ROW:
			range = range.toRowRange();
			break;
		case COLUMN:
			range = range.toColumnRange();
			break;
		case ALL:
			//we don't allow to set whole sheet style, use column range instead 
			range = range.toColumnRange();
		}
		selection = new AreaRef(range.getRow(),range.getColumn(),range.getLastRow(),range.getLastColumn());
		String color = getColor(ctx);
		
		UndoableActionManager uam = ctx.getSpreadsheet().getUndoableActionManager();
		uam.doAction(new CellStyleAction(Labels.getLabel("zss.undo.cellStyle"),sheet, selection.getRow(), selection.getColumn(), 
				selection.getLastRow(), selection.getLastColumn(), 
				CellOperationUtil.getBackColorApplier(range
					.getCellStyleHelper().createColorFromHtmlColor(color))));
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
		return "#FFFFFF";
	}
}
