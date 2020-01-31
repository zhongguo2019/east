package com.krm.ps.util.pdfUtil.util;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.awt.Color;

import org.jdom.*;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.BaseFont;

public class MakeItextTable {
	
	///////////////////////变量定义///////////////////
		
	Map stylehm = null;	//cell格式列表
	Table ptable = null;	//表格对象
	Cell pcell = null;
	Map spanmap = new HashMap();	//存放被合并的单元格
	
	//用于存放拼写后的(x,y)坐标
	String xy = null;	
	String newxy = null;
	
	//单元格宽、高
	int cellwidth = 0;
	int rowheight = 0;
		
	//水平、垂直对齐方式
	int ivertical = 0;
	int ihorizontal = 0;
	String horizontal = null;
	String vertical = null;
	
	//字体颜色
	Color wcolor = null;
	//背景色
	Color bgcolor = null;
	
	//四边线
	Color topcolor = null;
	Color bottomcolor = null;
	Color leftcolor = null;
	Color rightcolor = null;
	
	//四边边框
	String topweight = null;
	String bottomweight = null;
	String leftweight = null;
	String rightweight = null;
			
	//字号
	int wordsize = 0;
	
	//加粗\倾斜\下划线
	int wordblod = 0;
	String worditalic = null;
	int bivalue = 0;
	
	//单元格格式
	Paragraph paragraph = null;	//cell中的段落
	BaseFont bfChinese = null;
	Font FontChinese = null;
	
	//定义总行、列数
	int ExpandedColumnCount = 0;
	int ExpandedRowCount = 0;
	
	String data = null;	//数据
	
	int hide_x = 0;	//当前被隐藏行数
	int hide_y = 0;	//当前被隐藏列数

	///////////////////////方法定义///////////////////
	
	/**
	 * 解析xml字符串，返回解析后的itext中的Table
	 * @param xmlStr
	 * @return
	 * @throws IOException 
	 * @throws JDOMException 
	 */
	public Table MakeTable(List objectList) throws Exception{
		
		//cell格式
		stylehm = (HashMap)objectList.get(0);
		
		//table属性
		PDFTable pdftable = (PDFTable)objectList.get(1);
		ExpandedColumnCount = pdftable.getExpandedColumnCount() - pdftable.getFixedColumnCount();
		ExpandedRowCount = pdftable.getExpandedRowCount() - pdftable.getFixedRowCount();
		
		/**
		 *	建立pdf表格对象
		 */
		//ptable = new Table(ExpandedColumnCount, ExpandedRowCount);
		ptable = new Table(ExpandedColumnCount);
		//对齐方式
		//ptable.setAlignment("center");
		ptable.setSpacing((float)-0.5);
		ptable.setPadding((float)3);
		//table是否有边框
		ptable.setBorder(0);
		//自动填充空白单元格
		ptable.setAutoFillEmptyCells(true);
		//换页不拆cell
		ptable.setCellsFitPage(true);
		ptable.setWidths(pdftable.getWidths());
		ptable.setWidth(100);
		ptable.setOffset(32);

		/**
		 *	建立Cell对象,并加到表格对象中
		 */
		for(int j=2; j<objectList.size(); j++){
			PDFCell pdfcell = (PDFCell)objectList.get(j);
			CellStyles css = null;
			
			xy = pdfcell.getId();
			//x,y坐标放于数组中
			String id[] = xy.split("_");
			int x = Integer.parseInt(id[0]);
			int y = Integer.parseInt(id[1]);
			
			//首先判断是否被合并,没被合并则继续执行
			if(!"1".equals((String)spanmap.get(xy))){
				
				//合并单元格
				int rows = pdfcell.getMergeDown();
				int cols = pdfcell.getMergeAcross();
				if(rows != 0 && cols != 0){
					//把被合并的单元格放入HashMap中
					for(int ro = x; ro < x + rows; ro++){
						for(int co = y; co < y + cols; co++){
							newxy = ro + "_" + co;
							spanmap.put(newxy, "1");
						}	
					}
				}
				
				//单元格宽度
				cellwidth = pdfcell.getCellWidth();
				rowheight = pdfcell.getRowHeight();
				
				//读取格式
				css = (CellStyles)stylehm.get(pdfcell.getStyleid());
				
				//水平、垂直对齐
				horizontal = css.getHorizontal();
				vertical = css.getVertical();
				
				if("Left".equals(horizontal)){
					ihorizontal = com.lowagie.text.Element.ALIGN_LEFT;
				}else if("Center".equals(horizontal)){
					ihorizontal = com.lowagie.text.Element.ALIGN_CENTER;
				}else if("Right".equals(horizontal)){
					ihorizontal = com.lowagie.text.Element.ALIGN_RIGHT;
				}else{
					ihorizontal = com.lowagie.text.Element.ALIGN_CENTER;	
				}
				if("Top".equals(vertical)){
					ivertical = com.lowagie.text.Element.ALIGN_TOP;
				}else if("VCenter".equals(vertical)){
					ivertical = com.lowagie.text.Element.ALIGN_MIDDLE;
				}else if("Bottom".equals(vertical)){
					ivertical = com.lowagie.text.Element.ALIGN_BOTTOM;
				}else{
					ivertical = com.lowagie.text.Element.ALIGN_MIDDLE;	
				}
				
				int wordcolor = 0;
				int backcolor = 0;
				//字体颜色,默认为黑色
				if("4278190080".equals(css.getWordColor())){
					//wcolor = new Color(255,255,255);
					wcolor = new Color(0,0,0);
				}else if("0".equals(css.getWordColor())){
					wcolor = new Color(0,0,0);
				}else{
					wordcolor = Integer.parseInt(css.getWordColor());
					wcolor = this.getColor(wordcolor);
				}
				//背景色
				if("4278190080".equals(css.getBgColor())){
					bgcolor = new Color(255,255,255);
				}else if("0".equals(css.getBgColor())){
					bgcolor = new Color(0,0,0);
				}else{
					backcolor = Integer.parseInt(css.getBgColor());
					bgcolor = this.getColor(backcolor);
				}
				int tcolor = 0;
				int bcolor = 0;
				int lcolor = 0;
				int rcolor = 0;
				//上格线颜色
				if("4278190080".equals(css.getTopColor())){
					topcolor = new Color(255,255,255);
				}else if("0".equals(css.getTopColor())){
					topcolor = new Color(0,0,0);
				}else{
					tcolor = Integer.parseInt(css.getTopColor());
					topcolor = this.getColor(tcolor);
				}
				//下格线颜色
				if("4278190080".equals(css.getBottomColor())){
					bottomcolor = new Color(255,255,255);
				}else if("0".equals(css.getBottomColor())){
					bottomcolor = new Color(0,0,0);
				}else{
					bcolor = Integer.parseInt(css.getBottomColor());
					bottomcolor = this.getColor(bcolor);
				}
				//左格线颜色
				if("4278190080".equals(css.getLeftColor())){
					leftcolor = new Color(255,255,255);
				}else if("0".equals(css.getLeftColor())){
					leftcolor = new Color(0,0,0);
				}else{
					lcolor = Integer.parseInt(css.getLeftColor());
					leftcolor = this.getColor(lcolor);
				}
				//右格线颜色
				if("4278190080".equals(css.getRightColor())){
					rightcolor = new Color(255,255,255);
				}else if("0".equals(css.getRightColor())){
					rightcolor = new Color(0,0,0);
				}else{
					rcolor = Integer.parseInt(css.getRightColor());
					rightcolor = this.getColor(rcolor);
				}
				
				//四边表格
				int tweight = 0;
				int bweight = 0;
				int lweight = 0;
				int rweight = 0;
				topweight = css.getTopWeight();
				if("1".equals(topweight)){
					tweight = 1;	
				}
				if("0".equals(topweight)){
					if(x != (pdftable.getFixedRowCount())){
						newxy = String.valueOf(x-hide_x-1) + "_" + String.valueOf(y);
						
						String sid = null;
						for(int z=2; z<objectList.size(); z++){
							PDFCell pcell = (PDFCell)objectList.get(z);
							if(pcell.getId().equals(newxy)){
								sid = pcell.getStyleid();
							}
						}
						CellStyles c = (CellStyles)stylehm.get(sid);
						if("1".equals(c.getBottomWeight())){
							tweight = 1;
						}
					}
				}
				bottomweight = css.getBottomWeight();
				if("1".equals(bottomweight)){
					bweight = 1;	
				}
				leftweight = css.getLeftWeight();
				if("1".equals(leftweight)){
					lweight = 1;	
				}
				if("0".equals(leftweight)){
					if(y != (pdftable.getFixedColumnCount())){
						newxy = String.valueOf(x) + "_" + String.valueOf(y-hide_y-1);
						
						String sid = null;
						for(int z=2; z<objectList.size(); z++){
							PDFCell pcell = (PDFCell)objectList.get(z);
							if(pcell.getId().equals(newxy)){
								sid = pcell.getStyleid();
							}
						}
						CellStyles c = (CellStyles)stylehm.get(sid);
						if("1".equals(c.getRightWeight())){
							lweight = 1;	
						}
					}
				}
				rightweight = css.getRightWeight();
				if("1".equals(rightweight)){
					rweight = 1;	
				}
				
				//字体大小,要求绝对值
				wordsize = Math.abs(Integer.parseInt(css.getWordSize()));
				
				//加粗、倾斜、下划线
				wordblod = Integer.parseInt(css.getWordBlod());
				worditalic = css.getWordItalic();
				if(wordblod == 700 && worditalic.equals("True")){
					bivalue = Font.BOLDITALIC;	
				}else if(wordblod == 700 && worditalic.equals("False")){
					bivalue = Font.BOLD;	
				}else if(wordblod == 400 && worditalic.equals("True")){
					bivalue = Font.ITALIC;	
				}else{
					bivalue = Font.NORMAL;
				}
				if(css.getUnderLine().equals("True")){
					bivalue = bivalue + Font.UNDERLINE;
				}
				
				//数据
				if(pdfcell.getType().equals("ComboList")){
					String com[] = pdfcell.getData().split(";");
					data = com[0];
				}else{
					if(pdfcell.getData().equals("")){
						data = "\n";
					}else{
						data = pdfcell.getData();
					}
				}
				
				//记录当前被隐藏行\列数
				if(cellwidth == 1){
					hide_y++;	
				}else{
					hide_y = 0;	
				}
				if(rowheight == 1 && y == ExpandedColumnCount){
					hide_x++;
				}else if(rowheight != 1 && y == ExpandedColumnCount){
					hide_x = 0;	
				}
				//如果隐藏第一行/列
				if(x == pdftable.getFixedRowCount()){
					hide_x = 0;	
				}
				if(y == pdftable.getFixedColumnCount()){
					hide_y = 0;	
				}
				
				//建立一个字体
				bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
				FontChinese = new Font(bfChinese, wordsize, bivalue, wcolor);
				paragraph = new Paragraph(data, FontChinese);
				
				//Font font11 = new Font(bfChinese,12,Font.NORMAL); 
				//Paragraph paragraph = new Paragraph(data,font11); 

				/**
				 *	单元格对象
				 */
				pcell = new Cell();
				if(cellwidth == 1){
				}else{
					pcell.add(paragraph);
				}
				//pcell.setWidth(cellwidth);
				pcell.setHorizontalAlignment(ihorizontal);
				pcell.setVerticalAlignment(ivertical);
				pcell.setBackgroundColor(bgcolor);
				
				pcell.setBorderColorTop(topcolor);
				pcell.setBorderColorBottom(bottomcolor);
				pcell.setBorderColorLeft(leftcolor);
				pcell.setBorderColorRight(rightcolor);
				pcell.setBorderWidthTop(tweight);
				pcell.setBorderWidthBottom(bweight);
				pcell.setBorderWidthLeft(lweight);
				pcell.setBorderWidthRight(rweight);
				
				if(rows != 1 && rows != 0){
					pcell.setRowspan(rows);	
				}
				if(cols != 1 && cols != 0){
					pcell.setColspan(cols);	
				}
				//单元格中内容不折行
				//pcell.setMaxLines(1);
				if(pdfcell.getRowHeight() == 1){
					
				}else{
					ptable.addCell(pcell);
				}
			}else{
				//跳过被合并的单元格
			}
		}
		
		return ptable;
	}
	
	
	public Color getColor(int color10){
		
		if(color10 == 255){
			return new Color(255,0,0);	
		}else if(color10 == 16698268){
			return new Color(135,206,250);
		}else{
			return new Color(color10);
		}
	}
}
