package com.krm.ps.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import oracle.sql.BLOB;

import org.apache.commons.dbcp.DelegatingConnection;
import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.hibernate.Session;
import org.springframework.jdbc.support.lob.OracleLobHandler;
import org.springframework.jdbc.support.nativejdbc.CommonsDbcpNativeJdbcExtractor;
import org.springframework.jdbc.support.nativejdbc.NativeJdbcExtractor;
import org.springframework.jdbc.support.nativejdbc.WebLogicNativeJdbcExtractor;
import org.springframework.orm.hibernate3.HibernateCallback;

public class DBDialect {

	/**
	 * 生成序列
	 * 
	 * @param sequenceName
	 *            序列名
	 * @return
	 */
	public static String genSequence(String sequenceName) {
		switch (SysConfig.DB) {
		case 'p': // postgres
			return "nextval('" + sequenceName + "')";
		case 'o': // oracle
		case 'i': // informix
			return sequenceName + ".nextval";
		case 's': // SQL Server
			return "null";
		case 'd': // db2
			return "nextval for " + sequenceName;
		}
		return sequenceName + ".nextval";
	}

	/**
	 * 字符串连接
	 * 
	 * @return
	 */
	public static String conStr(String s1, String s2) {
		switch (SysConfig.DB) {
		case 'p': // postgres
		case 'o': // oracle
		case 'i': // informix
		case 'd': // db2
			return s1 + "||" + s2;
		case 's': // SQL Server
			return s1 + "+" + s2;
		}
		return s1 + "||" + s2;
	}

	public static String substring(String oriString, int from, int length) {
		if (SysConfig.DB == 's') {
			return "substring(" + oriString + "," + from + "," + length + ")";
		}
		return "substr(" + oriString + "," + from + "," + length + ")";
	}

	public static String substring(String oriString, int from) {
		if (SysConfig.DB == 's') {
			return "substring(" + oriString + "," + from + ",len(" + oriString
					+ "))";
		}
		return "substr(" + oriString + "," + from + ")";
	}

	/**
	 * 逃逸单引号
	 * 
	 * @param c
	 * @return
	 */
	public static String escapeSqm(String c) {
		if (c == null) {
			return "";
		}
		switch (SysConfig.DB) {
		case 'p': // postgres
		case 'o': // oracle
		case 'i': // informix
		case 's': // SQL Server
		case 'd': // db2
			return c.replaceAll("'", "''");
		}
		return c;
	}

	public static long seqCurval(String sequenceName) {
		// TODO
		return 0;
	}

	static NativeJdbcExtractor nativeJdbc = null;

	/**
	 * 取得底层Connection类型
	 * 
	 * @param c
	 *            连接池的代理类型
	 * @return
	 */
	public static Connection getNativeConnection(Connection c) {
		try {
			if (nativeJdbc == null) {
				if (c instanceof DelegatingConnection) {// commons DBCP
					nativeJdbc = new CommonsDbcpNativeJdbcExtractor();
				} else {
					nativeJdbc = new WebLogicNativeJdbcExtractor();// oracle驱动限于oracle
					// thin(class12)
				}
			}
			return nativeJdbc.getNativeConnection(c);
		} catch (SQLException e) {
			e.printStackTrace();
			return c;
		}
	}

	/**
	 * 取得长文本字段的值,在oracle中是Clob类型,在postgres中是text类型
	 * 
	 * @param r
	 * @param fieldName
	 * @return
	 * @deprecated 由于大对象字段映射自定义类型，不再需要此种实现方式
	 * @see UniversalLobHandler#
	 */
	public static String getLongTextField(ResultSet r, String fieldName) {

		try {
			switch (SysConfig.DB) {
			case 'p': // postgres
				return r.getString(fieldName);
			case 'o': // oracle
				return getClobFieldForOracle(r, fieldName);
			case 'd': // db2
			case 'i': // infomix
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return "";
	}

	private static String getClobFieldForOracle(ResultSet r, String fieldName) {

		try {
			java.sql.Clob clob = null;
			clob = r.getClob(fieldName);
			if ((clob == null)) {
				return null;
			}
			int len = (int) clob.length();
			int byteRead = 0;
			char[] bytes = new char[len];
			BufferedReader reader = new BufferedReader(clob
					.getCharacterStream());

			while ((byteRead = reader.read(bytes, 0, bytes.length)) > 0) {
				reader.read(bytes, 0, byteRead);
			}
			reader.close();
			return new String(bytes);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * 
	 * @param ps
	 * @param paramIndex
	 * @param content
	 * @throws SQLException
	 * @deprecated 由于大对象字段映射自定义类型，不再需要此种实现方式
	 * @see UniversalLobHandler#
	 */
	public static void setLongTextField(PreparedStatement ps, int paramIndex,
			String content) throws SQLException {
		switch (SysConfig.DB) {
		case 'p': // postgres
			ps.setString(paramIndex, content);
			return;
		case 'o': // oracle
			setClobForOracle(ps, paramIndex, content);
			return;
		case 'd': // db2
		case 'i': // infomix
		}
	}

	private static void setClobForOracle(PreparedStatement ps, int paramIndex,
			String content) throws SQLException {

		OracleLobHandler olh = new OracleLobHandler();
		olh.getLobCreator().setClobAsString(ps, paramIndex, content);
	}

	/**
	 * sql语句分隔符
	 * 
	 * @param c
	 * @return
	 * @deprecated
	 */
	/*
	 * public static void setSqlEndSign(String c) {// wsx 8-16,无效方法,没有改变c switch
	 * (SysConfig.DB) { case 'p': // postgres c += ";"; break; case 'o': //
	 * oracle case 'd': // db2 case 'i': // infomix } }
	 */

	/**
	 * 对数据进行四舍五入操作
	 * @param data 数据
	 * @param decimal 小数 
	 */
	public static String getRound(String data, String decimal){
		String result = null;
		switch (SysConfig.DB) {
		case 'p': // postgres
			result = "round("+data+", "+decimal+")";
		case 'o': // oracle
			result = "round("+data+", "+decimal+")";
		case 'i': // informix
			result = "round("+data+", "+decimal+")";
		case 's': // SQL Server
			result = "round("+data+", "+decimal+")";
		case 'd': // db2
			result = "round("+data+", "+decimal+")";
		}
		return result;
	}
	
	/**
	 * 返回列表中的第一个非空表达式
	 * @param data
	 * @param defaultData
	 * @return
	 */
	public static String getCoalesce(String data, String defaultData){
		String result = null;
		switch (SysConfig.DB) {
		case 'p': // postgres
			result = "coalesce("+data+", "+defaultData+")";
		case 'o': // oracle
			result = "coalesce("+data+", "+defaultData+")";
		case 'i': // informix
			result = "coalesce("+data+", "+defaultData+")";
		case 's': // SQL Server
			result = "coalesce("+data+", "+defaultData+")";
		case 'd': // db2
			result = "coalesce("+data+", "+defaultData+")";
		}
		return result;
	}
	
	/**
	 * 获取字段的长度
	 * @param field 字段名
	 * @return String
	 */
	public static String getLength(String field) {
		StringBuffer result = new StringBuffer();
		switch (SysConfig.DB) {
		case 'p': // postgres
			result.append("to_number(").append(field).append(",'9999999999999999999')");
			break;
		case 'o': // oracle
			result.append("to_number(").append(field).append(")");
			break;
		case 'i': // informix
			result.append(field);
			break;
		case 's': // SQL Server
			result.append(field);
			break;
		case 'd': // db2
			result.append("double(").append(field).append(")");
			break;
		}
		return result.toString();
	}
	
	/**
	 * @author zhaoPC
	 * 获得字段长度函数
	 * @param field 字段名
	 * @return String
	 */
	public static String getFieldLength(String field) {
		String result = "";
		switch (SysConfig.DB) {
		case 'p': // postgres
			result = "length("+field+")";
			break;
		case 'o': // oracle
			result = "length("+field+")";
			break;
		case 'i': // informix
			result = "length("+field+")";
			break;
		case 's': // SQL Server
			result = "len("+field+")";
			break;
		case 'd': // db2
			result = "length("+field+")";
			break;
		}
		return result;
	}
	
	public static String sqlPage(String sql,int start,int limit){
		sql = sql.trim();
		String db = SysConfig.DATABASE;
		StringBuffer pagingSelect = new StringBuffer(sql.length() + 100);
		if("oracle".equalsIgnoreCase(db)) {
			if (start > 0) {
				pagingSelect
						.append("select * from ( select row_.*, rownum rownum_ from ( ");
			} else {
				pagingSelect.append("select * from ( ");
			}
			pagingSelect.append(sql);
			if (start > 0) {
				pagingSelect
						.append(" ) row_ where rownum <= {1}) where rownum_ > {0}");
			} else {
				pagingSelect.append(" ) where rownum <= {1}");
			}
			
			sql = pagingSelect.toString();
			sql = sql.replaceAll("\\{0\\}", Integer.toString(start));
			Integer limitNum = 0;
			if(limit != 0){
				limitNum = (start / limit + 1) * limit;
			}
			sql = sql.replaceAll("\\{1\\}", Integer.toString(limitNum));
		}else if("db2".equalsIgnoreCase(db)){
			//int pos = sql.indexOf("select");
			sql = sql.substring(6);
			//pagingSelect.append("select * from (select rownumber() over(order by rdf.item_id asc) as rn,");
			pagingSelect.append("select * from (select rownumber() over(order by a1.pkid) as rn,");
			pagingSelect.append(sql);
			pagingSelect.append(") as t where t.rn between {0} and {1}");
			//pagingSelect.append(") as t where t.rn >{0}  fetch first {1} rows only ");
			sql = pagingSelect.toString();
			sql = sql.replaceAll("\\{0\\}", Integer.toString(start+1));
			sql = sql.replaceAll("\\{1\\}", Integer.toString(start +  limit));
		}
		System.out.println("sql====================="+sql);
		return sql;
	}
	
	public static String getSchema() {
		return "CUSTRISK.";
	}

}
