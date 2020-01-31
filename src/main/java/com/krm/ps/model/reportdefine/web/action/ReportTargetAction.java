package com.krm.ps.model.reportdefine.web.action;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.model.reportdefine.services.ReportConfigService;
import com.krm.ps.model.reportdefine.services.ReportDefineService;
import com.krm.ps.model.reportdefine.services.StrdReporeWheconditionService;
import com.krm.ps.model.vo.DicItem;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportConfig;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;
import com.krm.ps.sysmanage.reportdefine.vo.StrdReportWhecondition;

public class ReportTargetAction extends BaseAction {

	private static String REPORT_ID = "reportId";
	private static String TARGET_ID = "targetId";
	private static String TARGET_LIST = "targetList";

	/**
	 * 科目表维护--元数据管理
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	private Long getReportId(HttpServletRequest request) {
		String id = request.getParameter(REPORT_ID);
		if (null != id) {
			return new Long(id);
		}
		return null;
	}

	private Long getSessionReportId(HttpServletRequest request)
			throws Exception {
		Object id = request.getSession().getAttribute(REPORT_ID);
		if (null != id) {
			return (Long) id;
		}
		throw new Exception("sccion过期");
	}

	public ActionForward enter(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'enter' method");
		}
		if (null != getReportId(request)) {
			request.getSession().removeAttribute(REPORT_ID);
			request.getSession().setAttribute(REPORT_ID, getReportId(request));
		}
		return list(mapping, form, request, response);
	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return list(mapping, form, request, response,
				getSessionReportId(request));
	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			Long reportId) {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'list' method");
		}

		ReportDefineService rs = (ReportDefineService) getBean("rdreportDefineService");
		String tableName = request.getParameter("tableName");
		String editpkid = request.getParameter("editpkid");
		List targets = rs.getReportTargets(reportId, tableName);
		if (targets != null) {
			for (int i = 0; i < targets.size(); i++) {
				ReportTarget target = (ReportTarget) targets.get(i);
				String fdate = target.getBeginDate();
				String tdate = target.getEndDate();
				String s = "-";
				if (fdate != null) {
					target.setBeginDate(fdate.substring(0, 4) + s
							+ fdate.substring(4, 6) + s + fdate.substring(6, 8));
				}
				if (tdate != null) {
					target.setEndDate(tdate.substring(0, 4) + s
							+ tdate.substring(4, 6) + s + tdate.substring(6, 8));
				}
			}
		}
		Object id = request.getSession().getAttribute(REPORT_ID);
		// 查询字典表
		List<DicItem> dicList = rs.getDicAll();
		request.setAttribute(TARGET_LIST, targets);
		request.setAttribute("dicList", dicList);
		request.setAttribute("tableName", tableName);
		request.setAttribute("editpkid", editpkid);
		request.setAttribute("repId", (Long) id);
		return mapping.findForward("list");
	}

	/**
	 * 元数据管理--全部保存
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveTarget(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ReportDefineService rs = (ReportDefineService) getBean("rdreportDefineService");
		List<ReportTarget> targetlist = convertTarget(request);
		rs.saveReportTarget(targetlist);
		return null;
	}

	private List<ReportTarget> convertTarget(HttpServletRequest request) {
		String editsize = request.getParameter("editsize");
		String reportId = request.getParameter("reportId");
		List<ReportTarget> result = new ArrayList<ReportTarget>();
		if (editsize != null) {
			int size = Integer.parseInt(editsize);
			for (int i = 0; i < size; i++) {
				ReportTarget rt = new ReportTarget();
				String pkid = request.getParameter("edititem" + i + "[pkid]");
				if (pkid != null && !pkid.equals("")) {
					rt.setPkid(Long.parseLong(pkid));
				}
				rt.setReportId(Long.parseLong(reportId));
				rt.setTargetName(request.getParameter("edititem" + i
						+ "[targetName]").trim());
				rt.setTargetField(request.getParameter("edititem" + i
						+ "[targetField]"));
				String targetOrder = request.getParameter("edititem" + i
						+ "[targetOrder]");
				if (targetOrder != null && !targetOrder.equals("")) {
					rt.setTargetOrder(Integer.parseInt(targetOrder));
				}
				rt.setStatus(1);
				String dataType = request.getParameter("edititem" + i
						+ "[dataType]");
				if (dataType != null && !dataType.equals("")) {
					rt.setDataType(Integer.parseInt(dataType));
				}
				String dicPid = request
						.getParameter("edititem" + i + "[dicId]");
				if (dicPid != null && !dicPid.equals("0")) {
					if (dicPid.equals("9999") || dicPid == "9999") {
						rt.setFieldType(2);
					} else {
						rt.setDicPid(Long.parseLong(request
								.getParameter("edititem" + i + "[dicId]")));
						rt.setFieldType(1);
					}
				} else {
					rt.setFieldType(0);
				}
				rt.setRulesize(request.getParameter("edititem" + i
						+ "[rulesize]"));
				result.add(rt);
			}

		} else {
			ReportTarget rt = new ReportTarget();
			String pkid = request.getParameter("pkid");
			if (pkid != null && !pkid.equals("")) {
				rt.setPkid(Long.parseLong(pkid));
			}
			rt.setReportId(Long.parseLong(reportId));
			rt.setTargetName(request.getParameter("targetName").trim());
			rt.setTargetField(request.getParameter("targetField"));
			String targetOrder = request.getParameter("targetOrder");
			if (targetOrder != null && !targetOrder.equals("")) {
				rt.setTargetOrder(Integer.parseInt(targetOrder));
			}
			rt.setStatus(1);
			String dataType = request.getParameter("dataType");
			if (dataType != null && !dataType.equals("")) {
				rt.setDataType(Integer.parseInt(dataType));
			}
			String dicPid = request.getParameter("dicId");
			if (dicPid != null && !dicPid.equals("0")) {
				if (dicPid.equals("9999") || dicPid == "9999") {
					rt.setFieldType(2);
				} else {
					rt.setDicPid(Long.parseLong(request.getParameter("dicId")));
					rt.setFieldType(1);
				}
			} else {
				rt.setFieldType(0);
			}
			rt.setRulesize(request.getParameter("rulesize"));
			result.add(rt);
		}
		return result;
	}

	/**
	 * 元数据管理--删除
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward del(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'del' method");
		}
		ReportDefineService rs = (ReportDefineService) getBean("rdreportDefineService");
		Long id = getTargetId(request);
		if (null != id) {
			Long reportId = getReportId(request);
			ReportTarget rt = rs.removeReportTarget(reportId, id);
			if (rt != null) {
				// 操作类型为“删除列”
				request.setAttribute("operateType", "1");
				String regExp = ".*\\b" + rt.getReportId() + "\\.\\w+\\."
						+ rt.getTargetField() + "\\b.*";
				request.setAttribute("regExp", regExp);
				return list(mapping, form, request, response);
			}
		}
		return list(mapping, form, request, response);
	}

	private Long getTargetId(HttpServletRequest request) {
		String id = request.getParameter(TARGET_ID);
		if (null != id) {
			return new Long(id);
		} else {
			if ((Long) request.getAttribute(TARGET_ID) != null) {
				return (Long) request.getAttribute(TARGET_ID);
			}
		}
		return null;
	}

	/**
	 * 补录模板维护--列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward enterTargetUI(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'enterTargetUI' method");
		}

		request.setAttribute("reportId", 10000);
		request.setAttribute("reportId1", 10000);
		return mapping.findForward("targetUI");
	}

	/**
	 * 补录模板维护--配置模版指标
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listTarget(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'listTarget' method");
		}
		try{
			ReportDefineService rs = (ReportDefineService) getBean("rdreportDefineService");
			String levelFlag = request.getParameter("levelFlag");
			String templateId = request.getParameter("reportId");
			String modelId = request.getParameter("reportId1");
			Report template = rs.getReport(Long.parseLong(templateId));
			Report model = rs.getReport(Long.parseLong(modelId));
			List templateTargets = getReportTargets(Long.parseLong(templateId));
			List targets = getReportTargets(Long.parseLong(modelId));
			List<ReportTarget> targetListChecked = rs.getTemplateTargets(
					Long.parseLong(templateId), Long.parseLong(modelId));
			for (int i = 0; i < targetListChecked.size(); i++) {
				ReportTarget rt = targetListChecked.get(i);
				b: for (int j = 0; j < targets.size(); j++) {
					ReportTarget rt1 = (ReportTarget) targets.get(j);
					if (rt.getPkid().equals(rt1.getPkid())) {
						targets.remove(j);
						break b;
					}
				}
			}
			//
			StrdReporeWheconditionService strdReporeWheconditionService = (StrdReporeWheconditionService) this
					.getBean("strdReporeWheconditionService");
			String wd = strdReporeWheconditionService.getStrdreporeWhecondition(
					new Long(templateId), new Long(modelId));
			if (!"not".equals(wd)) {
				if (!"-".equals(wd)) {
					String[] splitwd = wd.split("-");
					String r1 = splitwd[0];
					String r2 = splitwd[1];
					String Rradio1 = r1.substring(r1.indexOf(".") + 1);
					String Rradio2 = r2.substring(r2.indexOf(".") + 1);
					String pkidqq = splitwd[2];
					request.setAttribute("Rradio1", Rradio1);
					request.setAttribute("Rradio2", Rradio2);
					request.setAttribute("pkidstrd", pkidqq);
				}
			}
			request.setAttribute("targetListChecked", targetListChecked);
			request.setAttribute("templateTargets", templateTargets);
			request.setAttribute("targetList", targets);
			request.setAttribute("reportId", templateId);
			request.setAttribute("reportId1", modelId);
			request.setAttribute("levelFlag", levelFlag);
			request.setAttribute("template", template);
			request.setAttribute("model", model);

			if ("2".equals(levelFlag)) {
				return mapping.findForward("targesendtUI");
			}
			return mapping.findForward("targetUI");
		}catch(Exception e){
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw, true));
			String str = sw.toString();
			request.setAttribute("errors", str);
			request.setAttribute("errorMessages", e);
			return mapping.findForward("error");
		}
		
	}

	private List getReportTargets(Long reportId) {
		ReportDefineService rs = (ReportDefineService) getBean("rdreportDefineService");
		return rs.getReportTargets(reportId, null);
	}

	/**
	 * 补录模板维护--保存
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveTemplateTargets(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ReportDefineService rs = (ReportDefineService) getBean("rdreportDefineService");
		// 关联关系管理类
		StrdReporeWheconditionService sr = (StrdReporeWheconditionService) this
				.getBean("strdReporeWheconditionService");

		String levelFlag = request.getParameter("levelFlag");
		String templateId = request.getParameter("reportId");
		String modelId = request.getParameter("reportId1");
		// 删除的指标
		String tTargets1 = request.getParameter("tTargets1");
		// 增加的指标
		String tTargets2 = request.getParameter("tTargets2");
		// 关联字段主表ID
		String Rradio1 = request.getParameter("Rradio1");
		// 关联字段从表ID
		String Rradio2 = request.getParameter("Rradio2");
		// 取页面传过来的标示 来判断模型名和模板名是否相等 ,即 标示关联关系是主表或是从表
		String tableas = request.getParameter("tableas");

		String[] targetArr1 = null;
		if (tTargets1 != null && !"".equals(tTargets1)) {
			targetArr1 = tTargets1.split(",");
		}
		String[] targetArr2 = null;
		if (tTargets2 != null && !"".equals(tTargets2)) {
			targetArr2 = tTargets2.split(",");
		}
		rs.saveTemplateTargets(Long.parseLong(templateId),
				Long.parseLong(modelId), targetArr1, targetArr2);
		// ---------- 配置关联关系 ----------
		String wd = sr.getStrdreporeWhecondition(new Long(templateId),
				new Long(modelId));
		String Ro1 = "";
		String Ro2 = "";
		if (!"-".equalsIgnoreCase(wd) && !"not".equals(wd)) {
			String[] strs = wd.split("-"); // a1.NBJGH-a2.NBJGH-122
			String r1 = strs[0];
			String r2 = strs[1];
			Ro1 = r1.substring(r1.indexOf(".") + 1);
			Ro2 = r2.substring(r2.indexOf(".") + 1);
		}
		ReportConfigService rc = (ReportConfigService) this
				.getBean("rdreportConfigService");
		List replist = rc.getReportConfigs(new Long(modelId), new Long(33)); // code_rep_config
		ReportConfig config = null;
		String[] tablelist = null;
		if (replist != null) {
			for (int i = 0; i < replist.size(); i++) {
				config = (ReportConfig) replist.get(0);// 得到表名
			}
		}
		String table = sr.getStrdreporeWhecondition(new Long(templateId));
		if (table != null) { // strd_report_whecondition 表必须有一个tempid存在才能配置关联关系
			if (!"a1".equals(table)) {
				tablelist = table.split("-");
			}
			if ("1".equals(tableas)) {
				table = tablelist[1];
			} else {
				table = tablelist[0];
			}

			String whecondition = tablelist[1] + "." + Rradio1 + "="
					+ tablelist[0] + "." + Rradio2;// a1.NBJGH=a2.NBJGH

			StrdReportWhecondition strdReportWhecondition = new StrdReportWhecondition();// 配置关联关系类
			String pkid = request.getParameter("pkidupdate");
			if (pkid != null && !pkid.equals("")) {
				strdReportWhecondition.setPkid(new Long(pkid));
			}
			strdReportWhecondition.setTempid(new Long(templateId));// 模板id
			strdReportWhecondition.setModeid(new Long(modelId)); // 模型id
			strdReportWhecondition.setIsmaintable(tableas); // 是否是主表 1 为主表 2 为从表
			strdReportWhecondition.setTable(table); // 表别名
			strdReportWhecondition.setTablename(config.getDefChar()); // 表名
			if ("2".equals(tableas)) {
				strdReportWhecondition.setWhecondition(whecondition); // 关联关系
			} else {
				strdReportWhecondition.setWhecondition(" ");// 同表为空
			}

			if (Rradio1 != null && !Rradio1.equals("") && Rradio2 != null
					&& !Rradio2.equals("")) { // 关联关系 等号任意一边为空或null就不保存
				if (!Ro1.trim().equals(Rradio1.trim())
						|| !Ro2.trim().equals(Rradio2.trim())) { // 当关联关已存在并且与新配置的关联关系不相等时保存
					sr.saveStrdreporeWhecondition(strdReportWhecondition); // 保存
				}
			}
		}
		return listTarget(mapping, form, request, response);
	}

	/**
	 * 报送模板维护--列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward enterTargetSend(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'enterTargetSend' method");
		}
		request.setAttribute("reportId", 10000);
		request.setAttribute("reportId1", 10000);
		return mapping.findForward("targesendtUI");
	}

	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'cancel' method");
		}
		return list(mapping, form, request, response);
	}

	// 关联关系删除
	public ActionForward delstrdReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'delstrdReport' method");
		}
		String levelFlag = request.getParameter("levelFlag");
		String templateId = request.getParameter("reportId");
		String modelId = request.getParameter("reportId1");
		if (templateId != null && !templateId.equals("") && modelId != null
				&& !modelId.equals("")) {
			StrdReporeWheconditionService sr = (StrdReporeWheconditionService) this
					.getBean("strdReporeWheconditionService");
			sr.delStrdreporeWhecondition(new Long(templateId),
					new Long(modelId));
		}

		request.setAttribute("levelFlag", levelFlag);
		return listTarget(mapping, form, request, response);
	}

	public ActionForward delTemplateTarget(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'delTemplateTarget' method");
		}
		ReportDefineService rs = (ReportDefineService) getBean("rdreportDefineService");
		String templateId = request.getParameter("reportId");
		String modelId = request.getParameter("reportId1");
		String targetId = request.getParameter("targetId");
		rs.delTemplateTarget(Long.parseLong(templateId),
				Long.parseLong(modelId), targetId);
		return listTarget(mapping, form, request, response);
	}

}
