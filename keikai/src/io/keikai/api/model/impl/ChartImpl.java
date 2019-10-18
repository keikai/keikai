/* ChartImpl.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2013/5/1 , Created by dennis
}}IS_NOTE

Copyright (C) 2013 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package io.keikai.api.model.impl;

import java.io.Serializable;

import io.keikai.api.SheetAnchor;
import io.keikai.api.model.Chart;
import io.keikai.model.SChart;
import io.keikai.model.SSheet;
import io.keikai.model.ViewAnchor;

/**
 * 
 * @author dennis
 * @since 3.0.0
 */
public class ChartImpl implements Chart, Serializable {
	private static final long serialVersionUID = -3653351304880514228L;
	
	private ModelRef<SSheet> _sheetRef;
	private ModelRef<SChart> _chartRef;
	
	public ChartImpl(ModelRef<SSheet> sheetRef, ModelRef<SChart> chartRef) {
		this._sheetRef = sheetRef;
		this._chartRef = chartRef;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_chartRef == null) ? 0 : _chartRef.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChartImpl other = (ChartImpl) obj;
		if (_chartRef == null) {
			if (other._chartRef != null)
				return false;
		} else if (!_chartRef.equals(other._chartRef))
			return false;
		return true;
	}
	
	public SChart getNative() {
		return _chartRef.get();
	}
	
	public String getId(){
		return getNative().getId();
	}
	
	@Override
	public SheetAnchor getAnchor() {
		ViewAnchor anchor = getNative().getAnchor();
		return anchor==null?null:SheetImpl.toSheetAnchor(_sheetRef.get(), anchor);
	}
}
