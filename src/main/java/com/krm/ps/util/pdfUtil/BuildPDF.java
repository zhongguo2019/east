package com.krm.ps.util.pdfUtil;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;

public interface BuildPDF {
	
	/**
	 * ���ĵ�
	 */
	public void openDocument();
	/**
	 * �ر��ĵ�
	 */
	public void closeDocument();
	/**
	 * ��ҳ
	 */
	public void newPage() throws DocumentException;
	/**
	 * ��������PDF��·��
	 * @param path
	 */
	public void setPdfPath(String path);
	
	/**
	 * ���krmģ���ʾ�ı��
	 * @param xmlTable
	 */
	public void addModelTable(String tableXML);
	
	/**
	 * ���ͼƬ
	 * @param element
	 */
	public void addImage(String imagefile, int width, String explain);
	/**
	 * ����һ������
	 * @param par
	 */
	public void addParagraph(String par, int strSize, int isblod, String color, int align);
	/**
	 * ����һ������
	 * @param par
	 */
	public void addPhrase(String par);
	/**
	 * �����������
	 * @param element itext�еĶ���
	 */
	public void addObject(Element element);
}
