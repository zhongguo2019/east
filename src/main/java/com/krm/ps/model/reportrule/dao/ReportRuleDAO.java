package com.krm.ps.model.reportrule.dao;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.krm.ps.model.vo.Page;
import com.krm.ps.model.vo.PaginatedListHelper;
import com.krm.ps.model.vo.ReportResult;
import com.krm.ps.model.vo.ReportRule;
import com.krm.ps.sysmanage.reportdefine.vo.ReportConfig;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;


public interface ReportRuleDAO {
	 public List getDateOrganEditReportForStandard(String paramDate, Long userId,String systemId, String levelFlag);
		
		/**
		 * 查询机构详细补录信息
		 * @param reportId
		 * @param reportType
		 * @param reportDate
		 * @param organId
		 * @param idxid
		 * @return
		 */
		public List getReportGuideDetail(Long reportId, String targettable,
				String organId, int idxid,String reportDate);
		/**
		 * 获报表或都数据模型的类型
		 * @param systemcode  系统标识 （比如人行标准化和客户风险）
		 * @param showlevel   不同层次的模型标识（采集层为1 目标层为2  levelflag）
		 * @return
		 */
		    public List getReportType(Integer systemcode,Integer showlevel);

			/**
			 * 根据报表id获得校验规则
			 * @param reportid
			 * @return
			 */
			public List getReportRuleByReportId(String reportid);
		    /**
			 * 查询哪些机构有哪些表要补录
			 * @param reportId
			 * @param reportType
			 * @param reportDate
			 * @param organId
			 * @param idxid
			 * @return
			 */
			public List getReportGuide(Long reportId, String levelFlag,String systemcode, String reportDate,
					String organId, int idxid,Long userId);
		    
		  //获得机构树父id
			public List getOrganPkid(String organcode,int idx);
		    /**
		     * 获得基本规则类型
		     * @return
		     */
		    public List getBasicRuleType(String flag,String systemcode);
		    /**
			  * 根据规则编码和校验状态 清洗结果
			  * @param rulecode
			  * @param cstatus
			  */
			 public int deleteReportResult(String  rulecode ,String cstatus ,String tablename);
			 /**
				 * 获得报表对象
				 * @param date 日期
				 * @param modelid  模型id
				 * @param ruletype  规则类型
				 * @param organId  机构id
				 * @return
				 */
				public List getReport(String reportids);
		    
		    /**
			 * 根据模型id和flag获得规则,其中flag0为人行,1为客户风险，2为风险预警
			 * @param modelid
			 * @return
			 */
			 public List getReportRuleFlag(String  modelid ,String flag );
			 /**
				 * 根据模型id获得模板id
				 * @param pkid
				 * @return
				 */
			   public List getTemplateByModelid(Long pkid,String targetid);
			   
				/**
				 * 风险预警层调用的存储过程
				 */
				public Object[] checkData(String rulecode,String date,String lastdate);
		    /**
			 * 测试sql语句是否正确
			 * @param sql
			 * @return
			 */
				 public List getNum(String  sql  );
				 
				 /**
					 * 测试规则是不是有要预警生成的数据
					 * @param sql
					 * @return
					 */
						 public boolean testSql(String  sql  );
						  /**
						     * 根据规则类型获得规则名称
						     * @return
						     */
						    public List getRuleType(String pkids);
		    
		    /**
		     * 获得基本规则
		     * @return
		     */
		    public List getReportRule(List reportList, List ruleType,String cstatus);
		    
			/**
			 * 根据模型id获得规则
			 * @param modelid
			 * @return
			 */
			 public List getReportRule(String  modelid  );
		       /**
			    *获得某一类型下的所有报表 
			    * @param reportType  报表类型
			    * @return
			    */
			   public List getReport(List reportType);
			   
			   /**
			    * 根据模型id，指标id获取规则
			    * @param modelid
			    * @param targetid
			    * @return
			    */
			  public List getReportRule(String modelid,String targetid,String rtype);
			  
			  /**
				 * 根据规则编码获得规则列表
				 * @param modelid
				 * @return
				 */
				 public List getReportRuleBycode(String rulecode);
		
		/**
		 * 保存校验规则
		 * @param 
		 * @return
		 */
		public boolean saveReportRule(ReportRule re);
		
		 /**
		 * 获得校验结果
		 * @param date 日期
		 * @param modelid  模型id
		 * @param ruletype  规则类型
		 * @param organId  机构id
		 * @return
		 */
		public List getReportResult(String date,String modelid,String ruletype,List organIdList,String tablename);
		
		/**
		 * 获取数据记录
		 * @param relist
		 * @param datatablename
		 * @return
		 */
		public int getReportData(Set set ,String datatablename);
		
		/**
		 * 保存数据
		 * @param datalist
		 * @param datatablename
		 * @return
		 */
		public List saveReportData(List datalist ,String datatablename);
		
		/**
		 * 调用存储过程在程序内部校验
		 * @param rulecode
		 */
		public Object[] checkData(String rulecode,String date);
		
		/**
		 * 根据日期，规则编码查询校验结果
		 * @param rulecode
		 * @param date
		 * @return
		 */
		public List getReportResult(String rulecode,String date,String tablename);
		
		/**
		 * 根据数据id查询校验结果
		 * @param dataId
		 * @return
		 */
		public List<ReportResult> getReportResultByDataId(String cstatus,String[] idArr,String date,String levelFlag,String targetids);
		
		 /**
		 * 获取数据记录并且插入,用一个sql
		 * @param relist
		 * @param datatablename
		 * @return
		 */
		public int getReportData(String rulecode, String dataDate, String targettable ,String datatablename);
		/**
		 * 分页获得校验结果
		 * @param date 日期
		 * @param modelid  模型id
		 * @param ruletype  规则类型
		 * @param organId  机构id
		 * @return
		 */
		public PaginatedListHelper getReportResultByPage(String date,String modelid,String ruletype,List organIdList,String tablename,Page page);
		 /**
		    * 删除规则
		    * @param rr
		    */
		   public void deleteReportRule(ReportRule rr);
		   /**
		    * 根据报表类型、报表日期查询出所有报表及报表数据状态（REP_DATAF_month_5表中是否有数据）
		    * @param reportType
		    * @param reportDate
		 * @param organId 
		 * @param idxid 
		    * @return
		    */
		public List getReport(Long reportId,List reportType, String reportDate, String organId, int idxid,Long userid,String levelFlag,String systemcode);
		/**
		 * 判断临时表中是否有数据  验证数据校验是否通过
		 * @param reportId
		 * @param temptablename 
		 * @param replaceAll
		 * @param temptablename
		 * @return
		 */
		public int getErrorReportData(String reportId, String reportDate, String temptablename);
		/**
		 * 根据系统标志取出规则列表 0代表人行标准化1代表客户风险
		 * @param tg
		 * @return
		 */
		public List getReportRuleByTg(String tg,String flag);
		/**
		 * 批量列新结果表
		 * @param temMap
		 * @param reportTargetList
		 * @param valueList
		 * @param cstatusList
		 * @param dtypeList
		 * @param dataId
		 * @param reportDate
		 * @return
		 */
		public int updateReportResult(List<ReportConfig> resultablename,String stFlag,Map temMap,List<ReportTarget> reportTargetList ,List valueList,List cstatusList,
				List dtypeList,String[] repDataSort,String reportDate);
		/**
		 * 获得后台预算结果 
		 * @param sql
		 * @param tablename
		 * @return
		 */
		public String getRvalue(String sql,String tablename);
		
		/**
		 * 批量列新數據表
		 * @param temMap
		 * @param reportTargetList
		 * @param valueList
		 * @param cstatusList
		 * @param dtypeList
		 * @param dataId
		 * @param reportDate
		 * @return
		 */
		public int updateReport(List<ReportConfig> resultablename,String stFlag,Map temMap,List<ReportTarget> reportTargetList ,List valueList,List cstatusList,
				List dtypeList,String[] repDataSort,String reportDate);

		public int insertReportService(List<ReportConfig> resultablename, String stFlag,
				Map temMap, List<ReportTarget> reportTargetList, List valueList,
				List cstatusList, String reportDate);
}
