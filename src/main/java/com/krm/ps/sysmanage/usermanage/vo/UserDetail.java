package com.krm.ps.sysmanage.usermanage.vo;

/**
 * 
 * @author LC
 * @Table UMG_USER_DETAIL
 *
 */
public class UserDetail {

	private Long udPkid;
	private Long userId;//�û�ID
	private String name;//�û���
	private String idCard;//���֤��
	private String phone;//��ϵ�绰
	private String organId;//������
	private String tellerId;//��Ա��
	
	
	
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
