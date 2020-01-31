package com.krm.ps.framework.taglib.tree;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author
 * 
 * @jsp.tag name="ActiveXTree" bodycontent="empty"
 */
public class ActiveXTreeTag extends TagSupport
{
	private static final long serialVersionUID = -2133039816698920451L;

	ActiveXTreeOld oTree = new ActiveXTreeOld();

	ActiveXTreeNew nTree = new ActiveXTreeNew();

	/**
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setId(String id)
	{
		oTree.setId(id);
	}
	
	/**
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setBgcolor(String bgcolor)
	{
		oTree.setBackColor(bgcolor);
	}

	/**
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setButtonname(String buttonname)
	{
		oTree.setButtonName(buttonname);
	}

	/**
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setCheckstyle(String checkstyle)
	{
		oTree.setCheckStyle(checkstyle);
	}

	/**
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setColumntitle(String columnTitle)
	{
		oTree.setColumnTitle(columnTitle);
	}

	/**
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setColumnwidth(String columnwidth)
	{
		oTree.setColumnWidth(columnwidth);
	}

	/**
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setFormname(String formname)
	{
		oTree.setFormName(formname);
	}

	/**
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setHeight(String height)
	{
		oTree.setHeight(height);
	}

	/**
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setIdstr(String idstr)
	{
		oTree.setIdstr(idstr);
	}

	/**
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setLeft(String left)
	{
		oTree.setLeft(left);
	}

	/**
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setNamestr(String namestr)
	{
		oTree.setNamestr(namestr);
	}
	
	/**
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setFilllayer(String filllayer)
	{
		oTree.setFillLayer(filllayer);
	}
	
	/**
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setTxtwidth(String txtwidth)
	{
		oTree.setTxtWidth(txtwidth);
	}

	/**
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setRootid(String rootid)
	{
		oTree.setRootId(rootid);
	}

	/**
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setSelectchild(String selectchild)
	{
		oTree.setSelectChild(selectchild);
	}

	/**
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setTop(String top)
	{
		oTree.setTop(top);
	}

	/**
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setWidth(String width)
	{
		oTree.setWidth(width);
	}

	/**
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setXml(String xml)
	{
		oTree.setXml(xml);
	}

	/**
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setStyle(String style)
	{
		oTree.setStyle(style);
	}

	/**
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setDisabled(String disabled)
	{
		oTree.setDisabled(disabled);
	}
	
	/**
	 * @jsp.attribute required="false" rtexprvalue="true"
	 */
	public void setOnhide(String onhide)
	{
		oTree.setOnhide(onhide);
	}
	
	public int doStartTag() throws JspException
	{
		oTree.evaluateExpressions(this, this.pageContext);
		nTree.evaluateExpressions(this, this.pageContext);
		String treeScript = oTree.isCompatible() ? oTree
			.create((HttpServletRequest) pageContext.getRequest()) : nTree
			.create((HttpServletRequest) pageContext.getRequest());
		JspWriter writer = pageContext.getOut();

		try
		{
			writer.println(treeScript);
		}
		catch (IOException ioe)
		{
			throw new JspException(
				"Error: IOException while writing to the user");
		}
		return SKIP_BODY;
	}

	public void release()
	{
//在weblogic和tomcat的实现有所不同，在一个页面有两个相同的自定义标签时weblogic会出现问题（weblogic是每个标签调用release()一次），
//		而且此方法也不需要实现，wsx 10-11
	}
}
