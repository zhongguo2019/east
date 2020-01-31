package com.krm.ps.framework.common.sort.web.form;

import com.krm.ps.framework.common.web.form.BaseForm;

/**
 * 
 * @struts.form name="sortForm"
 */
public class SortForm extends BaseForm implements java.io.Serializable
{
	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = 88899443232L;

	private String orderList;
	
	private Integer orderFlag;
	
	private String serviceName;
	
	private String forwardURL;
	
	public SortForm(){
		
	}

	public Integer getOrderFlag() {
		return orderFlag;
	}

	public void setOrderFlag(Integer orderFlag) {
		this.orderFlag = orderFlag;
	}

	public String getOrderList() {
		return orderList;
	}

	public void setOrderList(String orderList) {
		this.orderList = orderList;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getForwardURL() {
		return forwardURL;
	}

	public void setForwardURL(String forwardURL) {
		this.forwardURL = forwardURL;
	}
	
}
