package com.krm.ps.framework.common.services.impl;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.krm.ps.framework.dao.DAO;
import com.krm.ps.framework.common.services.Manager;

public abstract class BaseManager implements Manager
{
	protected final Log log = LogFactory.getLog(getClass());

	protected DAO dao = null;

	public void setDAO(DAO dao)
	{
		this.dao = dao;
	}

	public Object getObject(Class clazz, Serializable id)
	{
		return dao.getObject(clazz, id);
	}

	public List getObjects(Class clazz)
	{
		return dao.getObjects(clazz);
	}

	public void removeObject(Class clazz, Serializable id)
	{
		dao.removeObject(clazz, id);
	}

	public void saveObject(Object o)
	{
		dao.saveObject(o);
	}
}
