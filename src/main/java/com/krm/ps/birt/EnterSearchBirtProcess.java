package com.krm.ps.birt;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.krm.ps.birt.service.ShowBirtService;
import com.krm.ps.common.dto.DTO;
import com.krm.ps.common.dto.VO;
import com.krm.ps.common.process.KProcessImpl;
import com.krm.ps.model.reportview.services.ReportViewService;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.usermanage.vo.User;

public class EnterSearchBirtProcess extends KProcessImpl {

	@Override
	public void doCheck(VO in, DTO out) {

	}

	@Override
	public void execute(VO in, DTO out) {
		User user=(User)in.get("user");
		Long userId = user.getPkid();
		String organCode = in.getString("organId");
		String reportDate=in.getString("dataDate");
		String reportId=in.getString("reportId");
		String showbirt=in.getString("showbirt");
		int idxid =user.getOrganTreeIdxid();
		if (null == organCode) {
			organCode = user.getOrganId();
		}
		ShowBirtService sbs=(ShowBirtService)getService().getBean("showBirtService");
		List<Report> repList=sbs.getReports(userId);
		if(StringUtils.isBlank(reportId)){
			reportId=(String.valueOf(repList.get(0).getPkid()));
		}
		Report r=sbs.getReportById(reportId);
		String rptName=r.getPhyTable()+".rptdesign";
		out.put("showbirt", showbirt);
		out.put("prtName", rptName);
		out.put("reports", repList);
		out.put("dataDate", reportDate);
		out.put("organId", organCode);
		out.put("reportId", reportId);
	}

}
