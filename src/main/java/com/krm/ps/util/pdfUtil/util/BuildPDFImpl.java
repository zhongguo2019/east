package com.krm.ps.util.pdfUtil.util;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.krm.ps.util.pdfUtil.BuildPDF;
import com.lowagie.text.*;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfWriter;

public class BuildPDFImpl implements BuildPDF{
	Document doc = null;
	
	public BuildPDFImpl(){
		doc = new Document();
	}
	
	public BuildPDFImpl(Rectangle ps){
		new BuildPDFImpl(ps, 0);
	}
	
	/**
	 * 初始化横向
	 * @param ps
	 * @param isRotate
	 */
	public BuildPDFImpl(Rectangle ps, int isRotate){
		if(isRotate == 1){
			doc = new Document(ps.rotate());
		}else{
			doc = new Document(ps);
		}
	}
	/**
	 * 打开文档
	 */
	public void openDocument(){
		doc.open();
	}
	/**
	 * 关闭文档
	 */
	public void closeDocument(){
		doc.close();
	}
	/**
	 * 换页
	 * @throws DocumentException 
	 */
	public void newPage() throws DocumentException{
			doc.newPage();
	}
	/**
	 * 设置生成PDF的路径
	 * @param path
	 */
	public void setPdfPath(String path){
		try{
			PdfWriter.getInstance(doc, new FileOutputStream(path));
		}catch(Exception e){
			e.printStackTrace();	
		}
	}
	
	/**
	 * 添加krm模版表示的表格
	 * @param xmlTable
	 */
	public void addModelTable(String tableXML){
		MakeItextTable mit = new MakeItextTable();
		XmlToObjectList xtol = new XmlToObjectList();
		Table tab = null;
		
		List list = xtol.parseXmlToObject(tableXML);
		try {
			tab = mit.MakeTable(list);
			//doc.newPage();
	        doc.add(tab);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * 添加图片
	 * @param element
	 */
	public void addImage(String imagefile, int width, String explain){
		com.lowagie.text.Image jpeg = null;
		try {
			jpeg = com.lowagie.text.Image.getInstance(imagefile);
			jpeg.setAlignment(Image.MIDDLE);
			//jpeg.setWidthPercentage(80);
			//jpeg.scaleAbsolute(200,100);
			jpeg.scalePercent(width);	//原图缩小百分比
			doc.add(jpeg);
		} catch (BadElementException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 增加一个段落
	 * @param par
	 */
	public void addParagraph(String par, int strSize, int isblod, String color, int align){
		BaseFont bfChinese = null;
		Font FontChinese = null;
		Paragraph paragraph = null;
		
		int strStyle = 0;
		if(isblod == 1){
			strStyle = Font.BOLD;
		}
		
		String[] colors = color.split(",");
		int red = Integer.parseInt(colors[0]);
		int yellow = Integer.parseInt(colors[1]);
		int blue = Integer.parseInt(colors[2]);
		
		try {
			bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			FontChinese = new Font(bfChinese, strSize, strStyle, new Color(red, yellow, blue));
			paragraph = new Paragraph(par, FontChinese);
			//左:0	中:1		右:2
			paragraph.setAlignment(align);
			doc.add(paragraph);
		} catch (DocumentException e) {
		} catch (IOException e) {
		}
	}
	/**
	 * 增加一个部分
	 * @param par
	 */
	public void addPhrase(String par){
		try {
			BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
			Font FontChinese = new Font(bfChinese, 12, Font.NORMAL, new Color(0,0,0));
			Phrase phrase = new Phrase(par, FontChinese);
			doc.add(phrase);
		} catch (DocumentException e) {
		} catch (IOException e) {
		}
	}
	/**
	 * 添加其他对象
	 * @param element itext中的对象
	 */
	public void addObject(Element element){
		try {
			doc.newPage();
			doc.add(element);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}
