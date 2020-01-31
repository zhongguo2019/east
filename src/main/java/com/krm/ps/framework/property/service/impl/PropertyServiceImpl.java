package com.krm.ps.framework.property.service.impl;

import java.util.List;

import com.krm.ps.framework.property.dao.PropertyDAO;
import com.krm.ps.framework.property.service.PropertyService;
import com.krm.ps.framework.property.vo.Prop;

public class PropertyServiceImpl implements PropertyService{

	private PropertyDAO propertyDAO;
	
	public PropertyDAO getPropertyDAO() {
		return propertyDAO;
	}

	public void setPropertyDAO(PropertyDAO propertyDAO) {
		this.propertyDAO = propertyDAO;
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.property.service.PropertyService#getAllProperty()
	 */
	public List getAllProperty() {
		return propertyDAO.getAllProperty();  
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.property.service.PropertyService#getProperty(java.lang.Long)
	 */
	public Prop getProperty(Long id) {
		return propertyDAO.getProperty(id);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.property.service.PropertyService#addProperty(com.krm.slsint.property.vo.Prop)
	 */
	public void addProperty(Prop property) {
		propertyDAO.addProperty(property);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.property.service.PropertyService#updateProperty(com.krm.slsint.property.vo.Prop)
	 */
	public void updateProperty(Prop property) {
		propertyDAO.updateProperty(property);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.property.service.PropertyService#deleteProperty(java.lang.Long)
	 */
	public void deleteProperty(Long id) {
		propertyDAO.deleteProperty(id);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.property.service.PropertyService#batchdelete(java.lang.String)
	 */
	public void batchdelete(String ids) {
		propertyDAO.batchdelete(ids);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.property.service.PropertyService#getProNameList()
	 */
	public List getProNameList() {
		return propertyDAO.getProNameList();
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.property.service.PropertyService#getProValue(java.lang.String)
	 */
	public String getProValue(String proName) {
		return propertyDAO.getProValue(proName);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.property.service.PropertyService#getProValue(java.lang.String, java.lang.String)
	 */
	public String getProValue(String proName, String defaultValue) {
		return propertyDAO.getProValue(proName, defaultValue);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.property.service.PropertyService#getProperty(java.lang.String)
	 */
	public Prop getProperty(String proName) {
		return propertyDAO.getProperty(proName);
	}

}
