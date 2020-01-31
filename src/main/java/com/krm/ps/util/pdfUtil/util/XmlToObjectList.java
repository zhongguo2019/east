package com.krm.ps.util.pdfUtil.util;

import java.io.IOException;
import java.io.StringReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.xml.sax.InputSource;
import java.util.Map;
import java.util.HashMap;

import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class XmlToObjectList {
	
	List objectList = new ArrayList();	//对象列表
	List stylelist = null;	//格式列表
	List rowlist = null;
	List celllist = null;
	List colomns = null;	//存放各列宽度
	
	CellStyles css = null;	//格式数据结构
	PDFTable pdftable = null;
	PDFCell pdfcell = null;
	String styleid = null;	//styleId
	
	Map stylehm = new HashMap();	//所有格式存放(不含默认)
	Map basestyle = new HashMap();	//默认格式存放
	Map widths = new HashMap();	//各列宽
	
	/**
	 * 将krm模版xml解析后放到对象中(Jdom)
	 * @param xmlStr
	 */
	public List parseXmlToObject(String xmlStr){
		//解析模版字符串为jdom的document对象
		SAXBuilder builder = new SAXBuilder();
		org.jdom.Document read_doc = null;
		try {
			read_doc = builder.build(new InputSource(new StringReader(xmlStr)));
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		org.jdom.Element root = read_doc.getRootElement();
		stylelist = root.getChild("Styles").getChildren("Style");
		
		
		/**
		 *	设置默认格式
		 */
		//对齐方式
		org.jdom.Element style0 = (org.jdom.Element) stylelist.get(0);
		basestyle.put("Horizontal",style0.getChild("Alignment").getAttributeValue("Horizontal"));
		basestyle.put("Vertical",style0.getChild("Alignment").getAttributeValue("Vertical"));
		
		//单元格四边
		List borders0 = style0.getChild("Borders").getChildren("Border");
		org.jdom.Element topborder0 = (org.jdom.Element)borders0.get(0);
		basestyle.put("TopLineStyle",topborder0.getAttributeValue("LineStyle"));
		basestyle.put("TopColor",topborder0.getAttributeValue("Color"));
		basestyle.put("TopWeight",topborder0.getAttributeValue("Weight"));
		
		org.jdom.Element bottomborder0 = (org.jdom.Element)borders0.get(1);
		basestyle.put("BottomLineStyle",bottomborder0.getAttributeValue("LineStyle"));
		basestyle.put("BottomColor",bottomborder0.getAttributeValue("Color"));
		basestyle.put("BottomWeight",bottomborder0.getAttributeValue("Weight"));
		
		org.jdom.Element leftborder0 = (org.jdom.Element)borders0.get(2);
		basestyle.put("LeftLineStyle",leftborder0.getAttributeValue("LineStyle"));
		basestyle.put("LeftColor",leftborder0.getAttributeValue("Color"));
		basestyle.put("LeftWeight",leftborder0.getAttributeValue("Weight"));
		
		org.jdom.Element rightborder0 = (org.jdom.Element)borders0.get(3);
		basestyle.put("RightLineStyle",rightborder0.getAttributeValue("LineStyle"));
		basestyle.put("RightColor",rightborder0.getAttributeValue("Color"));
		basestyle.put("RightWeight",rightborder0.getAttributeValue("Weight"));
		
		//设置内容
		basestyle.put("FontName",style0.getChild("Font").getAttributeValue("FontName"));
		basestyle.put("Size",style0.getChild("Font").getAttributeValue("Size"));
		basestyle.put("Color",style0.getChild("Font").getAttributeValue("Color"));
		basestyle.put("Bold",style0.getChild("Font").getAttributeValue("Bold"));
		basestyle.put("Italic",style0.getChild("Font").getAttributeValue("Italic"));
		basestyle.put("Underline",style0.getChild("Font").getAttributeValue("Underline"));
		
		//设置背景
		basestyle.put("bgColor",style0.getChild("Interior").getAttributeValue("Color"));
		basestyle.put("bgPattern",style0.getChild("Interior").getAttributeValue("Pattern"));
		
		//设置格式
		basestyle.put("Format",style0.getChild("NumberFormat").getAttributeValue("Format"));
		
		//设置保护
		basestyle.put("Protected",style0.getChild("Protection").getAttributeValue("Protected"));
		
		
		/**
		 *	设置其它格式
		 */
		for(int i=1; i<stylelist.size(); i++){
			org.jdom.Element style = (org.jdom.Element) stylelist.get(i);
			styleid = style.getAttributeValue("ID");
			css = new CellStyles();
			//对齐方式
			if(style.getChild("Alignment") != null){
				if(style.getChild("Alignment").getAttributeValue("Horizontal") != null){
					css.setHorizontal(style.getChild("Alignment").getAttributeValue("Horizontal"));	
				}else{
					css.setHorizontal((String)basestyle.get("Horizontal"));	
				}
				
				if(style.getChild("Alignment").getAttributeValue("Vertical") != null){
					css.setVertical(style.getChild("Alignment").getAttributeValue("Vertical"));	
				}else{
					css.setVertical((String)basestyle.get("Vertical"));	
				}
			}else{
				css.setHorizontal((String)basestyle.get("Horizontal"));
				css.setVertical((String)basestyle.get("Vertical"));
			}
			
			//边框
			if(style.getChild("Borders") != null){
				List borders = style.getChild("Borders").getChildren("Border");
				
				org.jdom.Element topborder = (org.jdom.Element)borders.get(0);
				css.setTopLineStyle(topborder.getAttributeValue("LineStyle"));
				css.setTopColor(topborder.getAttributeValue("Color"));
				css.setTopWeight(topborder.getAttributeValue("Weight"));
				
				org.jdom.Element bottomborder = (org.jdom.Element)borders.get(1);
				css.setBottomLineStyle(bottomborder.getAttributeValue("LineStyle"));
				css.setBottomColor(bottomborder.getAttributeValue("Color"));
				css.setBottomWeight(bottomborder.getAttributeValue("Weight"));
				
				org.jdom.Element leftborder = (org.jdom.Element)borders.get(2);
				css.setLeftLineStyle(leftborder.getAttributeValue("LineStyle"));
				css.setLeftColor(leftborder.getAttributeValue("Color"));
				css.setLeftWeight(leftborder.getAttributeValue("Weight"));
				
				org.jdom.Element rightborder = (org.jdom.Element)borders.get(3);
				css.setRightLineStyle(rightborder.getAttributeValue("LineStyle"));
				css.setRightColor(rightborder.getAttributeValue("Color"));
				css.setRightWeight(rightborder.getAttributeValue("Weight"));
				
			}else{
				css.setTopLineStyle((String)basestyle.get("TopLineStyle"));
				css.setTopColor((String)basestyle.get("TopColor"));
				css.setTopWeight((String)basestyle.get("TopWeight"));
				
				css.setBottomLineStyle((String)basestyle.get("BottomLineStyle"));
				css.setBottomColor((String)basestyle.get("BottomColor"));
				css.setBottomWeight((String)basestyle.get("BottomWeight"));
				
				css.setLeftLineStyle((String)basestyle.get("LeftLineStyle"));
				css.setLeftColor((String)basestyle.get("LeftColor"));
				css.setLeftWeight((String)basestyle.get("LeftWeight"));
				
				css.setRightLineStyle((String)basestyle.get("RightLineStyle"));
				css.setRightColor((String)basestyle.get("RightColor"));
				css.setRightWeight((String)basestyle.get("RightWeight"));
			}
			
			//文字
			if(style.getChild("Font") != null){
				if(style.getChild("Font").getAttributeValue("FontName") != null){
					css.setFontName(style.getChild("Font").getAttributeValue("FontName"));	
				}else{
					css.setFontName((String)basestyle.get("FontName"));
				}
				
				if(style.getChild("Font").getAttributeValue("Size") != null){
					css.setWordSize(style.getChild("Font").getAttributeValue("Size"));	
				}else{
					css.setWordSize((String)basestyle.get("Size"));
				}
				
				if(style.getChild("Font").getAttributeValue("Color") != null){
					css.setWordColor(style.getChild("Font").getAttributeValue("Color"));	
				}else{
					css.setWordColor((String)basestyle.get("Color"));
				}
				
				if(style.getChild("Font").getAttributeValue("Bold") != null){
					css.setWordBlod(style.getChild("Font").getAttributeValue("Bold"));	
				}else{
					css.setWordBlod((String)basestyle.get("Bold"));
				}
				
				if(style.getChild("Font").getAttributeValue("Italic") != null){
					css.setWordItalic(style.getChild("Font").getAttributeValue("Italic"));	
				}else{
					css.setWordItalic((String)basestyle.get("Italic"));
				}
				
				if(style.getChild("Font").getAttributeValue("Underline") != null){
					css.setUnderLine(style.getChild("Font").getAttributeValue("Underline"));	
				}else{
					css.setUnderLine((String)basestyle.get("Underline"));
				}
			}else{
				css.setFontName((String)basestyle.get("FontName"));
				css.setWordSize((String)basestyle.get("Size"));
				css.setWordColor((String)basestyle.get("Color"));
				css.setWordBlod((String)basestyle.get("Bold"));
				css.setWordItalic((String)basestyle.get("Italic"));
				css.setUnderLine((String)basestyle.get("Underline"));
			}
			
			//背景
			if(style.getChild("Interior") != null){
				if(style.getChild("Interior").getAttributeValue("Color") != null){
					css.setBgColor(style.getChild("Interior").getAttributeValue("Color"));	
				}else{
					css.setBgColor((String)basestyle.get("bgColor"));
				}
				
				if(style.getChild("Interior").getAttributeValue("Pattern") != null){
					css.setPattern(style.getChild("Interior").getAttributeValue("Pattern"));	
				}else{
					css.setPattern((String)basestyle.get("bgPattern"));	
				}
			}else{
				css.setBgColor((String)basestyle.get("bgColor"));
				css.setPattern((String)basestyle.get("bgPattern"));	
			}
			
			/**
			 * 2012-08-22刘新华 湖北pdf导出小数点后位数太长问题   给pdf加上格式化的内容
			 */
			if(style.getChild("NumberFormat") != null){
				if(style.getChild("NumberFormat").getAttributeValue("Format") != null){
					css.setFormat(style.getChild("NumberFormat").getAttributeValue("Format"));	
				}
			}else{
				css.setFormat((String)basestyle.get("Format"));
			}
			
			//保护
			if(style.getChild("Protected") != null){
				if(style.getChild("Protection").getAttributeValue("Protected") != null){
					css.setProtected(style.getChild("Protection").getAttributeValue("Protected"));	
				}
			}else{
				css.setProtected((String)basestyle.get("Protected"));
			}
			
			stylehm.put(styleid,css);
		}
		objectList.add(stylehm);
		
		
		/**
		 *	设置表格对象属性
		 */
		org.jdom.Element table = root.getChild("Worksheets").getChild("Worksheet").getChild("Table");
		pdftable = new PDFTable();
		//pdftable.setBeginRow(table.getAttribute("BeginRow"));
		//pdftable.setEndRow(table.getAttribute("EndRow"));
		
		int DefaultColumnWidth = Integer.parseInt(table.getAttributeValue("DefaultColumnWidth"));
		int DefaultRowHeight = Integer.parseInt(table.getAttributeValue("DefaultRowHeight"));
		pdftable.setDefaultColumnWidth(DefaultColumnWidth);
		pdftable.setDefaultRowHeight(DefaultRowHeight);
		pdftable.setIsPrintBeginRow(Integer.parseInt(table.getAttributeValue("IsPrintBeginRow")));
		pdftable.setDmOrientation(Integer.parseInt(table.getAttributeValue("dmOrientation")));
		
		//跳过行、列
		int FixedColumnCount = Integer.parseInt(table.getAttributeValue("FixedColumnCount"));
		int FixedRowCount = Integer.parseInt(table.getAttributeValue("FixedRowCount"));
		pdftable.setFixedColumnCount(FixedColumnCount);
		pdftable.setFixedRowCount(FixedRowCount);
		int ExpandedColumnCount = Integer.parseInt(table.getAttributeValue("ExpandedColumnCount"));
		int ExpandedRowCount = Integer.parseInt(table.getAttributeValue("ExpandedRowCount"));
		pdftable.setExpandedColumnCount(ExpandedColumnCount);
		pdftable.setExpandedRowCount(ExpandedRowCount);
		
		//设置各列宽
		colomns = table.getChildren("Column");
		for(int q=0; q<colomns.size(); q++){
			org.jdom.Element width = (org.jdom.Element) colomns.get(q);
			widths.put(width.getAttributeValue("Index"),width.getAttributeValue("Width"));
		}
		//拼所有列宽
		int[] ws= new int[ExpandedColumnCount - FixedColumnCount];
		for(int w = FixedColumnCount; w < ExpandedColumnCount; w++){
			if(widths.containsKey(String.valueOf(w+1))){
				ws[w - FixedColumnCount] = Integer.parseInt((String)widths.get(String.valueOf(w+1)));
			}else{
				ws[w - FixedColumnCount] = DefaultColumnWidth;
			}
		}
		//把宽度为1的设为0(隐藏列)
		for(int x = 0; x<ws.length; x++){
			if(ws[x] == 1){
				ws[x] = 0;	
			}	
		}
		pdftable.setWidths(ws);
		
		objectList.add(pdftable);
		
		
		/**
		 *	设置单元格
		 */
		rowlist = table.getChildren("Row");
		for(int j=FixedRowCount; j<rowlist.size(); j++){
			org.jdom.Element row = (org.jdom.Element) rowlist.get(j);
			//行高
			int rowheight = 0;
			if(row.getAttributeValue("Height") != null){
				rowheight = Integer.parseInt(row.getAttributeValue("Height"));
			}else{
				rowheight = DefaultRowHeight;
			}
			
			celllist = row.getChildren("Cell");
			for(int k=FixedColumnCount; k<celllist.size(); k++){
				org.jdom.Element cell = (org.jdom.Element) celllist.get(k);
				
				pdfcell = new PDFCell();
				
				String xy = String.valueOf(j) + "_" + String.valueOf(k);
				pdfcell.setId(xy);
				
				pdfcell.setRowHeight(rowheight);
				
				if(widths.containsKey(String.valueOf(k+1))){
					pdfcell.setCellWidth(Integer.parseInt((String)widths.get(String.valueOf(k+1))));
				}else{
					pdfcell.setCellWidth(DefaultColumnWidth);	
				}
				String dataType=cell.getAttributeValue("StyleID");
				pdfcell.setStyleid(cell.getAttributeValue("StyleID"));
				pdfcell.setDataType(cell.getAttributeValue("DataType"));
				pdfcell.setIsMoney(cell.getAttributeValue("IsMoney"));
				
				pdfcell.setType(cell.getChild("Data").getAttributeValue("Type"));
				pdfcell.setData(cell.getChild("Data").getValue());
				if(cell.getAttributeValue("MergeAcross") != null){
					pdfcell.setMergeAcross(Integer.parseInt(cell.getAttributeValue("MergeAcross")));	
				}else{
					pdfcell.setMergeAcross(0);	
				}
				if(cell.getAttributeValue("MergeDown") != null){
					pdfcell.setMergeDown(Integer.parseInt(cell.getAttributeValue("MergeDown")));	
				}else{
					pdfcell.setMergeDown(0);	
				}
				
				objectList.add(pdfcell);
			}
		}
		
		return objectList;
	}
	public static void main(String[] ss){
		String data="-4.999999999";
		boolean flag=true;
		if(data.indexOf("-")!=-1){
			data=data.substring(1,data.length());
			flag=false;
		}
		if(data.indexOf(".")!=-1&&data.substring(0,data.lastIndexOf(".")).matches("[0-9]*")){
			String b=data.substring(data.lastIndexOf("."),data.length());
			if(b.length()>3){
				data=data.substring(0,data.length()-(b.length()-3));
			} 
			if(!flag){
				data="-"+data;
			}
		}
		System.out.println(data);
	}
}