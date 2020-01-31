package com.krm.ps.util;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author
 */
public class SessionAttribute
{
	//###################批量审核用############################
	//报表效验(表间/表内)
	public static final String CHECK_REPORT = "checkReports";
	//效验报表频度
	public static final String CHECK_FREQ = "checkFreq";
	//效验报表类型
	public static final String CHECK_TYPE = "checkTypes";
	//批量效验,json数据结构
	public static final String CHECK_REPORT_JSON_STRUTS = "checkJson";
	//效验机构级别
	public static final String CHECK_ORGAN_LEVEL = "organLevel";
	//########################################################
	
	//####################数据比对用############################
	public static final String MERGE_DATE = "mergeDate";
	public static final String MERGE_ORGAN = "mergeOrgan";
	public static final String MERGE_REPORT = "mergeReport";
	//########################################################
	
}
