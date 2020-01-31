package com.krm.ps.model.datafill.services;

import java.util.List;

import com.krm.ps.sysmanage.reportdefine.vo.ReportConfig;

public interface ReportConfigService {

	public List<ReportConfig> getdefCharByTem(Long reportid, Long funid);

}
