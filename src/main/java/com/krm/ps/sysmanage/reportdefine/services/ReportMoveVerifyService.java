package com.krm.ps.sysmanage.reportdefine.services;

/**
 * 
 * @date：2013-10-24 上午9:25:29   
 * @author gaozhongbo
 * 接口描述：用于EAST标准化补录后的数据迁移与验证
 *
 */
public interface ReportMoveVerifyService {
	
	/**
	 * 将补录过的数据迁移至F表
	 * 并修改状态
	 * @return boolean
	 */
	public boolean reportMove();
	
	/**
	 * 将数据迁移至F表并校验校验补录过的数据
	 * @return boolean
	 */
	public String reportVerify();

}
