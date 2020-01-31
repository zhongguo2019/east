package com.krm.ps.birt;

import com.krm.ps.birt.service.ShowBirtService;
import com.krm.ps.common.dto.DTO;
import com.krm.ps.common.dto.VO;
import com.krm.ps.common.process.KProcessImpl;
import com.krm.ps.sysmanage.reportdefine.vo.Report;

public class ShowBirtProcess extends KProcessImpl {

	@Override
	public void doCheck(VO in, DTO out) {
assertNotNull("机构编码不能为空", in.getString("organId"));
assertNotNull("报表编码不能为空", in.getString("reportId"));
assertNotNull("数据日期不能为空", in.getString("dataDate"));
	}

	@Override
	public void execute(VO in, DTO out) {
		String organCode = in.getString("organId");
		String reportDate=in.getString("dataDate");
		String reportId=in.getString("reportId");
		String reportName=in.getString("reportName");
		ShowBirtService sbs=(ShowBirtService)getService().getBean("showBirtService");
		Report r=sbs.getReportById(reportId);
		String rptName=r.getPhyTable()+".rptdesign";
		
		out.put("prtName", rptName);
		out.put("organId", organCode);
		out.put("dataDate", reportDate.replaceAll("-", ""));
		
		

	}

}
