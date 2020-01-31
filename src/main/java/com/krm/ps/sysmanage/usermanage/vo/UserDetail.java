package com.krm.ps.sysmanage.usermanage.vo;

/**
 * 
 * @author LC
 * @Table UMG_USER_DETAIL
 *
 */
public class UserDetail {

	private Long udPkid;
	private Long userId;//用户ID
	private String name;//用户名
	private String idCard;//身份证号
	private String phone;//联系电话
	private String organId;//机构号
	private String tellerId;//柜员号
	
	
	
	public Long getUdPkid() {
		return udPkid;
	}
	public void setUdPkid(Long udPkid) {
		this.udPkid = udPkid;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getOrganId() {
		return organId;
	}
	public void setOrganId(String organId) {
		this.organId = organId;
	}
	public String getTellerId() {
		return tellerId;
	}
	public void setTellerId(String tellerId) {
		this.tellerId = tellerId;
	}
	
}
