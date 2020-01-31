package com.krm.ps.sysmanage.organmanage2.dao.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.Query;

import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.organmanage2.dao.AreaDAO;
import com.krm.ps.sysmanage.organmanage2.vo.Area;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.slsint.util.DBDialect;

public class AreaDAOImpl extends BaseOrganDAO implements AreaDAO{
	private Object[][] listOrgansScalaries = { { "organTypeName", Hibernate.STRING },
			{ "businessTypeName", Hibernate.STRING },{ "aliasName", Hibernate.STRING }, {"subtreetag", Hibernate.STRING}};
	
	private static String SELECT_CLAUSE="SELECT d1.dicname AS organTypeName,d2.dicname AS businessTypeName," +
			"t.aliasname AS aliasName, t.subtreetag as subtreetag,{o.*}" +
			" FROM code_org_organ o";
	private static String JOIN_TREE_CLAUSE=" JOIN code_org_tree t ON o.pkid = t.nodeid AND t.idxid = ?";
	private static String JOIN_DICTIONARY_CLAUSE=	" JOIN code_dictionary d1 ON d1.parentid=1001 AND o.organ_type=d1.dicid" + 
													" JOIN code_dictionary d2 ON d2.parentid=1002 AND o.business_type=d2.dicid";
	private static String WHERE_CLAUSE=" WHERE o.dummy_type='1' and o.status <> 9 AND o.begin_date <= ? AND ? <= o.end_date";
	private static String ORDER_CLAUSE=" ORDER BY t.subtreetag";
	private static String ORDER_CLAUSE_3=" ORDER BY length(t.subtreetag),t.subtreetag";
	private static String ORDER_CLAUSE_2=" ORDER BY o.CREATE_DATE desc";
	private static String ORDER_CLAUSE_1=" ORDER BY " + DBDialect.getFieldLength("t.subtreetag") + ", t.subtreetag";
	
	public String getAreaCodeByUserId(int userId){
		return super.getAreaCodeByUserId(userId);
	}
	
	/**
	 * 根据organCode的机构名称
	 */
	public List getOrganName(String organCode)
	{
		//String sql = "select DISTINCT aliasname from code_org_tree where nodeid = (select pkid from code_org_organ where code='"+organCode+"')";
		String sql = "select {c.*} from code_org_organ c where c.code = '"+organCode+"'";
		Query q = this.getSession().createSQLQuery(sql).addEntity("c", OrganInfo.class);
		List ls = q.list();
		return ls;
	}
	
	/**
	 * 根据用户Id得到地区
	 * @param userId
	 * 			用户Id
	 * @return
	 */
	public Area getAreaByUserId(int userId) {
		String sql="SELECT {a.*} FROM code_areas a WHERE a.code = ( "+
					"SELECT super_id "+
					"FROM code_org_organ o "+
					"JOIN umg_user u ON u.pkid = ? AND u.organ_id = o.code) AND a.status <> 9";
		Object[] values=new Object[] {new Integer(userId)};
		List list =  list(sql,new Object[][] { { "a", Area.class } }, null, values);
		if(list.size() > 0)
			return (Area)list.get(0);
		else
			return null;
	}
	/**
	 * 得到指定地区下的机构列表
	 * 
	 * @return {@link OrganInfo}对象列表
	 */
	public List getOrgansByArea(String areaId) {
		
		Object[][] scalaries = { { "organTypeName", Hibernate.STRING },
			{ "businessTypeName", Hibernate.STRING }};		
	String sql = "SELECT d1.dicname AS organTypeName,d2.dicname AS businessTypeName,{o.*}" +
			" FROM code_org_organ o" +
			" JOIN code_dictionary d1 ON d1.parentid=1001 AND o.organ_type=d1.dicid" + 
			" JOIN code_dictionary d2 ON d2.parentid=1002 AND o.business_type=d2.dicid" +
//			" WHERE super_id = ? order by o.pkid desc";
			" order by o.SUPER_ID ,o.CODE ";
	Object[] values=new Object[] {areaId};
	List result=list(sql,new Object[][] { { "o", OrganInfo.class } },scalaries,null);
	return tranformResultToOrganInfoList(result);
	}
	
	/**
	 * 得到指定地区下的机构列表
	 * 
	 * @return {@link OrganInfo}对象列表
	 */
	public List getOrgansByArea(String areaId ,String orgcode,String shortNmae) {
		
		Object[][] scalaries = { { "organTypeName", Hibernate.STRING },
			{ "businessTypeName", Hibernate.STRING }};		
	String sql = "SELECT d1.dicname AS organTypeName,d2.dicname AS businessTypeName,{o.*}" +
			" FROM code_org_organ o" +
			" JOIN code_dictionary d1 ON d1.parentid=1001 AND o.organ_type=d1.dicid" + 
			" JOIN code_dictionary d2 ON d2.parentid=1002 AND o.business_type=d2.dicid" +
			" WHERE 1=1 "  ;
			if(StringUtils.isNotBlank(orgcode)){
				sql +=" and o.CODE like '%"+orgcode+"%'";	
			}
            if(StringUtils.isNotBlank(shortNmae)){
            	sql +=" and o.SHORT_NAME like '%"+shortNmae+"%'" ;
			}
            sql += " order by o.SUPER_ID ,o.CODE ";
 //	Object[] values=new Object[] {areaId};
	List result=list(sql,new Object[][] { { "o", OrganInfo.class } },scalaries,null);
	return tranformResultToOrganInfoList(result);
	}
	
	
	/**
	 * 得到指定地区下的地区列表
	 * @param areaId
	 * 			地区Id
	 * @return {@ like Area}对象列表
	 */
	public List getAreasByArea(String areaCode, String date) {
		areaCode = areaCode.replaceAll("(00)+$","");
		String sql = "SELECT {t1.*} FROM code_areas t1 WHERE code LIKE ? AND begin_date <= ? AND end_date >= ? AND status <> 9";
		Object[] values = new Object[] {areaCode+"%", date, date};
		List list = list(sql,new Object[][] { { "t1", Area.class } }, null, values);
		return list;
	}
	public List getAreasByArea1(String areaCode, String date) {
		areaCode = areaCode.replaceAll("(00)+$","");
		Object[][] scalaries = {{"code", Hibernate.STRING},{"name",Hibernate.STRING},{"order",Hibernate.INTEGER},{"parent",Hibernate.INTEGER}};
		String sql = "SELECT code AS code ,full_name AS name, show_order AS order, super_id AS parent" +
					 "FROM code_areas WHERE code LIKE ? AND begin_date <= ? AND end_date >= ?";
		Object[] values = new Object[] {"'"+areaCode+"%'", date, date};
		return list(sql, null, scalaries,values);
	}
	/**
	 * 根据机构Id得到该机构所属地区编码
	 * @param organId
	 * 			机构Id
	 * @return
	 */
	public String getAreaCodeByOrganId(int organId) {
		Object[][] scalaries = {{ "areaId", Hibernate.STRING }};
		String sql = "SELECT super_id AS areaId FROM code_org_organ WHERE pkid = ? AND status <> 9";
		List ls=list(sql,null,scalaries,new Object[] {new Integer(organId)});
		if(ls.size()>0) {
			return (String)ls.get(0);
		}
		return "";
	}

	/**
	 * 根据机构Id得到该机构所属地区对象
	 * @param organId
	 * 			机构Id
	 * @return
	 */
	public Area getAreaByOrganId(int organId) {
		String sql = "SELECT {a.*} FROM code_areas a JOIN code_org_organ o ON a.code = o.super_id "+
					 "WHERE o.pkid = ? AND status <> 9";
		Object[] values = new Object[] {new Integer(organId)};
		List list =  list(sql,new Object[][] { { "a", Area.class } }, null, values);
		if(list.size() != 0){
			return (Area)list.get(0);
		}else{
			return null;
		}
	}
	/**
	 * 得到下级地区
	 * @param areaId
	 * 		地区Id
	 * @return
	 */
	public List getSubArea(String areaId) {
		String sql = "SELECT {a.*} FROM code_areas a WHERE super_id = (SELECT pkid FROM code_areas b WHERE b.code = ?) AND status <> 9 ORDER BY show_order";
		Object [] values = new Object[]{areaId};
		List list =  list(sql,new Object[][] { { "a", Area.class } }, null, values);
		return list;
	}
	
	/**
	 * 根据地区code读取地区下所有机构
	 */
	public List getOrgansByAreas(String areaId) {
		Object[][] scalaries = { { "organTypeName", Hibernate.STRING },
			{ "businessTypeName", Hibernate.STRING }};		
	String sql = "SELECT d1.dicname AS organTypeName,d2.dicname AS businessTypeName,{o.*}" +
			" FROM code_org_organ o" +
			" JOIN code_dictionary d1 ON d1.parentid=1001 AND o.organ_type=d1.dicid" + 
			" JOIN code_dictionary d2 ON d2.parentid=1002 AND o.business_type=d2.dicid" +
			" WHERE super_id IN ("+areaId+")";
	List result=list(sql,new Object[][] { { "o", OrganInfo.class } },scalaries);
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
	/**
	 * 返回顶层地区
	 */
	public Area getTopArea(){
		String sql = "SELECT {a.*} FROM code_areas a where a.super_id = ? AND status <> 9";
		Object [] values = new Object[]{new Integer(0)};
		List list =  list(sql,new Object[][] { { "a", Area.class } }, null, values);
		if(list.size() > 0)
			return (Area)list.get(0);
		else
			return null;
	}
	

	/**
	 * 设置默认地区
	 */
	public void setAreaDefult(String areaCode){
		String sql = "UPDATE code_areas SET isdefult = ? WHERE status <> 9";
		Object [] values = new Object[]{new Integer(0)};
		jdbcUpdate(sql, values);
		sql = "UPDATE code_areas SET isdefult = ? WHERE code = ? AND status <> 9";
		values = new Object[]{new Integer(1),areaCode};		
		jdbcUpdate(sql, values);
	}
	
	/**
	 * 根据当前地区编码得到地区对象
	 * @param areaCode
	 * 			地区编码
	 * @return
	 */
	public Area getAreaByareaCode(String areaCode){
		String sql = "SELECT {a.*} FROM code_areas a WHERE a.code = ? AND a.status <> 9";
		Object [] values = new Object[]{areaCode};
		List list = list(sql,new Object[][]{{"a",Area.class}},null,values);
		if(list.size() > 0)
			return (Area)list.get(0);
		else 
			return null;
	}
	/**
	 * 得到最大Order编码
	 */
	public int getMaxOrder(String areaCode){
		Object[][] scalaries = {{"showOrder", Hibernate.INTEGER}};
		String sql = "SELECT MAX(show_order) AS showOrder FROM code_areas "+
					 "WHERE super_id = (SELECT pkid FROM code_areas WHERE code = '"+areaCode+"') AND status <> 9";
		List list = list(sql, null, scalaries);
		Iterator it = list.iterator();
		Integer order = (Integer) it.next();
		if(order == null){
			return 0;
		}else{
			return order.intValue();
		}
	}
	
	/**
	 * 取得上级地区
	 * @param areaCode
	 * @return
	 */
	public Area getSuperArea(String areaCode){
		
		return null;
	}
	
	/**
	 * 根据地区Id得到地区信息
	 * @param areaId
	 * 			地区Id
	 * @return
	 */
	public Area getAreaById(int areaId){
		String sql = "SELECT {a.*} FROM code_areas a WHERE pkid = ? AND status <> 9";
		Object [] values = new Object[]{new Integer(areaId)};
		List list = list(sql,new Object[][]{{"a",Area.class}},null,values);
		if(list.size() > 0)
			return (Area)list.get(0);
		else 
			return null;
	}
	
	/**
	 * 删除地区信息
	 * @param areaId
	 * 			地区Id
	 */
	public void deleteArea(String areaCode){
		String sql = "UPDATE code_areas SET status = 9 WHERE code = ? AND status <> 9";
		Object [] values = new Object[]{areaCode};
		jdbcUpdate(sql, values);
	}
	
	/**
	 * 得到所有下级地区(不包含本级)
	 * @param areaCode
	 * 			地区编码
	 * @return
	 */
	public List getAllSubAreas(String areaCode){
		areaCode = areaCode.replaceAll("(00)+$","");
		String sql = "SELECT {a.*} FROM code_areas a WHERE code LIKE ? AND status <> 9";
		Object [] values = new Object[]{"'"+areaCode+"%'"};
		return list(sql,new Object[][]{{"a",Area.class}},null,values);
	}
	
	/**
	 * 更新地区信息
	 * @param area
	 * 		地区对象
	 */
	public void updateArea(Area area){
		String sql = "UPDATE code_areas SET code = ? , full_name = ? , short_name = ? , begin_date = ? , end_date = ? WHERE pkid = ?";
		Object [] values = new Object[]{area.getCode(),area.getFullName(),area.getShortName(),area.getBeginDate(),area.getEndDate(),new Integer(area.getPkid())};
		jdbcUpdate(sql, values);
	}
	
	
	
	public String getAreasByArea_temp(String areaCode, String date) {
		areaCode = areaCode.replaceAll("(00)+$","");
		String sql = "SELECT {t1.*} FROM code_areas t1 WHERE code LIKE ? AND begin_date <= ? AND end_date >= ? AND status <> 9 order by super_id,show_order";
		Object[] values = new Object[] {areaCode+"%", date, date};
		List list = list(sql,new Object[][] { { "t1", Area.class } }, null, values);
		/*Area area = new Area();
		List listt = new ArrayList();
		area.setPkid(((Area)list.get(0)).getPkid());
		area.setCode(((Area)list.get(0)).getCode());
		area.setSuperId(((Area)list.get(0)).getSuperId());
		area.setShowOrder(((Area)list.get(0)).getShowOrder());
		area.setFullName(((Area)list.get(0)).getFullName());
		area.setShortName(((Area)list.get(0)).getShortName());
		area.setIsDefult(((Area)list.get(0)).getIsDefult());
		area.setStatus(((Area)list.get(0)).getStatus());
		area.setBeginDate(((Area)list.get(0)).getBeginDate());
		area.setEndDate(((Area)list.get(0)).getEndDate());
		listt.add(area);
		for (int i = 0; i < list.size(); i++) {
			for (int j = 0; j < list.size(); j++) {
				if(((Area)list.get(i)).getSuperId()==((Area)list.get(j)).getPkid()){
					area.setPkid(((Area)list.get(i)).getPkid());
					area.setCode(((Area)list.get(i)).getCode());
					area.setSuperId(((Area)list.get(i)).getSuperId());
					area.setShowOrder(((Area)list.get(i)).getShowOrder());
					area.setFullName(((Area)list.get(i)).getFullName());
					area.setShortName(((Area)list.get(i)).getShortName());
					area.setIsDefult(((Area)list.get(i)).getIsDefult());
					area.setStatus(((Area)list.get(i)).getStatus());
					area.setBeginDate(((Area)list.get(i)).getBeginDate());
					area.setEndDate(((Area)list.get(i)).getEndDate());
					listt.add(area);
					area = new Area();
				}
				
			}
		}
		
		System.out.println(listt); */
		
		String xml = getAreaxml(list,date);
		
		return xml;
	}

	private String getAreaxml(List listt,String date) {
		StringBuffer areaxml = new StringBuffer();
		areaxml.append("[{");
		areaxml.append("\"id\":\"" + ((Area)listt.get(0)).getCode() + "\",");
		areaxml.append("\"text\":\"" + ((Area)listt.get(0)).getFullName().trim() + "\"");
		Map<Integer, String> map  = new HashMap<Integer, String>();
		int len=0;
		for (int i = 0; i < listt.size(); i++) {
			if(((Area)listt.get(0)).getPkid()==((Area)listt.get(i)).getSuperId()){
				
				len+=1;
				map.put(i, ((Area)listt.get(i)).getCode());
			}
		}
		
		if(listt.size()>0){
			areaxml.append(",\"state\":\"closed\"");
			areaxml.append(",\"children\":[");
			for (int i = 1; i < 4; i++) {
				if (i == 1) {
					areaxml.append("{");
				} else {
					areaxml.append(",{");
				}
				for (int j = 1; j <= len; j++) { 
					
					if((Integer.parseInt((((Area)listt.get(i)).getCode()))==(Integer.parseInt(map.get(j).toString())))){
						areaxml.append("\"id\":\"" + ((Area)listt.get(i)).getCode() + "\",");
						areaxml.append("\"text\":\"" + ((Area)listt.get(i)).getFullName().trim()+ "\"");
						String sql = "SELECT {t1.*} FROM code_areas t1 WHERE code like ? AND begin_date <= ? AND end_date >= ? AND status <> 9 order by super_id,show_order";
						
						Object[] values = new Object[] {((Area)listt.get(j)).getCode().substring(0, 4)+"%", date, date};
						List list1 = list(sql,new Object[][] { { "t1", Area.class } }, null, values);
						if(list1.size()>0){
							areaxml.append(",\"state\":\"closed\"");
							areaxml.append(",\"children\":[");
							for (int k = 1; k < list1.size(); k++) {
								if (k == 1) {
									areaxml.append("{");
								} else {
									areaxml.append(",{");
								}
								areaxml.append("\"id\":\"" + ((Area)list1.get(k)).getCode() + "\",");
								areaxml.append("\"text\":\"" + ((Area)list1.get(k)).getFullName().trim()+ "\"");
								areaxml.append("}");
							}
							areaxml.append("]");
						}
						
					}
					
				}
				areaxml.append("}");
			}
			areaxml.append("]");
		}
		areaxml.append("}]");
		System.out.println(areaxml);
		return areaxml.toString();
	}

	@Override
	public OrganInfo getOrganByCode(String organId) {

		organId = organId.trim();
		String hql = "select new OrganInfo(o,e1.dicname,e2.dicname)from OrganInfo o,Dictionary e1,Dictionary e2 " 
		    + "where o.status <> 9 and o.business_type=e2.dicid and e2.parentid=1002 "
			+ "and o.organ_type=e1.dicid and e1.parentid=1001 and o.dummy_type='1' " 
			+ "and o.code=:code order by o.organOrder";
		System.out.println("hql:"+hql);
		Map map = new HashMap();
		map.put("code", organId);
		List ls = list(hql, map);
		if (ls.size() > 0)
			return (OrganInfo) ls.get(0);
		return null;
	
	}

	@Override
	public List getAllSubOrgans(int organSystemId, int topOrgan, String date) {

		
		String tag=getSubTreeTagByNodeid(organSystemId, topOrgan);

		StringBuffer sql=new StringBuffer();
		sql.append(SELECT_CLAUSE)
		.append(JOIN_TREE_CLAUSE)
		.append(" AND t.subtreetag like ?")
		.append(JOIN_DICTIONARY_CLAUSE)
		.append(WHERE_CLAUSE)
		.append(ORDER_CLAUSE);

		Object[] values=new Object[] {new Integer(organSystemId),tag+"%",date,date};

		List result=list(sql.toString(),new Object[][] { { "o", OrganInfo.class } },listOrgansScalaries,values);		
		return tranformResultToOrganInfoList1(result);
	
	}
	private List tranformResultToOrganInfoList1(List oil) {

		List organList=new ArrayList();
		Iterator it=oil.iterator();
		while(it.hasNext()) {
			Object[] organInfos=(Object[])it.next();
			String organTypeName=(String)organInfos[0];
			String businessTypeName=(String)organInfos[1];
			String aliasName=(String)organInfos[2];
			OrganInfo organInfo=(OrganInfo)organInfos[4];
			organInfo.setLayer(new Integer(((String)organInfos[3]).length() / 2));
			log.debug("机构[" + organInfo.getCode() + "]的层级为：[" + organInfo.getLayer() + "]");
			organInfo.setOrgan_typename(organTypeName);
			organInfo.setBusiness_typename(businessTypeName);
			if(aliasName!=null&&!aliasName.equals("")) {
				organInfo.setShort_name(aliasName);
				organInfo.setFull_name(aliasName);
			}
			organList.add(organInfo);
		}	
		return organList;
	}
	
}
