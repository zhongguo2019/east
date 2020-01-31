package com.krm.ps.framework.common.services;

import java.util.List;

import com.krm.ps.sysmanage.organmanage2.model.OrganNode;


public interface ReportTreeService {
	
	/**
	 * ƴͷ���
	 * */
	public String addReportXmlRoot(String treemiddlexml);
	
	/**
	 * ͨ���鴦���Ľ������ƴװxml���ļ�
	 * */
	public String buildReportTreeMiddle(List reportlist, List rtypelist);
	
	/**
	 * ���ϲ˵����ַ���
	 * */
	public String buildReportTreeXML(List reportlist,List rtypelist);
	
	/**
	 * ƴ�ӻ�������ƴ���ض���json��ʽ
	 * */
	public String buildOrganTreetoJson(OrganNode topOrgan,List nodeList);
	
	/**
	 * ƴ�ӱ�������ƴ��Extʶ���Json��ʽ
	 * */
	public String buildReportTreetoJson(List reportTypeList,List reportList);

	/**
	 *	ƴװ�˵����ַ���
	 * 
	 **/
	public String buildMenuTreeXML(List menuList);
}
