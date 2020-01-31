package com.krm.ps.model.reportview.services;


public interface DataCarryService {

	/**
	 * 判断是否该报表是否需要结转
	 * @param reportId 报表编号
	 * @return true 需要结转，否则不需要
	 */
	public boolean needCarry(Long reportId);
	
}


