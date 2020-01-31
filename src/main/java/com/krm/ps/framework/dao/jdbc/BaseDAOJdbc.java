package com.krm.ps.framework.dao.jdbc;

import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.krm.ps.util.SysConfig;

public class BaseDAOJdbc  extends JdbcDaoSupport
{
	public static final int DEFAULT_LOB_SIZE = 256;
	
	protected List list(String sql, Object[] values)
	{
		return getJdbcTemplate().queryForList(sql, values);
	}

	protected int update(String sql, Object[] values)
	
	{
		return getJdbcTemplate().update(sql, values);
	}
	
	protected void save(String sql)
	
	{
		getJdbcTemplate().execute(sql);
	}
	
	protected int update(String sql, Object[] values,int[] argTypes)
	{
		return getJdbcTemplate().update(sql, values,argTypes);
	}
	/**
	 * 调用没有返回值的存储过程
	 * @param sql	存储过程调用sql
	 * @param inValues	输入参数
	 * @param inIndexes	输入参数位置索引
	 */
	protected void callProcedure(final String sql, final Object[] inValues,
			int[] inIndexes){
		callProcedure(sql, inValues, inIndexes, null, null, DEFAULT_LOB_SIZE);
	}
	
	/**
	 * 调用有返回值的存储过程
	 * @param sql	存储过程调用sql
	 * @param inValues	输入参数值
	 * @param inIndexes	输入参数位置索引
	 * @param outType	输出参数类型
	 * @param outIndex	输出参数位置索引
	 * @return
	 */
	protected Object callProcedure(final String sql, final Object[] inValues,
			int[] inIndexes, int outType, int outIndex){
		return callProcedure(sql, inValues, inIndexes, outType, outIndex,
				DEFAULT_LOB_SIZE);
	}

	/**
	 * 调用有返回值的存储过程逻辑的实现方法
	 * 获取返回值为单一值
	 * @param sql	存储过程调用sql
	 * @param inValues	输入参数值
	 * @param inIndexes	输入参数位置索引
	 * @param outType	输出参数类型
	 * @param outIndex	输出参数位置索引
	 * @param lobSize	打字段的尺寸
	 * @return
	 */
	protected Object callProcedure(final String sql, final Object[] inValues,
			int[] inIndexes, int outType, int outIndex, int lobSize) {		
		Object[] outValues = callProcedure(sql, inValues, inIndexes,
				new int[] { outType }, new int[] { outIndex }, lobSize);
		if (outValues != null && outValues.length > 0) {
			return outValues[0];
		}
		return null;
	}

	/**
	 * 调用有返回值的存储过程
	 * 获取返回值为数据结构
	 * @param sql	存储过程调用sql
	 * @param inValues	输入参数值
	 * @param inIndexes	输入参数位置索引
	 * @param outType	输出参数类型
	 * @param outIndex	输出参数位置索引
	 * @return
	 */
	protected Object[] callProcedure(String sql, Object[] inValues, int[] outTypes,
			int[] inIndexes, int[] outIndexes) {
		return callProcedure(sql, inValues, inIndexes, outTypes, outIndexes,
				DEFAULT_LOB_SIZE);
	}
	
	/**
	 * 存储过程调用基础方法
	 * @param sql	调用语句
	 * @param inValues	输入参数
	 * @param inIndexes	输入参数位置
	 * @param outTypes	输出参数类型
	 * @param outIndexes	输出参数位置
	 * @param lobSize	打字段类型尺寸
	 * @return	存储过程执行结果。如果无法获得连接，返回值为null，请调用方自行处理。
	 */
	protected Object[] callProcedure(final String sql, final Object[] inValues,
			final int[] inIndexes, final int[] outTypes, final int[] outIndexes, 
			final int lobSize){
		CallableStatement cs = null;		
		Savepoint sp = null;
	
		try {
			//设置一个保存点 sp ,解决事物嵌套的问题
			//如果是 postgres 数据库 在这里会设置保存点，其他的数据库就不用了
			if(SysConfig.DATABASE.equals("postgres"))
			{
				sp = this.getConnection().setSavepoint("callProcedure1");
//				this.getConnection().getMetaData().supportsSavepoints();
			}
			cs = getConnection().prepareCall(sql);
			setCallableStatementInParameters(cs, inValues, inIndexes);
			registerCallableStatementOutParameter(cs, outTypes, outIndexes);
			cs.execute();
			return getCallableStatementOutParameter(cs, outTypes,
					outIndexes, lobSize);
		} catch (Exception e) {
			if(e instanceof CannotGetJdbcConnectionException){				
				e.printStackTrace();
			}else if(e instanceof SQLException){
				String procedureName = sql.toUpperCase().substring(sql.toUpperCase().indexOf("CALL") + 4);
				procedureName = procedureName.trim();
				if(sql.indexOf("(") > -1){
					procedureName = procedureName.substring(0, procedureName.indexOf("("));
				}
				if (e.getMessage().toUpperCase().indexOf(procedureName) > -1) {					
					procedureName = procedureName.trim();
					if ((System.getProperty("os.name")).toLowerCase().indexOf("window") > -1) {
						logger.error("警告：数据库中找不到存储过程[" + procedureName
								+ "]！！");
					} else {
						logger.error("WARRING：Can't find procedure["
								+ procedureName + "] in database!!");
					}
				} else {
					e.printStackTrace();
				}
				if(SysConfig.DATABASE.equals("postgres"))
				{
					try
					{
						//回滚保存点。		
						this.getConnection().rollback(sp);
					}
					catch (CannotGetJdbcConnectionException e1)
					{
						e1.printStackTrace();
					}
					catch (SQLException e1)
					{
						e1.printStackTrace();
					}finally{
						try
						{
							//释放回滚点。
							this.getConnection().releaseSavepoint(sp);
						}
						catch (CannotGetJdbcConnectionException e1)
						{
							e1.printStackTrace();
						}
						catch (SQLException e1)
						{
							e1.printStackTrace();
						}
					}
				}
			}else{
				e.printStackTrace();
			}
			
		}finally{
			if(cs!=null){
				try
				{
					cs.close();
				}
				catch (SQLException e)
				{
					e.printStackTrace();
					// TODO handle it
				}
			}
		}
		return null;
	}


	/**
	 * 设置输入参数
	 * @param cs	调用存储过程的对象
	 * @param values	输入参数值列表
	 * @param indexes	输入参数值的位置索引
	 * @throws SQLException
	 */
	private void setCallableStatementInParameters(CallableStatement cs,
			Object[] values, int[] indexes) throws SQLException {
		if (values != null) {
			if (indexes != null && indexes.length == values.length) {
				for (int i = 0; i < values.length; i++) {
					System.out.println("set in values: [" + indexes[i] + "];[" + values[i] + "]");
					cs.setObject(indexes[i], values[i]);
				}
			} else {
				for (int i = 0; i < values.length; i++) {
					cs.setObject(i + 1, values[i]);
				}
			}
		}
	}

	/**
	 * 设置输出参数
	 * @param cs	调用存储过程的对象
	 * @param values	输出参数值列表
	 * @param indexes	输出参数值的位置索引
	 * @throws SQLException
	 */
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

	/**
	 * 从存储过程执行结果中取出返回值。
	 * @param cs	调用存储过程的对象
	 * @param types	返回值类型列表
	 * @param indexes	返回值位置索引
	 * @param lobSize	打字段尺寸
	 * @return
	 * @throws SQLException
	 */
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


	/**
	 * 从存储过程执行结果中取出返回值的具体实现逻辑。
	 * @param cs	调用存储过程的对象
	 * @param types	返回值类型
	 * @param indexes	返回值位置索引
	 * @param lobSize	打字段尺寸
	 * @return
	 * @throws SQLException
	 */
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
	
	/**
	 * 根据sql语句和与其对应的参数值进行查询。
	 * 返回值必须是单值结果。并且返回值将直接被转换成参数中指定的返回值类型的对象。
	 * 
	 * @param sql 要执行的sql语句 
	 * @param args sql中的参数
	 * @param argTypes 参数的类型(constants from java.sql.Types)
	 * @param requiredType 返回值的对象类型
	 * @return 与要求的返回值类型一致的对象，或在sql为空的情况下返回NULL。
	 */
	protected Object uniqueResult(String sql, Object[] args,
			Class requiredType) {		
		return getJdbcTemplate().queryForObject(sql, args, requiredType);
	}
	
	/**
	 * 根据sql语句和与其对应的参数值进行查询。
	 * 返回值必须是单值结果。并且返回值将直接被转换成参数中指定的返回值类型的对象。
	 * 
	 * @param sql 要执行的sql语句 
	 * @param requiredType 返回值的对象类型
	 * @return 与要求的返回值类型一致的对象，或在sql为空的情况下返回NULL。
	 */
	protected Object uniqueResult(String sql, Class requiredType) {		
		return getJdbcTemplate().queryForObject(sql, requiredType);
	}
	/**
	 * 以批量的方式执行sql
	 * 在某些数据库上可以大大提高执行sql的效率。
	 * @param sqls	要执行的sql（后面不要带分号）
	 * @return	每条sql的执行结果
	 * @throws Exception
	 */
	protected  int[] executeSQLBatch(String [] sqls) throws Exception {
		/**
		 * 针对天津 数据录入 保存数据，保存不上而修改的，天津的程序保存比较大的表的时候(例如：A1411表)，数据保存不上。可能是informix数据库的原因导致的
		 * 现在该的算法是每次执行50条数据即可。这个事针对informix数据库而修改的。
		 * */
		if('i'==SysConfig.DB){
			int chushu = 50;
			if(sqls.length>chushu){
				int arralen = (sqls.length)/chushu;
				if(sqls.length%chushu!=0){
					arralen = arralen+1;
				}
				String[][] twoarrasqls = new String[arralen][chushu];
				int arralencount = 0;
				for(int j=0;j<sqls.length;j++){
					if(j!=0 &&j>=chushu && j%chushu==0){
						String[] cc = new String[chushu];
						for(int a=0;a<chushu;a++){
							cc[a]=sqls[j-chushu+a];
						}
						twoarrasqls[arralencount]=cc;
						arralencount++;
					}else{
//						if(j==sqls.length-1){
//							int e = sqls.length % chushu;
//							String[] cc = new String[e];
//							for(int f=0;f<cc.length;f++){
//								cc[f]=sqls[j-e+f];
//							}
//							twoarrasqls[arralencount]=cc;
//						}
						//2012.8。23 天津数据录入保存功能 大于200行不能存取数据问题修正   阴大伟
						 if(j==sqls.length-1){
								int e = sqls.length % chushu;
								if(e==0){
									e=chushu;
								}
								String[] cc = new String[e];
								for(int f=0;f<cc.length;f++){
									cc[f]=sqls[sqls.length-e+f];
								}
								twoarrasqls[arralencount]=cc;
							}
					}
				}
				for(int i=0;i<twoarrasqls.length;i++){
					  getJdbcTemplate().batchUpdate(twoarrasqls[i]);
				}
				int retint[]={1};
				return retint;
				
			}else{
				return getJdbcTemplate().batchUpdate(sqls);
			}
		}else{
			return getJdbcTemplate().batchUpdate(sqls);
		}
	}
	
}
