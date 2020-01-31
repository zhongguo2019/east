package com.krm.ps.model.datafill.dao;

import com.krm.ps.framework.dao.DAO;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;

public interface OrganInfoDAO extends DAO {

	public OrganInfo getOrganByCode(String code);

}
