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
 * 此类中实现拼 报表树 的 xml
 * */
public class ReportTreeServiceImpl implements ReportTreeService{
	
	/**
	 * 定义xml的头和尾 定义成静态成员代码
	 * 
	 * */
	
	//XML 头
	public static String REPORTTREE_HEAD = "<?xml version=\"1.0\" encoding=\"gbk\"?>";
	
	//XML 根节点
	public static String REPORTTREE_ROOT_HEAD = "\n<tree id=\"0\">" ; 
	
	//XML尾节点
	public static String REPORTTREE_ROOT_TAIL = "\n</tree>";
	
	
	//拼头结点
	/**
	 * 2011-11-30周石磊
	 * 要求报表树默认展开第一层
	 * open="1"
	 */
	public String addReportXmlRoot(String treemiddlexml)
	{
		//先拼出 xml根节点 和 尾节点
		StringBuffer reportXmlRoot = new StringBuffer("\n\t<item  text=\"所有报表\" id=\"t1_ar\" open=\"1\" im0=\"folderClosed.gif\" im1=\"folderOpen.gif\" im2=\"folderClosed.gif\">" +
				"<userdata name=\"isreptypeflag\">reptype</userdata>");
		StringBuffer reportXMLTail = new StringBuffer("\n\t</item>") ;
		reportXmlRoot.append(treemiddlexml).append(reportXMLTail);
		return reportXmlRoot.toString();
	}
	
	
	//通过查处来的结果集来拼装xml树文件
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
		//得到中间的部分
		String middletree = buildReportTreeMiddle(reportlist,rtypelist);
		
		//调用拼接头的方法，将中间的部分拼接上
		String headtree = addReportXmlRoot(middletree);
		//拼上中间的内容
		reportTree.append(headtree);
		//拼接尾部
		reportTree.append(REPORTTREE_ROOT_TAIL);
		
		return reportTree.toString();
	}


	public String buildOrganTreetoJson(OrganNode topOrgan, List nodeList) {
		//首先给出一个JSON的格式样式
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
		//报表树的结构比较简单,只有两级，类型，树机构
 
		return null;
	}


	public String buildMenuTreeXML(List menuList) {
		StringBuffer menuTree = new StringBuffer();
		menuTree.append(REPORTTREE_HEAD);
		menuTree.append(REPORTTREE_ROOT_HEAD);
		//得到中间的部分
		String middletree = buildMenuTreeMiddle(menuList);
		
		//调用拼接头的方法，将中间的部分拼接上
		String headtree = addMenuXmlRoot(middletree);
		//拼上中间的内容
		menuTree.append(headtree);
		//拼接尾部
		menuTree.append(REPORTTREE_ROOT_TAIL);
		
		return menuTree.toString();
	}


	private String addMenuXmlRoot(String middletree) {
		//先拼出 xml根节点 和 尾节点
		StringBuffer reportXmlRoot = new StringBuffer("\n\t<item  text=\"所有菜单\" id=\"t1_ar\" im0=\"folderClosed.gif\" im1=\"folderOpen.gif\" im2=\"folderClosed.gif\">" +
				"<userdata name=\"isreptypeflag\">reptype</userdata>");
		StringBuffer reportXMLTail = new StringBuffer("\n\t</item>") ;
		reportXmlRoot.append(middletree).append(reportXMLTail);
		return reportXmlRoot.toString();
	}


	/**
	 * 拼装xml树
	 * @param menuList	父类菜单集合
	 * @return
	 */
	private String buildMenuTreeMiddle(List menuList) {
		StringBuffer treeXML = new StringBuffer();
		for(int i=0; i<menuList.size(); i++)
		{
			int a=0;
			//获取父类菜单
			Menu pmenu = (Menu) menuList.get(i);
			treeXML.append("\n\t\t<item  text=\""+pmenu.getName()+"\" id=\""+pmenu.getId()+"\" im0=\"folderClosed.gif\" im1=\"folderOpen.gif\" im2=\"folderClosed.gif\">" +
					"<userdata name=\"isreptypeflag\">parent</userdata> ");
			//获取父类菜单的子菜单集合
			List subList = pmenu.getSubMenus();
			for(int j=0; j<subList.size(); j++)
			{
				Menu menu = (Menu) subList.get(j);
				a++;
				String url = null;
				try {
					//对url进行编码
					url = URLEncoder.encode(menu.getResource(), "GBK");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				treeXML.append("\n\t\t\t<item  text=\""+menu.getName()+"\" id=\""+menu.getId()+"\" im0=\"notebook.gif\" im1=\"notebook.gif\" im2=\"notebook.gif\">" +
							"<userdata name=\"isreptypeflag\">"+url+"</userdata></item>");
			}
			System.out.println("菜单类型 =\"" +pmenu.getId() +"\"===" + pmenu.getName() +"===="+ a );
			treeXML.append("\n\t\t</item>");
		}
		return treeXML.toString();
	}
}
