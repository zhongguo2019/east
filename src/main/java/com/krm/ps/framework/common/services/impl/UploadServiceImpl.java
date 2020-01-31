package com.krm.ps.framework.common.services.impl;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.krm.ps.framework.common.dao.UploadDAO;
import com.krm.ps.framework.common.services.UploadService;
import com.krm.ps.framework.common.vo.Accessory;
import com.krm.ps.framework.common.web.util.UploadUtil;
import com.krm.ps.util.StringUtil;

public class UploadServiceImpl implements UploadService
{
	private UploadDAO dao;

	public Accessory getAccessory(String accessoryClassName, Long accessoryId)
	{
		Accessory accessory = null;
		try
		{
			accessory = (Accessory) dao.getObject(Class
				.forName(accessoryClassName), accessoryId);
		}
		catch (ClassNotFoundException e)
		{
		}
		return accessory;
	}

	public String[] getAccessory(String accessoryClassName, Long entityId,
		String accessoryType)
	{
		List list = dao.getAccessory(accessoryClassName, entityId,
			accessoryType);
		String[] accessories = StringUtil.uniteListToString(list);
		if (accessories == null)
		{
			accessories = new String[]{null, null, null};
		}
		return accessories;
	}

	public void removeAccessories(String uploadDir, String accessoryClassName,
		String accessoryIds)
	{
		Class clazz = null;
		try
		{
			clazz = Class.forName(accessoryClassName);
		}
		catch (ClassNotFoundException e)
		{
		}
		if (clazz != null && accessoryIds != null && accessoryIds.length() > 0)
		{
			Long[] accessoryIdArray = StringUtil
				.splitStringToLongArray(accessoryIds);
			if (accessoryIdArray.length > 0)
			{
				List fileNames = new ArrayList();

				for (int i = 0; i < accessoryIdArray.length; i++)
				{
					Accessory accessory = (Accessory) dao.getObject(clazz,
						accessoryIdArray[i]);
					if (accessory != null)
					{
						fileNames.add(accessory.getRandomName());
						dao.removeObject(accessory);
					}
				}
				UploadUtil.removeFiles(uploadDir, fileNames);
			}
		}
	}

	public void saveAccessory(Accessory accessory)
	{
		dao.saveObject(accessory);
	}

	public void saveAccessory(Accessory accessory, InputStream in)
	{
		dao.saveAccessory(accessory, in);
	}

	public void setUploadDAO(UploadDAO dao)
	{
		this.dao = dao;
	}

	public void updateAccessory(String accessoryClassName, String accessoryIds,
		String accessoryTitles, Long entityId, String accessoryType)
	{
		dao.updateAccessory(accessoryClassName, accessoryIds, accessoryTitles,
			entityId, accessoryType);
	}

	public void updateLocalFile(Accessory accessory, String uploadDir)
	{
		dao.updateLocalFile(accessory, uploadDir);
	}

	public void updateLocalFile(String accessoryClassName, String filePath,
		OutputStream out)
	{
		String fileDir = UploadUtil.getFileDir(filePath);
		UploadUtil.makedirs(fileDir);
		String randomName = UploadUtil.getShortFileName(filePath);
		dao.updateLocalFile(accessoryClassName, randomName, fileDir);
		if (out != null)
		{
			dao.write(accessoryClassName, randomName, out);
		}
	}

	public void updateTextAccessory(String uploadDir,
		String accessoryClassName, String allTextAccessories,
		String realTextAccessories, Long entityId, String accessoryType)
	{
		dao.updateTextAccessory(uploadDir, accessoryClassName,
			allTextAccessories, realTextAccessories, entityId, accessoryType);
	}

}
