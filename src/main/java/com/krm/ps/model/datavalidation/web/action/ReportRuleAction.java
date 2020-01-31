package com.krm.ps.model.datavalidation.web.action;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.model.datavalidation.services.ReportRuleService;
import com.krm.ps.model.reportdefine.services.ReportConfigService;
import com.krm.ps.model.sysLog.util.LogUtil;
import com.krm.ps.model.vo.ReportComType;
import com.krm.ps.model.vo.ReportRule;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.util.DateUtil;
import com.krm.ps.util.FuncConfig;

public class ReportRuleAction extends BaseAction {

	/**
	 * 数据校验--列表，查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward enterComLogicRule(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try{
			if (log.isDebugEnabled()) {
				log.debug("Entering 'enterComLogicRule' method");
			}
			HttpSession session = request.getSession();
			String loginDate = (String) session.getAttribute("logindate");
			String levelFlag = request.getParameter("levelFlag");
			String repruletype = request.getParameter("repruletype"); // 规则类型
			repruletype = (repruletype == null) ? "0" : repruletype;
			String reportn = request.getParameter("reportn"); // 模型
			reportn = (reportn == null) ? "0" : reportn;
			String systemcode = FuncConfig.getProperty("system.sysflag", "1");
			User user = getUser(request);
			Long userId = user.getPkid();
			ReportRuleService rts = (ReportRuleService) getBean("reportRuleService");
			// 获得目标层数据模型的类型
			List reportType = rts.getReportType(Integer.parseInt(systemcode),
					Integer.parseInt(levelFlag));
			List reportType1 = reportType;
			// 获得类型下对应的所有报表
			List reportList = rts.getDateOrganEditReportForStandard(
					loginDate.replaceAll("-", ""), userId, systemcode, levelFlag);
			// List reportList=rts.getReport(reportType);
			List reportList1 = reportList;
			// 获得规则类型
			List ruleType = rts.getBasicRuleType("0", systemcode);
			List ruleType1 = ruleType;
			if (!"0".equals(repruletype)) {
				ruleType = new ArrayList();
				ReportComType rc = new ReportComType();
				rc.setPkid(Long.parseLong(repruletype));
				ruleType.add(rc);
			}

			if (!"0".equals(reportn)) {
				reportList = new ArrayList();
				Report r = new Report();
				r.setPkid(Long.parseLong(reportn));
				reportList.add(r);
			}
			// 获得规则列表
			List reporRule = rts.getReportRule(reportList, ruleType, "0");
			for (int j = 0; j < reporRule.size(); j++) {
				ReportRule reru = (ReportRule) reporRule.get(j);
				// 把对应的模型名称设置进去
				for (int m = 0; m < reportList1.size(); m++) {
					Report rp = (Report) reportList1.get(m);
					if (reru.getModelid().equals(rp.getPkid().toString())) {
						reru.setReportname(rp.getName());
					}
				}
				// 把对应的规则设置进去
				for (int m = 0; m < ruleType1.size(); m++) {
					ReportComType rp = (ReportComType) ruleType1.get(m);
					if (reru.getRtype().equals(rp.getPkid().toString())) {
						reru.setTypename(rp.getTypename());
					}
				}
			}
			if (levelFlag != null && !"".equals(levelFlag)) {
				request.setAttribute("params", "&levelFlag=" + levelFlag);
			}
			request.setAttribute("reportType", reportType1);
			request.setAttribute("reportList", reportList1);
			request.setAttribute("ruletypeid", repruletype);
			request.setAttribute("dataDate", loginDate);
			request.setAttribute("reportid", reportn);
			request.setAttribute("ruleType", ruleType1);
			request.setAttribute("levelFlag", levelFlag);
			request.setAttribute("reporRule", reporRule);
			return mapping.findForward("comlogicrule");
		}catch(Exception e){
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw, true));
			String str = sw.toString();
			request.setAttribute("errors", str);
			request.setAttribute("errorMessages", e);
			return mapping.findForward("error");
		}
	}

	/**
	 * 数据校验--单个数据校验
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	public ActionForward checkData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'checkData' method");
		}
		HttpSession session = request.getSession();
		ReportRuleService rts = (ReportRuleService) getBean("reportRuleService");
		ReportConfigService rcs = (ReportConfigService) getBean("rdreportConfigService");
		String dataDate = request.getParameter("dataDate");
		String loginDate = (String) session.getAttribute("logindate");
		dataDate = (dataDate == null) ? loginDate : dataDate;
		dataDate = dataDate.replaceAll("-", "");
		String rulecode = request.getParameter("rulecode");
		String targettable = request.getParameter("targettable");
		String cflag = request.getParameter("cflag");
		String yy = request.getParameter("mm");
		User user = (User) request.getSession().getAttribute("user");
		int isAdmin=user.getIsAdmin();
		int idx=user.getOrganTreeIdxid();
		String organId=user.getOrganId();
		final String userName = user.getName();
		// 插入系统日志־
		getSysLogService().insertLog(user.getPkid() + "", userName, "-1",
				LogUtil.LogType_User_Operate, "", "业务数据校验->数据校验", "-1");
        String organidRule =  organId ;
		if(isAdmin == 2){
			organidRule = "admin" ;
		}
		int ruleCount = rts.deleteRuleCheckProgress(organidRule); //删除进度表
		if(ruleCount>=0){
			log.info("cleared rule result ok !");
		}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		////创建一个校验存储过程进度对象
		final String threadKey = "ruleThreadResult" + UUID.randomUUID().toString().replaceAll("-", "");
		final Map<String, String> result = new HashMap<String, String>();
		request.getSession().setAttribute(threadKey, result);
		request.getSession().setAttribute("ruleThreadResult", threadKey);
		result.put("compress", "0");
	    result.put("isfinished", "false");
	    
	    //设置总数
	    if ("1".equals(cflag)) { //单条
	    	result.put("total", "1");
	    }else {  //批量
	    	String[] rulecodes = null;
	    	if (!(null == yy)) {
				rulecodes = yy.split(",");
			}
	    	result.put("total", rulecodes.length+"");
	    }
		try {
			
			String organTree = rts.selectOrganTreeSql(organId, isAdmin, idx);
			docheckData(organId ,isAdmin, rulecode, cflag, targettable, result, organTree, yy, dataDate);
			
		} catch (Exception e) {
			result.put("isfinished", "failed");
			
		}finally{
			   
			    out.print(threadKey);
				out.flush();
				out.close();
				return null;
		}

	}
	
	public void  docheckData(final String userOrganid ,final int isAdmin ,final String rulecode ,final String cflag,final String targettable,final Map<String, String> result,final String organTree ,final String yy ,final String dataDate){
	
		final ReportRuleService rts = (ReportRuleService) getBean("reportRuleService");
		final int idx = -1;
	
		Thread t = new Thread(){
			public void run() {
						// 单个校验
						if ("1".equals(cflag)) {
							// 目前没有做日期分表 Thread()程序不允许所有先注释掉
							/*targettable = targettable.replaceAll("\\{Y\\}",
									dataDate.substring(0, 4));
							targettable = targettable.replaceAll("\\{M\\}",
									dataDate.substring(4, 6));
							targettable = targettable.replaceAll("\\{D\\}",
									dataDate.substring(6, 8));*/
							rts.deleteReportResult("'" + rulecode + "'", organTree, targettable,idx,isAdmin);// 重新生成校验数据前，先清洗待补录的数据
							rts.checkData(rulecode, dataDate,organTree,isAdmin,userOrganid);
				           
						} else {
							// 批量校验
							String[] rulecodes = null;
							if (!(null == yy)) {
								rulecodes = yy.split(",");
							}
							String ruls = "";
							for (int i = 0; i < rulecodes.length; i++) {
								String[] rcd = rulecodes[i].split("%");
								//ruls += "'" + rcd[0] + "',";
								ruls = "'" + rcd[0] + "',";
								/*targettable = rcd[1].replaceAll("\\{Y\\}",
										dataDate.substring(0, 4));
								targettable = targettable.replaceAll("\\{M\\}",
										dataDate.substring(4, 6));
								targettable = targettable.replaceAll("\\{D\\}",
										dataDate.substring(6, 8));*/
								rts.deleteReportResult(ruls.substring(0, ruls.length() - 1), organTree, //写日志 异常处理
										rcd[1],idx,isAdmin);// 重新生成校验数据前，先清洗待补录的数据
							}
							
							for (int i = 0; i < rulecodes.length; i++) {
								String[] rcd = rulecodes[i].split("%");
								rts.checkData(rcd[0], dataDate,organTree,isAdmin,userOrganid);
							}
						}
			   }
			};
			t.start();
	}
	
	public ActionForward createFilePercent(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ReportRuleService rts = (ReportRuleService) getBean("reportRuleService");
		@SuppressWarnings("unchecked")
		String rulecheckPercen = request.getParameter("rulecheckPercen"); 
		Map<String, String> percent = (Map<String, String>) request.getSession().getAttribute(rulecheckPercen);
		User user = (User) request.getSession().getAttribute("user");
		String organId=user.getOrganId();
		int isAdmin=user.getIsAdmin();
		if(isAdmin == 2){
			organId = "admin"; //总行默认机构号为 'admin'
		}
		int total = Integer.parseInt(percent.get("total"));
		int compress = rts.getRuleCheckProgress(organId);
		percent.put("compress", compress+"");
		if(total == compress){
			percent.put("isfinished", "true");
		}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(JSONObject.fromObject(percent).toString());
		out.flush();
		out.close();
		return null;
	}
	
	public ActionForward listRulecheckPercen(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)  {
		HttpSession session = request.getSession();
		ReportRuleService rts = (ReportRuleService) getBean("reportRuleService");
		
		if (log.isDebugEnabled()) {
			log.debug("Entering 'listRulecheckPercen' method");
		}
		String loginDate = (String) session.getAttribute("logindate");
		String levelFlag = request.getParameter("levelFlag");
		String repruletype = request.getParameter("repruletype"); // 规则类型
		repruletype = (repruletype == null) ? "0" : repruletype;
		String reportn = request.getParameter("reportn"); // 模型
		reportn = (reportn == null) ? "0" : reportn;
		String systemcode = FuncConfig.getProperty("system.sysflag", "1");
		User user = getUser(request);
		Long userId = user.getPkid();
		// 获得目标层数据模型的类型
		List reportType = rts.getReportType(Integer.parseInt(systemcode), Integer.parseInt(levelFlag));
		List reportType1 = reportType;
		// 获得类型下对应的所有报表
		List reportList = rts.getDateOrganEditReportForStandard(loginDate.replaceAll("-", ""), userId, systemcode, levelFlag);
		// List reportList=rts.getReport(reportType);
		List reportList1 = reportList;
		// 获得规则类型
		List ruleType = rts.getBasicRuleType("0", systemcode);
		List ruleType1 = ruleType;
		if (!"0".equals(repruletype)) {
			ruleType = new ArrayList();
			ReportComType rc = new ReportComType();
			rc.setPkid(Long.parseLong(repruletype));
			ruleType.add(rc);
		}

		if (!"0".equals(reportn)) {
			reportList = new ArrayList();
			Report r = new Report();
			r.setPkid(Long.parseLong(reportn));
			reportList.add(r);
		}
		// 获得规则列表
		 
		request.setAttribute("reportType", reportType1);
		request.setAttribute("reportList", reportList1);
		request.setAttribute("ruletypeid", repruletype);
		request.setAttribute("dataDate", loginDate);
		request.setAttribute("reportid", reportn);
		request.setAttribute("ruleType", ruleType1);
		request.setAttribute("levelFlag", levelFlag);
		
		int isAdmin=user.getIsAdmin();
		String organId=user.getOrganId();
		if(isAdmin == 2){
			organId = "admin"; //总行默认机构号为 'admin'
		}
		if("0".equals(reportn)){
			reportn = null ; //取所有
		}
		List rulecheckpercenList =rts.getRuleCheckProgressList(organId, reportn);
		
		request.setAttribute("rulecheckpercenList", rulecheckpercenList);
		return mapping.findForward("rulecheckpercenList");
	}
	
	public ActionForward listResultFull(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)  {
		HttpSession session = request.getSession();
		ReportRuleService rts = (ReportRuleService) getBean("reportRuleService");
		String dataDate = request.getParameter("dataDate");
		String tabName  = request.getParameter("tabName");
		String loginDate = (String) session.getAttribute("logindate");
		dataDate = (dataDate == null) ? loginDate : dataDate;
		List resultfullList =rts.getResultfull(DateUtil.formatDate(dataDate).substring(0, 6),tabName);
		
		request.setAttribute("dataDate", dataDate);
		request.setAttribute("tabName", tabName);
		request.setAttribute("resultfullList", resultfullList);
		return mapping.findForward("resultfullList");
	}
	
	public ActionForward listResultShellCheckLog(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response)  {
			HttpSession session = request.getSession();
			String loginDate = (String) session.getAttribute("logindate");
			String levelFlag = request.getParameter("levelFlag");
			String systemcode = FuncConfig.getProperty("system.sysflag", "1");
			User user = getUser(request);
			Long userId = user.getPkid();
			ReportRuleService rts = (ReportRuleService) getBean("reportRuleService");
			 
			// 获得类型下对应的所有报表
			List reportList = rts.getDateOrganEditReportForStandard(loginDate.replaceAll("-", ""), userId, systemcode, levelFlag);
			
			request.setAttribute("dataDate", loginDate);
			request.setAttribute("reportList", reportList);
		
//		request.setAttribute("resultfullList", resultfullList);
		return mapping.findForward("resultLogList");
	}
}