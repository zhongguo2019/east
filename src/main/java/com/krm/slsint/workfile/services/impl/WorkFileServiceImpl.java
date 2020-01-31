package com.krm.slsint.workfile.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.krm.ps.sysmanage.usermanage.vo.Dictionary;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.util.DateUtil;
import com.krm.ps.util.SysConfig;
import com.krm.slsint.workfile.dao.WorkFileDAO;
import com.krm.slsint.workfile.services.WorkFileService;
import com.krm.slsint.workfile.vo.FileTransferData;
import com.krm.slsint.workfile.vo.OleFileData;
import com.krm.slsint.workfile.vo.WorkDirectData;
public class WorkFileServiceImpl implements WorkFileService {
	private WorkFileDAO workFileDAO;	
	
	public void setWorkFileDAO(WorkFileDAO workFileDAO){
		this.workFileDAO = workFileDAO;
	}
	public void saveWorkFile(WorkDirectData workDirectData){
		Long workShowOrderNo = workFileDAO.getWorkFileMaxOrderNo();
		Dictionary dic = (Dictionary) workFileDAO.getObject(Dictionary.class,workDirectData.getFileSourceId());
		workDirectData.setShowOrder(new Long(workShowOrderNo.longValue()+1));
		workDirectData.setFileSourceName(dic.getDicname());
		workFileDAO.saveObject(workDirectData);
	}
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.services.WorkFileService#addWorkFile(com.krm.slsint.workfile.vo.WorkDirectData, com.krm.slsint.workfile.vo.OleFileData)
	 */
	public void addWorkFile(WorkDirectData wo, String sFileName,String dFileName,int fileSize,  List accessoryFile) throws Exception 
	{
		String [] fileName = sFileName.split(",");
		String [] fileRondomName = dFileName.split(",");
		Long workShowOrderNo = workFileDAO.getWorkFileMaxOrderNo();
		Long accessoryOrderNo = workFileDAO.getAccessoryMaxOrderNo();
		Dictionary dic = (Dictionary) workFileDAO.getObject(Dictionary.class,wo.getFileSourceId());
		wo.setShowOrder(new Long(workShowOrderNo.longValue()+1));
		wo.setFileSourceName(dic.getDicname());
		workFileDAO.saveObject(wo);
		for(int i=0;i<fileName.length;i++){
			OleFileData oleFileData = new OleFileData();
			if(fileSize != 0){
				accessoryOrderNo = workFileDAO.getAccessoryMaxOrderNo();
				oleFileData.setKindId("1");
				oleFileData.setShowOrder(new Long(accessoryOrderNo.longValue()+1));
				oleFileData.setConId(wo.getPkId());
				oleFileData.setDFileName(fileRondomName[i]);
				oleFileData.setSFileName(fileName[i]);
				//===========================================================================
				//add by hejx informix��ݿ� hibernate ������ֶζ�����ĳ��ļ������q��ط�����
				if (SysConfig.DATABASE.toLowerCase().equals("informix")) {
					//�����ļ���Ϣ��·����Ϣ,���������ļ�����ݿ�
					workFileDAO.saveObject(oleFileData);
				} else {
					//�����ļ�����ݿ�
					byte[] b = (byte[]) accessoryFile.get(i);
					oleFileData.setFileBlob(b);
					workFileDAO.saveObject(oleFileData);
				}
				//===========================================================================
			}
		}
		
	}
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.services.WorkFileService#updateWorkFile(com.krm.slsint.workfile.vo.WorkDirectData, com.krm.slsint.workfile.vo.OleFileData)
	 */
	public void updateWorkFile(WorkDirectData wo, String sFileName,String dFileName,Long pkId,int fileSize, List accessoryFile) {
		String [] fileName = sFileName.split(",");
		String [] fileRondomName = dFileName.split(",");
		workFileDAO.updateWork(wo,pkId);
		if(fileSize != 0){
			Long accessoryOrderNo = workFileDAO.getAccessoryMaxOrderNo();
			for(int i=0;i<fileName.length;i++){
				OleFileData oleFileData = new OleFileData();
				oleFileData.setShowOrder(new Long(accessoryOrderNo.longValue()+1));
				oleFileData.setConId(pkId);
				oleFileData.setKindId("1");
				oleFileData.setDFileName(fileRondomName[i]);
				oleFileData.setSFileName(fileName[i]);
				//===========================================================================
				//add by hejx informix��ݿ� hibernate ������ֶζ�����ĳ��ļ������q��ط�����
				if (SysConfig.DATABASE.toLowerCase().equals("informix")) {
					//�����ļ���Ϣ��·����Ϣ,���������ļ�����ݿ�
					workFileDAO.saveObject(oleFileData);
				} else {
					//�����ļ�����ݿ�
					byte[] b = (byte[]) accessoryFile.get(i);
					oleFileData.setFileBlob(b);
					workFileDAO.saveObject(oleFileData);
				}
				//===========================================================================
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.services.WorkFileListService#getWorkFileList()
	 */
	public List getWorkFileList() {
		List list = workFileDAO.getFileList();
		return list;
	}
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.services.WorkFileListService#getWorkDirect(java.lang.Long)
	 */
	public WorkDirectData updateWorkDirect(Long pkId) {
		workFileDAO.changeFileStatus(pkId);
		return (WorkDirectData)workFileDAO.getObject(WorkDirectData.class,pkId);

	}
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.services.WorkFileService#getFileContent(java.lang.Long)
	 */
	public FileTransferData getFileContent(Long pkId) {
		return (FileTransferData)workFileDAO.getObject(FileTransferData.class,pkId);
	}	
	
	
	
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.services.WorkFileService#delworkFiles(java.lang.Long)
	 */
	public void delworkFiles(Long pkId,Long userId) {
		Date time = new Date();
		String date = DateUtil.getDateTime("yyyyMMdd",time);		
		User user = (User)workFileDAO.getObject(User.class,userId);
		String userName = user.getName();		
		workFileDAO.delFile(pkId,userName,userId,date);
	}
	
	
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.services.WorkFileService#getWorkFileList(java.lang.String, java.lang.String, java.lang.Long, java.lang.Long, java.lang.String)
	 */
	public List getWorkFileList(String title, String content, Long kind, Long fileSource, String issDate) {
		String aTitle = title.trim().toLowerCase();
		String aContent = content.trim().toLowerCase();
		return workFileDAO.getFileList(aTitle, aContent, kind, fileSource, issDate);
	}
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.services.WorkFileService#editFiles(java.lang.Long)
	 */
	public WorkDirectData editFiles(Long pkId){
		return (WorkDirectData)workFileDAO.getObject(WorkDirectData.class,pkId);
	}
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.services.WorkFileService#getOle(java.lang.Long)
	 */
	public ArrayList getOle(Long pkId) {
		List list = workFileDAO.getOleFile(pkId);
		ArrayList al = new ArrayList();
		Iterator it = list.iterator();
		while(it.hasNext()){
			OleFileData  oleFileData = new OleFileData();
			Object [] o = (Object[])it.next();
			oleFileData.setPkId((Long)o[0]);
			oleFileData.setKindId((String)o[1]);
			oleFileData.setConId((Long)o[2]);
			oleFileData.setShowOrder((Long)o[3]);
			oleFileData.setSFileName((String)o[4]);
			oleFileData.setDFileName((String)o[5]);
			al.add(oleFileData);
		}
		return al;
	}
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.services.WorkFileService#delOleFile(java.lang.Long)
	 */
	public void delOleFile(Long pkId) {
		workFileDAO.delFile(pkId);
	}
	
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.services.WorkFileService#getOleFile(java.lang.Long)
	 */
	public OleFileData getOleFile(Long pkId) {
		return (OleFileData)workFileDAO.getObject(OleFileData.class,pkId);
	}
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.services.WorkFileService#getNewWorkFile()
	 */
	public List getNewWorkFile() {
		List newFile = workFileDAO.getNewFile();
		return newFile;
	}
	
	/* (non-Javadoc)
	 * @see com.krm.slsint.workfile.services.WorkFileService#getDicName(java.lang.Long)
	 */
	public List getDicName(Long dicId) {
		List dicName = workFileDAO.getDicNameById(dicId);
		return dicName;
	}
	public void deleteWorkFile(Long fileSourceId)
	{
		workFileDAO.deleteWorkFile(fileSourceId);
	}
	
	/**
	 * @see com.krm.slsint.workfile.services.WorkFileService#getWorkfileListInLastDays(java.util.Date, java.util.Date)
	 */
	public List getWorkfileListInLastDays(Date beginDate, Date endDate)
	{
		String begin = DateUtil.getDateTime("yyyyMMdd", beginDate);
		String end = DateUtil.getDateTime("yyyyMMdd", endDate);
		return workFileDAO.getObjectSpecifiedInfo(null, WorkDirectData.class,
			"issDate >= '" + begin + "' and issDate <= '" + end + "' and status <> '9'");
	}
}
