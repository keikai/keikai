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

import java.util.ArrayList;
import java.util.List;

import io.keikai.api.AreaRef;
import io.keikai.api.CellOperationUtil;
import io.keikai.api.Range;
import io.keikai.api.Ranges;
import io.keikai.api.model.Sheet;
import io.keikai.ui.CellSelectionType;
import io.keikai.ui.UserActionContext;
import io.keikai.ui.impl.ActionHelper;
import io.keikai.ui.sys.UndoableAction;
import io.keikai.ui.sys.UndoableActionManager;
import org.zkoss.util.resource.Labels;
import io.keikai.ui.impl.undo.AggregatedAction;
import io.keikai.ui.impl.undo.FontStyleAction;

/**
 * @author dennis
 *
 */
public class FontItalicHandler extends AbstractCellHandler {
	private static final long serialVersionUID = 8778194802956921793L;

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
		
		boolean italic = !range.getCellStyle().getFont().isItalic();
		
		UndoableActionManager uam = ctx.getSpreadsheet().getUndoableActionManager();

		List<UndoableAction> actions = new ArrayList<UndoableAction>();
		actions.add(new FontStyleAction("", sheet, selection.getRow(), selection.getColumn(), 
				selection.getLastRow(), selection.getLastColumn(), 
				CellOperationUtil.getFontItalicApplier(italic)));
		ActionHelper.collectRichTextStyleActions(range, CellOperationUtil.getRichTextFontItalicApplier(italic), actions);
		AggregatedAction action = new AggregatedAction(Labels.getLabel("zss.undo.fontStyle"), actions.toArray(new UndoableAction[actions.size()]));
		
		uam.doAction(action);
		return true;
	}

}
