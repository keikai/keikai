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
public class GreaterThan2 extends BaseMatch2 {
	private static final long serialVersionUID = -2673045590406268437L;
	
	public GreaterThan2(Object b) {
		super(b);
	}
	public GreaterThan2(RuleInfo ruleInfo) {
		super(ruleInfo);
	}
	
	protected boolean matchString(String text, String b) {
		return text.compareTo(b) > 0;
	}

	protected boolean matchDouble(Double num, Double b) {
		return num.compareTo(b) > 0;
	}
}
