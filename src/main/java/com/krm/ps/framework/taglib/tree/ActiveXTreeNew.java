package com.krm.ps.framework.taglib.tree;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import com.krm.ps.util.EvalHelper;

public class ActiveXTreeNew
{
	//private static String CODEBASE_RALATIVE_PATH = "/common/ActiveXTree/ActiveX/GoldTree.ocx#Version=1,0,0,9";

	private String id;

	private String name;

	private String form;

	private String isPopup = "true";

	private String xmlPath;

	private String rootId;

	private String columnTitle;

	private String columnWidth;

	private String checkStyle = "1";

	private String selectChild = "1";

	private String width;

	private String height;

	private String backColor = "0xFFD3C0";

	private String tblStyle = "width: 100%;";

	private String btnTdStyle = "width:80;";

	private String txtTdStyle = "";

	private String btnDisplayName = "select...";

	private String btnStyke;

	public String getBackColor()
	{
		return backColor;
	}

	public void setBackColor(String backColor)
	{
		this.backColor = backColor;
	}

	public String getBtnTdStyle()
	{
		return btnTdStyle;
	}

	public void setBtnTdStyle(String btnTdStyle)
	{
		this.btnTdStyle = btnTdStyle;
	}

	public String getCheckStyle()
	{
		return checkStyle;
	}

	public void setCheckStyle(String checkStyle)
	{
		this.checkStyle = checkStyle;
	}

	public String getColumnTitle()
	{
		return columnTitle;
	}

	public void setColumnTitle(String columnTitle)
	{
		this.columnTitle = columnTitle;
	}

	public String getColumnWidth()
	{
		return columnWidth;
	}

	public void setColumnWidth(String columnWidth)
	{
		this.columnWidth = columnWidth;
	}

	public String getForm()
	{
		return form;
	}

	public void setForm(String form)
	{
		this.form = form;
	}

	public String getHeight()
	{
		return height;
	}

	public void setHeight(String height)
	{
		this.height = height;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String isPopup()
	{
		return isPopup;
	}

	public void setPopup(String isPopup)
	{
		this.isPopup = isPopup;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getRootId()
	{
		return rootId;
	}

	public void setRootId(String rootId)
	{
		this.rootId = rootId;
	}

	public String getSelectChild()
	{
		return selectChild;
	}

	public void setSelectChild(String selectChild)
	{
		this.selectChild = selectChild;
	}

	public String getTblStyle()
	{
		return tblStyle;
	}

	public void setTblStyle(String tblStyle)
	{
		this.tblStyle = tblStyle;
	}

	public String getTxtTdStyle()
	{
		return txtTdStyle;
	}

	public void setTxtTdStyle(String txtTdStyle)
	{
		this.txtTdStyle = txtTdStyle;
	}

	public String getWidth()
	{
		return width;
	}

	public void setWidth(String width)
	{
		this.width = width;
	}

	public String getXmlPath()
	{
		return xmlPath;
	}

	public void setXmlPath(String xmlPath)
	{
		this.xmlPath = xmlPath;
	}

	public String getBtnDisplayName()
	{
		return btnDisplayName;
	}

	public void setBtnDisplayName(String btnDisplayName)
	{
		this.btnDisplayName = btnDisplayName;
	}

	public String getBtnStyke()
	{
		return btnStyke;
	}

	public void setBtnStyke(String btnStyke)
	{
		this.btnStyke = btnStyke;
	}

	public String getIsPopup()
	{
		return isPopup;
	}

	public void setIsPopup(String isPopup)
	{
		this.isPopup = isPopup;
	}

	public void release()
	{
		id = null;
		name = null;
		form = null;
		isPopup = null;
		xmlPath = null;
		rootId = null;
		columnTitle = null;
		columnWidth = null;
		checkStyle = null;
		selectChild = null;
		width = null;
		height = null;
		backColor = null;
		tblStyle = null;
		btnTdStyle = null;
		txtTdStyle = null;
	}

	public void evaluateExpressions(Tag tag, PageContext pageContext)
		throws JspException
	{
		String string = null;
		Boolean bool = null;
		if ((string = EvalHelper.evalString("id", getId(), tag, pageContext)) != null)
		{
			setId(string);
		}
		if ((string = EvalHelper
			.evalString("name", getName(), tag, pageContext)) != null)
		{
			setName(string);
		}
		if ((string = EvalHelper
			.evalString("form", getForm(), tag, pageContext)) != null)
		{
			setForm(string);
		}
		if ((bool = EvalHelper.evalBoolean("isPopup", isPopup(), tag,
			pageContext)) != null)
		{
			setPopup(bool.toString());
		}
		if ((string = EvalHelper.evalString("xmlPath", getXmlPath(), tag,
			pageContext)) != null)
		{
			setXmlPath(string);
		}
		if ((string = EvalHelper.evalString("rootId", getRootId(), tag,
			pageContext)) != null)
		{
			setRootId(string);
		}
		if ((string = EvalHelper.evalString("columnTitle", getColumnTitle(),
			tag, pageContext)) != null)
		{
			setColumnTitle(string);
		}
		if ((string = EvalHelper.evalString("columnWidth", getColumnWidth(),
			tag, pageContext)) != null)
		{
			setColumnWidth(string);
		}
		if ((string = EvalHelper.evalString("checkStyle", getCheckStyle(), tag,
			pageContext)) != null)
		{
			setCheckStyle(string);
		}
		if ((string = EvalHelper.evalString("selectChild", getSelectChild(),
			tag, pageContext)) != null)
		{
			setSelectChild(string);
		}
		if ((string = EvalHelper.evalString("width", getWidth(), tag,
			pageContext)) != null)
		{
			setWidth(string);
		}
		if ((string = EvalHelper.evalString("height", getHeight(), tag,
			pageContext)) != null)
		{
			setHeight(string);
		}
		if ((string = EvalHelper.evalString("backColor", getBackColor(), tag,
			pageContext)) != null)
		{
			setBackColor(string);
		}
		if ((string = EvalHelper.evalString("tblStyle", getTblStyle(), tag,
			pageContext)) != null)
		{
			setTblStyle(string);
		}
		if ((string = EvalHelper.evalString("btnTdStyle", getBtnTdStyle(), tag,
			pageContext)) != null)
		{
			setBtnTdStyle(string);
		}
		if ((string = EvalHelper.evalString("txtTdStyle", getTxtTdStyle(), tag,
			pageContext)) != null)
		{
			setTxtTdStyle(string);
		}
	}

	public String create(HttpServletRequest request)
	{
		StringBuffer treeScript = new StringBuffer();
		return treeScript.toString();
	}
}
