package com.krm.ps.sysmanage.organmanage.services.impl;

import com.krm.ps.sysmanage.organmanage.dao.OrganInfoDAO;
import com.krm.ps.sysmanage.organmanage.services.OrganSortService;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;

public class OrganSortServiceImpl implements OrganSortService {

	private OrganInfoDAO dao;
	
	public void setOrganInfoDAO(OrganInfoDAO dao) {
	       this.dao = dao;
	}

	public void sort(String list){
		if(null!=list){
			OrganInfo organ = null;
			String[] orders = list.split(",");
			for(int i=0;i<orders.length;i++){
				Object o = dao.getObject(OrganInfo.class,new Long(orders[i]));
				if(null!=o){
					organ = (OrganInfo)o;
					organ.setOrganOrder(new Integer(i));
					dao.saveObject(organ);
				}
			}
		}
	}

}
