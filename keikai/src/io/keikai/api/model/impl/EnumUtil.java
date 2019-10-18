/* EnumUtil.java

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

import io.keikai.api.Range;
import io.keikai.api.model.CellStyle;
import io.keikai.api.model.Chart;
import io.keikai.api.model.Font;
import io.keikai.api.model.Picture;
import io.keikai.model.SAutoFilter;
import io.keikai.model.SBorder;
import io.keikai.model.SCellStyle;
import io.keikai.model.SChart;
import io.keikai.model.SFill;
import io.keikai.model.SFont;
import io.keikai.model.SHyperlink;
import io.keikai.model.SPicture;
import io.keikai.range.SRange;
import org.zkoss.poi.common.usermodel.Hyperlink;

/**
 * 
 * @author dennis
 * @since 3.0.0
 */
public class EnumUtil {
	
	private static <T> void assertArgNotNull(T obj,String name){
		if(obj == null){
			throw new IllegalArgumentException("argument "+name==null?"":name+" is null");
		}
	}
	
	public static SRange.PasteOperation toRangePasteOpNative(
			Range.PasteOperation op) {
		assertArgNotNull(op,"paste operation");
		switch(op){
		case ADD:
			return SRange.PasteOperation.ADD;
		case SUB:
			return SRange.PasteOperation.SUB;
		case MUL:
			return SRange.PasteOperation.MUL;
		case DIV:
			return SRange.PasteOperation.DIV;
		case NONE:
			return SRange.PasteOperation.NONE;
		}
		throw new IllegalArgumentException("unknow paste operation "+op);
	}


	public static SRange.PasteType toRangePasteTypeNative(Range.PasteType type) {
		assertArgNotNull(type,"paste type");
		switch(type){
		case ALL:
			return SRange.PasteType.ALL;
		case ALL_EXCEPT_BORDERS:
			return SRange.PasteType.ALL_EXCEPT_BORDERS;
		case COLUMN_WIDTHS:
			return SRange.PasteType.COLUMN_WIDTHS;
		case COMMENTS:
			return SRange.PasteType.COMMENTS;
		case FORMATS:
			return SRange.PasteType.FORMATS;
		case FORMULAS:
			return SRange.PasteType.FORMULAS;
		case FORMULAS_AND_NUMBER_FORMATS:
			return SRange.PasteType.FORMULAS_AND_NUMBER_FORMATS;
		case VALIDATAION:
			return SRange.PasteType.VALIDATAION;
		case VALUES:
			return SRange.PasteType.VALUES;
		case VALUES_AND_NUMBER_FORMATS:
			return SRange.PasteType.VALUES_AND_NUMBER_FORMATS;
		}
		throw new IllegalArgumentException("unknow paste operation "+type);
	}
	
	public static Font.TypeOffset toFontTypeOffset(SFont.TypeOffset typeOffset){
		switch(typeOffset){
		case NONE:
			return Font.TypeOffset.NONE;
		case SUB:
			return Font.TypeOffset.SUB;
		case SUPER:
			return Font.TypeOffset.SUPER;
		}
		throw new IllegalArgumentException("unknow font type offset "+typeOffset);
	}

	public static SFont.TypeOffset toFontTypeOffset(Font.TypeOffset typeOffset) {
		assertArgNotNull(typeOffset,"typeOffset");
		switch(typeOffset){
		case NONE:
			return SFont.TypeOffset.NONE;
		case SUB:
			return SFont.TypeOffset.SUB;
		case SUPER:
			return SFont.TypeOffset.SUPER;
		}
		throw new IllegalArgumentException("unknow font type offset "+typeOffset);
	}

	public static Font.Underline toFontUnderline(SFont.Underline underline) {
		switch(underline){
		case NONE:
			return Font.Underline.NONE;
		case SINGLE:
			return Font.Underline.SINGLE;
		case SINGLE_ACCOUNTING:
			return Font.Underline.SINGLE_ACCOUNTING;
		case DOUBLE:
			return Font.Underline.DOUBLE;
		case DOUBLE_ACCOUNTING:
			return Font.Underline.DOUBLE_ACCOUNTING;
		}
		throw new IllegalArgumentException("unknow font underline "+underline);
	}


	public static SFont.Underline toFontUnderline(Font.Underline underline) {
		assertArgNotNull(underline,"underline");
		switch(underline){
		case NONE:
			return SFont.Underline.NONE;
		case SINGLE:
			return SFont.Underline.SINGLE;
		case SINGLE_ACCOUNTING:
			return SFont.Underline.SINGLE_ACCOUNTING;
		case DOUBLE:
			return SFont.Underline.DOUBLE;
		case DOUBLE_ACCOUNTING:
			return SFont.Underline.DOUBLE_ACCOUNTING;
		}
		throw new IllegalArgumentException("unknow font underline "+underline);
	}

	public static Font.Boldweight toFontBoldweight(SFont.Boldweight boldweight) {
		switch(boldweight){
		case BOLD:
			return Font.Boldweight.BOLD;
		case NORMAL:
			return Font.Boldweight.NORMAL;
		}
		throw new IllegalArgumentException("unknow font boldweight "+boldweight);
	}
	
	public static SFont.Boldweight toFontBoldweight(Font.Boldweight boldweight) {
		switch(boldweight){
		case BOLD:
			return SFont.Boldweight.BOLD;
		case NORMAL:
			return SFont.Boldweight.NORMAL;
		}
		throw new IllegalArgumentException("unknow font boldweight "+boldweight);
	}

	public static CellStyle.FillPattern toStyleFillPattern(
			SFill.FillPattern pattern) {
		switch(pattern){
		case NONE:
			return CellStyle.FillPattern.NONE;
		case SOLID:
			return CellStyle.FillPattern.SOLID;
		case MEDIUM_GRAY:
			return CellStyle.FillPattern.MEDIUM_GRAY;
		case DARK_GRAY:
			return CellStyle.FillPattern.DARK_GRAY;
		case LIGHT_GRAY:
			return CellStyle.FillPattern.LIGHT_GRAY;
		case DARK_HORIZONTAL:
			return CellStyle.FillPattern.DARK_HORIZONTAL;
		case DARK_VERTICAL:
			return CellStyle.FillPattern.DARK_VERTICAL;
		case DARK_DOWN:
			return CellStyle.FillPattern.DARK_DOWN;
		case DARK_UP:
			return CellStyle.FillPattern.DARK_UP;
		case DARK_GRID:
			return CellStyle.FillPattern.DARK_GRID;
		case DARK_TRELLIS:
			return CellStyle.FillPattern.DARK_TRELLIS;
		case LIGHT_HORIZONTAL:
			return CellStyle.FillPattern.LIGHT_HORIZONTAL;
		case LIGHT_VERTICAL:
			return CellStyle.FillPattern.LIGHT_VERTICAL;
		case LIGHT_DOWN:
			return CellStyle.FillPattern.LIGHT_DOWN;
		case LIGHT_UP:
			return CellStyle.FillPattern.LIGHT_UP;
		case LIGHT_GRID:
			return CellStyle.FillPattern.LIGHT_GRID;
		case LIGHT_TRELLIS:
			return CellStyle.FillPattern.LIGHT_TRELLIS;
		case GRAY125:
			return CellStyle.FillPattern.GRAY125;
		case GRAY0625:
			return CellStyle.FillPattern.GRAY0625;
		}
		throw new IllegalArgumentException("unknow pattern type "+pattern);	
	}
	
	public static SFill.FillPattern toStyleFillPattern(
			CellStyle.FillPattern pattern) {
		switch(pattern){
		case NONE:
			return SFill.FillPattern.NONE;
		case SOLID:
			return SFill.FillPattern.SOLID;
		case MEDIUM_GRAY:
			return SFill.FillPattern.MEDIUM_GRAY;
		case DARK_GRAY:
			return SFill.FillPattern.DARK_GRAY;
		case LIGHT_GRAY:
			return SFill.FillPattern.LIGHT_GRAY;
		case DARK_HORIZONTAL:
			return SFill.FillPattern.DARK_HORIZONTAL;
		case DARK_VERTICAL:
			return SFill.FillPattern.DARK_VERTICAL;
		case DARK_DOWN:
			return SFill.FillPattern.DARK_DOWN;
		case DARK_UP:
			return SFill.FillPattern.DARK_UP;
		case DARK_GRID:
			return SFill.FillPattern.DARK_GRID;
		case DARK_TRELLIS:
			return SFill.FillPattern.DARK_TRELLIS;
		case LIGHT_HORIZONTAL:
			return SFill.FillPattern.LIGHT_HORIZONTAL;
		case LIGHT_VERTICAL:
			return SFill.FillPattern.LIGHT_VERTICAL;
		case LIGHT_DOWN:
			return SFill.FillPattern.LIGHT_DOWN;
		case LIGHT_UP:
			return SFill.FillPattern.LIGHT_UP;
		case LIGHT_GRID:
			return SFill.FillPattern.LIGHT_GRID;
		case LIGHT_TRELLIS:
			return SFill.FillPattern.LIGHT_TRELLIS;
		case GRAY125:
			return SFill.FillPattern.GRAY125;
		case GRAY0625:
			return SFill.FillPattern.GRAY0625;
		}
		throw new IllegalArgumentException("unknow pattern type "+pattern);
	}

	public static SCellStyle.Alignment toStyleAlignemnt(
			CellStyle.Alignment alignment) {
		switch(alignment){
		case GENERAL:
			return SCellStyle.Alignment.GENERAL;
		case LEFT:
			return SCellStyle.Alignment.LEFT;
		case CENTER:
			return SCellStyle.Alignment.CENTER;
		case RIGHT:
			return SCellStyle.Alignment.RIGHT;
		case FILL:
			return SCellStyle.Alignment.FILL;
		case JUSTIFY:
			return SCellStyle.Alignment.JUSTIFY;
		case CENTER_SELECTION:
			return SCellStyle.Alignment.CENTER_SELECTION;
		}
		throw new IllegalArgumentException("unknow cell alignment "+alignment);
	}
	public static CellStyle.Alignment toStyleAlignemnt(SCellStyle.Alignment alignment) {
		switch(alignment){
		case GENERAL:
			return CellStyle.Alignment.GENERAL;
		case LEFT:
			return CellStyle.Alignment.LEFT;
		case CENTER:
			return CellStyle.Alignment.CENTER;
		case RIGHT:
			return CellStyle.Alignment.RIGHT;
		case FILL:
			return CellStyle.Alignment.FILL;
		case JUSTIFY:
			return CellStyle.Alignment.JUSTIFY;
		case CENTER_SELECTION:
			return CellStyle.Alignment.CENTER_SELECTION;
		}
		throw new IllegalArgumentException("unknow cell alignment "+alignment);
	}
	public static SCellStyle.VerticalAlignment toStyleVerticalAlignemnt(
			CellStyle.VerticalAlignment alignment) {
		switch(alignment){
		case TOP:
			return SCellStyle.VerticalAlignment.TOP;
		case CENTER:
			return SCellStyle.VerticalAlignment.CENTER;
		case BOTTOM:
			return SCellStyle.VerticalAlignment.BOTTOM;
		case JUSTIFY:
			return SCellStyle.VerticalAlignment.JUSTIFY;
		}
		throw new IllegalArgumentException("unknow cell vertical alignment "+alignment);
	}
	public static CellStyle.VerticalAlignment toStyleVerticalAlignemnt(SCellStyle.VerticalAlignment alignment) {
		switch(alignment){
		case TOP:
			return CellStyle.VerticalAlignment.TOP;
		case CENTER:
			return CellStyle.VerticalAlignment.CENTER;
		case BOTTOM:
			return CellStyle.VerticalAlignment.BOTTOM;
		case JUSTIFY:
			return CellStyle.VerticalAlignment.JUSTIFY;
		}
		throw new IllegalArgumentException("unknow cell vertical alignment "+alignment);
	}

	public static SRange.ApplyBorderType toRangeApplyBorderType(
			Range.ApplyBorderType type) {
		switch(type){
		case FULL:
			return SRange.ApplyBorderType.FULL;
		case EDGE_BOTTOM:
			return SRange.ApplyBorderType.EDGE_BOTTOM;
		case EDGE_RIGHT:
			return SRange.ApplyBorderType.EDGE_RIGHT;
		case EDGE_TOP:
			return SRange.ApplyBorderType.EDGE_TOP;
		case EDGE_LEFT:
			return SRange.ApplyBorderType.EDGE_LEFT;
		case OUTLINE:
			return SRange.ApplyBorderType.OUTLINE;
		case INSIDE:
			return SRange.ApplyBorderType.INSIDE;
		case INSIDE_HORIZONTAL:
			return SRange.ApplyBorderType.INSIDE_HORIZONTAL;
		case INSIDE_VERTICAL:
			return SRange.ApplyBorderType.INSIDE_VERTICAL;
		case DIAGONAL:
			return SRange.ApplyBorderType.DIAGONAL;
		case DIAGONAL_DOWN:
			return SRange.ApplyBorderType.DIAGONAL_DOWN;
		case DIAGONAL_UP:
			return SRange.ApplyBorderType.DIAGONAL_UP;
		}
		throw new IllegalArgumentException("unknow cell border apply type "+type);
	}

	public static SBorder.BorderType toStyleBorderType(
			CellStyle.BorderType borderType) {
		switch(borderType){
		case NONE:
			return SBorder.BorderType.NONE;
		case THIN:
			return SBorder.BorderType.THIN;
		case MEDIUM:
			return SBorder.BorderType.MEDIUM;
		case DASHED:
			return SBorder.BorderType.DASHED;
		case HAIR:
			return SBorder.BorderType.HAIR;
		case THICK:
			return SBorder.BorderType.THICK;
		case DOUBLE:
			return SBorder.BorderType.DOUBLE;
		case DOTTED:
			return SBorder.BorderType.DOTTED;
		case MEDIUM_DASHED:
			return SBorder.BorderType.MEDIUM_DASHED;
		case DASH_DOT:
			return SBorder.BorderType.DASH_DOT;
		case MEDIUM_DASH_DOT:
			return SBorder.BorderType.MEDIUM_DASH_DOT;
		case DASH_DOT_DOT:
			return SBorder.BorderType.DASH_DOT_DOT;
		case MEDIUM_DASH_DOT_DOT:
			return SBorder.BorderType.MEDIUM_DASH_DOT_DOT;
		case SLANTED_DASH_DOT:
			return SBorder.BorderType.SLANTED_DASH_DOT;
		}
		throw new IllegalArgumentException("unknow style border type "+borderType);
	}
	
	public static CellStyle.BorderType toStyleBorderType(SBorder.BorderType borderType) {
		switch(borderType){
		case NONE:
			return CellStyle.BorderType.NONE;
		case THIN:
			return CellStyle.BorderType.THIN;
		case MEDIUM:
			return CellStyle.BorderType.MEDIUM;
		case DASHED:
			return CellStyle.BorderType.DASHED;
		case HAIR:
			return CellStyle.BorderType.HAIR;
		case THICK:
			return CellStyle.BorderType.THICK;
		case DOUBLE:
			return CellStyle.BorderType.DOUBLE;
		case DOTTED:
			return CellStyle.BorderType.DOTTED;
		case MEDIUM_DASHED:
			return CellStyle.BorderType.MEDIUM_DASHED;
		case DASH_DOT:
			return CellStyle.BorderType.DASH_DOT;
		case MEDIUM_DASH_DOT:
			return CellStyle.BorderType.MEDIUM_DASH_DOT;
		case DASH_DOT_DOT:
			return CellStyle.BorderType.DASH_DOT_DOT;
		case MEDIUM_DASH_DOT_DOT:
			return CellStyle.BorderType.MEDIUM_DASH_DOT_DOT;
		case SLANTED_DASH_DOT:
			return CellStyle.BorderType.SLANTED_DASH_DOT;
		}
		throw new IllegalArgumentException("unknow style border type "+borderType);
	}
	
	public static SBorder.BorderType toRangeBorderType(
			CellStyle.BorderType lineStyle) {
		switch(lineStyle){
		case NONE:
			return SBorder.BorderType.NONE;
		case THIN:
			return SBorder.BorderType.THIN;
		case MEDIUM:
			return SBorder.BorderType.MEDIUM;
		case DASHED:
			return SBorder.BorderType.DASHED;
		case HAIR:
			return SBorder.BorderType.HAIR;
		case THICK:
			return SBorder.BorderType.THICK;
		case DOUBLE:
			return SBorder.BorderType.DOUBLE;
		case DOTTED:
			return SBorder.BorderType.DOTTED;
		case MEDIUM_DASHED:
			return SBorder.BorderType.MEDIUM_DASHED;
		case DASH_DOT:
			return SBorder.BorderType.DASH_DOT;
		case MEDIUM_DASH_DOT:
			return SBorder.BorderType.MEDIUM_DASH_DOT;
		case DASH_DOT_DOT:
			return SBorder.BorderType.DASH_DOT_DOT;
		case MEDIUM_DASH_DOT_DOT:
			return SBorder.BorderType.MEDIUM_DASH_DOT_DOT;
		case SLANTED_DASH_DOT:
			return SBorder.BorderType.SLANTED_DASH_DOT;
		}
		throw new IllegalArgumentException("unknow cell border line style "+lineStyle);
	}

	public static SRange.InsertShift toRangeInsertShift(Range.InsertShift shift) {
		switch(shift){
		case DEFAULT:
			return SRange.InsertShift.DEFAULT;
		case DOWN:
			return SRange.InsertShift.DOWN;
		case RIGHT:
			return SRange.InsertShift.RIGHT;
		}
		throw new IllegalArgumentException("unknow range insert shift "+shift);
	}

	public static SRange.InsertCopyOrigin toRangeInsertCopyOrigin(
			Range.InsertCopyOrigin copyOrigin) {
		switch(copyOrigin){
		case FORMAT_NONE:
			return SRange.InsertCopyOrigin.FORMAT_NONE;
		case FORMAT_LEFT_ABOVE:
			return SRange.InsertCopyOrigin.FORMAT_LEFT_ABOVE;
		case FORMAT_RIGHT_BELOW:
			return SRange.InsertCopyOrigin.FORMAT_RIGHT_BELOW;
		}
		throw new IllegalArgumentException("unknow range insert copy origin "+copyOrigin);
	}
	
	public static SRange.DeleteShift toRangeDeleteShift(Range.DeleteShift shift) {
		switch(shift){
		case DEFAULT:
			return SRange.DeleteShift.DEFAULT;
		case UP:
			return SRange.DeleteShift.UP;
		case LEFT:
			return SRange.DeleteShift.LEFT;
		}
		throw new IllegalArgumentException("unknow range delete shift "+shift);
	}

	public static SRange.SortDataOption toRangeSortDataOption(
			Range.SortDataOption dataOption) {
		switch(dataOption){
		case TEXT_AS_NUMBERS:
			return SRange.SortDataOption.TEXT_AS_NUMBERS;
		case NORMAL_DEFAULT:
			return SRange.SortDataOption.NORMAL_DEFAULT;
		}
		throw new IllegalArgumentException("unknow sort data option "+dataOption);
	}

	public static SAutoFilter.FilterOp toRangeAutoFilterOperation(
			Range.AutoFilterOperation filterOp) {
		switch(filterOp){
		case AND:
			return SAutoFilter.FilterOp.and;
		case OR:
			return SAutoFilter.FilterOp.or;
			
			//ZSS-1234
		case TOP10:
		case TOP10PERCENT:
		case BOTTOM10:
		case BOTTOM10PERCENT:
			return SAutoFilter.FilterOp.top10;
		case VALUES:
			return SAutoFilter.FilterOp.values;
		}
		throw new IllegalArgumentException("unknow autofilter operation "+filterOp);
	}

	public static SRange.FillType toRangeFillType(Range.AutoFillType fillType) {
		switch(fillType){
		case COPY:
			return SRange.FillType.COPY;
		case DAYS:
			return SRange.FillType.DAYS;
		case DEFAULT:
			return SRange.FillType.DEFAULT;
		case FORMATS:
			return SRange.FillType.FORMATS;
		case MONTHS:
			return SRange.FillType.MONTHS;
		case SERIES:
			return SRange.FillType.SERIES;
		case VALUES:
			return SRange.FillType.VALUES;
		case WEEKDAYS:
			return SRange.FillType.WEEKDAYS;
		case YEARS:
			return SRange.FillType.YEARS;
		case GROWTH_TREND:
			return SRange.FillType.GROWTH_TREND;
		case LINER_TREND:
			return SRange.FillType.LINER_TREND;
		}
		throw new IllegalArgumentException("unknow fill type "+fillType);
	}

	public static SHyperlink.HyperlinkType toHyperlinkType(
			io.keikai.api.model.Hyperlink.HyperlinkType type) {
		switch(type){
		case URL:
			return SHyperlink.HyperlinkType.URL;
		case DOCUMENT:
			return SHyperlink.HyperlinkType.DOCUMENT;
		case EMAIL:
			return SHyperlink.HyperlinkType.EMAIL;
		case FILE:
			return SHyperlink.HyperlinkType.FILE;
		}
		throw new IllegalArgumentException("unknow hyperlink type "+type);
	}
	public static io.keikai.api.model.Hyperlink.HyperlinkType toHyperlinkType(int type) {
		switch(type){
		case Hyperlink.LINK_URL:
			return io.keikai.api.model.Hyperlink.HyperlinkType.URL;
		case Hyperlink.LINK_DOCUMENT:
			return io.keikai.api.model.Hyperlink.HyperlinkType.DOCUMENT;
		case Hyperlink.LINK_EMAIL:
			return io.keikai.api.model.Hyperlink.HyperlinkType.EMAIL;
		case Hyperlink.LINK_FILE:
			return io.keikai.api.model.Hyperlink.HyperlinkType.FILE;
		}
		throw new IllegalArgumentException("unknow hyperlink type "+type);
	}
	public static io.keikai.api.model.Hyperlink.HyperlinkType toHyperlinkType(SHyperlink.HyperlinkType type) {
		switch(type){
		case URL:
			return io.keikai.api.model.Hyperlink.HyperlinkType.URL;
		case DOCUMENT:
			return io.keikai.api.model.Hyperlink.HyperlinkType.DOCUMENT;
		case EMAIL:
			return io.keikai.api.model.Hyperlink.HyperlinkType.EMAIL;
		case FILE:
			return io.keikai.api.model.Hyperlink.HyperlinkType.FILE;
		}
		throw new IllegalArgumentException("unknow hyperlink type "+type);
	}

	public static SPicture.Format toPictureFormat(Picture.Format format) {
		switch(format){
		case EMF:
			return SPicture.Format.EMF;
		case WMF:
			return SPicture.Format.WMF;
		case PICT:
			return SPicture.Format.PICT;
		case JPEG:
			return SPicture.Format.JPG;
		case PNG:
			return SPicture.Format.PNG;
		case DIB:
			return SPicture.Format.DIB;
		}
		throw new IllegalArgumentException("unknow pciture format "+format);
	}

	public static SChart.ChartType toChartType(Chart.Type type) {
		switch(type){
		case AREA_3D:
		case AREA:
			return SChart.ChartType.AREA;
		case BAR_3D:
		case BAR:
			return SChart.ChartType.BAR;
		case BUBBLE:
			return SChart.ChartType.BUBBLE;
		case COLUMN:
		case COLUMN_3D:
			return SChart.ChartType.COLUMN;
		case DOUGHNUT:
			return SChart.ChartType.DOUGHNUT;
		case LINE_3D:
		case LINE:
			return SChart.ChartType.LINE;
		case OF_PIE:
			return SChart.ChartType.OF_PIE;
		case PIE_3D:
		case PIE:
			return SChart.ChartType.PIE;
		case RADAR:
			return SChart.ChartType.RADAR;
		case SCATTER:
			return SChart.ChartType.SCATTER;
		case STOCK:
			return SChart.ChartType.STOCK;
		case SURFACE_3D:
		case SURFACE:
			return SChart.ChartType.SURFACE;
		}
		throw new IllegalArgumentException("unknow chart type "+type);
	}
	
	public static boolean isThreeDimentionalChart(Chart.Type type) {
		switch(type){
		case AREA_3D:
		case BAR_3D:
		case COLUMN_3D:
		case LINE_3D:
		case PIE_3D:
		case SURFACE_3D:
			return true;
		default:
			return false;
		}
	}

	public static SChart.ChartGrouping toChartGrouping(Chart.Grouping grouping) {
		switch(grouping){
		case STANDARD:
			return SChart.ChartGrouping.STANDARD;
		case STACKED:
			return SChart.ChartGrouping.STACKED;
		case PERCENT_STACKED:
			return SChart.ChartGrouping.PERCENT_STACKED;
		case CLUSTERED:
			return SChart.ChartGrouping.CLUSTERED;//bar only
		}
		throw new IllegalArgumentException("unknow grouping "+grouping);
	}

	public static SChart.ChartLegendPosition toLegendPosition(
			Chart.LegendPosition pos) {
		switch(pos){
		case BOTTOM:
			return SChart.ChartLegendPosition.BOTTOM;
		case LEFT:
			return SChart.ChartLegendPosition.LEFT;
		case RIGHT:
			return SChart.ChartLegendPosition.RIGHT;
		case TOP:
			return SChart.ChartLegendPosition.TOP;
		case TOP_RIGHT:
			return SChart.ChartLegendPosition.TOP_RIGHT;
		}
		throw new IllegalArgumentException("unknow legend position "+pos);
	}
}
