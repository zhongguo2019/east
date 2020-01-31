package com.krm.ps.util;

/**
 * 在生成树xml时（在{@link ConvertUtil}内）使用，避免使用反射，可以提高效率
 * @author wsx
 *
 */
public interface TreeNodeAttributeReader{
	
	String getId(Object node);
	
	String getName(Object node);
	
	String getOrder(Object node);
	
	String getParent(Object node);
}