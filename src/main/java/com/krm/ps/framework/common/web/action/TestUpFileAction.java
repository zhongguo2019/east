package com.krm.ps.framework.common.web.action;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.krm.ps.framework.common.web.form.TestEncForm;

/**
 * 
 * @struts.action name="testEncForm" path="/testupfile" scope="request"
 * 
 * @struts.action-forward name="testUpFile" path="/testUpCharFile.jsp"
 * 
 */
public class TestUpFileAction extends Action {

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		TestEncForm f = (TestEncForm) form;
		if (f == null) {
			return mapping.findForward("testUpFile");
		}
		String dec = f.getDec();
		FormFile formatFile = f.getContent();
		if (formatFile == null) {
			return mapping.findForward("testUpFile");
		}

		BufferedReader reader = new BufferedReader(new InputStreamReader(
				new BufferedInputStream(formatFile.getInputStream())));

		StringBuffer content = new StringBuffer();
		String tem;
		while ((tem = reader.readLine()) != null) {
			if (dec == null || "".equals(dec)) {
				content.append(tem);
			} else {
				content.append(new String(tem.getBytes(), dec));
			}
			content.append("\n");
		}
		reader.close();
		content.append("\nlength:"+content.length());

		request.setAttribute("dec", dec);
		request.setAttribute("content", content.toString());

		return mapping.findForward("testUpFile");
	}

}
