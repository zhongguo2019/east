package com.krm.ps.framework.common.services.impl;



import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import com.krm.ps.framework.common.services.ReportTreeService;
import com.krm.ps.sysmanage.organmanage2.model.OrganNode;
import com.krm.ps.sysmanage.usermanage.bo.Menu;





/**
 * ������ʵ��ƴ ������ �� xml
 * */
public class ReportTreeServiceImpl implements ReportTreeService{
	
	/**
	 * ����xml��ͷ��β ����ɾ�̬��Ա����
	 * 
	 * */
	
	//XML ͷ
	public static String REPORTTREE_HEAD = "<?xml version=\"1.0\" encoding=\"gbk\"?>";
	
	//XML ���ڵ�
	public static String REPORTTREE_ROOT_HEAD = "\n<tree id=\"0\">" ; 
	
	//XMLβ�ڵ�
	public static String REPORTTREE_ROOT_TAIL = "\n</tree>";
	
	
	//ƴͷ���
	/**
	 * 2011-11-30��ʯ��
	 * Ҫ�󱨱���Ĭ��չ����һ��
	 * open="1"
	 */
	public String addReportXmlRoot(String treemiddlexml)
	{
		//��ƴ�� xml���ڵ� �� β�ڵ�
		StringBuffer reportXmlRoot = new StringBuffer("\n\t<item  text=\"���б���\" id=\"t1_ar\" open=\"1\" im0=\"folderClosed.gif\" im1=\"folderOpen.gif\" im2=\"folderClosed.gif\">" +
				"<userdata name=\"isreptypeflag\">reptype</userdata>");
		StringBuffer reportXMLTail = new StringBuffer("\n\t</item>") ;
		reportXmlRoot.append(treemiddlexml).append(reportXMLTail);
		return reportXmlRoot.toString();
	}
	
	
	//ͨ���鴦���Ľ������ƴװxml���ļ�
	public String buildReportTreeMiddle(List reportlist, List rtypelist)
	{
	
		return null;
	}
	
	public String buildReportTreeXML(List reportlist,List rtypelist)
	{
//		ReportTreeDao rtd = new ReportTreeDaoImpl();
		
//		List<Report> reportlist = rtd.getReportList();
//		List<ReportType> rtypelist = rtd.getReportTypeList();
		
		
		StringBuffer reportTree = new StringBuffer();
		reportTree.append(REPORTTREE_HEAD);
		reportTree.append(REPORTTREE_ROOT_HEAD);
		//�õ��м�Ĳ���
		String middletree = buildReportTreeMiddle(reportlist,rtypelist);
		
		//����ƴ��ͷ�ķ��������м�Ĳ���ƴ����
		String headtree = addReportXmlRoot(middletree);
		//ƴ���м������
		reportTree.append(headtree);
		//ƴ��β��
		reportTree.append(REPORTTREE_ROOT_TAIL);
		
		return reportTree.toString();
	}


	public String buildOrganTreetoJson(OrganNode topOrgan, List nodeList) {
		//���ȸ���һ��JSON�ĸ�ʽ��ʽ
		StringBuffer sbJson = new StringBuffer();
		buildJson(topOrgan,sbJson);
		
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("d:/treeJson.txt")));
			bw.write(sbJson.toString());
			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "["+sbJson.toString()+"]";
	}
	
	public void buildJson(OrganNode topOrgan,StringBuffer sb)
	{
		sb.append("{text: '" + topOrgan.getName()+"',id:'"+topOrgan.getOrganCode()+"'");
		if (topOrgan.getChildren()!= null && topOrgan.getChildren().size() > 0){
			sb.append(", children:[");
			for (int j = 0; j < topOrgan.getChildren().size(); j++) {
				buildJson((OrganNode)(topOrgan.getChildren().get(j)), sb);
				if (j < topOrgan.getChildren().size() - 1)
					sb.append(",");
			}
		}
		if (topOrgan.getChildren()!= null && topOrgan.getChildren().size() > 0)
			sb.append("]}");
		else
			sb.append(",leaf:true}");
	}


	public String buildReportTreetoJson(List reportTypeList, List reportList) {
		//�������Ľṹ�Ƚϼ�,ֻ�����������ͣ�������
 
		return null;
	}


	public String buildMenuTreeXML(List menuList) {
		StringBuffer menuTree = new StringBuffer();
		menuTree.append(REPORTTREE_HEAD);
		menuTree.append(REPORTTREE_ROOT_HEAD);
		//�õ��м�Ĳ���
		String middletree = buildMenuTreeMiddle(menuList);
		
		//����ƴ��ͷ�ķ��������м�Ĳ���ƴ����
		String headtree = addMenuXmlRoot(middletree);
		//ƴ���м������
		menuTree.append(headtree);
		//ƴ��β��
		menuTree.append(REPORTTREE_ROOT_TAIL);
		
		return menuTree.toString();
	}


	private String addMenuXmlRoot(String middletree) {
		//��ƴ�� xml���ڵ� �� β�ڵ�
		StringBuffer reportXmlRoot = new StringBuffer("\n\t<item  text=\"���в˵�\" id=\"t1_ar\" im0=\"folderClosed.gif\" im1=\"folderOpen.gif\" im2=\"folderClosed.gif\">" +
				"<userdata name=\"isreptypeflag\">reptype</userdata>");
		StringBuffer reportXMLTail = new StringBuffer("\n\t</item>") ;
		reportXmlRoot.append(middletree).append(reportXMLTail);
		return reportXmlRoot.toString();
	}


	/**
	 * ƴװxml��
	 * @param menuList	����˵�����
	 * @return
	 */
	private String buildMenuTreeMiddle(List menuList) {
		StringBuffer treeXML = new StringBuffer();
		for(int i=0; i<menuList.size(); i++)
		{
			int a=0;
			//��ȡ����˵�
			Menu pmenu = (Menu) menuList.get(i);
			treeXML.append("\n\t\t<item  text=\""+pmenu.getName()+"\" id=\""+pmenu.getId()+"\" im0=\"folderClosed.gif\" im1=\"folderOpen.gif\" im2=\"folderClosed.gif\">" +
					"<userdata name=\"isreptypeflag\">parent</userdata> ");
			//��ȡ����˵����Ӳ˵�����
			List subList = pmenu.getSubMenus();
			for(int j=0; j<subList.size(); j++)
			{
				Menu menu = (Menu) subList.get(j);
				a++;
				String url = null;
				try {
					//��url���б���
					url = URLEncoder.encode(menu.getResource(), "GBK");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				treeXML.append("\n\t\t\t<item  text=\""+menu.getName()+"\" id=\""+menu.getId()+"\" im0=\"notebook.gif\" im1=\"notebook.gif\" im2=\"notebook.gif\">" +
							"<userdata name=\"isreptypeflag\">"+url+"</userdata></item>");
			}
			System.out.println("�˵����� =\"" +pmenu.getId() +"\"===" + pmenu.getName() +"===="+ a );
			treeXML.append("\n\t\t</item>");
		}
		return treeXML.toString();
	}
}
