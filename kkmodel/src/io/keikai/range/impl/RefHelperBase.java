package io.keikai.range.impl;

import java.io.Serializable;

import io.keikai.model.SBookSeries;

/**
 * @author dennis
 * @since 3.5.0
 */
/*package*/ class RefHelperBase implements Serializable {
	private static final long serialVersionUID = -1735437734160582012L;
	
	final protected SBookSeries bookSeries;
	public RefHelperBase(SBookSeries bookSeries) {
		this.bookSeries = bookSeries;
	}
}
