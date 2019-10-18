/* HyperlinkEvent.java

	Purpose:
		
	Description:
		
	History:
		Jul 19, 2010 2:08:02 PM, Created by henrichen

Copyright (C) 2010 Potix Corporation. All Rights Reserved.

*/

package io.keikai.ui.event;

import java.util.Map;

import io.keikai.api.model.Hyperlink;
import io.keikai.api.model.Sheet;
import io.keikai.api.model.impl.EnumUtil;
import io.keikai.api.model.impl.SheetImpl;
import io.keikai.ui.Spreadsheet;
import org.zkoss.zk.au.AuRequest;
import org.zkoss.zk.au.AuRequests;
import org.zkoss.zk.ui.Component;

/**
 * Event when end user click on the hyperlink of a cell (used with onCellHyperlink event).
 * @author henrichen
 */
public class CellHyperlinkEvent extends CellMouseEvent{
	private String _address;
	private Hyperlink.HyperlinkType _type;

	public static CellHyperlinkEvent getHyperlinkEvent(AuRequest request) {
		final Map data = request.getData();
		final Component comp = request.getComponent();
		String sheetId = (String) data.get("sheetId");
		Sheet sheet = ((Spreadsheet) comp).getSelectedSheet();
		if (!((SheetImpl)sheet).getNative().getId().equals(sheetId))
			return null;
		
		final String name = request.getCommand();
		final int keys = AuRequests.parseKeys(data);
		final int type = AuRequests.getInt(data, "type", 0, true);
		return new CellHyperlinkEvent(name, comp, sheet,
				AuRequests.getInt(data, "row", 0, true),
				AuRequests.getInt(data, "col", 0, true),
				(String) data.get("href"),
				EnumUtil.toHyperlinkType(type),
				AuRequests.getInt(data, "x", 0, true),
				AuRequests.getInt(data, "y", 0, true),
				AuRequests.getInt(data, "pageX", 0, true),
				AuRequests.getInt(data, "pageY", 0, true), keys);
	}

	public CellHyperlinkEvent(String name, Component target, Sheet sheet, int row ,int col, String address, Hyperlink.HyperlinkType type, int x, int y,
			int pageX, int pageY, int keys) {
		super(name, target, sheet, row, col, x, y, keys, pageX, pageY);
		this._address = address;
		this._type = type;
	}	

	/**
	 * LINK Reference.
	 * @return URI reference.
	 */
	public String getAddress() {
		return _address;
	}

	/**
	 * URL LINK type 
	 * @return link type
	 */
	public Hyperlink.HyperlinkType getType() {
		return _type;
	}
}
