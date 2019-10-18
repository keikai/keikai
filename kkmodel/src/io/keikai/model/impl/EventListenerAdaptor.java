/*

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2013/12/01 , Created by dennis
}}IS_NOTE

Copyright (C) 2013 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package io.keikai.model.impl;

import io.keikai.model.ModelEvent;
import io.keikai.model.ModelEventListener;

/**
 * To adapt model event -> listener
 * @author dennis
 *
 */
public interface EventListenerAdaptor {

	public void addEventListener(ModelEventListener listener);
	
	public void removeEventListener(ModelEventListener listener);
	
	public int size();
	
	public void sendModelEvent(ModelEvent event);
	/**
	 * release the resource
	 */
	public void clear();
}
