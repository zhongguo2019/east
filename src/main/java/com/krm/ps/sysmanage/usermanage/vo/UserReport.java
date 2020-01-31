package com.krm.ps.sysmanage.usermanage.vo;

import java.io.Serializable;

import com.krm.ps.util.ConvertUtil;
import com.krm.ps.framework.vo.BaseObject;


/**
 * @hibernate.class table="rep_oper_contrast"
 */
public class UserReport extends BaseObject implements Serializable{

	private static final long serialVersionUID = 255511L;

    //唯一标识
	private Long pkId;
	
	//用户ID
	private Long operId;
	
	//用户名称
	private String operName;
	
    //报表类型ID
	private Long typeId;
	
	//报表类型名称
	private String typeName;
	  
	//报表ID
	private Long reportId;
	
    //报表名称
	private String reportName;
	
	//是否已经关联，默认为不关联
	private int status;
	
	public UserReport(){
	}
	
	public UserReport(UserReport userReport,String operName){
        
		try {
			
			ConvertUtil.copyProperties(this, userReport, true);
			this.operName=operName;
			
		} catch (Exception e) {
			;
		}
	}

	/**	
	 * @hibernate.id column="pkid" generator-class="native"
	 * @hibernate.generator-param name="sequence" value="rep_oper_contrast_seq"
	 */
	public Long getPkId() {
		return pkId;
	}

	public void setPkId(Long pkId) {
		this.pkId = pkId;
	}

	/**
	 * @hibernate.property column="operid" 
	 */
	public Long getOperId() {
		return operId;
	}

	public void setOperId(Long operId) {
		this.operId = operId;
	}

	
	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	/**
	 * @hibernate.property column="repid" 
	 */
	public Long getReportId() {
		return reportId;
	}

	public void setReportId(Long reportId) {
		this.reportId = reportId;
	}

	/**
	 * @hibernate.property column="typeid" 
	 */
	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("\n用户ID:" + pkId  + "\n");
		return s.toString();
		}

	public boolean equals(Object o) {
		return false;
	}

	public int hashCode() {
		return 0;
	}

}
