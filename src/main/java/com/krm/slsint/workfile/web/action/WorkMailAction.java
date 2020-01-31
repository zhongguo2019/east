package com.krm.slsint.workfile.web.action;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.sysmanage.organmanage.services.OrganService;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.organmanage2.services.AreaService;
import com.krm.ps.sysmanage.organmanage2.services.OrganService2;
import com.krm.ps.sysmanage.organmanage2.services.OrganTreeManager;
import com.krm.ps.sysmanage.organmanage2.vo.Area;
import com.krm.ps.sysmanage.organmanage2.vo.OrganTreeNode;
import com.krm.ps.sysmanage.usermanage.services.UserService;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.sysmanage.usermanage.vo.UserRole;
import com.krm.ps.util.Constant;
import com.krm.ps.util.DateUtil;
import com.krm.ps.util.FuncConfig;
import com.krm.ps.util.RandomGUID;
import com.krm.slsint.fileRepositoryManage.services.FileRepositoryService;
import com.krm.slsint.fileRepositoryManage.vo.FileRepositoryRecord;
//import com.krm.slsint.riskWarning.service.RiskWarningService;
import com.krm.slsint.workfile.bo.infoItemBo;
import com.krm.slsint.workfile.bo.infoListTableBo;
import com.krm.slsint.workfile.services.WorkMailService;
import com.krm.slsint.workfile.vo.FileTransferData;
import com.krm.slsint.workfile.web.form.WorkMailForm;

/**
 * @struts.action name="workMailForm" path="/workMail" scope="request"
 *                validate="false" parameter="method" input="addMail"
 *                
 * @struts.action-forward name="addMail" path="/workfile/sendMail.jsp"
 * @struts.action-forward name="inMailList" path="/workfile/inboxs.jsp"
 * @struts.action-forward name="outMailList" path="/workfile/outbox.jsp"
 * @struts.action-forward name="viewMail" path="/workfile/outboxContent.jsp"
 * @struts.action-forward name="viewInBoxMail" path="/workfile/inboxContent.jsp"
 * @struts.action-forward name="tree" path="/common/tree.jsp"
 * @struts.action-forward name="OutlineBox" path="/workfile/outlineBox.jsp"
 * @struts.action-forward name="Recyclebin" path="/workfile/recyclebin.jsp"
 */
public class WorkMailAction extends BaseAction {
	
	/**
	 * ���ʼ����浽���ݿ�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveMail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		WorkMailService workMailService = this.getWorkMailService();
		User user = (User)request.getSession().getAttribute("user");
		String dir = getServlet().getServletContext().getRealPath("/");
		if(dir.substring(dir.length()-1).equals(File.separator)){
			dir = dir.substring(0,dir.length()-1);
		}
		response.setContentType("text/html; charset=UTF-8");
		WorkMailForm wmf = (WorkMailForm) form;
		String addressee = request.getParameter("addressee");
		String strAddresseeId = request.getParameter("addresseeId");
		String accepid=request.getParameter("accepid");
		if(!accepid.equals("")){
		workMailService.delAcceptoper(new Long(accepid), "8", new Long(0));
		workMailService.delMail(new Long(accepid), "8", new Long(0));
		}else{
			accepid="0";
		}
		Long addresserId = user.getPkid();
		String title = wmf.getTitle();	
		String content = request.getParameter("content");		
		int fileSize = 0;
		String fileName = "";
		String fileRondomName = "";
		/**
		 * 2011-12-06��ʯ��
		 * �������еĸ�����С��Ϊ�����õ�
		 */
		String accessorySize = FuncConfig.getProperty("accessory.size", "10");
		int size = 1024*1024*Integer.valueOf(accessorySize).intValue();
		byte [] b = null;
		Hashtable ht = wmf.getMultipartRequestHandler().getFileElements();
		Enumeration e = ht.keys();
		while(e.hasMoreElements()){
			Object k = e.nextElement();
			FormFile obj = (FormFile)ht.get(k);
			fileSize = obj.getFileSize();
			if(fileSize != 0 && fileSize <= size){				
				String name = obj.getFileName();
				String fileExtendName = "";
				int isSymbol = name.indexOf(".");
				if(isSymbol != -1){
					fileExtendName = name.substring(name.indexOf("."));
				}else{
					fileExtendName = name;
				}
				RandomGUID myGUID = new RandomGUID();
				String rondomName = myGUID.toString() + fileExtendName;

	        	InputStream input = obj.getInputStream();
	        	b = new byte[input.available()];
				input.read(b);
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
			}else{		
				String organId = user.getOrganId();				
				UserService userService = (UserService) getBean("userService");	
				AreaService areaService = (AreaService)getBean("areaService");
				Area area = areaService.getAreaByUser(user.getPkid().intValue());
				String date = (String)request.getSession().getAttribute("logindate");
				date = date.replaceAll("-","");
				List areaList = areaService.getAreasByArea(area.getCode(),date);
				List userList = userService.getUsers(organId);
				request.setAttribute("userList", userList);
				request.setAttribute("areaList", areaList);
				request.setAttribute("fileSize","9");
				return mapping.findForward("addMail");
			}
		}		
		int isReturn=0;
		if(request.getParameter("isReturn")!=null){
			isReturn = Integer.parseInt(request.getParameter("isReturn"));
			
		}
		if(request.getParameter("isReply")!=null){
			workMailService.save(addressee, strAddresseeId, addresserId, title,
					content, fileName, fileRondomName,isReturn,"","1",new Integer(0),fileSize, b);
		}else{
			workMailService.save(addressee, strAddresseeId, addresserId, title,
					content, fileName, fileRondomName,isReturn,accepid,"1",new Integer(0),fileSize, b);
		}
		if(request.getParameter("rmailId")!=null && !request.getParameter("rmailId").equals("")){
		workMailService.returnUpdateMail(Long.valueOf(request.getParameter("rmailId")), Long.valueOf(user.getPkid().toString()));
		}
		if(request.getParameter("isReply")!=null){
			if(request.getParameter("isReply").equals("1")){
				workMailService.returnUpdateMail(Long.valueOf(accepid), Long.valueOf(user.getPkid().toString()));
				PrintWriter out = response.getWriter();	
				out.print("<script type='text/javascript'>alert('�ظ��ʼ��ɹ�'); window.close();</script>");
				out.flush();
				out.close();
			}
		}else{
		String conPath = request.getContextPath() + File.separatorChar;
		response.sendRedirect(conPath + "workMail.do?method=selectMail");
		}
		return null;
	}
	
	/**
	 * ��ѯ�ռ����ʼ�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryMail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WorkMailService workMailService = getWorkMailService();
		List tableBoList=new ArrayList();
		String status=request.getParameter("status");
		if(status==null){
			status="";
		}
		String zu = request.getParameter("zu");
		boolean replyQueryFlag = "reply".equals(zu);
		if (replyQueryFlag)
			log.debug("��ѯ���ظ��ʼ�����������������������������");
		String posinega=request.getParameter("posinega");
		User user = (User) request.getSession().getAttribute("user");
		Long userId = user.getPkid();
		Date date = new Date();
		List mailList = null;
		List thisWeekList=null;
		List lastWeekList=null;
		List lastTwoWeekList=null;
		List lastThreeWeekList=null;
		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
		String dateStr=sim.format(date).toString();
		Date toDay=DateUtil.convertStringToDate("yyyy-MM-dd", dateStr);
		int numWeek =getLastDayOfWeek(toDay);//�����Ǳ��µĵڼ���
		String zuTmp = replyQueryFlag ? zu : "2";
		if(zu==null||zu.equals("date") || replyQueryFlag){//����ǰ������������������Ĭ�ϵ�����
		Date bdate=getFirstDayOfWeek(toDay);//ȡ���ܵ�����һ		
		Date edate=getNextDay(toDay,6);//���ܵ�������
		if(numWeek==1||numWeek==2||numWeek==3||numWeek==4||numWeek==5){
			thisWeekList=this.addInfoItemBo(workMailService
				.getInboxLista(userId,DateUtil.getDateTime("yyyyMMdd", bdate),
					DateUtil.getDateTime("yyyyMMdd",edate), zuTmp,status));//ȡ�������ݵļ���
			tableBoList.add(addinfoListTableBo("ʱ�䣺����",thisWeekList,thisWeekList.size(),1));
		if(numWeek==2||numWeek==3||numWeek==4||numWeek==5){
			bdate= getPreviousWeekday(bdate);
			edate=getNextDay(bdate,6);
			lastWeekList=this.addInfoItemBo(workMailService
				.getInboxLista(userId,DateUtil.getDateTime("yyyyMMdd", bdate),DateUtil.getDateTime("yyyyMMdd",edate), zuTmp,status));//ȡ�������ݵļ���
		tableBoList.add(addinfoListTableBo("ʱ�䣺����",lastWeekList,lastWeekList.size(),2));
		if(numWeek==3||numWeek==4||numWeek==5){
			bdate=getPreviousWeekday(bdate);
			edate=getNextDay(bdate,6);
			lastTwoWeekList=this.addInfoItemBo(workMailService.getInboxLista(userId,
				DateUtil.getDateTime("yyyyMMdd", bdate),
				DateUtil.getDateTime("yyyyMMdd",edate), zuTmp,status));//ȡ���������ݵļ���
			tableBoList.add(addinfoListTableBo("ʱ�䣺������ǰ",lastTwoWeekList,lastTwoWeekList.size(),3));
			if(numWeek==4||numWeek==5){
				bdate=getPreviousWeekday(bdate);
				edate=getNextDay(bdate,6);
				lastThreeWeekList=this.addInfoItemBo(workMailService
					.getInboxLista(userId,DateUtil.getDateTime("yyyyMMdd", bdate),
						DateUtil.getDateTime("yyyyMMdd",edate), zuTmp,status));//ȡ���������ݵļ���
				tableBoList.add(addinfoListTableBo("ʱ�䣺������ǰ",lastThreeWeekList,lastThreeWeekList.size(),4));
			}
		}
		}}
			Date edateM=getFirstDayOfWeek(DateUtil.convertStringToDate("yyyy-MM-dd",sim.format(toDay).toString().substring(0,7)+"-01"));
			String bdateM=DateUtil.getDateTime("yyyyMMdd", edateM).substring(0,6)+"01";
			List lastMonthList=this.addInfoItemBo(workMailService.getInboxLista(userId,bdateM,DateUtil.getDateTime("yyyyMMdd",edateM), replyQueryFlag ? zu : "1",status));//ȡ�������ݵļ���
			tableBoList.add(addinfoListTableBo("ʱ�䣺����",lastMonthList,lastMonthList.size(),5));
			bdateM=bdateM.substring(0,6);
			List moreearly=this.addInfoItemBo(workMailService.getInboxLista(userId,bdateM,DateUtil.getDateTime("yyyyMMdd",edateM), replyQueryFlag ? zu : "0",status));//ȡ�������ݵļ���
			tableBoList.add(addinfoListTableBo("ʱ�䣺����",moreearly,moreearly.size(),6));
			request.setAttribute("tableBoList", tableBoList);
			request.setAttribute("zu","date");
			}
		
		else if(zu.equals("title")){//���ձ�������
			status = "";
			if(posinega==null||posinega.equals("nega")){
				posinega="posi";
				mailList = workMailService.getInboxLista(userId, "","posi","title",status);
			}else{
				posinega="nega";
				mailList = workMailService.getInboxLista(userId, "","nega","title",status);
			}
			List mailList1 = addInfoItemBo(mailList);
			request.setAttribute("mailList", mailList1);
			request.setAttribute("posinega", posinega);
		}
		else if(zu.equals("addresser")){//���շ���������
			status = "";
			if(posinega==null||posinega.equals("nega")){
				posinega="posi";
				mailList = workMailService.getInboxLista(userId, "","posi","addresser",status);
			}else{
				posinega="nega";
				mailList = workMailService.getInboxLista(userId, "","nega","addresser",status);
			}
			List mailList1 = addInfoItemBo(mailList);
			request.setAttribute("mailList", mailList1);
			request.setAttribute("posinega", posinega);
		}	
		return mapping.findForward("inMailList");	
	}
	/**
	 * ��infoListTableBo Bo�����������
	 * @param title
	 * @param list
	 * @param size
	 * @param index
	 * @return
	 */
	public infoListTableBo addinfoListTableBo(String title,List list,int size,int index){
		infoListTableBo iltb=new infoListTableBo();
		iltb.setTitleHeader(title);
		iltb.setInfoList(list);
		iltb.setListSize(size);
		iltb.setIndex(index);
		return iltb;
	}
	/**
	 * ��addInfoItemBo Bo�����������
	 * @param title
	 * @param list
	 * @param size
	 * @param index
	 * @return
	 */
	public List addInfoItemBo(List thisWeekList){
		//WorkMailService workMailService = getWorkMailService();
		List emailList=new ArrayList();
		for(int in=0;in<thisWeekList.size();in++){
			Object[] obj=(Object[])thisWeekList.get(in); 
			
			infoItemBo iib=new infoItemBo();
			iib.setIsRead(String.valueOf(obj[2]));
			iib.setAddresser(String.valueOf(obj[8]));
			iib.setTitle(String.valueOf(obj[6]));
			iib.setDate(String.valueOf(obj[5]));
			iib.setPkId(String.valueOf(obj[0]));
			iib.setFpkId(String.valueOf(obj[3]));
			iib.setIsReply(String.valueOf(obj[9]));
			iib.setItemtype(String.valueOf(obj[10]));
			//List ofdList=workMailService.getFiles(new Long(obj[3].toString()));
			FileRepositoryService fileRepositoryService=(FileRepositoryService)this.getBean("fileRepositoryService");
			List ofdList=fileRepositoryService.getfileRepository(Constant.mailFunId, obj[3].toString());
			if(ofdList.size()==0){
				iib.setIsAttachment("0");
			}else{
				iib.setIsAttachment("1");
			}
			emailList.add(iib);
		}
		return emailList;
	}
	
	/**
	 * ��ѯ�������ʼ�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward selectMail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WorkMailService workMailService = getWorkMailService();
		List tableBoList=new ArrayList();
		String zu = request.getParameter("zu");
		boolean replyQueryFlag = false;
		// 20100513 piliang ���zu�������ʽ����reply_1��reply�ķ�ʽ����ѯ��
		// ����ѯ��Ҫ�ظ������ݣ�����û�лظ���
		
		if (zu != null) 
		{
			replyQueryFlag = zu.matches("reply_\\d");
			if (replyQueryFlag)
				log.debug("��ѯ�ȴ����˻ظ����ʼ��б�����");
		}
		String posinega=request.getParameter("posinega");
		User user = (User) request.getSession().getAttribute("user");
		Long userId = user.getPkid();
		Date date = new Date();
		List mailList = null;
		List thisWeekList=null;
		List lastWeekList=null;
		List lastTwoWeekList=null;
		List lastThreeWeekList=null;
		SimpleDateFormat sim=new SimpleDateFormat("yyyy-MM-dd");
		String dateStr=sim.format(date).toString();
		Date toDay=DateUtil.convertStringToDate("yyyy-MM-dd", dateStr);
		int numWeek =getLastDayOfWeek(toDay);//�����Ǳ��µĵڼ���
		String zuTmp = replyQueryFlag ? zu : "2";
		if(zu==null||zu.equals("date") || replyQueryFlag){//����ǰ������������������Ĭ�ϵ�����
		Date bdate=getFirstDayOfWeek(toDay);//ȡ���ܵ�����һ		
		Date edate=getNextDay(toDay,6);//���ܵ�������
		if(numWeek==1||numWeek==2||numWeek==3||numWeek==4||numWeek==5){
		thisWeekList=workMailService.getOutboxLista(userId,DateUtil.getDateTime("yyyyMMdd", bdate),DateUtil.getDateTime("yyyyMMdd",edate), zuTmp);//ȡ�������ݵļ���
		tableBoList.add(addinfoListTableBo("ʱ�䣺����",thisWeekList,thisWeekList.size(),1));
		if(numWeek==2||numWeek==3||numWeek==4||numWeek==5){
		bdate= getPreviousWeekday(bdate);
		edate=getNextDay(bdate,6);
		lastWeekList=workMailService.getOutboxLista(userId,DateUtil.getDateTime("yyyyMMdd", bdate),DateUtil.getDateTime("yyyyMMdd",edate), zuTmp);//ȡ�������ݵļ���
		tableBoList.add(addinfoListTableBo("ʱ�䣺����",lastWeekList,lastWeekList.size(),2));
		if(numWeek==3||numWeek==4||numWeek==5){
		bdate=getPreviousWeekday(bdate);
		edate=getNextDay(bdate,6);
		lastTwoWeekList=workMailService.getOutboxLista(userId,DateUtil.getDateTime("yyyyMMdd", bdate),DateUtil.getDateTime("yyyyMMdd",edate), zuTmp);//ȡ���������ݵļ���
		tableBoList.add(addinfoListTableBo("ʱ�䣺������ǰ",lastTwoWeekList,lastTwoWeekList.size(),3));
		if(numWeek==4||numWeek==5){
		bdate=getPreviousWeekday(bdate);
		edate=getNextDay(bdate,6);
		lastThreeWeekList=workMailService.getOutboxLista(userId,DateUtil.getDateTime("yyyyMMdd", bdate),DateUtil.getDateTime("yyyyMMdd",edate), zuTmp);//ȡ���������ݵļ���
		tableBoList.add(addinfoListTableBo("ʱ�䣺������ǰ",lastThreeWeekList,lastThreeWeekList.size(),4));
		}}}}
		Date edateM=getFirstDayOfWeek(DateUtil.convertStringToDate("yyyy-MM-dd",sim.format(toDay).toString().substring(0,7)+"-01"));
		String bdateM=DateUtil.getDateTime("yyyyMMdd", edateM).substring(0,6)+"01";
		List lastMonthList=workMailService.getOutboxLista(userId,bdateM,DateUtil.getDateTime("yyyyMMdd",edateM), replyQueryFlag ? zu : "1");//ȡ�������ݵļ���
		tableBoList.add(addinfoListTableBo("ʱ�䣺����",lastMonthList,lastMonthList.size(),5));
		bdateM=bdateM.substring(0,6);
		List moreearly=workMailService.getOutboxLista(userId,bdateM,DateUtil.getDateTime("yyyyMMdd",edateM), replyQueryFlag ? zu : "0");//ȡ�������ݵļ���
		tableBoList.add(addinfoListTableBo("ʱ�䣺����",moreearly,moreearly.size(),6));
		request.setAttribute("tableBoList", tableBoList);
		request.setAttribute("zu","date");
		}
		else if(zu.equals("title")){//���ձ�������
			
			if(posinega==null||posinega.equals("nega")){
				posinega="posi";
				mailList = workMailService.getOutboxLista(userId, "","posi","title");
			}else{
				posinega="nega";
				mailList = workMailService.getOutboxLista(userId, "","nega","title");
			}

			request.setAttribute("mailList", mailList);
			request.setAttribute("posinega", posinega);
		}
		
		/*
		WorkMailService workMailService = getWorkMailService();		
		User user = (User) request.getSession().getAttribute("user");
		Long userId = user.getPkid();		
		List mailList = workMailService.getOutboxLista(userId);	
		request.setAttribute("mailList", mailList);
		*/
		return mapping.findForward("outMailList");
	}
	/**
	 * �����ʼ���־Ϊ9
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updateMail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long pkId = Long.valueOf(request.getParameter("pkId"));
		String status = request.getParameter("status");		
		User user = (User) request.getSession().getAttribute("user");
		Long userId = user.getPkid();		
		WorkMailService workMailService = getWorkMailService();
		workMailService.updateMailStatus(pkId, status, userId);
		String conPath = request.getContextPath() + "/";
		response.sendRedirect(conPath + "workMail.do?method=queryMail&status="
				+ status);
		return null;
	}
	/**
	 * ɾ���ռ����ʼ�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delMail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long pkId = Long.valueOf(request.getParameter("pkId"));
		String status = request.getParameter("status");
		User user = (User) request.getSession().getAttribute("user");
		Long userId = user.getPkid();		
		WorkMailService workMailService = getWorkMailService();
		workMailService.delMail(pkId, status, userId);
		String conPath = request.getContextPath() + "/";
		response.sendRedirect(conPath + "workMail.do?method=queryMail&status="
				+ status);
		return null;
	}
	/**
	 * ���뷢����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward sendMail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		/**
		 * ��÷����˻���ID,�õ������˻���.��ʾ�ڻ���select�е�һ��, ���ݷ����˻���ID,�õ��û�����������Ա,��ʾ�ڻ���select��		 
		 * �����Ա����ʱ����Ա������ӵ��ռ�����(ÿ���ռ��˺��","),����ԱIdһ���� hidden����ԱId.
		 */		
		String date = (String)request.getSession().getAttribute("logindate");
		date = date.replaceAll("-","");
		UserService userService = (UserService)getBean("userService");
		OrganService2 organService2=(OrganService2)getBean("organService2");
		OrganService organService=(OrganService)getBean("organService");
		OrganTreeManager organTreeManager=(OrganTreeManager)getBean("organTreeManager");
		User user1 = (User) request.getSession().getAttribute("user");
		OrganTreeNode otn= organTreeManager.getTopNode(user1.getOrganTreeIdxid());
		OrganInfo oif=organService.getOrgan(new Long(otn.getNodeId()));
		List organInfos = organService2.getSubOrgans(user1.getOrganTreeIdxid(),oif.getPkid().intValue(), 0,date);
		List userList = new ArrayList();
		for(Iterator itr = organInfos.iterator();itr.hasNext();){
			OrganInfo organInfo = (OrganInfo)itr.next();
//			List list = userService.getUsers(organInfo.getCode()); 
			/**
			 * 2011-12-12��ʯ�� 
			 * ��ȡ����ϵ���а�������Ա
			 */
			List list = userService.getSendMailUsers(organInfo.getCode());
			for(Iterator iter = list.iterator();iter.hasNext();){
				User user = (User)iter.next();
				if(!user.getPkid().equals(user1.getPkid())){				
				userList.add(user);
				}
			}
		}
		List AccessList=userService.getRolesAll();
		request.setAttribute("AccessList", AccessList);
		request.setAttribute("userList", userList);
		request.setAttribute("OrganId",oif.getCode());
		request.setAttribute("organInfos", organInfos);
		request.setAttribute("fileSize","0");
		/********************************************************************************************/
		if(request.getParameter("pkId")!=null){
		if(!request.getParameter("pkId").equals("") ){
			Long pkId = Long.valueOf(request.getParameter("pkId"));
			WorkMailService workMailService = getWorkMailService();
			if(request.getParameter("del")!=null){
				if(request.getParameter("del").equals("1")){
					workMailService.delFile(Long.valueOf(request.getParameter("oleid").toString()));
				}
			}
			/**
			 * ����f.pkid=a.mail_id�õ�a.a_operid(�ռ���id),�����ռ���id��user���ѯuser��Name
			 * workFileService.getUserName(pkId)
			 */
			List userNameId = workMailService.getUserNameID(pkId);
			List list = workMailService.viewMails(pkId);		
			FileTransferData fileTransferData = new FileTransferData();
			FileRepositoryService fileRepositoryService=(FileRepositoryService)this.getBean("fileRepositoryService");
			List ole = fileRepositoryService.getfileRepository(Constant.mailFunId, pkId.toString());
			for(Iterator itr = list.iterator();itr.hasNext();){
				Object [] obj = (Object[])itr.next();
				fileTransferData = (FileTransferData)obj[1];
			}
			List oles = new ArrayList();
			Iterator olefile = ole.iterator();
			while (olefile.hasNext()) {
				FileRepositoryRecord oleFileData = new FileRepositoryRecord();
				FileRepositoryRecord object = (FileRepositoryRecord) olefile.next(); 
				oleFileData.setPkid(object.getPkid());
				oleFileData.setFileName(object.getFileName());
				oles.add(oleFileData);
			}
			
			WorkMailForm wmf=new WorkMailForm();
			if(request.getParameter("isReply")!=null){
				if(request.getParameter("isReply").equals("1")){
					wmf.setTitle("Re:"+fileTransferData.getTitle());
					User us=(User)userService.getUser(Long.valueOf(userNameId.get(2).toString()));
					wmf.setAddressee(us.getName());
					wmf.setAddresseeId(userNameId.get(2).toString());
					request.setAttribute("isReply", request.getParameter("isReply"));
					request.setAttribute("isReturn",request.getParameter("isReturn"));
					request.setAttribute("rmailId",request.getParameter("rmailId"));
				}else{
					//request.setAttribute("oles", oles);
					wmf.setTitle(fileTransferData.getTitle());
					wmf.setAddressee(userNameId.get(0).toString());
					wmf.setAddresseeId(userNameId.get(1).toString());
					wmf.setContent(fileTransferData.getContent());
					request.setAttribute("isReturn",request.getParameter("isReturn"));
					request.setAttribute("rmailId",request.getParameter("rmailId"));
				}
			}else{
			request.setAttribute("oles", oles);
			wmf.setTitle(fileTransferData.getTitle());
			wmf.setAddressee(userNameId.get(0).toString());
			wmf.setAddresseeId(userNameId.get(1).toString());
			wmf.setContent(fileTransferData.getContent());
			request.setAttribute("isReturn",request.getParameter("isReturn"));
			request.setAttribute("rmailId",request.getParameter("rmailId"));
			}
			
			request.setAttribute("workMailForm", wmf);
			request.setAttribute("accepid", pkId);
			//request.setAttribute("inmail", request.getParameter("inmail"));
			
		}
		}
		
		if(request.getParameter("flag")!=null){
			if(request.getParameter("flag").equals("1")){
				String content = request.getSession().getAttribute("content").toString();
				request.getSession().removeAttribute("content");
				WorkMailForm fm = (WorkMailForm)form;
				fm.setContent(content);
			}
		}
		return mapping.findForward("addMail");
	}
	
	/**
	 * ɾ���������ʼ�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward dMail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WorkMailService workMailService = getWorkMailService();
		if(request.getParameter("status")!=null&&request.getParameter("status").equals("1")){
			String pkIds=request.getParameter("str");
			Object [] pkids=pkIds.split(",");
			for(int a=0;a<pkids.length;a++){
				workMailService.delOutMail(Long.valueOf(pkids[a].toString()));
			}
		}else{
			Long pkId = Long.valueOf(request.getParameter("pkId"));		
			workMailService.delOutMail(pkId);
		}
		String conPath = request.getContextPath() + "/";
		response.sendRedirect(conPath + "workMail.do?method=selectMail");
		return null;
	}
	/**
	 * ɾ���ݸ����ʼ�
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward dcaogaoMail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WorkMailService workMailService = getWorkMailService();
		if(request.getParameter("status")!=null&&request.getParameter("status").equals("1")){
			String pkIds=request.getParameter("str");
			Object [] pkids=pkIds.split(",");
			for(int a=0;a<pkids.length;a++){
				workMailService.delOutMail(Long.valueOf(pkids[a].toString()));
			}
		}else{
			Long pkId = Long.valueOf(request.getParameter("pkId"));		
			workMailService.delOutMail(pkId);
		}
		String conPath = request.getContextPath() + "/";
		response.sendRedirect(conPath + "workMail.do?method=selsctOutlineBox");
		return null;
	}
	/**
	 * �鿴�������ʼ�����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewMail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long pkId = Long.valueOf(request.getParameter("pkId"));		
		WorkMailService workMailService = getWorkMailService();
		/**
		 * ����f.pkid=a.mail_id�õ�a.a_operid(�ռ���id),�����ռ���id��user���ѯuser��Name
		 * workFileService.getUserName(pkId)
		 */
		String userName = workMailService.getUserName(pkId);
		List list = workMailService.viewMails(pkId);		
		FileTransferData fileTransferData = new FileTransferData();
		FileRepositoryService fileRepositoryService=(FileRepositoryService)this.getBean("fileRepositoryService");
		List ole = fileRepositoryService.getfileRepository(Constant.mailFunId, pkId.toString());
		String users = "";
		for(Iterator itr = list.iterator();itr.hasNext();){
			Object [] obj = (Object[])itr.next();
			fileTransferData = (FileTransferData)obj[1];
			users = (String)obj[0];
		}
		List oles = new ArrayList();
		Iterator olefile = ole.iterator();
		
		FileRepositoryRecord preOleFile = null;
		while (olefile.hasNext()) {
			FileRepositoryRecord oleFileData = new FileRepositoryRecord();
			FileRepositoryRecord object = (FileRepositoryRecord) olefile.next(); 
			// 2010-7-9 ����01:04:20 Ƥ���޸�
			// ��������������Ϊ��������ͬ�ģ��Ͳ�������ʾ��
			if (preOleFile != null 
					&& preOleFile.getOrganId().equals(object.getOrganId()) // ��ͬһ���ʼ��ĸ���
					&& preOleFile.getFileName().equals(object.getFileName()) // �ļ�������ͬ��
					&& preOleFile.getFileContent().length == object.getFileContent().length) // �ļ���СҲ����ͬ��
				continue;
			preOleFile = object;
			oleFileData.setPkid(object.getPkid());
			oleFileData.setFileName(object.getFileName());
			oles.add(oleFileData);
		}
		Long fileCnt = new Long(oles.size());
		request.setAttribute("fileCnt",fileCnt);
		request.setAttribute("oleFileData", oles);
		request.setAttribute("fileTransferData", fileTransferData);
		request.setAttribute("userName", userName);
		request.setAttribute("users", users);
		return mapping.findForward("viewMail");
	} 
	/**
	 * �鿴�ռ����ʼ�����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward viewMails(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long pkId=new Long(0);
		if(!request.getParameter("pkId").equals("")){
			pkId= Long.valueOf(request.getParameter("pkId"));
		}
		Long fPkId = Long.valueOf(request.getParameter("fPkId"));
		WorkMailService workMailService = getWorkMailService();
		List list =null;
		if(request.getParameter("recycle")==null){//���ռ���������鿴�ʼ�����
			list= workMailService.updateInBoxMail(pkId);
		}else{
			if(request.getParameter("recycle").equals("1") || request.getParameter("recycle").equals("2")){//�ӻ���վ�������鿴�ʼ�����
				list= workMailService.updateInBoxMails(fPkId);
			}
			if(request.getParameter("recycle").equals("0")){
				list= workMailService.updateInBoxMail(pkId);
			}
		}
		FileTransferData fileTransferData = new FileTransferData();
		FileRepositoryService fileRepositoryService=(FileRepositoryService)this.getBean("fileRepositoryService");
		List ole = fileRepositoryService.getfileRepository(Constant.mailFunId, fPkId.toString());
		String userName = "";
		for(Iterator itr = list.iterator();itr.hasNext();){
			Object [] obj = (Object[])itr.next();
			userName = (String)obj[0];
			fileTransferData = (FileTransferData)obj[1];
			
		}
		List oles = new ArrayList();
		Iterator olefile = ole.iterator();
		while (olefile.hasNext()) {
			FileRepositoryRecord oleFileData = new FileRepositoryRecord();
			FileRepositoryRecord object = (FileRepositoryRecord) olefile.next();
			oleFileData.setPkid(object.getPkid());
			oleFileData.setFileName(object.getFileName());
			oles.add(oleFileData);
		}
		Long fileCnt = new Long(oles.size());
		request.setAttribute("fpkId", fPkId);
		request.setAttribute("fileCnt",fileCnt);
		request.setAttribute("fileTransferData", fileTransferData);
		request.setAttribute("oleFileData", oles);		
		request.setAttribute("userName", userName);
		request.setAttribute("isReply", request.getParameter("isReply"));
		request.setAttribute("recycle",request.getParameter("recycle"));
		return mapping.findForward("viewInBoxMail");
	}
	/**
	 * ���ظ���
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
		WorkMailService workMailListService = getWorkMailService();
		//RiskWarningService rws = (RiskWarningService)getBean("riskWarningService");
		FileRepositoryRecord frr = workMailListService.getMailOleFile(olePkId);
		//FileTransferData ftd = rws.getRiskWarningItem(new Long(frr.getOrganId()));
		String fileName = frr.getFileName();
		String standardAccessories="yes";
		/*if(ftd.getItemtype().equals("2")){
			System.out.println("fileName.length"+fileName.split("\\.").length);
			if(fileName.length()>16){
				fileName = fileName.substring(0,15);
			}
			if(fileName.split("\\.").length == 1){
				standardAccessories="no";
				String ext = frr.getNamePostfix();
				fileName = fileName+ext;
			}
		}		*/
		response.reset();
		response.setContentType("application/x-download");
		fileName = URLEncoder.encode(fileName, "utf-8");
		response.addHeader("Content-Disposition", "attachment;filename="
				+ fileName);
		
			OutputStream output = response.getOutputStream();
		try {
			byte[] b;
			/*if(ftd.getItemtype().equals("1") ||  standardAccessories=="yes"){
			 b = frr.getFileContent();
			}else{
			 b = rws.buildriskWarningFile(ftd.getBusinessReportId(), ftd.getBusinessOrganCode(), ftd.getBusinessDate(),ftd.getDateDate());
			}*/
			//output.write(b);
			//output.flush();
		} catch (Exception e) {
			System.out.println("Error!!!!");
			e.printStackTrace();
		} finally {
			
			if (output != null) {
				output.close();
				output = null;
			}
		}
		return null;
	}
	/**
	 * ���ݲ��Ų�ѯ�ò�����Ա��(AJAX)
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request , HttpServletResponse response)
			throws Exception{
				log.debug(request.getParameterMap());
				String date = (String)request.getSession().getAttribute("logindate");
				String organId = request.getParameter("organId");
				String layer=request.getParameter("layer");
				String Access=request.getParameter("Access");
				List organInfos=null;
				User user1 = (User) request.getSession().getAttribute("user");				
				OrganService2 organService2=(OrganService2)getBean("organService2");
				UserService userService = (UserService)getBean("userService");
				OrganInfo organInfo1 = organService2.getOrganByCode(organId);				
				response.setContentType("text/html;charset=UTF-8");		
				if(layer.equals("3")){
					organInfos=organService2.getAllSubOrgans(user1.getOrganTreeIdxid(),organInfo1.getPkid().intValue(),date);
				}else{
					organInfos = organService2.getSubOrgans(user1.getOrganTreeIdxid(),organInfo1.getPkid().intValue(), Integer.parseInt(layer),date);
				}
				String userName = "";
				String usersId="";
				
				for(Iterator itr = organInfos.iterator();itr.hasNext();){
					OrganInfo organInfo = (OrganInfo)itr.next();
//					List list = userService.getUsers(organInfo.getCode());
					/**
					 * 2011-12-12��ʯ�� 
					 * ��ȡ����ϵ���а�������Ա
					 */
					List list = userService.getSendMailUsers(organInfo.getCode());
					for(Iterator iter = list.iterator();iter.hasNext();){
						if(Access.equals("all")){
							User user = (User)iter.next();
							if(user.getPkid()!=user1.getPkid()){
								if(usersId.equals("") && userName.equals("")){
									usersId = user.getPkid().toString();
									userName = user.getName();
								}else{
									usersId += ","+user.getPkid().toString();
									userName += ","+user.getName();
								}
							}
						}else{
							User user = (User)iter.next();
							if(user.getPkid()!=user1.getPkid()){
								UserRole role=userService.getUserRole(user.getPkid());
								if(role.getRoleType().toString().equals(Access)){
									if(usersId.equals("") && userName.equals("")){
										usersId = user.getPkid().toString();
										userName = user.getName();
									}else{
										usersId += ","+user.getPkid().toString();
										userName += ","+user.getName();
									}
								}
							}
						}
					}
				}
				
				PrintWriter out = response.getWriter();
				out.print(usersId+","+userName);
				out.flush();
				out.close();
				return null;
			}
	public ActionForward updateNewMail(ActionMapping mapping,ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		Long pkId = Long.valueOf(request.getParameter("pkId"));
		String status = request.getParameter("status");
		User user = (User) request.getSession().getAttribute("user");
		Long userId = user.getPkid();		
		WorkMailService workMailService = getWorkMailService();
		workMailService.updateMailStatus(pkId, status, userId);
		String conPath = request.getContextPath() + "/";
		response.sendRedirect(conPath + "loginAction.do?method=queryNewFile"
				);
		return null;
		
	}
	
//	��ȡ�����б���
	public ActionForward tree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String date = (String)request.getSession().getAttribute("logindate");
		AreaService areaService = (AreaService)this.getBean("areaService");
		String areaId = areaService.getAreaCodeByUser(getUser(request).getPkid().intValue());
		String areaTreeXml = areaService.getAreaTreeXML(areaId,date.replaceAll("-",""));
		request.setAttribute("treeXml", areaTreeXml);
		return mapping.findForward("tree");
	}
	
	public WorkMailService getWorkMailService() {
		return (WorkMailService) this.getBean("workMailService");
	}
//ȡʱ��ķ���
	public static Date getPreviousWeekday(Date date) {
	Calendar c = new GregorianCalendar();
	c.setFirstDayOfWeek(Calendar.MONDAY);
	c.setTime(date);
	int mondayPlus = getMondayPlus(date);
	c.add(GregorianCalendar.DATE, mondayPlus - 7);
	return c.getTime();
	}
	
	private static int getMondayPlus(Date date) {
	Calendar cd = Calendar.getInstance();
	cd.setFirstDayOfWeek(Calendar.MONDAY);
	cd.setTime(date);
	int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; 
	if (dayOfWeek == 1) {
	    return 0;
	} else {
	    return 1 - dayOfWeek;
	}
	}
	
	public static Date getFirstDayOfWeek(Date date) {
	Calendar c = new GregorianCalendar();
	c.setFirstDayOfWeek(Calendar.MONDAY);
	c.setTime(date);
	c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); 
	return c.getTime();
	}
	
	public static int getLastDayOfWeek(Date date) {
	Calendar c = new GregorianCalendar();
	c.setTime(date);
	return c.get(Calendar.WEEK_OF_MONTH);
	}
	public static Date getNextDay(Date date,int seed) {
	Calendar c = new GregorianCalendar();
	c.setFirstDayOfWeek(Calendar.MONDAY);
	c.setTime(date);
	c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + seed); 
	return c.getTime();
	}
	
//ȡʱ�����
	
	/**
	 * �����ʼ���־Ϊ9
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward selsctUpdateMail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//Long pkId = Long.valueOf(request.getParameter("pkId"));
		String pkIds=request.getParameter("str");
		String status = request.getParameter("status");		
		User user = (User) request.getSession().getAttribute("user");
		Long userId = user.getPkid();		
		WorkMailService workMailService = getWorkMailService();
		Object [] pkId=pkIds.split(",");
		for(int a=0;a<pkId.length;a++){
		workMailService.updateMailStatus(new Long(pkId[a].toString()), status, userId);
		}
		String conPath = request.getContextPath() + "/";
		response.sendRedirect(conPath+"workMail.do?method=queryMail");
		return null;
	}
	
	/**
	 * ����ɾ������վ������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward batchDelRecycleBin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String Ids=request.getParameter("str");
		WorkMailService workMailService = getWorkMailService();
		Object [] id=Ids.split(",");
		for(int a=0;a<id.length;a++){
			String isPosition=request.getParameter("isPosition"+id[a]);
			String pkId=request.getParameter("pkId"+id[a]);
			String fpkId=request.getParameter("fPkId"+id[a]);			
			if(isPosition.equals("1")){//��ʾҪɾ������վ���ռ���
				workMailService.restoreinBoxs("7", new Long(pkId));//7��ʾ����ɾ��
			}
			if(isPosition.equals("2")){//��ʾҪɾ������վ�з�����
				workMailService.restoreOutBoxs("7", new Long(fpkId));
			}
			if(isPosition.equals("3")){//��ʾҪɾ������վ�вݸ���
				workMailService.restoreinBoxs("7", new Long(pkId));
				workMailService.restoreOutBoxs("7", new Long(fpkId));
				//workMailService.updateItemtype("3", new Long(fpkId));
			}
		}
		String conPath = request.getContextPath() + "/";
		response.sendRedirect(conPath + "workMail.do?method=selsctRecyclebin");
		return null;
	}
	/**
	 * ������ԭ����վ������
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward batchRestoreRecycleBin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String Ids=request.getParameter("str");
		WorkMailService workMailService = getWorkMailService();
		Object [] id=Ids.split(",");
		for(int a=0;a<id.length;a++){
			String isPosition=request.getParameter("isPosition"+id[a]);
			String pkId=request.getParameter("pkId"+id[a]);
			String fpkId=request.getParameter("fPkId"+id[a]);			
			if(isPosition.equals("1")){//��ʾҪ߀ԭ���ռ���
				workMailService.restoreinBoxs("1", new Long(pkId));
			}
			if(isPosition.equals("2")){//��ʾҪ��ԭ��������
				workMailService.restoreOutBoxs("1", new Long(fpkId));
			}
			if(isPosition.equals("3")){//��ʾҪ��ԭ���ݸ���
				workMailService.restoreinBoxs("8", new Long(pkId));
				workMailService.restoreOutBoxs("8", new Long(fpkId));
				workMailService.updateItemtype("4", new Long(fpkId));
			}
		}
		String conPath = request.getContextPath() + "/";
		response.sendRedirect(conPath + "workMail.do?method=selsctRecyclebin");
		return null;
	}
	/**
	 * ����ݸ���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveOutlineBox(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		WorkMailService workMailService = this.getWorkMailService();
		User user = (User)request.getSession().getAttribute("user");
		String dir = getServlet().getServletContext().getRealPath("/");
		if(dir.substring(dir.length()-1).equals(File.separator)){
			dir = dir.substring(0,dir.length()-1);
		}
		response.setContentType("text/html; charset=UTF-8");
		WorkMailForm wmf = (WorkMailForm) form;
		String addressee = request.getParameter("addressee");
		String strAddresseeId = request.getParameter("addresseeId");
		String accepid=request.getParameter("accepid");
		if(!accepid.equals("")){
		workMailService.delAcceptoper(new Long(accepid), "8", new Long(0));
		workMailService.delMail(new Long(accepid), "8", new Long(0));
		}
		int fileSize = 0;
		String fileName = "";
		String fileRondomName = "";
		/**
		 * 2011-12-06��ʯ��
		 * �������еĸ�����С��Ϊ�����õ�
		 */
		String accessorySize = FuncConfig.getProperty("accessory.size", "10");
		int size = 1024*1024*Integer.valueOf(accessorySize).intValue();
		byte [] b = null;
		Hashtable ht = wmf.getMultipartRequestHandler().getFileElements();
		Enumeration e = ht.keys();
		while(e.hasMoreElements()){
			Object k = e.nextElement();
			FormFile obj = (FormFile)ht.get(k);
			fileSize = obj.getFileSize();
			if(fileSize != 0 && fileSize <= size){				
				String name = obj.getFileName();
				String fileExtendName = "";
				int isSymbol = name.indexOf(".");
				if(isSymbol != -1){
					fileExtendName = name.substring(name.indexOf("."));
				}else{
					fileExtendName = name;
				}
				RandomGUID myGUID = new RandomGUID();
				String rondomName = myGUID.toString() + fileExtendName;

	        	InputStream input = obj.getInputStream();
	        	b = new byte[input.available()];
				input.read(b);
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
			}else{		
				String organId = user.getOrganId();				
				UserService userService = (UserService) getBean("userService");	
				AreaService areaService = (AreaService)getBean("areaService");
				Area area = areaService.getAreaByUser(user.getPkid().intValue());
				String date = (String)request.getSession().getAttribute("logindate");
				date = date.replaceAll("-","");
				List areaList = areaService.getAreasByArea(area.getCode(),date);
				List userList = userService.getUsers(organId);
				request.setAttribute("userList", userList);
				request.setAttribute("areaList", areaList);
				request.setAttribute("fileSize","9");
				return mapping.findForward("addMail");
			}
		}		
		int isReturn=0;
		if(request.getParameter("isReturn")!=null){
			isReturn = Integer.parseInt(request.getParameter("isReturn"));
		}
		FileTransferData ftd=new FileTransferData();
		ftd.setStatus("8");//8��ʾ�ݸ����е�����
		ftd.setPoper(user.getPkid());
		ftd.setTitle(wmf.getTitle());
		ftd.setContent(request.getParameter("content"));
		if(!accepid.equals("")){
			if(request.getParameter("isReply")!=null){
				//�洢�ظ��Ĳݸ��ļ�
				workMailService.save1(addressee, strAddresseeId, ftd, isReturn, fileName, fileRondomName,accepid,"null",Integer.parseInt(request.getParameter("isReply")),new Integer(0),fileSize, b);
			}else{
				//�ڲݸ���洢�ݸ��ļ�
				workMailService.save1(addressee, strAddresseeId, ftd, isReturn, fileName, fileRondomName,accepid,request.getParameter("rmailId"),0,new Integer(0),fileSize, b);
			}
		}else{
			//���Ĵ洢�ݸ��ļ�
			workMailService.save1(addressee, strAddresseeId, ftd, isReturn, fileName, fileRondomName,"","",0,new Integer(0),fileSize, b);
		}
		if(request.getParameter("isReply")!=null){
			if(request.getParameter("isReply").equals("1")){
				PrintWriter out = response.getWriter();	
				out.print("<script type='text/javascript'>alert('����ݸ�ɹ�'); window.close();</script>");
				out.flush();
				out.close();
			}
		}else{
		String conPath = request.getContextPath() + File.separatorChar;
		response.sendRedirect(conPath + "workMail.do?method=selsctOutlineBox");
		}
		return null;
	}
	/**
	 * ��ѯ�ݸ���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward selsctOutlineBox(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WorkMailService workMailService = getWorkMailService();
		String posinega=request.getParameter("posinega");
		User user = (User) request.getSession().getAttribute("user");
		Long userId = user.getPkid();
		List mailList = null;
			if(posinega==null){
				mailList = workMailService.getOutboxLista(userId,"","8");
				
				posinega="posi";
			}
			else if(posinega.equals("nega")){
				posinega="posi";
				mailList = workMailService.getOutboxLista(userId,"posi","8");
			
			}else if(posinega.equals("posi")){
				posinega="nega";
				mailList = workMailService.getOutboxLista(userId,"nega","8");
			
			}
			request.setAttribute("mailList", mailList);
			request.setAttribute("posinega", posinega);
		return mapping.findForward("OutlineBox");
	}
	/**
	 * ��ѯ����վ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward selsctRecyclebin(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WorkMailService workMailService = getWorkMailService();
		FileRepositoryService fileRepositoryService=(FileRepositoryService)this.getBean("fileRepositoryService");
		UserService userService = (UserService)getBean("userService");
		User user = (User) request.getSession().getAttribute("user");
		Long userId = user.getPkid();
		List recyle=workMailService.getRecyclebin(userId, "9");
		List mailList=new ArrayList();
		
		List inBox=(List)recyle.get(0);
		List outBox=(List)recyle.get(1);
		List outlinebox=(List)recyle.get(2);
		for(int a=0;a<inBox.size();a++){
			Object[] obj=(Object[])inBox.get(a); 
			infoItemBo iib=new infoItemBo();
			iib.setAddresser(obj[8].toString());
			iib.setTitle(obj[6].toString());
			iib.setDate(obj[5].toString());
			iib.setPkId(obj[0].toString());
			iib.setFpkId(obj[3].toString());
			iib.setItemtype(obj[9].toString());
			iib.setIsRead("1");//��ʾ�ռ��������
			List ofdList=fileRepositoryService.getfileRepository(Constant.mailFunId,obj[3].toString());
			if(ofdList.size()==0){
				iib.setIsAttachment("0");
			}else{
				iib.setIsAttachment("1");
			}
			mailList.add(iib);
		}
		Long id = new Long(0);
		for(int b=0;b<outBox.size();b++){
			Object[] obj=(Object[])outBox.get(b); 
			Long lpkId = (Long)obj[0];
			if(lpkId.longValue() != id.longValue()){
			infoItemBo iib=new infoItemBo();
			User users=userService.getUser(new Long(obj[4].toString()));
			id = lpkId;
			iib.setAddresser(users.getName());
			iib.setTitle(obj[1].toString());
			iib.setDate(obj[2].toString());
			iib.setPkId(obj[5].toString());
			iib.setFpkId(lpkId.toString());
			iib.setIsRead("2");//��ʾ�����������
			iib.setItemtype(obj[6].toString());
			
			List ofdList=fileRepositoryService.getfileRepository(Constant.mailFunId,lpkId.toString());
			if(ofdList.size()==0){
				iib.setIsAttachment("0");
			}else{
				iib.setIsAttachment("1");
			}
			mailList.add(iib);
			}
			
		}
		Long id1 = new Long(0);
		for(int b=0;b<outlinebox.size();b++){
			Object[] obj=(Object[])outlinebox.get(b); 
			Long lpkId = (Long)obj[0];
			if(lpkId.longValue() != id1.longValue()){
			infoItemBo iib=new infoItemBo();
//			User users=new User();
//			if(obj[4]!=null){
//			users=userService.getUser(new Long(obj[4].toString()));
//			}
			id1 = lpkId;
//			if(users!=null){
//			iib.setAddresser(users.getName());
//			}else{
				iib.setAddresser(user.getName());
//			}
			iib.setTitle(obj[1].toString());
			iib.setDate(obj[2].toString());
			if(obj[7]!=null){
			iib.setPkId(obj[7].toString());
			}
			iib.setFpkId(lpkId.toString());
			iib.setIsRead("3");//�ݸ�������
			
			List ofdList=fileRepositoryService.getfileRepository(Constant.mailFunId,lpkId.toString());
			if(ofdList.size()==0){
				iib.setIsAttachment("0");
			}else{
				iib.setIsAttachment("1");
			}
			mailList.add(iib);
			}
			
		}
		request.setAttribute("mailList", mailList);
		return mapping.findForward("Recyclebin");
	}
	/**
	 * ���ʼ��ӻ���վ�л�ԭ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward restoreMail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		WorkMailService workMailService = getWorkMailService();
		String isPosition=request.getParameter("isPosition");
		String pkId=request.getParameter("pkId");
		String fpkId=request.getParameter("fPkId");
		if(isPosition.equals("1")){//��ʾҪ߀ԭ���ռ���
			workMailService.restoreinBoxs("1", new Long(pkId));
		}
		if(isPosition.equals("2")){//��ʾҪ��ԭ��������
			workMailService.restoreOutBoxs("1", new Long(fpkId));
		}
		if(isPosition.equals("3")){//��ʾҪ��ԭ���ݸ���
			workMailService.restoreinBoxs("8", new Long(pkId));
			workMailService.restoreOutBoxs("8", new Long(fpkId));
			//workMailService.updateItemtype("3", new Long(fpkId));
		}
		String conPath = request.getContextPath() + File.separatorChar;
		response.sendRedirect(conPath + "workMail.do?method=selsctRecyclebin");
		return null;
	}
}
