package com.krm.ps.sysmanage.reportdefine.dbparam;

import com.krm.ps.util.Constant;


/**
 * 
 * <p>Title: 参见对象{@link com.krm.slsint.reportdefine.vo.ReportConfig}，用于对此对象的动态查询</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author
 */
public class ReportConfigDBParam
{
	// 对应表名称
	public final String table_name = "code_rep_config";
	
	public final String funId = "fun_id";
	
	public final String defInt = "def_int";
	
	public final String defChar_S = "def_char" + Constant.DATATYPE_JAVA_STRING_POSTFIX;
	
	public final String  reportId = "report_id";
	
}
