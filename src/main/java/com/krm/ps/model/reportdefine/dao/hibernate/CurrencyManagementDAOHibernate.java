package com.krm.ps.model.reportdefine.dao.hibernate;

import java.util.List;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.model.reportdefine.dao.CurrencyManagementDAO;
import com.krm.ps.model.vo.CurrencyInfo;

public class CurrencyManagementDAOHibernate extends BaseDAOHibernate implements
		CurrencyManagementDAO {

	@Override
	public CurrencyInfo queryCurrencyInfoByCode(String code) {
		String sql = "SELECT {t.*} FROM code_rep_currency t WHERE t.code="
				+ code;
		List result = list(sql, new Object[][] { { "t", CurrencyInfo.class } },
				null);
		CurrencyInfo currencyInfo = (CurrencyInfo) result.get(0);
		return currencyInfo;
	}

}
