/*

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		
}}IS_NOTE

Copyright (C) 2013 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package io.keikai.model.impl.chart;

import io.keikai.model.SChart;

/**
 * 
 * @author Dennis
 * @since 3.5.0
 */
public class UnsupportedChartDataImpl extends ChartDataAdv {

	private static final long serialVersionUID = 1L;
	private SChart _chart;
	public UnsupportedChartDataImpl(SChart chart){
		this._chart = chart;
	}

	@Override
	public SChart getChart() {
		return _chart;
	}

	@Override
	public void clearFormulaResultCache() {
	}

	@Override
	public boolean isFormulaParsingError() {
		return false;
	}

	@Override
	public void destroy() {
		_chart = null;
	}

	@Override
	public void checkOrphan() {
		if(_chart==null){
			throw new IllegalStateException("doesn't connect to parent");
		}
	}

}
