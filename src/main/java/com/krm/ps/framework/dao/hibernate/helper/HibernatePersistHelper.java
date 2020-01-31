package com.krm.ps.framework.dao.hibernate.helper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;
import org.hibernate.type.Type;

public class HibernatePersistHelper
{

	public final static Log log = LogFactory.getLog(HibernatePersistHelper.class);
	
	private static Configuration hibernateConf = new Configuration(); 
	
	private static Map propertyToColumnMap = new HashMap();

    private static PersistentClass getPersistentClass(Class clazz) { 

           synchronized (HibernatePersistHelper.class) {
                  PersistentClass pc = hibernateConf.getClassMapping(clazz.getName());
                  if (pc == null) {
                	  log.debug("add the class [" + clazz + "] to persist");
                      hibernateConf = hibernateConf.addClass(clazz); 
                      pc = hibernateConf.getClassMapping(clazz.getName()); 
                  } 
                  return pc; 

           } 

    } 
    
    private static Map getPropertyToColumnMap(Class clazz)
    {
    	if (propertyToColumnMap.get(clazz) == null)
    	{
    		synchronized (HibernatePersistHelper.class)
			{
    			PersistentClass pc = getPersistentClass(clazz);
           	 	Iterator it = pc.getPropertyIterator();
                Map tmpMap = new HashMap();
                int index = 0;
                Property property;
        		while (it.hasNext())		
        		{
        			property = (Property)it.next();
        			tmpMap.put(property.getName(), property);
        			index++;
        		}
        		property = pc.getIdentifierProperty();
        		tmpMap.put(property.getName(), property);
        		propertyToColumnMap.put(clazz, tmpMap);
			}
    	}
 		return (Map)propertyToColumnMap.get(clazz);
    }

    public static String getTableName(Class clazz) { 
    	log.debug("get the table name for class [" + clazz + "]");
        return getPersistentClass(clazz).getTable().getName(); 

    } 

    public static String getTableColumnName(Class clazz, String propertyName) { 
    	Map map = getPropertyToColumnMap(clazz);
    	return ((Column)(((Property)map
    			.get(propertyName))
    			.getValue()
    			.getColumnIterator()
    			.next()))
    			.getName();
    } 

    public static Type getTableColumnHType(Class clazz, String propertyName)
    {
    	Map map = getPropertyToColumnMap(clazz);
    	Property property = (Property)map.get(propertyName);
    	if (property == null)
    	{
    		log.warn("class " + clazz.getName() + " do not contain the property " + propertyName);
    	}
    	return property.getType();
    }

	/**
	 * <p>得到clazz的实体属性Set集合</p> 
	 *
	 * @param clazz
	 * @return
	 * @author 皮亮
	 * @version 创建时间：2010-5-14 上午10:26:55
	 */
	public static Set getPropertySet(Class clazz)
	{
		return getPropertyToColumnMap(clazz).keySet();
	}

}
