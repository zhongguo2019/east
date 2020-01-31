package com.krm.ps.model.reportrule.dao;

import java.util.List;

import com.krm.ps.model.vo.FromValidator;

public interface FromValidatorDAO {
	/**
	 * 查询所有页面校验脚本
	 * 
	 * @param fromValidator
	 * @return list
	 */
	public List listFromValidator(Long pkid, String roportId, String targetfield);

	/**
	 * 查询所有页面校验脚本
	 * 
	 * @param roportId
	 * @return list
	 */
	public List listFromValidator(String roportId);

	/**
	 * 保存页面校验规则
	 * 
	 * @param fv
	 * @return
	 */
	public boolean saveFromValidator(FromValidator fv);

	/**
	 * 删除页面校验规则
	 * 
	 * @param fv
	 */
	public int deleteFromValidator(Long pkid);

	/**
	 * 查询pkid 最大值
	 * 
	 * @param
	 */
	public Long getFvPkid();

	/**
	 * 批量修改脚本规则
	 */
	public void updateFromValidator(FromValidator fv, List pkidstr);

}
