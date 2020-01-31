package com.krm.ps.sysmanage.groupmanage.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.krm.ps.sysmanage.usermanage.dao.DictionaryDAO;
import com.krm.ps.sysmanage.usermanage.dao.UserDAO;
import com.krm.ps.sysmanage.usermanage.vo.Dictionary;
import com.krm.ps.sysmanage.usermanage.vo.Units;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.util.DBDialect;
import com.krm.ps.util.SysConfig;
import com.krm.ps.sysmanage.groupmanage.service.GroupManageService;

public class GroupManageServiceImpl implements GroupManageService{

	public GroupManageServiceImpl() {
		super();
	}
	
	UserDAO udao;
	DictionaryDAO ddao;
	
	public void setDictionaryDAO(DictionaryDAO ddao) {
		this.ddao = ddao;
	}
	
	public void setUserDAO(UserDAO udao) {
		this.udao = udao;
	}
	
	public void sop(String in){
		System.out.println(in);
	}
	
	/**
	 * 根据组号得到有多少用户属于该组
	 * @param dicid
	 * @return
	 */
	public List getGroupUsers(String dicid){
		
		//根据组号得知有多少用户在该组下
		return udao.getUsersByStatus(dicid);

	}
	
	/**
	 * 删除一个空分组（没有用户属于它）
	 * @param dicid
	 * @return 哪些用户属于它
	 */
	public List deleteGroupReport(String dicid){
		
		//根据组号得知有多少用户在该组下
		List exsitStr = udao.getUsersByStatus(dicid);
		//如果没有用户在该组下，将该组删除
		if(exsitStr.size() == 0){
			String deleteSql = "delete from code_dictionary where dicid = " + dicid 
			+ " and parentid = 2001";
			ddao.exeAnySql(deleteSql);
			//对用户报表权限表删除该组的所有报表权限
			udao.deleteUserReportsByUserId(new Long(90000000l + Long.parseLong(dicid)));

			//删除成功返回空ArrayList
			return new ArrayList();
		}
		

		
		return exsitStr;
	}
	
	/**
	 * 得到要插入的disporder,最大的disporder+1
	 * @param dicname
	 * @return
	 */
	public int getBigDisporder(String dicname){
		
		List groupList = ddao.getDictionaryByParentid("2001");
		Iterator it = groupList.iterator();

		//得到最大的display order
		int disporder = 0;
		while(it.hasNext()){
			Dictionary dic = (Dictionary)it.next();
			
			//得到最大的display order
			if(dic.getDisporder().intValue() > disporder){
				disporder = dic.getDisporder().intValue();
			}
		}
		disporder ++;
		return disporder;
	}
	
	/**
	 * 得到分组的个数
	 * @return
	 */
	public int countDicid()
	{
		List groupList = ddao.getDictionaryByParentid("2001");
		return groupList.size();
	}
	
	/**
	 * (选择从11~99之间没被占用的dicid)
	 * 得到没被占用过的dicid
	 * @return
	 */
	public int getFreeDicId(){
		
		List groupList = ddao.getDictionaryByParentid("2001");
		Iterator it = groupList.iterator();
		
		//选择从11~99之间没被占用的dicid
		int userDicid = -1;
		for(int i = 11;i <= 99;i++){
			//dicid是否被占用，占用为-1,没占用为1
			int times = -1;
			it = groupList.iterator();
			while(it.hasNext()){
				Dictionary dic = (Dictionary)it.next();
				int dicid = dic.getDicid().intValue();	
				if(dicid == i){
					times = 1;
					break;
					
				}
			}
			if(times == -1){
				userDicid = i;
				break;
			}
		}
		return  userDicid;
	}
	
	/**
	 * 向code_dictionary表中插一条新的分组信息
	 * @param dicname
	 * @return
	 */
	public int addGroupReport(String dicname,int dicid,int disporder){
		
		//向code_dictionary表中插一条新的分组信息
		
		//2007.4.10 兼容SqlServer数据库
		String insertDic = "";
		if ('s' == SysConfig.DB) {
			insertDic = "insert into code_dictionary(dicid,dicname,parentid,layer,isleaf,disporder,status) "
					+ " values("
					+ dicid
					+ ",'"
					+ dicname
					+ "',2001,2,'1',"
					+ disporder + ",'1')";
		} else {

			insertDic = "insert into code_dictionary(pkid,dicid,dicname,parentid,layer,isleaf,disporder,status) "
					+ " values("
					+ DBDialect.genSequence("code_dictionary_seq")
					+ ","
					+ dicid
					+ ",'"
					+ dicname
					+ "',2001,2,'1',"
					+ disporder + ",'1')";
		}
		try{
			ddao.exeAnySql(insertDic);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return 0;
	}
	
	/**
	 * 根据parenetid得到分组对象列表
	 * @param parentid
	 * @return
	 */
	public List getDictionaryByParentid(String parentid){
		return ddao.getDictionaryByParentid(parentid);
	}
	
	/**
	 * 更新分组名称
	 * @param dicid
	 * @param parentid
	 * @param dicname
	 */
	public void updateDicname(String dicid,String parentid,String dicname){
		ddao.exeAnySql("update code_dictionary set dicname = '" + dicname + "' where dicid = " + dicid + " and parentid = " + parentid );
	}
	

	public Integer getDicnameCount(String dicname, String dicid){
		return ddao.getDicnameCount( dicname,  dicid);
	}

}
