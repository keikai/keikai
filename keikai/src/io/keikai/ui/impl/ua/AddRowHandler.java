/* AddSheetHandler.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2013/8/3 , Created by dennis
}}IS_NOTE

Copyright (C) 2013 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package io.keikai.ui.impl.ua;

import io.keikai.model.SSheet;
import io.keikai.ui.Spreadsheet;
import io.keikai.ui.UserActionContext;

/**
 * @author henrichen
 * @since 3.8.1
 */
public class AddRowHandler extends AbstractHandler{
	private static final long serialVersionUID = -7913679604998553179L;

	@Override
	protected boolean processAction(UserActionContext ctx) {
		SSheet sheet = ctx.getSheet().getInternalSheet();
		Spreadsheet ss = ctx.getSpreadsheet();
		if (ss.isShowAddRow()) {
			ss.setSheetMaxVisibleRows(sheet, ss.getSheetMaxVisibleRows(sheet) + 1);
		}
		return true;
	}
}
