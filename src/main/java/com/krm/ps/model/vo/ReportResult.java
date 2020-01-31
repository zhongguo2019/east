package com.krm.ps.model.vo;

public class ReportResult {
	/*****************************************************************
	* 描述: 对就数据库表code_rep_result,主要存放校验结果
	* //////////////////////////////////////////////////////// 
	* 创建�?lxh
	* 创建日期: 2013-04-17 10:35:11  
	* 修改�?  
	* 修改日期:  
	* 修改说明:  
	******************************************************************/
	 private Long pkid;        //主键
	 private String rulecode;   //规则编码
     private String modelid ;  //模型id
     private String targetid;  //指标idwq
     private String targetname;        //指标名称
     private String rtype;    //规则类型
     private String organid;    // 机构id
     private String etldate;    //数据日期
     private String rulemsg;   //未�?过原�?
     private Long keyvalue;    //数据key�?
     private String cstatus;  //状�? 0无修�?1已修�?
     private String oldvalue;  //待修订�?
     private String newvalue;  //修改后�?
     private String oper;     //修改�?
     private String editdate;  //修改日期
     private String rangelevel;//主要针对客户风险用的
     private String rvalue; //参�?�?
     private String dtype;//数据类型�?	通过1	不�?�?
     private Long keyvalue1;    //如果补录数据在F表不存在时需要新增加的数据pkid
     private Long keyvalue2;    //如果补录数据在F表不存在时需要新增加的数据pkid
     private Long keyvalue3;    //如果补录数据在F表不存在时需要新增加的数据pkid

     
	public Long getKeyvalue1() {
		return keyvalue1;
	}

	public void setKeyvalue1(Long keyvalue1) {
		this.keyvalue1 = keyvalue1;
	}

	public Long getKeyvalue2() {
		return keyvalue2;
	}

	public void setKeyvalue2(Long keyvalue2) {
		this.keyvalue2 = keyvalue2;
	}

	public Long getKeyvalue3() {
		return keyvalue3;
	}

	public void setKeyvalue3(Long keyvalue3) {
		this.keyvalue3 = keyvalue3;
	}

	public String getDtype() {
		return dtype;
	}

	public void setDtype(String dtype) {
		this.dtype = dtype;
	}

	public String getRvalue() {
		return rvalue;
	}

	public void setRvalue(String rvalue) {
		this.rvalue = rvalue;
	}

	public String getRangelevel() {
		return rangelevel;
	}

	public void setRangelevel(String rangelevel) {
		this.rangelevel = rangelevel;
	}

	public ReportResult() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	public String getRulecode() {
		return rulecode;
	}

	public void setRulecode(String rulecode) {
		this.rulecode = rulecode;
	}

	public String getModelid() {
		return modelid;
	}

	public void setModelid(String modelid) {
		this.modelid = modelid;
	}

	public String getTargetid() {
		return targetid;
	}

	public void setTargetid(String targetid) {
		this.targetid = targetid;
	}

	public String getTargetname() {
		return targetname;
	}

	public void setTargetname(String targetname) {
		this.targetname = targetname;
	}

	public String getRtype() {
		return rtype;
	}

	public void setRtype(String rtype) {
		this.rtype = rtype;
	}

	public String getOrganid() {
		return organid;
	}

	public void setOrganid(String organid) {
		this.organid = organid;
	}

	public String getEtldate() {
		return etldate;
	}

	public void setEtldate(String etldate) {
		this.etldate = etldate;
	}

	public String getRulemsg() {
		return rulemsg;
	}

	public void setRulemsg(String rulemsg) {
		this.rulemsg = rulemsg;
	}

	public Long getKeyvalue() {
		return keyvalue;
	}

	public void setKeyvalue(Long keyvalue) {
		this.keyvalue = keyvalue;
	}

	public String getCstatus() {
		return cstatus;
	}

	public void setCstatus(String cstatus) {
		this.cstatus = cstatus;
	}

	public String getOldvalue() {
		return oldvalue;
	}

	public void setOldvalue(String oldvalue) {
		this.oldvalue = oldvalue;
	}

	public String getNewvalue() {
		return newvalue;
	}

	public void setNewvalue(String newvalue) {
		this.newvalue = newvalue;
	}

	public String getOper() {
		return oper;
	}

	public void setOper(String oper) {
		this.oper = oper;
	}

	public String getEditdate() {
		return editdate;
	}

	public void setEditdate(String editdate) {
		this.editdate = editdate;
	}


	

}
