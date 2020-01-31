package com.krm.ps.sysmanage.groupmanage.service.impl;

import java.util.Iterator;
import java.util.List;

import com.krm.ps.sysmanage.groupmanage.service.GroupSortService;
import com.krm.ps.sysmanage.usermanage.dao.DictionaryDAO;
import com.krm.ps.sysmanage.usermanage.vo.Dictionary;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author
 */
public class GroupSortServiceImpl implements GroupSortService {

	/**
	 * 
	 */
	public GroupSortServiceImpl() {
		super();
	}

	private DictionaryDAO ddao;
	
	public void setDictionaryDAO(DictionaryDAO ddao){
		this.ddao = ddao;
	}
	
	/**
	 * 给分组排序
	 * @param list
	 */
	public void sort(String list) {
		if(null!=list){
			Dictionary dics = null;
			String[] orders = list.split(",");
			for(int i=0;i<orders.length;i++){
				Object o = ddao.getObject(Dictionary.class,new Long(orders[i]));
				if(null!=o){
					dics = (Dictionary)o;					
					dics.setDisporder(new Long(i));
					ddao.saveObject(dics);
				}
			}
		}
	}
	
	/**
	 * 内部排序
	 * @param groups
	 */
	public void sortInner(List groups){
		Iterator itr = groups.iterator();
		int i = 0;
		while(itr.hasNext()){
			Dictionary dics = (Dictionary)itr.next();
			dics.setDisporder(new Long(i++));
			ddao.saveObject(dics);
		}
		
		
	}
}
