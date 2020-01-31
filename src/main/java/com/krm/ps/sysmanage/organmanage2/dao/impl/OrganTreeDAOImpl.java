package com.krm.ps.sysmanage.organmanage2.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Hibernate;

import com.krm.ps.framework.common.web.util.TreeJson;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.organmanage2.dao.OrganTreeDAO;
import com.krm.ps.sysmanage.organmanage2.vo.OrganSystemInfo;
import com.krm.ps.sysmanage.organmanage2.vo.OrganTreeNode;
import com.krm.ps.util.DBDialect;

public class OrganTreeDAOImpl extends BaseOrganDAO implements OrganTreeDAO{

	private Object[][] listNodesScalaries = {
			{ "organCode", Hibernate.STRING }, { "oriName", Hibernate.STRING },
			{ "beginDate", Hibernate.STRING }, { "endDate", Hibernate.STRING } };

	private String listNodesBaseSql = "SELECT {t.*},o.code AS organCode,o.short_name AS oriName,"
			+ " o.begin_date AS beginDate,o.end_date AS endDate"
			+ " FROM code_org_tree t,code_org_organ o"
			+ " WHERE idxid = ?"
			+ " AND t.nodeid = o.pkid";

	/**
	 * 根据机构系统编码、顶层机构取得机构树节点列表
	 * 
	 * @param organSystemId
	 * @param topOrgan
	 * @return {@link OrganTreeNode}��List
	 */
	public List listOrganTreeNodes(int organSystemId, int topOrgan) {

		String tag = getSubTreeTagByNodeid(organSystemId, topOrgan);

		String sql = listNodesBaseSql + " AND subtreetag LIKE ?"
				+ " ORDER BY subtreetag";
		Object[] values = new Object[] { new Integer(organSystemId), tag + "%" };

		List resultList = list(sql,
				new Object[][] { { "t", OrganTreeNode.class } },
				listNodesScalaries, values);
		return tranformResultToNodeList(resultList);
	}
	
	public List getOrganTreeList_temp(int organSystemId, int topOrgan){
		String tag = getSubTreeTagByNodeid(organSystemId, topOrgan);
		String sql = listNodesBaseSql + " AND subtreetag LIKE ? "
		+ " ORDER BY subtreetag";
		Object[] values = new Object[] { new Integer(organSystemId), tag + "%"};

		List resultList = list(sql,
				new Object[][] { { "t", OrganTreeNode.class } },
				listNodesScalaries, values);
		
		List list = new ArrayList();
		
		for (int i = 0; i < resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			OrganTreeNode organtreenode = (OrganTreeNode) obj[4];
			TreeJson te = new TreeJson();
			te.setText((String) obj[1]);
			te.setSid((String) obj[0]);
			te.setId(String.valueOf(organtreenode.getNodeId()));
			te.setPid(String.valueOf(organtreenode.getParentId()));
			list.add(te);
		}
		return list;
	}

	public String listOrganTreeNodes_temp(int organSystemId, int topOrgan,String treeState,String nodeid) {

		String tag = getSubTreeTagByNodeid(organSystemId, topOrgan);
		String sql = null;
		String sql2=null;
		Object[] values = null;
		Object[] values2 = null;
		if(treeState.equals("2")&&!nodeid.equals("")&&treeState!=null){
			sql = listNodesBaseSql + " and t.parentid= (select pkid from code_org_organ where code=?) AND subtreetag LIKE ?  ORDER BY subtreetag";
			values= new Object[] { new Integer(organSystemId),nodeid, tag + "%"};
			
			sql2 = listNodesBaseSql + " AND subtreetag LIKE ?  ORDER BY subtreetag";
			values2= new Object[] { new Integer(organSystemId), tag + "%"};
			
		}else{
			sql = listNodesBaseSql + " AND subtreetag LIKE ?  ORDER BY subtreetag";
			values= new Object[] { new Integer(organSystemId), tag + "%"};
		}

		List resultList = list(sql,
				new Object[][] { { "t", OrganTreeNode.class } },
				listNodesScalaries, values);
		
		
		String eastUItree = "";
		if(treeState.equals("2")&&!nodeid.equals("")&&treeState!=null){
			List resultList2 = list(sql2,
					new Object[][] { { "t", OrganTreeNode.class } },
					listNodesScalaries, values2);
			eastUItree = Createtree2(resultList, resultList2,organSystemId, topOrgan, tag);
			
		}else{
			eastUItree = Createtree1(resultList, organSystemId, topOrgan, tag,treeState);
		}
		

		return eastUItree;
	}
	
	
	
	private String Createtree2(List resultList,List resultList2, int organSystemId,
			int topOrgan, String tag) {
		String[] cnames=new String[] {"id","text", "nodeid","parentid","subtreetag"};
		String[][] cols2 = makecolstree(resultList2,cnames);
		String[][] cols = makecolstree(resultList,cnames);
		
		StringBuffer sbtree = new StringBuffer();
		if(cols.length>0){
			sbtree.append("[");
			
			for (int i = 0; i < cols.length; i++) {
				if (i == 0) {
					sbtree.append("{");
				} else {
					sbtree.append(",{");
				}
				sbtree.append("\"id\":\"" + cols[i][0] + "\",");
				sbtree.append("\"text\":\"" + cols[i][1].trim()	+ "\"");
				String[][] treeCols1 = findColTree(cols2,cols[i][2],cnames);
				if(treeCols1.length>0){
					sbtree.append(",\"state\":\"closed\"");
				}
				sbtree.append("}");
			}
			sbtree.append("]");
		}
		
		System.out.println(sbtree.toString());
		return sbtree.toString();
	}

	private String Createtree1(List resultList, int organSystemId, int topOrgan,
			 String tag,String treeState) {
		String[] cnames=new String[] {"id","text", "nodeid","parentid","subtreetag"};
		String[][] cols = makecolstree(resultList,cnames);
		
		StringBuffer sbtree = new StringBuffer();
		sbtree.append("[{");
		
		sbtree.append("\"id\":\"" + cols[0][0] + "\",");
		sbtree.append("\"text\":\"" + cols[0][1].trim() + "\"");
		
		String[][] treeCols = findColTree(cols,cols[0][2],cnames);
		if(treeCols.length>0){
			sbtree.append(",\"state\":\"closed\"");
		}
		if(treeState==null||treeState.equals("0")||treeState.equals("3")){//ͬ��
			createTreeJson(sbtree,cols,treeCols,cnames);
		}else if(treeState.equals("1")){
			if(treeCols.length>0){
				if(topOrgan==Integer.parseInt(cols[0][2])){
					sbtree.append(",\"state\":\"closed\"");
					sbtree.append(",\"children\":[");
					for (int i = 0; i < treeCols.length; i++) {
						if (i == 0) {
							sbtree.append("{");
						} else {
							sbtree.append(",{");
						}
						sbtree.append("\"id\":\"" + treeCols[i][0] + "\",");
						sbtree.append("\"text\":\"" + treeCols[i][1].trim()	+ "\"");
						String[][] treeCols1 = findColTree(cols,treeCols[i][2],cnames);
						if(treeCols1.length>0){
							sbtree.append(",\"state\":\"closed\"");
						}
						sbtree.append("}");
					}
					sbtree.append("]");
				}else{
				createTreeJson(sbtree,cols,treeCols,cnames);
				}
			}
		}
		sbtree.append("}]");
		System.out.println(sbtree.toString());
		return sbtree.toString();
	}
	
	private void createTreeJson(StringBuffer sbtree, String[][] cols, String[][] treeCols,
			String[] cnames) {
		sbtree.append(",\"children\":[");
		for (int i = 0; i < treeCols.length; i++) {
			if (i == 0) {
				sbtree.append("{");
			} else {
				sbtree.append(",{");
			}
			sbtree.append("\"id\":\"" + treeCols[i][0] + "\",");
			sbtree.append("\"text\":\"" + treeCols[i][1].trim()	+ "\"");
			String[][] treeCols1 = findColTree(cols,treeCols[i][2],cnames);
			if(treeCols1.length>0){
				sbtree.append(",\"state\":\"closed\"");
				createTreeJson(sbtree,cols,treeCols1,cnames);
			}
			sbtree.append("}");
		}
		sbtree.append("]");
	}
	

	private String[][] findColTree(String[][] cols, String seq,String[] cnames) {
		String[][] treeCols = new String[cols.length][cnames.length];
		int j=0;
		for (int i = 1; i < cols.length; i++) {
			if(cols[i][3].equals(seq)){
				treeCols[j][0] = cols[i][0];
				treeCols[j][1] = cols[i][1];
				treeCols[j][2] = cols[i][2];
				treeCols[j][3] = cols[i][3];
				treeCols[j][4] = cols[i][4];
				j++;
			}
		}
		String[][] treeColss = new String[j][cnames.length];
		for (int k = 0; k < j; k++) {
				treeColss[k][0] = treeCols[k][0];
				treeColss[k][1] = treeCols[k][1];
				treeColss[k][2] = treeCols[k][2];
				treeColss[k][3] = treeCols[k][3];
				treeColss[k][4] = treeCols[k][4];
		}
		return treeColss;
	}

	private String[][] makecolstree(List resultList, String[] cnames) {
		
		String[][] cols=null;
		cols = new String[resultList.size()][cnames.length];
		for (int j = 0; j < cnames.length; j++)
		{
			for (int i = 0; i < resultList.size(); i++)
			{
				Object[] obj = (Object[]) resultList.get(i);
				OrganTreeNode organtreenode = (OrganTreeNode) obj[4];
				cols[i][0] = (String) obj[0];
				cols[i][1] = (String) obj[1];
				cols[i][2] = String.valueOf(organtreenode.getNodeId());
				cols[i][3] = String.valueOf(organtreenode.getParentId());
				cols[i][4] = organtreenode.getSubTreeTag();
			}
		
		}
		return cols;
	}

	/*private String Createtree0(List resultList, int organSystemId, int topOrgan,
 String tag) {

		Object[] obj = (Object[]) resultList.get(0);
		OrganTreeNode organtreenode = (OrganTreeNode) obj[4];
		StringBuffer sbtree = new StringBuffer();
		String sql = listNodesBaseSql + " AND subtreetag LIKE ? and parentid=?"
				+ " ORDER BY subtreetag";
		Object[] values;
		values = new Object[] { new Integer(organSystemId), tag + "%",
				new Integer(organtreenode.getNodeId()) };

		List list = list(sql, new Object[][] { { "t", OrganTreeNode.class } },
				listNodesScalaries, values);
		sbtree.append("[{");
		sbtree.append("\"id\":\"" + obj[0] + "\",");
		sbtree.append("\"text\":\"" + obj[1].toString().trim() + "\"");

		if (list.size() > 0) {
			sbtree.append(",\"state\":\"closed\"");
			sbtree.append(",\"children\":[");
			for (int i = 0; i < list.size(); i++) {
				Object[] obj1 = (Object[]) list.get(i);
				if (i == 0) {
					sbtree.append("{");
				} else {
					sbtree.append(",{");
				}
				sbtree.append("\"id\":\"" + obj1[0] + "\",");
				sbtree.append("\"text\":\"" + obj1[1].toString().trim()
						+ "\"");
				OrganTreeNode organtreenode1 = (OrganTreeNode) obj1[4];
				values = new Object[] { new Integer(organSystemId), tag + "%",
						new Integer(organtreenode1.getNodeId()) };
				List list1 = list(sql, new Object[][] { { "t",
						OrganTreeNode.class } }, listNodesScalaries, values);
				if (list1.size() > 0) {
					sbtree.append(",\"state\":\"closed\"");
					sbtree.append(",\"children\":[");
					for (int j = 0; j < list1.size(); j++) {
						Object[] obj2 = (Object[]) list1.get(j);
						if (j == 0) {
							sbtree.append("{");
						} else {
							sbtree.append(",{");
						}
						sbtree.append("\"id\":\"" + obj2[0] + "\",");
						sbtree.append("\"text\":\""
								+ obj2[1].toString().trim() + "\"");

						OrganTreeNode organtreenode2 = (OrganTreeNode) obj2[4];
						values = new Object[] { new Integer(organSystemId),
								tag + "%",
								new Integer(organtreenode2.getNodeId()) };
						List list2 = list(sql, new Object[][] { { "t",
								OrganTreeNode.class } }, listNodesScalaries,
								values);

						if (list2.size() > 0) {
							sbtree.append(",\"state\":\"closed\"");
							sbtree.append(",\"children\":[");
							for (int k = 0; k < list2.size(); k++) {
								Object[] obj3 = (Object[]) list2.get(k);
								if (k == 0) {
									sbtree.append("{");
								} else {
									sbtree.append(",{");
								}
								sbtree.append("\"id\":\"" + obj3[0]+ "\",");
								sbtree.append("\"text\":\""+ obj3[1].toString().trim() + "\"");
								sbtree.append("}");
							}
							sbtree.append("]");
						}
						sbtree.append("}");
					}
					sbtree.append("]");
				}
				sbtree.append("}");
			}
			sbtree.append("]");
		}
		sbtree.append("}]");

		System.out.println(sbtree.toString());
		return sbtree.toString();
	}*/
	
	private String Createtree(List resultList, int organSystemId, int topOrgan,
			String tag) {
		Object[] obj = (Object[]) resultList.get(0);
		OrganTreeNode organtreenode = (OrganTreeNode) obj[4];
		StringBuffer sbtree = new StringBuffer();
		String sql = listNodesBaseSql + " AND subtreetag LIKE ? and parentid=?"
				+ " ORDER BY subtreetag";
		Object[] values;
		values = new Object[] { new Integer(organSystemId), tag + "%",
				new Integer(organtreenode.getNodeId()) };

		List List = list(sql, new Object[][] { { "t", OrganTreeNode.class } },
				listNodesScalaries, values);

		sbtree.append("<ul class=\"easyui-tree\">");
		sbtree.append("\n\t<li data-options=\"state:'closed'\">");
		sbtree.append("\n\t\t<span id=" + obj[0] + ">" + obj[1] + "</span>");
		//
		for (int i = 0; i < List.size(); i++) {
			// for (int i = 0; i < 3; i++) {
			Object[] obj1 = (Object[]) List.get(i);
			sbtree.append("\n\t\t<ul>");
			sbtree.append("\n\t\t\t<li data-options=\"state:'closed'\">");
			sbtree.append("\n\t\t\t\t<span id='" + obj1[0] + "'>" + obj1[1]
					+ "</span>");
			OrganTreeNode organtreenode1 = (OrganTreeNode) obj1[4];
			values = new Object[] { new Integer(organSystemId), tag + "%",
					new Integer(organtreenode1.getNodeId()) };
			List List1 = list(sql,
					new Object[][] { { "t", OrganTreeNode.class } },
					listNodesScalaries, values);
			sbtree.append("\n\t\t\t\t<ul>");
			sbtree.append("\n\t\t\t\t\t<li data-options=\"state:'closed'\">");
			for (int j = 0; j < List1.size(); j++) {
				Object[] obj2 = (Object[]) List1.get(j);
				sbtree.append("\n\t\t\t\t\t\t<span id=\"" + obj2[0] + "\">"
						+ obj2[1] + "</span>");
				sbtree.append("\n\t\t\t\t\t\t<ul>");
				OrganTreeNode organtreenode2 = (OrganTreeNode) obj2[4];
				values = new Object[] { new Integer(organSystemId), tag + "%",
						new Integer(organtreenode2.getNodeId()) };
				List List2 = list(sql, new Object[][] { { "t",
						OrganTreeNode.class } }, listNodesScalaries, values);
				for (int k = 0; k < List2.size(); k++) {
					Object[] obj3 = (Object[]) List2.get(k);
					sbtree.append("\n\t\t\t\t\t\t\t<li id=\"" + obj3[0] + "\">"
							+ obj3[1] + "</li>");
				}
				sbtree.append("\n\t\t\t\t\t\t</ul>");
			}
			sbtree.append("\n\t\t\t\t\t</li>");
			sbtree.append("\n\t\t\t\t</ul>");
			sbtree.append("\n\t\t\t</li>");
			sbtree.append("\n\t\t</ul>");

		}
		sbtree.append("\n\t</li>");
		sbtree.append("\n</ul>");
		//System.out.println(sbtree.toString());
		return sbtree.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.krm.slsint.organmanage2.dao.OrganTreeDAO#getTopNode(int)
	 */
	public OrganTreeNode getTopNode(int organSystemId) {
		String hql = "FROM OrganTreeNode t WHERE t.idxId =  " + organSystemId
				+ " and parentid = 0 ";
		List resultList = list(hql, new HashMap());
		// 2010-6-28 上午09:28:32 皮亮修改
		// 机构树的根节点，把根节点对应机构编码加入
		if (resultList.size() > 0) // 查询到了最顶端机构节点
		{
			OrganTreeNode organTreeNode = (OrganTreeNode) resultList.get(0);
			OrganInfo organInfo = (OrganInfo) getObject(OrganInfo.class,
					new Long(organTreeNode.getNodeId()));
			organTreeNode.setOrganCode(organInfo.getCode());
		}
		return (OrganTreeNode) resultList.get(0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.krm.slsint.organmanage2.dao.OrganTreeDAO#getLeaves(int,
	 * java.lang.String)
	 */
	public List getLeaves(int organSystemId, String subtreeRootOrgCode) {
		String sql = "select {t.*} from code_org_tree t where "
				+ "t.subtreetag like (select tree.subtreetag "
				+ DBDialect.conStr("", "'%'") + " from code_org_tree tree, "
				+ "code_org_organ o where o.code = ? "
				+ "and tree.nodeid = o.pkid  and tree.idxId = " + organSystemId
				+ ") and t.idxId = " + organSystemId + " and "
				+ "t.nodeid not in ( select parentid from code_org_tree "
				+ "where idxId = " + organSystemId + ")";
		List resultList = list(sql,
				new Object[][] { { "t", OrganTreeNode.class } }, null,
				new Object[] { subtreeRootOrgCode });
		return resultList;
	}

	/**
	 * 根据机构系统编码取得机构树节点列表
	 * 
	 * @param organSystemId
	 * @return {@link OrganTreeNode}的List，是根据父节在前子结点在后排好序的
	 */
	public List listOrganTreeNodes(int organSystemId) {

		String sql = listNodesBaseSql + " ORDER BY subtreetag";
		Object[] values = new Object[] { new Integer(organSystemId) };

		List resultList = list(sql,
				new Object[][] { { "t", OrganTreeNode.class } },
				listNodesScalaries, values);
		return tranformResultToNodeList(resultList);
	}

	private List tranformResultToNodeList(List nodes) {
		List nodeList = new ArrayList();
		Iterator it = nodes.iterator();
		while (it.hasNext()) {
			Object[] nodeInfo = (Object[]) it.next();
			String organCode = (String) nodeInfo[0];
			String oriName = (String) nodeInfo[1];
			String beginDate = (String) nodeInfo[2];
			String endDate = (String) nodeInfo[3];
			OrganTreeNode node = (OrganTreeNode) nodeInfo[4];
			node.setOrganCode(organCode);
			node.setOriName(oriName);
			node.setBeginDate(beginDate);
			node.setEndDate(endDate);
			nodeList.add(node);
		}
		return nodeList;
	}

	/**
	 * 删除指定机构系统id的机构树所有节点
	 * @param organSystemId
	 * @return
	 */
	public void removeOrganTreeNodes(int organSystemId) {

		String sql = "DELETE FROM code_org_tree" + " WHERE idxid = ?";
		Object[] values = new Object[] { new Integer(organSystemId) };
		jdbcUpdate(sql, values);
	}

	public void removeOrganSystem(int organSystemId) {

		removeObject(OrganSystemInfo.class, new Integer(organSystemId));

		// String sql = "DELETE FROM code_user_org_idx" +
		// " WHERE pkid = ?";
		// Object[] values=new Object[] {new Integer(organSystemId)};
		// jdbcUpdate(sql, values);

		removeOrganTreeNodes(organSystemId);
	}
	/**
	 * 保存机构树节点列表
	 * 
	 * @param organTreeNodes {@link OrganTreeNode}的List
	 * @return 
	 */
	public void saveOrganTreeNodes(List organTreeNodes) {

		Iterator it = organTreeNodes.iterator();
		while (it.hasNext()) {
			OrganTreeNode node = (OrganTreeNode) it.next();
			saveObject(node);
		}
	}

	/**
	 * 列出指定用户可以查看、维护的机构系统
	 * 
	 * @param userId
	 * @param date
	 *            日期，此日期不在使用期限内的机构系统不列出来，格式yyyyMMdd
	 * @param type
	 *            查看类型，取值为
	 *            <ul>
	 *            	1:所有自己创建的均可见
	 *            	2:严格检查分组、地区和日期
	 *            </ul>
	 * @return {@link OrganSystemInfo}对象列表
	 */
	public List listOrganSystems(int userId, String date, int type) {

		String areaId = getAreaCodeByUserId(userId);
		ArrayList areaIds = selfAndSuperAreas(areaId);

		String ps = "";
		int areaIdSize = areaIds.size();
		for (int i = 0; i < areaIdSize; i++) {
			ps += (i == 0) ? "?" : ",?";
		}
		String sql = "";

		Object[] values = new Object[areaIdSize + 4];

		if (type == 1) {
			sql = "SELECT {t1.*} FROM code_user_org_idx t1"
					+ " WHERE userid=? OR"
					+ " (areaid IN ("
					+ ps
					+ ")"
					+ "   AND ("
					+ "     ispublic=0"
					+ "     OR (ispublic=1 AND groupid=(SELECT status FROM umg_user WHERE pkid = ?))"
					+ "   )" + "   AND begin_date <= ? AND ? <= end_date"
					+ " )" + " ORDER BY areaid,userid,show_order";

			values[0] = new Integer(userId);
			for (int i = 1; i < areaIdSize + 1; i++) {
				values[i] = areaIds.get(i - 1);
			}
		} else {
			sql = "SELECT {t1.*} FROM code_user_org_idx t1"
					+ " WHERE areaid IN ("
					+ ps
					+ ")"
					+ " AND ("
					+ "   ispublic=0"
					+ "   OR (ispublic=1 AND groupid=(SELECT status FROM umg_user WHERE pkid = ?))"
					+ "   OR (ispublic=2 AND userid=?)" + " )"
					+ " AND begin_date <= ? AND ? <= end_date "
					+ " ORDER BY areaid,userid,show_order";

			for (int i = 0; i < areaIdSize; i++) {
				values[i] = areaIds.get(i);
			}
			values[areaIdSize] = new Integer(userId);
		}

		values[areaIdSize + 1] = new Integer(userId);
		values[areaIdSize + 2] = date;
		values[areaIdSize + 3] = date;

		List fl = list(sql, new Object[][] { { "t1", OrganSystemInfo.class } },
				null, values);

		return fl;
	}

	/**
	 * 根据id取得机构系统
	 * 
	 * @param organSystemId
	 * @return
	 */
	public OrganSystemInfo getOrganSystem(int organSystemId) {

		return (OrganSystemInfo) getObject(OrganSystemInfo.class, new Integer(
				organSystemId));
	}

	/**
	 * 保存机构系统信息
	 * 
	 * @param organSystem
	 * @return
	 */
	public void saveOrganSystem(OrganSystemInfo organSystem) {

		saveObject(organSystem);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.krm.slsint.organmanage2.dao.OrganTreeDAO#getSubnode(int,
	 * java.lang.String)
	 */
	public List getSubnode(int organSystemId, String nodeId) {
		String sql = "select {n.*} from code_org_tree n where n.parentId = ? and n.idxid = ?";
		List tmpList = list(
				sql,
				new Object[][] { { "n", OrganTreeNode.class } },
				null,
				new Object[] { new Integer(nodeId), new Integer(organSystemId) });

		return tmpList;
	}



}
