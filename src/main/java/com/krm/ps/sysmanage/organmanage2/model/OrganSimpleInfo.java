package com.krm.ps.sysmanage.organmanage2.model;

/**
 * 简单机构信息。增加此类是为了提高效率，不用加载大量无关的信息，并且可以利于机构树缓存，不用频繁进行数据库查询。
 *
 */
public class OrganSimpleInfo {

	private int id;

	private String name;

	private String code;

	private String areaId;
	
	private String type;

	public OrganSimpleInfo(int id, String code, String name, String areaId) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.areaId = areaId;
	}
	
	//add by safe at 2007.11.27
	public OrganSimpleInfo(int id, String code, String name, String areaId, String type) {
		this.id = id;
		this.code = code;
		this.name = name;
		this.areaId = areaId;
		this.type = type;
	}

	/**
	 * 机构id
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * 机构编码
	 * @param code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 机构名称
	 * @param name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 地区编码
	 * @return
	 */
	public String getAreaId() {
		return areaId;
	}
	
	/**
	 * 机构类型
	 * @return
	 */
	public String getType() {
		return type;
	}
	
	public String toString() {
		return id+" "+code+" "+name+" "+areaId;
	}

	public void setType(String type) {
		this.type = type;
	}

	
}
