package com.krm.ps.model.flexiblequery.web.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.model.flexiblequery.services.DataFillService;
import com.krm.ps.model.flexiblequery.services.OrganService;
import com.krm.ps.model.flexiblequery.services.ReportConfigService;
import com.krm.ps.model.flexiblequery.services.ReportDefineService;
import com.krm.ps.model.flexiblequery.services.ReportRuleService;
import com.krm.ps.model.flexiblequery.web.from.FlexibleQueryForm;
import com.krm.ps.model.repfile.service.RepFileService;
import com.krm.ps.model.vo.CodeRepJhgzZf;
import com.krm.ps.model.vo.DicItem;
import com.krm.ps.model.vo.MapColumn;
import com.krm.ps.model.vo.Page;
import com.krm.ps.model.vo.PaginatedListHelper;
import com.krm.ps.model.vo.ReportResult;
import com.krm.ps.model.vo.ReportRule;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportConfig;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.util.FuncConfig;

public class QueryAction extends BaseAction {

	public DataFillService getDataFillService() {
		return (DataFillService) getBean("flexiblequeryDataFillService");
	}

	public ReportRuleService getReportRuleService() {
		return (ReportRuleService) getBean("flexiblequeryReportruleService");
	}

	protected ReportDefineService getReportDefineServices() {
		return (ReportDefineService) getBean("flexiblequeryReportDefineService");
	}

	protected ReportConfigService getReportConfigService() {
		return (ReportConfigService) getBean("flexiblequeryReportConfigService");
	}

	protected OrganService getOrganServices() {
		OrganService os = (OrganService) getBean("flexiblequeryOrganService");
		return os;
	}
	/**
	 * 查找数据 跳转到数据录入修改展示数据页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public ActionForward getDataDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		if (log.isDebugEnabled()) {
			log.info("Entering Standard_DataGather 'getDataDetail' method");
		}
		//FlexibleQueryForm flexibleQueryForm =(FlexibleQueryForm)form;
		
		HttpSession session = request.getSession();
		String levelFlag = request.getParameter("levelFlag");
		String levelview = request.getParameter("levelview") == "" ? null: request.getParameter("levelview");
		String systemcode = FuncConfig.getProperty("system.sysflag", "2");
		String cstatus = request.getParameter("cstatus");// 获得要查询的数据状态，2为已提交，5为已保存未提交// 4为已同步
	    
		
		String zhi1=request.getParameter("zhi1");
		String zhi2=request.getParameter("zhi2");
		String zhi3=request.getParameter("zhi3");
		
		String tField1 = request.getParameter("tField1");
		String tField2 = request.getParameter("tField2");
		String tField3 = request.getParameter("tField3");
		if(tField1=="" || "".equals(tField1)){
			tField1=null;
		}
		if(tField2=="" || "".equals(tField2)){
			tField2=null;
		}
		if(tField3=="" || "".equals(tField3)){
			tField3=null;
		}
		String aacstatus = null;
		if (cstatus != null && !"".equals(cstatus)) {
			aacstatus = "'" + cstatus + "'";
		}
		User user = getUser(request);
		Long userId = user.getPkid();
		
		Page page = getPage(request);
		
		String organCode = request.getParameter("organId");
		int idxid = getUser(request).getOrganTreeIdxid();
		String reportDate = request.getParameter("reportDate");
		if (null == reportDate) {
			reportDate = (String) session.getAttribute("logindate");
		}
		String organId = request.getParameter("organId"); // 机构 多选
		if (null == organId) {
			organId = user.getOrganId();
		}
		
		String reportId = request.getParameter("reportId"); // 报表单选
		if (null == reportId) {
			
			reportId = "10000";
//			// 获得目标层数据模型的类型
//			List reportType = getReportRuleService().getReportType(
//					Integer.parseInt(systemcode), Integer.parseInt(levelFlag),userId);
//			// 获得类型下对应的所有报表
//			/*List reportList = getReportRuleService().getReport(reportType);
//			Report report = (Report) reportList.get(0);*/
//			Report report = (Report) reportType.get(0);
//			reportId = String.valueOf(report.getPkid());
//			String reportName = report.getName();
//			request.setAttribute("reportName", reportName);
			
			if (levelFlag != null && !"".equals(levelFlag)) {
				request.setAttribute("params", "&paramdate=" + reportDate
						+ "&paramorgan=" + organId + "&levelFlag=" + levelFlag);
			} else {
				request.setAttribute("params", "&paramdate=" + reportDate
						+ "&paramorgan=" + organId);
			}
			request.setAttribute("dataDate", reportDate);
			request.setAttribute("reportId", reportId);
			request.setAttribute("levelFlag", levelFlag);
			request.setAttribute("organId", organCode);
			request.setAttribute("tField1", tField1);
			request.setAttribute("zhi1", zhi1);
			request.setAttribute("tField2", tField2);
			request.setAttribute("zhi2", zhi2);
			request.setAttribute("tField3", tField3);
			request.setAttribute("zhi3", zhi3);
			if (cstatus != null) {
				request.setAttribute("lxhcstatus", cstatus);
			}
			if (levelview != null) {
				request.setAttribute("lxview", levelview);
			}
			request.setAttribute("totalPage", page.getTotalPage());
			request.setAttribute("page", page);
			
			return mapping.findForward("enterreportdataresult");
			
		}
		List<ReportConfig> resultablename = getReportConfigService()
				.getdefCharByTem(new Long(reportId), new Long(34));
		String tablenames = "";
		for (ReportConfig rc : resultablename) {
			String rtable = rc.getDefChar();
			rtable = rtable.replaceAll("\\{M\\}", reportDate
					.replaceAll("-", "").substring(4, 6));
			rtable = rtable.replaceAll("\\{D\\}", reportDate
					.replaceAll("-", "").substring(6, 8));
			rtable = rtable.replaceAll("\\{Y\\}", reportDate
					.replaceAll("-", "").substring(0, 4));
			tablenames += rtable + "@"; // 把结果表的名称存下来
			// request.setAttribute("lxhresulttablename", rtable); //把结果表的名称存下来
		}
		request.setAttribute("lxhresulttablename", tablenames);
		
		

		// 获得模板的栏
		List reportTargetList = getReportDefineServices().getReportTargets(
				Long.parseLong(reportId));

		OrganInfo organInfo = getOrganServices().getOrganByCode(organCode);
		request.setAttribute("organInfo", organInfo);
		// 查询需要显示select列表的字典项信息
		// dicMap用来维护target项和字典列表的关系
		Map<Long, List<DicItem>> dicMap = getDicMap(
				reportDate.replaceAll("-", ""), reportTargetList);
		// 查出数据列表
		List mapColumn = getDataFillService().getMapColumn();
		Map mapC = new HashMap();
		for (int i = 0; i < mapColumn.size(); i++) {
			MapColumn mapcolumn = (MapColumn) mapColumn.get(i);
			mapC.put(mapcolumn.getResourceColumn(), mapcolumn.getTargetColumn());
		}
		if (mapC.isEmpty()) {
			mapC.put("pkid", "pkid");
			mapC.put("report_date", "report_date");
			mapC.put("organ_id", "organ_id");
		}
		try {
//		List reportDataList14 = getDataFillService().getReportDataAll1(
//					dicMap, aacstatus, user.getIsAdmin(), resultablename,
//					Long.parseLong(reportId), organId,
//					reportDate.replaceAll("-", ""), reportTargetList, page,
//					idxid, levelFlag, mapC);
			List reportDataList1 = getDataFillService().getReportDataAllMapColumn(dicMap, aacstatus,
					user.getIsAdmin(), resultablename, Long.parseLong(reportId),
					organId, reportDate.replaceAll("-", ""), reportTargetList,
					page, idxid, levelFlag, zhi1, tField1, zhi2, tField2, zhi3, tField3, mapC);
			Map repDataMap = new HashMap();
			if (reportDataList1 != null && reportDataList1.size() > 0) {
				repDataMap = (Map) reportDataList1.get(0);
				request.setAttribute("repDataMap", reportDataList1.get(0));
			}
			request.setAttribute("reportTargetList", reportTargetList);
			// 如果cstatus不为空，是查询的补录数据 查询已同步的数据时，不用替换
			if (cstatus != null && !"".equals(cstatus) && !"4".equals(cstatus)) {
				// 获昨结果数据集
				List idList = new ArrayList();
				for (Object e : repDataMap.keySet()) {
					String idStr = (String) e;
					String id = idStr.substring(0, idStr.indexOf("_"));
					idList.add(id);
				}
				String targetids = "";
				for (int a = 0; a < reportTargetList.size(); a++) {
					ReportTarget rta = (ReportTarget) reportTargetList.get(a);
					targetids += "'" + rta.getTargetField() + "',";
				}
				List<ReportResult> resultList = getReportRuleService()
						.getReportResultByDataId(aacstatus, idList, reportDate,
								reportDataList1.get(3).toString(),
								targetids.substring(0, targetids.length() - 1));
				// 错误信息的构建
				Map<String, Map> checkResult = getCheckResultYJH(repDataMap,
						Long.parseLong(reportId), resultList);
				if (checkResult.containsKey("repDataMap0")) {
					repDataMap = getDicMapByCstatus(
							reportDate.replaceAll("-", ""), reportTargetList,
							checkResult.get("repDataMap0"));
					request.setAttribute("repDataMap", repDataMap);
				}
			}
			// 查询出该报表的规则
			request.setAttribute("repItemSort", reportDataList1.get(2));
			if (reportDataList1 != null && reportDataList1.size() > 0) {
				PaginatedListHelper plh = (PaginatedListHelper) reportDataList1
						.get(1);
				page.setTotalRecord(plh.getFullListSize());
				if (page.getTotalRecord() % page.getPageSize() > 0) {
					page.setTotalPage(page.getTotalRecord()
							/ page.getPageSize() + 1);
				} else {
					page.setTotalPage(page.getTotalRecord()
							/ page.getPageSize());
				}
				request.setAttribute("totalPage", page.getTotalPage());
				request.setAttribute("page", page);
			}
			// 加入报表名称，显示到页面
			Report r = getReportDefineServices().getReport(Long.parseLong(reportId));
			request.setAttribute("reportName", r.getName());
			if (levelFlag != null && !"".equals(levelFlag)) {
				request.setAttribute("params", "&paramdate=" + reportDate
						+ "&paramorgan=" + organId + "&levelFlag=" + levelFlag);
			} else {
				request.setAttribute("params", "&paramdate=" + reportDate
						+ "&paramorgan=" + organId);
			}
			request.setAttribute("dataDate", reportDate);
			request.setAttribute("reportId", reportId);
			request.setAttribute("levelFlag", levelFlag);
			request.setAttribute("organId", organCode);
			request.setAttribute("tField1", tField1);
			request.setAttribute("zhi1", zhi1);
			request.setAttribute("tField2", tField2);
			request.setAttribute("zhi2", zhi2);
			request.setAttribute("tField3", tField3);
			request.setAttribute("zhi3", zhi3);
			if (cstatus != null) {
				request.setAttribute("lxhcstatus", cstatus);
			}
			if (levelview != null) {
				request.setAttribute("lxview", levelview);
			}
		
			
			return mapping.findForward("enterreportdataresult");
		} catch (Exception e) {
			e.printStackTrace();
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw, true));
			String str = sw.toString();
			request.setAttribute("errors", str);
			request.setAttribute("errorMessages", e);
			return mapping.findForward("error");
		}
	}

	/**
	 * 获取页数
	 * 
	 * @param request
	 * @return
	 */
	public Page getPage(HttpServletRequest request) {
		String pageStr = request.getParameter("page");
		int page = 1;
		if (pageStr != null && pageStr.matches("^\\d+$")) {
			page = Integer.parseInt(pageStr);
		}
		return new Page(page);
	}

	/**
	 * 重置数据状态״̬
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward resetReportData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.info("Entering Standard_DataGather 'resetReportData' method");
		}
		HttpSession session = request.getSession();
		String resulttablename = request.getParameter("resulttablename");
		String systemcode = FuncConfig.getProperty("system.sysflag", "2");
		String reportDate = request.getParameter("reportDate");// 获得要查询的数据状态，2为已提交，5为已保存未提交
																// 4为已同步
		User user = getUser(request);
		Long userId = user.getPkid();
		String dataId = request.getParameter("dataId");
		String reportId = request.getParameter("reportId");
		// 重置时，要改两处状态，一个结果表的cstatus改为0，一个为数据表中的itemvalue80改为0
		reportId = getDataFillService().getreportId(new Long(reportId), "1")
				.toString();// ת��
		String[] names = null;
		int flag = 0;
		if (resulttablename != null) {
			names = resulttablename.split("@");
		}
		for (int i = 0; i < names.length - 1; i++) {
			getDataFillService().resetResultData(names[i], null, dataId,
					reportId);
			flag = 1;
		}
		// int flag=ds.resetResultData(resulttablename, datafnam,
		// dataId,reportId);
		// 保存当前分页的数据（或保存所有分页数据时最后一页的操作）
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out;
		try {
			out = response.getWriter();
			out.print(flag);
			out.flush();
			out.close();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// 根据模型指标，获得该模型对应的字典项
	/***** 以后改造，做成批量 ******/
	private Map<Long, List<DicItem>> getDicMap(String reportdate,
			List reportTargetList1) {
		Map<Long, List<DicItem>> dicMap = new HashMap<Long, List<DicItem>>();
		for (int i = 0; i < reportTargetList1.size(); i++) {
			ReportTarget rt = (ReportTarget) reportTargetList1.get(i);
			if (rt != null) {
				// 读字典表
				if (new Integer(1).equals(rt.getFieldType())
						&& rt.getDicPid() != null) {
					List<DicItem> dicItems = getDataFillService().getDicItems(
							rt.getDicPid());
					dicMap.put(rt.getPkid(), dicItems);
					// 此时应该读实际对的表
				} else if (new Integer(3).equals(rt.getFieldType())
						&& rt.getDicPid() != null) {
					List<DicItem> dicItems = getDataFillService().getDicvalue(
							reportdate, rt);
					dicMap.put(rt.getPkid(), dicItems);
				}
			}
		}
		return dicMap;
	}

	/**
	 * @param reportDataList
	 *            数据列表
	 */
	private Map getCheckResultYJH(Map repDataMap, Long reportId,
			List<ReportResult> resultList) {
		Map<String, String> pcheckMap = new HashMap<String, String>(); // 存入页面校验脚本
		Map<String, String> rvalueMap = new HashMap<String, String>(); // 存入参考值校验脚本
		Map<String, String> pflagMap = new HashMap<String, String>(); // 存入校验类型
		Map<String, String> valueMap = new HashMap<String, String>(); // 放参考值
		Map<String, Map> checkresult = new HashMap<String, Map>();
		Map<String, String> resultMap = new HashMap<String, String>();
		Map<String, Long> relation = new HashMap<String, Long>();
		Map<String, String> result = new HashMap<String, String>();
		Map<String, String> resultMsg = new HashMap<String, String>();
		Map<String, String> dtypeMap = new HashMap<String, String>(); // 数据类型0为通过
		// 1为不通过
		Map<String, String> cstatusMap = new HashMap<String, String>();// 数据状态״̬
		Set ruleset = new HashSet();
		String rulecode = "";
		for (ReportResult rr : resultList) {
			ruleset.add(rr.getRulecode());
		}
		for (Object o : ruleset) {
			rulecode += "'" + String.valueOf(o) + "',";
		}
		if (!"".equals(rulecode)) {
			rulecode = rulecode.substring(0, rulecode.length() - 1);
		} else {
			rulecode = "'" + 1 + "'";
		}
		// 获得规则列表
		List<ReportRule> rulist = getReportRuleService().getReportRuleBycode(
				rulecode);
		for (ReportResult rr : resultList) {
			resultMap.put(rr.getModelid() + "_" + rr.getTargetid(),
					rr.getRulemsg());
			relation.put(rr.getRulecode(), reportId);
			// 将结果表中的模型对应列转化成模板对应列
			Object[] arr = getDataFillService().getRelateTarget(
					rr.getModelid(), rr.getTargetid(), reportId);
			if (arr != null) {
				String pkey = String.valueOf(rr.getKeyvalue()) + "_" + arr[2];
				if (null != rr.getNewvalue() && !"".equals(rr.getNewvalue())) {
					repDataMap.put(pkey, rr.getNewvalue());
				}
				// 组织成了和repDataMap中一样的键值ֵ
				result.put(pkey, rr.getRulecode());
				for (ReportRule mm : rulist) {
					if (rr.getRulecode().equals(mm.getRulecode())) {
						resultMsg.put(mm.getRulecode(), mm.getRulemsg());
						// 组织成了和repDataMap中一样的键值ֵ
						pcheckMap = getMapValue(pcheckMap, mm.getPcheck(), pkey);
						rvalueMap = getMapValue(rvalueMap, mm.getRcontent(),
								pkey);
						pflagMap = getMapValue(pflagMap, mm.getPflag(), pkey);
						valueMap.put(pkey, rr.getRvalue());
						if (rr.getDtype() == null
								|| "null".equals(rr.getDtype())
								|| "".equals(rr.getDtype())) {
							if (rr.getNewvalue() == null
									|| "".equals(rr.getNewvalue())) {
								dtypeMap.put(pkey, "1");
							} else {
								dtypeMap.put(pkey, "0");
							}

						} else {
							dtypeMap.put(pkey, rr.getDtype());
						}
						cstatusMap.put(pkey, rr.getCstatus());
					}

				}
			}
			checkresult.put("pcheckMap0", pcheckMap);
			checkresult.put("rvalueMap0", rvalueMap);
			checkresult.put("pflagMap0", pflagMap);
			checkresult.put("valueMap0", valueMap);
			checkresult.put("dtypeMap0", dtypeMap);
		}
		for (Entry e : resultMsg.entrySet()) {
			String ruleCode = (String) e.getKey();
			if (StringUtils.isNotEmpty(ruleCode)) {
				String msg = (String) e.getValue();
				String[] keys = ruleCode.split("_");
				String key = keys[0] + "_" + keys[1];
				if (resultMap.containsKey(key)) {
					String reMsg = resultMap.get(key);
					if (msg != null && !msg.equals("")) {// 20140918
						if (!resultMap.get(key).contains(msg)) { // 这个判断注掉就不会有问题但没做修改不知道是否影响业务逻辑
							reMsg = resultMap.get(key) + "  " + msg;
						}
					}
					resultMap.put(key, reMsg);
				}
			}
		}
		Map<String, String> targetMap1 = getDataFillService().getRelateTargets(
				relation);
		for (Entry e : targetMap1.entrySet()) {
			// 此处含义：(String) e.getValue()为模板的id_指标的targetfeild;
			// resultMap.get(e.getKey())为该模板对应的实际的模型以及指标组合的错误提示
			result.put((String) e.getValue(), resultMap.get(e.getKey()));
		}
		checkresult.put("ruleMap0", result);
		checkresult.put("repDataMap0", repDataMap);
		return checkresult;
	}

	// 根据模型指标，获得该模型对应的字典项
	/***** 以后改造，做成批量 ******/
	private Map getDicMapByCstatus(String reportdate, List reportTargetList1,
			Map repDataMap) {
		for (int i = 0; i < reportTargetList1.size(); i++) {
			List<DicItem> dicItems = null;
			ReportTarget rt = (ReportTarget) reportTargetList1.get(i);
			// 读字典表
			if (new Integer(1).equals(rt.getFieldType())
					&& rt.getDicPid() != null) {
				dicItems = getDataFillService().getDicItems(rt.getDicPid());
				// 此时应该读实际对的表
			} else if (new Integer(3).equals(rt.getFieldType())
					&& rt.getDicPid() != null) {
				dicItems = getDataFillService().getDicvalue(reportdate, rt);
			}
			if (dicItems != null) {
				Set keyr = repDataMap.keySet();
				Iterator targetIt = keyr.iterator();
				while (targetIt.hasNext()) {
					String keyvalu = targetIt.next().toString();
					String[] arr = keyvalu.split("_");
					if (rt.getTargetField().equals(arr[1])) {
						for (DicItem dc : dicItems) {
							if (repDataMap.get(keyvalu).equals(dc.getDicId())) {
								repDataMap.put(keyvalu, dc.getDicName());
							}
						}
					}
				}
			}
		}
		return repDataMap;
	}

	/**
	 * 得出map
	 * 
	 * @return
	 */
	private Map<String, String> getMapValue(Map<String, String> pMap,
			String value, String pkey) {
		if (pMap.containsKey(pkey)) {
			pMap.put(pkey, pMap.get(pkey) + ";" + value);
		} else {
			pMap.put(pkey, value);
		}
		return pMap;
	}

	/**
	 * 数据维护组合查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward getDataDetailgjcx(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if (log.isDebugEnabled()) {
			log.info("Entering Standard_DataGather 'getDataDetailgjcx' method");
		}
		FlexibleQueryForm reportdataform = (FlexibleQueryForm) form;

		String zhi1 = reportdataform.getZhi1().trim(); //页面高级查询的值
		String zhi2 = reportdataform.getZhi2().trim(); //页面高级查询的值
		String zhi3 = reportdataform.getZhi3().trim(); //页面高级查询的值
		String tField1 = request.getParameter("targetField1");
		String tField2 = request.getParameter("targetField2");
		String tField3 = request.getParameter("targetField3");
		HttpSession session = request.getSession();
		String levelFlag = reportdataform.getLevelFlag();
		String levelview = request.getParameter("levelview");
		String systemcode = FuncConfig.getProperty("system.sysflag", "2");
		String cstatus = request.getParameter("cstatus");//获得要查询的数据状态，2为已提交，5为已保存未提交 4为已同步  
		String aacstatus = null;
		if (cstatus != null && !"".equals(cstatus)) {
			aacstatus = "'" + cstatus + "'";
		}
		User user = getUser(request);
		Long userId = user.getPkid();
		String organCode = request.getParameter("organId");
		int idxid = getUser(request).getOrganTreeIdxid();
		String reportDate = request.getParameter("reportDate");
		if (null == reportDate) {
			reportDate = (String) session.getAttribute("logindate");
		}
		String reportId = request.getParameter("reportId"); //报表单选
		if (null == reportId) {
			//获得目标层数据模型的类型
			List reportType = getReportRuleService().getReportType(Integer.parseInt(systemcode),
					Integer.parseInt(levelFlag));
			List reportType1 = reportType;
			//获得类型下对应的所有报表
			List reportList = getReportRuleService().getReport(reportType);
			Report report = (Report) reportList.get(0);
			reportId = String.valueOf(report.getPkid());
			String reportName = report.getName();
			request.setAttribute("reportName", reportName); 
		}
		List<ReportConfig> resultablename =  getReportConfigService().getdefCharByTem(new Long(
				reportId), new Long(34));
		for (ReportConfig rc : resultablename) {
			String rtable = rc.getDefChar();
			rtable = rtable.replaceAll("\\{M\\}", reportDate
					.replaceAll("-", "").substring(4, 6));
			rtable = rtable.replaceAll("\\{D\\}", reportDate
					.replaceAll("-", "").substring(6, 8));
			rtable = rtable.replaceAll("\\{Y\\}", reportDate
					.replaceAll("-", "").substring(0, 4));
			request.setAttribute("lxhresulttablename", rtable);//把结果表的名称存下来
		}
		String organId = request.getParameter("organId"); //机构 多选
		if (null == organId) {
			organId = user.getOrganId();
		}
		Page page = getPage(request);

		// 获得模板的栏
		List reportTargetList = getReportDefineServices().getReportTargets(Long.parseLong(reportId));

		OrganInfo organInfo = getOrganServices().getOrganByCode(organCode);
		request.setAttribute("organInfo", organInfo);
		// 查询需要显示select列表的字典项信息
		// dicMap用来维护target项和字典列表的关系
		Map<Long, List<DicItem>> dicMap = getDicMap(
				reportDate.replaceAll("-", ""), reportTargetList);
		//查出数据列表     
		List mapColumn = getDataFillService().getMapColumn();
		log.info(mapColumn);
		Map mapC = new HashMap();
		for (int i = 0; i < mapColumn.size(); i++) {
			MapColumn mapcolumn = (MapColumn) mapColumn.get(i);
			mapC.put(mapcolumn.getResourceColumn(), mapcolumn.getTargetColumn());
		}
		if (mapC.isEmpty()) {
			mapC.put("pkid", "pkid");
			mapC.put("report_date", "report_date");
			mapC.put("organ_id", "organ_id");
		}
		List reportDataList1 = getDataFillService().getReportDataAllMapColumn(dicMap, aacstatus,
				user.getIsAdmin(), resultablename, Long.parseLong(reportId),
				organId, reportDate.replaceAll("-", ""), reportTargetList,
				page, idxid, levelFlag, zhi1, tField1, zhi2, tField2, zhi3, tField3, mapC);
		Map repDataMap = new HashMap();
		if (reportDataList1 != null && reportDataList1.size() > 0) {
			repDataMap = (Map) reportDataList1.get(0);
			request.setAttribute("repDataMap", reportDataList1.get(0));
		}
		request.setAttribute("reportTargetList", reportTargetList);
		//如果cstatus不为空，是查询的补录数据 查询已同步的数据时，不用替换
		if (cstatus != null && !"".equals(cstatus) && !"4".equals(cstatus)) {
			//获昨结果数据集
			List idList = new ArrayList();
			for (Object e : repDataMap.keySet()) {
				String idStr = (String) e;
				String id = idStr.substring(0, idStr.indexOf("_"));
				idList.add(id);
			}
			String targetids = "";
			for (int a = 0; a < reportTargetList.size(); a++) {
				ReportTarget rta = (ReportTarget) reportTargetList.get(a);
				targetids += "'" + rta.getTargetField() + "',";
			}
			List<ReportResult> resultList = getReportRuleService().getReportResultByDataId(
					aacstatus, idList, reportDate, reportDataList1.get(3)
							.toString(), targetids.substring(0,
							targetids.length() - 1));
			// 错误信息的构建
			Map<String, Map> checkResult = getCheckResultYJH(repDataMap,
					Long.parseLong(reportId), resultList);
			if (checkResult.containsKey("repDataMap0")) {
				repDataMap = getDicMapByCstatus(reportDate.replaceAll("-", ""),
						reportTargetList, checkResult.get("repDataMap0"));
				request.setAttribute("repDataMap", repDataMap);
			}
		}
		// 查询出该报表的规则
		request.setAttribute("repItemSort", reportDataList1.get(2));
		if (reportDataList1 != null && reportDataList1.size() > 0) {
			PaginatedListHelper plh = (PaginatedListHelper) reportDataList1
					.get(1);
			page.setTotalRecord(plh.getFullListSize());
			if (page.getTotalRecord() % page.getPageSize() > 0) {
				page.setTotalPage(page.getTotalRecord() / page.getPageSize()
						+ 1);
			} else {
				page.setTotalPage(page.getTotalRecord() / page.getPageSize());
			}
			request.setAttribute("totalPage", page.getTotalPage());
			request.setAttribute("page", page);
		}
		if (levelFlag != null && !"".equals(levelFlag)) {
			request.setAttribute("params", "&paramdate=" + reportDate
					+ "&paramorgan=" + organId + "&levelFlag=" + levelFlag);
		} else {
			request.setAttribute("params", "&paramdate=" + reportDate
					+ "&paramorgan=" + organId);
		}

		if (!"0".equals(tField1)) {
			request.setAttribute("zhi1", zhi1);
		} else {
			request.setAttribute("zhi1", " ");
		}
		
		if (!"0".equals(tField2)) {
			request.setAttribute("zhi2", zhi2);
		} else {
			request.setAttribute("zhi2", " ");
		}
		
		if (!"0".equals(tField3)) {
			request.setAttribute("zhi3", zhi3);
		} else {
			request.setAttribute("zhi3", " ");
		}
		request.setAttribute("dataDate", reportDate);
		request.setAttribute("reportId", reportId);
		request.setAttribute("levelFlag", levelFlag);
		request.setAttribute("organId", organCode);
		request.setAttribute("tField1", tField1);
		request.setAttribute("tField2", tField2);
		request.setAttribute("tField3", tField3);
		if (cstatus != null) {
			request.setAttribute("lxhcstatus", cstatus);
		}
		if (levelview != null) {
			request.setAttribute("lxview", levelview);
		}
		// 加入报表名称，显示到页面    
		Report r = getReportDefineServices().getReport(Long.parseLong(reportId));
		request.setAttribute("reportName", r.getName());
		return mapping.findForward("enterreportdataresult");
	}

	private RepFileService getRepFileservice() {
		return (RepFileService) getBean("repfileservice");
	}
	

	/**
	 * 导出上报数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward exportrepDataDeta(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if (log.isDebugEnabled()) {
			log.info("Entering  'exportrepDataDeta' method");
		}
		FlexibleQueryForm reportdataform = (FlexibleQueryForm) form;

		String zhi1 = reportdataform.getZhi1().trim(); //页面高级查询的值
		String zhi2 = reportdataform.getZhi2().trim(); //页面高级查询的值
		String zhi3 = reportdataform.getZhi3().trim(); //页面高级查询的值
		String tField1 = request.getParameter("targetField1");
		String tField2 = request.getParameter("targetField2");
		String tField3 = request.getParameter("targetField3");
		HttpSession session = request.getSession();
		String levelFlag = reportdataform.getLevelFlag();
		
		String aacstatus = null;
		 
		User user = getUser(request);
		int idxid = getUser(request).getOrganTreeIdxid();
		
		String reportDate = request.getParameter("reportDate");
		if (null == reportDate) {
			reportDate = (String) session.getAttribute("logindate");
		}
		
		String organId = request.getParameter("organId"); //机构 多选
		if (null == organId) {
			organId = user.getOrganId();
		}
		Page page = getPage(request);
		
		String reportId = request.getParameter("reportId"); //报表单选
		
		List<ReportConfig> resultablename =  getReportConfigService().getdefCharByTem(new Long(reportId), new Long(34));

		// 获得模板的栏
		List reportTargetList = getReportDefineServices().getReportTargets(Long.parseLong(reportId));

		OrganInfo organInfo = getOrganServices().getOrganByCode(organId);
		
		// 查询需要显示select列表的字典项信息
		// dicMap用来维护target项和字典列表的关系
		Map<Long, List<DicItem>> dicMap = getDicMap(reportDate.replaceAll("-", ""), reportTargetList);
		//查出数据列表     
		List mapColumn = getDataFillService().getMapColumn();

		Map mapC = new HashMap();
		for (int i = 0; i < mapColumn.size(); i++) {
			MapColumn mapcolumn = (MapColumn) mapColumn.get(i);
			mapC.put(mapcolumn.getResourceColumn(), mapcolumn.getTargetColumn());
		}
		if (mapC.isEmpty()) {
			mapC.put("pkid", "pkid");
			mapC.put("report_date", "report_date");
			mapC.put("organ_id", "organ_id");
		}
		page.setRecordNo(1);
		page.setPageSize(100000);
		List reportDataList1 = getDataFillService().getReportDataAllMapColumn(dicMap, aacstatus,
				user.getIsAdmin(), resultablename, Long.parseLong(reportId),
				organId, reportDate.replaceAll("-", ""), reportTargetList,
				page, idxid, levelFlag, zhi1, tField1, zhi2, tField2, zhi3, tField3, mapC);
	 
		  
		// 加入报表名称，显示到页面    
		Report r = getReportDefineServices().getReport(Long.parseLong(reportId));
		String rtable = "";
		for(ReportConfig rc:resultablename){
			 
			 rtable=rc.getDescription();
			
	   }
		
		
		RepFileService rpf = getRepFileservice();
		HSSFWorkbook wb = rpf.getXlsWorkZf(reportDataList1, reportTargetList);
		String fileName = "";
		try {
			String filestr = r.getName() ;
			fileName = new String(   filestr.getBytes("gb2312"),"iso8859-1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		response.setHeader("content-disposition", "attachment; filename="
				+ fileName + ".xls");
		response.setHeader("Content-Type",
				"application/vnd.ms-excel; charset=GBK");
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			wb.write(out);
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
		
	}

}
