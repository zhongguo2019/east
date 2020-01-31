package com.krm.ps.sysmanage.groupmanage.service;

import java.util.List;

import com.krm.ps.framework.common.dictionary.dao.DictionaryDAO;
import com.krm.ps.framework.common.dictionary.vo.Dictionary;

public interface GroupManageService {

	/**
	 * 根据组号得到有多少用户属于该组
	 * @param dicid
	 * @return
	 */
	public List getGroupUsers(String dicid);
	
	/**
	 * 根据parenetid得到分组对象列表
	 * @param parentid
	 * @return
	 */
	public List getDictionaryByParentid(String parentid);

	/**
	 * 向code_dictionary表中插一条新的分组信息(选择从11~99之间没被占用的dicid)
	 * @param dicname
	 * @return
	 */
	public int addGroupReport(String dicname,int dicid,int disporder);
	
	/**
	 * 删除一个空分组（没有用户属于它）
	 * @param dicid
	 * @return 哪些用户属于它
	 */
	public List deleteGroupReport(String dicid);
	
	/**
	 * 更新分组名称
	 * @param dicid
	 * @param parentid
	 * @param dicname
	 */
	public void updateDicname(String dicid,String parentid,String dicname);
	
	/**
	 * 得到没被占用过的dicid
	 * @return
	 */
	public int getFreeDicId();
	
	/**
	 * 得到要插入的disporder,最大的disporder+1
	 * @param dicname
	 * @return
	 */
	public int getBigDisporder(String dicname);
	
	/**
	 * 得到分组的个数
	 * @return
	 */
	public int countDicid();
	
	public Integer getDicnameCount(String dicname, String dicid);
}
