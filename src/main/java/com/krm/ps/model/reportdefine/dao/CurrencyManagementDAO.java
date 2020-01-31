package com.krm.ps.model.reportdefine.dao;

import com.krm.ps.model.vo.CurrencyInfo;

public interface CurrencyManagementDAO {

	public CurrencyInfo queryCurrencyInfoByCode(String code);

}
