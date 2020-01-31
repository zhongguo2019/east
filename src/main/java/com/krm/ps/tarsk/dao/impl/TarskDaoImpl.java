package com.krm.ps.tarsk.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.framework.dao.jdbc.BaseDAOJdbc;
import com.krm.ps.tarsk.dao.TarskDao;
import com.krm.ps.tarsk.vo.SubTarskInfo;
import com.krm.ps.tarsk.vo.Tarsk;

public class TarskDaoImpl extends BaseDAOHibernate implements TarskDao {

	
	protected BaseDAOJdbc jdbc;

	public void setDAOJdbc(BaseDAOJdbc jdbc) {
		this.jdbc = jdbc;
	}
	
	@Override
	public Tarsk tarskInit(Tarsk t) {
		getHibernateTemplate().saveOrUpdate(t);
		return t;
	}

	@Override
	public void tarskStart(Tarsk t) {
		getHibernateTemplate().saveOrUpdate(t);
		
	}

	@Override
	public void tarskEnd(Tarsk t) {
		getHibernateTemplate().saveOrUpdate(t);
		
	}

	@Override
	public void updateTarsk(Tarsk t) {
    getHibernateTemplate().update(t);		
	}

	@Override
	public List<Tarsk> getTarsks(Tarsk t) {
		String hql=" from Tarsk t where 1=1 ";
		if(null!=t.gettId()){
			hql+="	and t.tId="+t.gettId();
		}
		if(!StringUtils.isEmpty(t.getCreateTime())){
			hql+=" and t.createTime='"+t.getCreateTime()+"'";
		}
		if(!StringUtils.isEmpty(t.getEndTime())){
			hql+=" and t.endTime='"+t.getEndTime()+"'";
		}
		if(!StringUtils.isEmpty(t.getStatus())){
			hql+=" and t.startTime='"+t.getStartTime()+"'";
		}
		hql+=" order by t.tId" ;
		return list(hql);
	}

	@Override
	public SubTarskInfo subTarskInit(SubTarskInfo stk) {
		  if(stk.getPkid() == null){	  
			  String seq = " select  sub_tarsk_info_seq.nextval as pkids from dual ";
			  List list = list(seq, null, new Object[][] { { "pkids", Hibernate.STRING } });
			  String sd = (String)list.get(0);
			  stk.setPkid(new Long(sd));
		  }
		    String sql ="insert into sub_tarsk_info (TID, create_time, start_time, status, type , en_reportId, en_organId, en_dataDate, t_count, pkid)" +
		    		" values ( "+stk.gettId()+", '"+stk.getCreateTime()+"',  '"+stk.getStartTime()+"',  '"+stk.getStatus()+"',  '"+stk.getType()+"',  '"+stk.getReportId()+"',  '"+stk.getOrganId()+"',  '"+stk.getDataDate()+"',  "+stk.gettCount()+" , "+stk.getPkid()+" )";
		  
		     executeUpdateAutoCommit(sql);
		
		//getHibernateTemplate().saveOrUpdate(stk);
		return stk;
	}

	
	@Override
	public void updateSubTarsk(SubTarskInfo stk) {
		String sql = "update sub_tarsk_info set STATUS='"+stk.getStatus()+"' ,END_TIME='"+stk.getEndTime()+"' ,FILE_PATH='"+stk.getFilePath()+"',MESSAGE='"+stk.getMessage()+"' where PKID="+stk.getPkid();
		executeUpdateAutoCommit(sql);
        //getHibernateTemplate().saveOrUpdate(stk);
	}


	private void executeUpdateAutoCommit(String sql){
		Connection c = null;
		Statement s = null;
	   try{
		   
		    c = jdbc.getDataSource().getConnection();
			c.setAutoCommit(false);

			s = c.createStatement();
		    s.executeUpdate(sql);		
		    
			s.close();
			c.commit();
			c.setAutoCommit(true);	
	   }catch (Exception e) {
			try {
				c.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				if (c != null && !c.isClosed()) {
					c.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public List<SubTarskInfo> getSubTarsks(SubTarskInfo stk) {
		String hql=" from SubTarskInfo stk where 1=1 ";
		if(null!=stk.getPkid()){
			hql+=" and stk.pkid="+stk.getPkid();
		}
		if(!StringUtils.isEmpty(stk.getCreateTime())){
			hql+=" and stk.createTime='"+stk.getCreateTime()+"'" ;
		}
		if(!StringUtils.isEmpty(stk.getEndTime())){
			hql+=" and stk.endTime='"+stk.getEndTime()+"'" ;
		}
		if(!StringUtils.isEmpty(stk.getStartTime())){
			hql+=" and stk.startTime='"+stk.getStartTime()+"'" ;
		}
		if(!StringUtils.isEmpty(stk.getStatus())){
			hql+=" and stk.status='"+stk.getStatus()+"'" ;
		}
		if(!StringUtils.isEmpty(stk.getReportId())){
			hql+=" and stk.reportId='"+stk.getReportId()+"'" ;
		}
		if(!StringUtils.isEmpty(stk.getOrganId())){
			hql+=" and stk.organId='"+stk.getOrganId()+"'" ;
		}
		if(!StringUtils.isEmpty(stk.getDataDate())){
			hql+=" and stk.dataDate='"+stk.getDataDate()+"'" ;
		}
		/*if(0!=stk.gettCount()){
			hql+=" and stk.tCount="+stk.gettCount();
		}*/
		if(!StringUtils.isEmpty(stk.getType())){
			hql+=" and stk.type='"+stk.getType()+"'";
		}
		if(null!=stk.gettId()){
			hql+=" and stk.tId="+stk.gettId();
		}
		hql+="	 order by stk.pkid";
		return list(hql);
	}

	@Override
	public void deleteSubTarsk(SubTarskInfo stk) {
getHibernateTemplate().delete(stk);		
	}

	@Override
	public List<SubTarskInfo> getSubTarskInfos(String date, String organId, String repids) {
		String hql=" from SubTarskInfo stk where stk.organId='"+organId+"' and stk.dataDate='"+date+"' and stk.status='1' and stk.reportId in ("+repids+")";
	return list(hql);
	}

   public List getSubTarsks(String datadate, String repid) {
	      String sql = "select rep.id as repid, rep.rname as repname, st.stime  as createTime,st.status as status,st.sdatadate as repdate , st.endtime  as endtime, st.mess    AS message from (select r.name as rname, r.PKID as  id from code_rep_report r where r.status<>9 and r.begin_date <= '" + 
	        datadate.replace("-", "") + "' and r.end_date >= '" + datadate.replace("-", "") + "'" + 
	        "and r.report_type in (select t.pkid from code_rep_types t where" + 
	        " t.system_code=2 and t.showlevel=2)  order by r.report_type,r.show_order) rep," + 
	        " (select sti.create_time as stime,sti.status as  status ,crr.name as name,sti.en_datadate as  sdatadate , sti.end_time  AS endtime ,sti.message   AS mess " + 
	        " from SUB_TARSK_INFO sti,code_rep_report crr " + 
	        " where sti.pkid in(" + 
	        "  select max(sub.pkid) from SUB_TARSK_INFO sub group by sub.EN_REPORTID " + 
	        " ) and crr.PKID=sti.EN_REPORTID  and sti.en_datadate='" + datadate.replace("-", "") + "') st  where  rep.rname=st.name(+)  ";
	   
	      if (!StringUtils.isEmpty(repid)) {
	        sql = sql + " and rep.id='" + repid + "'";
	      }
	      
	      sql = sql + "  order by st.stime desc , case st.status when '3' then 1  when '1' then 2   when '2' then 4 else 3 end ";
	      List list = list(sql, null, new Object[][] { { "repid", Hibernate.STRING }, 
	        { "repname", Hibernate.STRING }, { "createTime", Hibernate.STRING }, 
	        { "status", Hibernate.STRING }, { "repdate", Hibernate.STRING }, { "endtime", Hibernate.STRING }, { "message", Hibernate.STRING } });
	  
	      return list;
	    }
}
