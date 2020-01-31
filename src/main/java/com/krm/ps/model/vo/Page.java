package com.krm.ps.model.vo;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;


public class Page implements Serializable {
	
	private int recordNo;//当前记录
	private int pageNo;//当前页面
	private int totalRecord;//总记录数
	private int pageSize = 30;//分页每页大小
	private int totalPage;
	
	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public Page(int pageNo){
		this.pageNo = pageNo;
	}
	public Page(int pageNo,int pageSize,int totalPage){
		this.pageNo = pageNo;
		this.pageNo=pageSize;
	}
	
	public Page(int totalRecord,int pageNo){
		this.totalRecord = totalRecord;
		this.pageNo = pageNo;
	}

	public int getRecordNo() {
		//return recordNo;
		//System.out.println("recordNo:"+(getPageNo()-1)*getPageSize());
		return (getPageNo()-1)*getPageSize();
	}

	public void setRecordNo(int recordNo) {
		this.recordNo = recordNo;
	}

	public int getPageNo() {
		if(pageNo<1){
			this.pageNo = 1;
		}else {
			if(totalRecord>0&&(pageNo-1)*pageSize>=totalRecord){
				--pageNo;
			}
		}
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	
	
	
}
