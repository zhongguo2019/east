package com.krm.ps.sysmanage.organmanage.web.action;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import jxl.*;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.sysmanage.organmanage.services.OrganService;
import com.krm.ps.sysmanage.organmanage.vo.OrganContrast;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.organmanage.web.form.ContrastForm;
import com.krm.ps.sysmanage.organmanage2.services.AreaService;
import com.krm.ps.sysmanage.organmanage2.vo.Area;
import com.krm.ps.sysmanage.usermanage.services.DictionaryService;
import com.krm.ps.sysmanage.usermanage.vo.Esystem;
/**
*
* @struts.action name="contrastForm" path="/contrastAction" scope="request" 
*  validate="false" parameter="method"
*  
* @struts.action-forward name="edit" path="/organmanage/contrastForm.jsp"
* @struts.action-forward name="tree" path="/common/tree.jsp"
*/
public class OrganContrastAction extends BaseAction{
	
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'edit' method");
		}

		
		if (!validateUser(mapping, request)) {
			return mapping.findForward("loginpage");
		}
		
		request.setAttribute("success", request.getParameter("success"));
		setToken(request);
		OrganService os = getOrganService();
		DictionaryService ds=this.getDictionaryService();
		String organs[][],contrasts[][],systems[][];
		AreaService areaService = (AreaService)getBean("areaService");
		String areaCode = "";
		String rootId = areaService.getAreaCodeByUser(getUser(request).getPkid().intValue());
		if(request.getParameter("areaCode") == null){
			areaCode = areaService.getAreaCodeByUser(getUser(request).getPkid().intValue());
		}else{
			areaCode = request.getParameter("areaCode");
		}
		request.setAttribute("areaCode",areaCode);
		request.setAttribute("rootId",rootId);
		//检索得到当前机构的下级机构、下级机构对照关系和外部系统列表
//		List organList = os.getJuniorOrgans(user.getOrganId(),date.replaceAll("-",""));
		List list = areaService.getSubArea(areaCode);
//		Area area = areaService.getAreaByUser(getUser(request).getPkid().intValue());
		String areaId = "'"+areaCode+"'";
		for(Iterator itr = list.iterator();itr.hasNext();){
			Area a = (Area)itr.next();
			areaId += ","+"'"+a.getCode()+"'";
		}
		List organList = areaService.getOrgansByAreas(areaId);
		List contrastList = null;
		if(organList.size()>0){
			String organsCode = getOrgansCode(organList);
		//	List contrastlist=os.getJuniorOrganContrasts(user.getOrganId(),date.replaceAll("-",""));
			contrastList = new ArrayList();
			contrastList = os.getOrganContrast(organsCode);
		}
		List systemlist=ds.getEsystem();
		//将机构列表赋值给字符数组organs
		if(organList != null){
			organs =new String[organList.size()][2];		
			for(int i=0;i< organList.size();i++){
				OrganInfo organ=(OrganInfo)organList.get(i);
				organs[i][0]=organ.getCode();
				organs[i][1]=organ.getShort_name();
			}
			request.setAttribute("organs",organs);
		}
		else{
			return null;
		}
		//��ϵͳ��Ϣ�б?ֵ���ַ�����systems
		if(systemlist!=null){
			systems=new String[systemlist.size()][2];
			for(int i=0;i< systemlist.size();i++){
				Esystem system=(Esystem)systemlist.get(i);
				systems[i][0]=system.getPkid().toString();
				systems[i][1]=system.getName();
			}
			request.setAttribute("systems",systems);
		}
		else{
			request.setAttribute("systems",null);
		}
		//将系统信息列表赋值给字符数组systems
		if(contrastList!=null){
			contrasts=new String[contrastList.size()][2];
			for(int i=0;i< contrastList.size();i++){
				OrganContrast contrast=(OrganContrast)contrastList.get(i);
				
				contrasts[i][0]=contrast.getNative_org_id()+"@"+contrast.getSystem_code();
				contrasts[i][1]=contrast.getOuter_org_code();
			}
				request.setAttribute("contrasts",contrasts);
		}
		else{
			request.setAttribute("contrasts",null);
		}
		
		return mapping.findForward("edit");
	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'save' method");
		}
		if(!tokenValidatePass(request)){
			return edit(mapping,form,request,response);
		}
		ContrastForm contrastForm = (ContrastForm)form;
		String areaId = contrastForm.getAreaId();
		AreaService areaService = (AreaService)getBean("areaService");
		List list = areaService.getSubArea(areaId);
		
		Area area = areaService.getAreaByareaCode(areaId);
		list.add(area);
		OrganService os = getOrganService();
		os.removeOrganByOrgan(list);
				
		 //解析字符串
		String contrasts=contrastForm.getOrganContrasts();
		//每行数据间以“＃”分隔
		String split="#";
		String datas[]=contrasts.split(split);
		for(int i=0;i<datas.length;i++){
			//数据间以“◎”分隔
			String datasplit="@";
			String datainfo[]=datas[i].split(datasplit);
			OrganContrast contrast = new OrganContrast();	
			if(datainfo.length>2){
			contrast.setNative_org_id(datainfo[0]);
			contrast.setSystem_code(new Integer(datainfo[1]));
			contrast.setOuter_org_code(datainfo[2]);
			os.saveContrast(contrast);
			}
		}
		String conPath = request.getContextPath() + File.separatorChar;
		
		response.sendRedirect(conPath + "contrastAction.do?method=edit&areaCode="+areaId+"&success=1");
		return null;	
	}
//	读取地区列表树
	public ActionForward tree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'tree' method");
		}
		String date = (String)request.getSession().getAttribute("logindate");
		AreaService areaService = (AreaService)this.getBean("areaService");
		String areaId = areaService.getAreaCodeByUser(getUser(request).getPkid().intValue());
		String areaTreeXml = areaService.getAreaTreeXML(areaId,date.replaceAll("-",""));
		request.setAttribute("treeXml", areaTreeXml);
		return mapping.findForward("tree");
	}
	
	private String getOrgansCode(List organList){
		String organsCode = "";
		for(Iterator itr = organList.iterator();itr.hasNext();){
			OrganInfo organInfo = (OrganInfo)itr.next();
			if(organsCode.equals("")){
				organsCode = "'"+organInfo.getCode()+"'";
			}else{
				organsCode += ","+"'"+organInfo.getCode()+"'";
			}
		}
		return organsCode;
	}
	/**
	 * 到处对照关系维护表的数据 by高仲博。20130902
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exportContr(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'exportContr' method");
		}
		if (!validateUser(mapping, request)) {
			return mapping.findForward("loginpage");
		}
		request.setAttribute("success", request.getParameter("success"));
		setToken(request);
		OrganService os = getOrganService();
		DictionaryService ds=this.getDictionaryService();
		AreaService areaService = (AreaService)getBean("areaService");
		String areaCode = request.getParameter("areaId");//获取要写入excel的id
		String rootId = areaService.getAreaCodeByUser(getUser(request).getPkid().intValue());
		request.setAttribute("areaCode",areaCode);
		request.setAttribute("rootId",rootId);
		//检索得到当前机构的下级机构、下级机构对照关系和外部系统列表
		List list = areaService.getSubArea(areaCode);//当前机构下级机构的id集合
		String areaId = "'"+areaCode+"'";
		for(Iterator itr = list.iterator();itr.hasNext();){
			Area a = (Area)itr.next();
			areaId += ","+"'"+a.getCode()+"'";
		}
		List organList = areaService.getOrgansByAreas(areaId);//根据地区Id得到地区下机构列表
		List contrastList = null;
		if(organList.size()>0){
			String organsCode = getOrgansCode(organList);
			contrastList = new ArrayList();
			contrastList = os.getOrganContrast(organsCode);//得到机构对照
		}
		List systemlist=ds.getEsystem();
		//将机构列表赋值给字符数组organs
		
			String organs[][] =new String[organList.size()][2];	
			String[] numorgans = new String[organList.size()];
			String[] organsid = new String[organList.size()];
			for(int i=0;i< organList.size();i++){
				OrganInfo organ=(OrganInfo)organList.get(i);
				organs[i][0]=organ.getCode();
				organs[i][1]=organ.getShort_name();
				numorgans[i] = organs[i][1];
				organsid[i] = organs[i][0];
			}
			request.setAttribute("organs",organs);
	
			//将系统信息列表赋值给字符数组systems
		
			String systems[][] =new String[systemlist.size()][2];
			String[] numsystems = new String[systemlist.size()];
			for(int i=0;i< systemlist.size();i++){
				Esystem system=(Esystem)systemlist.get(i);
				systems[i][0]=system.getPkid().toString();
				systems[i][1]=system.getName();
				numsystems[i] = systems[i][1];
			}
			request.setAttribute("systems",systems);
	
			//将对照关系列表赋值给字符数组contrasts
		String contrasts[][] = new String[contrastList.size()][2];;
		String numcontrasts[] = new String[contrastList.size()];
		for(int i=0;i< contrastList.size();i++){
			OrganContrast contrast=(OrganContrast)contrastList.get(i);
			//赋值时将对照关系中的机构ID和系统ID联合起来赋予一个字符串以方便下一步显示时确定位置
			contrasts[i][0]=contrast.getNative_org_id()+"@"+contrast.getSystem_code();
			contrasts[i][1]=contrast.getOuter_org_code();
			numcontrasts[i] = contrasts[i][1];
		}
		request.setAttribute("contrasts",contrasts);		
		//========numsystems列名numorgans行名numcontrasts数据
		String [][] excel= new String[organs.length][systems.length + 2];
		for(int i = 0;i < numorgans.length;i++){
			excel[i][0] = organs[i][1];
			excel[i][1] = organs[i][0];
			for(int j = 0;j < contrasts.length;j++){
				if(organs[i][0].equals(contrasts[j][0].substring(0, contrasts[j][0].length()-2))){
//					if(contrasts[j][1] == null){
//						excel[i][a] = "";Integer.parseInt(contrasts[j][0].charAt(contrasts[j][0].length() - 1))
//					}
					int num = Integer.parseInt(String.valueOf(contrasts[j][0].charAt(contrasts[j][0].length() - 1)));
					excel[i][num+1] = contrasts[j][1];
				}
			}
		}
		HSSFWorkbook wb = new HSSFWorkbook();//工作区
		HSSFSheet sheet= (HSSFSheet) wb.createSheet("系统管理-机构代码对照维护");//创建sheet
		HSSFCellStyle style = (HSSFCellStyle) wb.createCellStyle();//创建单元格，并设置表头居中
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//����
		HSSFRow row = (HSSFRow) sheet.createRow(0);//创建表头
		HSSFCell cell = (HSSFCell) row.createCell(0);
		for (int k = 0; k <= systems.length+1 ; k++) {
			if(k==0){	
				cell = (HSSFCell) row.createCell(k);
				cell.setCellValue("");
				cell.setCellStyle(style);	
			}else if(k==1){
				cell = (HSSFCell) row.createCell(k);
				cell.setCellValue("��id");
				cell.setCellStyle(style);
			}else{
				cell = (HSSFCell) row.createCell(k);
				cell.setCellValue(systems[k-2][1]);
				cell.setCellStyle(style);
			}
		}
		System.out.println("");
//-------------------------------------------st
		for(int i = 0;i < excel.length ;i++){
			row = (HSSFRow) sheet.createRow(i+1);
			for (int k = 0; k <= numsystems.length +1; k++) {
				if(k==0){	
					cell = (HSSFCell) row.createCell(k);
					cell.setCellValue(excel[i][0]);
					cell.setCellStyle(style);	
				}else{
					cell = (HSSFCell) row.createCell(k);
					if(excel[i][1] == null || "".equals(excel[i][1])){
						cell.setCellValue("");
					}
					cell.setCellValue(excel[i][k]);
					cell.setCellStyle(style);
				}	
				
			}
		}		

		String areaname = request.getParameter("areaname");
		areaname = new String(areaname.getBytes("ISO-8859-1"),"utf-8");
		String fileEncode = System.getProperty("file.encoding"); 
		String filename = areaname + new SimpleDateFormat("yyyyMMdd").format(new Date()) + ".xls"; // 生成文件名
		String aaaa = this.getServlet().getServletContext().getRealPath("upload\\"+ filename);
		FileOutputStream fileout = new FileOutputStream(aaaa); // 写到服务器端 
		wb.write(fileout);
		fileout.close();
		
		try {
			// path是指欲下载的文件的路径。
			File file = new File(aaaa);
			// 以流的形式下载文件。
			InputStream fis = new BufferedInputStream(new FileInputStream(aaaa));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			// 清空response
			response.reset();
			// 设置response的Header
			response.setCharacterEncoding("UTF-8");
			response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("GB2312"),"iso8859-1"));
			response.addHeader("Content-Length", "" + file.length());
			OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
			response.setContentType("application/octet-stream");
			toClient.write(buffer);
			toClient.flush();
			toClient.close();
			file.delete();
			} catch (IOException ex) {
			ex.printStackTrace();
			}
		//-------------------end
		return null;
	}
	//gaozhongbo  导入数据importContr
	/**
	 * 导入对照关系维护数据 by 高仲勃 20130902
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
		public ActionForward importContr(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			if (log.isDebugEnabled()) {
				log.info("Entering 'importContr' method");
			}
			//from的使用	
			final String path = request.getSession().getServletContext()
					.getRealPath(null);
			//判断是否是目录，并获取目录和目下文件，如果存在文件则删除。
			File file=new File(path);
			if(file.exists() && file.isDirectory()){
				if(file.listFiles().length>0){
					File delFile[]=file.listFiles();
					int i =file.listFiles().length;
					for(int j=0;j<i;j++){
						delFile[j].delete();
					}
				}
			}
			String importXlsFile = request.getParameter("importcontrastFile");
			
			ContrastForm contrastForm = (ContrastForm)form;
			
			String name = contrastForm.getAreaId();
			FormFile uploadFile = contrastForm.getImportXlsFile();
			String filePaths = "";
			if(uploadFile!=null){
				final String fileName = uploadFile.getFileName();
				final int pos = fileName.indexOf(".");
				filePaths = path + "/" + uploadFile.getFileName();
				if (!"xls".equals(fileName.substring(pos + 1))) {
					return mapping.findForward("error");//错误时返回
				}
				//判断文件名中的报表是否存在。
				String repId = fileName.split("_")[0];
				FileOutputStream outer = new FileOutputStream(path + "/"
						+ uploadFile.getFileName());
				byte[] buffer = uploadFile.getFileData();
				outer.write(buffer);
				outer.close();
				uploadFile.destroy();
			}	
			//15:12=-=-==-=-=-=-=-=-=-=-=-==-=222222222

			//完成的=-=-=-=-=-=-=-=-=-=-==-=-=-=-=-=-=-=-==-=-==-=-=-=-=-=	
				String areaCode = contrastForm.getAreaId();
//			    String areaCode = request.getParameter("areaId");
			    String savearea = areaCode;
				if (!validateUser(mapping, request)) {
					return mapping.findForward("loginpage");
				}
				
				request.setAttribute("success", request.getParameter("success"));
				setToken(request);
				OrganService os = getOrganService();
				DictionaryService ds=this.getDictionaryService();
				String organs[][],contrasts[][],systems[][];
				AreaService areaService = (AreaService)getBean("areaService");
				String rootId = areaService.getAreaCodeByUser(getUser(request).getPkid().intValue());
				request.setAttribute("areaCode",areaCode);
				request.setAttribute("rootId",rootId);
				//检索得到当前机构的下级机构、下级机构对照关系和外部系统列表
				List list = areaService.getSubArea(areaCode);//当前机构下级机构的id集合
				String areaId = "'"+areaCode+"'";
				for(Iterator itr = list.iterator();itr.hasNext();){
					Area a = (Area)itr.next();
					areaId += ","+"'"+a.getCode()+"'";
				}
				List organList = areaService.getOrgansByAreas(areaId);//根据地区Id得到地区下机构列表
				List contrastList = null;
				if(organList.size()>0){
					String organsCode = getOrgansCode(organList);
					contrastList = new ArrayList();
					contrastList = os.getOrganContrast(organsCode);//�õ������
				}
				List systemlist=ds.getEsystem();
				//将机构列表赋值给字符数组organs
					organs =new String[organList.size()][2];
					String[] organstr = new String[organList.size()];
					String[] organsid = new String[organList.size()];
					for(int i=0;i< organList.size();i++){
						OrganInfo organ=(OrganInfo)organList.get(i);
						organs[i][0]=organ.getCode();
						organs[i][1]=organ.getShort_name();
						organstr[i] = organs[i][1];
						organsid[i] = organs[i][0];
					}
					request.setAttribute("organs",organs);
					
					systems=new String[systemlist.size()][2];
					for(int i=0;i< systemlist.size();i++){
						Esystem system=(Esystem)systemlist.get(i);
						systems[i][0]=system.getPkid().toString();
						systems[i][1]=system.getName();
					}
					request.setAttribute("systems",systems);
			//====================================================
		
//			String filepath = request.getParameter("filepath");		
//			
//			filepath = new String(filepath.getBytes("ISO-8859-1"),"utf-8");
//			System.out.println("·��" + filepath);
//			filepath = java.net.URLDecoder.decode(filepath,"utf-8");
			//System.out.println("·��" + filepath);
			InputStream is;
			jxl.Workbook rwb = null;
			try {
				is = new FileInputStream(filePaths);
				rwb = Workbook.getWorkbook(is);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (BiffException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Sheet rs = rwb.getSheet(0);//读取Sheet1

			//获取列名
			 String[] importsys = new String[systems.length+2];
			 Cell cxo = rs.getCell(0, 0);
			 for(int i = 0;i < systems.length+2;i++){
				 cxo = rs.getCell(i, 0);
				 importsys[i] = cxo.getContents();
			 }
			 //循环读取列
			 Cell cox = rs.getCell(0, 0);
			 String[][] excels = new String[organstr.length][importsys.length-1];
			 for(int i = 1;i < importsys.length;i++){
				 for(int j = 0;j < organstr.length;j++){
					 cox = rs.getCell(i, j+1);
					 excels[j][i-1] = cox.getContents();
					 if("".equals(excels[j][i-1]) || excels[j][i-1] == null){
						 //excels[i][j] = null;//
						 excels[j][i-1] = "";
					 }
				 }
			 }
			 
			 //将读取到的数据变为要保存的字符串
			 String saveString = "";
			 for(int i=0;i < organstr.length;i++ ){
					 if(excels[i][0].equals(organsid[i])){
						 for(int z = 1;z < importsys.length-1;z++){
								 saveString += organsid[i];
								 saveString += "@" + z +"@" + excels[i][z] + "#";
						 }
					 }
			 }
					
			 //--------------------------
			
				AreaService areaServic = (AreaService)getBean("areaService");
				List lista = areaService.getSubArea(savearea);
				
				Area area = areaService.getAreaByareaCode(savearea);
				lista.add(area);
				OrganService oss = getOrganService();
				oss.removeOrganByOrgan(lista);
				 //解析字符串
				
			 //-----------------------
			 	String split="#";
				String datas[]=saveString.split(split);
				for(int i=0;i<datas.length;i++){
					//数据间以“◎”分隔
					String datasplit="@";
					String datainfo[]=datas[i].split(datasplit);
					OrganContrast contrast = new OrganContrast();	
					if(datainfo.length>2){
					contrast.setNative_org_id(datainfo[0]);
					contrast.setSystem_code(new Integer(datainfo[1]));
					contrast.setOuter_org_code(datainfo[2]);
					os.saveContrast(contrast);
					}
				}
				
				String conPath = request.getContextPath() + File.separatorChar;
				response.sendRedirect(conPath + "contrastAction.do?method=edit&areaCode="+savearea+"&success=1");
				return null;	
		}
}
