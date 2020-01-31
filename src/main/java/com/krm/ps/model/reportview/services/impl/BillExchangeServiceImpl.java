package com.krm.ps.model.reportview.services.impl;

import java.util.Iterator;
import java.util.List;

import com.krm.ps.model.reportview.dao.BillExchangeDAO;
import com.krm.ps.model.reportview.services.BillExchangeService;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;

public class BillExchangeServiceImpl implements BillExchangeService {
	BillExchangeDAO billExchangeDAO;

	public BillExchangeDAO getBillExchangeDAO() {
		return billExchangeDAO;
	}

	public void setBillExchangeDAO(BillExchangeDAO billExchangeDAO) {
		this.billExchangeDAO = billExchangeDAO;
	}

	public void updateBaseData(String organId, String date) {
		billExchangeDAO.updateBaseData(organId, date);

	}

	public void updateBaseData(String date, List organList) {
		Iterator itr = organList.iterator();
		while (itr.hasNext()) {
			OrganInfo oiObj = (OrganInfo) itr.next();
			billExchangeDAO.updateBaseData(oiObj.getCode(), date);
		}

	}

}
