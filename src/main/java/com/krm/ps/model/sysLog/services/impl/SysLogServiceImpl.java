package com.krm.ps.model.sysLog.services.impl;

import java.util.List;

import com.krm.ps.model.sysLog.dao.SysLogDao;
import com.krm.ps.model.sysLog.services.SysLogService;


public class SysLogServiceImpl implements SysLogService{

	private SysLogDao syslogdao;
	
	public SysLogDao getSyslogdao() {
		return syslogdao;
	}
	
	public void setSyslogdao(SysLogDao syslogdao) {
		this.syslogdao = syslogdao;
	}
	
	public List queryLogList(String userId, String userName, String recordDate,
			String reportId,String logType) {
		List list = syslogdao.queryLogList(userId,userName,recordDate,reportId,logType);
		return list;
	}
	
	public List queryLogList(String userId, String userName, String startDate,String endDate,
			String reportId,String logType,String  organId,int idxid) {
		List list = syslogdao.queryLogList(userId,userName,startDate,endDate,reportId,logType,organId,idxid);
		return list;
	}
	
	public void insertLog(String userId, String userName,
			String reportId, String logType, String organId, String memo,
			String reportType){
		syslogdao.insertLog(userId, userName, reportId, logType, organId, memo, reportType);
	}
	
	public void insertLog(String userId, String userName,
			String reportId, String logType, String organId, String memo,
			String reportType,String recordDate){
		syslogdao.insertLog(userId, userName, reportId, logType, organId, memo, reportType,recordDate);
	}
}
