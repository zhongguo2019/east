package com.krm.ps.sysmanage.reportdefine.dao;

import java.util.List;
import org.hibernate.Hibernate;
import com.krm.ps.framework.dao.DAO;
import com.krm.ps.sysmanage.reportdefine.vo.StrdReportWhecondition;

public interface StrdReporeWheconditionDAO  extends DAO{
	/**
	 * 通过 tempid 得道所有配置关系表的信息
	 * @param tempid
	 * @return
	 */
   public	List getStrdreporeWhecondition(Long tempid); 
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
	public StrdReportWhecondition getStrdreporeWhecondition(Long tempid ,Long modelid);
	
	/**
     *  删除一条配置关联信息
	 * @param tempid
	 * @param modelid
	 * @return
	 */
	public void delStrdreporeWhecondition(Long tempid ,Long modelid);
    

}
