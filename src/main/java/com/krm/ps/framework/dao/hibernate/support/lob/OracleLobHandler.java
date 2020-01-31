package com.krm.ps.framework.dao.hibernate.support.lob;

import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;

import oracle.sql.BLOB;

import org.hibernate.lob.SerializableBlob;

public class OracleLobHandler implements LobHandler
{

	public OutputStream getBinaryOutputStream(Object blob) throws SQLException
	{
		if (blob == null)
		{
			return null;
		}
		return ((BLOB) ((SerializableBlob) blob).getWrappedBlob())
			.getBinaryOutputStream();
	}

	public InputStream getBinaryInputStream(Object blob) throws SQLException
	{
		if (blob == null)
		{
			return null;
		}
		return ((SerializableBlob) blob).getBinaryStream();
	}

	public byte[] getBytes(Object blob) throws SQLException
	{
		if (blob == null)
		{
			return null;
		}
		BLOB oracleBlob = (BLOB) ((SerializableBlob) blob).getWrappedBlob();
		return oracleBlob.getBytes(1, (int) oracleBlob.length());
	}

	public int getBufferSize(Object blob) throws SQLException
	{
		if (blob == null)
		{
			return 0;
		}
		return ((BLOB) ((SerializableBlob) blob).getWrappedBlob())
			.getBufferSize();
	}
}
