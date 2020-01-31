package com.krm.ps.sysmanage.groupmanage.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.sysmanage.groupmanage.service.GroupManageService;
import com.krm.ps.sysmanage.groupmanage.service.GroupSortService;
import com.krm.ps.sysmanage.usermanage.services.DictionaryService;
import com.krm.ps.sysmanage.usermanage.services.UserService;
import com.krm.ps.sysmanage.usermanage.vo.Dictionary;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.sysmanage.usermanage.vo.UserReport;
import com.krm.ps.util.FuncConfig;

import com.krm.ps.sysmanage.groupmanage.web.form.GroupReportForm;
import com.krm.ps.sysmanage.reportdefine.services.ReportDefineService;
import com.krm.ps.sysmanage.reportdefine.vo.ReportType;


/**
*
* @struts.action name="groupReportForm" path="/groupReportAction" scope="request" 
*  validate="false" parameter="method"
*  
* @struts.action-forward name="list" path="/groupmanage/groupManageList.jsp" 
* @struts.action-forward name="editGroupReport" path="/groupmanage/groupReport.jsp"
* @struts.action-forward name="addGroupReport" path="/groupmanage/addGroupReport.jsp"
* @struts.action-forward name="deleteError" path="/groupmanage/deleteError.jsp"
* @struts.action-forward name="showGroupUsers" path="/groupmanage/showGroupUsers.jsp"
* @struts.action-forward name="showGroupPurview" path="/groupmanage/showGroupPurview.jsp"
* @struts.action-forward name="sort" path="/common/sortcommon.jsp"
* @struts.action-forward name="sort_list" path="/groupReportAction.do?method=list"
*/
public class GroupReportAction extends BaseAction{
	
	private static String GROUPID = "dicid";
	private static String GROUP_LIST = "groupList";
    GroupManageService gms = (GroupManageService) this.getBean("groupManageService");
	private Long getUserId(HttpServletRequest request){
		if(null!=request.getParameter(GROUPID)){
			return new Long(request.getParameter(GROUPID));
		}
		return null;
	}
	
	public void setToken(HttpServletRequest request){
		saveToken(request);
	}
	
	public boolean tokenValidatePass(HttpServletRequest request){
		if (!isTokenValid(request)) {
			saveToken(request);
			return false;
		}
		resetToken(request);
		return true;
	}
	
	
	public ActionForward showGroupPurview(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'showGroupPurview' method");
		}

		UserService us = getUserService();
		ReportDefineService rds = this.getReportDefineService();
        
        
		//得到分组编号用于计算份组编号在rep_oper_contrast里的operid
		long dicid = Long.valueOf(request.getParameter("dicid")).longValue() + 90000000;

		Long userid = new Long(dicid);
		
        List purviewReports = new ArrayList();
        List userReports = us.getUserReportsByUserID(userid);
        List reports = us.getUserReports("1",new Long(1),null);
        if(userReports!=null){
        	for(int i=0;i<reports.size();i++){
        		UserReport up1 = (UserReport)reports.get(i);
        		for(int j=0;j<userReports.size();j++){      			
        			UserReport up2 =(UserReport)userReports.get(j);
        			if(up1.getReportId().longValue()==up2.getReportId().longValue()){
                        up1.setStatus(1);
                        purviewReports.add(up1);
        				break;
        			}
        		}
        	}
        }
        
        List types = rds.getAllReportTypes(null);
        if(types!=null){
        	ReportType type = (ReportType)types.get(0);
            request.setAttribute("typeid",type.getPkid().toString());
        }
        request.setAttribute("dicid",request.getParameter("dicid"));
        request.setAttribute("dicname",request.getParameter("dicname"));
        request.setAttribute("userReports",purviewReports);
        
        return mapping.findForward("showGroupPurview");
	}
	
	/**
	 * 返回该组下用户的列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward showGroupUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'showGroupUser' method");
		}
		String dicid = (String)request.getParameter("dicid");
        GroupManageService gms = (GroupManageService) this.getBean("groupManageService");
        List groupUsers = gms.getGroupUsers(dicid);

        DictionaryService ds=getDictionaryService();
        Dictionary groupDic=ds.getDictionary(2001,Integer.parseInt(dicid));
        //request.setAttribute("dicname",dicname);
        request.setAttribute("dicid",request.getParameter("dicid"));
        request.setAttribute("dicname",groupDic.getDicname());
        request.setAttribute("groupUsers",groupUsers);
        return mapping.findForward("showGroupUsers");
		
	}
	
	/**
	 * 如果该组下无任何用户，将该组删除，否则将删除失败，并将所属用户id列出
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public ActionForward deleteGroupReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'deleteGroupReport' method");
		}
		setToken(request);

		String dicid = (String)request.getParameter("dicid");
        GroupManageService gms = (GroupManageService) this.getBean("groupManageService");
        GroupSortService gss = (GroupSortService) this.getBean("groupSortService");
        List deleteGroup = gms.deleteGroupReport(dicid);
        
        if( deleteGroup.size() == 0){
        	 
        	//删除后给disporder排序
            List groups = gms.getDictionaryByParentid("2001");
            gss.sortInner(groups);
    		
    		return list(mapping,form,request,response);
        }
        	request.setAttribute("userUnderGroup",deleteGroup);
        	return mapping.findForward("deleteError");

	}
	
	
	/**
	 * 增加一条分组信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public ActionForward addGroupReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'addGroupReport' method");
		}
		setToken(request);

		ReportDefineService rds = this.getReportDefineService();

		//得到报表类型
		List types = rds.getAllReportTypes(null);
        if(types!=null){
        	ReportType type = (ReportType)types.get(0);
            request.setAttribute("typeid",type.getPkid().toString());
        }
        Map reportsMap=new HashMap();
        //得到报表
		UserService us = getUserService();
        List reports = us.getUserReports("1",new Long(1),null);
        
        //return dicid count more than 88
		GroupManageService gms = (GroupManageService) this.getBean("groupManageService");
		Integer countDicid = new Integer(gms.countDicid());
        
		request.setAttribute("dicidCount",String.valueOf(countDicid));
        request.setAttribute("types",types);
        request.setAttribute("userReports",reports);
        request.getSession().setAttribute("reportsMap",reportsMap);
        return mapping.findForward("addGroupReport");
	}
	
	public void sop(String s){
		System.out.println(s);
	}
	
	
	/**
	 * 编辑组的报表权限
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editGroupReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'editGroupReport' method");
		}
		setToken(request);
        GroupReportForm urform= new GroupReportForm();	

		UserService us = getUserService();
		ReportDefineService rds = this.getReportDefineService();
		
		String dicidstr=request.getParameter("dicid");
		//得到分组编号用于计算份组编号在rep_oper_contrast里的operid
		long dicid = Long.valueOf(dicidstr).longValue() + 90000000;

		Long userid = new Long(dicid);

		urform.setUserid(userid);
		String dicname = (String)request.getParameter("dicname");
		urform.setUserName(dicname);
		String levelFlag=request.getParameter("levelFlag");
        List userReports = us.getUserReportsByUserID(userid);
        List reports = us.getUserReports("1",new Long(1),levelFlag);
        Map reportsMap=new HashMap();
        if(userReports!=null){
        	for(int i=0;i<reports.size();i++){
        		UserReport up1 = (UserReport)reports.get(i);
        		//up1.setOperName(user.getName());
        		for(int j=0;j<userReports.size();j++){      			
        			UserReport up2 =(UserReport)userReports.get(j);
        			if(up1.getReportId().longValue()==up2.getReportId().longValue()){
                        up1.setStatus(1);
                        reportsMap.put(String.valueOf(up1.getReportId()), up1.getTypeId()+"@"+up1.getReportId());
        				break;
        			}
        		}
        	}
        }
        
        List types = rds.getAllReportTypes(null);
        if(types!=null){
        	ReportType type = (ReportType)types.get(0);
            request.setAttribute("typeid",type.getPkid().toString());
        }
        DictionaryService ds=getDictionaryService();
        Dictionary groupDic=ds.getDictionary(2001,Integer.parseInt(dicidstr));
        request.setAttribute("dicid",request.getParameter("dicid"));
        request.setAttribute("dicname",groupDic.getDicname());
        request.setAttribute("userid",userid);
        request.setAttribute("types",types);
        request.setAttribute("userReports",reports);
        request.getSession().setAttribute("reportsMap",reportsMap);
		return mapping.findForward("editGroupReport");
	}
	
	/**
	 * 保存组的报表权限
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveGroupReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if (log.isDebugEnabled()) {
			log.info("Entering 'saveGroupReport' method");
		}
		UserService us = this.getUserService();
		GroupReportForm urform = (GroupReportForm) form;
		
		//update dicname
		Long dicid = urform.getDicid();
		String dicname = urform.getDicname();
		
		GroupManageService gms = (GroupManageService) this.getBean("groupManageService");
		Map reportsMap=(Map)request.getSession().getAttribute("reportsMap");
		int returnDicid = gms.getFreeDicId();
		if (isTokenValid(request, true)) { 
			if(dicid == null){
				//插入新分组dic
				saveToken(request);
				int disporder = gms.getBigDisporder(dicname);
	
				gms.addGroupReport(dicname,returnDicid,disporder);
				
				//修改组的报表权限:
				String typeReports[]=request.getParameterValues("yy");
				if(typeReports!=null){
					for(int i=0;i<typeReports.length;i++){
						
						String infos[]=typeReports[i].split("@");
						if(reportsMap.containsKey(infos[1]) != true){
							UserReport ur = new UserReport();
							ur.setOperId(new Long(returnDicid + 90000000l));
						    ur.setTypeId(new Long(infos[0]));
							ur.setReportId(new Long(infos[1]));
						    us.saveUserReport(ur);
						}
					}
					Iterator iterator = reportsMap.entrySet().iterator();            
					 while (iterator.hasNext()) { 
					     Entry entry = (Entry) iterator.next(); 
					     Object key =entry.getKey(); 
					     String value[]=entry.getValue().toString().split("@");
					     boolean mark=true;
					     for(int a=0;a<typeReports.length;a++){
								String infos[]=typeReports[a].split("@");
								if(key.equals(infos[1])){
									mark=false;
								}
					     }
					     if(mark){
					    	
					    	 us.deleteUserReportsByUserId(new Long(value[1]),new Long(value[0]) , new Long(returnDicid + 90000000l));
					     }
					} 
				}
	
			}else if(dicid != null){
				//更新分组dic
				gms.updateDicname(String.valueOf(dicid),"2001",dicname);
				us.deleteUserReportsByUserId(urform.getUserid());
				
				//修改组的报表权限:
				//BY Shengping 修改时不选择任何模板的时候报空指针异常
				String typeReports[]=request.getParameterValues("yy");
				if(typeReports!=null){
					for(int i=0;i<typeReports.length;i++){
						String infos[]=typeReports[i].split("@");
						UserReport ur = new UserReport();
						ur.setOperId(urform.getUserid());
					    ur.setTypeId(new Long(infos[0]));
						ur.setReportId(new Long(infos[1]));
					    us.saveUserReport(ur);
					}
				
				Iterator iterator = reportsMap.entrySet().iterator();            
				 while (iterator.hasNext()) { 
				     Entry entry = (Entry) iterator.next(); 
				     Object key =entry.getKey(); 
				     String value[]=entry.getValue().toString().split("@");
				     boolean mark=true;
				     for(int a=0;a<typeReports.length;a++){
							String infos[]=typeReports[a].split("@");
							if(key.equals(infos[1])){
								mark=false;
							}
				     }
				     if(mark){
				    	 us.deleteUserReportsByUserId(new Long(value[1]),new Long(value[0]) , urform.getUserid());
				     }
				} 
				}
				//关联用户的报表权限（选删除后增加）
				//得到要操作的用户列表
		        List groupUsers = gms.getGroupUsers(String.valueOf(dicid));
		        
		        Iterator itrs = groupUsers.iterator();
		        while(itrs.hasNext()){
		        	User user1 = (User)itrs.next();
		        	us.deleteAndAddUserPurview(user1.getPkid(),dicid);
		        	
		        }
		        
			}
		}else { 
			
			saveToken(request); 
		} 
		

		
		
		return list(mapping,form,request,response);
	}
	
	/**
	 * 返回显示分组维护界面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.info("Entering 'list' method");
		}
		GroupManageService gms = (GroupManageService) this.getBean("groupManageService");
		List dics = gms.getDictionaryByParentid("2001");
		//getDictionarysByParentid
		//zsj 增加 2007-12-17 启用智能流程，给角色用户授权其可以查看的流程
		//gyl 修改 2007-12-27 默认情况下不开启智能流程功能
		if("yes".equals(FuncConfig.getProperty("flowtip.enable", "no"))){
			request.setAttribute("flowTip","1");
		}
		///////////////////////////////////////////////////////
		request.setAttribute(GROUP_LIST,dics);
		return mapping.findForward("list");
	}


	/**
	 * 分组排序
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward taxisGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		if (log.isDebugEnabled()) {
			log.info("Entering 'taxisGroup' method");
		}
		setToken(request);
		//sort 调用系统的排序功能
        List groups = gms.getDictionaryByParentid("2001");
		ArrayList al = new ArrayList();
		Iterator it = groups.iterator();
		while(it.hasNext()){
			Object [] o = new Object [2];
			Dictionary u = (Dictionary)it.next();
			o[0] = u.getPkid();
			o[1] = u.getDicname();
			al.add(o);
		}
		request.setAttribute("fileTitle","系统管理－》分组排序");
		request.setAttribute("sortList",al);
		request.setAttribute("serviceName", "groupSortService");
		request.setAttribute("sortTitle", "分组排序");
		ActionForward forward = mapping.findForward("sort_list");
		String path = forward.getPath();
		request.setAttribute("forwardURL", path);
		return mapping.findForward("sort");
		
	}
	
	public ActionForward checkgroupName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
			String dicname = (String)request.getParameter("dicname");
			String dicid = (String)request.getParameter("dicid");
			Integer it =gms.getDicnameCount(dicname,dicid);
			
			response.setContentType("text/html; charset=UTF-8");
			response.getWriter().write(it.toString());
			response.getWriter().flush();
			response.getWriter().close();
		
		return null;
	}
}
