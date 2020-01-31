package com.krm.ps.model.workinstructions.service;

import java.io.IOException;
import java.util.List;

import org.apache.struts.upload.FormFile;
import org.dom4j.Document;

import com.krm.ps.model.workinstructions.vo.Workaccessory;
import com.krm.ps.model.workinstructions.vo.Workcontext;
import com.krm.ps.model.workinstructions.vo.Workcontext1;
import com.krm.ps.model.workinstructions.vo.Workinstructions;



public interface WorkinstructionsService {

	public String selectworkinstructiontree(); 
	 
	public void deleteid(long pkid);
	
	public List selectworksuperid(long pkid);
	
	public void insertid(String name,long superid);
	
	public void updateid(long pkid,String name);
	
	public List<Workcontext> selectcontext(long treeid);
	
	public List<Workcontext> selectstatus2();
	
	public void updatecontext(Workcontext w);
	
	public List selectinfo(long pkid);
	
	public void saveinfo(Workcontext w);
	
	public void deleteinfo(long pkid);
	
	public List<Workcontext> queryinfo(long pkid);
	
	public void updatestatus(long pkid, int status,String updatetime);
	
	public void updatestatus1(int status,int status2,String updatetime);

	
	public void saveaccessory(Workaccessory w);
	
	public List queryaccessory(long pkid);
	public List queryaccessory1(long pkid);
	
	public void deleteaccessory(long pkid);
	
	public List<Workcontext1> selectinfo(String title,String context,int pageNo,int pageSize);
	
	public int selectinfonum(String title,String context);
	
	public String selectname(long userid);
}
