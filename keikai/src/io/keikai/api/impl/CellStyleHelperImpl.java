/* CellStyleHelperImpl.java

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
package io.keikai.api.impl;

import java.io.Serializable;

import io.keikai.api.Range;
import io.keikai.model.SBook;
import io.keikai.model.SColor;
import io.keikai.model.SFont;
import io.keikai.model.util.FontMatcher;
import io.keikai.api.model.Book;
import io.keikai.api.model.CellStyle;
import io.keikai.api.model.Color;
import io.keikai.api.model.EditableCellStyle;
import io.keikai.api.model.EditableFont;
import io.keikai.api.model.Font;
import io.keikai.api.model.Font.Boldweight;
import io.keikai.api.model.Font.TypeOffset;
import io.keikai.api.model.Font.Underline;
import io.keikai.api.model.impl.BookImpl;
import io.keikai.api.model.impl.ColorImpl;
import io.keikai.api.model.impl.EditableCellStyleImpl;
import io.keikai.api.model.impl.EditableFontImpl;
import io.keikai.api.model.impl.EnumUtil;
import io.keikai.api.model.impl.FontImpl;
import io.keikai.api.model.impl.SimpleRef;

/**
 * 
 * @author dennis
 * @since 3.0.0
 */
/*package*/ class CellStyleHelperImpl implements Range.CellStyleHelper, Serializable {
	private static final long serialVersionUID = 5913867573087033877L;
	
	private Book _book;
	
	public CellStyleHelperImpl(Book book) {
		this._book = book;
	}
	
	/**
	 * create a new cell style and clone from src if it is not null
	 * @param src the source to clone, could be null
	 * @return the new cell style
	 */
	public EditableCellStyle createCellStyle(CellStyle src){
		SBook book = (SBook)((BookImpl)_book).getNative();
		EditableCellStyle style = new EditableCellStyleImpl(((BookImpl)_book).getRef(),new SimpleRef(book.createCellStyle(true)));
		if(src!=null){
			((EditableCellStyleImpl)style).copyAttributeFrom(src);
		}
		return style;
	}

	public EditableFont createFont(Font src) {
		SBook book = (SBook)((BookImpl)_book).getNative();
		SFont font = book.createFont(true);

		EditableFont nf = new EditableFontImpl(((BookImpl)_book).getRef(),new SimpleRef(font));
		if(src!=null){
			((EditableFontImpl)nf).copyAttributeFrom(src);
		}
			
		return nf;
	}

	public Color createColorFromHtmlColor(String htmlColor) {
		Book book = _book;
		return new ColorImpl(((BookImpl) book).getRef(),
				new SimpleRef<SColor>(((BookImpl) book).getNative().createColor(htmlColor)));
		
	}

	public Font findFont(Boldweight boldweight, Color color,
			int fontHeightPoints, String fontName, boolean italic,
			boolean strikeout, TypeOffset typeOffset, Underline underline) {
		SBook book = ((BookImpl) _book).getNative();

		FontMatcher fm = new FontMatcher();
		fm.setBoldweight(EnumUtil.toFontBoldweight(boldweight));
		fm.setColor(color.getHtmlColor());
		fm.setHeightPoints(fontHeightPoints);
		fm.setName(fontName);
		fm.setItalic(italic);
		fm.setStrikeout(strikeout);
		fm.setTypeOffset(EnumUtil.toFontTypeOffset(typeOffset));
		fm.setUnderline(EnumUtil.toFontUnderline(underline));
		
		SFont font = book.searchFont(fm);
		
		return font == null ? null : new FontImpl(((BookImpl) _book).getRef(),
				new SimpleRef<SFont>(font));
	}

	
	//TODO ZSS-424 get exception when undo after save
	@Override
	public boolean isAvailable(CellStyle style) {
		//always available in new 3.5 zss model.
		return true;
	}
}
