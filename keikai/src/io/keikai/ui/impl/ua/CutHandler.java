/* CopyHandler.java

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

import io.keikai.api.AreaRefWithType;
import io.keikai.api.model.Sheet;
import io.keikai.ui.UserActionContext;

public class CutHandler extends AbstractHandler {
	private static final long serialVersionUID = 1848917655654382976L;

	@Override
	protected boolean processAction(UserActionContext ctx) {
		Sheet sheet = ctx.getSheet();
		AreaRefWithType selection = ctx.getSelectionWithType(); //ZSS-717
		ctx.setClipboard(sheet,selection,true,null);
		return true;
	}

}
