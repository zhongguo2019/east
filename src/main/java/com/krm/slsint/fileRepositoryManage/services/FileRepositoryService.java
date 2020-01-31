package com.krm.slsint.fileRepositoryManage.services;

import java.util.List;

import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.slsint.fileRepositoryManage.dao.FileRepositoryDAO;
import com.krm.slsint.fileRepositoryManage.vo.FileRepositoryRecord;

public interface FileRepositoryService
{
	public final static String FUN_ID = "1014";

	/**
	 * �����ѯ�����ļ���code_file_repository��,add by lxk 20080703
	 * 
	 * @param fileData
	 */
	public void saveFileData(FileRepositoryRecord fileData);
	//���ά��ʵ�ֻ�Ƶ�ȹ�j����ӷ���
	public void saveFileDataforjianguan(List saveList);

	/**
	 * �õ�code_file_repository����ͬһ�����ļ���������
	 * 
	 * @param fun_id
	 * @return Long
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

	public List getFileList(Long user_id, String funId, String editTime,
		String fileName, String organId, Long status);

	public List getFuns(String funId);

	public FileRepositoryRecord getRecordById(Long id);

	public void delRecordById(Long id);

	public Long getShowOrder(String funId);
	
	public Long getNextShowOrder(String funId);

	/**
	 * ��ݹ���id��·���õ��б�
	 * @param fun_id
	 * @param filePath ����ͼ�?��,·��Ϊ:type + chartid
	 * @return
	 */
	public List getRepostioryRecord(String funId, String filePath);
	public List getRepostioryRecord(String funId);
	/**����ļ�����ϸ��Ϣ�õ�����ļ�
	 * @param funId
	 * @param prefix
	 * @param fileName
	 * @param postfix
	 * @param path
	 * @return
	 */
	public FileRepositoryRecord getRecord(String funId, String prefix,
			String fileName, String postfix, String path);
	
	/**���pkid�õ��ļ�����ļ���Ϣ
	 * @param pkid
	 * @return
	 */
	public FileRepositoryRecord getRiskRecord(String pkid);
	
	/**��ݻ�͹��ܴ���õ�����Ԥ����ģ��
	 * @param organCode
	 * @param fun_id
	 * @return
	 */
	public List getFileRecord(String organCode,String fun_id,String target);
	
	/**��ݹ��ܴ����)չ��õ���ͼͼƬ��ָ��
	 * @param funId
	 * @param postfix
	 * @return
	 */
	public List getMapFile(String funId,String postfix);
	public List getfileRepository(String funId, String organ_id);
	
	public List transferVoToForm(List list);
	public boolean getAdminId(User user);
	
	/**
	 * <p>�õ�DAO�ӿڣ��������һЩ�Ƚϻ�Ĳ���</p> 
	 *
	 * @return
	 * @author Ƥ��
	 * @version ����ʱ�䣺2010-4-30 ����03:27:37
	 */
	public FileRepositoryDAO getFileRepositoryDAO();
	
	public List getFileListByNamePrefix(String funId, String namePrefix);
}
