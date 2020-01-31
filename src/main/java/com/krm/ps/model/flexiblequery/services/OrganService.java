package com.krm.ps.model.flexiblequery.services;

import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;

public interface OrganService {

	/**
	 * 根据机构编码得到该机构对象
	 * 
	 * @param code
	 *            机构编码
	 * @return OrganInfo
	 */
	public OrganInfo getOrganByCode(String code);

}
