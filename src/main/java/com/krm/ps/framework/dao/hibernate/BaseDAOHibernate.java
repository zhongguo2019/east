package com.krm.ps.framework.dao.hibernate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import oracle.sql.BLOB;
import oracle.sql.CLOB;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.FlushMode;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.JDBCException;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.lob.SerializableClob;
import org.hibernate.type.Type;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.krm.ps.framework.dao.DAO;
import com.krm.ps.framework.dao.hibernate.helper.HibernatePersistHelper;
import com.krm.ps.framework.dao.hibernate.support.lob.LobHandler;
import com.krm.ps.util.Constant;
import com.krm.ps.util.Constants;
import com.krm.ps.util.ConvertUtil;
import com.krm.ps.util.StringUtil;

public abstract class BaseDAOHibernate extends HibernateDaoSupport implements
		DAO {
	public static final int DEFAULT_BUFFER_SIZE = 1024 * 64;

	public static final int MAX_BUFFER_SIZE = -1;

	public static final int DEFAULT_LOB_SIZE = 256;

	protected LobHandler lobHandler;

	protected final Log log = LogFactory.getLog(getClass());

	public byte[] getBlobObjectAsBytes(final Class clazz,
			final Serializable id, final String blobPropertyName) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				try {
					return lobHandler.getBytes(PropertyUtils.getProperty(
							session.get(clazz, id), blobPropertyName));
				} catch (Exception e) {
					log.debug(e.getMessage());
				}
				return null;
			}
		};

		return (byte[]) getHibernateTemplate().execute(callback);
	}
	public Object getObject(Class clazz, Serializable id) {
		return getHibernateTemplate().get(clazz, id);
	}

	public List getObjects(Class clazz) {
		return getHibernateTemplate().loadAll(clazz);
	}

	public void removeObject(Class clazz, Serializable id) {
		getHibernateTemplate().delete(getObject(clazz, id));
	}

	public void removeObject(Object o) {
		getHibernateTemplate().delete(o);
	}

	public void saveObject(Object o) {
		getHibernateTemplate().saveOrUpdate(o);
		getHibernateTemplate().flush();
	}

	public void setLobHandler(LobHandler lobHandler) {
		this.lobHandler = lobHandler;
	}

	private String[] getColNamesFromHQL(String hql) {
		String[] cnames = null;
		String temp = hql.toLowerCase();
		int pos1 = temp.indexOf("from");
		if (pos1 >= 0) {
			temp = hql.substring(0, pos1);
			cnames = temp.split(",");
			for (int i = 0; i < cnames.length; i++) {
				cnames[i] = cnames[i].trim();
				int pos2 = cnames[i].lastIndexOf(' ');
				if (pos2 >= 0) {
					cnames[i] = cnames[i].substring(pos2).trim();
				}
			}
		}
		return cnames;
	}
	/**
	 * update Object 
	 * @param entity
	 */
	public int upate(Object entity){
		int is;
		 try {
			this.getHibernateTemplate().update(entity);
			is =1;
		} catch (DataAccessException e) {
			e.printStackTrace();
			is =0;
		}
		return is;
	}
	public List getListOfMapFromSQL(final String sql){
		HibernateCallback callback = new HibernateCallback() {

			public Object doInHibernate(Session session)  throws HibernateException, SQLException{
				
				List list = new ArrayList();
				int rowcount = 0;
				ResultSet rs = null;
				Statement statement = null;
				try {
					//session.beginTransaction();
					Connection conn = session.connection();
					log.debug(sql);
					statement = conn.createStatement();
					rs = statement.executeQuery(sql);
					ResultSetMetaData rsm = rs.getMetaData();
					int columnCount = rsm.getColumnCount();
					String columnNames[] = new String[columnCount];
					for (int i = 1; i <= columnCount; i++) {
						columnNames[i-1] = rsm.getColumnName(i).toLowerCase();
					}
					while(rs.next()){
						Map map = new HashMap();
						for (int i = 1; i <= columnCount; i++) {
							String columnName = columnNames[i-1];
							Object data = rs.getObject(i);
							byte[] bb;
							if(data!=null && data.getClass().getName().equals("oracle.sql.BLOB")){
								oracle.sql.BLOB b = (BLOB) data;
								InputStream is = b.binaryStreamValue();
								
								//System.out.println(b.length());
								bb = new byte[Integer.parseInt(b.length()+"")];
								try {
									b.open(0);
									is.read(bb,0,bb.length);
								} catch (IOException e) {
									e.printStackTrace();
								}finally{
									b.close();
								}
								
								//b.open(0);
								//System.out.println(b.stringValue());
								//bb = b.getBytes();
								//System.out.println(bb.length);
								map.put(columnNames[i-1], bb);
							}else{
								map.put(columnNames[i-1], data);
							}
							
							/*
							System.out.println(columnName.toLowerCase() + "  :  " + rs.getObject(i));
							if("cdt".equals(columnName.toLowerCase())){
								Date d = (Date) rs.getObject(i);
								System.out.println(d.getTime() + "-------------------------");
							}*/
						}
						list.add(map);
						
					}
				} catch (JDBCException ex) {
					log.error(ex.getMessage());
					ex.printStackTrace();
				} finally{
					try {
						if(statement!=null){
							statement.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					if(rs!=null ){
						try {
							rs.close();
						} catch (SQLException e) {
							e.printStackTrace();
						}
					}
					if(session!=null && session.isConnected()){
						session.close();
					}
				}
				return list;
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}


	private Object getCallableStatementOutParameter(CallableStatement cs,
			int type, int index, int lobSize) throws SQLException {
		Object result = null;
		switch (type) {
		case Types.BLOB:
			Blob blob = cs.getBlob(index);
			if (blob != null) {
				if (lobSize <= 0) {
					result = blob.getBytes(1, (int) blob.length());
				} else {
					result = blob.getBytes(1, lobSize);
				}
			}
			break;
		case Types.CLOB:
			Clob clob = cs.getClob(index);
			if (clob != null) {
				if (lobSize <= 0) {
					result = clob.getSubString(1, (int) clob.length());
				} else {
					result = clob.getSubString(1, lobSize);
				}
			}
			break;
		default:
			result = cs.getObject(index);
			break;
		}
		return result;
	}

	private Object[] getCallableStatementOutParameter(CallableStatement cs,
			int[] types, int[] indexes, int lobSize) throws SQLException {
		Object[] result = null;
		if (types != null) {
			result = new Object[types.length];
			if (indexes != null && indexes.length == types.length) {
				for (int i = 0; i < types.length; i++) {
					result[i] = getCallableStatementOutParameter(cs, types[i],
							indexes[i], lobSize);
				}
			} else {
				for (int i = 0; i < types.length; i++) {
					result[i] = getCallableStatementOutParameter(cs, types[i],
							i + 1, lobSize);
				}
			}
		}
		return result;
	}

	private void registerCallableStatementOutParameter(CallableStatement cs,
			int[] types, int[] indexes) throws SQLException {
		if (types != null) {
			if (indexes != null && indexes.length == types.length) {
				for (int i = 0; i < types.length; i++) {
					cs.registerOutParameter(indexes[i], types[i]);
				}
			} else {
				for (int i = 0; i < types.length; i++) {
					cs.registerOutParameter(i + 1, types[i]);
				}
			}
		}
	}

	private void setCallableStatementInParameters(CallableStatement cs,
			Object[] values, int[] indexes) throws SQLException {
		if (values != null) {
			if (indexes != null && indexes.length == values.length) {
				for (int i = 0; i < values.length; i++) {
					cs.setObject(indexes[i], values[i]);
				}
			} else {
				for (int i = 0; i < values.length; i++) {
					cs.setObject(i + 1, values[i]);
				}
			}
		}
	}

	protected void setPreparedStatementParameters(PreparedStatement ps,
			Object[] values) throws SQLException {
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				ps.setObject(i + 1, values[i]);
			}
		}
	}

	private void setQueryParameters(Query query, Map parameters) {
		if (parameters != null) {
			for (Iterator i = parameters.entrySet().iterator(); i.hasNext();) {
				Map.Entry entry = (Map.Entry) i.next();
				String paramName = (String) entry.getKey();
				Object paramValue = entry.getValue();
				if (paramValue instanceof Collection) {
					query.setParameterList(paramName, (Collection) paramValue);
				} else if (paramValue instanceof Object[]) {
					query.setParameterList(paramName, (Object[]) paramValue);
				} else {
					query.setParameter(paramName, paramValue);
				}
			}
		}
	}

	private void setQueryParameters(Query query, Object[] values) {
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
	}

	protected byte[] getBlobObjectAsBytes(final Object o,
			final String blobPropertyName) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				try {
					return lobHandler.getBytes(PropertyUtils.getProperty(o,
							blobPropertyName));
				} catch (Exception e) {
					log.debug(e.getMessage());
				}
				return null;
			}
		};

		return (byte[]) getHibernateTemplate().execute(callback);
	}

	protected String getBlobObjectAsString(final Object o,
			final String blobPropertyName) {
		String content = null;
		try {
			byte[] bytes = getBlobObjectAsBytes(o, blobPropertyName);
			if (bytes != null) {
				content = StringUtil.getDbString(bytes);
			}
		} catch (UnsupportedEncodingException e) {
			if (log.isDebugEnabled()) {
				log.debug(e.getMessage());
			}
		}
		return content;
	}

	/**
	 * 执行HQL查询，将查询结果转换为树控件所需的xml格式
	 * 
	 * @param hql
	 *            HQL语句
	 * @return xml串
	 */
	protected String getTreeXML(String hql) {
		return getTreeXML(new String[] { hql });
	}

	protected String getTreeXML(String sql, Object[][] entities,
			Object[][] scalaries, String[] cnames) {
		return getTreeXML(list(sql, entities, scalaries), cnames);
	}

	protected String getTreeXML(List tree, String[] cnames) {
		return ConvertUtil.convertListToAdoXml(tree, cnames);
	}

	protected String getTreeXML(String[] hqls) {
		if (hqls.length >= 0) {
			return ConvertUtil.convertListToAdoXml(list(hqls),
					getColNamesFromHQL(hqls[0]));
		}
		return "";
	}

	protected int jdbcUpdate(final String sql, final Object[] values) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				PreparedStatement ps = session.connection().prepareStatement(
						sql);
				setPreparedStatementParameters(ps, values);
				return new Integer(ps.executeUpdate());
			}
		};
		return ((Integer) getHibernateTemplate().execute(callback)).intValue();
	}
	
	protected long jdbcUpdateKey(final String sql, final Object[] values) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				PreparedStatement ps = session.connection().prepareStatement(
						sql,Statement.RETURN_GENERATED_KEYS);
				setPreparedStatementParameters(ps, values);
				ps.executeUpdate();
				ResultSet rs=ps.getGeneratedKeys();
				long id=0;
				if(rs.next()){
					id= rs.getLong(1);
				}
				return id;
			}
		};
		return Long.parseLong( getHibernateTemplate().execute(callback).toString());
	}
	
	protected Object jdbcCall(final String sql, final Object[] inValues,
			int outType, int[] inIndexes, int outIndex) {
		return jdbcCall(sql, inValues, outType, inIndexes, outIndex,
				DEFAULT_LOB_SIZE);
	}

	protected Object jdbcCall(final String sql, final Object[] inValues,
			int outType, int[] inIndexes, int outIndex, int lobSize) {
		Object[] outValues = jdbcCall(sql, inValues, new int[] { outType },
				inIndexes, new int[] { outIndex }, lobSize);
		if (outValues != null && outValues.length > 0) {
			return outValues[0];
		}
		return null;
	}

	protected Object[] jdbcCall(String sql, Object[] inValues, int[] outTypes,
			int[] inIndexes, int[] outIndexes) {
		return jdbcCall(sql, inValues, outTypes, inIndexes, outIndexes,
				DEFAULT_LOB_SIZE);
	}

	protected Object[] jdbcCall(final String sql, final Object[] inValues,
			final int[] outTypes, final int[] inIndexes,
			final int[] outIndexes, final int lobSize) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				CallableStatement cs = session.connection().prepareCall(sql);
				setCallableStatementInParameters(cs, inValues, inIndexes);
				registerCallableStatementOutParameter(cs, outTypes, outIndexes);
				cs.execute();
				Object[] result = getCallableStatementOutParameter(cs, outTypes,
						outIndexes, lobSize);
				cs.close();
				return result;
			}
		};
		return (Object[]) getHibernateTemplate().execute(callback);
	}

	/**
	 * 执行HQL查询
	 * 
	 * @param hql
	 *            HQL查询语句
	 * @return 查询结果列表
	 */
	protected List list(String hql) {
		return getHibernateTemplate().find(hql);
	}

	protected List list(final String hql, final Map parameters) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				setQueryParameters(query, parameters);
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}

	protected List list(String hql, Object[] values) {
		return getHibernateTemplate().find(hql, values);
	}

	/**
	 * 执行原始SQL语句查询
	 * 
	 * @param sql
	 *            SQL语句
	 * @param entities
	 *            实体Map：key=表别名，value=vo类的class
	 * @param scalaries
	 *            标量Map
	 * @return 查询结果列表
	 * 
	 * <code>
	 * String sql = "SELECT t.pkid AS {t.pkId}, t.groupname AS {t.groupName}, " +
	 *			"t.manager AS {t.manager}, t.grade AS {t.grade}, t.groupkind AS {t.groupKind}, " +
	 *			"t.supergroupid AS {t.superGroupId}, t.disporder AS {t.dispOrder}, t.isleaf AS {t.isLeaf}, " +
	 *			"t.status-1 AS {t.status}, t.createtime AS {t.createTime}, t.edittime AS {t.editTime} " +
	 *			"FROM umg_group t START WITH t.supergroupid = 10 CONNECT BY PRIOR t.pkid = t.supergroupid";
	 *
	 * System.out.println(dao.executeSQL(sql, new Object[][]{{"t", Group.class}}, null).size());
	 * </code>
	 * 
	 * <code>
	 * String sql = "SELECT to_number(pkid) AS pkId,kindname AS groupName," +
	 *			"status-1 AS status,null AS manager,0 AS superGroupId " +
	 *			"FROM umg_groupkind WHERE length(pkId)=2" +
	 *			"UNION " +
	 *			"SELECT pkId,groupname,status-1 AS status,manager,superGroupId " +
	 *			"FROM umg_group WHERE status<>9 ";
	 *
	 * Object[][] scalaries = {
	 *		{"pkId", Hibernate.INTEGER},
	 *		{"groupName", Hibernate.STRING}, 
	 *		{"status", Hibernate.INTEGER},
	 *		{"manager", Hibernate.STRING}, 
	 *		{"superGroupId", Hibernate.INTEGER}};
	 *		
	 * System.out.println(dao.executeSQL(sql, null, scalaries).size());
	 * </code>
	 */
	protected List list(String sql, Object[][] entities, Object[][] scalaries) {
		return list(sql, entities, scalaries, null);
	}

	public List list(String sql, Object[][] entities, Object[][] scalaries,
			int maxResults) {
		return list(sql, entities, scalaries, null, maxResults);
	}

	public List list(String sql, Object[][] entities, Object[][] scalaries,
			Object[] values) {
		return list(sql, entities, scalaries, values, 0);
	}

	protected List list(String sql, Object[][] entities, Object[][] scalaries,
			Object[] values, int maxResults) {
		return list(sql, entities, scalaries, values, 0, maxResults);
	}

	protected List page(String sql, Object[][] entities, Object[][] scalaries,
			Object[] values, int pageNo, int pageSize) {
		return list(sql, entities, scalaries, values, (pageNo - 1) * pageSize,
				pageSize);
	}

	protected List list(final String sql, final Object[][] entities,
			final Object[][] scalaries, final Object[] values,
			final int firstResult, final int maxResults) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				if (entities != null) {
					for (int i = 0; i < entities.length; i++) {
						query.addEntity(entities[i][0].toString(),
								(Class) entities[i][1]);
					}
				}
				if (scalaries != null) {
					for (int i = 0; i < scalaries.length; i++) {
						query.addScalar(scalaries[i][0].toString(),
								(Type) scalaries[i][1]);
					}
				}
				setQueryParameters(query, values);
				if (firstResult > 0) {
					query.setFirstResult(firstResult);
				}
				if (maxResults > 0) {
					query.setMaxResults(maxResults);
				}
				return query.list();
			}
		};
		return (List) getHibernateTemplate().execute(callback);
	}

	protected List list(String[] hqls) {
		List result = null;
		if (hqls.length > 0) {
			result = list(hqls[0]);
		}
		for (int i = 1; i < hqls.length; i++) {
			result.addAll(list(hqls[i]));
		}
		return result;
	}

	protected void saveBlobObjectForOracle(final Object o,
			final String blobPropertyName, final byte[] data) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				try {
					PropertyUtils.setProperty(o, blobPropertyName, Hibernate
							.createBlob(new byte[1]));
					session.setFlushMode(FlushMode.AUTO);
					session.saveOrUpdate(o);
					session.flush();
					session.refresh(o, LockMode.UPGRADE);
					writeBufferToOutStream(data, lobHandler
							.getBinaryOutputStream(PropertyUtils.getProperty(o,
									blobPropertyName)));
					session.flush();
				} catch (Exception e) {
					log.debug(e.getMessage());
				}
				session.flush();

				return null;
			}
		};
		getHibernateTemplate().execute(callback);
	}

	protected void saveBlobObjectForOracle(final Object o,
			final String blobPropertyName, final InputStream in) {
		saveBlobObjectForOracle(o, blobPropertyName, in, DEFAULT_BUFFER_SIZE);
	}

	protected void saveBlobObjectForOracle(final Object o,
			final String blobPropertyName, final InputStream in,
			final int bufferSize) {
		if (o != null && blobPropertyName != null
				&& blobPropertyName.length() > 0 && in != null) {
			HibernateCallback callback = new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					try {
						PropertyUtils.setProperty(o, blobPropertyName,
								Hibernate.createBlob(new byte[1]));
						session.setFlushMode(FlushMode.AUTO);
						session.saveOrUpdate(o);
						session.flush();
						session.refresh(o, LockMode.UPGRADE);
						Object blob = PropertyUtils.getProperty(o,
								blobPropertyName);
						writeInStreamToOutStream(in, lobHandler
								.getBinaryOutputStream(blob), bufferSize);
						session.flush();
					} catch (Exception e) {
						log.debug(e.getMessage());
					}
					return null;
				}
			};
			getHibernateTemplate().execute(callback);
		}
	}

	protected void saveBlobObjectForOracle(Object o, String blobPropertyName,
			String content) {
		try {
			saveBlobObjectForOracle(o, blobPropertyName, StringUtil
					.getDbBytes(content));
		} catch (UnsupportedEncodingException e) {
			if (log.isDebugEnabled()) {
				log.debug(e.getMessage());
			}
		}
	}

	/**
	 * 执行HQL查询，返回唯一结果
	 * 
	 * @param hql
	 *            HQL查询语句
	 * @return 查询结果对象
	 */
	protected Object uniqueResult(final String hql) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				return session.createQuery(hql).uniqueResult();
			}
		};
		return getHibernateTemplate().execute(callback);
	}

	protected Object uniqueResult(final String hql, final Map parameters) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				setQueryParameters(query, parameters);
				return query.uniqueResult();
			}
		};
		return getHibernateTemplate().execute(callback);
	}

	protected Object uniqueResult(final String hql, final Object[] values) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				setQueryParameters(query, values);
				return query.uniqueResult();
			}
		};
		return getHibernateTemplate().execute(callback);
	}

	protected int update(String hql) {
		return update(hql, (Map) null);
	}

	/**
	 * 执行HQL带参数的批量更新，包括update及delete语句
	 * 
	 * @param hql
	 *            HQL语句
	 * @param parameters
	 *            命名参数Map
	 * @return 更新的记录数
	 * 
	 * <code>
	 * String hql = "UPDATE GroupKind SET kindName=:param1 WHERE pkId=50";
	 * Map parameters = new HashMap();
	 * parameters.put("param1", "自定义组");
	 * dao.update(hql, parameters);
	 * </code>
	 */
	protected int update(final String hql, final Map parameters) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				setQueryParameters(query, parameters);
				return new Integer(query.executeUpdate());
			}
		};
		return ((Integer) getHibernateTemplate().execute(callback)).intValue();
	}

	protected int update(final String hql, final Object[] values) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				setQueryParameters(query, values);
				return new Integer(query.executeUpdate());
			}
		};
		return ((Integer) getHibernateTemplate().execute(callback)).intValue();
	}

	protected Object writeBlobObjectToStream(final Class clazz,
			final Serializable id, final String blobPropertyName,
			final OutputStream out) {
		return writeBlobObjectToStream(clazz, id, blobPropertyName, out,
				DEFAULT_BUFFER_SIZE);
	}

	protected Object writeBlobObjectToStream(final Class clazz,
			final Serializable id, final String blobPropertyName,
			final OutputStream out, final int bufferSize) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Object o = session.get(clazz, id);
				try {
					writeInStreamToOutStream(lobHandler
							.getBinaryInputStream(PropertyUtils.getProperty(o,
									blobPropertyName)), out, bufferSize);
				} catch (Exception e) {
					if (log.isDebugEnabled()) {
						log.debug(e.getMessage());
					}
				}
				return o;
			}
		};

		return getHibernateTemplate().execute(callback);
	}

	protected void writeBufferToOutStream(byte[] buffer, OutputStream out)
			throws IOException {
		try {
			out.write(buffer);
		} catch (IOException e) {
			if (log.isDebugEnabled()) {
				log.debug(e.getMessage());
			}
		}
		out.close();
	}

	protected void writeInStreamToOutStream(InputStream in, OutputStream out,
			int bufferSize) throws IOException {
		if (in != null && out != null) {
			try {
				if (bufferSize > 0) {
					ConvertUtil.convertInStreamToOutStream(in, out, bufferSize);
				} else if (bufferSize == MAX_BUFFER_SIZE) {
					ConvertUtil.convertInStreamToOutStream(in, out, in
							.available());
				} else {
					ConvertUtil.convertInStreamToOutStream(in, out,
							DEFAULT_BUFFER_SIZE);
				}
				out.flush();
			} finally {
				try {
					in.close();
				} catch (IOException e) {
					log.debug(e.getMessage());
				}
				try {
					out.close();
				} catch (IOException e) {
					log.debug(e.getMessage());
				}
			}
		}
	}

	protected boolean sthRepeat(String className, String pkAttributeName,
			Long pkid, String sthAttributeName, String sth, String status) {
		if (null != status) {
			status = status + "!=" + Constants.STATUS_DEL + " and ";
		}
		String hql = "from " + className + " where " + status + pkAttributeName
				+ "!=:pkid and " + sthAttributeName + " = :name";
		// System.out.println(hql);
		Long spkid = pkid;
		if (null == spkid) {
			spkid = new Long(-1);
		}
		Map map = new HashMap();
		map.put("pkid", spkid);
		map.put("name", sth);
		List ls = list(hql, map);
		if (ls.size() > 0)
			return true;
		return false;
	}

	public boolean sthRepeat(String className, String pkAttributeName,
			Long pkid, String sthAttributeName, String sth) {
		return sthRepeat(className, pkAttributeName, pkid, sthAttributeName,
				sth, "status");
	}

	/**
	 * 左少杰 2006-07-21增加 保存ORACLE Clob数据
	 * 
	 * @param o
	 * @param clobPropertyName
	 * @param content
	 */
	protected void saveClobObjectForOracle(final Object o,
			final String clobPropertyName, final String content) {
		if (o != null && clobPropertyName != null
				&& clobPropertyName.length() > 0 && content != null) {
			HibernateCallback callback = new HibernateCallback() {
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					try {
						PropertyUtils.setProperty(o, clobPropertyName,
								Hibernate.createClob(" "));
						session.setFlushMode(FlushMode.AUTO);
						session.saveOrUpdate(o);
						session.flush();
						session.refresh(o, LockMode.UPGRADE);
						Object clobObject = PropertyUtils.getProperty(o,
								clobPropertyName);
						SerializableClob serializableClob = (SerializableClob) clobObject;
						CLOB clob = (CLOB) serializableClob.getWrappedClob();
						Writer pw = clob.getCharacterOutputStream();
						pw.write(content);
						pw.close();
						session.flush();
					} catch (Exception e) {
						log.debug(e.getMessage());
					}
					return null;
				}
			};
			getHibernateTemplate().execute(callback);
		}
	}

	protected String getClobObjectForOracle(final Object o,
			final String clobPropertyName) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				try {
					Object clobObject = PropertyUtils.getProperty(o,
							clobPropertyName);
					SerializableClob serializableClob = (SerializableClob) clobObject;
					CLOB clob = (CLOB) serializableClob.getWrappedClob();
					int len = (int) clob.length();
					int byteRead = 0;
					char[] bytes = new char[len];
					BufferedReader reader = new BufferedReader(clob
							.getCharacterStream());
					while ((byteRead = reader
							.read(bytes, 0, bytes.length))>0) {
						reader.read(bytes, 0, byteRead);
					}
					String content = new String(bytes);
					reader.close();
					return content;
				} catch (Exception e) {
					log.debug(e.getMessage());
				}
				return null;
			}
		};
		return (String) getHibernateTemplate().execute(callback);
	}


	// Batch insert into VO to table
	public void batchSaveVO(final List objectList) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				int i = 0;
				for (Iterator it = objectList.iterator(); it.hasNext();) {
					session.save(it.next());
					if (i % 20 == 0) {
						session.flush();
						session.clear();
					}
					i++;
				}
				session.flush();
				session.clear();
				return null;
			}
		};
		getHibernateTemplate().execute(callback);
	}
//	 Batch insert into VO to table
	public void batchSaveOrUpdateVO(final List objectList) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				int i = 0;
				for (Iterator it = objectList.iterator(); it.hasNext();) {
					session.saveOrUpdate(it.next());
					if (i % 20 == 0) {
						session.flush();
						session.clear();
					}
					i++;
				}
				session.flush();
				session.clear();
				return null;
			}
		};
		getHibernateTemplate().execute(callback);
	}

	// Batch insert into VO to table
	public void batchJdbcUpdate(final String[] sqls) {
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Statement ps = session.connection().createStatement();
				for (int i = 0; i < sqls.length; i++) {
					if(sqls[i] == null){
						continue;
					}else{
						ps.addBatch(sqls[i]);	
					}
				}
				ps.executeBatch();
				session.flush();
				session.clear();
				return null;
			}
		};
		getHibernateTemplate().execute(callback);
	}
	
	public List getObjects(String tableName, List resultColumns, Map conditionMap, Class entityClass) throws Exception
	{
		StringBuffer sqlBuffer = new StringBuffer();
		Map scalarMap = new HashMap();
		if (tableName != null 
				&& (resultColumns !=null || resultColumns.size() != 0) && conditionMap != null)
		{
			String tempStr = "";
			sqlBuffer.append("select distinct ");
			if (resultColumns == null)
			{
				sqlBuffer.append("{t.*} ");
			}
			else
			{
				int resultColumnsSize = resultColumns.size();
				int flag = resultColumnsSize - 1;
				String column = "";
				for (int i = 0; i < resultColumnsSize; i++)
				{
					sqlBuffer.append("t.");
					tempStr = (String)resultColumns.get(i);
					// 对是String类型的字段进行处理
					column = tempStr.indexOf(Constant.DATATYPE_JAVA_STRING_POSTFIX) > -1 ? tempStr.replaceAll(Constant.DATATYPE_JAVA_STRING_POSTFIX, "") : tempStr;
					sqlBuffer.append(column);
					scalarMap.put(column, Hibernate.STRING);
					if (i < flag)
						sqlBuffer.append(",");
				}
			}
			sqlBuffer.append(" from ");
			sqlBuffer.append(tableName).append(" t");
			sqlBuffer.append(" where 1=1 ");
			Set conditionSet = conditionMap.keySet();
			String condition = "";
			boolean strFlag = false;
			for (Iterator it = conditionSet.iterator(); it.hasNext();)
			{
				condition = (String)it.next();
				Object object = conditionMap.get(condition);
				strFlag = false;
				if (condition.indexOf(Constant.DATATYPE_JAVA_STRING_POSTFIX) > -1)
				{
					log.debug("there is a condition string [" + condition + "] with the type String");
					strFlag = true;
					condition = condition.replaceAll(Constant.DATATYPE_JAVA_STRING_POSTFIX, "");
				}
				sqlBuffer.append(" and ").append(" t.").append(condition);
				if (object instanceof List)
				{
					tempStr = listToString((List)object);
					sqlBuffer.append(" in (").append(tempStr).append(") "); 
				}
				else 
				{					
					if (object instanceof String)
					{
						tempStr = (String)object;
						if (strFlag) // 当前值类型为String
							sqlBuffer.append("='").append(tempStr.replaceAll(Constant.DATATYPE_JAVA_STRING_POSTFIX, "")).append("'");
						else
							sqlBuffer.append("=").append(tempStr);
					}
					else
						sqlBuffer.append("=").append(object.toString());
				}
				// if (index < flag)
					// sqlBuffer.append(" and ");
				// index++;
			}
		}
		String resultSQL = sqlBuffer.toString();
		log.debug("最终生成的SQL为：" + resultSQL);
		Object[][] entities = null;
		if (resultColumns == null)
			entities = new Object[][]{{"t", entityClass}};
		Set scalarSet = scalarMap.keySet();
		int scalarSize = scalarSet.size();
		Object[][] scalars = new Object[scalarSize][2];
		int index = 0;
		for (Iterator it = scalarSet.iterator(); it.hasNext();)
		{
			scalars[index][0] = it.next();
			scalars[index][1] = Hibernate.STRING;
		}
		return list(resultSQL, entities, scalars);		
	}
	
	private String listToString(List list)
	{
		int size = list.size();
		int flag = size - 1;
		String resultStr = "";
		for (int i = 0; i < size; i++)
		{
			resultStr = resultStr + list.get(i).toString();
			if (i < flag)
				resultStr += ",";
		}
		return resultStr;
	}

	/**
	 * @see com.krm.dao.DAO#deleteOrUpdateObjectsWithHQLSQL()
	 */
	public boolean deleteOrUpdateObjectsWithHQLSQL(final String queryString)
	{
		getHibernateTemplate().execute(new HibernateCallback(){
			public Object doInHibernate(Session session) throws HibernateException, SQLException
			{
				Query query = session.createQuery(queryString);
				query.executeUpdate();
				session.flush();
				return null;
			}
		});
		return true;
	}

	/**
	 * @see com.krm.dao.DAO#getObjectSpecifiedInfo(java.lang.String[], java.lang.Class, java.lang.String)
	 */
	public List getObjectSpecifiedInfo(String[] fields, Class objectClass, String condition)
	{
		log.debug("查询指定表中的指定信息！");
		String entityName = objectClass.getName();
		String alias = "obj";
		StringBuffer sqlBuffer = new StringBuffer();
		sqlBuffer.append("select ");
		int size = 0;
		if (fields == null)
			sqlBuffer.append(alias).append(" ");
		else
		{
			size = fields.length;
			boolean flag = false;
			for (int i = 0; i < size; i++)
			{
				sqlBuffer.append(flag ? "," : "").append(alias).append(".").append(fields[i]).append(" ");
				flag = true;
			}
		}
		sqlBuffer.append(" from ").append(entityName).append(" ").append(alias).append(" where ")
			.append(condition.replaceAll("(\\w+)\\s*(>=|<=|<>|[=><])", alias + ".$1$2"));
		log.debug("查询对象SQL为：=== " + sqlBuffer.toString());
		return list(sqlBuffer.toString());
	}
	
	/**
	 * <p></p> 
	 *
	 * @param sql
	 * @param entityMap
	 * @param firstResult 
	 * @param maxResults 此参数与firstResult参数均不为null的时候，才进行两个值的设置，两个值才起作用
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @author 皮亮
	 * @version 创建时间：2010-4-28 上午10:49:57
	 */
	public List listObjectsWithoutSessionManage(final String sql, final Map entityMap,
		final Map instanceClassMap, 
		final Integer firstResult, final Integer maxResults)
	{
		log.debug("传入的SQL为：" + sql);
//		final Pattern pattern = Pattern.compile("(\\w*\\.\\w*)(?=\\s+[^\\}\\s]{1}|\\s*$)");
		
//		Pattern pattern = Pattern.compile("\\{\\s*(\\S*)\\s*\\}");
		final List typeList = new ArrayList();
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {	
				String newSql = sql;
//				ClassMetadata metaData = session.getSessionFactory().getClassMetadata(entityClass
				newSql = convertStarToProperties(newSql, entityMap);
				log.debug("把a.*形式SQL转化后为：" + newSql);
				Pattern pattern1 = Pattern.compile("(\\w*\\.\\w*)(?=\\s+[^\\}\\s]{1}|\\s*=)");
				Matcher matcher1 = pattern1.matcher(newSql);
				String alias = null;
				String[] aliass = null;
				Class entityClass = null;
//				ClassMetadata metaData = null;
//				SQLQuery query = session.createSQLQuery(sql.replaceAll("\\{\\s*(\\S*)\\.(\\S*)\\s*\\}", "$1_$2"));
//				query.addScalar("fileName", Hibernate.STRING);
//				String bcc = HibernatePersistHelper.getTableColumnName(FileRepositoryRecord.class, "fileName");
//				String columnName;
//				List list1 = query.list();
				boolean flag = false;
				Type type = null;
				List flagList = new ArrayList();
				int start;
				while (matcher1.find())
				{
					alias = matcher1.group(1);
					log.debug("alias ======== " + alias);
					aliass = alias.split("\\.");
					log.debug("aliass ======== " + aliass[1]);
					flag = flagList.contains(aliass[0]);
					entityClass = (Class)entityMap.get(aliass[0]);
					if (entityClass == null)
						log.error("class [" + entityClass + "] are not be specified, please specify it so can analysis it");
					if (flag)
					{
						
					}
					else
					{
						flagList.add(aliass[0]);
						// 更新实体表名
						String className = entityClass.getName();
						newSql = newSql.replaceAll("(?<!\\w)(" + className.substring(className.lastIndexOf(".") + 1) + ")(?=\\s)", HibernatePersistHelper.getTableName(entityClass));
					}
					start = matcher1.end();
					if (start < newSql.indexOf(" from ")) // 在from之前的才进行类型的查询
					{
						type = HibernatePersistHelper.getTableColumnHType(entityClass, aliass[1]);
						typeList.add(type);
					}
					// 把属性名换为列名
					log.debug(entityClass);
					log.debug(aliass[0]);
					log.debug(aliass[1]);
					String columnName = HibernatePersistHelper.getTableColumnName(entityClass, aliass[1]);
					log.debug("column name is ======= " + columnName);
					newSql = newSql.replaceAll("(" + aliass[0]+ "\\.)"
						+ aliass[1] + "(?!\\s*\\})", "$1" + columnName);
				}
				newSql = newSql.replaceAll("\\{\\s*(\\w*)\\.(\\w*)\\s*\\}", "$1_$2");
				// 把替换后的别名设置hibernate类型
				pattern1 = Pattern.compile("(?!\\w*\\s*as\\s*|\\w*\\s*AS\\s*)(\\w*_\\w*)(?=.*from)");
				matcher1 = pattern1.matcher(newSql);
				log.debug("替换后的sql为：==== " + newSql);
				SQLQuery query = session.createSQLQuery(newSql);
				if (firstResult != null && maxResults != null)
				{
					query.setFirstResult(firstResult.intValue());
					query.setMaxResults(maxResults.intValue());
				}
				int index = 0;
				
				while (matcher1.find())
				{
					log.debug("列别名为: " + alias);
					alias = matcher1.group(1);
					log.debug("列：" + alias + " 的类型为" + (Type)typeList.get(index));
					query.addScalar(alias, (Type)typeList.get(index));
					index++;
				}
				
				List list = query.list();
				return list;
			}
		};
		Pattern pattern = Pattern.compile("\\{\\s*(\\S*)\\s*\\}");
		Matcher matcher = pattern.matcher(sql);
		List list = (List)getHibernateTemplate().execute(callback);
		Class preClass = null;
		Object obj = null;
		Object[] result = null;
		String alias = null;
		String[] aliass = null;
		Class entityClass = null;
		int resultSize = list.size();
		Object[] entityObjects = null;
		List resultList = new ArrayList();
		int index = 0;
		int entitySize = instanceClassMap.keySet().size();
		int objIndex = 0;
		try {
			for (int j = 0; j < resultSize; j++)
			{
				result = (Object[])list.get(j);
				matcher.reset();
				index = 0;
				preClass = null;
				obj = null;
				objIndex = 0;
				if (entitySize > 1)
					entityObjects = new Object[entitySize];
				while (matcher.find())
				{
					alias = matcher.group(1);
					aliass = alias.split("\\.");
					entityClass = (Class)instanceClassMap.get(aliass[0]);
					if (entityClass == null)
						log.error("please specify the right Class to be instanced");
					if (preClass == null)
						obj = entityClass.newInstance();
					if (entityClass != preClass && preClass != null)
					{
						if (obj != null)
						{
							if (entitySize > 1)
							{
								entityObjects[objIndex] = obj;
								objIndex++;
							}
							else
								resultList.add(obj);
						}
						obj = entityClass.newInstance();
					}
					//log.debug(obj);
					//log.debug(aliass[1]);
					//log.debug(result[index]);
					BeanUtils.setProperty(obj, aliass[1], result[index]);
					index++;
					preClass = entityClass;
				}
				if (entitySize > 1)
				{
					entityObjects[objIndex] = obj;
					resultList.add(entityObjects);
				}
				else
					resultList.add(obj);
			}
		} catch (Exception e) {
			log.error("查询出结果，在实例化对象时出现问题！", e);
			throw new RuntimeException("查询出结果，在实例化对象时出现问题！", e);
		}
		
		return resultList;
//		HibernateCallback callback = new HibernateCallback() {
//			public Object doInHibernate(Session session)
//					throws HibernateException, SQLException {
////				(?!\{\s*)\S*\.\S*(?=\s*\})
////				(\S*\.\S*)(?=\s*(?=as|AS))
//				SingleTableEntityPersister entity = (SingleTableEntityPersister)((SessionFactoryImplementor)getSessionFactory()).getEntityPersister(FileShareData.class.getCanonicalName());
//				//Map map = entity.
//				entity.getEntityName();
////				entity.getget
//				SQLQuery query = session.createSQLQuery(sql);
//				query.addScalar("mpkid", Hibernate.LONG);
//				query.addScalar("docRight", Hibernate.STRING);
//				
//				query.list();
//				return session;			
////				
//			}	
//		};
//		List list = (List)getHibernateTemplate().execute(callback);
//		return list;
	}
	
	/**
	 * <p>遍历属性集合，把a.*形式的转为a.pkid as {a.pkid}形式的</p> 
	 *
	 * @param newSql 处理的SQL
	 * @param persistClassMap a.* 中，可通过a得到对应的实体类 
	 * @return
	 * @author 皮亮
	 * @version 创建时间：2010-5-14 上午10:41:37
	 */
	private String convertStarToProperties(String newSql, Map persistClassMap)
	{
		Pattern pattern1 = Pattern.compile("\\w*\\.\\*");
		Matcher matcher = pattern1.matcher(newSql);
		String tmpStr;
		String alias = null;
		Set propertySet = null;
		StringBuffer strBufferAfterCon = new StringBuffer();
		while (matcher.find())
		{
			tmpStr = matcher.group();
			// 得到别名
			alias =tmpStr.split("\\.")[0];
			// 得到clazz的属性集合
			propertySet = HibernatePersistHelper.getPropertySet((Class)persistClassMap.get(alias));
			// 遍历属性集合，把a.*形式的转为a.pkid as {a.pkid}形式的
			for (Iterator it = propertySet.iterator(); it.hasNext();)
			{
				Object tmp = it.next();
				strBufferAfterCon.append(alias).append(".").append(tmp)
					.append(" as ").append("{").append(alias).append(".").append(tmp)
					.append("}").append(",");
			}
			newSql = newSql.replaceAll(alias + "\\." + "\\*", 
				strBufferAfterCon.toString().replaceAll(",$", ""));
			log.debug("把a.*形式的转为a.pkid as {a.pkid}形式的，最终SQL为：" + newSql);
		}
		return newSql;
	}
	
	public void batchExecuteJDBCSSQL(final Collection<String> sqlCollection, final Integer intervalCount){
		HibernateCallback callback = new HibernateCallback() {
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				Statement ps = session.connection().createStatement();
				int excSqlCount = 0;
				for (Iterator<String> iterator = sqlCollection.iterator(); iterator.hasNext();) {
					excSqlCount++;
					//System.out.println("sql:"+sqls[i]);
					String sql = iterator.next();
					if(sql == null){
						continue;
					}else{
						ps.addBatch(sql);	
					}
					if (intervalCount != null && excSqlCount % intervalCount == 0) {
						ps.executeBatch();
						ps.clearBatch();
					}
				}
				ps.executeBatch();
				session.flush();
				session.clear();	
				return null;
			}
		};
		getHibernateTemplate().execute(callback);
	}
	
	
}
