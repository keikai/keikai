/* AbstractTableStyleAdv.java

	Purpose:
		
	Description:
		
	History:
		May 4, 2016 2:58:49 PM, Created by henrichen

	Copyright (C) 2016 Potix Corporation. All Rights Reserved.
*/

package io.keikai.model.impl;

import java.io.Serializable;

import io.keikai.model.SBook;
import io.keikai.model.STableStyle;

/**
 * @author henri
 * @since 3.9.0
 */
public abstract class AbstractTableStyleAdv implements STableStyle,
		Serializable {
	/*package*/ abstract AbstractTableStyleAdv cloneTableStyle(SBook book);
}
