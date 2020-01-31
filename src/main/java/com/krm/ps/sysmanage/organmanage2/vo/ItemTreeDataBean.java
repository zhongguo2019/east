package com.krm.ps.sysmanage.organmanage2.vo;

import com.krm.ps.sysmanage.organmanage2.util.DisBean;
 
  
/**
 * 此实体继承DisBean 增加了机构与金额属性,
 * 增加了表项查询的报表科目和报表列
 * 
 * @author wx
 * 
 */
public class ItemTreeDataBean extends DisBean {

	private String organId;
	private String money;

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getOrganId() {
		return organId;
	}

	public void setOrganId(String organId) {
		this.organId = organId;
	}

}
