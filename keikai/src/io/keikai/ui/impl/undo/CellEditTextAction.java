/* CellEditTextAction.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2013/7/25, Created by Dennis.Chen
}}IS_NOTE

Copyright (C) 2013 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under GPL Version 2.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
*/
package io.keikai.ui.impl.undo;

import io.keikai.api.CellOperationUtil;
import io.keikai.api.IllegalFormulaException;
import io.keikai.api.Range;
import io.keikai.api.Ranges;
import io.keikai.api.impl.RangeImpl;
import io.keikai.api.model.Sheet;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;

/**
 * 
 * @author dennis
 *
 */
public class CellEditTextAction extends AbstractEditTextAction {
	private static final long serialVersionUID = -4091069271521601441L;
	
	private final String _editText;
	private final String[][] _editTexts;
	
	public CellEditTextAction(String label, Sheet sheet,int row, int column, int lastRow,int lastColumn,String editText){
		super(label,sheet,row,column,lastRow,lastColumn);
		this._editText = editText;
		_editTexts = null;
	}
	public CellEditTextAction(String label,Sheet sheet,int row, int column, int lastRow,int lastColumn,String[][] editTexts){
		super(label,sheet,row,column,lastRow,lastColumn);
		this._editTexts = editTexts;
		_editText = null;
	}

	//ZSS-1046
	private static final String ALREADY_PARSE_ERROR = "KEIKAI_ALREADY_PARSE_ERROR";
	protected void applyAction(){
		boolean protect = isSheetProtected();
		if(_editText!=null && !protect){
			Range r = Ranges.range(_sheet,_row,_column,_lastRow,_lastColumn);
			try {
				r.setCellEditText(_editText);
			}catch(IllegalFormulaException x){
				//ZSS-1046: In excel, it it saved as a String if already parse error in previous pasted cell in a paste
				final Execution exec = Executions.getCurrent();
				if (exec != null) {
					if (exec.getAttribute(ALREADY_PARSE_ERROR, false) != null) {
						((RangeImpl)r).setStringValue(_editText);
						return;
					} else {
						exec.setAttribute(ALREADY_PARSE_ERROR, Boolean.TRUE, false);
					}
				}
				throw x;
			}
			
			if(_editText.contains("\n"))
				CellOperationUtil.applyWrapText(r, true);
		}else{
			for(int i=_row;i<=_lastRow;i++){
				for(int j=_column;j<=_lastColumn;j++){
					Range r = Ranges.range(_sheet,i,j);
					boolean lock = r.getCellStyle().isLocked();
					if(protect && lock)
						continue;
					
					try{
						r.setCellEditText(_editText!=null?_editText:_editTexts[i][j]);
					}catch(IllegalFormulaException x){};//eat in this mode
				}
			}
		}
	}
}
