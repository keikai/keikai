/* CellValueHelper.java

	Purpose:
		
	Description:
		
	History:
		Jun 24, 2016 5:02:18 PM, Created by henrichen

	Copyright (C) 2016 Potix Corporation. All Rights Reserved.
*/

package io.keikai.range.impl;

import io.keikai.model.SCell;
import io.keikai.model.impl.CellImpl;
import io.keikai.model.impl.CellValue;
import io.keikai.model.sys.EngineFactory;
import io.keikai.model.sys.format.FormatContext;
import io.keikai.model.sys.format.FormatEngine;
import org.zkoss.poi.ss.usermodel.ZssContext;

/**
 * @author Henri
 *
 */
public class CellValueHelper {
	FormatEngine _formatEngine;
	
	public static CellValueHelper inst = new CellValueHelper();
	
	public String getFormattedText(SCell cell){
		return getFormatEngine().format(cell, new FormatContext(ZssContext.getCurrent().getLocale())).getText();
	}
	
	public CellValue getCellValue(SCell cell) {
		return ((CellImpl)cell).getEvalCellValue(true);
	}
	
	public Object getValue(SCell cell) {
		return cell.getValue();
	}
	
	protected FormatEngine getFormatEngine(){
		if(_formatEngine==null){
			_formatEngine = EngineFactory.getInstance().createFormatEngine();
		}
		return _formatEngine;
	}
}
