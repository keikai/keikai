/* DisplayGridlinesAction.java

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

import io.keikai.api.Range;
import io.keikai.api.Ranges;
import io.keikai.api.SheetOperationUtil;
import io.keikai.api.model.Sheet;
import io.keikai.ui.UserActionContext;
import io.keikai.ui.impl.ua.AbstractBookHandler;

/**
 * @author dennis
 *
 */
public class DisplayGridlinesAction extends AbstractBookHandler {
	private static final long serialVersionUID = 8248030146794007377L;

	/* (non-Javadoc)
	 * @see io.keikai.ui.sys.ua.impl.AbstractHandler#processAction(io.keikai.ui.UserActionContext)
	 */
	@Override
	protected boolean processAction(UserActionContext ctx) {
		Sheet sheet = ctx.getSheet();
		
		Range range = Ranges.range(sheet);
		
		SheetOperationUtil.displaySheetGridlines(range,!range.isDisplaySheetGridlines());
		return true;
	}

}
