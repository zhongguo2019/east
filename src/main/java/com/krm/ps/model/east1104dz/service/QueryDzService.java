package com.krm.ps.model.east1104dz.service;

import java.util.List;

public interface QueryDzService {

	public List getReports(String date, Long userid);

	public List getQuerydz(String date, String organid,int idx);
}
