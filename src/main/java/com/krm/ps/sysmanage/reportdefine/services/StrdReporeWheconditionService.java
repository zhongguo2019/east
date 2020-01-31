package com.krm.ps.sysmanage.reportdefine.services;

import com.krm.ps.sysmanage.reportdefine.vo.StrdReportWhecondition;

public interface StrdReporeWheconditionService {
	
	
	/**
	 * 得到 StrdreporeWhecondition 表的 table 字段
	 * @param tempid
	 * @return
	 */
     public String  getStrdreporeWhecondition(Long tempid); 
     /**
      * 新增关联配置数据
      * 
      */
     public void saveStrdreporeWhecondition(StrdReportWhecondition strdReportWhecondition);
     
     /**
      *  得道一条配置关联信息
 	 * @param tempid
 	 * @param modelid
 	 * @return
 	 */
 	public String getStrdreporeWhecondition(Long tempid ,Long modelid);
 	
 	/**
      *  删除一条配置关联信息
 	 * @param tempid
 	 * @param modelid
 	 * @return
 	 */
 	public void delStrdreporeWhecondition(Long tempid ,Long modelid);
  		
}
