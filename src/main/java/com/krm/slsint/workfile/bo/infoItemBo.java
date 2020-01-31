package com.krm.slsint.workfile.bo;

public class infoItemBo {
	private String pkId;
	private String fpkId;
	private String isRead;
	private String isAttachment;
	private String addresser;
	private String title;
	private String date;
	private String isReply;
	private String itemtype;
	private String unlock;
	public String getUnlock() {
		return unlock;
	}
	public void setUnlock(String unlock) {
		this.unlock = unlock;
	}
	public String getAddresser() {
		return addresser;
	}
	public void setAddresser(String addresser) {
		this.addresser = addresser;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getIsAttachment() {
		return isAttachment;
	}
	public void setIsAttachment(String isAttachment) {
		this.isAttachment = isAttachment;
	}
	public String getIsRead() {
		return isRead;
	}
	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFpkId() {
		return fpkId;
	}
	public void setFpkId(String fpkId) {
		this.fpkId = fpkId;
	}
	public String getPkId() {
		return pkId;
	}
	public void setPkId(String pkId) {
		this.pkId = pkId;
	}
	public String getIsReply() {
		return isReply;
	}
	public void setIsReply(String isReply) {
		this.isReply = isReply;
	}
	public String getItemtype() {
		return itemtype;
	}
	public void setItemtype(String itemtype) {
		this.itemtype = itemtype;
	}

}
