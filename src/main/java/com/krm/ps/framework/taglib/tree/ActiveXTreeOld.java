package com.krm.ps.framework.taglib.tree;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

import com.krm.ps.util.EvalHelper;

public class ActiveXTreeOld
{
	private static String CODEBASE_RALATIVE_PATH = "/common/ActiveXTree/ActiveX/GoldTree.ocx#Version=1,2,0,0";

	private static String TREE_COUNTER = "num";

	private String id;

	private String left;

	private String top;

	private String width;

	private String height;

	private String xml = "";

	private String rootId = "";

	private String backColor;

	private String columnTitle = "";

	private String columnWidth = "";

	private String formName;

	private String idstr;

	private String namestr;

	private String buttonName;

	private String checkStyle = "";

	private String selectChild = "";

	private String style = "0";

	private String disabled = "false";

	private String onhide;

	private String fillLayer = "0";
	
	private String txtWidth = "100%";

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getBackColor()
	{
		return backColor;
	}

	public void setBackColor(String backColor)
	{
		this.backColor = backColor;
	}

	public String getButtonName()
	{
		return buttonName;
	}

	public void setButtonName(String buttonName)
	{
		this.buttonName = buttonName;
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

	public String getFormName()
	{
		return formName;
	}

	public void setFormName(String formName)
	{
		this.formName = formName;
	}

	public String getHeight()
	{
		return height;
	}

	public void setHeight(String height)
	{
		this.height = height;
	}

	public String getIdstr()
	{
		return idstr;
	}

	public void setIdstr(String idstr)
	{
		this.idstr = idstr;
	}

	public String getLeft()
	{
		return left;
	}

	public void setLeft(String left)
	{
		this.left = left;
	}

	public String getNamestr()
	{
		return namestr;
	}

	public void setNamestr(String namestr)
	{
		this.namestr = namestr;
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

	public String getTop()
	{
		return top;
	}

	public void setTop(String top)
	{
		this.top = top;
	}

	public String getWidth()
	{
		return width;
	}

	public void setWidth(String width)
	{
		this.width = width;
	}

	public String getXml()
	{
		return xml;
	}

	public String getFillLayer()
	{
		return fillLayer;
	}

	public void setFillLayer(String fillLayer)
	{
		this.fillLayer = fillLayer;
	}

	public void setXml(String xml)
	{
		this.xml = xml;
	}

	public String getStyle()
	{
		return style;
	}

	public void setStyle(String style)
	{
		this.style = style;
	}

	public String getDisabled()
	{
		return disabled;
	}

	public void setDisabled(String enabled)
	{
		this.disabled = enabled;
	}

	public String getOnhide()
	{
		return onhide;
	}

	public void setOnhide(String onhide)
	{
		this.onhide = onhide;
	}

	public boolean isCompatible()
	{
		return true;
	}

	public void release()
	{
		id = null;
		left = null;
		top = null;
		width = null;
		height = null;
		xml = null;
		fillLayer = null;
		txtWidth = null;
		rootId = null;
		backColor = null;
		columnTitle = null;
		columnWidth = null;
		formName = null;
		idstr = null;
		namestr = null;
		buttonName = null;
		checkStyle = null;
		selectChild = null;
		style = null;
		disabled = null;
		onhide = null;
	}

	public void evaluateExpressions(Tag tag, PageContext pageContext)
		throws JspException
	{
		String string = null;
		if ((string = EvalHelper.evalString("id", getId(), tag, pageContext)) != null)
		{
			setId(string);
		}
		if ((string = EvalHelper
			.evalString("left", getLeft(), tag, pageContext)) != null)
		{
			setLeft(string);
		}
		if ((string = EvalHelper.evalString("top", getTop(), tag, pageContext)) != null)
		{
			setTop(string);
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
		if ((string = EvalHelper.evalString("xml", getXml(), tag, pageContext)) != null)
		{
			setXml(string);
		}
		if ((string = EvalHelper.evalString("filllayer", getFillLayer(), tag,
			pageContext)) != null)
		{
			setFillLayer(string);
		}
		if ((string = EvalHelper.evalString("rootid", getRootId(), tag,
			pageContext)) != null)
		{
			setRootId(string);
		}
		if ((string = EvalHelper.evalString("backColor", getBackColor(), tag,
			pageContext)) != null)
		{
			setBackColor(string);
		}
		if ((string = EvalHelper.evalString("columntitle", getColumnTitle(),
			tag, pageContext)) != null)
		{
			setColumnTitle(string);
		}
		if ((string = EvalHelper.evalString("columnwidth", getColumnWidth(),
			tag, pageContext)) != null)
		{
			setColumnWidth(string);
		}
		if ((string = EvalHelper.evalString("formname", getFormName(), tag,
			pageContext)) != null)
		{
			setFormName(string);
		}
		if ((string = EvalHelper.evalString("idstr", getIdstr(), tag,
			pageContext)) != null)
		{
			setIdstr(string);
		}
		if ((string = EvalHelper.evalString("namestr", getNamestr(), tag,
			pageContext)) != null)
		{
			setNamestr(string);
		}
		if ((string = EvalHelper.evalString("buttonname", getButtonName(), tag,
			pageContext)) != null)
		{
			setButtonName(string);
		}
		if ((string = EvalHelper.evalString("checkstyle", getCheckStyle(), tag,
			pageContext)) != null)
		{
			setCheckStyle(string);
		}
		if ((string = EvalHelper.evalString("txtwidth", getTxtWidth(), tag,
			pageContext)) != null)
		{
			setTxtWidth(string);
		}
		if ((string = EvalHelper.evalString("selectchild", getSelectChild(),
			tag, pageContext)) != null)
		{
			setSelectChild(string);
		}
		if ((string = EvalHelper.evalString("style", getStyle(), tag,
			pageContext)) != null)
		{
			setStyle(string);
		}
		if ((string = EvalHelper.evalString("disabled", getDisabled(), tag,
			pageContext)) != null)
		{
			setDisabled(string);
		}
	}

	public String create(HttpServletRequest request)
	{
		StringBuffer treeScript = new StringBuffer();
		String suffix = "";
		if (id != null && id.length() > 0)
		{
			suffix = Character.toUpperCase(id.charAt(0)) + id.substring(1);
		}
		else
		{
			if (request.getAttribute(TREE_COUNTER) == null)
			{
				request.setAttribute(TREE_COUNTER, new Integer(1));
			}
			Integer i = (Integer) request.getAttribute(TREE_COUNTER);
			request.setAttribute(TREE_COUNTER, new Integer(i.intValue() + 1));
			suffix = "Tree" + i;
		}
		String divId = "div" + suffix;
		String objId = "obj" + suffix;
		if (style == null || "0".equals(style))
		{
			String btnId = "btn" + suffix;
			String txtId = "txt" + suffix;

			treeScript.append("<div border=\"1\" id=\"");
			treeScript.append(divId);
			treeScript.append("\" name=\"");
			treeScript.append(divId);
			treeScript
				.append("\" style=\"display:block; width:1px; height:1px;\" onmouseout='");
			treeScript.append(getHideTreeScript(divId, objId, txtId));
			//2006.9.28
			treeScript.append("change").append(suffix).append("();");
			
			treeScript.append("'>");
			treeScript.append("</div>");
			treeScript
				.append("<table style=\"width: 100%;\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
			treeScript.append("<tr><td style=\"width:80;\">");
			if (disabled != null
				&& ("1".equals(disabled) || "true".equalsIgnoreCase(disabled)))
			{
				treeScript
					.append("<input style=\"width:100%;\" type=\"button\" disabled value=\"");
			}
			else
			{
				treeScript
					.append("<input style=\"width:100%;\" type=\"button\" value=\"");
			}
			treeScript.append(buttonName);
			treeScript.append("\" id=\"");
			treeScript.append(btnId);
			treeScript.append("\" name=\"");
			treeScript.append(btnId);
			treeScript.append("\" onclick='");
			treeScript.append(getShowTreeScript(divId, objId, txtId));
			treeScript.append("'/></td>");
			treeScript.append("<td>");			
			treeScript.append("<input style=\"width:");
			treeScript.append(txtWidth);
			treeScript.append(";\" type=\"text\" id=\"");				
			treeScript.append(txtId);
			treeScript.append("\" name=\"");
			treeScript.append(txtId);
			treeScript.append("\" value=\"\" readonly/></td></tr></table>");
			treeScript.append(getLoadScript(request, divId, objId, txtId));
		}
		else
		{
			treeScript.append("<div style=\"position:absolute; left: ");
			treeScript.append(left);
			treeScript.append("px; top: ");
			treeScript.append(top);
			treeScript
				.append("px; width:1px; height:1px; z-index:1; display: block; \" border=\"1\" id=\"");
			treeScript.append(divId);
			treeScript.append("\" name=\"");
			treeScript.append(divId);
			treeScript.append("\">");
			treeScript.append("<object id=\"");
			treeScript.append(objId);
			treeScript.append("\" name=\"");
			treeScript.append(objId);
			treeScript
				.append("\" classid=\"CLSID:B6DE0B41-91FE-41BC-9F4B-DC9428EA2B4B\" codebase=\"");
			treeScript
				.append(request.getContextPath() + CODEBASE_RALATIVE_PATH);
			treeScript.append("\" style=\"width:1px; height:1px;\"></object>");
			treeScript.append("</div>");
			treeScript.append("\n<script type=\"text/javascript\">");
			treeScript.append("\n<!--");
			treeScript.append(getShowTreeScript(divId, objId, "null"));
			treeScript.append("\n//-->");
			treeScript.append("\n</script>");
		}
		return treeScript.toString();
	}

	private StringBuffer getTextScript(String txtId)
	{
		StringBuffer script = new StringBuffer();
		script.append("document.getElementById(\"");
		script.append(txtId);
		script.append("\").value=");
		script.append(formName);
		script.append(".");
		script.append(namestr);
		script.append(".value;");
		return script;
	}

	private StringBuffer getHideTreeScript(String divId, String objId,
		String txtId)
	{
		StringBuffer script = new StringBuffer();
		script.append("ActiveXTree.onHide(");
		script.append("document.getElementById(\"");
		script.append(divId);
		script.append("\"),");
		script.append("document.getElementById(\"");
		script.append(objId);
		script.append("\"),");
		script.append(formName);
		script.append(".");
		script.append(idstr);
		script.append(",");
		script.append(formName);
		script.append(".");
		script.append(namestr);
		script.append(",");
		script.append(onhide == null ? "null" : onhide);
		script.append(");");
		script.append(getTextScript(txtId));

		return script;
	}

	private StringBuffer getShowTreeScript(String divId, String objId,
		String txtId)
	{
		StringBuffer script = new StringBuffer();
		script.append("ActiveXTree.showTree(");
		script.append("document.getElementById(\"");
		script.append(divId);
		script.append("\"),");
		script.append("document.getElementById(\"");
		script.append(objId);
		script.append("\"),");
		script.append("\"");
		script.append(width);
		script.append("\",");
		script.append("\"");
		script.append(height);
		script.append("\",");
		script.append("\"");
		script.append(xml);
		script.append("\",");
		script.append(backColor);
		script.append(",\"");
		script.append(checkStyle);
		script.append("\",");
		script.append("\"");
		script.append(selectChild);
		script.append("\",");
		script.append("\"");
		script.append(columnTitle);
		script.append("\",");
		script.append("\"");
		script.append(columnWidth);
		script.append("\",");
		script.append(fillLayer);
		script.append(",\"");
		script.append(rootId);
		script.append("\",");
		script.append(formName);
		script.append(".");
		script.append(idstr);
		script.append(".value,");
		script.append("document.getElementById(\"");
		script.append(txtId);
		script.append("\"));");

		return script;
	}

	private StringBuffer getLoadScript(HttpServletRequest request,
		String divId, String objId, String txtId)
	{
		StringBuffer script = new StringBuffer();
		script.append("\n<script type=\"text/javascript\">");
		script.append("\n<!--");
		script.append("\nActiveXTree.createControl(\"");
		script.append(divId);
		script.append("\",\"");
		script.append(objId);
		script.append("\",\"");		
		script.append(request.getContextPath() + CODEBASE_RALATIVE_PATH);
		script.append("\");");
		script.append("\ndocument.getElementById(\"");
		script.append(objId);
		script.append("\").onblur='");
		script.append(getHideTreeScript(divId, objId, txtId));
		script.append("';");
		script.append("\nActiveXTree.onLoad(");
		script.append("document.getElementById(\"");
		script.append(divId);
		script.append("\"),");
		script.append("document.getElementById(\"");
		script.append(objId);
		script.append("\"),");
		script.append("\"");
		script.append(xml);
		script.append("\",");
		script.append(backColor);
		script.append(",\"");
		script.append(checkStyle);
		script.append("\",");
		script.append("\"");
		script.append(selectChild);
		script.append("\",");
		script.append("\"");
		script.append(columnTitle);
		script.append("\",");
		script.append("\"");
		script.append(columnWidth);
		script.append("\",");
		script.append(fillLayer);
		script.append(",\"");
		script.append(rootId);
		script.append("\",");
		script.append(formName);
		script.append(".");
		script.append(idstr);
		script.append(",");
		script.append(formName);
		script.append(".");
		script.append(namestr);
		script.append(",");
		script.append("document.getElementById(\"");
		script.append(txtId);
		script.append("\"));");
		script.append("\n//-->");
		script.append("\n</script>");
		return script;
	}

	public String getTxtWidth()
	{
		return txtWidth;
	}

	public void setTxtWidth(String txtWidth)
	{
		this.txtWidth = txtWidth;
	}
}
