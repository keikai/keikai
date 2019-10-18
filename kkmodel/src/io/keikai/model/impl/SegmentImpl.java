package io.keikai.model.impl;

import java.io.Serializable;

import io.keikai.model.SFont;
import io.keikai.model.SRichText;

class SegmentImpl implements SRichText.Segment, Serializable {
	private static final long serialVersionUID = 1L;
	private String _text;
	private SFont _font;

	SegmentImpl(String text, SFont font) {
		this._text = text;
		this._font = font;
	}

	@Override
	public String getText() {
		return _text;
	}

	@Override
	public SFont getFont() {
		return _font;
	}

	//ZSS-688
	//@since 3.6.0
	/*package*/ SegmentImpl cloneSegmentImpl() {
		return new SegmentImpl(this._text, this._font);
	}
}