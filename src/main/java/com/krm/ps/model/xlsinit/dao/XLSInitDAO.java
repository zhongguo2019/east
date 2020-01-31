package com.krm.ps.model.xlsinit.dao;

import java.util.List;

import com.krm.ps.framework.dao.DAO;
import com.krm.ps.model.xlsinit.vo.DataSet;
import com.krm.ps.model.xlsinit.vo.TableList;
import com.krm.ps.sysmanage.usermanage.vo.User;



public interface XLSInitDAO extends DAO{
	public void XLSInit(List sql) throws Exception;
	
	/**
	 * 处理大字段问题所加方法,把SqlHelp内的逻辑转移到DAO层内,用jdbc处理大字段
	 * @param ds 读入Excel文件数据集合
	 * @param tabLst xml配置文件内容
	 * @param user 用户
	 * @throws Exception
	 */
	public void XLSInit(DataSet dataSet, TableList tableList, User user) throws Exception;
}
