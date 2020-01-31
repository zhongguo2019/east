package com.krm.ps.sysmanage.organmanage.services.impl;



import java.util.List;

import com.krm.ps.sysmanage.organmanage.dao.UnitDAO;
import com.krm.ps.sysmanage.organmanage.services.UnitService;

import com.krm.ps.sysmanage.usermanage.vo.Units;

/*
 * 添加日期:2006年9月12日
 * 修改人:赵鹏程
 */
public class UnitServiceImpl implements UnitService {
	
	private UnitDAO unitDAO;
	
	public void setUnitDAO(UnitDAO unitDAO){
		this.unitDAO = unitDAO;
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.organmanage.services.UnitService#saveUnit(com.krm.slsint.usermanage.vo.Units)
	 */
	public void saveUnit(Units unit) {
		//unitDAO.queryMaxDisplayOrder();
		Integer displayOrder = unitDAO.queryMaxDisplayOrder();
		int display = displayOrder.intValue()+1;		
		unit.setDisplay_order(new Integer(display));
		unitDAO.saveObject(unit);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.organmanage.services.UnitService#queryUnitById(java.lang.Long)
	 */
	public Units queryUnitById(Long pkid) {
		Units unit = (Units) unitDAO.getObject(Units.class,pkid);
		return unit;
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.organmanage.services.UnitService#updateUnit(com.krm.slsint.usermanage.vo.Units)
	 */
	public void updateUnit(Units unit) {		
		unitDAO.updateUnit(unit);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.organmanage.services.UnitService#delUnit(java.lang.Long)
	 */
	public void delUnit(Long pkid) {
		unitDAO.delUnit(pkid);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.organmanage.services.UnitService#queryUnit()
	 */
	public List queryUnit() {
		//unitDAO.queryUnit();
		return unitDAO.queryUnit();
	}

}
