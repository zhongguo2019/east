package com.krm.ps.model.flexiblequery.dao;

import com.krm.ps.framework.dao.DAO;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;

public interface OrganInfoDAO extends DAO {
	/**
	 * 根据机构代码取得机构对象
	 * 
	 * @param code
	 *            机构代码
	 * @return
	 */
	public OrganInfo getOrganByCode(String code);
}
