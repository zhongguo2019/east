package com.krm.ps.model.reportdefine.util;

import java.io.File;
import java.net.MalformedURLException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.io.SAXReader;

public class ReportFormatUtil {

	public static final String ATTR_FORMAT_LIST = "reportFormatList";
	public static final String ATTR_REPORT_LIST = "reportList";
	public static final String ATTR_SAVE_RESULT = "saveResult";
	public static final String ATTR_FREQUENCY_LIST = "frequencyList";
	public static final String ATTR_REPORT_FORMATXML = "reportFormatXml";
	public static final String ATTR_REPORT_ISNEW = "isnew";

	public static final String PARAM_REPORT_ID = "reportId";
	public static final String PARAM_REPORT_TYPE = "reporttype";
	public static final String PARAM_FREQUENCY = "frequency";
	public static final String PARAM_REPORT_FORMATID = "pkId";
	public static final String PARAM_FORMULA_FREQUENCY = "frequency";

	public static Document parseString(String xmlstr) throws DocumentException {
		Document document = DocumentHelper.parseText(xmlstr);
		return document;
	}

	public static String parseXML(Document document) {
		return document.asXML();
	}

	public static Document read(String fileName) throws MalformedURLException,
			DocumentException {
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(fileName));
		return document;
	}
}
