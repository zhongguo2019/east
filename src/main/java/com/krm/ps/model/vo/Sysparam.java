package com.krm.ps.model.vo;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @date�?013-10-28 下午4:09:02   
 * @author gaozhongbo
 * 类描述：获取补录数据时间
 *
 */
public class Sysparam implements Serializable{
	
	//CURRENTDATE,LASTDATE,NEXTDATE
    private Date currentdate; //当前日期
    private Date lastdate; //昨天
    private Date nextdate; //明天
	public Sysparam() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Sysparam(Date currentdate, Date lastdate, Date nextdate) {
		super();
		this.currentdate = currentdate;
		this.lastdate = lastdate;
		this.nextdate = nextdate;
	}
	public Date getCurrentdate() {
		return currentdate;
	}
	public void setCurrentdate(Date currentdate) {
		this.currentdate = currentdate;
	}
	public Date getLastdate() {
		return lastdate;
	}
	public void setLastdate(Date lastdate) {
		this.lastdate = lastdate;
	}
	public Date getNextdate() {
		return nextdate;
	}
	public void setNextdate(Date nextdate) {
		this.nextdate = nextdate;
	}
    
	
}
