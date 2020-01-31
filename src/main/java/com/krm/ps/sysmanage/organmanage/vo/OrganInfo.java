package com.krm.ps.sysmanage.organmanage.vo;

import java.io.Serializable;

import com.krm.ps.util.ConvertUtil;
import com.krm.ps.framework.vo.BaseObject;


/**
 * @hibernate.class table="code_org_organ"
 */
public class OrganInfo extends BaseObject implements Serializable{

	private static final long serialVersionUID = 253234L;

    //唯一标识
	private Long pkid;
	
	//机构编号
	private String code;
	
    //上级机构ID
	private String super_id;
	  
	//机构全称
	private String full_name;
	
	//机构简称
	private String short_name;
	
	//机构类型
	private Integer organ_type;
	
    //机构类型名称
	private String organ_typename;
	
    //业务类型
	private Integer business_type;
	
	//业务类型名称
	private String business_typename;
	
	//虚实机构类型
	private String dummy_type;
	
    //顶层机构
	private String peak;
	
	//邮编
	private String postalcode;
	
	//电话号码
	private String phone; 
	
    //单位地址
	private  String adder;
	
    //法人代表
	private  String delegate; 
	
	//法人代表手机号码
	private  String del_mobile;
	
    //法人代表办公电话
	private  String del_phone; 
	
    //法人代表其它联系方式
	private  String del_other;
	
	// 状态（常量）
	private  Integer status;
	
    //机构信息描述
	private  String description; 
	
    //开始日期
	private  String begin_date;
	
    //失效日期
	private String end_date;
	
    //创建日期
	private String create_date;
	
    //创建人
	private Integer creator;
	
	//机构级别
	private Integer layer;
	
	//机构序号
	private Integer organOrder;
	
	public OrganInfo(){
	}
	
	public OrganInfo(OrganInfo organ,String organ_typename ,String business_typename){
		try {
			
			ConvertUtil.copyProperties(this, organ, true);
			this.organ_typename=organ_typename;
			this.business_typename=business_typename;
			
		} catch (Exception e) {
			;
		}
	}
	
	public OrganInfo(OrganInfo organ){
		try {			
			ConvertUtil.copyProperties(this, organ, true);
		} catch (Exception e) {
			;
		}
	}
	
	public String getBusiness_typename() {
		return business_typename;
	}


	public void setBusiness_typename(String business_typename) {
		this.business_typename = business_typename;
	}


	public String getOrgan_typename() {
		return organ_typename;
	}


	public void setOrgan_typename(String organ_typename) {
		this.organ_typename = organ_typename;
	}


	/**
	 * @hibernate.property column="adder" 
	 */
	public String getAdder() {
		return adder;
	}

	public void setAdder(String adder) {
		this.adder = adder;
	}

	/**
	 * @hibernate.property column="begin_date" 
	 */
	public String getBegin_date() {
		return begin_date;
	}

	public void setBegin_date(String begin_date) {
		this.begin_date = begin_date;
	}

	/**
	 * @hibernate.property column="business_type" 
	 */
	public Integer getBusiness_type() {
		return business_type;
	}

	public void setBusiness_type(Integer business_type) {
		this.business_type = business_type;
	}

	/**
	 * @hibernate.property column="code" 
	 */
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @hibernate.property column="create_date" 
	 */
	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	/**
	 * @hibernate.property column="creator" 
	 */
	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	/**
	 * @hibernate.property column="del_mobile" 
	 */
	public String getDel_mobile() {
		return del_mobile;
	}

	public void setDel_mobile(String del_mobile) {
		this.del_mobile = del_mobile;
	}

	/**
	 * @hibernate.property column="del_other" 
	 */
	public String getDel_other() {
		return del_other;
	}

	public void setDel_other(String del_other) {
		this.del_other = del_other;
	}

	/**
	 * @hibernate.property column="del_phone" 
	 */
	public String getDel_phone() {
		return del_phone;
	}

	public void setDel_phone(String del_phone) {
		this.del_phone = del_phone;
	}

	/**
	 * @hibernate.property column="delegate" 
	 */
	public String getDelegate() {
		return delegate;
	}

	public void setDelegate(String delegate) {
		this.delegate = delegate;
	}

	/**
	 * @hibernate.property column="description" 
	 */
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @hibernate.property column="dummy_type" 
	 */
	public String getDummy_type() {
		return dummy_type;
	}

	public void setDummy_type(String dummy_type) {
		this.dummy_type = dummy_type;
	}

	/**
	 * @hibernate.property column="end_date" 
	 */
	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	/**
	 * @hibernate.property column="full_name" 
	 */
	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	/**
	 * @hibernate.property column="organ_type" 
	 */
	public Integer getOrgan_type() {
		return organ_type;
	}

	public void setOrgan_type(Integer organ_type) {
		this.organ_type = organ_type;
	}

	/**
	 * @hibernate.property column="peak" 
	 */
	public String getPeak() {
		return peak;
	}

	public void setPeak(String peak) {
		this.peak = peak;
	}

	/**
	 * @hibernate.property column="phone" 
	 */
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**	
	 * @hibernate.id column="pkid" generator-class="native"
	 * @hibernate.generator-param name="sequence" value="code_org_organ_seq"
	 */
	public Long getPkid() {
		return pkid;
	}

	public void setPkid(Long pkid) {
		this.pkid = pkid;
	}

	/**
	 * @hibernate.property column="postalcode" 
	 */
	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	/**
	 * @hibernate.property column="short_name" 
	 */
	public String getShort_name() {
		return short_name;
	}

	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}

	/**
	 * @hibernate.property column="status" 
	 */
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * @hibernate.property column="super_id" 
	 */
	public String getSuper_id() {
		return super_id;
	}

	public void setSuper_id(String super_id) {
		this.super_id = super_id;
	}

	
	public Integer getLayer() {
		return layer;
	}

	public void setLayer(Integer layer) {
		this.layer = layer;
	}

	/**
	 * @hibernate.property column="organ_order" 
	 */
	public Integer getOrganOrder() {
		return organOrder;
	}

	public void setOrganOrder(Integer organOrder) {
		this.organOrder = organOrder;
	}

	public String toString() {
		return ("\n机构编号:" + code + "机构名称:" + full_name 
				+ "\n");
		}

	public boolean equals(Object o) {
		return false;
	}

	public int hashCode() {
		return 0;
	}
}
