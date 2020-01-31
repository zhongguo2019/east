package com.krm.ps.sysmanage.organmanage.services;

import com.krm.ps.framework.common.sort.service.SortService;
import com.krm.ps.sysmanage.organmanage.dao.OrganInfoDAO;

public interface OrganSortService extends SortService{
	public void setOrganInfoDAO(OrganInfoDAO dao);
}
