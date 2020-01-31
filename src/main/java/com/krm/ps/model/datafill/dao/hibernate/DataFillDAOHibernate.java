package com.krm.ps.model.datafill.dao.hibernate;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.model.datafill.bo.FormulaHelp;
import com.krm.ps.model.datafill.dao.DataFillDAO;
import com.krm.ps.model.datafill.dao.FormulaDAO;
import com.krm.ps.model.vo.DicItem;
import com.krm.ps.model.vo.Page;
import com.krm.ps.model.vo.PaginatedListHelper;
import com.krm.ps.model.vo.ReportRule;
import com.krm.ps.model.vo.TemplateModel;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportConfig;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;
import com.krm.ps.util.Constants;
import com.krm.ps.util.DBDialect;
import com.krm.ps.util.FuncConfig;
import com.krm.ps.model.datafill.vo.StatusForm;

/**
 * <p>
 * Title:
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * 
 * <p>
 * Company: KRM
 * </p>
 * 
 * @author
 */
public class DataFillDAOHibernate extends BaseDAOHibernate implements
		DataFillDAO {
	private FormulaDAO formulaDAO;
	public FormulaDAO getFormulaDAO() {
		return formulaDAO;
	}

	public void setFormulaDAO(FormulaDAO formulaDAO) {
		this.formulaDAO = formulaDAO;
	}
	public List getMapColumn() {
		String hql = "from MapColumn r where r.status ='0'";
		List result = this.list(hql);
		return result;
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

	

	@Override
	public List<DicItem> getDicByPid(Long dicPid) {
		String hql = "from DicItem di where di.parentId='" + dicPid + "'";
		return list(hql);
	}

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

	public StringBuffer getitem(Long temid, Long modid, String tab) {
		StringBuffer selectitme = new StringBuffer();
		List repfileL = getrepfileL(temid, modid);
		for (int j = 0; j < repfileL.size(); j++) {
			Object[] objval = (Object[]) repfileL.get(j);
			selectitme.append(tab + "." + (String) objval[3] + "  as  "
					+ (String) objval[4] + ",");
		}
		return selectitme;
	}

	public long getTotalRecord(String sql, String totalColumn) {
		List list = getListOfMapFromSQL(sql);
		if (list.size() <= 0) {
			return 0;
		}
		Map totalMap = (Map) list.get(0);
		Integer total = Integer.valueOf(String.valueOf(
				totalMap.get(totalColumn)).trim());		
		if (total == null) {
			return 0;
		}
		return total.longValue();
	}

	public List getrepfileL(Long reportid, Long modeid) {

		String sql = "select pkid,TEMPLATE_ID as temid,MODEL_ID as modeid,MODEL_TARGET as modeltarget,TEMPLATE_TARGET as temtarget from TEMPLATE_MODEL  where TEMPLATE_ID="
				+ reportid + " and MODEL_ID=" + modeid;
		Object[][] scalaries = new Object[][] { { "pkid", Hibernate.LONG },
				{ "temid", Hibernate.LONG }, { "modeid", Hibernate.LONG },
				{ "modeltarget", Hibernate.STRING },
				{ "temtarget", Hibernate.STRING } };
		return list(sql, null, scalaries, null);
	}
	@Override
	public int delreport(String pkid,String reportId, String date,String organId) {
		    int a=2;
			//String rdfTableName = formulaDAO.getPhyTableNameByTemp(reportId, date);
		    String rdfTableName = getPhyTableNameByTemp(reportId);
			log.debug("rdfTableName:"+rdfTableName);
			String []strdate =date.split("-");
	    	String subdate=strdate[0]+strdate[1]+"00";
			String sql =null ;
			rdfTableName=rdfTableName.replaceAll("\\{M\\}",date.substring(4,6));
			rdfTableName=rdfTableName.replaceAll("\\{Y\\}",date.substring(0,4));
			rdfTableName=rdfTableName.replaceAll("\\{D\\}",date.substring(6,8));
			if(pkid=="0" || "0".equals(pkid)){
				//sql="delete from "+rdfTableName+" t where t.organ_id="+organId;
				sql="delete from "+rdfTableName+" t where t.REPORT_DATE="+subdate;
			}else{
				sql="delete from "+rdfTableName+" t where t.pkid="+pkid;
			}
			 
			if(sql!=null){
				int result=jdbcUpdate(sql, null);
					if(result>0){
						a=1;
					}
			}
		return a;
	}
	Properties sepTableProTemp = null;
	public String getPhyTableNameByTemp(String reportid) {
		String phyTableName=null;
		String sql = "select def_char ttlab from code_rep_config t where t.report_Id = "+reportid+" and t.fun_Id = 33 ";
		List resultL = list(sql, null, new Object[][] { { "ttlab",Hibernate.STRING } });
		for (int i = 0; i < resultL.size(); i++) {
			phyTableName=resultL.get(0).toString();
		}
		return phyTableName;
	}
	
	
	
	private void initSepTableProForTemp() {
		sepTableProTemp = new Properties();
		List repCfgList = getReportConfigsByFunc(new Long(34));
		if (repCfgList == null){
			return;
		}
		ReportConfig rc = null;
		for (int i = 0 ; i < repCfgList.size() ; i++){
			rc = (ReportConfig)repCfgList.get(i);
			sepTableProTemp.put(rc.getReportId().toString(),rc.getDefChar());
		}
	}
	
	public List getReportConfigsByFunc(Long funcId) {
		String hql = "from ReportConfig t where t.funId = " + funcId
		+ " order by t.idxId,t.funId";
		Map map = new HashMap();
		return list(hql,map);
	}

	
	/**
	 * 查得模板下一条数据  根据 pkid 添加映射列
	 * @param reportId
	 * @param organId
	 * @param reportDate
	 * @param page
	 * @param idxid
	 * @param mapC
	 * @return
	 */
	
	public PaginatedListHelper editReportDataByPageMapColumn(String cstatus,int isadmin,List resultablename,Long reportId, String organId,
			String reportDate,Page page,int idxid,String levelFlag,String pkid,Map mapC) {
			String lab = "";
			StringBuffer[] sbs=null;
			//isadmin 为2时，为超级管理员或者isadmin为3 ,即不是管理员，但是也能看  但是如果isadmin为0，则不能看下级机构的数据
			if(isadmin==2||isadmin==3){
			/*******青海客户要求，如果是超级管理员的话，可以看所有机构，而以下联社的本级机构只能看本级机构的数据，不能看下级机构，所以注释掉，以后需要，可以再放开*****/
			List labL = getOrganidx(organId, idxid);
			if (labL.size() > 0) {
				lab = (String) labL.get(0);
			}
			//获得sql语句
			 	sbs=getModelSqlMapColumn(resultablename,reportId,reportDate,lab,levelFlag,isadmin,cstatus,pkid,mapC);
			}else{
				sbs=getModelSqlMapColumn(resultablename,reportId,reportDate,organId,levelFlag,isadmin,cstatus,pkid,mapC);
			}
			//查询包含子机构数据
			StringBuffer sql = sbs[0];
			StringBuffer totalsql = sbs[1];
			int totalRecord = (int) getTotalRecord(totalsql.toString());
			page.setTotalRecord(totalRecord);
			//page.setRecordNo((page.getPageNo()-1)*page.getPageSize());
			page.setPageNo(1);
			page.setRecordNo(0);
			String sql1 = DBDialect.sqlPage(sql.toString(),page.getRecordNo(),page.getPageSize());
			List list=getListOfMapFromSQL(sql1);
			return new PaginatedListHelper(list, page,sbs[2].toString());
		}
	/**
	 * 组织sql语句 ,添加字段映射
	 * @return
	 */
	//private StringBuffer[] getModelSql(List <ReportConfig> resultablename,Long reportId,String date,String lab,String levelFlag){
	private StringBuffer[] getModelSqlMapColumn(List <ReportConfig> resultablename,Long reportId,String date,String organId,String levelFlag ,int isadmin,String cstatus,String pkid,Map mapC){
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
		
		 	  tableitem.append(" and "+maintab+".pkid="+pkid );//ͨ��pkid ��ѯ��������
		  selectitem.append(itme+selectitem2.substring(0,selectitem2.lastIndexOf(","))+tableitem); 
		  totalbu.append(tableitem);
		  sbs[0]=selectitem;
		  sbs[1]=totalbu;
		  sbs[2]=new StringBuffer(rtable);
		return sbs;
	}	

	/**
	 * 根据模板，关联查出模板下报表的数据  添加传参Map字段映射
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
	
	public PaginatedListHelper getReportDataByPageAll1(String cstatus,int isadmin,List resultablename,Long reportId, String organId,
			String reportDate,Page page,int idxid,String levelFlag,String zhi1,String targetField1,String zhi2,String targetField2,String zhi3,String targetField3,Map mapC) {
			String lab = "";
			StringBuffer[] sbs=null;
			//isadmin 为2时，为超级管理员或者isadmin为3 ,即不是管理员，但是也能看  但是如果isadmin为0，则不能看下级机构的数据
			if(isadmin==2||isadmin==3){
			/*******青海客户要求，如果是超级管理员的话，可以看所有机构，而以下联社的本级机构只能看本级机构的数据，不能看下级机构，所以注释掉，以后需要，可以再放开*****/
			List labL = getOrganidx(organId, idxid);
			if (labL.size() > 0) {
				lab = (String) labL.get(0);
			}
			 	sbs=getModelSql1(resultablename,reportId,reportDate,lab,levelFlag,isadmin,cstatus, zhi1,targetField1, zhi2,targetField2, zhi3,targetField3,mapC);
			}else{
				sbs=getModelSql1(resultablename,reportId,reportDate,organId,levelFlag,isadmin,cstatus, zhi1,targetField1, zhi2,targetField2, zhi3,targetField3,mapC);
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
	private StringBuffer[] getModelSql1(List <ReportConfig> resultablename,Long reportId,String date,String organId,String levelFlag ,int isadmin,String cstatus, String zhi1,String targetField1,String zhi2,String targetField2,String zhi3,String targetField3,Map mapC){
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
			  tablename=tablename.replaceAll("\\{M\\}",date.substring(4,6));
			  tablename=tablename.replaceAll("\\{Y\\}",date.substring(0,4));
			  tablename=tablename.replaceAll("\\{D\\}",date.substring(6,8));
			  //1说明该表是主表
			  if("1".equals((String)objmval[4])){
				  for(ReportConfig rc:resultablename){
						 if( rc.getReportId().toString().equals(modid+"") ){
							 rtable=rc.getDefChar();
							 rtable=rtable.replaceAll("\\{Y\\}",date.substring(0,4));
							 rtable=rtable.replaceAll("\\{M\\}",date.substring(4,6));
							 rtable=rtable.replaceAll("\\{D\\}",date.substring(6,8));
						 }
				  }
				  tableitem.append( " from "+tablename+" "+tabl+" ");
				  maintab=tabl;
				  mainrepid=String.valueOf(modid);
			  }else{
				  tableitem.append(" left outer join  "+tablename+" "+tabl+" on "+whecondition);
			  }
			  selectitem2.append(getitem(temid,modid,tabl)); // o-o
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
		 if( !"0".equals( targetField1 ) && targetField1 !=null && !"".equals( targetField1 ) ){
		 	  tableitem.append(" and "+maintab+"."+targetField1+" like '%"+zhi1+"%'" );//把搜索得到的只组装到查询语句里
		 }
		 if( !"0".equals( targetField2 ) && targetField2 !=null && !"".equals( targetField2 ) ){
		 	  tableitem.append(" and "+maintab+"."+targetField2+" like '%"+zhi2+"%'" );//把搜索得到的只组装到查询语句里
		 }
		 if( !"0".equals( targetField3 ) && targetField3 !=null && !"".equals( targetField3 ) ){
		 	  tableitem.append(" and "+maintab+"."+targetField3+" like '%"+zhi3+"%'" );//把搜索得到的只组装到查询语句里
		 }
		     selectitem.append(itme+selectitem2.substring(0,selectitem2.lastIndexOf(","))+tableitem); 
		  totalbu.append(tableitem);
		  sbs[0]=selectitem;
		  sbs[1]=totalbu;
		  sbs[2]=new StringBuffer(rtable);
		return sbs;
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
			String reportDate,Page page,int idxid,String levelFlag,String zhi1,String targetField1,String zhi2,String targetField2,String zhi3,String targetField3,Map mapC) {
			String lab = "";
			StringBuffer[] sbs=null;
			//isadmin 为2时，为超级管理员或者isadmin为3 ,即不是管理员，但是也能看  但是如果isadmin为0，则不能看下级机构的数据
			if(isadmin==2||isadmin==3){
			/*******青海客户要求，如果是超级管理员的话，可以看所有机构，而以下联社的本级机构只能看本级机构的数据，不能看下级机构，所以注释掉，以后需要，可以再放开*****/
			List labL = getOrganidx(organId, idxid);
			if (labL.size() > 0) {
				lab = (String) labL.get(0);
			}
			 	sbs=getModelSqlMapColumn(resultablename,reportId,reportDate,lab,levelFlag,isadmin,cstatus,zhi1,targetField1,zhi2,targetField2,zhi3,targetField3,mapC);
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
	private StringBuffer[] getModelSqlMapColumn(List <ReportConfig> resultablename,Long reportId,String date,String organId,String levelFlag ,int isadmin,String cstatus,String zhi1,String targetField1,String zhi2,String targetField2,String zhi3,String targetField3,Map mapC){
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
	
	public int commitDataStatus(StatusForm statusForm) {
		saveObject(statusForm);
		return 0;
	}
	public int getDataStatus(StatusForm statusForm) {
		String sql ="select count(1) as num from CODE_REP_STATUS where REPORT_ID ='"+
				statusForm.getReportId()+"' and ORGAN_ID = '"+statusForm.getOrganId()+"' and FREQUENCY ='"+
				statusForm.getFrequency().substring(0, 6) +"00'";
		Object[][] scalaries = { { "num", Hibernate.INTEGER} };
		List result = list(sql, null, scalaries, null);
		return (Integer) result.get(0);
	}
	public int unLockDataStatus(StatusForm statusForm, String isLock) {
		String sql = "UPDATE CODE_REP_STATUS SET ISLOCK = '"+isLock+"' WHERE REPORT_ID ='"+statusForm.getReportId()+"' AND ORGAN_ID = '"+statusForm.getOrganId()+"' AND FREQUENCY ='"+statusForm.getFrequency().substring(0, 6)+"00'";
		return jdbcUpdate(sql, null);
	}

	@Override
	public Long getReportByModid(Report report) {
		String sql ="select t.pkid as pkid  from code_rep_report t where  t.report_type in(select pkid from code_rep_types t where  t.showlevel=1) and t.name='"+report.getName()+"'";
		Object[][] scalaries = { { "pkid", Hibernate.LONG} };
		List result = list(sql, null, scalaries, null);
		Object[] o = null;
		if(result!=null && result.size()>0) {
			 o= new Object[]{result.get(0)};
		}
		return Long.parseLong(o[0].toString());
	}
	
	public List getReportOrgTypes(Long reportId) {
		String hql = "from ReportOrgType t where t.reportId = " + reportId;
		return list(hql);
	}
	
	public List<ReportTarget> getReportTargets(Long reportId) {
		String hql = "from ReportTarget t where t.status <> '"
				+ Constants.STATUS_DEL + "' and t.reportId = " + reportId
				+ " order by t.targetOrder";
		Map map = new HashMap();
		return list(hql, map);
	}
	
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
			logger.error("查询错误:", e);
		} finally {
			if(rs != null){
				try {
					rs.close();
				} catch (SQLException e) { }
			}
			if(conn != null){
				try {
					conn.close();
				} catch (SQLException e) {	}
			}
		}
		return result;
	}
	
	public int insertDataF( String sql){
		return insertDataF(sql, null);
	}
	
	protected int insertDataF(final String sql, final Object[] values) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				PreparedStatement ps = session.connection().prepareStatement(
						sql);
				setPreparedStatementParameters(ps, values);
				int aa=ps.executeUpdate();
				ps.close();
				return new Integer(aa);
			}
		};
		return ((Integer) getHibernateTemplate().execute(callback)).intValue();
	}
	
	public List<?> getdefChar(Long reportid, Long funid) {
		String hql0="from TemplateModel  where templateId='"+reportid+"'";
		List<TemplateModel> list = list(hql0);
		reportid = Long.parseLong(list.get(0).getModelid().toString());
		
		String hql = "from ReportConfig t where t.reportId = " + reportid
				+ " and t.funId = "+ funid;
		Map map = new HashMap();
		return list(hql, map);
	}
	
	public List getReportRuleByReportId(String reportid){
		String sql="select {r.*} from code_rep_rule r where r.model_id=(select model_id from template_model  where template_id='"+reportid+"' and rownum=1)";
		
		return this.list(sql,new Object[][]{{"r",ReportRule.class}},null);
	}
	
	public int deleteReportResult(String  rulecode ,String cstatus ,String tablename ){
		 String sql="delete from "+tablename+" where RULECODE in ("+rulecode+") and CSTATUS='"+cstatus+"'";
		 return jdbcUpdate(sql,null);
	 }
	
	public Object[] checkData(String rulecode,String date){
		String sql="{CALL P_M_DORULE(?,?)}";
		int[] arr= {1};
		
		return jdbcCall(sql, new Object[]{rulecode,date},null, arr, null);
	}

	@Override
	public String getModelId(String reportId) {
		String hql="from TemplateModel  where templateId='"+reportId+"' order by modelid";
		List<TemplateModel> list = list(hql);
		reportId = list.get(0).getModelid().toString();
		return reportId;
	}
}
