package com.krm.ps.sysmanage.usermanage.services;

import java.util.ArrayList;
import java.util.List;

import com.krm.ps.sysmanage.usermanage.vo.Role;

public interface UserRoleService {
	/**
	 * 查询用户权限
	 * @return {@link UserRole}对象列表
	 */
	public List getUserRole();
	/**
	 * 查询权限列表
	 * @return List<Object []>
	 */
	public ArrayList getMenu();
	
	/**
	 * @param userid
	 * 根据role_type得到role_level
	 */
	public String getUserLevel(Long userid);
	
	/**
	 * 查询权限信息
	 * @param pkid
	 * @return {@link Role}对象列表
	 */
	public Role getRoleInfo(Long pkid);
	/**
	 * 查询用户权限Menu
	 * @param roleType
	 * @return List<Object []>
	 */
	public List getRoleMenu(Long roleType);
	/**
	 * 删除用户权限
	 * @param pkid
	 * @param roleType
	 */
	public void delUserRole(Long pkid,Long roleType);
	/**
	 * 删除用户菜单权限
	 * @param roleType
	 */
	public void delRoleRight(Long systemId,Long roleType);
	/**
	 * 添加用户权限
	 * @param role
	 * @param menuId
	 */
	public void addRole(Role role,String [] menuId);
	/**
	 * 更新Role
	 * @param role
	 */
	public void updateRole(Role role);
	/**
	 * 添加用户菜单权限
	 * @param role
	 * @param menuId
	 */
	public void addRoleRight(Role role,String [] menuId);
	/**
	 * 查询RoleType
	 * @return
	 */
	public String getRoleType();
	/**
	 * 根据用户id查询角色号
	 * @param pkid
	 */
	public Long getRoleType(Long pkid);
	/**
	 * 查询出某系统下的菜单
	 * @return
	 */
	public ArrayList getMenuBySystem(Long id);
	/**
	 * 查询出某系统中有权限的菜单
	 * @param id
	 * @param roleType
	 * @return
	 */
	public List getRoleMenuBySystem(Long id, Long roleType);
	

}
