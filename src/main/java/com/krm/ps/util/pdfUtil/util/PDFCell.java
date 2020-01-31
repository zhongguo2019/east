package com.krm.ps.util.pdfUtil.util;

public class PDFCell {
	private String id;	//标识
	private String styleid;	//
	
	private int CellWidth;	//单元格宽
	private int RowHeight;	//行高
	
	private String DataType;	//数据分类
	private String IsMoney;		//是否货币
	private String Type;	//数据类型
	
	private int MergeAcross;	//列合并
	private int MergeDown;	//行合并
	
	private String Data;

	public int getCellWidth() {
		return CellWidth;
	}

	public void setCellWidth(int cellWidth) {
		CellWidth = cellWidth;
	}

	public String getData() {
		return Data;
	}

	public void setData(String data) {
		Data = data;
	}

	public String getDataType() {
		return DataType;
	}

	public void setDataType(String dataType) {
		DataType = dataType;
	}

	public String getIsMoney() {
		return IsMoney;
	}

	public void setIsMoney(String isMoney) {
		IsMoney = isMoney;
	}

	public int getMergeAcross() {
		return MergeAcross;
	}

	public void setMergeAcross(int mergeAcross) {
		MergeAcross = mergeAcross;
	}

	public int getMergeDown() {
		return MergeDown;
	}

	public void setMergeDown(int mergeDown) {
		MergeDown = mergeDown;
	}

	public int getRowHeight() {
		return RowHeight;
	}

	public void setRowHeight(int rowHeight) {
		RowHeight = rowHeight;
	}

	public String getStyleid() {
		return styleid;
	}

	public void setStyleid(String styleid) {
		this.styleid = styleid;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}
