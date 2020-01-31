package com.krm.ps.model.flexiblequery.services.impl;

import com.krm.ps.model.flexiblequery.dao.OrganInfoDAO;
import com.krm.ps.model.flexiblequery.services.OrganService;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;

public class OrganServiceImpl implements OrganService {
	private OrganInfoDAO flexiblequeryOrganInfoDAO;

	public OrganInfoDAO getFlexiblequeryOrganInfoDAO() {
		return flexiblequeryOrganInfoDAO;
	}

	public void setFlexiblequeryOrganInfoDAO(
			OrganInfoDAO flexiblequeryOrganInfoDAO) {
		this.flexiblequeryOrganInfoDAO = flexiblequeryOrganInfoDAO;
	}

	public OrganInfo getOrganByCode(String code) {
		return flexiblequeryOrganInfoDAO.getOrganByCode(code);
	}
}
