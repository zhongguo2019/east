package com.krm.ps.model.repfile.dao;

import java.util.List;

import com.krm.ps.framework.dao.DAO;
import com.krm.ps.model.vo.DicItem;

public interface DataFillDAO extends DAO {

	/**
	 * 通过parentid查询标准化字典
	 * 
	 * @param dicPid
	 * @return
	 */
	public List<DicItem> getDicByPid(Long dicPid);

}
