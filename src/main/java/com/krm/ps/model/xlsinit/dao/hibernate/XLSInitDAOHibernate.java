package com.krm.ps.model.xlsinit.dao.hibernate;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.krm.ps.framework.dao.hibernate.BaseDAOHibernate;
import com.krm.ps.framework.dao.jdbc.BaseDAOJdbc;
import com.krm.ps.model.xlsinit.dao.XLSInitDAO;
import com.krm.ps.model.xlsinit.util.SqlHelp;
import com.krm.ps.model.xlsinit.vo.DataSet;
import com.krm.ps.model.xlsinit.vo.RowSet;
import com.krm.ps.model.xlsinit.vo.Table;
import com.krm.ps.model.xlsinit.vo.TableField;
import com.krm.ps.model.xlsinit.vo.TableList;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.util.DBDialect;
import com.krm.ps.util.SysConfig;


public class XLSInitDAOHibernate extends BaseDAOHibernate implements XLSInitDAO{
	protected BaseDAOJdbc jdbc;    

	public void setDAOJdbc(BaseDAOJdbc jdbc) {
		this.jdbc = jdbc;
	}
	
	public void XLSInit(List sql) throws Exception{
		Connection c = null;
		Statement s = null;
	   try{
		    c = jdbc.getDataSource().getConnection();
			c.setAutoCommit(false);

			s = c.createStatement();
			
			for (int i=0;i<sql.size();i++){
				s.executeUpdate((String)sql.get(i));				
			}

			s.close();
			c.commit();
			c.setAutoCommit(true);	
	   }catch (Exception e) {
			try {
				c.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				if (c != null && !c.isClosed()) {
					c.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.xlsinit.dao.XLSInitDAO#XLSInit(com.krm.slsint.xlsinit.vo.DataSet, com.krm.slsint.xlsinit.vo.TableList, com.krm.slsint.usermanage.vo.User)
	 */
	public void XLSInit(DataSet dataSet, TableList tableList, User user)
		throws Exception
	{
		Connection c = null;
		
		StringBuffer sb = null;
		StringBuffer fsb = null;
		StringBuffer sql = null;
		
		List data = dataSet.getXlsData();
		List tab = tableList.getTableList();
		
		for( int i = 0; i < tab.size(); i++ ) {
			Table t = ( Table )tab.get( i );
			for( int j = 0; j < data.size(); j++ ) {
				List blobValue  = new ArrayList();
				sql = new StringBuffer();
				RowSet r = ( RowSet )data.get( j );
				
				Map rd = r.getRowData();
				sb = new StringBuffer( "insert into " );
				sb.append( t.getTableName() ).append( "(" );
				fsb = new StringBuffer( "values(" );
				//字段名称
				List f = t.getField();
				for( int k = 0; k < f.size(); k++ ){
					TableField td = ( TableField )f.get( k );
					if ( 's' == SysConfig.DB ){
						 if ( ! td.is_pk() ){
							 sb.append( td.get_name() ).append(",");
						 }
					 }else{
						 sb.append( td.get_name() ).append(",");
					 }
					String fv = "";
					if (td.is_pk()){
				    	 if ('s'!=SysConfig.DB){				    						    	
				    		 fv = DBDialect.genSequence(td.get_seq()) + ",";
				    	 }
				     }else if (!"".equals(td.get_constval())&&td.get_constval()!=null){				    	 
				    	 fv = SqlHelp.getConst(td.get_constval(),td, user);
				     }else if  (td.is_drop()&&rd.get(td.get_map())!=null){				    	
				    	 fv = (String)rd.get(td.get_map());
				    	 fv = SqlHelp.wrapType(fv.substring(fv.indexOf("&")+1),td);
				     }else if(rd.get(td.get_map())!=null) {				    	 
				    	 fv = (String)rd.get(td.get_map());				    	 
				    	 fv = SqlHelp.wrapType(fv,td);
				     }else{
				    	 fv = "null,";
				     }
					
					if( (t.getTableName().equals( SqlHelp.FORMAT_TABLE) && td.get_name().equals( SqlHelp.FORMAT_BLOB_FIELD)) || 
							(t.getTableName().equals( SqlHelp.FORMULA_TABLE) && (td.get_name().equals( SqlHelp.FORMULA_BLOB_FIELD1) || 
									td.get_name().equals( SqlHelp.FORMULA_BLOB_FIELD2)))) {
						fsb.append(" ?, ");
						blobValue.add( fv );
					}else {
						fsb.append( fv );
					}
					
				}
				sql.append(sb.substring(0,sb.toString().length() - 1))
				.append(") ").append(fsb.substring(0,fsb.toString().length() - 1))
				.append(")");
				
				try {
					c = jdbc.getDataSource().getConnection();
					//Connection nc = DBDialect.getNativeConnection(c);
					c.setAutoCommit(false);
//					PreparedStatement ps = nc.prepareStatement( sql.toString() );
					PreparedStatement ps = c.prepareStatement( sql.toString() );
					for(int n = 0; n < blobValue.size(); n++) {
						String val = ( String )blobValue.get(n);
						if(val != null || val.length() != 0)
							ps.setBytes(n + 1, val.getBytes() );
						else
							ps.setBytes(n + 1, null);
					}
					ps.execute();
					ps.close();
					c.commit();
				}catch (Exception e) {
					e.printStackTrace();
				}finally {
					try {
						if(null != c) {
							c.close();
						}
					}catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				System.out.println(sql.toString());
			}
			
		}
	}

}
