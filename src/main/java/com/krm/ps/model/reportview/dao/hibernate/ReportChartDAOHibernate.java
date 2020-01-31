package com.krm.ps.model.reportview.dao.hibernate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.model.reportview.dao.ReportChartDAO;
import com.krm.ps.model.reportview.util.ReportChart;
import com.krm.ps.model.vo.ChartModel;

public class ReportChartDAOHibernate extends BaseDAOHibernate implements
		ReportChartDAO {
	private ChartModel model = null;

	public int getChartCount(long format, boolean onlyEnabled) {
		int result = 0;
		try {
			String s = "select ch from ChartModel ch where " + "ch.reportID="
					+ format;
			if (onlyEnabled)
				s = s + " and ch.chartEnabled > 0";
			List l = this.list(s);
			if (l != null)
				result = l.size();
		} catch (Exception e) {
			result = 0;
		}
		return result;
	}

}
