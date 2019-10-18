package io.keikai.ui.event;

import io.keikai.api.model.Sheet;
import org.zkoss.zk.ui.Component;

/**
 * The event to notify a sheet's updated
 * @author dennis
 * @see Events#ON_SHEET_SELECT
 */
public class SheetSelectEvent extends SheetEvent{
	private static final long serialVersionUID = 1L;
	private Sheet _previousSheet;
	
	
	public SheetSelectEvent(String name, Component target, Sheet sheet, Sheet previousSheet) {
		super(name, target, sheet);
		_previousSheet = previousSheet;
	}

	public Sheet getPreviousSheet(){
		return _previousSheet;
	}
}
