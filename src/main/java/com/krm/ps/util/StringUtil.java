package com.krm.ps.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.i18n.LocaleContextHolder;

import com.krm.ps.util.SysConfig;

public class StringUtil
{
	public static final String ID_SEPARATOR = ",";

	private final static Log log = LogFactory.getLog(StringUtil.class);

	/**
	 * Decode a string using Base64 encoding.
	 * 
	 * @param str
	 * @return String
	 */
	public static String decodeString(String str)
	{
		sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
		try
		{
			return new String(dec.decodeBuffer(str));
		}
		catch (IOException io)
		{
			throw new RuntimeException(io.getMessage(), io.getCause());
		}
	}

	/**
	 * Encode a string using algorithm specified in web.xml and return the
	 * resulting encrypted password. If exception, the plain credentials string
	 * is returned
	 * 
	 * @param password Password or other credentials to use in authenticating
	 *            this username
	 * @param algorithm Algorithm used to do the digest
	 * 
	 * @return encypted password based on the algorithm.
	 */
	public static String encodePassword(String password, String algorithm)
	{
		byte[] unencodedPassword = password.getBytes();

		MessageDigest md = null;

		try
		{
			// first create an instance, given the provider
			md = MessageDigest.getInstance(algorithm);
		}
		catch (Exception e)
		{
			log.error("Exception: " + e);

			return password;
		}

		md.reset();

		// call the update method one or more times
		// (useful when you don't know the size of your data, eg. stream)
		md.update(unencodedPassword);

		// now calculate the hash
		byte[] encodedPassword = md.digest();

		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < encodedPassword.length; i++)
		{
			if ((encodedPassword[i] & 0xff) < 0x10)
			{
				buf.append("0");
			}

			buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
		}

		return buf.toString();
	}

	/**
	 * Encode a string using Base64 encoding. Used when storing passwords as
	 * cookies.
	 * 
	 * This is weak encoding in that anyone can use the decodeString routine to
	 * reverse the encoding.
	 * 
	 * @param str
	 * @return String
	 */
	public static String encodeString(String str)
	{
		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		return encoder.encodeBuffer(str.getBytes()).trim();
	}

	public static String getDbString(byte[] dbBytes)
		throws UnsupportedEncodingException
	{
		return new String(dbBytes, getDbCharsetName());
	}

	public static byte[] getDbBytes(String appString)
		throws UnsupportedEncodingException
	{
		return appString.getBytes(getDbCharsetName());
	}

	public static String getDbCharsetName()
	{
		return getResourceString("charset.db", Constants.DEFAULT_CHARSET);
	}

	/**
	 * 将ID_SEPARATOR分隔的字符串拆分到Long[]中，忽略非数字
	 * 
	 * @param s ID_SEPARATOR分隔的字符串
	 * @return 拆分出的Long[]
	 */
	public static Long[] splitStringToLongArray(String s)
	{
		Long[] result = null;
		if (s != null && s.length() > 0)
		{
			String[] strArray = s.split(ID_SEPARATOR);
			if (strArray != null && strArray.length > 0)
			{
				result = new Long[strArray.length];
				for (int i = 0; i < strArray.length; i++)
				{
					try
					{
						result[i] = Long.valueOf(strArray[i]);
					}
					catch (NumberFormatException e)
					{
						if (log.isDebugEnabled())
						{
							log.debug(e.getMessage());
						}
					}
				}
			}
		}
		return result;
	}

	/**
	 * 将ID_SEPARATOR分隔的字符串拆分到链表中
	 * 
	 * @param s ID_SEPARATOR分隔的字符串
	 * @return 拆分出的链表，链表的每个元素为String类型
	 */
	public static List splitStringToStringList(String s)
	{
		List result = null;
		if (s != null)
		{
			result = new ArrayList(Arrays.asList(s.split(ID_SEPARATOR)));
		}
		return result;
	}

	public static String toString(Object o)
	{
		return o == null ? "" : o.toString();
	}

	/**
	 * 将数组元素连接成ID_SEPARATOR分隔的字符串
	 * 
	 * @param aArray 要连接的数组
	 * @return ID_SEPARATOR分隔的字符串。
	 */
	public static String uniteArrayToString(Object[] aArray)
	{
		String result = "";

		if (aArray != null)
		{
			for (int i = 0; i < aArray.length; i++)
			{
				result += ID_SEPARATOR + aArray[i].toString();
			}
			if (result.length() > 0)
			{
				result = result.substring(ID_SEPARATOR.length());
			}
		}
		return result;
	}

	/**
	 * 将数字格式化
	 */
	public String formatstring(int number)
	{
		String formatstring = "";
		String tt = "";
		String tt1 = "";
		if (number < 10)
		{
			tt = "0" + new Integer(number).toString();
		}
		else if (number > 100 && number < 1000)
		{

			tt1 = new Integer(number).toString();
			tt = tt1.substring(0, 2);
		}
		else
		{
			tt = new Integer(number).toString();
		}
		formatstring = tt;
		return formatstring;
	}

	public static String getResourceString(String key, String defaultValue)
	{
		Locale locale = LocaleContextHolder.getLocale();
		try
		{
			return ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale)
				.getString(key);
		}
		catch (MissingResourceException e)
		{
			return defaultValue;
		}
	}

	/**
	 * 将链表元素连接成ID_SEPARATOR分隔的字符串
	 * 
	 * @param aList 要连接的链表，链表元素可以是Object及Object[]
	 * @return ID_SEPARATOR分隔的字符串数组。如果链表元素是Object类型，则数组长度为1；如果链表元素是Object[]类型，则返回的数组的长度与链表元素的长度相等
	 */
	public static String[] uniteListToString(List aList)
	{
		String[] result = null;

		if (aList != null)
		{
			Iterator itr = aList.iterator();
			if (itr.hasNext())
			{
				Object item = itr.next();
				if (item instanceof Object[])
				{
					result = new String[((Object[]) item).length];
					for (int j = 0; j < result.length; j++)
					{
						result[j] = "";
					}
					for (Iterator i = aList.iterator(); i.hasNext();)
					{
						Object[] items = (Object[]) i.next();
						for (int j = 0; j < items.length; j++)
						{
							result[j] += ID_SEPARATOR + items[j].toString();
						}
					}
					for (int j = 0; j < result.length; j++)
					{
						if (result[j].length() > 0)
						{
							result[j] = result[j].substring(ID_SEPARATOR
								.length());
						}
					}
				}
				else
				{
					result = new String[]{""};
					for (Iterator i = aList.iterator(); i.hasNext();)
					{
						result[0] += ID_SEPARATOR + i.next().toString();
					}
					if (result[0].length() > 0)
					{
						result[0] = result[0].substring(ID_SEPARATOR.length());
					}
				}
			}
		}
		return result;
	}

	public static String uniteListToString(List aList, int idx)
	{
		String result = "";

		if (aList != null && idx >= 0)
		{
			Iterator itr = aList.iterator();
			if (itr.hasNext())
			{
				Object item = itr.next();
				if (item instanceof Object[] && ((Object[]) item).length > idx)
				{
					for (Iterator i = aList.iterator(); i.hasNext();)
					{
						Object[] items = (Object[]) i.next();
						result += ID_SEPARATOR + items[idx].toString();
					}
					if (result.length() > 0)
					{
						result = result.substring(ID_SEPARATOR.length());
					}
				}
			}
		}
		return result;
	}
	
	public static String escapeUrlString(String strUrl)
	{
		//%20%23%24%25%26%2F%3A%3B%3C%3D%3E%3F%40%5B%5C%5D%5E%60%7B%7C%7D%7E
		//strUrl = strUrl.replaceAll("\\%20","");
		strUrl = strUrl.replaceAll("\\%20"," ");//保留空格，wsx 1019
		strUrl = strUrl.replaceAll("\\%23","\\#");
		strUrl = strUrl.replaceAll("\\%24","\\$");
		strUrl = strUrl.replaceAll("\\%25","\\%");
		strUrl = strUrl.replaceAll("\\%26","\\&");
		strUrl = strUrl.replaceAll("\\%2F","\\/");
		strUrl = strUrl.replaceAll("\\%3A","\\:");
		strUrl = strUrl.replaceAll("\\%3B","\\;");
		strUrl = strUrl.replaceAll("\\%3C","\\<");
		strUrl = strUrl.replaceAll("\\%3D","\\=");
		strUrl = strUrl.replaceAll("\\%3E","\\>");
		strUrl = strUrl.replaceAll("\\%3F","\\?");
		strUrl = strUrl.replaceAll("\\%40","\\@");
		strUrl = strUrl.replaceAll("\\%5B","\\[");
		strUrl = strUrl.replaceAll("\\%5C","\\\\");
		strUrl = strUrl.replaceAll("\\%5D","\\]");
		strUrl = strUrl.replaceAll("\\%5E","\\^");
		strUrl = strUrl.replaceAll("\\%60","\\`");
		strUrl = strUrl.replaceAll("\\%7B","\\{");
		strUrl = strUrl.replaceAll("\\%7C","\\|");
		strUrl = strUrl.replaceAll("\\%7D","\\}");
		strUrl = strUrl.replaceAll("\\%7E","\\~");
		return strUrl;
	}
	
	public static String convertArrayToSplitString(Object[] array,String splitStr)
	{
		String toString = "";
		for(int i=0;i<array.length;i++)
		{
			if(i>0)
			{
				toString += splitStr;
			}
			toString += array[i].toString();
		}
		return toString;
	}
	
	public static String convertArrayToSplitString2(Object[] array,String splitStr)
	{
		String toString = "";
		for(int i=0;i<array.length;i++)
		{
			if(i>0)
			{
				toString += splitStr;
			}
			toString += "'" + array[i].toString() + "'";
		}
		return toString;
	}
	
	public static String[] divideString(String source, String divideFlag) {
	    if (source == null) {
	      return null;
	    }
	    if (source.equals("")) {
	      return new String[] {
	        ""};
	    }
	    if (source == null || source.equals("")) {
	      return new String[] {
	        source};
	    }
	    StringTokenizer st = new StringTokenizer(source, divideFlag);
	    //求数组长度
	    int count = st.countTokens();
	    String apple[] = new String[count];
	    //填充数组
	    for (int ii = 0; ii < count; ii++) {
	      apple[ii] = st.nextToken();
	    }
	    return apple;
	  }
	
	public static String emptySafe(String source, String defaultStr){
		if(source == null){
			return defaultStr;
		}else{
			return source;
		}
	}
	/**
	 * 
	 * @param db 源数据
	 * @param formatType 格式化的形式
	 * @return
	 */
	public static String doubletoString(Double db,String formatType){
		DecimalFormat a = new DecimalFormat(formatType);
		 return  a.format(db);
	}
	
	public static String removeRn(String v) {
		v = v.replaceAll("\n", " ");
		v = v.replaceAll("\t", " ");
		v = v.replaceAll("\r", "");
		return v;
	}
	
	public static String getResultTableName(String date,String levelFlag) {
		String str = "code_rep_result_";
		//if("4".equals(levelFlag)) {
		//	str = str + "s_";
		//}else {
			str = str + "t_";
		//}
		return str+date.substring(4,6);
	}
	public static String getDBTreeStr(String node,String idxnode){
		String db2tree="";
		if('d'==SysConfig.DB){
		 db2tree="WITH  RPL (PARENTID, NODEID, ALIASNAME) AS(SELECT ROOT.PARENTID, ROOT.NODEID, ROOT.ALIASNAME FROM code_org_tree ROOT WHERE ROOT.NODEID = "+node+" and IDXID="+idxnode+" UNION  ALL SELECT CHILD.PARENTID, CHILD.NODEID, CHILD.ALIASNAME FROM RPL PARENT, code_org_tree CHILD WHERE PARENT.NODEID = CHILD.PARENTID) SELECT CODE FROM RPL a , code_org_organ  org where a.NODEID =org.pkid ORDER BY PARENTID, NODEID, ALIASNAME";
		}else{
		 db2tree	="select o.code from CODE_ORG_TREE t,code_org_organ o  where t.nodeid=o.pkid and t.IDXID= "+idxnode+" start with   t.nodeid=1 　Connect by prior  t.nodeid= t.parentid";
		}
	    return db2tree;
	}
	public static String convertstrisnull(String str){
		if(str==null){
			return "";
		}else{
			return  itemValidate(str);
		}
	}
	/**
	 * 去掉字符串中的空格
	 * @param item 字符串
	 */
	public static String itemValidate(String item) {
		String itemValue = "";
		if(!("".equals(item))&&!(null==item) ){
		if (" ".equals(String.valueOf(item.charAt(0)))) {
			itemValue = item.substring(1).trim();
		} else {
			itemValue = item.trim();
		if(item!=null&&item.length()>0){
			if (" ".equals(String.valueOf(item.charAt(0)))) {
				itemValue = item.substring(1).trim();
			} else {
				itemValue = item.trim();
			}
		}
		}
		}
		return itemValue;
	}
	

	public static void main(String[] args) {
		System.out.println("convertstrisnull:"+convertstrisnull("    ")+":");
	}
}
