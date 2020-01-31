package com.krm.ps.model.vo;
/*****************************************************************
* 描述: 对就数据库表code_from_validator,主要存放指标对应的页面校验规�?
* //////////////////////////////////////////////////////// 
* 创建�?QWK
* 创建日期: 2014-07-9 11:57:11  
* 修改�?  
* 修改日期:  
* 修改说明:  
******************************************************************/
public class FromValidator {
    private Long    pkid;         //主键
    private String  funname;      //函数名称  
    private String  rulesname;    //规则名称
    private String  prompt;       //页面提示信息
    private String  report_id;    //报表id
    private String  target_field; //字段列名
    private String  funparam;     //函数参数
	
    
    public FromValidator() {
		super();
	}


	public Long getPkid() {
		return pkid;
	}


	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}


	public String getFunname() {
		return funname;
	}


	public void setFunname(String funname) {
		this.funname = funname;
	}


	public String getRulesname() {
		return rulesname;
	}


	public void setRulesname(String rulesname) {
		this.rulesname = rulesname;
	}


	public String getPrompt() {
		return prompt;
	}


	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}


	public String getReport_id() {
		return report_id;
	}


	public void setReport_id(String report_id) {
		this.report_id = report_id;
	}


	public String getTarget_field() {
		return target_field;
	}


	public void setTarget_field(String target_field) {
		this.target_field = target_field;
	}


	public String getFunparam() {
		return funparam;
	}


	public void setFunparam(String funparam) {
		this.funparam = funparam;
	}
    
    


}
