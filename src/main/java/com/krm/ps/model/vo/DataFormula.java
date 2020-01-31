package com.krm.ps.model.vo;

//import org.w3c.dom.Document;

/**
 * 
 * <p>
 * 类名称: DataFormula
 * <p>
 * 功能: 公式数据信息类
 * <p>
 * 创建时间: 2006.4.17
 * 
 * @author nxd
 * @version v1.1 2006.4.17
 * @since 修改记录：――――――――――――――――――――――――――
 *        <p>
 *        修改时间 修改者 修改的方法 修改的原因
 */

public class DataFormula implements Cloneable {

	/**
	 * 数据类行标识：double 类型数据
	 */
	public static final int DATA_DOUBLE = 0;

	/**
	 * 数据类行标识：String 类型数据
	 */
	public static final int DATA_STRING = 1;
	
	/**
	 * 数据类行标识：表内公式类型数据
	 */
	public static final int DATA_INFRMA = 2;

	/**
	 * 公式或数据显示的行
	 */
	private int row = 0;

	/**
	 * 公式或数据显示的列
	 */
	private int col = 0;

	/**
	 * 公式字符串
	 */
	private String formulastr = "";

	/**
	 * 数据类行 DATA_DOUBLE = 0;DATA_STRING = 1。
	 */
	private int datatype = 0;

	/**
	 * double 类型数据
	 */
	private double datadouble = 0;

	/**
	 * String 类型数据
	 */
	private String datastr = "";
	
	/**
	 * 单元格文字颜色数值(10进制)
	 */
	private String frColor = "";
	
	/**
	 * 单元格背景颜色数值(10进制)
	 */
	private String bgColor = "";

	/**
	 * 小数位数，在生成上报xml时使用。0表示整数，2表示两位小数,如12.34
	 */
	//gyl@20060902追加格式定义.参看com.krm.goldreport.getXMLDataType(String,Document)方法
	
	private int decimalDigits = 0;
	
	/**
	 * 扩展行数量(复合扩展公式用)
	 */
	private int insertCount = 0;
	
	/**
	 * 扩展行插入位置(复合扩展公式用)
	 */
	private int insertPosition = 0;
	
	/**
	 * 科目类型
	 */
	private int itemType = 0;

	public static final int INTTYPE = 0;// 正数型

	public static final int PROPROTION2 = 1;// 比例数带2位小数

	public static final int PROPROTION4 = 2;// 比例数带4位小数

	public static final int DOUBLE2 = 3;// 双精度数带2位小数

	public static final int DOUBLE4 = 4;// 双精度数带4位小数

	public static final int PROPROTION6= 5;// 比例数带6位小数

	public static final int PROPROTION3 = 6;// 比例数带3位小数

	public static final int PROPROTION = 7;// 比例数带2位小数

	public static final int DOUBLE5 = 8;// 双精度数带4位小数

	public static final int PROPROTION4P = 9;// 比例数带4位小数

	public boolean isConvertMoney = false;// 是否转换金额，给导出1104XML使用

	/**
	 * <p>
	 * 方法的名称及参数: DataFormula()
	 * <p>
	 * 方法的描述: 默认构造函数
	 */
	public DataFormula() {
	}

	/**
	 * 构造函数
	 * 
	 * @param row
	 *            行
	 * @param col
	 *            列
	 * @param formulastr
	 *            公式字符串
	 * @param datatype
	 *            数据类行
	 * @param datadouble
	 *            double型数据
	 * @param datastring
	 *            string型数据
	 */
	public DataFormula(int row, int col, String formulastr, int datatype,
			double datadouble, String datastr) {
		this.row = row;
		this.col = col;
		this.formulastr = formulastr;
		this.datatype = datatype;
		this.datadouble = datadouble;
		this.datastr = datastr;
	}

	public DataFormula(int row, int col, String formulastr) {
		this.row = row;
		this.col = col;
		this.formulastr = formulastr;

	}
	
	/**
	 * 拷贝
	 * @return DataFormula
	 */
	public DataFormula copy() {
		try {
			return (DataFormula) this.clone();
		} catch (CloneNotSupportedException e) {
			return new DataFormula(getRow(), getCol(), getFormulaString(), getDataType(),
					 getDataDouble(), getDataString());
		}
	}

	/**
	 * 设置公式或数据的行
	 * 
	 * @param row
	 *            公式或数据的行
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * 得到公式或数据的行
	 * 
	 * @return int 公式或数据的行
	 */
	public int getRow() {
		return this.row;
	}

	/**
	 * 设置公式或数据的列
	 * 
	 * @param col
	 *            公式或数据的列
	 */
	public void setCol(int col) {
		this.col = col;
	}

	/**
	 * 得到公式或数据的列
	 * 
	 * @return int 公式或数据的列
	 */
	public int getCol() {
		return this.col;
	}

	/**
	 * 设置公式字符串
	 * 
	 * @param formulastr
	 *            公式字符串
	 */
	public void setFormulaString(String formulastr) {
		this.formulastr = formulastr;
	}

	/**
	 * 得到公式字符串
	 * 
	 * @return String 公式字符串
	 */
	public String getFormulaString() {
		return this.formulastr;
	}

	/**
	 * 设置数据类型
	 * 
	 * @param datatype
	 *            数据类型
	 */
	public void setDataType(int datatype) {
		this.datatype = datatype;
	}

	/**
	 * 得到数据类型
	 * 
	 * @return int 数据类型
	 */
	public int getDataType() {
		return this.datatype;
	}

	/**
	 * 设置double型数据
	 * 
	 * @param datadouble
	 *            double型数据
	 */
	public void setDataDouble(double datadouble) {
		this.datadouble = datadouble;
	}

	/**
	 * 得到double型数据
	 * 
	 * @return double double型数据
	 */
	public double getDataDouble() {
		return this.datadouble;
	}

	/**
	 * 设置String型数据
	 * 
	 * @param datastr
	 *            String型数据
	 */
	public void setDataString(String datastr) {
		this.datastr = datastr;
	}

	/**
	 * 得到String型数据
	 * 
	 * @return String String型数据
	 */
	public String getDataString() {
		return this.datastr;
	}

	public int getDecimalDigits() {
		return decimalDigits;
	}

	public void setDecimalDigits(int decimalDigits) {
		this.decimalDigits = decimalDigits;
	}

	public boolean isConvertMoney() {
		return isConvertMoney;
	}

	public void setConvertMoney(boolean isConvertMoney) {
		this.isConvertMoney = isConvertMoney;
	}

	public String toString() {
		return "" + row + "." + col + " " + formulastr+" = " +((datatype==DATA_STRING)? datastr:""+datadouble)+"\n" ;
	}

	/**
	 * 获得单元格背景色数值(为""表示使用原单元格默认背景色)
	 * @return
	 */
	public String getBgColor() {
		return bgColor;
	}

	/**
	 * 设置单元格背景色数值(为""表示使用原单元格默认背景色)
	 * @param bgColor
	 */
	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	/**
	 * 获得单元格文字颜色数值（为""表示使用原单元格默认文字颜色）
	 * @return
	 */
	public String getFrColor() {
		return frColor;
	}

	/**
	 * 设置单元格文字颜色数值（为""表示使用原单元格默认文字颜色）
	 * @param frColor
	 */
	public void setFrColor(String frColor) {
		this.frColor = frColor;
	}

	/**
	 * 获取科目类型
	 * @return 
	 */
	public int getItemType() {
		return itemType;
	}

	/**
	 * 设置科目类型
	 * @param itemType
	 */
	public void setItemType(int itemType) {
		this.itemType = itemType;
	}

	/**
	 * 获取扩展行插入数量
	 * @return int
	 */
	public int getInsertCount() {
		return insertCount;
	}

	public void setInsertCount(int insertCount) {
		this.insertCount = insertCount;
	}

	/**
	 * 获取扩展行插入位置
	 * @return
	 */
	public int getInsertPosition() {
		return insertPosition;
	}

	public void setInsertPosition(int insertPosition) {
		this.insertPosition = insertPosition;
	}

}
