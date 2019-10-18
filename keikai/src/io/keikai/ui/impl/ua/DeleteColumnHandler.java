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
import io.keikai.api.model.Book;
import io.keikai.api.model.Sheet;
import io.keikai.ui.UserActionContext;
import io.keikai.ui.sys.UndoableActionManager;
import org.zkoss.util.resource.Labels;
import io.keikai.ui.impl.undo.DeleteCellAction;

/**
 * @author dennis
 *
 */
public class DeleteColumnHandler extends AbstractHandler {
	private static final long serialVersionUID = -1446768788646889555L;

	/* (non-Javadoc)
	 * @see io.keikai.ui.sys.ua.impl.AbstractHandler#processAction(io.keikai.ui.UserActionContext)
	 */
	@Override
	protected boolean processAction(UserActionContext ctx) {
		Sheet sheet = ctx.getSheet();
		AreaRef selection = ctx.getSelection();
		Range range = Ranges.range(sheet, selection);
		range = range.toColumnRange();
		//work around for ZSS-404 JS Error after insert column when freeze
		if(checkInCornerFreezePanel(range)){
			throw new IllegalOpArgumentException(Labels.getLabel("zss.msg.operation_not_supported_with_freeze_panel"));
		}
		UndoableActionManager uam = ctx.getSpreadsheet().getUndoableActionManager();
		uam.doAction(new DeleteCellAction(Labels.getLabel("zss.undo.deleteColumn"),sheet, range.getRow(), range.getColumn(), 
				range.getLastRow(), range.getLastColumn(), 
				Range.DeleteShift.LEFT));
		ctx.clearClipboard();
		return true;
	}

	@Override
	public boolean isEnabled(Book book, Sheet sheet) {
		return book != null && sheet != null && (!sheet.isProtected() ||
				Ranges.range(sheet).getSheetProtection().isDeleteColumnsAllowed());
	}

}
