package com.krm.ps.model.east1104dz.dao;

import java.util.List;

public interface QueryDzDao {

	public List getReports(String date, Long userid);

	public List getQuerydz(String date, String organid,int idx);
}
