package com.krm.slsint.fileRepositoryManage.dao;

import java.util.List;

import com.krm.ps.framework.dao.DAO;
import com.krm.slsint.fileRepositoryManage.vo.FileRepositoryRecord;

/**
 * <p>Title: �ļ��ֿ����ݲ���ӿ�</p>
 * 
 * <p>Description: ���ӿڶ����˶�<code>code_file_repository</code>��Ļ���� </p>
 * 
 * <p>Copyright: Copyright (c) 2006</p>
 * 
 * <p>Company: KRM Soft </p>
 * 
 * @author ��Ծ�� August 25th, 2008
 */
public interface FileRepositoryDAO extends DAO
{
	/**
	 * �õ�code_file_repository����ͬһ�����ļ�����һ����ʾ���
	 * 
	 * @param fun_id �ļ�����ID�����Դ��ֵ���в�ѯparenid=1014�õ������б?
	 * @return ��һ��Ӧ�ò������ʾ��š�
	 */
	public Long getNextShowOrder(String fun_id);

	/**
	 * �õ�code_file_repository����ͬһ�����ļ���������
	 * 
	 * @param fun_id
	 * @return Long
	 * @deprecated using <code>getNextShowOrder(String)</code> instand.
	 */
	public Long getFileRepositoryMaxOrderNo(String fun_id);

	/**
	 * �õ�code_file_repository���û���Ȩ�޵��ļ��б�
	 * 
	 * @param user_id
	 * @param fun_id
	 * @return List
	 */
	public List getFileList(Long user_id, String fun_id);

	/**
	 * �õ�code_file_repository���û���Ȩ�޵��ļ��б�
	 * 
	 * @param user_id
	 * @param status
	 * @param organId
	 * @param fileName
	 * @param editTime
	 * @param funId
	 * @return List
	 */
	public List getFileList(Long user_id, String funId, String editTime,
		String fileName, String organId, Long status);
	
	/**
	 * ��ݹ��ܷ����ȫ·���ļ���õ��ļ�����
	 * @param funId
	 * @param prefix
	 * @param fileName
	 * @param postfix
	 * @param path
	 * @return
	 */
	public FileRepositoryRecord getRecord(String funId, String prefix,
			String fileName, String postfix, String path);

	public List getfuns(String funId);

	public Long getShowOrder(final String funId);
	/**
	 * ��ݹ���id��·���õ��б�
	 * @param fun_id
	 * @param filePath ����ͼ�?��,·��Ϊ:type + chartid
	 * @return
	 */
	public List getRepostioryRecord(String funId, String filePath);
	public List getRepostioryRecord(String funId);
	
	/**��ݹ��ܴ��룬��)չ��õ��б�
	 * @param organCode
	 * @param fun_id
	 * @param postfix
	 * @return
	 */
	public List getFileRecord(String organCode,String fun_id,String postfix);
	
	/**���funId��)չ��õ��ļ��б�
	 * @param funId
	 * @param postfix
	 * @return
	 */
	public List getMapFile(String funId, String postfix) ;
	public List getfileRepository(String funId, String organ_id);

	public List getFileListByNamePrefix(String funId, String namePrefix);
}
