package com.krm.ps.sysmanage.organmanage2.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.ezmorph.bean.MorphDynaBean;

import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.organmanage2.model.OrganNode;
import com.krm.ps.sysmanage.organmanage2.model.OrganSystem;
import com.krm.ps.sysmanage.organmanage2.model.OrganTree;
import com.krm.ps.sysmanage.usermanage.vo.Dictionary;
import com.krm.ps.sysmanage.usermanage.vo.Role;

public class JsonUtil {
	
	private static int i = 0;
	
	public static List getOrganJson(List organs){
		List organJson = new ArrayList();
		for(Iterator itr = organs.iterator();itr.hasNext();){
			OrganInfo organInfo = (OrganInfo)itr.next();
			Map map = new HashMap();
			map.put("id",organInfo.getPkid());
			map.put("name",organInfo.getShort_name());
			map.put("type",organInfo.getOrgan_type());
			map.put("areaId",organInfo.getSuper_id());
			map.put("beginDate",organInfo.getBegin_date());
			map.put("endDate",organInfo.getEnd_date());
			map.put("code", organInfo.getCode());
			organJson.add(map);
		}
		return organJson;
	}
	public static List getRoleJson(List roleList) {
		List roleJson = new ArrayList();
		for(Iterator itr = roleList.iterator(); itr.hasNext();) {
			Role role = (Role)itr.next();
			Map map = new HashMap();
			map.put("id", role.getPkid());
			map.put("name", role.getName());
			roleJson.add(map);
		}
		return roleJson;
	}
	public static List getFreqJson(List freqList) {
		List freqJson = new ArrayList();
		for(Iterator itr = freqList.iterator(); itr.hasNext();) {
			Dictionary dic = (Dictionary)itr.next();
			Map map = new HashMap();
			map.put("freqValue", dic.getDicid());
			map.put("freqName", dic.getDicname());
			freqJson.add(map);
		}
		return freqJson;
	}
	
	public static List getReportJson(List reportList){
	
		return reportList;
	}
	
	public static List getUserJson(List userList){
		List userJson = new ArrayList();
		for(Iterator itr = userList.iterator(); itr.hasNext();) {
			Object [] object = (Object [])itr.next();
			Map map = new HashMap();
			map.put("id", (Long)object[0]);
			map.put("name", (String)object[1]);
			userJson.add(map);
		}
		return userJson;
	}
	
	public static List getOrganSystemJson(List organSystemList){
		List organSystemJson = new ArrayList();
		for(Iterator itr = organSystemList.iterator();itr.hasNext();){
			OrganSystem organSystem = (OrganSystem)itr.next();
			Map map = new HashMap();
			map.put("id",organSystem.getId());
			map.put("name",organSystem.getName());
			map.put("creatorId",new Integer(organSystem.getCreatorId()));
			map.put("areaId",organSystem.getAreaId());
			map.put("groupId",new Integer(organSystem.getGroupId()));
			map.put("beginDate",organSystem.getBeginDate());
			map.put("endDate",organSystem.getEndDate());
			map.put("use", organSystem.getIsUse());
			organSystemJson.add(map);
		}
		return organSystemJson;
	}
	
 
	
	public static List getJson(OrganTree organTree){
		OrganNode organNode = organTree.getTopOrgan();
		List treeJson = new ArrayList();
		Map root = new HashMap();
		root.put("id","root");
		root.put("txt",organNode.getName());
		root.put("organId",Integer.toString(organNode.getOrganId()));
		root.put("onclick","myClick");
		root.put("onedit","myEdit");
		List children = organNode.getChildren();
		if(children != null){
			List list = new ArrayList();
			root.put("items",list);
			root.put("onopen", "myOpen");
			for(Iterator itr = children.iterator();itr.hasNext();){
				OrganNode node = (OrganNode)itr.next();
//				list.add(getChild(node));
				list.add(getSubChild(node));
//				list.add(new HashMap());
			}
		}
		treeJson.add(root);
		return treeJson;
	}
	
	public static Map getSubChild(OrganNode child){
		Map map = new HashMap();
		map.put("id","node_"+i++);
		map.put("txt",child.getName());
		map.put("onclick","myClick");
		map.put("onedit","myEdit");
		map.put("organId",Integer.toString(child.getOrganId()));
		List children = child.getChildren();
		if(children != null){
			List list = new ArrayList();
			Map map1 = new HashMap();
			map.put("items",list);
			map.put("onopen", "myOpen");
			map1.put("id", "delete");
			map1.put("txt", "waitting...");
			list.add(map1);
		}
		return map;
	}
	
	public static List getItems(List child){
		List list = new ArrayList();
		for(Iterator itr = child.iterator();itr.hasNext();){
			OrganNode organNode = (OrganNode)itr.next();
			list.add(getSubChild(organNode));
		}
		return list;
	}
	
	public static Map getChild(OrganNode child){
		 Map map = new HashMap();
		 map.put("id","node_"+i++);
		 map.put("txt",child.getName());
		 map.put("onclick","myClick");
		 map.put("onedit","myEdit");
		 map.put("organId",Integer.toString(child.getOrganId()));
		 List children = child.getChildren();
		 if(children != null){
			List list = new ArrayList();
			map.put("items",list);
			map.put("onopen", "myOpen");
			for(Iterator itr = children.iterator();itr.hasNext();){
				OrganNode node = (OrganNode)itr.next();
				list.add(getChild(node));
			}
		 }
		 return map;
	}
	
	public static OrganNode loadOrganNode(MorphDynaBean dynaBean){
		OrganNode organNode = new OrganNode();
		organNode.setOrganId(Integer.parseInt((String)dynaBean.get("organId")));
		organNode.setAlias((String)dynaBean.get("txt"));
		List children = null;
		try {
			children = (List)dynaBean.get("items");
		} catch (RuntimeException e) {
			System.out.println(organNode);
		}
		if(children != null){
			for(Iterator itr = children.iterator();itr.hasNext();){
				MorphDynaBean bean = (MorphDynaBean)itr.next();
				organNode.addChild(loadOrganNode(bean));
			}
		}
		return organNode;
	}
	
	public static OrganNode loadOrganNode(MorphDynaBean dynaBean, Map treeNode){
		OrganNode organNode = new OrganNode();
		String nodeOrganId = null;
		List children = null;
		try{
			nodeOrganId = (String) dynaBean.get("organId");
		}catch (RuntimeException e){System.out.println(organNode);}		
		if(nodeOrganId != null){
			organNode.setOrganId(Integer.parseInt(nodeOrganId));
			organNode.setAlias((String)dynaBean.get("txt"));
		}
		try {
			children = (List)dynaBean.get("items");
		} catch (RuntimeException e) {System.out.println(organNode);}
		if(children != null){
			MorphDynaBean mdb = (MorphDynaBean)children.get(0);
			if(((String)mdb.get("id")).equals("delete")){
				OrganNode node = (OrganNode)treeNode.get(nodeOrganId);
				organNode.setChildren(node.getChildren());
			}else{
				for(Iterator itr = children.iterator();itr.hasNext();){
					MorphDynaBean bean = (MorphDynaBean)itr.next();
					organNode.addChild(loadOrganNode(bean, treeNode));
				}
			}
		}
		return organNode;
	}
	
 
	
	 
	/**
	 * 
	 * $responce->rows[$i]['id']=$row[id];
     *	$responce->rows[$i]['cell']=array($row[id],$row[invdate],$row[name],$row[amount],$row[tax],$row[total],$row[note]);
     *	$i++;
	 */
	public static List getLogJson(List organTypeList, Map logMap)
	{
		List logJson = new ArrayList();
	 
		return logJson;
	}
	public static List getLogLockJson(Map logMap)
	{
		List logJson = new ArrayList();
	 
		return logJson;
	}
}
