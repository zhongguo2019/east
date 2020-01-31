package com.krm.ps.sysmanage.organmanage.web.action;

import java.util.Iterator;
import java.util.List;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.sysmanage.organmanage.services.OrganService;
import com.krm.ps.sysmanage.organmanage2.services.AreaService;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.organmanage.web.form.SysInitForm;
import com.krm.ps.sysmanage.usermanage.services.UserService;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.sysmanage.organmanage2.model.OrganNode;
import com.krm.ps.sysmanage.organmanage2.model.OrganSystem;
import com.krm.ps.sysmanage.organmanage2.model.OrganTree;
import com.krm.ps.sysmanage.organmanage2.vo.Area;
import com.krm.ps.sysmanage.organmanage2.services.OrganTreeManager;

/**
 * 
 * @struts.action name="sysInitForm" path="/sysInitAction" scope="request"
 *                validate="false" parameter="method"
 * 
 * @struts.action-forward name="initform" path="/organmanage/sysinit.jsp"
 * 
 */
public class SysInitAction extends BaseAction {

	public ActionForward initform(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'initform' method");
		}

		AreaService areaService = (AreaService)getBean("areaService");
		Area top=areaService.getTopArea();
		request.setAttribute("rootArea", top);
		String curDate=null;
		request.setAttribute("curDate", curDate);
		
		return mapping.findForward("initform");
	}

	public ActionForward initMunal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'initMunal' method");
		}
		SysInitForm f = (SysInitForm) form;
		UserService us = getUserService();
		OrganService os = getOrganService();

		//创建系统管理员
		User sysadmin = new User();
		sysadmin.setOrganId(f.getOrganCode());
		sysadmin.setIsAdmin(new Integer(2));
		sysadmin.setLogonName(f.getLogonName());
		sysadmin.setName(f.getUserName());
		sysadmin.setPassword(f.getPassword());
		sysadmin.setRoleType(new Long(1));
		sysadmin.setStatus(new Integer(1));
		
		List ul=us.getUsers();
		Iterator i=ul.iterator();
		while(i.hasNext()){
			User u=(User)i.next();
			us.removeUser(u.getPkid());
		}
		us.saveUser(sysadmin);
		us.setUserReportAss(sysadmin.getPkid());

		//创建机构（包括机构管理员）
		OrganInfo thisorgan = new OrganInfo();
		thisorgan.setCode(f.getOrganCode());
		thisorgan.setSuper_id(f.getOrganCode()+"00");//地区编码
		thisorgan.setOrgan_type(new Integer(802));
		thisorgan.setShort_name(f.getOrganShortName());
		thisorgan.setFull_name(f.getOrganShortName());
		thisorgan.setBusiness_type(new Integer(1));
		thisorgan.setDummy_type("1");
		thisorgan.setPeak("0");
		thisorgan.setDelegate("TBD");
		thisorgan.setStatus(new Integer(1));
		thisorgan.setCreator(new Integer(1));
		thisorgan.setOrganOrder(new Integer(2));
		thisorgan.setBegin_date("19991231");
		thisorgan.setEnd_date("20151231");
		thisorgan.setOrganOrder(new Integer(0));
		os.saveOrgan(thisorgan, null);

		//设值默认地区
		 AreaService areaService = (AreaService)getBean("areaService");
		 //areaService.setAreaDefult(f.getAreaCode());
		 Area area = new Area();
		 area.setFullName(f.getAreaName());
		 area.setShortName(f.getAreaName());
		 area.setCode(f.getOrganCode()+"00");
		 area.setBeginDate("19991231");
		 area.setEndDate("20151231");
		 area.setShowOrder(1);
		 area.setStatus(1);
		 area.setSuperId(0);
		 area.setIsDefult(0);
		 areaService.saveArea(null, area);
		
		//创建默认机构树
		OrganTree organTree = new OrganTree();

		OrganSystem organSystem = new OrganSystem();
		organSystem.setAreaId(f.getOrganCode()+"00");
		organSystem.setBeginDate("20010101");
		organSystem.setEndDate("20151231");
		organSystem.setCreatorId(sysadmin.getPkid().intValue());
		organSystem.setName(f.getAreaName());
		organSystem.setVisibility(OrganSystem.VISIBILITY_PUBLIC);
		organSystem.setGroupId(1);
		organSystem.setStatus("1");
		
		organTree.setOrganSystem(organSystem);
		
		OrganNode node=new OrganNode();
		node.setAlias(f.getOrganShortName());
		node.setOrganId(thisorgan.getPkid().intValue());
		organTree.setTopOrgan(node);
		
		OrganTreeManager organTreeManager = (OrganTreeManager)this.getBean("organTreeManager");
		organTreeManager.saveOrganTree(organTree);		
		
		return mapping.findForward("loginpage");
	}

	

}
