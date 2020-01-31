package com.krm.ps.model.vo;

public class RepFlRepFiled {
	 private Long pkid;        //涓婚敭
     private Long repflid ;  //鏍煎紡ID
     private Long repid ;   //鎶ユ枃ID
     private String replable ;  //鎶ユ枃鏍囩
     private String hvattr ;    //鏄惁鏈夊睘鎬�
     private String hvfiled;	//鏄惁鏈夊瓧娈�
     private String isloop;	//鏄惁鏈夊瓧娈�
     private String repdesc;	//鎶ユ枃鏍囩
     private String reporder; //鎶ユ枃椤哄簭
     private String  datetype; //鏁版嵁绫诲瀷DATETYPE 
     private String status;//鐘舵�
     private String  attribute;//鐘舵�
     private String  looplab;//寰幆鏍囩
     private String outrule;//鏍煎紡鍖栫被鍨�
     private String rulesize;//淇濈暀灏忔暟浣嶆暟鎴栨寚鏍囬暱搴�
     
     
	public String getLooplab() {
		return looplab;
	}
	public void setLooplab(String looplab) {
		this.looplab = looplab;
	}
	public String getAttribute() {
		return attribute;
	}
	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}
	public String getIsloop() {
		return isloop;
	}
	public void setIsloop(String isloop) {
		this.isloop = isloop;
	}
	public String getDatetype() {
		return datetype;
	}
	public void setDatetype(String datetype) {
		this.datetype = datetype;
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
	public String getOutrule() {
		return outrule;
	}
	public void setOutrule(String outrule) {
		this.outrule = outrule;
	}
	public String getRulesize() {
		return rulesize;
	}
	public void setRulesize(String rulesize) {
		this.rulesize = rulesize;
	}
	
}
