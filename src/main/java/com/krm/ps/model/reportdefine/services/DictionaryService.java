package com.krm.ps.model.reportdefine.services;

import java.util.List;

import com.krm.ps.sysmanage.usermanage.vo.Dictionary;

public interface DictionaryService {

	/**
	 * 得到所有字典对象
	 * 
	 * @return {@link Dictionary}对象列表
	 */
	public List getDics(int type);

	/**
	 * 得到机构对照配置列表
	 * 
	 * @return {@link Esystem}对象列表
	 */
	public List getALLsystem();

	/**
	 * 得到报表频度列表
	 * 
	 * @return {@link Dictionary}对象列表
	 */
	public List getReportfrequency();

	/**
	 * 获得机构业务类别列表
	 * 
	 * @return {@link Dictionary}对象列表
	 */
	public List getOrgansort();

	/**
	 * 得到单位配置对象列表
	 * 
	 * @return {@link Units}对象列表
	 */
	public List getUnitcode();

	/**
	 * 根据父id,dic id 得到字典对象
	 * 
	 * @param type
	 * @param dicid
	 * @return Dictionary
	 */
	public Dictionary getDictionary(int type, int dicid);

}
