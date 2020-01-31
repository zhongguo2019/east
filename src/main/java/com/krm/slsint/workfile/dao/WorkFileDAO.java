package com.krm.slsint.workfile.dao;

import java.util.List;

import com.krm.ps.framework.dao.DAO;
import com.krm.slsint.workfile.vo.OleFileData;
import com.krm.slsint.workfile.vo.WorkDirectData;

public interface WorkFileDAO extends DAO{
	/**
	 * �õ�Info_workdirect���������
	 * @return
	 */
	public Long getWorkFileMaxOrderNo();
	/**
	 * �õ�info_olefile���������(����)
	 * @return
	 */
	public Long getAccessoryMaxOrderNo();
	/**
	 * �õ�WorkFile�����б�
	 * @return
	 */
	public List getFileList();
	/**
	 * ��ݲ�ѯ����õ�����ָ���б�
	 * @param title ����
	 * @param content ����
	 * @param kind ����
	 * @param fileSource ��
	 * @param issDate ��������
	 * @return
	 */
	public List getFileList(String title,String content,Long kind,Long fileSource,String issDate);
		
	/**
	 * �õ������б�
	 * @param pkId
	 * @return
	 */
	public List getFiles(Long pkId);
	/**
	 * ɾ��workFile
	 * @param pkId
	 */
	public void delFile(Long pkId,String userName,Long userId,String date);
	
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
	 * ���¹����ļ�
	 * @param o
	 */
	public void updateWork(WorkDirectData wo,Long pkId);
	/**
	 * ���¸���
	 * @param of
	 */
	public void updateOle(OleFileData of ,Long pkId);
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
	 * ��ѯ�����¹���ָ��
	 * @return
	 */
	public List getNewFile();
	
	/**
	 * �ı乤��ָ��״̬
	 * @param pkId
	 */
	public void changeFileStatus(Long pkId);
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
	public void deleteWorkFile(Long fileSourceId);
	
//	/**
//	 * ���湤��ָ��
//	 */
//	public void saveWorkFile(WorkDirectData wo);
//	
//	/**
//	 * ��ѯһ����ָ������
//	 * @return
//	 */
//	public WorkDirectData viewWorkFileContent(Long pkId);
}
