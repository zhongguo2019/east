package com.krm.ps.model.vo;
/*****************************************************************
* 描述: 对就数据库表code_rep_task,主要存放校验任务
* //////////////////////////////////////////////////////// 
* 创建�?lxh
* 创建日期: 2013-04-13 11:01:11   周六
* 修改�?  
* 修改日期:  
* 修改说明:  
******************************************************************/
public class ReportTask {
	 private Long pkid;                             //主键
     private String reportId;						//报表id
     private String reportName;						//报表名称
     private String organId;						//机构id
     private String organName ;						//机构名称
     private String createPerson;					//任务创建�?
     private String createPersonid;					//任务创建人id
     private String creatTime;						//任务创建时间	2012-03-12		
     private String executTime;						//任务执行时间
     private String status;							//任务状�?   0代表�?�� 1代表执行�?代表已完�?校验出错
     private String content ;						//备注
     private String taskType;                       //任务类型  1是校�?2是报�?
     
     
     
     
     
	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public ReportTask() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getCreatePersonid() {
		return createPersonid;
	}

	public void setCreatePersonid(String createPersonid) {
		this.createPersonid = createPersonid;
	}

	public Long getPkid() {
		return pkid;
	}
	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getOrganId() {
		return organId;
	}
	public void setOrganId(String organId) {
		this.organId = organId;
	}
	public String getOrganName() {
		return organName;
	}
	public void setOrganName(String organName) {
		this.organName = organName;
	}
	public String getCreatePerson() {
		return createPerson;
	}
	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}
	public String getCreatTime() {
		return creatTime;
	}
	public void setCreatTime(String creatTime) {
		this.creatTime = creatTime;
	}
	public String getExecutTime() {
		return executTime;
	}
	public void setExecutTime(String executTime) {
		this.executTime = executTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
     

}
