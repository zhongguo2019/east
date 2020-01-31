package com.krm.ps.sysmanage.organmanage.web.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.sysmanage.usermanage.services.DictionaryService;
import com.krm.ps.sysmanage.usermanage.vo.Dictionary;

/**
 * 
 * @struts.action path="/dicAction" scope="request" validate="false"
 *                parameter="method"
 * 
 * @struts.action-forward name="list" path="/dictionary/dicList.jsp"
 * @struts.action-forward name="sublist" path="/dictionary/subdicList.jsp"
 * @struts.action-forward name="edit" path="/dictionary/dicedit.jsp"
 */
public class DicAction extends BaseAction {

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.info("Entering 'list' method");
		}

		DictionaryService ds = getDictionaryService();
		request.setAttribute("dicList", ds.getDictionarys());
		return mapping.findForward("list");
	}

	public ActionForward listSubDic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.info("Entering 'listSubDic' method");
		}
		String dicType = request.getParameter("dictype");
		String editable = request.getParameter("editable");
		
			int dic = Integer.parseInt(dicType);
			DictionaryService ds = getDictionaryService();
			request.setAttribute("parentid", dicType);
			List subDic=ds.getDics(dic);
			request.setAttribute("subdicList", subDic);
			request.setAttribute("editable", editable);
			return mapping.findForward("sublist");
	}

	public ActionForward enterEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.info("Entering 'enterEdit' method");
		}
		String pkid = request.getParameter("pkid");
		Dictionary d;
		if (pkid != null && !"".equals(pkid)) {
			DictionaryService ds = getDictionaryService();
			d = ds.getDic(Long.valueOf(pkid));
		} else {
			d = new Dictionary();
			d.setDicname("");
		}
		String parentid = request.getParameter("parentid");
		if(d.getDicname().equals("开放验证"))
		{
			d.setDicname("openip");
		}
		else if(d.getDicname().equals("关闭验证"))
		{
			d.setDicname("closeip");
		}
		request.setAttribute("parentid", parentid);
		request.setAttribute("theDic", d);
		return mapping.findForward("edit");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'save' method");
		}
		String pkid = request.getParameter("pkid");
		String name = request.getParameter("name");
		String parentid = request.getParameter("parentid");
		Dictionary d;
		DictionaryService ds = getDictionaryService();
		if ("".equals(pkid)) {
			d = new Dictionary();
			d.setPkid(null);
			int dicid = ds.nextDicid(Integer.parseInt(parentid));
			d.setDicid(new Long(dicid));
			d.setLayer(new Long(2));
			d.setParentid(Long.valueOf(parentid));
			d.setStatus("1");
			d.setIsleaf("1");
			d.setDisporder(new Long(dicid));
		} else {
			d = ds.getDic(Long.valueOf(pkid));
		}
		if(name.equals("openip"))
		{
			d.setDicname("开放验证");
		}
		else if(name.equals("closeip"))
		{
			d.setDicname("关闭验证");
		}
		else
		{
			d.setDicname(name);
		}
		ds.saveDic(d);
		response.sendRedirect(request.getContextPath()
				+ "/dicAction.do?method=listSubDic&dictype=" + parentid
				+ "&editable=1");
		return null;
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'delete' method");
		}
		String pkid = request.getParameter("pkid");
		DictionaryService ds = getDictionaryService();
		ds.removeDic(Long.valueOf(pkid));
		String parentid = request.getParameter("parentid");
		response.sendRedirect(request.getContextPath()
				+ "/dicAction.do?method=listSubDic&dictype=" + parentid
				+ "&editable=1");
		return null;
	}

}
