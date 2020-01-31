package com.krm.ps.util;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.krm.ps.framework.common.vo.LabelValue;

public final class ConvertUtil
{
	private static final String[] ADOXML_STRTYPE_ATTR = {"string", "str",
		"255", null, null};

	private static Log log = LogFactory.getLog(ConvertUtil.class);

	//private static final String[] ADOXML_NUMTYPE_ATTR = {"number", "numeric",	null, "0", null};

	public static Object convert(Class targetClass, Object arg)
		throws Exception
	{
		return convert(targetClass, new Object[]{arg});
	}

	public static Object convert(Class targetClass, Object[] args)
		throws Exception
	{
		Class[] argTypes = new Class[args.length];
		for (int i = 0; i < args.length; i++)
		{
			argTypes[i] = args[i].getClass();
		}
		Constructor ctor = targetClass.getConstructor(argTypes);
		return ctor.newInstance(args);
	}

	/**
	 * Method to convert a ResourceBundle to a Map object.
	 * 
	 * @param rb a given resource bundle
	 * @return Map a populated map
	 */
	public static Map convertBundleToMap(ResourceBundle rb)
	{
		Map map = new HashMap();

		for (Enumeration keys = rb.getKeys(); keys.hasMoreElements();)
		{
			String key = (String) keys.nextElement();
			map.put(key, rb.getString(key));
		}

		return map;
	}

	/**
	 * Method to convert a ResourceBundle to a Properties object.
	 * 
	 * @param rb a given resource bundle
	 * @return Properties a populated properties object
	 */
	public static Properties convertBundleToProperties(ResourceBundle rb)
	{
		Properties props = new Properties();

		for (Enumeration keys = rb.getKeys(); keys.hasMoreElements();)
		{
			String key = (String) keys.nextElement();
			props.put(key, rb.getString(key));
		}

		return props;
	}

	public static void convertInStreamToOutStream(InputStream in,
		OutputStream out, int bufferSize) throws IOException
	{
		if (bufferSize > 0)
		{
			int bytesRead = 0;
			byte[] buffer = new byte[bufferSize];
			while ((bytesRead = in.read(buffer, 0, bufferSize)) != -1)
			{
				out.write(buffer, 0, bytesRead);
			}
		}
	}

	public static List convertList(Class targetClass, List list)
		throws Exception
	{
		List result = null;
		if (list != null)
		{
			result = (List) list.getClass().newInstance();
			for (Iterator i = list.iterator(); i.hasNext();)
			{
				result.add(convert(targetClass, i.next()));
			}
		}
		return result;
	}

	public static List convertList(Class targetClass, String propertyName,
		List list) throws Exception
	{
		List result = null;
		if (list != null)
		{
			result = (List) list.getClass().newInstance();
			for (Iterator i = list.iterator(); i.hasNext();)
			{
				result.add(convert(targetClass, PropertyUtils.getProperty(i
					.next(), propertyName)));
			}
		}
		return result;
	}

	public static String convertListToAdoXml(List treeList, String[] cnames)
	{
		String[][] cols = null;
		cols = new String[6][cnames.length];
		for (int j = 0; j < cols[0].length; j++)
		{
			cols[0][j] = cnames[j];
			for (int i = 0; i < ADOXML_STRTYPE_ATTR.length; i++)
			{
				cols[i + 1][j] = ADOXML_STRTYPE_ATTR[i];
			}
		}
		return convertListToAdoXml(treeList, cols);
	}

	// convertListToAdoXml(List treeList, String[] cnames)的替代方法，不需使用反射读取列表元素属性，提高了效率。wsx，2007-7-3
	public static String convertListToAdoXml(List treeList, TreeNodeAttributeReader tnar)
	{
		String[] cnames=new String[] {"id","name","order", "parent"};
		String[][] cols = null;
		cols = new String[6][cnames.length];
		for (int j = 0; j < cols[0].length; j++)
		{
			cols[0][j] = cnames[j];
			for (int i = 0; i < ADOXML_STRTYPE_ATTR.length; i++)
			{
				cols[i + 1][j] = ADOXML_STRTYPE_ATTR[i];
			}
		}
		return convertListToAdoXml(treeList, cols, tnar);
	}
	

	//	wsx,2007-7-3
	private static String convertListToAdoXml(List list, String[][] cols,TreeNodeAttributeReader tnar)
	{
		StringBuffer treeXML = new StringBuffer();
		treeXML
			.append("<xml xmlns:s='uuid:BDC6E3F0-6DA3-11d1-A2A3-00AA00C14882'");
		treeXML
			.append("\n\txmlns:dt='uuid:C2F41010-65B3-11d1-A29F-00AA00C14882'");
		treeXML.append("\n\txmlns:rs='urn:schemas-microsoft-com:rowset'");
		treeXML.append("\n\txmlns:z='#RowsetSchema'>");
		treeXML.append("\n\t<s:Schema id='RowsetSchema'>");
		treeXML
			.append("\n\t\t<s:ElementType name='row' content='eltOnly' rs:CommandTimeout='30' rs:updatable='true' >");
		for (int i = 0; i < cols[0].length; i++)
		{
			treeXML.append("\n\t\t\t<s:AttributeType name='");
			treeXML.append(cols[0][i]);
			treeXML.append("' ");

			treeXML.append("rs:number='");
			treeXML.append(i + 1);
			treeXML.append("' ");

			treeXML.append("rs:nullable='true'>");

			treeXML.append("\n\t\t\t\t<s:datatype dt:type='");
			treeXML.append(cols[1][i]);
			treeXML.append("' ");

			if (cols[2][i] != null)
			{
				treeXML.append("rs:dbtype='");
				treeXML.append(cols[2][i]);
				treeXML.append("' ");
			}

			if (cols[3][i] != null)
			{
				treeXML.append("dt:maxLength='");
				treeXML.append(cols[3][i]);
				treeXML.append("' ");
			}

			if (cols[4][i] != null)
			{
				treeXML.append("rs:scale='");
				treeXML.append(cols[4][i]);
				treeXML.append("' ");
			}

			if (cols[5][i] != null)
			{
				treeXML.append("rs:precision='");
				treeXML.append(cols[5][i]);
				treeXML.append("' ");
			}

			treeXML.append("/>");
			treeXML.append("\n\t\t\t</s:AttributeType>");
		}
		treeXML.append("\n\t\t\t<s:extends type='rs:rowbase'/>");
		treeXML.append("\n\t\t</s:ElementType>");
		treeXML.append("\n\t</s:Schema>");
		treeXML.append("\n\t<rs:data>");

		if (list != null)
		{
			for (Iterator itr = list.iterator(); itr.hasNext();)
			{
				treeXML.append("\n\t\t<z:row ");
				Object[] cvalues;
				if(tnar!=null) {
					cvalues=convertNodeAttrToArray(itr.next(),tnar);//wsx,2007-7-3
				}else {
					cvalues=convertRowToArray(itr.next(), cols[0]);
				}				
				for (int i = 0; i < cols[0].length; i++)
				{
					treeXML.append(cols[0][i]);
					treeXML.append("='");
					treeXML.append((""+cvalues[i]).replaceAll("'",""));//wsx 9-25
					treeXML.append("' ");
				}
				treeXML.append("/>");
			}
		}
		treeXML.append("\n\t</rs:data>");
		treeXML.append("\n</xml>");
		return treeXML.toString();
	}

	/**
	 * 转换列表数据到AdoXml格式
	 * 
	 * AdoXml格式示例如下：
	 * 
	 *<xml xmlns:s='uuid:BDC6E3F0-6DA3-11d1-A2A3-00AA00C14882'
	 *	xmlns:dt='uuid:C2F41010-65B3-11d1-A29F-00AA00C14882'
	 *	xmlns:rs='urn:schemas-microsoft-com:rowset'
	 *	xmlns:z='#RowsetSchema'>
	 *	<s:Schema id='RowsetSchema'>
	 *		<s:ElementType name='row' content='eltOnly' rs:CommandTimeout='30' rs:updatable='true' >
	 *			<s:AttributeType name='pkId' rs:number='1' rs:nullable='true'>
	 *				<s:datatype dt:type='number' rs:dbtype='numeric' rs:scale='0' />
	 *			</s:AttributeType>
	 *			<s:AttributeType name='groupName' rs:number='2' rs:nullable='true'>
	 *				<s:datatype dt:type='string' rs:dbtype='str' dt:maxLength='50' />
	 *			</s:AttributeType>
	 *			<s:AttributeType name='status' rs:number='3' rs:nullable='true'>
	 *				<s:datatype dt:type='number' rs:dbtype='numeric' rs:scale='0' />
	 *			</s:AttributeType>
	 *			<s:AttributeType name='manager' rs:number='4' rs:nullable='true'>
	 *				<s:datatype dt:type='string' rs:dbtype='str' dt:maxLength='50' />
	 *			</s:AttributeType>
	 *			<s:AttributeType name='dispOrder' rs:number='5' rs:nullable='true'>
	 *				<s:datatype dt:type='number' rs:dbtype='numeric' rs:scale='0' />
	 *			</s:AttributeType>
	 *			<s:AttributeType name='superGroupId' rs:number='6' rs:nullable='true'>
	 *				<s:datatype dt:type='number' rs:dbtype='numeric' rs:scale='0' />
	 *			</s:AttributeType>
	 *			<s:extends type='rs:rowbase'/>
	 *		</s:ElementType>
	 *	</s:Schema>
	 *	<rs:data>
	 *		<z:row pkId='10' groupName='机构' status='8' manager='null' dispOrder='0' superGroupId='0' />
	 *		<z:row pkId='11' groupName='支行' status='8' manager='null' dispOrder='0' superGroupId='0' />
	 *		<z:row pkId='12' groupName='部室' status='8' manager='null' dispOrder='0' superGroupId='0' />
	 *		<z:row pkId='20' groupName='职务' status='8' manager='null' dispOrder='0' superGroupId='0' />
	 *		<z:row pkId='30' groupName='职称' status='8' manager='null' dispOrder='0' superGroupId='0' />
	 *		<z:row pkId='40' groupName='角色' status='8' manager='null' dispOrder='0' superGroupId='0' />
	 *		<z:row pkId='108' groupName='远郊支行' status='0' manager='远郊支行' dispOrder='1' superGroupId='11' />
	 *		<z:row pkId='119' groupName='科长' status='0' manager='科长' dispOrder='2' superGroupId='20' />
	 *		<z:row pkId='123' groupName='管理员' status='0' manager='管理员' dispOrder='0' superGroupId='20' />
	 *		<z:row pkId='124' groupName='部室' status='0' manager='部室' dispOrder='99999' superGroupId='12' />
	 *	</rs:data>
	 *</xml>
	 * 
	 * @param list 要转换的数据列表，列表的每一项为一个Object数组
	 * @param cols 要转换数据的列的描述。 
	 * <p> cols[0]-name数组； 
	 * <p> cols[1]-type数组； 
	 * <p> cols[2]-dbtype数组； 
	 * <p> cols[3]-maxLength数组； 
	 * <p> cols[4]-scale数组；
	 * <p> cols[5]-precision数组
	 * @return
	 */
	public static String convertListToAdoXml(List list, String[][] cols)
	{
		return convertListToAdoXml(list, cols, null);
	}

	public static Map convertListToMap(List list)
	{
		Map map = new LinkedHashMap();

		for (Iterator it = list.iterator(); it.hasNext();)
		{
			LabelValue option = (LabelValue) it.next();
			map.put(option.getLabel(), option.getValue());
		}

		return map;
	}

	public static Object[] convertRowToArray(Object row, String[] cnames)
	{
		Object[] cvalues = null;
		if (row.getClass().equals(Object[].class))
		{
			cvalues = (Object[]) row;
		}
		else
		{
			cvalues = new Object[cnames.length];
			for (int i = 0; i < cnames.length; i++)
			{
				try
				{
					cvalues[i] = PropertyUtils.getProperty(row, cnames[i]);
					//cvalues[i]="a";
				}
				catch (Exception e)
				{
				}
			}
		}
		return cvalues;
	}

	public static Object[] convertNodeAttrToArray(Object node, TreeNodeAttributeReader tnar)
	{
		Object[] cvalues = new String[4];
		cvalues[0]=tnar.getId(node);
		cvalues[1]=tnar.getName(node);
		cvalues[2]=tnar.getOrder(node);
		cvalues[3]=tnar.getParent(node);
		return cvalues;
	}
	
	/**
	 * 主要给公式，科目列的导入导出所使用
	 * @param source
	 * @param target
	 * @param cindexs
	 * @param cnames
	 */
	public static void convertStringArrayToObject(String[] source,Object target,int[] cindexs, String[] cnames)
	{
		if (cindexs.length != cnames.length || cindexs[cindexs.length-1] >= source.length)
		{
			return;
		}
		else
		{
			for (int i = 0; i < cindexs.length; i++)
			{
				try
				{
					int idx = cindexs[i];
					Class propertyClass = PropertyUtils.getPropertyType(target,cnames[i]);
					if(source[idx] != null)
					{
						if(propertyClass == String.class)
						{
							PropertyUtils.setProperty(target, cnames[i],source[idx]);
						}
						if(propertyClass == Long.class)
						{
							Long longValue = Long.valueOf(source[idx]);
							PropertyUtils.setProperty(target, cnames[i],longValue);
						}
						if(propertyClass == Integer.class)
						{
							Integer integerValue = Integer.valueOf(source[idx]);
							PropertyUtils.setProperty(target, cnames[i],integerValue);
						}
					}
					else
					{
						PropertyUtils.setProperty(target, cnames[i],null);
					}
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	public static void copyProperties(Map properties, Object object)
	{
		PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(object);
		for (int i = 0; i < pds.length; i++)
		{
			try
			{
				String propertyName = pds[i].getName();
				properties.put(propertyName, PropertyUtils.getProperty(object,
					propertyName));
			}
			catch (IllegalAccessException e)
			{
			}
			catch (InvocationTargetException e)
			{
			}
			catch (NoSuchMethodException e)
			{
			}
		}
	}

	public static void copyProperties(Object object, Map properties)
	{
		PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(object);
		for (int i = 0; i < pds.length; i++)
		{

			String propertyName = pds[i].getName();
			Object propertyValue = properties.get(propertyName);
			if (propertyValue != null)
			{
				try
				{
					PropertyUtils.setProperty(object, propertyName,
						propertyValue);
				}
				catch (IllegalAccessException e)
				{
					if (log.isDebugEnabled())
					{
						log.debug(e.getMessage());
					}
				}
				catch (InvocationTargetException e)
				{
					if (log.isDebugEnabled())
					{
						log.debug(e.getMessage());
					}
				}
				catch (NoSuchMethodException e)
				{
					if (log.isDebugEnabled())
					{
						log.debug(e.getMessage());
					}
				}
			}

		}
	}

	public static void copyProperties(Object to, Object from)
	{
		try
		{
			copyProperties(to, from, true);
		}
		catch (Exception e)
		{
			if (log.isDebugEnabled())
			{
				log.debug(e.getMessage());
			}
		}
	}

	public static void copyProperties(Object to, Object from,
		boolean ignoreUnknown) throws Exception
	{
		PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(from);
		for (int i = 0; i < pds.length; i++)
		{
			try
			{
				PropertyUtils.setProperty(to, pds[i].getName(), PropertyUtils
					.getProperty(from, pds[i].getName()));
			}
			catch (Exception ex)
			{
				if (!ignoreUnknown)
				{
					throw ex;
				}
			}
		}
	}

	public static void copyProperties(Object to, Object from,
		String getMethodName)
	{
		PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(to);
		for (int i = 0; i < pds.length; i++)
		{
			try
			{
				Object propValue = from.getClass().getMethod(getMethodName,
					new Class[]{String.class}).invoke(from,
					new Object[]{pds[i].getName()});
				BeanUtils.setProperty(to, pds[i].getName(), propValue);

			}
			catch (IllegalArgumentException e)
			{
				if (log.isDebugEnabled())
				{
					log.debug(e.getMessage());
				}
			}
			catch (SecurityException e)
			{
				if (log.isDebugEnabled())
				{
					log.debug(e.getMessage());
				}
			}
			catch (IllegalAccessException e)
			{
				if (log.isDebugEnabled())
				{
					log.debug(e.getMessage());
				}
			}
			catch (InvocationTargetException e)
			{
				if (log.isDebugEnabled())
				{
					log.debug(e.getMessage());
				}
			}
			catch (NoSuchMethodException e)
			{
				if (log.isDebugEnabled())
				{
					log.debug(e.getMessage());
				}
			}
		}
	}

	/**
	 * Convenience method used by tests to populate an object from a
	 * ResourceBundle
	 * 
	 * @param obj an initialized object
	 * @param rb a resource bundle
	 * @return a populated object
	 */
	public static Object populateObject(Object obj, ResourceBundle rb)
	{
		try
		{
			Map map = convertBundleToMap(rb);

			BeanUtils.copyProperties(obj, map);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			log.error("Exception occured populating object: " + e.getMessage());
		}

		return obj;
	}
	
    public static String getSimpleName(String className) {
    	    return className.substring(className.lastIndexOf(".")+1);
    }
}
