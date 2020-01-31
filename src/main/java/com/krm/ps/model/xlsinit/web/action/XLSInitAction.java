package com.krm.ps.model.xlsinit.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.model.xlsinit.services.XLSInitService;
import com.krm.ps.model.xlsinit.web.form.XLSInitForm;
import com.krm.ps.sysmanage.reportdefine.services.ReportDefineService;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.util.FuncConfig;




/**
 * @struts.action name="xlsInitForm" path="/xlsInit" scope="request"
 *                validate="false" parameter="method" input="search"
 * 
 * @struts.action-forward name="search" path="/xlsinit/xlsinit.jsp"
 * @struts.action-forward name="init" path="/xlsinit/xlsinit.jsp"
 * 
 */
public class XLSInitAction extends BaseAction{
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward xlsInit(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'xlsInit' method");
		}

		XLSInitForm xlsInitForm = (XLSInitForm) form;
		User user = getUser(request);
		FormFile data = xlsInitForm.getDataFile();
		FormFile config = xlsInitForm.getXmlFile();
		
		XLSInitService xlsInitService = (XLSInitService)this.getBean("xlsInitService");
		
		try {
			xlsInitService.XLSInit(data,config,user);
			request.setAttribute("error1","-1");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			request.setAttribute("error2","-1");
			//e.printStackTrace();
		}
		
		return mapping.findForward("init");
	}
	
	/**
	 *  导入补录数据（信贷资产转让， 资产转让关系表，委托贷款）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public ActionForward uploadExcelYJH(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'uploadExcelYJH' method");
		}
		XLSInitForm xlsInitForm = (XLSInitForm) form;
		User user = getUser(request);
		FormFile data = xlsInitForm.getDataFile();
		//filename格式：信贷资产转让_1047_20140131.xlsx
		//String filename=request.getParameter("xlsfilename");
		String filename = xlsInitForm.getDataFile().getFileName();
		String organId=request.getParameter("organId");
		//filename=new String(filename.getBytes("ISO-8859-1"),"UTF-8");
		String reportId=request.getParameter("reportId");
		String dataDate=request.getParameter("dataDate");
		
		 String path  = FuncConfig.getProperty("import.temp.path");
		 String realpath = path+File.separator+filename;
		 File f = new File(path);
		    if(!f.exists()){
		     f.mkdir();
		    }
	   
	    FileOutputStream os = new FileOutputStream(realpath);
	    os.write(data.getFileData());
	    os.flush();
	    os.close();
		ReportDefineService rs = getReportDefineService();
		/*int message=rs.XLSInit(data,filename,organId);*/
		int message=rs.XLSInit(data,realpath,organId,dataDate);
		//System.out.println("message==="+message);
		request.setAttribute("organId", organId);
		request.setAttribute("reportId",reportId );
		request.setAttribute("dataDate", dataDate);
		request.setAttribute("message",message);
		return mapping.findForward("xlslsint");
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
	public ActionForward uploadExcelJHGZ(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'uploadExcelJHGZ' method");
		}
		XLSInitForm xlsInitForm = (XLSInitForm) form;
		User user = getUser(request);
		FormFile data = xlsInitForm.getDataFile();
		//filename格式：信贷资产转让_1047_20140131.xlsx
		String filename =data.getFileName();// xlsInitForm.getDataFile().getFileName();
		
		 String path  = FuncConfig.getProperty("import.temp.path");
		 String realpath = path+File.separator+filename;
		 File f = new File(path);
		    if(!f.exists()){
		     f.mkdir();
		    }
	   
	    FileOutputStream os = new FileOutputStream(realpath);
	    os.write(data.getFileData());
	    os.flush();
	    os.close();
		ReportDefineService rs = getReportDefineService();
		int message=rs.XLSInit(data);
		//System.out.println("message==="+message);
		request.setAttribute("message",message);
		return mapping.findForward("xlinputdatajhgz");
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
	public ActionForward uploadExcelJHGZzf(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'uploadExcelJHGZzf' method");
		}
		XLSInitForm xlsInitForm = (XLSInitForm) form;
		User user = getUser(request);
		FormFile data = xlsInitForm.getDataFile();
		//filename格式：信贷资产转让_1047_20140131.xlsx
		String filename =data.getFileName();// xlsInitForm.getDataFile().getFileName();
		
		 String path  = FuncConfig.getProperty("import.temp.path");
		 String realpath = path+File.separator+filename;
		 File f = new File(path);
		    if(!f.exists()){
		     f.mkdir();
		    }
	   
	    FileOutputStream os = new FileOutputStream(realpath);
	    os.write(data.getFileData());
	    os.flush();
	    os.close();
		ReportDefineService rs = getReportDefineService();
		int message=rs.XLSInitZf(data);
		//System.out.println("message==="+message);
		request.setAttribute("message",message);
		return mapping.findForward("xlinputdatajhgzZf");
	}
}
