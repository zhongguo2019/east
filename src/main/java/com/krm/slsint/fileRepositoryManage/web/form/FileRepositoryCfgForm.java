package com.krm.slsint.fileRepositoryManage.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

/**
 * <p> Title: 文件仓库维护功能表示层模型 </p>
 * 
 * <p> Description: 本类实现文件仓库表示层的模型 </p>
 * 
 * <p> Copyright: Copyright (c) 2006 </p>
 * 
 * <p> Company: KRM Soft </p>
 * 
 * @author
 * @struts.form name="fileRepositoryCfgForm"
 */
public class FileRepositoryCfgForm extends ActionForm
{

	/**
	 * <code></code>
	 */
	private static final long serialVersionUID = 6998359765218850708L;

	private FormFile ff;

	/**
	 * 唯一标识
	 */
	private Long pkid;

	/**
	 * 功能代码
	 */
	private String funId;

	private String funName;

	/**
	 * 编辑时间
	 */
	private String editTime;

	/**
	 * 文件名称
	 */
	private String fileName;

	/**
	 * 文件名前缀
	 */
	private String namePrefix;

	/**
	 * 文件名后缀
	 */
	private String namePostfix;

	/**
	 * 文件路径
	 */
	private String filePath;

	/**
	 * 用户ID
	 */
	private Long userId;

	private String userName;

	/**
	 * 显示顺序
	 */
	private Long showOrder;

	/**
	 * 文件状态
	 */
	private Long status;

	private String statusName;

	/**
	 * 文件内容
	 */
	private String fileContent;

	/**
	 * 描述信息
	 */
	private String description;

	/**
	 * 备注
	 */
	private String memo;

	/**
	 * 机构ID
	 */
	private String organId;

	private String organName;
	
	private String reportId;
	
	private FormFile fp;
	
	private String slstarget;

	public FileRepositoryCfgForm()
	{

	}

	public FileRepositoryCfgForm(Long pkid, String funId, String funName,
		String editTime, String fileName, String namePrefix,
		String namePostfix, String filePath, String organId,String organName, Long userId,
		Long showOrder, Long status, String description, String memo)
	{
		this.pkid = pkid;
		this.funId = funId;
		this.funName = funName;
		this.editTime = editTime;
		this.fileName = fileName;
		this.namePrefix = namePrefix;
		this.namePostfix = namePostfix;
		this.filePath = filePath;
		this.organId = organId;
		this.organName = organName;
		this.userId = userId;
		this.userName = null;
		this.showOrder = showOrder;
		this.status = status;
		this.statusName = null;

		this.description = description;
		this.memo = memo;
	}

	public void reset(ActionMapping arg0, HttpServletRequest arg1)
	{
		super.reset(arg0, arg1);
		pkid = null;
		funId = null;
		editTime = null;
		fileName = null;
		namePrefix = null;
		namePostfix = null;
		filePath = null;
		organId = null;
		userId = null;
		showOrder = null;
		status = null;
		fileContent = null;
		description = null;
		memo = null;
		organName = null;
		statusName = null;
		userName = null;
		funName = null;
		ff = null;
		fp = null;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getEditTime()
	{
		return editTime;
	}

	public void setEditTime(String editTime)
	{
		this.editTime = editTime;
	}

	public String getFileContent()
	{
		return fileContent;
	}

	public void setFileContent(String fileContent)
	{
		this.fileContent = fileContent;
	}

	public String getFileName()
	{
		return fileName;
	}

	public void setFileName(String fileName)
	{
		this.fileName = fileName;
	}

	public String getFilePath()
	{
		return filePath;
	}

	public void setFilePath(String filePath)
	{
		this.filePath = filePath;
	}

	public String getFunId()
	{
		return funId;
	}

	public void setFunId(String funId)
	{
		this.funId = funId;
	}

	public String getMemo()
	{
		return memo;
	}

	public void setMemo(String memo)
	{
		this.memo = memo;
	}

	public String getNamePostfix()
	{
		return namePostfix;
	}

	public void setNamePostfix(String namePostfix)
	{
		this.namePostfix = namePostfix;
	}

	public String getNamePrefix()
	{
		return namePrefix;
	}

	public void setNamePrefix(String namePrefix)
	{
		this.namePrefix = namePrefix;
	}

	public String getOrganId()
	{
		return organId;
	}

	public void setOrganId(String organId)
	{
		this.organId = organId;
	}

	public Long getPkid()
	{
		return pkid;
	}

	public void setPkid(Long pkid)
	{
		this.pkid = pkid;
	}

	public Long getShowOrder()
	{
		return showOrder;
	}

	public void setShowOrder(Long showOrder)
	{
		this.showOrder = showOrder;
	}

	public Long getStatus()
	{
		return status;
	}

	public void setStatus(Long status)
	{
		this.status = status;
	}

	public Long getUserId()
	{
		return userId;
	}

	public void setUserId(Long userId)
	{
		this.userId = userId;
	}

	public String getFunName()
	{
		return funName;
	}

	public void setFunName(String funName)
	{
		this.funName = funName;
	}

	public String getOrganName()
	{
		return organName;
	}

	public void setOrganName(String organName)
	{
		this.organName = organName;
	}

	public String getStatusName()
	{
		if (this.status != null)
		{
			int statusInt = status.intValue();
			if (statusInt == 1)
			{
				statusName = "可用";
			}
			if (statusInt == 2)
			{
				statusName = "冻结";
			}
			if (statusInt == 3)
			{
				statusName = "作废";
			}
		}
		return statusName;
	}

	public void setStatusName(String statusName)
	{
		this.statusName = statusName;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public FormFile getFf()
	{
		return ff;
	}

	public void setFf(FormFile ff)
	{
		this.ff = ff;
	}



	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public FormFile getFp() {
		return fp;
	}

	public void setFp(FormFile fp) {
		this.fp = fp;
	}

	public String getSlstarget() {
		return slstarget;
	}

	public void setSlstarget(String slstarget) {
		this.slstarget = slstarget;
	}



}
