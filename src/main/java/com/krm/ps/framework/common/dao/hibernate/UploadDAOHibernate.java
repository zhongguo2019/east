package com.krm.ps.framework.common.dao.hibernate;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.framework.common.dao.UploadDAO;
import com.krm.ps.framework.common.vo.Accessory;
import com.krm.ps.framework.common.web.util.UploadUtil;
import com.krm.ps.util.StringUtil;

public class UploadDAOHibernate extends BaseDAOHibernate implements UploadDAO
{
	public List getAccessory(String accessoryClassName, Long entityId,
		String accessoryType)
	{
		String hql = "SELECT accessoryId,title,randomName FROM "
			+ accessoryClassName
			+ " WHERE entityId=? AND entityKind=? AND status=1 ORDER BY dispOrder";
		return list(hql, new Object[]{entityId, accessoryType});
	}

	public void removeAccessories(String accessoryClassName, List accessoryIds)
	{
		if (accessoryIds != null && accessoryIds.size() > 0)
		{
			String hql = "DELETE FROM " + accessoryClassName
				+ " WHERE accessoryId in (:accessoryIds)";
			Map parameters = new HashMap();
			parameters.put("accessoryIds", accessoryIds);
			update(hql, parameters);
		}
	}

	public void removeAccessoriesForName(String accessoryClassName, List names,
		Long entityId)
	{
		if (names != null && names.size() > 0)
		{
			String hql = "DELETE FROM " + accessoryClassName
				+ " WHERE randomName in(:names)";
			Map parameters = new HashMap();
			parameters.put("names", names);
			update(hql, parameters);
		}
	}

	public void saveAccessory(Accessory accessory, InputStream in)
	{
		saveBlobObjectForOracle(accessory, "accessory", in);
	}

	public void updateAccessories(String accessoryClassName,
		Object[][] paramArray, Long entityId, String accessoryType)
	{
		String hql = "UPDATE " + accessoryClassName
			+ " SET status=9 WHERE entityId=? AND entityKind=?";
		update(hql, new Object[]{entityId, accessoryType});
		if (paramArray != null && paramArray[0] != null
			&& paramArray[1] != null)
		{
			hql = "UPDATE "
				+ accessoryClassName
				+ " SET entityId=?,entityKind=?,title=?,dispOrder=?,status=1 WHERE accessoryId=?";
			for (int i = 0; i < paramArray[0].length; i++)
			{
				update(hql, new Object[]{entityId, accessoryType,
					paramArray[1][i], new Integer(i), paramArray[0][i]});
			}
		}
	}

	public void removeAccessories(String accessoryClassName, Long entityId,
		String accessoryType)
	{
		String hql = "DELETE FROM " + accessoryClassName
			+ " WHERE entityId=? AND entityKind=? AND status=9";
		update(hql, new Object[]{entityId, accessoryType});
	}

	public void updateAccessoriesForName(String accessoryClassName, List names,
		Long entityId, String accessoryType)
	{
		if (names != null && names.size() > 0)
		{
			String hql = "UPDATE "
				+ accessoryClassName
				+ " SET entityId=:entityId,entityKind=:accessoryType,status=1 WHERE randomName in(:names)";
			Map parameters = new HashMap();
			parameters.put("names", names);
			parameters.put("entityId", entityId);
			parameters.put("accessoryType", accessoryType);
			update(hql, parameters);
		}
	}

	public void updateAccessory(String accessoryClassName, String accessoryIds,
		String accessoryTitles, Long entityId, String accessoryType)
	{
		if (accessoryIds != null && accessoryIds.length() > 0
			&& accessoryTitles != null && accessoryTitles.length() > 0)
		{
			Object[][] paramArray = {
				StringUtil.splitStringToLongArray(accessoryIds),
				accessoryTitles.split(StringUtil.ID_SEPARATOR)};
			if (paramArray[0] != null && paramArray[1] != null
				&& paramArray[0].length > 0
				&& paramArray[0].length == paramArray[1].length)
			{
				updateAccessories(accessoryClassName, paramArray, entityId,
					accessoryType);
			}
		}
	}

	public void updateLocalFile(Accessory accessory, String uploadDir)
	{
		try
		{
			UploadUtil.makedirs(uploadDir);
			OutputStream out = new FileOutputStream(UploadUtil.getFullFileName(
				uploadDir, accessory.getRandomName(), accessory.getExtName()));
			writeBlobObjectToStream(accessory.getClass(), accessory
				.getAccessoryId(), "accessory", out);
		}
		catch (Exception e)
		{
			if (log.isDebugEnabled())
			{
				log.debug(e.getMessage());
			}
		}
	}

	public void updateLocalFile(String accessoryClassName, String randomName,
		String fileDir)
	{
		String hql = "FROM " + accessoryClassName + " t WHERE t.randomName=?";
		updateLocalFile((Accessory) uniqueResult(hql, new Object[]{UploadUtil
			.getFileNameWithoutExt(randomName)}), fileDir);
	}

	public void updateTextAccessory(String uploadDir,
		String accessoryClassName, String allTextAccessories,
		String realTextAccessories, Long entityId, String accessoryType)
	{
		if (allTextAccessories != null && allTextAccessories.length() > 0)
		{
			List allNames = StringUtil
				.splitStringToStringList(allTextAccessories);
			List realNames = StringUtil
				.splitStringToStringList(realTextAccessories);
			if (realNames != null)
			{
				updateAccessoriesForName(accessoryClassName, realNames,
					entityId, accessoryType);
				if (allNames != null)
				{
					allNames.removeAll(realNames);
				}
			}
			removeAccessoriesForName(accessoryClassName, allNames, entityId);
			UploadUtil.removeFiles(uploadDir, allNames);
		}
	}

	public void write(Accessory accessory, OutputStream out)
	{
		writeBlobObjectToStream(accessory.getClass(), accessory
			.getAccessoryId(), "accessory", out);
	}

	public void write(String accessoryClassName, String randomName,
		OutputStream out)
	{
		String hql = "FROM " + accessoryClassName + " t WHERE t.randomName=?";
		write((Accessory) uniqueResult(hql, new Object[]{UploadUtil
			.getFileNameWithoutExt(randomName)}), out);
	}
}
