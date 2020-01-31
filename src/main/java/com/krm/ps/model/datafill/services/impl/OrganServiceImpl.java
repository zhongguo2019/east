package com.krm.ps.model.datafill.services.impl;

import com.krm.ps.model.datafill.dao.OrganInfoDAO;
import com.krm.ps.model.datafill.services.OrganService;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;

public class OrganServiceImpl implements OrganService {
	private OrganInfoDAO datafillOrganInfoDAO;

	public OrganInfoDAO getDatafillOrganInfoDAO() {
		return datafillOrganInfoDAO;
	}

	public void setDatafillOrganInfoDAO(OrganInfoDAO datafillOrganInfoDAO) {
		this.datafillOrganInfoDAO = datafillOrganInfoDAO;
	}

	public OrganInfo getOrganByCode(String code) {
		return datafillOrganInfoDAO.getOrganByCode(code);
	}

}
