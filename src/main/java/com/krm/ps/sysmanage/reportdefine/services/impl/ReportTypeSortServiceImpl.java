package com.krm.ps.sysmanage.reportdefine.services.impl;

import com.krm.ps.sysmanage.reportdefine.dao.ReportDefineDAO;
import com.krm.ps.sysmanage.reportdefine.services.ReportTypeSortService;
import com.krm.ps.sysmanage.reportdefine.vo.ReportItem;
import com.krm.ps.sysmanage.reportdefine.vo.ReportType;

public class ReportTypeSortServiceImpl implements ReportTypeSortService{

private ReportDefineDAO dao;
	
	public void setReportDefineDAO(ReportDefineDAO dao) {
		this.dao = dao;
	}

	public void sort(String list){
		
		if(null!=list){
			ReportType type = null;
			String[] orders = list.split(",");
			for(int i=0;i<orders.length;i++){
				Object o = dao.getObject(ReportType.class,new Long(orders[i]));
				if(null!=o){
					type = (ReportType)o;
					type.setShowOrder(new Long(i));
					dao.saveObject(type);
				}
			}
		}
	}
}
