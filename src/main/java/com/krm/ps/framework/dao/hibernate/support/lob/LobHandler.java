package com.krm.ps.framework.dao.hibernate.support.lob;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

public interface LobHandler
{
	OutputStream getBinaryOutputStream(Object blob) throws SQLException;

	InputStream getBinaryInputStream(Object blob) throws SQLException;

	byte[] getBytes(Object blob) throws SQLException;

	int getBufferSize(Object blob) throws SQLException;
}
