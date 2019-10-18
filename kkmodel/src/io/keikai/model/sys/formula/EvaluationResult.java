/*

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2013/12/01 , Created by dennis
}}IS_NOTE

Copyright (C) 2013 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package io.keikai.model.sys.formula;

import org.zkoss.poi.ss.formula.eval.ValueEval;

/**
 * 
 * @author dennis
 * @since 3.5.0
 */
public interface EvaluationResult {
	/**
	 * @since 3.5.0
	 */
	public enum ResultType{
		SUCCESS,ERROR
	}
	
	ResultType getType();
	Object getValue();
	ValueEval getValueEval(); //ZSS-810
	
}
