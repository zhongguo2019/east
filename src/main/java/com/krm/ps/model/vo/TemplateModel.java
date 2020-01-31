package com.krm.ps.model.vo;

public class TemplateModel {
	/*****************************************************************
	* 描述: 对就数据库表Template_Model,主要存放校验结果
	* //////////////////////////////////////////////////////// 
	* 创建�?lxh
	* 创建日期: 2013-07-17 10:35:11  
	* 修改�?  
	* 修改日期:  
	* 修改说明:  
	******************************************************************/
	private Long pkid;        //主键
	 private Long templateId;   //模板id
    private Long modelid ;  //模型id
    private String templateTarget;  //模板指标field
    private String modelTarget;        //模型指标field
	public Long getPkid() {
		return pkid;
	}
	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}
	public Long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	public Long getModelid() {
		return modelid;
	}
	public void setModelid(Long modelid) {
		this.modelid = modelid;
	}
	public String getTemplateTarget() {
		return templateTarget;
	}
	public void setTemplateTarget(String templateTarget) {
		this.templateTarget = templateTarget;
	}
	public String getModelTarget() {
		return modelTarget;
	}
	public void setModelTarget(String modelTarget) {
		this.modelTarget = modelTarget;
	}
    
    
}
