/* GreaterThan.java

	Purpose:
		
	Description:
		
	History:
		May 18, 2016 3:13:53 PM, Created by henrichen

	Copyright (C) 2016 Potix Corporation. All Rights Reserved.
*/

package io.keikai.range.impl;

import io.keikai.model.impl.RuleInfo;

/**
 * @author henri
 * @since 3.9.0
 */
public class Equals extends BaseMatch2 {
	private static final long serialVersionUID = -2673045590406268437L;
	
	public Equals(Object b) {
		super(b);
	}
	public Equals(RuleInfo ruleInfo) {
		super(ruleInfo);
	}
			
	protected boolean matchString(String text, String b) {
		return text.equals(b);
	}

	protected boolean matchDouble(Double num, Double b) {
		return num.compareTo(b) == 0;
	}
}
