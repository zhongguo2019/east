package com.krm.ps.sysmanage.organmanage.services.impl;

import com.krm.ps.sysmanage.organmanage.dao.UnitDAO;
import com.krm.ps.sysmanage.organmanage.services.UnitSortService;
import com.krm.ps.sysmanage.usermanage.vo.Units;

public class UnitSortServiceImpl implements UnitSortService{
	
	private UnitDAO dao;

	public void setUnitDAO(UnitDAO dao) {
		this.dao = dao;
	}

	public void sort(String list) {
		if(null!=list){
			Units units = null;
			String[] orders = list.split(",");
			for(int i=0;i<orders.length;i++){
				Object o = dao.getObject(Units.class,new Long(orders[i]));
				if(null!=o){
					units = (Units)o;					
					units.setDisplay_order(new Integer(i));
					dao.saveObject(units);
				}
			}
		}
	}

}
