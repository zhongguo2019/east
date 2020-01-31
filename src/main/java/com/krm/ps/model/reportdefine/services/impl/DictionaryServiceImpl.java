package com.krm.ps.model.reportdefine.services.impl;

import java.util.List;

import com.krm.ps.model.reportdefine.dao.DictionaryDAO;
import com.krm.ps.model.reportdefine.services.DictionaryService;
import com.krm.ps.sysmanage.usermanage.vo.Dictionary;

public class DictionaryServiceImpl implements DictionaryService {
	private DictionaryDAO rddictionaryDAO;

	public DictionaryDAO getRddictionaryDAO() {
		return rddictionaryDAO;
	}

	public void setRddictionaryDAO(DictionaryDAO rddictionaryDAO) {
		this.rddictionaryDAO = rddictionaryDAO;
	}

	@Override
	public List getDics(int type) {
		return rddictionaryDAO.getDics(type);
	}

	@Override
	public List getALLsystem() {
		return rddictionaryDAO.getALLsystem();
	}

	@Override
	public List getReportfrequency() {
		return rddictionaryDAO.getDics(1004);
	}

	@Override
	public List getOrgansort() {
		return rddictionaryDAO.getDics(1001);
	}

	public List getUnitcode() {
		return rddictionaryDAO.getUnits();
	}

	public Dictionary getDictionary(int type, int dicid) {
		return rddictionaryDAO.getDictionary(type, dicid);
	}

}
