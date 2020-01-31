package com.krm.ps.sysmanage.groupmanage.service;

import java.util.List;

import com.krm.ps.framework.common.sort.service.SortService;
import com.krm.ps.sysmanage.usermanage.dao.DictionaryDAO;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2006</p>
 *
 * <p>Company: </p>
 *
 * @author
 */
public interface GroupSortService extends SortService {

	public void setDictionaryDAO(DictionaryDAO ddao);
	
	/**
	 * 内部排序
	 * @param groups
	 */
	public void sortInner(List groups);
}
