package com.krm.ps.model.reportrule.web.action;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.model.reportrule.services.FromValidatorService;
import com.krm.ps.model.reportrule.services.ReportConfigService;
import com.krm.ps.model.reportrule.services.ReportDefineService;
import com.krm.ps.model.reportrule.services.ReportRuleService;
import com.krm.ps.model.sysLog.services.SysLogService;
import com.krm.ps.model.sysLog.util.LogUtil;
import com.krm.ps.model.vo.FromValidator;
import com.krm.ps.model.vo.ReportComType;
import com.krm.ps.model.vo.ReportRule;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportConfig;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;
import com.krm.ps.sysmanage.usermanage.services.DictionaryService;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.util.FuncConfig;

public class ReportRuleAction extends BaseAction {
	ReportDefineService rds = (ReportDefineService) getBean("reportruleReportDefineService");
	ReportRuleService rts = (ReportRuleService) getBean("reportruleReportruleService");
	ReportConfigService rcs = (ReportConfigService) getBean("reportruleReportConfigService");
	FromValidatorService fvs = (FromValidatorService) getBean("reportruleFromValidatorService");
	SysLogService sls = (SysLogService) getBean("syslogService");

	/**
	 * 基础规则配置 新增按钮
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward enterBasicRule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'enterBasicRule' method");
		}
		HttpSession session = request.getSession();
		String loginDate = (String) session.getAttribute("logindate");
		String levelFlag = request.getParameter("levelFlag");
		User user = getUser(request);
		Long userId = user.getPkid();
		String systemcode = FuncConfig.getProperty("system.sysflag", "1");

		// List reportType=rts.getReportType(Integer.parseInt(systemcode),
		// Integer.parseInt(levelFlag));

		List reportList = rts.getDateOrganEditReportForStandard(
				loginDate.replaceAll("-", ""), userId, systemcode, levelFlag);
		Report report = (Report) reportList.get(0);
		String reportId = String.valueOf(report.getPkid());
		String reportName = report.getName();
		String targetName = "";
		String targetId = "";
		List targets = new ArrayList();
		targets = rds.getReportTargets(new Long(reportId));
		if (targets != null && targets.size() > 0) {
			ReportTarget repTarget = (ReportTarget) targets.get(0);
			targetId = repTarget.getTargetField();
			targetName = repTarget.getTargetName();
		}

		request.setAttribute("targetId", targetId);
		request.setAttribute("targetName", targetName);
		request.setAttribute("reportId", reportId);
		request.setAttribute("reportName", reportName);
		request.setAttribute("levelFlag", levelFlag);
		request.setAttribute("dataDate", loginDate);
		if (levelFlag != null && !"".equals(levelFlag)) {
			request.setAttribute("params", "&levelFlag=" + levelFlag);
		}
	 
		return mapping.findForward("enterbasicrule");
	}

	/**
	 * 进入基本类校验规则页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward enterPriRule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'enterPriRule' method");
		}
		HttpSession session = request.getSession();
		String loginDate = (String) session.getAttribute("logindate");
		String levelFlag = request.getParameter("levelFlag");
		User user = getUser(request);
		Long userId = user.getPkid();
		String systemcode = FuncConfig.getProperty("system.sysflag", "1");

		// List reportType=rts.getReportType(Integer.parseInt(systemcode),
		// Integer.parseInt(levelFlag));

		// List reportList=rts.getReport(reportType);
		List reportList = rts.getDateOrganEditReportForStandard(
				loginDate.replaceAll("-", ""), userId, systemcode, levelFlag);
		Report report = (Report) reportList.get(0);
		String reportId = String.valueOf(report.getPkid());
		String reportName = report.getName();
		String targetName = "";
		String targetId = "";
		List targets = new ArrayList();
		targets = rds.getReportTargets(new Long(reportId));
		if (targets != null && targets.size() > 0) {
			ReportTarget repTarget = (ReportTarget) targets.get(0);
			targetId = repTarget.getTargetField();
			targetName = repTarget.getTargetName();
		}

		request.setAttribute("targetId", targetId);
		request.setAttribute("targetName", targetName);
		request.setAttribute("reportId", reportId);
		request.setAttribute("reportName", reportName);
		request.setAttribute("levelFlag", levelFlag);
		request.setAttribute("dataDate", loginDate);
		if (levelFlag != null && !"".equals(levelFlag)) {
			request.setAttribute("params", "&levelFlag=" + levelFlag);
		}
		return mapping.findForward("enterprirule");
	}

	/**
	 * 配制规则
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editBasicRule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'editBasicRule' method");
		}
		String flags = request.getParameter("flags");
		String targetNames =  null; // request.getParameter("targetNames");
		String reportName =  null;  //request.getParameter("reportName");
		String reportId = request.getParameter("reportId");
		String targetIds = request.getParameter("targetIds");
		String levelFlag = request.getParameter("levelFlag");
		String systemcode = FuncConfig.getProperty("system.sysflag", "1");
		List targets = new ArrayList();
		ReportRule rr = new ReportRule();
		targets = rds.getReportTargets(new Long(reportId));
		String[] targetId = targetIds.split(",");
		List targetList = new ArrayList();
		for (int i = 0; i < targets.size(); i++) {
			ReportTarget rts =  (ReportTarget)targets.get(i);
			for (int j = 0; j < targetId.length; j++) {
				if(targetId[j].equals(rts.getTargetField())){
					ReportTarget rt = new ReportTarget();
					rt.setTargetField(targetId[j]);
					rt.setTargetName(rts.getTargetName());
					rt.setReportId(Long.parseLong(reportId));
					targetList.add(rt);
					if(j == 0){
						targetNames = rts.getTargetName();
					}
				}
			}
		}
		
		
		
		List repconfiglist = rcs.getReportConfigs(new Long(reportId), new Long(
				33));
		String defChar = "";
		if (repconfiglist.size() > 0) {
			ReportConfig rc = (ReportConfig) repconfiglist.get(0);
			defChar = rc.getDefChar();
			reportName  = rc.getDescription() ;
		}
		String resulttablename = rcs.defChar(new Long(reportId), new Long(34));

		List ruleType = rts.getBasicRuleType("0", systemcode);
		List list = rts.getReportRule(reportId, targetId[0],
				((ReportComType) ruleType.get(0)).getPkid().toString());
		if (list.size() > 0) {
			rr = (ReportRule) list.get(0);
		} else {
			// String sql="select r.ORGAN_ID,r.PKID,r."+targetId[1];
			// if(Integer.parseInt(targetId[1])>=21&&Integer.parseInt(targetId[1])<50){
			String sql = "select r.ORGAN_ID,r.PKID,to_char(r." + targetId[0]
					+ ") ,null as a,null as b,null as c ";
			// }
			rr.setRulecode(reportId + "_" + targetId[0] + "_"
					+ ((ReportComType) ruleType.get(0)).getPkid());
			rr.setTargettable(resulttablename);
			rr.setKeyid("PKID");
			// rr.setContent(sql+" from  REP_DATAF_${MONTH}_"+defChar+"  r where r.REPORT_ID="+reportId+" and  ");
			rr.setContent(sql + " from  " + defChar + "  r where ");
			rr.setTargetid(targetId[0]);
			rr.setTargetname(targetNames);
			rr.setModelid(reportId);
			rr.setRtype(((ReportComType) ruleType.get(0)).getPkid().toString());
		}
	 
		String urlflag = "reportrule.do?method=editTheReportRule&repid="
				+ reportId + "&rtarid=" + targetId[0] + "&targetN="
				+ targetNames + "&rtype=" + rr.getRtype()
				+ "&bflag=0&levelFlag=" + levelFlag + "&flags=" + flags;
		request.setAttribute("urlflag", urlflag);
		request.setAttribute("reportrule", rr);
		request.setAttribute("ruleType", ruleType);
		request.setAttribute("defChar", defChar);
		request.setAttribute("targetsList", targets);
	/*	request.setAttribute("reportlist", reportList);*/
		request.setAttribute("levelFlag", levelFlag);
		request.setAttribute("ll", targets);
		HttpSession session = request.getSession();
		session.setAttribute("lxhtargetList", targetList);
		request.setAttribute("enit", 2);
		request.setAttribute("reportName", reportName);
		request.setAttribute("atiaos", 1);
		request.setAttribute("flags", flags);
		return mapping.findForward("editbasicrule");
	}

	/**
	 * 隐私规则配置点新增按钮
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editPriRule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'editPriRule' method");
		}
		HttpSession session = request.getSession();
		String targetNames = null; //request.getParameter("targetNames");
		String reportName = null ;// request.getParameter("reportName");
		String reportId = request.getParameter("reportId");
		String targetIds = request.getParameter("targetIds");
		String levelFlag = request.getParameter("levelFlag");
		String systemcode = FuncConfig.getProperty("system.sysflag", "2");
		User user = getUser(request);
		Long userId = user.getPkid();

 
		List targets = new ArrayList();
		ReportRule rr = new ReportRule();
		targets = rds.getReportTargets(new Long(reportId));
		String[] targetId = targetIds.split(",");
		List targetList = new ArrayList();
		for (int i = 0; i < targets.size(); i++) {
			ReportTarget rts =  (ReportTarget)targets.get(i);
			for (int j = 0; j < targetId.length; j++) {
				if(targetId[j].equals(rts.getTargetField())){
					ReportTarget rt = new ReportTarget();
					rt.setTargetField(targetId[j]);
					rt.setTargetName(rts.getTargetName());
					rt.setReportId(Long.parseLong(reportId));
					targetList.add(rt);
					if(j == 0){
						targetNames = rts.getTargetName();
					}
				}
			}
		}

		
		
		
		List ruleType = rts.getBasicRuleType("3", systemcode);

		List repconfiglist = rcs.getReportConfigs(new Long(reportId), new Long(
				33));
		String defChar = "";
		if (repconfiglist.size() > 0) {
			ReportConfig rc = (ReportConfig) repconfiglist.get(0);
			defChar = rc.getDefChar();
			reportName= rc.getDescription();
		}
		List list = rts.getReportRule(reportId, targetId[0],
				((ReportComType) ruleType.get(0)).getPkid().toString());
		if (list.size() > 0) {
			rr = (ReportRule) list.get(0);
		} else {
			rr.setRulecode(reportId + "_" + targetId[0] + "_"
					+ ((ReportComType) ruleType.get(0)).getPkid());
			rr.setKeyid("PKID");
			rr.setTargetid(targetId[0]);
			rr.setTargetname(targetNames);
			rr.setModelid(reportId);
			rr.setRtype(((ReportComType) ruleType.get(0)).getPkid().toString());
		}

		String urlflag = "reportrule.do?method=editThePriRule&repid="
			+ reportId + "&rtarid=" + targetId[0] + "&targetN="
			+ targetNames + "&rtype=" + rr.getRtype()
			+ "&bflag=0&levelFlag=" + levelFlag + "&flags=2";
		
		request.setAttribute("urlflag", urlflag);
		request.setAttribute("reportrule", rr);
		request.setAttribute("defChar", defChar);
		request.setAttribute("ruleType", ruleType);
		request.setAttribute("targetsList", targets);
 //		request.setAttribute("reportlist", reportList);
		request.setAttribute("ll", targets);
		session.setAttribute("lxhtargetList", targetList);
		request.setAttribute("levelFlag", levelFlag);
		request.setAttribute("reportName", reportName);
		request.setAttribute("atiaos", 0);
		request.setAttribute("flags", request.getParameter("flags"));
		return mapping.findForward("editprirule");
	}

	/**
	 * 隐私规则查看详细配置
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editThePriRule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'editThePriRule' method");
		}
		HttpSession session = request.getSession();
		String repid = request.getParameter("repid");
		String targetid = request.getParameter("rtarid");
		String rtype = request.getParameter("rtype");
		String targetN = null; //request.getParameter("targetN");
		String isdetail = request.getParameter("isdetail");
		String cflag = request.getParameter("cflag");
		String systemcode = FuncConfig.getProperty("system.sysflag", "2");
		String flags = request.getParameter("flags");
		String reportName = null; //request.getParameter("reportName");
 
		ReportRule rr = new ReportRule();

		List repconfiglist = rcs
				.getReportConfigs(new Long(repid), new Long(33));
		String defChar = "";
		if (repconfiglist.size() > 0) {
			ReportConfig rc = (ReportConfig) repconfiglist.get(0);
			defChar = rc.getDefChar();
			reportName = rc.getDescription();
		}
	 
	
		ReportRule rru = new ReportRule();
		if ("1".equals(cflag)) {
			String rulecode = request.getParameter("rulecode");
			List list = rts.getReportRuleBycode("'" + rulecode + "'");
			if (list.size() > 0) {
				rru = (ReportRule) list.get(0);
				targetN = rru.getTargetname();
			}
			List targetList = new ArrayList();
			ReportTarget rt = new ReportTarget();
			rt.setTargetField(targetid);
			rt.setTargetName(targetN);
			rt.setReportId(Long.parseLong(repid));
			targetList.add(rt);
			session.setAttribute("lxhtargetList", targetList);
			
		} else {
			List list = rts.getReportRule(repid, targetid, rtype);
			if (list.size() > 0) {
				rru = (ReportRule) list.get(0);
			} else {
				List targets = new ArrayList();
				targets = rds.getReportTargets(new Long(repid));
				for (int i = 0; i < targets.size(); i++) {
					ReportTarget rts =  (ReportTarget)targets.get(i);
					if(targetid.equals(rts.getTargetField())){
						targetN = rts.getTargetName();
						break;
				    }
				}
				rru.setRulecode(repid + "_" + targetid + "_" + rtype);
				rru.setKeyid("PKID");
				rru.setTargetid(targetid);
				rru.setTargetname(targetN);
				rru.setModelid(repid);
				rru.setRtype(rtype);
			}
		}
		request.setAttribute("reportrule", rru);
		request.setAttribute("defChar", defChar);
		if ("0".equals(isdetail)) {
			
			List ruleType = rts.getBasicRuleType("3", systemcode);
			String typename = null ; 
			for (int m = 0; m < ruleType.size(); m++) {
				ReportComType rp = (ReportComType) ruleType.get(m);
				typename = rp.getTypename();
			}
			request.setAttribute("rename", reportName);
			request.setAttribute("typename", typename);
			return mapping.findForward("listpriruledetail");
		}
		String levelFlag = request.getParameter("levelFlag");
		String urlflag = "reportrule.do?method=editThePriRule&repid="
			+ repid + "&rtarid=" + targetid + "&targetN="
			+ targetN + "&rtype=" + rtype
			+ "&bflag=0&levelFlag=" + levelFlag + "&flags="+flags;
		request.setAttribute("urlflag", urlflag);
		request.setAttribute("levelFlag", levelFlag);
		request.setAttribute("cflag", cflag);
		request.setAttribute("flags", flags);
		request.setAttribute("reportName", reportName);
		request.setAttribute("atiaos", 1);
		return mapping.findForward("editprirule");
	}

	/**
	 * 基础规则配置 里查看详细 配置方法按钮
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editTheReportRule(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'editTheReportRule' method");
		}
		HttpSession session = request.getSession();
		String repid = request.getParameter("repid");
		String targetid = request.getParameter("rtarid");
		String rtype = request.getParameter("rtype");
		String targetN =  null ; //request.getParameter("targetN");
		String isdetail = request.getParameter("isdetail");
		String cflag = request.getParameter("cflag");
		String bflag = request.getParameter("bflag");
		String flags = request.getParameter("flags");
		String systemcode = FuncConfig.getProperty("system.sysflag", "2");
		String reportName = null ;//request.getParameter("reportName");

		List targets = new ArrayList();
		targets = rds.getReportTargets(new Long(repid));

		List repconfiglist = rcs
				.getReportConfigs(new Long(repid), new Long(33));
		String defChar = "";
		if (repconfiglist.size() > 0) {
			ReportConfig rc = (ReportConfig) repconfiglist.get(0);
			defChar = rc.getDefChar();
			reportName = rc.getDescription();
		}

		String resulttablename = rcs.defChar(new Long(repid), new Long(34));
		 
		List ruleType = rts.getBasicRuleType(bflag, systemcode);
		ReportRule rru = new ReportRule();
		if ("1".equals(cflag)) {
			String rulecode = request.getParameter("rulecode");
			List list = rts.getReportRuleBycode("'" + rulecode + "'");
			if (list.size() > 0) {
				rru = (ReportRule) list.get(0);
				targetN = rru.getTargetname();
			}
		
			List targetList = new ArrayList();
			ReportTarget rt = new ReportTarget();
			rt.setTargetField(targetid);
			rt.setTargetName(targetN);
			rt.setReportId(Long.parseLong(repid));
			targetList.add(rt);
			session.setAttribute("lxhtargetList", targetList);
		} else {
			List list = rts.getReportRule(repid, targetid, rtype);
			if (list.size() > 0) {
				rru = (ReportRule) list.get(0);
			} else {
				for (int i = 0; i < targets.size(); i++) {
					ReportTarget rts =  (ReportTarget)targets.get(i);
					if(targetid.equals(rts.getTargetField())){
						targetN = rts.getTargetName();
						break;
				    }
				}
				rru.setRulecode(repid + "_" + targetid + "_" + rtype);
				rru.setKeyid("PKID");
				// String sql="select r.ORGAN_ID,r.PKID,r.ITEMVALUE"+targetid;
				// if(Integer.parseInt(targetid)>=21&&Integer.parseInt(targetid)<50){
				String sql = "select r.ORGAN_ID,r.PKID,to_char(r." + targetid
						+ "),null as a,null as b,null as c ";
				// }
				rru.setTargettable(resulttablename);
				// rru.setContent(sql+" from REP_DATAF_${MONTH}_"+defChar+" r  where r.REPORT_ID="+repid+" and ");
				rru.setContent(sql + " from " + defChar + " r  where  ");
				rru.setTargetid(targetid);
				rru.setTargetname(targetN);
				rru.setModelid(repid);
				rru.setRtype(rtype);
			}
		}
		request.setAttribute("ll", targets);
		request.setAttribute("reportrule", rru);
		request.setAttribute("ruleType", ruleType);
		request.setAttribute("defChar", defChar);
		if ("0".equals(isdetail)) {
		 
			String typename = null ; 
			for (int m = 0; m < ruleType.size(); m++) {
				ReportComType rp = (ReportComType) ruleType.get(m);
				if (rtype.equals(rp.getPkid().toString())) {
					typename = rp.getTypename();
				}
			}
		
			request.setAttribute("rename", reportName);
			request.setAttribute("typename", typename);
			request.setAttribute("bflag", bflag);
			return mapping.findForward("listbasicruledetail");
		}
		String levelFlag = request.getParameter("levelFlag");

		List fromVlidatList = fvs.listFromValidator(null, repid, targetid);
		String urlflag = "reportrule.do?method=editTheReportRule&repid="
				+ repid + "&rtarid=" + targetid + "&targetN=" + targetN
				+ "&rtype=" + rtype + "&bflag=" + bflag + "&levelFlag="
				+ levelFlag + "&flags=" + flags;
		request.setAttribute("fromVlidatList", fromVlidatList);
		request.setAttribute("enit", 2);
		request.setAttribute("enou", 1);
		/*request.setAttribute("reportlist", reportList);*/
		request.setAttribute("targetsList", targets);
		request.setAttribute("levelFlag", levelFlag);
		request.setAttribute("cflag", cflag);
		request.setAttribute("urlflag", urlflag);
		request.setAttribute("flags", flags);
		request.setAttribute("reportName", reportName);
		return mapping.findForward("editbasicrule");
	}

	/**
	 * 配置基础规则时点保存按钮
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveReportRule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'saveReportRule' method");
		}
		HttpSession session = request.getSession();
		DictionaryService ds = this.getDictionaryService();
		String flags = request.getParameter("flags");
		String systemcode = FuncConfig.getProperty("system.sysflag", "1");
		String rtype = request.getParameter("rtype1");// 1
		String rulecode = request.getParameter("rulecode");
		String keyid = request.getParameter("keyid");
		String targettable = request.getParameter("targettable");
		String cstatus = request.getParameter("cstatus");
		String content = request.getParameter("content");
		String contentdes = request.getParameter("contentdes");
		String rulemsg = request.getParameter("rulemsg");
		String repid = request.getParameter("repid");// 1001
		String targetname = request.getParameter("targetN");//
		String targetid = request.getParameter("rtarid");// YXJGDM
		String levelFlag = request.getParameter("levelFlag");// 1
		String rulesname = request.getParameter("gzmc");
		String funname = request.getParameter("hsmc");
		String funparam = request.getParameter("hscs");
		String prompt = request.getParameter("tsxx");
		String pkidobj = request.getParameter("pkidobj");
		User user = (User) request.getSession().getAttribute("user");
		final String userName = user.getName();

		List targets = new ArrayList();
		targets = rds.getReportTargets(new Long(repid));
		request.setAttribute("ll", targets);
		String bflag = request.getParameter("bflag1"); // 判断是哪一层过来的（采集、目标、风险预警）
		String range = request.getParameter("range");
		ReportRule rr = new ReportRule();
		rr.setContent(content);
		rr.setContentdes(contentdes);
		rr.setCstatus(cstatus);
		rr.setKeyid(keyid);
		rr.setModelid(repid);
		rr.setRtype(rtype);
		rr.setRulecode(rulecode);
		rr.setRulemsg(rulemsg);
		rr.setTargetid(targetid);
		rr.setTargetname(targetname);
		rr.setRangelevel(range);
		rr.setTargettable(targettable);
		rr.setFlag(bflag);
		rr.setKey1(request.getParameter("key1"));
		rr.setKey2(request.getParameter("key2"));
		rr.setKey3(request.getParameter("key3"));
		String[] pchecks = request.getParameterValues("pcheck");
		String pcheck = "";
		if (pchecks != null) {
			for (int a = 0; a < pchecks.length; a++) {
				pcheck += pchecks[a] + ",";
			}
		}
		rr.setPcheck(pcheck);
		rr.setPflag(request.getParameter("pflag"));
		rr.setRcontent(request.getParameter("rcontent"));
		sls.insertLog(user.getPkid() + "", userName, "-1",
				LogUtil.LogType_User_Operate, "", "业务指标规则配置->指标逻辑规则配置", "-1");
		rr.setGroupid("1");
		rr.setSystemcode(systemcode);
		boolean flag = rts.saveReportRule(rr);
		List ruleType = rts.getBasicRuleType(bflag, systemcode);
		if (flag) {
			request.setAttribute("message", "1");
		} else {
			request.setAttribute("message", "2");
		}

		// 添加页面校验脚本
		Long pkidnew = fvs.getFvPkid(); // 得到最大pkid 值
		FromValidator fromValidator = new FromValidator();
		if (funname == null || "".equals(funname)) {
			fromValidator.setFunname("isNull"); // 函数名
		} else {
			fromValidator.setFunname(funname);// 函数名
		}
		if (funparam == null || "".equals(funparam)) {
			if ("notnull".equals(funname)) {
				fromValidator.setFunparam("/^$/"); // 函数参数
			} else {
				fromValidator.setFunparam("isNull"); // 函数参数
			}
		} else {
			fromValidator.setFunparam(funparam);
		}
		if (rulesname == null || "".equals(rulesname)) {
			if ("notnull".equals(funname)) {
				fromValidator.setRulesname(FuncConfig.getCNProperty("fkjy"));
			} else {
				fromValidator.setRulesname("isNull");
			}
		} else {
			fromValidator.setRulesname(rulesname);
		}
		if (prompt == null || "".equals(prompt)) {
			fromValidator.setPrompt(targetname
					+ FuncConfig.getCNProperty("bnwk")); // 页面提示信息
		} else {
			fromValidator.setPrompt(prompt);
		}

		String typestr = null;
		if (fromValidator != null) {
			// 判断是否存在相同函数名和函数参数的
			List<FromValidator> fromVlidatList = fvs.listFromValidator(null,
					repid, targetid);
			for (FromValidator oldfv : fromVlidatList) {

				if (fromValidator.getFunname().equals(oldfv.getFunname())
						&& fromValidator.getFunparam().equals(
								oldfv.getFunparam())) {
					typestr = "1";
					break;
				}

			}

			if ("reg".equals(fromValidator.getFunname())
					&& "isNull".equals(fromValidator.getFunparam())) {
				typestr = "1";
			}
			if ("isNull".equals(fromValidator.getFunname())) {
				typestr = "1";
			}
			// 添加
			if (typestr == null) {
				fromValidator.setPkid(pkidnew + 1);
				fromValidator.setReport_id(repid);
				fromValidator.setTarget_field(targetid);
				boolean fvsbool = fvs.saveFromValidator(fromValidator);
			}
		}
		// 多条修改
		if (pkidobj != null) {
			String[] vallist = pkidobj.split("@");
			for (int i = 0; i < vallist.length - 1; i++) {
				// fromValidator= new FromValidator();
				String[] newval = vallist[i].split(",");
				fromValidator.setPkid(new Long(newval[0]));
				fromValidator.setRulesname(newval[1]);
				fromValidator.setFunname(newval[2]);
				fromValidator.setFunparam(newval[3]);
				fromValidator.setPrompt(newval[4]);
				fromValidator.setReport_id(repid);
				fromValidator.setTarget_field(targetid);
				fvs.updateFromValidator(fromValidator, null);
			}
		}
		request.setAttribute("reportrule", rr);
		request.setAttribute("ruleType", ruleType);

		List reportType = rts.getReportType(Integer.parseInt(systemcode),
				Integer.parseInt(levelFlag));

		List reportList = rts.getReport(reportType);
		List targetsList = new ArrayList();

		if (reportList.size() > 0) {
			Report re = (Report) reportList.get(0);
			targetsList = rds.getReportTargets(new Long(re.getPkid()));
		}
		request.setAttribute("reportlist", reportList);
		request.setAttribute("targetsList", targetsList);
		request.setAttribute("levelFlag", levelFlag);
		if (!"isNull".equals(fromValidator.getFunname())) {
			request.setAttribute("funname", fromValidator.getFunname());
			request.setAttribute("funparam", fromValidator.getFunparam());
			request.setAttribute("rulesname", fromValidator.getRulesname());
			// S/ystem.out.println("rulesname---------------"+fromValidator.getRulesname());
			request.setAttribute("prompt", fromValidator.getPrompt());
			// S/ystem.out.println("fromValidator.getPrompt()-----------------"+fromValidator.getPrompt());
		}
		request.setAttribute("enit", 2);
		request.setAttribute("flags", flags);
		// request.getSession().setAttribute("flags", flags);
		return mapping.findForward("editbasicrule");// 1
	}

	/**
	 * 保存规则默认值，隐私数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveYjhReportRule(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'saveYjhReportRule' method");
		}
		HttpSession session = request.getSession();
		String systemcode = FuncConfig.getProperty("system.sysflag", "2");
		String rtype = request.getParameter("rtype1");
		String rulecode = request.getParameter("rulecode");
		String keyid = request.getParameter("keyid");
		String targettable = request.getParameter("targettable");
		String cstatus = request.getParameter("cstatus");
		String content = request.getParameter("content");
		String contentdes = request.getParameter("contentdes");
		String rulemsg = request.getParameter("rulemsg");
		String repid = request.getParameter("repid");
		String targetname = request.getParameter("targetN");
		String targetid = request.getParameter("rtarid");
		String levelFlag = request.getParameter("levelFlag");
		User user = (User) request.getSession().getAttribute("user");
		String flags=request.getParameter("flags");
		final String userName = user.getName();

		List targets = new ArrayList();
		targets = rds.getReportTargets(new Long(repid));
		request.setAttribute("ll", targets);
		String bflag = request.getParameter("bflag1"); // 判断是哪一层过来的（3隐私数据，4默认值）
		String range = request.getParameter("range");
		ReportRule rr = new ReportRule();
		rr.setContent(content);
		rr.setContentdes(contentdes);
		rr.setCstatus(cstatus);
		rr.setKeyid(keyid);
		rr.setModelid(repid);
		rr.setRtype(rtype);
		rr.setRulecode(rulecode);
		rr.setRulemsg(rulemsg);
		rr.setTargetid(targetid);
		rr.setTargetname(targetname);
		rr.setRangelevel(range);
		rr.setTargettable(targettable);
		rr.setFlag(bflag);
		if ("3".equals(bflag)) {
			// 插入系统日志
			sls.insertLog(user.getPkid() + "", userName, "-1",
					LogUtil.LogType_User_Operate, "", "规则配置->隐私数据规则配置", "-1");
		} else {
			// 插入系统日志
			sls.insertLog(user.getPkid() + "", userName, "-1",
					LogUtil.LogType_User_Operate, "", "规则配置 -> 默认值规则配置", "-1");
		}
		rr.setGroupid("1");
		rr.setSystemcode(systemcode);
		boolean flag = rts.saveReportRule(rr);
		List ruleType = rts.getBasicRuleType(bflag, systemcode);
		if (flag) {
			request.setAttribute("message", "1");
		} else {
			request.setAttribute("message", "2");
		}
		request.setAttribute("reportrule", rr);
		request.setAttribute("ruleType", ruleType);

		List reportType = rts.getReportType(Integer.parseInt(systemcode),
				Integer.parseInt(levelFlag));

		List reportList = rts.getReport(reportType);
		List targetsList = new ArrayList();

		if (reportList.size() > 0) {
			Report re = (Report) reportList.get(0);
			targetsList = rds.getReportTargets(new Long(re.getPkid()));
		}
		request.setAttribute("reportlist", reportList);
		request.setAttribute("targetsList", targetsList);
		request.setAttribute("levelFlag", levelFlag);
		request.setAttribute("flags", flags);
		if ("3".equals(bflag)) {
			request.setAttribute("atiaos", 1);
			return mapping.findForward("editprirule");
		}
		return mapping.findForward("editdefrule");
	}

	/**
	 * 
	 * 点菜单基础类规则配置 进入方法 基础规则配置列表界面查询按钮
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listBasicRule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try{
			if (log.isDebugEnabled()) {
				log.info("Entering 'listBasicRule' method");
			}

			HttpSession session = request.getSession();

			String levelFlag = request.getParameter("levelFlag");
			String repruletype = request.getParameter("repruletype");
			repruletype = (repruletype == null) ? "0" : repruletype;
			String reportn = request.getParameter("reportn");
			reportn = (reportn == null) ? "0" : reportn;
			String systemcode = FuncConfig.getProperty("system.sysflag", "2");
			User user = getUser(request);
			Long userId = user.getPkid();

			List reportType = rts.getReportType(Integer.parseInt(systemcode),
					Integer.parseInt(levelFlag));
			List reportType1 = reportType;

			// List reportList=rts.getReport(reportType);

			String loginDate = (String) session.getAttribute("logindate");
			List reportList = rts.getDateOrganEditReportForStandard(
					loginDate.replaceAll("-", ""), userId, systemcode, levelFlag);
			List reportList1 = reportList;

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

			List reporRule = rts.getReportRule(reportList, ruleType, null);
			for (int j = 0; j < reporRule.size(); j++) {
				ReportRule reru = (ReportRule) reporRule.get(j);

				for (int m = 0; m < reportList1.size(); m++) {
					Report rp = (Report) reportList1.get(m);
					if (reru.getModelid().equals(rp.getPkid().toString())) {
						reru.setReportname(rp.getName());
					}
				}

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
			request.setAttribute("reportid", reportn);
			request.setAttribute("ruleType", ruleType1);
			request.setAttribute("levelFlag", levelFlag);
			request.setAttribute("reporRule", reporRule);
			return mapping.findForward("listbasicrule");
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
	 * 查询出规则列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listPriRule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try{
			if (log.isDebugEnabled()) {
				log.info("Entering 'listPriRule' method");
			}
			HttpSession session = request.getSession();
			String levelFlag = request.getParameter("levelFlag");
			String repruletype = request.getParameter("repruletype"); // 瑙勫垯绫诲瀷
			repruletype = (repruletype == null) ? "0" : repruletype;
			String reportn = request.getParameter("reportn"); // 妯″瀷
			reportn = (reportn == null) ? "0" : reportn;
			String systemcode = FuncConfig.getProperty("system.sysflag", "1");
			User user = getUser(request);
			Long userId = user.getPkid();

			List reportType = rts.getReportType(Integer.parseInt(systemcode),
					Integer.parseInt(levelFlag));
			List reportType1 = reportType;

			// List reportList=rts.getReport(reportType);
			String loginDate = (String) session.getAttribute("logindate");
			List reportList = rts.getDateOrganEditReportForStandard(
					loginDate.replaceAll("-", ""), userId, systemcode, levelFlag);
			List reportList1 = reportList;

			List ruleType = rts.getBasicRuleType("3", systemcode);
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

			List reporRule = rts.getReportRule(reportList, ruleType, null);
			for (int j = 0; j < reporRule.size(); j++) {
				ReportRule reru = (ReportRule) reporRule.get(j);

				for (int m = 0; m < reportList1.size(); m++) {
					Report rp = (Report) reportList1.get(m);
					if (reru.getModelid().equals(rp.getPkid().toString())) {
						reru.setReportname(rp.getName());
					}
				}

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
			request.setAttribute("reportid", reportn);
			request.setAttribute("ruleType", ruleType1);
			request.setAttribute("levelFlag", levelFlag);
			request.setAttribute("reporRule", reporRule);
			return mapping.findForward("listprirule");
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
	 * 
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward testSql(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'testSql' method");
		}
		String content = request.getParameter("content");
		content = content.replaceAll("\\{M\\}", "01");
		content = content.replaceAll("\\$\\{LASTMONTH\\}", "09");
		content = content.replaceAll("\\$\\{LASTDATE\\}", "20130900");
		content = content.replaceAll("\\$\\{NEWDATE\\}", "20131000");
		int aa = content.toLowerCase().indexOf(" from ");
		content = "select count(*) as num  "
				+ content.substring(aa, content.length());
		boolean flag = rts.testSql(content);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(flag);
		out.flush();
		out.close();
		return null;
	}

	/**
	 * 
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delFromValidator(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'delFromValidator' method");
		}
		String fvpkid = request.getParameter("fvpkid");

		int flag = fvs.deleteFromValidator(new Long(fvpkid));
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(flag);
		out.flush();
		out.close();
		return null;

	}

	/**
	 * 基础类规则配置 页面上删除按钮功能
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteReportRule(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'deleteReportRule' method");
		}
		HttpSession session = request.getSession();
		String rulecode = request.getParameter("rulecode");
		try {
			rts.deleteReportRule(rulecode);
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print("删除成功！");
			out.flush();
			out.close();
			return null;
		} catch (RuntimeException re) {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print("删除过程中出错！");
			out.flush();
			out.close();
			return null;
		}
	}
}
