package com.krm.ps.framework.property.dao.hibernate;

import java.util.List;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.framework.property.dao.PropertyDAO;
import com.krm.ps.framework.property.vo.Prop;

public class PropertyDAOHibernate extends BaseDAOHibernate implements PropertyDAO{

	/* (non-Javadoc)
	 * @see com.krm.slsint.property.dao.PropertyDAO#getAllProperty()
	 */
	public List getAllProperty() {
		String hql = "FROM Prop p where p.status='1'";
		return list(hql);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.property.dao.PropertyDAO#getProperty(java.lang.Long)
	 */
	public Prop getProperty(Long id) {
		String hql = "FROM Prop p where p.proId='"+id+"'";
		return (Prop) list(hql).get(0);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.property.dao.PropertyDAO#addProperty(com.krm.slsint.property.vo.Prop)
	 */
	public void addProperty(Prop property) {
		getHibernateTemplate().persist(property);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.property.dao.PropertyDAO#updateProperty(com.krm.slsint.property.vo.Prop)
	 */
	public void updateProperty(Prop property) {
		getHibernateTemplate().update(property);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.property.dao.PropertyDAO#deleteProperty(java.lang.Long)
	 */
	public void deleteProperty(Long id) {
		Prop property = getProperty(id);  
		property.setStatus('9');
		updateProperty(property); 
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.property.dao.PropertyDAO#batchdelete(java.lang.String)
	 */
	public void batchdelete(String ids) {
		String[] idss = ids.split(",");
		for (int i = 0; i < idss.length; i++) {
			long l =  Long.parseLong(idss[i]);
			System.out.println("要删除"+l);
			deleteProperty(new Long(l));
		}
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.property.dao.PropertyDAO#getProNameList()
	 */
	public List getProNameList() {
		String hql = "SELECT p.proName FROM Prop as p where status='1'";
		return list(hql);
	}

	public Prop getProperty(String proName) {
		String hql = "FROM Prop p where p.proName='"+proName+"'";
		return (Prop)uniqueResult(hql);
	}
	
	//用jdbc读取数据库中property的信息
	/* (non-Javadoc)
	 * @see com.krm.slsint.property.dao.PropertyDAO#getProValue(java.lang.String)
	 */
	public String getProValue(String proName) {
		String hql = "SELECT p.proValue FROM Prop as p where p.proName='"+proName+"'";
		return (String)uniqueResult(hql);
	}
	
	/* (non-Javadoc)
	 * @see com.krm.slsint.property.dao.PropertyDAO#getProValue(java.lang.String, java.lang.String)
	 */
	public String getProValue(String proName, String defaultValue) {
		String proValue = getProValue(proName); 
		if(proValue==null){
			return defaultValue;
		}else{
			return proValue;
		}
	}
	
}
