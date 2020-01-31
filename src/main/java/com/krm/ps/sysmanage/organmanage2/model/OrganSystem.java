package com.krm.ps.sysmanage.organmanage2.model;

/**
 * 机构体系信息，包含创建此树的用户id，地区等
 * 
 */
public class OrganSystem implements Cloneable {

	public static final int VISIBILITY_PUBLIC = 0;

	public static final int VISIBILITY_GROUP = 1;

	public static final int VISIBILITY_CREATOR = 2;

	private Integer id;

	private String name;

	private int creatorId;

	private int showOrder;

	private String areaId;

	private int groupId;

	private int visibility;

	private String beginDate;

	private String endDate;

	private String status;

	private String description;
	
	private String creatorName;
	
	private String isUse;

	/**
	 * 机构系统id
	 */
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * 机构系统名称
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 创建用户
	 */
	public int getCreatorId() {
		return creatorId;
	}

	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}

	/**
	 * 地区
	 */
	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	/**
	 * 分组
	 */
	public int getGroupId() {
		return groupId;
	}

	public void setGroupId(int groupId) {
		this.groupId = groupId;
	}

	/**
	 * 可见性，取值VISIBILITY_PUBLIC、VISIBILITY_GROUP、VISIBILITY_CREATOR
	 */
	public int getVisibility() {
		return visibility;
	}

	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

	/**
	 * 机构系统启用日期
	 */
	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	/**
	 * 停用日期
	 */
	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	/**
	 * 使用状态
	 */
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getShowOrder() {
		return showOrder;
	}

	public void setShowOrder(int showOrder) {
		this.showOrder = showOrder;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	/*
	 * 根据可用日期判断是否失效
	 */
	boolean isValid(String date) {
		if(date==null) {
			return true;
		}
		if(beginDate==null||endDate==null) {
			return false;
		}
		if(beginDate.compareTo(date)<=0&&date.compareTo(endDate)<=0) {
			return true;
		}
		return false;
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

//	public boolean equals(Object o) {
//		return false;
//	}
	
	
	public String toString() {
		StringBuffer s=new StringBuffer();
		s.append("organSystem ").append(id).append(" ").append(name).append(" ").append(visibility).append("\n");		
		return s.toString();
	}

	public String getCreatorName()
	{
		return creatorName;
	}

	public void setCreatorName(String creatorName)
	{
		this.creatorName = creatorName;
	}

	public String getIsUse()
	{
		return isUse;
	}

	public void setIsUse(String isUse)
	{
		this.isUse = isUse;
	}

}
