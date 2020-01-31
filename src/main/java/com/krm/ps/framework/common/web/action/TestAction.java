package com.krm.ps.framework.common.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.krm.ps.util.StringUtil;

/**
 * 
 * @struts.action path="/testenc" scope="request"
 * 
 * @struts.action-forward name="testenc" path="/testEncoding.jsp"
 * 
 */
public class TestAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String asyn = request.getParameter("asyn");
		if ("true".equals(asyn)) {
			return proAjax(mapping, form, request, response);
		}

		String oreq = request.getQueryString();
		request.setAttribute("oreq", oreq);
		String ozh = request.getParameter("zh");
		request.setAttribute("ozh", ozh);

		String encode1 = request.getParameter("encode1");
		if (encode1 != null && !"".equals(encode1) && !"null".equals(encode1)) {
			request.setCharacterEncoding(encode1);
		}
		String req = request.getQueryString();
		if (req == null) {
			return mapping.findForward("testenc");
		}
		req = StringUtil.escapeUrlString(req);
		String encode2 = request.getParameter("encode2");
		if (encode2 != null && !"".equals(encode2) && !"null".equals(encode2)) {
			req = new String(req.getBytes(encode2));
		}
		String zh = req.substring(req.indexOf("&zh=") + 4);
		request.setAttribute("zh", zh);

		return mapping.findForward("testenc");
	}

	public ActionForward proAjax(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String ozh = request.getParameter("zh");

		//全部带中文参数时url的最大长度大概是1100
		//全部带英文参数时url的最大长度大概是2100
		String encode1 = request.getParameter("encode1");
		if (encode1 != null && !"".equals(encode1) && !"null".equals(encode1)) {
			request.setCharacterEncoding(encode1);
		}
		String req = request.getQueryString();
		req=req.replaceAll(" ","&nbsp;");
		req = StringUtil.escapeUrlString(req);
		String encode2 = request.getParameter("encode2");
		if (encode2 != null && !"".equals(encode2) && !"null".equals(encode2)) {
			req = new String(req.getBytes(encode2));
		}
		String zh = req.substring(req.indexOf("&zh=") + 4);

		String res=ozh+"$$$"+zh;
		
		response.setContentType("text/html; charset=GBK");
		response.getWriter().write(res);
		
		return null;
	}

}
