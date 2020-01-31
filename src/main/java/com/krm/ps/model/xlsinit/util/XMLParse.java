package com.krm.ps.model.xlsinit.util;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.struts.upload.FormFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.krm.ps.model.xlsinit.vo.Table;
import com.krm.ps.model.xlsinit.vo.TableField;
import com.krm.ps.model.xlsinit.vo.TableList;

public class XMLParse {
	public static TableList constructXML(FormFile in){
		List data = new ArrayList();
		//为解析XML作准备，创建DocumentBuilderFactory实例,指定DocumentBuilder
	    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	    try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(in.getInputStream());
			
			Element root = doc.getDocumentElement();
		    NodeList nodeList = root.getElementsByTagName("table");
		    
		    Element tableNode, itemsNode,item ;
		    NodeList tableNameNode = null;
		    NodeList tableItemNode = null;		    
		    NodeList itemNodeList;
		    Node tableName1, tableName2, item1,item2;
		    String tableName = "";
		    String type, pk, seq, drop, constval,map,name;		    
		    
		    for (int i = 0; i < nodeList.getLength(); i++) {
		    	tableNode = (Element) nodeList.item(i);
		    	List lst = new ArrayList();
	            TableField fld = null;
	            //解析tableName
		        tableNameNode = tableNode.getElementsByTagName("tablename");
		        if (tableNameNode.getLength() == 1) {
		          tableName1 = tableNameNode.item(0);
		          tableName2 = tableName1.getFirstChild();
		          tableName = tableName2.getNodeValue().trim();		          
		        }
		      //解析tableItem
		        tableItemNode = tableNode.getElementsByTagName("tableItem");
		        if (tableItemNode.getLength() == 1) {
		            itemsNode = (Element) tableItemNode.item(0);
		            itemNodeList = itemsNode.getElementsByTagName("item");		            		            
		            for (int j = 0; j < itemNodeList.getLength(); j++) {		            	
		            	  //得到字段类型
		              item = (Element) itemNodeList.item(j);
		              type = item.getAttribute("type");		              
		              //是否主键			              
		              pk = item.getAttribute("pk");
		            //是否下拉
		              drop = item.getAttribute("drop");
		            //常量
		              constval = item.getAttribute("const");
		            //映射excel
		              map = item.getAttribute("map");
		            //序列
		              seq = item.getAttribute("seq");
		              
		              //得到item的value
		              item1 = itemNodeList.item(j);
		              item2 = item1.getFirstChild();
		              name = item2.getNodeValue();	
		              fld = new TableField(name,Integer.parseInt(type),"true".equals(pk)?true:false,seq,"true".equals(drop)?true:false,constval,map);
		              lst.add(fld);
		            }
		          }
		        Table tab = new Table(tableName,lst);
		        data.add(tab);
		    }
		    
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new TableList(data);
	}
}
