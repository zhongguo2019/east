package com.krm.ps.framework.common.services;

import java.util.List;

import com.krm.ps.sysmanage.organmanage2.model.OrganNode;


public interface ReportTreeService {
	
	/**
	 * 拼头结点
	 * */
	public String addReportXmlRoot(String treemiddlexml);
	
	/**
	 * 通过查处来的结果集来拼装xml树文件
	 * */
	public String buildReportTreeMiddle(List reportlist, List rtypelist);
	
	/**
	 * 整合菜单树字符串
	 * */
	public String buildReportTreeXML(List reportlist,List rtypelist);
	
	/**
	 * 拼接机构树，拼成特定的json格式
	 * */
	public String buildOrganTreetoJson(OrganNode topOrgan,List nodeList);
	
	/**
	 * 拼接报表树，拼成Ext识别的Json格式
	 * */
	public String buildReportTreetoJson(List reportTypeList,List reportList);

	/**
	 *	拼装菜单树字符串
	 * 
	 **/
	public String buildMenuTreeXML(List menuList);
}
