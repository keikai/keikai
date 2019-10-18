/*
{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Aug 21, 2014, Created by henri
}}IS_NOTE

Copyright (C) 2014 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/

package io.keikai.ui.impl;

import java.util.List;

import io.keikai.api.CellOperationUtil;
import io.keikai.api.CellVisitor;
import io.keikai.api.Range;
import io.keikai.api.model.Sheet;
import io.keikai.model.SCell;
import io.keikai.model.impl.AbstractCellAdv;
import io.keikai.ui.sys.UndoableAction;
import io.keikai.ui.impl.undo.CellRichTextAction;

/**
 * @author henri
 * @since 3.6.0
 */
public class ActionHelper {

	/**
	 * Scan the given range and collect rich text cells actions which will be applied with the applier.
	 * @param range
	 * @param applier
	 * @param actions
	 */
	//ZSS-752
	public static void collectRichTextStyleActions(
			Range range, final CellOperationUtil.CellStyleApplier applier, final List<UndoableAction> actions) {
		//scan each cell of the range and prepare RichTextAction for cells with richtext
		range.visit(new CellVisitor() {
			@Override
			public boolean visit(Range cellRange) {
				final int row = cellRange.getRow();
				final int column = cellRange.getColumn();
				final Sheet sheet = cellRange.getSheet();
				final SCell cell = sheet.getInternalSheet().getCell(row, column);
				if (!((AbstractCellAdv)cell).isRichTextValue())
					return true;
				
				final UndoableAction action =
						new CellRichTextAction("",sheet,row,column,row,column, applier);
				actions.add(action);
				return true;
			}
			

			@Override
			public boolean ignoreIfNotExist(int row, int column) {
				return true;
			}

			@Override
			public boolean createIfNotExist(int row, int column) {
				return false;
			}
		});
	}
}
