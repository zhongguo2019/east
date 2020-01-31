package com.krm.ps.framework.common.services;

import java.util.List;

import com.krm.ps.framework.common.vo.CommonQueryParams;


public interface CommonQueryService {
	
	public List commonQuery(String sql, Object[][] entities, Object[][] scalaries, Object[] values);
	
	public List commonQuery(String sqlId, String params, Class sqlManagerClass);

	/**
	 * Common query.
	 * 
	 *  为了统一事务的需要，需要同时传入多个{@link CommonQueryParams}对象完成对应功能
	 *
	 * @param sqlParamList the sql param list
	 * @return the list
	 */
	public List commonQuery(List sqlParamList);
}
