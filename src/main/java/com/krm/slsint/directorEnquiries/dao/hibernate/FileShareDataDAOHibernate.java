package com.krm.slsint.directorEnquiries.dao.hibernate;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.slsint.directorEnquiries.dao.FileShareDataDAO;

public class FileShareDataDAOHibernate extends BaseDAOHibernate implements
		FileShareDataDAO {

	public Long getFileShareDataMaxOrder() {
		Object[][] scalaries = { { "showOrder", Hibernate.LONG } };
		String sql = "select MAX(show_order) as showOrder from umg_file_share ";
		List list = list(sql, null, scalaries);
		Iterator it = list.iterator();
		Long order = (Long) it.next();
		if(order == null){
			return new Long(0);
		}else{
			return order;
		}
	}

}
