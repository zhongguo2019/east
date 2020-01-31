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
	 * ִ�б������
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
		System.out.println("content �ļ�����= " + content);
//		content = new String(content.getBytes("ISO-8859-1"),"GBK");
//		System.out.println("content �ļ�����= " + content);
		WorkDirectData workDirectData = new WorkDirectData();
		workDirectData.setKindId(wf.getKindId());
		workDirectData.setIssDate(wf.getIssDate());
		String title = wf.getTitle();
		System.out.println("title ���� = " + title);
//		title = new String(title.getBytes("ISO-8859-1"),"GBK");
//		System.out.println("title ���� = " + title);
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
		 * 2011-12-06��ʯ��
		 * ������ָ���еĸ�����С��Ϊ�����õ�
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
				System.out.println("�ļ�����  name = " + name);
//				name = new String(name.getBytes("ISO-8859-1"),"GBK");
//				System.out.println("�ļ�����  name = " + name);
				
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
				//add by hejx informix���ݿ� hibernate ������ֶζ������ĳ��ļ����������ط�����
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
					System.out.println("����·����=== fi1.getName()" + fi1.getName());
					System.out.println("����·����=== fi1.getPath()" + fi1.getPath());
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
	 * �������ӹ���ָ����ҳ��
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
	 * �������ӹ���ָ����ѯҳ��
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
	 * ���빤��ָ��������棬�г�����ָ���б�
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
	 * <p>�ѹ���ָ������</p> 
	 * 
	 * �ѹ���ָ���������һ��SET�У����SETָ����ʱ�併�����У��ٰѴ�SET����MAP�У�MAP�����������ʶ�Ӧ��
	 * ����ָ���ļ�SET
	 *
	 * @param fileList
	 * @return
	 * @author Ƥ��
	 * @version ����ʱ�䣺2010-5-15 ����04:08:53
	 */
	private Map sortWorkFileListByDate(List fileList)
	{
		// ��fileListתΪfileMap(workfile type, List(workfile order by date))
		Map fileMap = new TreeMap();
		int size = fileList.size();
		Object[] objects;
		for (int i = 0; i < size; i++)
		{
			objects = (Object[])fileList.get(i);
			// �Ƿ��Ѿ����˹���ָ������
			if (!fileMap.keySet().contains(objects[2])) // û�д�����
				fileMap.put(objects[2], new TreeSet(
					new Comparator(){
						public int compare(Object objects1, Object objects2)
						{
							// ��Map������ʽ��Ϊ���������򣨴ӽ����磩
							//By Shengping20120514 ����ʾ���������и�Ϊ��������
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
		//LinkedHashMap �������Ƚ��ȳ� ����Ϊ��������
		Set set = fileMap.entrySet();
		Iterator iter = fileMap.entrySet().iterator(); 
		while (iter.hasNext()) { 
		    Map.Entry entry = (Map.Entry) iter.next(); 
		    Object key = entry.getKey(); 
		    System.out.println("================================="+key);
		} 
		Map fileMaps = new LinkedHashMap ();
		if(fileMap.size()>0){
			if(fileMap.get("֪ͨ")!=null){
				fileMaps.put("֪ͨ", fileMap.get("֪ͨ"));
			}
			if(fileMap.get("ʡ�����ļ�")!=null){
				fileMaps.put("ʡ�����ļ�", fileMap.get("ʡ�����ļ�"));
			}
			if(fileMap.get("�ο�����")!=null){
				fileMaps.put("�ο�����", fileMap.get("�ο�����"));
			}
			if(fileMap.get("���������ļ�")!=null){
				fileMaps.put("���������ļ�", fileMap.get("���������ļ�"));
			}
			if(fileMap.get("���߷���")!=null){
				fileMaps.put("���߷���", fileMap.get("���߷���"));
			}
		}
		//By Shengping ToDESC 20120606
		return fileMaps;
	}
	
	/**
	 * ��������ָ��
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
		
		//2010.08.04�޸� ����
		//���û�в鵽���ݸ�����ʾ��Ϣ alert
		 
		if(fileList.size()>0)
		{
			prepareForSortDisplay(request, fileList);
			return mapping.findForward("fileList");
		}
		else
		{
			request.setAttribute(ALERT_MESSAGE, "û�в鵽��Ϣ��");
			return enterSearch(mapping, form, request, response);
		}
		
		// request.setAttribute("fileList", fileList);
		
	}
	
	/**
	 * <p>�Ѳ�ѯ���Ĺ���ָ���б���з�������,�Դﵽ��ҳ���Ϸ�����ʾ��Ч��</p> 
	 *
	 * @param request
	 * @param fileList ��ѯ�Ĺ���ָ���б�
	 * @author Ƥ��
	 * @version ����ʱ�䣺2010-6-10 ����01:38:00
	 */
	private void prepareForSortDisplay(HttpServletRequest request, List fileList)
	{
		// �Ȱ�fileList������ͬ����������
		Map fileMap = sortWorkFileListByDate(fileList);
		// �õ��ļ�������SET
		Set typeSet = fileMap.keySet();
		// �õ�sizeMap
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
	 * ���빤��ָ��ά������
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
	 * �鿴һ������ָ���ļ�����
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
	 * ������������ػ�򿪸���
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
		//add by hejx informix���ݿ� hibernate ������ֶζ������ĳ��ļ����������ط�����
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
		
		// �޸�ʱ�䣺2011-10-20 ����08:04:52 Ƥ��
		// ���ظ���ʱ������ļ����ƽϳ�������IE6��IE7�г��ֲ���������������⣬��Ҫ����������
		// ���汾��IE��header�ĳ�����һ�������ƣ�һ������������������ִ������ڣ�������ļ�����
		// ISO-8859-1���룬��IE�����Զ�ת����������
		ActionUtil.downLoadFile(response, fileName, oleFileData.getFileBlob(), "GBK");
		
		return null;
	}
	/**
	 * ɾ��һ������ָ���ļ�
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
		//add by hejx 2012.7.12 ɾ����informix���ݿⱣ�������ط�������Ӧ����
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
	        	log.debug("ɾ�������ɹ���");
	        } else {
	        	log.debug(dir + File.separatorChar + "olefile" + File.separatorChar + "workfile·����"+"ɾ������ʧ�ܣ�,�������ļ������ڣ�"+temp);
	        }
		}
		//===========================================================================
		//ɾ�����ݿ�����
		workFileService.delworkFiles(pkId, userId);
		String conPath = request.getContextPath() + "/";
		response.sendRedirect(conPath + "workFile.do?method=whFile");
		return null;
	}
	/**
	 * ����༭����ָ���ļ�
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
	 * ɾ������
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
		//add by hejx 2012.7.12 ɾ����informix���ݿⱣ�������ط�������Ӧ����
		if (SysConfig.DATABASE.toLowerCase().equals("informix")) {
			String dir = this.getServlet().getServletContext().getRealPath("/");
	        if(SysConfig.APPSERVER.toLowerCase().equals("websphere")) {
	            dir = FuncConfig.getCNProperty("workfile.path", "/home/slsint");
	        }
	        OleFileData oleFileData = workFileService.getOleFile(oPkId);
	        String fileRondomName = oleFileData.getDFileName();
	        String path = dir + File.separatorChar + "olefile" + File.separatorChar + "workfile" + File.separatorChar + fileRondomName;
	        if (deleteFile(path)) {
	        	log.debug("ɾ�������ɹ���");
	        } else {
	        	log.debug("ɾ������ʧ�ܣ�");
	        }
		}
		//===========================================================================
		//ɾ�����ݿ�����
		workFileService.delOleFile(oPkId);
		String conPath = request.getContextPath() + "/";
		response.sendRedirect(conPath + "workFile.do?method=editWorkFile&pkId="+pkId);
		return null;
	}
	
    /**
     * add by hejx 2012.7.12
     * ɾ�������ļ�(informix ���ݿ�ʱ������ֶζ���ʧ��ʱ���ĳɱ�������������ʹ��)
     * @param   sPath    ��ɾ���ļ����ļ���
     * @return �����ļ�ɾ���ɹ�����true�����򷵻�false
     */
    public boolean deleteFile(String sPath) {
        boolean flag = false;
        File file = new File(sPath);
        // ·��Ϊ�ļ��Ҳ�Ϊ�������ɾ��
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }
}
