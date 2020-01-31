package com.krm.ps.util;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

public final class EvalHelper
{
	private EvalHelper()
	{
	}

	public static Object eval(String attrName, String attrValue, Tag tagObject,
		PageContext pageContext) throws JspException
	{
		Object result = null;
		if (attrValue != null)
		{
			result = ExpressionEvaluatorManager.evaluate(attrName, attrValue,
				Object.class, tagObject, pageContext);
		}

		return (result);
	}

	public static String evalString(String attrName, String attrValue,
		Tag tagObject, PageContext pageContext) throws JspException
	{
		Object result = null;
		if (attrValue != null)
		{
			result = ExpressionEvaluatorManager.evaluate(attrName, attrValue,
				String.class, tagObject, pageContext);
		}

		return ((String) result);
	}

	public static Integer evalInteger(String attrName, String attrValue,
		Tag tagObject, PageContext pageContext) throws JspException
	{
		Object result = null;
		if (attrValue != null)
		{
			result = ExpressionEvaluatorManager.evaluate(attrName, attrValue,
				Integer.class, tagObject, pageContext);
		}

		return ((Integer) result);
	}

	public static Boolean evalBoolean(String attrName, String attrValue,
		Tag tagObject, PageContext pageContext) throws JspException
	{
		Object result = null;
		if (attrValue != null)
		{
			result = ExpressionEvaluatorManager.evaluate(attrName, attrValue,
				Boolean.class, tagObject, pageContext);
		}

		return ((Boolean) result);
	}
}
