package com.krm.ps.framework.property.service.impl;

import java.util.List;

import com.krm.ps.framework.property.dao.OrganPropertyDAO;
import com.krm.ps.framework.property.service.OrganPropertyService;
import com.krm.ps.framework.property.vo.OrganProperty;

public class OrganPropertyServiceImpl implements OrganPropertyService{

	private OrganPropertyDAO organPropertyDAO;
	
	
	public OrganPropertyDAO getOrganPropertyDAO() {
		return organPropertyDAO;
	}

	public void setOrganPropertyDAO(OrganPropertyDAO organPropertyDAO) {
		this.organPropertyDAO = organPropertyDAO;
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.property.service.OrganPropertyService#addOrganProperty(com.krm.slsint.property.vo.OrganProperty)
	 */
	public void addOrganProperty(OrganProperty op) {
		organPropertyDAO.addOrganProperty(op);
		
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.property.service.OrganPropertyService#deleteOrganProperty(com.krm.slsint.property.vo.OrganProperty)
	 */
	public void deleteOrganProperty(OrganProperty op) {
		organPropertyDAO.deleteOrganProperty(op);
		
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.property.service.OrganPropertyService#getOp(java.lang.String)
	 */
	public List getOp(String organCode) {
		return organPropertyDAO.getOp(organCode); 
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.property.service.OrganPropertyService#getOrganProperty(java.lang.String, java.lang.Long)
	 */
	public OrganProperty getOrganProperty(String organCode, Long proId) {
		return organPropertyDAO.getOrganProperty(organCode,proId); 
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.property.service.OrganPropertyService#getList(java.lang.Long)
	 */
	public List getList(Long proId) {
		return organPropertyDAO.getList(proId);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.property.service.OrganPropertyService#updateOrganProperty(com.krm.slsint.property.vo.OrganProperty)
	 */
	public void updateOrganProperty(OrganProperty op) {
		organPropertyDAO.updateOrganProperty(op);
	}

}
