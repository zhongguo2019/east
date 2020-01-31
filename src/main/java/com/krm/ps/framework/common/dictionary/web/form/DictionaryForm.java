package com.krm.ps.framework.common.dictionary.web.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;

import com.krm.ps.framework.common.web.form.BaseForm;

/**
 * @struts.form name="dictionaryForm" 
 */
public class DictionaryForm extends BaseForm implements java.io.Serializable
{

	private static final long serialVersionUID = 8198357259253522895L;

	private String dicName;

	private String disc;

	public DictionaryForm()
	{
	}

	public String getDicName()
	{
		return dicName;
	}

	public void setDicName(String dicName)
	{
		this.dicName = dicName;
	}

	public String getDisc()
	{
		return disc;
	}

	public void setDisc(String disc)
	{
		this.disc = disc;
	}

	public void reset(ActionMapping actionMapping,
		HttpServletRequest httpServletRequest)
	{
	}
}
