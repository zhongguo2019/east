package com.krm.ps.model.reportdefine.services.impl;

import com.krm.ps.model.reportdefine.dao.CurrencyManagementDAO;
import com.krm.ps.model.reportdefine.services.CurrencyManagementService;
import com.krm.ps.model.vo.CurrencyInfo;

public class CurrencyManagementServiceImpl implements CurrencyManagementService {

	private CurrencyManagementDAO currencyManagementDAO;

	public CurrencyManagementDAO getCurrencyManagementDAO() {
		return currencyManagementDAO;
	}

	public void setCurrencyManagementDAO(
			CurrencyManagementDAO currencyManagementDAO) {
		this.currencyManagementDAO = currencyManagementDAO;
	}

	public CurrencyInfo queryCurrencyInfoByCode(String code) {
		return currencyManagementDAO.queryCurrencyInfoByCode(code);
	}
}
