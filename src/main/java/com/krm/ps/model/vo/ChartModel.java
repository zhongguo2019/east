package com.krm.ps.model.vo;

import com.krm.ps.framework.vo.BaseObject;


public class ChartModel extends BaseObject {

	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = 625221617133875258L;

	//����id
	private int reportID;
	//ģʽid
	private Long chartID;
	//ͼ������
	private short chartType;
	//���ݷ�������
	private short chartOrientation;
	//������Ϣ��ʼλ��
	private short catBegin;
	//������Ϣ��ȡ��Ŀ
	private short catCount;
	//ϵ����Ϣ��ʼλ��
	private short seriBegin;
	//ϵ����Ϣ��ȡ��ľ
	private short seriCount;
	//ͼ����ʾѡ��
	private short chartLegend;
	//���ݱ���ʾѡ��
	private short chartTable;
	//������Ϣ����λ��
	private short catIndex;
	//ϵ����Ϣ����λ��
	private short seriIndex;
	//ģʽ����
	private String chartName;
	//����ͼ����
	private String chartTitle;
	//x�����
	private String chartTitleX;
	//y�����
	private String chartTitleY;
	//��ǩ��ʾѡ��
	private String chartLabel;
	//������ʾ��ǩ
	private String chartGrid;
	//����ͼ״̬
	private short chartEnabled;
	
	/**
	 * ��������ͼ�����Ϣ
	 * add by LXK 20081010
	 * ͼ�����Ƿ��԰ٷֱȵ���ʽ��ʵ��Ĭ��ΪN�����԰ٷֱȵ���ʽ��ʾ��
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
     * �������ݶ�Ӧ��ģ��ID
     * @return ģ��ID���������Ϊ�գ�����0
     * @hibernate.id column="pkid" generator-class="native"
     * @hibernate.generator-param name="sequence" value="chart_model_seq"
     */
    public Long getChartID(){
    	return chartID;
    } 
	/**
	 * ���ط���ͼ�����ı���ID
	 * @return ����ID
	 * @hibernate.property column="report_id"
	 */
	public int getReportID(){
		return reportID;
	}
	/**
	 * ���ط���ͼ�����ͣ����ͺ���μ�getChartTypeStr
	 * @return ���ͺ�
	 * @link validChartType
	 * @hibernate.property column="ch_type"
	 */
	public short getChartType(){
		return chartType;
	}
	/**
	 * ���ط���ͼģ������
	 * @return ģ������
	 * @hibernate.property column="ch_name"
	 */
	public String getChartName(){
		return chartName;
	}
	/**
	 * ���ط���ͼ������ȡ���򣨺���μ�getOrientationStr��
	 * @return ��ȡ����
	 * @link validChartOrientation, getCatBegin, getCatCount, getSeriBegin, getSeriCount
	 * @hibernate.property column="ch_orientation"
	 */
	public short getChartOrientation(){
		return chartOrientation;
	}
	/**
	 * ���ط���ͼ�������ݿ�ʼλ�ã���/�У�������ȡ����Ĳ�ͬ�����岻ͬ��
	 * @return �������ݿ�ʼλ��
	 * @link getChartOrientation, getCatCount
	 * @hibernate.property column="cat_begin"
	 */
	public short getCatBegin(){
		return catBegin;
	}
	/**
	 * ���ط���ͼ������Ŀ����ȡ����/������������ȡ����Ĳ�ͬ�����岻ͬ��
	 * @return ������Ŀ
	 * @link getChartOrientation, getCatBegin
	 * @hibernate.property column="cat_count"
	 */
	public short getCatCount(){
		return catCount;
	}
	/**
	 * ���ط���ͼ������Ϣ��/�к�
	 * @return ��/�к�
	 * @hibernate.property column="cat_index"
	 */
	public short getCatIndex(){
		return catIndex;
	}
	/**
	 * ���ط���ͼϵ�����ݿ�ʼλ�ã���/�У�������ȡ����Ĳ�ͬ�����岻ͬ��
	 * @return ϵ�����ݿ�ʼλ��
	 * @link getChartOrientation, getSeriCount
	 * @hibernate.property column="ser_begin"
	 */
	public short getSeriBegin(){
		return seriBegin;
	}
	/**
	 * ���ط���ͼϵ����Ŀ����ȡ����/������������ȡ����Ĳ�ͬ�����岻ͬ��
	 * @return ϵ����Ŀ
	 * @link getChartOrientation, getSeriBegin
	 * @hibernate.property column="ser_count"
	 */
	public short getSeriCount(){
		return seriCount;
	}
	/**
	 * ���ط���ͼϵ����/�к�
	 * @return ��/�к�
	 * @hibernate.property column="ser_index"
	 */
	public short getSeriIndex(){
		return seriIndex;
	}
	/**
	 * ���ط���ͼ����
	 * @return ����ͼ����
	 * @hibernate.property column="ch_title"
	 */
	public String getChartTitle(){
		return chartTitle;
	}
	/**
	 * ���ط���ͼX�����
	 * @return X�����
	 * @hibernate.property column="ch_x_title"
	 */
	public String getChartTitleX(){
		return chartTitleX;
	}
	/**
	 * ���ط���ͼY�����
	 * @return Y�����
	 * @hibernate.property column="ch_y_title"
	 */
	public String getChartTitleY(){
		return chartTitleY;
	}
	/**
	 * ���ط���ͼͼ����ʾѡ�� 0-����ʾ��������ʾλ�ã�����μ�getPositionStr��
	 * @return ͼ����ʾѡ��
	 * @hibernate.property column="ch_legend"
	 */
	public short getChartLegend(){
		return chartLegend;
	}
	/**
	 * �������ݱ����ʾѡ�� 0-����ʾ��������ʾλ�ã�����μ�getPositionStr��
	 * @return ���ݱ����ʾѡ��
	 * @hibernate.property column="ch_table"
	 */
	public short getChartTable(){
		return chartTable;
	}
	/**
	 * ���ر�ǩ��ʾѡ��ַ���ÿһλ����һ������ʾ��Ŀ������μ�getLabelDefine����ÿһλ��ֵ�������Ŀ�Ƿ���ʾ
	 * @return ��ǩ��ʾѡ��
	 * @hibernate.property column="ch_label"
	 */
	public String getChartLabel(){
		return chartLabel;
	}
	/**
	 * ������������ʾѡ��ַ���ÿһλ����һ������ʾ��Ŀ������μ�getGridDefine����ÿһλ��ֵ�������Ŀ�Ƿ���ʾ
	 * @return ��������ʾѡ��
	 * @hibernate.property column="ch_grid"
	 */
	public String getChartGrid(){
		return chartGrid; 
	}
	/**
	 * ���ظ÷���ͼģ���Ƿ����
	 * @return �Ƿ����
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
