/* SpreadsheetWebAppInit.java

	Purpose:
		
	Description:
		
	History:
		Thu, Jun 26, 2014  4:54:52 PM, Created by RaymondChao

Copyright (C) 2014 Potix Corporation. All Rights Reserved.

*/
package io.keikai.ui;

import io.keikai.theme.SpreadsheetThemeFns;
import io.keikai.theme.SpreadsheetThemeProvider;
import io.keikai.theme.SpreadsheetThemeRegistry;
import io.keikai.theme.SpreadsheetThemeResolver;
import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.util.Configuration;

/**
 * 
 * @author RaymondChao
 * @since 3.5.0
 */
public class WebAppInit implements org.zkoss.zk.ui.util.WebAppInit {


	public void init(WebApp wapp) throws Exception {
		SpreadsheetThemeFns.setThemeResolver(new SpreadsheetThemeResolver());
		SpreadsheetThemeFns.setThemeRegistry(new SpreadsheetThemeRegistry());
		setSpreadsheetThemeProvider(wapp.getConfiguration());
	}
	
	private void setSpreadsheetThemeProvider(Configuration configuration) {
		configuration.setThemeProvider(new SpreadsheetThemeProvider(configuration.getThemeProvider()));
	}

}