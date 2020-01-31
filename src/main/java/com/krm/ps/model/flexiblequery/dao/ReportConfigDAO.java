package com.krm.ps.model.flexiblequery.dao;

import java.util.List;

import com.krm.ps.framework.dao.DAO;
import com.krm.ps.sysmanage.reportdefine.vo.ReportConfig;

public interface ReportConfigDAO extends DAO {

	public List<ReportConfig> getdefCharByTem(Long reportid, Long funid);

}
