package com.krm.ps.model.reportdefine.services;

import com.krm.ps.model.vo.CurrencyInfo;

public interface CurrencyManagementService {

	public CurrencyInfo queryCurrencyInfoByCode(String code);

}