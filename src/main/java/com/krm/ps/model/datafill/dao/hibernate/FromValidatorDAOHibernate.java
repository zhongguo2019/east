package com.krm.ps.model.datafill.dao.hibernate;

import java.util.List;

import org.hibernate.Hibernate;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.model.datafill.dao.FromValidatorDAO;
import com.krm.ps.model.vo.FromValidator;


public class FromValidatorDAOHibernate extends BaseDAOHibernate implements FromValidatorDAO {

	public boolean saveFromValidator(FromValidator fv) {
		try{
			  this.saveObject(fv);
			   return true;
		      } catch (RuntimeException re) {
	          log.error("save failed", re);
	          return false;
	        }
	}

	public int deleteFromValidator(Long pkid) {
		String sql ="delete from code_from_validator t where t.pkid="+pkid ; 
		int a=0;
		if(sql!=null){
			int result=jdbcUpdate(sql, null);
				if(result>0){
					a=1;
				}
		}
		return a;
	}

	
	public List listFromValidator(Long pkid ,String roportId ,String targetfield) {
		StringBuffer hql = new StringBuffer("from FromValidator f  where 1=1");
			if(pkid!=null){
				hql.append(" and f.pkid=");
				hql.append(pkid);
			}
			if(roportId!=null){
				hql.append(" and f.report_id='");
				hql.append(roportId);
			}
			if(targetfield!=null){
				hql.append("' and f.target_field='");
				hql.append(targetfield);
			}
		    hql.append("' order by  target_field , pkid desc");
	
	    return this.list(hql.toString());
	}

	@Override
	public List listFromValidator(String roportId) {
		StringBuffer hql = new StringBuffer("from FromValidator f  where 1=1");
		if(roportId!=null){
			hql.append(" and f.report_id=");
			hql.append(roportId);
		}
	    hql.append(" order by target_field ,pkid desc");

    return this.list(hql.toString());
	}

	 
	public Long getFvPkid() {
		String sql="select max(pkid) as result from code_from_validator";
		List resultL= list(sql,null,new Object[][]{{"result",Hibernate.LONG}});
		if(resultL.size()==0){
			return new Long(0);
		}
		return new Long(resultL.get(0).toString());
	}

	@Override
	public void updateFromValidator(FromValidator fv,List pkidstr) {
		if(pkidstr!=null){
			for (int i = 0; i < pkidstr.size(); i++) {
				fv.setPkid(new Long(pkidstr.get(i).toString())); 
				getHibernateTemplate().update(fv);
			}
		}else{
				getHibernateTemplate().update(fv);
		}
	}

	
	
	
}


