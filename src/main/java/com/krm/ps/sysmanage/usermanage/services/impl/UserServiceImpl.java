package com.krm.ps.sysmanage.usermanage.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.krm.ps.sysmanage.usermanage.dao.UserDAO;
import com.krm.ps.sysmanage.usermanage.services.UserService;
import com.krm.ps.sysmanage.usermanage.vo.Role;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.sysmanage.usermanage.vo.UserDetail;
import com.krm.ps.sysmanage.usermanage.vo.UserReport;
import com.krm.ps.sysmanage.usermanage.vo.UserRole;
import com.krm.ps.util.DBDialect;
import com.krm.ps.util.SysConfig;
import com.krm.ps.sysmanage.reportdefine.dao.ReportDefineDAO;
import com.krm.ps.sysmanage.reportdefine.vo.Report;


public class UserServiceImpl implements UserService{
	
	private UserDAO udao;
	
	private ReportDefineDAO rddao;
	
	public void setUserDAO(UserDAO dao){
		this.udao=dao;
	}
	
	public void setReportDefineDAO(ReportDefineDAO dao){
		this.rddao=dao;
	}
	
	public List getUsers(){
		return udao.getUsers();
	}
	
	public List getRoles(){
		return udao.getRoles();
	}
	
	/**
	 * 根据机构号查询
	 * @param organId
	 * 
	 */
	public List queryUserDetail(String allOrganCode,String idCard,String tellerId)
	{
		return udao.queryUserDetail(allOrganCode, idCard, tellerId);
	}
	
	/**
	 * 获取整个树的数据
	 */
	public List getAllUserDetail(String organcode,int idx)
	{
		return udao.getAllUserDetail(organcode, idx);
	}
	
	/**
	 * 根据机构号查询
	 * @param organId
	 * 
	 */
	/*public List queryUserDetailByOrganId(String organId,String organcode,int idx)
	{
		return udao.queryUserDetailByOrganId(organId,organcode,idx);
	}*/
	public List queryUserDetailByOrganId(String organId)
	{
		return null;
		//return udao.queryUserDetailByOrganId(organId);
	}
	
	/**
	 * 根据用户名和IP查询对象
	 * @author LC
	 * @param logname
	 * @param ipAddr
	 */
	public User getUserByIp(String logonname,String ipaddr)
	{
		return udao.getUserByIp(logonname, ipaddr);
	}
	
	/**
	 * 获得子角色
	 */
	public List getSubRoleLevel(String roleLevel){
		return udao.getSubRoleLevel(roleLevel);
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
		return udao.queryUser(organId, idCard, tellerId);
	}
	
	/**
	 * 删除用户详细信息
	 * @param userid
	 */
	public void delUserDetail(Long userid)
	{
		udao.delUserDetail(userid);
	}
	
	/**
	 * 获得用户详细信息
	 * @param ud
	 * @return 
	 */
	public UserDetail getUserDetail(Long userId)
	{
		return udao.getUserDetail(userId);
	}
	
	/**
	 * 保存用户详细信息
	 * @param ud
	 * @return 
	 */
	public int saveUserDetail(UserDetail ud)
	{
		return udao.saveUserDetail(ud);
	}
	
	
	/**
	 * 得到最新PKID的USERID
	 * @return USERID
	 */
	public Long getNewUserId()
	{
		return udao.getNewUserId();
	}
	
	
	
	public User getUser(Long pkid){
		Object o = udao.getObject(User.class,pkid);
		if(null!=o){
			User user = (User)o;
			UserRole userRole = udao.getUserRole(user.getPkid());
			if(null!=userRole){
				user.setRoleType(userRole.getRoleType());
			}
			return user;
		}
		return null;
	}
	
	public int saveUser(User user){
		return udao.saveUser(user);
	}
	
	public void removeUser(Long pkid){
		udao.removeUser(pkid);
	}

	public User getUser(String longname, String password) {
		
		return udao.getUser(longname,password);
	}
	public void updatePassword(Long pkid,String password)
	{
		 udao.updatePassword(pkid,password);
	}
	public void updatePassword_new(Long pkid,String password)
	{
		 udao.updatePassword_new(pkid,password);
	}
	public List getUsers(String  organ)
	{
		return udao.getUsers(organ);
	}

	public List getUsersByOrgan(String organ) {

        return udao.getUsersByOrgan(organ);
	}

	public List getUserReportsByUserID(Long pkid) {
		return udao.getUserReportsByUserID(pkid);
	}
	
	public List getUserReportsByGPID(Long pkid) {
		return udao.getUserReportsByGPID(pkid);
	}

	public List getUserReports(String date,Long userid,String showlevel) {
		List userReports = new ArrayList();
		List reports = rddao.getReports(null,null,new Long(-1),showlevel);
		if(reports!=null){
			for(int i=0;i<reports.size();i++){
				Report report = (Report)reports.get(i);
				UserReport userReport = new UserReport();
				userReport.setTypeId(report.getReportType());
				userReport.setReportId(report.getPkid());
				userReport.setReportName(report.getName());
				userReport.setTypeName(report.getRepname());
				userReport.setStatus(0);
				userReports.add(userReport);
			}
		}
		return userReports;
	}
	
	public void deleteUserReportsByUserId(Long pkid) {
	       
		udao.deleteUserReportsByUserId(pkid);
	}

	public void saveUserReport(UserReport ur) {
		udao.saveObject(ur);
	}
	
	public void setUserReportAss(Long userid){
		udao.setUserReportAss(userid);
	}
	
	/**
	 * 操作用户的报表权限，包含（消除报表权限，改变status，增加报表权限）
	 * @param userid
	 * @return
	 */
	public boolean operUserContrast(Long userid,Long groupType){
		
		if(groupType.intValue() == 1){
			//删除用户所有的报表权限
			this.deleteUserReportsByUserId(userid);
			//更新status字段
			String updateSql = "update umg_user set status = " + groupType + " where pkid = " + userid;
			//System.out.println("updateSql: [" + updateSql + "]");
			udao.exeAnySql(updateSql);
			//给用户增加相应的组权限
//			this.setUserReportAss(userid);
			List reportList = getAdminReports();
			if (reportList != null) {
				for (int i = 0; i < reportList.size(); i++) {
					UserReport ur = (UserReport) reportList.get(i);
					ur.setOperId(userid);
					udao.saveObject(ur);
				}
			}
			
		}else if(groupType.intValue() == 9){
			//删除用户所有的报表权限
			this.deleteUserReportsByUserId(userid);
			//更新status字段
			String updateSql = "update umg_user set status = " + groupType + " where pkid = " + userid;
			//System.out.println("updateSql: [" + updateSql + "]");
			udao.exeAnySql(updateSql);
		}else{
			//删除用户所有的报表权限
			this.deleteUserReportsByUserId(userid);
			//更新status字段
			String updateSql = "update umg_user set status = " + groupType + " where pkid = " + userid;
			//System.out.println("updateSql: [" + updateSql + "]");
			udao.exeAnySql(updateSql);
			//给用户增加相应的组权限	
			
			//2007.4.10 兼容SqlServer数据库
			String addSql = "";
			if ('s'==SysConfig.DB){
				 addSql = "insert into rep_oper_contrast(operid,typeid,repid) " +
					"select " + userid + ",typeid,repid from rep_oper_contrast " +
					"where operid=" + (90000000l + groupType.longValue());
				 udao.exeAnySql(addSql);
			}else if('i'==SysConfig.DB){
				 List userReportList = udao.getUserReport(90000000l + groupType.longValue());
				 List saveObjl = new ArrayList();
					//System.out.println(userReportList.size());
					for(Iterator itr = userReportList.iterator(); itr.hasNext();) {
						Object [] obj = (Object[])itr.next();
						UserReport ur = new UserReport();
						ur.setOperId(userid);
						ur.setTypeId((Long)obj[0]);
						ur.setReportId((Long)obj[1]);
//						udao.saveObject(ur);
						saveObjl.add(ur);
					}
					udao.batchSaveVO(saveObjl);
			}
			else{
				 addSql = "insert into rep_oper_contrast(pkid,operid,typeid,repid) " +
				"select " + DBDialect.genSequence("rep_oper_contrast_seq")+ "," + userid + ",typeid,repid from rep_oper_contrast " +
				"where operid=" + (90000000l + groupType.longValue());
				 udao.exeAnySql(addSql);
			}
			
			//System.out.println("addSql: [" + addSql + "]");
		}


		return false;
	}
	
	/**
	 * 得到admin用户所创建报表
	 * @return
	 */
	public List getAdminReports() {
		List adminReportList = new ArrayList();
		List admin = udao.getAdminUser();
		String adminId = "";
		for(Iterator itr = admin.iterator(); itr.hasNext();) {
			User user = (User)itr.next();
			if(adminId.equals("")) {
				adminId = user.getPkid().toString();
			}else {
				adminId += ","+user.getPkid().toString();
			}
		}
		List reportList = rddao.getAdminCreateReport(adminId);
		if(reportList.size() > 0) {
			for(Iterator iter = reportList.iterator(); iter.hasNext();) {
				Report report = (Report)iter.next();
				UserReport userReport = new UserReport();
				userReport.setTypeId(report.getReportType());
				userReport.setReportId(report.getPkid());
				userReport.setReportName(report.getName());
				userReport.setTypeName(report.getReportTypeName());
				userReport.setStatus(0);
				adminReportList.add(userReport);
			}
		}
		return adminReportList;
	}
	
	/**
	 * 先删除用户的报表权限，然后再付用户新的报表权限（该组的权限）
	 * 用关联用户与组的报表权限（在修改了组的报表权限时）
	 * @return
	 */
	public boolean deleteAndAddUserPurview(Long userid,Long groupType)
	{
		//删除用户所有的报表权限
		this.deleteUserReportsByUserId(userid);
		//给用户增加相应的组权限	
		
		//2007.4.10 兼容SqlServer数据库
		String addSql = "";
		if ('s'==SysConfig.DB){
			 addSql = "insert into rep_oper_contrast(operid,typeid,repid) " +
				"select " + userid + ",typeid,repid from rep_oper_contrast " +
				"where operid=" + (90000000l + groupType.longValue());
			 udao.exeAnySql(addSql);
		}else if('i'==SysConfig.DB){
			
			 List userReportList = udao.getUserReport(90000000l + groupType.longValue());
			 List saveObjl = new ArrayList();
				//System.out.println(userReportList.size());
				for(Iterator itr = userReportList.iterator(); itr.hasNext();) {
					Object [] obj = (Object[])itr.next();
					UserReport ur = new UserReport();
					ur.setOperId(userid);
					ur.setTypeId((Long)obj[0]);
					ur.setReportId((Long)obj[1]);
//					udao.saveObject(ur);
					saveObjl.add(ur);
				}
				udao.batchSaveVO(saveObjl);
		}else{
			 addSql = "insert into rep_oper_contrast(pkid,operid,typeid,repid) " +
			"select " + DBDialect.genSequence("rep_oper_contrast_seq")+ "," + userid + ",typeid,repid from rep_oper_contrast " +
			"where operid=" + (90000000l + groupType.longValue());
			 udao.exeAnySql(addSql);
		}
		//System.out.println("addSql: [" + addSql + "]");
		
		return true;
	}

	/**
	 * 根据用户登陆名称查找用户
	 * @param loginName
	 * 			用户登陆名称
	 */
	public User getUser(String loginName){
		return udao.getUser(loginName);
	}
	
	/**
	 * 更新用户信息
	 * @param user
	 * 			用户对象
	 */
	public void updateUser(User user){
		udao.updateUser(user);
	}
	
	public int updateRepidByRepid(Long newRepid, Long oldRepid){
		return udao.updateRepidByRepid(newRepid, oldRepid);
	}
	/**
	 * 得到该地区下所有用户(除了管理员和机构管理员)
	 * @param areaId
	 * @return
	 */
	public List getUserByArea(String areaId, Long userId){
		List userList = new ArrayList();
		List list = udao.getUserByArea(areaId,userId);
		for(Iterator itr = list.iterator();itr.hasNext();){
			Object [] object = (Object[])itr.next();
			Object [] obj = new Object[2];
			obj[0] = (Long)object[0];
			obj[1] = "["+(String)object[2]+"]-->["+(String)object[1]+"]";
			userList.add(obj);
		}
		return userList;
	}
	/**
	 * 根据报表id,报表类型id得到有权限的用户
	 * @param typeId
	 * @param repId
	 * @return
	 */
	public List getRepUser(Long typeId, Long repId, Long userId){
		List repUserList = new ArrayList();
		List list = udao.getRepUser(typeId, repId, userId);
		for(Iterator itr = list.iterator();itr.hasNext();){
			Object [] object = (Object[])itr.next();
			Object [] obj = new Object[2];
			obj[0] = (Long)object[0];
			obj[1] = "["+(String)object[2]+"]-->["+(String)object[1]+"]";
			repUserList.add(obj);
		}
		return repUserList;
	}

	public void saveUserReport(Long reportId, Long typeId, String usersId, Long uId){
		udao.delUserRepByRepIdAndTypeId(reportId, typeId, uId);
		String [] userId = usersId.split(",");
		for(int i = 0; i < userId.length; i++) {
			UserReport userReport = new UserReport();
			userReport.setOperId(new Long(userId[i]));
			userReport.setReportId(reportId);
			userReport.setTypeId(typeId);
			udao.saveObject(userReport);
		}
	}
	
	/**
	 * 20071127 lxk
	 * 根据机构Id,用户角色Id得到指定角色用户
	 * @param organId
	 * @param roleId
	 * @return
	 */
	public User getAppointRoleUser(String organId, Long roleId)
	{
		return udao.getAppointRoleUser(organId, roleId);
	}
	/**
	 * 根据用户Id和报表Id得到用户报表权限
	 * @param operId
	 * @param reportId
	 * @return
	 */
	public List getUserReport(int operId, int reportId){
		return udao.getUserReport(operId, reportId);
	}
	/**
	 * 得到用户权限对象
	 * @param userid 用户id
	 * @return UserRole
	 */
	public UserRole getUserRole(Long userid)
	{
		return udao.getUserRole(userid);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.usermanage.services.UserService#getUsers(java.lang.String, java.lang.Long)
	 */
	public List getUsers(String organId, Long roleType)
	{
		return udao.getUsers(organId, roleType);
	}
	
	public List getUserListByOrganIdsAndRoleIds(String organIds, String roleIds)
	{
		return udao.getUserListByOrganIdsAndRoleIds(organIds, roleIds);
	}
	
	public List getRolesAll(){
		return udao.getRolesAll();
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.usermanage.services.UserService#getRole(java.lang.String)
	 */
	public Role getRole(String type)
	{
		return udao.getRole(type);
	}

	/**
	 * @see com.krm.slsint.usermanage.services.UserService#getUserReport(java.lang.Long)
	 */
	public List getUserReport(Long userId)
	{
		return udao.getUserReport(userId);
	}
	
	public User getAdminUser()
	{
		return (User)udao.getAdminUser().get(0);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.usermanage.services.UserService#getPlatUserGpInSubSys(java.lang.Long, java.lang.String)
	 */
	public List getPlatUserGpInSubSys(Long userId, String subSysFlag) {
		return udao.getPlatUserGpInSubSys(userId,subSysFlag);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.usermanage.services.UserService#getSendMailUsers(java.lang.String)
	 */
	public List getSendMailUsers(String code) {
		return udao.getSendMailUsers(code);
	}
	
	/**
	 * 根据用户ID查找用户
	 * @param userId
	 * 			用户ID
	 */
	public User getUserById(String userId){
		return udao.getUserById(userId);
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
		
		return udao.getUsersByOrganLogonNameUserNmae(organ, loginname, username);
	}
	public void deleteUserReportsByUserId(Long reportId, Long typeId, Long userId){
		udao.deleteUserReportsByUserId(reportId,typeId,userId);
	}
	/**
	 * 删除，保存用户权限
	 * @param ur
	 */
	public void DelsaveUserReport(List<User>  userlist,List<UserReport> userreportlist){
		udao.DelsaveUserReport(userlist,userreportlist);
	}
	@Override
	public List getUserBYOrganArea(String areaCode) {
		// TODO Auto-generated method stub
		return udao.getUserBYOrganArea(areaCode);
	}
	public List getUserList(String username){
		return udao.getUserList(username);
	}
	/**
	 * 根据机构
	 * @param organ 用户所选机构
	 * @param logonname 用户登陆名称左右全部模糊
	 * @param username  用户名称右边进行模糊
	 * @return
	 * @ add by ydw 2011-11-21
	 */
	public List getAllOrgan(String arerCode) {
		arerCode = arerCode.substring(0, arerCode.lastIndexOf("0"));
		for (int i = arerCode.length(); ; i--){
			if(arerCode.charAt(i-1) != '0'){
				arerCode = arerCode.substring(0, i);
				break;
			}
		}
		return udao.getAllOrgan(arerCode);
	
	}

	@Override
	public List getUsersByOrgan(String organ, String userid, String userName) {
		 return udao.getUsersByOrgan(organ, userid, userName);
				 
				 
	}

	@Override
	public List getUsersByOLUNmae(String organ, String loginname,
			String username, int isadmin, int idxid) {
		return	udao.getUsersByOLUNmae(organ, loginname, username, isadmin, idxid);
	}
}