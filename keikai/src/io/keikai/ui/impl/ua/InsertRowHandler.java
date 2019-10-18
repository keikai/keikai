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
import io.keikai.ui.impl.undo.InsertCellAction;

/**
 * @author dennis
 *
 */
public class InsertRowHandler extends AbstractHandler {
	private static final long serialVersionUID = -9104949440019029413L;

	/* (non-Javadoc)
	 * @see io.keikai.ui.sys.ua.impl.AbstractHandler#processAction(io.keikai.ui.UserActionContext)
	 */
	@Override
	protected boolean processAction(UserActionContext ctx) {
		Sheet sheet = ctx.getSheet();
		AreaRef selection = ctx.getSelection();
		Range range = Ranges.range(sheet, selection);
		range = range.toRowRange();
		//work around for ZSS-404 JS Error after insert column when freeze
		if(checkInCornerFreezePanel(range)){
			throw new IllegalOpArgumentException(Labels.getLabel("zss.msg.operation_not_supported_with_freeze_panel"));
		}
		// work around for ZSS-586: don't allow insert rows when select whole visible rows
		int row = range.getRow();
		int lastRow = range.getLastRow();
		int maxVisibleRows = ctx.getSpreadsheet().getCurrentMaxVisibleRows(); //ZSS-1084
		if(lastRow - row + 1 == maxVisibleRows) {
			throw new IllegalOpArgumentException(Labels.getLabel("zss.msg.operation_not_supported_with_all_row"));
		}
		
		UndoableActionManager uam = ctx.getSpreadsheet().getUndoableActionManager();
		uam.doAction(new InsertCellAction(Labels.getLabel("zss.undo.insertRow"),sheet, range.getRow(), range.getColumn(), 
				range.getLastRow(), range.getLastColumn(), 
				Range.InsertShift.DOWN, Range.InsertCopyOrigin.FORMAT_LEFT_ABOVE)); // ZSS-404, Excel default behavior is left or above
		ctx.clearClipboard();
		return true;
	}

	@Override
	public boolean isEnabled(Book book, Sheet sheet) {
		return book != null && sheet != null && (!sheet.isProtected() ||
				Ranges.range(sheet).getSheetProtection().isInsertRowsAllowed());
	}

}
