package com.krm.ps.model.sysLog.dao;

import java.util.List;


public interface SysLogDao {

	/**
	 * 查询日志列表
	 * @param userId 用户号
	 * @param userName 用户名
	 * @param recordDate 日志创建时间
	 * @param reportId 报表id
	 * @param logType 日志类型
	 * @return
	 */
	List queryLogList(String userId, String userName, String recordDate,
			String reportId,String logType);
	/**
	 * 查询日志列表
	 * @param userId 用户号
	 * @param userName 用户名
	 * @param recordDate 日志创建时间
	 * @param reportId 报表id
	 * @param logType 日志类型
	 * @return
	 */
	List  queryLogList(String userId, String userName, String startDate,String endDate,
			String reportId,String logType,String organId,int idxid);
	
	/**
	 * 插入日志
	 * @param userId 用户号
	 * @param userName 用户名
	 * @param reportId 报表id
	 * @param logType 日志类型
	 * @param organId 机构id
	 * @param memo 描述
	 * @param reportType 报表类型
	 */
	void insertLog(String userId, String userName, 
			String reportId, String logType, String organId, String memo,
			String reportType);
	/**
	 * 插入日志
	 * @param userId 用户号
	 * @param userName 用户名
	 * @param reportId 报表id
	 * @param logType 日志类型
	 * @param organId 机构id
	 * @param memo 描述
	 * @param reportType 报表类型
	 * @param recordDate 选中时间
	 */
	public void insertLog(String userId, String userName,
			String reportId, String logType, String organId, String memo,
			String reportType ,String recordDate);
}
