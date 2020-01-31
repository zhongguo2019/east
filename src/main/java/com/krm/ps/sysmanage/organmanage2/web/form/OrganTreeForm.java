package com.krm.ps.sysmanage.organmanage2.web.form;

import com.krm.ps.framework.common.web.form.BaseForm;

/**
 * 
 * @struts.form name="organTreeForm"
 */
public class OrganTreeForm extends BaseForm{

	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = 1L;
	
	private String json;
	
	private String beginDate;
	
	private String endDate;
	
	private String group;
	
	private String isPublic;
	
	private String id;
	
	private String name;
	
	private String creatorId;
	
	private String areaId;
	
	private String organsId;
	
	/**
	 * 
	 * @return 区域编码
	 */
	public String getAreaId() {
		return areaId;
	}	
	/**
	 * 
	 * @param areaId 区域编码
	 */
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}	
	/**
	 * 
	 * @return 开始日期
	 */
	public String getBeginDate() {
		return beginDate;
	}	
	/**
	 * 
	 * @param beginDate 开始日期
	 */
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}	
	/**
	 * 
	 * @return 创建者编码
	 */
	public String getCreatorId() {
		return creatorId;
	}	
	/**
	 * 
	 * @param creatorId 创建者编码
	 */
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}	
	/**
	 * 
	 * @return 结束日期
	 */
	public String getEndDate() {
		return endDate;
	}	
	/**
	 * 
	 * @param endDate 结束日期
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}	
	/**
	 * 
	 * @return 组
	 */
	public String getGroup() {
		return group;
	}	
	/**
	 * 
	 * @param group 组
	 */
	public void setGroup(String group) {
		this.group = group;
	}	
	/**
	 * 
	 * @return 编码
	 */
	public String getId() {
		return id;
	}	
	/**
	 * 
	 * @param id 编码
	 */
	public void setId(String id) {
		this.id = id;
	}	
	/**
	 * 
	 * @return 是否公开
	 */
	public String getIsPublic() {
		return isPublic;
	}	
	/**
	 * 
	 * @param isPublic 是否公开
	 */
	public void setIsPublic(String isPublic) {
		this.isPublic = isPublic;
	}	
	/**
	 * 
	 * @return
	 */
	public String getJson() {
		return json;
	}	
	/**
	 * 
	 * @param json json
	 */
	public void setJson(String json) {
		this.json = json;
	}	
	/**
	 * 
	 * @return 名称
	 */
	public String getName() {
		return name;
	}	
	/**
	 * 
	 * @param name 名称
	 */
	public void setName(String name) {
		this.name = name;
	}	
	/**
	 * 
	 * @return 机构编码
	 */
	public String getOrgansId()
	{
		return organsId;
	}	
	/**
	 * 
	 * @param organsId 机构编码
	 */
	public void setOrgansId(String organsId)
	{
		this.organsId = organsId;
	}
	
}
