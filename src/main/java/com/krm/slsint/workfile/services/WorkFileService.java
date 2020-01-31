package com.krm.slsint.workfile.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.krm.slsint.workfile.vo.WorkDirectData;
import com.krm.slsint.workfile.vo.OleFileData;

public interface WorkFileService {
	/**
	 * 保存一个工作指引及其附件内容
	 * @param wo
	 * @param of
	 */
	public void saveWorkFile(WorkDirectData workDirectData);
	public void addWorkFile(WorkDirectData wo,String sFileName,String dFileName,int fileSize, List accessoryFile) throws Exception;
	/**
	 * 更新一个工作指引内容
	 * @param wo
	 * @param of
	 */
	public void updateWorkFile(WorkDirectData wo,String sFileName,String dFileName,Long pkId,int fileSize, List accessoryFile);
	/**
	 * 得到WorkFile列表
	 * @return
	 */
	public List getWorkFileList();
	/**
	 * 得到文件名称
	 * @param olePkId
	 * @return
	 */
	public OleFileData getOleFile(Long pkId);
	/**
	 * 得到文件正文内容
	 * @param pkId
	 * @return
	 */
	public WorkDirectData updateWorkDirect(Long pkId);
	
	
	/**
	 * 删除WorkFile
	 * @param pkId
	 */
	public void delworkFiles(Long pkId,Long userId);
	public void deleteWorkFile(Long fileSourceId);
	
	/**
	 * 根据查询条件得到工作指引列表
	 * @param title 标题
	 * @param content 正文
	 * @param kind 分类
	 * @param fileSource 出处
	 * @param issDate 发布日期
	 * @return
	 */
	public List getWorkFileList(String title, String content, Long kind, Long fileSource, String issDate);
	/**
	 * 查询工作文件内容
	 * @param pkId
	 * @return
	 */
	public WorkDirectData editFiles(Long pkId);
	/**
	 * 查询附件
	 * @param pkId
	 * @return
	 */
	public ArrayList getOle(Long pkId);
	/**
	 * 删除附件
	 * @param pkId
	 */
	public void delOleFile(Long pkId);
	
	/**
	 * 查询所有新工作指引
	 * @return
	 */
	public List getNewWorkFile();
	
	/**
	 * 得到分类名称
	 * @param dicId
	 * @return
	 */
	public List getDicName(Long dicId);
	
	/**
	 * <p>查询在指定间隔间隔内发出的工作指引列表</p> 
	 *
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @return
	 * @author 皮亮
	 * @version 创建时间：2010-5-18 上午10:27:05
	 */
	public List getWorkfileListInLastDays(Date beginDate, Date endDate);
	
}
