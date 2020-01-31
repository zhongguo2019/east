package com.krm.ps.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;

// Informix的大对象处理类
class InformixLobHandler extends DefaultLobHandler {

	public String getClobAsString(ResultSet rs, int columnIndex)
			throws SQLException {// called by ClobStringType

		String result = "";
		String encoding = "GBK";

		try {
			InputStream is = rs.getBinaryStream(columnIndex);
			if (is == null) {
				return "";
			}
			byte[] bytes = new byte[is.available()];

			is.read(bytes);
			result = new String(bytes, encoding);
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	public LobCreator getLobCreator() {
		return new InformixDefaultLobCreator();
	}

	protected class InformixDefaultLobCreator extends DefaultLobCreator {

		public void setClobAsString(PreparedStatement ps, int paramIndex,
				String content) throws SQLException {// called by
														// ClobStringType

			String encoding = "GBK";
			try {
				byte[] bytes = content.getBytes(encoding);

				ps.setBinaryStream(paramIndex, new ByteArrayInputStream(bytes),
						bytes.length);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
