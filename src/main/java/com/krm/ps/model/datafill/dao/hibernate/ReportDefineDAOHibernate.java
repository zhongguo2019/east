package com.krm.ps.model.datafill.dao.hibernate;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.derby.tools.sysinfo;
import org.hibernate.Hibernate;
import org.hibernate.Session;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.model.datafill.dao.ReportDefineDAO;
import com.krm.ps.model.vo.ReportResult;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportConfig;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;
import com.krm.ps.util.Constants;
import com.krm.ps.util.FuncConfig;
import com.krm.ps.util.SysConfig;


public class ReportDefineDAOHibernate extends BaseDAOHibernate implements
		ReportDefineDAO {
	
	
	public List getReports(String date, Long userid) {
		return getReports(null, date, userid,null);
	}
	
	
	public List getReports(Report report, String date, Long userid,String showlevel) {

		Map map = new HashMap();
		String hql = "";
		
		if (date != null) {
			hql = "select new Report(r,t.name) from Report r,ReportType t where t.status<>'"
					+ Constants.STATUS_DEL
					+ "' and r.pkid in(select ur.reportId from UserReport ur where ur.operId =:userid) and r.status <>"
					+ Constants.STATUS_DEL;
			map.put("userid", userid);

			if (date.matches("^\\d{8}$")) {
				hql += " and r.beginDate<=:date and r.endDate>=:date";
				map.put("date", date);
			}
			hql += " and r.reportType = t.pkid";
		}
		else if (userid == null) {
			hql = "select new Report(r,t.name) from Report r,ReportType t where t.status<>'"
					+ Constants.STATUS_DEL
					+ "' and r.status <>"
					+ Constants.STATUS_DEL
					+ " and r.reportType = t.pkid and r.pkid <>0";
		}
		else {
			hql = "select new Report(r,t.name) from Report r,ReportType t where t.status<>'"
					+ Constants.STATUS_DEL
					+ "' and r.status <>"
					+ Constants.STATUS_DEL + " and r.reportType = t.pkid ";
		}
		if(showlevel!=null&&!"".equals(showlevel)){
			hql+=" and t.showlevel="+showlevel;
		}
		String and = " and ";

		if (null == report) {
			return list(hql + " order by r.reportType,r.showOrder", map);
		}

		StringBuffer conditions = new StringBuffer();

		if (!isNull(report.getIsSum())) {
			conditions.append("r.isSum = :isSum");
			map.put("isSum", report.getIsSum());
			conditions.append(and);
		}

		if (!isNull(report.getReportType())) {
			conditions.append("r.reportType = :reportType");
			map.put("reportType", report.getReportType());
			conditions.append(and);
		}
		// 2006.9.22
		if (!isNull(report.getFrequency())) {
			conditions.append("r.frequency = :frequency");
			map.put("frequency", report.getFrequency());
			conditions.append(and);
		}

		if (!isNull(report.getRol())) {
			conditions.append("r.rol = :rol");
			map.put("rol", report.getRol());
			conditions.append(and);
		}

		if (!isNull(report.getMoneyunit())) {
			conditions.append("r.moneyunit = :moneyunit");
			map.put("moneyunit", report.getMoneyunit());
			conditions.append(and);
		}

		if (!isNull(report.getBeginDate())) {
			conditions.append("r.beginDate >= :beginDate");
			map.put("beginDate", report.getBeginDate());
			conditions.append(and);
		}

		if (!isNull(report.getEndDate())) {
			conditions.append("r.endDate <= :endDate");
			map.put("endDate", report.getEndDate());
			conditions.append(and);
		}

		if (!isNull(report.getName())) {
			conditions.append("r.name like :name");
			map.put("name", "%" + report.getName() + "%");
			conditions.append(and);
		}

		if (!isNull(report.getConCode())) {
			conditions.append("r.conCode = :conCode");
			map.put("conCode", report.getConCode());
			conditions.append(and);
		}

		if (!isNull(report.getCode())) {
			conditions.append("r.code = :code");
			map.put("code", report.getCode());
			conditions.append(and);
		}

		String condition = conditions.toString();
		if (condition.length() > 0) {
			hql = hql + and + condition.substring(0, condition.length() - 5);
		}
		hql = hql + " order by r.reportType,r.showOrder";

		return list(hql, map);
	}
	private boolean isNull(Object o) {
		if (null != o && o.toString().trim().length() > 0) {
			return false;
		}
		return true;
	}
	
	@Override
	public List<ReportTarget> getReportTargetsBySRC(String tableName) {
		List<ReportTarget> result = new ArrayList<ReportTarget>();
		Connection conn = null;
		ResultSet rs = null;
		String driverclass = FuncConfig.getProperty("jdbc.connection.driver_class");
		String url = FuncConfig.getProperty("jdbc.connection.url");
		String username = FuncConfig.getProperty("jdbc.connection.username");
		String password = FuncConfig.getProperty("jdbc.connection.password");
		String schema = FuncConfig.getProperty("jdbc.connection.schema");
		String DBType = FuncConfig.getProperty("jdbc.database.type");
		try { 
			Class.forName(driverclass);
			Properties props =new Properties();
			if(DBType.toUpperCase().equals("ORACLE")){
				props.put("remarksReporting","true");
			}
			props.put("user", username);
			props.put("password", password);
			conn = DriverManager.getConnection(url, props);
			DatabaseMetaData dbmd=conn.getMetaData(); 
			rs = dbmd.getColumns(null, schema, tableName, "%"); 
			while (rs.next())
			{
				ReportTarget rt = new ReportTarget();
				String columnName = rs.getString("COLUMN_NAME");
				String columnCNName = rs.getString("REMARKS");
				rt.setTargetField(columnName);
				if(columnCNName != null && !columnCNName.equals("")){
					rt.setTargetName(columnCNName);
				} else {
					rt.setTargetName(columnName);
				}
				rt.setRulesize(rs.getString("COLUMN_SIZE"));
				String temp = rs.getString("TYPE_NAME");
				if(temp.equals("VARCHAR") || temp.equals("VARCHAR2")){
					rt.setDataType(3);
				} else {
					rt.setDataType(1);
				}
				rt.setStatus(0);
				result.add(rt);
			}
		} catch (Exception e) {
			logger.error(e);
		} finally {
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) {logger.error(e); }
			}
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {logger.error(e);	}
			}
		}
		return result;
	}


	public List<ReportTarget> getReportTargets(Long reportId) {
		String hql = "from ReportTarget t where t.status <> '"
				+ Constants.STATUS_DEL + "' and t.reportId = " + reportId
				+ " order by t.targetOrder";
		Map map = new HashMap();
		return list(hql, map);
		}
	
	public List getReportOrgTypes(Long reportId) {
		String hql = "from ReportOrgType t where t.reportId = " + reportId;
		return list(hql);
	}
	
	@Override
	public List getTemplates(Long reportid) {
		// TODO Auto-generated method stub
		String hql = "from TemplateModel rt where rt.templateId=" + reportid;
		List ls = getHibernateTemplate().find(hql);
		return ls;
	}
	
	@Override
	public void reportSeleMove(String status1,String reportid){
		// TODO Auto-generated method stub		
		String sqlcon = "select {r.*} from code_rep_config r where r.fun_id = 34";  //查询结果表的表名
		if(reportid !=null){
			 sqlcon += " and r.REPORT_ID ='"+reportid+"'";  //查询结果表的表名
		}
		List conlist = list(sqlcon, new Object[][] { { "r", ReportConfig.class } }, null);
	//	logger.info("要更新的结果表数："+conlist.size());
		String[] moda = getResultdate();
		ReportConfig reportConfig;
		String resulttablename = "";
		String sqlresult = "";
		List listtest;
		for(int i=0;i<conlist.size();i++){
			sqlresult = "select {r.*} from ";
			reportConfig = (ReportConfig) conlist.get(i);
			resulttablename = reportConfig.getDefChar();  //结果表的表名
			resulttablename = resulttablename.replaceAll("\\{M\\}", moda[0]);
			resulttablename = resulttablename.replaceAll("\\{D\\}", moda[1]);
			resulttablename = resulttablename.replaceAll("\\{Y\\}", moda[2]);
			String db = SysConfig.DATABASE;
			if(reportid !=null){
				if("oracle".equalsIgnoreCase(db)) {
					sqlresult += resulttablename + " r where r.cstatus = '" + status1 +"' and rownum <= 5000"; //每个表取出前1000条
				}else if("db2".equalsIgnoreCase(db)){
					sqlresult += resulttablename + " r where r.cstatus = '" + status1 +"' fetch first 5000 rows only "; //每个表取出前1000条
				}
			}else{
				if("oracle".equalsIgnoreCase(db)) {
					sqlresult += resulttablename + " r where r.cstatus = '" + status1 +"' and rownum <= 1000"; //每个表取出前1000条
				}else if("db2".equalsIgnoreCase(db)){
					sqlresult += resulttablename + " r where r.cstatus = '" + status1 +"' fetch first 1000 rows only"; //每个表取出前1000条
				}
			}
			listtest = list(sqlresult, new Object[][] { { "r", ReportResult.class } }, null);
	//		logger.info("迁移数据的大小为:" + listtest.size()+"--"+resulttablename);
			if(listtest != null ){
				String idstr="";
				String updatesql="";
				try{
				for(int x=0;x<listtest.size();x++){
					ReportResult reportResult = (ReportResult)listtest.get(x);
					idstr+=reportResult.getPkid()+",";
				}
				if(!"".equals(idstr)){

					//7鐘舵�佷负姝ｅ湪澶勭悊			

					updatesql = "update " + resulttablename+" c set c.CSTATUS ='7'"+" where c.pkid in ("+idstr.substring(0,idstr.length()-1)+")";
					this.jdbcUpdate(updatesql, null);
	//				logger.error("【"+resulttablename+"】修改状态为正处理成功！："+idstr);

				}
				Map targetMap=new HashMap();
				Map targetidMap=new HashMap();
				String defchar = getstyle(reportConfig.getReportId());
				String dataftablename = defchar.replaceAll("\\{M\\}", moda[0]);
				dataftablename = dataftablename.replaceAll("\\{D\\}", moda[1]);
				dataftablename = dataftablename.replaceAll("\\{Y\\}", moda[2]);
				for(int j=0;j<listtest.size();j++){
					ReportResult reportResult = (ReportResult)listtest.get(j);
					Long keyvalues=reportResult.getKeyvalue();
					String item = reportResult.getTargetid();
					if(targetidMap.containsKey(keyvalues)){
						targetidMap.put(keyvalues, targetidMap.get(keyvalues)+"'"+reportResult.getTargetid()+"',");
					}else{
						targetidMap.put(keyvalues, "'"+reportResult.getTargetid()+"',");
					}
					if(targetMap.containsKey(keyvalues)){
						String sql=targetMap.get(keyvalues).toString();
						sql+=item +"=";
//						if(item > 20 && item < 51){
//							if("".equals(reportResult.getNewvalue())){
//								sql+=0;

//							    sql += Double.parseDouble(reportResult.getNewvalue());

//							}else if(null==reportResult.getNewvalue()){
//								sql+=null;
//							}
//						}else{
//							sql += "'" + reportResult.getNewvalue() + "'";
//						}
						sql += reportResult.getNewvalue()==null?null:"'" + reportResult.getNewvalue() + "'";
						sql+=",";
						targetMap.put(keyvalues, sql);
					}else{
						String sql=item +"=";
//						if(item > 20 && item < 51){
//							if("".equals(reportResult.getNewvalue())){
//								sql+=0;
//							}else if(null!=reportResult.getNewvalue()){

//							    sql += Double.parseDouble(reportResult.getNewvalue());

//							}else if(null==reportResult.getNewvalue()){
//								sql+=null;
//							}
//						}else{
//							sql += "'" + reportResult.getNewvalue() + "'";
//						}
						sql +=  reportResult.getNewvalue()==null?null:"'" + reportResult.getNewvalue() + "'";
						sql+=",";
						targetMap.put(keyvalues, sql);
					}
					
				}

				reportMove(resulttablename,targetMap,targetidMap,status1,dataftablename);//閻犲鍟伴弫顦date闁哄倽顬冪涵锟介柟杈炬嫹 insert闁哄倽顬冪涵锟� 閻忓繐妫涚划銊╁几濠婂懏鐣遍柡浣哄瀹撲焦娼绘担鐩掆晠宕氶惂瀵告偘閿燂拷				//reportUpdaStatus(tablename,status1,status2,listtest);//濞ｅ浂鍠楅弫鑲╃磼閹惧浜悶娑栧妽閺嗙喖骞戦鎯﹂柟顒婃嫹

				//if("1".equals(status1)){
					//闁哄稄绻濋悰娆戞偘閵夈儳绉块弶鈺佹川濞堟垿寮悧鍫濈ウ  妤犵偠娉涢惃銏ゆ偐閼哥鎷烽柡锟斤拷鐠愶拷
				//	reportVerify(tablename,listtest);
				//}
			}catch (RuntimeException re) {
				//7为正处理状态，如果修改状态失败，再把状态改为2
				updatesql = "update " + resulttablename+" c set c.CSTATUS ='2'"+" where c.pkid in ("+idstr.substring(0,idstr.length()-1)+")";
				this.jdbcUpdate(updatesql, null);
				logger.error("【"+resulttablename+"】修改状态为正处理出错："+idstr+"--错误原因:"+re.toString()); //e.printStackTrace();
		        }finally{
	        	continue;
	        }
			}
				
		}
	}
	// 数据迁移
	private void reportMove(String tablename,Map targetMap,Map targetidMap,String status1,String dataftablename){
		// TODO Auto-generated method stub
		Set set=targetMap.keySet();
		Iterator targetIt=set.iterator();
		while(targetIt.hasNext()){
			//batchJdbcUpdate
			String sqls="";
			Long keyvalue=Long.parseLong(String.valueOf(targetIt.next()));
			String targetids=String.valueOf(targetidMap.get(keyvalue));
			if(keyvalue != null){
				String sqltar=targetMap.get(keyvalue).toString();
				sqls="update "+dataftablename+" r set "+sqltar.substring(0,sqltar.length()-1);
				sqls+="  where r.pkid = " + keyvalue;
			}
			try{
					int iflag=this.jdbcUpdate(sqls, null);					
					if(iflag>0){
						//当数据在数据表时更新成功后，再修改结果表的状态
						String sql = "update " + tablename+" c set c.CSTATUS ='4'"+" where c.CSTATUS ='7'  and  c.keyvalue =" + keyvalue+" and c.target_id in ("+targetids.substring(0,targetids.length()-1)+")";
						this.jdbcUpdate(sql, null);
				//		logger.info("数据表：【"+dataftablename+"】同步【"+keyvalue+"】记录成功！");

							/*String qusql="select count(pkid) as total from "+tablename+" c where c.CSTATUS !='4' and c.keyvalue =" + keyvalue;
							Object[][] scalaries = new Object[][] { {"total", Hibernate.INTEGER}};
							List numlist=this.list(qusql, null, scalaries);
							int num =  Integer.parseInt(numlist.get(0).toString());
							if(num==0){
								String upsqls="update "+dataftablename+" r set r.itemvalue80='1'  where r.pkid = " + keyvalue;
								this.jdbcUpdate(upsqls, null);
							}*/
						
					}
				} catch (RuntimeException re) {
					//6为提交失败的数据，目前为测试阶段，先改状态
					String sql = "update " + tablename+" c set c.CSTATUS ='6'"+" where c.CSTATUS = '7'  and  c.keyvalue =" + keyvalue+" and c.target_id in ("+targetids.substring(0,targetids.length()-1)+")";
					this.jdbcUpdate(sql, null);
					logger.error("数据表：【"+dataftablename+"】同步【"+keyvalue+"】记录时出错，请查看:"+re.toString());
		        }finally{
		        	continue;
		        }
			}
		}
	
	//补录数据在F表存在对原数据进行update操作
	private String[] getResultdate(){
		String sql="select currentdate as currentDate from SYSPARAM";
		Object[][] scalaries = new Object[][] { 
				{ "currentDate", Hibernate.STRING }
				};
		List datelist=list(sql, null, scalaries);
		String dater =  datelist.get(0).toString();
		String year=dater.substring(0,4);
		String month=dater.substring(4,6);
		String day=dater.substring(6,8);
		String[] moda = {month,day,year};
		return moda;
	}
	
	private String getstyle(Long modelid){
		String sql = "select c.DEF_CHAR from CODE_REP_CONFIG c where c.fun_id=33 and c.REPORT_ID=" + modelid ;
		Session session = this.getSession();
		List list = session.createSQLQuery(sql).addScalar("DEF_CHAR",Hibernate.STRING).list();
		session.close();
		return (String)list.get(0);
	}
}
