/* ContainsError.java

	Purpose:
		
	Description:
		
	History:
		May 18, 2016 3:13:53 PM, Created by henrichen

	Copyright (C) 2016 Potix Corporation. All Rights Reserved.
*/

package io.keikai.range.impl;

import java.io.Serializable;

import io.keikai.model.SCell;

/**
 * @author henri
 * @since 3.9.0
 */
public class ContainsError implements Matchable<SCell>, Serializable {
	private static final long serialVersionUID = -1055754523050091894L;
	
	boolean _not = false;
	public ContainsError(boolean not) {
		this._not = not;
	}
	@Override
	public boolean match(SCell cell) {
		return _not ? !_match0(cell) : _match0(cell);
	}
	
	private boolean _match0(SCell cell) {
		return cell != null && !cell.isNull() && getCellType(cell) == SCell.CellType.ERROR;
	}

	private SCell.CellType getCellType(SCell cell) {
		SCell.CellType type = cell.getType();
		if (type == SCell.CellType.FORMULA) {
			type = cell.getFormulaResultType();
		}
		return type;
	}
}
