package com.krm.ps.birt;

import org.eclipse.birt.report.engine.api.script.IReportContext;
import org.eclipse.birt.report.engine.api.script.ScriptException;
import org.eclipse.birt.report.engine.api.script.eventadapter.DataItemEventAdapter;
import org.eclipse.birt.report.engine.api.script.instance.IDataItemInstance;

import com.krm.ps.util.FuncConfig;

public class Style extends DataItemEventAdapter{
	
	public void onRender(IDataItemInstance data, IReportContext reportContext) throws ScriptException {
		super.onRender(data, reportContext);
		String area = FuncConfig.getProperty("fujian.area");
		String[] str=area.split(",");
		for(int i=0;i<str.length;i++){
			if (str[i].equals(data.getValue())) {
				data.getStyle().setFontWeight("bold");
			}
		}
	}
}
