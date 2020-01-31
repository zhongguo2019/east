package com.krm.ps.sysmanage.usermanage.web.action;

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
import com.krm.ps.sysmanage.usermanage.services.UserService;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.sysmanage.usermanage.vo.UserReport;
import com.krm.ps.sysmanage.usermanage.web.form.UserReportForm;
import com.krm.ps.sysmanage.reportdefine.services.ReportDefineService;
import com.krm.ps.sysmanage.reportdefine.vo.ReportType;


/**
*
* @struts.action name="userReportForm" path="/userReportAction" scope="request" 
*  validate="false" parameter="method"
*  
* @struts.action-forward name="list" path="/usermanage/userList.jsp"
* @struts.action-forward name="editUserReport" path="/usermanage/userReport.jsp"
* @struts.action-forward name="list" path="/usermanage/userList.jsp"
*/
public class UserReportAction extends BaseAction{
	
	private static String USERID = "userid";
	
	private Long getUserId(HttpServletRequest request){
		if(null!=request.getParameter(USERID)){
			return new Long(request.getParameter(USERID));
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
	
	public ActionForward editUserReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		 if (log.isDebugEnabled()) {
				log.info("Entering 'editUserReport' method");
			}
		setToken(request);
		request.setAttribute("areaCode", request.getParameter("areaCode"));
        UserReportForm urform= new UserReportForm();	
        User user1 = getUser(request);
        String date = (String)request.getSession().getAttribute("logindate");
        User user = new User();
		UserService us = getUserService();
		ReportDefineService rds = this.getReportDefineService();
		//String userid1=request.getParameter("userid");
		//System.out.println("-----"+request.getAttribute("userid"));
		//Long userid = (Long) (Long.parseLong((String) request.getAttribute("userid")));
		Long userid = (Long) (Long.parseLong((String) request.getParameter("userid"))) ;
		String showLevel=(String) request.getParameter("showlevel");
		if(showLevel==null) showLevel="0";
		if(userid==null){
		   userid = getUserId(request);
		}
		if(null!=userid){
			user = us.getUser(userid);
		}
		urform.setUserid(userid);
		urform.setUserName(user.getName());
        List userReports = us.getUserReportsByUserID(userid);
        List reports = us.getUserReports(date.replaceAll("-",""),user1.getPkid(),showLevel);
        Map reportsMap=new HashMap();
        if(userReports!=null){
        	for(int i=0;i<reports.size();i++){
        		UserReport up1 = (UserReport)reports.get(i);
        		up1.setOperName(user.getName());
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
        
        List types = rds.getAllReportTypes(showLevel);
        if(types!=null){
        	ReportType type = (ReportType)types.get(0);
            request.setAttribute("typeid",type.getPkid().toString());
        }
        request.setAttribute("userid",userid);
        request.setAttribute("usersname", user.getName());
        request.setAttribute("types",types);
        request.setAttribute("showlevel",showLevel);
        request.getSession().setAttribute("reportsMap",reportsMap);
        request.setAttribute("userReports",reports);
		return mapping.findForward("editUserReport");
	}
	
	public ActionForward saveUserReport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'saveUserReport' method");
		}
		UserReportForm urform = (UserReportForm) form;
		UserService us = this.getUserService();
		String typeReports[]=request.getParameterValues("yy");
		Map reportsMap=(Map)request.getSession().getAttribute("reportsMap");
		String organlevel=request.getParameter("isshoworgan");
		String username=request.getParameter("usersname");
		List <UserReport>userreportlist=new ArrayList();
		List <User>userlist=null;
		//0代表本级机构
		if("0".equals(organlevel)){
			userlist=new ArrayList();
			if(typeReports!=null){
				User user=new User();
				user.setPkid(urform.getUserid());
				userlist.add(user);
				for(int i=0;i<typeReports.length;i++){
					    String infos[]=typeReports[i].split("@");
						UserReport ur = new UserReport();
						ur.setOperId(urform.getUserid());
					    ur.setTypeId(new Long(infos[0]));
						ur.setReportId(new Long(infos[1]));
						userreportlist.add(ur);
				}
			}

			}else{
				userlist=us.getUserList(username);
				if(typeReports!=null){
					for(int j=0;j<userlist.size();j++){
						User u=(User) userlist.get(j);
					for(int i=0;i<typeReports.length;i++){
						String infos[]=typeReports[i].split("@");
							UserReport ur = new UserReport();
							ur.setOperId(u.getPkid());
						    ur.setTypeId(new Long(infos[0]));
							ur.setReportId(new Long(infos[1]));
							userreportlist.add(ur);
						
					}
				}
			}
		}
		us.DelsaveUserReport(userlist, userreportlist);
		request.setAttribute("userid",urform.getUserid());
		request.setAttribute("showlevel", "0");
		request.setAttribute("organlevel", organlevel);
		request.setAttribute("message", "1");
		return editUserReport(mapping,form,request,response);
	}
	
	
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.info("Entering 'list' method");
		}
		UserService us = getUserService();
		User user = getUser(request);
		request.setAttribute("userList",us.getUsersByOrgan(user.getOrganId()));
		return mapping.findForward("list");
	}

}

