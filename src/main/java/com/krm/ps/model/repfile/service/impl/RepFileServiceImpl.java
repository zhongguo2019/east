package com.krm.ps.model.repfile.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.xml.sax.SAXException;

import com.ibm.mq.commonservices.internal.utils.Message;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.krm.common.logger.KRMLogger;
import com.krm.common.logger.KRMLoggerUtil;
import com.krm.ps.framework.dao.jdbc.BaseDAOJdbc;
import com.krm.ps.model.repfile.dao.DataFillDAO;
import com.krm.ps.model.repfile.dao.ReportFileDAO;
import com.krm.ps.model.repfile.dao.ReportRuleDAO;
import com.krm.ps.model.repfile.service.RepFileService;
import com.krm.ps.model.repfile.util.BuildRule;
import com.krm.ps.model.repfile.util.FormatOutput;
import com.krm.ps.model.repfile.util.rule.BuildPriImple;
import com.krm.ps.model.repfile.util.rule.FormatOutputImple;
import com.krm.ps.model.reportdefine.dao.ReportDefineDAO;
import com.krm.ps.model.vo.CodeRepConfigure;
import com.krm.ps.model.vo.CodeRepJhgz;
import com.krm.ps.model.vo.CodeRepJhgzZf;
import com.krm.ps.model.vo.CodeRepSubmitalist;
import com.krm.ps.model.vo.DicItem;
import com.krm.ps.model.vo.KettleData;
import com.krm.ps.model.vo.MapColumn;
import com.krm.ps.model.vo.RepFlFomat;
import com.krm.ps.model.vo.RepFlRep;
import com.krm.ps.model.vo.RepFlRepFiled;
import com.krm.ps.model.vo.RepFlfile;
import com.krm.ps.model.vo.ReportRule;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.tarsk.service.TarskService;
import com.krm.ps.tarsk.util.Constant;
import com.krm.ps.tarsk.vo.SubTarskInfo;
import com.krm.ps.tarsk.vo.Tarsk;
import com.krm.ps.util.DateUtil;
import com.krm.ps.util.FuncConfig;
import com.krm.ps.util.StringUtil;

public class RepFileServiceImpl implements RepFileService {

	KRMLogger logger = KRMLoggerUtil.getLogger(RepFileServiceImpl.class);
	private ReportFileDAO repfileDao;
	protected BaseDAOJdbc jdbc;
	ReportRuleDAO rfreportdao;
	ReportDefineDAO rfreportDefineDAO;
	private DataFillDAO dataFillDAO;
	private TarskService tarskService;
    private static  int nums = 0 ;
    private static  Tarsk tt = null ;
	public TarskService getTarskService() {
		return tarskService;
	}

	public void setTarskService(TarskService tarskService) {
		this.tarskService = tarskService;
	}

	public ReportFileDAO getRepfileDao() {
		return repfileDao;
	}

	public void setRepfileDao(ReportFileDAO repfileDao) {
		this.repfileDao = repfileDao;
	}

	public void setDAOJdbc(BaseDAOJdbc jdbc) {
		this.jdbc = jdbc;
	}

	public ReportRuleDAO getRfreportdao() {
		return rfreportdao;
	}

	public void setRfreportdao(ReportRuleDAO rfreportdao) {
		this.rfreportdao = rfreportdao;
	}

	public ReportDefineDAO getRfreportDefineDAO() {
		return rfreportDefineDAO;
	}

	public void setRfreportDefineDAO(ReportDefineDAO rfreportDefineDAO) {
		this.rfreportDefineDAO = rfreportDefineDAO;
	}

	public DataFillDAO getDataFillDAO() {
		return dataFillDAO;
	}

	public void setDataFillDAO(DataFillDAO dataFillDAO) {
		this.dataFillDAO = dataFillDAO;
	}

	/**
	 * 报文文件打包
	 * 
	 * @param repdate
	 *            格式为 yyyy-MM-dd
	 */
	public List createFile(String begindate, String enddate, List repIDarray,
			String organCode, String repdate, HttpServletRequest request,
			String systemid, String batch, int idx, String cstatus,Tarsk t,SubTarskInfo stk,Map<String, String> schedule) {

		List<RepFlFomat> repFlatList = getRepFlAList(null, batch);
		List<RepFlFomat> repFlatL = new ArrayList<RepFlFomat>();
		String bfile = FuncConfig.getProperty("imp.report.filepath");

		File f = new File(bfile);
		if (!f.exists()) {
			f.mkdir();
		}

		String bassfile = bfile;
		logger.info("生成路径：" + bassfile);
	
		schedule.put("total", repIDarray.size() + "");
		schedule.put("isfinished", "false");
		schedule.put("created", "0");
		// 后缀名
		for (int i = 0; i < repFlatList.size(); i++) {
			RepFlFomat repFla = repFlatList.get(i);
			// 获得后缀名
			// 检查获得的批次、日期报文ID是否生成成功
			String datedate = repdate.substring(0, repdate.length() - 2) + "00";
			datedate = datedate.replaceAll("-", "");
			String suffix = repFla.getRepfileexte();
			if ("xml".equals(suffix.toLowerCase())) {
				createFileTxtorXML(repFla, organCode, repdate, bassfile,
						systemid, batch, idx);
			} else if ("dat".equals(suffix.toLowerCase())
					|| "txt".equals(suffix.toLowerCase())) {
				createFileTxtordat(begindate, enddate, repIDarray, repFla,
						organCode, repdate, bassfile, systemid, batch, idx,
						cstatus,t,stk,schedule);
			}
			repFla.setSuccessinfo("文件生成成功");
			// 生成完成后把数据日志表的状态更新
			repFlatL.add(repFla);
		}
		return repFlatL;
	}

	/**
	 * 生成文件名
	 */
	public List<RepFlFomat> getRepFlAList(String[] repIDarray, String batch) {

		List<RepFlFomat> repFlfomatL = repfileDao
				.getRepAList(repIDarray, batch);
		logger.info("getRepFlAList : repFlfomatL>>>" + repFlfomatL.size());
		return repFlfomatL;
	}

	/**
	 * 写dat\txt文件
	 * 
	 * @param repFla
	 * @param idx
	 */
	public void createFileTxtordat(  String begindate,   String enddate,
			List repIDarray,   RepFlFomat repFla,   String organCode,   String date,
			  String basefile,   String systemid,   String batch,   int idx,
			String cstatus,Tarsk t,  SubTarskInfo stk,   Map<String, String> schedule) {
		String suffix = repFla.getRepfileexte();// 文件后缀
		String split = repFla.getRepfilesepar();// 分隔符
	
	/*	// 获得打包机构的金融机构编码 不分操作系统不需要内外机构对照
		String reporganCode = getouterorgan(organCode, systemid);*/
		
		String filename = getrepFilename(repFla, organCode, date);
		//创建主任务是为了生成某机构某报表某时间的报送文件 ，但是主任务的创建实在for外，如何知道主任务要创建的子任务其实系统中系已经有了。
		// 创建主任务
		t = tarskService.tarskInit(t);
		
		//启动主任务
		updateTarsk(t, basefile, organCode);
		String mess = "success";
	 
		try {			
			for (int i = 0; i < repIDarray.size(); i++) {

				Report rep = (Report) repIDarray.get(i);
				rep.setCode(rep.getCode().replaceAll("\\{M\\}",
						date.replaceAll("-", "").substring(4, 6)));
				rep.setCode(rep.getCode().replaceAll("\\{Y\\}",
						date.replaceAll("-", "").substring(0, 4)));
				rep.setCode(rep.getCode().replaceAll("\\{D\\}",
						date.replaceAll("-", "").substring(6, 8)));
			    String aafilename = filename.replaceAll("\\{报表编号\\}",
						rep.getCode());// 替换报文名称
				 
						//开始新建或重建子任务
						SubTarskInfo st = new SubTarskInfo();
						st.setPkid(stk.getPkid());//pkid有值重建子任务，否则新建子任务
						String message = "success";
						initSubtarsk(st, organCode, date, String.valueOf(rep.getPkid()), t.gettId());
						// 获得该模板下所有模型表的关联关系
						List reptab = repfileDao.getreptabL(rep.getPkid());
						// 报送模板的指标
						List reportTargetList1 = rfreportDefineDAO.getReportTargets(rep
								.getPkid());
						String repids = "";
						for (int m = 0; m < reptab.size(); m++) {
							Object[] objmval = (Object[]) reptab.get(m);
							repids += objmval[2] + ",";
						}
		
						try {
		
							// 获得数据List
							if (true) {
								// 打包文件的
								createFileZipJdbc(begindate, enddate, basefile,
										reportTargetList1, repFla, rep, aafilename,
										organCode, idx, date, reptab);
		
								RepFlfile repflfile = getFileInfo(new Long(0),
										aafilename + ".txt", date, "", basefile,
										organCode, repFla.getRepname(), systemid,
										batch, repFla.getPkid());
		
								repfileDao.saveRepFlfile(repflfile);
								
								if (!FuncConfig.isOpenFun("notcreate.log")) {
									RepFlfile repflfilelog = getFileInfo(new Long(0),
											aafilename + ".log", date, "", basefile,
											organCode, repFla.getRepname(), systemid,
											batch, repFla.getPkid());
									repfileDao.saveRepFlfile(repflfilelog);
								}
		
								
		
								logger.info("method:createFileTxtordat打包文件的");
							} else {
								logger.info("method:createFileTxtordat只生成我呢就文件的");
							}
							// 日志类型
							String logtype = "1";
							// 日志状态 2是打包数据的
							String status = "2";
							repfileDao.updateRepLog(date, organCode, logtype, status,
									repids.substring(0, repids.length() - 1), batch, idx);
							//更新子任务信息
							st.setFilePath(basefile+File.separator+aafilename + ".txt");
							st.setStatus(Constant.TARSK_STATUS_END);
						} catch (Exception e) {
							message = e.getMessage().replaceAll("'", "\"");
							st.setStatus(Constant.TARSK_STATUS_ERROR);
							e.printStackTrace();
						}
						finally{
							st.setEndTime((new SimpleDateFormat("yyyyMMdd HH:mm:ss"))
									.format(new Date()));
							st.setMessage(message);
							tarskService.updateSubTarsk(st);
							nums = i + 1 ;
							schedule.put("created",  nums +"");
						}
						
			}
			//for结束子任务创建完毕，主任务结束，更新主任务信息
			t.setStatus(Constant.TARSK_STATUS_END);
			
		} catch (Exception e) {
			mess = e.getMessage().replaceAll("'", "\"");;
			t.setStatus(Constant.TARSK_STATUS_ERROR);
			e.printStackTrace();
		}
		finally{
			t.setEndTime((new SimpleDateFormat("yyyyMMddHHmmss"))
					.format(new Date()));
			t.setMessage(mess);
			tarskService.updateTarsk(t);
			schedule.put("isfinished", "true");
		}
		
	}

	/**
	 * 创建文件并且数据打包
	 * 
	 * @param temid
	 *            为报送模板id
	 */
	public void createFileZipJdbc(String begindate, String enddate,
			String basefile, List reportTargetList, RepFlFomat repFla,
			Report rep, String filename, String organCode, int idx,
			String datastr, List reptab) {
		
		String splite = repFla.getRepfilesepar();
		String jfilename = filename + "." + repFla.getRepfileexte();
		String wfilename = basefile + "/" + filename + "."
				+ repFla.getRepfileexte();
		String wfilename1 = basefile + "/" + filename + "temp."
				+ repFla.getRepfileexte();// chenhao
		String loggname = basefile + "/" + filename + ".log";// 生成一个同名的.log文件
//		logger.info("wfilename:" + wfilename);
//		logger.info("loggname:" + loggname);
		StringBuffer sb = new StringBuffer();
		BuildRule bri = new BuildPriImple();
		FormatOutput foi = new FormatOutputImple();
		// 获得脱敏规则
		Map ruleMap = getPriRule(reptab);
		datastr = datastr.replaceAll("-", "");
		Connection c = null;
		ResultSet rs = null;
		Statement stmt = null;
		PreparedStatement ps = null;
		Map<Long, List<DicItem>> dicMap = new HashMap<Long, List<DicItem>>();
		for (int i = 0; i < reportTargetList.size(); i++) {
			ReportTarget rt = (ReportTarget) reportTargetList.get(i);
			if (rt != null) {
				// 读字典表
				if (new Integer(1).equals(rt.getFieldType())
						&& rt.getDicPid() != null) {
					List<DicItem> dicItems = dataFillDAO.getDicByPid(rt
							.getDicPid());
					dicMap.put(rt.getPkid(), dicItems);
					// 此时应该读实际对的表
				} else if (new Integer(3).equals(rt.getFieldType())
						&& rt.getDicPid() != null) {
					List<DicItem> dicItems = dataFillDAO.getDicByPid(rt
							.getDicPid());
					dicMap.put(rt.getPkid(), dicItems);
				}
			}
		}
		try {
			FileWriter loggwriter = null;
			BufferedWriter bufWriter = null;
			FileOutputStream fos =  null ;
		    OutputStreamWriter osw = null;
			
			try {
				List mapColumn = repfileDao.getMapColumn(rep.getPkid());
				Map mapC = new HashMap();
				for (int i = 0; i < mapColumn.size(); i++) {
					MapColumn mapcolumn = (MapColumn) mapColumn.get(i);
					mapC.put(mapcolumn.getResourceColumn(),
							mapcolumn.getTargetColumn());
				}
				if (mapC.isEmpty()) {
					mapC.put("pkid", "pkid");
					mapC.put("report_date", "report_date");
					mapC.put("organ_id", "organ_id");
				}
				 fos = new FileOutputStream(wfilename); //直接写到目标文件中
			     osw = new OutputStreamWriter(fos, "UTF-8"); 
			     bufWriter = new BufferedWriter(osw,1024 * 1024); //设置了1M的缓冲区
			     
				c = jdbc.getDataSource().getConnection();
				logger.info("查询开始："
						+ DateUtil.getDateTime("yyyyMMdd HH:mm:ss", new Date()));
				// 组成sql语句
				// stmt = c.createStatement();
				StringBuffer sql = repfileDao.getReportDataByPageAll(begindate,
						enddate, rep.getPkid(), organCode,
						datastr.substring(0, datastr.length() - 2) + "00", idx,
						reptab, mapC);
				logger.info("查询的sql为》》" + sql);

				ps = c.prepareStatement(sql.toString(),ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
				ps.setFetchSize(1000);
				rs = ps.executeQuery();
				// rs = stmt.executeQuery(sql.toString());
				logger.info("查询结束：" + DateUtil.getDateTime("yyyyMMdd HH:mm:ss", new Date()));
				int m = 0;
				ResultSetMetaData rsm = rs.getMetaData();
				int columnCount = rsm.getColumnCount();
				ReportTarget reportTarget = null;
				String columnName = null;
				Object value = null;
				List<DicItem> dicItems = null;
				logger.info("rs循环开始："+ DateUtil.getDateTime("yyyyMMdd HH:mm:ss", new Date()));
				
				while (rs.next()) {
					for (int p = 0; p < reportTargetList.size(); p++) {
						reportTarget = (ReportTarget) reportTargetList.get(p);
						for (int i = 1; i <= columnCount; i++) {
							columnName = rsm.getColumnName(i).toLowerCase();
							if (columnName.equals(reportTarget.getTargetField()
									.toLowerCase())) {
								value = rs.getObject(i);
								if (value != null && !("null".equals(value))) {
									
									// 取映射关系 当fileType为1时，说明需要映射
									if (1 == reportTarget.getFieldType()
											|| 3 == reportTarget.getFieldType()) {
										if (dicMap != null) {
											dicItems = dicMap.get(reportTarget
													.getPkid());
											if (dicItems != null) {
												for (DicItem dic : dicItems) {
													if (String
															.valueOf(value)
															.equals(dic
																	.getDicId())) {
														value = dic
																.getDicName();
													}
												}
											}
										}
									}
									// 进行脱敏操作
									// StringUtil.convertstrisnull(rs.getString(i+1))
									
									
									
									if (ruleMap != null
											&& ruleMap.get(reportTarget
													.getTargetField()) != null) {
										value = bri
												.doProcess(
														ruleMap.get(
																reportTarget
																		.getTargetField())
																.toString(),
														value.toString(),null);
									}
									// 进行格式化操作
									if (!"".equals(reportTarget.getOutrule())
											&& reportTarget.getOutrule() != null) {
										value = foi.formatout(value,
												reportTarget.getOutrule(),
												reportTarget.getRulesize());
									}
									// 是字符串的话，就加""
									if (reportTarget.getDataType() == 3) {
										value = '"' + value.toString() + '"';
									}
								} else {
									if (reportTarget.getDataType() == 1) {
										value = "0";
									} else {
										value = "";
									}
								}
								sb.append(value);
								if (p != reportTargetList.size() - 1) {
									sb.append(splite);
								}
								break;
							}
						}
					}
					m++;
					sb.append("\r\n");
					
					if (m % 100000 == 0 && m > 99999) {
						logger.info("rs循环内部开始10w写文件：" + DateUtil.getDateTime("yyyyMMdd HH:mm:ss", new Date()));
						bufWriter.write(sb.toString());
						sb = new StringBuffer();
						bufWriter.flush();
						logger.info("rs循环内部结束10w写文件：" + DateUtil.getDateTime("yyyyMMdd HH:mm:ss", new Date()));
					}

				}
				logger.info("rs循环结束开始写文件：" + DateUtil.getDateTime("yyyyMMdd HH:mm:ss", new Date()));
				//writer.write(sb.toString());
				bufWriter.write(sb.toString());
				logger.info("结束写文件：" + DateUtil.getDateTime("yyyyMMdd HH:mm:ss", new Date()));
				bufWriter.flush();
				bufWriter.close();
			 
			    Long sizeLength = new File(wfilename).length(); //文件大小
				loggwriter = new FileWriter(loggname);// 生成日志文件
				loggwriter.write(jfilename);
				loggwriter.write("\r\n");
				loggwriter.write( sizeLength+ "");
				loggwriter.write("\r\n");
				loggwriter.write(DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss",new Date()));
				loggwriter.write("\r\n");
				loggwriter.write("Y");
				loggwriter.write("\r\n");
				loggwriter.write(m+"");
				loggwriter.flush();
				loggwriter.close();
				logger.info("日志文件写入完成：" + DateUtil.getDateTime("yyyyMMdd HH:mm:ss", new Date()));

				
				String repdataName="EAST_"+filename.split("-")[1];
				
				String submitalistsql = "update code_rep_submitalist set FILES_NAME='"+jfilename+"',FILES_BYTES='"+sizeLength+"',RECORD_NUMBER="+m+" where REPORTNAME_EN='"+repdataName+"' and ORGANID='"+organCode+"'";
				
				//修改报送清单数据
				repfileDao.jdbcUpdatSql(submitalistsql);

			} catch (SQLException e) {
				try {
					c.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			} finally {
				try {
					if (ps != null) {
						ps.close();
					}
					if (rs != null) {
						rs.close();
					}
					if (c != null && !c.isClosed()) {
						c.close();
					}
					if(bufWriter != null){
						bufWriter.close();
					}
					if(loggwriter != null){
						loggwriter.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		} catch (FileNotFoundException e) {
			logger.error("FileNotFoundException:" + e.getMessage());
		} catch (IOException e) {
			logger.error("IOException:" + e.getMessage());
		}

	}

	/**
	 * 创建XML文件
	 * 
	 * @param repFla
	 *            报文
	 * @param organCode
	 *            机构号
	 * @param repdate
	 *            日期 格式为yyyy-MM-dd
	 * @param bassfile
	 *            报文保存服务器的路径
	 * @param idx
	 */
	private void createFileTxtorXML(RepFlFomat repFla, String organCode,
			String repdate, String bassfile, String systemid, String batch,
			int idx) {
		String reporganCode = getouterorgan(organCode, systemid);// ?
		String filename = getrepFilename(repFla, reporganCode, repdate);
		String fileXMLname = bassfile + "/" + filename + ".XML";
		logger.info("打包文件：》》》" + fileXMLname);
		String lab = "";
		List labL = repfileDao.getOrganidx(organCode, idx);
		if (labL.size() > 0) {
			lab = (String) labL.get(0);
		}
		// 获得报文关联的报表列表
		List<RepFlRep> repFlRep = repfileDao.getRepFlRep(repFla);
		createFileXML(repFla, organCode, repdate, fileXMLname, lab, repFlRep);
		RepFlfile repflfile = getFileInfo(new Long(0), filename + ".XML",
				repdate, "", bassfile, organCode, repFla.getRepname(),
				systemid, batch, repFla.getPkid());
		repfileDao.saveRepFlfile(repflfile);
		// 更新日志信息把审核的日志更新到状态为2并把批次
		updaterepLog(repFla, batch, repdate, organCode, lab, repFlRep);
	}

	public String getouterorgan(String organCode, String systemid) {
		return repfileDao.getouterorgan(organCode, systemid);
	}

	/**
	 * 创建XML文件
	 * 
	 * @param repFla
	 *            报文
	 * @param organCode
	 *            机构号
	 * @param repdate
	 *            报表日期
	 * @param fileXMLname
	 *            //文件名
	 * @param lab
	 *            //机构标识
	 * @param repFlRep
	 *            //关联的报表列表
	 */
	public void createFileXML(RepFlFomat repFla, String organCode,
			String repdate, String fileXMLname, String lab,
			List<RepFlRep> repFlRep) {
		// 获得root标签

		String xmlhead = new String(repFla.getRephead());
		xmlhead = replaceorganorrepdate(organCode, repdate, xmlhead);
		String date = repdate.replaceAll("-", "");
		logger.info("生成xml文件的头>>>>" + xmlhead);
		boolean pp = false;// 检查是否有数据如果没有数据、最后一个标签不写入
		// 获得父节点的id号
		FileWriter writer = null;
		// String splite=repFla.getRepfileexte();
		String filename = fileXMLname;
		logger.info("fileName:>>>" + filename);
		String organTree = "select tr.CODE from code_org_organ tr,CODE_ORG_TREE tt where tr.PKID=tt.NODEID  and tt.SUBTREETAG like '"
				+ lab + "%' ";
		try {
			File file = new File(filename);
			if (file.exists()) {
				file.delete();
			}
			writer = new FileWriter(filename, true);
			Connection c = null;
			Statement s = null;
			try {
				c = jdbc.getDataSource().getConnection();
				logger.info("查询开始："
						+ DateUtil.getDateTime("yyyyMMdd HH:mm:ss", new Date()));
				Statement stmt = c.createStatement();
				String fileds = "";
				String talblename = "";
				String wherenames = "";
				String talbelabe = "";

				String[] itemsarray = null;
				String[] datysarry = null;
				String[] datelab = null;

				Map<Integer, Integer> posMap = new HashMap<Integer, Integer>();// 构建结果集下标和数据类型的map
				Map<Integer, Boolean> pkidMap = new HashMap<Integer, Boolean>();// 构建结果集下标和是否为pkid的map
				Map<Integer, String> dataLableMap = new HashMap<Integer, String>();// 构建结果集下标和数据标签的map
				Map<Integer, String> attrMap = new HashMap<Integer, String>();// 构建结果集下标和属性的map
				Map<Integer, Integer> countMap = new HashMap<Integer, Integer>();// 构建pkid下标和指标数量的map
				Map<Integer, String> lableMap = new HashMap<Integer, String>();// 构建pkid下标和大标签的关系
				Map<Integer, String> loopLableMap = new HashMap<Integer, String>();// 构建pkid下标和循环标签的关系
				Map<Integer, Integer> stringMap = new HashMap<Integer, Integer>();// 构建下标和stringbuffer对应关系的map
				Map<Integer, String> strLableMap = new HashMap<Integer, String>();// 构建stringbuffer和大标签的关系
				Map<Integer, String> loopLableMapForEnd = new HashMap<Integer, String>();// 构建结果集下标和循环标签的关系
				int pos = 1;
				for (int k = 0; k < repFlRep.size(); k++) {
					RepFlRep rfr = repFlRep.get(k);
					List reporttargetL = repfileDao.getRepFlFiled(rfr);
					for (int j = 0; j < reporttargetL.size() + 1; j++) {
						if (j == 0) {
							posMap.put(pos, 3);// 若是遍历新的field，则需先加入pkid
							pkidMap.put(pos, true);// 当前位置为pkid
							lableMap.put(pos, rfr.getReplable());
							loopLableMap.put(pos, rfr.getLooplab());
							countMap.put(pos, reporttargetL.size());
							strLableMap.put(k, rfr.getReplable());
							stringMap.put(pos, k);
						} else {
							Object[] obj = (Object[]) reporttargetL.get(j - 1);
							ReportTarget reptarget = (ReportTarget) obj[0];
							RepFlRepFiled rfrf = (RepFlRepFiled) obj[1];
							posMap.put(pos, reptarget.getDataType());// 将实际的指标加入
							pkidMap.put(pos, false);// 当前位置不是pkid
							dataLableMap.put(pos, rfrf.getReplable());
							attrMap.put(pos, rfrf.getAttribute());
							loopLableMapForEnd.put(pos, rfr.getLooplab());
							stringMap.put(pos, k);
						}
						pos++;
					}
				}

				for (int k = 0; k < repFlRep.size(); k++) {
					RepFlRep rfr = repFlRep.get(k);
					if (k == 0) {
						talbelabe = rfr.getTablelab();
						talblename += gettablename(rfr, repdate) + " "
								+ rfr.getTablelab();
					}
					fileds += genDataFields(rfr); // 生成字段
					if (rfr.getWherecondition() != null
							&& !"".equals(rfr.getWherecondition())) {
						talblename += " left outer join "
								+ gettablename(rfr, repdate) + " "
								+ rfr.getTablelab(); // 生成表名
						talblename += " on " + rfr.getWherecondition()
								+ " and " + rfr.getTablelab() + ".report_id="
								+ rfr.getRepid();
					}
					if (k != repFlRep.size() - 1) {
						fileds += ",";
					}
				}
				// 日志文件审核通过的数据
				String pkidsql = "SELECT to_number(ASS_MEMO) FROM LOG_DATA_"
						+ date.substring(4, 6) + "  where  id_rep="
						+ repFlRep.get(0).getRepid()
						+ "  and MK_TYPE='1'  and status='1'";
				String sql = "select " + fileds + " from " + talblename
						+ " where " + talbelabe + ".ORGAN_ID in (" + organTree
						+ ") AND " + talbelabe + ".REPORT_ID="
						+ repFlRep.get(0).getRepid() + " and " + talbelabe
						+ ".pkid in (" + pkidsql + ")";
				System.out.println("sql:" + sql);
				System.err.println("pkidsql:" + sql);
				logger.info("查询sql：" + sql);
				ResultSet rs = stmt.executeQuery(sql); // 有问题的地方
				logger.info("查询结束："
						+ DateUtil.getDateTime("yyyyMMdd HH:mm:ss", new Date()));
				int m = 0;
				writer.write(xmlhead);
				List<String> pkidList = new ArrayList<String>();// 保存数据id，用于判断是否写过该条数据
				StringBuffer[] sb = new StringBuffer[repFlRep.size()];
				for (int n = 0; n < sb.length; n++) {
					sb[n] = new StringBuffer();
				}
				Long pkid = null;// 第一个表的id
				while (rs.next()) {// 遍历每条数据
					pp = true;
					int l = 1;// 每条数据下标从1开始
					for (Entry<Integer, Integer> e : posMap.entrySet()) {// 遍历posMap，大小和结果集中每行的列数一致
						if (l > posMap.size()) {
							break;
						}
						Boolean idFlag = pkidMap.get(l);// 是否是pkid列
						Integer dataType = posMap.get(l);// 数据类型
						if (idFlag) {// 若是pkid
							Long id = rs.getLong(l);
							if (l == 1) {
								if (pkid == null) {
									pkid = rs.getLong(l);
								}
								if (rs.getLong(l) != pkid && pkid != null) {
									// 写入文件
									m = writeToXML(writer, strLableMap, m, sb,
											pp);
									pkid = id;
								}
								if (sb[stringMap.get(l)].length() < 1) {
									sb[stringMap.get(l)].append("<");
									sb[stringMap.get(l)]
											.append(changgestrlab(lableMap
													.get(l)));
									sb[stringMap.get(l)].append(">");
								}
								if (pkidList.contains(String.valueOf(id))
										|| id == 0) {
									l += countMap.get(l);// 跳过写过的数据的列
								} else {
									pkidList.add(String.valueOf(id));
								}
								l++;
								continue;
							} else {
								if (pkidList.contains(String.valueOf(id))
										|| id == 0) {
									l += countMap.get(l);// 跳过写过的数据的列
								} else {
									pkidList.add(String.valueOf(id));
									if (sb[stringMap.get(l)].length() < 1) {
										sb[stringMap.get(l)].append("<");
										sb[stringMap.get(l)]
												.append(changgestrlab(lableMap
														.get(l)));
										sb[stringMap.get(l)].append(">");
									}
									sb[stringMap.get(l)].append("<");
									sb[stringMap.get(l)]
											.append(changgestrlab(loopLableMap
													.get(l)));
									sb[stringMap.get(l)].append(">");
								}
							}
						} else {// ����pkid,��ָ����
							sb[stringMap.get(l)].append("<");
							sb[stringMap.get(l)]
									.append(changgestrlab(dataLableMap.get(l)
											+ " " + attrMap.get(l)));
							sb[stringMap.get(l)].append(">");
							if (3 == dataType) {
								sb[stringMap.get(l)].append(changgestrvalue(rs
										.getString(l)));
							} else if (1 == dataType) {
								sb[stringMap.get(l)].append(StringUtil
										.doubletoString(rs.getDouble(l),
												"###,00"));
							}
							sb[stringMap.get(l)].append("</");
							sb[stringMap.get(l)]
									.append(changgestrlab(dataLableMap.get(l)));
							sb[stringMap.get(l)].append(">");
							if (l < pkidMap.size()) {
								if (pkidMap.get(l + 1)) {// 若下一列是pkid,则结束循环标签
									if (loopLableMapForEnd.get(l) != null
											&& !"".equals(loopLableMapForEnd
													.get(l))) {
										sb[stringMap.get(l)].append("</");
										sb[stringMap.get(l)]
												.append(changgestrlab(loopLableMapForEnd
														.get(l)));
										sb[stringMap.get(l)].append(">");
									}
								}
							} else if (l == pkidMap.size()) {
								if (loopLableMapForEnd.get(l) != null
										&& !"".equals(loopLableMapForEnd.get(l))) {
									sb[stringMap.get(l)].append("</");
									sb[stringMap.get(l)]
											.append(changgestrlab(loopLableMapForEnd
													.get(l)));
									sb[stringMap.get(l)].append(">");
								}
							}
						}
						l++;
					}

				}
				writeToXML(writer, strLableMap, m, sb, pp);
				writer.write("</cbrc>");
				logger.info("文件写入完成："
						+ DateUtil.getDateTime("yyyyMMdd HH:DD:MM", new Date()));
				writer.flush();
				writer.close();
			} catch (SQLException e) {
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
		} catch (Exception e) {
			e.printStackTrace();
			try {
				writer.close();
			} catch (IOException e1) {
				logger.error("关闭文件错误：" + e1.getMessage());
			}
			logger.error("导入文件错误：" + e.getMessage());
		}
	}

	/**
	 * @param writer
	 * @param strLableMap
	 * @param m
	 * @param sb
	 * @return
	 * @throws IOException
	 */
	private int writeToXML(FileWriter writer, Map<Integer, String> strLableMap,
			int m, StringBuffer[] sb, boolean pp) throws IOException {
		boolean b = false;// 判断有没有数据，默认是没有数据
		for (int n = 1; n < sb.length; n++) {
			if (sb[n].length() > 1) {
				b = true;
				sb[n].append("</");
				sb[n].append(changgestrlab(strLableMap.get(n)));
				sb[n].append(">");
			}
		}
		for (int g = 0; g < sb.length; g++) {
			writer.write(sb[g].toString());
			sb[g] = new StringBuffer();
		}
		if (b || pp) {
			writer.write("</" + strLableMap.get(0) + ">");
		}
		logger.info("写入文件：" + ++m);
		writer.flush();
		return m;
	}

	public String changgestrlab(String lab) {
		lab = StringUtil.itemValidate(lab);
		return lab;
	}

	public String changgestrvalue(String lab) {
		lab = StringUtil.convertstrisnull(lab);
		return lab;
	}

	private String gettablename(RepFlRep repflRep, String date) {
		date = date.replaceAll("-", "");
		return repfileDao.getPhyTableName(String.valueOf(repflRep.getRepid()),
				date);
	}

	/**
	 * 替换字符串中的，机构号，机构名称、报送日期
	 * 
	 * @return
	 */
	private String replaceorganorrepdate(String organCode, String repdate,
			String xmlStr) {
		// 获得机构号：
		// 获得机构名称
		// 获得报送日期
		String organcode = "C2013041623";
		String organanem = "省联社";
		Date dateM = DateUtil.getLastbyDate(repdate, "yyyy-MM-dd");
		xmlStr = xmlStr.replaceAll("\\{报送机构号\\}", organcode);
		xmlStr = xmlStr.replaceAll("\\{报送机构名称\\}", organanem);
		xmlStr = xmlStr.replaceAll("\\{报送日期\\}",
				DateUtil.formatDate(dateM, "yyyy-MM-dd"));
		return xmlStr;
	}

	public String getrepFilename(RepFlFomat repFla, String organCode,
			String date) {
		String filename = repFla.getRepfilename();
	 
		String jrxkzh=repfileDao.getJrxkzh(organCode);
	    if (jrxkzh==null||"".equals(jrxkzh)){
	    	filename = filename.replaceAll("\\{报送机构号\\}", organCode);// 替换机构
		}else{
			filename = filename.replaceAll("\\{报送机构号\\}",jrxkzh);
		}
	 
		// Date dateM=DateUtil.getLastbyDate(date, "yyyy-MM-dd");
		// 文件名字为当天日期
		filename = filename.replaceAll("\\{报送日期格式为YYYYMMDD\\}",
				date.replaceAll("-", ""));// 替换报送日期格式
		filename = filename.replaceAll("\\{报送日期格式为YYYY-MM-DD\\}", date);// 替换报送日期格式
		// filename = filename.replaceAll("-", "");
		return filename;
	}

	// 文件信息保存数据库
	public RepFlfile getFileInfo(Long userid, String repfilename,
			String datadate, String filebath, String filepath,
			String organcode, String name, String systemid, String batch,
			Long fileid) {
		datadate = datadate.replaceAll("-", "");
		long pkid = getfilecount(fileid,
				datadate.substring(0, datadate.length() - 2) + "00", batch,
				organcode, repfilename);
		RepFlfile repfile = new RepFlfile();
		if (pkid != 0) {
			repfile.setPkid(pkid);
		}
		repfile.setUserid(userid);
		repfile.setStatus("1");
		repfile.setRepfilename(repfilename);
		repfile.setCreatedate(DateUtil.getDateTime("yyyyMMdd", new Date()));
		repfile.setDatadate(datadate.substring(0, datadate.length() - 2) + "00");
		repfile.setFilebath(batch);
		repfile.setFilepath(filepath);
		repfile.setOrgancode(organcode);
		repfile.setSysid(systemid);
		repfile.setName(repfilename);
		repfile.setDownloadnum(new Integer(0));
		repfile.setFileid(fileid);
		return repfile;
	}

	/**
	 * 通过报文的ID号、数据日期、批次获取到文件列表
	 * 
	 * @param organCode
	 * @return
	 */
	public int getfilecount(Long repfileid, String repdate, String batch,
			String organCode, String filename) {
		return repfileDao.getfilecount(repfileid, repdate, batch, organCode,
				filename);

	}

	/**
	 * 生成查询数据的列
	 * 
	 * @param rfr
	 */
	private String genDataFields(RepFlRep rfr) {
		List reporttargetL = repfileDao.getRepFlFiled(rfr);
		StringBuffer sb = new StringBuffer();
		sb.append(rfr.getTablelab() + ".pkid,");
		for (int j = 0; j < reporttargetL.size(); j++) {
			Object[] obj = (Object[]) reporttargetL.get(j);
			ReportTarget reptarget = (ReportTarget) obj[0];
			sb.append(rfr.getTablelab() + ".itemvalue"
					+ reptarget.getTargetField());
			if (j != reporttargetL.size() - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}

	/**
	 * 更新日志信息把审核的日志更新到状态为2并把批次
	 * 
	 * @param repFla
	 *            报文、批次
	 * @param batch
	 *            批次
	 * @param date日期
	 *            yyyy-MM-dd
	 * @param organCode
	 *            机构
	 * @param lab
	 *            机构树标识
	 * @param repFlRep
	 *            关联的报表列表
	 */
	private void updaterepLog(RepFlFomat repFla, String batch, String repdate,
			String organCode, String lab, List<RepFlRep> repFlRep) {
		String organTree = "select tr.CODE from code_org_organ tr,CODE_ORG_TREE tt where tr.PKID=tt.NODEID  and tt.SUBTREETAG like '"
				+ lab + "%' ";
		String talbelabe = "";
		String talblename = "";
		String talblename1 = "";
		//
		String date = repdate.replaceAll("-", "");
		String[] updatesql = new String[repFlRep.size()];
		// String
		// pkidsql="SELECT to_number(ASS_MEMO) FROM LOG_DATA_"+date.substring(4,
		// 6)+"  where  id_rep="+repFlRep.get(0).getRepid()+"  and MK_TYPE='1'  and status='1'";
		String pkidsql = "SELECT id_rep FROM LOG_DATA_" + date.substring(4, 6)
				+ "  where  MK_TYPE='1'  and status='1'";
		for (int k = 0; k < repFlRep.size(); k++) {
			RepFlRep rfr = repFlRep.get(k);
			if (k == 0) {
				talbelabe = rfr.getTablelab();
				talblename += gettablename(rfr, repdate) + " "
						+ rfr.getTablelab();
				updatesql[k] = "select to_char(" + rfr.getTablelab()
						+ ".pkid) from " + gettablename(rfr, repdate) + " "
						+ rfr.getTablelab() + " where " + rfr.getTablelab()
						+ ".report_id=" + rfr.getRepid() + " and  "
						+ rfr.getTablelab() + ".ORGAN_ID in (" + organTree
						+ ") and " + rfr.getTablelab() + ".report_id in ("
						+ pkidsql + ")";
			}

			if (rfr.getWherecondition() != null
					&& !"".equals(rfr.getWherecondition())) {
				talblename1 = talblename + " , " + gettablename(rfr, repdate)
						+ " " + rfr.getTablelab(); // ���ɱ���
				talblename1 += " where  " + rfr.getWherecondition() + " and "
						+ rfr.getTablelab() + ".report_id=" + rfr.getRepid();
				updatesql[k] = "select to_char(" + rfr.getTablelab()
						+ ".pkid) from " + talblename1 + " and " + talbelabe
						+ ".report_id=" + repFlRep.get(0).getRepid() + "  and "
						+ talbelabe + ".ORGAN_ID in (" + organTree + ") and "
						+ talbelabe + ".report_id in (" + pkidsql + ")";
				;
			}

		}
		// 日志文件审核通过的数据

		for (int i = 0; i < updatesql.length; i++) {
			String updatesql1 = "update LOG_DATA_" + date.substring(4, 6)
					+ " set BATCH='" + batch
					+ "' ,status='2' where  ASS_MEMO in (" + updatesql[i] + ")";
			updatesql[i] = updatesql1 + "";
			logger.debug(i + ">>>>>" + updatesql1);
		}
		repfileDao.insertrepDatF(updatesql);
	}

	// 获得脱敏规则列表
	private Map getPriRule(List reptab) {
		if (reptab == null || reptab.size() == 0)
			return null;
		Map ruleMap = new HashMap();
		List priruleL = new ArrayList();
		String repids = "";
		for (int m = 0; m < reptab.size(); m++) {
			Object[] objmval = (Object[]) reptab.get(m);
			repids += "'" + objmval[2] + "',";
		}
		priruleL = rfreportdao.getReportRuleFlag(
				repids.substring(0, repids.length() - 1), "3"); // 得到需要脱敏的规则
		for (int i = 0; i < priruleL.size(); i++) {
			ReportRule rr = (ReportRule) priruleL.get(i);
			ruleMap.put(rr.getTargetid(), rr.getContent()); // 存入每指标对应的规则
		}
		return ruleMap;
	}

	public List<RepFlfile> getRepFlfile(RepFlfile repflfile, String organid,
			int isadmin, int idxid) {
		return repfileDao.getRepFlfile2(repflfile, organid, isadmin, idxid);
	}

	public RepFlfile getRepFlfileOne(RepFlfile repfile) {
		List<RepFlfile> list = repfileDao.getRepFlfile(repfile);
		if (list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 通过主键更新文件的下载次数
	 */
	public void updateRepFlfile(RepFlfile repfile) {

		repfileDao.updateRepFlfile(repfile);
	}

	public List getRepFlfileOne(String pkids) {
		List<RepFlfile> list = repfileDao.getRepFlfile(pkids);
		return list;
	}

	public void delRepFlfile(String pkids) {
		repfileDao.delRepFlfile(pkids);
	}

	public void delRepFlfile(RepFlfile repfile) {
		repfileDao.delRepFlfile(repfile);
	}

	@Override
	public List getRepislock(String organId, String dataDate) {
		dataDate = dataDate.replaceAll("-", "");
		return repfileDao.getRepislock(organId, dataDate);
	}

	@Override
	public List getIfridlock(String organId, String dataDate, String reportid) {
		dataDate = dataDate.replaceAll("-", "");
		return repfileDao.getRepislock2(organId, dataDate, reportid);
	}

	@Override
	public Long getreportId(Long reportId, String showlevel) {
		Report report = repfileDao.getReport(reportId);
		Long reportpkid = null;
		if (report != null) {
			reportpkid = repfileDao.getReportByid(report, showlevel);
		}

		return reportpkid;
	}

	@Override
	public List<KettleData> getAll() {
		return repfileDao.getAll();
	}

	@Override
	public void savektrForm(KettleData kettleData) {
		repfileDao.savektrForm(kettleData);

	}

	@Override
	public void addAttribute(String pkid, String attribute1, String message,
			String attribute2, String attribute3) {
		repfileDao.addAttribute(pkid, attribute1, message, attribute2,
				attribute3);
	}

	@Override
	public byte[] getAttribute5(String pkid) {

		return repfileDao.getAttribute5(pkid);
	}

	@Override
	public void delKtr(String pkid) {
		repfileDao.delKtr(pkid);
	}

	@Override
	public List<KettleData> queryktr(String ktrname, String ktrremark,
			String ktrtime) {
		return repfileDao.queryktr(ktrname, ktrremark, ktrtime);
	}

//	public static void main(String[] args) {
//
//		try {
//			Class.forName("oracle.jdbc.driver.OracleDriver");
//			DriverManagerConnectionFactory dr = new DriverManagerConnectionFactory(
//					"jdbc:oracle:thin:@219.142.180.32:1521:ORCL", "eastsp",
//					"eastsp");
//			Connection c = dr.createConnection();
//			String sql = "insert into east_ygb "
//					+ "  (gh, yxjgdm, nbjgh, jrxkzh, xm, sfzh, lxdh, wdh, ssbm, zw, ygzt, gwbh, cjrq, organ_id, report_date, pkid) "
//					+ " values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//
//			/*
//			 * String sql2="insert into east_gyb " +
//			 * "(gyh, gh, yxjgdm, nbjgh, zxjgdm, jrxkzh, gylx, sfstgy, khjlbz, jbzwbz, qxglbz, ybglbz, xdybz, kgybz, zhgybz, sqbz, sqfw, gwbh, gyyhjb, gyqxjb, sgrq, cjrq, organ_id, report_date, pkid)"
//			 * +"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
//			 */
//			// +"  ('140305015000011', '402163600188', '1000', 'E0859U314060018', '周云芳', '140622198605139848', '', '', '', '委派会计（授权经理）', '在岗', '003', '20140630', '1000', '20140630', east_ygb_seq.nextval) ";
//			ResultSet rs = null;
//			PreparedStatement pstmt = c.prepareStatement(sql);
//
//			int recordNum = 0; // 计数器
//
//			int commit_size = 1000;// 每次提交记录数5
//
//			for (int i = 0; i <= 999999; i++) {
//
//				c.setAutoCommit(false);// 设置数据手动提交，自己管理事务
//
//				recordNum++; // 计数
//				pstmt.setString(1, "140305015000011");
//				pstmt.setString(2, "402163600188");
//				pstmt.setString(3, "1000");
//				pstmt.setString(4, "E0859U314060018");
//				pstmt.setString(5, "周云芳");
//				pstmt.setString(6, "140622198605139848");
//				pstmt.setString(7, "");
//				pstmt.setString(8, "");
//				pstmt.setString(9, "");
//				pstmt.setString(10, "委派会计");
//				pstmt.setString(11, "在岗");
//				pstmt.setString(12, "003");
//				pstmt.setString(13, "20140630");
//				pstmt.setString(14, "1000");
//				pstmt.setString(15, "20140630");
//				pstmt.setLong(16, 1033797 + i);
//
//				/*
//				 * pstmt.setString(1, "120256");
//				 * pstmt.setString(2,"140104018000040"); pstmt.setString(3,
//				 * "402161005134"); pstmt.setString(4, "1000");
//				 * pstmt.setString(5, "402161005134"); pstmt.setString(6,
//				 * "E1379S214010001"); pstmt.setString(7, "一般柜员");
//				 * pstmt.setString(8, "是"); pstmt.setString(9, "否");
//				 * pstmt.setString(10, "否"); pstmt.setString(11, "否");
//				 * pstmt.setString(12, "否"); pstmt.setString(13, "否");
//				 * pstmt.setString(14,"否"); pstmt.setString(15, "否");
//				 * pstmt.setString(16, "否"); pstmt.setString(17, "暂无");
//				 * pstmt.setString(18, "016"); pstmt.setString(19, "4");
//				 * pstmt.setString(20, "4"); pstmt.setString(21, "20140117");
//				 * pstmt.setString(22, "20150630"); pstmt.setString(23, "1000");
//				 * pstmt.setString(24, "20150630"); pstmt.setLong(25,
//				 * 1159010+i);
//				 */
//
//				pstmt.addBatch();
//
//				if ((i + 1) % commit_size == 0) {
//
//					pstmt.executeBatch();
//
//					c.commit();
//
//					c.close();
//					c = DriverManager.getConnection(
//							"jdbc:oracle:thin:@219.142.180.32:1521:ORCL",
//							"eastsp", "eastsp");
//
//					c.setAutoCommit(false);
//
//					pstmt = c.prepareStatement(sql);
//					System.out.println(i);
//				}
//			}
//			if (recordNum % commit_size != 0) {
//
//				pstmt.executeBatch();
//
//				c.commit();
//
//				System.out.println("提交:" + recordNum);
//
//			}
//
//			pstmt.close();
//
//			c.close();
//
//			System.out.println("insert success!!!");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	@Override
	public List getUncheckReports(String condate, String organId, String reports) {
		return repfileDao.getUncheckReports(condate.replaceAll("-", ""),
				organId, reports);
	}

	@Override
	public List<Report> getReports(String reports) {

		return repfileDao.getReports(reports);
	}
	/**	//检索相同业务条件下即将创建的子任务是否已正在运行中
	 * @param date
	 * @param organId
	 * @param reparray
	 * @return true =重复，false=无重复
	 */
	public List<SubTarskInfo> getRuningSubTarsk(String date,String organId,List reparray) {
		List<SubTarskInfo>  list=tarskService.alreadySubTarsk(date, organId, reparray);
	
		return list;
	}
	private void updateTarsk(Tarsk t,String dirPath,String organCode){

		t.setDirPath(dirPath);
		t.setStartTime((new SimpleDateFormat("yyyyMMddHHmmss")).format(new Date()));
		t.setOrganId(organCode);
		t.setStatus(Constant.TARSK_STATUS_START);
	}
	private void initSubtarsk(SubTarskInfo st,String organId,String date,String reportId,Long tId) {
		st.setCreateTime((new SimpleDateFormat("yyyyMMdd HH:mm:ss"))
				.format(new Date()));
		st.settId(tId);
		st.setDataDate(date.replaceAll("-", ""));
		st.setOrganId(organId);
		st.setStatus(Constant.TARSK_STATUS_START);
		st.setReportId(reportId);
		st.setStartTime((new SimpleDateFormat("yyyyMMdd HH:mm:ss"))
				.format(new Date()));
		st = tarskService.subTarskInit(st);
	}

	@Override
	public List<CodeRepConfigure> getRepConfigure(
			CodeRepConfigure codeRepConfigure) {
		return repfileDao.getRepConfigure(codeRepConfigure);
	}

	@Override
	public int saveRepConfigure(CodeRepConfigure codeRepConfigure) {
		return repfileDao.saveRepConfigure(codeRepConfigure);
	}

	@Override
	public int delRepConfigure(long pkid) {
		return repfileDao.delRepConfigure(pkid);
	}

	public List<CodeRepJhgz> getRepJhgz(CodeRepJhgz codeRepJhgz ,int isadmin, int idxid) {
		return repfileDao.getRepJhgz(codeRepJhgz, isadmin, idxid);
	}

	@Override
	public HSSFWorkbook getXlsWork(List datas, String[] titles,String organnName) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
	    HSSFRow row =  null; 
		HSSFCell cell = null;
		 // 单元格列宽
        int[] excelHeaderWidth = {150, 100, 100 ,150, 150, 300, 100,100, 100, 300};

      
        HSSFCellStyle style = wb.createCellStyle();
        // 设置居中样式
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 水平居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
    //    style.setWrapText(true); //自动换行
        style.setBorderLeft((short)1); //边框
        style.setBorderRight((short)1); //边框
        style.setBorderTop((short)1); //边框
        style.setBorderBottom((short)1);//边框
        
        
        HSSFCellStyle style2 = wb.createCellStyle();
        // 设置居中样式
        style2.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // 水平居右
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
   //     style2.setWrapText(true); //自动换行
        style2.setBorderLeft((short)1); //边框
        style2.setBorderRight((short)1); //边框
        style2.setBorderTop((short)1); //边框
        style2.setBorderBottom((short)1);//边框
        
        // 设置合计样式
        HSSFCellStyle style1 = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setBoldweight(font.BOLDWEIGHT_BOLD); // 粗体
        font.setFontHeightInPoints((short)20); //设置字体大小
        style1.setFont(font);
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
        style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
        style1.setBorderLeft((short)1); //边框
        style1.setBorderRight((short)1); //边框
        style1.setBorderTop((short)1); //边框
        style1.setBorderBottom((short)1);//边框
        
        
        // 设置合计样式
        HSSFCellStyle style3 = wb.createCellStyle();
        HSSFFont font2 = wb.createFont();
        font2.setColor(HSSFColor.BLACK.index);
        font2.setBoldweight(font.BOLDWEIGHT_BOLD); // 粗体
        font2.setFontHeightInPoints((short)12); //设置字体大小
        style3.setFont(font2);
        style3.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
        style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
        style3.setBorderLeft((short)1); //边框
        style3.setBorderRight((short)1); //边框
        style3.setBorderTop((short)1); //边框
        style3.setBorderBottom((short)1);//边框
        // 合并单元格
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));
        
        
        
        // 设置列宽度（像素）
        for (int i = 0; i < excelHeaderWidth.length; i++) {
            sheet.setColumnWidth(i, 32 * excelHeaderWidth[i]);
        }
        row = sheet.createRow((int) 0);
		cell = row.createCell(0);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellValue(organnName+"字段校验结果");
		cell.setCellStyle(style1);
		
		row = sheet.createRow((int) 1);
        // 添加表格头
        for (int i = 0; i < titles.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(style3);
        }

        //循环列表数据，逐个添加
        for (int i = 0; i < datas.size(); i++) {
            row = sheet.createRow(i + 2);
            CodeRepJhgz crj = (CodeRepJhgz)datas.get(i);

            int cellNum = 0;


            cell = row.createCell(cellNum++);
            cell.setCellValue(crj.getJhgz());
            cell.setCellStyle(style);

            cell = row.createCell(cellNum++);
            cell.setCellValue(crj.getDatabatch());
            cell.setCellStyle(style);
            
            cell = row.createCell(cellNum++);
            cell.setCellValue(crj.getCheckingrules());
            cell.setCellStyle(style);
            
            
            cell = row.createCell(cellNum++);
            cell.setCellValue(crj.getReport_name());
            cell.setCellStyle(style);
            
            cell = row.createCell(cellNum++);
            cell.setCellValue(crj.getTarget_name());
            cell.setCellStyle(style);
            
            cell = row.createCell(cellNum++);
            cell.setCellValue(crj.getRuledescription());
            cell.setCellStyle(style);
            
            cell = row.createCell(cellNum++);
            cell.setCellValue(crj.getErrornumber());
            cell.setCellStyle(style2);
            
            cell = row.createCell(cellNum++);
            cell.setCellValue(crj.getTotal());
            cell.setCellStyle(style2);
            
            cell = row.createCell(cellNum++);
            cell.setCellValue(crj.getErrorrate()+"%");
            cell.setCellStyle(style2);
            
            cell = row.createCell(cellNum++);
            cell.setCellValue(crj.getReasons());
            cell.setCellStyle(style);
            

            
        }
		
        
		return wb;
	}

	
	@Override
	public HSSFWorkbook getXlsWorkZf(List datas, String[] titles,String organnName) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
	    HSSFRow row =  null; 
		HSSFCell cell = null;
		 // 单元格列宽
        int[] excelHeaderWidth = {50, 100, 100 ,200, 200, 200, 100,100, 100, 300,0};

      
        HSSFCellStyle style = wb.createCellStyle();
        // 设置居中样式
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 水平居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
    //    style.setWrapText(true); //自动换行
        style.setBorderLeft((short)1); //边框
        style.setBorderRight((short)1); //边框
        style.setBorderTop((short)1); //边框
        style.setBorderBottom((short)1);//边框
        
        
        HSSFCellStyle style2 = wb.createCellStyle();
        // 设置居中样式
        style2.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // 水平居右
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
  //      style2.setWrapText(true); //自动换行
        style2.setBorderLeft((short)1); //边框
        style2.setBorderRight((short)1); //边框
        style2.setBorderTop((short)1); //边框
        style2.setBorderBottom((short)1);//边框
        
        // 设置合计样式
        HSSFCellStyle style1 = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setBoldweight(font.BOLDWEIGHT_BOLD); // 粗体
        font.setFontHeightInPoints((short)20); //设置字体大小
        style1.setFont(font);
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
        style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
        style1.setBorderLeft((short)1); //边框
        style1.setBorderRight((short)1); //边框
        style1.setBorderTop((short)1); //边框
        style1.setBorderBottom((short)1);//边框
        
        
        // 设置合计样式
        HSSFCellStyle style3 = wb.createCellStyle();
        HSSFFont font2 = wb.createFont();
        font2.setColor(HSSFColor.BLACK.index);
        font2.setBoldweight(font.BOLDWEIGHT_BOLD); // 粗体
        font2.setFontHeightInPoints((short)12); //设置字体大小
        style3.setFont(font2);
        style3.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
        style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
        style3.setBorderLeft((short)1); //边框
        style3.setBorderRight((short)1); //边框
        style3.setBorderTop((short)1); //边框
        style3.setBorderBottom((short)1);//边框
        
        // 合并单元格
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));
        
        
        
        // 设置列宽度（像素）
        for (int i = 0; i < excelHeaderWidth.length; i++) {
            sheet.setColumnWidth(i, 32 * excelHeaderWidth[i]);
        }
        row = sheet.createRow((int) 0);
		cell = row.createCell(0);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellValue(organnName+"总分核对结果");
		cell.setCellStyle(style1);
		
		row = sheet.createRow((int) 1);
        // 添加表格头
        for (int i = 0; i < titles.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(style3);
        }

        //循环列表数据，逐个添加
        for (int i = 0; i < datas.size(); i++) {
            row = sheet.createRow(i + 2);
            CodeRepJhgzZf crj = (CodeRepJhgzZf)datas.get(i);

            int cellNum = 0;
 

            cell = row.createCell(cellNum++);
            cell.setCellValue(i+1);
            cell.setCellStyle(style2);

            cell = row.createCell(cellNum++);
            cell.setCellValue(crj.getDatabatch());
            cell.setCellStyle(style);
            
            cell = row.createCell(cellNum++);
            cell.setCellValue(crj.getCheckingrules());
            cell.setCellStyle(style);
            
            
            cell = row.createCell(cellNum++);
            cell.setCellValue(crj.getReport_name());
            cell.setCellStyle(style);
            
            cell = row.createCell(cellNum++);
            cell.setCellValue(crj.getTarget_name());
            cell.setCellStyle(style);
            
            cell = row.createCell(cellNum++);
            cell.setCellValue(crj.getRuledescription());
            cell.setCellStyle(style);
            
            cell = row.createCell(cellNum++);
            cell.setCellValue(crj.getErrornumber());
            cell.setCellStyle(style2);
            
            cell = row.createCell(cellNum++);
            cell.setCellValue(crj.getTotal());
            cell.setCellStyle(style2);
            
            cell = row.createCell(cellNum++);
            cell.setCellValue(crj.getErrorrate());
            cell.setCellStyle(style2);
            
            cell = row.createCell(cellNum++);
            cell.setCellValue(crj.getReasons());
            cell.setCellStyle(style);
            
            cell = row.createCell(cellNum++);
            cell.setCellValue(crj.getPkid());
            cell.setCellStyle(style);
            

            
        }
		
        
		return wb;
	}
	
	
	@Override
	public HSSFWorkbook getXlsWorkZf(List datas , List titles) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
	    HSSFRow row =  null; 
		HSSFCell cell = null;
		
      
        HSSFCellStyle style = wb.createCellStyle();
        // 设置居中样式
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 水平居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
    //    style.setWrapText(true); //自动换行
        style.setBorderLeft((short)1); //边框
        style.setBorderRight((short)1); //边框
        style.setBorderTop((short)1); //边框
        style.setBorderBottom((short)1);//边框
        
        
        HSSFCellStyle style2 = wb.createCellStyle();
        // 设置居中样式
        style2.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // 水平居右
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
  //      style2.setWrapText(true); //自动换行
        style2.setBorderLeft((short)1); //边框
        style2.setBorderRight((short)1); //边框
        style2.setBorderTop((short)1); //边框
        style2.setBorderBottom((short)1);//边框
        
        // 设置合计样式
        HSSFCellStyle style1 = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setBoldweight(font.BOLDWEIGHT_BOLD); // 粗体
        font.setFontHeightInPoints((short)20); //设置字体大小
        style1.setFont(font);
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
        style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
        style1.setBorderLeft((short)1); //边框
        style1.setBorderRight((short)1); //边框
        style1.setBorderTop((short)1); //边框
        style1.setBorderBottom((short)1);//边框
        
        
        // 设置合计样式
        HSSFCellStyle style3 = wb.createCellStyle();
        HSSFFont font2 = wb.createFont();
        font2.setColor(HSSFColor.BLACK.index);
        font2.setBoldweight(font.BOLDWEIGHT_BOLD); // 粗体
        font2.setFontHeightInPoints((short)12); //设置字体大小
        style3.setFont(font2);
        style3.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
        style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
        style3.setBorderLeft((short)1); //边框
        style3.setBorderRight((short)1); //边框
        style3.setBorderTop((short)1); //边框
        style3.setBorderBottom((short)1);//边框
        
    
		row = sheet.createRow((int) 0);
        // 添加表格头
        for (int i = 0; i < titles.size(); i++) {
        	ReportTarget reportTarget = (ReportTarget) titles.get(i);
            cell = row.createCell(i);
            cell.setCellValue(reportTarget.getTargetName());
            cell.setCellStyle(style3);
            sheet.setColumnWidth(i, reportTarget.getTargetName().getBytes().length*256);// 设置列宽度（像素）（中文适用）
        }
        
        int rows = 1 ;
        //循环列表数据，逐个添加
        if (datas != null && datas.size() > 0) { 
        	
            Map repDataMap = (Map)datas.get(0);
            Map pkidMap = (Map)datas.get(2);
            
            for (Object pkid : pkidMap.keySet()) {
                row = sheet.createRow(rows++);
                 
                int cellNum = 0;
				for (Object reportTargets : titles) {
					ReportTarget reportTarget = (ReportTarget) reportTargets;
					cell = row.createCell(cellNum++);
	                cell.setCellValue((String)repDataMap.get(pkid+"_"+reportTarget.getTargetField()));
	                cell.setCellStyle(style);
				}
			}
            
            
        }
		
        
		return wb;
	}
	
	
	@Override
	public HSSFWorkbook getXlsWorkUser(List datas , List titles) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
	    HSSFRow row =  null; 
		HSSFCell cell = null;
		
      
        HSSFCellStyle style = wb.createCellStyle();
        // 设置居中样式
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 水平居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
    //    style.setWrapText(true); //自动换行
        style.setBorderLeft((short)1); //边框
        style.setBorderRight((short)1); //边框
        style.setBorderTop((short)1); //边框
        style.setBorderBottom((short)1);//边框
        
        
        HSSFCellStyle style2 = wb.createCellStyle();
        // 设置居中样式
        style2.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // 水平居右
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
  //      style2.setWrapText(true); //自动换行
        style2.setBorderLeft((short)1); //边框
        style2.setBorderRight((short)1); //边框
        style2.setBorderTop((short)1); //边框
        style2.setBorderBottom((short)1);//边框
        
        // 设置合计样式
        HSSFCellStyle style1 = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setBoldweight(font.BOLDWEIGHT_BOLD); // 粗体
        font.setFontHeightInPoints((short)20); //设置字体大小
        style1.setFont(font);
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
        style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
        style1.setBorderLeft((short)1); //边框
        style1.setBorderRight((short)1); //边框
        style1.setBorderTop((short)1); //边框
        style1.setBorderBottom((short)1);//边框
        
        
        // 设置合计样式
        HSSFCellStyle style3 = wb.createCellStyle();
        HSSFFont font2 = wb.createFont();
        font2.setColor(HSSFColor.BLACK.index);
        font2.setBoldweight(font.BOLDWEIGHT_BOLD); // 粗体
        font2.setFontHeightInPoints((short)12); //设置字体大小
        style3.setFont(font2);
        style3.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
        style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
        style3.setBorderLeft((short)1); //边框
        style3.setBorderRight((short)1); //边框
        style3.setBorderTop((short)1); //边框
        style3.setBorderBottom((short)1);//边框
        
    
		row = sheet.createRow((int) 0);
        // 添加表格头
        for (int i = 0; i < titles.size(); i++) {
            cell = row.createCell(i);
            cell.setCellValue(titles.get(i).toString());
            cell.setCellStyle(style3);
            sheet.setColumnWidth(i, titles.get(i).toString().getBytes().length*256);// 设置列宽度（像素）（中文适用）
        }
        
        //循环列表数据，逐个添加
        if (datas != null && datas.size() > 0) { 
            
             
            //循环列表数据，逐个添加
            for (int i = 0; i < datas.size(); i++) {
                row = sheet.createRow(i + 1);
                User crj = (User)datas.get(i);
                int cellNum = 0;

                cell = row.createCell(cellNum++);
                cell.setCellValue(i+1);
                cell.setCellStyle(style2);

                cell = row.createCell(cellNum++);
                cell.setCellValue(crj.getName());
                cell.setCellStyle(style);
                
                cell = row.createCell(cellNum++);
                cell.setCellValue(crj.getLogonName());
                cell.setCellStyle(style);
                
        

                
            }
    		 
        }
		
        
		return wb;
	}
	
	
	
	@Override
	public void updateRepJhgz(String pkid, String urlreasons) {
		repfileDao.updateRepJhgz(pkid, urlreasons);
	}

	@Override
	public List listRepTarget(String reportid) {
		return repfileDao.listRepTarget(reportid);
	}

	@Override
	public CodeRepJhgz selectRepJhgz(String pkid) {
		return repfileDao.selectRepJhgz(pkid);
	}

	@Override
	public List<CodeRepJhgzZf> getRepJhgzZf(CodeRepJhgzZf codeRepJhgzZf ,int isadmin, int idxid) {
		return repfileDao.getRepJhgzZf(codeRepJhgzZf , isadmin, idxid);
	}

	@Override
	public void updateRepJhgzZf(String pkid, String urlreasons) {
		repfileDao.updateRepJhgzZf(pkid, urlreasons);
		
	}

	@Override
	public CodeRepJhgzZf selectRepJhgzZf(String pkid) {
		return repfileDao.selectRepJhgzZf(pkid);
	}

	public Font f8;
	@Override
	public void addPdf(Document doc, List datas ,String[] tableHeader ,String filetitle,String flay) {
		
		   //设置中文字体和字体样式  
        BaseFont bfChinese = null;
		try {
			bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
		} catch (DocumentException e1) {
			logger.error("exception", e1);
		} catch (IOException e1) {
			logger.error("exception", e1);
		}    
       f8 = new Font(bfChinese, 8, Font.NORMAL); 
       doc.setPageSize(PageSize.A4);
	   doc.open();
		
	    PdfPTable tabletile=new PdfPTable(1);
	    tabletile.setWidthPercentage(100);  
	    tabletile.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);  
	    PdfPCell pdfTableTiles = new PdfPCell();  
	    //设置表格的表头单元格颜色  
	    pdfTableTiles.setHorizontalAlignment(PdfPCell.ALIGN_CENTER); 
	    pdfTableTiles.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
	    pdfTableTiles.setPhrase(new Paragraph(filetitle, new Font(bfChinese, 12, Font.NORMAL)));  
		tabletile.addCell(pdfTableTiles);  
		
		PdfPTable table=new PdfPTable(10);
		table.setWidthPercentage(100);  
		table.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);  
	    PdfPCell pdfTableHeaderCell = new PdfPCell();  
	    //设置表格的表头单元格颜色  
	    pdfTableHeaderCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER); 
		for (String tableHeaderInfo : tableHeader) {
			 pdfTableHeaderCell.setPhrase(new Paragraph(tableHeaderInfo, f8));  
			 table.addCell(pdfTableHeaderCell);  
		}
		 int cellNum = 1;
		 
		for (Object obj : datas) {
			  
			if("1".equals(flay)){
              	CodeRepJhgz codeRepJhgz= (CodeRepJhgz)obj;
				table.addCell(createCell(codeRepJhgz.getJhgz(),null, null));
				table.addCell(createCell(codeRepJhgz.getDatabatch(),null, null));
				table.addCell(createCell(codeRepJhgz.getCheckingrules(),null, null));
				table.addCell(createCell(codeRepJhgz.getReport_name(),null, null));
				table.addCell(createCell(codeRepJhgz.getTarget_name(),null, null));
				table.addCell(createCell(codeRepJhgz.getRuledescription(),null, null));
				table.addCell(createCell(codeRepJhgz.getErrornumber(),null, null));
				table.addCell(createCell(codeRepJhgz.getTotal(),null, null));
				table.addCell(createCell(codeRepJhgz.getErrorrate()+"%",null, null));
				table.addCell(createCell(codeRepJhgz.getReasons(),null, null));
			}else if("2".equals(flay)){
				 
				CodeRepJhgzZf codeRepJhgz= (CodeRepJhgzZf)obj;
				table.addCell(createCell(""+cellNum++,null, null));
				table.addCell(createCell(codeRepJhgz.getDatabatch(),null, null));
				table.addCell(createCell(codeRepJhgz.getCheckingrules(),null, null));
				table.addCell(createCell(codeRepJhgz.getReport_name(),null, null));
				table.addCell(createCell(codeRepJhgz.getTarget_name(),null, null));
				table.addCell(createCell(codeRepJhgz.getRuledescription(),null, null));
				table.addCell(createCell(codeRepJhgz.getErrornumber(),null, null));
				table.addCell(createCell(codeRepJhgz.getTotal(),null, null));
				table.addCell(createCell(codeRepJhgz.getErrorrate(),null, null));
				table.addCell(createCell(codeRepJhgz.getReasons(),null, null));
			}
		     
		 }
			
		  PdfPTable tabletile2=new PdfPTable(1);
		    tabletile2.setWidthPercentage(100);  
		    tabletile2.setHorizontalAlignment(PdfPTable.ALIGN_RIGHT);  
		    PdfPCell pdfTableTiles2 = new PdfPCell();  
		    pdfTableTiles2.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT); 
		    pdfTableTiles2.setVerticalAlignment(PdfPCell.ALIGN_RIGHT);
		    pdfTableTiles2.setPhrase(new Paragraph("电子签章", new Font(bfChinese, 8, Font.NORMAL,BaseColor.WHITE))); 
		    pdfTableTiles2.setBorderWidth(0f);
		    pdfTableTiles2.setPaddingRight(120);
		    pdfTableTiles2.setPaddingTop(80);
			tabletile2.addCell(pdfTableTiles2);  
		
		try {
			doc.add(tabletile);
			doc.add(table);
			doc.add(tabletile2);
		} catch (DocumentException e) {
			logger.error("exception", e);
		}
		
		
	}
	
	 /**
	   * 写一个cell 应该传入一个cell的实体类（设置cell属性）
	   * @param context   内容
	   * @param rowspan   合并行数
	   * @param colspan   合并列数
	   */
	public PdfPCell createCell(String context ,Integer rowspan,Integer colspan) {
		PdfPCell   pdfTableContentCell = new PdfPCell();  
        pdfTableContentCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);  
        pdfTableContentCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); 
        pdfTableContentCell.setPhrase(new Phrase(context, f8));
		if(null!=rowspan&&rowspan!=0) {
         pdfTableContentCell.setRowspan(rowspan);//合并行
		}
		if(null!=colspan) {
         pdfTableContentCell.setColspan(colspan);//合并列
		}
		return pdfTableContentCell;
	}

	@Override
	public String rsXml(Map map) {
	    StringBuilder sb = new StringBuilder();  
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?> ");  
        sb.append("<reqt>");  
        sb.append("     <svcHdr>");  
        sb.append("         <corrId></corrId>");          //<!-- Correlation Id 可选项 元素类型:A 长度: 精度:  备注:服务关联Id-->
        sb.append("         <svcId></svcId>");            //<!-- Service Id 可选项 元素类型:A 长度: 精度:  备注:服务Id-->
        sb.append("         <verNbr></verNbr>");          //<!-- Version Number 可选项 元素类型:A 长度: 精度:  备注:服务版本号-->
        sb.append("         <csmrId></csmrId>");          //<!-- Consumer Id 可选项 元素类型:A 长度: 精度:  备注:服务请求方Id(Response不必填写)-->
        sb.append("         <csmrSerNbr></csmrSerNbr>");  //<!-- Consumer Serial Number 可选项 元素类型:A 长度: 精度:  备注:服务请求方交易流水号-->
        sb.append("         <tmStamp></tmStamp>");        //<!-- Time stamp 可选项 元素类型:A 长度: 精度:  备注:服务访问/响应时间戳，由服务请求方/提供方填写。-->
        sb.append("         <reqtIp></reqtIp>");          //<!-- Request IP 可选项 元素类型:A 长度: 精度:  备注:1. 接入系统填. ( 对于维修资金,填柜员所在终端的IP)-->
        sb.append("    </svcHdr>");  
        sb.append("    <appHdr>");  
        sb.append("        <pckgsq>");sb.append(map.get("pckgsq"));sb.append("</pckgsq>");     //<!-- 请求流水 必输项 元素类型: string   长度:32.0 精度:  备注:（该序列在各源系统中是唯一的）-->
        sb.append("        <reqtdt>");sb.append(map.get("reqtdt"));sb.append("</reqtdt>");     //<!-- 请求日期 必输项 元素类型: string   长度:8.0 精度:  备注:-->
        sb.append("        <reqttm>");sb.append(map.get("reqttm"));sb.append("</reqttm>");        //<!-- 请求时间 必输项 元素类型: string   长度:6.0 精度:  备注:-->
        sb.append("        <outsfg>");sb.append(map.get("outsfg"));sb.append("</outsfg>");     //<!-- 源系统标识 必输项 元素类型: string   长度:10.0 精度:  备注:-->
        sb.append("        <openbr></openbr>");        //<!-- 机构号 需要登记核心则输入 元素类型: string   长度:30.0 精度:  备注:-->
        sb.append("        <logino></logino>");        //<!-- 柜员号 需要登记核心则输入 元素类型: string   长度:30.0 精度:  备注:-->
        sb.append("        <tellernm></tellernm>");    //<!-- 柜员名称 需要登记核心则输入 元素类型: string   长度:70.0 精度:  备注:可选项; 类型:String 长度:70-->
        sb.append("        <brcNm></brcNm>");          //<!-- 机构名称 需要登记核心则输入 元素类型: string   长度:40.0 精度:  备注:可选项; 类型:String 长度:40-->
        sb.append("     </appHdr>");  
        sb.append("    <appBody>");  
        sb.append("        <modename>");sb.append(map.get("modename"));sb.append("</modename>");  // <!-- 模板名称 此名称需要统一标准，录入模板时指定 匹配盖章规则-->  
        sb.append("        <userid>818</userid>");       //<!-- 渠道号，固定传818 必输项-->
        sb.append("        <sysid>DQSIGNPDF</sysid>");   //<!-- 系统标识号，区别是哪个系统 传SIGNPDF（大写）-->
        sb.append("        <filedata>");sb.append(map.get("filedata"));sb.append("</filedata>");      //<!-- PDF文件的Base64值-->
        sb.append("        <yxid>");sb.append(map.get("yxid"));sb.append("</yxid>");           //<!-- 上传到影像平台的影像流水号-->
        sb.append("        <yxdate>");sb.append(map.get("yxdate"));sb.append("</yxdate>");       //<!-- 上传到影像平台的影像日期-->
        sb.append("      </appBody>");
        sb.append("</reqt>");  
        return sb.toString();  
	}
	
	@Override
	public String rsXmlout(Map map) {
	    StringBuilder sb = new StringBuilder();  
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?> ");  
        sb.append("<resp>");  
        sb.append("     <svcHdr>");  
        sb.append("         <corrId></corrId>");          //<!-- Correlation Id 可选项 元素类型:A 长度: 精度:  备注:服务关联Id-->
        sb.append("         <svcId></svcId>");            //<!-- Service Id 可选项 元素类型:A 长度: 精度:  备注:服务Id-->
        sb.append("         <verNbr></verNbr>");          //<!-- Version Number 可选项 元素类型:A 长度: 精度:  备注:服务版本号-->
        sb.append("         <csmrId></csmrId>");          //<!-- Consumer Id 可选项 元素类型:A 长度: 精度:  备注:服务请求方Id(Response不必填写)-->
        sb.append("         <pvdrId></pvdrId>");          //<!-- Provider Id 可选项 元素类型:A 长度: 精度:  备注:服务提供方Id-->
        sb.append("         <pvdrSerNbr></pvdrSerNbr>");  //<!-- Provider Serial Number 可选项 元素类型:A 长度: 精度:  备注:服务提供方交易流水号-->
        sb.append("         <respCde></respCde>");        //<!-- Response Code 可选项 元素类型:A 长度: 精度:  备注:响应代码-->
        sb.append("         <respMsg></respMsg>");        //<!-- Time stamp 可选项 元素类型:A 长度: 精度:  备注:服务访问/响应时间戳，由服务请求方/提供方填写。-->
        sb.append("         <tmStamp></tmStamp>");        //<!-- Time stamp 可选项 元素类型:A 长度: 精度:  备注:服务访问/响应时间戳，由服务请求方/提供方填写。-->
        sb.append("    </svcHdr>");  
        sb.append("    <appHdr>");  
        sb.append("        <pckgsq>xxx</pckgsq>");     //<!-- 请求流水 必输项 元素类型: string   长度:32.0 精度:  备注:（该序列在各源系统中是唯一的）-->
        sb.append("        <reqtdt>xxx</reqtdt>");     //<!-- 请求日期 必输项 元素类型: string   长度:8.0 精度:  备注:-->
        sb.append("        <reqttm></reqttm>");        //<!-- 请求时间 必输项 元素类型: string   长度:6.0 精度:  备注:-->
        sb.append("        <respdt></respdt>");        //<!-- 请求日期 必输项 元素类型: string   长度:8.0 精度:  备注:-->
        sb.append("        <resptm></resptm>");        //<!-- 返回时间 必输项 元素类型: string   长度:6.0 精度:  备注:-->
        sb.append("        <rtcode>999999</rtcode>");  //<!-- 错误编码 必输项 元素类型: string   长度:4.0 精度:  备注:000000 表示成功 999999 错误，有错误信息-->
        sb.append("        <erormg>xxxxxx</erormg>");  //<!-- 错误描述 可选项 元素类型: string   长度:1000.0 精度:  备注:如果成功为空，失败为错误信息-->
        sb.append("     </appHdr>");  
        sb.append("    <appBody>");  
        sb.append("        <contentid></contentid>	");// <!--上传到影像平台后的CONtentID,可以根据此ID和交易日期到影像平台获得文档-->  
        sb.append("        <filedata>JVBERi0xLjQKJeLjz9MKMiAwIG9iago8PC9MZW5ndGggODg2L0ZpbHRlci9GbGF0ZURlY29kZT4+c3RyZWFtCnicjVW/TxRBGF0sKLaw14alMVA4zszOzO6KMcZESIzecbeHAe+UEzk4hUPAeIRCEgwmXJSADRICsfQvoLCzEAs6Y22isaFCCxOlwvmxi3e7M2oum9vce+/7bt73vd0Z+3LBdpnjQ+YURm3onEXHNxCKuysFO2fPyAs7VznQZ0NAnTmh8gLoUOw6XDNbsUNO4tWQA/kHORi6ACLOwU6hZp/rRQ7id2N219R+bS63NdC7fM0qdeWqq+uTfeUeq9Rd7sm3V/3i4cLH8a3uwn3R2dTX83lf4IqKsrFCfJ//ZMAQgcAoRAEGgQnEhAJsBAOPfxtAlwTAN4EEYoCMICWAmEAKGfCMPfkhEVQI89Pu6LHIHQOo3NGDkTsGULmjByN39GDkjgFU7ujByB1DT+awIEIwTLljwJQ7JlC6YwCVOyZQumMAlTsGULljAqU7BlC5kwJbgsv1iE/Nh3FufRnb26vhzyiWTeTAB0RDrkzfnRqZn+hMCxCFwNMoqm+rfuNd/lRaIR8kBsVytVCa1UgoBa6pyeJjR6MIAsA0ioy18Hr+Q/2At5n/npa5vFGgkeXbR7yVM5Xp/gtpDUEYYI2m3GNUeBhQjeLWwcZ2mk2hD3wte/hE+OrG58bXF3tpFYPi8UASGgtpRs6d8jRUbEELWQG/oOXqhPHok8r4wa9RBFSOnvitiuJhbrK4Od5RHJy4KFQpvuwQTQG25Z6u7TfT4o1ycRNtuarZCuLJPUr27++ovMkObWzDthWvpXDMV/0VKXNSLFALjbmSFvUXJPNGJt0KB7LXm8+eWEjGA44006EWUZduIQP5pk5pfD5NIi7dSlK9RkwfmpeSJZxc28+3l3dzO+G3jArysYCnRAqUldlSafh8nlU7N5+3sKKyVDl5VBrL9vJCGWvxR3lXV44QScztvLT6b8ZJ+ENiksRfE3G1Z0NPjk7rOmJ5kOPdrTXCPS1N/f1ao/g++6X8IF9YH23tiIArXnTNm1hf4lmiuknJkLKAJZNnCqmG+p8hTSn/HVII/y+kGHqCz4LmM5uzlyo7yBMQxaqlLA0EPy5bP/h7rJJls5/W7+Umw1/iHzeXdREV/LisIXUeBARp7Cb6ORIMgc80fJkeXX6ivKUVhkyLxcIofUzroTVqjVl3xHd9aeRS2OIhxURsbXzYRx31paj0bx7yrtgKZW5kc3RyZWFtCmVuZG9iago0IDAgb2JqCjw8L1R5cGUvUGFnZS9NZWRpYUJveFswIDAgNTk1IDg0Ml0vUmVzb3VyY2VzPDwvRm9udDw8L0YxIDEgMCBSPj4+Pi9Db250ZW50cyAyIDAgUi9QYXJlbnQgMyAwIFI+PgplbmRvYmoKNSAwIG9iago8PC9UeXBlL0ZvbnREZXNjcmlwdG9yL0FzY2VudCA4ODAvQ2FwSGVpZ2h0IDg4MC9EZXNjZW50IC0xMjAvRmxhZ3MgNi9Gb250QkJveCBbLTI1IC0yNTQgMTAwMCA4ODBdL0ZvbnROYW1lL1NUU29uZy1MaWdodC9JdGFsaWNBbmdsZSAwL1N0ZW1WIDkzL1N0eWxlPDwvUGFub3NlKAEFAgIEAAAAAAAAACk+Pj4+CmVuZG9iago2IDAgb2JqCjw8L1R5cGUvRm9udC9TdWJ0eXBlL0NJREZvbnRUeXBlMC9CYXNlRm9udC9TVFNvbmctTGlnaHQvRm9udERlc2NyaXB0b3IgNSAwIFIvVyBbOSAxMCAzNzQgMTcgMjIgNDYyIDI1IDI2IDQ2MiA2Nls0MTddNjlbNTI5XTcxWzI2NF04NFszMzZdXS9EVyAxMDAwL0NJRFN5c3RlbUluZm88PC9SZWdpc3RyeShBZG9iZSkvT3JkZXJpbmcoR0IxKS9TdXBwbGVtZW50IDQ+Pj4+CmVuZG9iagoxIDAgb2JqCjw8L1R5cGUvRm9udC9TdWJ0eXBlL1R5cGUwL0Jhc2VGb250L1NUU29uZy1MaWdodC1VbmlHQi1VQ1MyLUgvRW5jb2RpbmcvVW5pR0ItVUNTMi1IL0Rlc2NlbmRhbnRGb250c1s2IDAgUl0+PgplbmRvYmoKMyAwIG9iago8PC9UeXBlL1BhZ2VzL0NvdW50IDEvS2lkc1s0IDAgUl0+PgplbmRvYmoKNyAwIG9iago8PC9UeXBlL0NhdGFsb2cvUGFnZXMgMyAwIFI+PgplbmRvYmoKOCAwIG9iago8PC9Qcm9kdWNlcihpVGV4dK4gNS41LjEzIKkyMDAwLTIwMTggaVRleHQgR3JvdXAgTlYgXChBR1BMLXZlcnNpb25cKSkvQ3JlYXRpb25EYXRlKEQ6MjAxOTExMjcxMTQ4NTArMDgnMDAnKS9Nb2REYXRlKEQ6MjAxOTExMjcxMTQ4NTArMDgnMDAnKT4+CmVuZG9iagp4cmVmCjAgOQowMDAwMDAwMDAwIDY1NTM1IGYgCjAwMDAwMDE0OTYgMDAwMDAgbiAKMDAwMDAwMDAxNSAwMDAwMCBuIAowMDAwMDAxNjIwIDAwMDAwIG4gCjAwMDAwMDA5NjggMDAwMDAgbiAKMDAwMDAwMTA4MCAwMDAwMCBuIAowMDAwMDAxMjcxIDAwMDAwIG4gCjAwMDAwMDE2NzEgMDAwMDAgbiAKMDAwMDAwMTcxNiAwMDAwMCBuIAp0cmFpbGVyCjw8L1NpemUgOS9Sb290IDcgMCBSL0luZm8gOCAwIFIvSUQgWzwzMjhkZTc1ZTBjNTRlM2E0MjZhNzU5ODEzZDU3ZTEzMz48MzI4ZGU3NWUwYzU0ZTNhNDI2YTc1OTgxM2Q1N2UxMzM+XT4+CiVpVGV4dC01LjUuMTMKc3RhcnR4cmVmCjE4NzQKJSVFT0YK</filedata>");    // <!-- PDF文件的Base64值-->
        sb.append("      </appBody>");
        sb.append("</resp>");  
        return sb.toString();  
	}


	@Override
	public String resprept(Map map ,String urlPath) {
	        //String urlPath = new String("http://localhost:8080/Test1/HelloWorld?name=丁丁".getBytes("UTF-8"));
	       
	        String xmlData = rsXml(map) ;
	        DataOutputStream dos = null ;
	        BufferedReader responseReader = null;
	        
	        DataInputStream input = null;   
		    ByteArrayOutputStream out = null;
		    
		    String base64pdfsignature = null ;
	       try {
	
		        //建立连接
		        URL url=new URL(urlPath);
		        HttpURLConnection httpConn=(HttpURLConnection)url.openConnection();
		
		
		        //设置参数
		        httpConn.setDoOutput(true);     //需要输出
		        httpConn.setDoInput(true);      //需要输入
		        httpConn.setUseCaches(false);   //不允许缓存
		        httpConn.setRequestMethod("POST");      //设置POST方式连接
		
		        //设置请求属性
		        httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		        httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
		        httpConn.setRequestProperty("Charset", "UTF-8");
		
		        //连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
		        httpConn.connect();
		
		        //建立输入流，向指向的URL传入参数
		        dos=new DataOutputStream(httpConn.getOutputStream());
		        dos.write(xmlData.getBytes("UTF-8"));
		        dos.flush();
		        dos.close();
		
		
		        //获得响应状态
		        int resultCode=httpConn.getResponseCode();
		        if(HttpURLConnection.HTTP_OK==resultCode){
		            StringBuffer sb=new StringBuffer();
		            String readLine=new String();
		            responseReader=new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"UTF-8"));
		            while((readLine=responseReader.readLine())!=null){
		                sb.append(readLine).append("\n");
		            }
		            responseReader.close();
		        //    System.out.println(sb.toString());
		
			        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();   
			        DocumentBuilder db = dbf.newDocumentBuilder();   
			        org.w3c.dom.Document d = db.parse(new ByteArrayInputStream(sb.toString().getBytes())); 
			      
			        if(d.getElementsByTagName("filedata").item(0).getFirstChild() != null){
			        	
			        	base64pdfsignature = d.getElementsByTagName("filedata").item(0).getFirstChild().getNodeValue();   
			        }
			   //     System.out.println("filedata:"+base64pdfsignature);  
		            
		        }  
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			}finally{
				 try {
					 if(dos != null){
						 dos.close(); 
					 }
					 
					 if(responseReader != null){
						 responseReader.close();
					 }
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
 
        return  base64pdfsignature; 
	}
	
	
	@Override
	public List<CodeRepSubmitalist> getRepSubmitalist(
			CodeRepSubmitalist repSubmitalist) {
		
		return repfileDao.getRepSubmitalist(repSubmitalist);
	}
	
	
	@Override
	public HSSFWorkbook getXlsWorkRepSubmitalist(List datas, String[] titles) {
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
	    HSSFRow row =  null; 
		HSSFCell cell = null;
		 // 单元格列宽
        int[] excelHeaderWidth = {200, 150, 300 ,400, 150, 100, 300};

      
        HSSFCellStyle style = wb.createCellStyle();
        HSSFFont font1 = wb.createFont();
        font1.setFontHeightInPoints((short)10); //设置字体大小
        style.setFont(font1);
        // 设置居中样式
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 水平居中
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
    //    style.setWrapText(true); //自动换行
        style.setBorderLeft((short)1); //边框
        style.setBorderRight((short)1); //边框
        style.setBorderTop((short)1); //边框
        style.setBorderBottom((short)1);//边框
        
        
        HSSFCellStyle style2 = wb.createCellStyle();
        HSSFFont font2 = wb.createFont();
        font2.setFontHeightInPoints((short)10); //设置字体大小
        style2.setFont(font2);
        // 设置居中样式
        style2.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // 水平居右
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
  //      style2.setWrapText(true); //自动换行
        style2.setBorderLeft((short)1); //边框
        style2.setBorderRight((short)1); //边框
        style2.setBorderTop((short)1); //边框
        style2.setBorderBottom((short)1);//边框
        
        // 设置合计样式
        HSSFCellStyle style1 = wb.createCellStyle();
        HSSFFont font = wb.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setBoldweight(font.BOLDWEIGHT_BOLD); // 粗体
        font.setFontHeightInPoints((short)20); //设置字体大小
        style1.setFont(font);
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
        style1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
 
        
        
        // 设置合计样式
        HSSFCellStyle style3 = wb.createCellStyle();
        HSSFFont font3 = wb.createFont();
        font3.setColor(HSSFColor.BLACK.index);
        font3.setBoldweight(font.BOLDWEIGHT_BOLD); // 粗体
        font3.setFontHeightInPoints((short)14); //设置字体大小
        style3.setFont(font3);
        style3.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 水平居中
        style3.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
        style3.setBorderLeft((short)1); //边框
        style3.setBorderRight((short)1); //边框
        style3.setBorderTop((short)1); //边框
        style3.setBorderBottom((short)1);//边框
        
        
        HSSFCellStyle style4 = wb.createCellStyle();
        HSSFFont font4 = wb.createFont();
        font4.setFontHeightInPoints((short)12); //设置字体大小
        style4.setFont(font4);
        // 设置居中样式
        style4.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 水平居中
        style4.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中
      
        
        // 合并单元格
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 4, 6));
        
        
        
        // 设置列宽度（像素）
        for (int i = 0; i < excelHeaderWidth.length; i++) {
            sheet.setColumnWidth(i, 32 * excelHeaderWidth[i]);
        }
        row = sheet.createRow((int) 0);
		cell = row.createCell(0);
		cell.setCellType(Cell.CELL_TYPE_STRING);
		cell.setCellValue("监管标准化数据报送清单");
		cell.setCellStyle(style1);
		
		String information = null;
		String arrStr[] = null ;
		String name = "";
		String phone = "";
		String name1 = "";
		String phone1 = "";
		
		if(datas.size()>0){
			CodeRepSubmitalist crj = (CodeRepSubmitalist)datas.get(0);
			information = crj.getInformation();
		}
		if(StringUtils.isNotBlank(information)){
			 arrStr =information.split("@");
			 if(arrStr.length==4){
				 name  = arrStr[0];
				 phone = arrStr[1];
				 name1 = arrStr[2];
				 phone1 = arrStr[3];
			 }
		 }
		row = sheet.createRow((int) 1);
        // 添加表格头
		cell = row.createCell(0);
	    cell.setCellValue("填表人："+name);
	    cell.setCellStyle(style4);
	    // 添加表格头
		cell = row.createCell(1);
	    cell.setCellValue("填表人联系方式：");
	    cell.setCellStyle(style4);
	    // 添加表格头
		cell = row.createCell(2);
	    cell.setCellValue(phone);
	    cell.setCellStyle(style4);
	    
	    // 添加表格头
		cell = row.createCell(3);
	    cell.setCellValue("部门负责人："+name1);
	    cell.setCellStyle(style4);
	    // 添加表格头
		cell = row.createCell(4);
	    cell.setCellValue("部门负责人联系方式："+phone1);
	    cell.setCellStyle(style4);
	 
	     

        row = sheet.createRow((int) 2);
        // 添加表格头
        for (int i = 0; i < titles.length; i++) {
            cell = row.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(style3);
        }

        //循环列表数据，逐个添加
        for (int i = 0; i < datas.size(); i++) {
            row = sheet.createRow(i + 3);
            CodeRepSubmitalist crj = (CodeRepSubmitalist)datas.get(i);

            int cellNum = 0;


            cell = row.createCell(cellNum++);
            cell.setCellValue(crj.getReportname_cn());
            cell.setCellStyle(style);
            
            cell = row.createCell(cellNum++);
            cell.setCellValue(crj.getRecord_number());
            cell.setCellStyle(style2);
            
            
            cell = row.createCell(cellNum++);
            cell.setCellValue(crj.getReflecting_business());
            cell.setCellStyle(style);
            
            cell = row.createCell(cellNum++);
            cell.setCellValue(crj.getFiles_name());
            cell.setCellStyle(style);
            
            cell = row.createCell(cellNum++);
            cell.setCellValue(crj.getFiles_bytes());
            cell.setCellStyle(style2);
            
            cell = row.createCell(cellNum++);
            cell.setCellValue(crj.getIschecked());
            cell.setCellStyle(style);
            
            cell = row.createCell(cellNum++);
            cell.setCellValue(crj.getRemarks());
            cell.setCellStyle(style);
            
        }
		
        
		return wb;
	}
	
	
	public void addPdf(Document doc, List datas ,String[] tableHeader) {
		
		   //设置中文字体和字体样式  
	     BaseFont bfChinese = null;
			try {
				bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			} catch (DocumentException e1) {
				logger.error("exception", e1);
			} catch (IOException e1) {
				logger.error("exception", e1);
			}    
	    f8 = new Font(bfChinese, 10, Font.NORMAL); 
	    doc.setPageSize(PageSize.A4);
	 
	    doc.open();
		
	    PdfPTable tabletile=new PdfPTable(1);
	    tabletile.setWidthPercentage(100);  
	    tabletile.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);  
	    PdfPCell pdfTableTiles = new PdfPCell();  
	    //设置表格的表头单元格颜色  
	    pdfTableTiles.setHorizontalAlignment(PdfPCell.ALIGN_CENTER); 
	    pdfTableTiles.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE);
	    pdfTableTiles.setPhrase(new Paragraph("监管标准化数据报送清单", new Font(bfChinese, 14, Font.NORMAL)));  
		tabletile.addCell(pdfTableTiles);  
		
		
	    PdfPTable tabletile1=new PdfPTable(1);
	    tabletile1.setWidthPercentage(100);  
	    tabletile1.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);  
	    PdfPCell pdfTableTiles1 = new PdfPCell();  
	    //设置表格的表头单元格颜色  
	    pdfTableTiles1.setHorizontalAlignment(PdfPCell.ALIGN_LEFT); 
	    pdfTableTiles1.setVerticalAlignment(PdfPCell.ALIGN_LEFT);
	     
		
		PdfPTable table=new PdfPTable(7);
		table.setWidthPercentage(100);  
		table.setHorizontalAlignment(PdfPTable.ALIGN_LEFT);  
	    PdfPCell pdfTableHeaderCell = new PdfPCell();  
	    //设置表格的表头单元格颜色  
	    pdfTableHeaderCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER); 
		for (String tableHeaderInfo : tableHeader) {
			 pdfTableHeaderCell.setPhrase(new Paragraph(tableHeaderInfo, f8));  
			 table.addCell(pdfTableHeaderCell);  
		}

		try {
			table.setWidths(new int[] { 15, 12, 10, 35 ,12 ,6 ,20}); //列宽
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String information = null;
		for (Object obj : datas) {
			  
				CodeRepSubmitalist submitalist= (CodeRepSubmitalist)obj;
				table.addCell(createCell(submitalist.getReportname_cn(),null, null));
				table.addCell(createCell(submitalist.getRecord_number()+"",null, null));
				table.addCell(createCell(submitalist.getReflecting_business(),null, null));
				table.addCell(createCell(submitalist.getFiles_name(),null, null));
				table.addCell(createCell(submitalist.getFiles_bytes(),null, null));
				table.addCell(createCell(submitalist.getIschecked(),null, null));
				table.addCell(createCell(submitalist.getRemarks(),null, null));
				 
				information = submitalist.getInformation();
		 }
		   
		   if(StringUtils.isNotBlank(information)){
			    String arrStr[] =information.split("@");
			    if(arrStr.length==4){
			    	pdfTableTiles1.setPhrase(new Paragraph("填表人："+arrStr[0]+"      填表人联系方式："+arrStr[1]+"         部门负责人："+arrStr[2]+"          部门负责人联系方式："+arrStr[3]+"       ", new Font(bfChinese, 10, Font.NORMAL)));
			    }else{
			    	pdfTableTiles1.setPhrase(new Paragraph("填表人：                填表人联系方式：                        部门负责人：                    部门负责人联系方式：            ", new Font(bfChinese, 10, Font.NORMAL)));
			    }
		    }else{
		    	
		    	pdfTableTiles1.setPhrase(new Paragraph("填表人：                填表人联系方式：                        部门负责人：                    部门负责人联系方式：            ", new Font(bfChinese, 10, Font.NORMAL)));  
		    }
			tabletile1.addCell(pdfTableTiles1); 
			
		  PdfPTable tabletile2=new PdfPTable(1);
		    tabletile2.setWidthPercentage(100);  
		    tabletile2.setHorizontalAlignment(PdfPTable.ALIGN_RIGHT);  
		    PdfPCell pdfTableTiles2 = new PdfPCell();  
		    pdfTableTiles2.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT); 
		    pdfTableTiles2.setVerticalAlignment(PdfPCell.ALIGN_RIGHT);
		    pdfTableTiles2.setPhrase(new Paragraph("电子签章", new Font(bfChinese, 8, Font.NORMAL,BaseColor.WHITE))); 
		    pdfTableTiles2.setBorderWidth(0f);
		    pdfTableTiles2.setPaddingRight(120);
		    pdfTableTiles2.setPaddingTop(80);
			tabletile2.addCell(pdfTableTiles2);  
		
		try {
			doc.add(tabletile);
			doc.add(tabletile1);
			doc.add(table);
			doc.add(tabletile2);
		} catch (DocumentException e) {
			logger.error("exception", e);
		}
		
		
	}
	
	@Override
	public CodeRepSubmitalist selectCodeRepSubmitalist(String pkid) {
		return repfileDao.selectCodeRepSubmitalist(pkid);
	}
	
	
	@Override
	public int  updateRepsubmitAlist(String pkid, String urlreasons) {
		return repfileDao.updateRepsubmitAlist(pkid, urlreasons);
	}
	
	public int  updateRepsubmitAlistBy(String pkid, String information) {
		return repfileDao.updateRepsubmitAlistBy(pkid, information);
	}
	
	  public static void main(String[] args){

	 
	 
	/*   String urlPath = new String("http://172.0.0.1:8082/east");   
	        //String urlPath = new String("http://localhost:8080/Test1/HelloWorld?name=丁丁".getBytes("UTF-8"));

	        Map map =new HashMap();
	        map.put("modename", "曲文凯");
	        RepFileServiceImpl dd =new RepFileServiceImpl();
	        String xmlData = dd.rsXml(map) ;
	        DataOutputStream dos = null ;
	        BufferedReader responseReader = null;
	        
	        DataInputStream input = null;   
		    ByteArrayOutputStream out = null;
	     try {

	        //建立连接
	        URL url=new URL(urlPath);
	        HttpURLConnection httpConn=(HttpURLConnection)url.openConnection();


	        //设置参数
	        httpConn.setDoOutput(true);     //需要输出
	        httpConn.setDoInput(true);      //需要输入
	        httpConn.setUseCaches(false);   //不允许缓存
	        httpConn.setRequestMethod("POST");      //设置POST方式连接

	        //设置请求属性
	        httpConn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	        httpConn.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
	        httpConn.setRequestProperty("Charset", "UTF-8");

	        //连接,也可以不用明文connect，使用下面的httpConn.getOutputStream()会自动connect
	        httpConn.connect();

	        //建立输入流，向指向的URL传入参数
	        dos=new DataOutputStream(httpConn.getOutputStream());
	        dos.writeBytes(xmlData);
	        dos.flush();
	        dos.close();


	        //获得响应状态
	        int resultCode=httpConn.getResponseCode();
	        if(HttpURLConnection.HTTP_OK==resultCode){
	            StringBuffer sb=new StringBuffer();
	            String readLine=new String();
	            responseReader=new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"UTF-8"));
	            while((readLine=responseReader.readLine())!=null){
	                sb.append(readLine).append("\n");
	            }
	            responseReader.close();
	            System.out.println(sb.toString());

	            input = new DataInputStream(httpConn.getInputStream());   
			    byte[] rResult;   
			    out = new  ByteArrayOutputStream();   
			    byte[] bufferByte = new byte[1024];   
		        int lin = -1;   
		        int downloadSize = 0;   
		        while ((lin = input.read(bufferByte)) > -1) {   
		            downloadSize += lin;   
		            out.write(bufferByte, 0, lin);   
		            out.flush();   
		        }   
		        rResult = out.toByteArray();   
		        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();   
		        DocumentBuilder db = dbf.newDocumentBuilder();   
		        org.w3c.dom.Document d = db.parse(new ByteArrayInputStream(rResult));   
		        String TaskAddr = d.getElementsByTagName("filedata").item(0).getFirstChild().getNodeValue();   
		        System.out.println("filedata:"+TaskAddr);  
	            
	        }  
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}finally{
			 try {
				dos.close(); 
				responseReader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}*/
	     
//	        Map map =new HashMap();
//	        map.put("modename", "曲文凯");
//	        RepFileServiceImpl dd =new RepFileServiceImpl();
//	        String xmlData = dd.rsXmlout(map) ;
//		    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();   
//		    DocumentBuilder db = dbf.newDocumentBuilder(); 
//		    org.w3c.dom.Document d = db.parse(new ByteArrayInputStream(xmlData.getBytes()));
//		      //  org.w3c.dom.Document d = db.parse(new ByteArrayInputStream(rResult));   
//		    String   base64pdfsignature = d.getElementsByTagName("filedata").item(0).getFirstChild().getNodeValue();   
//		    System.out.println("filedata:"+base64pdfsignature);

	 }






}
