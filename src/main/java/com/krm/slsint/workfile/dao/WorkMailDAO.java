package com.krm.slsint.workfile.dao;

import java.util.List;

import com.krm.ps.framework.dao.DAO;
import com.krm.slsint.workfile.vo.OleFileData;
public interface WorkMailDAO extends DAO{
	/**
	 * �õ�info_olefile���������(����)
	 * @return
	 */
	public Long getAccessoryMaxOrderNo();
	/**
	 * �õ������ʼ�
	 * @return
	 */
	public List getInbox(Long userId,String status);
	/**
	 * �����ʼ�ɾ�����,����ʼ���ʶΪ9
	 * @param mailId
	 */
	public void updataMail(Long pkId,String status,Long userId);
	/**
	 * ɾ���ʼ�
	 * @param pkId
	 */
	public void deleteMail(Long pkId,String status,Long userId);
	/**
	 * ��ѯ�������ʼ�
	 * @param pOper
	 * @return
	 */	
	public List getOutBox(Long pOper);
	/**
	 * ɾ������ʼ�
	 * @param pkid
	 */
	public void delOutBox(Long pkid);
	/**
	 * �鿴�������ʼ�����
	 * @param pkId
	 * @return
	 */
	public List mailContent(Long pkId);
	/**
	 * �鿴�ռ����ʼ�����
	 * @param pkId
	 * @return
	 */
	public List viewInBoxContent(Long pkId);
	/**
	 * �õ��ռ����б�
	 * @param pkId
	 * @return
	 */
	public List getUsers(Long pkId);
	/**
	 * �õ������б�
	 * @param pkId
	 * @return
	 */
	public List getFiles(Long pkId);
	/**
	 * ���f.pkid�鿴������mail_id,�Ƿ��и���
	 * @param fPkId
	 * @return
	 */
	public List getFile(Long fPkId);
	/**
	 * ��ѯ���� 
	 * @param pkId
	 * @return
	 */
	public List getOleFile(Long pkId);
	/**
	 * ɾ���
	 * @param pkId
	 */
	public void delFile(Long pkId);
	/**
	 * ��ݲ��ű�ŵõ��û�ID
	 * @param organCode
	 * @return
	 */
	public List getUserId(String organCode);
	/**
	 * ��ݲ��ű�ŵõ��û�����
	 * @param organCode
	 * @return
	 */
	public List getUserName(String organCode);
	/**
	 * �ж��Ƿ��и���
	 * @param pkId
	 * @return
	 */
	public List isOle(Long pkId);
	/**
	 * ���븽��
	 * @param of
	 * @param pkId
	 */
	public void insertOle(OleFileData of,Long pkId);
	/**
	 * �ı��ʼ�״̬
	 * @param pkId
	 */
	public void updateMailStatus(Long pkId);
	/**
	 * ��ѯ�������ʼ�
	 * @return
	 */
	public List getNewMail(Long userId);
	/**
	 * �õ��������
	 * @param dicId
	 * @return
	 */
	public List getDicNameById(Long dicId);
	/**
	 * �õ�������Ϣ
	 * @param pkId
	 * @return
	 */
	public List getOleInfo(Long pkId);
	
	/**
	 * �����ʼ�����
	 * @param ft
	 * @return
	 */
//	public void saveMail(FileTransferData ft);
	
	/**
	 * <p>��������ȡ�ʼ�</p> 
	 *
	 * @param userId
	 * @param bdate
	 * @param date
	 * @param zu  2010-5-13 piliang���룬�˲�����ֵΪ"reply"ʱ������4��status����reply�н��й���
	 * 			  ������status��Ϊ�ظ�״̬���в�ѯ
	 * @param status 
	 * @return
	 * @author Ƥ��
	 * @version ����ʱ�䣺2010-5-13 ����07:58:00
	 */
	public List getInboxa(Long userId,String bdate,String date,String zu,String status);
	public void selectUpdataMail(String pkIds,String status,Long userId);
	public List getOutBoxa(Long pOper,String bdate,String date,String zu);
	public List getOutBoxa(Long pOper,String date,String status);
	/**
	 * �õ��û���ID name
	 * @param pkId
	 * @return
	 */
	public List getUsersID(Long pkId);
	/**
	 * ɾ���ϵ������
	 * @param pkId
	 * @param status
	 * @param userId
	 */
	public void deleteAcceptoper(Long pkId ,String status,Long userId);
	/**
	 * ��ѯ����վ
	 * @param pOper
	 * @param date
	 * @param status
	 * @return
	 */
	public List getRecyclebin(Long pOper,String date ,String status);
	/**
	 * ��ѯ������
	 * @param pOper
	 * @param status
	 * @return
	 */
	public List getOutBox(Long pOper,String status);
	/**
	 * �������Ͳ�ѯ�ʼ�
	 * @param pOper
	 * @param status
	 * @param itemType
	 * @return
	 */
	public List getOutBox(Long pOper,String status,String emailBox);
	/**
	 * ���ʼ���״̬�ĳ��ѻظ�
	 * @param pkId
	 * @param userId
	 */
	public void returnUpdateMail(Long pkId,Long userId);
	/**
	 * �޸ĸ���
	 * @param pkId
	 * @param mailid
	 */
	public void updateFile(Long pkId,Long mailid);
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
	/**
	 * �����]������Ͳ�ԃ�]��
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
	 * ������4��ѯ��ͬ���������
	 * @param userId
	 * @param f_oper
	 * @param reportId
	 * @return
	 * add by ydw 20120416
	 */
	public List getSameContentMail(Long userId,Long f_oper,Long reportId);
}
