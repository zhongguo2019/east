package com.krm.ps.framework.common.dao;

import java.util.List;

import com.krm.ps.framework.dao.DAO;

public interface CommonQueryDAO extends DAO {

	List commonQuery(String sql, Object[][] entities, Object[][] scalaries, Object[] values);

}
