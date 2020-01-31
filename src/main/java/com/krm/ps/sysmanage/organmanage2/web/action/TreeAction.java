package com.krm.ps.sysmanage.organmanage2.web.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.krm.ps.framework.common.vo.TreeElement;
import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.framework.common.web.util.TreeJson;
import com.krm.ps.sysmanage.organmanage.services.OrganService;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.organmanage2.services.AreaService;
import com.krm.ps.sysmanage.organmanage2.services.OrganService2;
import com.krm.ps.sysmanage.organmanage2.services.OrganTreeManager;
import com.krm.ps.sysmanage.organmanage2.vo.OrganTreeNode;
import com.krm.ps.sysmanage.usermanage.services.DictionaryService;
import com.krm.ps.sysmanage.usermanage.vo.Dictionary;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.util.ConvertUtil;
import com.krm.ps.util.FuncConfig;

/**
 * @struts.action  path="/treeAction" scope="request"
 *                validate="false" parameter="method"
 * 
 * @struts.action-forward name="tree" path="/common/tree.jsp"
 */
public class TreeAction extends BaseAction{
	
	/**
	 * 得到机构树,调用次方法时需在url中加参数
	 * 1.要生成xml树的树id 参数名treeId
	 * 2.日期 参数名date (如果不传入日期参数,日期将以登陆时间为准)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getOrganTreeXML(ActionMapping mapping, ActionForm form,
			   HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'getOrganTreeXML' method");
		}

		User user = getUser(request);
		HttpSession session = request.getSession();
		String treeId=request.getParameter("treeId");
		String nodeid = request.getParameter("nodeid");
		String treeState = request.getParameter("treeState");//treeState机构树是0,同步，1，异步，2，异步，机构树获取下级，3，同步，直接获取下级所有的数据，但是不把数据 put进json
		int organSystemId;
		if(treeState==null){
			treeState="0";
		}
		if(treeId==null) {
			organSystemId=user.getOrganTreeIdxid();
			//organSystemId=1;
		}else {
			organSystemId = Integer.parseInt(treeId);
			user.setOrganTreeIdxid(organSystemId);
		}
		String date = request.getParameter("date");
		if(date == null){
			date = (String)session.getAttribute("logindate");
		}
		date = date.replaceAll("-","");
		OrganInfo curOrgan=(OrganInfo)session.getAttribute("curorgan");
		int organId = curOrgan.getPkid().intValue();
		OrganService2 organService2 = (OrganService2)getBean("organService2");
		
		if(FuncConfig.isOpend("oldTree")){
			if ("1".equals(getParameterValue(request, "allTree")))
			{
				OrganTreeNode organTreeNode = organService2.getRootOrganTreeNode(user.getOrganTreeIdxid());
				organId = organTreeNode.getNodeId();
			}
			String xml = organService2.getOrganTreeXML(organSystemId,organId,date);
			request.setAttribute("treeXml", xml);
			return mapping.findForward("tree");
		}
		else{
			String xml = organService2.getOrganTreeXML_temp(organSystemId,organId,date,treeState,nodeid);
			
			response.setContentType("text/html;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			if(treeState.equals("0")){
				JSONObject json = new JSONObject();
				json.put("treeXml", xml);
				String treeJson = JSONArray.fromObject(json).toString();
				writer.write(treeJson);
			}else if(treeState.equals("1")||treeState.equals("2")||treeState.equals("3")){
				writer.write(xml);
			}
			writer.close();
			return null;
		}
	}
	
	
	/**
	 * 得到机构树,调用次方法时需在url中加参数
	 * 1.要生成xml树的树id 参数名treeId
	 * 2.日期 参数名date (如果不传入日期参数,日期将以登陆时间为准)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return(天津不包括501机构类型机构的树xml)
	 * @throws Exception
	 */
	public ActionForward getOrgTreeXML(ActionMapping mapping, ActionForm form,
			   HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'getOrgTreeXML' method");
		}
		User user = getUser(request);
		HttpSession session = request.getSession();
		String treeId=request.getParameter("treeId");
		int organSystemId;
		if(treeId==null) {
			organSystemId=user.getOrganTreeIdxid();
		}else {
			organSystemId = Integer.parseInt(treeId);
			user.setOrganTreeIdxid(organSystemId);
		}
		String date = request.getParameter("date");
		if(date == null){
			date = (String)session.getAttribute("logindate");
		}
		date = date.replaceAll("-","");
		OrganInfo curOrgan=(OrganInfo)session.getAttribute("curorgan");
		int organId = curOrgan.getPkid().intValue();
		OrganService2 organService2 = (OrganService2)getBean("organService2");
		String xml = organService2.getOrgTreeXML(organSystemId, organId, date, new Integer("501"));
		request.setAttribute("treeXml", xml);
		return mapping.findForward("tree");
	}
	/**
	 * 得到地区构树,调用次方法时需在url中加参数
	 * 1.要生成xml树的地区Id 参数名areaId
	 * 2.日期 参数名date (如果不传入日期参数,日期将以登陆时间为准)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward getAreaTreeXML(ActionMapping mapping, ActionForm form,
			   HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'getAreaTreeXML' method");
		}
		HttpSession session = request.getSession();
		AreaService areaService = (AreaService)getBean("areaService");
		String areaCode = request.getParameter("areaCode");
		String date = request.getParameter("date");
		if(date == null){
			date = (String)session.getAttribute("logindate");
		}
		if(areaCode == null){
			areaCode = areaService.getAreaCodeByUser(getUser(request).getPkid().intValue());
		}
		date = date.replaceAll("-","");
		String areaXML = areaService.getAreaTreeXML(areaCode,date);
		request.setAttribute("treeXml", areaXML);
		return mapping.findForward("tree");
	}
	
	public ActionForward isTreeNode(ActionMapping mapping, ActionForm form,
		   HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'isTreeNode' method");
		}
		String isTreeNode = "no";
		User user = getUser(request);
		String treeId = request.getParameter("treeId");
		String organCode = user.getOrganId();
		String date = (String)request.getSession().getAttribute("logindate");
		OrganService2 organService2 = (OrganService2)getBean("organService2");
		List allTreeNode = organService2.getAllSubOrgans(Integer.parseInt(treeId), organCode, date);
		for(Iterator itr = allTreeNode.iterator(); itr.hasNext();) {
			OrganInfo organInfo = (OrganInfo)itr.next();
			if(organInfo.getCode().equals(organCode)) {
				isTreeNode = "yes";
			}
		}

		request.getSession().setAttribute("currTreeAvail", isTreeNode);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(isTreeNode);
		out.flush();
		out.close();
		return null;
	}
	
	public ActionForward buildSubtreeCode(ActionMapping mapping, ActionForm form,
		   HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'buildSubtreeCode' method");
		}
		OrganTreeManager otm = (OrganTreeManager)getBean("organTreeManager");
		String idxid = request.getParameter("idxid");
		String rootId = request.getParameter("rootId");
		String pattern = request.getParameter("pattern");
		otm.buildSubtreetag(Integer.parseInt(idxid), rootId, pattern);
		return null;
	}
	
	/**
	 * <p>取得机构及部门树<p>
	 * 
	 * @author 皮亮
	 * @version 创建时间：2010-4-16 下午04:01:47
	 */
	public ActionForward getOrgAndDepartmentXML(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
	{
		if (log.isDebugEnabled()) {
			log.info("Entering 'getOrgAndDepartmentXML' method");
		}
		OrganService organService = (OrganService)getBean("organService");
		OrganService2 organService2 = (OrganService2)getBean("organService2");
		User user = getUser(request);
		OrganInfo curOrgan=(OrganInfo)request.getSession().getAttribute("curorgan");
		int organId = curOrgan.getPkid().intValue();
		String date =null;
		log.info("user.getOrganTreeIdxid()==============" + user.getOrganTreeIdxid());
		log.info("机构ID为：" + organId);
		log.info("date==========" + date);
		// 得到顶级机构，因为设置权限的时候是需要设置上级机构的权限的，这个操作是通过请求参数allTree来控制的
		if ("1".equals(getParameterValue(request, "allTree")))
		{
			OrganTreeNode organTreeNode = organService2.getRootOrganTreeNode(user.getOrganTreeIdxid());
			organId = organTreeNode.getNodeId();
		}
		String xml = organService2.gerOrgAndDepartmentXML(user.getOrganTreeIdxid(), organId, date);
		request.setAttribute("treeXml", xml);
		return mapping.findForward("tree");
	}
	
	
	/**
	 * 2012 贡琳
	 * 根据登录时间,初始化一个频度树
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getChuFrequencyTree(ActionMapping mapping, ActionForm form,
			   HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'getChuFrequencyTree' method");
		}
		
		HttpSession session = request.getSession();
		
		String cdate = request.getParameter("date");
		
		String yuefen = "";
		
		if(cdate == null){
			cdate = (String)session.getAttribute("logindate");
		}
		
		yuefen = cdate.substring(5, 7);
		int yue = Integer.parseInt(yuefen);
		
		DictionaryService dic = getDictionaryService();
		
		List list = new ArrayList();

		if(yue%3==0){
			list.add(new Integer(1));
			list.add(new Integer(2));
			list.add(new Integer(5));
			  if(yue%6==0){
				  //显示半年报；
				list.add(new Integer(1));
				list.add(new Integer(3));
				list.add(new Integer(5));
			    if(yue%12==0){
			    	 // 显示年报；
					list.add(new Integer(1));
					list.add(new Integer(4));
					list.add(new Integer(5));
			    }
			  }
			}else{
				list.add(new Integer(1));
				list.add(new Integer(5));
			}
		
		List freqList = dic.getDicss(1004,list);
		
		Dictionary dictionary=new Dictionary();
		
		dictionary.setDicid(new Long("1004"));
		dictionary.setDicname("报表频度");
		dictionary.setParentid(new Long("0"));
		freqList.add(dictionary);
		
		String[] cnames = new String[] { "dicid", "dicname", "parentid"};
		String freqXml = ConvertUtil.convertListToAdoXml(freqList, cnames);
		request.setAttribute("treeXml", freqXml);
		return mapping.findForward("tree");
	}
	
	
	
	
	/**
	 * 得到一个现实报表频度的树
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward getFrequencyTree(ActionMapping mapping, ActionForm form,
			   HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'getFrequencyTree' method");
		}
		DictionaryService dic =getDictionaryService();
		List freqList = dic.getDics(1004);
		
		Dictionary dictionary=new Dictionary();
		dictionary.setDicid(new Long("1004"));
		dictionary.setDicname("报表频度");
		dictionary.setParentid(new Long("0"));
		freqList.add(dictionary);
		String[] cnames = new String[] { "dicid", "dicname", "parentid"};
		String freqXml = ConvertUtil.convertListToAdoXml(freqList, cnames);
		request.setAttribute("treeXml", freqXml);
		return mapping.findForward("tree"); 
	}
	
	/**
	 * 得到机构树,调用次方法时需在url中加参数 1.要生成xml树的树id 参数名treeId 2.日期 参数名date
	 * (如果不传入日期参数,日期将以登陆时间为准)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getOrganTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.info("enter chart getReportTree method");
		User user = getUser(request);
		HttpSession session = request.getSession();
		// int organSystemId = 1;
		String date = request.getParameter("date");
		if (date == null) {
			date = (String) session.getAttribute("logindate");
		}
		date = date.replaceAll("-", "");
		OrganInfo curOrgan = (OrganInfo) session.getAttribute("curorgan");
		int organId = curOrgan.getPkid().intValue();
		OrganService2 organService2 = (OrganService2) getBean("organService2");
		String treeId = "1";
		String xml = organService2.getOrganTreeXML(Integer.parseInt(treeId),
				organId, date);
		request.setAttribute("treeXml", xml);
		return mapping.findForward("tree");
	}
}
