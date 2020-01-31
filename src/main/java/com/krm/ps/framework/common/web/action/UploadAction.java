package com.krm.ps.framework.common.web.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.krm.ps.framework.common.services.UploadService;
import com.krm.ps.framework.common.vo.Accessory;
import com.krm.ps.framework.common.web.form.UploadForm;
import com.krm.ps.framework.common.web.util.UploadUtil;

/**
 * @struts.action name="uploadForm" path="/uploadFile" scope="request" validate="false" parameter="method" input="view"
 * @struts.action-forward name="view" path="/common/uploadForm.jsp"
 */
public class UploadAction extends BaseAction
{
	public ActionForward delete(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		UploadForm uploadForm = (UploadForm) form;
		String selectIds = uploadForm.getSelectedIds();
		String accessoryClassName = UploadUtil.getAccessoryClassName(request);
		getService().removeAccessories(getUploadDir(request),
			accessoryClassName, selectIds);
		return view(mapping, form, request, response);
	}

	public ActionForward download(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		UploadForm uploadForm = (UploadForm) form;
		Accessory accessory = getService().getAccessory(
			uploadForm.getAccessoryClassName(), uploadForm.getAccessoryId());
		String fileURL = UploadUtil.getAccessoryURL(request, accessory);
		response.sendRedirect(fileURL);
		return null;
	}

	public ActionForward upload(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		UploadForm uploadForm = (UploadForm) form;
		String uploadDir = getUploadDir(request);
		Accessory accessory = UploadUtil.saveFile(request, uploadForm
			.getNewFile(), uploadDir);
		if (accessory != null)
		{
			String newTitle = uploadForm.getNewTitle();
			accessory.setTitle(newTitle);
			saveAccessory(accessory);

			String newId = accessory.getAccessoryId().toString();
			String oldIds = uploadForm.getAccessoryIds();
			String oldTitles = uploadForm.getAccessoryTitles();
			if (oldIds != null && oldIds.trim().length() > 0
				&& oldTitles != null && oldTitles.trim().length() > 0)
			{
				uploadForm.setAccessoryIds(newId + "," + oldIds);
				uploadForm.setAccessoryTitles(newTitle + "," + oldTitles);
			}
			else
			{
				uploadForm.setAccessoryIds(newId);
				uploadForm.setAccessoryTitles(newTitle);
			}
			uploadForm.setFileURL(UploadUtil
				.getAccessoryURL(request, accessory));
			uploadForm.setNewTitle("");
		}
		return view(mapping, form, request, response);
	}

	public ActionForward view(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception
	{
		return mapping.findForward("view");
	}

	private UploadService getService()
	{
		return (UploadService) getBean("uploadService");
	}

	private String getUploadDir(HttpServletRequest request)
	{
		String uploadDir = UploadUtil.getUploadDir(UploadUtil
			.getAccessoryClassName(request));
		return servlet.getServletContext().getRealPath(uploadDir);
	}

	private void saveAccessory(Accessory accessory)
	{
		getService()
			.saveAccessory(accessory);
	}
}
