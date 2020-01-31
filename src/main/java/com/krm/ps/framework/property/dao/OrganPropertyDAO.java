package com.krm.ps.framework.property.dao;

import java.util.List;

import com.krm.ps.framework.dao.DAO;
import com.krm.ps.framework.property.vo.OrganProperty;

public interface OrganPropertyDAO extends DAO{

	/**
	 * 添加机构属性
	 * @param op
	 */
	public void addOrganProperty(OrganProperty op);

	/**
	 * 删除机构属性
	 * @param op
	 */
	public void deleteOrganProperty(OrganProperty op);
	
	/**
	 * 根据机构编码获取属性列表(包括关联的属性与未关联的属性)
	 * @param organCode
	 * @return
	 */
	public List getOp(String organCode);

	/**
	 * 根据机构编码和属性id获取唯一的机构属性
	 * @param organCode
	 * @param proId
	 * @return
	 */
	public OrganProperty getOrganProperty(String organCode, Long proId);

	/**
	 * 根据属性id获取机构属性列表
	 * @param proId
	 * @return
	 */
	public List getList(Long proId);

	/**
	 * 更新机构属性
	 * @param op
	 */
	public void updateOrganProperty(OrganProperty op);  
}
