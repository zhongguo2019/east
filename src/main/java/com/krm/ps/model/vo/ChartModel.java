package com.krm.ps.model.vo;

import com.krm.ps.framework.vo.BaseObject;


public class ChartModel extends BaseObject {

	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = 625221617133875258L;

	//报表id
	private int reportID;
	//模式id
	private Long chartID;
	//图表类型
	private short chartType;
	//数据分析方向
	private short chartOrientation;
	//类型信息开始位置
	private short catBegin;
	//类新信息提取树目
	private short catCount;
	//系列信息开始位置
	private short seriBegin;
	//系列信息提取树木
	private short seriCount;
	//图例显示选项
	private short chartLegend;
	//数据表显示选项
	private short chartTable;
	//类型信息所在位置
	private short catIndex;
	//系列信息所在位置
	private short seriIndex;
	//模式名称
	private String chartName;
	//分析图标题
	private String chartTitle;
	//x轴标题
	private String chartTitleX;
	//y轴标题
	private String chartTitleY;
	//标签显示选项
	private String chartLabel;
	//网格显示标签
	private String chartGrid;
	//分析图状态
	private short chartEnabled;
	
	/**
	 * 增加配置图表的信息
	 * add by LXK 20081010
	 * 图例轴是否以百分比的形式现实，默认为N（不以百分比的形式显示）
	 */
	private String leftAxis;
	
	private String rightAxis;
	
	public String getLeftAxis() {
		return leftAxis;
	}
	public void setLeftAxis(String leftAxis) {
		this.leftAxis = leftAxis;
	}
	public String getRightAxis() {
		return rightAxis;
	}
	public void setRightAxis(String rightAxis) {
		this.rightAxis = rightAxis;
	}
	/**
     * 返回数据对应的模型ID
     * @return 模型ID，如果数据为空，返回0
     * @hibernate.id column="pkid" generator-class="native"
     * @hibernate.generator-param name="sequence" value="chart_model_seq"
     */
    public Long getChartID(){
    	return chartID;
    } 
	/**
	 * 返回分析图归属的报表ID
	 * @return 报表ID
	 * @hibernate.property column="report_id"
	 */
	public int getReportID(){
		return reportID;
	}
	/**
	 * 返回分析图的类型，类型含义参见getChartTypeStr
	 * @return 类型号
	 * @link validChartType
	 * @hibernate.property column="ch_type"
	 */
	public short getChartType(){
		return chartType;
	}
	/**
	 * 返回分析图模型名称
	 * @return 模型名称
	 * @hibernate.property column="ch_name"
	 */
	public String getChartName(){
		return chartName;
	}
	/**
	 * 返回分析图数据提取方向（含义参见getOrientationStr）
	 * @return 提取方向
	 * @link validChartOrientation, getCatBegin, getCatCount, getSeriBegin, getSeriCount
	 * @hibernate.property column="ch_orientation"
	 */
	public short getChartOrientation(){
		return chartOrientation;
	}
	/**
	 * 返回分析图类型数据开始位置（行/列，根据提取方向的不同，含义不同）
	 * @return 类型数据开始位置
	 * @link getChartOrientation, getCatCount
	 * @hibernate.property column="cat_begin"
	 */
	public short getCatBegin(){
		return catBegin;
	}
	/**
	 * 返回分析图类型数目（提取的行/列数，根据提取方向的不同，含义不同）
	 * @return 类型数目
	 * @link getChartOrientation, getCatBegin
	 * @hibernate.property column="cat_count"
	 */
	public short getCatCount(){
		return catCount;
	}
	/**
	 * 返回分析图类型信息行/列号
	 * @return 行/列号
	 * @hibernate.property column="cat_index"
	 */
	public short getCatIndex(){
		return catIndex;
	}
	/**
	 * 返回分析图系列数据开始位置（行/列，根据提取方向的不同，含义不同）
	 * @return 系列数据开始位置
	 * @link getChartOrientation, getSeriCount
	 * @hibernate.property column="ser_begin"
	 */
	public short getSeriBegin(){
		return seriBegin;
	}
	/**
	 * 返回分析图系列数目（提取的行/列数，根据提取方向的不同，含义不同）
	 * @return 系列数目
	 * @link getChartOrientation, getSeriBegin
	 * @hibernate.property column="ser_count"
	 */
	public short getSeriCount(){
		return seriCount;
	}
	/**
	 * 返回分析图系列行/列号
	 * @return 行/列号
	 * @hibernate.property column="ser_index"
	 */
	public short getSeriIndex(){
		return seriIndex;
	}
	/**
	 * 返回分析图标题
	 * @return 分析图标题
	 * @hibernate.property column="ch_title"
	 */
	public String getChartTitle(){
		return chartTitle;
	}
	/**
	 * 返回分析图X轴标题
	 * @return X轴标题
	 * @hibernate.property column="ch_x_title"
	 */
	public String getChartTitleX(){
		return chartTitleX;
	}
	/**
	 * 返回分析图Y轴标题
	 * @return Y轴标题
	 * @hibernate.property column="ch_y_title"
	 */
	public String getChartTitleY(){
		return chartTitleY;
	}
	/**
	 * 返回分析图图例显示选项 0-不显示，其他表示位置（含义参见getPositionStr）
	 * @return 图例显示选项
	 * @hibernate.property column="ch_legend"
	 */
	public short getChartLegend(){
		return chartLegend;
	}
	/**
	 * 返回数据表格显示选项 0-不显示，其他表示位置（含义参见getPositionStr）
	 * @return 数据表格显示选项
	 * @hibernate.property column="ch_table"
	 */
	public short getChartTable(){
		return chartTable;
	}
	/**
	 * 返回标签显示选项，字符串每一位代表一个待显示项目（含义参见getLabelDefine），每一位的值代表该项目是否显示
	 * @return 标签显示选项
	 * @hibernate.property column="ch_label"
	 */
	public String getChartLabel(){
		return chartLabel;
	}
	/**
	 * 返回网格线显示选项，字符串每一位代表一个待显示项目（含义参见getGridDefine），每一位的值代表该项目是否显示
	 * @return 网格线显示选项
	 * @hibernate.property column="ch_grid"
	 */
	public String getChartGrid(){
		return chartGrid; 
	}
	/**
	 * 返回该分析图模型是否可用
	 * @return 是否可用
	 * @hibernate.property column="ch_enabled"
	 */
	public short getChartEnabled(){
		return chartEnabled;
	}

	public void setChartID(Long value){
		chartID = value;
	}

	public void setReportID(int value){
		reportID = value;
	}

	public void setChartType(short value){
		chartType = value;
	}

	public void setChartName(String value){
		chartName = value;
	}

	public void setChartOrientation(short value){
		chartOrientation = value;
	}

	public void setCatBegin(short value){
		catBegin = value;
	}

	public void setCatCount(short value){
		catCount = value;
	}

	public void setSeriBegin(short value){
		seriBegin = value;
	}

	public void setSeriCount(short value){
		seriCount = value;
	}

	public void setChartTitle(String value){
		chartTitle = value;
	}

	public void setChartTitleX(String value){
		chartTitleX = value;
	}

	public void setChartTitleY(String value){
		chartTitleY = value;
	}

	public void setChartLegend(short value){
		chartLegend = value;
	}

	public void setChartTable(short value){
		chartTable = value;
	}

	public void setChartLabel(String value){
		chartLabel = value;
	}

	public void setChartGrid(String value){
		chartGrid = value;
	}

	public void setChartEnabled(short value){
		chartEnabled = value;
	}
	
	public void setCatIndex(short value){
		catIndex = value;
	}
	
	public void setSeriIndex(short value){
		seriIndex = value;
	}
	
	public ChartModel(){
		super();
	}
	
	public String toString() {
		StringBuffer b = new StringBuffer();
		b.append("Name:" + chartName + ";");
		b.append("Orientation:" + chartOrientation + ";");
		b.append("Title:" + chartTitle + ";");
		b.append("x Title:" + chartTitleX + ";");
		b.append("y Title:" + chartTitleY + ";");
		b.append("id:" + chartID + ";");
		b.append("ReportID:" + reportID + ";");
		b.append("Cat Index:" + catIndex + ";");
		b.append("Cat Begin:" + catBegin + ";");
		b.append("Cat Count:" + catCount + ";");
		b.append("Seri Index:" + seriIndex + ";");
		b.append("Seri Begin:" + seriBegin + ";");
		b.append("Seri Count:" + seriCount + ";");
		b.append("Legend:" + chartLegend + ";");
		b.append("Label:" + chartLabel + ";");
		b.append("Table:" + chartTable + ";");
		b.append("Grid:" + chartGrid + ";");
		b.append("Type:" + chartType + ";");
		b.append("Enabled:" + chartEnabled + ";");
		return b.toString();
	}

	public boolean equals(Object o) {
		return false;
	}

	public int hashCode() {
		return 0;
	}

}
