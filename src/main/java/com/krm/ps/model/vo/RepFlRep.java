package com.krm.ps.model.vo;

public class RepFlRep {
	 private Long pkid;       
     private Long repflid ;  
     private Long repid ;  
     private String replable ;  
     private String hvattr ;    
     private String hvfiled;	
     private String repdesc;	
     private String reporder; 
     private String status;
     private String looplab;
     private String tablelab;
     private String wherecondition;
     
     
     
   
	
	public String getWherecondition() {
		return wherecondition;
	}
	public void setWherecondition(String wherecondition) {
		this.wherecondition = wherecondition;
	}
	public String getTablelab() {
		return tablelab;
	}
	public void setTablelab(String tablelab) {
		this.tablelab = tablelab;
	}
	public String getLooplab() {
		return looplab;
	}
	public void setLooplab(String looplab) {
		this.looplab = looplab;
	}
	public Long getPkid() {
		return pkid;
	}
	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}
	public Long getRepflid() {
		return repflid;
	}
	public void setRepflid(Long repflid) {
		this.repflid = repflid;
	}
	public Long getRepid() {
		return repid;
	}
	public void setRepid(Long repid) {
		this.repid = repid;
	}
	public String getReplable() {
		return replable;
	}
	public void setReplable(String replable) {
		this.replable = replable;
	}
	public String getHvattr() {
		return hvattr;
	}
	public void setHvattr(String hvattr) {
		this.hvattr = hvattr;
	}
	public String getHvfiled() {
		return hvfiled;
	}
	public void setHvfiled(String hvfiled) {
		this.hvfiled = hvfiled;
	}
	public String getRepdesc() {
		return repdesc;
	}
	public void setRepdesc(String repdesc) {
		this.repdesc = repdesc;
	}
	public String getReporder() {
		return reporder;
	}
	public void setReporder(String reporder) {
		this.reporder = reporder;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
