package com.krm.ps.model.reportdefine.services;

import com.krm.ps.sysmanage.reportdefine.vo.StrdReportWhecondition;

public interface StrdReporeWheconditionService {

	public String getStrdreporeWhecondition(Long tempid);

	public void saveStrdreporeWhecondition(
			StrdReportWhecondition strdReportWhecondition);

	public String getStrdreporeWhecondition(Long tempid, Long modelid);

	public void delStrdreporeWhecondition(Long tempid, Long modelid);

}
