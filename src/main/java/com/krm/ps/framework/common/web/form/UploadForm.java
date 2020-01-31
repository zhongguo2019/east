package com.krm.ps.framework.common.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;

/**
 * 
 * @struts.form name="uploadForm"
 */
public class UploadForm extends BaseForm implements java.io.Serializable
{
	private static final long serialVersionUID = -5783960054013800214L;

	private String accessoryClassName;

	private Long accessoryId;

	private String accessoryIds;

	private String accessoryTitles;

	private Long entityId;

	private String entityKind;

	private String fileURL;

	private String funArg;

	private FormFile newFile;
	
	private String newTitle;

	private String selectedIds;
	
	private Integer status;

	public String getAccessoryClassName()
	{
		return accessoryClassName;
	}

	public Long getAccessoryId()
	{
		return accessoryId;
	}

	public String getAccessoryIds()
	{
		return accessoryIds;
	}

	public String getAccessoryTitles()
	{
		return accessoryTitles;
	}

	public Long getEntityId()
	{
		return entityId;
	}

	public String getEntityKind()
	{
		return entityKind;
	}

	public String getFileURL()
	{
		return fileURL;
	}

	public String getFunArg()
	{
		return funArg;
	}

	public FormFile getNewFile()
	{
		return newFile;
	}

	public String getNewTitle()
	{
		return newTitle;
	}

	public String getSelectedIds()
	{
		return selectedIds;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setAccessoryClassName(String accessoryClassName)
	{
		this.accessoryClassName = accessoryClassName;
	}

	public void setAccessoryId(Long accessoryId)
	{
		this.accessoryId = accessoryId;
	}

	public void setAccessoryIds(String accessoryIds)
	{
		this.accessoryIds = accessoryIds;
	}


	public void setAccessoryTitles(String accessoryTitles)
	{
		this.accessoryTitles = accessoryTitles;
	}

	public void setEntityId(Long entityId)
	{
		this.entityId = entityId;
	}

	public void setEntityKind(String entityKind)
	{
		this.entityKind = entityKind;
	}

	public void setFileURL(String fileURL)
	{
		this.fileURL = fileURL;
	}

	public void setFunArg(String funArg)
	{
		this.funArg = funArg;
	}

	public void setNewFile(FormFile newFile)
	{
		this.newFile = newFile;
	}

	public void setNewTitle(String newTitle)
	{
		this.newTitle = newTitle;
	}


	public void setSelectedIds(String selectedIds)
	{
		this.selectedIds = selectedIds;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public ActionErrors validate(ActionMapping mapping,
		HttpServletRequest request)
	{
		ActionErrors errors = null;

		Boolean maxLengthExceeded = (Boolean) request
			.getAttribute(MultipartRequestHandler.ATTRIBUTE_MAX_LENGTH_EXCEEDED);
		if ((maxLengthExceeded != null) && (maxLengthExceeded.booleanValue()))
		{
			errors = new ActionErrors();
			errors.add("MaxLengthExceeded", new ActionMessage(
				"maxLengthExceeded"));
		}
		return errors;
	}
}
