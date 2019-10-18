/* RenameSheetAction.java

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

import io.keikai.api.Range;
import io.keikai.api.Ranges;
import io.keikai.api.SheetOperationUtil;
import io.keikai.api.model.Book;
import io.keikai.api.model.Sheet;
import io.keikai.ui.UserActionContext;
import org.zkoss.lang.Strings;
import org.zkoss.util.resource.Labels;

/**
 * @author dennis
 * @since 3.0.0
 */
public class RenameSheetHandler extends AbstractSheetHandler{
	private static final long serialVersionUID = -8419691290554319060L;

	@Override
	protected boolean processAction(UserActionContext ctx) {
		Book book = ctx.getBook();
		Sheet sheet = ctx.getSheet();
		String newname = (String) ctx.getData("name");
		if(sheet.getSheetName().equals(newname)){
			return true;
		}
		if(!isLeaglSheetName(newname)){
			showWarnMessage(Labels.getLabel("zss.actionhandler.msg.illegal_sheet_name", new Object[]{newname}));
			return true;
		}
		if(book.getSheet(newname)!=null){
			showWarnMessage(Labels.getLabel("zss.actionhandler.msg.duplicated_sheet_name"));
			return true;
		}
		
		Range range = Ranges.range(sheet);
		SheetOperationUtil.renameSheet(range,newname);
		return true;
	}
	
	protected boolean isLeaglSheetName(String newname) {
		if(Strings.isEmpty(newname))
			return false;
		
		if(newname.length()>31){
			return false;
		}
		
		// \/?*[] are illegal
		String regx = ".*[\\\\\\/\\?\\*\\[\\]]+.*";
		if(newname.matches(regx)){
			return false;
		}
		
		return true;
	}

}
