package com.krm.ps.sysmanage.organmanage2.dao.impl;

import java.util.List;
import java.util.ArrayList;

import org.hibernate.Hibernate;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;

public class BaseOrganDAO extends BaseDAOHibernate{

	/*
	 * 得到用户的机构所在地区的地区编码
	 */
	protected String getAreaCodeByUserId(int userId) {

		Object[][] scalaries = { { "areaId", Hibernate.STRING }};
		
		String sql="SELECT super_id AS areaId" +
				" FROM code_org_organ o" +
				" JOIN umg_user u ON u.pkid = ? AND u.organ_id = o.code";
		
		List ls=list(sql,null,scalaries,new Object[] {new Integer(userId)});
		
		if(ls.size()>0) {
			return (String)ls.get(0);
		}
		
		return "";
	}
	
	/*
	 * 取得一个地区本身和上级地区id列表.地区id是符合两位一级规则的,如地区id为1122334400,
	 * 则上级地区是1122330000,1122000000,1100000000,0000000000
	 */
	protected ArrayList selfAndSuperAreas(String selfAreaId) {
		ArrayList areas=new ArrayList();
		areas.add(selfAreaId);
		StringBuffer bf=new StringBuffer(selfAreaId);
		int areaIdLen=bf.length();
		for(int i=areaIdLen-2;i>=0;i-=2) {
			if(bf.charAt(i)=='0'&&bf.charAt(i+1)=='0') {
			}else {
				bf.setCharAt(i, '0');
				bf.setCharAt(i+1, '0');
				areas.add(bf.toString());
			}
		}
		return areas;
	}
	
	/*
	 * 根据节点id(机构pkid)取得节点的子树编码
	 */
	protected String getSubTreeTagByNodeid(int organSystemId, int topOrgan) {

		Object[][] scalaries = { { "subTreeTag", Hibernate.STRING }};
		
		String sql = "SELECT subtreetag AS subTreeTag" +
				" FROM code_org_tree" +
				" WHERE idxid = ? AND nodeid = ?";	

		Object[] values=new Object[] {new Integer(organSystemId),new Integer(topOrgan)};
		
		List tagl=list(sql,null,scalaries,values);
		
		if(tagl.size()>0) {
			return (String)tagl.get(0);
		}
		
		return null;
	}

}
