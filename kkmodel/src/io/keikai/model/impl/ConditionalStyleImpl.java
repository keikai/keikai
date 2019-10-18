/* ConditionalStyle.java

	Purpose:
		
	Description:
		
	History:
		Jun 22, 2016 5:01:14 PM, Created by henrichen

	Copyright (C) 2016 Potix Corporation. All Rights Reserved.
*/

package io.keikai.model.impl;

import io.keikai.model.SBook;
import io.keikai.model.SBorder;
import io.keikai.model.SCellStyle;
import io.keikai.model.SColorScale;
import io.keikai.model.SConditionalStyle;
import io.keikai.model.SDataBar;
import io.keikai.model.SFill;
import io.keikai.model.SFont;
import io.keikai.model.SIconSet;

/**
 * Used to pass style and format for SConditionalFormattingRule.
 * @author Henri
 *
 */
public class ConditionalStyleImpl extends ExtraStyleImpl implements
		SConditionalStyle {
	private static final long serialVersionUID = -3335806807968034624L;
	
	private SColorScale colorScale;
	private SDataBar dataBar;
	private Double barPercent;
	private SIconSet iconSet;
	private Integer iconSetId;
	
	//ZSS-1183
	//@since 3.9.0
	/*package*/ ConditionalStyleImpl(ExtraStyleImpl src, BookImpl book) {
		super(src, book);
	}
	
	public ConditionalStyleImpl(SFont font, SFill fill, SBorder border, String dataFormat,
			SColorScale colorScale,  
			SDataBar dataBar, Double barPercent,
			SIconSet iconSet, Integer iconSetId) {
		super(font, fill, border, dataFormat);
		this.colorScale = colorScale;
		this.dataBar = dataBar;
		this.iconSet = iconSet;
		this.barPercent = barPercent;
		this.iconSetId = iconSetId;
	}
	
	public SColorScale getColorScale() {
		return colorScale;
	}

	public void setColorScale(SColorScale colorScale) {
		this.colorScale = colorScale;
	}

	public SDataBar getDataBar() {
		return dataBar;
	}

	public void setDataBar(SDataBar dataBar) {
		this.dataBar = dataBar;
	}

	public Double getBarPercent() {
		return barPercent;
	}

	public void setBarPercent(Double barPercent) {
		this.barPercent = barPercent;
	}

	public SIconSet getIconSet() {
		return iconSet;
	}

	public void setIconSet(SIconSet iconSet) {
		this.iconSet = iconSet;
	}

	public Integer getIconSetId() {
		return iconSetId;
	}

	public void setIconSetId(Integer iconSetId) {
		this.iconSetId = iconSetId;
	}

	//ZSS-1142
	//@since 3.9.0
	@Override
	/*package*/ SCellStyle createCellStyle(SBook book) {
		return new ConditionalStyleImpl(this, (BookImpl) book);
	}
}
