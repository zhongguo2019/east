package com.krm.ps.sysmanage.organmanage.web.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.krm.ps.sysmanage.organmanage2.services.OrganTreeManager;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.sysmanage.organmanage.services.OrganService;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.organmanage.web.form.OrganForm;
import com.krm.ps.sysmanage.organmanage2.services.AreaService;
import com.krm.ps.sysmanage.usermanage.services.DictionaryService;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.util.Constants;
import com.krm.ps.util.ConvertUtil;

/**
*
* @struts.action name="organForm" path="/organAction" scope="request" 
*  validate="false" parameter="method" input="edit"
*  
* @struts.action-forward name="edit" path="/organmanage/organForm.jsp"
* @struts.action-forward name="list" path="/organmanage/organList.jsp"
*  @struts.action-forward name="list" path="/organmanage/syncOrgan.jsp"
* @struts.action-forward name="sort" path="/common/sortcommon.jsp"
* @struts.action-forward name="sort_list" path="/organAction.do?method=list"
*/

public class OrganAction extends BaseAction{
	
	private static String ORGANID = "organid";
	private static String ORGAN_LIST = "organList";
	private static String TYPE_LIST = "typeList";
	private static String BTYPE_LIST = "btypeList";
	private int savepro = 2;
	
	private Long getOrganId(HttpServletRequest request){
		if(null!=request.getParameter(ORGANID)){
			return new Long(request.getParameter(ORGANID));
		}
		return null;
	}
	
	public ActionForward toEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.info("Entering 'toEdit' method");
		}
		setToken(request);
		DictionaryService ds = this.getDictionaryService();
		//得到机构类型信息
		request.setAttribute(TYPE_LIST,ds.getOrgansort());
		//得到业务类型信息
		request.setAttribute(BTYPE_LIST,ds.getOrganoperationsort());
		if(savepro==1){
			request.setAttribute("error","-1");
			savepro = 2;
		}else if (savepro == -1){
			request.setAttribute("error","-2");
			savepro = 2;
		}else if (savepro == -2){
			request.setAttribute("error","-3");
			savepro = 2;
		}
		return mapping.findForward("edit");
	}
	
	//机构信息列表
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.info("Entering 'list' method");
		}
		if (!validateUser(mapping, request)) {
				return mapping.findForward("loginpage");
			}
		String error2 = (String)request.getSession().getAttribute("error4");
		if (error2 != null) {
			if (error2.equals("-1")) {
				request.setAttribute("error2", "-1");
				request.getSession().removeAttribute("error4");

			}
		}
		
		/*列出登录用户所属机构的所在地区(不含下各级地区)的所属机构*/	
		AreaService areaService = (AreaService)getBean("areaService");
		String areaCode = "";
		String rootId = areaService.getAreaCodeByUser(getUser(request).getPkid().intValue());
		if(request.getParameter("areaCode") == null){
			areaCode = areaService.getAreaCodeByUser(getUser(request).getPkid().intValue());
		}else{
			areaCode = request.getParameter("areaCode");
		}
		String orgcode = request.getParameter("orgcode");
		String short_name = request.getParameter("short_name");
		request.setAttribute("orgcode",orgcode);
		request.setAttribute("short_name",short_name);
		request.setAttribute("areaCode",areaCode);
		request.setAttribute("rootId",rootId);
		List organList = areaService.getOrgansByArea(areaCode,orgcode,short_name);
		if(organList!=null){
		   for(int i=0;i<organList.size();i++){
		       OrganInfo organ = (OrganInfo)organList.get(i);
		       String fdate = organ.getBegin_date();
		       String tdate = organ.getEnd_date();
		       String s ="-";
		       if(fdate!=null){
		       organ.setBegin_date(fdate.substring(0,4)+s+fdate.substring(4,6)+s+fdate.substring(6,8));
		       }
		       if(tdate!=null){
		       organ.setEnd_date(tdate.substring(0,4)+s+tdate.substring(4,6)+s+tdate.substring(6,8));
		       }
            }
		}
        request.setAttribute(ORGAN_LIST,organList);
		return mapping.findForward("list");
	}

	//编辑机构表单
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'edit' method");
		}
		OrganForm organForm = (OrganForm)form;
		OrganInfo organ = new OrganInfo();
		OrganService os = getOrganService();
		Long organid = getOrganId(request);
		if(organid!=null){
			organ =os.getOrgan(organid);
			organ.setPostalcode(organ.getPostalcode()== null?null:organ.getPostalcode().trim());
			ConvertUtil.copyProperties(organForm,organ,true);
		}else{
			//新建机构时设置地区代码
			String areaCode = request.getParameter("areaCode");
			organForm.setSuper_id(areaCode);
		}
		String fdate = organForm.getBegin_date();
		String tdate = organForm.getEnd_date();
		String s = "-";
		//设置有效日期
		if(fdate!=null){
			organForm.setBegin_date(fdate.substring(0,4)+s+fdate.substring(4,6)+s+fdate.substring(6,8));
		}
		else{
			fdate ="1999-12-31";
			organForm.setBegin_date(fdate);
		}
		if(tdate!=null){
		    organForm.setEnd_date(tdate.substring(0,4)+s+tdate.substring(4,6)+s+tdate.substring(6,8));
		}
		else{
			organForm.setEnd_date("2015-01-01");
		}
		
		return toEdit(mapping,organForm,request,response);
	}
	
	//保存机构信息
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'save' method");
		}
		if(!tokenValidatePass(request)){
			return list(mapping,form,request,response);
		}
		OrganService os = getOrganService();
		OrganForm organForm = (OrganForm)form;
		OrganInfo organ = new OrganInfo();
		String codeBeforeChange = null;
		if(organForm.getBegin_date()!=null){
			String begindate=organForm.getBegin_date().replaceAll("-","");
			organForm.setBegin_date(begindate);
		}
		if(organForm.getEnd_date()!=null){
			String enddate=organForm.getEnd_date().replaceAll("-","");
			organForm.setEnd_date(enddate);
		}
		if(organForm.getPkid()!=null){
			codeBeforeChange = os.getOrgan(organForm.getPkid()).getCode();
		}
		ConvertUtil.copyProperties(organ, organForm, true);
		
		//设定机构的状态位、建立时间、创建人
		User user=getUser(request);
		organ.setStatus(Constants.STATUS_AVAILABLE);
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		organ.setCreate_date(sdf.format(new Date()));
		String creator = user.getPkid().toString();		
		organ.setCreator(new Integer(creator));
		//设定虚实机构类型及是否顶层机构
		organ.setDummy_type("1");
		organ.setPeak("0");

		if(organ.getPkid()!=null){
			//如果修改前的机构代码已经有相关的数据记录或日志记录则不能修改
			if(!codeBeforeChange.equals(organForm.getCode()) && os.isOrganUsed(codeBeforeChange)){
				savepro=-1;
				return toEdit(mapping,form,request,response);
			}
		}
		
		int i = os.saveOrgan(organ,codeBeforeChange);

		if(codeBeforeChange!=null){
			if(!codeBeforeChange.equals(organ.getCode())){
				if(user.getOrganId().equals(codeBeforeChange)){
					user.setOrganId(organ.getCode());
					request.getSession().setAttribute("user",user);
				}
			}
		}
		if(i==1){
			savepro=1;
			return toEdit(mapping,form,request,response);
		}
		//return list(mapping,form,request,response);
		response.sendRedirect(request.getContextPath()
				+ "/organAction.do?method=list&areaCode="+organ.getSuper_id());
		return null;
	}
	
	//删除机构信息
	public ActionForward del(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'del' method");
		}
		OrganService os = getOrganService();
		User user = getUser(request);
		Long organid = getOrganId(request);
		OrganInfo organ = os.getOrgan(organid);
		// 判断所选文件的机构号是否和用户自身机构号一致
		if (user.getOrganId().equals(organ.getCode())) {
			request.getSession().setAttribute("error4","-1");
			response.sendRedirect(request.getContextPath()
					+ "/organAction.do?method=list");
			return null;
		} else {
			if (null != organid) {
				OrganInfo oi = os.getOrgan(organid);
				/*if(os.isOrganUsed(oi.getCode())){
					//如果修改前的机构代码已经有相关的数据记录或日志记录则不能删除
					request.setAttribute("error","-3");
					return list(mapping,form,request,response);
				}*/
				List treeNameList = os.isOrganInTree(oi.getCode());
				if(treeNameList.size() > 0){
					//如果机构挂在一棵树上则不能删除
					request.setAttribute("error","-4");
					String treeList = "";
					for(int i = 0; i < treeNameList.size(); i++){
						treeList = treeList.concat((String)treeNameList.get(i));
						if(i < treeNameList.size() - 1){
							treeList = treeList.concat(",");
						}
					}
					request.setAttribute("treeNames",treeList);
					return list(mapping,form,request,response);
				}
				os.removeOrgan(organid);
			}
			response.sendRedirect(request.getContextPath()
					+ "/organAction.do?method=list&areaCode="+request.getParameter("areaCode"));
			return null;
		}
	}
	
	public ActionForward sort(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'sort' method");
		}
		setToken(request); //避免重复提交
		OrganService os = getOrganService();
		List ls = new ArrayList();
		User user=getUser(request);
		String date = (String)request.getSession().getAttribute("logindate");
		List organList = os.getSubOrgansInArea(user.getOrganId(), date.replaceAll("-",""));	
		if(organList!=null){
		for(int i=0;i<organList.size();i++){
			Object[] o = new Object[2];
			o[0]=((OrganInfo)organList.get(i)).getPkid();
			o[1]=((OrganInfo)organList.get(i)).getShort_name()+"("+((OrganInfo)organList.get(i)).getCode()+")";
            ls.add(o);
		}}
		request.setAttribute("fileTitle","系统管理－》机构排序");
		request.setAttribute("sortList",ls);
		request.setAttribute("serviceName","organSortService");
		request.setAttribute("sortTitle","机构排序");
		ActionForward forward = mapping.findForward("sort_list");
		request.setAttribute("forwardURL",forward.getPath());
		return mapping.findForward("sort");
	}
	 
}
