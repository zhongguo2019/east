package com.krm.ps.model.flexiblequery.dao.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.hibernate.Hibernate;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.model.flexiblequery.dao.DataFillDAO;
import com.krm.ps.model.vo.DicItem;
import com.krm.ps.model.vo.Page;
import com.krm.ps.model.vo.PaginatedListHelper;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportConfig;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;
import com.krm.ps.util.DBDialect;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: KRM
 * </p>
 * 
 * @author
 */
public class DataFillDAOHibernate extends BaseDAOHibernate implements
		DataFillDAO {

	public List<DicItem> getDicvalue(String reportdate, ReportTarget rt) {
		List<DicItem> dicList = new ArrayList<DicItem>();
		String[] str = rt.getStocktype().split(";");
		String repdatatable = str[0].replaceAll("\\{M\\}",
				reportdate.substring(4, 6));
		repdatatable = repdatatable.replaceAll("\\{Y\\}",
				reportdate.substring(0, 4));
		repdatatable = repdatatable.replaceAll("\\{D\\}",
				reportdate.substring(6, 8));
		String sql = "select distinct " + str[1] + " as dicId," + str[2]
				+ " as dicName from " + repdatatable + " where REPORT_ID="
				+ rt.getDicPid();
		Object[][] scalaries = new Object[][] { { "dicId", Hibernate.STRING },
				{ "dicName", Hibernate.STRING } };
		List reptab = list(sql, null, scalaries, null);
		for (int m = 0; m < reptab.size(); m++) {
			DicItem di = new DicItem();
			Object[] objmval = (Object[]) reptab.get(m);
			di.setDicId(objmval[0].toString());
			di.setDicName(objmval[1].toString());
			dicList.add(di);
		}
		return dicList;
	}

	public List getMapColumn() {
		String hql = "from MapColumn r where r.status ='0'";
		List result = this.list(hql);
		return result;
	}

	/**
	 * 根据模板，关联查出模板下报表的数据 2014-07-08 添加传参Map字段映射
	 * 
	 * @param reportId
	 * @param organId
	 * @param reportDate
	 * @param reportTargetList
	 * @param page
	 * @param bondstock
	 * @param idxid
	 * @param mapC
	 * @return
	 */

	public PaginatedListHelper getReportDataByPageAll1(String cstatus,
			int isadmin, List resultablename, Long reportId, String organId,
			String reportDate, Page page, int idxid, String levelFlag, Map mapC) {
		String lab = "";
		StringBuffer[] sbs = null;
		// isadmin 为2时，为超级管理员或者isadmin为3 ,即不是管理员，但是也能看 但是如果isadmin为0，则不能看下级机构的数据
		if (isadmin == 2 || isadmin == 3) {
			/*******
			 * 青海客户要求，如果是超级管理员的话，可以看所有机构，而以下联社的本级机构只能看本级机构的数据，不能看下级机构，所以注释掉，以后需要
			 * ，可以再放开
			 *****/
			List labL = getOrganidx(organId, idxid);
			if (labL.size() > 0) {
				lab = (String) labL.get(0);
			}
			sbs = getModelSql1(resultablename, reportId, reportDate, lab,
					levelFlag, isadmin, cstatus, mapC);
		} else {
			sbs = getModelSql1(resultablename, reportId, reportDate, organId,
					levelFlag, isadmin, cstatus, mapC);
		}
		StringBuffer sql = sbs[0];
		StringBuffer totalsql = sbs[1];
		int totalRecord = (int) getTotalRecord(totalsql.toString());
		page.setTotalRecord(totalRecord);
		page.setRecordNo((page.getPageNo() - 1) * page.getPageSize());
		String sql1 = DBDialect.sqlPage(sql.toString(), page.getRecordNo(),
				page.getPageSize());
		List list = getListOfMapFromSQL(sql1);
		return new PaginatedListHelper(list, page, sbs[2].toString());
	}

	/**
	 * 组织sql语句
	 * 
	 * @return
	 */
	private StringBuffer[] getModelSql1(List<ReportConfig> resultablename,
			Long reportId, String date, String organId, String levelFlag,
			int isadmin, String cstatus, Map mapC) {
		// /获得该模板下所有模型表的关联关系
		List reptab = getreptabL(reportId);
		StringBuffer[] sbs = new StringBuffer[3];
		StringBuffer selectitem = new StringBuffer();
		StringBuffer totalbu = new StringBuffer();
		// 组合select成语句
		selectitem.append("select ");
		totalbu.append("select count(*) as total  ");
		StringBuffer selectitem2 = new StringBuffer();
		StringBuffer itme = new StringBuffer();
		StringBuffer tableitem = new StringBuffer();
		String maintab = "";
		String mainrepid = "";
		String rtable = "";
		String organTree = "";
		if (isadmin == 2 || isadmin == 3) {
			organTree = "select tr.CODE from code_org_organ tr,CODE_ORG_TREE tt where tr.PKID=tt.NODEID  and tt.SUBTREETAG like '"
					+ organId + "%' ";
		}
		for (int m = 0; m < reptab.size(); m++) {
			Object[] objmval = (Object[]) reptab.get(m);
			Long temid = (Long) objmval[1];
			Long modid = (Long) objmval[2];
			String whecondition = (String) objmval[3];
			String ismaintable = (String) objmval[4];
			String tablename = (String) objmval[5];
			String tabl = (String) objmval[6];
			tablename = tablename.replaceAll("\\{M\\}", date.substring(4, 6));
			tablename = tablename.replaceAll("\\{Y\\}", date.substring(0, 4));
			tablename = tablename.replaceAll("\\{D\\}", date.substring(6, 8));
			// 1说明该表是主表
			if ("1".equals((String) objmval[4])) {
				for (ReportConfig rc : resultablename) {
					if (rc.getReportId().toString().equals(modid + "")) {
						rtable = rc.getDefChar();
						rtable = rtable.replaceAll("\\{Y\\}",
								date.substring(0, 4));
						rtable = rtable.replaceAll("\\{M\\}",
								date.substring(4, 6));
						rtable = rtable.replaceAll("\\{D\\}",
								date.substring(6, 8));
					}
				}
				tableitem.append(" from " + tablename + " " + tabl + " ");
				maintab = tabl;
				mainrepid = String.valueOf(modid);
			} else {
				tableitem.append(" left outer join  " + tablename + " " + tabl
						+ " on " + whecondition);
			}
			selectitem2.append(getitem(temid, modid, tabl)); // o-o
		}
		itme.append(maintab + ".pkid as PKID," + maintab + "."
				+ mapC.get("organ_id") + " as ORGAN_ID," + reportId + ","
				+ maintab + "." + mapC.get("report_date") + " as REPORT_DATE,");
		if (isadmin == 2 || isadmin == 3) {
			tableitem.append(" where substr(" + maintab + "."
					+ mapC.get("report_date") + ",0,6)||'00' ='"
					+ date.substring(0, 6) + "00" + "' AND " + maintab + "."
					+ mapC.get("organ_id") + "  in ( " + organTree + ")");
		} else {
			tableitem.append(" where substr(" + maintab + "."
					+ mapC.get("report_date") + ",0,6)||'00' ='"
					+ date.substring(0, 6) + "00" + "' AND " + maintab + "."
					+ mapC.get("organ_id") + "  in ( '" + organId + "')");
		}
		if (cstatus != null) {
			tableitem.append(" and " + maintab
					+ ".pkid in (select re.KEYVALUE from " + rtable
					+ "  re where  re.CSTATUS in (" + cstatus + ") )");
		}
		selectitem.append(itme
				+ selectitem2.substring(0, selectitem2.lastIndexOf(","))
				+ tableitem);
		totalbu.append(tableitem);
		sbs[0] = selectitem;
		sbs[1] = totalbu;
		sbs[2] = new StringBuffer(rtable);
		return sbs;
	}

	// 获得机构树的IDx标识
	public List getOrganidx(String organcode, int idx) {
		String sql = "select tt.SUBTREETAG ttlab from code_org_organ t,CODE_ORG_TREE tt where t.PKID=tt.NODEID and t.CODE='"
				+ organcode + "' and  tt.IDXID=" + idx + "";
		List resultL = list(sql, null, new Object[][] { { "ttlab",
				Hibernate.STRING } });
		return resultL;
	}

	public long getTotalRecord(String sql) {
		return getTotalRecord(sql, "total");
	}

	public long getTotalRecord(String sql, String totalColumn) {
		List list = getListOfMapFromSQL(sql);
		if (list.size() <= 0) {
			return 0;
		}
		Map totalMap = (Map) list.get(0);
		Integer total = Integer.valueOf(String.valueOf(
				totalMap.get(totalColumn)).trim());// 控制强转时字符串中有空格
		if (total == null) {
			return 0;
		}
		return total.longValue();
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

	/**
	 * 获得模板下各个模型的关联关系
	 */
	private List getreptabL(Long pkid) {
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

	@Override
	public Long getReportByid(Report report) {
		String sql = "select t.pkid as pkid  from code_rep_report t where  t.report_type in(select pkid from code_rep_types t where  t.showlevel=0) and t.name='"
				+ report.getName() + "'";
		Object[][] scalaries = { { "pkid", Hibernate.LONG } };
		List result = list(sql, null, scalaries, null);
		Object[] o = null;
		if (result != null && result.size() > 0) {
			o = new Object[] { result.get(0) };
		}
		return Long.parseLong(o[0].toString());
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

	/**
	 * 重置数据状态
	 * 
	 * @param resulttablename
	 * @param repdatafname
	 * @param dataId
	 * @param reportId
	 * @return
	 */
	public int resetResultData(String resulttablename, String repdatafname,
			String dataId, String reportId) {
		String resultsql = "update " + resulttablename
				+ " set cstatus='0' where keyvalue=" + Long.parseLong(dataId)
				+ " and MODEL_ID=" + reportId;
		try {
			this.batchJdbcUpdate(new String[] { resultsql });
			return 1;
		} catch (RuntimeException re) {
			return 0;
		}
	}

	@Override
	public List<DicItem> getDicByPid(Long dicPid) {
		String hql = "from DicItem di where di.parentId='" + dicPid + "'";
		return list(hql);
	}

	@Override
	public Object[] getRelateTarget(String modelid, String targetid,
			Long reportId) {
		Object[][] scalaries = { { "tid", Hibernate.LONG },
				{ "mid", Hibernate.LONG }, { "tt", Hibernate.STRING },
				{ "mt", Hibernate.STRING } };
		String sqlStr = "SELECT  template_id as tid,modeL_id as mid,template_target as tt,model_target as mt"
				+ " FROM template_model WHERE template_id=? and model_id=? and model_target=?";
		List result = list(sqlStr, null, scalaries, new Object[] { reportId,
				modelid, targetid });
		Object[] o = null;
		if (result != null && result.size() > 0) {
			o = (Object[]) result.get(0);
		}
		return o;
	}

	@Override
	public List getRelateTargets(Map<String, Long> relation) {
		List list = new ArrayList();
		for (Entry e : relation.entrySet()) {
			String str = (String) e.getKey();
			String[] strs = str.split("_");
			Object[][] scalaries = { { "tid", Hibernate.LONG },
					{ "mid", Hibernate.LONG }, { "tt", Hibernate.STRING },
					{ "mt", Hibernate.STRING } };
			String sqlStr = "SELECT  template_id as tid,modeL_id as mid,template_target as tt,model_target as mt"
					+ " FROM  template_model WHERE template_id=? and model_id=? and model_target=?";
			List result = list(sqlStr, null, scalaries,
					new Object[] { e.getValue(), strs[0], strs[1] });
			if (result != null && result.size() > 0) {
				list.add(result.get(0));
			}
		}
		return list;
	}
	/**
	 * 查得模板下所有数据.高级查询 -添加字段映射
	 * @param reportId
	 * @param organId
	 * @param reportDate
	 * @param page
	 * @param idxid
	 * @return
	 */
	
	public PaginatedListHelper getReportDataByPageAllMapColumn(String cstatus,int isadmin,List resultablename,Long reportId, String organId,
			String reportDate,Page page,int idxid,String levelFlag, String zhi1,
			String targetField1, String zhi2,
			String targetField2, String zhi3,
			String targetField3,Map mapC) {
			String lab = "";
			StringBuffer[] sbs=null;
			//isadmin 为2时，为超级管理员或者isadmin为3 ,即不是管理员，但是也能看  但是如果isadmin为0，则不能看下级机构的数据
			if(isadmin==2||isadmin==3){
			/*******青海客户要求，如果是超级管理员的话，可以看所有机构，而以下联社的本级机构只能看本级机构的数据，不能看下级机构，所以注释掉，以后需要，可以再放开*****/
			List labL = getOrganidx(organId, idxid);
			if (labL.size() > 0) {
				lab = (String) labL.get(0);
			}
			 	sbs=getModelSqlMapColumn(resultablename,reportId,reportDate,lab,levelFlag,isadmin,cstatus,zhi1,targetField1,zhi2,targetField2,zhi3,targetField3, mapC);
			}else{
				sbs=getModelSqlMapColumn(resultablename,reportId,reportDate,organId,levelFlag,isadmin,cstatus,zhi1,targetField1,zhi2,targetField2,zhi3,targetField3,mapC);
			}
			//查询包含子机构数据
			StringBuffer sql = sbs[0];
			StringBuffer totalsql = sbs[1];
			int totalRecord = (int) getTotalRecord(totalsql.toString());
			page.setTotalRecord(totalRecord);
			page.setRecordNo((page.getPageNo()-1)*page.getPageSize());
			String sql1 = DBDialect.sqlPage(sql.toString(),page.getRecordNo(),page.getPageSize());
			List list=getListOfMapFromSQL(sql1);
			return new PaginatedListHelper(list, page,sbs[2].toString());
		}
	
	/**
	 * 组织sql语句
	 * @return
	 */
	private StringBuffer[] getModelSqlMapColumn(List <ReportConfig> resultablename,Long reportId,String date,String organId,String levelFlag ,int isadmin,String cstatus,String zhi1,String targetField1,String zhi2,String targetField2,String zhi3,String targetField3 ,Map mapC){
		//获得该模板下所有模型表的关联关系
		  List reptab=getreptabL(reportId);
		  StringBuffer[] sbs=new StringBuffer[3];
		  StringBuffer selectitem=new StringBuffer();
		  StringBuffer totalbu=new StringBuffer();
		  selectitem.append("select ");
		  totalbu.append("select count(*) as total  ");
		  StringBuffer selectitem2=new StringBuffer();
		  StringBuffer itme=new StringBuffer();
		  StringBuffer tableitem=new StringBuffer();
		  String maintab="";
		  String mainrepid="";
		  String rtable="";
		  String organTree="";
		  if(isadmin==2||isadmin==3){
			  organTree = "select tr.CODE from code_org_organ tr,CODE_ORG_TREE tt where tr.PKID=tt.NODEID  and tt.SUBTREETAG like '"
					+ organId + "%' ";
		  }
		  for(int m=0;m<reptab.size();m++){
			  Object[]  objmval=(Object[])reptab.get(m); 
			  Long temid=(Long)objmval[1];
			  Long modid=(Long)objmval[2];
			  String whecondition=(String)objmval[3];
			  String ismaintable=(String)objmval[4];
			  String tablename=(String)objmval[5];
			  String tabl=(String)objmval[6];
			  //1说明该表是主表
			  if("1".equals((String)objmval[4])){
				  for(ReportConfig rc:resultablename){
						 if( rc.getReportId().toString().equals(modid+"") ){
							 rtable=rc.getDefChar();
							 rtable=rtable.replaceAll("\\{M\\}",date.substring(4,6));
							 rtable=rtable.replaceAll("\\{D\\}",date.substring(6,8));
							 rtable=rtable.replaceAll("\\{Y\\}",date.substring(0,4));
						 }
				  }
				  tablename=tablename.replaceAll("\\{M\\}",date.substring(4,6));
				  tablename=tablename.replaceAll("\\{Y\\}",date.substring(0,4));
				  tablename=tablename.replaceAll("\\{D\\}",date.substring(6,8));
				  tableitem.append( " from "+tablename+" "+tabl+" ");
				  maintab=tabl;
				  mainrepid=String.valueOf(modid);
			  }else{
				  tableitem.append(" left outer join  "+tablename+" "+tabl+" on "+whecondition);
			  }
			  selectitem2.append(getitem(temid,modid,tabl));
		  }
		  itme.append(maintab+".pkid as PKID,"+maintab+"."+mapC.get("organ_id")+" as ORGAN_ID,"+reportId+","+maintab+"."+mapC.get("report_date")+" as REPORT_DATE,");
		 if(isadmin==2||isadmin==3){
			 tableitem.append(" where substr("+maintab+"."+mapC.get("report_date")+",1,6)||'00' ='"+date.substring(0, 6)+"00"+"' AND "+maintab+"."+mapC.get("organ_id")+"  in ( "+organTree+")" );
			 
		 }else{
			 tableitem.append(" where substr("+maintab+"."+mapC.get("report_date")+",1,6)||'00' ='"+date.substring(0, 6)+"00"+"' AND "+maintab+"."+mapC.get("organ_id")+"  in ( '"+organId+"')" );
		 }
		 if(cstatus!=null){
			  tableitem.append(" and "+maintab+".pkid in (select re.KEYVALUE from "+rtable+"  re where  re.CSTATUS in ("+cstatus+") )");
		  }
		 if( !"0".equals( targetField1 ) && targetField1 !=null ){
		 	  tableitem.append(" and "+maintab+"."+targetField1+" like '%"+zhi1+"%'" );//把搜索得到的只组装到查询语句里
		 }	
		 if( !"0".equals( targetField2 ) && targetField2 !=null ){
		 	  tableitem.append(" and "+maintab+"."+targetField2+" like '%"+zhi2+"%'" );//把搜索得到的只组装到查询语句里
		 }	
		 if( !"0".equals( targetField3 ) && targetField3 !=null ){
		 	  tableitem.append(" and "+maintab+"."+targetField3+" like '%"+zhi3+"%'" );//把搜索得到的只组装到查询语句里
		 }	
		  selectitem.append(itme+selectitem2.substring(0,selectitem2.lastIndexOf(","))+tableitem); 
		  totalbu.append(tableitem);
		  sbs[0]=selectitem;
		  sbs[1]=totalbu;
		  sbs[2]=new StringBuffer(rtable);
		return sbs;
	}
	
}
