package com.krm.ps.model.repfile.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.itextpdf.text.Document;
import com.krm.ps.model.vo.CodeRepConfigure;
import com.krm.ps.model.vo.CodeRepJhgz;
import com.krm.ps.model.vo.CodeRepJhgzZf;
import com.krm.ps.model.vo.CodeRepSubmitalist;
import com.krm.ps.model.vo.KettleData;
import com.krm.ps.model.vo.RepFlfile;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.tarsk.service.TarskService;
import com.krm.ps.tarsk.vo.SubTarskInfo;
import com.krm.ps.tarsk.vo.Tarsk;

public interface RepFileService {

	/**
	 * 
	 * @param repIDarray
	 * @param organCode
	 * @param condate
	 * @param batch
	 * @param i
	 * @param cstatus
	 *            增全量标志
	 */
	public List createFile(String begindate, String enddate, List repIDarray,
			String organCode, String condate, HttpServletRequest request,
			String systemid, String batch, int i, String cstatus,Tarsk t,SubTarskInfo stk,Map<String, String> schedule);

	public List<RepFlfile> getRepFlfile(RepFlfile repflfile, String organid,
			int isadmin, int idxid);

	/**
	 * 通过主键获得文件对象
	 * 
	 * @param repfile
	 * @return
	 */
	public RepFlfile getRepFlfileOne(RepFlfile repfile);

	/**
	 * 通过主键更新文件的下载次数
	 */
	public void updateRepFlfile(RepFlfile repfile);

	public List getRepFlfileOne(String pkids);

	public void delRepFlfile(String pkids);

	/**
	 * 删除文件
	 * 
	 * @param repfile
	 */
	public void delRepFlfile(RepFlfile repfile);

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
	public List getIfridlock(String organId, String dataDate, String reportid);

	public Long getreportId(Long reportId, String showlevel);

	public List<KettleData> getAll();

	public void savektrForm(KettleData kettleData);

	public void addAttribute(String pkid, String attribute1, String message,
			String attribute2, String attribute3);

	public byte[] getAttribute5(String pkid);

	public void delKtr(String pkid);

	public List<KettleData> queryktr(String ktrname, String ktrremark,
			String ktrtime);
	/**
	 * @param conDate
	 * @param organId
	 * @param reports
	 * @return
	 */
	public List getUncheckReports(String conDate,String organId,String reports);
	/**查询一张或多张报表对象
	 * @param reports
	 * @return
	 */
	public List<Report> getReports(String reports);
	
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
	public List<CodeRepJhgz> getRepJhgz(CodeRepJhgz codeRepJhgz ,int isadmin, int idxid);
	
	public HSSFWorkbook getXlsWork(List datas ,String[] titles,String organnName);
	
	public HSSFWorkbook getXlsWorkZf(List datas ,String[] titles,String organnName);
	
	public HSSFWorkbook getXlsWorkZf(List datas , List titles);
	
	public HSSFWorkbook getXlsWorkUser(List datas , List titles);
	/**
	 * 添加pdf
	 * @param  doc   文本
	 * @param  datas 数据
	 * @param  tableHeader 表头
	 * @param  filetitle  标题
	 * @param  flay  1:检核结果表  2:总分结果表
	 * @return
	 */
	public void addPdf(Document doc,List datas,String[] tableHeader ,String filetitle ,String flay);
	
	/**
	 * 添加pdf
	 * @param  doc   文本
	 * @param  datas 数据
	 * @param  tableHeader 表头
	 * @param  filetitle  标题
	 * @param  flay  1:检核结果表  2:总分结果表
	 * @return
	 */
	public void addPdf(Document doc,List datas,String[] tableHeader);
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
	 * 查询核检总分核对数据
	 * @param codeRepConfigure
	 * @return
	 */
	public List<CodeRepJhgzZf> getRepJhgzZf(CodeRepJhgzZf codeRepJhgzZf ,int isadmin, int idxid );
	/**
	 * 修改检核结果总分核对问题提出现原因
	 * @param pkid
	 * @param urlreasons
	 */
	public void updateRepJhgzZf(String pkid ,String urlreasons);
	
	/**
	 * 查询检核结果总分核对单条
	 * @param pkid
	 * @param urlreasons
	 */
	public CodeRepJhgzZf selectRepJhgzZf(String pkid);
	/**
	 * 电子签章请求xml
	 * @param pkid
	 * @param urlreasons
	 */
	public String rsXml(Map map);
	/**
	 * 电子签章响应xml记录用
	 * @param pkid
	 * @param urlreasons
	 */
	public String rsXmlout(Map map);
	
	/**
	 * 签章接口请求响应管理
	 * @param xmlbase64  PDF Base64 字符串
	 * @param map 其它基础参数
	 * @return
	 */
	public String resprept(Map map ,String url); 
	
	
	/**
	 * 查询报送清单
	 * @param CodeRepSubmitalist
	 * @return
	 */
	public List<CodeRepSubmitalist> getRepSubmitalist(CodeRepSubmitalist repSubmitalist);
	
	public HSSFWorkbook getXlsWorkRepSubmitalist(List datas, String[] titles) ;
	
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
	public int  updateRepsubmitAlist(String pkid ,String urlreasons);
	
	/**
	 * 修改报送清单备注
	 * @param pkid
	 * @param INFORMATION 填报人信息
	 */
	public int updateRepsubmitAlistBy(String pkid ,String information);
	
	/**	//检索相同业务条件下即将创建的子任务是否已正在运行中
	 * @param date
	 * @param organId
	 * @param reparray
	 * @return true =重复，false=无重复
	 */
	public List<SubTarskInfo> getRuningSubTarsk(String date,String organId,List reparray);
	
}