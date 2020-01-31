package com.krm.ps.sysmanage.usermanage.services.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowCallbackHandler;

import com.krm.ps.sysmanage.usermanage.bo.Menu;
import com.krm.ps.util.DBDialect;
import com.krm.ps.util.FuncConfig;
import com.krm.ps.util.SysConfig;

public class MenuManager {
	protected final Log log = LogFactory.getLog(MenuManager.class);
	private DataSource dataSource;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public List getAllMenu() {
		String tableName = FuncConfig.getProperty("menu.tableName", "menu");
		String sql = "SELECT * FROM "  +tableName + " ORDER BY pane,id";
		return getMenu(sql);
	}

	public List getMenuByRole(long roleType) {
		String tableName = FuncConfig.getProperty("menu.tableName", "menu")+" menu";
		String sql = "";
		if(SysConfig.DB=='d'){
			sql = "SELECT * FROM "+tableName+" JOIN role_right ON role_id = "
				+ roleType + " AND menu.id = cast(char(integer(menu_id)) as char(6)) ORDER BY pane,id";
		}else{
			sql = "SELECT * FROM "+tableName+" JOIN role_right ON role_id = "
				+ roleType + " AND menu.id = menu_id ORDER BY pane,id";
		}
		return getMenu(sql);
	}
	
	
	public List getMenuByUser(long user_id) {
		String menu_id = "";
		String tableName = FuncConfig.getProperty("menu.tableName", "menu")+" menu";
		
		if(SysConfig.DB=='d'){
			menu_id = "cast(char(integer(menu_id)) as char(6))";
		}else{
			menu_id = "menu_id";
		}
		String sql = "SELECT DISTINCT menu.* FROM "+tableName
				+ " JOIN (role_right JOIN umg_user_role"
				+ " ON role_id = role_type AND user_id = "
				+ user_id
				+ ") ON (menu.id = " + menu_id +"  OR menu.id = "
				+ DBDialect.conStr(DBDialect.substring(menu_id, 1, 2),
						"'0000'") + ")"// 加上父菜单
				+ " ORDER BY pane,id";
		return getMenu(sql);
	}
	
	/**
	 * 新增根据用户userId查询菜单方法
	 * plat_user_gp、role_right、menu
	 * @param user_id
	 * @return
	 */
	public List getMenuByUserId(long user_id) {
		String menu_id = "";
		
		if(SysConfig.DB=='d'){
			menu_id = "cast(char(integer(menu_id)) as char(6))";
		}else{
			menu_id = "menu_id";
		}
		String sql = "SELECT DISTINCT menu.* FROM menu"
				+ " JOIN (role_right JOIN plat_user_gp"
				+ " ON role_id = gp_id AND flag='2' AND user_id = "
				+ user_id
				+ ") ON (menu.id = " + menu_id +"  OR menu.id = "
				+ DBDialect.conStr(DBDialect.substring(menu_id, 1, 2),
						"'0000'") + ")"// 加上父菜单
				+ " ORDER BY pane,id";
		return getMenu(sql);
	}
	
	public Menu getMenuByURL(String url){
		String tableName = FuncConfig.getProperty("menu.tableName", "menu");
		
		String [] str = url.split("/");
		String sql = "SELECT * FROM " +tableName+ " m WHERE m.url = '"+ str[str.length-1] +"' ";
		return getUserOperMenu(sql);
	}
	
	public Menu getParentMenuByCode(String code){
		String tableName = FuncConfig.getProperty("menu.tableName", "menu");
		String sql = "SELECT * FROM " + tableName+ " m WHERE m.id = '"+code+"'";
		return getUserOperMenu(sql);
	}
	
	private Menu getUserOperMenu(String sql) {
		final Menu m = new Menu();
		JdbcTemplate jt = new JdbcTemplate(dataSource);
		jt.query(sql, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
			}
		}, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				String id = rs.getString("id");
				m.setId(id);
				m.setPane(rs.getString("pane"));
				m.setName(rs.getString("name"));
				m.setResource(rs.getString("url"));
			}
		});
		return m;
	}



	private List getMenu(String sql) {
		final SortedMap ms = new TreeMap();
		JdbcTemplate jt = new JdbcTemplate(dataSource);
		jt.query(sql, new PreparedStatementSetter() {
			public void setValues(PreparedStatement ps) throws SQLException {
			}
		}, new RowCallbackHandler() {
			public void processRow(ResultSet rs) throws SQLException {
				Menu m = new Menu();
				String id = rs.getString("id");
				m.setId(id);
				m.setName(rs.getString("name"));
				m.setResource(rs.getString("url"));
				String parentId = rs.getString("pane");
				parentId=parentId.replaceAll(" ", "");
				if ("0".equals(parentId)) {
					ms.put(id, m);
				} else {
					Menu parent = (Menu) ms.get(parentId);
					if (parent != null) {
						List subMenus = parent.getSubMenus();
						if (subMenus == null) {
							subMenus = new ArrayList();
							parent.setSubMenus(subMenus);
						}
						subMenus.add(m);
						m.setParent(parent);
					}
				}
			}
		});
		List menus = new ArrayList();
		menus.addAll(ms.values());

		return menus;
	}

	/**
	 * 通过菜单Id获取菜单
	 * @param id
	 * @return
	 */
	public Menu getMenuByMenuId(String id) {
		String sql = "SELECT * FROM menu m WHERE m.id = '"+ id +"' ";
		return getUserOperMenu(sql);
	}
	
	/**
	 * 通过用户id和系统id获取菜单
	 * @param user_id
	 * @param systemId
	 * @return
	 */
	public List getMenuByUserAndSys(long user_id, String systemId) {
		String menu_id = "";
		String tableName = FuncConfig.getProperty("menu.tableName", "menu");
		
		if(SysConfig.DB=='d'){
			//menu_id = "cast(char(integer(t.menu_id)) as char(6))";
			menu_id = "t.menu_id";
		}else{
			menu_id = "t.menu_id";
		}
		/*String sql = "SELECT DISTINCT menu.* FROM "+tableName
				+ " JOIN (role_right JOIN umg_user_role"
				+ " ON role_id = role_type AND user_id = "
				+ user_id
				+ ") ON (menu.id = " + menu_id +"  OR menu.id = "
				+ DBDialect.conStr(DBDialect.substring(menu_id, 1, 2),
						"'0000'") + " and menu.system_id="+systemId+")"// 加上父菜单
				+ " ORDER BY pane,id";*/
		
		String sql = "SELECT DISTINCT m.* FROM "+tableName+" m," +
				"(SELECT DISTINCT menu.id id,menu.pane pane FROM "+tableName+" menu," +
				"(SELECT t1.menu_id menu_id from role_right t1,umg_user_role t2 where t1.role_id =t2.role_type AND t2.user_id="+user_id+")  t" +
				" WHERE menu.id = "+menu_id+"  and menu.system_id="+systemId+")  a" +
				" WHERE m.id=a.id or m.id=a.pane ORDER BY m.pane,m.id";
		log.info("通过用户id和系统id获取菜单 sql 为:"+sql);
		return getMenu(sql);
	}

	public List getSystemIdByUser(long pkid_) {
		String sql = "SELECT DISTINCT m.system_id FROM menu m,role_right rr, umg_user_role uur  WHERE m.id=rr.menu_id and rr.role_id=uur.role_type and uur.user_id=?";
		 Object[] args = {pkid_}; 
		 final List list = new ArrayList();
		 JdbcTemplate jt = new JdbcTemplate(dataSource);
		 jt.query(sql, args, new RowCallbackHandler() { 
		      @Override 
		      public void processRow(ResultSet rs) throws SQLException { 
		        if (!(rs.isAfterLast())) {
		        	list.add(rs.getString("system_id"));
		        } 
		      } 
		    }); 
		      return list; 
		  } 
}
