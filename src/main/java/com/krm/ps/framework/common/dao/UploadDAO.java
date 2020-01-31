package com.krm.ps.framework.common.dao;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.krm.ps.framework.dao.DAO;
import com.krm.ps.framework.common.vo.Accessory;

public interface UploadDAO extends DAO
{
	public List getAccessory(String accessoryClassName, Long entityId,
		String accessoryType);

	public void removeAccessories(String accessoryClassName, List accessoryIds);

	public void saveAccessory(Accessory accessory, InputStream in);

	public void updateAccessory(String accessoryClassName, String accessoryIds,
		String accessoryTitles, Long entityId, String accessoryType);

	public void updateLocalFile(Accessory accessory, String uploadDir);

	public void updateLocalFile(String accessoryClassName, String randomName,
		String fileDir);

	public void updateTextAccessory(String uploadDir,
		String accessoryClassName, String allTextAccessories,
		String realTextAccessories, Long entityId, String accessoryType);

	public void write(String accessoryClassName, String randomName,
		OutputStream out);
}
