package com.krmsoft.test;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;


import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCTEST {
	
	
	
	private static ComboPooledDataSource _ds = new ComboPooledDataSource();  
    
	/**  
	 * 数据库连接池初始化  
	*/    
	static {    
	    try {         
	        
	        _ds.setJdbcUrl("jdbc:oracle:thin:@66.3.43.31:1521:NCDB");    
	        _ds.setUser("eastsc");    
	        _ds.setPassword("eastsc");   
	        _ds.setMaxStatements(0);     
	        //TODO 连接池各种优化配置 按需添加  
	        _ds.setDriverClass("oracle.jdbc.driver.OracleDriver");  
	        } catch (PropertyVetoException e) {  
	          
	        }            
	} 
	
	/** 
	* 获取数据库连接 
	* @return 
	*/  
	private static Connection getConnection() {    
	    Connection conn = null;    
	    try {    
	        conn = _ds.getConnection();    
	    } catch (Exception e) {    
	        e.printStackTrace(); 
	    }    
	    return conn;    
	} 
	
	
	public static Map<String, String> GetRuleMode()
	{
		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		String testSql = "SELECT * FROM  CODE_REP_RULE  WHERE MODEL_ID in (1006,1007,1022)";
		Map<String, String> ruleMap = new HashMap<String, String>();
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			
			rs = (ResultSet)stmt.executeQuery(testSql);
			for (int id = 1; rs.next(); id++) {
			     
				ruleMap.put(rs.getString("MODEL_ID")+";"+rs.getString("TARGET_ID"), rs.getString("RULECODE"));

				if(id%1000==0)
					{
					stmt.executeBatch();
					stmt.clearBatch();
					}
				
			    }
			    rs.close();
			    
			    stmt.executeBatch();
			    System.out.println("GetRulesInfo end ONE!");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			
            try {
            	if(rs!=null)
				rs.close();
            	if(stmt!=null)
				stmt.close();
            	if(conn!=null)
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		}
		
		return ruleMap;
	}
	
	/**
	 * 获取字段中文名称
	 * @return
	 */
	public static Map<String, String> GettargetName()
	{
		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		String testSql = "SELECT * FROM  CODE_REP_RULE  WHERE MODEL_ID in (1006,1007,1022)";
		Map<String, String> ruleMap = new HashMap<String, String>();
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			
			rs = (ResultSet)stmt.executeQuery(testSql);
			for (int id = 1; rs.next(); id++) {
			     
				ruleMap.put(rs.getString("MODEL_ID")+";"+rs.getString("TARGET_ID"), rs.getString("TARGET_NAME"));

				if(id%1000==0)
					{
					stmt.executeBatch();
					stmt.clearBatch();
					}
				
			    }
			    rs.close();
			    
			    stmt.executeBatch();
			    System.out.println("GetRulesInfo end TWO!");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			
            try {
            	if(rs!=null)
				rs.close();
            	if(stmt!=null)
				stmt.close();
            	if(conn!=null)
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		}
		
		return ruleMap;
	}
	
	/**
	 * 
	 * @param tableName
	 * @param model_id
	 * @param keylength 
	 */
//	public void TrdataTable(String tableName,String model_id,int keylength)
	public void TrdataTable(String tableName,String model_id,String no)
	{
		
        //FT_GETCODE 函数
		String dataTable = "EAST_"+tableName;
		String reTable = "RESULT_"+tableName;
		ResultSet rs = null;
		Connection conn = null;
		Statement stmt = null;
		String testSql = "select a.*,a.NBJGH AS NBJGH,F.CHECK_COL AS CHECK_COL ,"+no+" AS TABCLOUM_ZH,T.TAB_DESC "
				+ "from "+dataTable+" A, EAST_DQ_CHECK_RESULT_FULL_BAK t,EAST_DQ_CHECK_RULE_FULL F "
			//	+ "where FT_GETCODE(T.EXCEPT_DESC,"+keylength+")=A.HQCKZH AND T.TAB_DESC=F.RULE_ID " +
				+ "where t.EXCEPT_DESC1=A."+no+" AND T.TAB_DESC=F.RULE_ID " +
						"and F.CHECK_TAB in ('"+dataTable+"') ";
		String rsstring ="";
		
		String tabColum = "";
		Map<String, String> ruleMap = GetRuleMode();
		Map<String, String> ruleMapName = GettargetName();
		
		
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			System.out.println(testSql);
			rs = (ResultSet)stmt.executeQuery(testSql);
			//md = rs.getMetaData();
			
			//rsstring =	RsToXml.generateTxt(rs,null,null);
		
			System.out.println("GetSate And Insert data to rs table start:"+rsstring);
			
			
			for (int id = 1; rs.next(); id++) {
				String tSQL = "INSERT INTO "+reTable+
						      "(PKID,"+
						      "RULECODE,"+
						      "MODEL_ID,"+
						      "TARGET_ID,"+
						       "TARGET_NAME,"+
						       "RTYPE,"+
						       "ORGAN_ID,"+
						       "ETLDATE,"+
						       "RULEMSG,"+
						       "CSTATUS,"+
						       "KEYVALUE,"+
						       "OLDVALUE,"+
						       "NEWVALUE,"+
						       "OPER,"+
						       "EDITDATE,"+
						       "RANGELEVEL,"+
						       "KEYVALUE1,"+
						       "KEYVALUE2,"+
						       "KEYVALUE3,"+
						       "R_VALUE,"+
						       "D_TYPE)";
				tabColum = rs.getString("CHECK_COL");
				rsstring = rs.getString("NBJGH");
				System.out.println(tabColum);
				//tabColumValue = rs.getString(tabColum);
				//tSQL = tSQL+" VALUES('"+tabColum+"','"+tabColumValue+"')";
				
				tSQL= tSQL+"VALUES("+
					      "CODE_RULE_DATA_SQ.NEXTVAL,"+
					      "'"+ruleMap.get(model_id+";"+tabColum)+"',"+
					      "'"+model_id+"',"+
					      "'"+tabColum+"',"+
					      "'"+ruleMapName.get(model_id+";"+tabColum)+"',"+
					      "'1',"+
					      "'"+rsstring+"',"+
					      "'"+rs.getLong("CJRQ")+"',"+
					      "'',"+
					      "'0',"+
					      rs.getLong("PKID")+","+
					      "'"+rs.getString(tabColum)+"',"+
					      "'',"+
					      "'',"+
					      "'',"+
					      "'',"+
					      "0,"+
					      "0,"+
					      "0,"+
					      "'',"+
					      "'1'"+
					      ")";
				
				
				stmt.addBatch(tSQL);

				System.out.println(tSQL);
				
			    }
			    rs.close();
			    
			    stmt.executeBatch();
			    System.out.println("ExecuteBatch  insert rs datatable end!");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
            try {
            	if(rs!=null)
				rs.close();
            	if(stmt!=null)
				stmt.close();
            	if(conn!=null)
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}
			
		
		}	
		
		
		
	}
	
	
	public static void main(String args[]) { 
		JDBCTEST JD = new JDBCTEST();
		//JD.TrdataTable("GRHQCKFHZMXJL","1007",18);
//		JD.TrdataTable("GRHQCKFHZ","1006","HQCKZH");
		JD.TrdataTable("GRHQCKFHZMXJL","1007","HXJYLSH");
	//	JD.TrdataTable("GRJCXX","1022","KHTYBH");
		
    } 
	

}
