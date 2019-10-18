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
import io.keikai.api.Range;
import io.keikai.api.Ranges;
import io.keikai.api.model.Sheet;
import io.keikai.ui.CellSelectionType;
import io.keikai.ui.UserActionContext;
import io.keikai.ui.sys.UndoableActionManager;
import org.zkoss.util.resource.Labels;
import io.keikai.ui.impl.undo.ClearCellAction;
import io.keikai.ui.impl.undo.ClearCellAction.Type;

/**
 * @author dennis
 * 
 */
public class ClearCellHandler extends AbstractHandler {
	private static final long serialVersionUID = -3759122452949257087L;
	ClearCellAction.Type _type;

	public ClearCellHandler(Type type) {
		this._type = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * io.keikai.ui.sys.ua.impl.AbstractHandler#processAction(org.zkoss.
	 * zss.ui.UserActionContext)
	 */
	@Override
	protected boolean processAction(UserActionContext ctx) {
		Sheet sheet = ctx.getSheet();
		AreaRef selection = ctx.getSelection();
		CellSelectionType type = ctx.getSelectionType();
		Range range = Ranges.range(sheet, selection);
		//ZSS-576
		if(range.isProtected()){
			switch(type) {
			case ROW:
				if (range.getSheetProtection().isFormatRowsAllowed()) { 
					showProtectMessage();
					return true;
				}
				break;
			case COLUMN:
			case ALL:
				if (!range.getSheetProtection().isFormatColumnsAllowed()) {
					showProtectMessage();
					return true;
				}
				break;
			case CELL:
				if (!range.getSheetProtection().isFormatCellsAllowed()) {
					showProtectMessage();
					return true;
				}
				break;
			}
		}

		//TODO support zss-623, the implementation of ClearCellAction doesn't support we do this here
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
		
		String label = null;
		switch (_type) { //ZSS-692
		case ALL:
			label = Labels.getLabel("zss.undo.clearAll");
			break;
		case CONTENT:
			label = Labels.getLabel("zss.undo.clearContents");
			break;
		case STYLE:
			label = Labels.getLabel("zss.undo.clearStyles");
			break;
		}
		UndoableActionManager uam = ctx.getSpreadsheet()
				.getUndoableActionManager();
		uam.doAction(new ClearCellAction(label, sheet, selection.getRow(),
				selection.getColumn(), selection.getLastRow(), selection
						.getLastColumn(), _type));
		return true;
	}

}
