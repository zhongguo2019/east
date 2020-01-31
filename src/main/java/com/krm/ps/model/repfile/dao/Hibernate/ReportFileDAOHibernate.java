package com.krm.ps.model.repfile.dao.Hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.model.repfile.dao.ReportFileDAO;
import com.krm.ps.model.repfile.util.FormulaHelp;
import com.krm.ps.model.vo.CodeRepConfigure;
import com.krm.ps.model.vo.CodeRepJhgz;
import com.krm.ps.model.vo.CodeRepJhgzZf;
import com.krm.ps.model.vo.CodeRepSubmitalist;
import com.krm.ps.model.vo.KettleData;
import com.krm.ps.model.vo.RepFlFomat;
import com.krm.ps.model.vo.RepFlRep;
import com.krm.ps.model.vo.RepFlRepFiled;
import com.krm.ps.model.vo.RepFlfile;
import com.krm.ps.model.vo.ReportRule;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportConfig;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;
import com.krm.ps.util.Constant;
import com.krm.ps.util.Constants;
import com.krm.ps.util.SysConfig;

public class ReportFileDAOHibernate extends BaseDAOHibernate implements
		ReportFileDAO {

	/**
	 * 分表功能配置属性对象
	 */
	private static Properties sepTablePro = null;
	private static Properties sepTableProTemp = null;

	@Override
	public List<RepFlFomat> getRepAList(String[] repIDarray, String batch) {
		String hql = "from RepFlFomat where repfileexte='" + batch + "'";
		return list(hql);
	}

	@Override
	/**
	 *获得模板下各个模型的关联关系 
	 */
	public List getreptabL(Long pkid) {
		String sql = "SELECT PKID , TEMPID , MODEID, WHECONDITION, ISMAINTABLE, TABLENAME, TABLAB FROM STRD_REPORT_WHECONDITION where TEMPID="
				+ pkid + " order by ISMAINTABLE";
		Object[][] scalaries = new Object[][] { { "PKID", Hibernate.LONG },
				{ "TEMPID", Hibernate.LONG }, { "MODEID", Hibernate.LONG },
				{ "WHECONDITION", Hibernate.STRING },
				{ "ISMAINTABLE", Hibernate.STRING },
				{ "TABLENAME", Hibernate.STRING },
				{ "TABLAB", Hibernate.STRING } };
		return list(sql, null, scalaries, null);
	}

	/**
	 * 保存文件
	 * 
	 * @param repflfile
	 */
	public void saveRepFlfile(RepFlfile repflfile) {
		saveObject(repflfile);
	}

	public void updateRepLog(String date, String organCode, String logtype,
			String status, String reportid, String batch, int idx) {
		date = date.substring(5, 7);
	 
		String updatesql = "update  LOG_DATA_" + date + " set BATCH='" + batch
				+ "' ,status='" + status + "' where CD_ORGAN='" + organCode
				+ "' and ID_REP in(" + reportid + " )and MK_TYPE='" + logtype
				+ "'";
		logger.info("updatesql:" + updatesql);
		jdbcUpdate(updatesql, null);
	}

	public List getOrganidx(String organcode, int idx) {

		String sql = "select tt.SUBTREETAG ttlab from code_org_organ t,CODE_ORG_TREE tt where t.PKID=tt.NODEID and t.CODE='"
				+ organcode + "' and  tt.IDXID=" + idx + "";
		List resultL = list(sql, null, new Object[][] { { "ttlab",
				Hibernate.STRING } });
		return resultL;
	}

	/**
	 * 获得上报数据
	 * 
	 * @param reportId
	 * @param organId
	 * @param reportDate
	 * @param page
	 * @param idxid
	 * @param levelFlag
	 * @return
	 */
	public StringBuffer getReportDataByPageAll(String begindate,
			String enddate, Long reportId, String organId, String reportDate,
			int idxid, List reptab, Map mapC) {
		String lab = "";
		List labL = getOrganidx(organId, idxid);
		if (labL.size() > 0) {
			lab = (String) labL.get(0);
		}
		
		//查询上报前配置表配置信息 通过用户机构和报表id
		CodeRepConfigure codeRepConfigure = new CodeRepConfigure();
		codeRepConfigure.setOrgan_id(organId);
		codeRepConfigure.setReport_id(reportId+"");
		List listRepConfigure =getRepConfigure(codeRepConfigure);
		
		
		// 获得sql语句
		StringBuffer[] sbs = getModelSql(begindate, enddate, reportId,
				reportDate, lab, reptab, mapC ,listRepConfigure);
		// 查询包含子机构数据
		StringBuffer sql = sbs[0];
		// List list=this.getListOfMapFromSQL(sql.toString());
		return sql;
	}
	
	

	/**
	 * 组织sql语句
	 * 
	 * @return
	 */
	public StringBuffer[] getModelSql(String begindate, String enddate,
			Long reportId, String date, String lab, List reptab, Map mapC ,List listRepConfigure) {
		StringBuffer[] sbs = new StringBuffer[2];
		StringBuffer selectitem = new StringBuffer();
		// 组合select成语句
		String bdate = begindate.replace("-", "");
		String edate = enddate.replace("-", "");
		selectitem.append("select  /*+ parallel(dual,8) */  ");
		StringBuffer selectitem2 = new StringBuffer();
		StringBuffer tableitem = new StringBuffer();
		String maintab = "";
		String mainrepid = "";
		String organTree = "select tr.CODE from code_org_organ tr,CODE_ORG_TREE tt where tr.PKID=tt.NODEID  and tt.SUBTREETAG like '"
				+ lab + "%' ";
		String whereStr = "";
		String astabl = "";
		for (int m = 0; m < reptab.size(); m++) {
			Object[] objmval = (Object[]) reptab.get(m);
			Long temid = (Long) objmval[1];
			Long modid = (Long) objmval[2];
			String whecondition = (String) objmval[3];
			String ismaintable = (String) objmval[4];
			String tablename = (String) objmval[5];
			String tabl = (String) objmval[6];
			tablename = tablename.replaceAll("\\{Y\\}", date.substring(0, 4));
			tablename = tablename.replaceAll("\\{M\\}", date.substring(4, 6));
			tablename = tablename.replaceAll("\\{D\\}", date.substring(6, 8));
			// 1说明该表是主表
			if ("1".equals((String) objmval[4])) {
				tableitem.append(" from " + tablename + " " + tabl + " ");
				maintab = tabl;
				mainrepid = String.valueOf(modid);
			} else {
				tableitem.append(" left outer join  " + tablename + " " + tabl
						+ " on " + whecondition);
				logger.debug("tableitem:" + tableitem);
			}
			selectitem2.append(getitem(temid, modid, tabl));
			whereStr += " AND " + tabl + "." + mapC.get("report_date") + " >='"
					+ bdate + "'  AND " + tabl + "." + mapC.get("report_date")
					+ " <='" + edate + "'";
			
			astabl = tabl ;
		}
		tableitem.append(" where " + maintab + "." + mapC.get("organ_id") + "  in ( " + organTree + ") " + whereStr);
		
		
		if(!"".equals(astabl)){
			astabl = astabl + ".";
		}
		
		//循环添加配置表配置信息
		for (int i = 0; i < listRepConfigure.size(); i++) {
			CodeRepConfigure  codeRepConfigure = (CodeRepConfigure)listRepConfigure.get(i);
			
			
			tableitem.append(" and  abs("+ astabl +codeRepConfigure.getTarget_id()+")"+codeRepConfigure.getRtype()+codeRepConfigure.getThreshold());
		}
		
		selectitem.append(selectitem2.substring(0, selectitem2.lastIndexOf(",")) + tableitem);
		sbs[0] = selectitem;
		return sbs;
	}

	/**
	 * 获得查询项
	 * 
	 * @param temid
	 * @param modid
	 * @param tab
	 * @return
	 */
	public StringBuffer getitem(Long temid, Long modid, String tab) {
		// 获得模板关联的字段
		StringBuffer selectitme = new StringBuffer();
		List repfileL = getrepfileL(temid, modid);
		for (int j = 0; j < repfileL.size(); j++) {
			Object[] objval = (Object[]) repfileL.get(j);
			selectitme.append(tab + "." + (String) objval[3] + "  as  "
					+ (String) objval[4] + ",");
		}
		return selectitme;
	}

	/**
	 * 获得模板中
	 */
	public List getrepfileL(Long reportid, Long modeid) {

		String sql = "select pkid,TEMPLATE_ID as temid,MODEL_ID as modeid,MODEL_TARGET as modeltarget,TEMPLATE_TARGET as temtarget from TEMPLATE_MODEL  where TEMPLATE_ID="
				+ reportid + " and MODEL_ID=" + modeid;
		Object[][] scalaries = new Object[][] { { "pkid", Hibernate.LONG },
				{ "temid", Hibernate.LONG }, { "modeid", Hibernate.LONG },
				{ "modeltarget", Hibernate.STRING },
				{ "temtarget", Hibernate.STRING } };
		return list(sql, null, scalaries, null);
	}

	public List<RepFlRep> getRepFlRep(RepFlFomat repFla) {
		String hql = "from RepFlRep where repflid=" + repFla.getPkid()
				+ " order by reporder";
		return list(hql);
	}

	public String getouterorgan(String organcode, String systemid) {
		String sql = "SELECT outer_org_code as result FROM code_org_contrast where system_code="
				+ systemid + " and NATIVE_ORG_ID='" + organcode + "'";
		List resultL = list(sql, null, new Object[][] { { "result",
				Hibernate.STRING } });
		if (resultL.size() > 0) {
			return ((String) resultL.get(0));
		}
		return "";

	}

	public List getRepFlFiled(RepFlRep repFla) {
		String sql = "select {tr.*} ,{fl.*} from DSP_FL_REP_FILED fl,CODE_REP_TARGET tr  where fl.REPID= "
				+ repFla.getPkid()
				+ "    and   fl.REPFLID=tr.PKID order by fl.REPORDER ,tr.TARGET_ORDER";
		Object[][] entities = new Object[][] { { "tr", ReportTarget.class },
				{ "fl", RepFlRepFiled.class } };
		return list(sql, entities, null);
	}

	public int getfilecount(Long repfileid, String repdate, String batch,
			String organcode, String filename) {
		String sql = "select pkid as result from DSP_FL_FILE where FILEID="
				+ repfileid + " and FILEBATH='" + batch + "' and datadate='"
				+ repdate + "' and organcode='" + organcode
				+ "' and filename='" + filename + "'";
		List resultL = list(sql, null, new Object[][] { { "result",
				Hibernate.INTEGER } });
		if (resultL.size() == 0) {
			return 0;
		}
		return ((Integer) resultL.get(0)).intValue();
	}

	public void insertrepDatF(String[] sqls) {
		batchJdbcUpdate(sqls);
	}

	/**
	 * 查询文件
	 */
	public List<RepFlfile> getRepFlfile(RepFlfile repflfile) {

		String hql = "from RepFlfile where sysid='" + repflfile.getSysid()
				+ "' ";
		if (repflfile.getPkid() != null) {
			hql += " and pkid=" + repflfile.getPkid();
		}
		return list(hql);
	}

	/**
	 * 查询文件
	 */
	public List<RepFlfile> getRepFlfile2(RepFlfile repflfile, String organid,
			int isadmin, int idxid) {

		String lab = "";
		List labL = getOrganidx(organid, idxid);
		if (labL.size() > 0) {
			lab = (String) labL.get(0);
		}
		String organTree = "";
		if (isadmin == 2 || isadmin == 3) {
			organTree = "select tr.CODE from code_org_organ tr,CODE_ORG_TREE tt where tr.PKID=tt.NODEID  and tt.SUBTREETAG like '"
					+ lab + "%' ";
		} else {
			organTree = organid;
		}

		String sql = "select {t.*} from DSP_FL_FILE t where t.ORGANCODE in ("
				+ organTree + ")  AND  t.SYSID='" + repflfile.getSysid() + "' ";

		if (repflfile.getPkid() != null) {
			sql += " and pkid=" + repflfile.getPkid();
		}
		
		sql += " order by pkid desc ";
		return list(sql, new Object[][] { { "t", RepFlfile.class } }, null,
				null);

	}

	public void updateRepFlfile(RepFlfile repfile) {
		String hql = "update RepFlfile  set downloadnum= downloadnum+1 where pkid="
				+ repfile.getPkid();
		;
		update(hql);
	}

	/**
	 * 查询文件
	 */
	public List<RepFlfile> getRepFlfile(String pkids) {

		String hql = "from RepFlfile where pkid in (" + pkids + ")";
		return list(hql);
	}

	public void delRepFlfile(String pkids) {
		String hql = "delete from RepFlfile  where pkid in (" + pkids + ")";
		update(hql);
	}

	public void delRepFlfile(RepFlfile repfile) {
		String hql = "delete from RepFlfile  where pkid=" + repfile.getPkid();
		update(hql);
	}

	/**
	 * 获取存储物理表名称
	 * 
	 * @param reportID
	 *            报表编码
	 * @param date
	 *            日期
	 * @return String
	 */
	public String getPhyTableName(String reportID, String date) {
		String phyTableName = FormulaHelp.getPhyTable(Integer
				.parseInt(reportID));

		if (phyTableName.startsWith("rep_dataf")) {
			// 流水型报表，add by safe at 2007.11.27
			phyTableName = getFlowRepTableName(reportID, date);
		} else if (phyTableName.startsWith("rep_datam")) {
			// 采集层数据表名生成
			// 获取分表号
			if (sepTablePro == null) {
				initSepTablePro();
			}
			String sepTableNo = sepTablePro.getProperty(reportID);
			if (sepTableNo == null || "".equals(sepTableNo)) {
				// 默认的分表号
				sepTableNo = "0";
			}
			phyTableName = "rep_dataf_" + date.substring(4, 6) + "_"
					+ sepTableNo;
		} else {
			if ("true".equals(SysConfig.IS_DISTRIBUTE_DATA)) {
				phyTableName = "rep_data_" + date.substring(4, 6);
			}
		}
		return phyTableName;
	}

	/**
	 * 获取流水表的分表物理表名
	 * 
	 * @param reportID
	 *            报表编码
	 * @param date
	 *            日期
	 * @return String
	 */
	public synchronized String getFlowRepTableName(String reportID, String date) {
		// 获取分表号
		if (sepTablePro == null) {
			initSepTablePro();
		}
		String sepTableNo = sepTablePro.getProperty(reportID);
		if (sepTableNo == null || "".equals(sepTableNo)) {
			// 默认的分表号
			sepTableNo = "0";
		}
		StringBuffer tableName = new StringBuffer();
		tableName.append("rep_dataf_").append(date.substring(4, 6)).append('_')
				.append(sepTableNo);
		return tableName.toString();
	}

	/**
	 * 初始化分表配置信息
	 * 
	 */
	private void initSepTablePro() {
		sepTablePro = new Properties();
		// 根据分表功能编码获取报表配置信息
		List repCfgList = getReportConfigsByFunc(new Long(
				Constant.CONFIG_SUBAREA));
		if (repCfgList == null) {
			return;
		}
		ReportConfig rc = null;
		for (int i = 0; i < repCfgList.size(); i++) {
			rc = (ReportConfig) repCfgList.get(i);
			sepTablePro.put(rc.getReportId().toString(), rc.getDefChar());
		}
	}

	public List getReportConfigsByFunc(Long funcId) {
		String hql = "from ReportConfig t where t.funId = " + funcId
				+ " order by t.idxId,t.funId";
		Map map = new HashMap();
		return list(hql, map);
	}

	@Override
	public List getRepislock(String organId, String dataDate) {
		String sql = "select t.report_id ttlab from code_rep_status t where substr(t.frequency,1,6)=substr("
				+ dataDate + ",1,6) and t.islock=0 and t.organ_id=" + organId;
		List RulecodeL = list(sql, null, new Object[][] { { "ttlab",
				Hibernate.STRING } });
		return RulecodeL;
	}

	@Override
	public Long getReportByid(Report report, String showlevel) {
		String sql = "select t.pkid as pkid  from code_rep_report t where  t.report_type in(select pkid from code_rep_types t where  t.showlevel="
				+ showlevel + ") and t.name='" + report.getName() + "'";
		Object[][] scalaries = { { "pkid", Hibernate.LONG } };
		List result = list(sql, null, scalaries, null);
		Object[] o = null;
		if (result != null && result.size() > 0) {
			o = new Object[] { result.get(0) };
		}
		return Long.parseLong(o[0].toString());
	}

	@Override
	public Report getReport(Long reportId) {
		String hql = "from Report r where r.pkid=" + reportId;
		Report report = null;
		List list = list(hql);
		for (int i = 0; i < list.size(); i++) {
			report = (Report) list.get(0);
		}
		return report;
	}

	public List getMapColumn(Long reportId) {
		String hql = "from MapColumn  where status ='0' and reportid="
				+ reportId;
		List result = this.list(hql);
		return result;
	}

	@Override
	public List getRepislock2(String organId, String dataDate, String reportid) {
		String sql = "select t.report_id ttlab from code_rep_status t where substr(t.frequency,1,6)=substr("
				+ dataDate
				+ ",1,6)  and t.organ_id="
				+ organId
				+ " and t.report_id=" + reportid;
		List RulecodeL = list(sql, null, new Object[][] { { "ttlab",
				Hibernate.STRING } });
		return RulecodeL;
	}

	@Override
	public List<KettleData> getAll() {
		String db=SysConfig.DATABASE;
		String hql=null;
		if("oracle".equals(db)){
			hql = "from KettleData order by pkid";
		}else if("db2".equals(db)){
			hql="from KettleData";
		}
		return list(hql);
	}

	@Override
	public void savektrForm(KettleData kettleData) {
		saveObject(kettleData);

	}

	@Override
	public void addAttribute(String pkid, String attribute1, String message,
			String attribute2, String attribute3) {
		String updatesql = "update kettle_data set attribute1='" + attribute1
				+ "',attribute2='" + attribute2 + "',attribute3='" + attribute3
				+ "',attribute4='" + message + "' where pkid='" + pkid + "'";
		jdbcUpdate(updatesql, null);

	}

	@Override
	public byte[] getAttribute5(String pkid) {
		String hql = "from KettleData where pkid='" + pkid + "'";
		List<KettleData> list = list(hql);
		byte[] getAttribute5 = list.get(0).getAttribute5();
		return getAttribute5;
	}

	@Override
	public void delKtr(String pkid) {
		String hql = "delete from KettleData where pkid='" + pkid + "'";
		update(hql);
	}

	@Override
	public List<KettleData> queryktr(String ktrname, String ktrremark,
			String ktrtime) {
		StringBuffer hql = new StringBuffer("from KettleData where 1=1 ");
		if (ktrname != null && !ktrname.equals("")) {
			hql.append(" and ktrName='" + ktrname + "'");
		}
		if (ktrremark != null && !ktrremark.equals("")) {
			hql.append(" and ktrRemark like'%" + ktrremark + "%'");
		}
		if (ktrtime != null && !ktrtime.equals("")) {
			hql.append(" and ktrTime='" + ktrtime + "'");
		}
		hql.append(" order by pkid");
		return list(hql.toString());
	}

	@Override
	public List getUncheckReports(String condate, String organId, String reports) {
	final	String sql="select pkid,name from (select  rt.pkid,rt.name from "
+"(select a.name,b.pkid,b.report_type from code_rep_report a,code_rep_report b where exists (select t.report_id from code_rep_status t where substr(t.frequency,1,6)=substr('"+condate+"',1,6) and t.status<>9"
+" and t.organ_id='"+organId+"' and t.islock=0 and t.report_id=a.pkid ) and a.name=b.name ) rt ,code_rep_types ty "
+" where rt.report_type=ty.pkid and ty.showlevel='2' "
+" ) abc where pkid in ("+reports+")" ;
	//List list=list(sql, null, new Object[][]{{"pkid",Hibernate.LONG},{"name",Hibernate.STRING}});
	
List list=(List)getHibernateTemplate().execute(new HibernateCallback() {
	@Override
	public Object doInHibernate(Session session) throws HibernateException,
			SQLException {
		List list=new ArrayList();
		Connection connection=session.connection();
		PreparedStatement ps=connection.prepareStatement(sql);
		ResultSet rs=ps.executeQuery();
		Map map=null;
		while (rs.next()) {
			map=new HashMap();
			map.put("pkid", rs.getLong("pkid"));
			map.put("name", rs.getString("name"));
			list.add(map);
		}
		return list;
	}
})	;	
		return list;
	}

	@Override
	public List<Report> getReports(String reports) {
		String hql=" from Report where pkid in ("+reports+")";
		return list(hql);
	}
	
	public String getJrxkzh(String organId){
		String sqlq = "select jrxkzh ttlab from code_filename where organ_id='"+organId+"'";
		List resultL = list(sqlq, null, new Object[][] { { "ttlab",
				Hibernate.STRING } });
		if(resultL.size()>0){
			String jr=(String) resultL.get(0);
			return jr;
		}
		return "";
	}

	@Override
	public List<CodeRepConfigure> getRepConfigure(
			CodeRepConfigure codeRepConfigure) {
		String sqlq = "select {t.*} from code_rep_configure t where 1=1";
		if(StringUtils.isNotBlank(codeRepConfigure.getOrgan_id())){
			sqlq += " and organ_id ='" + codeRepConfigure.getOrgan_id()+"'";
		}
		if(StringUtils.isNotBlank(codeRepConfigure.getReport_id()) && !"0".equals(codeRepConfigure.getReport_id()) ){
			sqlq += " and report_id ='" + codeRepConfigure.getReport_id()+"'";
		}
		if(StringUtils.isNotBlank(codeRepConfigure.getTarget_id())  && !"0".equals(codeRepConfigure.getTarget_id())){
			sqlq += " and target_id ='" + codeRepConfigure.getTarget_id()+"'";
		}
		if(StringUtils.isNotBlank(codeRepConfigure.getRtype()) && !"ALL".equals(codeRepConfigure.getRtype())){
			sqlq += " and rtype ='" + codeRepConfigure.getRtype()+"'";
		}
		if(StringUtils.isNotBlank(codeRepConfigure.getThreshold())){
			sqlq += " and threshold ='" + codeRepConfigure.getThreshold()+"'";
		}
		sqlq += " order by pkid desc ,organ_id " ;
		@SuppressWarnings("unchecked")
		List<CodeRepConfigure> resultL = this.list(sqlq,new Object[][]{{"t",CodeRepConfigure.class}},null);
		
		return resultL;
	}

	@Override
	public int saveRepConfigure(CodeRepConfigure codeRepConfigure) {
		int a = 0 ;
		try{
			this.saveObject(codeRepConfigure);
			a = 1 ;
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return a;
	}

	@Override
	public int delRepConfigure(long pkid) {
		
		int a = 0 ;
		try{
			String sqlq = "delete from code_rep_configure t where pkid="+pkid;
			this.jdbcUpdate(sqlq, null);
			a = 1 ;
		}catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return a;
	}

	
	@Override
	public List<CodeRepJhgz> getRepJhgz(CodeRepJhgz codeRepJhgz ,int isadmin, int idxid) {
	
		String sqlq = "select {t.*} from code_rep_jhgz t where 1=1";
		if(StringUtils.isNotBlank(codeRepJhgz.getOrgan_id())){
			
//			String lab = "";
//			List labL = getOrganidx(codeRepJhgz.getOrgan_id(), idxid);
//			if (labL.size() > 0) {
//				lab = (String) labL.get(0);
//			}
//			String organTree = "";
//			if (isadmin == 2 || isadmin == 3) {
//				organTree = "select tr.CODE from code_org_organ tr,CODE_ORG_TREE tt where tr.PKID=tt.NODEID  and tt.SUBTREETAG like '"
//						+ lab + "%' ";
//			} else {
//				organTree = codeRepJhgz.getOrgan_id();
//			}
			sqlq += " and organ_id = '" + codeRepJhgz.getOrgan_id()+"'";
		}
		if(StringUtils.isNotBlank(codeRepJhgz.getReport_id()) && !"0".equals(codeRepJhgz.getReport_id()) ){
			sqlq += " and report_id ='" + codeRepJhgz.getReport_id()+"'";
		}
		if(StringUtils.isNotBlank(codeRepJhgz.getTarget_id())  && !"0".equals(codeRepJhgz.getTarget_id())){
			sqlq += " and target_id ='" + codeRepJhgz.getTarget_id()+"'";
		}
		if(StringUtils.isNotBlank(codeRepJhgz.getDatabatch())){
			sqlq += " and databatch ='" + codeRepJhgz.getDatabatch().replaceAll("-", "")+"'";
		}
			
		sqlq += " order by organ_id " ;
		
		@SuppressWarnings("unchecked")
		List<CodeRepJhgz> resultL = this.list(sqlq,new Object[][]{{"t",CodeRepJhgz.class}},null);
		
		return resultL;
	}

	@Override
	public void updateRepJhgz(String pkid, String urlreasons) {
		String sqlq = "update CODE_REP_JHGZ set REASONS='"+urlreasons+"' where PKID="+pkid;
		this.jdbcUpdate(sqlq, null);
	}

	@Override
	public List listRepTarget(String reportid) {
		String sql = "select TARGET_FIELD,TARGET_NAME from CODE_REP_TARGET where REPORT_ID='"+reportid+"' and status <> '" + Constants.STATUS_DEL + "' order by target_Order ";
		Object[][] scalaries = new Object[][] { { "TARGET_FIELD", Hibernate.STRING }, { "TARGET_NAME", Hibernate.STRING }};
		return list(sql, null, scalaries, null);
	}

	@Override
	public CodeRepJhgz selectRepJhgz(String pkid) {
		String hql = "from CodeRepJhgz r where r.pkid=" + pkid;
		CodeRepJhgz report = null;
		List list = list(hql);
		for (int i = 0; i < list.size(); i++) {
			report = (CodeRepJhgz) list.get(0);
		}
		return report;
	}

	@Override
	public List<CodeRepJhgzZf> getRepJhgzZf(CodeRepJhgzZf codeRepJhgzZf ,int isadmin, int idxid) {
		String sqlq = "select {t.*} from code_rep_jhgzZf t where 1=1";
		if(StringUtils.isNotBlank(codeRepJhgzZf.getOrgan_id())){
//			String lab = "";
//			List labL = getOrganidx(codeRepJhgzZf.getOrgan_id(), idxid);
//			if (labL.size() > 0) {
//				lab = (String) labL.get(0);
//			}
//			String organTree = "";
//			if (isadmin == 2 || isadmin == 3) {
//				organTree = "select tr.CODE from code_org_organ tr,CODE_ORG_TREE tt where tr.PKID=tt.NODEID  and tt.SUBTREETAG like '"
//						+ lab + "%' ";
//			} else {
//				organTree = codeRepJhgzZf.getOrgan_id();
//			}
			sqlq += " and organ_id = '" + codeRepJhgzZf.getOrgan_id()+"'";
		}
		if(StringUtils.isNotBlank(codeRepJhgzZf.getReport_id()) && !"0".equals(codeRepJhgzZf.getReport_id()) ){
			sqlq += " and report_id ='" + codeRepJhgzZf.getReport_id()+"'";
		}
		if(StringUtils.isNotBlank(codeRepJhgzZf.getTarget_id())  && !"0".equals(codeRepJhgzZf.getTarget_id())){
			sqlq += " and target_id ='" + codeRepJhgzZf.getTarget_id()+"'";
		}
		if(StringUtils.isNotBlank(codeRepJhgzZf.getDatabatch())){
			sqlq += " and databatch ='" + codeRepJhgzZf.getDatabatch().replaceAll("-", "")+"'";
		}
			
		sqlq += " order by organ_id " ;
		
		@SuppressWarnings("unchecked")
		List<CodeRepJhgzZf> resultL = this.list(sqlq,new Object[][]{{"t",CodeRepJhgzZf.class}},null);
		
		return resultL;
	}

	@Override
	public void updateRepJhgzZf(String pkid, String urlreasons) {
		String sqlq = "update CODE_REP_JHGZZf set REASONS='"+urlreasons+"' where PKID="+pkid;
		this.jdbcUpdate(sqlq, null);
	}

	@Override
	public CodeRepJhgzZf selectRepJhgzZf(String pkid) {
		String hql = "from CodeRepJhgzZf r where r.pkid=" + pkid;
		CodeRepJhgzZf report = null;
		List list = list(hql);
		for (int i = 0; i < list.size(); i++) {
			report = (CodeRepJhgzZf) list.get(0);
		}
		return report;
	}

	@Override
	public List<CodeRepSubmitalist> getRepSubmitalist(
			CodeRepSubmitalist repSubmitalist) {
		String sqlq = "select {t.*} from CODE_REP_SUBMITALIST t where 1=1";
		if(StringUtils.isNotBlank(repSubmitalist.getOrganid())){
 
			sqlq += " and organid = '" + repSubmitalist.getOrganid()+"'";
		}
			
		sqlq += " order by reportname_en " ;
		
		@SuppressWarnings("unchecked")
		List<CodeRepSubmitalist> resultL = this.list(sqlq,new Object[][]{{"t",CodeRepSubmitalist.class}},null);
		return resultL ;
	}

	@Override
	public void jdbcUpdatSql(String updatesql) {
		this.jdbcUpdate(updatesql,null);
	}
	
	@Override
	public CodeRepSubmitalist selectCodeRepSubmitalist(String pkid) {
		String hql = "from CodeRepSubmitalist r where r.pkid=" + pkid;
		CodeRepSubmitalist report = null;
		List list = list(hql);
		for (int i = 0; i < list.size(); i++) {
			report = (CodeRepSubmitalist) list.get(0);
		}
		return report;
	}

	@Override
	public int updateRepsubmitAlist(String pkid, String urlreasons) {
		String sqlq = "update code_rep_submitalist set remarks='"+urlreasons+"' where PKID="+pkid;
		return this.jdbcUpdate(sqlq, null);
	}

	@Override
	public int updateRepsubmitAlistBy(String pkid, String information) {
		String sqlq = "update code_rep_submitalist set information='"+information+"'";
		return this.jdbcUpdate(sqlq, null);
	}
}
