package com.krm.slsint.workfile.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.krm.slsint.workfile.vo.WorkDirectData;
import com.krm.slsint.workfile.vo.OleFileData;

public interface WorkFileService {
	/**
	 * ����һ������ָ�����丽������
	 * @param wo
	 * @param of
	 */
	public void saveWorkFile(WorkDirectData workDirectData);
	public void addWorkFile(WorkDirectData wo,String sFileName,String dFileName,int fileSize, List accessoryFile) throws Exception;
	/**
	 * ����һ������ָ������
	 * @param wo
	 * @param of
	 */
	public void updateWorkFile(WorkDirectData wo,String sFileName,String dFileName,Long pkId,int fileSize, List accessoryFile);
	/**
	 * �õ�WorkFile�б�
	 * @return
	 */
	public List getWorkFileList();
	/**
	 * �õ��ļ�����
	 * @param olePkId
	 * @return
	 */
	public OleFileData getOleFile(Long pkId);
	/**
	 * �õ��ļ���������
	 * @param pkId
	 * @return
	 */
	public WorkDirectData updateWorkDirect(Long pkId);
	
	
	/**
	 * ɾ��WorkFile
	 * @param pkId
	 */
	public void delworkFiles(Long pkId,Long userId);
	public void deleteWorkFile(Long fileSourceId);
	
	/**
	 * ���ݲ�ѯ�����õ�����ָ���б�
	 * @param title ����
	 * @param content ����
	 * @param kind ����
	 * @param fileSource ����
	 * @param issDate ��������
	 * @return
	 */
	public List getWorkFileList(String title, String content, Long kind, Long fileSource, String issDate);
	/**
	 * ��ѯ�����ļ�����
	 * @param pkId
	 * @return
	 */
	public WorkDirectData editFiles(Long pkId);
	/**
	 * ��ѯ����
	 * @param pkId
	 * @return
	 */
	public ArrayList getOle(Long pkId);
	/**
	 * ɾ������
	 * @param pkId
	 */
	public void delOleFile(Long pkId);
	
	/**
	 * ��ѯ�����¹���ָ��
	 * @return
	 */
	public List getNewWorkFile();
	
	/**
	 * �õ���������
	 * @param dicId
	 * @return
	 */
	public List getDicName(Long dicId);
	
	/**
	 * <p>��ѯ��ָ���������ڷ����Ĺ���ָ���б�</p> 
	 *
	 * @param beginDate ��ʼ����
	 * @param endDate ��������
	 * @return
	 * @author Ƥ��
	 * @version ����ʱ�䣺2010-5-18 ����10:27:05
	 */
	public List getWorkfileListInLastDays(Date beginDate, Date endDate);
	
}
