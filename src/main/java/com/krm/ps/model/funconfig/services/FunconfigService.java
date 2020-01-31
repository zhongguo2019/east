package com.krm.ps.model.funconfig.services;

import java.util.List;

public interface FunconfigService {
	
	public List selectfunconfig();
	
	
	public void savefunconfig(String funkey,String funvalue,String fundes,int funtype,String ext1);
	
	
	public void updatefunconfig(String funkey,String funvalue,String fundes,int funtype,String ext1);
	
	
	public void deletefunconfig(String funkey);
	
	
	public List selectkey();
	
	public List selectfunconfig1(String funkey);
	
	public String selectvalue(String funkey);
}
