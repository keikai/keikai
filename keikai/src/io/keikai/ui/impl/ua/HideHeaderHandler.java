package io.keikai.ui.impl.ua;

import io.keikai.api.AreaRef;
import io.keikai.api.IllegalOpArgumentException;
import io.keikai.api.Range;
import io.keikai.api.Ranges;
import io.keikai.api.model.Book;
import io.keikai.api.model.Sheet;
import io.keikai.api.model.SheetProtection;
import io.keikai.ui.UserActionContext;
import io.keikai.ui.sys.UndoableActionManager;
import org.zkoss.util.resource.Labels;
import io.keikai.ui.impl.undo.HideHeaderAction;
import io.keikai.ui.impl.undo.HideHeaderAction.Type;

public class HideHeaderHandler extends AbstractHandler {
	private static final long serialVersionUID = 9120677511231533029L;
	final HideHeaderAction.Type _type;
	final boolean _hide;
	
	public HideHeaderHandler(Type type, boolean hide) {
		this._type = type;
		this._hide = hide;
	}



	@Override
	protected boolean processAction(UserActionContext ctx) {
		Sheet sheet = ctx.getSheet();
		AreaRef selection = ctx.getSelection();
		Range range = Ranges.range(sheet, selection);
		//ZSS-576
		if(range.isProtected()) {
			switch(_type) {
			case COLUMN:
				if (!range.getSheetProtection().isFormatColumnsAllowed()) {
					showProtectMessage();
					return true;
				}
				break;
			case ROW:
				if (!range.getSheetProtection().isFormatRowsAllowed()) {
					showProtectMessage();
					return true;
				}
			}
		}
		
		//ZSS-504, to prevent user's operation 
		if(_hide && _type == HideHeaderAction.Type.ROW && checkSelectAllVisibleRow(ctx)){
			throw new IllegalOpArgumentException(Labels.getLabel("zss.msg.operation_not_supported_with_all_row"));
		}
		if(_hide && _type == HideHeaderAction.Type.COLUMN && checkSelectAllVisibleColumn(ctx)){
			throw new IllegalOpArgumentException(Labels.getLabel("zss.msg.operation_not_supported_with_all_column"));
		}
		
		
		String label = null;
		switch(_type){
		case COLUMN:
			label = _hide?Labels.getLabel("zss.undo.hideColumn"):Labels.getLabel("zss.undo.unhideColumn");
			break;
		case ROW:
			label = _hide?Labels.getLabel("zss.undo.hideRow"):Labels.getLabel("zss.undo.unhideRow");
			break;
		}
		
		UndoableActionManager uam = ctx.getSpreadsheet().getUndoableActionManager();
		uam.doAction(new HideHeaderAction(label,sheet, selection.getRow(), selection.getColumn(), 
			selection.getLastRow(), selection.getLastColumn(), 
			_type,_hide));
		
		return true;
	}

	@Override
	public boolean isEnabled(Book book, Sheet sheet) {
		if (book == null || sheet == null) {
			return false;
		}
		if (!sheet.isProtected()) {
			return true;
		}
		final SheetProtection sheetProtection = Ranges.range(sheet).getSheetProtection();
		boolean allowed = false;
		switch(_type){
		case COLUMN:
			allowed = sheetProtection.isFormatColumnsAllowed();
			break;
		case ROW:
			allowed = sheetProtection.isFormatRowsAllowed();
			break;
		}
		return allowed;
	}
}
