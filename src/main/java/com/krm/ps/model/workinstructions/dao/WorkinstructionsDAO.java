package com.krm.ps.model.workinstructions.dao;

import java.util.List;

import com.krm.ps.framework.dao.DAO;
import com.krm.ps.model.workinstructions.vo.Workaccessory;
import com.krm.ps.model.workinstructions.vo.Workcontext;
import com.krm.ps.model.workinstructions.vo.Workcontext1;
import com.krm.ps.model.workinstructions.vo.Workinstructions;






public interface WorkinstructionsDAO extends DAO 
{
	//查询树
	public List<Workinstructions> selectworkinstructiontree(); 
	
	//删除树
	public void deleteid(long pkid);
	
	//查询这个节点有没有下一层
	public List selectworksuperid(long pkid);
	
	//新增一个节点
	public void insertid(String name,long superid);
	
	//更新一个节点
	public void updateid(long pkid,String name);
	
	//查询这个节点的内容
	public List<Workcontext> selectcontext(long treeid);
	
	//页面一加载 默认查询置顶
	public List<Workcontext> selectstatus2();
	
	//修改置顶内容
	public void updatecontext(Workcontext w);
	
	//查询一个节点下的所有信息列表
	public List selectinfo(long pkid);
	
	//新增一个信息
	public void saveinfo(Workcontext w);
	
	//删除一个信息
	public void deleteinfo(long pkid);
	
	//修改前的查询 按 id查
	public List<Workcontext> queryinfo(long pkid);
	
	//修改信息状态״̬
	public void updatestatus(long pkid,int status,String updatetime);
	public void updatestatus1(int status,int status2,String updatetime);
	
	public void saveaccessory(Workaccessory w);
	
	
	public List queryaccessory(long pkid);
	
	public List queryaccessory1(long pkid);
	
	//删除附件
	public void deleteaccessory(long pkid);
	
	//按标题和内容组合查询
	public List<Workcontext1> selectinfo(String title,String context,int pageNo,int pageSize);
	//按标题和内容组合查询信息总数
	public int selectinfonum(String title,String context);
	
	public String selectname(long userid);
	
	
	
}