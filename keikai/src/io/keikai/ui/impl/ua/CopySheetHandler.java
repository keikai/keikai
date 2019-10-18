/* CopySheetHandler.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2014/9/3 , Created by jerrychen
}}IS_NOTE

Copyright (C) 2014 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package io.keikai.ui.impl.ua;

import io.keikai.api.Range;
import io.keikai.api.Ranges;
import io.keikai.api.SheetOperationUtil;
import io.keikai.ui.UserActionContext;

/**
 * @author jerrychen
 * @since 3.6.0
 */
public class CopySheetHandler extends AbstractBookHandler{
	private static final long serialVersionUID = 7305083772423271908L;

	@Override
	protected boolean processAction(UserActionContext ctx) {
		Range range = Ranges.range(ctx.getSheet());
		SheetOperationUtil.CopySheet(range);
		
		return true;
	}

}