/* AbstractSheetAwareHandler.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2013/8/2 , Created by dennis
}}IS_NOTE

Copyright (C) 2013 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package io.keikai.ui.impl.ua;

import io.keikai.api.model.Book;
import io.keikai.api.model.Sheet;

/**
 * @author dennis
 * @since 3.0.0
 */
public abstract class AbstractSheetHandler extends AbstractHandler{
	private static final long serialVersionUID = -7340830617468477287L;

	@Override
	public boolean isEnabled(Book book, Sheet sheet) {
		return book!=null && sheet!=null;
	}
}
