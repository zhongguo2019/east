package com.krm.ps.sysmanage.organmanage2.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.hibernate.Hibernate;

import com.krm.ps.sysmanage.organmanage2.dao.OrganInfoDAO2;
import com.krm.ps.sysmanage.organmanage2.vo.OrganTreeNode;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.util.DBDialect;

public class OrganInfoDAO2Impl extends BaseOrganDAO implements OrganInfoDAO2{

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
	private static String ORDER_CLAUSE_1=" ORDER BY " + DBDialect.getFieldLength("t.subtreetag") + ", t.subtreetag";
	
	
	
	/**
	 * 根据organCode查询nodeid
	 */
	public String getnodeid(String organCode,int idx)
	{
		String sql="select t.SUBTREETAG as nodeid from code_org_tree t where nodeid in (select pkid from code_org_organ where code='"+organCode+"') and idxid="+idx;
		Object[] values=new Object[]{};
		Object[][] scalaries=new Object[][]{{"nodeid",Hibernate.STRING}};
		List ls = this.list(sql, null, scalaries, values);
		if(ls.size()>0){
			return (String)ls.get(0);
		}else{
			return "";
		}
	}
	
	/**
	 * 取得下一级机构列表
	 * 
	 * @param organSystemId
	 * @param topOrgan
	 * @param date
	 * @return {@link OrganInfo}对象列表
	 */
	public List getSubOrgans(int organSystemId, int topOrgan, String date) {
		
		StringBuffer sql=new StringBuffer();
		sql.append(SELECT_CLAUSE)
		.append(JOIN_TREE_CLAUSE)
		.append(" AND t.parentid = ?")
		.append(JOIN_DICTIONARY_CLAUSE)
		.append(WHERE_CLAUSE)
		.append(ORDER_CLAUSE);
		date = date.replaceAll("-", "");
		Object[] values=new Object[] {new Integer(organSystemId),new Integer(topOrgan),date,date};
		
		List result=list(sql.toString(),new Object[][] { { "o", OrganInfo.class } },listOrgansScalaries,values);
		/*try{
			List result=list(sql.toString(),new Object[][] { { "o", OrganInfo.class } },listOrgansScalaries,values);
		}catch (Exception e){
			System.out.println("1:" + values[0]+","+"2:" + values[1]+","+"3:" + values[2]+","+"4:" + values[3]);
		}*/
		
		//List result=list(sql.toString(),new Object[][] { { "o", OrganInfo.class } },listOrgansScalaries,values);		
		return tranformResultToOrganInfoList(result);
	}
	
	/**
	 * @see com.krm.slsint.organmanage2.dao.OrganInfoDAO2#gerAllLeafOrgans(int, int, java.lang.String)
	 */
	public List getAllLeafOrgans(int organSystemId, int topOrgan, String date){
	    String tag=getSubTreeTagByNodeid(organSystemId, topOrgan);

        StringBuffer sql=new StringBuffer();
        sql.append(SELECT_CLAUSE)
        .append(JOIN_TREE_CLAUSE)
        .append(" AND t.subtreetag like ? ")
        .append(JOIN_DICTIONARY_CLAUSE)
        .append(WHERE_CLAUSE)
        .append(" and not exists(select t_tmp.pkid from code_org_tree t_tmp where t_tmp.parentid = t.nodeid)" +
                " and o.pkid <> ?")
        .append(ORDER_CLAUSE);

        Object[] values=new Object[] {new Integer(organSystemId),tag+"%",date,date, new Integer(topOrgan)};

        List result=list(sql.toString(),new Object[][] { { "o", OrganInfo.class } },listOrgansScalaries,values);        
        return tranformResultToOrganInfoList(result);
	}

	/**
	 * 取得所有下层机构（可能有多层）。包含本级机构
	 * 
	 * @param organSystemId
	 * @param topOrgan
	 * @param date
	 * @return {@link OrganInfo}对象列表
	 */
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
		return tranformResultToOrganInfoList(result);
	}
	
	/**
	 * 取得指定层数的下级机构。包含本级机构
	 * 
	 * @param organSystemId
	 * @param topOrgan
	 * @param layer
	 *            层数，1表示下一级
	 * @param date
	 * @return {@link OrganInfo}对象列表
	 */
	public List getAllSubOrgansByGrade(int organSystemId, int topOrgan, String date) {
		
		String tag=getSubTreeTagByNodeid(organSystemId, topOrgan);

		StringBuffer sql=new StringBuffer();
		sql.append(SELECT_CLAUSE)
		.append(JOIN_TREE_CLAUSE)
		.append(" AND t.subtreetag like ?")
		.append(JOIN_DICTIONARY_CLAUSE)
		.append(WHERE_CLAUSE)
		.append(ORDER_CLAUSE_1);

		Object[] values=new Object[] {new Integer(organSystemId),tag+"%",date,date};
		
		List result=list(sql.toString(),new Object[][] { { "o", OrganInfo.class } },listOrgansScalaries,values);		
		return tranformResultToOrganInfoList(result);
	}

	/**
	 * 取得指定层数的下级机构。包含本级机构
	 * 
	 * @param organSystemId
	 * @param topOrgan
	 * @param layer
	 *            层数，1表示下一级
	 * @param date
	 * @return {@link OrganInfo}对象列表
	 */
	public List getSubOrgans(int organSystemId, int topOrgan, int layer, String date) {
		
		String tag=getSubTreeTagByNodeid(organSystemId, topOrgan);

		StringBuffer sql=new StringBuffer();
		sql.append(SELECT_CLAUSE)
		.append(JOIN_TREE_CLAUSE)
		.append(" AND t.subtreetag like ?")
		.append(" AND "+DBDialect.getFieldLength("t.subtreetag")+" <= ?")
		.append(JOIN_DICTIONARY_CLAUSE)
		.append(WHERE_CLAUSE)
		.append(ORDER_CLAUSE);
		Integer maxLen=new Integer(tag.length()+layer*2);
		Object[] values=new Object[] {new Integer(organSystemId),tag+"%",maxLen,date,date};		

		List result=list(sql.toString(),new Object[][] { { "o", OrganInfo.class } },listOrgansScalaries,values);		
		return tranformResultToOrganInfoList(result);
	}
	
	/* (non-Javadoc)
	 * @see com.krm.slsint.organmanage2.dao.OrganInfoDAO2#getAppointLayerOrgans(int, int, int, java.lang.String)
	 */
	public List getAppointLayerOrgans(int organSystemId, int topOrgan, int layer, String date) {
		
		
		if(organSystemId == 0){
			String treeId = "1";
			organSystemId = Integer.parseInt(treeId);
		}
		
		String tag=getSubTreeTagByNodeid(organSystemId, topOrgan);
		
		StringBuffer sql=new StringBuffer();
		sql.append(SELECT_CLAUSE)
		.append(JOIN_TREE_CLAUSE)
		.append(" AND t.subtreetag like ?")
		.append(" AND "+DBDialect.getFieldLength("t.subtreetag")+" = ?")
		.append(JOIN_DICTIONARY_CLAUSE)
		.append(WHERE_CLAUSE)
		.append(ORDER_CLAUSE);
		Integer maxLen=new Integer(tag.length()+layer*2);
		Object[] values=new Object[] {new Integer(organSystemId),tag+"%",maxLen,date,date};		

		List result=list(sql.toString(),new Object[][] { { "o", OrganInfo.class } },listOrgansScalaries,values);		
		return tranformResultToOrganInfoList(result);
	}
	
	/**
	 * 获取机构类型列表
	 * @return List
	 */
	public List getOrganTypeList() {
		Object[][] scalaries = {{ "orgID", Hibernate.STRING },{ "orgType", Hibernate.INTEGER }};
		String sql = "SELECT code AS orgID,organ_type AS orgType FROM code_org_organ";
		return list(sql,null,scalaries);
	}
	
	/**
	 * 获取机构上下级关系列表（机构id，上级机构ids）
	 * @return List
	 */
	public List getOrganRelationList() {
		Object[][] scalaries = {{ "orgID", Hibernate.STRING },{ "topOrgID", Hibernate.STRING }};
		String sql = "SELECT nodeid AS orgID,parentid AS topOrgID FROM code_org_tree where parentid <> 0";
		return list(sql,null,scalaries);
	}
	/**
	 * 得到最小TreeId的机构树
	 * @param date
	 * @return
	 */
	public int getMinOrganTreeId(String date){
		String sql = "select min(pkid) as minPkid from code_user_org_idx";
		Object[][] scalaries = { { "minPkid", Hibernate.LONG } };
		List result = list(sql, null, scalaries);
		if(result.size() != 0) {
			return ((Long)result.get(0)).intValue();
		}else {
			return 0;
		}
	}

	public boolean isTreeNode(Integer treeId, Long nodeId)
	{
		String sql = "select {t.*} from code_org_tree t where idxid = "+treeId+" and nodeid = "+nodeId;
		List result = list(sql, new Object[][]{{"t",OrganTreeNode.class}}, null, null);
		if(result.size() > 0) {
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 取得机构树上的非叶子节点
	 * @param organSystemId	机构树id
	 * @param subtreeRootOrgCode	要取得的子树的根节点
	 * @return	
	 */
	public List getLeavesOrganInfo(int organSystemId, String subtreeRootOrgCode){
		OrganInfo oi = getOrganByCode(subtreeRootOrgCode);
		String hql = "from OrganTreeNode t where t.idxId=:idx and t.nodeId=:pkid";
		Map map = new HashMap();
		map.put("idx", new Integer(organSystemId));
		map.put("pkid", oi.getPkid());
		OrganTreeNode topNode = (OrganTreeNode)list(hql, map).get(0); 
		
		String sql = "select {t.*} from code_org_tree t where " +
				"t.idxId = ? and t.subtreetag like '" + 
				topNode.getSubTreeTag() + "%' and " +
				"t.nodeid not in (select distinct(parentid) from code_org_tree " +
				"where idxId = ?) order by t.subtreetag";
		List leavesNodeList = list(sql, new Object[][] {{ "t", OrganTreeNode.class } }, 
				null, new Object[] {new Integer(organSystemId),
				new Integer(organSystemId)});
		return transformTreeNode2OIList(leavesNodeList);
	}
	
	/**
	 * 取得机构树上的非叶子节点
	 * @param treeId	机构树id
	 * @param topOrganCode	要取得的子树的根节点
	 * @param date	数据日期日期
	 * @return	
	 */
	public List getTrunkOrganList(int treeId, String topOrganCode, String date){
		OrganInfo oi = getOrganByCode(topOrganCode);
		String hql = "from OrganTreeNode t where t.idxId=:idx and t.nodeId=:pkid";
		Map map = new HashMap();
		map.put("idx", new Integer(treeId));
		map.put("pkid", oi.getPkid());
		OrganTreeNode topNode = (OrganTreeNode)list(hql, map).get(0); 
		
		String sql = "select {t.*} from code_org_tree t where t.idxid = ? " +
				"and t.subtreetag like '" + topNode.getSubTreeTag() + "%' " +
				"and t.nodeid in (select distinct(parentid) from code_org_tree " +
				"where idxid = ?) order by t.subtreetag";
		List orgInfoList = list(sql, new Object[][]{ { "t", OrganTreeNode.class }}, null, 
				new Object[]{new Integer(treeId), new Integer(treeId)});
		orgInfoList = transformTreeNode2OIList(orgInfoList);
		return orgInfoList;
	}
	
	/**
	 * 将机构树节点的列表转换成机构列表
	 * @param treeNodeList	机构树节点的列表
	 * @return	机构列表
	 */
	public List transformTreeNode2OIList(List treeNodeList){
		List organInfoList = new ArrayList();
		Iterator itr = treeNodeList.iterator();
		while(itr.hasNext()){
			OrganTreeNode otn = (OrganTreeNode)itr.next();
			Long pkid = new Long(otn.getNodeId());
			organInfoList.add(getObject(OrganInfo.class, pkid));
		}
		return organInfoList;
	}
	
	public OrganInfo getOrganByCode(String code) {
		String hql = "select new OrganInfo(o,e1.dicname,e2.dicname)from " +
				"OrganInfo o,Dictionary e1,Dictionary e2 " +
				"where o.status <> 9 and o.business_type=e2.dicid and e2.parentid=1002 "
				+ "and o.organ_type=e1.dicid and e1.parentid=1001 and o.dummy_type='1' " 
				+ "and o.code=:code order by o.organOrder";
		Map map = new HashMap();
		map.put("code", code);
		List ls = list(hql, map);
		if (ls.size() > 0)
			return (OrganInfo) ls.get(0);
		return null;
	}
	
	private List tranformResultToOrganInfoList(List oil) {

		List organList=new ArrayList();
		Iterator it=oil.iterator();
		while(it.hasNext()) {
			Object[] organInfos=(Object[])it.next();
			String organTypeName=(String)organInfos[0];
			String businessTypeName=(String)organInfos[1];
			String aliasName=(String)organInfos[2];
			OrganInfo organInfo=(OrganInfo)organInfos[4];
			organInfo.setLayer(new Integer(((String)organInfos[3]).length() / 2));
			log.info("机构[" + organInfo.getCode() + "]的层级为：[" + organInfo.getLayer() + "]");
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

	public String getPreOrgan(int organSystem, String organCode) {
		String sql = "select {t.*} from code_org_organ t where  " +
				"t.pkid in(select c.parentid from code_org_tree c,code_org_organ t  where t.code='" +
				organCode+"' and t.pkid = c.nodeid and c.idxid=" +
				organSystem+")" ;
		List result = list(sql, new Object[][]{{"t",OrganInfo.class}}, null, null);
		if(result!=null && result.size()>0){
			return ((OrganInfo)result.get(0)).getCode();
		}else{
			return "";
		}
	}

	public List getDepartmentSimpleInfoList()
	{
		String hql = "select new com.krm.slsint.organmanage2.model.DepartmentSimpleInfo(dept.pkid,dept.deptName) from Department dept";
		return list(hql);
	}

	public List getParentId(int organSystemId, int topOrgan) {
		String sql = "SELECT subtreetag AS subTreeTag" +
		" FROM code_org_tree" +
		" WHERE idxid = "+organSystemId+" AND nodeid = "+topOrgan;	
		List result = list(sql, null,new Object[][]{{"subTreeTag",Hibernate.STRING }});
		return result;
	}
	
	public String getReportType(String reportId){
		String sql = "SELECT report_type as reportType FROM code_rep_report WHERE pkid="+reportId+"";
		List result = list(sql, null,new Object[][]{{"reportType",Hibernate.STRING }});
		if(!result.isEmpty()){
			return (String)result.get(0);
		}else{
			return "";
		}
	}
	
	public String getTreeTagByNodeid(int organSystemId, int topOrgan){
		Object[][] scalaries = { { "parentid", Hibernate.INTEGER }};
		
		String sql = "SELECT parentid AS parentid" +
				" FROM code_org_tree" +
				" WHERE idxid = ? AND nodeid = ?";	

		Object[] values=new Object[] {new Integer(organSystemId),new Integer(topOrgan)};
		
		List tagl=list(sql,null,scalaries,values);
		
		if(tagl.size()>0) {
			return String.valueOf(tagl.get(0));
		}
		
		return "";
	}
	
	public OrganInfo getOrganById(String pkid) {
		String hql = "select new OrganInfo(o,e1.dicname,e2.dicname)from " +
				"OrganInfo o,Dictionary e1,Dictionary e2 " +
				"where o.status <> 9 and o.business_type=e2.dicid and e2.parentid=1002 "
				+ "and o.organ_type=e1.dicid and e1.parentid=1001 and o.dummy_type='1' " 
				+ "and o.pkid=:pkid order by o.organOrder";
		Map map = new HashMap();
		map.put("pkid", pkid);
		List ls = list(hql, map);
		if (ls.size() > 0)
			return (OrganInfo) ls.get(0);
		return null;
	}
	
	public String[] getAddresseeList(String[] organIds,String roleType){
		String ids = "";
		Object[][] scalaries = { { "user_id", Hibernate.INTEGER }};
		for(int i=0;i<organIds.length;i++){
			if(i==0){
				ids +="'"+organIds[i]+"'";
			}else{
				ids +=",'"+organIds[i]+"'";
			}
		}
		String sql = "select user_id as user_id from umg_user_role where user_id in(select u.pkid from umg_user u where u.organ_id in("+ids+")) and role_type in("+roleType+",0,1)";
		List tagl=list(sql,null,scalaries);
		String userIds = "";
		if(!tagl.isEmpty()){
			for(int i=0;i<tagl.size();i++){
				if(i==0){
					userIds+=""+tagl.get(i);
				}else{
					userIds+=","+tagl.get(i);
				}
			}
		}
		return userIds.split(",");
	}
	
}
