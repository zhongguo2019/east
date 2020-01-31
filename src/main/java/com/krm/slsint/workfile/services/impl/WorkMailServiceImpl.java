package com.krm.slsint.workfile.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.organmanage2.services.AreaService;
import com.krm.ps.sysmanage.usermanage.services.UserService;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.util.Constant;
import com.krm.ps.util.DateUtil;
import com.krm.slsint.fileRepositoryManage.services.FileRepositoryService;
import com.krm.slsint.fileRepositoryManage.vo.FileRepositoryRecord;
import com.krm.slsint.workfile.dao.WorkMailDAO;
import com.krm.slsint.workfile.services.WorkMailService;
import com.krm.slsint.workfile.vo.AcceptoperData;
import com.krm.slsint.workfile.vo.FileTransferData;
import com.krm.slsint.workfile.vo.OleFileData;

public class WorkMailServiceImpl implements WorkMailService{
	private WorkMailDAO workMailDAO;
	private AreaService areaService;
	private UserService userService;
	private FileRepositoryService fileRepositoryService;
	public void setUserService(UserService userService){
		this.userService = userService;
	}
	public void setAreaService(AreaService areaService){
		this.areaService = areaService;
	}
	public void setWorkMailDAO(WorkMailDAO workMailDAO){
		this.workMailDAO = workMailDAO;
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.services.WorkFileService#getInboxList()
	 */
	public List getInboxList(Long userId,String status) {		
		return workMailDAO.getInbox(userId,status);
	}
	

	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.services.WorkFileService#reMailStatus(java.lang.Long)
	 */
	public void updateMailStatus(Long pkId,String status,Long userId) {		
		workMailDAO.updataMail(pkId,status,userId);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.services.WorkFileService#delMail(java.lang.Long)
	 */
	public void delMail(Long pkId,String status,Long userId) {		
		workMailDAO.deleteMail(pkId,status,userId);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.services.WorkFileService#save(com.krm.slsint.workfile.vo.AcceptoperData, com.krm.slsint.workfile.vo.FileTransferData,)
	 */
	public void save(String name,String addresseeId,Long addresserId,String title,String content,
			String sFileName,String dFileName,int reply,String mailid,String itemType,Integer replyDate,int fileSize, byte [] b) {
		
		FileTransferData fileTransferData = new FileTransferData();
		fileTransferData.setStatus("1"); // �ʼ�״̬ 1����
		fileTransferData.setTitle(title);
		fileTransferData.setContent(content);		
		fileTransferData.setPoper(addresserId);
		fileTransferData.setItemtype(itemType); // itemTypeΪ1ʱ�������ʼ�
		fileTransferData.setMailBox("0"); // ������ַ�����Ͳݸ��� 0��������
		Date date = new Date();
		String strDate = DateUtil.getDateTime("yyyyMMdd",date);
		fileTransferData.setDateDate(strDate); // ��������
		workMailDAO.saveObject(fileTransferData);
		String [] inId = addresseeId.split(",");
		for(int i=0;i<inId.length;i++){
			Long id = Long.valueOf(inId[i]);
			AcceptoperData acceptoperData = new AcceptoperData();
			acceptoperData.setAoperId(id);
			acceptoperData.setStatus("0"); // ʹ��״̬
			acceptoperData.setAdate(strDate);
			acceptoperData.setMailId(fileTransferData.getPkId()); // ��Ӧ���ʼ���Ϣ
			acceptoperData.setReply(new Integer(reply)); // 0�����ظ� 1���ظ� 3���ѻظ�
			acceptoperData.setReplyDate(replyDate);
			workMailDAO.saveObject(acceptoperData);			
		}
		if(!mailid.equals("") && !mailid.equals("0")){
			List fileList=fileRepositoryService.getfileRepository(Constant.mailFunId, mailid);
			for(int w=0;w<fileList.size();w++){
				FileRepositoryRecord file=(FileRepositoryRecord)fileList.get(w);
				workMailDAO.updateFile(file.getPkid(), fileTransferData.getPkId());
				
			}
		}
		String [] fileName = sFileName.split(",");
		String [] fileRondomName = dFileName.split(",");
		for(int i=0;i<fileName.length;i++){
			FileRepositoryRecord ff = new FileRepositoryRecord();
			if(fileSize != 0){	
				ff.setEditTime(strDate);
				ff.setFileName(fileName[i]);
				ff.setFilePath(fileRondomName[i]);
				ff.setFunId(Constant.mailFunId);
				ff.setOrganId(fileTransferData.getPkId().toString());
				ff.setUserId(addresserId);
				ff.setStatus(new Long(1));
				ff.setNamePostfix(".doc");
				ff.setFileContent(b);
				Long order = fileRepositoryService.getShowOrder(Constant.mailFunId);
				ff.setShowOrder(new Long(order.longValue()+1));
				workMailDAO.saveObject(ff);
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.services.WorkFileService#save(com.krm.slsint.workfile.vo.AcceptoperData, com.krm.slsint.workfile.vo.FileTransferData, com.krm.slsint.workfile.vo.OleFileData)
	 */
	public void save(String name,String addresseeId,Long addresserId,String title,String content,
			String sFileName,String dFileName,int fileSize, byte [] b) {
		
		FileTransferData fileTransferData = new FileTransferData();			
		fileTransferData.setStatus("1");
		fileTransferData.setTitle(title);
		fileTransferData.setContent(content);		
		fileTransferData.setPoper(addresserId);
		fileTransferData.setItemtype("1"); // itemTypeΪ1ʱ�������ʼ�
		Date date = new Date();
		String strDate = DateUtil.getDateTime("yyyyMMdd",date);
		fileTransferData.setDateDate(strDate);
		workMailDAO.saveObject(fileTransferData);
		String [] inId = addresseeId.split(",");
		for(int i=0;i<inId.length;i++){
			Long id = Long.valueOf(inId[i]);
			AcceptoperData acceptoperData = new AcceptoperData();
			acceptoperData.setAoperId(id);
			acceptoperData.setStatus("0");
			acceptoperData.setAdate(strDate);
			acceptoperData.setMailId(fileTransferData.getPkId());
			workMailDAO.saveObject(acceptoperData);
		}
		String [] fileName = sFileName.split(",");
		String [] fileRondomName = dFileName.split(",");
		for(int i=0;i<fileName.length;i++){
			//�ܷ�У��ʱ���ͽ��ʱ�����txt�ļ������͵��Է��ռ�����
			//�䱣������Ϊinfo_olefile����,���ռ����ȡ�����ı�ΪCODE_FILE_REPOSITORY
			//�ֽ����Ϊ���浽CODE_FILE_REPOSITORY���С�
			//20111123 ���� 
//			OleFileData oleFileData = new OleFileData();
			FileRepositoryRecord ff = new FileRepositoryRecord();
			if(fileSize != 0){
//				oleFileData.setKindId("2");
//				oleFileData.setSFileName(fileName[i]);
//				oleFileData.setDFileName(fileRondomName[i]);
//				oleFileData.setConId(fileTransferData.getPkId());
//				Long accessoryOrderNo = workMailDAO.getAccessoryMaxOrderNo();
//				oleFileData.setShowOrder(new Long(accessoryOrderNo.longValue()+1));
//				oleFileData.setFileBlob(b);
//				workMailDAO.saveObject(oleFileData);
				ff.setEditTime(strDate);
				ff.setFileName(fileName[i]);
				ff.setFilePath(fileRondomName[i]);
				ff.setFunId(Constant.mailFunId);
				ff.setOrganId(fileTransferData.getPkId().toString());
				ff.setUserId(addresserId);
				ff.setStatus(new Long(1));
				ff.setNamePostfix(".doc");
				ff.setFileContent(b);
				Long order = fileRepositoryService.getShowOrder(Constant.mailFunId);
				ff.setShowOrder(new Long(order.longValue()+1));
				workMailDAO.saveObject(ff);
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.services.WorkFileService#getOutboxList(java.lang.Long)
	 */
	public List getOutboxList(Long pOper) {
		FileTransferData fileTransferData = null;
		LinkedList list = new LinkedList();		
		List outboxList = workMailDAO.getOutBox(pOper);
		Iterator it = outboxList.iterator();
		Long id = new Long(0);
		String name = "";
		while(it.hasNext()){
			Object[] object = (Object[])it.next();
			Long lpkId = (Long)object[0];
			if(lpkId.longValue() != id.longValue()){
				fileTransferData = new FileTransferData();
				list.addLast(fileTransferData);
				id = lpkId;
				fileTransferData.setPkId(lpkId);
				fileTransferData.setTitle((String)object[1]);
				fileTransferData.setDateDate((String)object[2]);
				fileTransferData.setUserName((String)object[3]);
				name = (String)object[3];
			}else{
				name += (","+(String)object[3]);
				fileTransferData.setUserName(name);
			}
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.services.WorkFileService#delOutMail(java.lang.Long)
	 */
	public void delOutMail(Long pkid) {		
		workMailDAO.delOutBox(pkid);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.services.WorkFileService#viewMails(java.lang.Long)
	 */
	public List viewMails(Long pkId) {
		List list = workMailDAO.mailContent(pkId);		
		return list;
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.services.WorkFileService#viewInBoxMail(java.lang.Long)
	 */
	public List updateInBoxMail(Long pkId) {
		workMailDAO.updateMailStatus(pkId);
		return workMailDAO.viewInBoxContent(pkId);
	}
	public List updateInBoxMails(Long pkId) {
		//workMailDAO.updateMailStatus(pkId);
		return workMailDAO.mailContent(pkId);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.services.WorkFileService#Name(java.lang.Long)
	 */
	public String getUserName(Long pkId) {
		List users = workMailDAO.getUsers(pkId);
		Iterator it = users.iterator();
		String userName = "";
		while(it.hasNext()){
			String user = (String)it.next();
			if(userName == ""){
				userName += user;
			}else{
				userName += (","+user);
			}
		}
		return userName;
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.services.WorkFileService#getFiles(java.lang.Long)
	 */
	public List getFiles(Long fPkId) {
		return workMailDAO.getFile(fPkId);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.services.WorkFileService#getUser(java.lang.String)
	 */
	public String getUser(String areaCode) {
		String usersId = "";
		String usersName = "";
		List organList = areaService.getOrgansByArea(areaCode);
		List userList = new ArrayList();
		for(Iterator itr = organList.iterator();itr.hasNext();){
			OrganInfo organInfo = (OrganInfo)itr.next();
			List list = userService.getUsers(organInfo.getCode());
			for(Iterator iter = list.iterator();iter.hasNext();){
				User user = (User)iter.next();
				userList.add(user);
			}
		}
		for(Iterator itr = userList.iterator();itr.hasNext();){
			User user = (User)itr.next();
			if(usersId.equals("") && usersName.equals("")){
				usersId = user.getPkid().toString();
				usersName = user.getName();
			}else{
				usersId += ","+user.getPkid().toString();
				usersName += ","+user.getName();
			}
		}
//		List userId = workMailDAO.getUserId(organCode);
//		List userName = workMailDAO.getUserName(organCode);
//		Iterator it = userId.iterator();
//		Iterator it2 = userName.iterator();
//		String usersId = "";
//		String usersName = "";
//		while(it.hasNext()){
//			Long id = (Long)it.next();
//			if(usersId!=""){
//				usersId += ("," + id.toString());
//			}
//			if(usersId == ""){
//				usersId = id.toString();
//			}
//		}
//		while(it2.hasNext()){
//			String name = (String)it2.next();
//			if(usersName != ""){
//				usersName += (","+name);
//			}
//			if(usersName == ""){
//				usersName = name;
//			}
//		}
		String users = usersId + "," + usersName;
		return users;
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.services.WorkFileService#getNewWorkMail()
	 */
	public List getNewWorkMail(Long userId) {
		List newMail = workMailDAO.getNewMail(userId);
		return newMail;
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.services.WorkFileService#getMailOleFile(java.lang.Long)
	 */
	public FileRepositoryRecord getMailOleFile(Long pkId) {
		return (FileRepositoryRecord)workMailDAO.getObject(FileRepositoryRecord.class,pkId);
//		List oleInfo = workMailDAO.getOleInfo(pkId);
//		return oleInfo;
	}
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.services.WorkFileService#getInboxList()
	 */
	public List getInboxLista(Long userId,String bdate,String date,String zu,String status) {		
		return workMailDAO.getInboxa(userId,bdate,date,zu,status);
	}
	public void selectUpdataMail(String pkIds,String status,Long userId){
		 workMailDAO.selectUpdataMail(pkIds, status, userId);
	}
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.services.WorkFileService#getOutboxList(java.lang.Long)
	 */
	public List getOutboxLista(Long pOper,String bdate,String date,String zu) {
		FileTransferData fileTransferData = null;
		LinkedList list = new LinkedList();		
		List outboxList = workMailDAO.getOutBoxa(pOper,bdate,date,zu);
		Iterator it = outboxList.iterator();
		Long id = new Long(0);
		String name = "";
		while(it.hasNext()){
			Object[] object = (Object[])it.next();
			Long lpkId = (Long)object[0];
			if(lpkId.longValue() != id.longValue()){
				fileTransferData = new FileTransferData();
				list.addLast(fileTransferData);
				id = lpkId;
				fileTransferData.setPkId(lpkId);
				fileTransferData.setTitle((String)object[1]);
				fileTransferData.setDateDate((String)object[2]);
				fileTransferData.setUserName((String)object[3]);
				fileTransferData.setItemtype((String)object[5]);
				name = (String)object[3];
			}else{
				name += (","+(String)object[3]);
				fileTransferData.setUserName(name);
			}
			List ofdList=fileRepositoryService.getfileRepository(Constant.mailFunId, object[0].toString());
			if(ofdList.size()==0){
				fileTransferData.setIsAttachment("0");
			}else{
				fileTransferData.setIsAttachment("1");
			}
			
			if(fileTransferData.getUserName().length()>20){
				fileTransferData.setUserName(fileTransferData.getUserName().substring(0,20)+"......");
			}
		}
		return list;
	}
	public void save1(String name,String addresseeId,FileTransferData fileTransfer,int isReturn,
			String sFileName,String dFileName,String mailid,String rmailid,int reply,Integer replyDate,int fileSize, byte [] b) {
		
		FileTransferData fileTransferData = fileTransfer;			
		Date date = new Date();
		String strDate = DateUtil.getDateTime("yyyyMMdd",date);
		fileTransferData.setDateDate(strDate);
		fileTransferData.setMailBox("1");//��ʾ�ݸ���
		workMailDAO.saveObject(fileTransferData);
		if(addresseeId!=null && !addresseeId.equals("")){//�жϷ����˲�Ϊ��
			String [] inId = addresseeId.split(",");
			for(int i=0;i<inId.length;i++){
				Long id = Long.valueOf(inId[i]);
				AcceptoperData acceptoperData = new AcceptoperData();
				acceptoperData.setAoperId(id);
				acceptoperData.setStatus("8");//��ʾ�ݸ����е�����
				acceptoperData.setAdate(strDate);
				acceptoperData.setMailId(fileTransferData.getPkId());
				acceptoperData.setReplyDate(replyDate);
				if(!mailid.equals("") && !mailid.equals("0")){
					if(!rmailid.equals("")&&!rmailid.equals("null")){
						acceptoperData.setRmailId(new Long(rmailid));
					}
					if(rmailid.equals("null")){
						acceptoperData.setRmailId(new Long(mailid));
					}
				}
				if(isReturn==0){
					acceptoperData.setReply(new Integer(isReturn));
				}else{
					acceptoperData.setReply(new Integer(isReturn));
				}
				workMailDAO.saveObject(acceptoperData);
			}
		}
		if(!mailid.equals("") && !mailid.equals("0") && reply==0){
			List fileList=fileRepositoryService.getfileRepository(Constant.mailFunId, mailid);
			for(int w=0;w<fileList.size();w++){
				FileRepositoryRecord file=(FileRepositoryRecord)fileList.get(w);
				workMailDAO.updateFile(file.getPkid(), fileTransferData.getPkId());
				
			}
			
		}
		String [] fileName = sFileName.split(",");
		String [] fileRondomName = dFileName.split(",");
		for(int i=0;i<fileName.length;i++){
			FileRepositoryRecord ff = new FileRepositoryRecord();
			if(fileSize != 0){	
				ff.setEditTime(strDate);
				ff.setFileName(fileName[i]);
				ff.setFilePath(fileRondomName[i]);
				ff.setFunId(Constant.mailFunId);
				ff.setOrganId(fileTransferData.getPkId().toString());
				ff.setUserId(fileTransferData.getPoper());
				ff.setStatus(new Long(1));
				ff.setNamePostfix(".doc");
				ff.setFileContent(b);
				Long order = fileRepositoryService.getShowOrder(Constant.mailFunId);
				ff.setShowOrder(new Long(order.longValue()+1));
				workMailDAO.saveObject(ff);
			}
		}
	}
	public List getOutboxLista(Long pOper,String date,String status) {
		FileTransferData fileTransferData = null;
		LinkedList list = new LinkedList();		
		List outboxList = workMailDAO.getOutBoxa(pOper,date,status);
		Iterator it = outboxList.iterator();
		Long id = new Long(0);
		String name = "";
		while(it.hasNext()){
			Object[] object = (Object[])it.next();
			Long lpkId = (Long)object[0];
			if(lpkId.longValue() != id.longValue()){
				fileTransferData = new FileTransferData();
				list.addLast(fileTransferData);
				id = lpkId;
				fileTransferData.setPkId(lpkId);
				fileTransferData.setTitle((String)object[1]);
				fileTransferData.setDateDate((String)object[2]);
				fileTransferData.setUserName((String)object[3]);
				if(object[4]!=null){
				fileTransferData.setReply(Integer.valueOf(object[4].toString()));
				}else{
					fileTransferData.setReply(Integer.valueOf("0"));
				}
				if(object[6]!=null){
				fileTransferData.setRmailId(new Long(object[6].toString()));
				}
				name = (String)object[3];
			}else{
				name += (","+(String)object[3]);
				fileTransferData.setUserName(name);
			}
			List ofdList=fileRepositoryService.getfileRepository(Constant.mailFunId, object[0].toString());
			if(ofdList.size()==0){
				fileTransferData.setIsAttachment("0");
			}else{
				fileTransferData.setIsAttachment("1");
			}
			if(fileTransferData.getUserName()!=null){
			if(fileTransferData.getUserName().length()>20){
				fileTransferData.setUserName(fileTransferData.getUserName().substring(0,20)+"......");
			}
			}
		}
		return list;
	}
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.services.WorkFileService#Name(java.lang.Long)
	 */
	public List getUserNameID(Long pkId) {
		List usersList = workMailDAO.getUsersID(pkId);
		Iterator it = usersList.iterator();
		String userName="";
		String userId="";
		String pOper="";
		List userIdName=new ArrayList();
		while(it.hasNext()){
			Object[] object = (Object[])it.next();
			if(userName == ""){
				userName += object[0];
			}else{
				userName += (","+object[0]);
			}
			if(userId == ""){
				userId += object[1];
			}else{
				userId += (","+object[1]);
			}
			pOper=object[2].toString();
		}
	
		userIdName.add(userName);
		userIdName.add(userId);
		userIdName.add(pOper);
		return userIdName;
	}
	public void delAcceptoper(Long pkId,String status,Long userId) {		
		workMailDAO.deleteAcceptoper(pkId,status,userId);
	}
	public List getRecyclebin(Long pOper,String status){
		List inbox=workMailDAO.getInbox(pOper, status);//�ռ���
		List outbox=workMailDAO.getOutBox(pOper, status,"0");//������
		List outlinebox=workMailDAO.getOutBoxa(pOper,"1", status);//�ݸ���
		List recyclebinList=new ArrayList();
		recyclebinList.add(inbox);
		recyclebinList.add(outbox);
		recyclebinList.add(outlinebox);
		return recyclebinList;
	}
	public void returnUpdateMail(Long pkId,Long userId) {
		workMailDAO.returnUpdateMail(pkId, userId);
	}
	public void delFile(Long pkId){
		workMailDAO.delFile(pkId);
	}
	public void setFileRepositoryService(FileRepositoryService fileRepositoryService) {
		this.fileRepositoryService = fileRepositoryService;
	}
	/**
	 * ��ԭ������
	 * @param status
	 * @param pkid
	 */
	public void restoreOutBoxs(String status,Long pkid) {
		workMailDAO.restoreOutBoxs(status, pkid);
	}
	/**
	 * �޸��ʼ����
	 * @param itemtype
	 * @param pkid
	 */
	public void updateItemtype(String itemtype,Long pkid){
		workMailDAO.updateItemtype(itemtype, pkid);
	}
	/**
	 * ��ԭ�ռ���
	 * @param status
	 * @param pkid
	 */
	public void restoreinBoxs(String status,Long pkid){
		workMailDAO.restoreinBoxs(status, pkid);
	}
	/**
	 * ���汨���������
	 */
	public void save(String addresseeId,FileTransferData fileTransfer) {
		
		FileTransferData fileTransferData = fileTransfer;			
		
		Date date = new Date();
		String strDate = DateUtil.getDateTime("yyyyMMdd",date);
		fileTransferData.setDateDate(strDate);
		workMailDAO.saveObject(fileTransferData);
		String [] inId = addresseeId.split(",");
		for(int i=0;i<inId.length;i++){
			Long id = Long.valueOf(inId[i]);
			AcceptoperData acceptoperData = new AcceptoperData();
			acceptoperData.setAoperId(id);
			acceptoperData.setStatus("0");
			acceptoperData.setAdate(strDate);
			acceptoperData.setMailId(fileTransferData.getPkId());
			acceptoperData.setReply(new Integer(0));
			acceptoperData.setReplyDate(new Integer(0));
			workMailDAO.saveObject(acceptoperData);
		}
	}
	public List getInbox(Long userId,String status,String itemType){
		return workMailDAO.getInbox(userId, status, itemType);
	}
	
	/**
	 * @see com.krm.slsint.workfile.services.WorkMailService#getMailListToBeAnsweredByUser(java.lang.Long)
	 */
	public List getMailListToBeAnsweredByUser(Long userId)
	{
		return workMailDAO.getMailListToBeAnsweredByUser(userId);
	}
	/**
	 * @see com.krm.slsint.workfile.services.WorkMailService#getMailListToBeAnsweredToUser(java.lang.Long)
	 */
	public List getMailListToBeAnsweredToUser(Long userId)
	{
		return workMailDAO.getMailListToBeAnsweredToUser(userId);
	}
	
	/**
	 * @see com.krm.slsint.workfile.services.WorkMailService#getMailListToRead(java.lang.Long)
	 */
	public List getMailListToRead(Long userId)
	{
		return workMailDAO.getObjectSpecifiedInfo(null, AcceptoperData.class, "aoperId=" + userId + " and status = '0'");
	}
	/**
	 * ������4��ѯ��ͬ���������
	 * @param userId
	 * @param f_oper
	 * @param reportId
	 * @return
	 * add by ydw 20120416
	 */
	public List getSameContentMail(Long userId,Long f_oper,Long reportId){
		return workMailDAO.getSameContentMail(userId, f_oper, reportId);
	}
}
