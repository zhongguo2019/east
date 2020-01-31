package com.krm.ps.model.vo;

public class DicItem {
	
	private Long pkid;
	private String dicId;
	private String dicName;
	private Long parentId;
	private Long layer;
	private Integer isLeaf;
	private Long dispOrder;
	private String description;
	private Integer status;
	private Integer readonly;
	public Long getPkid() {
		return pkid;
	}
	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}
	public String getDicId() {
		return dicId;
	}
	public void setDicId(String dicId) {
		this.dicId = dicId;
	}
	public String getDicName() {
		return dicName;
	}
	public void setDicName(String dicName) {
		this.dicName = dicName;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Long getLayer() {
		return layer;
	}
	public void setLayer(Long layer) {
		this.layer = layer;
	}
	public Integer getIsLeaf() {
		return isLeaf;
	}
	public void setIsLeaf(Integer isLeaf) {
		this.isLeaf = isLeaf;
	}
	public Long getDispOrder() {
		return dispOrder;
	}
	public void setDispOrder(Long dispOrder) {
		this.dispOrder = dispOrder;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getReadonly() {
		return readonly;
	}
	public void setReadonly(Integer readonly) {
		this.readonly = readonly;
	}
	
	
}
