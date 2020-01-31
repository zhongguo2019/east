package com.krm.ps.sysmanage.usermanage.services.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.krm.ps.sysmanage.usermanage.bo.Menu;
import com.krm.ps.sysmanage.usermanage.dao.UserRoleDAO;
import com.krm.ps.sysmanage.usermanage.services.UserRoleService;
import com.krm.ps.sysmanage.usermanage.vo.Role;
/*
 *  添加日期:2006年9月13日
 *  添加人:赵鹏程
 */
public class UserRoleServiceImpl implements UserRoleService{
	
	private UserRoleDAO urDAO;
	
	public void setUserRoleDAO(UserRoleDAO urDAO){
		this.urDAO = urDAO;
	}

	
	/**
	 * @param userid
	 * 根据role_type得到role_level
	 */
	public String getUserLevel(Long userid)
	{
		return urDAO.getUserLevel(userid);
	}
	
	/* (non-Javadoc)
	 * @see com.krm.slsint.usermanage.services.UserRoleService#getUserRole()
	 */
	public List getUserRole() {
		//urDAO.getUserRole();
		return urDAO.getUserRole();
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.usermanage.services.UserRoleService#getMenu()
	 */
	public ArrayList getMenu() {
		ArrayList al = new ArrayList();
		ArrayList pane = new ArrayList();
		List list = urDAO.getMenu(null);
		List paneMenu = urDAO.getPaneMenu(null);
		Iterator it = list.iterator();
		while(it.hasNext()){
			Menu menu = new Menu();
			Object[] obj = (Object[])it.next();
			menu.setId(String.valueOf((Long)obj[0]));//zhaoerxi
			menu.setName((String)obj[1]);
			menu.setPane(String.valueOf((Long)obj[2]));//zhaoerxi
			al.add(menu);
		}
		Iterator it1 = paneMenu.iterator();
		while(it1.hasNext()){
			Menu m = new Menu();
			Object [] o = (Object[])it1.next();
			m.setId(String.valueOf(o[0]));
			m.setName((String)o[1]);
			m.setPane(String.valueOf(o[2]));
			pane.add(m);
		}
		for(int i=0;i<pane.size();i++){
			for(int j=0;j<al.size();j++){
				if(((Menu)al.get(j)).getPane().equals(((Menu)pane.get(i)).getId())){
					((Menu)al.get(j)).setName("【"+((Menu)pane.get(i)).getName()+"】"+((Menu)al.get(j)).getName());					
				}
			}
		}
		return al;
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.usermanage.services.UserRoleService#getRoleInfo(java.lang.Long)
	 */
	public Role getRoleInfo(Long pkid) {		
		Role role = new Role();
		List l = urDAO.getRoleInfo(pkid);
		Iterator it = l.iterator();
		while(it.hasNext()){
			role = (Role)it.next();
		}
		return role;
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.usermanage.services.UserRoleService#getRoleMenu(java.lang.Long)
	 */
	public List getRoleMenu(Long roleType) {
		List l = new ArrayList();
		ArrayList pane = new ArrayList();
		List list = urDAO.getRoleMenu(null,roleType);
		List paneMenu = urDAO.getPaneMenu(null);
		Iterator it = list.iterator();
		while(it.hasNext()){
			Menu menu = new Menu();
			Object [] obj = (Object[])it.next();			
			menu.setId((String)obj[0]);
			menu.setName((String)obj[1]);
			menu.setPane((String)obj[2]);
			l.add(menu);
		}
		Iterator it1 = paneMenu.iterator();
		while(it1.hasNext()){
			Menu m = new Menu();
			Object [] o = (Object[])it1.next();
			m.setId((String)o[0]);
			m.setName((String)o[1]);
			m.setPane((String)o[2]);
			pane.add(m);
		}		
		for(int i=0;i<pane.size();i++){
			for(int j=0;j<l.size();j++){				
				if(((Menu)l.get(j)).getPane().equals(((Menu)pane.get(i)).getId())){
					((Menu)l.get(j)).setName("【"+((Menu)pane.get(i)).getName()+"】"+((Menu)l.get(j)).getName());					
				}
			}
		}
		return l;
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.usermanage.services.UserRoleService#delUserRole(java.lang.Long, java.lang.Long)
	 */
	public void delUserRole(Long pkid, Long roleType) {
		//urDAO.delRole(pkid);
		urDAO.delRole(pkid);
		//urDAO.delRoleRight(roleType);
		urDAO.delRoleRight(roleType);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.usermanage.services.UserRoleService#addRole(com.krm.slsint.usermanage.vo.Role, java.lang.String[])
	 */
	public void addRole(Role role, String[] menuId) {
		//urDAO.saveObject();
		urDAO.saveObject(role);
		//urDAO.addRoleRight(pkid,roleId,menuId);
		for(int i=0;i<menuId.length;i++){
			Long pkid = new Long(urDAO.getMaxPkid().longValue()+1);
			Long roleId = role.getRoleType();
			Long menu = Long.valueOf(menuId[i]);
			urDAO.addRoleRight(pkid,roleId,menu);
		}
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.usermanage.services.UserRoleService#delRoleRight(java.lang.Long)
	 */
	public void delRoleRight(Long systemId,Long roleType) {
		urDAO.delRoleRight(systemId,roleType);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.usermanage.services.UserRoleService#updateRole(com.krm.slsint.usermanage.vo.Role)
	 */
	public void updateRole(Role role) {
		//urDAO.updateRole(role);
		urDAO.updateRole(role);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.usermanage.services.UserRoleService#addRoleRight(com.krm.slsint.usermanage.vo.Role, java.lang.String[])
	 */
	public void addRoleRight(Role role, String[] menuId) {
		for(int i=0;i<menuId.length;i++){
			Long pkid = new Long(urDAO.getMaxPkid().longValue()+1);
			Long roleId = role.getRoleType();
			Long menu = Long.valueOf(menuId[i]);
			urDAO.addRoleRight(pkid,roleId,menu);
		}
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.usermanage.services.UserRoleService#getRoleType()
	 */
	public String getRoleType() {
		//urDAO.getRoleType();
		List list = urDAO.getRoleType();
		Iterator it = list.iterator();
		String roleType = "";
		while(it.hasNext()){
			String str = (String)it.next();
			if(roleType.equals("")){
				roleType = str;
			}else{
				roleType += (","+str);
			}				
		}
		return roleType;
	}

	@Override
	public ArrayList getMenuBySystem(Long id) {
		ArrayList al = new ArrayList();
		ArrayList pane = new ArrayList();
		List list = urDAO.getMenu(id);
		List paneMenu = urDAO.getPaneMenu(id);
		Iterator it = list.iterator();
		while(it.hasNext()){
			Menu menu = new Menu();
			Object[] obj = (Object[])it.next();
			menu.setId(String.valueOf((Long)obj[0]));
			menu.setName((String)obj[1]);
			menu.setPane(String.valueOf((Long)obj[2]));
			al.add(menu);
		}
		Iterator it1 = paneMenu.iterator();
		while(it1.hasNext()){
			Menu m = new Menu();
			Object [] o = (Object[])it1.next();
			m.setId(String.valueOf((Long)o[0]));
			m.setName((String)o[1]);
			m.setPane(String.valueOf((Long)o[2]));
			pane.add(m);
		}
		for(int i=0;i<pane.size();i++){
			for(int j=0;j<al.size();j++){
				if(((Menu)al.get(j)).getPane().equals(((Menu)pane.get(i)).getId())){
					((Menu)al.get(j)).setName("【"+((Menu)pane.get(i)).getName()+"】"+((Menu)al.get(j)).getName());					
				}
			}
		}
		return al;
	}

	@Override
	public List getRoleMenuBySystem(Long id, Long roleType) {
		List l = new ArrayList();
		ArrayList pane = new ArrayList();
		List list = urDAO.getRoleMenu(id,roleType);
		List paneMenu = urDAO.getPaneMenu(id);
		Iterator it = list.iterator();
		while(it.hasNext()){
			Menu menu = new Menu();
			Object [] obj = (Object[])it.next();			
			menu.setId(String.valueOf((Long)obj[0]));
			menu.setName((String)obj[1]);
			menu.setPane(String.valueOf((Long)obj[2]));
			l.add(menu);
		}
		Iterator it1 = paneMenu.iterator();
		while(it1.hasNext()){
			Menu m = new Menu();
			Object [] o = (Object[])it1.next();
			m.setId(String.valueOf((Long)o[0]));
			m.setName((String)o[1]);
			m.setPane(String.valueOf((Long)o[2]));
			pane.add(m);
		}		
		for(int i=0;i<pane.size();i++){
			for(int j=0;j<l.size();j++){				
				if(((Menu)l.get(j)).getPane().equals(((Menu)pane.get(i)).getId())){
					((Menu)l.get(j)).setName("【"+((Menu)pane.get(i)).getName()+"】"+((Menu)l.get(j)).getName());					
				}
			}
		}
		return l;
	}

	@Override
	public Long  getRoleType(Long pkid) {
		Long roleId = urDAO.getRoleType(pkid);
		return roleId;
	}

}
