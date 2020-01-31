package com.krm.ps.sysmanage.organmanage.dao;

import java.util.List;

import com.krm.ps.framework.dao.DAO;
import com.krm.ps.sysmanage.usermanage.vo.Units;

public interface UnitDAO extends DAO{
	/**
	 * 查询最大显示编号
	 * @return
	 */
	public Integer queryMaxDisplayOrder();
	/**
	 * 更新数量单位
	 * @param unit
	 */
	public void updateUnit(Units unit);
	/**
	 * 删除数量单位
	 * @param pkid
	 */
	public void delUnit(Long pkid);
	/**
	 * 查询数量单位
	 * @return
	 */
	public List queryUnit();

}
