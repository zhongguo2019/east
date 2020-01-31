package com.krm.ps.util;

import java.io.File;

public class Constants
{
	public static final Integer STATUS_AVAILABLE = new Integer(1);
	
	public static final Integer STATUS_FREEZE = new Integer(2);
	
	public static final Integer STATUS_DEL = new Integer(9);
	
	/** 默认导出文件目录*/
	//public static final String ExportDir = "temp\\";
    public static final String ExportDir = "temp" + File.separator;
	
	/** 导出文件名规则*/
	public static final String ExportFilename = "Z{organcode}{yyyyMM}000000.dat";
	
	/** 添加用户时是否默认关联全部报表*/
	public static final int relatingReport = 1;
	
	/** 添加机构时建立用户的默认权限*/
	public static final Long DEFAULT_ROLETYPE = new Long(2);
	
	/** 添加机构时的默认密码 */
	public static final String DEFAULT_PASSWORD = "888888";
	
	/** 资源文件名称 */
	public static final String BUNDLE_KEY = "ApplicationResources";

	/** 默认字符集 */
	public static final String DEFAULT_CHARSET = "UTF-8";

	/** 文件上传的根目录 */
	public static final String UPLOAD_ROOT_DIR = "/temp";

	/** 默认的附件上传目录 */
	public static final String DEFAULT_ACCESSORY_UPLOAD_DIR = UPLOAD_ROOT_DIR;

	/** 默认的附件类名 */
	public static final String DEFAULT_ACCESSORY_CLASS_NAME = "com.krm.slsint.common.vo.GeneralAccessory";

	/** 附件类名属性 */
	public static final String ATTR_ACCESSORY_CLASSNAME_KEY = "accessoryClassName";

	/** 默认的下载内容类型 */
	public static final String DEFAULT_DOWNLOAD_CONTENT_TYPE = "application/octet-stream";

	/** 下载内容类型在资源文件中的键值的前缀 */
	public static final String DOWNLOAD_CONTENT_TYPE_KEY_PREFIX = "donwload.contenttype.";

	/** 读写BLOB字段的默认缓存大小 */
	public static final int DEFAULT_BUFFER_SIZE = 8192;
	
	/** 任务调度器名称 */
	public static final String SCHEDULER_KEY = "scheduler";

	/** 导航信息属性 */
	public static final String SESSION_ATTR_NAVIGATION_INFO = "navigationInfo";

	/** 禁用导航条属性 */
	public static final String ATTR_DISABLED_NAV = "disabledNav";

	/** 禁用导航条参数 */
	public static final String PARAM_DISABLED_NAV = "disabledNav";

	/** 树控件根结点属性 */
	public static final String ATTR_TREE_ROOT = "treeRoot";

	/** 树控件Xml属性 */
	public static final String ATTR_TREE_XML = "treeXml";

	/** 树控件空根结点属性 */
	public static final String NULL_TREE_ROOT = "-100";

	//信息列表的分页条使用
	public static final int DEF_PAGE_STYLE_ONE = 1;//结果为：11,12,13,14,15,16,17,18,19,20

	public static final int DEF_PAGE_STYLE_TWO = 2;//结果为：2,3,4,5,6,7,8,9,10,11

	public static final int DEF_PAGE_STYLE_THREE = 3;//结果为：1,2,3,4,5,6,7,8,9,10,11

	public static final String ATTR_PAGE_INFO = "pageInfo";

	public static final String ATTR_PAGE_RESULTNUM = "resultNum";

	public static final String PARAM_PAGE_PAGENUM = "pageNum";

	public static final String PARAM_PAGE_PAGENO = "pageNo";
	
	public static final String VIEW_DATA_LOG_MODE_FINISHED = "queryFinished";// 显示完成进度
	
	public static final String VIEW_DATA_LOG_MODE_NOT_FINISHED = "queryNotFinished";// 显示全部未完成机构的完成进度

	/** 一级机构，机构代码为xx000000 */
	public static final String ORG_LEVEL_1 = "1";

	/** 二级机构，机构代码为xxyy0000 */
	public static final String ORG_LEVEL_2 = "2";

	/** 三级机构，机构代码为xxyyzz00 */	
	public static final String ORG_LEVEL_3 = "3";

	/** 四级机构，机构代码为xxyyzzww */	
	public static final String ORG_LEVEL_4 = "4";
	
	/** Session管理 */
	public static final String ADDED_USER = "addUser";
	
	public static final String ADDED_SESSION = "addSession";
	
	public static final String USERS_COUNT = "conut";
	
	public static final String USER_OPER = "userURI";
	
	public static final String METHOD_STRING = "?method=";
	
	/** 流水报表科目编码基准起始值 **/
	public static final int FLOW_REP_ITEM_BEGIN_INDEX = 1;
	
	/** 科目类型编码 **/
	public static final int ITEM_TYPE_NUM = 1;	//数值
	
	public static final int ITEM_TYPE_INT = 2;	//整型
	
	public static final int ITEM_TYPE_STR = 3;	//字符
	
	public static final int ITEM_TYPE_PST = 4;	//比例
	
	public static final String XMl_FILECODING="UTF-8";
	
}
