package com.krm.ps.sysmanage.usermanage.dao.hibernate;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.sysmanage.usermanage.dao.UserRoleDAO;
import com.krm.ps.sysmanage.usermanage.vo.Role;
import com.krm.ps.util.SysConfig;
import com.krm.ps.util.FuncConfig;
/*
 *  添加日期:2006年9月13日
 *  添加人:赵鹏程
 */
public class UserRoleDAOHibernate extends BaseDAOHibernate implements UserRoleDAO {

	/* (non-Javadoc)
	 * @see com.krm.slsint.usermanage.dao.UserRoleDAO#getUserRole()
	 */
	public List getUserRole() {
		String sql = "select {r.*} from umg_role r";
		List list = list(sql,new Object[][]{{"r", Role.class}},null);
		return list;
	}

	
	/**
	 * @param userid
	 * 根据user_id和role_type得到role_level
	 */
	public String getUserLevel(Long userid)
	{
		StringBuffer sb = new StringBuffer("select role_level from umg_role ,umg_user_role where umg_role.role_type =umg_user_role.role_type and umg_user_role.user_id= ");
		String sql = sb.append(userid).toString();
		String userlevel = null;
		List list = this.getSession().createSQLQuery(sql).addScalar("role_level",Hibernate.STRING).list();
		if(!list.isEmpty()) {
			userlevel = (String) list.get(0);
		}
		return userlevel;
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.usermanage.dao.UserRoleDAO#getMenu()
	 */
	public List getMenu(Long systemId) {
		String tableName = FuncConfig.getProperty("menu.tableName", "menu");
		String sql = "";
	/*	if(systemId==null) {
			//sql = "select id as menuId,name as aName,pane as pane from "+tableName+" m where pane <> '000000' order by id";
			sql = "select id as menuId,name as aName,pane as pane from "+tableName+" m where pane <> '000000' and system_id is NULL order by id";
		}else {
		}*/
		sql = "select id as menuId,name as aName,pane as pane from "+tableName+" m where pane <> 0 and system_id="+ systemId+" order by id";
		Object[][] scalaries = {{"menuId",Hibernate.LONG},{"aName",Hibernate.STRING},{"pane",Hibernate.LONG}};
		return this.list(sql, null, scalaries);		
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.usermanage.dao.UserRoleDAO#getRoleInfo(java.lang.Long)
	 */
	public List getRoleInfo(Long pkid) {
		String sql = "select {r.*} from umg_role r where pkid = "+pkid;
		List list = this.list(sql,new Object[][]{{"r",Role.class}},null);
		return list;
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.usermanage.dao.UserRoleDAO#getRoleMenu(java.lang.Long)
	 */
	public List getRoleMenu(Long systemId,Long roleType) {
		String rMenu = "";
		if(SysConfig.DB=='d'){
			//rMenu = Util.coverDecimalToVarchar("r.menu_id");
			rMenu = "r.menu_id";
			//rMenu = "ltrim(rtrim(char(integer(r.menu_id))))";
		}else{
			rMenu = "r.menu_id";
		}
		String tableName = FuncConfig.getProperty("menu.tableName", "menu");
		String sql = "";
	/*	if(systemId==null) {
			 sql = "select m.id as menuId , m.name as aName,m.pane as pane from "+tableName+" m where m.id in( select  "+rMenu+
			" from role_right r where r.role_id = "+roleType+") order by id";
			 sql = "select m.id as menuId , m.name as aName,m.pane as pane from "+tableName+" m where m.id in( select  "+rMenu+
				" from role_right r where r.role_id = "+roleType+") and system_id is NULL order by id";
		}else{
		}*/
		sql = "select m.id as menuId , m.name as aName,m.pane as pane from "+tableName+" m where m.id in( select  "+rMenu+
		" from role_right r where r.role_id = "+roleType+") and system_id="+systemId+" order by id";
		Object [][] scalaries = {{"menuId",Hibernate.LONG},{"aName",Hibernate.STRING},{"pane",Hibernate.LONG}};
		return this.list(sql,null,scalaries);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.usermanage.dao.UserRoleDAO#delRole(java.lang.Long)
	 */
	public void delRole(Long pkid) {
		String sql = "delete from umg_role where pkid = ?";
		Object [] scalaries = new Object[]{pkid};
		this.jdbcUpdate(sql,scalaries);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.usermanage.dao.UserRoleDAO#delRoleRight(java.lang.Long)
	 */
	public void delRoleRight(Long systemId,Long roleType) {
		String sql = "delete from role_right where role_id = ? and menu_id in (select id from menu where system_id ="+systemId+")";
		Object [] scalaries = new Object[]{roleType};
		this.jdbcUpdate(sql,scalaries);
	}

	public void delRoleRight(Long roleType) {
		String sql = "delete from role_right where role_id = ?";
		Object [] scalaries = new Object[]{roleType};
		this.jdbcUpdate(sql,scalaries);
	}
	/* (non-Javadoc)
	 * @see com.krm.slsint.usermanage.dao.UserRoleDAO#getMaxPkid()
	 */
	public Long getMaxPkid() {
		String sql = "select MAX(pkid) as pkid from role_right";
		Object [][] scalaries = new Object[][]{{"pkid",Hibernate.LONG}};
		List list = list(sql, null, scalaries);
		Iterator it = list.iterator();
		Long pkid = (Long) it.next();
		if(pkid == null){
			return new Long(0);
		}else{
			return pkid;
		}		
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.usermanage.dao.UserRoleDAO#addRoleRight(java.lang.Long, java.lang.Long, java.lang.Long)
	 */
	public void addRoleRight(Long pkid, Long roleId, Long menuId) {
		String sql;
		Object [] scalaries;
		if(SysConfig.DB == 's'){//wsx,2007-7-11
			sql = "insert into role_right (role_id,menu_id) values (?,?)";
			scalaries = new Object[]{roleId,menuId};
		}else {
			sql = "insert into role_right (pkid,role_id,menu_id) values (?,?,?)";
			scalaries = new Object[]{pkid,roleId,menuId};
		}
		this.jdbcUpdate(sql,scalaries);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.usermanage.dao.UserRoleDAO#updateRole(com.krm.slsint.usermanage.vo.Role)
	 */
	public void updateRole(Role role) {
		//String sql = "update umg_role set name = ? , description = ? where pkid = ?";
		String sql = "update umg_role set name = ? , description = ?,role_level = ? where pkid = ?";
		Object [] scalaries = new Object[]{role.getName(),role.getDescription(),role.getRoleLevel(),role.getPkid()};
		this.jdbcUpdate(sql,scalaries);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.usermanage.dao.UserRoleDAO#getRoleType()
	 */
	public List getRoleType() {
		String sql = "select role_type as roleType from umg_role order by role_type";
		Object [][] scalaries = new Object[][]{{"roleType",Hibernate.STRING}};
		return this.list(sql,null,scalaries);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.usermanage.dao.UserRoleDAO#getPaneMenu()
	 */
	public List getPaneMenu(Long systemId) {
		String tableName = FuncConfig.getProperty("menu.tableName", "menu")+" menu";
		String sql = "";
	/*	if(systemId==null) {
			 sql = "select id as menuId,name as aName,pane as pane from "+tableName+" where pane = '000000' and system_id is NULL order by id";
		}else {
		}*/
		sql = "select id as menuId,name as aName,pane as pane from "+tableName+" where pane = 0 and system_id="+systemId+" order by id";
		Object[][] scalaries = {{"menuId",Hibernate.LONG},{"aName",Hibernate.STRING},{"pane",Hibernate.LONG}};
		return this.list(sql, null, scalaries);
	}
	
	/**
	 * 查询用户子系统关系
	 * @return
	 */
	public List getUserSys(String sysFlag) {
		String hql = "select id as pkid,sys_flag as sysFlag,gp_id as gpId,create_date as createDate,status as status,flag as Flag from plat_sys_gp where status='1' and sys_flag = '"+sysFlag+"'";
		Object[][] scalaries = {{"pkid",Hibernate.STRING},{"sysFlag",Hibernate.STRING},{"gpId",Hibernate.STRING},{"createDate",Hibernate.STRING},{"status",Hibernate.STRING},{"Flag",Hibernate.STRING}};
		return this.list(hql,null,scalaries);
	}
	
	/**
	 * 判断用户是否有权限进入子系统
	 */
	public List getGPuser(String userId, String gpId) {
		String sql = "select pkid as pkid,user_id as userid,gp_id as gpId from plat_user_gp where user_id = "+userId+" and gp_id = "+gpId+" and flag='1'";
		Object[][] scalaries = {{"pkid",Hibernate.STRING},{"userid",Hibernate.STRING},{"gpId",Hibernate.STRING}};
		return this.list(sql,null,scalaries);
	}

	@Override
	public Long getRoleType(Long pkid) {
		String sql = "select role_type as roleType from umg_user_role where user_id="+ pkid;
		Object [][] scalaries = new Object[][]{{"roleType",Hibernate.LONG}};
		 List list = this.list(sql,null,scalaries);
		 if(list.size()>0){
			 return  (Long) list.get(0);
		 }
		 return null;
	}
	
	
}
