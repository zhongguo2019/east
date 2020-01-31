package com.krm.ps.model.vo;

import java.util.List;
import java.util.Map;

import com.krm.ps.model.reportview.util.FillDataCollectCallback;
import com.krm.ps.sysmanage.reportdefine.vo.ReportFormat;


/**
 * 获取报表XML参数类
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author
 */
public class ReportXMLParam {

	//机构数系统编码
	public int organSystem = 0;
	
	//机构编码
	public String organId;
	
	//报表编码
	public Long reportId;
	
	//日期
	public String dataDate;
	
	//频度
	public String frequency;
	
	//报表格式对象
	public ReportFormat reportFormat;
	
	//数据集合填充回调对象
	public FillDataCollectCallback fcc;
	
	//附加颜色信息对象
	public boolean addColorInfo = false;
	
	//选择日期对象
	public String selectDate;
	
	//报表单位
	public String rptUnit;
	
	//预取报表编码列表
	public List preRepIDList;
	
	//公式集合对象缓存Map
	public Map dataCollectMap;
}
