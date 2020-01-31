package com.krm.ps.sysmanage.organmanage.dao.hibernate;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.sysmanage.organmanage.dao.UnitDAO;
import com.krm.ps.sysmanage.usermanage.vo.Units;
/*
 * 添加日期:2006年9月12日
 * 修改人:赵鹏程
 */
public class UnitDAOHibernate extends BaseDAOHibernate implements UnitDAO{

	/* (non-Javadoc)
	 * @see com.krm.slsint.organmanage.dao.UnitDAO#queryMaxDisplayOrder()
	 */
	public Integer queryMaxDisplayOrder() {
		Object[][] scalaries = { { "showOrder", Hibernate.INTEGER } };
		String sql = "select MAX(display_order) as showOrder from code_unit_units";
		List list = list(sql, null, scalaries);
		Iterator it = list.iterator();
		Integer order = (Integer) it.next();
		if(order == null){
			return new Integer(0);
		}else{
			return order;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.krm.slsint.organmanage.dao.UnitDAO#updateUnit(com.krm.slsint.usermanage.vo.Units)
	 */
	public void updateUnit(Units unit) {
		String sql = "update code_unit_units "+
        " set code = ? , name = ? , modulus = ? where pkid = ? ";
		Object [] scalaries = new Object[]{unit.getCode(),unit.getName(),unit.getModulus(),unit.getPkid()};
		this.jdbcUpdate(sql,scalaries);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.organmanage.dao.UnitDAO#delUnit(java.lang.Long)
	 */
	public void delUnit(Long pkid) {
		String sql = "delete from code_unit_units where pkid = ?";
		Object [] scalaries = new Object[]{pkid};
		this.jdbcUpdate(sql,scalaries);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.organmanage.dao.UnitDAO#queryUnit()
	 */
	public List queryUnit() {
		String sql = "select {c.*} from code_unit_units c order by display_order";
		List list = list(sql,new Object[][]{{"c", Units.class}},null);		
		return list;		
	}

}
