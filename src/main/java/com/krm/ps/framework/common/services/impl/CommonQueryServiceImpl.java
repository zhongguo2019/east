package com.krm.ps.framework.common.services.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

import com.krm.common.logger.KRMLogger;
import com.krm.common.logger.KRMLoggerUtil;
import com.krm.ps.framework.common.dao.CommonQueryDAO;
import com.krm.ps.framework.common.services.CommonQueryService;
import com.krm.ps.framework.common.vo.CommonQueryParams;
import com.krm.ps.framework.common.web.util.BeanUtilServlet;

public class CommonQueryServiceImpl implements CommonQueryService {

	KRMLogger logger = KRMLoggerUtil.getLogger(CommonQueryServiceImpl.class);

	private CommonQueryDAO commonQueryDAO;

	// 防止一个多线程处理的MAP
	WeakHashMap sqlManagerMap = new WeakHashMap();

	public void setCommonQueryDAO(CommonQueryDAO commonQueryDAO) {
		this.commonQueryDAO = commonQueryDAO;
	}

	public List commonQuery(String sql, Object[][] entities,
			Object[][] scalaries, Object[] values) {

		return commonQueryDAO.commonQuery(sql, entities, scalaries, values);

	}

	public List commonQuery(String sqlId, String params, Class sqlManagerClass) {
		Class sqlManager = sqlManagerClass;
		try {
			Method method = sqlManager.getMethod("getCommonQueryParams",
					new Class[] { String.class, String.class });
			if (sqlId == null || "".equals(sqlId))
				return new ArrayList();
			String[] sqlIds = sqlId.split(",");
			for (int i = 0; i < sqlIds.length; i++) {
				CommonQueryParams queryParams = (CommonQueryParams) method
						.invoke(sqlManager, new Object[] { sqlIds[i], params });
				CommonQueryService commonQueryService = (CommonQueryService) BeanUtilServlet
						.getBean("commonQueryService");
				return commonQueryService.commonQuery(queryParams.sql,
						queryParams.entities, queryParams.scalaries,
						queryParams.values);
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList();
	}

	public static Object getSpecifiedValue(Object container, String index) {
		String[] ids = index.split(",");
		if (container instanceof List) {
			if (ids.length > 1)
				return getSpecifiedValue(
						((List) container).get(Integer.parseInt(ids[0])),
						index.substring(ids[0].length() + 1));
			else
				return ((List) container).get(Integer.parseInt(ids[0]));
		} else if (container instanceof Object[]) {
			if (ids.length > 1)
				return getSpecifiedValue(
						((Object[]) container)[Integer.parseInt(ids[0])],
						index.substring(ids[0].length() + 1));
			else
				return ((Object[]) container)[Integer.parseInt(ids[0])];
		} else {
			KRMLogger logger = KRMLoggerUtil
					.getLogger(CommonQueryServiceImpl.class);
			logger.warn("the container can not be resolved!!!");
			return null;
		}
	}

	public List commonQuery(List sqlParamList) {

		List resultList = new ArrayList();

		CommonQueryService commonQueryService = (CommonQueryService) BeanUtilServlet
				.getBean("commonQueryService");
		for (int i = 0; i < sqlParamList.size(); i++) {
			CommonQueryParams queryParams = (CommonQueryParams) sqlParamList
					.get(i);
			List list = commonQueryService.commonQuery(queryParams.sql,
					queryParams.entities, queryParams.scalaries,
					queryParams.values);
			resultList.add(list);
		}
		return resultList;
	}
}
