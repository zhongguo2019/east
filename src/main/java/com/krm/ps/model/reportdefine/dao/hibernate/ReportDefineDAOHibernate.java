package com.krm.ps.model.reportdefine.dao.hibernate;

import java.io.Serializable;
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
import java.util.Map.Entry;
import java.util.Properties;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.model.reportdefine.dao.ReportDefineDAO;
import com.krm.ps.model.reportdefine.services.ReportDefineService;
import com.krm.ps.model.vo.DicItem;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;
import com.krm.ps.sysmanage.reportdefine.vo.ReportType;
import com.krm.ps.util.Constants;
import com.krm.ps.util.DBDialect;
import com.krm.ps.util.FuncConfig;

public class ReportDefineDAOHibernate extends BaseDAOHibernate implements
		ReportDefineDAO {

	private boolean isNull(Object o) {
		if (null != o && o.toString().trim().length() > 0) {
			return false;
		}
		return true;
	}

	@Override
	public List getAllReportTypes(String showlevel) {
		String hql = "from ReportType t where  t.status <>'"
				+ Constants.STATUS_DEL + "' ";
		if (showlevel != null && !"".equals(showlevel)) {
			hql += "and t.showlevel=" + new Long(showlevel);
		}
		hql += "order by t.showOrder , t.pkid";
		return list(hql);
	}

	public Object getObject(Class clazz, Serializable id) {
		return getHibernateTemplate().get(clazz, id);
	}

	@Override
	public void removeReportType(Long typeId) {
		Object type = this.getObject(ReportType.class, typeId);
		if (null != type) {
			((ReportType) type).setStatus(Constants.STATUS_DEL);
			this.saveObject(type);
		}
	}

	@Override
	public boolean typeNameRepeat(Long pkid, String name) {
		Long spkid = pkid;
		if (null == spkid) {
			spkid = new Long(-1);
		}
		String hql = "from ReportType t where t.status <> '"
				+ Constants.STATUS_DEL
				+ "' and t.pkid<>:pkid and t.name = :name";
		Map map = new HashMap();
		map.put("pkid", spkid);
		map.put("name", name);
		List ls = list(hql, map);
		if (ls.size() > 0)
			return true;
		return false;
	}

	@Override
	public void saveObject(Object o) {
		getHibernateTemplate().saveOrUpdate(o);
		getHibernateTemplate().flush();
	}

	@Override
	public List getReportTypes(Long userid) {
		String sql = "select {r.*} from code_rep_types r where r.DATA_SOURCE <>'9'  and showlevel='1' and r.status <> '"
				+ Constants.STATUS_DEL
				+ "' and r.pkid in(select ur.typeId from rep_oper_contrast ur where ur.operId ='"
				+ userid + "') order by r.show_Order";
		List ls = list(sql, new Object[][] { { "r", ReportType.class } }, null);
		return ls;
	}

	@Override
	public List getReports(Report report, String date, Long userid,
			String showlevel) {

		/*
		 * Map map = new HashMap(); String hql = "";
		 * 
		 * if (date != null) { hql =
		 * "select new Report(r,t.name) from Report r,ReportType t where t.status<>'"
		 * + Constants.STATUS_DEL +
		 * "' and r.pkid in(select ur.REPORT_ID from UserReport ur where ur.operId =:userid) and r.status <>"
		 * + Constants.STATUS_DEL; map.put("userid", userid);
		 * 
		 * if (date.matches("^\\d{8}$")) { hql +=
		 * " and r.beginDate<=:date and r.endDate>=:date"; map.put("date",
		 * date); } hql += " and r.reportType = t.pkid"; } else if (userid ==
		 * null) { hql =
		 * "select new Report(r,t.name) from Report r,ReportType t where t.status<>'"
		 * + Constants.STATUS_DEL + "' and r.status <>" + Constants.STATUS_DEL +
		 * " and r.reportType = t.pkid and r.pkid <>0"; } else { hql =
		 * "select new Report(r,t.name) from Report r,ReportType t where t.status<>'"
		 * + Constants.STATUS_DEL + "' and r.status <>" + Constants.STATUS_DEL +
		 * " and r.reportType = t.pkid "; } if (showlevel != null &&
		 * !"".equals(showlevel)) { hql += " and t.showlevel=" + showlevel; }
		 * String and = " and ";
		 * 
		 * if (null == report) { return list(hql +
		 * " order by r.reportType,r.showOrder", map); }
		 * 
		 * StringBuffer conditions = new StringBuffer();
		 * 
		 * if (!isNull(report.getIsSum())) {
		 * conditions.append("r.isSum = :isSum"); map.put("isSum",
		 * report.getIsSum()); conditions.append(and); }
		 * 
		 * if (!isNull(report.getReportType())) {
		 * conditions.append("r.reportType = :reportType");
		 * map.put("reportType", report.getReportType());
		 * conditions.append(and); } // 2006.9.22 if
		 * (!isNull(report.getFrequency())) {
		 * conditions.append("r.frequency = :frequency"); map.put("frequency",
		 * report.getFrequency()); conditions.append(and); }
		 * 
		 * if (!isNull(report.getRol())) { conditions.append("r.rol = :rol");
		 * map.put("rol", report.getRol()); conditions.append(and); }
		 * 
		 * if (!isNull(report.getMoneyunit())) {
		 * conditions.append("r.moneyunit = :moneyunit"); map.put("moneyunit",
		 * report.getMoneyunit()); conditions.append(and); }
		 * 
		 * if (!isNull(report.getBeginDate())) {
		 * conditions.append("r.beginDate >= :beginDate"); map.put("beginDate",
		 * report.getBeginDate()); conditions.append(and); }
		 * 
		 * if (!isNull(report.getEndDate())) {
		 * conditions.append("r.endDate <= :endDate"); map.put("endDate",
		 * report.getEndDate()); conditions.append(and); }
		 * 
		 * if (!isNull(report.getName())) {
		 * conditions.append("r.name like :name"); map.put("name", "%" +
		 * report.getName() + "%"); conditions.append(and); }
		 * 
		 * if (!isNull(report.getConCode())) {
		 * conditions.append("r.conCode = :conCode"); map.put("conCode",
		 * report.getConCode()); conditions.append(and); }
		 * 
		 * if (!isNull(report.getCode())) { conditions.append("r.code = :code");
		 * map.put("code", report.getCode()); conditions.append(and); }
		 * 
		 * String condition = conditions.toString(); if (condition.length() > 0)
		 * { hql = hql + and + condition.substring(0, condition.length() - 5); }
		 * hql = hql + " order by r.reportType,r.showOrder";
		 * 
		 * return list(hql, map);
		 */
		String hql = "";

		if (date != null) {
			hql = "select {r.*},{t.*} from code_rep_report r,code_rep_types t where t.status<>'"
					+ Constants.STATUS_DEL
					+ "' and r.pkid in(select ur.repid from rep_oper_contrast ur where ur.operId ='"
					+ userid + "') and r.status <>" + Constants.STATUS_DEL;

			if (date.matches("^\\d{8}$")) {
				hql += " and r.begin_Date<='" + date + "' and r.end_Date>='"
						+ date + "'";
			}
			hql += " and r.report_Type = t.pkid";
		} else if (userid == null) {
			hql = "select {r.*},{t.*} from code_rep_report r,code_rep_types t where t.status<>'"
					+ Constants.STATUS_DEL
					+ "' and r.status <>"
					+ Constants.STATUS_DEL
					+ " and r.report_Type = t.pkid and r.pkid <>0";
		} else {
			hql = "select {r.*},{t.*} from code_rep_report r,code_rep_types t where t.status<>'"
					+ Constants.STATUS_DEL
					+ "' and r.status <>"
					+ Constants.STATUS_DEL + " and r.report_Type = t.pkid ";
		}
		if (showlevel != null && !"".equals(showlevel)) {
			hql += " and t.showlevel=" + showlevel;
		}
		String and = " and ";

		if (null == report) {
			List l = list(hql + " order by r.report_Type,r.show_Order",
					new Object[][] { { "r", Report.class },
							{ "t", ReportType.class } }, null);
		}

		StringBuffer conditions = new StringBuffer();

		if (!isNull(report.getIsSum())) {
			conditions.append("r.is_Sum = '" + report.getIsSum() + "'");
			conditions.append(and);
		}

		if (!isNull(report.getReportType())) {
			conditions.append("r.report_Type = '" + report.getReportType()
					+ "'");
			conditions.append(and);
		}
		// 2006.9.22
		if (!isNull(report.getFrequency())) {
			conditions.append("r.frequency = '" + report.getFrequency() + "'");
			conditions.append(and);
		}

		if (!isNull(report.getRol())) {
			conditions.append("r.rol = '" + report.getRol() + "'");
			conditions.append(and);
		}

		if (!isNull(report.getMoneyunit())) {
			conditions.append("r.moneyunit = '" + report.getMoneyunit() + "'");
			conditions.append(and);
		}

		if (!isNull(report.getBeginDate())) {
			conditions
					.append("r.begin_Date >= '" + report.getBeginDate() + "'");
			conditions.append(and);
		}

		if (!isNull(report.getEndDate())) {
			conditions.append("r.end_Date <= '" + report.getEndDate() + "'");
			conditions.append(and);
		}

		if (!isNull(report.getName())) {
			conditions.append("r.name like '%" + report.getName() + "%'");
			conditions.append(and);
		}

		if (!isNull(report.getConCode())) {
			conditions.append("r.con_Code = '" + report.getConCode() + "'");
			conditions.append(and);
		}

		if (!isNull(report.getCode())) {
			conditions.append("r.code = '" + report.getCode() + "'");
			conditions.append(and);
		}

		String condition = conditions.toString();
		if (condition.length() > 0) {
			hql = hql + and + condition.substring(0, condition.length() - 5);
		}
		hql = hql + " order by r.report_Type,r.show_Order";

		List l = list(hql, new Object[][] { { "r", Report.class },
				{ "t", ReportType.class } }, null);
		List list = new ArrayList();
		Report o1 = null;
		ReportType o2 = null;
		for (int i = 0; i < l.size(); i++) {
			Object[] obj = (Object[]) l.get(i);
			o1 = (Report) obj[0];
			o2 = (ReportType) obj[1];
			o1.setRepname(o2.getName());
			list.add(o1);
		}

		return list;
	}

	@Override
	public List getReportsByTypeFrequencyDate(Long typeId, String frequencyId,
			String beginDateId, String endDateId, String date, Long userid) {
		Report report = new Report();
		if (typeId != null)
			report.setReportType(typeId);
		if (frequencyId != null)
			report.setFrequency(frequencyId);
		if (beginDateId != null)
			report.setBeginDate(beginDateId);
		if (endDateId != null)
			report.setEndDate(endDateId);
		return getReports(report, date, userid, null);
	}

	@Override
	public List getReports(String date, Long userid) {
		return getReports(null, date, userid, null);
	}

	@Override
	public List getReportOrgTypes(Long reportId) {
		String hql = "from ReportOrgType t where t.reportId = " + reportId;
		return list(hql);
	}

	@Override
	public List<String> getDataTable() {
		List<String> result = new ArrayList<String>();
		Session session = null;
		ResultSet rs = null;
		String schema = FuncConfig.getProperty("jdbc.connection.schema");
		String tablename = FuncConfig.getProperty("jdbc.connection.tablename");
		try {
			session = this.getSession();
			DatabaseMetaData md = session.connection().getMetaData();
			rs = md.getTables(null, schema, tablename, new String[] { "TABLE" });
			while (rs.next()) {
				String tableName = rs.getString(3);
				result.add(tableName);
			}
		} catch (Exception e) {
			logger.error("��ѯ����", e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					logger.error("��ѯ����", e);
				}
			}
		}
		return result;
	}

	@Override
	public void updateUserReport(int reportId, int newType) {
		String sql = "update rep_oper_contrast set typeid = " + newType
				+ " where repid = " + reportId;
		jdbcUpdate(sql, null);
	}

	public Integer getShowOrderByType(Long typeId) {
		String sql = "select MAX(r.showOrder) from Report r where r.reportType = "
				+ typeId.longValue();
		List show = list(sql);
		Iterator i = show.iterator();
		Integer temp = (Integer) i.next();
		if (temp != null) {
			return (temp);
		}

		sql = "select MAX(r.showOrder) from Report r";
		show = list(sql);
		i = show.iterator();
		temp = (Integer) i.next();
		if (temp != null) {
			return (temp);
		}
		return (new Integer(0));
	}

	@Override
	public void updateShowOrder(Integer showOrder) {
		String sql = "update code_rep_report set show_order = show_order+1 where show_order >"
				+ showOrder.intValue();
		this.jdbcUpdate(sql, null);
	}

	@Override
	public Integer getReportShowOrder(Long reportId) {
		String hql = "select t.showOrder from Report t where t.pkid ="
				+ reportId;
		List ls = list(hql);
		return (Integer) ls.get(0);
	}

	@Override
	public void delReportOrgTypes(Long reportId) {
		String sql = "delete from code_orgtype_report where reportid =?";
		jdbcUpdate(sql, new Object[] { reportId });
	}

	@Override
	public ReportType getReportTypeByReportId(Long reportId) {
		String sql = "select {t.*} from code_rep_types t where t.pkid =("
				+ " select rt.report_type from code_rep_report rt where pkid="
				+ reportId + ")";
		List result = list(sql, new Object[][] { { "t", ReportType.class } },
				null);
		return (ReportType) result.get(0);
	}

	@Override
	public void removeReport(Long pkid) {
		Object report = this.getObject(Report.class, pkid);
		if (null != report) {
			this.removeObject(report);
		}
	}

	@Override
	public Long deleteReport(Long pkid) {
		Long result = null;
		String sql = "update code_rep_report set status = 9 where pkid = "
				+ pkid;
		try {
			jdbcUpdate(sql, null);
		} catch (Exception e) {
			result = new Long(0);
		}
		return result;
	}

	@Override
	public List<ReportTarget> getReportTargets(Long reportId) {
		String hql = "from ReportTarget t where t.status <> '"
				+ Constants.STATUS_DEL + "' and t.reportId = " + reportId
				+ " order by t.targetOrder";
		Map map = new HashMap();
		return list(hql, map);
	}

	@Override
	public List<ReportTarget> getReportTargetsBySRC(String tableName) {
		List<ReportTarget> result = new ArrayList<ReportTarget>();
		Connection conn = null;
		ResultSet rs = null;
		String driverclass = FuncConfig
				.getProperty("jdbc.connection.driver_class");
		String url = FuncConfig.getProperty("jdbc.connection.url");
		String username = FuncConfig.getProperty("jdbc.connection.username");
		String password = FuncConfig.getProperty("jdbc.connection.password");
		String schema = FuncConfig.getProperty("jdbc.connection.schema");
		String DBType = FuncConfig.getProperty("jdbc.database.type");
		try {
			Class.forName(driverclass);
			Properties props = new Properties();
			if (DBType.toUpperCase().equals("ORACLE")) {
				props.put("remarksReporting", "true");
			}
			props.put("user", username);
			props.put("password", password);
			conn = DriverManager.getConnection(url, props);
			DatabaseMetaData dbmd = conn.getMetaData();
			rs = dbmd.getColumns(null, schema, tableName, "%");
			while (rs.next()) {
				ReportTarget rt = new ReportTarget();
				String columnName = rs.getString("COLUMN_NAME");
				String columnCNName = rs.getString("REMARKS");
				rt.setTargetField(columnName);
				if (columnCNName != null && !columnCNName.equals("")) {
					rt.setTargetName(columnCNName);
				} else {
					rt.setTargetName(columnName);
				}
				rt.setRulesize(rs.getString("COLUMN_SIZE"));
				String temp = rs.getString("TYPE_NAME");
				if (temp.equals("VARCHAR") || temp.equals("VARCHAR2")) {
					rt.setDataType(3);
				} else {
					rt.setDataType(1);
				}
				rt.setStatus(0);
				result.add(rt);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
					logger.error(e);
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
					logger.error(e);
				}
			}
		}
		return result;
	}

	@Override
	public List<DicItem> queryDicAll() {
		String hql = "Select d from DicItem d where d.parentId = '0'";
		return list(hql);
	}

	@Override
	public List getReportsByType(Long typeId, String date, Long userid) {
		Report report = new Report();
		if (typeId != 0)
			report.setReportType(typeId);
		return getReports(report, date, userid, null);
	}

	@Override
	public Integer getMaxOrderNum(Long reportId) {
		Object[][] scalaries = { { "cnt", Hibernate.INTEGER } };
		Integer maxColNum = new Integer(0);
		List result;
		String sqlStr = "SELECT MAX(target_order) as cnt "
				+ "FROM code_rep_target WHERE report_id = ?";
		result = list(sqlStr, null, scalaries, new Object[] { reportId });
		maxColNum = (Integer) result.get(0);
		if (maxColNum == null) {
			maxColNum = 0;
		}
		return maxColNum;
	}

	@Override
	public void saveReportTarget(ReportTarget rt) {
		saveObject(rt);
	}

	@Override
	public int removeReportTarget(Long reportId, Long targetId) {
		Object o = getObject(ReportTarget.class, targetId);
		if (null != o) {
			ReportTarget target = (ReportTarget) o;
			this.removeObject(target);
		}
		return ReportDefineService.DEL_OK;
	}

	@Override
	public List<ReportTarget> getTemplateTargets(Long templateId, Long modelId) {
		String sql = "SELECT {t.*} FROM code_rep_target t WHERE t.target_field IN (SELECT model_target FROM template_model WHERE template_id=? and model_id=?) and report_id=?";
		return list(sql, new Object[][] { { "t", ReportTarget.class } }, null,
				new Object[] { templateId, modelId, modelId });
	}

	@Override
	public void delTemplateTargets(Long templateId, Long modelId,
			String modelTarget) {
		String sql1 = "delete from code_rep_target where target_field in (select template_target  from template_model where template_id="
				+ templateId
				+ " and model_id="
				+ modelId
				+ " and model_target='"
				+ modelTarget
				+ "') and report_id="
				+ templateId;
		String sql2 = "delete from template_model where template_id="
				+ templateId + " and model_id=" + modelId
				+ " and model_target='" + modelTarget + "'";
		batchJdbcUpdate(new String[] { sql1, sql2 });
	}

	@Override
	public List getReportTarget(Long reportId, String targetField) {
		String hql = "from ReportTarget t where t.status <> '"
				+ Constants.STATUS_DEL + "' and t.reportId = " + reportId
				+ " and t.targetField ='" + targetField + "'"
				+ " order by t.targetOrder,t.pkid";
		Map map = new HashMap();
		return list(hql, map);
	}

	@Override
	public Long insertTarget(ReportTarget target) {
		Long pkid = (Long) getHibernateTemplate().save(target);
		return pkid;
	}

	@Override
	public void updateTargetRelation(Long templateId, Long modelId,
			Map<String, String> targetMap) {
		String[] sqls = new String[targetMap.size()];
		int i = 0;
		for (Entry<String, String> entry : targetMap.entrySet()) {
			String sql = "INSERT INTO template_model(pkid,template_id,model_id,template_target,model_target) values("
					+ DBDialect.genSequence("CODE_TEMPLATE_SEQ")
					+ ","
					+ templateId
					+ ","
					+ modelId
					+ ",'"
					+ entry.getKey()
					+ "','" + entry.getValue() + "') ";
			sqls[i] = sql;
			i++;
		}
		batchJdbcUpdate(sqls);
	}

	public List getDateOrganEditReportForYJH(String paramDate,
			Integer paramorgan_type, String canEdit, Long userId,
			String systemId, String levelFlag) {
		String freqStr = "";
		StringBuffer hql = new StringBuffer(
				"select {r.*} from code_rep_report r ")
				.append("where r.status<>9 ");
		if (paramDate != null && !"".equals(paramDate)) {
			hql.append("and ").append("r.begin_date <= '").append(paramDate)
					.append("' and ").append("r.end_date >= '")
					.append(paramDate).append("'");
		}

		if (systemId != null && !"".equals(systemId)) {
			hql = hql
					.append(" and r.report_type in (select t.pkid from code_rep_types t where t.system_code=")
					.append(systemId);
			if (levelFlag != null && !"".equals(levelFlag)) {
				hql = hql.append(" and t.showlevel=").append(levelFlag + ")");
			}
		} else {
			if (levelFlag != null && !"".equals(levelFlag)) {
				hql = hql
						.append(" and r.report_type in (select t.pkid from code_rep_types t where ");
				hql = hql.append(" t.showlevel=").append(levelFlag + ")");
			}
		}

		hql = hql.append("  order by r.report_type,r.show_order");
		List ls = list(hql.toString(),
				new Object[][] { { "r", Report.class } }, null);

		return ls;
	}

	public void delTemplateTarget(Long templateId, Long modelId,
			String targetField) {
		String sql1 = "delete from code_rep_target where report_id="
				+ templateId + " AND target_field='" + targetField + "'";
		String sql2 = "delete from template_model where template_id="
				+ templateId + " and template_target='" + targetField + "'";
		batchJdbcUpdate(new String[] { sql1, sql2 });
	}
}
