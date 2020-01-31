package com.krm.ps.sysmanage.organmanage2.web.action;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ezmorph.bean.MorphDynaBean;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.organmanage2.model.OrganNode;
import com.krm.ps.sysmanage.organmanage2.model.OrganSystem;
import com.krm.ps.sysmanage.organmanage2.model.OrganTree;
import com.krm.ps.sysmanage.organmanage2.services.AreaService;
import com.krm.ps.sysmanage.organmanage2.services.OrganService2;
import com.krm.ps.sysmanage.organmanage2.services.OrganTreeManager;
import com.krm.ps.sysmanage.organmanage2.util.JsonUtil;
import com.krm.ps.sysmanage.organmanage2.web.form.OrganTreeForm;
import com.krm.ps.sysmanage.usermanage.services.DictionaryService;
import com.krm.ps.sysmanage.usermanage.services.UserService;
import com.krm.ps.sysmanage.usermanage.vo.Dictionary;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.util.FuncConfig;
import com.krm.ps.util.Util;

/**
 * @struts.action name="organTreeForm" path="/organTreeAction" scope="request"
 *                validate="false" parameter="method"
 * 			<forward name="beforeadd" path="/common/tree.jsp" redirect="false"/>
 * @struts.action-forward name="list" path="/organTreeManage/viewOrganTree.jsp"
 * @struts.action-forward name="edit" path="/organTreeManage/organTreeForm.jsp"
 * @struts.action-forward name="new" path="/organTreeManage/organTreeForm.jsp"
 * @struts.action-forward name="copy" path="/organTreeManage/organTreeForm.jsp"
 * @struts.action-forward name="tree" path="/common/tree.jsp"
 * @struts.action-forward name="sort" path="/common/sortcommon.jsp"
 * @struts.action-forward name="sort_list"
 *                        path="/organTreeAction.do?method=list"
 * @struts.action-forward name="viewtree" path="/organTreeManage/organTree.jsp"
 */

public class OrganTreeAction extends BaseAction
{

	private static final int ORGANTYPE_PARENT_ID = 1001;

	private static final int GROUT_ID = 2001;

	// 显示机构树列表
	public ActionForward list(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		if (log.isDebugEnabled()) {
			log.info("Entering 'list' method");
		}

		try{
			User user = getUser(request);
			Long userId = user.getPkid();
			String date = (String) request.getSession().getAttribute("logindate");
			date = date.replaceAll("-", "");
			UserService userService = (UserService) getBean("userService");
			OrganTreeManager organTreeManager = (OrganTreeManager) this
				.getBean("organTreeManager");
			List organSystemList = organTreeManager.listOrganSystems(userId
				.intValue(), date, 1);
			List systemList = new ArrayList();
			for (Iterator itr = organSystemList.iterator(); itr.hasNext();)
			{
				OrganSystem organSystem = (OrganSystem) itr.next();
				User u = userService.getUser(new Long(organSystem.getCreatorId()));
				organSystem.setCreatorName(u.getName());
				organSystem.setBeginDate(organSystem.getBeginDate().substring(0, 4)
					+ "-" + organSystem.getBeginDate().substring(4, 6) + "-"
					+ organSystem.getBeginDate().substring(6, 8));
				organSystem.setEndDate(organSystem.getEndDate().substring(0, 4)
					+ "-" + organSystem.getEndDate().substring(4, 6) + "-"
					+ organSystem.getEndDate().substring(6, 8));
				systemList.add(organSystem);
			}
			request.setAttribute("organSystemList", systemList);
			request.setAttribute("userId", userId);
			String refreshOrganSystemList = (String) request
				.getAttribute("refreshOrganSystemList");
			request.setAttribute("refreshOrganSystemList", refreshOrganSystemList);
			return mapping.findForward("list");
		}catch(Exception e){
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw, true));
			String str = sw.toString();
			request.setAttribute("errors", str);
			request.setAttribute("errorMessages", e);
			return mapping.findForward("error");
		}
	}

	// 新建机构树
	public ActionForward newTree(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		if (log.isDebugEnabled()) {
			log.info("Entering 'newTree' method");
		}
		DictionaryService dictionaryService = (DictionaryService) getBean("dictionaryService");
		List organTypes = dictionaryService.getDics(ORGANTYPE_PARENT_ID);
		List group = dictionaryService.getDics(GROUT_ID);
		String typeId = "";
		String typeName = "";
		String groupId = "";
		String groupName = "";
		if (organTypes != null)
		{
			for (Iterator itr = organTypes.iterator(); itr.hasNext();)
			{
				Dictionary dictionary = (Dictionary) itr.next();
				if (typeId.equals("") && typeName.equals(""))
				{
					typeId = dictionary.getDicid().toString();
					typeName = dictionary.getDicname();
				}
				else
				{
					typeId += "," + dictionary.getDicid().toString();
					typeName += "," + dictionary.getDicname();
				}
			}
			request.setAttribute("typeId", typeId);
			request.setAttribute("typeName", typeName);
		}
		if (group != null)
		{
			for (Iterator itr = group.iterator(); itr.hasNext();)
			{
				Dictionary dictionary = (Dictionary) itr.next();
				if (groupId.equals("") && groupName.equals(""))
				{
					groupId = dictionary.getDicid().toString();
					groupName = dictionary.getDicname();
				}
				else
				{
					groupId += "," + dictionary.getDicid().toString();
					groupName += "," + dictionary.getDicname();
				}
			}
			request.setAttribute("groupId", groupId);
			request.setAttribute("groupName", groupName);
		}
		User user = getUser(request);
		if (user.getIsAdmin().intValue() == 2)
		{
			request.setAttribute("isAdmin", "1");
		}
		else
		{
			request.setAttribute("isAdmin", "0");
		}
		AreaService areaService = (AreaService) getBean("areaService");
		String areaCode = areaService.getAreaCodeByUser(getUser(request)
			.getPkid().intValue());
		OrganTreeForm organTreeForm = (OrganTreeForm) form;
		String beginDate =Util.thisDate();
		String year = beginDate.substring(0, 4);
		year = new Integer(Integer.parseInt(year) - 5).toString();
		beginDate = year + beginDate.substring(4);
		beginDate = beginDate.substring(0, 4) + "-" + beginDate.substring(4, 6)
			+ "-" + beginDate.substring(6, 8);
		organTreeForm.setBeginDate(beginDate);
		String endDate = Util.thisDate();
		year = endDate.substring(0, 4);
		year = new Integer(Integer.parseInt(year) + 5).toString();
		endDate = year + endDate.substring(4);
		endDate = endDate.substring(0, 4) + "-" + endDate.substring(4, 6) + "-"
			+ endDate.substring(6, 8);
		organTreeForm.setEndDate(endDate);
		updateFormBean(mapping, request, organTreeForm);
		request.setAttribute("areaCode", areaCode);
		request.setAttribute("myJson", "[]");
		request.setAttribute("newOrEdit", "0");
		request.setAttribute("refreshOrganSystemList", "need");
		return mapping.findForward("new");
	}

	// 编辑机构树
	public ActionForward editTree(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		if (log.isDebugEnabled()) {
			log.info("Entering 'editTree' method");
		}
		DictionaryService dictionaryService = (DictionaryService) getBean("dictionaryService");
		List organTypes = dictionaryService.getDics(ORGANTYPE_PARENT_ID);
		List group = dictionaryService.getDics(GROUT_ID);
		String typeId = "";
		String typeName = "";
		String groupId = "";
		String groupName = "";
		if (organTypes != null)
		{
			for (Iterator itr = organTypes.iterator(); itr.hasNext();)
			{
				Dictionary dictionary = (Dictionary) itr.next();
				if (typeId.equals("") && typeName.equals(""))
				{
					typeId = dictionary.getDicid().toString();
					typeName = dictionary.getDicname();
				}
				else
				{
					typeId += "," + dictionary.getDicid().toString();
					typeName += "," + dictionary.getDicname();
				}
			}
			request.setAttribute("typeId", typeId);
			request.setAttribute("typeName", typeName);
		}
		if (group != null)
		{
			for (Iterator itr = group.iterator(); itr.hasNext();)
			{
				Dictionary dictionary = (Dictionary) itr.next();
				if (groupId.equals("") && groupName.equals(""))
				{
					groupId = dictionary.getDicid().toString();
					groupName = dictionary.getDicname();
				}
				else
				{
					groupId += "," + dictionary.getDicid().toString();
					groupName += "," + dictionary.getDicname();
				}
			}
			request.setAttribute("groupId", groupId);
			request.setAttribute("groupName", groupName);
		}
		User user = getUser(request);
		if (user.getIsAdmin().intValue() == 2)
		{
			request.setAttribute("isAdmin", "1");
		}
		else
		{
			request.setAttribute("isAdmin", "0");
		}
		String organSystemId = request.getParameter("treeId");
		OrganTreeManager organTreeManager = (OrganTreeManager) this
			.getBean("organTreeManager");
		OrganTree organTree = organTreeManager.getOrganTree(Integer
			.parseInt(organSystemId));
		List tree = JsonUtil.getJson(organTree);
		JSONArray json = JSONArray.fromObject(tree);
		OrganSystem organSystem = organTreeManager.getOrganSystem(Integer
			.parseInt(organSystemId));
		OrganTreeForm organTreeForm = (OrganTreeForm) form;
		organTreeForm.setId(Integer.toString(organSystem.getId().intValue()));
		setFormByOrganSystem(organTreeForm, organSystem);
		updateFormBean(mapping, request, organTreeForm);
		AreaService areaService = (AreaService) getBean("areaService");
		String areaCode = areaService.getAreaCodeByUser(getUser(request)
			.getPkid().intValue());
		request.setAttribute("areaCode", areaCode);
		response.setContentType("text/html; charset=UTF-8");
		request.setAttribute("myJson", json);
		request.setAttribute("rootId", "root");
		request.setAttribute("newOrEdit", "1");
		request.setAttribute("refreshOrganSystemList", "need");
		request.setAttribute("organSystemId", organSystemId);
		if (request.getParameter("viewtree") != null)
		{
			return mapping.findForward("viewtree");
		}
		return mapping.findForward("edit");
	}

	// 删除机构树
	public ActionForward deleteTree(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		if (log.isDebugEnabled()) {
			log.info("Entering 'deleteTree' method");
		}
		// 根据TreeId删除机构树
		String organSystemId = request.getParameter("treeId");
		OrganTreeManager organTreeManager = (OrganTreeManager) this
			.getBean("organTreeManager");
		organTreeManager.removeOrganTree(Integer.parseInt(organSystemId));

		request.setAttribute("refreshOrganSystemList", "need");
		return list(mapping, form, request, response);
	}

	// 复制机构树
	public ActionForward copyTree(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		if (log.isDebugEnabled()) {
			log.info("Entering 'copyTree' method");
		}
		DictionaryService dictionaryService = (DictionaryService) getBean("dictionaryService");
		List organTypes = dictionaryService.getDics(ORGANTYPE_PARENT_ID);
		List group = dictionaryService.getDics(GROUT_ID);
		String typeId = "";
		String typeName = "";
		String groupId = "";
		String groupName = "";
		if (organTypes != null)
		{
			for (Iterator itr = organTypes.iterator(); itr.hasNext();)
			{
				Dictionary dictionary = (Dictionary) itr.next();
				if (typeId.equals("") && typeName.equals(""))
				{
					typeId = dictionary.getDicid().toString();
					typeName = dictionary.getDicname();
				}
				else
				{
					typeId += "," + dictionary.getDicid().toString();
					typeName += "," + dictionary.getDicname();
				}
			}
			request.setAttribute("typeId", typeId);
			request.setAttribute("typeName", typeName);
		}
		if (group != null)
		{
			for (Iterator itr = group.iterator(); itr.hasNext();)
			{
				Dictionary dictionary = (Dictionary) itr.next();
				if (groupId.equals("") && groupName.equals(""))
				{
					groupId = dictionary.getDicid().toString();
					groupName = dictionary.getDicname();
				}
				else
				{
					groupId += "," + dictionary.getDicid().toString();
					groupName += "," + dictionary.getDicname();
				}
			}
			request.setAttribute("groupId", groupId);
			request.setAttribute("groupName", groupName);
		}
		User user = getUser(request);
		if (user.getIsAdmin().intValue() == 2)
		{
			request.setAttribute("isAdmin", "1");
		}
		else
		{
			request.setAttribute("isAdmin", "0");
		}
		// 根据TreeId先得到机构Tree,然后根据当前用户Id查看用户所属机构,
		// 在Tree上找到用户所属机构Id,把用户所属机构当作root节点向下复制.
		String organSystemId = request.getParameter("treeId");
		OrganTreeManager organTreeManager = (OrganTreeManager) this
			.getBean("organTreeManager");
		OrganTree organTree = organTreeManager.getOrganTree(Integer
			.parseInt(organSystemId), ((OrganInfo) request.getSession()
			.getAttribute("curorgan")).getPkid().intValue());
		List tree = JsonUtil.getJson(organTree);
		JSONArray json = JSONArray.fromObject(tree);
		OrganSystem organSystem = organTreeManager.getOrganSystem(Integer
			.parseInt(organSystemId));
		OrganTreeForm organTreeForm = (OrganTreeForm) form;
		setFormByOrganSystem(organTreeForm, organSystem);//
		updateFormBean(mapping, request, organTreeForm);
		AreaService areaService = (AreaService) getBean("areaService");
		String areaCode = areaService.getAreaCodeByUser(getUser(request)
			.getPkid().intValue());
		request.setAttribute("areaCode", areaCode);
		response.setContentType("text/html; charset=UTF-8");
		request.setAttribute("myJson", json);
		request.setAttribute("rootId", "root");
		request.setAttribute("newOrEdit", "1");
		request.setAttribute("refreshOrganSystemList", "need");
		request.setAttribute("organSystemId", organSystemId);
		return mapping.findForward("copy");
	}

	// 根据OrganSystem对象设值表单对象属性（不包括id）
	private void setFormByOrganSystem(OrganTreeForm organTreeForm,
		OrganSystem organSystem)
	{
		organTreeForm.setName(organSystem.getName());
		organTreeForm.setAreaId(organSystem.getAreaId());
		organTreeForm.setBeginDate(organSystem.getBeginDate().substring(0, 4)
			+ "-" + organSystem.getBeginDate().substring(4, 6) + "-"
			+ organSystem.getBeginDate().substring(6, 8));
		organTreeForm.setEndDate(organSystem.getEndDate().substring(0, 4) + "-"
			+ organSystem.getEndDate().substring(4, 6) + "-"
			+ organSystem.getEndDate().substring(6, 8));
		organTreeForm
			.setCreatorId(Integer.toString(organSystem.getCreatorId()));
		organTreeForm.setGroup(Integer.toString(organSystem.getGroupId()));
		organTreeForm
			.setIsPublic(Integer.toString(organSystem.getVisibility()));
	}

	// 解析json,保存机构树
	public ActionForward saveTree(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		if (log.isDebugEnabled()) {
			log.info("Entering 'saveTree' method");
		}
		OrganTreeForm organTreeForm = (OrganTreeForm) form;
		OrganTreeManager organTreeManager = (OrganTreeManager) this
			.getBean("organTreeManager");

		Map treeNode = new HashMap();
		if (!organTreeForm.getOrgansId().equals(""))
		{
			String organSystemId = request.getParameter("organSystemId");
			String organsId = organTreeForm.getOrgansId();
			String[] organId = organsId.split(",");
			for (int i = 0; i < organId.length; i++)
			{
				OrganTree organTree = organTreeManager.getOrganTree(Integer
					.parseInt(organSystemId), Integer.parseInt(organId[i]));
				OrganNode organNode = organTree.getTopOrgan();
				treeNode.put(organId[i], organNode);
			}
		}

		String myJson = organTreeForm.getJson();
		JSONObject json = JSONObject.fromObject(myJson);
		MorphDynaBean obj = (MorphDynaBean) JSONObject.toBean(json);
		OrganNode organNode = JsonUtil.loadOrganNode(obj, treeNode);
		OrganTree organTree = new OrganTree();
		organTree.setTopOrgan(organNode);
		OrganSystem organSystem = new OrganSystem();
		if (!organTreeForm.getId().equals(""))
		{
			organSystem.setId(new Integer(organTreeForm.getId()));
		}

		User user = getUser(request);
		AreaService areaService = (AreaService) this.getBean("areaService");
		String areaId = areaService
			.getAreaCodeByUser(user.getPkid().intValue());
		organSystem.setAreaId(areaId);// 地区编码
		organSystem.setBeginDate(organTreeForm.getBeginDate().replaceAll("-",
			""));
		organSystem.setEndDate(organTreeForm.getEndDate().replaceAll("-", ""));
		if (organTreeForm.getCreatorId().equals(""))
		{
			organSystem.setCreatorId(getUser(request).getPkid().intValue());
		}
		else
		{
			organSystem.setCreatorId(Integer.parseInt(organTreeForm
				.getCreatorId()));
		}

		organSystem.setName(organTreeForm.getName());
		organSystem
			.setVisibility(Integer.parseInt(organTreeForm.getIsPublic()));
		organSystem.setGroupId(Integer.parseInt(organTreeForm.getGroup()));
		organSystem.setStatus("1");
		organTree.setOrganSystem(organSystem);
		organTreeManager.saveOrganTree(organTree);

		request.setAttribute("refreshOrganSystemList", "need");
		return list(mapping, form, request, response);
	}
	
	// 读取地区下的机构数
		public ActionForward getOrganByTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
		{
			if (log.isDebugEnabled()) {
				log.info("Entering 'getOrganByTree' method");
			}
			String organId = request.getParameter("organId");
		//	String organId = request.getParameter("areaId");
			AreaService areaService = (AreaService) this.getBean("areaService");
	//		String areaId = areaService.getAreaCodeByUser(getUser(request).getPkid().intValue());
			String organTreeXml = areaService.getOrganTreeXMLByArea(organId);
			request.setAttribute("treeXml", organTreeXml);
			return mapping.findForward("beforeadd");
		}
	
	
		// 读取地区列表树
	public ActionForward tree(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		if (log.isDebugEnabled()) {
			log.info("Entering 'tree' method");
		}
		String date = (String) request.getSession().getAttribute("logindate");
		AreaService areaService = (AreaService) this.getBean("areaService");
		String areaId = areaService.getAreaCodeByUser(getUser(request)
			.getPkid().intValue());
		
		if(FuncConfig.isOpend("oldAreaTree")){
			String areaTreeXml = areaService.getAreaTreeXML(areaId, date.replaceAll("-", ""));
			request.setAttribute("treeXml", areaTreeXml);
			return mapping.findForward("tree");
		}else{
			String areaTreeXml = areaService.getAreaTreeXML_temp(areaId, date.replaceAll("-", ""));
			JSONObject json = new JSONObject();
			json.put("treeXml", areaTreeXml);
			request.setAttribute("treeXml", areaTreeXml);
			String dateJson = JSONArray.fromObject(json).toString();
			response.setContentType("text/html;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			writer.write(dateJson);
			writer.close();
			return null;
		}
	}
	
	// 更改地区tree时,查询该地区下所属机构
	// public ActionForward getBuildProgress(ActionMapping mapping, ActionForm
	// form,
	public ActionForward getOrganList(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		if (log.isDebugEnabled()) {
			log.info("Entering 'getOrganList' method");
		}
		String areaId = request.getParameter("areaId");
		AreaService areaService = (AreaService) this.getBean("areaService");
		List organList = areaService.getOrgansByArea(areaId);
		List organJson = JsonUtil.getOrganJson(organList);
		JSONArray json = JSONArray.fromObject(organJson);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
		return null;
	}

	// 对机构树排序
	public ActionForward sortOrganTree(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		if (log.isDebugEnabled()) {
			log.info("Entering 'sortOrganTree' method");
		}
		// 根据当前登陆用户从查询中机构树中过滤出当前登陆用户所创建的机构树,对这些机构树进行排序
		setToken(request);
		User user = getUser(request);
		int userId = user.getPkid().intValue();
		String date = (String) request.getSession().getAttribute("logindate");
		date = date.replaceAll("-", "");
		OrganTreeManager organTreeManager = (OrganTreeManager) this
			.getBean("organTreeManager");
		List organSystemList = organTreeManager.listOrganSystems(userId, date,
			1);
		List list = new ArrayList();
		for (Iterator itr = organSystemList.iterator(); itr.hasNext();)
		{
			OrganSystem organSystem = (OrganSystem) itr.next();
			if (userId == organSystem.getCreatorId())
			{
				Object[] o = new Object[2];
				o[0] = organSystem.getId();
				o[1] = organSystem.getName();
				list.add(o);
			}
		}
		request.setAttribute("fileTitle", "系统管理－》机构树排序");
		request.setAttribute("sortList", list);
		request.setAttribute("serviceName", "organTreeManager");
		request.setAttribute("sortTitle", "报表类型排序");
		ActionForward forward = mapping.findForward("sort_list");
		String path = forward.getPath();
		request.setAttribute("forwardURL", path);
		return mapping.findForward("sort");
	}

	// 更改Menu上时间
	public ActionForward changeDate(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		if (log.isDebugEnabled()) {
			log.info("Entering 'changeDate' method");
		}
		String date = request.getParameter("date");
		int userId = getUser(request).getPkid().intValue();
		date = date.replaceAll("-", "");
		OrganTreeManager organTreeManager = (OrganTreeManager) this
			.getBean("organTreeManager");
		List organTreeList = organTreeManager.listOrganSystems(userId, date);
		String id = "";
		String name = "";
		for (Iterator itr = organTreeList.iterator(); itr.hasNext();)
		{
			OrganSystem organSystem = (OrganSystem) itr.next();
			if (id.equals("") && name.equals(""))
			{
				id = organSystem.getId().toString();
				name = organSystem.getName();
			}
			else
			{
				id += "," + organSystem.getId().toString();
				name += "," + organSystem.getName();
			}
		}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(id + "," + name);
		out.flush();
		out.close();
		return null;
	}

	/*
	 * comment out by wsx public ActionForward getAreaId(ActionMapping mapping,
	 * ActionForm form, HttpServletRequest request, HttpServletResponse
	 * response) throws Exception { String organId =
	 * request.getParameter("organId"); AreaService areaService =
	 * (AreaService)this.getBean("areaService"); String areaId =
	 * areaService.getAreaCodeByUser(Integer.parseInt(organId));//��id�����û�id?
	 * response.setContentType("text/html;charset=UTF-8"); PrintWriter out =
	 * response.getWriter(); out.print(areaId); out.flush(); out.close(); return
	 * null; }
	 */

	public ActionForward getOrganTree(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		if (log.isDebugEnabled()) {
			log.info("Entering 'getOrganTree' method");
		}
		boolean isTreeNode = false;
		String date = (String) request.getSession().getAttribute("logindate");
		date = date.replaceAll("-", "");
		int userId = getUser(request).getPkid().intValue();
		// OrganService2 organService2 =
		// (OrganService2)getBean("organService2");
		OrganTreeManager organTreeManager = (OrganTreeManager) this
			.getBean("organTreeManager");
		OrganInfo organInfo = (OrganInfo) request.getSession().getAttribute(
			"curorgan");
		OrganService2 organService2 = (OrganService2) getBean("organService2");
		List organSystemList = organTreeManager.listOrganSystems(userId, date);
	
		for (Iterator itr = organSystemList.iterator(); itr.hasNext();)
		{
			OrganSystem organSystem = (OrganSystem) itr.next();
			isTreeNode = organService2.isTreeNode(organSystem.getId(),
				organInfo.getPkid());
			if (isTreeNode)
			{
				organSystem.setIsUse("yes");
			}
			else
			{
				organSystem.setIsUse("no");
			}
		}
		List organSystemJson = JsonUtil.getOrganSystemJson(organSystemList);
		JSONArray json = JSONArray.fromObject(organSystemJson);
		OrganSystem defaultOrganSystem = new OrganSystem();
		if (organSystemList.size() != 0)
		{
			defaultOrganSystem = (OrganSystem) organSystemList.get(0);
		}
		// OrganInfo organInfo =
		// (OrganInfo)request.getSession().getAttribute("curorgan");
		// String xml =
		// organService2.getOrganTreeXML(defaultOrganSystem.getId().intValue(),organInfo.getPkid().intValue(),date);
		User user = getUser(request);
		// user.setOrganTreeXML(xml);
		user.setOrganTreeIdxid(defaultOrganSystem.getId().intValue());
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(json);
		out.flush();
		out.close();
		return null;
	}

	public ActionForward changeOrganTree(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
		HttpServletResponse response) throws Exception
	{
		if (log.isDebugEnabled()) {
			log.info("Entering 'changeOrganTree' method");
		}
		String treeId = request.getParameter("treeId");
		//System.out.println("treeId=[" + treeId + "]");
		// String date = (String)request.getSession().getAttribute("logindate");
		// date = date.replaceAll("-","");
		// OrganInfo organInfo =
		// (OrganInfo)request.getSession().getAttribute("curorgan");
		User user = getUser(request);
		// OrganService2 organService2 =
		// (OrganService2)getBean("organService2");
		// String xml =
		// organService2.getOrganTreeXML(Integer.parseInt(treeId),organInfo.getPkid().intValue(),date);
		// user.setOrganTreeXML(xml);
		user.setOrganTreeIdxid(Integer.parseInt(treeId));
		// /////////////////////////////////////////////////////////////////
		ServletContext servletContext = servlet.getServletContext();
		Map uDefineTreeMap = (Map) servletContext
			.getAttribute("userDefineTree");
		uDefineTreeMap.remove(user.getPkid());
		uDefineTreeMap.put(user.getPkid(), new Integer(treeId));
		servletContext.setAttribute("userDefineTree", uDefineTreeMap);
		// /////////////////////////////////////////////////////////////////////
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print("ok");
		out.flush();
		out.close();
		return null;
	}

	public ActionForward getSubChild(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		if (log.isDebugEnabled()) {
			log.info("Entering 'getSubChild' method");
		}
		String treeId = request.getParameter("treeId");
		String organId = request.getParameter("organId");
		OrganTreeManager organTreeManager = (OrganTreeManager) this
			.getBean("organTreeManager");
		OrganTree organTree = organTreeManager.getOrganTree(Integer
			.parseInt(treeId), Integer.parseInt(organId));
		OrganNode organNode = organTree.getTopOrgan();
		List json = JsonUtil.getItems(organNode.getChildren());
		JSONArray myJson = JSONArray.fromObject(json);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		// System.out.println(myJson);
		out.print(myJson);
		out.flush();
		out.close();
		return null;
	}

	/*
	 * 机构树，实现方式DHTMLXTREE add by dong
	 */
	public ActionForward getDhtmlxTree(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		if (log.isDebugEnabled()) {
			log.info("Entering 'getDhtmlxTree' method");
		}
		response.setContentType("text/html;charset=gbk");
		OrganInfo organInfo = (OrganInfo)request.getSession().getAttribute("curorgan");
		String date = (String) request.getSession().getAttribute("logindate");
		date = date.replaceAll("-", "");
		OrganTreeManager organTreeManager = (OrganTreeManager) this
			.getBean("organTreeManager");
		OrganService2 organService2 = (OrganService2)getBean("organService2");
		int organSystemId = Integer.parseInt((request.getSession()
			.getAttribute("min_tree_id")).toString());
		PrintWriter out = response.getWriter();
		OrganTree organTree = organTreeManager.getOrganTree(organSystemId, organInfo.getPkid().intValue());
		organTree = organService2.filtOrganTree(organTree, new Integer("501"), date);
		StringBuffer buffer = new StringBuffer("");
		if (request.getParameter("id") != null)
		{
			String organId = request.getParameter("id");
			geneteDhtmlxtree(organTree, buffer, organId);

		}
		else
		{
			buffer.append("<?xml version='1.0' encoding='gbk'?>");
			buffer.append("<tree id='0' radio='1'>");
			geneteDhtmlxtree(organTree, buffer, null);
			buffer.append("</tree>");

		}
		String xtreexml = buffer.toString();
		request.setAttribute("organSystemId", organSystemId + "");
		// request.getSession().setAttribute("xtreexml", xtreexml);
		// out.write(buffer.toString());
		//System.out.println(xtreexml);
		out.println(xtreexml);
		out.flush();
		out.close();
		return null;
	}

	/*
	 * Dhtmlxtree方式组织机构树 add by dong
	 */
	private void geneteDhtmlxtree(OrganTree organTree, StringBuffer buffer,
		String nodeId)
	{
		OrganNode organNode = organTree.getTopOrgan();
		List children = organNode.getChildren();
		String endFlag = " child='0'>";
		int subSize = 0;
		if (nodeId == null)
		{

			if (children!=null&&children.size() > 0)
			{
				subSize=children.size();
				endFlag = " child='1'>";
			}
			buffer.append("<item text='" + organNode.getName() + "' id='"
				+ organNode.getOrganCode()
				+ "' im0='jg01.gif' im1='jglx01.gif' im2='jglx02.gif'");
			buffer.append(endFlag);
			if (subSize > 0)
			{
				buildingChildrens(children, buffer, false);
			}

			buffer.append("</item>");
		}
		else
		{
			OrganNode childNode = null;
			for (int i = 0; i < children.size(); i++)
			{
				OrganNode node = (OrganNode) children.get(i);
				if (node.getOrganCode().equals(nodeId))
				{
					childNode = node;
					break;
				}
			}
			if (childNode != null)
			{
				refreshSub(childNode, buffer);
			}

		}

	}

	/*
	 * 
	 * 递归组织机构树叶子节点
	 * 
	 */
	private void buildingChildrens(List list, StringBuffer buffer,
		boolean getall)
	{
		for (int i = 0; i < list.size(); i++)
		{
			OrganNode childNode = (OrganNode) list.get(i);
			buffer.append("<item text='" + childNode.getName() + "' id='"
				+ childNode.getOrganCode()
				+ "' im0='jg01.gif' im1='jg01.gif' im2='jg02.gif'");
			String endFlag = " child='0'>";
			List childrens = childNode.getChildren();
			if (childrens != null && childrens.size() > 0)
			{
				endFlag = " child='1'>";
			}
			buffer.append(endFlag);
			if (getall)
			{
				if (childrens != null && childrens.size() > 0)
				{
					buildingChildrens(childrens, buffer, true);
				}
			}
			buffer.append("</item>");
		}

	}

	private void refreshSub(OrganNode organNode, StringBuffer buffer)
	{
		// OrganNode childNode = (OrganNode) list.get(i);
		buffer.append("<item text='" + organNode.getName() + "' id='"
			+ organNode.getOrganCode()
			+ "' im0='jg01.gif' im1='jg01.gif' im2='jg02.gif' >");
		List childrens = organNode.getChildren();
		if (childrens != null && childrens.size() > 0)
		{
			buildingChildrens(childrens, buffer, true);
		}
		buffer.append("</item>");
	}

}