package com.krm.ps.model.funconfig.dao;

import java.util.List;

import com.krm.ps.framework.dao.DAO;

public interface FunconfigDao extends DAO{

	public List selectfunconfig();
	
	
	public void savefunconfig(String funkey,String funvalue,String fundes,int funtype,String ext1);
	
	
	public void updatefunconfig(String funkey,String funvalue,String fundes,int funtype,String ext1);
	
	
	public void deletefunconfig(String funkey);
	
	
	public List selectkey();
	
	
	public List selectfunconfig1(String funkey);
	
	public String selectvalue(String funkey);
}
