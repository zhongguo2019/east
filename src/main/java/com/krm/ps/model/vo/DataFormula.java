package com.krm.ps.model.vo;

//import org.w3c.dom.Document;

/**
 * 
 * <p>
 * ������: DataFormula
 * <p>
 * ����: ��ʽ������Ϣ��
 * <p>
 * ����ʱ��: 2006.4.17
 * 
 * @author nxd
 * @version v1.1 2006.4.17
 * @since �޸ļ�¼���D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D
 *        <p>
 *        �޸�ʱ�� �޸��� �޸ĵķ��� �޸ĵ�ԭ��
 */

public class DataFormula implements Cloneable {

	/**
	 * �������б�ʶ��double ��������
	 */
	public static final int DATA_DOUBLE = 0;

	/**
	 * �������б�ʶ��String ��������
	 */
	public static final int DATA_STRING = 1;
	
	/**
	 * �������б�ʶ�����ڹ�ʽ��������
	 */
	public static final int DATA_INFRMA = 2;

	/**
	 * ��ʽ��������ʾ����
	 */
	private int row = 0;

	/**
	 * ��ʽ��������ʾ����
	 */
	private int col = 0;

	/**
	 * ��ʽ�ַ���
	 */
	private String formulastr = "";

	/**
	 * �������� DATA_DOUBLE = 0;DATA_STRING = 1��
	 */
	private int datatype = 0;

	/**
	 * double ��������
	 */
	private double datadouble = 0;

	/**
	 * String ��������
	 */
	private String datastr = "";
	
	/**
	 * ��Ԫ��������ɫ��ֵ(10����)
	 */
	private String frColor = "";
	
	/**
	 * ��Ԫ�񱳾���ɫ��ֵ(10����)
	 */
	private String bgColor = "";

	/**
	 * С��λ�����������ϱ�xmlʱʹ�á�0��ʾ������2��ʾ��λС��,��12.34
	 */
	//gyl@20060902׷�Ӹ�ʽ����.�ο�com.krm.goldreport.getXMLDataType(String,Document)����
	
	private int decimalDigits = 0;
	
	/**
	 * ��չ������(������չ��ʽ��)
	 */
	private int insertCount = 0;
	
	/**
	 * ��չ�в���λ��(������չ��ʽ��)
	 */
	private int insertPosition = 0;
	
	/**
	 * ��Ŀ����
	 */
	private int itemType = 0;

	public static final int INTTYPE = 0;// ������

	public static final int PROPROTION2 = 1;// ��������2λС��

	public static final int PROPROTION4 = 2;// ��������4λС��

	public static final int DOUBLE2 = 3;// ˫��������2λС��

	public static final int DOUBLE4 = 4;// ˫��������4λС��

	public static final int PROPROTION6= 5;// ��������6λС��

	public static final int PROPROTION3 = 6;// ��������3λС��

	public static final int PROPROTION = 7;// ��������2λС��

	public static final int DOUBLE5 = 8;// ˫��������4λС��

	public static final int PROPROTION4P = 9;// ��������4λС��

	public boolean isConvertMoney = false;// �Ƿ�ת����������1104XMLʹ��

	/**
	 * <p>
	 * ���������Ƽ�����: DataFormula()
	 * <p>
	 * ����������: Ĭ�Ϲ��캯��
	 */
	public DataFormula() {
	}

	/**
	 * ���캯��
	 * 
	 * @param row
	 *            ��
	 * @param col
	 *            ��
	 * @param formulastr
	 *            ��ʽ�ַ���
	 * @param datatype
	 *            ��������
	 * @param datadouble
	 *            double������
	 * @param datastring
	 *            string������
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
	 * ����
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
	 * ���ù�ʽ�����ݵ���
	 * 
	 * @param row
	 *            ��ʽ�����ݵ���
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * �õ���ʽ�����ݵ���
	 * 
	 * @return int ��ʽ�����ݵ���
	 */
	public int getRow() {
		return this.row;
	}

	/**
	 * ���ù�ʽ�����ݵ���
	 * 
	 * @param col
	 *            ��ʽ�����ݵ���
	 */
	public void setCol(int col) {
		this.col = col;
	}

	/**
	 * �õ���ʽ�����ݵ���
	 * 
	 * @return int ��ʽ�����ݵ���
	 */
	public int getCol() {
		return this.col;
	}

	/**
	 * ���ù�ʽ�ַ���
	 * 
	 * @param formulastr
	 *            ��ʽ�ַ���
	 */
	public void setFormulaString(String formulastr) {
		this.formulastr = formulastr;
	}

	/**
	 * �õ���ʽ�ַ���
	 * 
	 * @return String ��ʽ�ַ���
	 */
	public String getFormulaString() {
		return this.formulastr;
	}

	/**
	 * ������������
	 * 
	 * @param datatype
	 *            ��������
	 */
	public void setDataType(int datatype) {
		this.datatype = datatype;
	}

	/**
	 * �õ���������
	 * 
	 * @return int ��������
	 */
	public int getDataType() {
		return this.datatype;
	}

	/**
	 * ����double������
	 * 
	 * @param datadouble
	 *            double������
	 */
	public void setDataDouble(double datadouble) {
		this.datadouble = datadouble;
	}

	/**
	 * �õ�double������
	 * 
	 * @return double double������
	 */
	public double getDataDouble() {
		return this.datadouble;
	}

	/**
	 * ����String������
	 * 
	 * @param datastr
	 *            String������
	 */
	public void setDataString(String datastr) {
		this.datastr = datastr;
	}

	/**
	 * �õ�String������
	 * 
	 * @return String String������
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
	 * ��õ�Ԫ�񱳾�ɫ��ֵ(Ϊ""��ʾʹ��ԭ��Ԫ��Ĭ�ϱ���ɫ)
	 * @return
	 */
	public String getBgColor() {
		return bgColor;
	}

	/**
	 * ���õ�Ԫ�񱳾�ɫ��ֵ(Ϊ""��ʾʹ��ԭ��Ԫ��Ĭ�ϱ���ɫ)
	 * @param bgColor
	 */
	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	/**
	 * ��õ�Ԫ��������ɫ��ֵ��Ϊ""��ʾʹ��ԭ��Ԫ��Ĭ��������ɫ��
	 * @return
	 */
	public String getFrColor() {
		return frColor;
	}

	/**
	 * ���õ�Ԫ��������ɫ��ֵ��Ϊ""��ʾʹ��ԭ��Ԫ��Ĭ��������ɫ��
	 * @param frColor
	 */
	public void setFrColor(String frColor) {
		this.frColor = frColor;
	}

	/**
	 * ��ȡ��Ŀ����
	 * @return 
	 */
	public int getItemType() {
		return itemType;
	}

	/**
	 * ���ÿ�Ŀ����
	 * @param itemType
	 */
	public void setItemType(int itemType) {
		this.itemType = itemType;
	}

	/**
	 * ��ȡ��չ�в�������
	 * @return int
	 */
	public int getInsertCount() {
		return insertCount;
	}

	public void setInsertCount(int insertCount) {
		this.insertCount = insertCount;
	}

	/**
	 * ��ȡ��չ�в���λ��
	 * @return
	 */
	public int getInsertPosition() {
		return insertPosition;
	}

	public void setInsertPosition(int insertPosition) {
		this.insertPosition = insertPosition;
	}

}
