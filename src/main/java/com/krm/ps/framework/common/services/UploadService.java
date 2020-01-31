package com.krm.ps.framework.common.services;

import java.io.InputStream;
import java.io.OutputStream;

import com.krm.ps.framework.common.dao.UploadDAO;
import com.krm.ps.framework.common.vo.Accessory;

public interface UploadService
{
	public Accessory getAccessory(String accessoryClassName, Long accessoryId);

	public String[] getAccessory(String accessoryClassNameParam, Long entityId,
		String accessoryType);
	
	public void removeAccessories(String uploadDir, String accessoryClassName,
		String accessoryIdsIds);

	public void saveAccessory(Accessory accessory);

	public void saveAccessory(Accessory accessory, InputStream in);

	public void setUploadDAO(UploadDAO dao);
	
	public void updateAccessory(String accessoryClassName, String accessoryIds,
		String accessoryTitles, Long entityId, String accessoryType);

	public void updateLocalFile(Accessory accessory, String uploadDir);

	public void updateLocalFile(String accessoryClassName, String filePath, OutputStream out);

	public void updateTextAccessory(String uploadDir,
		String accessoryClassName, String allTextAccessories,
		String realTextAccessories, Long entityId, String accessoryType);
}
