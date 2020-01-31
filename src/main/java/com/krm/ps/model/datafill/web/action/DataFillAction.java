package com.krm.ps.model.datafill.web.action;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.model.datafill.services.DataFillService;
import com.krm.ps.model.datafill.services.FromValidatorService;
import com.krm.ps.model.datafill.services.OrganService;
import com.krm.ps.model.datafill.services.ReportConfigService;
import com.krm.ps.model.datafill.services.ReportDefineService;
import com.krm.ps.model.datafill.services.ReportMoveVerifyService;
import com.krm.ps.model.datafill.services.ReportRuleService;
import com.krm.ps.model.datafill.vo.StatusForm;
import com.krm.ps.model.datafill.web.form.ReportDataForm;
import com.krm.ps.model.sysLog.services.SysLogService;
import com.krm.ps.model.sysLog.util.LogUtil;
import com.krm.ps.model.vo.DicItem;
import com.krm.ps.model.vo.FromValidator;
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
import com.krm.ps.util.DateUtil;
import com.krm.ps.util.FuncConfig;

public class DataFillAction extends BaseAction {
	private static String BTYPE_LIST = "reportTargetList";

	ReportConfigService rcs = (ReportConfigService) getBean("datafillReportConfigService");
	ReportDefineService rds = (ReportDefineService) getBean("datafillReportDefineService");
	OrganService os = (OrganService) getBean("datafillOrganService");
	DataFillService dfs = (DataFillService) getBean("datafillDataFillService");
	ReportRuleService rrs = (ReportRuleService) getBean("datafillReportRuleService");
	FromValidatorService fvs = (FromValidatorService) getBean("datafillFromValidatorService");
	SysLogService sls = (SysLogService) getBean("syslogService");
	ReportMoveVerifyService rmv =(ReportMoveVerifyService)getBean("reportMoveVerifyService");

	/**
	 * 状态查询页面点数据补录进入此方法
	 * 
	 * @param mappingoeditReportDataEntryForDataInput
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public ActionForward editReportDataEntryForDataInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException {
		if (log.isDebugEnabled()) {
			log.info("Entering Standard_DataGather 'editReportDataEntryForDataInput' method");
		}
		String dataId = request.getParameter("dataId");
		String levelFlag = request.getParameter("levelFlag");
		ReportDataForm reportDataForm = (ReportDataForm) form;
		String openMode = request.getParameter("openmode");
		String flag = request.getParameter("flag");
		String flagtap = request.getParameter("flagtap");
		request.setAttribute("flagtap", flagtap);

		int idxid = getUser(request).getOrganTreeIdxid();
		String reportDate = reportDataForm.getDataDate(); // 3
		if (reportDate == null) {
			reportDate = request.getParameter("reportDate");
			reportDataForm.setDataDate(reportDate);
		}
		// Long reportId = reportDataForm.getReportId();
		// if (reportId == null) {
		//从工作指引→搜索→数据补录 链接进入的到的 reportId 是模型莫modeid需要转换成tempid 才可使用
		//从业务数据补录→数据补录 进入则不需要转化
		Long reportId = new Long(request.getParameter("reportId"));
		reportDataForm.setReportId(reportId);
		// }

		String flag2 = request.getParameter("flag2");
		if ("1".equals(flag2)) {
			reportId = dfs.getreportId(reportId);
			reportDataForm.setReportId(reportId);
		}
		List<ReportConfig> resultablename = rcs.getdefCharByTem(reportId,new Long(34));
		String canEdit = request.getParameter("canEdit");
		/*
		 * String resultablename1 = resultablename.get(0).getDefChar();
		 * 
		 * request.setAttribute("resultablename1", resultablename1);
		 */

		List reportTargetList1 = rds.getReportTargets(reportId);
		String organCode = reportDataForm.getOrganId();
	   if (organCode == null) {
		 organCode = request.getParameter("organCode");
		 reportDataForm.setOrganId(organCode);
		 }
	   //---------- 加入组合查询的参数----------------------------
		String zhi1=request.getParameter("zhi1");
		String zhi2=request.getParameter("zhi2");
		String zhi3=request.getParameter("zhi3");
		String tField1 = request.getParameter("tField1");
		String tField2 = request.getParameter("tField2");
		String tField3 = request.getParameter("tField3");
		//----------------------------------
		Map<Long, List<DicItem>> dicMap = getDicMap(
				reportDate.replaceAll("-", ""), reportTargetList1);
		request.setAttribute("dicMap", dicMap);

		OrganInfo organInfo = os.getOrganByCode(organCode);
		request.setAttribute("organInfo", organInfo);
		User user = (User) request.getSession().getAttribute("user");
		reportDataForm.setFillUser(user.getName());
		reportDataForm.setPhone("");
		reportDataForm.setCheckUser(user.getName());
		Page page = getPage(request);
		String pagesize = request.getParameter("pageperno");
		if (pagesize != null && pagesize.matches("^\\d+$")) {
			page.setPageSize(Integer.parseInt(pagesize));
		}
		List reportDataList1 = new ArrayList();
		String cstatus = "'0','3','5'";
		if (dataId == null || "".equals(dataId)) {
			List mapColumn = dfs.getMapColumn();
			log.info(mapColumn);
			Map mapC = new HashMap();
			for (int i = 0; i < mapColumn.size(); i++) {
				MapColumn mapcolumn = (MapColumn) mapColumn.get(i);
				mapC.put(mapcolumn.getResourceColumn(),
						mapcolumn.getTargetColumn());
			}
			if (mapC.isEmpty()) {
				mapC.put("pkid", "pkid");
				mapC.put("report_date", "report_date");
				mapC.put("organ_id", "organ_id");
			}
			try {

				reportDataList1 = dfs.getReportDataAll1(null, cstatus,
						user.getIsAdmin(), resultablename, reportId,
						reportDataForm.getOrganId(),
						reportDate.replaceAll("-", ""), reportTargetList1,
						page, idxid, levelFlag, zhi1,tField1,
						zhi2,tField2,
						zhi3,tField3, mapC);
			} catch (Exception e) {
				/*
				 * List
				 * <ReportConfig>reptablename=rcs.getdefCharByTem(reportId,new
				 * Long(33)); String rtable=""; for(ReportConfig
				 * rc:reptablename){ rtable=rc.getDefChar();
				 * rtable=rtable.replaceAll
				 * ("\\{Y\\}",reportDate.replaceAll("-","").substring(0,4));
				 * rtable
				 * =rtable.replaceAll("\\{M\\}",reportDate.replaceAll("-",""
				 * ).substring(4,6));
				 * rtable=rtable.replaceAll("\\{D\\}",reportDate
				 * .replaceAll("-","").substring(6,8)); }
				 * request.setAttribute("rtable",rtable);
				 */
				// e.printStackTrace();
				StringWriter sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw, true));
				String str = sw.toString();
				request.setAttribute("errors", str);
				request.setAttribute("errorMessages", e);
				return mapping.findForward("error");
				// return mapping.findForward("editUI2bug");
			}
		}
		Map repDataMap = new HashMap();
		if (reportDataList1 != null && reportDataList1.size() > 0) {
			repDataMap = (Map) reportDataList1.get(0);
		}

		List idList = new ArrayList();
		for (Object e : repDataMap.keySet()) {
			String idStr = (String) e;
			String id = idStr.substring(0, idStr.indexOf("_"));
			idList.add(id);
		}
		String targetids = "";
		for (int a = 0; a < reportTargetList1.size(); a++) {
			ReportTarget rta = (ReportTarget) reportTargetList1.get(a);
			targetids += "'" + rta.getTargetField() + "',";
		}
		List<ReportResult> resultList = rrs.getReportResultByDataId(cstatus,
				idList, reportDate, reportDataList1.get(3).toString(),
				targetids.substring(0, targetids.length() - 1));

		Map<String, Map> checkResult = getCheckResultYJH(repDataMap, reportId,
				resultList);
		if (checkResult.containsKey("pcheckMap0")) {
			request.setAttribute("pcheckMap", checkResult.get("pcheckMap0"));
		}
		if (checkResult.containsKey("rvalueMap0")) {
			request.setAttribute("rvalueMap", checkResult.get("rvalueMap0"));
		}
		if (checkResult.containsKey("pflagMap0")) {
			request.setAttribute("pflagMap", checkResult.get("pflagMap0"));
		}
		if (checkResult.containsKey("valueMap0")) {
			request.setAttribute("valueMap", checkResult.get("valueMap0"));
		}
		if (checkResult.containsKey("ruleMap0")) {
			request.setAttribute("ruleMap", checkResult.get("ruleMap0"));
		}
		if (checkResult.containsKey("dtypeMap0")) {
			request.setAttribute("dtypeMap", checkResult.get("dtypeMap0"));
		}
		if (checkResult.containsKey("repDataMap0")) {
			request.setAttribute("repDataMap", checkResult.get("repDataMap0"));
		}

		request.setAttribute("reportTargetList", reportTargetList1);

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

			reportId = dfs.getreportId(reportId, "1");
			List fromVlidatList = fvs.listFromValidator(reportId.toString());
			JSONObject jsonObject = new JSONObject();
			JSONArray jsonArray = new JSONArray();
			String targerfieldold = null;
			for (int i = 0; i < fromVlidatList.size(); i++) {
				FromValidator fromValidator = (FromValidator) fromVlidatList
						.get(i);
				if (i == 0) {
					targerfieldold = fromValidator.getTarget_field();
				}
				if (targerfieldold.equals(fromValidator.getTarget_field())) {
					jsonArray.add(fromValidator);
				} else {
					jsonObject.put(targerfieldold, jsonArray.toString());
					targerfieldold = fromValidator.getTarget_field();
					jsonArray = new JSONArray();
					jsonArray.add(fromValidator);
				}
				if (i == fromVlidatList.size() - 1) {
					jsonObject.put(targerfieldold, jsonArray.toString());
				}

			}
			request.setAttribute("jsonObjectFrom", jsonObject);

		}
		request.setAttribute("openMode", openMode);
		request.getSession().setAttribute("dataDate",
				reportDataForm.getDataDate());
		request.getSession().setAttribute("reportId",
				reportDataForm.getReportId().toString());
		request.getSession().setAttribute("organId",
				reportDataForm.getOrganId());
		Report r = rds.getReport(reportId);
		request.setAttribute("reportName", r.getName());
		request.setAttribute("resultablename1", "REP_RESULT_" + r.getCode());
		if ("submitAll".equals(request.getParameter("enterMode"))) {
			request.setAttribute("continueSubmit", "yes");
		} else {
			request.setAttribute("continueSubmit", "no");
		}
		reportDataForm.setTargetField1(tField1);
		reportDataForm.setTargetField2(tField2);
		reportDataForm.setTargetField3(tField3);
		request.setAttribute("tField1", tField1);
		request.setAttribute("tField2", tField2);
		request.setAttribute("tField3", tField3);
		request.setAttribute("zhi1", zhi1);
		request.setAttribute("zhi2", zhi2);
		request.setAttribute("zhi3", zhi3);
		request.setAttribute("relateFlag", flag);
		request.setAttribute("dataId", dataId);
		request.setAttribute("levelFlag", levelFlag);
		updateFormBean(mapping, request, reportDataForm);
		if ("0".equals(canEdit)) {
			return mapping.findForward("queryUI");
		}
		return mapping.findForward("editUI2");
	}

	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward exportReportData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String valstr = request.getParameter("valstr");
		// String valstrcode = new String(valstr.getBytes(),"utf-8");
		// String valstrcode1 = new String(valstr.getBytes(),"gbk");
		String idarr = request.getParameter("idarr");

		Long reportId = Long.parseLong(request.getParameter("reportId"));
		List<ReportConfig> resultablename = rcs.getdefCharByTem(reportId,
				new Long(34));
		String name = resultablename.get(0).toString();
		String reportName = name.substring(name.lastIndexOf(",") + 1);
		// String path =
		// System.getProperty("java.io.tmpdir")+File.separator+"retpacket";
		String path = FuncConfig.getProperty("create.excel.filepath");
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}
		// String outputFile = path+File.separator+reportName+"_"+new
		// SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+".xls";
		String outputFile = path + "/" + reportName + "_"
				+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
				+ ".xls";
		String filename = reportName + "_"
				+ new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
				+ ".xls";
		log.info(outputFile);

		HSSFWorkbook workbook = new HSSFWorkbook();

		HSSFSheet sheet = workbook.createSheet("Sheet1");
		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 11);
		font.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);

		HSSFCellStyle style = (HSSFCellStyle) workbook.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		style.setFillForegroundColor(HSSFColor.RED.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setLocked(true);
		style.setFont(font);
		HSSFRow row;
		row = sheet.createRow((short) 0);
		HSSFCell cell;
		List<ReportTarget> reportTargetList1 = rds.getReportTargets(reportId);
		cell = row.createCell((short) 0);
		cell.setCellValue("PKID");
		cell.setCellStyle(style);

		for (int i = 0; i < reportTargetList1.size(); i++) {
			cell = row.createCell((short) i + 1);
			// CellStyle style =cell.getCellStyle();
			String targetName = reportTargetList1.get(i).getTargetName();
			cell.setCellValue(targetName);
			cell.setCellStyle(style);
			// setDataValidation(cell);
			// style.setLocked(true);
		}

		String valstrlen[] = idarr.split(",");
		String valstrva = null;
		log.info(valstr);
		// int valstrlen = Integer.parseInt(valstr.split("@").toString());
		for (int i = 0; i < valstrlen.length; i++) {// hang
			valstrva = valstr.split("@")[i];
			log.info(valstrva);
			String valstrvalen[] = valstrva.split(",");
			row = sheet.createRow((short) i + 1);//
			for (int j = 0; j < valstrvalen.length; j++) {// lie
				if (j == 0) {
					row.createCell((short) 0).setCellValue(idarr.split(",")[i]);
				}
				cell = row.createCell((short) j + 1);
				cell.setCellValue(valstrva.split(",")[j]);
			}
			valstrva = "";
		}

		FileOutputStream fOut;
		fOut = new FileOutputStream(outputFile);
		workbook.write(fOut);

		fOut.flush();
		fOut.close();

		downloadExcel(filename, outputFile, request, response);
		return null;
	}

	public ActionForward downloadExcel(String filename, String outputFile,
			HttpServletRequest request, HttpServletResponse response) {
		/*
		 * try { //String fileName = new
		 * String(filename.getBytes("gb2312"),"iso8859-1");
		 * response.setHeader("content-disposition", "attachment; filename="+
		 * filename); response.setHeader("Content-Type",
		 * "application/vnd.ms-excel; charset=UTF-8"); } catch (Exception e1) {
		 * e1.printStackTrace(); } OutputStream sos = null; try { sos =
		 * response.getOutputStream(); sos.write(downLoadExcel(outputFile));
		 * sos.flush(); } catch (Exception e) { e.printStackTrace(); } finally {
		 * try { sos.close(); } catch (IOException e) { e.printStackTrace(); } }
		 */
		/*
		 * OutputStream out = null; try { response.reset();
		 * response.setContentType("application/x-download"); filename =
		 * java.net.URLEncoder.encode(filename, "UTF-8");
		 * //response.addHeader("Content-Disposition", "attachment;filename="+
		 * filename); response.setHeader("content-disposition",
		 * "attachment; filename="+ filename);
		 * response.setHeader("Content-Type",
		 * "application/vnd.ms-excel; charset=UTF-8"); out =
		 * response.getOutputStream();
		 * 
		 * InputStream is = null; is = new FileInputStream(outputFile); byte[] b
		 * = new byte[is.available()]; if(b.length>0){ is.read(b); } if (b !=
		 * null) { out.write(b); out.flush(); //response.reset();
		 * 
		 * } }catch (Exception ex) { ex.printStackTrace(); } finally { if (out
		 * != null) { try { out.close(); } catch (IOException e) {
		 * e.printStackTrace(); } out = null; } }
		 */
		OutputStream out = null;//
		InputStream in = null;
		try {
			response.reset();
			response.setHeader("content-disposition", "attachment; filename="
					+ filename);
			response.setContentType("application/vnd.ms-excel");
			/*
			 * response.addHeader("Content-Disposition", "attachment;filename="+
			 * filename);
			 */
			out = response.getOutputStream();
			outputFile = FuncConfig.getProperty("create.excel.filepath") + "/"
					+ filename;
			log.info(outputFile);
			in = new FileInputStream(outputFile);
			byte[] tempbytes = new byte[1024];
			int byteread = 0;
			while ((byteread = in.read(tempbytes)) != -1) {
				out.write(tempbytes, 0, byteread);
			}
			out.flush();
			response.reset();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}

		return null;
	}

	public static byte[] downLoadExcel(String outputFile) {
		InputStream fis = null;
		byte[] buffer = null;
		try {
			fis = new BufferedInputStream(new FileInputStream(outputFile));
			buffer = new byte[fis.available()];
			fis.read(buffer);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return buffer;
	}

	public ActionForward openattach(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		return mapping.findForward("openattach");

	}

	/*
	 * public ActionForward uploadExcel(ActionMapping mapping, ActionForm form,
	 * HttpServletRequest request, HttpServletResponse response){ excelForm fm =
	 * (excelForm) form;
	 * 
	 * 
	 * 
	 * ArrayList list = new ArrayList();
	 * 
	 * 
	 * return null; }
	 */

	/**
	 * 数据补录 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward getReportgjcx(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		if (log.isDebugEnabled()) {
			log.info("Entering Standard_DataGather 'getReportgjcx' method");
		}
		//--------------------------------
		String zhi1=request.getParameter("zhi1");
				if(zhi1!=null){
					zhi1=zhi1.trim();
				 }
		String tField1 = request.getParameter("targetField1");
		//--------------------------------
		String zhi2=request.getParameter("zhi2");
		if(zhi2!=null){
			zhi2=zhi2.trim();
		 }
		String tField2 = request.getParameter("targetField2");
		//--------------------------------
		String zhi3=request.getParameter("zhi3");
		if(zhi3!=null){
			zhi3=zhi3.trim();
		 }
		String tField3 = request.getParameter("targetField3");
		//--------------------------------
		String orgId = request.getParameter("orgid");
		String dataId = request.getParameter("dataId");
		//System.out.println("dataId==="+dataId);
		
		String levelFlag = request.getParameter("levelFlag");
		ReportDataForm reportDataForm = (ReportDataForm) form;
		reportDataForm.setOrganId(orgId);
		String openMode = request.getParameter("openmode");
		String flag = request.getParameter("flag");
		int idxid = getUser(request).getOrganTreeIdxid();
		String reportDate = reportDataForm.getDataDate(); // 3
		if (reportDate == null) {
			reportDate = request.getParameter("reportDate");
			reportDataForm.setDataDate(reportDate);
		}
		//String aaa=request.getParameter("reportId");
		//System.out.println("---"+aaa);
		Long reportId = reportDataForm.getReportId();

		if (reportId == null) {
			reportId = new Long(request.getParameter("reportId"));
			reportDataForm.setReportId(reportId);
		}
		
		//从工作指引→搜索→数据补录 链接进入的到的 reportId 是模型莫modeid需要转换成tempid 才可使用
		//从业务数据补录→数据补录 进入则不需要转化
		reportId=dfs.getreportId(new Long(reportId), "0");//转换补录模板
		reportDataForm.setReportId(reportId);
//		String flag2 = request.getParameter("flag2");
//		if ("1".equals(flag2)) {
//			reportId = dfs.getreportId(reportId);
//			reportDataForm.setReportId(reportId);
//		}
		List<ReportConfig> resultablename = rcs.getdefCharByTem(reportId,
				new Long(34));
		String canEdit = request.getParameter("canEdit");

		// 获得模板的栏
		List reportTargetList1 = rds.getReportTargets(reportId);
		String organCode = reportDataForm.getOrganId();
		if (organCode == null) {
			organCode = request.getParameter("organCode");
			reportDataForm.setOrganId(organCode);
		}

		// 查询需要显示select列表的字典项信息
		// dicMap用来维护target项和字典列表的关系
		Map<Long, List<DicItem>> dicMap = getDicMap(
				reportDate.replaceAll("-", ""), reportTargetList1);
		request.setAttribute("dicMap", dicMap);

		OrganInfo organInfo = os.getOrganByCode(organCode);
		request.setAttribute("organInfo", organInfo);
		User user = (User) request.getSession().getAttribute("user");
		reportDataForm.setFillUser(user.getName());
		reportDataForm.setPhone("");
		reportDataForm.setCheckUser(user.getName());
		Page page = getPage(request);
		String pagesize = request.getParameter("pageperno");
		if (pagesize != null && pagesize.matches("^\\d+$")) {
			page.setPageSize(Integer.parseInt(pagesize));
		}
		List reportDataList1 = new ArrayList();
		String cstatus = "'0','3','5'"; //状态为未补录，待修订，已保存状态时
		if (dataId == null || "".equals(dataId)) {
			//根据模板以及关联关系获得页面要展示的数据,---20140708添加上字段映射
			List mapColumn = dfs.getMapColumn();
			log.info(mapColumn);
			Map mapC = new HashMap();
			for (int i = 0; i < mapColumn.size(); i++) {
				MapColumn mapcolumn = (MapColumn) mapColumn.get(i);
				mapC.put(mapcolumn.getResourceColumn(),
						mapcolumn.getTargetColumn());
			}
			if (mapC.isEmpty()) {
				mapC.put("pkid", "pkid");
				mapC.put("report_date", "report_date");
				mapC.put("organ_id", "organ_id");
			}
			reportDataList1 = dfs.getReportDataAllMapColumn(null, cstatus,
					user.getIsAdmin(), resultablename, reportId,
					reportDataForm.getOrganId(),
					reportDate.replaceAll("-", ""), reportTargetList1, page,
					idxid, levelFlag, zhi1, tField1, zhi2, tField2, zhi3, tField3, mapC);
			//System.out.println("reportDataList1=="+reportDataList1.size());
		}

		Map repDataMap = new HashMap();
		if (reportDataList1 != null && reportDataList1.size() > 0) {
			repDataMap = (Map) reportDataList1.get(0);
		}

		//获昨结果数据集
		List idList = new ArrayList();
		for (Object e : repDataMap.keySet()) {
			String idStr = (String) e;
			String id = idStr.substring(0, idStr.indexOf("_"));
			idList.add(id);
		}
		String targetids = "";
		for (int a = 0; a < reportTargetList1.size(); a++) {
			ReportTarget rta = (ReportTarget) reportTargetList1.get(a);
			targetids += "'" + rta.getTargetField() + "',";
		}
		List<ReportResult> resultList = rrs.getReportResultByDataId(cstatus,
				idList, reportDate, reportDataList1.get(3).toString(),
				targetids.substring(0, targetids.length() - 1));

		// 错误信息的构建
		Map<String, Map> checkResult = getCheckResultYJH(repDataMap, reportId,
				resultList);
		if (checkResult.containsKey("pcheckMap0")) {
			request.setAttribute("pcheckMap", checkResult.get("pcheckMap0")); //存入页面校验脚本
		}
		if (checkResult.containsKey("rvalueMap0")) {
			request.setAttribute("rvalueMap", checkResult.get("rvalueMap0"));//存入参考值校验脚本
		}
		if (checkResult.containsKey("pflagMap0")) {
			request.setAttribute("pflagMap", checkResult.get("pflagMap0"));//存入校验类型
		}
		if (checkResult.containsKey("valueMap0")) {
			request.setAttribute("valueMap", checkResult.get("valueMap0"));//放参考值
		}
		if (checkResult.containsKey("ruleMap0")) {
			request.setAttribute("ruleMap", checkResult.get("ruleMap0"));
		}
		if (checkResult.containsKey("dtypeMap0")) {
			request.setAttribute("dtypeMap", checkResult.get("dtypeMap0"));
		}
		if (checkResult.containsKey("repDataMap0")) {
			request.setAttribute("repDataMap", checkResult.get("repDataMap0"));
		}

		request.setAttribute(BTYPE_LIST, reportTargetList1);
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
		request.setAttribute("openMode", openMode);
		request.getSession().setAttribute("dataDate",
				reportDataForm.getDataDate());
		request.getSession().setAttribute("reportId",
				reportDataForm.getReportId().toString());
		request.getSession().setAttribute("organId",
				reportDataForm.getOrganId());
		// 加入报表名称，显示到页面             
		Report r = rds.getReport(reportId);
		request.setAttribute("reportName", r.getName());
		if ("submitAll".equals(request.getParameter("enterMode"))) {
			request.setAttribute("continueSubmit", "yes");
		} else {
			request.setAttribute("continueSubmit", "no");
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
		
		request.setAttribute("relateFlag", flag);
		request.setAttribute("dataId", dataId);
		request.setAttribute("levelFlag", levelFlag);
		request.setAttribute("tField1", tField1);
		request.setAttribute("tField2", tField2);
		request.setAttribute("tField3", tField3);
		reportId = dfs.getreportId(reportId, "0");
		List fromVlidatList = fvs.listFromValidator(reportId.toString());
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		String targerfieldold = null;
		for (int i = 0; i < fromVlidatList.size(); i++) {
			FromValidator fromValidator = (FromValidator) fromVlidatList
					.get(i);
			if (i == 0) {
				targerfieldold = fromValidator.getTarget_field();
			}
			if (targerfieldold.equals(fromValidator.getTarget_field())) {
				jsonArray.add(fromValidator);
			} else {
				jsonObject.put(targerfieldold, jsonArray.toString());
				targerfieldold = fromValidator.getTarget_field();
				jsonArray = new JSONArray();
				jsonArray.add(fromValidator);
			}
			if (i == fromVlidatList.size() - 1) {
				jsonObject.put(targerfieldold, jsonArray.toString());
			}

		}
		request.setAttribute("jsonObjectFrom", jsonObject);
		updateFormBean(mapping, request, reportDataForm);
		if ("0".equals(canEdit)) {
			return mapping.findForward("queryUI");
		}
		return mapping.findForward("editUI2");

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
		ReportDataForm reportdataform = (ReportDataForm) form;

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
			List reportType = rrs.getReportType(Integer.parseInt(systemcode),
					Integer.parseInt(levelFlag));
			List reportType1 = reportType;
			//获得类型下对应的所有报表
			List reportList = rrs.getReport(reportType);
			Report report = (Report) reportList.get(0);
			reportId = String.valueOf(report.getPkid());
			String reportName = report.getName();
			request.setAttribute("reportName", reportName);
		}
		List<ReportConfig> resultablename = rcs.getdefCharByTem(new Long(
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
		List reportTargetList = rds.getReportTargets(Long.parseLong(reportId));

		OrganInfo organInfo = os.getOrganByCode(organCode);
		request.setAttribute("organInfo", organInfo);
		// 查询需要显示select列表的字典项信息
		// dicMap用来维护target项和字典列表的关系
		Map<Long, List<DicItem>> dicMap = getDicMap(
				reportDate.replaceAll("-", ""), reportTargetList);
		//查出数据列表     
		List mapColumn = dfs.getMapColumn();
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
		List reportDataList1 = dfs.getReportDataAllMapColumn(dicMap, aacstatus,
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
			List<ReportResult> resultList = rrs.getReportResultByDataId(
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
		Report r = rds.getReport(Long.parseLong(reportId));
		request.setAttribute("reportName", r.getName());
		return mapping.findForward("enterreportdataresultde");
	}

	/**
	 * 单条保存数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward singleSaveTEMP(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		if (log.isDebugEnabled()) {
			log.info("Entering DataFill 'singleSaveTEMP' method");
		}
		String organCode = request.getParameter("organCode");
		String dataId = request.getParameter("dataId");
		String valueurl = request.getParameter("valueurl");
		String datatypeurl = request.getParameter("datatypeurl");
		String pflagurl = request.getParameter("pflagurl");
		String levelFlag = request.getParameter("levelFlag");
		//stFlag 为0时，表示保存，但不提交 不修改结果表状态，这时还可以看到数据 ; 为1时，表示提交，即要改结果表里的状态
		String stFlag = request.getParameter("stflag");
		String reportDate = request.getParameter("reportDate");
		reportDate = DateUtil.formatDate(reportDate);
		Long reportId = new Long(request.getParameter("reportId"));
		String currentDate = DateUtil.getDateTime("yyyyMMdd", new Date());
		User user = (User) request.getSession().getAttribute("user");
		final String userName = user.getName();
		reportId=dfs.getreportId(new Long(reportId), "0");//转换补录模板
		Report report = rds.getReport(reportId);
		Map temMap = rds.getTemplate(reportId); //模板和模型指标关系
		// 插入系统日志
		sls.insertLog(
				user.getPkid() + "",
				userName,
				reportId + "",
				LogUtil.LogType_User_Operate,
				organCode,
				"业务数据补录->指标数据补录->执行单条保存操作",
				report.getReportType() + "");
		List<ReportTarget> reportTargetList = rds.getReportTargets(reportId);

		List valueList=new ArrayList();//存更新的值
		List cstatusList=new ArrayList();//存状态
		List dtypeList=new ArrayList();//存数据状态
		List xvalueList=new ArrayList();//存更新的值
		List xcstatusList=new ArrayList();//存状态
		List xdtypeList=new ArrayList();//存数据状态
		String[] valuearr = valueurl.split("@");
		String[] pflagarr = pflagurl.split(",");
		String[] datatypearr = datatypeurl.split(",");
		for (Iterator it = reportTargetList.iterator(); it.hasNext();) {
			ReportTarget reportTarget = (ReportTarget) it.next();
			for (int i = 0; i < datatypearr.length; i++) {
				String strFormInputParamName = dataId + "_"
						+ reportTarget.getTargetField();
				String[] values = valuearr[i].split(":");
				String[] datatypes = datatypearr[i].split(":");
				String[] pflags = pflagarr[i].split(":");
				String strFormInputValue = "";
				String pflagvalue = "";
				String datatypevalue = "";
				if (strFormInputParamName.equals(values[0])) {
					if (values.length > 1) {
						strFormInputValue = values[1];//每列数据的值
					} else {
						strFormInputValue = "";
					}
					xvalueList.add(strFormInputValue);
				}
				String pflag = dataId + "_" + reportTarget.getTargetField()
						+ "_pflag";//标志是否要实时校验
				if (pflag.equals(pflags[0])) {
					//pflag为3为实时校验，这时改结果表里的cstatus字段为待校验
					if (pflags.length > 1) {
						pflagvalue = pflags[1];
						if (pflagvalue.contains("3")) {
							xcstatusList.add("1"); //1	待校验
						} else {
							xcstatusList.add("2");//2	待提交
						}
					} else {
						xcstatusList.add("2");//2	待提交
					}
				}
				String datatype = dataId + "_" + reportTarget.getTargetField()
						+ "_datatype"; //标志是校验通过还是没通过
				if (datatype.equals(datatypes[0])) {
					if (datatypes.length > 1) {
						datatypevalue = datatypes[1];
					} else {
						datatypevalue = null;
					}
					xdtypeList.add(datatypevalue);//0	通过；1	不通过
				}
			}
		}
		valueList.add(xvalueList);
		dtypeList.add(xdtypeList);
		cstatusList.add(xcstatusList);
		List resultablename = rcs.getdefCharByTem(reportId, new Long(34));
		int flag = rrs.updateReportResult(resultablename, stFlag, temMap,
				reportTargetList, valueList, cstatusList, dtypeList,
				new String[] { dataId }, reportDate);
		//根据补录信息更新结果表
		if (flag == 1) {
			// 保存当前分页的数据（或保存所有分页数据时最后一页的操作）
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			if ("0".equals(stFlag)) {
				out.print("0");
			} else {
				out.print("1");
			}
			out.flush();
			out.close();
			return null;
		}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		if ("1".equals(stFlag)) {
			out.print("2");
		} else {
			out.print("3");
		}
		out.flush();
		out.close();
		return null;

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
		ReportDataForm reportDataForm =(ReportDataForm)form;
		HttpSession session = request.getSession();
		String levelFlag = request.getParameter("levelFlag");
		String levelview = request.getParameter("levelview");
		String systemcode = FuncConfig.getProperty("system.sysflag", "2");
		String cstatus = request.getParameter("cstatus");//获得要查询的数据状态，2为已提交，5为已保存未提交 4为已同步  
		String zhi1=request.getParameter("zhi1");
		String zhi2=request.getParameter("zhi2");
		String zhi3=request.getParameter("zhi3");
		
		String tField1 = request.getParameter("tField1");
		if(tField1=="" || "".equalsIgnoreCase(tField1)){
			tField1=null;
		}
		String tField2 = request.getParameter("tField2");
		if(tField2=="" || "".equalsIgnoreCase(tField2)){
			tField2=null;
		}
		String tField3 = request.getParameter("tField3");
		if(tField3=="" || "".equalsIgnoreCase(tField3)){
			tField3=null;
		}
		
		String aacstatus = null;

		String paramDate = (String) request.getSession().getAttribute(
				"logindate");
		String paramOrgan = getUser(request).getOrganId();

		String systemId = (String) request.getSession().getAttribute(
				"system_id");
		if (cstatus != null && !"".equals(cstatus)) {
			aacstatus = "'" + cstatus + "'";
		}
		User user = getUser(request);
		Long userId = user.getPkid();
		String organCode = request.getParameter("organId");
		if (organCode == null) {
			organCode = user.getOrganId();
		}
		int idxid = getUser(request).getOrganTreeIdxid();
		String reportDate = request.getParameter("reportDate");
		if (null == reportDate) {
			reportDate = (String) session.getAttribute("logindate");
		}
		
		Page page = getPage(request);

		
		String organId = request.getParameter("organId"); // 闁哄牏鍎ら悗锟� 濠㈣埖宀搁敓锟�
		if (null == organId) {
			organId = user.getOrganId();
		}
		String reportId = request.getParameter("reportId"); //报表单选
		if (null == reportId) {
			
			reportId = "10000";
			
//			OrganInfo organInfo1 = os.getOrganByCode(paramOrgan);
//			int paramOrganType = organInfo1.getOrgan_type();
//			//获得目标层数据模型的类型
//			/*
//			 * List reportType=rrs.getReportType(Integer.parseInt(systemcode),
//			 * Integer.parseInt(levelFlag)); List reportType1=reportType;---chm
//			 */
//			//获得类型下对应的所有报表
//			// List reportList=rrs.getReport(reportType)---chm;
//			List reportList = rrs.getDateOrganEditReport(
//					paramDate.replaceAll("-", ""), paramOrganType, 1, userId,
//					systemId, levelFlag);
//			Report report = (Report) reportList.get(0);
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
			request.setAttribute("tField2", tField2);
			request.setAttribute("tField3", tField3);
			request.setAttribute("zhi1", zhi1);
			request.setAttribute("zhi2", zhi2);
			request.setAttribute("zhi3", zhi3);
			
			if (cstatus != null) {
				request.setAttribute("lxhcstatus", cstatus);
			}
			if (levelview != null) {
				request.setAttribute("lxview", levelview);
			}
			
			request.setAttribute("totalPage", page.getTotalPage());
			request.setAttribute("page", page);

		 
			return mapping.findForward("enterreportdataresultde");

		}
		List<ReportConfig> resultablename = rcs.getdefCharByTem(new Long(reportId), new Long(34));
		String tablenames = "";
		for (ReportConfig rc : resultablename) {
			String rtable = rc.getDefChar();
			rtable = rtable.replaceAll("\\{M\\}", reportDate
					.replaceAll("-", "").substring(4, 6));
			rtable = rtable.replaceAll("\\{D\\}", reportDate
					.replaceAll("-", "").substring(6, 8));
			rtable = rtable.replaceAll("\\{Y\\}", reportDate
					.replaceAll("-", "").substring(0, 4));
			tablenames += rtable + "@";  //把结果表的名称存下来

		}
		request.setAttribute("lxhresulttablename", tablenames);
		
		
		// 获得模板的栏
		List reportTargetList = rds.getReportTargets(Long.parseLong(reportId));

		OrganInfo organInfo = os.getOrganByCode(organCode);
		request.setAttribute("organInfo", organInfo);
		// 查询需要显示select列表的字典项信息
		// dicMap用来维护target项和字典列表的关系
		Map<Long, List<DicItem>> dicMap = getDicMap(
				reportDate.replaceAll("-", ""), reportTargetList);
		// 闁哄被鍎遍崵顓㈠极閻楀牆绁﹂柛鎺擃殸閵嗭拷
		List mapColumn = dfs.getMapColumn();
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
//			List reportDataList1 = dfs.getReportDataAll1(dicMap, aacstatus,
//					user.getIsAdmin(), resultablename,
//					Long.parseLong(reportId), organId,
//					reportDate.replaceAll("-", ""), reportTargetList, page,
//					idxid, levelFlag, mapC);
			
			List reportDataList1 = dfs.getReportDataAllMapColumn(dicMap, aacstatus,
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
				List<ReportResult> resultList = rrs.getReportResultByDataId(
						aacstatus, idList, reportDate, reportDataList1.get(3)
								.toString(), targetids.substring(0,
								targetids.length() - 1));
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
			request.setAttribute("tField2", tField2);
			request.setAttribute("tField3", tField3);
			request.setAttribute("zhi1", zhi1);
			request.setAttribute("zhi2", zhi2);
			request.setAttribute("zhi3", zhi3);
			
			if (cstatus != null) {
				request.setAttribute("lxhcstatus", cstatus);
			}
			if (levelview != null) {
				request.setAttribute("lxview", levelview);
			}

			// 加入报表名称，显示到页面              
			Report r = rds.getReport(Long.parseLong(reportId));
			request.setAttribute("reportName", r.getName());
			return mapping.findForward("enterreportdataresultde");
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw, true));
			String str = sw.toString();
			request.setAttribute("errors", str);
			request.setAttribute("errorMessages", e);
			return mapping.findForward("error");
		}
	}

	/**
	 * 单条导入
	 * 
	 * @Author chm
	 * @date 2014-7-8
	 * */
	public ActionForward importDataOne(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		// String xl = request.getParameter("xl");
		HttpSession session = request.getSession();
		User user = getUser(request);
		Long userId = user.getPkid();
		String organCode = request.getParameter("organId");

		String reportDate = request.getParameter("dataDate");
		if (null == reportDate) {
			reportDate = (String) session.getAttribute("logindate");
		}

		String reportId=request.getParameter("reportId"); //报表
		reportId=dfs.getreportId(new Long(reportId), "2").toString();//转换
		
		String organId=request.getParameter("organId");   //机构

		// 获得模板的栏
		List reportTargetList = rds.getReportTargets(Long.parseLong(reportId));

		Map<Long, List<DicItem>> dicMap = getDicMap(
				reportDate.replaceAll("-", ""), reportTargetList);
		request.setAttribute("dicMap", dicMap);
		request.setAttribute("dicMapsize", dicMap.size());
		List reportDataList = new ArrayList();

		OrganInfo organInfo = os.getOrganByCode(organCode);
		// request.setAttribute("xl", xl);
		request.setAttribute("organInfo", organInfo);
		request.setAttribute("reportTargetList", reportTargetList);
		request.setAttribute("reportTargetListlength", reportTargetList.size());
		request.setAttribute("dataDate", reportDate);
		request.setAttribute("reportId", reportId);
		request.setAttribute("organId", organCode);
		// 加入报表名称，显示到页面       
		Report r = rds.getReport(Long.parseLong(reportId));
		request.setAttribute("reportName", r.getName());

		 //查询该表所以页面校验规则
		Long repId = dfs.getreportId(new Long(reportId), "1");//转换
		List fromVlidatList = fvs.listFromValidator(repId.toString());
		JSONObject jsonObject = new JSONObject();
		JSONArray jsonArray = new JSONArray();
		String targerfieldold = null;
		for (int i = 0; i < fromVlidatList.size(); i++) {
			FromValidator fromValidator = (FromValidator) fromVlidatList.get(i);
			if (i == 0) {
				targerfieldold = fromValidator.getTarget_field();
			}
			if (targerfieldold.equals(fromValidator.getTarget_field())) {
				jsonArray.add(fromValidator);
			} else {
				jsonObject.put(targerfieldold, jsonArray.toString());
				targerfieldold = fromValidator.getTarget_field();
				jsonArray = new JSONArray();
				jsonArray.add(fromValidator);
			}
			if (i == fromVlidatList.size() - 1) {
				jsonObject.put(targerfieldold, jsonArray.toString()); //解决名字相同循环完毕没有 put的情况
			}

		}
		request.setAttribute("jsonObjectFrom", jsonObject);
		return mapping.findForward("importDataOne");

	}

	public ActionForward singleSaveOne1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering DataFill 'singleSaveOne1' method");
		}
		String organCode = request.getParameter("organCode");
		String valueurl = request.getParameter("valueurl");
		//stFlag 为0时，表示保存，但不提交 不修改结果表状态，这时还可以看到数据 ; 为1时，表示提交，即要改结果表里的状态
		String stFlag = request.getParameter("stflag");
		String reportDate = request.getParameter("reportDate");
		
		reportDate = reportDate.replaceAll("-", "");
		
		Long reportId = new Long(request.getParameter("reportId"));
		String currentDate = DateUtil.getDateTime("yyyyMMdd", new Date());
		User user = (User) request.getSession().getAttribute("user");
		final String userName = user.getName();
		Report report = rds.getReport(reportId);
		Map temMap = rds.getTemplate(reportId);  //模板和模型指标关系
		// 插入系统日志
		sls.insertLog(
				user.getPkid() + "",
				userName,
				reportId + "",
				LogUtil.LogType_User_Operate,
				organCode,
				"业务数据补录->指标数据补录->执行单条保存操作",
				report.getReportType() + "");
		List<ReportTarget> reportTargetList = rds.getReportTargets(reportId);

		List valueList = new ArrayList();//存字段
		List cstatusList = new ArrayList();//存值
		String jt = FuncConfig.getProperty("qh.delcjrq", "yes");

		String[] valuearr = valueurl.split("@");
		String[] valuearrr = valueurl.split(":");
		
		for (int i = 0; i < valuearr.length; i++) {
			valueList.add(valuearr[i].substring(0, valuearr[i].lastIndexOf(":")));
			if(jt.equals("yes")){
				if(valuearr[i].substring(0, valuearr[i].lastIndexOf(":")).equals("NBJGH")){
					valueList.remove("NBJGH");
//					cstatusList.add("70000000");
				}else if(valuearr[i].substring(0, valuearr[i].lastIndexOf(":")).equals("CJRQ")){
					valueList.remove("CJRQ");
//					cstatusList.add(reportDate);
				}else{
					cstatusList.add(valuearrr[i + 1].substring(0,valuearrr[i + 1].lastIndexOf("@")).replace("-", "").trim());
					
				}
				
			}else{
				cstatusList.add(valuearrr[i + 1].substring(0,valuearrr[i + 1].lastIndexOf("@")).replace("-", "").trim());
			}
		}
		
			if (jt.equals("yes")) {
				valueList.add("NBJGH")	;
				cstatusList.add(organCode);
				valueList.add("CJRQ")	;
				cstatusList.add(reportDate);
			}
		
		List resultablename = rcs.getdefCharByTem(reportId, new Long(33));
		//根据补录信息更新數據表
		// 保存当前分页的数据（或保存所有分页数据时最后一页的操作）
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		try {
			int flag = rrs.insertReportService(resultablename, stFlag, temMap,
					reportTargetList, valueList, cstatusList, reportDate);
		} catch (Exception e) {
			e.printStackTrace();
			out.print("1");
		}

		out.flush();
		out.close();
		return null;

	}

	public ActionForward singleSaveOne11(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering DataFill 'singleSaveOne11' method");
		}
		String organCode = request.getParameter("organCode");
		String valueurllist = request.getParameter("valueurllist");
		String stFlag = request.getParameter("stflag");
		String reportDate = request.getParameter("reportDate");
		reportDate = DateUtil.formatDate(reportDate);
		Long reportId = new Long(request.getParameter("reportId"));
		String currentDate = DateUtil.getDateTime("yyyyMMdd", new Date());
		User user = (User) request.getSession().getAttribute("user");
		final String userName = user.getName();
		Report report = rds.getReport(reportId);
		Map temMap = rds.getTemplate(reportId); 
		
		sls.insertLog(
				user.getPkid() + "",
				userName,
				reportId + "",
				LogUtil.LogType_User_Operate,
				organCode,
				"业务数据补录->指标数据补录->批量保存",
				report.getReportType() + "");
		List<ReportTarget> reportTargetList = rds.getReportTargets(reportId);

		List valueList = new ArrayList();//存字段
		List cstatusList = new ArrayList();//存值

		for (int j = 0; j < valueurllist.split("#").length; j++) {
			valueList = new ArrayList();
			cstatusList = new ArrayList();
			String valueurl = valueurllist.split("#")[j];
			String[] valuearr = valueurl.split("@");
			String[] valuearrr = valueurl.split(":");
			for (int i = 0; i < valuearr.length; i++) {
				valueList.add(valuearr[i].substring(0,
						valuearr[i].lastIndexOf(":")));
				cstatusList.add(valuearrr[i + 1].substring(0,
						valuearrr[i + 1].lastIndexOf("@")).replace("-", "").trim());
			}

			List resultablename = rcs.getdefCharByTem(reportId, new Long(33));
			
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			try {
				int flag = rrs.insertReportService(resultablename, stFlag,
						temMap, reportTargetList, valueList, cstatusList,
						reportDate);
			} catch (Exception e) {
				e.printStackTrace();
				out.print("1");
			}

			out.flush();
			out.close();

		}
		return null;
	}

	/**
	 * 查找数据 单条
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward geteditDataDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.info("Entering Standard_DataGather 'getDataDetail' method");
		}
		HttpSession session = request.getSession();
		String levelFlag = request.getParameter("levelFlag");
		String levelview = request.getParameter("levelview");
		String pkid = request.getParameter("dataId");
		String pager = request.getParameter("pager");
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
		String reportDate = request.getParameter("dataDate");
		if (null == reportDate) {
			reportDate = (String) session.getAttribute("logindate");
		}
		String reportId = request.getParameter("reportId"); //报表单选
		if (null == reportId) {
			//获得目标层数据模型的类型
			List reportType = rrs.getReportType(Integer.parseInt(systemcode),
					Integer.parseInt(levelFlag));
			List reportType1 = reportType;
			//获得类型下对应的所有报表
			List reportList = rrs.getReport(reportType);
			Report report = (Report) reportList.get(0);
			reportId = String.valueOf(report.getPkid());
			String reportName = report.getName();
			request.setAttribute("reportName", reportName);
		}
		List<ReportConfig> resultablename = rcs.getdefCharByTem(new Long(
				reportId), new Long(34));
		for (ReportConfig rc : resultablename) {
			String rtable = rc.getDefChar();
			rtable = rtable.replaceAll("\\{M\\}", reportDate
					.replaceAll("-", "").substring(4, 6));
			rtable = rtable.replaceAll("\\{D\\}", reportDate
					.replaceAll("-", "").substring(6, 8));
			rtable = rtable.replaceAll("\\{Y\\}", reportDate
					.replaceAll("-", "").substring(0, 4));
			request.setAttribute("lxhresulttablename", rtable); //把结果表的名称存下来
		}
		String organId = request.getParameter("organId"); //机构 多选
		if (null == organId) {
			organId = user.getOrganId();
		}
		Page page = getPage(request);

		// 获得模板的栏
		List reportTargetList = rds.getReportTargets(Long.parseLong(reportId));

		OrganInfo organInfo = os.getOrganByCode(organCode);
		request.setAttribute("organInfo", organInfo);
		// 查询需要显示select列表的字典项信息
		// dicMap用来维护target项和字典列表的关系
		Map<Long, List<DicItem>> dicMap = getDicMap(
				reportDate.replaceAll("-", ""), reportTargetList);
		request.setAttribute("dicMap", dicMap);
		//查出数据列表
		List mapColumn = dfs.getMapColumn();
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
		List reportDataList1 = dfs.getReportDataAllMapColumn(dicMap, aacstatus,
				user.getIsAdmin(), resultablename, Long.parseLong(reportId),
				organId, reportDate.replaceAll("-", ""), reportTargetList,
				page, idxid, levelFlag, pkid, mapC);
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
			List<ReportResult> resultList = rrs.getReportResultByDataId(
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
		request.setAttribute("dataDate", reportDate);
		request.setAttribute("reportId", reportId);
		request.setAttribute("levelFlag", levelFlag);
		request.setAttribute("organId", organCode);
		request.setAttribute("pager", pager);
		if (cstatus != null) {
			request.setAttribute("lxhcstatus", cstatus);
		}
		if (levelview != null) {
			request.setAttribute("lxview", levelview);
		}
		// 加入报表名称，显示到页面      
		Report r = rds.getReport(Long.parseLong(reportId));
		request.setAttribute("reportName", r.getName());
		return mapping.findForward("editOneUI2");
	}

	/**
	 * 删除报送数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return getDataDetail
	 * @throws Exception
	 */
	public ActionForward delSingelData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering Standard_DataGather method='delSingelData' ");
		}
		String pkid = request.getParameter("pkid");
		String reportId = request.getParameter("reportId");
		String date = request.getParameter("dataDate");
		String organId = request.getParameter("organId");
		int flag = dfs.delreport(pkid, reportId, date, organId);

		if (flag == 1) {
			User user = (User) request.getSession().getAttribute("user");
			final String userName = user.getName();
			Report report = rds.getReport(new Long(reportId));
			Map temMap = rds.getTemplate(new Long(reportId));
			sls.insertLog(user.getPkid() + "", userName, reportId + "",
					LogUtil.LogType_User_Operate, organId,
					"业务数据补录->导入数据维护->执行删除操作", report.getReportType()
							+ "");
		}
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

	/**
	 * 单条保存数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward singleSaveOne(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering DataFill 'singleSave' method");
		}
		String organCode = request.getParameter("organCode");
		String dataId = request.getParameter("dataId");
		String valueurl = request.getParameter("valueurl");
		String datatypeurl = request.getParameter("datatypeurl");
		String pflagurl = request.getParameter("pflagurl");
		String levelFlag = request.getParameter("levelFlag");
		String pager = request.getParameter("pager");
		//stFlag 为0时，表示保存，但不提交 不修改结果表状态，这时还可以看到数据 ; 为1时，表示提交，即要改结果表里的状态
		String stFlag = request.getParameter("stflag");
		String reportDate = request.getParameter("reportDate");
		//reportDate = DateUtil.formatDate(reportDate);
		Long reportId = new Long(request.getParameter("reportId"));
		String currentDate = DateUtil.getDateTime("yyyyMMdd", new Date());
		User user = (User) request.getSession().getAttribute("user");
		final String userName = user.getName();
		Report report = rds.getReport(reportId);
		Map temMap = rds.getTemplate(reportId);//模板和模型指标关系
		// 插入系统日志
		sls.insertLog(
				user.getPkid() + "",
				userName,
				reportId + "",
				LogUtil.LogType_User_Operate,
				organCode,
				"业务数据补录->指标数据补录->执行单条保存操作",
				report.getReportType() + "");
		List<ReportTarget> reportTargetList = rds.getReportTargets(reportId);
		List valueList = new ArrayList();//存更新的值
		List cstatusList = new ArrayList();//存状态
		List dtypeList = new ArrayList();//存数据状态
		List xvalueList = new ArrayList();//存更新的值
		List xcstatusList = new ArrayList();//存状态
		List xdtypeList = new ArrayList();//存数据状态
		String[] valuearr = valueurl.split("@");
		String[] pflagarr = pflagurl.split(",");
		String[] datatypearr = datatypeurl.split(",");
		for (Iterator it = reportTargetList.iterator(); it.hasNext();) {
			ReportTarget reportTarget = (ReportTarget) it.next();
			for (int i = 0; i < datatypearr.length; i++) {
				String strFormInputParamName = dataId + "_"
						+ reportTarget.getTargetField();
				String[] values = valuearr[i].split(":");
				String[] datatypes = datatypearr[i].split(":");
				String[] pflags = pflagarr[i].split(":");
				String strFormInputValue = "";
				String pflagvalue = "";
				String datatypevalue = "";
				if (strFormInputParamName.equals(values[0])) {
					if (values.length > 1) {
						strFormInputValue = values[1];//每列数据的值
					} else {
						strFormInputValue = "";
					}
					xvalueList.add(strFormInputValue);
				}
				String pflag = dataId + "_" + reportTarget.getTargetField()
						+ "_pflag";//标志是否要实时校验
				if (pflag.equals(pflags[0])) {
					//pflag为3为实时校验，这时改结果表里的cstatus字段为待校验
					if (pflags.length > 1) {
						pflagvalue = pflags[1];
						if (pflagvalue.contains("3")) {
							xcstatusList.add("1"); //1	待校验
						} else {
							xcstatusList.add("2");//2	待提交
						}
					} else {
						xcstatusList.add("2");//2	待提交
					}
				}
				String datatype = dataId + "_" + reportTarget.getTargetField()
						+ "_datatype"; //标志是校验通过还是没通过
				if (datatype.equals(datatypes[0])) {
					if (datatypes.length > 1) {
						datatypevalue = datatypes[1];
					} else {
						datatypevalue = null;
					}
					xdtypeList.add(datatypevalue);//0	通过；1	不通过
				}
			}
		}
		valueList.add(xvalueList);
		dtypeList.add(xdtypeList);
		cstatusList.add(xcstatusList);
		List resultablename = rcs.getdefCharByTem(reportId, new Long(33));
		//根据补录信息更新數據表
		int flag = rrs.updateReportService(resultablename, stFlag, temMap,
				reportTargetList, valueList, cstatusList, dtypeList,
				new String[] { dataId }, reportDate);

		if (flag == 1) {
			// 保存当前分页的数据（或保存所有分页数据时最后一页的操作）
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			if ("0".equals(stFlag)) {
				out.print("0");
			} else {

				if (pager == null && "1".equals(pager)) {
					out.print("dataFill.do?method=getDataDetail&levelFlag=2&levelview=1");
				} else {
					out.print("dataFill.do?method=getDataDetail&openmode=_self&cstatus=&reportDate="
							+ reportDate
							+ "&reportId="
							+ reportId
							+ "&organId="
							+ organCode
							+ "&levelFlag=2&page="
							+ pager + "&levelview=1");
				}
			}
			out.flush();
			out.close();
			return null;
		}
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		if ("1".equals(stFlag)) {
			out.print("2");
		} else {
			out.print("3");
		}
		out.flush();
		out.close();
		return null;
	}

	private Map<Long, List<DicItem>> getDicMap(String reportdate,
			List reportTargetList1) {
		Map<Long, List<DicItem>> dicMap = new HashMap<Long, List<DicItem>>();
		for (int i = 0; i < reportTargetList1.size(); i++) {
			ReportTarget rt = (ReportTarget) reportTargetList1.get(i);
			if (rt != null) {
				if (new Integer(1).equals(rt.getFieldType())
						&& rt.getDicPid() != null) {
					List<DicItem> dicItems = dfs.getDicItems(rt.getDicPid());
					dicMap.put(rt.getPkid(), dicItems);
				} else if (new Integer(3).equals(rt.getFieldType())
						&& rt.getDicPid() != null) {
					List<DicItem> dicItems = dfs.getDicvalue(reportdate, rt);
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
		Map<String, String> pcheckMap = new HashMap<String, String>(); //存入页面校验脚本
		Map<String, String> rvalueMap = new HashMap<String, String>(); //存入参考值校验脚本
		Map<String, String> pflagMap = new HashMap<String, String>(); //存入校验类型
		Map<String, String> valueMap = new HashMap<String, String>();  //放参考值
		Map<String, Map> checkresult = new HashMap<String, Map>();
		Map<String, String> resultMap = new HashMap<String, String>();
		Map<String, Long> relation = new HashMap<String, Long>();
		Map<String, String> result = new HashMap<String, String>();
		Map<String, String> resultMsg = new HashMap<String, String>();
		Map<String, String> dtypeMap = new HashMap<String, String>(); //数据类型0为通过 1为不通过
		Map<String, String> cstatusMap = new HashMap<String, String>();//数据状态
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
		//获得规则列表 
		List<ReportRule> rulist = rrs.getReportRuleBycode(rulecode);
		for (ReportResult rr : resultList) {
			resultMap.put(rr.getModelid() + "_" + rr.getTargetid(),
					rr.getRulemsg());
			relation.put(rr.getRulecode(), reportId);
			// 将结果表中的模型对应列转化成模板对应列
			Object[] arr = dfs.getRelateTarget(rr.getModelid(),
					rr.getTargetid(), reportId);
			if (arr != null) {
				String pkey = String.valueOf(rr.getKeyvalue()) + "_" + arr[2];
				if (null != rr.getNewvalue() && !"".equals(rr.getNewvalue())) {
					repDataMap.put(pkey, rr.getNewvalue());
				}
				//组织成了和repDataMap中一样的键值
				result.put(pkey, rr.getRulecode());
				for (ReportRule mm : rulist) {
					if (rr.getRulecode().equals(mm.getRulecode())) {
						resultMsg.put(mm.getRulecode(), mm.getRulemsg());
						//组织成了和repDataMap中一样的键值
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
					
					reMsg = resultMap.get(key) + "  " + msg;
					
					resultMap.put(key, reMsg);
				}
			}
		}
		Map<String, String> targetMap1 = dfs.getRelateTargets(relation);
		for (Entry e : targetMap1.entrySet()) {
			//此处含义：(String) e.getValue()为模板的id_指标的targetfeild;
			//resultMap.get(e.getKey())为该模板对应的实际的模型以及指标组合的错误提示
			result.put((String) e.getValue(), resultMap.get(e.getKey()));
		}
		checkresult.put("ruleMap0", result);
		checkresult.put("repDataMap0", repDataMap);
		return checkresult;
	}

	private Map getDicMapByCstatus(String reportdate, List reportTargetList1,
			Map repDataMap) {
		for (int i = 0; i < reportTargetList1.size(); i++) {
			List<DicItem> dicItems = null;
			ReportTarget rt = (ReportTarget) reportTargetList1.get(i);
			if (new Integer(1).equals(rt.getFieldType())
					&& rt.getDicPid() != null) {
				dicItems = dfs.getDicItems(rt.getDicPid());
			} else if (new Integer(3).equals(rt.getFieldType())
					&& rt.getDicPid() != null) {
				dicItems = dfs.getDicvalue(reportdate, rt);
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

	private Map<String, String> getMapValue(Map<String, String> pMap,
			String value, String pkey) {
		if (pMap.containsKey(pkey)) {
			pMap.put(pkey, pMap.get(pkey) + ";" + value);
		} else {
			pMap.put(pkey, value);
		}
		return pMap;
	}

	public Page getPage(HttpServletRequest request) {
		String pageStr = request.getParameter("page");
		int page = 1;
		if (pageStr != null && pageStr.matches("^\\d+$")) {
			page = Integer.parseInt(pageStr);
		}
		return new Page(page);
	}

	/**
	 * 状态查询功能 点击 进入方法 点查看详细返回也进入这个方法 查看报表按钮也进入此方法
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward getReportGuide(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			if (log.isDebugEnabled()) {
				log.info("Entering Standard_DataGather 'getReportGuide' method");
			}
			String levelFlag = request.getParameter("levelFlag");
			String leveType = request.getParameter("leveType");
			ReportDataForm reportDataForm = (ReportDataForm) form;
			String openMode = request.getParameter("openmode");
			String flagtap = request.getParameter("flagtap");
			request.setAttribute("flagtap", flagtap);
			int idxid = getUser(request).getOrganTreeIdxid();
			String reportDate = reportDataForm.getDataDate();
			if (reportDate == null) {
				reportDate = request.getParameter("reportDate");
				reportDataForm.setDataDate(reportDate);
			}
			// Long reportId = reportDataForm.getReportId();
			Long reportId = null;
			// if (reportId == null) {
			if (request.getParameter("reportId") != null) {
				reportId = new Long(request.getParameter("reportId"));
				reportDataForm.setReportId(reportId);
			}
			// }
			// ReportConfigService rcs=(ReportConfigService)
			// getBean("reportConfigService");
			// ReportDefineService rs = (ReportDefineService)
			// getReportDefineService();

			String organId = request.getParameter("organCode");

			if (organId == null) {
				organId = getUser(request).getOrganId();
				reportDataForm.setOrganId(organId);
			}
			OrganInfo organInfo = os.getOrganByCode(organId);
			request.setAttribute("organInfo", organInfo);
			User user = (User) request.getSession().getAttribute("user");
			reportDataForm.setFillUser(user.getName());
			reportDataForm.setPhone("");
			reportDataForm.setCheckUser(user.getName());
			String cstatus = "'0','3','5'";
			// ReportRuleService rts = (ReportRuleService)
			// getBean("reportruleService");
			request.setAttribute("levelFlag", levelFlag);
			HttpSession session = request.getSession();
			if (reportDate == null) {
				reportDate = (String) session.getAttribute("logindate");
				reportDataForm.setDataDate(reportDate);
			}

			request.setAttribute("organInfo", organInfo);
			if (levelFlag != null && !"".equals(levelFlag)) {
				request.setAttribute("params", "&paramdate=" + reportDate
						+ "&paramorgan=" + organId + "&levelFlag=" + levelFlag);
			} else {
				request.setAttribute("params", "&paramdate=" + reportDate
						+ "&paramorgan=" + organId);
			}
			request.setAttribute("orgparam", "&date=" + reportDate);
			request.setAttribute("organId", organId);
			request.setAttribute("dataDate", reportDate);
			String reportName = request.getParameter("reportName");

			String systemcode = FuncConfig.getProperty("system.sysflag", "1");

			List<Object[]> reportList = null;
			if (reportId == null) {

				reportList = rrs.getReportGuide(reportId, levelFlag,
						systemcode, reportDate, organId, idxid, user.getPkid());
			} else {
				reportList = rrs.getReportGuide(reportId, levelFlag,
						systemcode, reportDate, organId, idxid, user.getPkid());
			}

			for (Object[] objects : reportList) {
				if (new Long(objects[2].toString()) > 0) {
					// String userName=user.getName();
					reportDate = DateUtil.formatDate(reportDate);
					// getSysLogService().insertLog(user.getPkid() + "",
					// userName,
					// reportId + "", LogUtil.LogType_User_Operate,
					// objects[1].toString(),
					
					StatusForm statusForm = new StatusForm();
					statusForm.setOrganId(objects[1].toString());
					statusForm.setReportId(objects[0].toString());
					statusForm.setFrequency(reportDate);
					int result = dfs.unLockDataStatus(statusForm);
				}
			}

	/*		Iterator it=reportList.iterator();
			while(it.hasNext()){
				Object[]o=(Object[])it.next();
				System.out.println("我是0=="+o[0].toString());
				System.out.println("我是1=="+o[1].toString());
				System.out.println("我是2=="+o[2].toString());
				System.out.println("我是3=="+o[3].toString());
				System.out.println("我是4=="+o[4].toString());
				System.out.println("我是5=="+o[5].toString());
				System.out.println("我是6=="+o[6].toString());
				System.out.println("我是7=="+o[7].toString());
				
			}*/
			
			if(null == reportId ){
				reportId = 10000L;
			}
			request.setAttribute("leveType", leveType);
			request.setAttribute("reportId", reportId);
			request.setAttribute("reportList", reportList);

			return mapping.findForward("reportguide");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw, true));
			String str = sw.toString();
			request.setAttribute("errors", str);
			request.setAttribute("errorMessages", e);
			return mapping.findForward("error");
		}
	}

	public ActionForward getLeftReportGuide(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try {
			if (log.isDebugEnabled()) {
				log.info("Entering Standard_DataGather 'getReportGuide' method");
			}
			String levelFlag = request.getParameter("levelFlag");
			String leveType = request.getParameter("leveType");
			ReportDataForm reportDataForm = (ReportDataForm) form;
			String openMode = request.getParameter("openmode");
			String flagtap = request.getParameter("flagtap");
			request.setAttribute("flagtap", flagtap);
			int idxid = getUser(request).getOrganTreeIdxid();
			String reportDate = reportDataForm.getDataDate();
			if (reportDate == null) {
				reportDate = request.getParameter("reportDate");
				reportDataForm.setDataDate(reportDate);
			}
			Long reportId = null;
			if (request.getParameter("reportId") != null) {
				reportId = new Long(request.getParameter("reportId"));
				reportDataForm.setReportId(reportId);
			}
			String organId = request.getParameter("organCode");
			if (organId == null) {
				organId = getUser(request).getOrganId();
				reportDataForm.setOrganId(organId);
			}
			OrganInfo organInfo = os.getOrganByCode(organId);
			request.setAttribute("organInfo", organInfo);
			User user = (User) request.getSession().getAttribute("user");
			reportDataForm.setFillUser(user.getName());
			reportDataForm.setPhone("");
			reportDataForm.setCheckUser(user.getName());
			String cstatus = "'0','3','5'"; 
			request.setAttribute("levelFlag", levelFlag);
			HttpSession session = request.getSession();
			if (reportDate == null) {
				reportDate = (String) session.getAttribute("logindate");
				reportDataForm.setDataDate(reportDate);
			}

			request.setAttribute("organInfo", organInfo);
			if (levelFlag != null && !"".equals(levelFlag)) {
				request.setAttribute("params", "&paramdate=" + reportDate
						+ "&paramorgan=" + organId + "&levelFlag=" + levelFlag);
			} else {
				request.setAttribute("params", "&paramdate=" + reportDate
						+ "&paramorgan=" + organId);
			}
			request.setAttribute("orgparam", "&date=" + reportDate);
			request.setAttribute("organId", organId);
			request.setAttribute("dataDate", reportDate);
			String reportName = request.getParameter("reportName");
			String systemcode = FuncConfig.getProperty("system.sysflag", "1");
			List<Object[]> reportList = null;
			if (reportId == null) {
				reportList = rrs.getReportGuide(reportId, levelFlag,
						systemcode, reportDate, organId, idxid, user.getPkid());
			} else {
				reportList = rrs.getReportGuide(reportId, levelFlag,
						systemcode, reportDate, organId, idxid, user.getPkid());
			}
			for (Object[] objects : reportList) {
				if (new Long(objects[2].toString()) > 0) {
					reportDate = DateUtil.formatDate(reportDate);
					StatusForm statusForm = new StatusForm();
					statusForm.setOrganId(objects[1].toString());
					statusForm.setReportId(objects[0].toString());
					statusForm.setFrequency(reportDate);
					int result = dfs.unLockDataStatus(statusForm);
				}
			}

			request.setAttribute("leveType", leveType);
			request.setAttribute("reportId", reportId);
			request.setAttribute("reportList", reportList);

			return mapping.findForward("Leftreportguide");
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw, true));
			String str = sw.toString();
			request.setAttribute("errors", str);
			request.setAttribute("errorMessages", e);
			return mapping.findForward("error");
		}
	}

	/**
	 * 状态查询界面 点击查看详细按钮方法
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward getReportGuideDetailed(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		try{
			if (log.isDebugEnabled()) {
				log.info("Entering Standard_DataGather 'getReportGuideDetailed' method");
			}
			int idxid = getUser(request).getOrganTreeIdxid();
			Long reportId = new Long(request.getParameter("reportId"));
			String organId = request.getParameter("organId");
			String targetTable = request.getParameter("targetTable");
			String reportDate = request.getParameter("reportDate");
			request.setAttribute("targetTable", targetTable);
			request.setAttribute("organId", organId);
			request.setAttribute("reportId", reportId);
			request.setAttribute("dataDate", reportDate);
			String cstatus = "'0','3','5'"; //
			// ReportRuleService rts = (ReportRuleService)
			// getBean("reportruleService");
			List<Object[]> reportList = null;
			reportList = rrs.getReportGuideDetail(reportId, targetTable, organId,
					idxid, reportDate);
			request.setAttribute("reportList", reportList);
			return mapping.findForward("reportguideDetailed");
		}catch(Exception e){
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw, true));
			String str = sw.toString();
			request.setAttribute("errors", str);
			request.setAttribute("errorMessages", e);
			return mapping.findForward("error");
		}
	}

	public ActionForward commitDataStatus(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		if(request.getParameter("aaaa").equals("1")){
			
			User user = getUser(request);
			String userName = user.getName();
			String[] reportId1 = request.getParameter("reportId").split(",");
			String[] organId1 = request.getParameter("organId").split(",");
			String dataDate = request.getParameter("dataDate");
			dataDate = DateUtil.formatDate(dataDate);
			
				for(int i=0;i<reportId1.length;i++){
					System.out.println(reportId1[i]);
					System.out.println(organId1[i]);
					getSysLogService().insertLog(user.getPkid() + "", userName,
							reportId1[i] + "", LogUtil.LogType_User_Operate, organId1[i],
							"补录状态查询->补录提交", reportId1[i] + "");
					StatusForm statusForm = new StatusForm();
					statusForm.setOrganId(organId1[i]);
					statusForm.setReportId(reportId1[i]);
					statusForm.setFrequency(dataDate.substring(0, 6) + "00");
					statusForm.setStatus("1");
					statusForm.setIsLock("1");
					int result = dfs.commitDataStatus(statusForm);
				}
			
			
			/*StatusForm statusForm = new StatusForm();
			statusForm.setOrganId(organId);
			statusForm.setReportId(reportId);
			statusForm.setFrequency(dataDate.substring(0, 6) + "00");
			statusForm.setStatus("1");
			statusForm.setIsLock("1");
			int result = dfs.commitDataStatus(statusForm);*/
			return null;
		}else{
			
	
		User user = getUser(request);
		String userName = user.getName();
		String reportId = request.getParameter("reportId");
		String organId = request.getParameter("organId");
		String dataDate = request.getParameter("dataDate");
		dataDate = DateUtil.formatDate(dataDate);


		getSysLogService().insertLog(user.getPkid() + "", userName,
				reportId + "", LogUtil.LogType_User_Operate, organId,
				"补录状态查询->补录提交", reportId + "");
		StatusForm statusForm = new StatusForm();
		statusForm.setOrganId(organId);
		statusForm.setReportId(reportId);
		statusForm.setFrequency(dataDate.substring(0, 6) + "00");
		statusForm.setStatus("1");
		statusForm.setIsLock("1");
		int result = dfs.commitDataStatus(statusForm);
		return null;
		}
	}

	// }

	public ActionForward unLockDataStatus(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		User user = getUser(request);
		String userName = user.getName();
		String reportId = request.getParameter("reportId");
		String organId = request.getParameter("organId");
		String dataDate = request.getParameter("dataDate");
		dataDate = DateUtil.formatDate(dataDate);
		getSysLogService().insertLog(user.getPkid() + "", userName,
				reportId + "", LogUtil.LogType_User_Operate, organId,
				"补录状态查询->解锁", reportId + "");
		// DataFillService ds = getDataFillService();
		StatusForm statusForm = new StatusForm();
		statusForm.setOrganId(organId);
		statusForm.setReportId(reportId);
		statusForm.setFrequency(dataDate);
		int result = dfs.unLockDataStatus(statusForm);
		return null;
	}

	public ActionForward exportTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String organId = request.getParameter("organId");
		String reportId = request.getParameter("reportId");
		String dataDate = request.getParameter("dataDate").replace("-", "");

		reportId = dfs.getreportId(new Long(reportId), "1").toString();
		Report report = dfs.getReport(Long.parseLong(reportId));
		HSSFWorkbook wb = dfs.getXlsWork(report, organId, dataDate);
		String fileName = "";
		try {
			fileName = new String(
					(report.getName() + "_" + reportId + "_" + dataDate)
							.getBytes("gb2312"),
					"iso8859-1");
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

	public ActionForward xlinputdata(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String organId = request.getParameter("organId");
		String reportId = request.getParameter("reportId");
		String dataDate = request.getParameter("dataDate");
		String resultablename1 = request.getParameter("resultablename1");
		request.setAttribute("organId", organId);
		request.setAttribute("reportId", reportId);
		request.setAttribute("dataDate", dataDate);
		request.setAttribute("resultablename1", resultablename1);
		log.info("resultablename1==========" + resultablename1);
		return mapping.findForward("xlinputdata");
	}

	public ActionForward importDataFromXls(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String organId = request.getParameter("organId");
		String reportId = dfs.getreportId(
				new Long(request.getParameter("reportId")), "2").toString();

		String dataDate = request.getParameter("dataDate").replace("-", "");
		request.setAttribute("organId", organId);
		request.setAttribute("reportId", reportId);
		request.setAttribute("dataDate", dataDate);
		return mapping.findForward("importDataFromXls");
	}

	
	public ActionForward exportTemplate1(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		String organId = request.getParameter("organId");
		String reportId = request.getParameter("reportId");
		String dataDate = request.getParameter("dataDate").replace("-", "");

		

		Report report = dfs.getReport(Long.parseLong(reportId));
		HSSFWorkbook wb = dfs.getXlsWork(report, organId, dataDate);
		String fileName = "";
		try {
			fileName = new String(
					(report.getName() + "_" + reportId + "_" + dataDate)
							.getBytes("gb2312"),
					"iso8859-1");
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

	public ActionForward singleSaveTEMP11(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		if (log.isDebugEnabled()) {
			log.info("Entering DataFill 'singleSaveTEMP11' method");
		}
		String organCode = request.getParameter("organCode");
		String dataId = request.getParameter("dataId");
		String valueurl = request.getParameter("valueurl");
		String datatypeurl = request.getParameter("datatypeurl");
		String pflagurl = request.getParameter("pflagurl");
		String levelFlag = request.getParameter("levelFlag");

		String stFlag = request.getParameter("stflag");
		String reportDate = request.getParameter("reportDate");
		reportDate = DateUtil.formatDate(reportDate);
		Long reportId = new Long(request.getParameter("reportId"));
		String currentDate = DateUtil.getDateTime("yyyyMMdd", new Date());
		User user = (User) request.getSession().getAttribute("user");
		final String userName = user.getName();
		reportId=dfs.getreportId(new Long(reportId), "0");//转换补录模板
		Report report = rds.getReport(reportId);
		Map temMap = rds.getTemplate(reportId);

		sls.insertLog(
				user.getPkid() + "",
				userName,
				reportId + "",
				LogUtil.LogType_User_Operate,
				organCode,
				"业务数据补录->数据补录->执行批量保存",
				report.getReportType() + "");
		List<ReportTarget> reportTargetList = rds.getReportTargets(reportId);
		List valueList = new ArrayList();
		List cstatusList = new ArrayList();
		List dtypeList = new ArrayList();
		List xvalueList = new ArrayList();
		List xcstatusList = new ArrayList();
		List xdtypeList = new ArrayList();
		Map<Integer, List> valueList1 = new HashMap<Integer, List>();
		Map<Integer, List> cstatusList1 = new HashMap<Integer, List>();
		Map<Integer, List> dtypeList1 = new HashMap<Integer, List>();
		int flag = 0;
		List resultablename = rcs.getdefCharByTem(reportId, new Long(34));
		String[] valueurllength = valueurl.split("#");
		String[] pflagurllength = pflagurl.split("#");
		String[] datatypeurllength = datatypeurl.split("#");
		String[] dataIdlength = dataId.split("#");
		for (int k = 0; k < valueurllength.length; k++) {
			String[] valuearr = valueurllength[k].split("@");
			String[] pflagarr = pflagurllength[k].split(",");
			String[] datatypearr = datatypeurllength[k].split(",");
			for (Iterator it = reportTargetList.iterator(); it.hasNext();) {

				ReportTarget reportTarget = (ReportTarget) it.next();
				for (int i = 0; i < datatypearr.length; i++) {

					String strFormInputParamName = dataIdlength[k] + "_"
							+ reportTarget.getTargetField();
					String[] values = valuearr[i].split(":");
					String[] datatypes = datatypearr[i].split(":");
					String[] pflags = pflagarr[i].split(":");
					String strFormInputValue = "";
					String pflagvalue = "";
					String datatypevalue = "";
					if (strFormInputParamName.equals(values[0])) {
						if (values.length > 1) {
							strFormInputValue = values[1];
						} else {
							strFormInputValue = "";
						}
						xvalueList.add(strFormInputValue);
					}
					String pflag = dataIdlength[k] + "_"
							+ reportTarget.getTargetField() + "_pflag";
					if (pflag.equals(pflags[0])) {
						if (pflags.length > 1) {
							pflagvalue = pflags[1];
							if (pflagvalue.contains("3")) {
								xcstatusList.add("1");
							} else {
								xcstatusList.add("2");
							}
						} else {
							xcstatusList.add("2");
						}
					}
					String datatype = dataIdlength[k] + "_"
							+ reportTarget.getTargetField() + "_datatype";
					if (datatype.equals(datatypes[0])) {
						if (datatypes.length > 1) {
							datatypevalue = datatypes[1];
						} else {
							datatypevalue = null;
						}
						xdtypeList.add(datatypevalue);
					}

				}
			}
			valueList.add(xvalueList);
			dtypeList.add(xdtypeList);
			cstatusList.add(xcstatusList);
			flag = rrs.updateReportResult(resultablename, stFlag, temMap,
					reportTargetList, valueList, cstatusList, dtypeList,
					new String[] { dataIdlength[k] }, reportDate);
			valueList.clear();
			cstatusList.clear();
			dtypeList.clear();
			xvalueList.clear();
			xcstatusList.clear();
		}

		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		if (flag == 1) {
			out.print("0");
		} else {
			out.print("2");
		}
		out.flush();
		out.close();

		return null;
	}
	/**
	 * <!--鏁版嵁鍚屾 杩佺Щ琛ュ綍鏁版嵁 -->
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward reportMove(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering DataFill 'reportMove' method");
		}
		 String reportName = request.getParameter("reportName");
		 String reportid   = request.getParameter("reportId");
	  	 String levelFlag  = request.getParameter("levelFlag");
		 int idxid         = getUser(request).getOrganTreeIdxid();
		 String reportDate = request.getParameter("reportDate");
		 Long reportId = null;
		 if (request.getParameter("reportId") != null) {
			reportId = new Long(request.getParameter("reportId"));
		 }
	     String organId = request.getParameter("organCode");
		 if (organId == null) {
			organId = getUser(request).getOrganId();
		 }
		 String str=null;
		 String snum=null;
		 // 鎻掑叆绯荤粺鏃ュ織
		 User user = (User) request.getSession().getAttribute("user");
		 final String userName = user.getName();
		 if(reportId!=null){
			 Report	  report = rds.getReport(reportId);
			 reportName = report.getName();
			 sls.insertLog(user.getPkid() + "",userName,reportId + "",LogUtil.LogType_User_Operate,organId,"业务数据补录>数据同步",report.getReportType() + "");
		 }else{
			 sls.insertLog(user.getPkid() + "",userName,user.getOrganId() + "",LogUtil.LogType_User_Operate,organId,"业务数据补录>数据同步","-1" + "");
		 }
		 
		 boolean bool=  false;
		 if (reportid !=null) {
	    	 bool=rmv.reportMove(reportid);
			 snum=reportName+"已同步，单表每次同步5000条数据，";
		 }else{
			 bool=rmv.reportMove();
			 snum="批量同步，每次每张表1000条数据，"; 
		 }
		 if (bool) {
			 str="a";
		 }else{
			 str="b";
		 }
		 request.setAttribute("str", str);
		 request.setAttribute("snum", snum);
		 request.setAttribute("organId", organId);
		 request.setAttribute("dataDate", reportDate);
		 request.setAttribute("reportId", reportId);
		 return  getReportGuideMove(mapping, form, request, response);
	}
	
	/**
	 * 鏁版嵁鍚屾鍔熻兘 鐐瑰嚮 杩涘叆鏂规硶  鏌ョ湅鍏蜂綋鎻愪氦鏁版嵁淇℃伅
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward getReportGuideMove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try {
			if (log.isDebugEnabled()) {
				log.info("Entering Standard_DataGather 'getReportGuide' method");
			}
			String levelFlag = request.getParameter("levelFlag");
			ReportDataForm reportDataForm = (ReportDataForm) form;
			String openMode = request.getParameter("openmode");
			int idxid = getUser(request).getOrganTreeIdxid();
			String reportDate = reportDataForm.getDataDate();
			if (reportDate == null) {
				reportDate = request.getParameter("reportDate");
				reportDataForm.setDataDate(reportDate);
			}
			Long reportId = null;
			if (request.getParameter("reportId") != null) {
				reportId = new Long(request.getParameter("reportId"));
				reportDataForm.setReportId(reportId);
			}
			String organId = request.getParameter("organCode");

			if (organId == null) {
				organId = getUser(request).getOrganId();
				reportDataForm.setOrganId(organId);
			}
			OrganInfo organInfo = os.getOrganByCode(organId);
			request.setAttribute("organInfo", organInfo);
			User user = (User) request.getSession().getAttribute("user");
			reportDataForm.setFillUser(user.getName());
			reportDataForm.setPhone("");
			reportDataForm.setCheckUser(user.getName());
			String cstatus = "'0','3','5'";
			request.setAttribute("levelFlag", levelFlag);
			HttpSession session = request.getSession();
			if (reportDate == null) {
				reportDate = (String) session.getAttribute("logindate");
				reportDataForm.setDataDate(reportDate);
			}

			request.setAttribute("organInfo", organInfo);
			if (levelFlag != null && !"".equals(levelFlag)) {
				request.setAttribute("params", "&paramdate=" + reportDate
						+ "&paramorgan=" + organId + "&levelFlag=" + levelFlag);
			} else {
				request.setAttribute("params", "&paramdate=" + reportDate
						+ "&paramorgan=" + organId);
			}
			request.setAttribute("orgparam", "&date=" + reportDate);
			request.setAttribute("organId", organId);
			request.setAttribute("dataDate", reportDate);
			String systemcode = FuncConfig.getProperty("system.sysflag", "1");

			List<Object[]> reportList = null;

				reportList = rrs.getReportGuide(reportId, levelFlag,
						systemcode, reportDate, organId, idxid, user.getPkid());

			for (Object[] objects : reportList) {
				if (new Long(objects[2].toString()) > 0) {
					reportDate = DateUtil.formatDate(reportDate);
					StatusForm statusForm = new StatusForm();
					statusForm.setOrganId(objects[1].toString());
					statusForm.setReportId(objects[0].toString());
					statusForm.setFrequency(reportDate);
					int result = dfs.unLockDataStatus(statusForm);
				}
			}

			if( null == reportId){
				reportId = 10000L;
			}
			request.setAttribute("reportId", reportId);
			request.setAttribute("reportList", reportList);

			return mapping.findForward("reportMove");
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw, true));
			String str = sw.toString();
			request.setAttribute("errors", str);
			request.setAttribute("errorMessages", e);
			return mapping.findForward("error");
		}
	}
	
}
