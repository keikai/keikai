/* SCustomFilter.java

	Purpose:
		
	Description:
		
	History:
		May 11, 2016 11:38:42 AM, Created by henrichen

	Copyright (C) 2016 Potix Corporation. All Rights Reserved.
*/

package io.keikai.model;

//ZSS-1224
/**
 * @author henri
 * @since 3.9.0
 */
public interface SCustomFilter {
	String getValue();
	SAutoFilter.FilterOp getOperator();
}
