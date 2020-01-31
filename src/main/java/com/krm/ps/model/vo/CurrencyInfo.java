package com.krm.ps.model.vo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: KRM soft</p>
 *
 * @author 王永慧
 */
/**
 * 
 * @hibernate.class table="code_rep_currency"
 */
public class CurrencyInfo implements Serializable{
	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = 999333245L;
	
	/**
	 * <code>唯一标识</code>
	 */
	private  Long pkid;
	
	/**
	 * <code>币种代号</code>
	 */
	private String code;
	
	/**
	 * <code>币种全称</code>
	 */
	private  String full_name;
	
	/**
	 * <code>币种简称</code>
	 */
	private String short_name;
	
	/**
	 * <code>货币单位名称/code>
	 */
	private String unit_name;

	/**
	 * <code>状态（常量）：1可用；2冻结；9作废</code>
	 */
	private String status;
	/**
	 * <code>修改时间</code>
	 */
	private String modify_date;
	/**
	 * <code>说明信息</code>
	 */
	private String description;
	
	
	public CurrencyInfo(){
		
	}

	/**
	 * @hibernate.id column="pkid" generator-class="native"
	 * @hibernate.generator-param name="sequence" value="code_rep_currency_seq"
	 */
	
	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}
	
	/**
	 * 
	 * @hibernate.property column="code"
	 */
	public String  getCode() {
		return  code;
	}
	public void setCode(String code) {
		this.code= code;
	}

	/**
	 * 
	 * @hibernate.property column="short_name"
	 */
	public String  getShort_name() {
		return  short_name;
	}
	public void setShort_name(String short_name) {
		this.short_name= short_name;
	}
	
	/**
	 * 
	 * @hibernate.property column="full_name"
	 */
	public String  getFull_name() {
		return  full_name;
	}
	public void setFull_name(String full_name) {
		this.full_name= full_name;
	}
	
	/**
	 * 
	 * @hibernate.property column="unit_name"
	 */
	public String  getUnit_name() {
		return  unit_name;
	}
	public void setUnit_name(String unit_name) {
		this.unit_name= unit_name;
	}
	
	/**
	 * 
	 * @hibernate.property column="status"
	 */
	public String  getStatus() {
		return  status;
	}
	public void setStatus(String status) {
		this.status= status;
	}
	
	/**
	 * 
	 * @hibernate.property column="description"
	 */
	public String  getDescription() {
		return  description;
	}
	public void setDescription(String description) {
		this.description= description;
	}
	
	/**
	 * 
	 * @hibernate.property column="modify_date"
	 */
	public String  getModify_date() {
		return  modify_date;
	}
	public void setModify_date(String modify_date) {
		this.modify_date= modify_date;
	}
	

	public String toString() {
        return new ToStringBuilder(this)
            .append("CurrencyInfo", getPkid())
            .toString();
    }
}
