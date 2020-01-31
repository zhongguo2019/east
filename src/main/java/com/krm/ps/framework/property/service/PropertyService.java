package com.krm.ps.framework.property.service;

import java.util.List;

import com.krm.ps.framework.property.vo.Prop;

public interface PropertyService {

	/**
	 * 获取所有属性
	 * @return
	 */
	public List getAllProperty();
	
	/**
	 * 获取属性
	 * @param id
	 * @return
	 */
	public Prop getProperty(Long id);
	
	/**
	 * 获取属性名称的list集合，用于判断属性名称是否唯一
	 * @return
	 */
	public List getProNameList();
	
	/**
	 * 新增属性
	 * @param property
	 */
	public void addProperty(Prop property);
	
	/**
	 * 更新属性
	 * @param property
	 */
	public void updateProperty(Prop property);
	
	/**
	 * 删除属性
	 * @param id
	 */
	public void deleteProperty(Long id);
	
	/**
	 * 批量删除
	 * 通过ids字符串删除如ids=2,3 则删除id为2和3的两个属性
	 * @param ids
	 */
	public void batchdelete(String ids);
	
	/**
	 * 根据属性名称获取属性值
	 * @param proName
	 * @return
	 */
	public String getProValue(String proName);
	
	/**
	 * 查询属性的值，若未定义该属性，返回默认值
	 * @param proName
	 * @return
	 */
	public String getProValue(String proName,String defaultValue);

	/**
	 * 根据属性名称获取属性对象
	 * @param proName
	 * @return
	 */
	public Prop getProperty(String proName); 
}
