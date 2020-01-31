package com.krm.ps.model.datafill.dao.hibernate;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.model.datafill.dao.ReportConfigDAO;
import com.krm.ps.sysmanage.reportdefine.vo.ReportConfig;

public class ReportConfigDAOHibernate extends BaseDAOHibernate implements
		ReportConfigDAO {
	 

	public List < ReportConfig > getdefCharByTem(Long reportid, Long funid) {
		String sql = "select {t.*} from code_rep_config t where t.report_Id in ( select te.model_id  " +
				"from  template_model te where te.template_id = "+reportid+" )" 
				+ " and t.fun_Id = "+ funid;
		return list(sql,new Object[][]{{"t", ReportConfig.class}}, null, null);
		
	}
	
	public List getReportConfigsByFunc(Long funcId) {
		String hql = "from ReportConfig t where t.funId = " + funcId
		+ " order by t.idxId,t.funId";
		Map map = new HashMap();
		return list(hql,map);
	}
	
}
