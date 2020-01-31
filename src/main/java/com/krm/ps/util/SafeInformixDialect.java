package com.krm.ps.util;

import java.sql.Types;

import org.hibernate.dialect.InformixDialect;
import org.hibernate.type.Type;


/**
 * Informix数据库方言，覆盖hibernate的某些（不合适的）默认实现。类名在hibernate的配置文件中配置
 * 
 */
public class SafeInformixDialect extends InformixDialect {

	 public SafeInformixDialect() {
         super();
         registerColumnType(Types.BLOB, "BYTE" );
         registerColumnType(Types.CLOB, "TEXT");
   }

	/**
	 * vo配置的标识符生成策略是native：
	 * supportsIdentityColumns方法return true，就会使得hibernate使用IdentityGenerator方式生成标识符，
	 * 而在IDS10.4/windows/informix-JDBC.3.00.JC3下测试有问题，
	 * return false使得hibernate使用SequenceGenerator方式，没有问题，wsx 2006-11-01
	 * 
	 */
	public boolean supportsIdentityColumns() {
		return false;
	}	

	/**
	 * 当前连接用户不是systables的owner。wsx 2006-11-02
	 */
	public String getSequenceNextValString(String sequenceName) {
		return "select " + sequenceName + ".nextval from informix.systables where tabid=1";
	}

}