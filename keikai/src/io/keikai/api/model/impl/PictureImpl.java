/* PictureImpl.java

{{IS_NOTE
	Purpose:
		
	Description:
		
	History:
		2013/5/1 , Created by dennis
}}IS_NOTE

Copyright (C) 2013 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
*/
package io.keikai.api.model.impl;

import java.io.Serializable;

import io.keikai.api.SheetAnchor;
import io.keikai.api.model.Picture;
import io.keikai.model.SPicture;
import io.keikai.model.SSheet;
import io.keikai.model.ViewAnchor;

/**
 * 
 * @author dennis
 * @since 3.0.0
 */
public class PictureImpl implements Picture, Serializable {
	private static final long serialVersionUID = 7816263162242619441L;
	
	private ModelRef<SSheet> _sheetRef;
	private ModelRef<SPicture> _picRef;
	
	public PictureImpl(ModelRef<SSheet> sheetRef, ModelRef<SPicture> picRef) {
		this._sheetRef = sheetRef;
		this._picRef = picRef;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_picRef == null) ? 0 : _picRef.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PictureImpl other = (PictureImpl) obj;
		if (_picRef == null) {
			if (other._picRef != null)
				return false;
		} else if (!_picRef.equals(other._picRef))
			return false;
		return true;
	}
	
	public SPicture getNative() {
		return _picRef.get();
	}
	
	
	public String getId(){
		return getNative().getId();
	}

	@Override
	public SheetAnchor getAnchor() {
		ViewAnchor anchor = getNative().getAnchor();
		return anchor==null?null:SheetImpl.toSheetAnchor(_sheetRef.get(), anchor);
	}
}
