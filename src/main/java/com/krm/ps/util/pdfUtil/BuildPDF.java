package com.krm.ps.util.pdfUtil;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;

public interface BuildPDF {
	
	/**
	 * 打开文档
	 */
	public void openDocument();
	/**
	 * 关闭文档
	 */
	public void closeDocument();
	/**
	 * 换页
	 */
	public void newPage() throws DocumentException;
	/**
	 * 设置生成PDF的路径
	 * @param path
	 */
	public void setPdfPath(String path);
	
	/**
	 * 添加krm模版表示的表格
	 * @param xmlTable
	 */
	public void addModelTable(String tableXML);
	
	/**
	 * 添加图片
	 * @param element
	 */
	public void addImage(String imagefile, int width, String explain);
	/**
	 * 增加一个段落
	 * @param par
	 */
	public void addParagraph(String par, int strSize, int isblod, String color, int align);
	/**
	 * 增加一个部分
	 * @param par
	 */
	public void addPhrase(String par);
	/**
	 * 添加其他对象
	 * @param element itext中的对象
	 */
	public void addObject(Element element);
}
