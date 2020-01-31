package com.krm.ps.model.xlsinit.services.impl;

import java.util.List;

import org.apache.struts.upload.FormFile;

import com.krm.ps.model.xlsinit.dao.XLSInitDAO;
import com.krm.ps.model.xlsinit.services.XLSInitService;
import com.krm.ps.model.xlsinit.util.DataCheck;
import com.krm.ps.model.xlsinit.util.SqlHelp;
import com.krm.ps.model.xlsinit.util.XLSBuild;
import com.krm.ps.model.xlsinit.util.XMLParse;
import com.krm.ps.model.xlsinit.vo.DataSet;
import com.krm.ps.model.xlsinit.vo.TableList;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.util.SysConfig;


public class XLSInitServiceImpl implements XLSInitService{
	
   private XLSInitDAO xlsinitDAO;
   
   public void setXLSInitDAO(XLSInitDAO xlsinitDAO) {
		this.xlsinitDAO = xlsinitDAO;
	}
   
   public void XLSInit(FormFile data,FormFile config,User usr) throws Exception{
	 //读入excel数据，构造数据集
	   DataSet ds = XLSBuild.constructData(data);	   
	 //解析xml配置文件
	   TableList tabLst = XMLParse.constructXML(config);	 
	 //校验数据是否合法
	   if (DataCheck.checkLegal(ds,tabLst.getTableList())){		   
		 //产生数据库sql
		   List sql = SqlHelp.sqlList(ds,tabLst,usr);		   
		 //数据插入数据库
		   try {
			   if ( 'p' == SysConfig.DB || 'o' == SysConfig.DB)
				   xlsinitDAO.XLSInit(sql);
			   else
				   xlsinitDAO.XLSInit(ds, tabLst, usr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new Exception("XLSInit failed!");
		}
	   }
   }

}
