package com.krm.ps.util;

public class Constant {

	/**
	 * 月报
	 */
	public static final String FREQUENCY_MONTH = "1";

	/**
	 * 季报
	 */
	public static final String FREQUENCY_QUARTER = "2";

	/**
	 * 半年报
	 */
	public static final String FREQUENCY_HALFYEAR = "3";

	/**
	 * 年报
	 */
	public static final String FREQUENCY_YEAR = "4";

	/**
	 * 日报
	 */
	public static final String FREQUENCY_DAY = "5";

	/**
	 * 旬报
	 */
	public static final String FREQUENCY_TENDAY = "6";

	/**
	 * 周报
	 */
	public static final String FREQUENCY_WEEK = "7";

	/**
	 * 五日报
	 */
	public static final String FREQUENCY_FIVEDAY = "8";
	

	public static final int REPORT_TYPE_BUILDABLE = 1;

	public static final int REPORT_TYPE_CHECKABLE = 2;

	/** 报表类型，临时参照用报表（不纳入报表类型系统中，仅在舍位平衡中临时生成数据的时候使用） */
	public static final int REPORT_TYPE_REF = 0;

	public static final String FORMULA_TYPE_BUILD = "1";

	public static final String FORMULA_TYPE_INNER_REPORT = "2";

	public static final String FORMULA_TYPE_CROSS_REPORT = "3";

	public static final int DATA_TYPE_NORMAL = 0;

	public static final int DATA_TYPE_AMOUNT = 2;

	public static final int DATA_TYPE_PROPORTION = 4;

	public static final int DATA_TYPE_ALL = -1;

	public static final int DATA_TYPE_AMOUNT_ORPROPORTION = 24;

	/**
	 * 用户默认分组
	 */
	public static final int USERN_DEFAULT_GROUP = 0;
	
	//ReportConfigServiceImpl
	public static final int PURPOSE_DIC = 1203;
	public static final int BATCH_DIC = 1202;
	public static final int SJDW_DIC = 1204;
	public static final int SJJD_DIC = 1205;
	public static final int NEW_SJSX_DIC = 1208;
	public static final int OLD_SJSX_DIC = 1207;
	public static final int SUBAREA = 1230;
	public static final int PDF = 1240;
	public static final int PURPOSE_BATCH = 11;
	public static final int CONFIG_BZ = 14;
	public static final int CONFIG_SJDW = 15;
	public static final int CONFIG_SJJD = 16;
	public static final int NEW_CONFIG_SJSX = 18;
	public static final int OLD_CONFIG_SJSX = 17;
	public static final int CONFIG_SUBAREA = 33;
	public static final int CONFIG_PDF = 32;
	public static final String FUNNAME_BZ = "币种";
	public static final String FUNNAME_SJSX_NEW = "数据属性（新）";
	public static final String FUNNAME_SJSX_OLD = "数据属性（旧）";
	public static final int CURRENCY_CONFIG = 41;
	// 报表要素共用配置功能
	public static final int SHARE_CONFIG = 42;
	public static final int CURRENCY_EXCHANGE = 31;
	public static final int CONFIG_CARRY = 21;
	public static final int ITEMSETTING = 34;
	public static int CONFIG_REP_RELATIONSHIP = 20;
	public static final int FREQ_DIC = 1004;
	public static final int CONFIG_PBDATA = 23;
	
	public static int CONFIG_EXTDATA = 35;
	public static int EXTDATA = 1250;
	
	public static int RISK_WARNING = 44;
	
	/**
	 * 普通邮件
	 */
	public static String mail = "1";
	/**
	 * 风险预警邮件
	 */
	public static String riskWarningMail = "2";
	/**
	 * 风险预警邮件新生成
	 */
	public static Integer riskWarningBuild = new Integer("0");
	/**
	 * 风险预警邮件已发送
	 */
	public static Integer riskWarningSend = new Integer("1");
	/**
	 * 风险预警邮件已关闭
	 */
	public static Integer riskWarningClosed = new Integer("2");
	
	public static final Long riskWarningRoleType ;
	
	static {
		riskWarningRoleType = new Long(FuncConfig.getProperty("riskWarning.role", "0"));
	}
	
	public static String riskWarningFunId = "0201";
	
	public static String mailFunId = "0301";
	
	public static String mapInfoFunId = "0008";
	
	/**
	 * 风险预警配置
	 */
	public static final String riskWarningConfig = "44";
	
	/**
	 * 指定了在funconfig文件中配置的风险预警报表类型
	 */
	public static final String riskWarningReportConfig_STRING = "riskwarning.report.type.id";
	
	/**
	 * 这是为了标识变量类型为String
	 */
	public final static String DATATYPE_JAVA_STRING_POSTFIX = "_@STRING";
	
	// 字典表功能常量设置
	
	public final static String FILE_TYPE_CONFIG = "1014";
	/**
	 * 文档共享功能
	 */
	public final static String DOC_SHARE_MANAGE_FUNID = "555";
	
	/**
	 * 文件状态 1
	 */
	public final static String FILE_STATUS_CANUSE = "1";
	
	/**
	 * 保存生成报告模板的功能
	 * 
	 * 这个模板是用来生成一定成型的报告，包括数据及自定义的图表，通过自定义标签的替换完成数据的更新
	 * 完成模板数据的生成
	 */
	public final static String REPORT_GEN_TEMPLATE = "556";
	
	/**
	 * <code>session对象中用于区分系统的变量名称。</code>
	 */
	public static final String SESSION_SYSTEM_ID = "system_id";
	
}
