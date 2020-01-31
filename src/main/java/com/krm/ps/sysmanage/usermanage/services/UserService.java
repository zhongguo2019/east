package com.krm.ps.sysmanage.usermanage.services;

import java.util.List;

import com.krm.ps.sysmanage.usermanage.dao.UserDAO;
import com.krm.ps.sysmanage.usermanage.vo.Role;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.sysmanage.usermanage.vo.UserDetail;
import com.krm.ps.sysmanage.usermanage.vo.UserReport;
import com.krm.ps.sysmanage.usermanage.vo.UserRole;

public interface UserService {
	
	public void setUserDAO(UserDAO dao);
	
	/**
	 * 根据机构号查询
	 * @param organId
	 * 
	 */
	public List queryUserDetail(String allOrganCode,String idCard,String tellerId);
	
	/**
	 * 获取整个树的数据
	 */
	public List getAllUserDetail(String organcode,int idx);
	
	/**
	 * 根据机构号查询
	 * @param organId
	 * 
	 */
//	public List queryUserDetailByOrganId(String organId,String organcode,int idx);
	public List queryUserDetailByOrganId(String organId);
	/**
	 * 根据用户名和IP查询对象
	 * @author LC
	 * @param logname
	 * @param ipAddr
	 */
	public User getUserByIp(String logonname,String ipaddr);
	
	
	/**
	 * 根据机构号，身份证,柜员号查询
	 * @param organId
	 * @param idCard
	 * @param tellerId
	 * 
	 */
	public List queryUser(String organId,String idCard,String tellerId);
	
	/**
	 * 删除用户详细信息
	 * @param userid
	 */
	public void delUserDetail(Long userid);
	
	/**
	 * 获得用户详细信息
	 * @param ud
	 * @return 
	 */
	public UserDetail getUserDetail(Long userId);
	
	/**
	 * 保存用户详细信息
	 * @param ud
	 * @return 
	 */
	public int saveUserDetail(UserDetail ud);
	
	/**
	 * 得到最新PKID的USERID
	 * @return USERID
	 */
	public Long getNewUserId();
	
	/**
	 * 得到用户列表
	 * @return {@link User}对象列表
	 */
	public List getUsers();
	/**
	 * 得到权限列表
	 * @return {@link Role}对象列表
	 */
	
	public List getRoles();
	
	
	/**
	 * 根据用户级别，得到子权限级别列表
	 */
	public List getSubRoleLevel(String roleLevel);
	
	
	/**
	 * 根据用户名,密码查询用户对象
	 * @param longname
	 * @param password
	 * @return User
	 */
	public User getUser(String  longname,String  password);
	/**
	 * 根据pkid得到用户信息
	 * @param pkid
	 * @return User
	 */
	public User getUser(Long pkid);
	/**
	 * 保存用户信息
	 * @param user
	 * @return
	 */
	public int saveUser(User user);
	/**
	 * 删除用户
	 * @param pkid
	 */
	public void removeUser(Long pkid);
	/**
	 * 更新用户密码
	 * @param pkid
	 * @param password
	 */
	public void updatePassword(Long pkid,String password);
	/**
	 * 更新用户密码，以及记录更新密码的时间。
	 * @param pkid
	 * @param password
	 */
	public void updatePassword_new(Long pkid, String password);
	
	/**
	 * 根据机构得到用户信息
	 * @param organ
	 * @return {@link User}对象列表
	 */
	public List getUsers(String  organ);
	/**
	 * 根据机构得到用户信息
	 * @param organ
	 * @return {@link User}对象列表
	 */
	public List getUsersByOrgan(String  organ);
	
	/**
	 * 根据机构id得到用户列表
	 * @param organ
	 * @param userid
	 * @param userName
	 * @return {@link User}对象列表
	 */
	public List getUsersByOrgan(String organ,String userid ,String userName);
	/**
	 * 根据用户id得到用户报表权限
	 * @param pkid
	 * @return {@link UserReport}对象列表
	 */
	public List getUserReportsByUserID(Long pkid);
	/**
	 * 根据GPid得到用户报表权限
	 * @param pkid
	 * @return {@link UserReport}对象列表
	 */
	public List getUserReportsByGPID(Long pkid);
	/**
	 * 得到用户报表权限信息
	 * @param date
	 * @param userid
	 * @return {@link UserReport}对象列表
	 */
	public List getUserReports(String date,Long userid,String showlevel);
	/**
	 * 删除用户报表权限
	 * @param pkid
	 */
	public void deleteUserReportsByUserId(Long pkid);
	/**
	 * 保存用户报表权限
	 * @param ur
	 */
	public void saveUserReport(UserReport ur);
	
	/**
	 * 根据用户Id和报表Id得到用户报表权限
	 * @param operId
	 * @param reportId
	 * @return
	 */
	public List getUserReport(int operId, int reportId);
	
	/**
	 * 保存用户报表权限
	 * @param reportId
	 * @param typeId
	 * @param usersId
	 * @param uId
	 */
	public void saveUserReport(Long reportId, Long typeId, String usersId, Long uId);
	
	/**
	 * 更新用户信息
	 * @param user
	 * 			用户对象
	 */
	public void updateUser(User user);
	
	/**
	 * 根据用户登陆名称查找用户
	 * @param loginName
	 * 			用户登陆名称
	 */
	public User getUser(String loginName);

	/**
	 * 关联所有报表
	 * @param userid
	 */
	public void setUserReportAss(Long userid);
	
	/**
	 * 操作用户的报表权限，包含（消除报表权限，改变status，增长报表权限）
	 * @param userid
	 * @return
	 */
	public boolean operUserContrast(Long userid,Long groupType);
	
	/**
	 * 先删除用户的报表权限，然后再付用户新的报表权限（该组的权限）
	 * 用关联用户与组的报表权限（在修改了组的报表权限时）
	 * @return
	 */
	public boolean deleteAndAddUserPurview(Long userid,Long groupType);
	
	/**
	 * 20070825 周浩 报表克隆
	 * 将原来的repid更新为新的repid
	 * @param newRepid
	 * @param oldRepid
	 * @return
	 */
	public int updateRepidByRepid(Long newRepid, Long oldRepid);
	/**
	 * 得到该地区下所有用户(除了管理员和机构管理员)
	 * @param areaId
	 * @return
	 */
	public List getUserByArea(String areaId,Long userId);
	/**
	 * 根据报表id,报表类型id得到有权限的用户
	 * @param typeId
	 * @param repId
	 * @return
	 */
	public List getRepUser(Long typeId, Long repId,Long userId);
	
	/**
	 * 20071127 lxk
	 * 根据机构Id,用户角色Id得到指定角色用户
	 * @param organId
	 * @param roleId
	 * @return
	 */
	public User getAppointRoleUser(String organId, Long roleId);
	/**
	 * 得到admin用户所创建报表
	 * @return
	 */
	public List getAdminReports();
	/**
	 * 得到用户权限对象
	 * @param userid 用户id
	 * @return UserRole
	 */
	public UserRole getUserRole(Long userid);

	/**
	 * 根据机构,角色得到用户
	 * @param organId
	 * @param roleType
	 * @return
	 */
	public List getUsers(String organId, Long roleType);
	
	/**
	 * <p>根据机构及角色得到人员</p>
	 * 
	 * 此时机构与角色均为多个。风险配置中，指定了相关信息要发给哪些机构的哪些角色，通过此方式即能获得所有人员
	 * 
	 * @param organIds 机构ID串，以，号隔开
	 * @param roleIds 角色ID串，以，号隔开
	 * @return
	 * @author 皮亮
	 * @version 创建时间：2010-3-30 下午04:05:13
	 */
	public List getUserListByOrganIdsAndRoleIds(String organIds, String roleIds);
	
		/**
	 * 得到权限列表
	 * @return {@link Role}对象列表
	 */
	public List getRolesAll();
	
	/**
	 * 根据角色type得到角色
	 */
	public Role getRole(String type);
	
	/**
	 * 根据用户得到用户权限报表
	 * @param userId
	 * @return
	 */
	public List getUserReport(Long userId);
	
	/**
	 * 取得管理员用户信息的方法
	 * */
	public User getAdminUser();
	
	/**
	 * 获取用户在当前子系统中拥有的组权限，即用户关联的分组
	 * @param userId				用户Id
	 * @param subSysFlag		当前子系统标识
	 * @return
	 */
	public List getPlatUserGpInSubSys(Long userId, String subSysFlag);
	
	/**
	 * 根据用户ID查找用户
	 * @param userId
	 * 			用户ID
	 */
	public User getUserById(String userId);
	
	/**
	 * 发文时，通讯录处把系统管理员列出来，实现给系统管理员发信
	 * @param code	机构编码
	 * @return
	 */
	public List getSendMailUsers(String code);
	
	/**
	 * 根据机构、登陆名称、用户名称进行模糊查询
	 * @param organ 用户所选机构
	 * @param logonname 用户登陆名称左右全部模糊
	 * @param username  用户名称右边进行模糊
	 * @return
	 * @ add by ydw 2011-11-21
	 */
	public List getUsersByOrganLogonNameUserNmae(String organ,String loginname,String username);
	/**
	 * 根据用户、报表、报表类型删除用户报表权限
	 * @param reportId
	 * @param typeId
	 * @param userId
	 * @ add by ydw 2012-03-20
	 */
	public void deleteUserReportsByUserId(Long reportId, Long typeId, Long userId);
	/**
	 * 根据用户传入的地区 查询出用户
	 * @param areaCode
	 * @return
	 */
	public List getUserBYOrganArea(String areaCode);
	
	public List getUserList(String username);
	
	/**
	 * 删除，保存用户权限
	 * @param ur
	 */
	public void DelsaveUserReport(List<User>  userlist,List<UserReport> userreportlist);

	public List getAllOrgan(String areaCode);
	
	public List getUsersByOLUNmae(String organ,String loginname,String username ,int isadmin, int idxid);
}
