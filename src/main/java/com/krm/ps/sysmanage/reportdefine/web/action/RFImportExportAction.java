package com.krm.ps.sysmanage.reportdefine.web.action;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.framework.common.web.util.UploadUtil;
import com.krm.ps.sysmanage.reportdefine.services.ReportDefineService;
import com.krm.ps.sysmanage.reportdefine.services.ReportFormatService;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportFormat;
import com.krm.ps.sysmanage.reportdefine.web.form.RFImportExportForm;
import com.krm.ps.util.SysConfig;
import com.krm.ps.util.FileUtil;
import com.krm.ps.util.ZipUtil;

/**
 * ReportFormatImportExportAction
 * 
 * @struts.action name="RFImportExportForm" path="/RFImportExportAction"
 *                scope="request" parameter="method"
 * 
 */
public class RFImportExportAction extends BaseAction {

	public ActionForward upload(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'upload' method");
		}
		RFImportExportForm f = (RFImportExportForm) form;
		FormFile formatFile = f.getRepFormatFile();

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(formatFile.getInputStream())));

		StringBuffer formatStr = new StringBuffer();
		String tem;
		while ((tem = reader.readLine()) != null) {
			formatStr.append(new String(tem.getBytes(), "GBK"));
		}
		reader.close();

		ReportFormat format = getRepFormatService()
				.getReportFormat(f.getPkId());

		try {
			format.setReportFormat(formatStr.toString());
			getRepFormatService().saveReportFormat(format);
		} catch (Exception e) {
			e.printStackTrace();
		}

		ReportDefineService rs = this.getReportDefineService();
		Report report = rs.getReport(format.getReportId());
		response.setContentType("text/html; charset=GBK");

		response.getWriter().println(
				"<html><body bgcolor=\"#eeeeee\"></body></html>");
		response.getWriter().println(
				"<script>alert('报表" + report.getCode()
						+ "的格式已成功导入');window.close()</script>");
		return null;
	}

	public ActionForward export(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'export' method");
		}
		RFImportExportForm f = (RFImportExportForm) form;
		ReportFormat format = getRepFormatService()
				.getReportFormat(f.getPkId());

		ReportDefineService rs = this.getReportDefineService();
		Report report = rs.getReport(format.getReportId());
		// ==LZ 修改报表编码和主码的位置，在导出的文件名中
		String defaultFileName = "format_" + report.getCode() + "_"
				+ f.getPkId();
		defaultFileName = URLEncoder.encode(defaultFileName, "UTF-8");

		response.setContentType("text/xml;charset=GBK");
		response.setHeader("Content-disposition", "attachment; filename="
				+ defaultFileName + ".xml");

		PrintWriter output = response.getWriter();
		output.println(format.getReportFormat());
		output.close();

		return null;
	}

	// 打包导出多个报表模板，wsx 9-2
	public ActionForward exportZipedFormats(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'exportZipedFormats' method");
		}
		try {
			String reporttype = request.getParameter("reporttype");

			Long type = Long.valueOf(reporttype);
			ReportFormatService mgr = getRepFormatService();
			List reporFormatList = mgr.getReportFormatsByType(type,true);

			HttpSession session = request.getSession();

			String exportedDir = "downLoadReportFormat";
			String realDir = session.getServletContext().getRealPath("/");
			realDir = FileUtil.makeNormalDirName(realDir);
			String realExportedDir = realDir + exportedDir;
			if (FileUtil.isExistsFile(realExportedDir)) {
				File reportFormatDir = new File(realExportedDir);
				UploadUtil.deleteFolder(reportFormatDir);
			}

			UploadUtil.makedirs(realExportedDir);

			ReportDefineService rs = this.getReportDefineService();
			type = (type.intValue() == 0) ? null : type;
			List reports = rs.getReportsByType(type);

			Map reportMap = new HashMap();
			Iterator rit = reports.iterator();
			while (rit.hasNext()) {
				Report r = (Report) rit.next();
				reportMap.put(r.getPkid(), r);
			}

			Iterator it = reporFormatList.iterator();
			while (it.hasNext()) {
				ReportFormat format = (ReportFormat) it.next();
				if (format.getPkId() == null) {
					continue;
				}

				if (format.getReportFormat() != null) {
					Report rep = (Report) reportMap.get(format.getReportId());
					String reportCode = (rep == null) ? "" : rep.getCode();
					String fileName = "format_" + reportCode + "_"
							+ format.getPkId();// 修改报表编码和主码的位置，在导出的文件名中，wsx
					// 11-20

					String enc = "GBK";
					if ("weblogic".equals(SysConfig.APPSERVER)
							&& "shandong".equals(SysConfig.PROVINCE)) {// TODO
						// enc = "UTF-8";
						fileName = "format_" + format.getReportId() + "_"
								+ format.getPkId();
					}
					FileUtil.makeFile(format.getReportFormat(), FileUtil
							.makeNormalDirName(realExportedDir)
							+ fileName + ".xml", enc);
				}
			}

			ZipUtil.zipDir(realExportedDir);
			response.setContentType("application/x-download");

			String typ = ("0".equals(reporttype)) ? "ALL" : reporttype;
			String zipFileName = "reportFormat_" + typ;
			zipFileName = URLEncoder.encode(zipFileName, "UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ (zipFileName + ".zip"));
			FileInputStream fis = new FileInputStream(realExportedDir + ".zip");

			OutputStream output = response.getOutputStream();

			byte[] b = new byte[1024];
			int i = 0;
			while ((i = fis.read(b)) > 0) {
				output.write(b, 0, i);
			}
			fis.close();
			output.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	// 压缩目录（java程序压缩目录，作为一个工具）
	public ActionForward zipedDir271828(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'zipedDir271828' method");
		}
		try {
			String dir2zip = request.getParameter("dir2zip");
			HttpSession session = request.getSession();
			String realExportedDir = dir2zip;
			if (dir2zip.indexOf('\\') == -1 && dir2zip.indexOf('/') == -1) {
				String realDir = session.getServletContext().getRealPath("/");
				realDir = FileUtil.makeNormalDirName(realDir);
				realExportedDir = realDir + dir2zip;
			}
			ZipUtil.zipDir(realExportedDir);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
