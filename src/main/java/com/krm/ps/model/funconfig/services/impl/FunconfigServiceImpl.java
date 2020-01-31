package com.krm.ps.model.funconfig.services.impl;

import java.util.List;

import com.krm.ps.model.funconfig.dao.FunconfigDao;
import com.krm.ps.model.funconfig.services.FunconfigService;

public class FunconfigServiceImpl implements FunconfigService{

	private FunconfigDao funconfigDao;
	
	

	public FunconfigDao getFunconfigDao() {
		return funconfigDao;
	}

	public void setFunconfigDao(FunconfigDao funconfigDao) {
		this.funconfigDao = funconfigDao;
	}

	@Override
	public List selectfunconfig() {
		
		return funconfigDao.selectfunconfig();
	}

	@Override
	public void savefunconfig(String funkey, String funvalue, String fundes,
			int funtype, String ext1) {
		
		funconfigDao.savefunconfig(funkey, funvalue, fundes, funtype, ext1);
	}

	@Override
	public void updatefunconfig(String funkey, String funvalue, String fundes,
			int funtype, String ext1) {
		funconfigDao.updatefunconfig(funkey, funvalue, fundes, funtype, ext1);
		
	}

	@Override
	public void deletefunconfig(String funkey) {
		funconfigDao.deletefunconfig(funkey);
		
	}

	@Override
	public List selectkey() {
		return funconfigDao.selectkey();
		 
	}

	@Override
	public List selectfunconfig1(String funkey) {
		
		return funconfigDao.selectfunconfig1(funkey);
	}

	@Override
	public String selectvalue(String funkey) {
		
		return funconfigDao.selectvalue(funkey);
	}

}
