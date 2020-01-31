package com.krm.ps.model.reportview.services;

import java.util.List;

public interface BillExchangeService {
	
	/**
	 * 更新(一个机构的)基期数据的报表日期
	 * 为了能在每一期报表中都看到基期数据，
	 * 需要将基期数据的报表日期更新为当前报表日期。
	 * @param organId 填报机构id
	 * @param date 报表日期
	 */
	public void updateBaseData(String organId, String date);
	
	/**
	 * 更新(一批机构的)基期数据的报表日期
	 * 
	 * @param date 报表日期
	 * @param organList 填报机构id列表
	 */
	public void updateBaseData(String date, List organList);
}
