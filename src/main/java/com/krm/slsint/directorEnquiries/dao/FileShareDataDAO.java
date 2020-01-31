package com.krm.slsint.directorEnquiries.dao;

import com.krm.ps.framework.dao.DAO;

public interface FileShareDataDAO extends DAO {

	/**
	 * �õ�umg_file_share������show_order
	 * 
	 * @param
	 * @return Long
	 */
	public Long getFileShareDataMaxOrder();
}
