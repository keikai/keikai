/* Between.java

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
public class ContainsText extends BaseMatch2 {
	public ContainsText(String base) {
		super(base);
	}
	
	public ContainsText(RuleInfo ruleInfo) {
		super(ruleInfo);
	}
	
	protected boolean matchString(String text, String b) {
		return text.indexOf(b) >= 0;
	}

	protected boolean matchDouble(Double num, Double b) {
		return false;
	}
}

