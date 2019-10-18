/* AbstractTableStyleInfoAdv.java

	Purpose:
		
	Description:
		
	History:
		May 4, 2016 2:54:58 PM, Created by henrichen

	Copyright (C) 2016 Potix Corporation. All Rights Reserved.
*/

package io.keikai.model.impl;

import java.io.Serializable;

import io.keikai.model.SBook;
import io.keikai.model.STableStyleInfo;

/**
 * @author henri
 * @since 3.9.0
 */
public abstract class AbstractTableStyleInfoAdv implements STableStyleInfo, Serializable {
	//ZSS-1183
	/*package*/ abstract AbstractTableStyleInfoAdv cloneTableStyleInfo(
			SBook book);
}
