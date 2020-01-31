package com.krm.ps.model.reportrule.web.action;

import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.model.reportrule.bo.ReportTable;
import com.krm.ps.model.reportrule.services.ReportDefineService;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportItem;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;
import com.krm.ps.sysmanage.usermanage.services.DictionaryService;
import com.krm.ps.util.ConvertUtil;
import com.krm.ps.util.FuncConfig;

public class IntegratedQueryAction extends BaseAction {

	ReportDefineService rds = (ReportDefineService) getBean("reportruleReportDefineService");

	public ActionForward targetTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'targetTree' method");
		}

		String repIdStr = request.getParameter("reportId");
		String reportName = request.getParameter("reportName");
		String stocktype = request.getParameter("stocktype");
		// reportName = new String(reportName.getBytes("ISO-8859-1"),"UTF-8");
		Long reportId = Long.valueOf(repIdStr);
		ReportTable rt = null;
		if (stocktype != null && "2".equals(stocktype)) {
			rt = getReportTableStock(request, reportId, stocktype);
		} else {
			rt = getReportTable(request, reportId);
		}
		String[][] targets = rt.getTargets();
		// 目前这里得到栏时，并没有进行时间的过滤，也就是对栏是否有效没有进行判断
		// 这里加入此过滤，如果想对时间过滤可以在request请求参数中加入
		// filterItems：参数，其为1时，会进行有效性的过滤
		// filterDates：需要过滤的日期要通过此参数传入
		String filterItems = request.getParameter("filterItems");
		String filterDatesStr = request.getParameter("filterDates");
		if (filterItems != null && "1".equals(filterItems)) // 过滤栏参数为1，对栏进行过滤
		{
			targets = filterInvalidItemsOrTargets(targets, filterDatesStr);
		}
		String[] cnames = new String[] { "targetCode", "targetName",
				"targetOrder", "reportId" };
		List list1 = buildTargetTreeList(reportId, rt.getReportName(), targets);
		if(FuncConfig.isOpend("oldTarget")){
			String adoXml = ConvertUtil.convertListToAdoXml(list1, cnames);
			 DictionaryService dic =getDictionaryService(); dic.getDics(1004);
			 request.setAttribute("treeXml", adoXml); return
			 mapping.findForward("tree");
		}else{
			String adoXml = getTargetxml(list1);
			JSONObject json = new JSONObject();
			json.put("treeXml", adoXml);
			request.setAttribute("treeXml", adoXml);
			String dateJson = JSONArray.fromObject(json).toString();
			response.setContentType("text/html;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter writer = response.getWriter();
			writer.write(dateJson);
			writer.close();

		return null;
		}
	}

	private String getTargetxml(List list) {
		StringBuffer targetxml = new StringBuffer();
		targetxml.append("[{");
		targetxml.append("\"id\":\""
				+ ((ReportTarget) list.get(0)).getTargetCode() + "\",");
		targetxml.append("\"text\":\""
				+ ((ReportTarget) list.get(0)).getTargetName() + "\"");
		if (list.size() > 0) {
			targetxml.append(",\"state\":\"closed\"");
			targetxml.append(",\"children\":[");
			for (int i = 1; i < list.size(); i++) {
				if (i == 1) {
					targetxml.append("{");
				} else {
					targetxml.append(",{");
				}
				targetxml.append("\"id\":\""
						+ ((ReportTarget) list.get(i)).getTargetCode() + "\",");
				targetxml.append("\"text\":\""
						+ ((ReportTarget) list.get(i)).getTargetName() + "\"");
				targetxml.append("}");
			}
			targetxml.append("]");
		}
		targetxml.append("}]");
		System.out.println(targetxml.toString());
		return targetxml.toString();
	}

	/**
	 * 为了区分债券和股权重写的方法
	 * 
	 * @param request
	 * @param reportId
	 * @return
	 */
	private ReportTable getReportTableStock(HttpServletRequest request,
			Long reportId, String stocktype) {
		return getReportTableStock(request, reportId, false, stocktype);
	}

	private ReportTable getReportTable(HttpServletRequest request, Long reportId) {
		return getReportTable(request, reportId, false);
	}

	private ReportTable getReportTable(HttpServletRequest request,
			Long reportId, boolean newReportTable) {
		ReportTable rt = null;
		Map reportTableMap = (Map) request.getSession().getAttribute(
				"reportTableMap2");// without formula
		if (newReportTable || reportTableMap == null) {
			reportTableMap = new HashMap();
			request.getSession()
					.setAttribute("reportTableMap2", reportTableMap);
		} else {
			rt = (ReportTable) reportTableMap.get(reportId);
		}
		if (reportId == 812 || rt == null) {
			rt = getSingleReportTable(request, reportId);
			reportTableMap.put(reportId, rt);
		}
		return rt;
	}

	/**
	 * 取一条报表信息
	 * 
	 * @param request
	 * @param reportId
	 * @return
	 */
	private ReportTable getSingleReportTable(HttpServletRequest request,
			Long reportId) {

		Report report = rds.getReport(reportId);
		List reportItemsList = rds.getReportItems(reportId);

		List reportTargetsList = rds.getReportTargets(reportId);

		String[][] items = new String[reportItemsList.size()][4];
		String[][] targets = new String[reportTargetsList.size()][4];
		int intItems = 0;
		int intTargets = 0;
		for (Iterator itItems = reportItemsList.iterator(); itItems.hasNext();) {
			ReportItem reportItem = (ReportItem) itItems.next();
			items[intItems][0] = reportItem.getCode();
			items[intItems][1] = reportItem.getItemName();
			// piliang add comment for reading 2010-3-23 上午09:34:02
			// 这里由于有过滤科目的需要，目前把科目的开始和结束日期也加入
			items[intItems][2] = reportItem.getBeginDate();
			items[intItems][3] = reportItem.getEndDate();
			intItems++;
		}
		for (Iterator itTargets = reportTargetsList.iterator(); itTargets
				.hasNext();) {
			ReportTarget reportTarget = (ReportTarget) itTargets.next();
			targets[intTargets][0] = reportTarget.getTargetField();
			targets[intTargets][1] = reportTarget.getTargetName();
			// piliang add comment for reading 2010-3-23 上午10:23:33
			// 同样为了过滤栏有效性加入此设置，把栏的开始结束日期加入
			targets[intTargets][2] = reportTarget.getBeginDate();
			targets[intTargets][3] = reportTarget.getEndDate();
			intTargets++;
		}
		ReportTable reportTable = new ReportTable(report.getPkid().toString(),
				report.getName(), items, targets);

		return reportTable;
	}

	/**
	 * 区分债券和股权重写的方法
	 * 
	 * @param request
	 * @param reportId
	 * @param newReportTable
	 * @return
	 */
	private ReportTable getReportTableStock(HttpServletRequest request,
			Long reportId, boolean newReportTable, String stocktype) {
		ReportTable rt = null;
		Map reportTableMap = (Map) request.getSession().getAttribute(
				"reportTableMap2");// without formula
		if (newReportTable || reportTableMap == null) {
			reportTableMap = new HashMap();
			request.getSession()
					.setAttribute("reportTableMap2", reportTableMap);
		} else {
			rt = (ReportTable) reportTableMap.get(reportId);
		}
		if (reportId == 812 || rt == null) {
			rt = getSingleStockTable(request, reportId, stocktype);
			reportTableMap.put(reportId, rt);
		}
		return rt;
	}

	/**
	 * Ϊ为了区分债券，股权重写的方法
	 * 
	 * @param request
	 * @param reportId
	 * @return
	 */
	private ReportTable getSingleStockTable(HttpServletRequest request,
			Long reportId, String stocktype) {

		Report report = rds.getReport(reportId);
		List reportItemsList = rds.getReportItems(reportId);
		List reportTargetsList = rds.getReportTargetsStock(reportId, stocktype);

		String[][] items = new String[reportItemsList.size()][4];
		String[][] targets = new String[reportTargetsList.size()][4];
		int intItems = 0;
		int intTargets = 0;
		for (Iterator itItems = reportItemsList.iterator(); itItems.hasNext();) {
			ReportItem reportItem = (ReportItem) itItems.next();
			items[intItems][0] = reportItem.getCode();
			items[intItems][1] = reportItem.getItemName();
			// piliang add comment for reading 2010-3-23 上午09:34:02
			// 这里由于有过滤科目的需要，目前把科目的开始和结束日期也加入
			items[intItems][2] = reportItem.getBeginDate();
			items[intItems][3] = reportItem.getEndDate();
			intItems++;
		}
		for (Iterator itTargets = reportTargetsList.iterator(); itTargets
				.hasNext();) {
			ReportTarget reportTarget = (ReportTarget) itTargets.next();
			targets[intTargets][0] = reportTarget.getTargetField();
			targets[intTargets][1] = reportTarget.getTargetName();
			// piliang add comment for reading 2010-3-23 上午10:23:33
			// 同样为了过滤栏有效性加入此设置，把栏的开始结束日期加入
			targets[intTargets][2] = reportTarget.getBeginDate();
			targets[intTargets][3] = reportTarget.getEndDate();
			intTargets++;
		}
		ReportTable reportTable = new ReportTable(report.getPkid().toString(),
				report.getName(), items, targets);

		return reportTable;
	}

	private List buildTargetTreeList(Long reportId, String reportName,
			String[][] targets) {

		List targetList = new ArrayList();
		if (targets.length == 0) // 没有指定报表的栏
			return targetList;
		ReportTarget root = new ReportTarget();
		root.setTargetCode("10000");
		root.setTargetName(reportName);
		root.setTargetOrder(new Integer(0));
		root.setReportId(new Long(-1));
		targetList.add(root);

		for (int i = 0; i < targets.length; i++) {
			String[] target = targets[i];
			ReportTarget ti = new ReportTarget();
			ti.setTargetCode(target[0]);
			ti.setTargetName(target[1]);
			ti.setTargetOrder(new Integer(i + 1));
			ti.setReportId(new Long(10000));
			targetList.add(ti);
		}

		return targetList;
	}

	/**
	 * <p>
	 * 过滤无效的科目或栏
	 * </p>
	 * 
	 * @param itemsOrTargets
	 *            要进行过滤的科目或栏信息：格式［［ , ,开始时间,结束时间］...］ 也就是需要下标为2，3时分别对应开始及结束日期
	 * @param filterDatesStr
	 *            需要过滤的日期 如：20090909,20091009
	 * @return 过滤后的科目或栏
	 * @author 皮亮
	 * @version 创建时间：2010-3-23 上午10:38:07
	 */
	private String[][] filterInvalidItemsOrTargets(String[][] itemsOrTargets,
			String filterDatesStr) {
		// 需要过滤的日期可以是单个，也可是多个，多个时需用,号隔开
		// validItemsList：加入此List主要是为了把有效的科目或栏加入这里
		List validItemsList = new ArrayList();
		String[] filterDates = filterDatesStr.split(",");
		int itemsSize = itemsOrTargets.length;
		int filterDatesSize = filterDates.length;
		boolean isValid = true;
		String tempDate = "";
		for (int i = 0; i < itemsSize; i++) {
			for (int j = 0; j < filterDatesSize; j++) {
				tempDate = filterDates[j].replaceAll("-", "");
				if (!(tempDate.compareTo(itemsOrTargets[i][2]) > 0 && tempDate
						.compareTo(itemsOrTargets[i][3]) < 0)) // ���ڲ��ڿ�Ŀ����Чʱ�����
				{
					isValid = false;
					break;
				}
			}
			if (isValid)
				validItemsList.add((String[]) itemsOrTargets[i]);
			isValid = true;
		}
		itemsOrTargets = new String[validItemsList.size()][4];
		validItemsList.toArray(itemsOrTargets);
		return itemsOrTargets;
	}

}
