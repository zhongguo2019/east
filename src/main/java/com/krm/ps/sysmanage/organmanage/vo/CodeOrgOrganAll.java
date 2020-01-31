package com.krm.ps.sysmanage.organmanage.vo;

public class CodeOrgOrganAll {
	//唯一标识
	private Integer pkid;
	
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
	
    //业务类型
	private Integer business_type;
	
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
	
	//机构序号
	private Integer organ_order;
	//上级机构号
	private String inst_parent_no;
	//上级机构名称
	private String inst_parent_name;
	//机构级别
	private Integer inst_level;
	//法人代码
	private String creunit_no;
	
	
	public String getInst_parent_no() {
		return inst_parent_no;
	}

	public void setInst_parent_no(String inst_parent_no) {
		this.inst_parent_no = inst_parent_no;
	}

	public String getInst_parent_name() {
		return inst_parent_name;
	}

	public void setInst_parent_name(String inst_parent_name) {
		this.inst_parent_name = inst_parent_name;
	}

	public Integer getInst_level() {
		return inst_level;
	}

	public void setInst_level(Integer inst_level) {
		this.inst_level = inst_level;
	}

	public String getCreunit_no() {
		return creunit_no;
	}

	public void setCreunit_no(String creunit_no) {
		this.creunit_no = creunit_no;
	}

	public CodeOrgOrganAll(Integer pkid, String code, String super_id,
			String full_name, String short_name, Integer organ_type,
			Integer business_type, String dummy_type, String peak,
			String postalcode, String phone, String adder,
			String delegate, String del_mobile, String del_phone,
			String del_other, Integer status, String description,
			String begin_date, String end_date, String create_date,
			Integer creator, Integer organ_order) {
//		super();
		this.pkid = pkid;
		this.code = code;
		this.super_id = super_id;
		this.full_name = full_name;
		this.short_name = short_name;
		this.organ_type = organ_type;
		this.business_type = business_type;
		this.dummy_type = dummy_type;
		this.peak = peak;
		this.postalcode = postalcode;
		this.phone = phone;
		this.adder = adder;
		this.delegate = delegate;
		this.del_mobile = del_mobile;
		this.del_phone = del_phone;
		this.del_other = del_other;
		this.status = status;
		this.description = description;
		this.begin_date = begin_date;
		this.end_date = end_date;
		this.create_date = create_date;
		this.creator = creator;
		this.organ_order = organ_order;
	}

	public CodeOrgOrganAll() {
		// TODO Auto-generated constructor stub
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSuper_id() {
		return super_id;
	}

	public void setSuper_id(String super_id) {
		this.super_id = super_id;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getShort_name() {
		return short_name;
	}

	public void setShort_name(String short_name) {
		this.short_name = short_name;
	}

	public Integer getOrgan_type() {
		return organ_type;
	}

	public void setOrgan_type(Integer organ_type) {
		this.organ_type = organ_type;
	}

	public Integer getBusiness_type() {
		return business_type;
	}

	public void setBusiness_type(Integer business_type) {
		this.business_type = business_type;
	}

	public String getDummy_type() {
		return dummy_type;
	}

	public void setDummy_type(String dummy_type) {
		this.dummy_type = dummy_type;
	}

	public String getPeak() {
		return peak;
	}

	public void setPeak(String peak) {
		this.peak = peak;
	}

	public String getPostalcode() {
		return postalcode;
	}

	public void setPostalcode(String postalcode) {
		this.postalcode = postalcode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAdder() {
		return adder;
	}

	public void setAdder(String adder) {
		this.adder = adder;
	}

	public String getDelegate() {
		return delegate;
	}

	public void setDelegate(String delegate) {
		this.delegate = delegate;
	}

	public String getDel_mobile() {
		return del_mobile;
	}

	public void setDel_mobile(String del_mobile) {
		this.del_mobile = del_mobile;
	}

	public String getDel_phone() {
		return del_phone;
	}

	public void setDel_phone(String del_phone) {
		this.del_phone = del_phone;
	}

	public String getDel_other() {
		return del_other;
	}

	public void setDel_other(String del_other) {
		this.del_other = del_other;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getBegin_date() {
		return begin_date;
	}

	public void setBegin_date(String begin_date) {
		this.begin_date = begin_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public String getCreate_date() {
		return create_date;
	}

	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	public Integer getCreator() {
		return creator;
	}

	public void setCreator(Integer creator) {
		this.creator = creator;
	}

	public Integer getOrgan_order() {
		return organ_order;
	}

	public void setOrgan_order(Integer organ_order) {
		this.organ_order = organ_order;
	}

	public Integer getPkid() {
		return pkid;
	}

	public void setPkid(Integer pkid) {
		this.pkid = pkid;
	}

}
