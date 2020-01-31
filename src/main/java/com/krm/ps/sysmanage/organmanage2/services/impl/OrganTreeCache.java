package com.krm.ps.sysmanage.organmanage2.services.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Iterator;

import com.krm.ps.sysmanage.organmanage2.model.OrganSystem;
import com.krm.ps.sysmanage.organmanage2.model.OrganTree;

/**
 * 机构树缓存类，服务层内部实现
 * 
 */
class OrganTreeCache {

	private Map organSystemMap = new HashMap();

	/**
	 * 从缓存取出机构树，如果没有缓存或已被垃圾回收器回收，返回null
	 * 
	 * @param organSystemId
	 * @param topOrganId
	 * @return
	 */
	public synchronized OrganTree getOrganTree(int organSystemId, int topOrganId) {

		OrganSystemCache osc = (OrganSystemCache) organSystemMap.get(""
				+ organSystemId);
		if (osc != null) {
			return osc.getOrganTree(topOrganId);
		}
		return null;
	}

	/**
	 * 从缓存取出完整机构树
	 * 
	 * @param organSystemId
	 * @param topOrganId
	 * @return
	 */
	public synchronized OrganTree getCompleteOrganTree(int organSystemId) {

		OrganSystemCache osc = (OrganSystemCache) organSystemMap.get(""
				+ organSystemId);
		if (osc != null) {
			return osc.getCompleteOrganTree();
		}
		return null;
	}

	/**
	 * 缓存机构树
	 * 
	 * @param organTree
	 */
	public synchronized void cacheOrganTree(OrganTree organTree) {

		OrganSystem os = organTree.getOrganSystem();
		String systemKey=""	+ os.getId();
		OrganSystemCache osc = (OrganSystemCache) organSystemMap.get(systemKey);
		if (osc == null) {
			osc = new OrganSystemCache(os);
			organSystemMap.put("" + os.getId(), osc);
		}
		osc.cacheOrganTree(organTree);
	}

	public void clearCache() {
		Iterator it=organSystemMap.keySet().iterator();
		while(it.hasNext()) {
			OrganSystemCache osc = (OrganSystemCache) organSystemMap.get(it.next());
			osc.free();
		}
		organSystemMap.clear();
	}

	/**
	 * 取得机构系统信息
	 * @param organSystemId
	 * @return
	 */
	public OrganSystem getOrganSystem(int organSystemId) {
		OrganSystemCache osc = (OrganSystemCache) organSystemMap.get(""
				+ organSystemId);
		if (osc != null) {
			return osc.getOrganSystem();
		}
		return null;
	}

	/**
	 * 更新机构系统信息（如果缓存中存在）
	 * @param organSystemId
	 * @return
	 */
	public void updateOrganSystem(OrganSystem organSystem) {
		OrganSystemCache osc = (OrganSystemCache) organSystemMap.get(""
				+ organSystem.getId());
		if(osc!=null) {
			osc.updateOrganSystem(organSystem);
		}		
	}
	
	public synchronized OrganTree getOrganTree_temp(int organSystemId, int topOrganId) {

		OrganSystemCache osc = (OrganSystemCache) organSystemMap.get(""
				+ organSystemId);
		if (osc != null) {
			return osc.getOrganTree(topOrganId);
		}
		return null;
	}

}
