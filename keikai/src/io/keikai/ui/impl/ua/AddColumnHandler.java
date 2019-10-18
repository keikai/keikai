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
public class AddColumnHandler extends AbstractHandler{
	private static final long serialVersionUID = -4625955856473698048L;

	@Override
	protected boolean processAction(UserActionContext ctx) {
		SSheet sheet = ctx.getSheet().getInternalSheet();
		Spreadsheet ss = ctx.getSpreadsheet();
		if (ss.isShowAddColumn()) {
			ss.setSheetMaxVisibleColumns(sheet, ss.getSheetMaxVisibleColumns(sheet) + 1);
		}
		return true;
	}
}
