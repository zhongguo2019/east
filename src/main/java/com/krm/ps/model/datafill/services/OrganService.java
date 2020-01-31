package com.krm.ps.model.datafill.services;

import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;

public interface OrganService {

	public OrganInfo getOrganByCode(String code);
}
