/* ExtraStyleImpl.java

	Purpose:
		
	Description:
		
	History:
		Oct 26, 2015 3:58:03 PM, Created by henrichen

	Copyright (C) 2015 Potix Corporation. All Rights Reserved.
*/

package io.keikai.model.impl;

import io.keikai.model.SBook;
import io.keikai.model.SBorder;
import io.keikai.model.SBorderLine;
import io.keikai.model.SCellStyle;
import io.keikai.model.SColor;
import io.keikai.model.SExtraStyle;
import io.keikai.model.SFill;
import io.keikai.model.SFont;
import io.keikai.model.util.Strings;
import io.keikai.model.util.Validations;

/**
 * @author henri
 * @since 3.8.2
 */
public class ExtraStyleImpl extends CellStyleImpl implements SExtraStyle {
	private static final long serialVersionUID = -3304797955338410853L;

	//ZSS-1183
	//@since 3.9.0
	/*package*/ ExtraStyleImpl(ExtraStyleImpl src, BookImpl book) {
		super(src, book);
	}
	
	public ExtraStyleImpl(SFont font, SFill fill, SBorder border, String dataFormat) {
		super((AbstractFontAdv)font, (AbstractFillAdv) fill, (AbstractBorderAdv) border);
		//ZSS-1183
		if(fill!=null) {
			Validations.argInstance(fill, ExtraFillImpl.class);
		}

		if (dataFormat != null && !Strings.isBlank(dataFormat))
			setDataFormat(dataFormat);
	}

	//ZSS-1145
	@Override
	public SBorder.BorderType getBorderLeft() {
		if (_border == null) return null;
		SBorderLine bline = _border.getLeftLine();
		return bline == null ? null : bline.getBorderType();
	}

	//ZSS-1145
	@Override
	public SBorder.BorderType getBorderTop() {
		if (_border == null) return null;
		SBorderLine bline = _border.getTopLine();
		return bline == null ? null : bline.getBorderType();
	}

	//ZSS-1145
	@Override
	public SBorder.BorderType getBorderRight() {
		if (_border == null) return null;
		SBorderLine bline = _border.getRightLine();
		return bline == null ? null : bline.getBorderType();
	}
	
	//ZSS-1145
	@Override
	public SBorder.BorderType getBorderBottom() {
		if (_border == null) return null;
		SBorderLine bline = _border.getBottomLine();
		return bline == null ? null : bline.getBorderType();
	}
	
	//ZSS-1145
	@Override
	public SBorder.BorderType getBorderDiagonal() {
		if (_border == null) return null;
		SBorderLine bline = _border.getDiagonalLine();
		return bline == null ? null : bline.getBorderType();
	}

	//ZSS-1145
	@Override
	public SBorder.BorderType getBorderVertical() {
		if (_border == null) return null;
		SBorderLine bline = _border.getVerticalLine();
		return bline == null ? null : bline.getBorderType();
	}

	//ZSS-1145
	@Override
	public SBorder.BorderType getBorderHorizontal() {
		if (_border == null) return null;
		SBorderLine bline = _border.getHorizontalLine();
		return bline == null ? null : bline.getBorderType();
	}

	//ZSS-1145
	@Override
	public String getDataFormat() {
		return _dataFormat;
	}
	
	//ZSS-1162: Use ExtraFillImpl
	@Override
	public void setBackColor(SColor backColor) {
		Validations.argNotNull(backColor);
		if (_fill == null) {
			_fill = new ExtraFillImpl();
		}
		_fill.setBackColor(backColor);
	}

	//ZSS-1162: Use ExtraFillImpl
	@Override
	public void setFillColor(SColor fillColor) {
		Validations.argNotNull(fillColor);
		if (_fill == null) {
			_fill = new ExtraFillImpl();
		}
		_fill.setFillColor(fillColor);
	}

	//ZSS-1162: Use ExtraFillImpl
	@Override
	public void setFillPattern(SFill.FillPattern fillPattern) {
		Validations.argNotNull(fillPattern);
		if (_fill == null) {
			_fill = new ExtraFillImpl();
		}
		_fill.setFillPattern(fillPattern);
	}
	
	//ZSS-1183
	//@since 3.9.0
	@Override
	/*package*/ SCellStyle createCellStyle(SBook book) {
		return new ExtraStyleImpl(this, (BookImpl) book);
	}
}
