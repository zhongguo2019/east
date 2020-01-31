package com.krm.slsint.workfile.services;

import java.util.List;

import com.krm.slsint.fileRepositoryManage.vo.FileRepositoryRecord;
import com.krm.slsint.workfile.vo.FileTransferData;

public interface WorkMailService {
	/**
	 * 得到所有邮件
	 * @return
	 */
	public List getInboxList(Long userId,String status);
	
	/**
	 * 单封邮件删除操作,更改邮件标识为9
	 * @param mailId
	 */
	public void updateMailStatus(Long pkId,String status,Long userId);
	/**
	 * 删除邮件
	 * @param pkId
	 */
	public void delMail(Long pkId,String status,Long userId);
	/**
	 * 发送邮件
	 * @param acceptoperData
	 * @param fileTransferData
	 * @param oleFileData
	 */
	public void save(String name,String addresseeId,Long addresserId,String title,String content,
			         String sFileName,String dFileName,int fileSize, byte [] b);
	/**
	 * 发送邮件1
	 * @param acceptoperData
	 * @param fileTransferData
	 * @param oleFileData
	 */
	public void save(String name,String addresseeId,Long addresserId,String title,String content,
					 String sFileName,String dFileName,int reply,String mailid,String itemType,Integer replyDate,int fileSize, byte [] b);
	/**
	 * 查询发件箱邮件
	 * @param pOper
	 * @return
	 */
	public List getOutboxList(Long pOper);
	/**
	 * 删除发件箱邮件
	 * @param pkid
	 */
	public void delOutMail(Long pkid);
	/**
	 * 查看发件箱邮件内容
	 * @param pkId
	 * @return
	 */
	public List viewMails(Long pkId);
	/**
	 * 查看邮件箱邮件内容
	 * @param pkId
	 * @return
	 */
	public List updateInBoxMail(Long pkId);
	/**
	 * 查询用户名（拼接）
	 * @param pkId
	 * @return
	 */
	public String getUserName(Long pkId);
	/**
	 * 根据f.pkid查看附件表mail_id,是否有附件
	 * @param fPkId
	 * @return
	 */
	public List getFiles(Long fPkId);
	/**
	 * 根据部门编号得到用户
	 * @param organCode
	 * @return
	 */
	public String getUser(String areaCode);
	/**
	 * 查询所有新邮件
	 * @return
	 */
	public List getNewWorkMail(Long userId);
	/**
	 * 得到邮件附件
	 * @param pkid
	 * @return
	 */
	public FileRepositoryRecord getMailOleFile(Long pkid);
	/**
	 * 得到所有邮件
	 * @return
	 */
	public List getInboxLista(Long userId,String bdate,String date,String zu,String status);
	/**
	 * 根据条件修改邮件状态为9
	 * @param pkIds
	 * @param status
	 * @param userId
	 */
	public void selectUpdataMail(String pkIds,String status,Long userId);
	/**
	 * 查询发件箱邮件
	 * @param pOper
	 * @return
	 */
	public List getOutboxLista(Long pOper,String bdate,String date,String zu);
	/**
	 * 发送邮件
	 * @param acceptoperData
	 * @param fileTransferData
	 * @param oleFileData
	 */
	public void save1(String name,String addresseeId,FileTransferData fileTransfer,int isReturn,
			String sFileName,String dFileName,String mailid,String rmailid,int reply,Integer replyDate,int fileSize, byte [] b);
	/**
	 * 查询草稿箱
	 * @param pOper
	 * @param date
	 * @return
	 */
	public List getOutboxLista(Long pOper,String date,String status);
	/**
	 * 获取用户的IDName
	 * @param pkId
	 * @return
	 */
	public List getUserNameID(Long pkId);
	/**
	 * 删除Acceptoper表
	 * @param pkId
	 * @param status
	 * @param userId
	 */
	public void delAcceptoper(Long pkId,String status,Long userId);
	
	public List getRecyclebin(Long pOper,String status);
	/**
	 * 将邮件的状态改成已回复
	 * @param pkId
	 * @param userId
	 */
	public void returnUpdateMail(Long pkId,Long userId);
	/**
	 * 删除附件
	 * @param pkId
	 */
	public void delFile(Long pkId);
	/**
	 * 还原发件箱
	 * @param status
	 * @param pkid
	 */
	public void restoreOutBoxs(String status,Long pkid) ;
	/**
	 * 修改邮件类别
	 * @param itemtype
	 * @param pkid
	 */
	public void updateItemtype(String itemtype,Long pkid) ;
	/**
	 * 还原收件箱
	 * @param status
	 * @param pkid
	 */
	public void restoreinBoxs(String status,Long pkid) ;
	/*
	 * 查看收件邮件内容
	 */
	public List updateInBoxMails(Long pkId);
	/**
	 * 发送申请报表解锁
	 * @param acceptoperData
	 * @param fileTransferData
	 * @param oleFileData
	 */
	public void save(String addresseeId,FileTransferData fileTransfer);
	/**
	 * 根据邮件类型查询邮件
	 * @param userId
	 * @param status
	 * @param itemType
	 * @return
	 */
	public List getInbox(Long userId,String status,String itemType);
	
	/**
	 * <p>查询需要其它人给指定用户回复的邮件列表</p> 
	 *
	 * @param userId 指定人
	 * @return
	 * @author 皮亮
	 * @version 创建时间：2010-5-13 下午06:19:18
	 */
	public List getMailListToBeAnsweredToUser(Long userId);
	
	/**
	 * <p>查询需要指定人员回复他人的邮件列表</p> 
	 *
	 * @param userId
	 * @return
	 * @author 皮亮
	 * @version 创建时间：2010-5-13 下午06:20:25
	 */
	public List getMailListToBeAnsweredByUser(Long userId);

	/**
	 * <p>查询指定用户未读邮件列表</p> 
	 *
	 * @param userId
	 * @return
	 * @author 皮亮
	 * @version 创建时间：2010-5-18 上午09:29:19
	 */
	public List getMailListToRead(Long userId);
	/**
	 * 方法用来查询相同报表的申请
	 * @param userId
	 * @param f_oper
	 * @param reportId
	 * @return
	 * add by ydw 20120416
	 */
	public List getSameContentMail(Long userId,Long f_oper,Long reportId);
}
