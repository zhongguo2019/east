package com.krm.slsint.workfile.bo;

import java.util.List;

public class infoListTableBo {
	private String titleHeader;//����ı���
	private List infoList;//���еļ���
	private int listSize;
	private int index;
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public List getInfoList() {
		return infoList;
	}
	public void setInfoList(List infoList) {
		this.infoList = infoList;
	}
	public String getTitleHeader() {
		return titleHeader;
	}
	public void setTitleHeader(String titleHeader) {
		this.titleHeader = titleHeader;
	}
	public int getListSize() {
		return listSize;
	}
	public void setListSize(int listSize) {
		this.listSize = listSize;
	}
}
