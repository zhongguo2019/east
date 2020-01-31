package com.krm.ps.model.vo;

import java.util.List;

import org.displaytag.properties.SortOrderEnum;


public class PaginatedListHelper{

	private int fullListSize;//总记录数
	private List list;//要实现是实体类列�?
	private int pageSize = 15;//每页的大�?
	private int currentPageNo;//当前页码�?
	private String searchId;
	private String sortCriterion;
	private Page page;
	
	
	
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPageNo() {
		return currentPageNo;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public PaginatedListHelper(List list,int currentPageNo){
		this.list = list;
		this.currentPageNo = currentPageNo;
	}
	
	public PaginatedListHelper(List list,int fullListSize,int currentPageNo){
		this.list = list;
		this.fullListSize = fullListSize;
		this.currentPageNo = currentPageNo;
	}
	public PaginatedListHelper(List list,int fullListSize,int currentPageNo,int pageSize){
		this.list = list;
		this.fullListSize = fullListSize;
		this.currentPageNo = currentPageNo;
		this.pageSize = pageSize;
	}
	
	public PaginatedListHelper(List list,Page page){
		this.list = list;
		this.page=page;
		this.fullListSize = page.getTotalRecord();
		this.currentPageNo = page.getPageNo();
	} 
	public PaginatedListHelper(List list,Page page,String sortCriterion){
		this.list = list;
		this.page=page;
		this.fullListSize = page.getTotalRecord();
		this.currentPageNo = page.getPageNo();
		this.sortCriterion=sortCriterion;
	} 
	public void setFullListSize(int fullListSize) {
		this.fullListSize = fullListSize;
	}

	public void setList(List list) {
		this.list = list;
	}

	public void setObjectsPerPage(int objectsPerPage) {
		this.pageSize = objectsPerPage;
	}

	public void setPageNumber(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}

	public void setSortCriterion(String sortCriterion) {
		this.sortCriterion = sortCriterion;
	}

	public void setSortDirection(SortOrderEnum sortDirection) {
		this.sortDirection = sortDirection;
	}

	private SortOrderEnum sortDirection;
	
	public int getFullListSize() {
		return this.fullListSize;
	}

	public List getList() {
		return this.list;
	}

	public int getObjectsPerPage() {
		return this.pageSize;
	}

	public int getPageNumber() {
		return this.currentPageNo;
	}

	public String getSearchId() {
		return this.searchId;
	}

	public String getSortCriterion() {
		return this.sortCriterion;
	}

	public SortOrderEnum getSortDirection() {
		return this.sortDirection;
	}
	
	
}
