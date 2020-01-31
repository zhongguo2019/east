package com.krm.ps.model.east1104dz.service.impl;

import java.util.List;

import com.krm.ps.model.east1104dz.dao.QueryDzDao;
import com.krm.ps.model.east1104dz.service.QueryDzService;

public class QueryDzServiceImpl implements QueryDzService {

	private QueryDzDao queryDzDao;

	public QueryDzDao getQueryDzDao() {
		return queryDzDao;
	}

	public void setQueryDzDao(QueryDzDao queryDzDao) {
		this.queryDzDao = queryDzDao;
	}

	public List getReports(String date, Long userid) {
		return queryDzDao.getReports(date, userid);
	}

	@Override
	public List getQuerydz(String date, String organid,int idx) {
		return queryDzDao.getQuerydz(date, organid,idx);
	}

}
