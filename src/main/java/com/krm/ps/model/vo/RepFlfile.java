package com.krm.ps.model.vo;

public class RepFlfile {
	 private Long pkid;        //涓婚敭
     private String repfilename ;   //鎶ユ枃鏂囦欢鍚嶇О
     private String filepath ;  //鎶ユ枃鏂囦欢瀛樻斁鐨勮矾寰�
     private byte[] filebody ;    //报文主体
     private String createdate;		//鍒涘缓鏃堕棿
     private String organcode;
     private String  datadate;
     private String status;		//鐘舵�
     private String filebath;  //  鏂囦欢鍒涘缓鐨勬壒娆� 
     private String sysid;
     private Long userid ;    //创建者的ID
     private String name;
     private Long fileid;//报文的ID
     private Integer downloadnum;//涓嬭浇娆℃暟  

	/**
      * 
      * create table CUSTRISK.DSP_FL_FILE
(
 PKID       BIGINT not null,
 FILENAME   VARCHAR(256),
 FILEPATH    VARCHAR(256),
  FILEBODY   BLOB,
  CREATEDATE VARCHAR(30),
  ORGANCODE   VARCHAR(10), 
  USERID   VARCHAR(10), 
  DATADATE   VARCHAR(20), 
  STATUS     VARCHAR(10),
  FILEBATH   VARCHAR(256),
  SYSID      VARCHAR(256)
)
IN CUSTRISK_BASE 
 COMPRESS NO 
;
      * 
      * 
      * @return
      */
     
     
	public Long getPkid() {
		return pkid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrgancode() {
		return organcode;
	}
	public void setOrgancode(String organcode) {
		this.organcode = organcode;
	}
	public String getDatadate() {
		return datadate;
	}
	public void setDatadate(String datadate) {
		this.datadate = datadate;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}
	public String getRepfilename() {
		return repfilename;
	}
	public void setRepfilename(String repfilename) {
		this.repfilename = repfilename;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public byte[] getFilebody() {
		return filebody;
	}
	public void setFilebody(byte[] filebody) {
		this.filebody = filebody;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFilebath() {
		return filebath;
	}
	public void setFilebath(String filebath) {
		this.filebath = filebath;
	}
	public String getSysid() {
		return sysid;
	}
	public void setSysid(String sysid) {
		this.sysid = sysid;
	}
    public Integer getDownloadnum() {
		return downloadnum;
	}
	public void setDownloadnum(Integer downloadnum) {
		this.downloadnum = downloadnum;
	}
	public Long getFileid() {
		return fileid;
	}
	public void setFileid(Long fileid) {
		this.fileid = fileid;
	}

}
