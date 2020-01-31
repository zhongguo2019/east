package com.krm.ps.framework.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.krm.ps.framework.dao.hibernate.support.lob.LobHandler;

public interface DAO
{
	public void setLobHandler(LobHandler lobHandler);

	public List getObjects(Class clazz);

	public Object getObject(Class clazz, Serializable id);

	public void saveObject(Object o);

	public void removeObject(Object o);

	public void removeObject(Class clazz, Serializable id);
	
	public boolean sthRepeat(String className,String pkAttributeName,Long pkid,String sthAttributeName,String sth);
	
	public void batchSaveVO(List voList);
	
	/**
	 * <p>支持对象的动态查询</p>
	 * 
	 * @param tableName 表名称
	 * @param resultColumns 要查询得到的列 此参数为null时得到的是类对象列表
	 * @param conditionMap 条件Map，及相关的查询条件（目前这些查询条件只支持与）
	 * 					   此参数的某个key对应的值可以传入LIST，list中放入ID，这样会把list转为串，用in的方式去查询
	 * @param entityClass 实体类，指定造成哪个类对象
	 * @return
	 * @throws Exception
	 * @author 皮亮
	 * @version 创建时间：2010-3-31 上午11:05:55
	 */
	public List getObjects(String tableName, List resultColumns, Map conditionMap, Class entityClass) throws Exception;
	
	/**
	 * <p>使用HQL语句删除或更新数据</p> 
	 *
	 * @param sqlString 要执行的SQL语句
	 * @return
	 * @author 皮亮
	 * @version 创建时间：2010-4-25 上午12:28:53
	 */
	public boolean deleteOrUpdateObjectsWithHQLSQL(String sqlString);

	/**
	 * <p>动态查询对象的指定信息</p> 
	 *
	 * @param fields 指定要查询的对象字段
	 * 				null：则表示查询整个对象
	 * @param objectClass 指定要查询的表对象
	 * @param condition 查询对象时的条件
	 * @return
	 * @author 皮亮
	 * @version 创建时间：2010-4-25 下午10:33:14
	 */
	public List getObjectSpecifiedInfo(String[] fields, Class objectClass, String condition);
	
//	public List listObjectsWithoutSessionManage(Object[][] entitis);
}
