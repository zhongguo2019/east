package com.krm.ps.model.funconfig.web.action;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.model.funconfig.services.FunconfigService;
import com.krm.ps.model.workinstructions.service.WorkinstructionsService;

public class FunconfigAction  extends BaseAction{

	FunconfigService funconfigService=(FunconfigService)getBean("funconfigService");
	//展示
	public ActionForward selectfunconfiglist(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list=funconfigService.selectfunconfig();
		
		request.setAttribute("list", list);
		
		return mapping.findForward("selectfunconfiglist");
	}
	//删除
	public ActionForward deletefunconfig(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		funconfigService.deletefunconfig(request.getParameter("funkey"));
		
		String info="删除成功";
		request.setAttribute("info", info);
		return mapping.findForward("add");
	}
	public ActionForward selectkey(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		//System.out.println("==="+request.getParameter("pkid"));
		List list=funconfigService.selectfunconfig1(request.getParameter("pkid"));
		
		JSONObject json = new JSONObject();
		  json.put("size11", list.size());
		  String dateJson = JSONArray.fromObject(json).toString();
		  response.setContentType("text/html;charset=UTF-8");
		  response.setHeader("Cache-Control", "no-cache");
		  PrintWriter pw = response.getWriter();
		  pw.write(dateJson);
		  pw.flush();
		  pw.close();

		
		return null;
	}
	
	public ActionForward openwindow(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List list=funconfigService.selectkey();

		request.setAttribute("list", list);
		request.setAttribute("flag", 1);
		return mapping.findForward("openwindow");
	}
	
	public ActionForward addfunconfig(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//System.out.println("====="+Integer.parseInt(request.getParameter("flag")));
		if(Integer.parseInt(request.getParameter("flag"))==1){
			funconfigService.savefunconfig(request.getParameter("funkey"), request.getParameter("funvalue"), request.getParameter("fundes"), Integer.parseInt(request.getParameter("funtype")), request.getParameter("funext1"));
			
			//request.setAttribute("flag", request.getParameter("flag"));
			String info="新增成功";
			request.setAttribute("flag", 1);
			request.setAttribute("info", info);
			
		}
		if(Integer.parseInt(request.getParameter("flag"))==2){
			funconfigService.updatefunconfig(request.getParameter("funkey"), request.getParameter("funvalue"), request.getParameter("fundes"), Integer.parseInt(request.getParameter("funtype")), request.getParameter("funext1"));
			String info="修改成功";
			request.setAttribute("info", info);
			
		}
		return mapping.findForward("add");
	}
	public ActionForward editfunconfig(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		request.setAttribute("flag", 2);
		List list=funconfigService.selectfunconfig1(request.getParameter("funkey"));
		Iterator it=list.iterator();
		while(it.hasNext()){
			Object[]o=(Object[])it.next();
			request.setAttribute("funkey", request.getParameter("funkey"));
			request.setAttribute("funvalue", (String)o[1]);
			request.setAttribute("fundes", (String)o[2]);
			request.setAttribute("funtype", o[3].toString());
			request.setAttribute("funext1",(String)o[4]);
		}
		List list1=funconfigService.selectfunconfig();
		
		request.setAttribute("list", list1);
		return mapping.findForward("editfunconfig");
	}
	
	
}
