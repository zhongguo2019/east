package com.krm.ps.model.funconfig.dao.hibernate;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.model.funconfig.dao.FunconfigDao;

public class FunconfigDaoHibernate extends BaseDAOHibernate implements FunconfigDao{

	@Override
	public List selectfunconfig() {
		String sql="select t.FUN_KEY,t.FUN_VALUE,t.FUN_DES,t.FUN_TYPE,t.FUN_EXT1  from FUN_CONFIG t";
		//                   0          1            2        3           4  
		return list(sql, null, new Object[][] {
				{ "FUN_KEY",Hibernate.STRING },
				{ "FUN_VALUE",Hibernate.STRING },
				{ "FUN_DES",Hibernate.STRING },
				//{ "status",Hibernate.INTEGER },
				//{ "px",Hibernate.INTEGER },
				{ "FUN_TYPE",Hibernate.INTEGER },
				{ "FUN_EXT1",Hibernate.STRING },
				//{ "layer",Hibernate.INTEGER },
				});
	}

	@Override
	public void savefunconfig(String funkey, String funvalue, String fundes,
			int funtype, String ext1) {
		String sql="insert into FUN_CONFIG values('"+funkey+"','"+funvalue+"','"+fundes+"',"+funtype+",'"+ext1+"')";
		int a=jdbcUpdate(sql, null);
	}

	@Override
	public void updatefunconfig(String funkey, String funvalue, String fundes,
			int funtype, String ext1) {
		String sql="update FUN_CONFIG set FUN_KEY='"+funkey+"',FUN_VALUE='"+funvalue+"',FUN_DES='"+fundes+"',FUN_TYPE="+funtype+",FUN_EXT1='"+ext1+"' where FUN_KEY='"+funkey+"'";
		System.out.println("sql="+sql);
		int a=jdbcUpdate(sql, null);
	}

	@Override
	public void deletefunconfig(String funkey) {
		String sql="delete FUN_CONFIG  where FUN_KEY='"+funkey+"'";
		int a=jdbcUpdate(sql, null);
	}

	@Override
	public List selectkey() {
		String sql="select FUN_KEY, '' as aa from FUN_CONFIG ";
		
		 return list(sql, null, new Object[][] {
				{ "FUN_KEY",Hibernate.STRING },
				
				{ "aa",Hibernate.INTEGER },
				});
	}

	@Override
	public List selectfunconfig1(String funkey) {
		String sql="select t.FUN_KEY,t.FUN_VALUE,t.FUN_DES,t.FUN_TYPE,t.FUN_EXT1  from FUN_CONFIG t where t.FUN_KEY='"+funkey+"'";
		return list(sql, null, new Object[][] {
				{ "FUN_KEY",Hibernate.STRING },
				{ "FUN_VALUE",Hibernate.STRING },
				{ "FUN_DES",Hibernate.STRING },
				//{ "status",Hibernate.INTEGER },
				//{ "px",Hibernate.INTEGER },
				{ "FUN_TYPE",Hibernate.INTEGER },
				{ "FUN_EXT1",Hibernate.STRING },
				//{ "layer",Hibernate.INTEGER },
				});
	}

	@Override
	public String selectvalue(String funkey) {
		String sql="select t.FUN_VALUE,t.FUN_DES  from FUN_CONFIG t where t.FUN_KEY='"+funkey+"'";
		List list=list(sql, null, new Object[][] {			
				{ "FUN_VALUE",Hibernate.STRING },
				{ "FUN_DES",Hibernate.STRING },			
				});
		String value="";
		Iterator it=list.iterator();
		while(it.hasNext()){
			Object[]o=(Object[])it.next();
			value=o[0].toString();
			
		}
		return value;
	}	

	
}
