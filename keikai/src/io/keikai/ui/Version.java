/* Version.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		Dec 19, 2007 03:10:40 PM , Created by Dennis.Chen
}}IS_NOTE

Copyright (C) 2007 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
	This program is distributed under GPL Version 2.0 in the hope that
	it will be useful, but WITHOUT ANY WARRANTY.
}}IS_RIGHT
*/
package io.keikai.ui;

import org.zkoss.lang.Classes;

/**
 * Defines the version of ZK Spreadsheet.
 * It must be the same as the version defined in metainfo/zk/lang-addon.xml
 *
 * @author Dennis Chen
 */
public class Version {
	/** Returns the version UID.
	 */
	public static final String UID = "5.0.1-Beta";
	
	/** Features. */
	private static final boolean
		_ee = Classes.existsByThread("io.keikaiex.Version");
	
	public static String getEdition() {
		return _ee ? "EE": "OSE";
	}
	
}
