/* SortHandler.java

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
package io.keikai.ui.sys;

import org.zkoss.util.resource.Labels;
import io.keikai.api.Range;
import io.keikai.api.Ranges;
import io.keikai.api.AreaRef;
import io.keikai.api.model.Book;
import io.keikai.api.model.Sheet;
import io.keikai.model.CellRegion;
import io.keikai.range.SRange;
import io.keikai.range.impl.DataRegionHelper;
import io.keikai.ui.UserActionContext;
import io.keikai.ui.impl.ua.AbstractHandler;
import io.keikai.ui.impl.undo.SortCellAction;

/**
 * @author dennis
 *
 */
public class SortHandler extends AbstractHandler {
	private static final long serialVersionUID = 274722279166484811L;
	boolean _desc;
	public SortHandler(boolean desc) {
		this._desc = desc;
	}


	/* (non-Javadoc)
	 * @see io.keikai.ui.sys.ua.impl.AbstractHandler#processAction(io.keikai.ui.UserActionContext)
	 */
	@Override
	protected boolean processAction(UserActionContext ctx) {
		Sheet sheet = ctx.getSheet();
		AreaRef selection = ctx.getSelection();
		//ZSS-1261: select one cell == select all valid range
		SRange srcrng = Ranges.range(sheet, selection.getRow(), selection.getColumn(), selection.getLastRow(), selection.getLastColumn()).getInternalRange();
		CellRegion rng = new DataRegionHelper(srcrng).findCustomSortRegion();
		final AreaRef selection0 = new AreaRef(rng.getRow(),rng.getColumn(),rng.getLastRow(),rng.getLastColumn());
		
		final Range range = Ranges.range(sheet, selection0);
		if (range.isProtected()) {
			showProtectMessage();
			return true;
		}
		//ZSS-1261: index is based on currently selected cell/range.
		Range index1 = Ranges.range(sheet, rng.getRow(), selection.getColumn(), rng.getLastRow(), selection.getColumn());
		UndoableActionManager uam = ctx.getSpreadsheet().getUndoableActionManager();
		uam.doAction(new SortCellAction(Labels.getLabel("zss.undo.sortAsc"),sheet, selection0.getRow(), selection0.getColumn(),
			selection0.getLastRow(), selection0.getLastColumn(), index1, _desc));
		ctx.clearClipboard();
		return true;
	}

	@Override
	public boolean isEnabled(Book book, Sheet sheet) {
		return book != null && sheet != null && (!sheet.isProtected() ||
				Ranges.range(sheet).getSheetProtection().isSortAllowed());
	}

}
