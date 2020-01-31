package com.krm.ps.model.xlsinit.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.krm.ps.model.xlsinit.constants.DataConst;
import com.krm.ps.model.xlsinit.constants.TypeConfig;
import com.krm.ps.model.xlsinit.vo.DataSet;
import com.krm.ps.model.xlsinit.vo.RowSet;
import com.krm.ps.model.xlsinit.vo.Table;
import com.krm.ps.model.xlsinit.vo.TableField;
import com.krm.ps.model.xlsinit.vo.TableList;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.util.DBDialect;
import com.krm.ps.util.SysConfig;


public class SqlHelp {
	
	public static final String FORMULA_TABLE = "code_formula";
	public static final String FORMAT_TABLE = "rep_format";
	public static final String FORMAT_BLOB_FIELD = "report_format";
	public static final String FORMULA_BLOB_FIELD1 = "formula";
	public static final String FORMULA_BLOB_FIELD2 = "explain";
	
	public static List sqlList(DataSet dt,TableList tbl,User usr){
		List lst = new ArrayList();
		
		List data = dt.getXlsData();
		List tab = tbl.getTableList();
		StringBuffer sb = new StringBuffer();
		StringBuffer fsb = new StringBuffer();
		StringBuffer sql = new StringBuffer();
		
		for (int i=0;i<tab.size();i++){
			Table t = (Table)tab.get(i);
			for (int j=0;j<data.size();j++){
				sql = new StringBuffer();
				RowSet r = (RowSet)data.get(j);
				Map rd = r.getRowData();
				sb = new StringBuffer("insert into ");
				sb.append(t.getTableName()).append("(");
				fsb = new StringBuffer("values(");
				//字段名称
				List f = t.getField();
				for (int k=0;k<f.size();k++){
					 TableField td = (TableField)f.get(k);
					 
					//2007.4.10 兼容SqlServer数据库
					 if ('s'==SysConfig.DB){
						 if (!td.is_pk()){
							 sb.append(td.get_name()).append(",");
						 }
					 }else{
						 sb.append(td.get_name()).append(",");
					 }
				     String fv = "";
				     if (td.is_pk()){
				    	 if ('s'!=SysConfig.DB){				    						    	
				    		 fv = DBDialect.genSequence(td.get_seq()) + ",";
				    	 }
				     }else if (!"".equals(td.get_constval())&&td.get_constval()!=null){				    	 
				    	 fv = getConst(td.get_constval(),td,usr);
				     }else if  (td.is_drop()&&rd.get(td.get_map())!=null){				    	
				    	 fv = (String)rd.get(td.get_map());
				    	 fv = wrapType(fv.substring(fv.indexOf("&")+1),td);
				     }else if(rd.get(td.get_map())!=null) {				    	 
				    	 fv = (String)rd.get(td.get_map());				    	 
				    	 fv = wrapType(fv,td);
				     }else{
				    	 fv = "null,";
				     }
					 fsb.append(fv);
				}
				sql.append(sb.substring(0,sb.toString().length() - 1))
				.append(") ").append(fsb.substring(0,fsb.toString().length() - 1))
				.append(")");				
				lst.add(sql.toString());
			}								
		}
		
		return lst;
	}
	public static String getConst(String constval,TableField tf,User usr){
		String value = "";
		if (constval.equals(DataConst.USER_ID))
			value = String.valueOf(usr.getPkid());
		if (constval.equals(DataConst.USER_NAME))
			value = usr.getName();
		if (constval.equals(DataConst.USER_LOGNAME))
			value = usr.getLogonName();
		if (constval.equals(DataConst.USER_ORGAN))
			value = usr.getOrganId();
		if (constval.equals(DataConst.USER_ROLE_TYPE))
			value = String.valueOf(usr.getRoleType());
		if (constval.equals(DataConst.USER_ROLE_NAME))
			value = usr.getRoleName();
		if (constval.equals(DataConst.CURRENT_DATE)){
			long t = System.currentTimeMillis();
			Date d = new Date(t);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			value = formatter.format(d);
		}
		if (constval.equals(DataConst.CURRENT_DATETIME)){
			long t = System.currentTimeMillis();
			Date d = new Date(t);
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			value = formatter.format(d);
		}
		value = wrapType(value,tf);		
		return value;
	}
	public static String wrapType(String val,TableField tf){
		String value = "";
		int type = tf.get_type();

		if (type == TypeConfig.INTEGER || type == TypeConfig.DOUBLE) {
			value = val + ",";
		} else {
			value = "'" + val + "'" + ",";
		}
		return value;
	}
}
