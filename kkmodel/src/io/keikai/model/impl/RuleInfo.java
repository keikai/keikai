/* RuleInfo.java

	Purpose:
		
	Description:
		
	History:
		Jun 24, 2016 4:38:14 PM, Created by henrichen

	Copyright (C) 2016 Potix Corporation. All Rights Reserved.
*/

package io.keikai.model.impl;

import java.util.HashMap;
import java.util.Map;

import io.keikai.model.SCell;
import io.keikai.model.SSheet;
import io.keikai.model.sys.EngineFactory;
import io.keikai.model.sys.formula.EvaluationResult;
import io.keikai.model.sys.formula.FormulaEngine;
import io.keikai.model.sys.formula.FormulaEvaluationContext;
import io.keikai.model.sys.formula.FormulaExpression;

/**
 * @author Henri
 *
 */
public class RuleInfo {
	private SSheet _sheet;
	private ConditionalFormattingRuleImpl _rule;
	private int row0;
	private int col0;
	private FormulaExpression _formulaExpr;

	private Map<String, EvaluationResult> _cacheMap; 
	
	RuleInfo(SSheet sheet, ConditionalFormattingRuleImpl rule, FormulaExpression formulaExpr, int row0, int col0) {
		this._sheet = sheet;
		this._rule = rule;
		this._formulaExpr = formulaExpr;
		this.row0 = row0;
		this.col0 = col0;
		_cacheMap = new HashMap<String, EvaluationResult>();
		if (formulaExpr != null) {
			rule.addDependency(formulaExpr);
		}
	}
	
	public EvaluationResult evalFormula(SCell cell){
		synchronized (this) {
			final int row = cell == null ? row0 : cell.getRowIndex();
			final int col = cell == null ? col0 : cell.getColumnIndex();
			final String key = ""+row+"_"+col;
			final EvaluationResult cache = _cacheMap.get(key);
			if (cache != null) {
				return cache;
			}
			
			final int rowOffset = row - row0;
			final int colOffset = col - col0;
			FormulaEngine fe = EngineFactory.getInstance().createFormulaEngine();
			if(_formulaExpr!=null) {
//				if (!_rule.hasNoRelative()) {
//					if (prepareDependency(rowOffset, colOffset)) {
//						_rule.setNoRelative(true);
//					}
//				}
				final EvaluationResult result = 
					fe.evaluate(_formulaExpr,new FormulaEvaluationContext(
							_sheet, cell, null, new int[] {rowOffset, colOffset}, true)); //ZSS-1257, ZSS-1271
				_cacheMap.put(key, result);
				return result;
			}
			return null;
		}
	}
	
//	public void clearCache(int row, int col) {
//		_cacheMap.remove(""+row+"_"+col);
//	}
	
	public void clearCacheMap() {
		_cacheMap = new HashMap<String, EvaluationResult>();
	}
}
