package com.krm.ps.sysmanage.reportdefine.services.impl;

import java.util.List;

import com.krm.ps.sysmanage.reportdefine.dao.StrdReporeWheconditionDAO;
import com.krm.ps.sysmanage.reportdefine.services.StrdReporeWheconditionService;
import com.krm.ps.sysmanage.reportdefine.vo.StrdReportWhecondition;

public class StrdreporeWheconditionServiceImpl implements StrdReporeWheconditionService {
	
	StrdReporeWheconditionDAO  strdReporeWheconditionDAO;
	
	
	

	public String getStrdreporeWhecondition(Long tempid) {
	  StrdReportWhecondition strdReportWhecondition = null ; 
	  StrdReportWhecondition strdReportWhecondition1 = null ;
	  String tableas = null ;
	  List list = strdReporeWheconditionDAO.getStrdreporeWhecondition(tempid);
	  if( list.size()== 0 ){
		return  tableas = "a1" ;
	  }
	  
	  for (int i = 0; i < list.size(); i++) {
		  strdReportWhecondition  =( StrdReportWhecondition )list.get(0);
		  strdReportWhecondition1 =( StrdReportWhecondition )list.get(list.size()-1);
      }
	  String tabless= strdReportWhecondition.getTable();
	  String tables2= strdReportWhecondition1.getTable();
	  int tableindex=Integer.parseInt(tabless.substring(1, tabless.length()));
	 
	  return "a"+(tableindex+1)+"-"+tables2;
	}

	@Override
	public void saveStrdreporeWhecondition( StrdReportWhecondition strdReportWhecondition ) {
		this.strdReporeWheconditionDAO.saveStrdreporeWhecondition(strdReportWhecondition);
		
	}
	

	@Override
	public String getStrdreporeWhecondition(Long tempid, Long modelid) {
		 StrdReportWhecondition reportWhecondition=	this.strdReporeWheconditionDAO.getStrdreporeWhecondition(tempid, modelid) ;
		 if( reportWhecondition == null){
			 return "not";
		 }
		 String wd  =	reportWhecondition.getWhecondition();
		 String pkid =  reportWhecondition.getPkid().toString();
		 if( wd == null){
			 return "-" ;
		 }else {
			 String [] splitwd = wd.split("=") ;// a1.NBJGH=a2.NBJGH
			 String Rradio1 = splitwd[0];
			 String Rradio2 = splitwd[1];
			 return 	Rradio1+"-"+Rradio2+"-"+pkid;
		 }
	}

	@Override
	public void delStrdreporeWhecondition(Long tempid, Long modelid) {
		 this.strdReporeWheconditionDAO.delStrdreporeWhecondition(tempid, modelid);
		
	}
	
	
	
	
	
	
	

	public StrdReporeWheconditionDAO getStrdReporeWheconditionDAO() {
		return strdReporeWheconditionDAO;
	}

	public void setStrdReporeWheconditionDAO(
			StrdReporeWheconditionDAO strdReporeWheconditionDAO) {
		this.strdReporeWheconditionDAO = strdReporeWheconditionDAO;
	}
}
