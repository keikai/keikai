/* InserCellRightHandler.java

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
import io.keikai.api.IllegalOpArgumentException;
import io.keikai.api.Range;
import io.keikai.api.Ranges;
import io.keikai.api.model.Sheet;
import io.keikai.ui.UserActionContext;
import io.keikai.ui.sys.UndoableActionManager;
import org.zkoss.util.resource.Labels;
import io.keikai.ui.impl.undo.InsertCellAction;

/**
 * @author dennis
 *
 */
public class InsertCellDownHandler extends AbstractProtectedHandler {
	private static final long serialVersionUID = -4198213882698815150L;

	/* (non-Javadoc)
	 * @see io.keikai.ui.sys.ua.impl.AbstractHandler#processAction(io.keikai.ui.UserActionContext)
	 */
	@Override
	protected boolean processAction(UserActionContext ctx) {
		Sheet sheet = ctx.getSheet();
		AreaRef selection = ctx.getSelection();
		Range range = Ranges.range(sheet, selection);
		//work around for ZSS-404 JS Error after insert column when freeze
		if(checkInCornerFreezePanel(range)){
			throw new IllegalOpArgumentException(Labels.getLabel("zss.msg.operation_not_supported_with_freeze_panel"));
		}
		UndoableActionManager uam = ctx.getSpreadsheet().getUndoableActionManager();
		uam.doAction(new InsertCellAction(Labels.getLabel("zss.undo.shiftCell"),sheet, selection.getRow(), selection.getColumn(), 
				selection.getLastRow(), selection.getLastColumn(), 
				Range.InsertShift.DOWN, Range.InsertCopyOrigin.FORMAT_LEFT_ABOVE)); // ZSS-404, Excel default behavior is left or above
		ctx.clearClipboard();
		return true;
	}
}
