package com.krm.ps.sysmanage.reportdefine.services.impl;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.organmanage2.services.OrganService2;
import com.krm.ps.sysmanage.reportdefine.dao.ReportDefineDAO;
import com.krm.ps.sysmanage.reportdefine.dao.ReportFormatDAO;
import com.krm.ps.sysmanage.reportdefine.services.ReportFormatService;
import com.krm.ps.sysmanage.reportdefine.util.ReportFormatUtil;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportFormat;
import com.krm.ps.sysmanage.usermanage.vo.Units;
import com.krm.ps.util.FuncConfig;

public class ReportFormatServiceImpl implements ReportFormatService {
	private ReportFormatDAO reportFormatDAO;

	private ReportDefineDAO reportDefineDAO;
	
	private OrganService2 organService2;
	
	public void setOrganService2(OrganService2 organService2){
		this.organService2 = organService2;
	}

	public void setReportFormatDAO(ReportFormatDAO reportFormatDAO) {
		this.reportFormatDAO = reportFormatDAO;
	}

	public void setReportDefineDAO(ReportDefineDAO reportDefineDAO) {
		this.reportDefineDAO = reportDefineDAO;
	}

	public List getReportFormats(Long reportId) {
		return reportFormatDAO.getReportFormats(reportId);
	}

	public void removeReportFormat(Long reportFormatId) {
		reportFormatDAO.removeObject(ReportFormat.class, reportFormatId);// wsx
		// 10-12
	}

	public ReportFormat getReportFormat(Long reportFormatId) {
		return (ReportFormat) reportFormatDAO.getObject(ReportFormat.class,
				reportFormatId);
	}

	public void saveReportFormat(ReportFormat reportFormat) {
//		if (FuncConfig.isOpenFun("report.module.jianguanrep")){
//			reportFormat.setCreateDate(JianGuanRep.TEMPLATE_FORMATE_CREATE_DATE);
//			reportFormat.setEditDate(JianGuanRep.TEMPLATE_FORMATE_CREATE_DATE);
//		}
		// 10-12
		reportFormatDAO.saveObject(reportFormat);
	}

	public List getReportFormatsByType(Long reporttype, boolean needFormatXml) {
/*		if (reporttype.longValue() == 0) {
			if (needFormatXml) {
				return reportFormatDAO.getReportFormatsByType(reporttype);
			} else {
				return reportFormatDAO
						.getReportFormatsByTypeWithoutFormatXML(reporttype);
			}
		}*/
		List formats;
		if (needFormatXml) {
			formats = reportFormatDAO.getReportFormatsByType(reporttype);
		} else {
			formats = reportFormatDAO
					.getReportFormatsByTypeWithoutFormatXML(reporttype);
		}
		List reports = reportDefineDAO.getReportsByType(reporttype);
		List returnFormats = new ArrayList();

		if (reports == null || formats == null) {
			return returnFormats;
		}

		for (Iterator ri = reports.iterator(); ri.hasNext();) {// 一个报表可以有多个模板，wsx
																// 10-13
			Report report = (Report) ri.next();
			int repPkid = report.getPkid().intValue();
			int count = 0;
			for (Iterator fi = formats.iterator(); fi.hasNext();) {
				ReportFormat format = (ReportFormat) fi.next();
				if (format.getReportId().intValue() == repPkid) {
					returnFormats.add(format);
					count++;
				}
			}
			if (count == 0) {
				ReportFormat format = new ReportFormat();
				format.setReportId(report.getPkid());
				format.setReportName(report.getName());
				returnFormats.add(format);
			}
		}
		return returnFormats;
	}
	
	public List getReportFormatsByType(Long reporttype, Long userId,boolean needFormatXml){

		/*if (reporttype.longValue() == 0) {
			if (needFormatXml) {
				return reportFormatDAO.getReportFormatsByType(reporttype,userId);
			} else {
				return reportFormatDAO
						.getReportFormatsByTypeWithoutFormatXML(reporttype,userId);
			}
		}*/
		List formats;
		if (needFormatXml) {
			formats = reportFormatDAO.getReportFormatsByType(reporttype);
		} else {
			formats = reportFormatDAO
					.getReportFormatsByTypeWithoutFormatXML(reporttype);
		}
		List reports = reportDefineDAO.getReportsByType(reporttype, null, userId);
		List returnFormats = new ArrayList();

		if (reports == null || formats == null) {
			return returnFormats;
		}

		for (Iterator ri = reports.iterator(); ri.hasNext();) {// 一个报表可以有多个模板，wsx
																// 10-13
			Report report = (Report) ri.next();
			int repPkid = report.getPkid().intValue();
			int count = 0;
			for (Iterator fi = formats.iterator(); fi.hasNext();) {
				ReportFormat format = (ReportFormat) fi.next();
				if (format.getReportId().intValue() == repPkid) {
					returnFormats.add(format);
					count++;
				}
			}
			if (count == 0) {
				ReportFormat format = new ReportFormat();
				format.setReportId(report.getPkid());
				format.setReportName(report.getName());
				returnFormats.add(format);
			}
		}
		return returnFormats;
	
	}

	public ReportFormat getReportFormat(Long reportId, String date,
			String frequency) {
		return reportFormatDAO.getReportFormat(reportId, date, frequency);
	}

	public Units getReportMoneyUnit(Long reportId) {
		return reportFormatDAO.getReportMoneyUnit(reportId);
	}
	
	public ReportFormat processSpecial(ReportFormat reportFormat, String organCodes, String date) {
		//String formulaData = date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6);
		String [] organCode = organCodes.split(",");
		String [] organName = new String[organCode.length];
		for(int i = 0; i < organCode.length; i++) {
			OrganInfo oi = organService2.getOrganByCode(organCode[i]);
			organName[i] = oi.getShort_name();
		}
		try {
			//Document document = ReportFormatUtil.read(filename);
			Document document = ReportFormatUtil.parseString(reportFormat.getReportFormat());
			
			List list = document.selectNodes("//Workbook/Worksheets/Worksheet/Table/Row" );
			
			Element tableNode = (Element)document.selectSingleNode("//Workbook/Worksheets/Worksheet/Table");
			Attribute eccAttribute = tableNode.attribute("ExpandedColumnCount");
			if(eccAttribute != null) {
				Object eccObj = eccAttribute.getData();
				eccAttribute.setValue(String.valueOf(Integer.parseInt(eccObj.toString()) + organCode.length - 1));
			}
			
			List columnList = document.selectNodes("//Workbook/Worksheets/Worksheet/Table/Column");
			Element lastColumnNode = (Element)columnList.get(columnList.size() - 1);
			Attribute indexAttribute = lastColumnNode.attribute("Index");
			Object indexObj = null;
			if(indexAttribute != null) {
				indexObj = indexAttribute.getData();
			}
			for(int j = 1; j < organCode.length; j++) {
				Element cloneColumn = lastColumnNode.createCopy();
				Attribute cloneIndex = cloneColumn.attribute("Index");
				cloneIndex.setValue(String.valueOf(Integer.parseInt(indexObj.toString()) + j));
				columnList.add(cloneColumn);
				//tableNode.add(cloneColumn);
			}
			
			for(Iterator itr = list.iterator(); itr.hasNext();) {
				Element rowElement = (Element)itr.next();
				List cellList = rowElement.selectNodes("Cell");
				boolean flag = true;
				for(Iterator iter = cellList.iterator(); iter.hasNext();) {
					Element cellNode = (Element)iter.next();
					Attribute mergeAcrossAttribute = cellNode.attribute("MergeAcross");
					if(mergeAcrossAttribute != null) {
						Object obj = mergeAcrossAttribute.getData();
						mergeAcrossAttribute.setValue(String.valueOf(Integer.parseInt(obj.toString()) + organCode.length - 1));
						//Object obj1 = mergeAcrossAttribute.getData();
					}else {
						flag = false;
					}
				}
				if(!flag) {
					Element lastElement = (Element)cellList.get(cellList.size() - 1);
					Attribute formulaAttribute = lastElement.attribute("OuterFormula");
					if(formulaAttribute != null) {
						Object obj = formulaAttribute.getData();
						//formulaAttribute.setValue(organCode[0]+ obj.toString());
						formulaAttribute.setValue("A{cstdem:"+obj.toString().substring(1)+":"+organCode[0]+":"+date+":1:}");
						for(int i = 1; i < organCode.length; i++) {
							Element cloneElement = lastElement.createCopy();
							//{cstdem:A5017.1.1:36000000:20071100:1:}
							Attribute attribute = cloneElement.attribute("OuterFormula");
							//Object o = attribute.getData();
							attribute.setValue("A{cstdem:"+obj.toString().substring(1)+":"+organCode[i]+":"+date+":1:}");
							//attribute.setValue(organCode[i]+ obj.toString());
							rowElement.add(cloneElement);
						}
					}else {
						List dataList = lastElement.selectNodes("Data");
						Element dataElement = (Element)dataList.get(0);
						String dataText = dataElement.getText();
						if(dataText != null && dataText.length() > 0) {
							dataElement.setText(organName[0]);
							for(int i = 1; i < organName.length; i++) {
								Element cloneElement = lastElement.createCopy();
								List dList = cloneElement.selectNodes("Data");
								Element dElement = (Element)dList.get(0);
								dElement.setText(organName[i]);
								rowElement.add(cloneElement);
							}
						}else {
							for(int i = 1; i < organName.length; i++){
								Element cloneElement = lastElement.createCopy();
								rowElement.add(cloneElement);
							}
						}
					}
				}
				columnList.add(rowElement);
			}
			tableNode.setContent(columnList);
			//FileWriter out = new FileWriter("e:\\foo.xml");
			//document.write(out);
			//System.out.println(ReportFormatUtil.parseXML(document));
			reportFormat.setReportFormat(ReportFormatUtil.parseXML(document));
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return reportFormat;
	}
	
	
	public static void main(String [] args)  {
//		String filename = "e:\\format_Q09_441.xml";
//		ReportFormatServiceImpl rsi = new ReportFormatServiceImpl();
//		String organCodes = "111,222,333";
//		String organNames = "机构1,机构2,机构3";
//		rsi.processSpecial(filename, organCodes, organNames);
		String s = "a1111";
		System.out.println(s.substring(1));
	}
}
