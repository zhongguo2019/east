package com.krm.slsint.workfile.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.sysmanage.organmanage.services.OrganService;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.usermanage.services.DictionaryService;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.util.ActionUtil;
import com.krm.ps.util.ConvertUtil;
import com.krm.ps.util.FuncConfig;
import com.krm.ps.util.RandomGUID;
import com.krm.ps.util.SysConfig;
import com.krm.slsint.workfile.services.WorkFileService;
import com.krm.slsint.workfile.vo.OleFileData;
import com.krm.slsint.workfile.vo.WorkDirectData;
import com.krm.slsint.workfile.web.form.WorkFileForm;

/**
 * @struts.action name="workFileForm" path="/workFile" scope="request"
 *                validate="false" parameter="method" input="addList"
 *                
 * @struts.action-forward name="addList" path="/workfile/workFileForm.jsp"
 * @struts.action-forward name="fileList" path="/workfile/workFileList.jsp"
 * @struts.action-forward name="view" path="/workfile/workFileContent.jsp"
 * @struts.action-forward name="search" path="/workfile/workFileSearch.jsp"
 */
public class WorkFileAction extends BaseAction {
	/**
	 * 执行保存操作
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		String dir = getServlet().getServletContext().getRealPath("/");
		if(dir.substring(dir.length()-1).equals(File.separator)){
			dir = dir.substring(0,dir.length()-1);
		}
//		response.setContentType("text/html; charset=UTF-8");
		WorkFileForm wf = (WorkFileForm) form;
		User user = (User) request.getSession().getAttribute("user");
		String organId = user.getOrganId();
		Long pkId = wf.getPkId();
		String content = request.getParameter("content");
		System.out.println("content 文件内容= " + content);
//		content = new String(content.getBytes("ISO-8859-1"),"GBK");
//		System.out.println("content 文件内容= " + content);
		WorkDirectData workDirectData = new WorkDirectData();
		workDirectData.setKindId(wf.getKindId());
		workDirectData.setIssDate(wf.getIssDate());
		String title = wf.getTitle();
		System.out.println("title 标题 = " + title);
//		title = new String(title.getBytes("ISO-8859-1"),"GBK");
//		System.out.println("title 标题 = " + title);
		workDirectData.setTitle(title);
		workDirectData.setContent(content);
		workDirectData.setFileSourceId(wf.getFileSourceId());
		workDirectData.setFileSourceName("");
		workDirectData.setIssOper(user.getPkid());
		workDirectData.setOperName(user.getName());
		workDirectData.setIssOper(user.getPkid());
		workDirectData.setOperName(user.getName());
		workDirectData.setOrganId(organId);
		OrganService organService = (OrganService)this.getBean("organService");
		OrganInfo organInfo = organService.getOrganByCode(organId);
		String organName = organInfo.getShort_name();
		workDirectData.setOrganName(organName);
		workDirectData.setDepartId(null);
		workDirectData.setDepartName(null);
		workDirectData.setStatus("0");
		int fileSize = 0;
		String fileName = "";
		String fileRondomName = "";
		/**
		 * 2011-12-06周石磊
		 * 将工作指引中的附件大小设为可配置的
		 */
		String accessorySize = FuncConfig.getProperty("accessory.size", "10");
		int size = 1024*1024*Integer.valueOf(accessorySize).intValue();
		List accessoryFile = new ArrayList();
		
		Hashtable ht = wf.getMultipartRequestHandler().getFileElements();
		Enumeration e = ht.keys();
		while(e.hasMoreElements()){
			byte [] b = null;
			Object k = e.nextElement();
			FormFile file = (FormFile)ht.get(k);
			fileSize = file.getFileSize();
			if(fileSize <= size){
				String name = file.getFileName();
				System.out.println("文件名称  name = " + name);
//				name = new String(name.getBytes("ISO-8859-1"),"GBK");
//				System.out.println("文件名称  name = " + name);
				
				String fileExtendName = "";
				int isSymbol = name.indexOf(".");
				if(isSymbol != -1){
					fileExtendName = name.substring(name.indexOf("."));
				}else{
					fileExtendName = name;
				}				
				RandomGUID myGUID = new RandomGUID();
				String rondomName = myGUID.toString() + fileExtendName;
				InputStream input = file.getInputStream();
				b = new byte[input.available()];
				input.read(b);
				//===========================================================================
				//add by hejx informix数据库 hibernate 保存大字段对象出错改成文件保存至本地服务器
				if (SysConfig.DATABASE.toLowerCase().equals("informix")) {
					if (SysConfig.APPSERVER.toLowerCase().equals("websphere")) {
						dir = FuncConfig.getCNProperty("workfile.path", "/home");
					}
					File fi = new File(dir + File.separator + "olefile");
					File fi1 = new File(dir + File.separator + "olefile" + File.separatorChar + "workfile");
					if (!fi.exists()) {
						fi.mkdirs();
						fi1.mkdirs();
					}
					System.out.println("附件路径：=== fi1.getName()" + fi1.getName());
					System.out.println("附件路径：=== fi1.getPath()" + fi1.getPath());
					OutputStream output = new FileOutputStream(dir + File.separator + "olefile" + File.separatorChar + "workfile" + File.separatorChar + rondomName);
					//int byteRead = 0;
	                //byte buffer[] = new byte[8192];
	                //while((byteRead = input.read(buffer, 0, 8192)) != -1){
	                 //   output.write(buffer, 0, byteRead);
	                //}
					output.write(b);
	                input.close();
	                output.close();
				}
				//===========================================================================
				
				if(fileName.equals("")){
	        		fileName += name;
	        	}else{
	        		fileName += ("," + name);
	        	}
	        	if(fileRondomName.equals("")){
	        		fileRondomName += rondomName;
	        	}else{
	        		fileRondomName += ("," + rondomName);
	        	}
	        	accessoryFile.add(b);
			}else{
				String loginDate = (String) request.getSession().getAttribute("logindate");
				wf.setIssDate(loginDate.substring(0,4)+loginDate.substring(5,7)+loginDate.substring(8,10));
				this.getServlet().getServletContext().getRealPath("/");
				DictionaryService dictionaryService = (DictionaryService) getBean("dictionaryService");
				List jobList = dictionaryService.getJobdirect();
				List derivationList = dictionaryService.getDerivation();
				request.setAttribute("jobList", jobList);
				request.setAttribute("derList", derivationList);
				request.setAttribute("fileSize","9");
				return mapping.findForward("addList");
			}
		}			
		WorkFileService workFileService = this.getWorkFileService();
		if(pkId == null){
			workFileService.addWorkFile(workDirectData,fileName,fileRondomName,fileSize, accessoryFile);			
		}else{
			workFileService.updateWorkFile(workDirectData,fileName,fileRondomName,pkId,fileSize, accessoryFile);
		}
		String conPath = request.getContextPath() + File.separatorChar;
		response.sendRedirect(conPath + "workFile.do?method=whFile");
		return null;	
	}
	/**
	 * 进入增加工作指引表单页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
	{
		WorkFileForm wf = (WorkFileForm) form;
		String loginDate = (String) request.getSession().getAttribute("logindate");		
		wf.setIssDate(loginDate.substring(0,4)+loginDate.substring(5,7)+loginDate.substring(8,10));
		this.getServlet().getServletContext().getRealPath("/");
		DictionaryService dictionaryService = (DictionaryService) getBean("dictionaryService");
		List jobList = dictionaryService.getJobdirect();
		List derivationList = dictionaryService.getDerivation();		
		request.setAttribute("jobList", jobList);
		request.setAttribute("derList", derivationList);	
		request.setAttribute("fileSize","0");
		return mapping.findForward("addList");
	}	
	/**
	 * 进入增加工作指引查询页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward enterSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		WorkFileForm wf = (WorkFileForm) form;
		DictionaryService dictionaryService = (DictionaryService) getBean("dictionaryService");
		List jobList = dictionaryService.getJobdirect();
		List derivationList = dictionaryService.getDerivation();		
		request.setAttribute("jobList", jobList);
		request.setAttribute("derList", derivationList);
		updateFormBean(mapping,request,wf);
		return mapping.findForward("search");
	}
	/**
	 * @return
	 */
	public WorkFileService getWorkFileService() {
		return (WorkFileService) this.getBean("workFileService");
	}
	/**
	 * 进入工作指引浏览界面，列出工作指引列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WorkFileService workFileListService = getWorkFileService();
		List fileList = workFileListService.getWorkFileList();
		prepareForSortDisplay(request, fileList);
		return mapping.findForward("fileList");
	}
	
	/**
	 * <p>把工作指引排序</p> 
	 * 
	 * 把工作指引对象放在一个SET中，这个SET指定以时间降序排列，再把此SET放入MAP中，MAP用类型来访问对应的
	 * 工作指引文件SET
	 *
	 * @param fileList
	 * @return
	 * @author 皮亮
	 * @version 创建时间：2010-5-15 下午04:08:53
	 */
	private Map sortWorkFileListByDate(List fileList)
	{
		// 把fileList转为fileMap(workfile type, List(workfile order by date))
		Map fileMap = new TreeMap();
		int size = fileList.size();
		Object[] objects;
		for (int i = 0; i < size; i++)
		{
			objects = (Object[])fileList.get(i);
			// 是否已经有了工作指引类型
			if (!fileMap.keySet().contains(objects[2])) // 没有此类型
				fileMap.put(objects[2], new TreeSet(
					new Comparator(){
						public int compare(Object objects1, Object objects2)
						{
							// 把Map的排序方式设为按日期排序（从近到早）
							//By Shengping20120514 把显示由升序排列改为降序排列
							int i=(((String)((Object[])objects2)[6]).compareTo(((String)((Object[])objects1)[6])));
							System.out.println(i);
							if(i == 0)
							{
								return 1;
							}
							else 
							{
								return i;
							}
						}		
					}
				));
			((Set)(fileMap.get(objects[2]))).add(objects);
		}
		//By Shengping ToDESC 20120606
		//LinkedHashMap 排序是先进先出 其他为无序排列
		Set set = fileMap.entrySet();
		Iterator iter = fileMap.entrySet().iterator(); 
		while (iter.hasNext()) { 
		    Map.Entry entry = (Map.Entry) iter.next(); 
		    Object key = entry.getKey(); 
		    System.out.println("================================="+key);
		} 
		Map fileMaps = new LinkedHashMap ();
		if(fileMap.size()>0){
			if(fileMap.get("通知")!=null){
				fileMaps.put("通知", fileMap.get("通知"));
			}
			if(fileMap.get("省联社文件")!=null){
				fileMaps.put("省联社文件", fileMap.get("省联社文件"));
			}
			if(fileMap.get("参考资料")!=null){
				fileMaps.put("参考资料", fileMap.get("参考资料"));
			}
			if(fileMap.get("人民银行文件")!=null){
				fileMaps.put("人民银行文件", fileMap.get("人民银行文件"));
			}
			if(fileMap.get("政策法规")!=null){
				fileMaps.put("政策法规", fileMap.get("政策法规"));
			}
		}
		//By Shengping ToDESC 20120606
		return fileMaps;
	}
	
	/**
	 * 搜索工作指引
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WorkFileForm wf = (WorkFileForm) form;
		WorkFileService workFileListService = getWorkFileService();
		List fileList = workFileListService.getWorkFileList(wf.getTitle(),wf.getContent(),
			wf.getKindId(),wf.getFileSourceId(),wf.getIssDate());
		
		//2010.08.04修改 刘勇
		//如果没有查到数据给出提示信息 alert
		 
		if(fileList.size()>0)
		{
			prepareForSortDisplay(request, fileList);
			return mapping.findForward("fileList");
		}
		else
		{
			request.setAttribute(ALERT_MESSAGE, "没有查到信息！");
			return enterSearch(mapping, form, request, response);
		}
		
		// request.setAttribute("fileList", fileList);
		
	}
	
	/**
	 * <p>把查询到的工作指引列表进行分类排序,以达到在页面上分类显示的效果</p> 
	 *
	 * @param request
	 * @param fileList 查询的工作指引列表
	 * @author 皮亮
	 * @version 创建时间：2010-6-10 上午01:38:00
	 */
	private void prepareForSortDisplay(HttpServletRequest request, List fileList)
	{
		// 先把fileList按日期同近到早排序
		Map fileMap = sortWorkFileListByDate(fileList);
		// 得到文件的类型SET
		Set typeSet = fileMap.keySet();
		// 得到sizeMap
		Map sizeMap = new HashMap();
		Object key;
		for (Iterator it = typeSet.iterator(); it.hasNext();)
		{
			key = it.next();
			sizeMap.put(key, new Integer(((Set)fileMap.get(key)).size()));
		}
		request.setAttribute("workfileType", typeSet);
		request.setAttribute("sizeMap", sizeMap);
		request.setAttribute("fileMap", fileMap);
	}
	
	/**
	 * 进入工作指引维护界面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward whFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return listFile(mapping, form,request, response);
	}	
	/**
	 * 查看一个工作指引文件内容
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long pkId = Long.valueOf(request.getParameter("pkId"));
		Long dicId = Long.valueOf(request.getParameter("dicId"));
		WorkFileService workFileListService = getWorkFileService();
		List dicName = workFileListService.getDicName(dicId);
		WorkDirectData workDirectData = workFileListService.updateWorkDirect(pkId);
		ArrayList al = workFileListService.getOle(pkId);
		Long fileCnt = new Long(al.size());
		request.setAttribute("fileCnt",fileCnt);
		request.setAttribute("workDirectData", workDirectData);
		request.setAttribute("oleFileData", al);
		request.setAttribute("dicName", dicName);
		return mapping.findForward("view");
	}
	/**
	 * 点击附件后下载或打开附件
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward download(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long olePkId = Long.valueOf(request.getParameter("fileId"));
		WorkFileService workFileListService = getWorkFileService();
		OleFileData oleFileData = workFileListService.getOleFile(olePkId);
//		String fileRondomName = oleFileData.getDFileName();
		String fileName = oleFileData.getSFileName();
		String dir = this.getServlet().getServletContext().getRealPath("/");
		if(dir.substring(dir.length()-1).equals(File.separator)){
			dir = dir.substring(0,dir.length()-1);
		}
		if (SysConfig.APPSERVER.toLowerCase().equals("websphere"))
		{
			dir = "/home";
		}
		
		//===========================================================================
		//add by hejx informix数据库 hibernate 保存大字段对象出错改成文件保存至本地服务器
		if (SysConfig.DATABASE.toLowerCase().equals("informix")) {
	        if(SysConfig.APPSERVER.toLowerCase().equals("websphere")) {
	            dir = FuncConfig.getCNProperty("workfile.path", "/home/slsint");
	        }
	        String fileRondomName = oleFileData.getDFileName();
		//	FileInputStream fis = new FileInputStream(dir + File.separatorChar + "olefile" + File.separatorChar + "workfile" + File.separatorChar + fileRondomName);
		//	byte b[] = new byte[1024];
		//	fis.read(b);
//			ActionUtil.downLoadFile(response, fileName, b, "GBK");
		  response.reset();
          response.setContentType("application/x-download");
          fileName = URLEncoder.encode(fileName, "utf-8");
          response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
          FileInputStream fis = new FileInputStream(dir + File.separatorChar + "olefile" + File.separatorChar + "workfile" + File.separatorChar + fileRondomName);
          OutputStream output = response.getOutputStream();
          try{
             byte b[] = new byte[1024];
             for(int i = 0; (i = fis.read(b)) > 0;){
                  output.write(b, 0, i);
             }
             output.flush();
                  }catch(Exception e){
                	  System.out.println("Error!!!!");
                	  e.printStackTrace();
                  }finally{
                	  if(fis != null){
                		  fis.close();
                		  fis = null;
                      }
                	  if(output != null){
                		  output.close();
                		  output = null;
                      }
                  }
			return null;
		}
		//===========================================================================
		
		// 修改时间：2011-10-20 下午08:04:52 皮亮
		// 下载附件时，如果文件名称较长，则在IE6或IE7中出现部分名称乱码的问题，主要是由于这两
		// 个版本的IE对header的长度有一定的限制，一旦超过这个限制则会出现此种现在，这里把文件名以
		// ISO-8859-1编码，让IE进行自动转码解决此问题
		ActionUtil.downLoadFile(response, fileName, oleFileData.getFileBlob(), "GBK");
		
		return null;
	}
	/**
	 * 删除一个工作指引文件
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delWorkFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long pkId = Long.valueOf(request.getParameter("pkId"));
		User user = (User) request.getSession().getAttribute("user");
		Long userId = user.getPkid();
		WorkFileService workFileService = this.getWorkFileService();
		//===========================================================================
		//add by hejx 2012.7.12 删除因informix数据库保存至本地服务器对应附件
		if (SysConfig.DATABASE.toLowerCase().equals("informix")) {
			String dir = this.getServlet().getServletContext().getRealPath("/");
	        if(SysConfig.APPSERVER.toLowerCase().equals("websphere")) {
	            dir = FuncConfig.getCNProperty("workfile.path", "/home/slsint");
	        }
 	        boolean flag = false;
 	        String temp = "";
	        List list = workFileService.getOle(pkId);
	        for (int i=0; i<list.size();i++) {
	        	OleFileData oleFileData = (OleFileData)list.get(i);
	        	String fileRondomName = oleFileData.getDFileName();
	        	String path = dir + File.separatorChar + "olefile" + File.separatorChar + "workfile" + File.separatorChar + fileRondomName;
 	        	if (deleteFile(path)) {
 	        		flag = true;
 	        	} else {
 	        		temp += fileRondomName+"|";
 	        	}
	        }
	        if (flag) {
	        	log.debug("删除附件成功！");
	        } else {
	        	log.debug(dir + File.separatorChar + "olefile" + File.separatorChar + "workfile路径下"+"删除附件失败！,服务器文件不存在："+temp);
	        }
		}
		//===========================================================================
		//删除数据库数据
		workFileService.delworkFiles(pkId, userId);
		String conPath = request.getContextPath() + "/";
		response.sendRedirect(conPath + "workFile.do?method=whFile");
		return null;
	}
	/**
	 * 进入编辑工作指引文件
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editWorkFile(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long pkId = Long.valueOf(request.getParameter("pkId"));		
		WorkFileService workFileService = (WorkFileService)this.getWorkFileService();
		WorkDirectData workDirectData =	workFileService.updateWorkDirect(pkId);
		ArrayList al = workFileService.getOle(pkId);
		DictionaryService dictionaryService = (DictionaryService) getBean("dictionaryService");
		List jobList = dictionaryService.getJobdirect();
		List derivationList = dictionaryService.getDerivation();				
		WorkFileForm workFileForm = new WorkFileForm();
		ConvertUtil.copyProperties(workFileForm,workDirectData);		
		this.updateFormBean(mapping,request,workFileForm);
		request.setAttribute("ole",al);
		request.setAttribute("jobList", jobList);
		request.setAttribute("derList", derivationList);
		return mapping.findForward("addList");		
	}	
	/**
	 * 删除附件
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delOle(ActionMapping mapping,ActionForm form,
		   HttpServletRequest request,HttpServletResponse response)
	       throws Exception{		
		Long oPkId = Long.valueOf(request.getParameter("olePkId"));
		Long pkId = Long.valueOf(request.getParameter("pkId"));		
		WorkFileService workFileService = (WorkFileService)this.getWorkFileService();
		//===========================================================================
		//add by hejx 2012.7.12 删除因informix数据库保存至本地服务器对应附件
		if (SysConfig.DATABASE.toLowerCase().equals("informix")) {
			String dir = this.getServlet().getServletContext().getRealPath("/");
	        if(SysConfig.APPSERVER.toLowerCase().equals("websphere")) {
	            dir = FuncConfig.getCNProperty("workfile.path", "/home/slsint");
	        }
	        OleFileData oleFileData = workFileService.getOleFile(oPkId);
	        String fileRondomName = oleFileData.getDFileName();
	        String path = dir + File.separatorChar + "olefile" + File.separatorChar + "workfile" + File.separatorChar + fileRondomName;
	        if (deleteFile(path)) {
	        	log.debug("删除附件成功！");
	        } else {
	        	log.debug("删除附件失败！");
	        }
		}
		//===========================================================================
		//删除数据库数据
		workFileService.delOleFile(oPkId);
		String conPath = request.getContextPath() + "/";
		response.sendRedirect(conPath + "workFile.do?method=editWorkFile&pkId="+pkId);
		return null;
	}
	
    /**
     * add by hejx 2012.7.12
     * 删除单个文件(informix 数据库时保存大字段对象失败时，改成保存文至服务器使用)
     * @param   sPath    被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
}
