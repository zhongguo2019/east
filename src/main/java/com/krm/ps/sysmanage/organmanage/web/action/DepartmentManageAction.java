package com.krm.ps.sysmanage.organmanage.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.sysmanage.organmanage.services.OrganService;
import com.krm.ps.sysmanage.organmanage.vo.UserDeptIdx;
import com.krm.ps.sysmanage.organmanage.web.form.DepartmentManageForm;

/**
 * <p>Title: 部门管理Action</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author
 * @struts.action name="departmentManageForm" path="/departmentManageAction" scope="request" validate="false"
 *                parameter="method"
 * 
 * @struts.action-forward name="enter" path="/organmanage/userDeptConfig.jsp"
 */
public class DepartmentManageAction extends BaseAction
{
	/**
	 * <p>进入用户部门配置页面<p>
	 * 
	 * @author 皮亮
	 * @version 创建时间：2010-5-4 下午03:22:20
	 */
	public ActionForward enter(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
	{
		if (log.isDebugEnabled()) {
			log.info("Entering 'enter' method");
		}

		OrganService organService = getOrganService();
		List deptList = organService.getAllDepartmentList();
		request.setAttribute("deptList", deptList);
		return mapping.findForward("enter");
	}
	
	/**
	 * <p>保存设置的人员部门信息<p>
	 * 
	 * @author 皮亮
	 * @version 创建时间：2010-5-4 下午03:34:07
	 */
	public ActionForward saveUserDeptInfo(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
	{
		if (log.isDebugEnabled()) {
			log.info("Entering 'saveUserDeptInfo' method");
		}
		OrganService organService = getOrganService();
		DepartmentManageForm deptManageForm = (DepartmentManageForm)form;
		List userDeptIdxList = new ArrayList();
		UserDeptIdx userDeptIdx = null;
		String operFlag = deptManageForm.getOperFlag();
		String userIdStr = deptManageForm.getUserIdStr();
		String[] userIds = userIdStr.split(",");
		String[] userNames = deptManageForm.getUserNameStr().split(",");
		if (userIds.length > 0)
		{
			int size = userIds.length;
			for (int i = 0; i < size; i++)
			{
				userDeptIdx = new UserDeptIdx();
				userDeptIdx.setDeptId(deptManageForm.getDeptId());
				userDeptIdx.setDeptName(deptManageForm.getDeptName());
				userDeptIdx.setOrganCode(deptManageForm.getOrganCode());
				userDeptIdx.setOrganName(deptManageForm.getOrganName());
				userDeptIdx.setUserId(new Long(userIds[i]));
				userDeptIdx.setUserName(userNames[i]);
				userDeptIdxList.add(userDeptIdx);
			}
		}
		if ("0".equals(operFlag))
			organService.deleteUserDeptIdxList(userDeptIdxList);
		else
			organService.saveUserDeptIdxList(userDeptIdxList);
		request.setAttribute(ALERT_MESSAGE, "人员部门信息" + ("0".equals(operFlag) ? "删除" : "保存") + "成功");
		return enter(mapping, form, request, response);
	}
}
