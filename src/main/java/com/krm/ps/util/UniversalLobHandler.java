package com.krm.ps.util;

import java.io.InputStream;
import java.io.Reader;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.jdbc.support.lob.OracleLobHandler;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.AbstractLobHandler;
import org.springframework.jdbc.support.nativejdbc.NativeJdbcExtractor;
import org.springframework.jdbc.support.nativejdbc.SimpleNativeJdbcExtractor;
import org.springframework.jdbc.support.nativejdbc.WebSphereNativeJdbcExtractor;

/**
 * 大字段处理类
 * <p>
 * 使用hibernate时，不同数据库的大字段都用这个类处理（实现spring的接口）
 * </p>
 * 
 * @author wsx
 * 
 */
public class UniversalLobHandler extends AbstractLobHandler {

	private LobHandler handler;

	private synchronized LobHandler getHandler() {
		if (handler == null) {
			if ("oracle".equals(SysConfig.DATABASE)) {
				NativeJdbcExtractor nje = new SimpleNativeJdbcExtractor();
				if("websphere".equals(SysConfig.APPSERVER)) {
					nje = new WebSphereNativeJdbcExtractor();
				}
				OracleLobHandler olh = new OracleLobHandler();
				olh.setNativeJdbcExtractor(nje);
				handler = olh;
			} else if ("informix".equals(SysConfig.DATABASE)) {
				handler = new InformixLobHandler();
			} else {
				handler = new DefaultLobHandler();
			}
		}

		return handler;
	}

	public String getClobAsString(ResultSet rs, int columnIndex)
			throws SQLException {//called by ClobStringType
		return getHandler().getClobAsString(rs, columnIndex);
	}

	public InputStream getClobAsAsciiStream(ResultSet rs, int columnIndex)
			throws SQLException {
		return getHandler().getClobAsAsciiStream(rs, columnIndex);
	}

	public Reader getClobAsCharacterStream(ResultSet rs, int columnIndex)
			throws SQLException {
		return getHandler().getClobAsCharacterStream(rs, columnIndex);
	}

	public LobCreator getLobCreator() {
		return getHandler().getLobCreator();
	}

	public byte[] getBlobAsBytes(ResultSet rs, int columnIndex)
			throws SQLException {
		return getHandler().getBlobAsBytes(rs, columnIndex);
	}

	public InputStream getBlobAsBinaryStream(ResultSet rs, int columnIndex)
			throws SQLException {
		return getHandler().getBlobAsBinaryStream(rs, columnIndex);
	}

}
