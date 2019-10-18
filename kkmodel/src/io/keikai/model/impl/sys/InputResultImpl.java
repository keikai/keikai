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
package io.keikai.model.impl.sys;

import java.io.Serializable;

import io.keikai.model.SCell;
import io.keikai.model.sys.input.InputResult;
/**
 * 
 * @author Dennis
 * @since 3.5.0
 */
public class InputResultImpl implements InputResult, Serializable{
	private static final long serialVersionUID = 4291145732915114386L;
	
	private String _editText = null;
	private Object _value = null;
	private SCell.CellType _type = SCell.CellType.BLANK;
	private String _format = null;
	public InputResultImpl(){}
	public InputResultImpl(String input) {
		this._editText = input;
	}

	public String getEditText() {
		return _editText;
	}

	public Object getValue() {
		return _value;
	}

	public SCell.CellType getType() {
		return _type;
	}

	public void setValue(Object value) { //ZSS-1234
		this._value = value;
	}

	public void setType(SCell.CellType type) { //ZSS-1234
		this._type = type;
	}
	public String getFormat() {
		return _format;
	}
	void setFormat(String format) {
		this._format = format;
	}
}
