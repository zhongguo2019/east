package com.krm.ps.model.repfile.web.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.pentaho.di.core.exception.KettleException;
import org.pentaho.di.core.util.EnvUtil;
import org.pentaho.di.trans.Trans;
import org.pentaho.di.trans.TransMeta;

import sun.misc.BASE64Decoder;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.krm.ps.common.dto.DTO;
import com.krm.ps.common.exception.KRMException;
import com.krm.ps.common.exception.ParameterCheckException;
import com.krm.ps.common.process.KProcessHandler;
import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.model.datafill.services.DataFillService;
import com.krm.ps.model.repfile.process.CreateFileSearchProcess;
import com.krm.ps.model.repfile.process.ListSubTarskProcess;
import com.krm.ps.model.repfile.process.TarskProcess;
import com.krm.ps.model.repfile.service.RepFileService;
import com.krm.ps.model.repfile.vo.FtpDownLoad;
import com.krm.ps.model.repfile.web.form.RepFlFomatForm;
import com.krm.ps.model.reportdefine.services.DictionaryService;
import com.krm.ps.model.reportdefine.services.ReportDefineService;
import com.krm.ps.model.reportrule.services.ReportRuleService;
import com.krm.ps.model.sysLog.util.LogUtil;
import com.krm.ps.model.vo.CodeRepConfigure;
import com.krm.ps.model.vo.CodeRepJhgz;
import com.krm.ps.model.vo.CodeRepJhgzZf;
import com.krm.ps.model.vo.CodeRepSubmitalist;
import com.krm.ps.model.vo.KettleData;
import com.krm.ps.model.vo.RepFlfile;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.usermanage.vo.Dictionary;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.tarsk.service.TarskService;
import com.krm.ps.tarsk.util.Constant;
import com.krm.ps.tarsk.vo.SubTarskInfo;
import com.krm.ps.util.DateUtil;
import com.krm.ps.util.FuncConfig;
import common.Logger;

public class RepFileAction extends BaseAction {

	private Logger logger = Logger.getLogger(RepFileAction.class);
	
	ReportRuleService rts = (ReportRuleService) getBean("reportruleReportruleService");
	DataFillService dfs = (DataFillService) getBean("datafillDataFillService");

	/**
	 * 上报文件生成--列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward entercreateFileSerch(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try{
			logger.info("method:entercreateFileSerch");
			HttpSession session = request.getSession();
			// 修改之前上报文件的逻辑
			// 系统标识
			String systemId = (String) request.getSession().getAttribute(
					"system_id");
			// 获取数据日期
			String datevalue = (String) session.getAttribute("logindate");
			ReportDefineService rs = (ReportDefineService) getBean("rdreportDefineService");
			String levelFlag = request.getParameter("levelFlag");
			User user = getUser(request);
			Long userId = user.getPkid();
			List repList = rs.getDateOrganEditReportForYJH(
					datevalue.replaceAll("-", ""), null, null, userId, systemId,
					levelFlag);
			
			//报表按频度分类，目前只分日月频度后期可以添加季报、半年报、年报等
			//添加规则 "CODE_REP_REPORT"."FREQUENCY" IS '报表频度   1=月报 2=季报 3=半年报 4=年报 5=日报 6=旬报 7=周报 '
			List monthrepList = new ArrayList(); //月报集合
			List dayrepList = new ArrayList(); //日报集合
			
			// 查询按频度报送的机构
			DictionaryService ds = (DictionaryService) getBean("rddictionaryService");
			List funList = ds.getDics(777777);
			
			boolean flag = false ;
			for (int j = 0; j < funList.size(); j++) {
				Dictionary dic = (Dictionary)funList.get(j);
				 if((""+dic.getDicid()).equals(user.getOrganId())){  //判断是否按照频度报送  目前天津滨海总行 east4.0 分日报月报 ，其它支行按月报报送。
					 flag = true ;
				 }
			}
			if( flag){ 
				for (int i = 0; i < repList.size(); i++) {
					Report report =(Report)repList.get(i);
					
					//日报
					if("5".equals(report.getFrequency())){
						dayrepList.add(report);
					}
					//月报
					if("1".equals(report.getFrequency())){
						monthrepList.add(report);
					}
					
				}
			}else{
				monthrepList = repList ;
			}
			if(dayrepList.size()==0){
				request.setAttribute("offdayradio", "off");
			}else{
				request.setAttribute("offdayradio", "on");
			}
			request.setAttribute("dayrepList", dayrepList);
			request.setAttribute("monthrepList", monthrepList);
			
			request.setAttribute("begindate", datevalue);
			request.setAttribute("enddate", datevalue);
			request.setAttribute("condates", datevalue);
			request.setAttribute("organId", user.getOrganId());
			request.getSession().setAttribute("asrepList", repList);
			request.setAttribute("dayradiochecked",  request.getParameter("dayradiochecked"));
			
			/*EnCreateFileSerchProcess process=new EnCreateFileSerchProcess();
			try {
				DTO out=KProcessHandler.doProcess(request, response, process);
			} catch(ParameterCheckException e){
		         mapping.findForward("exception");
		     }catch(KRMException e){
		         mapping.findForward("error");
		     }*/
			
			return mapping.findForward("createfileSearch");
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
	 * 上报文件生成--生成文件
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward createFileSerch(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		
		// 获取生成文件的报文ID
		logger.info("method:createFileSerch ");
		RepFileService rpf = getRepFileservice();
		HttpSession session = request.getSession();
		// 获取报送模板的id
		String condate = request.getParameter("condates");
		if (condate == null) {
			condate = (String) session.getAttribute("logindate");
		}
//		// 选时间段进行生成
//		String begindate = request.getParameter("begindate");
//		String enddate = request.getParameter("enddate");
//		String batch = request.getParameter("batch");// 判断生成文件类型 XML或txt bat 等等
//		String cstatus = request.getParameter("cstatus");// 判断是增量上报还是全量上报 0增量1全量
		
		//String[] repIDarray = request.getParameterValues("reportId");
		String  repIDarrayers = request.getParameter("reportId");
		String[] repIDarray=repIDarrayers.trim().split(",");
		
		User user = (User) request.getSession().getAttribute("user");
		String organCode = request.getParameter("organId");
		if (organCode == null && "".equals(organCode)) {
			organCode = user.getOrganId();
		}
		if (repIDarray == null) {
			repIDarray = new String[] { request.getParameter("reportId") };
		}
		List<Report> reportListfo = (List) request.getSession().getAttribute("asrepList");
		//未锁定报表showlevel=1
		List listLock = rpf.getRepislock(organCode, condate);

		List reporNamets = null;
		List reporNametList = new ArrayList();
		List reporidsList2 = new ArrayList();//未进行审核的报表编码记录集合showlevel=2
       //获取页面报表id数组，showlevel=2
		
		for (int a = 0; a < repIDarray.length; a++) {
			repIDarray[a] = rpf.getreportId(new Long(repIDarray[a].toString()),
					"1").toString();
			List IfReportidlock = rpf.getIfridlock(organCode, condate,
					repIDarray[a]);
			if (IfReportidlock.size() == 0) {//状态表中是否存在审核记录
				repIDarray[a] = rpf.getreportId(
						new Long(repIDarray[a].toString()), "2").toString();//还原页面选取的报表id
				reporidsList2.add(repIDarray[a]);//未审核报表
			}
		}
		if (reporidsList2.size() == 0) {
			if (listLock.size() > 0) {
				List newReporidsList = new ArrayList();
				for (int i = 0; i < listLock.size(); i++) {
					newReporidsList.add(rpf.getreportId(
							new Long(listLock.get(i).toString()), "2")
							.toString());//转换成level=2的报表id
				}

				List reporidsList = new ArrayList();
				for (int i = 0; i < newReporidsList.size(); i++) {
					for (int a = 0; a < repIDarray.length; a++) {
						repIDarray[a] = rpf.getreportId(
								new Long(repIDarray[a].toString()), "2")
								.toString();
						if (String.valueOf(newReporidsList.get(i)).equals(
								repIDarray[a])) {
							reporidsList.add(newReporidsList.get(i));
						}
					}
				}
				if (reporidsList.size() > 0) {
					for (Report re : reportListfo) {
						for (int a = 0; a < reporidsList.size(); a++) {
							if (String.valueOf(re.getPkid()).equals(
									reporidsList.get(a))) {
								reporNametList.add(re.getName());
							}
						}
					}
					reporNamets = reporNametList;
				}
			}
		} else {
			for (Report re : reportListfo) {
				for (int a = 0; a < reporidsList2.size(); a++) {
					if (String.valueOf(re.getPkid()).equals(
							reporidsList2.get(a))) {
						reporNametList.add(re.getName());
					}
				}
			}
			reporNamets = reporNametList;
		}
		String reporNametstr ="";
		if (reporNamets != null) {
			 reporNametstr = "以下报表请先审核,在生成上报文件\n";
			for (int b = 0; b < reporNamets.size(); b++) {
				reporNametstr += "【"+reporNamets.get(b)+ "】";
				if(b<=(reporNamets.size()-2)){
				   reporNametstr +=  ",";
				}
			}
			request.setAttribute("reporNametstr", reporNametstr);
			request.setAttribute("dayradiochecked",  request.getParameter("dayradiochecked"));
			
		}
		
		if(reporNametstr == ""){
			
			List<Report> reportList = new ArrayList();
			// 获取页面选取报表对象集合
			reportList = rpf.getReports(repIDarrayers);
			//检测即将开始的子任务的业务重复性
			List<SubTarskInfo>	sList= rpf.getRuningSubTarsk(condate.replaceAll("-", ""), organCode, reportList);
			if(sList.size()>0){
				 reporNametstr = "以下报表正在生成中,请到上报文件生成查询菜单，查看生成状态\n";
				for(SubTarskInfo s:sList){
					for (Report report : reportList) {
						if(s.getReportId().equals(String.valueOf(report.getPkid()))){
							reporNametstr += "【"+report.getName()+ "】";
							 
						}
					}
					
				}
				request.setAttribute("reporNametstr", reporNametstr);
				request.setAttribute("dayradiochecked",  request.getParameter("dayradiochecked"));
				reporNamets = new ArrayList();
				reporNamets.add("true") ; //控制下面代码不执行。
			}
		}
		// 下一部代码开始
		if (reporNamets == null || reporNamets.size() == 0) {
//			List reportList = new ArrayList();
//			List repIDarrayList = new ArrayList();
//			for (int a = 0; a < repIDarray.length; a++) {
//				repIDarray[a] = rpf.getreportId(
//						new Long(repIDarray[a].toString()), "2").toString();
//				repIDarrayList.add(repIDarray[a]);
//			}
//			for (Report re : reportListfo) {
//				for (int a = 0; a < repIDarrayList.size(); a++) {
//					if (String.valueOf(re.getPkid()).equals(
//							repIDarrayList.get(a))) {
//						reportList.add(re);
//					}
//				}
//			}
//			String userName = user.getName();
//			// 插入系统日志
//			getSysLogService().insertLog(user.getPkid() + "", userName, "-1",
//					LogUtil.LogType_User_Operate, organCode,
//					"报送数据文件生成->上报文件生成", "-1");
//			logger.info("要生成的报表数量：" + reportList.size());
//			String systemid = (String) request.getSession().getAttribute(
//					"system_id");
//			List<RepFlfile> repList = rpf.createFile(begindate, enddate,
//					reportList, organCode, condate, request, systemid, batch,
//					user.getOrganTreeIdxid(), cstatus);
//			request.setAttribute("repList", repList);
//			request.setAttribute("succehints", "文件已经生成、请到文件下载里下载报送");
//			request.setAttribute("begindate", begindate);
//			request.setAttribute("enddate", enddate);
//			request.setAttribute("condates", condate);
//			return mapping.findForward("createfileprompt");
			
			
			//String forward="";
			CreateFileSearchProcess process=new CreateFileSearchProcess();
			request.getSession().setAttribute("createFileSchedule", process.getSchedule());
			try {
				DTO out=KProcessHandler.doProcess(request, response, process);
				//TarskProcess tProcess=new TarskProcess();
				//	DTO out2=KProcessHandler.doProcess(request, response, tProcess);
				/*String json=(String)out.get("tarskJson");
			response.setContentType("html/text;charSet=utf-8");
			PrintWriter pw=response.getWriter();
			pw.write(json);
			pw.close();*/
				//forward=(String)out.get("forwards");
			} catch(ParameterCheckException e){
				mapping.findForward("exception");
				e.printStackTrace();
			}catch(KRMException e){
				mapping.findForward("error");
				e.printStackTrace();
			}
			return mapping.findForward("createfileprompt");
		}
		
		return mapping.findForward("createfileSearch");
	}
	
	/**
	 * 上报文件生成--文件生成 进度
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward createFilePercent(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, String> percent = (Map<String, String>) request.getSession().getAttribute("createFileSchedule");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(JSONObject.fromObject(percent).toString());
		out.flush();
		out.close();
		return null;
	}

	private RepFileService getRepFileservice() {
		return (RepFileService) getBean("repfileservice");
	}

	/**
	 * 上报文件下载--列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward downloadFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		logger.info("method:createFileTank");
		String repId = request.getParameter("reportId");
		RepFileService rpf = getRepFileservice();
		logger.info("repId:" + repId);
		List<RepFlfile> repList = new ArrayList<RepFlfile>();// 报表
		String systemid = request.getParameter("systemid");
		request.setAttribute("systemid", systemid);
		RepFlfile repfile = new RepFlfile();
		repfile.setSysid(systemid);
		String organId = (String) request.getParameter("organId");
		if (organId == null) {
			organId = getUser(request).getOrganId();
		}
		int idxid = getUser(request).getOrganTreeIdxid();
		int IsAdmin = getUser(request).getIsAdmin();
		repList = rpf.getRepFlfile(repfile, organId, IsAdmin, idxid);
		request.setAttribute("repList", repList);
		return mapping.findForward("downloadfileList");
	}

	/**
	 * 上报文件下载--单个下载
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward downloadfile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		RepFileService rpf = getRepFileservice();
		RepFlfile repfile = new RepFlfile();
		logger.info("method:deldownloadfile");
		String pkid = request.getParameter("pkid");
		repfile.setPkid(Long.valueOf(pkid));
		String systemid = request.getParameter("systemid");
		repfile.setSysid(systemid);
		repfile = rpf.getRepFlfileOne(repfile);

		User user = (User) request.getSession().getAttribute("user");
		String userName = user.getName();

		// 插入系统日志
		getSysLogService().insertLog(user.getPkid() + "", userName, "-1",
				LogUtil.LogType_User_Operate, repfile.getOrgancode(),
				"报送数据文件生成->上报文件下载", "-1");

		// String
		// path=request.getSession().getServletContext().getRealPath("/ftpfile/batch"+repfile.getFilebath()+"/");
		String path = FuncConfig.getProperty("imp.report.filepath");
		String filename = path + "/" + repfile.getRepfilename();
		
		/*//判断文件是否存在
		File file = new File(filename);
		if(!file.exists()) { //文件不存在
		    System.out.println("文件"+repfile.getRepfilename()+"不存在"+request.getLocalAddr());
		    ftpdownLoad(path,repfile.getRepfilename());
		}*/
		
		OutputStream out = null;
		InputStream in = null;
		try {
			String fileName = repfile.getRepfilename();
			response.reset();
			response.setContentType("application/x-download");
			fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ fileName);
			out = response.getOutputStream();
			in = new FileInputStream(filename);
			byte[] tempbytes = new byte[1024];
			int byteread = 0;
			while ((byteread = in.read(tempbytes)) != -1) {
				out.write(tempbytes, 0, byteread);
			}
			// 更新下载次数
			repfile.setStatus("2"); // 设置状态
			rpf.updateRepFlfile(repfile);
			out.flush();

		} catch (Exception e) {
			// TODO: handle exception
			logger.info("method:deldownloadfile");
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e1) {
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (Exception e1) {
				}
			}
		}

		return null;
	}

	/**
	 * 上报文件下载--批量下载
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward piLiangdownloadfile(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		RepFileService rpf = getRepFileservice();
		logger.info("Piliangdownloadfile");
		String pkid = request.getParameter("mm"); // 获要打包的报文id
		List repfilelist = rpf.getRepFlfileOne(pkid.substring(0,
				pkid.length() - 1));
		User user = (User) request.getSession().getAttribute("user");
		String userName = user.getName();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String zipName = dateFormat.format(new Date()) + ".zip";
		// String zippath = request.getSession().getServletContext()
		// .getRealPath("/ftpfile/");
		String zippath = FuncConfig.getProperty("imp.report.filepath");
		zippath = zippath + "/" + zipName;
		ZipOutputStream zipout = new ZipOutputStream(new FileOutputStream(zippath));
		
		OutputStream out = null ;
		InputStream in = null ;
		BufferedOutputStream bouts = null;
		InputStream zipin = null ;
		BufferedInputStream bins = null;
//		for (int i = 0; i < repfilelist.size(); i++) {
//			RepFlfile repfiles = (RepFlfile) repfilelist.get(i);
//			String nametextlog=repfiles.getRepfilename();
//			for(File file:files){
//				if(file.getName().equals(nametextlog)){
//					file.delete();
//				}
//			}
//		}
		try {
			// 生成压缩包
			for (int i = 0; i < repfilelist.size(); i++) {
				RepFlfile repfile = (RepFlfile) repfilelist.get(i);
				// String
				// path=request.getSession().getServletContext().getRealPath("/ftpfile/batch"+repfile.getFilebath()+"/");
				String path = FuncConfig.getProperty("imp.report.filepath");
				String filename = path + "/" + repfile.getRepfilename();
				String fileName = repfile.getRepfilename();
				
				/*//判断文件是否存在
				File file = new File(filename);
				if(!file.exists()) { //文件不存在
				    System.out.println("文件"+repfile.getRepfilename()+"不存在"+request.getLocalAddr());
				    ftpdownLoad(path,fileName);
				}*/
				
				zipout.putNextEntry(new ZipEntry(fileName));
				 in = new FileInputStream(filename);
				byte[] tempbytes = new byte[1024];
				int byteread = 0;
				while ((byteread = in.read(tempbytes)) != -1) {
					zipout.write(tempbytes, 0, byteread);
				}
				// 更新下载次数
				rpf.updateRepFlfile(repfile);
				zipout.closeEntry();
				in.close();
			}
			if( in !=null){
				in.close();
			}
			zipout.close();

			
			// 下载 压缩包
			response.reset();
			response.setContentType("application/x-download");
			zipName = java.net.URLEncoder.encode(zipName, "UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename="
					+ zipName);
			
			 out = response.getOutputStream();
			 bouts = new BufferedOutputStream(out);
			
			 zipin = new FileInputStream(zippath);
			 bins = new BufferedInputStream(zipin);
			
			byte[] tempbytes = new byte[1024];
			int byteread = 0;
			while ((byteread = bins.read(tempbytes)) != -1) {
				bouts.write(tempbytes, 0, byteread);
			}
			bouts.flush();
			
			
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			e.printStackTrace(new PrintWriter(sw, true));
			String str = sw.toString();
			request.setAttribute("errors", str);
			request.setAttribute("errorMessages", e);
			return mapping.findForward("error");

		}finally{
			if(	bouts!=null){
				bouts.close();
			}
			if(	bins!=null){
				bins.close();
			}
			if( zipin!= null){
				zipin.close();
			}
			if( out!= null){
				out.close();
			}
			if( in !=null){
				in.close();
			}
		}
		
		//下载完成删除zip 包
		File filee = new File(zippath);
		filee.delete();
		
		return null;
	}

	/**
	 * 上报文件删除--批量删除 ，（不对文件做真正删除，防止数据丢失。只删除下载文件信息）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward piLiangdelfile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'piLiangdelfile' method");
		}

		String pkid = request.getParameter("mm");
		RepFileService rpf = getRepFileservice();
		//----新增 删除目录下报送文件------
		String path = FuncConfig.getProperty("imp.report.filepath");
		File folder = new File(path);
		File[] files = folder.listFiles();
		List repfilelist = rpf.getRepFlfileOne(pkid.substring(0, pkid.length() - 1));
		for (int i = 0; i < repfilelist.size(); i++) {
			RepFlfile repfiles = (RepFlfile) repfilelist.get(i);
			String nametextlog=repfiles.getRepfilename();
			for(File file:files){
				if(file.getName().equals(nametextlog)){
					file.delete();
				}
			}
		}
		
		// -----end ------------------------
		rpf.delRepFlfile(pkid.substring(0, pkid.length() - 1));
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print("删除成功！");
		out.flush();
		out.close();
		return null;
	}

	/**
	 * 上报文件下载--单个删除
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deldownloadfile(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'deldownloadfile' method");
		}
		String pkid = request.getParameter("pkid");
		RepFileService rpf = getRepFileservice();
		//----新增 删除目录下报送文件------
		String path = FuncConfig.getProperty("imp.report.filepath");
		File folder = new File(path);
		File[] files = folder.listFiles();
		List repfilelist = rpf.getRepFlfileOne(pkid);
		for (int i = 0; i < repfilelist.size(); i++) {
			RepFlfile repfiles = (RepFlfile) repfilelist.get(i);
			String nametextlog=repfiles.getRepfilename();
			for(File file:files){
				if(file.getName().equals(nametextlog)){
					file.delete();
				}
			}
		}
		
		// -----end ------------------------
		RepFlfile repfile = new RepFlfile();
		repfile.setPkid(Long.valueOf(pkid));
		rpf.delRepFlfile(repfile);
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print("删除成功！");
		out.flush();
		out.close();
		return null;
	}

	public ActionForward openKettle(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'openKettle' method");
		}
		RepFileService rpf = getRepFileservice();

		List<KettleData> kdlist = rpf.getAll();
		request.setAttribute("kdlist", kdlist);
		return mapping.findForward("openKettle");
	}

	public ActionForward openattach(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("openattach");
	}

	public ActionForward uploadKtr(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws UnsupportedEncodingException, IOException {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'uploadKtr' method");
		}

		RepFlFomatForm repFlFomatForm = (RepFlFomatForm) form;

		HttpSession session = request.getSession();
		String oData = request.getParameter("oData");

		User user = (User) request.getSession().getAttribute("user");
		String ktrTime = (String) session.getAttribute("logindate");
		FormFile data = repFlFomatForm.getDataFile();
		String ktrName = repFlFomatForm.getKtrName();
		oData = new String(oData.getBytes("ISO-8859-1"), "UTF-8");
		String path = FuncConfig.getProperty("import.ktr.path");
		String ktrPath = path + "/" + ktrName + "_"
				+ new SimpleDateFormat("yyyyMMddHHMMSS").format(new Date())
				+ oData.substring(oData.lastIndexOf("."));
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}
		RepFileService rpf = getRepFileservice();
		try {
			FileOutputStream os = new FileOutputStream(ktrPath);
			os.write(data.getFileData());
			os.flush();
			os.close();
			InputStream is = null;
			is = new FileInputStream(ktrPath);
			byte[] attribute5 = new byte[is.available()];
			if (attribute5.length > 0) {
				is.read(attribute5);
			}

			KettleData kettleData = new KettleData();
			kettleData.setUserName(user.getName());
			kettleData.setLogonName(user.getLogonName());
			kettleData.setKtrTime(ktrTime);
			kettleData.setKtrName(repFlFomatForm.getKtrName());
			kettleData.setKtrRemark(repFlFomatForm.getKtrRemark());
			kettleData.setKtrPath(ktrPath);
			kettleData.setAttribute1("1");
			kettleData.setAttribute5(attribute5);
			rpf.savektrForm(kettleData);
			is.close();
			request.setAttribute("flag", "1");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("flag", "6");
			request.setAttribute("message", e.getMessage());
		}
		return mapping.findForward("openattach");
		/*
		 * List<KettleData> kdlist = rpf.getAll();
		 * request.setAttribute("kdlist", kdlist); return
		 * mapping.findForward("openKettle");
		 */
	}

	public ActionForward activdKtr(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, KettleException {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'activdKtr' method");
		}
		JSONObject json = new JSONObject();
		String pkid = request.getParameter("pkid");
		String ktrpath = request.getParameter("ktrPath");
		String temppath = FuncConfig.getProperty("import.ktr.temp.path");
		String filepath = temppath
				+ ktrpath.substring(ktrpath.lastIndexOf("/"));
		File f = new File(temppath);
		if (!f.exists()) {
			f.mkdir();
		}
		RepFileService rpf = getRepFileservice();
		byte[] attribute5 = rpf.getAttribute5(pkid);
		FileOutputStream os = new FileOutputStream(filepath);
		os.write(attribute5);
		os.flush();
		os.close();
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		User user = (User) request.getSession().getAttribute("user");
		try {
			// KettleEnvironment.init();
			EnvUtil.environmentInit();
			TransMeta transMeta = new TransMeta(filepath);
			Trans trans = new Trans(transMeta);
			trans.execute(null);
			trans.waitUntilFinished();
			rpf.addAttribute(pkid, "2", null, date, user.getName());
			json.put("flag", 2);
		} catch (Exception e) {
			rpf.addAttribute(pkid, "3", e.getMessage(), null, null);
			e.printStackTrace();
			json.put("flag", 3);
			json.put("message", e.getMessage());
		} finally {
			String Json = JSONArray.fromObject(json).toString();
			PrintWriter writer = response.getWriter();
			writer.write(Json);
			writer.close();
		}
		/*
		 * String transFileName = "d://menu.ktr";//
		 * 
		 * try { KettleEnvironment.init(); TransMeta transMeta = new
		 * TransMeta(transFileName); Trans trans = new Trans(transMeta);
		 * trans.execute(null); trans.waitUntilFinished(); } catch (Exception e)
		 * { e.printStackTrace(); }finally{ String Json =
		 * JSONArray.fromObject(json).toString(); PrintWriter writer =
		 * response.getWriter(); writer.write(Json); writer.close(); }
		 */

		return null;
	}

	public ActionForward delktr(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'delktr' method");
		}
		JSONObject json = new JSONObject();
		String pkid = request.getParameter("pkid");
		RepFileService rpf = getRepFileservice();
		try {
			rpf.delKtr(pkid);
			json.put("flag", 4);
		} catch (Exception e) {
			e.printStackTrace();
			json.put("flag", 5);
			json.put("message", e.getMessage());
		}
		String Json = JSONArray.fromObject(json).toString();
		PrintWriter writer = response.getWriter();
		writer.write(Json);
		writer.close();
		return null;
	}

	public ActionForward queryktr(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'queryktr' method");
		}
		String ktrname = request.getParameter("ktrnamed");
		String ktrremark = request.getParameter("ktrremarked");
		String ktrtime = request.getParameter("ktrtimed");
		RepFileService rpf = getRepFileservice();
		List<KettleData> kdlist = rpf.queryktr(ktrname, ktrremark, ktrtime);
		request.setAttribute("kdlist", kdlist);
		return mapping.findForward("openKettle");
	}

	
	/**
	 * 查看子任务信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showMainTarsks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//ListSubTarskProcess process=new ListSubTarskProcess();
		TarskProcess tarskProcess=new TarskProcess();
		String json="";
		try {
			DTO dt=KProcessHandler.doProcess(request, response, tarskProcess);
			json=(String)dt.get("tarskJson");
		}catch
		(KRMException e){
	         mapping.findForward("error");
		}
		 PrintWriter out= response.getWriter();
		 out.write(json);
		 out.close();
		return null ;
	}
	
	public ActionForward showSubTarsks(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ListSubTarskProcess lpos=new ListSubTarskProcess();
		try {
		DTO out=KProcessHandler.doProcess(request, response, lpos);
		}catch
		(KRMException e){
	         mapping.findForward("error");
		}
		return mapping.findForward("listSubTarsk");
	}
	
	
	public  void ftpdownLoad(String path, String fillname) throws Exception{
	    boolean flag=false; 
	  
  		String ip =FuncConfig.getProperty("ftp.ip1"); // "192.168.0.51";
  		int port = 21;
		String userNames = FuncConfig.getProperty("ftp.name1"); // "qwk";
		String password  = FuncConfig.getProperty("ftp.password1"); // "qwk";
		String destinationFile ="C:\\ftpfile\\";// path; //文件要放的位置
		String remoteRoot ="\\ftp\\";// path;// "\\ftp\\"; //ftp文件的位置

		FtpDownLoad fUp = new FtpDownLoad(ip, port, userNames, password);
		fUp.login();
		fUp.sendCommand(remoteRoot);
		List fileList = fUp.fileNames(remoteRoot);
		for (int i = 0; i < fileList.size(); i++) {
			String fileName = fileList.get(i).toString();
			System.out.println(fileName);
			if (fillname.equals(fileName)) {//将想要的文件放到 path下
				String tempFile = destinationFile + fileName;
				fUp.downFile(fileName, tempFile);
				flag=true;
			}
		}
		fUp.logout();
		
		if (!flag) {
			 ip =FuncConfig.getProperty("ftp.ip2"); // "192.168.0.50";
			 userNames = FuncConfig.getProperty("ftp.name2"); // "qwk";
			 password  = FuncConfig.getProperty("ftp.password2"); // "qwk";
		
		    fUp = new FtpDownLoad(ip, port, userNames, password);
			fUp.login();
			fUp.sendCommand(remoteRoot);
		    fileList = fUp.fileNames(remoteRoot);
			for (int i = 0; i < fileList.size(); i++) {
				String fileName = fileList.get(i).toString();
				System.out.println(fileName);
				if (fillname.equals(fileName)) {//将想要的文件放到 path下
					flag=true;
					String tempFile = destinationFile + fileName;
					fUp.downFile(fileName, tempFile);
				}
			}
			fUp.logout();
		}
  }
	
	
	/**
	 * 
	 * 生成报文前配置
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listRepConfigure (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try{
			if (log.isDebugEnabled()) {
				log.info("Entering 'listRepConfigure' method");
			}

			HttpSession session = request.getSession();
			RepFileService rpf = getRepFileservice();
			String levelFlag = "2";
			
			String systemcode = FuncConfig.getProperty("system.sysflag", "2");
			User user = getUser(request);
			Long userId = user.getPkid();

			String loginDate = (String) session.getAttribute("logindate");
			List reportList = rts.getDateOrganEditReportForStandard(loginDate.replaceAll("-", ""), userId, systemcode, levelFlag);
			List reportList1 = reportList;

		
			CodeRepConfigure codeRepConfigure = new CodeRepConfigure();
			
			codeRepConfigure.setOrgan_id(request.getParameter("organ_id"))  ;
			codeRepConfigure.setReport_id(request.getParameter("report_id"));
			codeRepConfigure.setTarget_id(request.getParameter("target_id"));
			codeRepConfigure.setRtype(request.getParameter("rtype"));
			codeRepConfigure.setThreshold(request.getParameter("threshold"));
			
			List<CodeRepConfigure> repconfig = rpf.getRepConfigure(codeRepConfigure);
			
			 
			request.setAttribute("reportList", reportList1);
			request.setAttribute("repconfig", repconfig);
			request.setAttribute("reportid", codeRepConfigure.getReport_id());
			request.setAttribute("target_id", codeRepConfigure.getTarget_id());
			request.setAttribute("threshold", codeRepConfigure.getThreshold());
			request.setAttribute("rtype", codeRepConfigure.getRtype());
		
			return mapping.findForward("listRepConfigure");
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
	 * 删除生产报文配置
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward delRepConfigure(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
			try{
				if (log.isDebugEnabled()) {
					log.debug("Entering 'delRepConfigure' method");
				}
				String pkid = request.getParameter("pkid");
				if(StringUtils.isBlank(pkid)){
					pkid = "0" ;
				}
			     	RepFileService rpf = getRepFileservice();
				 
					rpf.delRepConfigure(new Long(pkid));  //删除
					
					
				HttpSession session = request.getSession();
				String levelFlag = "2";
				
				String systemcode = FuncConfig.getProperty("system.sysflag", "2");
				User user = getUser(request);
				Long userId = user.getPkid();

				String loginDate = (String) session.getAttribute("logindate");
				List reportList = rts.getDateOrganEditReportForStandard(loginDate.replaceAll("-", ""), userId, systemcode, levelFlag);
				List reportList1 = reportList;

			
				CodeRepConfigure codeRepConfigure = new CodeRepConfigure();
				
				codeRepConfigure.setOrgan_id(request.getParameter("organ_id"))  ;
				codeRepConfigure.setReport_id(request.getParameter("report_id"));
				codeRepConfigure.setTarget_id(request.getParameter("target_id"));
				codeRepConfigure.setRtype(request.getParameter("rtype"));
				codeRepConfigure.setThreshold(request.getParameter("threshold"));
				
				List<CodeRepConfigure> repconfig = rpf.getRepConfigure(codeRepConfigure);
				
				 
				request.setAttribute("reportList", reportList1);
				request.setAttribute("repconfig", repconfig);
				request.setAttribute("reportid", codeRepConfigure.getReport_id());
				request.setAttribute("target_id", codeRepConfigure.getTarget_id());
				request.setAttribute("threshold", codeRepConfigure.getThreshold());
				request.setAttribute("rtype", codeRepConfigure.getRtype());
				request.setAttribute("isok", "1");
			
				return mapping.findForward("listRepConfigure");
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
	 * 添加生产报文配置
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward saveRepConfigure(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		try{
			if (log.isDebugEnabled()) {
				log.debug("Entering 'delRepConfigure' method");
			}
			RepFileService rpf = getRepFileservice();
			
	        CodeRepConfigure codeRepConfigure = new CodeRepConfigure();
			
			codeRepConfigure.setOrgan_id(request.getParameter("organ_id"))  ;
			codeRepConfigure.setReport_id(request.getParameter("report_id"));
			codeRepConfigure.setTarget_id(request.getParameter("target_id"));
			codeRepConfigure.setRtype(request.getParameter("rtype"));
			codeRepConfigure.setThreshold(request.getParameter("threshold"));
			codeRepConfigure.setOrgan_name(request.getParameter("organ_name"));
			codeRepConfigure.setReport_name(request.getParameter("report_name"));
			codeRepConfigure.setTarget_name(request.getParameter("target_name"));
			
		    rpf.saveRepConfigure(codeRepConfigure) ;  //保存
				
				
			HttpSession session = request.getSession();
			String levelFlag = "2";
			
			String systemcode = FuncConfig.getProperty("system.sysflag", "2");
			User user = getUser(request);
			Long userId = user.getPkid();

			String loginDate = (String) session.getAttribute("logindate");
			List reportList = rts.getDateOrganEditReportForStandard(loginDate.replaceAll("-", ""), userId, systemcode, levelFlag);
			List reportList1 = reportList;

		
		    codeRepConfigure = new CodeRepConfigure();
				
			codeRepConfigure.setOrgan_id(request.getParameter("organ_id"))  ;
			codeRepConfigure.setReport_id(request.getParameter("report_id"));
			
			List<CodeRepConfigure> repconfig = rpf.getRepConfigure(codeRepConfigure);
			
			 
			request.setAttribute("reportList", reportList1);
			request.setAttribute("repconfig", repconfig);
			request.setAttribute("reportid", codeRepConfigure.getReport_id());
			request.setAttribute("target_id", codeRepConfigure.getTarget_id());
			request.setAttribute("threshold", codeRepConfigure.getThreshold());
			request.setAttribute("rtype", codeRepConfigure.getRtype());
			request.setAttribute("isok", "2");
		
			return mapping.findForward("listRepConfigure");
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
	 * 添加生产报文配置
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ActionForward listRepTarget(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'listRepTarget' method");
		}
		JSONObject json = new JSONObject();
		
		String reportid =request.getParameter("report_id");
		 
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain;charset=UTF-8");

		RepFileService rpf = getRepFileservice();
		List tarGetlist = null ;
		if(StringUtils.isNotBlank(reportid)){
			 tarGetlist = rpf.listRepTarget(reportid);
		}
		
	    json.put("tarGetlist", tarGetlist);
		 
		String Json = JSONArray.fromObject(json).toString();
		PrintWriter writer = response.getWriter();
		writer.write(Json);
		writer.close();
		return null;
	}
	
	/**
	 * 
	 * 检核数据明细查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listRepJhgz (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try{
			if (log.isDebugEnabled()) {
				log.info("Entering 'listRepJhgz' method");
			}

			@SuppressWarnings("unused")
			String displaypage = request.getParameter("d-5480-p");
		        
		       
			HttpSession session = request.getSession();
			RepFileService rpf = getRepFileservice();
			String levelFlag = "1";
			
			String systemcode = FuncConfig.getProperty("system.sysflag", "2");
			User user = getUser(request);
			Long userId = user.getPkid();

			String loginDate = (String) session.getAttribute("logindate");
			List reportList = rts.getDateOrganEditReportForStandard(loginDate.replaceAll("-", ""), userId, systemcode, levelFlag);
			List reportList1 = reportList;

			String databatch = request.getParameter("databatch");
			if(StringUtils.isBlank(databatch)){
				databatch = loginDate ;
			}
		
		    String organId = request.getParameter("organ_id");
		    if(StringUtils.isBlank(organId)){
		    	organId = user.getOrganId();
		    }
			CodeRepJhgz codeRepJhgz = new CodeRepJhgz();
			
			codeRepJhgz.setOrgan_id(organId)  ;
			codeRepJhgz.setReport_id(request.getParameter("report_id"));
			codeRepJhgz.setTarget_id(request.getParameter("target_id"));
			codeRepJhgz.setDatabatch(databatch);
			
			
			int idxid = getUser(request).getOrganTreeIdxid();
			int IsAdmin = getUser(request).getIsAdmin();
			List<CodeRepJhgz> repconfig = rpf.getRepJhgz(codeRepJhgz ,IsAdmin ,idxid);
			
			 
			request.setAttribute("reportList", reportList1);
			request.setAttribute("repconfig", repconfig);
			request.setAttribute("reportid", codeRepJhgz.getReport_id());
			request.setAttribute("target_id", codeRepJhgz.getTarget_id());
			request.setAttribute("databatch", codeRepJhgz.getDatabatch());
			request.setAttribute("organ_id", organId);
			request.setAttribute("displaypage", displaypage);
		
			return mapping.findForward("listRepJhgz");
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
	 * 检核数据修改
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listRepJhgzAdd (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try{
			RepFileService rpf = getRepFileservice();

			String pkid = request.getParameter("urlpkid");
			String urlreasons = request.getParameter("urlreasons");
		
		    rpf.updateRepJhgz(pkid, urlreasons); //保存
			
			if (log.isDebugEnabled()) {
				log.info("Entering 'listRepJhgzAdd' method");
			}

			HttpSession session = request.getSession();
			
			User user = getUser(request);

			String loginDate = (String) session.getAttribute("logindate");

			String databatch = request.getParameter("databatch");
			if(StringUtils.isBlank(databatch)){
				databatch = loginDate ;
			}
			
			String organId = request.getParameter("organ_id");
		    if(StringUtils.isBlank(organId)){
		    	organId = user.getOrganId();
		    }
			CodeRepJhgz codeRepJhgz2 = rpf.selectRepJhgz(pkid);
			String displaypage = request.getParameter("displaypage");
			
		 
			request.setAttribute("displaypage", displaypage);
			request.setAttribute("codeRepJhgz2", codeRepJhgz2);
			
			request.setAttribute("Freportid", request.getParameter("report_id"));
			request.setAttribute("Ftarget_id", request.getParameter("target_id"));
			request.setAttribute("Forgan_id", organId);
			request.setAttribute("Fdatabatch", databatch); 
 			request.setAttribute("isok", "1");
		
			return mapping.findForward("listRepJhgzOne");
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
	 * 检核数据明细查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listRepJhgzOne (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try{
			RepFileService rpf = getRepFileservice();

			String pkid = request.getParameter("urlpkid");
			request.setAttribute("Freportid", request.getParameter("report_id"));
			request.setAttribute("Ftarget_id", request.getParameter("target_id"));
			request.setAttribute("Forgan_id", request.getParameter("organ_id"));
			request.setAttribute("Fdatabatch", request.getParameter("databatch")); 
			
			if (log.isDebugEnabled()) {
				log.info("Entering 'listRepJhgzOne' method");
			}
			
			 CodeRepJhgz codeRepJhgz2 = rpf.selectRepJhgz(pkid);
			
			 
			request.setAttribute("codeRepJhgz2", codeRepJhgz2);
			request.setAttribute("displaypage", request.getParameter("displaypage"));
			
			return mapping.findForward("listRepJhgzOne");
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
	 * 导出检核数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward exportTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		RepFileService rpf = getRepFileservice();
		User user = getUser(request);
		String organId = request.getParameter("organ_id");
	    if(StringUtils.isBlank(organId)){
	    	organId = user.getOrganId();
	    }
		
	    String loginDate = (String) session.getAttribute("logindate");
	    String databatch = request.getParameter("databatch");
		if(StringUtils.isBlank(databatch)){
			databatch = loginDate ;
		}
	
	    
		CodeRepJhgz codeRepJhgz = new CodeRepJhgz();
		
		codeRepJhgz.setOrgan_id(organId)  ;
		codeRepJhgz.setReport_id(request.getParameter("report_id"));
		codeRepJhgz.setTarget_id(request.getParameter("target_id"));
		codeRepJhgz.setDatabatch(databatch);
		String organ_name = request.getParameter("organ_name") ;
		if(StringUtils.isBlank(organ_name)){
			organ_name = "滨海农商行";
		}
		
		int idxid = getUser(request).getOrganTreeIdxid();
		int IsAdmin = getUser(request).getIsAdmin();
		
		List<CodeRepJhgz> repconfig = rpf.getRepJhgz(codeRepJhgz,IsAdmin,idxid);

		String[] titles = new String[10];
		titles[0] = "检核规则序号";	
		titles[1] = "数据批次";	 
		titles[2] = "检核规则";	 
		titles[3] = "检核表名";	 
		titles[4] = "检核字段名";	 
		titles[5] = "报送要求的定义描述"; 
		titles[6] = "错误条数"; 
		titles[7] = "总量";
		titles[8] = "错误率";
		titles[9] = "错误数据原因解释";	 
		 
		
		HSSFWorkbook wb = rpf.getXlsWork(repconfig, titles, organ_name);
		String fileName = "";
		try {
			String filestr = organ_name+"字段校验结果" ;
			fileName = new String( filestr.getBytes("gb2312"),"iso8859-1");
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
	
	
	/**
	 * 
	 * 检核数据总分核对查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listRepJhgzZf (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try{
			if (log.isDebugEnabled()) {
				log.info("Entering 'listRepJhgz' method");
			}

			@SuppressWarnings("unused")
			String displaypage = request.getParameter("d-5480-p");
			request.setAttribute("displaypage", displaypage);
			
			HttpSession session = request.getSession();
			RepFileService rpf = getRepFileservice();
			String levelFlag = "1";
			
			String systemcode = FuncConfig.getProperty("system.sysflag", "2");
			User user = getUser(request);
			Long userId = user.getPkid();

			String loginDate = (String) session.getAttribute("logindate");
			List reportList = rts.getDateOrganEditReportForStandard(loginDate.replaceAll("-", ""), userId, systemcode, levelFlag);
			List reportList1 = reportList;

			String databatch = request.getParameter("databatch");
			if(StringUtils.isBlank(databatch)){
				databatch = loginDate ;
			}
		
			String organId = request.getParameter("organ_id");
		    if(StringUtils.isBlank(organId)){
		    	organId = user.getOrganId();
		    }
		    int idxid = getUser(request).getOrganTreeIdxid();
			int IsAdmin = getUser(request).getIsAdmin();
			
			CodeRepJhgzZf codeRepJhgzZf = new CodeRepJhgzZf();
			
			codeRepJhgzZf.setOrgan_id(organId)  ;
			codeRepJhgzZf.setReport_id(request.getParameter("report_id"));
			codeRepJhgzZf.setTarget_id(request.getParameter("target_id"));
			codeRepJhgzZf.setDatabatch(databatch);
			
			
			List<CodeRepJhgzZf> repconfig = rpf.getRepJhgzZf(codeRepJhgzZf,IsAdmin,idxid);
			
			 
			request.setAttribute("reportList", reportList1);
			request.setAttribute("repconfig", repconfig);
			request.setAttribute("reportid", codeRepJhgzZf.getReport_id());
			request.setAttribute("target_id", codeRepJhgzZf.getTarget_id());
			request.setAttribute("databatch", databatch);
			request.setAttribute("organ_id", organId);
		
			return mapping.findForward("listRepJhgzZf");
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
	 * 检核数据修改总分核对查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listRepJhgzZfAdd (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try{
			RepFileService rpf = getRepFileservice();

			String pkid = request.getParameter("urlpkid");
			String urlreasons = request.getParameter("urlreasons");
		
		    rpf.updateRepJhgzZf(pkid, urlreasons); //保存
			
			if (log.isDebugEnabled()) {
				log.info("Entering 'listRepJhgzZfAdd' method");
			}

			HttpSession session = request.getSession();
		
			User user = getUser(request);

			String loginDate = (String) session.getAttribute("logindate");

			String databatch = request.getParameter("databatch");
			if(StringUtils.isBlank(databatch)){
				databatch = loginDate ;
			}
			
			String organId = request.getParameter("organ_id");
		    if(StringUtils.isBlank(organId)){
		    	organId = user.getOrganId();
		    }
			CodeRepJhgzZf codeRepJhgz2 = rpf.selectRepJhgzZf(pkid);
		
			String displaypage = request.getParameter("displaypage");
		
			request.setAttribute("displaypage", displaypage);
			request.setAttribute("codeRepJhgz2", codeRepJhgz2);
			request.setAttribute("Freportid", request.getParameter("report_id"));
			request.setAttribute("Ftarget_id", request.getParameter("target_id"));
			request.setAttribute("Forgan_id", organId);
			request.setAttribute("Fdatabatch", databatch); 
			request.setAttribute("isok", "1");
		
			return mapping.findForward("listRepJhgzZfOne");
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
	 * 检核数据明细查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listRepJhgzZfOne (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try{
			RepFileService rpf = getRepFileservice();

			String pkid = request.getParameter("urlpkid");
			 
			if (log.isDebugEnabled()) {
				log.info("Entering 'listRepJhgzZfOne' method");
			}
			
			 CodeRepJhgzZf codeRepJhgzZf2 = rpf.selectRepJhgzZf(pkid);
			
			 
			request.setAttribute("codeRepJhgz2", codeRepJhgzZf2);
			String displaypage = request.getParameter("displaypage");
			request.setAttribute("displaypage", displaypage);
			request.setAttribute("Freportid", request.getParameter("report_id"));
			request.setAttribute("Ftarget_id", request.getParameter("target_id"));
			request.setAttribute("Forgan_id", request.getParameter("organ_id"));
			request.setAttribute("Fdatabatch", request.getParameter("databatch")); 
			
			return mapping.findForward("listRepJhgzZfOne");
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
	 * 导出检核数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward exportTemplateZf(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		RepFileService rpf = getRepFileservice();
		User user = getUser(request);
		String organId = request.getParameter("organ_id");
	    if(StringUtils.isBlank(organId)){
	    	organId = user.getOrganId();
	    }
	    int idxid = getUser(request).getOrganTreeIdxid();
		int IsAdmin = getUser(request).getIsAdmin();
		String loginDate = (String) session.getAttribute("logindate");

		String databatch = request.getParameter("databatch");
		if(StringUtils.isBlank(databatch)){
			databatch = loginDate ;
		}
		
		CodeRepJhgzZf codeRepJhgzZf = new CodeRepJhgzZf();
		
		codeRepJhgzZf.setOrgan_id(organId)  ;
		codeRepJhgzZf.setReport_id(request.getParameter("report_id"));
		codeRepJhgzZf.setTarget_id(request.getParameter("target_id"));
		codeRepJhgzZf.setDatabatch(databatch);
		String organ_name = request.getParameter("organ_name") ;
		if(StringUtils.isBlank(organ_name)){
			organ_name = "滨海农商行";
		}
		List<CodeRepJhgzZf> repconfig = rpf.getRepJhgzZf(codeRepJhgzZf,IsAdmin,idxid);

		String[] titles = new String[11];
		titles[0] = "序号";	
		titles[1] = "数据批次";	 
		titles[2] = "检核规则";	 
		titles[3] = "检核表名";	 
		titles[4] = "检核要素";	 
		titles[5] = "一级科目名称"; 
		titles[6] = "分户账数值"; 
		titles[7] = "总账数值";
		titles[8] = "差额";
		titles[9] = "差异原因解释";
		titles[10] = "pkid";
		 
		
		HSSFWorkbook wb = rpf.getXlsWorkZf(repconfig, titles, organ_name);
		String fileName = "";
		try {
			String filestr = organ_name+"总分核对结果" ;
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
	
	
	
	 
  public ActionForward createFileSearch(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)  throws Exception
	  {
	  	  String reportDate = request.getParameter("reportDate");
	      String reportId = request.getParameter("reportId");
		  if ("10000".equals(reportId) || "null".equals(reportId)) {
		   		reportId = null;
		  }
		   	
	      HttpSession session = request.getSession();
		  if (reportDate == null) {
		   reportDate = (String) session.getAttribute("logindate");
		  }
		  if(StringUtils.isNotBlank(reportId)){
			  
			 Long reportIdL = dfs.getreportId(new Long(reportId), "2");
			 if (null==reportIdL) {
			   		reportId = null;
			  }else{
				    reportId = reportIdL+"";
			  }
		  }
		  
		  
		  TarskService ts = (TarskService) getBean("tarskService");
		  List Showlist = ts.getRepSubTarsks(reportDate, reportId);
		  request.setAttribute("reportDate", reportDate);
		  
		  if(StringUtils.isNotBlank(reportId)){
			  
			  Long reportIdL = dfs.getreportId(new Long(reportId), "1");
			  reportId = reportIdL+"";
		  }else{
			  reportId = "10000";
		  }
	      request.setAttribute("reportId", reportId);
		  request.setAttribute("Showlist", Showlist);
		  return mapping.findForward("fileResult");
     }
	  
	public ActionForward importDataFromXls(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		 
		return mapping.findForward("importDataFromXls");
	}
	
	public ActionForward importDataFromXlsZf(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		 
		return mapping.findForward("importDataFromXlsZf");
	}
	
	
	/**
	 * 导出检核数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward exportTemplatePdf(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		RepFileService rpf = getRepFileservice();
		User user = getUser(request);
		String organId = request.getParameter("organ_id");
	    if(StringUtils.isBlank(organId)){
	    	organId = user.getOrganId();
	    }
		
	    String loginDate = (String) session.getAttribute("logindate");
	    String databatch = request.getParameter("databatch");
		if(StringUtils.isBlank(databatch)){
			databatch = loginDate ;
		}
	
	    
		CodeRepJhgz codeRepJhgz = new CodeRepJhgz();
		
		codeRepJhgz.setOrgan_id(organId)  ;
		codeRepJhgz.setReport_id(request.getParameter("report_id"));
		codeRepJhgz.setTarget_id(request.getParameter("target_id"));
		codeRepJhgz.setDatabatch(databatch);
		String organ_name = request.getParameter("organ_name") ;
		if(StringUtils.isBlank(organ_name)){
			organ_name = "滨海农商行";
		}
		String filestr = organ_name+"字段校验结果"  ;
		
		int idxid = getUser(request).getOrganTreeIdxid();
		int IsAdmin = getUser(request).getIsAdmin();
		
		List<CodeRepJhgz> repJhgzs = rpf.getRepJhgz(codeRepJhgz,IsAdmin,idxid);

		Document doc=new Document();
		ByteArrayOutputStream byteOutputS = new ByteArrayOutputStream();
		
		String[] tableHeader = new String[10];
		tableHeader[0] = "检核规则序号";	
		tableHeader[1] = "数据批次";	 
		tableHeader[2] = "检核规则";	 
		tableHeader[3] = "检核表名";	 
		tableHeader[4] = "检核字段名";	 
		tableHeader[5] = "报送要求的定义描述"; 
		tableHeader[6] = "错误条数"; 
		tableHeader[7] = "总量";
		tableHeader[8] = "错误率";
		tableHeader[9] = "错误数据原因解释";	
	
		
		
		try {
			PdfWriter.getInstance(doc,byteOutputS);
			rpf.addPdf(doc,repJhgzs, tableHeader,filestr,"1");
		} catch (DocumentException e) {
			e.printStackTrace();
		}finally{
			doc.close();
		}
		
		byte [] pdfimages = null;//返回图像
		try {
			//刷新此输出流并强制写出所有缓冲的输出字节，必须这行代码，否则有可能有问题
			byteOutputS.flush();
			byteOutputS.toByteArray();
			String dUrlData= new sun.misc.BASE64Encoder().encode(byteOutputS.toByteArray());
			System.out.println(dUrlData);
			
			Map<String, String> map = new HashMap<String, String>();
			String dd = DateUtil.getDateTime("yyyyMMddHHmmssSSS", new Date());
			map.put("pckgsq", "east"+dd);
			map.put("reqtdt", dd.substring(0,8));
			map.put("reqttm", dd.substring(8,14));
			map.put("outsfg", "EAST");
			map.put("modename", "EAST检核结果");
			map.put("filedata", dUrlData) ;
			map.put("yxid", "east"+dd);
			map.put("yxdate", dd.substring(0,8));
			
			String url = FuncConfig.getProperty("signatureUrl", "http://172.0.0.1:8082/east");
			// pdf 发送签章请求响应结果；
			String base64pdfsignature = rpf.resprept(map, url);
			
			if(base64pdfsignature == null){
				base64pdfsignature = dUrlData ;
			}
		    BASE64Decoder decoder = new BASE64Decoder();
			pdfimages= decoder.decodeBuffer(base64pdfsignature);//Base64转换成byte数组
			System.out.println(pdfimages);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		String fileName = "";
		try {
			fileName = new String( filestr.getBytes("gb2312"),"iso8859-1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		OutputStream out = null;
		try {
			String timeNow =new SimpleDateFormat("yyyyMMdd").format(new Date());
			response.setContentType("application/x-download");
			response.addHeader("Content-Disposition","attachment;filename=" + fileName +"_"+timeNow+".pdf");
			out = response.getOutputStream();
			out.write(pdfimages);
			
		} catch (Exception ex) {
			logger.error("exception", ex);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException es) {
					logger.error("exception", es);
				}
				out = null;
			}
		}
		return null;
	}
	
	/**
	 * 导出检核数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward exportTemplatePdfzf(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		
		HttpSession session = request.getSession();
		RepFileService rpf = getRepFileservice();
		User user = getUser(request);
		String organId = request.getParameter("organ_id");
	    if(StringUtils.isBlank(organId)){
	    	organId = user.getOrganId();
	    }
	    int idxid = getUser(request).getOrganTreeIdxid();
		int IsAdmin = getUser(request).getIsAdmin();
		String loginDate = (String) session.getAttribute("logindate");

		String databatch = request.getParameter("databatch");
		if(StringUtils.isBlank(databatch)){
			databatch = loginDate ;
		}
		
		CodeRepJhgzZf codeRepJhgzZf = new CodeRepJhgzZf();
		
		codeRepJhgzZf.setOrgan_id(organId)  ;
		codeRepJhgzZf.setReport_id(request.getParameter("report_id"));
		codeRepJhgzZf.setTarget_id(request.getParameter("target_id"));
		codeRepJhgzZf.setDatabatch(databatch);
		String organ_name = request.getParameter("organ_name") ;
		if(StringUtils.isBlank(organ_name)){
			organ_name = "滨海农商行";
		}
		String filestr = organ_name+"总分核对结果" ;
		List<CodeRepJhgzZf> jhgzZfs = rpf.getRepJhgzZf(codeRepJhgzZf,IsAdmin,idxid);

		String[] titles = new String[10];
		titles[0] = "序号";	
		titles[1] = "数据批次";	 
		titles[2] = "检核规则";	 
		titles[3] = "检核表名";	 
		titles[4] = "检核要素";	 
		titles[5] = "一级科目名称"; 
		titles[6] = "分户账数值"; 
		titles[7] = "总账数值";
		titles[8] = "差额";
		titles[9] = "差异原因解释";	 
		 
		Document doc=new Document();
		ByteArrayOutputStream byteOutputS = new ByteArrayOutputStream();
		
		 
		
		try {
		
			PdfWriter.getInstance(doc,byteOutputS);
			rpf.addPdf(doc,jhgzZfs, titles,filestr,"2");
			
		} catch (DocumentException e) {
			e.printStackTrace();
			
		}finally{
			doc.close();
		}
		byte [] pdfimages = null;//返回图像
		try {
			//刷新此输出流并强制写出所有缓冲的输出字节，必须这行代码，否则有可能有问题
			byteOutputS.flush();
			byteOutputS.toByteArray();
			String dUrlData= new sun.misc.BASE64Encoder().encode(byteOutputS.toByteArray());
			Map<String, String> map = new HashMap<String, String>();
			String dd = DateUtil.getDateTime("yyyyMMddHHmmssSSS", new Date());
			map.put("pckgsq", "east"+dd);
			map.put("reqtdt", dd.substring(0,8));
			map.put("reqttm", dd.substring(8,14));
			map.put("outsfg", "EAST");
			map.put("modename", "EAST总分结果");
			map.put("filedata", dUrlData) ;
			map.put("yxid", "east"+dd);
			map.put("yxdate", dd.substring(0,8));
			
			String url = FuncConfig.getProperty("signatureUrl", "http://192.200.5.117:8090/Seal/AutoPdfServlet");
			// pdf 发送签章请求响应结果；
			String base64pdfsignature = rpf.resprept(map, url);
			
			if(base64pdfsignature == null){
				base64pdfsignature = dUrlData ;
			}
			BASE64Decoder decoder = new BASE64Decoder();
			pdfimages= decoder.decodeBuffer(base64pdfsignature);//Base64转换成byte数组
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String fileName = "";
		try {
			fileName = new String( filestr.getBytes("gb2312"),"iso8859-1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		OutputStream out = null;
		try {
			String timeNow =new SimpleDateFormat("yyyyMMdd").format(new Date());
			response.setContentType("application/x-download");
			response.addHeader("Content-Disposition","attachment;filename=" + fileName +"_"+timeNow+".pdf");
			
			out = response.getOutputStream();
			out.write(pdfimages);
		} catch (Exception ex) {
			logger.error("exception", ex);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException es) {
					logger.error("exception", es);
				}
				out = null;
			}
		}
		return null;
	}
	
	/**
	 * @param args
	 * @throws DocumentException
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	public static void main(String[] args) throws  MalformedURLException, IOException, DocumentException  {
		// TODO Auto-generated method stub
		String myFileName ="鎶ユ枃鐨処D";
		System.out.println(new String(myFileName.getBytes("gbk")));   
		  //BASE64编码  
        String str = "hello";
        byte[] bytes = str.getBytes("utf-8");
        str = new sun.misc.BASE64Encoder().encode(bytes);
        System.out.println("编码后...  "+str);

        //BASE64解码
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b = decoder.decodeBuffer("JVBERi0xLjQKJeLjz9MKMiAwIG9iago8PC9MZW5ndGggODg2L0ZpbHRlci9GbGF0ZURlY29kZT4+c3RyZWFtCnicjVW/TxRBGF0sKLaw14alMVA4zszOzO6KMcZESIzecbeHAe+UEzk4hUPAeIRCEgwmXJSADRICsfQvoLCzEAs6Y22isaFCCxOlwvmxi3e7M2oum9vce+/7bt73vd0Z+3LBdpnjQ+YURm3onEXHNxCKuysFO2fPyAs7VznQZ0NAnTmh8gLoUOw6XDNbsUNO4tWQA/kHORi6ACLOwU6hZp/rRQ7id2N219R+bS63NdC7fM0qdeWqq+uTfeUeq9Rd7sm3V/3i4cLH8a3uwn3R2dTX83lf4IqKsrFCfJ//ZMAQgcAoRAEGgQnEhAJsBAOPfxtAlwTAN4EEYoCMICWAmEAKGfCMPfkhEVQI89Pu6LHIHQOo3NGDkTsGULmjByN39GDkjgFU7ujByB1DT+awIEIwTLljwJQ7JlC6YwCVOyZQumMAlTsGULljAqU7BlC5kwJbgsv1iE/Nh3FufRnb26vhzyiWTeTAB0RDrkzfnRqZn+hMCxCFwNMoqm+rfuNd/lRaIR8kBsVytVCa1UgoBa6pyeJjR6MIAsA0ioy18Hr+Q/2At5n/npa5vFGgkeXbR7yVM5Xp/gtpDUEYYI2m3GNUeBhQjeLWwcZ2mk2hD3wte/hE+OrG58bXF3tpFYPi8UASGgtpRs6d8jRUbEELWQG/oOXqhPHok8r4wa9RBFSOnvitiuJhbrK4Od5RHJy4KFQpvuwQTQG25Z6u7TfT4o1ycRNtuarZCuLJPUr27++ovMkObWzDthWvpXDMV/0VKXNSLFALjbmSFvUXJPNGJt0KB7LXm8+eWEjGA44006EWUZduIQP5pk5pfD5NIi7dSlK9RkwfmpeSJZxc28+3l3dzO+G3jArysYCnRAqUldlSafh8nlU7N5+3sKKyVDl5VBrL9vJCGWvxR3lXV44QScztvLT6b8ZJ+ENiksRfE3G1Z0NPjk7rOmJ5kOPdrTXCPS1N/f1ao/g++6X8IF9YH23tiIArXnTNm1hf4lmiuknJkLKAJZNnCqmG+p8hTSn/HVII/y+kGHqCz4LmM5uzlyo7yBMQxaqlLA0EPy5bP/h7rJJls5/W7+Umw1/iHzeXdREV/LisIXUeBARp7Cb6ORIMgc80fJkeXX6ivKUVhkyLxcIofUzroTVqjVl3xHd9aeRS2OIhxURsbXzYRx31paj0bx7yrtgKZW5kc3RyZWFtCmVuZG9iago0IDAgb2JqCjw8L1R5cGUvUGFnZS9NZWRpYUJveFswIDAgNTk1IDg0Ml0vUmVzb3VyY2VzPDwvRm9udDw8L0YxIDEgMCBSPj4+Pi9Db250ZW50cyAyIDAgUi9QYXJlbnQgMyAwIFI+PgplbmRvYmoKNSAwIG9iago8PC9UeXBlL0ZvbnREZXNjcmlwdG9yL0FzY2VudCA4ODAvQ2FwSGVpZ2h0IDg4MC9EZXNjZW50IC0xMjAvRmxhZ3MgNi9Gb250QkJveCBbLTI1IC0yNTQgMTAwMCA4ODBdL0ZvbnROYW1lL1NUU29uZy1MaWdodC9JdGFsaWNBbmdsZSAwL1N0ZW1WIDkzL1N0eWxlPDwvUGFub3NlKAEFAgIEAAAAAAAAACk+Pj4+CmVuZG9iago2IDAgb2JqCjw8L1R5cGUvRm9udC9TdWJ0eXBlL0NJREZvbnRUeXBlMC9CYXNlRm9udC9TVFNvbmctTGlnaHQvRm9udERlc2NyaXB0b3IgNSAwIFIvVyBbOSAxMCAzNzQgMTcgMjIgNDYyIDI1IDI2IDQ2MiA2Nls0MTddNjlbNTI5XTcxWzI2NF04NFszMzZdXS9EVyAxMDAwL0NJRFN5c3RlbUluZm88PC9SZWdpc3RyeShBZG9iZSkvT3JkZXJpbmcoR0IxKS9TdXBwbGVtZW50IDQ+Pj4+CmVuZG9iagoxIDAgb2JqCjw8L1R5cGUvRm9udC9TdWJ0eXBlL1R5cGUwL0Jhc2VGb250L1NUU29uZy1MaWdodC1VbmlHQi1VQ1MyLUgvRW5jb2RpbmcvVW5pR0ItVUNTMi1IL0Rlc2NlbmRhbnRGb250c1s2IDAgUl0+PgplbmRvYmoKMyAwIG9iago8PC9UeXBlL1BhZ2VzL0NvdW50IDEvS2lkc1s0IDAgUl0+PgplbmRvYmoKNyAwIG9iago8PC9UeXBlL0NhdGFsb2cvUGFnZXMgMyAwIFI+PgplbmRvYmoKOCAwIG9iago8PC9Qcm9kdWNlcihpVGV4dK4gNS41LjEzIKkyMDAwLTIwMTggaVRleHQgR3JvdXAgTlYgXChBR1BMLXZlcnNpb25cKSkvQ3JlYXRpb25EYXRlKEQ6MjAxOTExMjcxMTQ4NTArMDgnMDAnKS9Nb2REYXRlKEQ6MjAxOTExMjcxMTQ4NTArMDgnMDAnKT4+CmVuZG9iagp4cmVmCjAgOQowMDAwMDAwMDAwIDY1NTM1IGYgCjAwMDAwMDE0OTYgMDAwMDAgbiAKMDAwMDAwMDAxNSAwMDAwMCBuIAowMDAwMDAxNjIwIDAwMDAwIG4gCjAwMDAwMDA5NjggMDAwMDAgbiAKMDAwMDAwMTA4MCAwMDAwMCBuIAowMDAwMDAxMjcxIDAwMDAwIG4gCjAwMDAwMDE2NzEgMDAwMDAgbiAKMDAwMDAwMTcxNiAwMDAwMCBuIAp0cmFpbGVyCjw8L1NpemUgOS9Sb290IDcgMCBSL0luZm8gOCAwIFIvSUQgWzwzMjhkZTc1ZTBjNTRlM2E0MjZhNzU5ODEzZDU3ZTEzMz48MzI4ZGU3NWUwYzU0ZTNhNDI2YTc1OTgxM2Q1N2UxMzM+XT4+CiVpVGV4dC01LjUuMTMKc3RhcnR4cmVmCjE4NzQKJSVFT0YK");

        str = new String(b);
        System.out.println("解码后... "  + str);
 
		BaseFont   bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			 
		Font font =  new Font(bfChinese, 8, Font.NORMAL); 
		
		// Listing 1. Instantiation of document object
		Document document = new Document(PageSize.A4);

		// Listing 2. Creation of PdfWriter object
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:\\ITextTest.pdf"));
		
		document.open();

		String tableHeader []= {"风险要素","风险子项","权重","评分参考","系统评分","初评评分","复评评分"};
		//logger.info(riskCt);
	
		PdfPTable table=new PdfPTable(7);
		table.setWidthPercentage(100);  
		table.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);  
		table.setSpacingBefore(25);
		table.setSpacingAfter(25);
	    PdfPCell pdfTableHeaderCell = new PdfPCell();  
       //设置表格的表头单元格颜色  
        pdfTableHeaderCell.setBackgroundColor(new CMYKColor(0,255, 255, 17));  
        pdfTableHeaderCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER); 
		for (String tableHeaderInfo : tableHeader) {
			 pdfTableHeaderCell.setPhrase(new Paragraph(tableHeaderInfo, font));  
			 table.addCell(pdfTableHeaderCell);  
		}
		
		document.add(table);
		
		// Listing 6. Creation of table object
		PdfPTable t = new PdfPTable(3);
			
		
		t.setSpacingBefore(25);
		t.setSpacingAfter(25);
		PdfPCell c1 = new PdfPCell(new Phrase("Header1"));
		t.addCell(c1);
		PdfPCell c2 = new PdfPCell(new Phrase("Header2"));
		t.addCell(c2);
		PdfPCell c3 = new PdfPCell();
		c3.setPhrase(new Paragraph("风险子项", font));
		t.addCell(c3);
		t.addCell(new Paragraph("我", font));
		t.addCell("1.2");
		t.addCell("you");
		document.add(t);
		document.close();

	}
	
	/**
	 * 
	 * 检核数据总分核对查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listRepSubmitalist (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try{
			if (log.isDebugEnabled()) {
				log.info("Entering 'listRepSubmitalist' method");
			}

			RepFileService rpf = getRepFileservice();
			
			User user = getUser(request);
		
			CodeRepSubmitalist repSubmitalist = new CodeRepSubmitalist();
			
			repSubmitalist.setOrganid(user.getOrganId())  ;
			
			List<CodeRepSubmitalist> repconfig = rpf.getRepSubmitalist(repSubmitalist);
			
			request.setAttribute("repconfig", repconfig);
			return mapping.findForward("repSubmitalist");
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
	 * 导出报送清单
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward exportTemplaterepSubmitalist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		RepFileService rpf = getRepFileservice();
		User user = getUser(request);
		
		CodeRepSubmitalist repSubmitalist = new CodeRepSubmitalist();
		
		repSubmitalist.setOrganid(user.getOrganId())  ;
		
		List<CodeRepSubmitalist> repconfig = rpf.getRepSubmitalist(repSubmitalist);

		String[] titles = new String[7];
		titles[0] = "表名";	
		titles[1] = "记录数";	 
		titles[2] = "是否完整反映业务情况";	 
		titles[3] = "文件名";	 
		titles[4] = "文件大小(B)";	 
		titles[5] = "是否检验"; 
		titles[6] = "备注"; 
	 
		 
		
		HSSFWorkbook wb = rpf.getXlsWorkRepSubmitalist(repconfig, titles);
		String fileName = "";
		try {
			String filestr =  "报送清单" ;
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
	
	/**
	 * 导出报送清单pdf
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward exportPdfRepSubmitalist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		
		RepFileService rpf = getRepFileservice();
		User user = getUser(request);
		
		CodeRepSubmitalist repSubmitalist = new CodeRepSubmitalist();
		
		repSubmitalist.setOrganid(user.getOrganId())  ;
		
		List<CodeRepSubmitalist> repconfig = rpf.getRepSubmitalist(repSubmitalist);

		String[] titles = new String[7];
		titles[0] = "表名";	
		titles[1] = "记录数";	 
		titles[2] = "是否完整反映业务情况";	 
		titles[3] = "文件名";	 
		titles[4] = "文件大小(B)";	 
		titles[5] = "是否检验"; 
		titles[6] = "备注"; 
		
		Document doc=new Document();
		ByteArrayOutputStream byteOutputS = new ByteArrayOutputStream();
		
		 
		
		try {
		
			PdfWriter.getInstance(doc,byteOutputS);
			rpf.addPdf(doc,repconfig, titles);
			
		} catch (DocumentException e) {
			e.printStackTrace();
			
		}finally{
			doc.close();
		}
		byte [] pdfimages = null;//返回图像
		try {
			//刷新此输出流并强制写出所有缓冲的输出字节，必须这行代码，否则有可能有问题
			byteOutputS.flush();
			byteOutputS.toByteArray();
			String dUrlData= new sun.misc.BASE64Encoder().encode(byteOutputS.toByteArray());
			Map<String, String> map = new HashMap<String, String>();
			String dd = DateUtil.getDateTime("yyyyMMddHHmmssSSS", new Date());
			map.put("pckgsq", "east"+dd);
			map.put("reqtdt", dd.substring(0,8));
			map.put("reqttm", dd.substring(8,14));
			map.put("outsfg", "EAST");
			map.put("modename", "EAST报送清单");
			map.put("filedata", dUrlData) ;
			map.put("yxid", "east"+dd);
			map.put("yxdate", dd.substring(0,8));
			
			String url = FuncConfig.getProperty("signatureUrl", "http://192.200.5.117:8090/Seal/AutoPdfServlet");
			// pdf 发送签章请求响应结果；
			String base64pdfsignature =  rpf.resprept(map, url);
			
			if(base64pdfsignature == null){
				base64pdfsignature = dUrlData ;
			}
			BASE64Decoder decoder = new BASE64Decoder();
			pdfimages= decoder.decodeBuffer(base64pdfsignature);//Base64转换成byte数组
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		String fileName = "";
		try {
			fileName = new String("报送清单".getBytes("gb2312"),"iso8859-1");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		OutputStream out = null;
		try {
			String timeNow =new SimpleDateFormat("yyyyMMdd").format(new Date());
			response.setContentType("application/x-download");
			response.addHeader("Content-Disposition","attachment;filename=" + fileName +"_"+timeNow+".pdf");
			
			out = response.getOutputStream();
			out.write(pdfimages);
		} catch (Exception ex) {
			logger.error("exception", ex);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException es) {
					logger.error("exception", es);
				}
				out = null;
			}
		}
		return null;
	}
	
	
	/**
	 * 
	 * 检核数据明细查询
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listRepsubmitAListOne (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try{
			RepFileService rpf = getRepFileservice();

			String pkid = request.getParameter("urlpkid");
			 
			if (log.isDebugEnabled()) {
				log.info("Entering 'listRepsubmitAListOne' method");
			}
			String strInformation= null ;
			CodeRepSubmitalist submitalist = rpf.selectCodeRepSubmitalist(pkid);
			if(submitalist != null){
				strInformation = submitalist.getInformation();
			}
			if(strInformation != null){
				String strg [] =strInformation.split("@");
				if(strg.length>0 && strg.length ==4 ){
					request.setAttribute("tbr", strg[0]);
					request.setAttribute("tbrlxfs", strg[1]);
					request.setAttribute("bmfzr", strg[2]);
					request.setAttribute("bmfzrlxfs", strg[3]);
				}
			}
			 
			request.setAttribute("submitalist", submitalist);
		  
			
			return mapping.findForward("listRepsubmitAListOne");
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
	 * 检核数据修改
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listRepsubmitAListAdd (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try{
			if (log.isDebugEnabled()) {
				log.info("Entering 'listRepsubmitAListAdd' method");
			}
			
			RepFileService rpf = getRepFileservice();

			String pkid = request.getParameter("urlpkid");
			String urlreasons = request.getParameter("urlreasons");//备注
			
			String tbr = request.getParameter("tbr"); //填报人
			String tbrlxfs = request.getParameter("tbrlxfs");//填表人联系方式
			String bmfzr = request.getParameter("bmfzr"); //部门负责人
			String bmfzrlxfs = request.getParameter("bmfzrlxfs");//部门负责人联系方式
		
		    int isok =rpf.updateRepsubmitAlist(pkid, urlreasons); //保存
			
		    String information =tbr+"@"+tbrlxfs +"@"+bmfzr +"@"+bmfzrlxfs ;
		    int a = rpf.updateRepsubmitAlistBy(pkid, information);
			
			CodeRepSubmitalist submitalist = rpf.selectCodeRepSubmitalist(pkid);
			
			 
			request.setAttribute("submitalist", submitalist);
			request.setAttribute("tbr", tbr);
			request.setAttribute("tbrlxfs", tbrlxfs);
			request.setAttribute("bmfzr",bmfzr);
			request.setAttribute("bmfzrlxfs", bmfzrlxfs);
 			request.setAttribute("isok", isok);
		
			return mapping.findForward("listRepsubmitAListOne");
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
