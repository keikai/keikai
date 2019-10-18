/* MultipleStyleCellLoader.java

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
package io.keikai.ui.sys;

import io.keikai.model.SCellStyle;
import io.keikai.model.SConditionalStyle;
import io.keikai.model.SSheet;
import io.keikai.model.sys.format.FormatResult;

/**
 * @author dennis
 * @since 3.0.0
 */
public interface CellDisplayLoader {

	/**
	 * return the html text for this cell
	 * @return the html text or null if the cell is not support to show it.
	 */
	public String getCellHtmlText(SSheet sheet,int row, int column);
	
	//ZSS945, ZSS-1018
	/**
	 * Internal Use.
	 * @param sheet
	 * @param row
	 * @param column
	 * @return
	 * @since 3.8.0
	 * @deprecated
	 */
	public String getCellHtmlText(SSheet sheet,int row, int column, FormatResult ft, SCellStyle tbStyle);
	
	//ZSS-1142
	/**
	 * 
	 * @param sheet
	 * @param row
	 * @param column
	 * @param ft
	 * @param tbStyle
	 * @param cdStyle
	 * @since 3.9.0
	 * @return
	 */
	public String getCellHtmlText(SSheet sheet,int row, int column, FormatResult ft, SCellStyle tbStyle, SConditionalStyle cdStyle);
}
