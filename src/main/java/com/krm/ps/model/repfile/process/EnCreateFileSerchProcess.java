package com.krm.ps.model.repfile.process;

import java.util.List;

import com.krm.ps.common.dto.DTO;
import com.krm.ps.common.dto.VO;
import com.krm.ps.common.process.KProcessImpl;
import com.krm.ps.model.reportdefine.services.ReportDefineService;
import com.krm.ps.sysmanage.usermanage.vo.User;

public class EnCreateFileSerchProcess extends KProcessImpl {

	@Override
	public void doCheck(VO in, DTO out) {

	}

	@Override
	public void execute(VO in, DTO out) {
		String systemId =in.getString("system_id");
		String datevalue=in.getString("logindate");
		String levelFlag=in.getString("levelFlag");
		User user=(User)in.get("user");
		ReportDefineService rs = (ReportDefineService)getService().getBean("rdreportDefineService");
		List repList = rs.getDateOrganEditReportForYJH(
				datevalue.replaceAll("-", ""), null, null, user.getPkid(), systemId,
				levelFlag);
		out.put("repList", repList);
		out.put("begindate", datevalue);
		out.put("enddate", datevalue);
		out.put("condates", datevalue);
		out.put("organId", user.getOrganId());
	}

}
