package com.krm.ps.sysmanage.organmanage.services;

import com.krm.ps.framework.common.sort.service.SortService;

import com.krm.ps.sysmanage.organmanage.dao.UnitDAO;

public interface UnitSortService extends SortService{
	public void setUnitDAO(UnitDAO dao);
}
