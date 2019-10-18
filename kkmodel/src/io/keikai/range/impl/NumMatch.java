/* TrueMatch.java

	Purpose:
		
	Description:
		
	History:
		May 18, 2016 3:13:53 PM, Created by henrichen

	Copyright (C) 2016 Potix Corporation. All Rights Reserved.
*/

package io.keikai.range.impl;

import java.io.Serializable;

import io.keikai.model.SCell;
import io.keikai.model.impl.AbstractCellAdv;
import io.keikai.model.impl.CellValue;

/**
 * return true if a number
 * @author henri
 * @since 3.9.0
 */
public class NumMatch implements Matchable<SCell>, Serializable {
	private static final long serialVersionUID = -6497660602871191241L;
	
	@Override
	public boolean match(SCell cell) {
		final CellValue cv = ((AbstractCellAdv)cell).getEvalCellValue(true);
		return cv.getType() == SCell.CellType.NUMBER;
	}
}
