package com.krm.ps.framework.common.dictionary.web.util;

/**
 * 
 * <p>Title: �ֵ�ά�����򹤾�</p>
 *
 * <p>Description: ������������</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author
 */

public class OrderCode
{

	/**
	 * @param args
	 */
	String ordercode;

	public String getOrdercode(String code)
	{
		String temp;
		char[] Ctemp = new char[9];
		for (int i = 0; i < (9 - code.length()); i++)
		{
			Ctemp[i] = '0';
		}
		temp = new String(Ctemp);
		return temp.trim() + code;
	}

}
