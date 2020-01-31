package com.krm.ps.model.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <p>
 * ������: DataCollect
 * <p>
 * ����: ��ʽ�����ݼ�����
 * <p>
 * ����ʱ��: 2006.4.17
 * 
 * @author nxd
 * @version v1.1 2006.4.17
 * @since �޸ļ�¼���D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D�D
 *        <p>
 *        �޸�ʱ�� �޸��� �޸ĵķ��� �޸ĵ�ԭ��
 *        <p>
 *        2006.5.1 Safe (a)removeDataFormula ���ɾ������
 */

public class DataCollect {

	/**
	 * ��ͨ��ʽ��־
	 */
	public static final int NORMAL_FLAG = 0;

	/**
	 * ��ʽ�������б�
	 */
	private List dflist = new ArrayList();

	/**
	 * ��ʽmap����߸��ݹ�ʽ���ҵ�Ч�ʣ�key��row#col����wsx 2006-11-09
	 */
	private Map dfMap = new HashMap();

	/**
	 * �ǹ�ʽ���ַ������ݣ��б����ڴ�ŷ���չ����Ŀ�Ŀ�����ַ���
	 */
	private ArrayList strlist = new ArrayList();// wsx 2006-10-25

	/**
	 * Ԥȡ��������б�Ϊ��ߴ�����ȡ�����������⣬����һ��IO��Ԥȡ�������
	 */
	private List preRepIDList = null;
	
	/**
	 * ���캯��
	 */
	public DataCollect() {

	}

	/**
	 * <p>
	 * ���������Ƽ�����: public int getCount()
	 * <p>
	 * ����������: �õ���ʽ�����ݶ������
	 * 
	 * @return int ��ʽ�����ݶ������
	 */
	public int getCount() {
		return dflist.size();
	}

	/**
	 * <p>
	 * ���������Ƽ�����: public void add(DataFormula dataformula)
	 * <p>
	 * ����������: ���ӹ�ʽ�����ݶ���
	 * 
	 * @param dataformula
	 *            ��ʽ�����ݶ���
	 */
	public void add(DataFormula dataformula) {
		if (dataformula != null) {
			dflist.add(dataformula);
			if (dataformula.getFormulaString() != null){
				dfMap.put(dataformula.getFormulaString(), dataformula);
			}
		}
	}

	/**
	 * ����dataformula
	 */
	public void add(DataFormula dataformula,int index) {//wsx 11-09
		if (dataformula != null) {
			dflist.add(index,dataformula);
			if (dataformula.getFormulaString() != null){
				dfMap.put(dataformula.getFormulaString(), dataformula);
			}
		}
	}

	/**
	 * <p>
	 * ���������Ƽ�����: public void clean()
	 * <p>
	 * ����������: ɾ�����й�ʽ�����ݶ���
	 */
	public void clean() {
		dflist.clear();
		dfMap.clear();
	}

	/**
	 * <p>
	 * ���������Ƽ�����: public DataFormula getDataFormula(int index)
	 * <p>
	 * ����������: ���������õ���ʽ�����ݶ���
	 * 
	 * @param index
	 *            ��ʽ�����ݶ��������
	 * @return DataFormula ��ʽ�����ݶ���
	 */
	public DataFormula getDataFormula(int index) {
		if (index < 0 || index >= dflist.size())
			return null;
		return (DataFormula) dflist.get(index);
	}

	/**
	 * ��������ȡ�ù�ʽ
	 * 
	 * @param row
	 * @param col
	 * @return
	 */
	public DataFormula getDataFormula(int row, int col) {

		DataFormula df = null;
		for (int i = 0; i < dflist.size(); i++) {
			df = (DataFormula) dflist.get(i);
			if (df.getRow() == row && df.getCol() == col) {
				break;
			} else {
				df = null;
			}
		}
		return df;

		// return (DataFormula) dfmap.get(row + "#" + col);// 
	}
	
	
	/**
	 * ���ݹ�ʽ���м���
	 * @param formula ��ʽ�ַ���
	 * @return DataFormula
	 */
	public DataFormula getDataFormula(String formula) {
		return (DataFormula) dfMap.get(formula);
	}
	
	/**
	 * ɾ�����й�ʽ
	 * @param row �к�
	 */
	public void removeRowDataFormula(int row){
		DataFormula df = null;
		for (int i = 0; i < dflist.size(); i++) {
			df = (DataFormula) dflist.get(i);
			if (df.getRow() == row) {
				dflist.remove(i);
				dfMap.remove(df.getFormulaString());
				i--;
			}
		}
	}
	
	/**
	 * �ж��Ƿ���ĳ�е�����
	 * @param row �к�
	 * @return boolean
	 */
	public boolean hasRowData(int row){
		DataFormula df = null;
		for (int i = 0; i < dflist.size(); i++) {
			df = (DataFormula) dflist.get(i);
			if (df.getRow() == row){
				return true;
			}
		}
		return false;
	}

	/**
	 * <p>
	 * ���������Ƽ�����: public void removeDataFormula(int index)
	 * <p>
	 * ����������: ��������ɾ����ʽ�����ݶ���
	 * 
	 * @param index
	 *            ��ʽ�����ݶ��������
	 * @return void
	 */
	public void removeDataFormula(int index) {
		if (index < 0 || index >= dflist.size())
			return;
		DataFormula df = getDataFormula(index);
		dflist.remove(index);
		if (df != null) {
			dfMap.remove(df.getFormulaString());
		}
	}

	/**
	 * ����һ����Ԫ�񣨷ǹ�ʽ���ַ���ֵ
	 * 
	 * @param dataformula
	 */
	public void addCellStr(DataFormula dataformula) {// wsx 2006-10-25
		strlist.add(dataformula);
	}

	/**
	 * 
	 * @return �ǹ�ʽ���ַ������ݣ��б����ڴ�ŷ���չ����Ŀ�Ŀ�����ַ���
	 */
	public ArrayList getStrlist() {// wsx 2006-10-25
		return strlist;
	}

	public String toString() {
		return dflist.toString();
	}

	/**
	 * ��ȡԤȡ��������б� 
	 * @return List
	 */
	public List getPreRepIDList() {
		return preRepIDList;
	}

	/**
	 * ����Ԥȡ��������б�
	 * @param preRepIDList
	 */
	public void setPreRepIDList(List preRepIDList) {
		this.preRepIDList = preRepIDList;
	}

	public List getDflist() {
		return dflist;
	}

	public Map getDfMap() {
		return dfMap;
	}

	public void setDflist(List dflist) {
		this.dflist = dflist;
	}

	public void setDfMap(Map dfMap) {
		this.dfMap = dfMap;
	}

	
}
