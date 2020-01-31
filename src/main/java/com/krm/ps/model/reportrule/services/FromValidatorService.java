package com.krm.ps.model.reportrule.services;

import java.util.List;

import com.krm.ps.model.vo.FromValidator;

public interface FromValidatorService {
	/**
	 * 查询所有页面校验脚本
	 * 
	 * @param fromValidator
	 * @return list
	 */
	public List listFromValidator(String roportId);

	/**
	 * 查询所有页面校验脚本
	 * 
	 * @param fromValidator
	 * @return list
	 */
	public List listFromValidator(Long pkid, String roportId, String targetfield);

	/**
	 * 保存校验规则
	 * 
	 * @param rulecode
	 * @return
	 */
	public boolean saveFromValidator(FromValidator fromValidator);

	/**
	 * 删除规则
	 * 
	 * @param rulecode
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
