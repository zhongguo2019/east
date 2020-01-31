package com.krm.slsint.workfile.services;

import java.util.List;

import com.krm.slsint.fileRepositoryManage.vo.FileRepositoryRecord;
import com.krm.slsint.workfile.vo.FileTransferData;

public interface WorkMailService {
	/**
	 * �õ������ʼ�
	 * @return
	 */
	public List getInboxList(Long userId,String status);
	
	/**
	 * �����ʼ�ɾ������,�����ʼ���ʶΪ9
	 * @param mailId
	 */
	public void updateMailStatus(Long pkId,String status,Long userId);
	/**
	 * ɾ���ʼ�
	 * @param pkId
	 */
	public void delMail(Long pkId,String status,Long userId);
	/**
	 * �����ʼ�
	 * @param acceptoperData
	 * @param fileTransferData
	 * @param oleFileData
	 */
	public void save(String name,String addresseeId,Long addresserId,String title,String content,
			         String sFileName,String dFileName,int fileSize, byte [] b);
	/**
	 * �����ʼ�1
	 * @param acceptoperData
	 * @param fileTransferData
	 * @param oleFileData
	 */
	public void save(String name,String addresseeId,Long addresserId,String title,String content,
					 String sFileName,String dFileName,int reply,String mailid,String itemType,Integer replyDate,int fileSize, byte [] b);
	/**
	 * ��ѯ�������ʼ�
	 * @param pOper
	 * @return
	 */
	public List getOutboxList(Long pOper);
	/**
	 * ɾ���������ʼ�
	 * @param pkid
	 */
	public void delOutMail(Long pkid);
	/**
	 * �鿴�������ʼ�����
	 * @param pkId
	 * @return
	 */
	public List viewMails(Long pkId);
	/**
	 * �鿴�ʼ����ʼ�����
	 * @param pkId
	 * @return
	 */
	public List updateInBoxMail(Long pkId);
	/**
	 * ��ѯ�û�����ƴ�ӣ�
	 * @param pkId
	 * @return
	 */
	public String getUserName(Long pkId);
	/**
	 * ����f.pkid�鿴������mail_id,�Ƿ��и���
	 * @param fPkId
	 * @return
	 */
	public List getFiles(Long fPkId);
	/**
	 * ���ݲ��ű�ŵõ��û�
	 * @param organCode
	 * @return
	 */
	public String getUser(String areaCode);
	/**
	 * ��ѯ�������ʼ�
	 * @return
	 */
	public List getNewWorkMail(Long userId);
	/**
	 * �õ��ʼ�����
	 * @param pkid
	 * @return
	 */
	public FileRepositoryRecord getMailOleFile(Long pkid);
	/**
	 * �õ������ʼ�
	 * @return
	 */
	public List getInboxLista(Long userId,String bdate,String date,String zu,String status);
	/**
	 * ���������޸��ʼ�״̬Ϊ9
	 * @param pkIds
	 * @param status
	 * @param userId
	 */
	public void selectUpdataMail(String pkIds,String status,Long userId);
	/**
	 * ��ѯ�������ʼ�
	 * @param pOper
	 * @return
	 */
	public List getOutboxLista(Long pOper,String bdate,String date,String zu);
	/**
	 * �����ʼ�
	 * @param acceptoperData
	 * @param fileTransferData
	 * @param oleFileData
	 */
	public void save1(String name,String addresseeId,FileTransferData fileTransfer,int isReturn,
			String sFileName,String dFileName,String mailid,String rmailid,int reply,Integer replyDate,int fileSize, byte [] b);
	/**
	 * ��ѯ�ݸ���
	 * @param pOper
	 * @param date
	 * @return
	 */
	public List getOutboxLista(Long pOper,String date,String status);
	/**
	 * ��ȡ�û���IDName
	 * @param pkId
	 * @return
	 */
	public List getUserNameID(Long pkId);
	/**
	 * ɾ��Acceptoper��
	 * @param pkId
	 * @param status
	 * @param userId
	 */
	public void delAcceptoper(Long pkId,String status,Long userId);
	
	public List getRecyclebin(Long pOper,String status);
	/**
	 * ���ʼ���״̬�ĳ��ѻظ�
	 * @param pkId
	 * @param userId
	 */
	public void returnUpdateMail(Long pkId,Long userId);
	/**
	 * ɾ������
	 * @param pkId
	 */
	public void delFile(Long pkId);
	/**
	 * ��ԭ������
	 * @param status
	 * @param pkid
	 */
	public void restoreOutBoxs(String status,Long pkid) ;
	/**
	 * �޸��ʼ����
	 * @param itemtype
	 * @param pkid
	 */
	public void updateItemtype(String itemtype,Long pkid) ;
	/**
	 * ��ԭ�ռ���
	 * @param status
	 * @param pkid
	 */
	public void restoreinBoxs(String status,Long pkid) ;
	/*
	 * �鿴�ռ��ʼ�����
	 */
	public List updateInBoxMails(Long pkId);
	/**
	 * �������뱨�����
	 * @param acceptoperData
	 * @param fileTransferData
	 * @param oleFileData
	 */
	public void save(String addresseeId,FileTransferData fileTransfer);
	/**
	 * �����ʼ����Ͳ�ѯ�ʼ�
	 * @param userId
	 * @param status
	 * @param itemType
	 * @return
	 */
	public List getInbox(Long userId,String status,String itemType);
	
	/**
	 * <p>��ѯ��Ҫ�����˸�ָ���û��ظ����ʼ��б�</p> 
	 *
	 * @param userId ָ����
	 * @return
	 * @author Ƥ��
	 * @version ����ʱ�䣺2010-5-13 ����06:19:18
	 */
	public List getMailListToBeAnsweredToUser(Long userId);
	
	/**
	 * <p>��ѯ��Ҫָ����Ա�ظ����˵��ʼ��б�</p> 
	 *
	 * @param userId
	 * @return
	 * @author Ƥ��
	 * @version ����ʱ�䣺2010-5-13 ����06:20:25
	 */
	public List getMailListToBeAnsweredByUser(Long userId);

	/**
	 * <p>��ѯָ���û�δ���ʼ��б�</p> 
	 *
	 * @param userId
	 * @return
	 * @author Ƥ��
	 * @version ����ʱ�䣺2010-5-18 ����09:29:19
	 */
	public List getMailListToRead(Long userId);
	/**
	 * ����������ѯ��ͬ���������
	 * @param userId
	 * @param f_oper
	 * @param reportId
	 * @return
	 * add by ydw 20120416
	 */
	public List getSameContentMail(Long userId,Long f_oper,Long reportId);
}
