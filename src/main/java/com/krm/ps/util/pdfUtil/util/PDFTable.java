package com.krm.ps.util.pdfUtil.util;

public class PDFTable {
	private int BeginRow;	//
	private int EndRow;
	
	private int DefaultColumnWidth;	//默认列宽
	private int DefaultRowHeight;	//默认行高
	private int IsPrintBeginRow;	//打印起始页
	private int dmOrientation;	//打印方向
	
	private int FixedColumnCount;	//跳过列数
	private int FixedRowCount;	//跳过行数
	private int ExpandedColumnCount;	//总列数
	private int ExpandedRowCount;	//总行数
	
	private int[] widths;
	
	public int[] getWidths() {
		return widths;
	}
	public void setWidths(int[] widths) {
		this.widths = widths;
	}
	public int getBeginRow() {
		return BeginRow;
	}
	public void setBeginRow(int beginRow) {
		BeginRow = beginRow;
	}
	public int getDefaultColumnWidth() {
		return DefaultColumnWidth;
	}
	public void setDefaultColumnWidth(int defaultColumnWidth) {
		DefaultColumnWidth = defaultColumnWidth;
	}
	public int getDefaultRowHeight() {
		return DefaultRowHeight;
	}
	public void setDefaultRowHeight(int defaultRowHeight) {
		DefaultRowHeight = defaultRowHeight;
	}
	public int getDmOrientation() {
		return dmOrientation;
	}
	public void setDmOrientation(int dmOrientation) {
		this.dmOrientation = dmOrientation;
	}
	public int getEndRow() {
		return EndRow;
	}
	public void setEndRow(int endRow) {
		EndRow = endRow;
	}
	public int getExpandedColumnCount() {
		return ExpandedColumnCount;
	}
	public void setExpandedColumnCount(int expandedColumnCount) {
		ExpandedColumnCount = expandedColumnCount;
	}
	public int getExpandedRowCount() {
		return ExpandedRowCount;
	}
	public void setExpandedRowCount(int expandedRowCount) {
		ExpandedRowCount = expandedRowCount;
	}
	public int getFixedColumnCount() {
		return FixedColumnCount;
	}
	public void setFixedColumnCount(int fixedColumnCount) {
		FixedColumnCount = fixedColumnCount;
	}
	public int getFixedRowCount() {
		return FixedRowCount;
	}
	public void setFixedRowCount(int fixedRowCount) {
		FixedRowCount = fixedRowCount;
	}
	public int getIsPrintBeginRow() {
		return IsPrintBeginRow;
	}
	public void setIsPrintBeginRow(int isPrintBeginRow) {
		IsPrintBeginRow = isPrintBeginRow;
	}
	
	
}
