package com.krm.ps.model.workinstructions.web.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.hibernate.lob.SerializableBlob;

import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.model.datafill.services.ReportDefineService;
import com.krm.ps.model.workinstructions.service.WorkinstructionsService;
import com.krm.ps.model.workinstructions.vo.Workaccessory;
import com.krm.ps.model.workinstructions.vo.Workcontext;
import com.krm.ps.model.workinstructions.vo.Workcontext1;
import com.krm.ps.model.workinstructions.web.form.WorkinstructionsForm;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.usermanage.vo.User;



public class WorkinstructionsAction extends BaseAction{

	WorkinstructionsService workinstructionsService=(WorkinstructionsService)getBean("workinstructionsService");
	//默认进入工作指引查询树
	public ActionForward defaultwork(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'defaultwork' method");
		}

		String treexml=workinstructionsService.selectworkinstructiontree();
		List<Workcontext> list=workinstructionsService.selectstatus2();
		
		request.setAttribute("treexml",treexml);
		if(list.size()==0){
			request.setAttribute("flag", 1);
		}else{
			Iterator<Workcontext> it=list.iterator();
			while(it.hasNext()){
				Workcontext w=it.next();
				byte[] b=w.getContext();
				request.setAttribute("pkid", w.getPkid());
				request.setAttribute("title",w.getTitle());
				request.setAttribute("displaytitle", w.getDisplaytitle());
				request.setAttribute("begintime",w.getBegintime() );
				request.setAttribute("updatetime",w.getUpdatetime());
				request.setAttribute("treeid",w.getTreeid());
				request.setAttribute("authorid",w.getAuthorid());
				String  name= workinstructionsService.selectname(w.getAuthorid());
				//System.out.println("----"+name);
				request.setAttribute("name",name);
//				String context=null;
//				try {
//					//context=new String(w.getContext(),"GBK");				
//					context = context.replaceAll("<.*?>", "");
//					// context = HTMLDecoder.decode(context); 
//					//System.out.println("-----"+context);
//					 //other=new String(b1,"GBK");
//
//				} catch (UnsupportedEncodingException e) {
//					e.printStackTrace();
//				}
				request.setAttribute("context",w.getContext().toString().replaceAll("<.*?>", ""));
				List list1=workinstructionsService.queryaccessory(w.getPkid());
				request.setAttribute("list1", list1);
			}
		}
		return mapping.findForward("defaultwork");
	}
	
	
	public ActionForward getRightdefaultwork(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'getRightdefaultwork' method");
		}
		String treexml=workinstructionsService.selectworkinstructiontree();
		List<Workcontext> list=workinstructionsService.selectstatus2();
		
		request.setAttribute("treexml",treexml);
		if(list.size()==0){
			request.setAttribute("flag", 1);
		}else{
			Iterator<Workcontext> it=list.iterator();
			while(it.hasNext()){
				Workcontext w=it.next();
				byte[] b=w.getContext();
				request.setAttribute("pkid", w.getPkid());
				request.setAttribute("title",w.getTitle());
				request.setAttribute("displaytitle", w.getDisplaytitle());
				request.setAttribute("begintime",w.getBegintime() );
				request.setAttribute("updatetime",w.getUpdatetime());
				request.setAttribute("treeid",w.getUpdatetime());
//				String context=null;
//				try {
//					 context = new String(w.getContext().toString().getBytes("ISO_8859_1"),"GBK");
//					//context=new String(w.getContext(),"GBK");
//				} catch (UnsupportedEncodingException e) {
//					e.printStackTrace();
//				}
				request.setAttribute("context",w.getContext());
				List list1=workinstructionsService.queryaccessory(w.getPkid());
				request.setAttribute("list1", list1);
			}
		}
		return mapping.findForward("getRightdefaultwork");
	}
	//默认进入工作指引查询树
	public ActionForward defaultwork1(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'defaultwork1' method");
		}
		
		response.setContentType("text/xml;charset=UTF-8");
        response.setHeader("Cache-Control","no-cache"); 
		String treexml=workinstructionsService.selectworkinstructiontree();
		   
         response.getWriter().print(treexml);  
		//JSONValue jsonValue = JSONMapper.toJSON(obj);    
 
		
		return null;
		
	}
	//打开弹窗
	public ActionForward openwindow(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'openwindow' method");
		}
		//System.out.println("status=="+request.getParameter("status"));
		//System.out.println("oldname=="+request.getParameter("oldname"));
		if(Integer.parseInt(request.getParameter("status"))==1){
			return mapping.findForward("newname");
		}else{
			//System.out.println("status=="+request.getParameter("status"));
			//System.out.println("oldname=="+new String(request.getParameter("oldname").getBytes("ISO-8859-1"),"UTF-8"));
			//request.setAttribute("oldname",new String(request.getParameter("oldname").getBytes("ISO-8859-1"),"UTF-8"));
			return mapping.findForward("updatename");
		}
		
		
	}
	//删除一个树节点
	public ActionForward deleteid(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'deleteid' method");
		}
		List list=workinstructionsService.selectworksuperid(Long.valueOf(request.getParameter("pkid")));
		if(list.size()==0){
			workinstructionsService.deleteid(Long.valueOf(request.getParameter("pkid")));
		}else{
			Iterator it=list.iterator();
			while(it.hasNext()){
				Object[]o=(Object[])it.next();
				workinstructionsService.deleteid(Long.valueOf(o[0].toString()));
			}
		}
		//System.out.println("pkid=="+request.getParameter("pkid"));
		return null;
		
	}
	
	//新增一个树节点
	public ActionForward insertid(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'insertid' method");
		}
		//System.out.println("------"+request.getParameter("name2"));
		if(request.getParameter("pkid2")!=null&&!request.getParameter("pkid2").equals("")){
			//System.out.println("jinru 1");
			workinstructionsService.insertid(request.getParameter("name2"),Long.valueOf(request.getParameter("pkid2")));
		}else{
			//System.out.println("jinru 2");
			workinstructionsService.insertid(request.getParameter("name2"),0);
		}
		//new String(oData.getBytes("ISO-8859-1"),"UTF-8");
		//System.out.println("fu id=="+request.getParameter("superid"));
		//System.out.println("newname=="+new String(request.getParameter("newname").getBytes("ISO-8859-1"),"UTF-8"));
		
        response.setContentType("text/html;charset=GBK"); 
        response.setCharacterEncoding("GBK"); 
        PrintWriter writer = response.getWriter(); 
        writer.write(" <script>alert('成功'); window.parent.window.ClosePanel();window.parent.window.addPaneltab('新增节点' ,'workinstructionsAction.do?method=defaultwork'); </script>"); 
        writer.close(); 
        return null;
		//return defaultwork(mapping,form,request,response);
	}
	//修改一个树节点
	public ActionForward updateid(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'updateid' method");
		}
		//System.out.println("�����޸Ľڵ�");
		//System.out.println("====="+request.getParameter("name1"));
		workinstructionsService.updateid(Long.valueOf(request.getParameter("pkid")), request.getParameter("name1"));
		 response.setContentType("text/html;charset=GBK"); 
	        response.setCharacterEncoding("GBK"); 
	        PrintWriter writer = response.getWriter(); 
	        writer.write(" <script>alert('成功');window.parent.window.ClosePanel();window.parent.window.addPaneltab('修改节点','workinstructionsAction.do?method=defaultwork'); </script>"); 
	        writer.close(); 
	        return null;
		//return defaultwork(mapping,form,request,response);
	}
	//查询信息列表
	public ActionForward tiaozhuan(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'tiaozhuan' method");
		}
		//System.out.println("jinru tiaozhuan "); 
		//String treexml=workinstructionsService.selectworkinstructiontree();
		// S/ystem.out.println("-----"+request.getParameter("treeid"));  
		 List list=workinstructionsService.selectinfo(Long.valueOf(request.getParameter("treeid")));
		 request.setAttribute("list", list);
		 request.setAttribute("pkid",request.getParameter("treeid") );
        // response.getWriter().print();
		//return mapping.findForward("context");
		//return defaultwork(mapping,form,request,response);
		 
		return mapping.findForward("tiaozhuan");
	}
	
	
	//新增一条信息
	public ActionForward add(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'add' method");
		}
		WorkinstructionsForm upLoad=(WorkinstructionsForm)form;
	
	//	System.out.println("title="+request.getParameter("title"));
	//	System.out.println("id="+request.getParameter("pkid"));
		User user = (User) request.getSession().getAttribute("user");
		
		Workcontext w=new Workcontext();
		w.setTitle(request.getParameter("title"));
		w.setTreeid(Long.valueOf(request.getParameter("pkid")));
		
		w.setContext(request.getParameter("content").getBytes("GBK"));
		  Date currentTime = new Date();  
		  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		  String begintime = formatter.format(currentTime);
		w.setBegintime(begintime);
		w.setUpdatetime(begintime);
		w.setStatus(1);
		w.setAuthorid(user.getPkid());
		//w.setPkid(Long.valueOf(request.getParameter("pkid")));
		//w.setDisplaytitle(request.getParameter("displaytitle"));
		workinstructionsService.saveinfo(w);
		//System.out.println("=========="+w.getPkid());
		request.setAttribute("treeid",request.getParameter("pkid") );
		String info="成功";
		request.setAttribute("info", info);
		String save_path = "E://updownfile//";
		 //如果目录不存在，则建立。
		java.io.File folder = new java.io.File(save_path);
		 if(!folder.exists()){
		      folder.mkdirs();
		 }
		 List upLoads = upLoad.getFiles();

		List fileList = new ArrayList();

		//System.out.println("lujin"+request.getParameter("url").substring(0,request.getParameter("url").length()-1));
		if(Integer.parseInt(request.getParameter("flag"))==0){
			//System.out.println("wo shi 0");
		}else{
		String[]url1=request.getParameter("url").substring(0,request.getParameter("url").length()-1).split(",");
		for(int i=0;i<Integer.parseInt(request.getParameter("num"));i++){
			Workaccessory  workaccessory=new Workaccessory(); 
			 FormFile ff = (FormFile) upLoads.get(i);
			
		
		// WorkinstructionsForm uf = (WorkinstructionsForm) form;
		 //FormFile ff = uf.getFile();
		 InputStream is = ff.getInputStream(); 
		 //System.out.println("�ļ���="+ff.getFileName());
		 File file =  new   File("E://updownfile", ff.getFileName()); 
		   BufferedOutputStream bos =  new   BufferedOutputStream( 
	                  new   FileOutputStream(file)); 
	          
		      int   b = -1; 
	          while   ((b = is.read()) != -1) { 
	             bos.write(b); 
	         } 
	         bos.close(); 
	         is.close(); 
	         workaccessory.setInfoid(w.getPkid());
	         workaccessory.setUrl(url1[i]);
	         String []u=ff.getFileName().split("\\.");
	         
	         workaccessory.setFilename(u[0]);
	         workaccessory.setHouzhui(u[1]);
	         InputStream is1 = null;
	 	    is1 = new FileInputStream("E://updownfile//"+ff.getFileName());
	 	    byte[] attribute5 = new byte[is1.available()];
	 	   workaccessory.setFile1(attribute5);
	 	    if(attribute5.length>0){
	 			is1.read(attribute5);
	 		}
	         workinstructionsService.saveaccessory(workaccessory);
		}
		}
		return mapping.findForward("add");
	}
	
	public ActionForward deleteinfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'deleteinfo' method");
		}
		workinstructionsService.deleteinfo(Long.valueOf(request.getParameter("id")));
		request.setAttribute("treeid",request.getParameter("treeid") );
		String info="删除成功";
		request.setAttribute("info", info);
		return mapping.findForward("add");
	}
	//修改前的查询
	public ActionForward updatebefore(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'updatebefore' method");
		}
		List<Workcontext> list= workinstructionsService.queryinfo(Long.valueOf(request.getParameter("pkid")));
		Iterator <Workcontext>it=list.iterator();
		while(it.hasNext()){
			
			Workcontext w=it.next();
			request.setAttribute("pkid",w.getPkid());
			//request.setAttribute("displaytitle",w.getDisplaytitle() );
			request.setAttribute("title", w.getTitle());
			request.setAttribute("begintime",w.getBegintime());
			request.setAttribute("updatetime",w.getUpdatetime());
			request.setAttribute("treeid",w.getTreeid());
			request.setAttribute("status", w.getStatus());
			request.setAttribute("authorid", w.getAuthorid());
			String context=null;
			
			try {
				context=new String(w.getContext(),"GBK");
				 System.out.println("-----"+new String(w.getContext(),"GBK"));
				 //other=new String(b1,"GBK");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			request.setAttribute("context",context);
			List list1=workinstructionsService.queryaccessory(Long.valueOf(request.getParameter("pkid")));
			request.setAttribute("list1", list1);
		}
		
		
		return mapping.findForward("updatebefore");
		
	}
	//修改信息
	public ActionForward updateinfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'updateinfo' method");
		}
		WorkinstructionsForm upLoad=(WorkinstructionsForm)form;
		User user = (User) request.getSession().getAttribute("user");
		
			Workcontext w=new Workcontext();
			//System.out.println("---"+request.getParameter("pkid"));
			//System.out.println("---"+request.getParameter("title"));
			//System.out.println("---"+request.getParameter("treeid"));
			//System.out.println("---"+request.getParameter("begintime"));
			w.setPkid(Long.valueOf(request.getParameter("pkid")));
			w.setTitle(request.getParameter("title"));
			w.setTreeid(Long.valueOf(request.getParameter("treeid")));
			w.setBegintime(request.getParameter("begintime"));
			w.setContext(request.getParameter("content").getBytes("GBK"));
			w.setStatus(Integer.parseInt(request.getParameter("status")));
			w.setAuthorid(user.getPkid());
			 Date currentTime = new Date();  
			  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			  String begintime = formatter.format(currentTime);
			
			 
			
				String save_path = "E://updownfile//";
				 //如果目录不存在，则建立。
				java.io.File folder = new java.io.File(save_path);
				 if(!folder.exists()){
				      folder.mkdirs();
				 }
			request.setAttribute("treeid",request.getParameter("treeid") );
			String info="修改成功";
			String []pkid1= request.getParameterValues("pkid1");
			
				List list=workinstructionsService.queryaccessory(w.getPkid());
				if(list.size()==0){
					//S/ystem.out.println("000");
				}else{
					//S/ystem.out.println("111");
					Iterator it=list.iterator();
					while(it.hasNext()){
						boolean b1=false;	
						Object[]o=(Object[])it.next();
						if(pkid1!=null){
							for(int i=0;i<pkid1.length;i++){
								//S/ystem.out.println(pkid1[i]);
								if(pkid1[i].equals(o[0].toString())){
									
									b1=true;
									break;
								}				
							}
							if(b1==false){
								workinstructionsService.deleteaccessory(Long.valueOf(o[0].toString()));
							}
						}else{
							workinstructionsService.deleteaccessory(Long.valueOf(o[0].toString()));
						}
						
						
					}
				}
				
					
		
			 List upLoads = upLoad.getFiles();

				List fileList = new ArrayList();
				

				//System.out.println("lujin"+request.getParameter("url").substring(0,request.getParameter("url").length()-1));
				
	
			
				if(Integer.parseInt(request.getParameter("flag"))==0){
					//System.out.println("wo shi 0");
				}else{
					String[]url1=request.getParameter("url").substring(0,request.getParameter("url").length()-1).split(",");
					//System.out.println("wo shi 1");
					for(int i=0;i<Integer.parseInt(request.getParameter("num"));i++){
						Workaccessory  workaccessory=new Workaccessory(); 
						 FormFile ff = (FormFile) upLoads.get(i);
						
					
					// WorkinstructionsForm uf = (WorkinstructionsForm) form;
					 //FormFile ff = uf.getFile();
					 InputStream is = ff.getInputStream(); 
					
					 File file =  new   File("E://updownfile", ff.getFileName()); 
					   BufferedOutputStream bos =  new   BufferedOutputStream( 
				                  new   FileOutputStream(file)); 
				          
					      int   b = -1; 
				          while   ((b = is.read()) != -1) { 
				             bos.write(b); 
				         } 
				         bos.close(); 
				         is.close(); 
				         workaccessory.setInfoid(w.getPkid());
				         workaccessory.setUrl(url1[i]);
				         String []u=ff.getFileName().split("\\.");
				         
				         workaccessory.setFilename(u[0]);
				         workaccessory.setHouzhui(u[1]);
				         InputStream is1 = null;
				 	    is1 = new FileInputStream("E://updownfile//"+ff.getFileName());
				 	    byte[] attribute5 = new byte[is1.available()];
				 	   workaccessory.setFile1(attribute5);
				 	    if(attribute5.length>0){
				 			is1.read(attribute5);
				 		}
				         workinstructionsService.saveaccessory(workaccessory);
					}
				}	
				
			request.setAttribute("info", info);
			w.setUpdatetime(begintime);
			workinstructionsService.updatecontext(w);
			
			return mapping.findForward("add");
		
		
		
	}
	
	//修改信息状态״̬
	public ActionForward updatestatus(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'updatestatus' method");
		}
		//S/ystem.out.println("11111111");
			Date currentTime = new Date();  
		  SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		  String begintime = formatter.format(currentTime);
		if(Integer.parseInt(request.getParameter("status"))==1){    //改变位置顶
			//System.out.println("zuihou shi jian ="+request.getParameter("updatetime"));
			workinstructionsService.updatestatus1(1, 2,null);
			workinstructionsService.updatestatus(Long.valueOf(request.getParameter("pkid")), 2,begintime);
			
		}else{   //取消置顶
			
			workinstructionsService.updatestatus1(1, 2,begintime);
			
		}
		request.setAttribute("treeid",request.getParameter("treeid"));
		String info="操作成功";
		request.setAttribute("info", info);
		return mapping.findForward("add");
	}
	
	//查询详细信息
	public ActionForward selectinfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'selectinfo' method");
		}
		List<Workcontext> list=workinstructionsService.queryinfo(Long.valueOf(request.getParameter("pkid")));
	
		Iterator <Workcontext>it=list.iterator();
		while(it.hasNext()){
			
			Workcontext w=it.next();
			request.setAttribute("pkid",w.getPkid());
			//request.setAttribute("displaytitle",w.getDisplaytitle() );
			request.setAttribute("title", w.getTitle());
			request.setAttribute("begintime",w.getBegintime());
			request.setAttribute("updatetime",w.getUpdatetime());
			request.setAttribute("treeid",w.getTreeid());
			request.setAttribute("status", w.getStatus());
			request.setAttribute("authorid", w.getAuthorid());
			String name=workinstructionsService.selectname(w.getAuthorid());
			request.setAttribute("name", name);
			String context=null;
			
			try {
				context=new String(w.getContext(),"GBK");
				
				 //other=new String(b1,"GBK");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

			request.setAttribute("context",context);
		}
		List list1=workinstructionsService.queryaccessory(Long.valueOf(request.getParameter("pkid")));
		//System.out.println("-----"+new String(request.getParameter("search").getBytes("ISO-8859-1"),"UTF-8"));
		if(request.getParameter("flag")!=null&&!request.getParameter("flag").equals("")){
			request.setAttribute("flag", request.getParameter("flag"));
			int a=Integer.parseInt(request.getParameter("total"));
			request.setAttribute("total",a);
			request.setAttribute("totalPageNo", a/Integer.parseInt(request.getParameter("pageSize"))+1);
			request.setAttribute("pageNo", request.getParameter("pageNo"));
			request.setAttribute("pageSize", request.getParameter("pageSize"));
			request.setAttribute("checkboxvalue", request.getParameter("checkboxvalue"));
			request.setAttribute("search",new String(request.getParameter("search").getBytes("ISO-8859-1"),"UTF-8"));
			request.setAttribute("pkid", request.getParameter("pkid"));
		}else{
			request.setAttribute("flag", 2);
		}
		
		request.setAttribute("list1", list1);
		return mapping.findForward("selectinfo");
	}	
	
	//下载
	public ActionForward download(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'download' method");
		}
		List list=workinstructionsService.queryaccessory1(Long.valueOf(request.getParameter("pkid")));
		Iterator it=list.iterator();
		
		while(it.hasNext()){
			
			Object[]o=(Object[])it.next();
			o[0].toString();
			//S/ystem.out.println(o[1]);
			byte[] bytes =null;
			OutputStream out = null;
			BufferedInputStream bis = null;
			try {
				SerializableBlob blob = (SerializableBlob) o[1];
				bis = new BufferedInputStream(blob.getBinaryStream());
				bytes = new byte[(int)blob.length()];
				int len = bytes.length;
				int offest = 0;
				int read = 0;
				while(offest<len&&(read=bis.read(bytes, offest, len-offest))>0){
					offest+=read;
				}
			} catch (Exception e) {
				bytes = null;
				e.printStackTrace();
			} finally{
				if(bis!=null){
					try {
						bis.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					bis = null;
				}
			}
			   try {
		   
			    response.reset();//application/x-msdownload
			// response.setContentType("application/x-msdownload");
			 response.setContentType("application/x-download");
			String  fileName =new String(o[2].toString().getBytes("GBK"))+"."+o[3].toString();//= java.net.URLEncoder.encode(o[2].toString(), "UTF-8");
			
			fileName = URLEncoder.encode(fileName,"UTF-8");
			
			response.addHeader("Content-Disposition", "attachment;filename="+fileName);
			   out = response.getOutputStream();
	
			   //response.reset();
			    if (bytes != null) {
			     out.write(bytes);
			     out.flush();
			     
			    }
			   } catch (Exception ex) {
			     ex.printStackTrace();
			   } finally {
			    if (out != null) {
			     try {
			    	
			      out.close();
			     } catch (IOException e) {
			      e.printStackTrace();
			     }
			     out = null;
			    }
			   }

		}
		
		
		
		return null;
	}
	
	//组合查询
	
	public ActionForward selectinfolist(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'selectinfolist' method");
		}
		
		User user = (User) request.getSession().getAttribute("user");  
		
		List <Workcontext1>list=new ArrayList<Workcontext1>();
		int a=0;
		if(Integer.parseInt(request.getParameter("checkboxvalue"))==1){
			 a=workinstructionsService.selectinfonum(request.getParameter("search"), null);
			list=workinstructionsService.selectinfo(request.getParameter("search"), null,Integer.parseInt(request.getParameter("pageNo")),Integer.parseInt(request.getParameter("pageSize")));
		}
		if(Integer.parseInt(request.getParameter("checkboxvalue"))==2){
			 a=workinstructionsService.selectinfonum(null, request.getParameter("search"));
			list=workinstructionsService.selectinfo(null, request.getParameter("search"),Integer.parseInt(request.getParameter("pageNo")),Integer.parseInt(request.getParameter("pageSize")));
		}
		if(Integer.parseInt(request.getParameter("checkboxvalue"))==3){
			 a=workinstructionsService.selectinfonum(request.getParameter("search"), request.getParameter("search"));
			list=workinstructionsService.selectinfo(request.getParameter("search"), request.getParameter("search"),Integer.parseInt(request.getParameter("pageNo")),Integer.parseInt(request.getParameter("pageSize")));
		}
		if(Integer.parseInt(request.getParameter("checkboxvalue"))==0){
			 a=workinstructionsService.selectinfonum(null, null);
			list=workinstructionsService.selectinfo(null, null,Integer.parseInt(request.getParameter("pageNo")),Integer.parseInt(request.getParameter("pageSize")));
		}
		//System.out.println("aaaaaa="+a);
		request.setAttribute("total",a);
		request.setAttribute("totalPageNo", a/Integer.parseInt(request.getParameter("pageSize"))+1);
		request.setAttribute("pageNo", request.getParameter("pageNo"));
		request.setAttribute("pageSize", request.getParameter("pageSize"));
		request.setAttribute("checkboxvalue", request.getParameter("checkboxvalue"));
		request.setAttribute("search",request.getParameter("search"));
		request.setAttribute("list", list);
		
		
		return mapping.findForward("selectinfolist");
		
	}
	public ActionForward edittree(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'edittree' method");
		}
		if(request.getParameter("flag").equals("1")){
			request.setAttribute("flag", 1);
			request.setAttribute("id", request.getParameter("newid"));
		}else{
			request.setAttribute("flag", 2);
			request.setAttribute("oldname",request.getParameter("oldname") );
			request.setAttribute("pkid", request.getParameter("pkid"));
		}
		
		return mapping.findForward("edittree");
	} 
	public ActionForward editinfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'editinfo' method");
		}
		if(request.getParameter("flag").equals("1")){
			request.setAttribute("flag", 1);
			request.setAttribute("pkid", request.getParameter("pkid"));
			
		}else{
			
		}
		
		return mapping.findForward("editinfo");
	}
	
	public ActionForward selectuserinfo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'selectuserinfo' method");
		}
		User user = (User) request.getSession().getAttribute("user");
		OrganInfo organInfo = (OrganInfo)request.getSession().getAttribute("curorgan");
		
		//System.out.println("1111=========================="+organInfo.getFull_name());
		
		
		user.getOrganId();
		InetAddress address = InetAddress.getLocalHost(); 
		address.getHostAddress(); 
		System.out.println("IP=="+address.getHostAddress());
		
		request.setAttribute("fullname", organInfo.getFull_name());
		request.setAttribute("name", user.getName());
		request.setAttribute("loginname", user.getLogonName());
		if(user.getIsAdmin()==2){
			
		}else{
			
		}
		
		return mapping.findForward("selectuserinfo");
	}
	
}
