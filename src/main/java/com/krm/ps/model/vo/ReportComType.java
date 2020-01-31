package com.krm.ps.model.vo;

public class ReportComType {
	 
	private Long pkid;        //主键
    private String typename ;  //报表id
    private String flag; ////跑批的标志  0采集层 1目标层2风险预警
    private String systemcode;//系统标识  1人行标准化3客户风险
     
	public ReportComType() {
		super();
	}

	
	
	public String getSystemcode() {
		return systemcode;
	}



	public void setSystemcode(String systemcode) {
		this.systemcode = systemcode;
	}



	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	

}
