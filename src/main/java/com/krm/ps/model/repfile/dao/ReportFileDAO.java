package com.krm.ps.model.repfile.dao;

import java.util.List;
import java.util.Map;

import com.krm.ps.model.vo.CodeRepConfigure;
import com.krm.ps.model.vo.CodeRepJhgz;
import com.krm.ps.model.vo.CodeRepJhgzZf;
import com.krm.ps.model.vo.CodeRepSubmitalist;
import com.krm.ps.model.vo.KettleData;
import com.krm.ps.model.vo.RepFlFomat;
import com.krm.ps.model.vo.RepFlRep;
import com.krm.ps.model.vo.RepFlfile;
import com.krm.ps.sysmanage.reportdefine.vo.Report;

public interface ReportFileDAO {
	
	/**
	 * @param condate
	 * @param organId
	 * @param reports
	 * @return
	 */
	public List getUncheckReports(String condate,String organId,String reports);

	List<RepFlFomat> getRepAList(String[] repIDarray, String batch);

	public List getreptabL(Long pkid);

	/**
	 * 保存文件
	 * 
	 * @param repflfile
	 */
	void saveRepFlfile(RepFlfile repflfile);

	void updateRepLog(String date, String organCode, String logtype,
			String status, String reportid, String batch, int idx);

	/**
	 * 获得上报数据
	 * 
	 * @param reportId
	 * @param organId
	 * @param reportDate
	 * @param page
	 * @param idxid
	 * @param levelFlag
	 * @return
	 */
	public StringBuffer getReportDataByPageAll(String begindate,
			String enddate, Long reportId, String organId, String reportDate,
			int idxid, List reptab, Map mapC);

	/**
	 * 根据报文格式 获取具体的报文
	 * 
	 * @param repFla
	 *            报文格式
	 * @return 报文格式的列表
	 */
	List<RepFlRep> getRepFlRep(RepFlFomat repFla);

	/**
	 * @param organCode
	 * @param idx
	 * @return
	 */
	List getOrganidx(String organCode, int idx);

	/**
	 * 获得外部机构号
	 * 
	 * @param organCode
	 */
	String getouterorgan(String organCode, String systemid);

	/**
	 * 获取关联的字段
	 * 
	 * @param repFla
	 *            报文主题
	 * @return 报文关联的报文 和报文
	 */
	List getRepFlFiled(RepFlRep repFla);

	/**
	 * 通过报文的ID号、数据日期、批次获取到文件列表
	 * 
	 * @param repfileid
	 * @param repdate
	 * @param batch
	 * @param organCode
	 * @return
	 */
	int getfilecount(Long repfileid, String repdate, String batch,
			String organCode, String filename);

	void insertrepDatF(String[] sqls);

	/**
	 * 查询文件
	 */
	List<RepFlfile> getRepFlfile(RepFlfile repflfile);

	/**
	 * 查询文件
	 */
	List<RepFlfile> getRepFlfile2(RepFlfile repflfile, String organid,
			int isadmin, int idxid);

	/**
	 * 通过主键更新文件的下载次数
	 */
	void updateRepFlfile(RepFlfile repfile);

	/**
	 * 查询文件
	 */
	public List<RepFlfile> getRepFlfile(String pkids);

	public void delRepFlfile(String pkids);

	/**
	 * 删除打包好的文件
	 * 
	 * @param repfile
	 */
	void delRepFlfile(RepFlfile repfile);

	/**
	 * 获取物理表名
	 * 
	 * @param reportID
	 * @param date
	 * @return
	 */
	String getPhyTableName(String reportID, String date);

	/**
	 * 查询未锁定报表
	 */
	public List getRepislock(String organId, String dataDate);

	/**
	 * 查询reportid 是否存在 code_rep_status表中
	 * 
	 * @param organId
	 * @param dataDate
	 * @return
	 */
	public List getRepislock2(String organId, String dataDate, String reportid);

	public Long getReportByid(Report report, String showlevel);

	public Report getReport(Long reportId);

	/**
	 * 查询映射字段
	 * 
	 * @param
	 * @return
	 */
	public List getMapColumn(Long reportId);

	List<KettleData> getAll();

	void savektrForm(KettleData kettleData);

	void addAttribute(String pkid, String attribute1, String message,
			String attribute2, String attribute3);

	byte[] getAttribute5(String pkid);

	void delKtr(String pkid);

	List<KettleData> queryktr(String ktrname, String ktrremark, String ktrtime);
    
	public List<Report> getReports(String reports);

	public String getJrxkzh(String organId);

	/**
	 * 查询报文生成前配置信息
	 * @param codeRepConfigure
	 * @return
	 */
	public List<CodeRepConfigure> getRepConfigure(CodeRepConfigure codeRepConfigure);

	/**
	 * 保存报文生成前配置信息
	 * @param codeRepConfigure
	 * @return
	 */
	public int saveRepConfigure(CodeRepConfigure codeRepConfigure);
	
	/**
	 * 删除报文生成前配置信息
	 * @param pkid
	 * @return
	 */
	public int delRepConfigure(long pkid);
	
	/**
	 * 查询核检数据
	 * @param codeRepConfigure
	 * @return
	 */
	public List<CodeRepJhgz> getRepJhgz(CodeRepJhgz codeRepJhgz,int isadmin, int idxid );
	/**
	 * 修改检核结果问题提出现原因
	 * @param pkid
	 * @param urlreasons
	 */
	public void updateRepJhgz(String pkid ,String urlreasons);
	
	/**
	 * 查询检核结果单条
	 * @param pkid
	 * @param urlreasons
	 */
	public CodeRepJhgz selectRepJhgz(String pkid);
	/**
	 * 查找报送模板列
	 * @param reportid
	 * @return
	 */
	public List listRepTarget(String  reportid);
	
	
	/**
	 * 查询核检数据
	 * @param codeRepConfigure
	 * @return
	 */
	public List<CodeRepJhgzZf> getRepJhgzZf(CodeRepJhgzZf codeRepJhgzZf ,int isadmin, int idxid);
	/**
	 * 修改检核结果问题提出现原因
	 * @param pkid
	 * @param urlreasons
	 */
	public void updateRepJhgzZf(String pkid ,String urlreasons);
	
	/**
	 * 查询检核结果单条
	 * @param pkid
	 * @param urlreasons
	 */
	public CodeRepJhgzZf selectRepJhgzZf(String pkid);
	
	
	/**
	 * 查询报送清单
	 * @param CodeRepSubmitalist
	 * @return
	 */
	public List<CodeRepSubmitalist> getRepSubmitalist(CodeRepSubmitalist repSubmitalist);
	
	public void jdbcUpdatSql(String sql);
	
	/**
	 * 查询报送清单单条
	 * @param pkid
	 * @param urlreasons
	 */
	public CodeRepSubmitalist selectCodeRepSubmitalist(String pkid);
	
	/**
	 * 修改报送清单备注
	 * @param pkid
	 * @param urlreasons 备注
	 */
	public int updateRepsubmitAlist(String pkid ,String urlreasons);
	
	/**
	 * 修改报送清单备注
	 * @param pkid
	 * @param INFORMATION 填报人信息
	 */
	public int updateRepsubmitAlistBy(String pkid ,String information);
	
}
