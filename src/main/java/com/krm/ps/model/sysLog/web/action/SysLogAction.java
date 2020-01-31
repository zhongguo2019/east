package com.krm.ps.model.sysLog.web.action;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.model.sysLog.services.SysLogService;
import com.krm.ps.model.sysLog.util.LogUtil;
import com.krm.ps.model.sysLog.web.form.SysLogForm;
import com.krm.ps.sysmanage.usermanage.vo.User;


public class SysLogAction extends BaseAction{
	 public ActionForward list(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) 
	 {
		 if (log.isDebugEnabled()) {
				log.info("Entering 'list' method");
			}
		 // 登陆账号
		 try{
			 String userNum = request.getParameter("userNum");
			 HttpSession session = request.getSession();
			 User user = getUser(request);
			 if(userNum == null){
				 userNum = user.getLogonName();
			 }
			 // 开始时间
			 String startDate=request.getParameter("startDate");
			 if (startDate == null) {
				 startDate = (String) session.getAttribute("logindate");
			 }
			 // 结束时间
			 String endDate=request.getParameter("endDate");
			 if (endDate == null) {
				 endDate = (String) session.getAttribute("logindate");
			 }
			 // 获得机构
			 String organId=request.getParameter("organId");
			 if(null==organId || "".equals(organId)){
				 organId = getUser(request).getOrganId();
			 }
			 int idxid = getUser(request).getOrganTreeIdxid();
			 SysLogService sysLogService= (SysLogService) getBean("syslogService");
			 //根据条件查询所有的日志信息
			 List allist=sysLogService.queryLogList(userNum,null,startDate,endDate,null,LogUtil.LogType_User_Operate, organId,idxid);
			 request.setAttribute("list", allist);
			 request.setAttribute("userNum", userNum);
			 request.setAttribute("startDate", startDate);
			 request.setAttribute("endDate", endDate);
			 request.setAttribute("organId", organId);
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
}
