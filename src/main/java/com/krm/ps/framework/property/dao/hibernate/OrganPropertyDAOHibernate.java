package com.krm.ps.framework.property.dao.hibernate;

import java.sql.Connection;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.jdbc.support.JdbcUtils;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.framework.property.dao.OrganPropertyDAO;
import com.krm.ps.framework.property.dao.PropertyDAO;
import com.krm.ps.framework.property.vo.OrganProperty;
import com.krm.ps.framework.property.vo.Prop;

public class OrganPropertyDAOHibernate extends BaseDAOHibernate implements OrganPropertyDAO{

	/* (non-Javadoc)
	 * @see com.krm.slsint.property.dao.OrganPropertyDAO#addOrganProperty(com.krm.slsint.property.vo.OrganProperty)
	 */
	public void addOrganProperty(OrganProperty op) {
		getHibernateTemplate().persist(op);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.property.dao.OrganPropertyDAO#deleteOrganProperty(com.krm.slsint.property.vo.OrganProperty)
	 */
	public void deleteOrganProperty(OrganProperty op) {
		getHibernateTemplate().delete(op);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.property.dao.OrganPropertyDAO#getOp(java.lang.String)
	 */
	public List getOp(String organCode) {
//		String sql = "SELECT * FROM code_property p LEFT OUTER JOIN(SELECT organ_code, pro_id AS opProId, [value] FROM code_organ_property op WHERE (organ_code = '"+organCode+"')) AS t ON code_property.pro_id = t.opProId";
//		return null;
		String sql = "SELECT p.pro_id AS proId, p.pro_name AS proName, p.pro_value AS proValue,p.pro_comment AS proComment,organCode,opProId,opValue FROM code_property p LEFT OUTER JOIN(SELECT organ_code AS organCode, pro_id AS opProId, op.value AS opValue FROM code_organ_property op WHERE (organ_code = '"+organCode+"')) AS t ON p.pro_id = t.opProId where p.status='1'";
//		Object[][] entities = {{"p",Prop.class},{"op",OrganProperty.class}};
		Object[][] scalaries = {
				{"proId",Hibernate.LONG},
				{"proName",Hibernate.STRING},
				{"proValue",Hibernate.STRING},
				{"proComment",Hibernate.STRING},
				{"organCode",Hibernate.STRING},
				{"opProId",Hibernate.LONG},
				{"opValue",Hibernate.STRING}
		};
		return list(sql, null, scalaries, null);
	}
	
	/* (non-Javadoc)
	 * @see com.krm.slsint.property.dao.OrganPropertyDAO#getOrganProperty(java.lang.String, java.lang.Long)
	 */
	public OrganProperty getOrganProperty(String organCode, Long proId) {
		String hql = "FROM OrganProperty op where op.organCode='"+organCode+"' and op.proId="+proId;
		return (OrganProperty) uniqueResult(hql);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.property.dao.OrganPropertyDAO#getList(java.lang.Long)
	 */
	public List getList(Long proId) {
		String hql = "FROM OrganProperty op where op.proId="+proId;
		return list(hql);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.property.dao.OrganPropertyDAO#updateOrganProperty(com.krm.slsint.property.vo.OrganProperty)
	 */
	public void updateOrganProperty(OrganProperty op) {
		getHibernateTemplate().update(op);
	}

}
