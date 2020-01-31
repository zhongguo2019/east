package com.krm.ps.model.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <p>
 * 类名称: DataCollect
 * <p>
 * 功能: 公式及数据集合类
 * <p>
 * 创建时间: 2006.4.17
 * 
 * @author nxd
 * @version v1.1 2006.4.17
 * @since 修改记录：――――――――――――――――――――――――――
 *        <p>
 *        修改时间 修改者 修改的方法 修改的原因
 *        <p>
 *        2006.5.1 Safe (a)removeDataFormula 添加删除方法
 */

public class DataCollect {

	/**
	 * 普通公式标志
	 */
	public static final int NORMAL_FLAG = 0;

	/**
	 * 公式及数据列表
	 */
	private List dflist = new ArrayList();

	/**
	 * 公式map，提高根据公式查找的效率（key是row#col）。wsx 2006-11-09
	 */
	private Map dfMap = new HashMap();

	/**
	 * 非公式（字符串数据）列表，用于存放非扩展报表的科目、栏字符串
	 */
	private ArrayList strlist = new ArrayList();// wsx 2006-10-25

	/**
	 * 预取报表编码列表，为提高大批量取数的性能问题，设置一次IO的预取报表编码
	 */
	private List preRepIDList = null;
	
	/**
	 * 构造函数
	 */
	public DataCollect() {

	}

	/**
	 * <p>
	 * 方法的名称及参数: public int getCount()
	 * <p>
	 * 方法的描述: 得到公式及数据对象个数
	 * 
	 * @return int 公式及数据对象个数
	 */
	public int getCount() {
		return dflist.size();
	}

	/**
	 * <p>
	 * 方法的名称及参数: public void add(DataFormula dataformula)
	 * <p>
	 * 方法的描述: 增加公式及数据对象
	 * 
	 * @param dataformula
	 *            公式及数据对象
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
	 * 插入dataformula
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
	 * 方法的名称及参数: public void clean()
	 * <p>
	 * 方法的描述: 删除所有公式及数据对象
	 */
	public void clean() {
		dflist.clear();
		dfMap.clear();
	}

	/**
	 * <p>
	 * 方法的名称及参数: public DataFormula getDataFormula(int index)
	 * <p>
	 * 方法的描述: 根据索引得到公式及数据对象
	 * 
	 * @param index
	 *            公式及数据对象的索引
	 * @return DataFormula 公式及数据对象
	 */
	public DataFormula getDataFormula(int index) {
		if (index < 0 || index >= dflist.size())
			return null;
		return (DataFormula) dflist.get(index);
	}

	/**
	 * 根据行列取得公式
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
	 * 根据公式进行检索
	 * @param formula 公式字符串
	 * @return DataFormula
	 */
	public DataFormula getDataFormula(String formula) {
		return (DataFormula) dfMap.get(formula);
	}
	
	/**
	 * 删除整行公式
	 * @param row 行号
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
	 * 判断是否有某行的数据
	 * @param row 行号
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
	 * 方法的名称及参数: public void removeDataFormula(int index)
	 * <p>
	 * 方法的描述: 根据索引删除公式及数据对象
	 * 
	 * @param index
	 *            公式及数据对象的索引
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
	 * 增加一个单元格（非公式）字符串值
	 * 
	 * @param dataformula
	 */
	public void addCellStr(DataFormula dataformula) {// wsx 2006-10-25
		strlist.add(dataformula);
	}

	/**
	 * 
	 * @return 非公式（字符串数据）列表，用于存放非扩展报表的科目、栏字符串
	 */
	public ArrayList getStrlist() {// wsx 2006-10-25
		return strlist;
	}

	public String toString() {
		return dflist.toString();
	}

	/**
	 * 获取预取报表编码列表 
	 * @return List
	 */
	public List getPreRepIDList() {
		return preRepIDList;
	}

	/**
	 * 设置预取报表编码列表
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
