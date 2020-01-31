package com.krm.ps.framework.common.vo;

import java.util.Date;

public interface Accessory
{
	
	public Long getAccessoryId();

	public Date getCreateTime();

	public Integer getDispOrder();

	public Date getEditTime();

	public Long getEntityId();

	public String getEntityKind();

	public String getExtName();

	public String getOriginalName();

	public String getRandomName();

	public Integer getStatus();

	public String getTitle();

	public void setAccessoryId(Long accessoryId);

	public void setCreateTime(Date createTime);

	public void setDispOrder(Integer dispOrder);

	public void setEditTime(Date editTime);

	public void setEntityId(Long entityId);

	public void setEntityKind(String entityKind);

	public void setExtName(String extName);

	public void setOriginalName(String orignalName);

	public void setRandomName(String randomName);

	public void setStatus(Integer status);

	public void setTitle(String title);
}