package com.krm.ps.sysmanage.organmanage2.dao.impl;
import java.util.List;

import org.hibernate.Hibernate;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;

import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.organmanage2.dao.OrganExtendDAO;
import com.krm.ps.util.SysConfig;
public class OrganExtendDAOImpl  extends BaseDAOHibernate implements OrganExtendDAO  {
	/**
	 * 取得同级机构列表
	 * 
	 * @param organSystemId
	 * @param orgID
	 * @return {list}对象列表
	 */
	public List getSameLevelOrgList(String orgID,int organSystemId,String date){
		 List list=null;
		 Object [][] scalaries = {{"code",Hibernate.STRING}};
		 String sql;
		//String  sql="select   pkid pkid from code_org_tree  where idxid='"+orgID+"' and parentid='79'";
		 
		 if(SysConfig.DB=='o'){
			 sql="select b.code code from code_org_tree a,  code_org_organ b where a.nodeid=b.pkid and b.begin_date<='"+date+"' and b.end_date>='"+date+"' and a.idxid='"+organSystemId+"'  and a.parentid=(select parentid from code_org_tree  where  idxid='"+organSystemId+"' and nodeid=(select pkid from code_org_organ where code='"+orgID+"'))";
		 }else{
			 sql="select b.code code from code_org_tree a,  code_org_organ b where a.nodeid=b.pkid and b.begin_date<='"+date+"' and b.end_date>='"+date+"' and a.idxid="+organSystemId+"  and a.parentid=(select parentid from code_org_tree  where  idxid="+organSystemId+" and nodeid=(select pkid from code_org_organ where code='"+orgID+"'))";
		 }
		
		list= this.list(sql,null,scalaries);
		 return list;
	  
	 }

}
