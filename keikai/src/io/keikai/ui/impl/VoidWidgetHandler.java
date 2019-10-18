package io.keikai.ui.impl;

import java.io.Serializable;

import io.keikai.model.SSheet;
import io.keikai.ui.Spreadsheet;
import io.keikai.ui.Widget;
import io.keikai.ui.sys.WidgetHandler;

/**
 * Default widget implementation, don't provide any function.
 */
public class VoidWidgetHandler implements WidgetHandler, Serializable {
	private static final long serialVersionUID = -1715862041912291146L;
	
	Spreadsheet spreadsheet;

	public VoidWidgetHandler() {
	}

	public boolean addWidget(Widget widget) {
		return false;
	}

	public Spreadsheet getSpreadsheet() {
		return spreadsheet;
	};

	public void invaliate() {
	}

	public void onLoadOnDemand(SSheet sheet, int left, int top, int right,
			int bottom) {
	}

	public boolean removeWidget(Widget widget) {
		return false;
	}

	public void init(Spreadsheet spreadsheet) {
		this.spreadsheet = spreadsheet;
	}

	public void updateWidgets(SSheet sheet, int left, int top, int right,
			int bottom) {
	}

	public void updateWidget(SSheet sheet, String widgetId) {
	}
}