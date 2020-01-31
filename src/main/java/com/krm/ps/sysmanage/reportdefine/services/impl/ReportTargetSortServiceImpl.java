package com.krm.ps.sysmanage.reportdefine.services.impl;

import com.krm.ps.sysmanage.reportdefine.dao.ReportDefineDAO;
import com.krm.ps.sysmanage.reportdefine.services.ReportItemSortService;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;

public class ReportTargetSortServiceImpl implements ReportItemSortService {
	
	private ReportDefineDAO dao;
	
	public void setReportDefineDAO(ReportDefineDAO dao) {
		this.dao = dao;
	}
	
	public void sort(String list){
		if(null!=list){
			ReportTarget target = null;
			String[] orders = list.split(",");
			for(int i=0;i<orders.length;i++){
				System.out.print(orders[i]+",");
				Object o = dao.getObject(ReportTarget.class,new Long(orders[i]));
				if(null!=o){
					target = (ReportTarget)o;
					target.setTargetOrder(new Integer(i));
					dao.saveObject(target);
				}
			}
		}
	}
}
