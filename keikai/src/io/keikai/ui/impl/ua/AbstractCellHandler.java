/* AbstractCellFormatHandler.java

	Purpose:
		
	Description:
		
	History:
		Wed, Apr 30, 2014 10:23:05 AM, Created by RaymondChao

Copyright (C) 2014 Potix Corporation. All Rights Reserved.

*/
package io.keikai.ui.impl.ua;

import io.keikai.api.Ranges;
import io.keikai.api.model.Book;
import io.keikai.api.model.Sheet;

/**
 * 
 * @author RaymondChao
 * @since 3.5.0
 */
public abstract class AbstractCellHandler extends AbstractHandler {
	private static final long serialVersionUID = -1020093806000599051L;

	@Override
	public boolean isEnabled(Book book, Sheet sheet) {
		return book != null && sheet != null && ( !sheet.isProtected() ||
				Ranges.range(sheet).getSheetProtection().isFormatCellsAllowed());
	}
}
