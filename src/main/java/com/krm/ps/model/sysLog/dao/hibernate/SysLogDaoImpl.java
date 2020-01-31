package com.krm.ps.model.sysLog.dao.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.model.sysLog.dao.SysLogDao;
import com.krm.ps.model.sysLog.vo.LogData;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportType;
import com.krm.ps.util.DBDialect;
import com.krm.ps.util.DateUtil;


public class SysLogDaoImpl extends BaseDAOHibernate implements SysLogDao{
	
	@Override
	public List queryLogList(String userId, String userName, String recordDate,
			String reportId,String logType) {
		String sql = "select t.NM_ORGAN as organName,u.LOGON_NAME as logonName,t.NM_OPER as userName,to_date(t.DATE_REC,'yyyy-mm-dd hh24:mi:ss') as dataDate,t.NM_REP as reportName,t.ASS_MEMO as memo from log_data t"
					 +" left join UMG_USER u on u.PKID=t.ID_OPER WHERE u.LOGON_NAME = '" + userId + "' ";
		if (null != reportId && !"".equals(reportId)) {
			sql += "AND t.id_rep IN(" + reportId + ") ";
		}
		if (null != userName && !"".equals(userName)) {
			sql += "AND t.nm_oper ='" + reportId + "' ";
		}
		if(!StringUtils.isEmpty(recordDate)){
			recordDate = recordDate.replaceAll("-", "");
		}
		sql += "and t.STATUS='1' AND t.mk_type = '" + logType + "' AND t.date_data = '" + recordDate + "'"
				+ " ORDER BY t.DATE_REC desc";
		String replaceTableName = "log_data_" + recordDate.substring(4, 6);
		sql = sql.replaceAll("log_data",replaceTableName);
		final String tsql = sql;
		
		List ListAll = (List)this.getHibernateTemplate().execute(  
	                new HibernateCallback(){  
	                    public Object doInHibernate(Session session)  
	                    throws HibernateException, SQLException {  
	                    Connection con = session.connection();  
	                    PreparedStatement ps = con.prepareStatement(tsql);  
	                    ResultSet rs = ps.executeQuery();  
	                    List<LogData> all = new ArrayList<LogData>();  
	                    while(rs.next()){  
	                        LogData kqb = new LogData();  
	                        kqb.setOrganName(rs.getString("organName"));  
	                        kqb.setLogonName(rs.getString("logonName"));  
	                        kqb.setUserName(rs.getString("userName"));  
	                        kqb.setDataDate(rs.getString("dataDate"));  
	                        kqb.setReportName(rs.getString("reportName"));  
	                        kqb.setMemo(rs.getString("memo"));  
	                        all.add(kqb);  
	                    }  
	                    rs.close();  
	                    ps.close();  
	                    session.flush();  
	                    session.close();  
	                    return all;  
	                    }  
	                }  
	        );  
		return ListAll;
	}

	
	public void insertLog(String userId, String userName,
			String reportId, String logType, String organId, String memo,
			String reportType) {
		final String strNow = DateUtil
				.getDateTime("yyyyMMddHHmmss", new Date());
		String reportName = "";
		String reportTypeName = "";
		if (!reportType.equals(-1)) {
			reportTypeName = getReportTypeName(Long.valueOf(reportType));
			if (!reportId.equals("-1")) {
				reportName = getReportName(Long.valueOf(reportId));
			}
		}
		if(reportTypeName.equals("")){
			reportTypeName = reportType;
		}
		String recordDate = DateUtil.getDateTime("yyyyMMdd", new Date());
		String insertTable = "log_data_" + recordDate.substring(4, 6);
		String tsql = "INSERT INTO "
				+ insertTable
				+ " (pkid,cd_organ,nm_organ,date_data,id_oper,"
				+ "nm_oper,date_rec,mk_type,id_rep_type,nm_rep_type,id_rep,nm_rep,ass_memo,STATUS) ";
		if (StringUtils.isNotBlank(organId)) {
			tsql += "select " + DBDialect.genSequence("log_data_pkid_seq")
					+ ",o.CODE,o.FULL_NAME,'" + recordDate + "'," + userId
					+ ",'" + userName + "','" + strNow + "' ,'" + logType
					+ "'," + reportType + ",'" + reportTypeName + "',"
					+ Long.valueOf(reportId) + ",'" + reportName + "','" + memo
					+ "', '1' from CODE_ORG_ORGAN o where CODE= '" + organId
					+ "'";
		} else {
			tsql += "values(" + DBDialect.genSequence("log_data_pkid_seq")
					+ ",' ','','" + recordDate + "'," + userId + ",'" + userName
					+ "','" + strNow + "' ,'" + logType + "'," + reportType
					+ ",'" + reportTypeName + "'," + Long.valueOf(reportId)
					+ ",'" + reportName + "','" + memo + "', '1')";
		}
		final String sql = tsql;
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				PreparedStatement ps = session.connection().prepareStatement(
						sql);
				ps.executeUpdate();
				return null;
			}
		};
		getHibernateTemplate().execute(callback);
	}
	
	private String getReportTypeName(long reportType2) {
		ReportType reportType = (ReportType) getObject(ReportType.class,
				reportType2);
		if (reportType != null)
			return reportType.getName();
		else
			return "";
	}
	
	private String getReportName(long reportId) {
		Report report = (Report) getObject(Report.class, reportId);
		if (report != null)
			return report.getName();
		else
			return "";
	}
	public void insertLog(String userId, String userName,
			String reportId, String logType, String organId, String memo,
			String reportType ,String recordDate) {
		final String strNow = DateUtil
				.getDateTime("yyyyMMddHHmmss", new Date());
		String reportName = "";
		String reportTypeName = "";
		if (!reportType.equals(-1)) {
			reportTypeName = getReportTypeName(Long.valueOf(reportType));
			if (!reportId.equals("-1")) {
				reportName = getReportName(Long.valueOf(reportId));
			}
		}
		if(reportTypeName.equals("")){
			reportTypeName = reportType;
		}
		String insertTable = "log_data_" + recordDate.substring(4, 6);
		String tsql = "INSERT INTO "
				+ insertTable
				+ " (pkid,cd_organ,nm_organ,date_data,id_oper,"
				+ "nm_oper,date_rec,mk_type,id_rep_type,nm_rep_type,id_rep,nm_rep,ass_memo,STATUS) ";
		if (StringUtils.isNotBlank(organId)) {
			tsql += "select " + DBDialect.genSequence("log_data_pkid_seq")
					+ ",o.CODE,o.FULL_NAME,'" + recordDate + "'," + userId
					+ ",'" + userName + "','" + strNow + "' ,'" + logType
					+ "'," + reportType + ",'" + reportTypeName + "',"
					+ Long.valueOf(reportId) + ",'" + reportName + "','" + memo
					+ "', '1' from CODE_ORG_ORGAN o where CODE= '" + organId
					+ "'";
		} else {
			tsql += "values(" + DBDialect.genSequence("log_data_pkid_seq")
					+ ",' ','','" + recordDate + "'," + userId + ",'" + userName
					+ "','" + strNow + "' ,'" + logType + "'," + reportType
					+ ",'" + reportTypeName + "'," + Long.valueOf(reportId)
					+ ",'" + reportName + "','" + memo + "', '1')";
		}
		final String sql = tsql;
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				PreparedStatement ps = session.connection().prepareStatement(
						sql);
				ps.executeUpdate();
				return null;
			}
		};
		getHibernateTemplate().execute(callback);
	}


	@Override
	public List<LogData> queryLogList(String userId, String userName, String startDate,String endDate,
			String reportId,String logType,String organId,int idxid) {
		String sql = "select o.SHORT_NAME as organName,u.LOGON_NAME as logonName,t.NM_OPER as userName,to_date(t.DATE_REC,'yyyy-mm-dd hh24:mi:ss') as dataDate,t.NM_REP as reportName,t.ASS_MEMO as memo from log_data t"
					 +" left join UMG_USER u on u.PKID=t.ID_OPER left join CODE_ORG_ORGAN o on t.CD_ORGAN=o.CODE WHERE  1=1  ";
		if(null!=userId && !"".equals(userId) ){
			sql += " AND u.LOGON_NAME = '" + userId + "'   ";
		}
		if(null!=organId && !"".equals(organId)){
			//获得机构及下属机构
			List labL = getOrganidx(organId, idxid);
			String lab = "";
			if (labL.size() > 0) {
				lab = (String) labL.get(0);
			}
			String organTree = "select tr.CODE from code_org_organ tr,CODE_ORG_TREE tt where tr.PKID=tt.NODEID  and tt.SUBTREETAG like '"
					+ lab + "%' ";
			sql += "AND t.CD_ORGAN IN(" + organTree + ") ";
		}
		if (null != reportId && !"".equals(reportId)) {
			sql += "AND t.id_rep IN(" + reportId + ") ";
		}
		if (null != userName && !"".equals(userName)) {
			sql += "AND t.nm_oper ='" + reportId + "' ";
		}
		if(!StringUtils.isEmpty(startDate)){
			startDate = startDate.replaceAll("-", "");
		}
		if(!StringUtils.isEmpty(endDate)){
			endDate = endDate.replaceAll("-", "");
		}
		sql += "and t.STATUS='1' AND t.mk_type = '" + logType 
			+ "' AND (t.date_data between '" + startDate + "' and '" + endDate + "')"
			+ " ORDER BY t.DATE_REC desc";
		int startMonth = Integer.parseInt(startDate.substring(4, 6));//截取开始月份
		int endMonth = Integer.parseInt(endDate.substring(4, 6));//截取开始月份
		List<LogData> logAllLists = new ArrayList<LogData>();  
		for(int i=startMonth;i<=endMonth;i++){
			
			String replaceTableName = "log_data_"+(i<10?"0"+i:i+"");//截取月份
			final String tsql = sql.replaceAll("log_data",replaceTableName);//替换表名
			logger.debug(">>"+tsql);
			@SuppressWarnings("unchecked")
			List<LogData> list = queryLogList(tsql);
			
			Iterator<LogData> it = list.iterator();
			while (it.hasNext()) {
				LogData log = (LogData)it.next();
				logAllLists.add(log);
			}
		}
		return logAllLists;
	}
	public List queryLogList(final String tsql) {
		List list = (List) this.getHibernateTemplate().execute(
				new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						Connection con = session.connection();
						PreparedStatement ps = con.prepareStatement(tsql);
						ResultSet rs = ps.executeQuery();
						List<LogData> all = new ArrayList<LogData>();
						while (rs.next()) {
							LogData kqb = new LogData();
							kqb.setOrganName(rs.getString("organName"));
							kqb.setLogonName(rs.getString("logonName"));
							kqb.setUserName(rs.getString("userName"));
							kqb.setDataDate(rs.getString("dataDate"));
							kqb.setReportName(rs.getString("reportName"));
							kqb.setMemo(rs.getString("memo"));
							all.add(kqb);
						}
						rs.close();
						ps.close();
						session.flush();
						session.close();
						return all;
					}
				});
		return list;
	}
	//获得机构树的IDx标识
		public List getOrganidx(String organcode,int idx){
			String sql="select tt.SUBTREETAG ttlab from code_org_organ t,CODE_ORG_TREE tt where t.PKID=tt.NODEID and t.CODE='"+organcode+"' and  tt.IDXID="+idx+"";
			List resultL= list(sql,null,new Object[][]{{"ttlab",Hibernate.STRING}});
			return resultL;
		}
}
