/* SimpleCellDisplayLoader.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2013/8/7 , Created by dennis
}}IS_NOTE

Copyright (C) 2013 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package io.keikai.ui.impl;

import io.keikai.model.SCellStyle;
import io.keikai.model.SConditionalStyle;
import io.keikai.model.SSheet;
import io.keikai.model.sys.format.FormatResult;
import io.keikai.ui.sys.CellDisplayLoader;

/**
 * @author dennis
 * @since 3.0.0
 */
public class SimpleCellDisplayLoader implements CellDisplayLoader {

	/* (non-Javadoc)
	 * @see io.keikai.ui.sys.RichCellContentLoader#getCellHtmlText(io.keikai.model.sys.XSheet, int, int)
	 */
	@Override
	public String getCellHtmlText(SSheet sheet, int row, int column) {
		return CellFormatHelper.getCellHtmlText(sheet, row, column);
	}

	//ZSS-945, ZSS-1018
	@Override
	@Deprecated
	public String getCellHtmlText(SSheet sheet, int row, int column, FormatResult ft, SCellStyle tbStyle) {
		return getCellHtmlText(sheet, row, column, ft, tbStyle, null);
	}
	//ZSS-1142
	@Override
	public String getCellHtmlText(SSheet sheet, int row, int column, FormatResult ft, SCellStyle tbStyle, SConditionalStyle cdStyle) {
		return CellFormatHelper.getCellHtmlText(sheet, row, column, ft, tbStyle, cdStyle);
	}
}
