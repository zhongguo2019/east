package com.krm.ps.sysmanage.organmanage.web.form;

import org.apache.struts.action.ActionForm;
/**
 * 机构Form
 * @struts.form name="organForm"
 */
public class OrganForm extends ActionForm {
	
	private static final long serialVersionUID = 142423L;

	//唯一标识
	private Long pkid;
	
	//机构编码
	private String code;
	
	//机构全称
	private String full_name;
	
	//机构简称
	private String short_name;
	
	//机构类型
	private int organ_type;
	
	//业务类型
	private int business_type;
	
	//邮编
	private String postalcode;
	
	//电话
	private String phone;
	
	//地址
	private String adder;
	
	//法人代表
	private String delegate;
	
	//法人代表手机号
	private String del_mobile;
	
	//法人代表办公电话
	private String del_phone;
	
	//其他联系方式
	private String del_other;
	
	//开始日期
	private String begin_date;
	
	private String end_date;
	
	/**
	 * <code>地区ID</code>
	 */
	private String super_id;
	
	public OrganForm(){
		
	}

	/**
	 * 
	 * @return 地址
	 */
	public String getAdder() {
		return adder;
	}

	/**
	 * 
	 * @param adder 地址
	 */
	public void setAdder(String adder) {
		this.adder = adder;
	}

	/**
	 * 
	 * @return 开始日期
	 */
	public String getBegin_date() {
		return begin_date;
	}

	/**
	 * 
	 * @param begin_date 开始日期
	 */
	public void setBegin_date(String begin_date) {
		this.begin_date = begin_date;
	}

	/**
	 * 
	 * @return 业务类型
	 */
	public int getBusiness_type() {
		return business_type;
	}

	/**
	 * 
	 * @param business_type 业务类型
	 */
	public void setBusiness_type(int business_type) {
		this.business_type = business_type;
	}

	/**
	 * 
	 * @return 机构编码
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 
	 * @param code 机构编码
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 
	 * @return 法人代表手机号
	 */
	public String getDel_mobile() {
		return del_mobile;
	}

	/**
	 * 
	 * @param del_mobile 法人代表手机号
	 */
	public void setDel_mobile(String del_mobile) {
		this.del_mobile = del_mobile;
	}

	/**
	 * 
	 * @return 其他联系方式
	 */
	public String getDel_other() {
		return del_other;
	}

	/**
	 * 
	 * @param del_other 其他联系方式
	 */
	public void setDel_other(String del_other) {
		this.del_other = del_other;
	}

	/**
	 * 
	 * @return 法人代表办公电话
	 */
	public String getDel_phone() {
		return del_phone;
	}

	/**
	 * 
	 * @param del_phone 法人代表办公电话
	 */
	public void setDel_phone(String del_phone) {
		this.del_phone = del_phone;
	}

	/**
	 * 
	 * @return 法人代表
	 */
	public String getDelegate() {
		return delegate;
	}

	/**
	 * 
	 * @param delegate 法人代表
	 */
	public void setDelegate(String delegate) {
		this.delegate = delegate;
	}

	/**
	 * 
	 * @return 机构全称
	 */
	public String getFull_name() {
		return full_name;
	}

	/**
	 * 
	 * @param full_name 机构全称
	 */
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	/**
	 * 
	 * @return 机构类型
	 */
	public int getOrgan_type() {
		return organ_type;
	}

	/**
	 * 
	 * @param organ_type 机构类型
	 */
	public void setOrgan_type(int organ_type) {
		this.organ_type = organ_type;
	}

	/**
	 * 
	 * @return 电话
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * 
	 * @param phone 电话
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * 
	 * @return 唯一标识
	 */
	public Long getPkid() {
		return pkid;
	}

	/**
	 * 
	 * @param pkid 唯一标识
	 */
	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	/**
	 * 
	 * @return 邮编
	 */
	public String getPostalcode() {
		return postalcode;
	}

	/**
	 * 
	 * @param postalcode 邮编
	 */
	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	/**
	 * 
	 * @return 机构简称
	 */
	public String getShort_name() {
		return short_name;
	}

	/**
	 * 
	 * @param short_name 机构简称
	 */
	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}

	/**
	 * 
	 * @return 结束日期
	 */
	public String getEnd_date() {
		return end_date;
	}

	/**
	 * 
	 * @param end_date 结束日期
	 */
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	/**
	 * 
	 * @return 地区编码
	 */
	public String getSuper_id() {
		return super_id;
	}

	/**
	 * 
	 * @param super_id 地区编码
	 */
	public void setSuper_id(String super_id) {
		this.super_id = super_id;
	}

}
