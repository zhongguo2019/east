package com.krm.ps.sysmanage.usermanage.dao;

import java.util.List;

import com.krm.ps.framework.dao.DAO;
import com.krm.ps.sysmanage.usermanage.vo.Role;

public interface UserRoleDAO extends DAO{
	/**
	 * 查询用户权限
	 * @return {@link UserRole}对象列表
	 */
	public List getUserRole();
	/**
	 * 查询用户子系统关系
	 * @return
	 */
	public List getUserSys(String sysFlag);
	/**
	 * 判断用户是否有权限进入子系统
	 * @param userId
	 * @param gpId
	 * @return
	 */
	public List getGPuser(String userId,String gpId);
	
	/**
	 * @param userid
	 * 根据role_type得到role_level
	 */
	public String getUserLevel(Long userid);

	/**
	 * 查询权限列表
	 * @return List<Object []>
	 */
	public List getMenu(Long systemId);
	/**
	 * 查询父级列表
	 * @return List<Object []>
	 */
	public List getPaneMenu(Long systemId);
	/**
	 * 查询权限信息
	 * @param pkid
	 * @return {@link Role}对象列表
	 */
	public List getRoleInfo(Long pkid);
	/**
	 * 查询用户权限Menu
	 * @param roleType
	 * @return List<Object []>
	 */
	public List getRoleMenu(Long systemId,Long roleType);
	/**
	 * 删除权限
	 * @param pkid
	 */
	public void delRole(Long pkid);
	/**
	 * 删除用户菜单权限
	 * @param roleType
	 */
	public void delRoleRight(Long roleType);
	/**
	 * 查询RoleRight最大pkid
	 * @return
	 */
	public Long getMaxPkid();
	/**
	 * 添加RoleRight
	 * @param pkid
	 * @param roleId
	 * @param menuId
	 */
	public void addRoleRight(Long pkid,Long roleId,Long menuId);
	/**
	 * 更新Role
	 * @param role
	 */
	public void updateRole(Role role);
	/**
	 * 查询RoleType
	 * @return
	 */
	public List getRoleType();
	/**
	 * 删除某系统下的角色的所有权限
	 * @param systemId 系统id
	 * @param roleType 角色id
	 */
	void delRoleRight(Long systemId, Long roleType);
	/**
	 * 通过用户id获得角色id
	 * @param pkid 用户id
	 * @return
	 */
	public Long getRoleType(Long pkid);

}
