package com.krm.ps.model.vo;

public class RepFlFomat {
	 private Long pkid;       
     private String repid ;  
     private String repfilename ;   
     private String repfileexte ;  
     private String repfilesepar ;    
     private String startdate;	
	private String enddate;	
     private String createdate;
     private String organCode;
     private byte[]  rephead ;  
     private String status;      
     private String repname;     
     
     private String systemid;
     
     private String successinfo;
     
     
     
     public String getSuccessinfo() {
    	 return successinfo;
     }
     public void setSuccessinfo(String successinfo) {
    	 this.successinfo = successinfo;
     }
     
	public String getRepname() {
		return repname;
	}
	public void setRepname(String repname) {
		this.repname = repname;
	}
	public String getSystemid() {
		return systemid;
	}
	public void setSystemid(String systemid) {
		this.systemid = systemid;
	}
	public byte[] getRephead() {
		return rephead;
	}
	public Long getPkid() {
		return pkid;
	}
	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}
	public String getRepid() {
		return repid;
	}
	public void setRepid(String repid) {
		this.repid = repid;
	}
	public String getRepfilename() {
		return repfilename;
	}
	public void setRepfilename(String repfilename) {
		this.repfilename = repfilename;
	}
	public String getRepfileexte() {
		return repfileexte;
	}
	public void setRepfileexte(String repfileexte) {
		this.repfileexte = repfileexte;
	}
	public String getRepfilesepar() {
		return repfilesepar;
	}
	public void setRepfilesepar(String repfilesepar) {
		this.repfilesepar = repfilesepar;
	}
	public void setRephead(byte[] rephead) {
		this.rephead = rephead;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStartdate() {
		return startdate;
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getOrganCode() {
		return organCode;
	}
	public void setOrganCode(String organCode) {
		this.organCode = organCode;
	}

}
