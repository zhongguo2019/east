package com.krm.ps.model.workinstructions.service.impl;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.krm.ps.model.workinstructions.dao.WorkinstructionsDAO;
import com.krm.ps.model.workinstructions.service.WorkinstructionsService;
import com.krm.ps.model.workinstructions.vo.Workaccessory;
import com.krm.ps.model.workinstructions.vo.Workcontext;
import com.krm.ps.model.workinstructions.vo.Workcontext1;
import com.krm.ps.model.workinstructions.vo.Workinstructions;



public class WorkinstructionsServiceImpl implements WorkinstructionsService{

	private WorkinstructionsDAO workinstructionsDAO;

	public WorkinstructionsDAO getWorkinstructionsDAO() {
		return workinstructionsDAO;
	}

	public void setWorkinstructionsDAO(WorkinstructionsDAO workinstructionsDAO) {
		this.workinstructionsDAO = workinstructionsDAO;
	}

	
	public String selectworkinstructiontree() {
		List<Workinstructions> list=workinstructionsDAO.selectworkinstructiontree();
		
		StringBuffer treexml=new StringBuffer();
		//treexml.append("<?xml version='1.0' encoding='utf-8'?>");
		//treexml.append("\n\t\t<tree id='0'>");
		 Document document = DocumentHelper.createDocument();
		 Element tree = document.addElement("tree");
		 tree.addAttribute("id","0");     //�������� id=��0��

		forList(tree,list,0);
		try{
		 OutputFormat format = new OutputFormat("    ", true);
		 format.setEncoding("GBK");
		  StringWriter out = new StringWriter();
		   XMLWriter xmlWriter = new XMLWriter(out, format);
		  
		   xmlWriter.write(document);
		   xmlWriter.flush();
		 
		    String s = out.toString();
		    //System.out.println(s);
		    return s;
		}catch(Exception e){
			 e.printStackTrace();
			 return null;
		}
/*		try{
			 OutputFormat format = OutputFormat.createPrettyPrint();
			              format.setEncoding("GBK");
			              XMLWriter output = new XMLWriter(
			                     new FileWriter( new File("e:/ee.xml")),format);
			                  output.write( document );
			                  output.close();
			                  }
			               catch(IOException e){System.out.println(e.getMessage());}*/
			          
		
			
		
		//treexml.append("\n\t\t</tree>");
		//System.out.println("treexml="+treexml.toString());
		//return treexml.toString();
	}
	

	public void forList( Element tree,List<Workinstructions> itemlist, long parentId) {
		//int a=0;
			for (Workinstructions item : itemlist) {
				
				if ( item.getSuperid() == 0){
					//System.out.println(item.getPkid()+"-----"+a);
					 Element item1=tree.addElement("item");
					 item1.addAttribute("text", item.getName());
					 item1.addAttribute("id", item.getPkid().toString());

					//treexml.append("\n\t\t<item text='"+item.getName()+"' id='a"+item.getPkid()+"' >");
						forList1(item1,itemlist, item.getPkid());
					//treexml.append("\n\t\t</item>");
					//a++;
				}
				
			}
		}
	public void forList1(Element item1,List<Workinstructions> itemlist, long parentId) {
		//StringBuffer treexml=new StringBuffer();
		
		for (Workinstructions item : itemlist) {
			if ( item.getSuperid() != 0&&item.getSuperid()==parentId){
				 Element item2=item1.addElement("item");
				 item2.addAttribute("text",item.getName());
				 item2.addAttribute("id", item.getPkid().toString());
				//treexml.append("\n\t\t<item text='"+item.getName()+"'  id='a"+item.getPkid()+"' >");	
					forList1(item2,itemlist,item.getPkid());
				//treexml.append("</item>");
				
			}
			
		}
		
		
	}

	@Override
	public void deleteid(long pkid) {
		workinstructionsDAO.deleteid(pkid);
		
	}

	@Override
	public List selectworksuperid(long pkid) {
		
		return workinstructionsDAO.selectworksuperid(pkid);
	}

	@Override
	public void insertid(String name,long superid) {
		
		workinstructionsDAO.insertid(name,superid);
	}

	@Override
	public void updateid(long pkid, String name) {
		workinstructionsDAO.updateid(pkid, name);
		
	}

	@Override
	public List<Workcontext> selectcontext(long treeid) {
		
		return workinstructionsDAO.selectcontext(treeid);
	}

	@Override
	public List<Workcontext> selectstatus2() {
		
		return workinstructionsDAO.selectstatus2();
	}

	public void updatecontext(Workcontext w){
		
		workinstructionsDAO.updatecontext(w);
	}
	@Override
	public List selectinfo(long pkid) {
		
		return workinstructionsDAO.selectinfo(pkid);
	}

	@Override
	public void saveinfo(Workcontext w) {
		workinstructionsDAO.saveinfo(w);
		
	}

	@Override
	public void deleteinfo(long pkid) {
		workinstructionsDAO.deleteinfo(pkid);
		
	}

	@Override
	public List<Workcontext> queryinfo(long pkid) {
		
		return workinstructionsDAO.queryinfo(pkid);
	}
	
	public void updatestatus(long pkid, int status,String updatetime){
		workinstructionsDAO.updatestatus(pkid, status,updatetime);
	}

	public void updatestatus1(int status,int status2,String updatetime){
		workinstructionsDAO.updatestatus1(status, status2,updatetime);
	}

	@Override
	public void saveaccessory(Workaccessory w) {
		workinstructionsDAO.saveaccessory(w);
		
	}
	
	public List queryaccessory(long pkid){
		return workinstructionsDAO.queryaccessory(pkid);
	}
	public List queryaccessory1(long pkid){
		return workinstructionsDAO.queryaccessory1(pkid);
	}

	@Override
	public void deleteaccessory(long pkid) {
		workinstructionsDAO.deleteaccessory(pkid);
		
	}
	
	public List<Workcontext1> selectinfo(String title,String context,int pageNo,int pageSize){
		return workinstructionsDAO.selectinfo(title, context, pageNo, pageSize);
	}

	@Override
	public int selectinfonum(String title, String context) {
		
		return workinstructionsDAO.selectinfonum(title, context);
	}

	@Override
	public String selectname(long userid) {
		
		return workinstructionsDAO.selectname(userid);
	}


	
}
