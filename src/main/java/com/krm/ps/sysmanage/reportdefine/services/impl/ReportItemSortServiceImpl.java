package com.krm.ps.sysmanage.reportdefine.services.impl;

import com.krm.ps.sysmanage.reportdefine.dao.ReportDefineDAO;
import com.krm.ps.sysmanage.reportdefine.services.ReportItemSortService;
import com.krm.ps.sysmanage.reportdefine.vo.ReportItem;

public class ReportItemSortServiceImpl implements ReportItemSortService {
	
	private ReportDefineDAO dao;
	
	public void setReportDefineDAO(ReportDefineDAO dao) {
		this.dao = dao;
	}
	
	public void sort(String list){
		if(null!=list){
			ReportItem item = null;
			String[] orders = list.split(",");
			for(int i=0;i<orders.length;i++){
				Object o = dao.getObject(ReportItem.class,new Long(orders[i]));
				if(null!=o){
					item = (ReportItem)o;
					item.setItemOrder(new Integer(i));
					dao.saveObject(item);
				}
			}
		}
	}

}
