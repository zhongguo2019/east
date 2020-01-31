package com.krm.ps.sysmanage.usermanage.dao.hibernate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.framework.common.vo.PlatUserGp;
import com.krm.ps.model.vo.CodeRepJhgzZf;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.usermanage.dao.UserDAO;
import com.krm.ps.sysmanage.usermanage.vo.Role;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.sysmanage.usermanage.vo.UserDetail;
import com.krm.ps.sysmanage.usermanage.vo.UserReport;
import com.krm.ps.sysmanage.usermanage.vo.UserRole;
import com.krm.ps.util.DBDialect;
import com.krm.ps.util.SysConfig;
import com.krm.ps.util.Util;

public class UserDAOHibernate extends BaseDAOHibernate implements UserDAO {
	
	/**
	*
	*organcode传入的是登录用户的机构号
	× idx 是session中的user.getOrganTreeIdxid()			 
	**/
	public List getOrganidx(String organcode,int idx){
		String sql="select tt.SUBTREETAG ttlab from code_org_organ t,CODE_ORG_TREE tt where t.PKID=tt.NODEID and t.CODE='"+organcode+"' and  tt.IDXID="+idx+"";
		List resultL= list(sql,null,new Object[][]{{"ttlab",Hibernate.STRING}});
		return resultL;
	}
	/**
	 * 获取整个树的数据
	 */
public List getAllUserDetail(String organcode,int idx)
{
	List ls = this.getOrganidx(organcode, idx);
	String organTree="select tr.CODE from code_org_organ tr,CODE_ORG_TREE tt where tr.PKID=tt.NODEID  and tt.SUBTREETAG like '"+ls.get(0).toString()+"'";
	/*String hql = " from UserDetail ud where   ud.organId in ("+organTree+")";
	Map map = new HashMap();
	List result = list(hql, map);*/
	String sql = "select {ud.*} from umg_user_detail ud where ud.organ_id in ("+organTree+")";
	List result = list(sql,new Object[][]{{ "ud", UserDetail.class}},null,null);
	return result;
}


/**
 * 根据用户名和IP查询对象
 * @author LC
 * @param logname
 * @param ipAddr
 */
public User getUserByIp(String logonname,String ipaddr)
{
	String sql = "select {u.*} from umg_user u where logon_name = ? and ip = ? and status<>9";
	List ls = list(sql,new Object[][]{{ "u", User.class}},
			null, new Object[]{logonname,ipaddr});

	if (ls.size() > 0)
		return (User) ls.get(0);
	return null;
}

/**
 * 根据机构号查询
 * @param organId
 * 
 */
//public List queryUserDetailByOrganId(String organId)
public List queryUserDetail(String allOrganCode,String idCard,String tellerId)
{
/*		StringBuffer hql = new StringBuffer("from UserDetail ud where ud.organId in (:allOrganCode)");
	if(!"".equals(idCard))
	{
		hql.append(" and ud.idCard = "+idCard+"");
	}
	if(!"".equals(tellerId))
	{
		hql.append(" and ud.tellId = "+tellerId+"");
	}*/
	
	String sql="select {t.*} from UMG_USER_DETAIL t  where 1=1 ";
	
	if(!"".equals(allOrganCode) && allOrganCode!=null){
		String organTree="select tr.CODE from code_org_organ tr,CODE_ORG_TREE tt where tr.PKID=tt.NODEID  and tt.SUBTREETAG like '"+allOrganCode+"%'";
		sql+=" and  t.ORGAN_ID in ("+organTree+")";
	}
	if(!"".equals(idCard)&&idCard!=null)
	{
		//hql.append(" and ud.idCard = "+idCard+"");
		sql+=" and  t.ID_CARD ='"+idCard+"'";
		
	}
	if(!"".equals(tellerId)&&tellerId!=null)
	{
		sql+=" and  t.TELLER_ID ='"+tellerId+"'";
	}
	Object[][] obj=new Object[][]{{"t",UserDetail.class}};
	return list(sql, obj, null);

}


/**
 * 根据机构号，身份证,柜员号查询
 * @param organId
 * @param idCard
 * @param tellerId
 * 
 */
public List queryUser(String organId,String idCard,String tellerId)
{
	
	/*String hql = "select new User(u,r.roleType,r.name) from User u,UserRole ur,Role r,OrganInfo o ,UserDetail ud where (u.organId = o.code and " +
			"u.status<>9 and o.status<>9 and ur.roleType=r.roleType and ur.roleType=2 and u.pkid=ur.userId and u.isAdmin<>'2' and u.pkid = ud.userId and ud.tellerId = '"+tellerId+"' and ud.idCard = '"+idCard+"')"
	+ " or (" +
			"u.organId = o.code and u.status<>9 and ur.roleType=r.roleType and ur.roleType<>1 and u.pkid=ur.userId and u.isAdmin<>'2' and u.pkid = ud.userId and ud.tellerId = '"+tellerId+"' and ud.idCard = '"+idCard+"') order by u.name";
*/
//	String hql = " from UserDetail ud where ud.organId = '"+organId+"' and ud.tellerId = '"+tellerId+"' and ud.idCard = '"+idCard+"'";
	String hql = " from UserDetail ud where  ud.tellerId = '"+tellerId+"' and ud.idCard = '"+idCard+"'";
	Map map = new HashMap();
	List ls = list(hql, map);
	return ls;
}

/**
 * 删除用户详细信息
 * @param userid
 */
public void delUserDetail(Long userid)
{
	String sql = "delete  from UMG_USER_DETAIL where user_id = " + userid;
	try {
		this.getSession().connection().prepareStatement(sql).executeUpdate();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}


/**
 * 获得用户详细信息
 * @param ud
 * @return 
 */
public UserDetail getUserDetail(Long userId)
{
	//两张表都pkid，怎么区分？
	String sql ="select ud.teller_id as teller_id,ud.id_card as id_card,ud.phone as phone,ud.pkid as pkid from umg_user u,umg_user_detail ud where u.pkid = ud.user_id and ud.user_id = "+userId.toString();
	//String sql ="select * from umg_user u,umg_user_detail ud where u.pkid = ud.user_id and ud.user_id = "+userId.toString();
	List ls = this.getSession().createSQLQuery(sql).addScalar("teller_id", Hibernate.STRING)
										 .addScalar("id_card",Hibernate.STRING)
										 .addScalar("phone",Hibernate.STRING)
										 .addScalar("pkid",Hibernate.LONG).list();
	if(ls.size()!=0)
	{
		Object[] obj = (Object[])ls.get(0); 
		UserDetail ud = new UserDetail();
		ud.setTellerId(obj[0].toString());
		ud.setIdCard(obj[1].toString());
		ud.setPhone(obj[2].toString());
		ud.setUdPkid(Long.parseLong(obj[3].toString()));
		return ud;
	}
	else 
	{
		return null;
	}
	
}

/**
 * 保存用户详细信息
 * @param ud
 * @return 
 */
public int saveUserDetail(UserDetail ud)
{
	try {
		this.saveObject(ud);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		return UserDAO.SAVEDETAILFAIL;
	}
	return UserDAO.SAVEOK;
}

/**
 * 得到最新PKID的USERID
 * @return USERID
 */
public Long getNewUserId()
{
	String sql = "select max(pkid) as pkid from umg_user";
	Long userId = (Long)this.getSession().createSQLQuery(sql).addScalar("pkid",Hibernate.LONG).list().get(0);
	return userId;
}


public List getUsers() {
	String hql = "";
	if(SysConfig.DB=='d'){
		String urType = Util.coverDecimalToVarchar("ur.roleType");
		hql = "select new User(u,r.roleType,r.name)from User u,UserRole ur,Role r where u.status<>9 and "+urType+"=r.roleType and u.pkid=ur.userId";
	}else{
		hql = "select new User(u,r.roleType,r.name)from User u,UserRole ur,Role r where u.status<>9 and ur.roleType=r.roleType and u.pkid=ur.userId";
	}
	return list(hql);
}

public List getRoles() {
	//String hql = "from Role t where t.roleType>'2'";
	
	String hql = "";
	if(SysConfig.DB=='d'){
		hql = "from Role t where t.roleType>2";
	}else{
		hql = "from Role t where t.roleType>2";
	}
	
	return list(hql);
}

public List getSubRoleLevel(String roleLevel)
{
	String hql ="from Role t where t.roleLevel > '"+roleLevel+"'";
	return list(hql);
	
}

public UserRole getUserRole(Long userid) {
	String hql = "from UserRole t where t.userId = " + userid;
	Object o = this.uniqueResult(hql);
	if (null != o) {
		return (UserRole) o;
	}
	return null;
}

public boolean logonNameRepeat(Long pkid, String logonName) {
	Long spkid = pkid;
	if (null == spkid) {
		spkid = new Long(-1);
	}
	String hql = "from User t where t.pkid<>:pkid and t.logonName = :name and t.status<>9";
	Map map = new HashMap();
	map.put("pkid", spkid);
	map.put("name", logonName);
	List ls = list(hql, map);
	if (ls.size() > 0)
		return true;
	return false;
}

public int saveUser(User user) {
	Long roleType = user.getRoleType();
	if (logonNameRepeat(user.getPkid(), user.getLogonName())) {
		return UserDAO.LONGNAME_REPEAT;
	}
	
	saveObject(user);
	UserRole ur = getUserRole(user.getPkid());
	if (null != ur) {
		ur.setRoleType(roleType);
		ur.setUserId(user.getPkid());
		saveObject(ur);
	} else {
		ur = new UserRole();
		ur.setUserId(user.getPkid());
		ur.setRoleType(user.getRoleType());
		saveObject(ur);
	}
	return UserDAO.SAVEOK;
}

public void removeUser(Long pkid) {
	Object user = this.getObject(User.class, pkid);
	UserRole role = this.getUserRole(pkid);
	if (null != user) {
		this.removeObject(user);
	}
	if (null != role) {
		this.removeObject(role);
	}
}

public void updatePassword(Long pkid, String password) {
	Object user = this.getObject(User.class, pkid);
	if (null != user) {
		((User) user).setPassword(password);
		this.saveObject(user);
	}
}

public void updatePassword_new(Long pkid, String password) {
	Object user = this.getObject(User.class, pkid);
	if (null != user) {
		((User) user).setPassword(password);
		//计算出时间,确保时间精确在数据库层，得出时间。
		Date d = new Date();
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String date = df.format(d);
		((User) user).setCreatetime(date);
		this.saveObject(user);
	}
}

public User getUser(String longname, String password) {
	String sql = "select {u.*} from umg_user u where password= '"+password+"' and logon_name = '"+longname+"' and status<>9";
//	Map map = new HashMap();
//	map.put("password", password);
//	map.put("longname", longname);
	/*List ls = list(sql,new Object[][]{{ "u", User.class}},
			null, new Object[]{password,longname});*/
	List ls = list(sql,new Object[][]{{ "u", User.class}},null);
	if (ls.size() > 0)
		return (User) ls.get(0);
	return null;
}

//获得机构下所有所有操作员的方法
public List getUsers(String organ) {
	String hql = "from User t where t.organId=:organ and t.status<>9 and t.isAdmin<>'2' order by  t.name ";
	Map map = new HashMap();
	map.put("organ", organ);
	List ls = list(hql, map);
	return ls;
}

/**
 * 得到所有属于该组的用户列表 add by zhaoyi _20070329
 * 根据状态得到用户的列表
 * @param status
 * @return
 */
public List getUsersByStatus(String status) {
	//1.得到用户信息
	String hql = "";
	/*if(SysConfig.DB=='d'){
		//String urType = Util.coverDecimalToVarchar("ur.roleType");
		*//**
		 * 2012-08-22修改湖北用户分组权限保存不成功
		 *//*
		  //String rType = Util.coverVarcharToDecimal("r.roleType");
		String rType="r.roleType";
		hql = "select new User(u,r.roleType,r.name) from User u,UserRole ur,Role r where u.status<>9 and u.status =:status and ur.roleType="+rType+" and u.pkid=ur.userId";
	}else{
		hql = "select new User(u,r.roleType,r.name) from User u,UserRole ur,Role r where u.status<>9 and u.status =:status and ur.roleType=r.roleType and u.pkid=ur.userId";
	}
	Map map = new HashMap();
	map.put("status", status);
	List ls = list(hql, map);*/
	
	
		hql = "select {u.*},{r.*} from umg_user u,umg_user_role ur,umg_role r where u.status<>9 and u.status ='"+status +"' and ur.role_Type=r.role_Type and u.pkid=ur.user_Id";
	List list = this.list(hql, new Object[][]{{"u",User.class},{"r",Role.class}}, null);
	
	List<User> ls = new ArrayList<User>();
	for (int i = 0; i < list.size(); i++) {
		Object[] obj = (Object[]) list.get(i);
		User u = (User) obj[0];
		Role r = (Role) obj[1];
		u.setRoleType(r.getRoleType());
		u.setRoleName(r.getName());
		ls.add(u);
	}
	return ls;
}

public List getUsersByOrgan(String organ) { 
	String lab = "";
	List labL = getOrganidx(organ, 1);
	if (labL.size() > 0) {
		lab = (String) labL.get(0);
	}
	String organTree = "select  tr.code,tr.status  from code_org_organ tr, CODE_ORG_TREE tt where tr.pkid=tt.nodeId  and tt.subTreeTag like '" + lab + "%' ";
	 
	
	String hql = "";
//	if(SysConfig.DB=='d'){
//		//String urRoleType = Util.coverDecimalToVarchar("ur.roleType");//��DB2�Ļ����£��޸ĸ��﷨��ʽ 2012-04-01   ����
//		//String rType = Util.coverVarcharToDecimal("r.roleType");//
//		String rType="r.roleType";//
//		hql = "select new User(u,r.roleType,r.name) from User u,UserRole ur,Role r,("+organTree+") o where (u.organId = o.code and   u.status<>9 and o.status<>9 and ur.roleType="+rType+" and ur.roleType=2 and u.pkid=ur.userId and u.isAdmin<>'2')"
//				+ " or (  u.organId = o.code and u.status<>9 and ur.roleType="+rType+" and ur.roleType<>1 and u.pkid=ur.userId and u.isAdmin<>'2') order by u.name";
//
//	
//	}else{
//		hql = "select new User(u,r.roleType,r.name) from User u,UserRole ur,Role r,("+organTree+") o where (u.organId = o.code and   u.status<>9 and o.status<>9 and ur.roleType=r.roleType and ur.roleType=2 and u.pkid=ur.userId and u.isAdmin<>'2')"
//		        + " or ( u.organId = o.code and u.status<>9 and ur.roleType=r.roleType and ur.roleType<>1 and u.pkid=ur.userId and u.isAdmin<>'2') order by u.name";
//	
//	}
//	
//	
//	Map map = new HashMap();
////	map.put("organ", organ);
//	 this.log.info("============"+hql);
//	List ls = list(hql, map);
	
 
	hql = "select {u.*},r.name as rname from umg_user u,umg_user_role ur,umg_role r,("+organTree+") o where (u.organ_Id = o.code  and   u.status<>9 and o.status<>9 and ur.role_Type=r.role_Type and ur.role_Type=2 and u.pkid=ur.user_Id and u.isAdmin<>'2')"
		        + " or ( u.organ_Id = o.code and u.status<>9 and ur.role_Type=r.role_Type and ur.role_Type<>1 and u.pkid=ur.user_Id and u.isAdmin<>'2') order by u.name";
	 
	List ls= list(hql,new Object[][]{{"u",User.class}},new Object[][]{{"rname",Hibernate.STRING} });
	List userLs =new ArrayList();
	if(ls.size()>0){
		for (int i = 0; i < ls.size(); i++) {
			Object[] obj = (Object[])ls.get(i);
			User user =(User) obj[1];
			String role =(String) obj[0];
			user.setRoleName(role);
			userLs.add(user);
		}
	}
	return userLs;
}


 

public List getUsersByOrgan(String organ , String userid ,String userName) { 
	String hql = "";
//	if(SysConfig.DB=='d'){
//		//String urRoleType = Util.coverDecimalToVarchar("ur.roleType");//��DB2�Ļ����£��޸ĸ��﷨��ʽ 2012-04-01   ����
//		//String rType = Util.coverVarcharToDecimal("r.roleType");//
//		String rType="r.roleType";//
//		hql = "select new User(u,r.roleType,r.name) from User u,UserRole ur,Role r,OrganInfo o where (u.organId = o.code and o.super_id =:organ and u.status<>9 and o.status<>9 and ur.roleType="+rType+" and ur.roleType=2 and u.pkid=ur.userId and u.isAdmin<>'2')"
//				+ " or (u.organId=:organ and u.organId = o.code and u.status<>9 and ur.roleType="+rType+" and ur.roleType<>1 and u.pkid=ur.userId and u.isAdmin<>'2')  ";
//
//		if(StringUtils.isNotBlank(userid)){
//			 hql += " and u.logonName ='"+userid+"'";
//		}
//       if(StringUtils.isNotBlank(userName)){
//       	 hql += " and u.name ='"+userName+"'";
//		}
//       
//         hql += " order by u.name ";
//	
//	}else{
//		hql = "select new User(u,r.roleType,r.name) from User u,UserRole ur,Role r,OrganInfo o where (u.organId = o.code and o.super_id =:organ and u.status<>9 and o.status<>9 and ur.roleType=r.roleType and ur.roleType=2 and u.pkid=ur.userId and u.isAdmin<>'2')"
//		        + " or (u.organId=:organ and u.organId = o.code and u.status<>9 and ur.roleType=r.roleType and ur.roleType<>1 and u.pkid=ur.userId and u.isAdmin<>'2') ";
//	
//		if(StringUtils.isNotBlank(userid)){
//			 hql += " and u.logonName ='"+userid+"'";
//		}
//        if(StringUtils.isNotBlank(userName)){
//        	 hql += " and u.name ='"+userName+"'";
//		}
//        
//          hql += " order by u.name ";
//		
//	}
//	
//	Map map = new HashMap();
//	map.put("organ", organ);
//	List ls = list(hql, map);
//	
	
	String lab = "";
	List labL = getOrganidx(organ, 1);
	if (labL.size() > 0) {
		lab = (String) labL.get(0);
	}
	String organTree = "select  tr.code,tr.status  from code_org_organ tr, CODE_ORG_TREE tt where tr.pkid=tt.nodeId  and tt.subTreeTag like '" + lab + "%' ";
	 
	
	
	hql = "select {u.*},r.name as rname from umg_user u,umg_user_role ur,umg_role r,("+organTree+") o where (u.organ_Id = o.code  and   u.status<>9 and o.status<>9 and ur.role_Type=r.role_Type and ur.role_Type=2 and u.pkid=ur.user_Id and u.isAdmin<>'2'" ;
			if(StringUtils.isNotBlank(userid)){
				 hql += " and u.logon_Name like '%"+userid+"%'";
			}
		    if(StringUtils.isNotBlank(userName)){
		  	   hql += " and u.name like '%"+userName+"%'";
			}	
	 hql +="  ) or ( u.organ_Id = o.code and u.status<>9 and ur.role_Type=r.role_Type and ur.role_Type<>1 and u.pkid=ur.user_Id and u.isAdmin<>'2' ";
 
	if(StringUtils.isNotBlank(userid)){
		 hql += " and u.logon_Name like '%"+userid+"%'";
	}
    if(StringUtils.isNotBlank(userName)){
  	   hql += " and u.name like '%"+userName+"%'";
	}
  
    hql += ") order by u.name ";	
	
	List ls= list(hql,new Object[][]{{"u",User.class}},new Object[][]{{"rname",Hibernate.STRING} });
	List userLs =new ArrayList();
	if(ls.size()>0){
		for (int i = 0; i < ls.size(); i++) {
			Object[] obj = (Object[])ls.get(i);
			User user =(User) obj[1];
			String role =(String) obj[0];
			user.setRoleName(role);
			userLs.add(user);
		}
	}
	
	return userLs;
}

public void removeUserByOrgan(String organid) {
	String sql = "delete from umg_user where isadmin<>'2' and organ_id =?";
	jdbcUpdate(sql,new Object[]{organid});
}

public List getUserReportsByUserID(Long pkid) {
	String hql = "from UserReport t where t.operId =" + pkid;
	List ls = list(hql);
	return ls;
}

public List getUserReportsByGPID(Long pkid){
	String hql = "from UserReport t where t.operId =" + pkid;
	List ls = list(hql);
	return ls;
}

/**
 * add by zhaoyi 
 * 通过用户id得到可以操作的唯一的报表列表
 * @param pkid
 * @return
 */
public List getUserReportsByUserIDDistinct(Long pkid){
	String sql = "select distinct repid as di from rep_oper_contrast where operid = " + pkid;
	Object[][] scalaries = { { "di", Hibernate.LONG }};
	List list = this.list(sql,null,scalaries);
	return list;
}

public void deleteUserReportsByUserId(Long pkid) {
	String sql = "delete from rep_oper_contrast where operid =?";
	jdbcUpdate(sql,new Object[]{pkid});
	
}
public void deleteUserReportsByUserId(Long reportId, Long typeId, Long userId) {
	String sql = "delete from rep_oper_contrast where typeid = "+typeId+" and repid = "+reportId+" and operid="+userId;
	this.jdbcUpdate(sql,null);
	
}
/**
 * 删除，保存用户权限
 * @param ur
 */
public void DelsaveUserReport(List<User>  userlist,List<UserReport> userreportlist){
	String[] delsql=new String[userlist.size()];
	int uu=0;
	for(User u:userlist){
		delsql[uu]="delete from rep_oper_contrast where operid="+u.getPkid();
		uu=uu+1;
	}
	this.batchJdbcUpdate(delsql);
	this.batchSaveVO(userreportlist);
}
public void delUserRepByRepIdAndTypeId(Long reportId, Long typeId, Long userId) {
	String sql = "delete from rep_oper_contrast where typeid = "+typeId+" and repid = "+reportId+" and operid in ( "+
				 "select u.pkid from umg_user u, umg_user_role r where u.pkid = r.user_id and r.role_type not in(1,2) and u.pkid <> "+userId+") ";
	this.jdbcUpdate(sql, null);
}

public void setUserReportAss(Long userid) {
	List adminUserList = getAdminUser();
	String adminUser = "";
	for(Iterator itr = adminUserList.iterator(); itr.hasNext();) {
		User user = (User)itr.next();
		if(adminUser.equals("")) {
			adminUser = user.getPkid().toString();
		}else {
			adminUser += "," + user.getPkid().toString();
		}
	}
	
	String dsql = "delete from rep_oper_contrast where operid = ?";
	jdbcUpdate(dsql,new Object[]{userid});
	
	//2007.4.10 兼容SqlServer数据库		
	String isql ="";
	if ('s'==SysConfig.DB){
		isql = "insert into rep_oper_contrast(operid,typeid,repid)"
			+ " (select " + "?,report_type,pkid from code_rep_report where createid in ("+adminUser+") or createid is NULL)";
		jdbcUpdate(isql,new Object[]{userid});
		
	}else if('d'==SysConfig.DB){
		isql = "insert into rep_oper_contrast(pkid,operid,typeid,repid)"
			+ " select " + DBDialect.genSequence("rep_oper_contrast_seq") + ","+userid+",report_type,pkid from code_rep_report where createid in ("+adminUser+") or createid is NULL";
		this.getHibernateTemplate().update(isql);
		
	}else{
		isql = "insert into rep_oper_contrast(pkid,operid,typeid,repid)"
			+ " select " + DBDialect.genSequence("rep_oper_contrast_seq") + ",?,report_type,pkid from code_rep_report where createid in ("+adminUser+") or createid is NULL";
		
		jdbcUpdate(isql,new Object[]{userid});
	}
	 
	
}

/**
 * add by zhaoyi _20070329
 * 执行任义sql
 * @param dic
 */
public void exeAnySql(String sql){
	this.jdbcUpdate(sql,null);
}
/**
 * 根据用户登陆名称查找用户
 * @param loginName
 * 			用户登陆名称
 */
public User getUser(String loginName){
	String sql = "SELECT {u.*} FROM umg_user u WHERE u.logon_name = ?";
	Object[] values=new Object[] {loginName};
	List list =  list(sql,new Object[][] { { "u", User.class } }, null, values);
	if(list.size() > 0)
		return (User)list.get(0);
	else
		return null;
}
/**
 * 更新用户信息
 * @param user
 * 			用户对象
 */
public void updateUser(User user){
	String sql = "UPDATE umg_user SET name = ? ,password = ?, organ_id = ? WHERE pkid = ?";
	Object [] values =  new Object[]{user.getName(),user.getPassword(),user.getOrganId(),user.getPkid()};
	jdbcUpdate(sql,values);
}

public int updateRepidByRepid(Long newRepid, Long oldRepid){
	String sql="update rep_oper_contrast set repid = "
		+newRepid+" where repid = "+oldRepid;
	return jdbcUpdate(sql,null);
}
/**
 * 得到该地区下所有用户(除了管理员和机构管理员)
 * @param areaId
 * @return
 */
public List getUserByArea(String areaId, Long userId){
	Object[][] scalaries = { { "id", Hibernate.LONG },
		{ "userName", Hibernate.STRING }, {"organName", Hibernate.STRING}};
	String sql = "select u.pkid as id, u.name as userName, o.short_name as organName from umg_user u, umg_user_role r, code_org_organ o where u.organ_id in "+
				"(select code from code_org_organ "+ 
				"where super_id = '"+areaId+"' and status <> 9) and u.pkid = r.user_id "+
				"and r.role_type not in (1,2) and u.organ_id = o.code and u.pkid <>" + userId;
	List result =  list(sql, null, scalaries, null);
	return result;
}
/**
 * 根据报表id,报表类型id得到有权限的用户
 * @param typeId
 * @param repId
 * @return
 */
public List getRepUser(Long typeId, Long repId, Long userId){
	Object[][] scalaries = { { "id", Hibernate.LONG },
		{ "userName", Hibernate.STRING }, {"organName", Hibernate.STRING}};
	String sql = "select u.pkid as id, u.name as userName, o.short_name as organName from umg_user u, rep_oper_contrast c ,umg_user_role r, code_org_organ o "+
				 "where u.pkid = c.operid and u.pkid = r.user_id and r.role_type not in (1,2) "+ 
				 "and typeid = "+typeId+" and repid = " + repId + " and u.organ_id = o.code and u.pkid <>" + userId;
	List result = list(sql, null, scalaries, null);
	return result;
}

/**
 * 20071127 lxk
 * 根据机构Id,用户角色Id得到指定角色用户
 * @param organId
 * @param roleId
 * @return
 */
public User getAppointRoleUser(String organId, Long roleId) {
	String sql = "select {u.*} from umg_user u join umg_user_role ur on u.pkid = ur.user_id where u.organ_id = '"+organId+"' and ur.role_type = "+roleId;
	List list =  list(sql, new Object[][]{{"u", User.class}}, null, null);
	return list.size() > 0 ? (User)list.get(0) : null;
}
/**
 * 根据用户Id和报表Id得到用户报表权限
 * @param operId
 * @param reportId
 * @return
 */
public List getUserReport(int operId, int reportId) {
	String sql = "SELECT {u.*} FROM rep_oper_contrast u WHERE operid = "+operId+" AND repid =" + reportId;
	List result = list(sql, new Object[][]{{"u", UserReport.class}}, null, null);
	return result;
}
/**
 * 得到系统管理员
 */
public List getAdminUser() {
	String sql = "select {u.*} from umg_user u where isadmin = '2'";
	List result = list(sql, new Object[][]{{"u", User.class}}, null, null);
	return result;
}

/* (non-Javadoc)
 * @see com.krm.slsint.usermanage.dao.UserDAO#getUsers(java.lang.String, java.lang.Long)
 */
public List getUsers(String organId, Long roleType)
{
	String sql = "select {u.*} from umg_user u join umg_user_role ur on u.pkid = ur.user_id "+
				 "where 1 = 1 ";
	if(organId != null){
		sql += " and u.organ_id = '"+ organId +"' ";
	}
	if(roleType != null){
		sql += "and ur.role_type = " + roleType;
	}
	
	List result = list(sql, new Object[][]{{"u", User.class}}, null, null);
	return result;
}

public List getUserListByOrganIdsAndRoleIds(String organIds, String roleIds)
{
	String sql = "select {u.*} from umg_user u join umg_user_role ur on u.pkid = ur.user_id "+
	 "where 1 = 1 ";
	if(organIds!= null){
		sql += " and u.organ_id in ("+ organIds +") ";
	}
	if(roleIds != null){
		sql += "and ur.role_type in (" + roleIds + ")";
	}
	List result = list(sql, new Object[][]{{"u", User.class}}, null, null);
	return result;
}

	public List getRolesAll() {
	String hql = "from Role t";
	return list(hql);
}

	/* (non-Javadoc)
	 * @see com.krm.slsint.usermanage.dao.UserDAO#getRole(java.lang.String)
	 */
	public Role getRole(String type)
	{
		String sql = "select {r.*} from umg_role r where role_type = '"+type+"'";
		List result = list(sql, new Object[][]{{"r", Role.class}}, null, null);
		if(result.size() > 0)
		{
			return (Role)result.get(0);
		}
		return null;
	}

	/**
	 * @see com.krm.slsint.usermanage.dao.UserDAO#getUserReport(java.lang.Long)
	 */
	public List getUserReport(Long userId)
	{
		String sql = "SELECT {c.*} FROM rep_oper_contrast c WHERE operid = "+ userId;
		List result = list(sql, new Object[][]{{"c", UserReport.class}}, null, null);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.usermanage.dao.UserDAO#getPlatUserGpInSubSys(java.lang.Long, java.lang.String)
	 */
	public List getPlatUserGpInSubSys(Long userId, String subSysFlag) {
		String sql = "SELECT {p.*} FROM plat_user_gp p WHERE p.flag='1' and p.user_id=? and p.gp_id in(SELECT gp_id FROM plat_sys_gp WHERE status='1' and sys_flag='"+subSysFlag+"')";
		Object[] values=new Object[] {userId};
		List result = list(sql,new Object[][]{{"p",PlatUserGp.class}},null,values);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.usermanage.dao.UserDAO#getSendMailUsers(java.lang.String)
	 */
	public List getSendMailUsers(String organCode) {
		String hql = "from User t where t.organId=:organ and t.status<>9  order by  t.name ";
		Map map = new HashMap();
		map.put("organ", organCode);
		List ls = list(hql, map);
		return ls;
	}
	/**
	 * 根据机构、登陆名称、用户名称进行模糊查询
	 * @param organ 用户所选机构
	 * @param logonname 用户登陆名称左右全部模糊
	 * @param username  用户名称右边进行模糊
	 * @return
	 * @ add by ydw 2011-11-21
	 */
	public List getUsersByOrganLogonNameUserNmae(String organ,String loginname,String username) { 
		String hql = "";
		// 
//		if(SysConfig.DB=='d'){
//			String urRoleType = Util.coverDecimalToVarchar("ur.roleType");
//			hql = "select new User(u,r.roleType,r.name) from User u,UserRole ur,Role r,OrganInfo o where (u.organId = o.code and " +
//					//"and o.super_id =:organ and " +
//					" u.status<>9 and o.status<>9 and "+urRoleType+"=r.roleType and ur.roleType=2 and u.pkid=ur.userId and u.isAdmin<>'2' and u.name like '%"+username+"%' and u.logonName like '%"+loginname+"%')"
//					+ " or (" +
//							//"u.organId=:organ and " +
//							"u.organId = o.code and u.status<>9 and "+urRoleType+"=r.roleType and ur.roleType<>1 and u.pkid=ur.userId and u.isAdmin<>'2' and u.name like '%"+username+"%' and u.logonName like '%"+loginname+"%') order by u.name";
//		}else{
//			hql = "select new User(u,r.roleType,r.name) from User u,UserRole ur,Role r,OrganInfo o where (u.organId = o.code and " +
//					//"o.super_id =:organ and " +
//					"u.status<>9 and o.status<>9 and ur.roleType=r.roleType and ur.roleType=2 and u.pkid=ur.userId and u.isAdmin<>'2' and u.name like '%"+username+"%' and u.logonName like '%"+loginname+"%')"
//			+ " or (" +
//					//"u.organId=:organ and " +
//					"u.organId = o.code and u.status<>9 and ur.roleType=r.roleType and ur.roleType<>1 and u.pkid=ur.userId and u.isAdmin<>'2' and u.name like '%"+username+"%' and u.logonName like '%"+loginname+"%') order by u.name";
//		}
//		
		//select user0_.pkid as col_0_0_, role2_.role_type as col_1_0_, role2_.name as col_2_0_ from umg_user user0_, umg_user_role userrole1_, umg_role role2_, code_org_organ organinfo3_ where user0_.organ_id=organinfo3_.code and user0_.status<>9 and organinfo3_.status<>9 and userrole1_.role_type=role2_.role_type and userrole1_.role_type=2 and user0_.pkid=userrole1_.user_id and user0_.isadmin<>'2' and (user0_.name like '%ååºå¿äºä¸ä¸%') and (user0_.logon_name like '%%') or user0_.organ_id=organinfo3_.code and user0_.status<>9 and userrole1_.role_type=role2_.role_type and userrole1_.role_type<>1 and user0_.pkid=userrole1_.user_id and user0_.isadmin<>'2' and (user0_.name like '%ååºå¿äºä¸ä¸%') and (user0_.logon_name like '%%') order by user0_.name
//		Map map = new HashMap();
//		//map.put("organ", "");
//		List ls = list(hql, map);
		
		
		hql = "select {u.*},r.name as rname from umg_user u,umg_user_role ur,umg_role r,code_org_organ o where (u.organ_Id = o.code  and   u.status<>9 and o.status<>9 and ur.role_Type=r.role_Type and ur.role_Type=2 and u.pkid=ur.user_Id and u.isAdmin<>'2'" ;
				if(StringUtils.isNotBlank(loginname)){
					 hql += " and u.logon_Name like '%"+loginname+"%'";
				}
			    if(StringUtils.isNotBlank(username)){
			  	   hql += " and u.name like '%"+username+"%'";
				}	
		 hql +="  ) or ( u.organ_Id = o.code and u.status<>9 and ur.role_Type=r.role_Type and ur.role_Type<>1 and u.pkid=ur.user_Id and u.isAdmin<>'2' ";
	 
		if(StringUtils.isNotBlank(loginname)){
			 hql += " and u.logon_Name like '%"+loginname+"%'";
		}
	    if(StringUtils.isNotBlank(username)){
	  	   hql += " and u.name like '%"+username+"%'";
		}
	  
	    hql += ") order by u.name ";	
		
		List ls= list(hql,new Object[][]{{"u",User.class}},new Object[][]{{"rname",Hibernate.STRING} });
		List userLs =new ArrayList();
		if(ls.size()>0){
			for (int i = 0; i < ls.size(); i++) {
				Object[] obj = (Object[])ls.get(i);
				User user =(User) obj[1];
				String role =(String) obj[0];
				user.setRoleName(role);
				userLs.add(user);
			}
		}
		
		return userLs;
		
	}
	
	/**
	 * 根据用户ID查找用户
	 * @param userId
	 * 			用户ID
	 */
	public User getUserById(String userId){
		String sql = "SELECT {u.*} FROM umg_user u WHERE u.pkid = ?";
		Object[] values=new Object[] {userId};
		List list =  list(sql,new Object[][] { { "u", User.class } }, null, values);
		if(list.size() > 0)
			return (User)list.get(0);
		else
			return null;
	}

	public List getUserReport(long groupType) {
		String sql = "select typeid as typeid, repid as repid from rep_oper_contrast " +
				"where operid=" + String.valueOf(groupType);
		Object[][] scalaries = { { "typeid", Hibernate.LONG }, { "repid", Hibernate.LONG }};
		List result = list(sql, null, scalaries, null);
		return result;
	}
	@Override
	public List getUserBYOrganArea(String areaCode) {
		// TODO Auto-generated method stub
		//String sql=""
		String sql="select user0_.pkid as pkid,  user0_.creator    as creator,  user0_.createtime as createtime,  user0_.isadmin    as isadmin,  user0_.logon_name as logon,  user0_.name as name,  user0_.organ_id   as organ," +
				"  user0_.password   as password,  user0_.status     as status,  role2_.role_type  as roletype,  role2_.name as rolename   from umg_user user0_, umg_user_role userrole1_, umg_role role2_  where userrole1_.role_type = role2_.role_type    and user0_.isadmin <> '2'    and user0_.pkid = userrole1_.user_id    and user0_.organ_id in  (select p.code     from code_org_organ p    where p.organ_type in (select d1.dicid     from code_dictionary d1          where d1.parentid = 1001)      and p.business_type in    (select d1.dicid       from code_dictionary d1      where d1.parentid = 1002)      and p.status < 9    start with p.code = '"+areaCode+"'   connect by prior p.code = p.super_id)    and user0_.status <> 9 ";
		Object[][] scalaries = { { "pkid", Hibernate.LONG },
				{ "creator", Hibernate.LONG },
				{ "createtime", Hibernate.STRING },
				{ "isadmin", Hibernate.INTEGER },
				{ "logon", Hibernate.STRING },
				{ "name", Hibernate.STRING },
				{ "organ", Hibernate.STRING },
				{ "password", Hibernate.STRING },
				{ "roletype", Hibernate.LONG },
				{ "rolename", Hibernate.STRING }
		};
		List result = list(sql, null, scalaries, null);
		List<User> userL=new ArrayList<User>();
		for(int i=0;i<result.size();i++){
			Object[] obj=(Object[])result.get(i);
			userL.add(changUser(obj));
		}
		return userL;
	}
	private User changUser(Object[] obj){
		User user=new User();
		user.setPkid((Long)obj[0]);
		user.setCreator((Long)obj[1]);
		user.setCreatetime((String)obj[2]);
		user.setIsAdmin((Integer)obj[3]);
		user.setLogonName((String)obj[4]);
		user.setName((String)obj[5]);
		user.setOrganId((String)obj[6]);
		user.setPassword((String)obj[7]);
		user.setRoleType((Long)obj[8]);
		user.setRoleName((String)obj[9]);
		return user;
	}
	public List getUserList(String username){
		//因为青海的用户名字和机构号是一样的
		String lab="";
		List labL = getOrganidx(username, 1);
		if (labL.size() > 0) {
			lab = (String) labL.get(0);
		}
//		String organTree = "select tr.CODE from code_org_organ tr,CODE_ORG_TREE tt where tr.PKID=tt.NODEID  and tt.SUBTREETAG like '"
//				+ lab + "%' ";OrganInfo
		String organTree = "select tr.code from OrganInfo tr,OrganTreeNode tt where tr.pkid=tt.nodeId  and tt.subTreeTag like '"
		+ lab + "%' ";
		//String sql="select * from umg_user r where r.organ_id in ("+organTree+")";
		String sql="from User r where r.organId in ("+organTree+")";
		return list(sql);
		//return list(sql, new Object[][]{{"r", User.class}}, null,null);
	}
	@Override
	public List getAllOrgan(String arerCode) {
		Object[][] scalaries = { { "organTypeName", Hibernate.STRING },
				{ "businessTypeName", Hibernate.STRING }};		
		String sql = "SELECT d1.dicname AS organTypeName,d2.dicname AS businessTypeName,{o.*}" +
				" FROM code_org_organ o" +
				" JOIN code_dictionary d1 ON d1.parentid=1001 AND o.organ_type=d1.dicid" + 
				" JOIN code_dictionary d2 ON d2.parentid=1002 AND o.business_type=d2.dicid" +
				" WHERE super_id like '"+arerCode+"%' order by o.pkid desc";
//				" order by o.SUPER_ID ,o.CODE ";
//		Object[] values=new Object[] {"'arerCode %'"};
		List result=list(sql,new Object[][] { { "o", OrganInfo.class } },scalaries,null);
		return tranformResultToOrganInfoList(result);
		
	}
	private List tranformResultToOrganInfoList(List oil) {

		List organList=new ArrayList();
		Iterator it=oil.iterator();
		while(it.hasNext()) {
			Object[] organInfos=(Object[])it.next();
			String organTypeName=(String)organInfos[0];
			String businessTypeName=(String)organInfos[1];
			OrganInfo organInfo=(OrganInfo)organInfos[2];
			organInfo.setOrgan_typename(organTypeName);
			organInfo.setBusiness_typename(businessTypeName);
			organList.add(organInfo);
		}		
		return organList;
	}
	@Override
	public List getUsersByOLUNmae(String organ, String loginname, String username,int isadmin, int idxid) {
		String sqlq = "select {t.*} from UMG_USER t where 1=1";
		if(StringUtils.isNotBlank(organ)){
			String lab = "";
			List labL = getOrganidx(organ, idxid);
			if (labL.size() > 0) {
				lab = (String) labL.get(0);
			}
			String organTree = "";
			if (isadmin == 2 || isadmin == 3) {
				organTree = "select tr.CODE from code_org_organ tr,CODE_ORG_TREE tt where tr.PKID=tt.NODEID  and tt.SUBTREETAG like '"
						+ lab + "%' ";
			} else {
				organTree = organ;
			}
			sqlq += " and ORGAN_ID in ("+ organTree+")";
		}
		if(StringUtils.isNotBlank(loginname) ){
			sqlq += " and LOGON_NAME like'%" + loginname+"%'";
		}
		if(StringUtils.isNotBlank(username)){
			sqlq += " and NAME like'%" + username+"%'";
		}
		@SuppressWarnings("unchecked")
		List<User> resultL = this.list(sqlq,new Object[][]{{"t",User.class}},null);
		
		return resultL;
	}
	
}
