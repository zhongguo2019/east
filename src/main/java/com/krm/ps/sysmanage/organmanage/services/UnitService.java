package com.krm.ps.sysmanage.organmanage.services;



import java.util.List;

import com.krm.ps.sysmanage.usermanage.vo.Units;

public interface UnitService {
	/**
	 * 保存数量单位
	 * @param unit
	 */
	public void saveUnit(Units unit);
	/**
	 * 查询数量单位
	 * @param pkid
	 * @return
	 */
	public Units queryUnitById(Long pkid);
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