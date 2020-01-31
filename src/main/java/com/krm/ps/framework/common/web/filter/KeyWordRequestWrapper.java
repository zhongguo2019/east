package com.krm.ps.framework.common.web.filter;

import java.util.*;


import javax.servlet.http.*;


public class KeyWordRequestWrapper extends HttpServletRequestWrapper{

	private Properties props;

	public KeyWordRequestWrapper(HttpServletRequest request,Properties props) {
		super(request);
		this.props = props;
	}

	@Override
	public Map getParameterMap() {
		super.getContextPath();
		Map<String, String[]> map = super.getParameterMap();
		if (!map.isEmpty()) {
			Set<String> keySet = map.keySet();
			Iterator<String> keyIt = keySet.iterator();
			while (keyIt.hasNext()) {
				String key = keyIt.next();
				String[] values = map.get(key);
				for (int i = 0; i < values.length; i++) {
					map.get(key)[i] = this.replaceParam(values[i]);
				}
			}
		}
		return map;
	}

	@Override
	public String getParameter(String str) {
		String s = super.getParameter(str);
		return replaceParam(s);
	}

	@Override
	public String[] getParameterValues(String str) {
		String[] ss = super.getParameterValues(str);
		if (ss == null || ss.length == 0) {
			return ss;
		}
		String[] ss2 = new String[ss.length];
		for (int i = 0; i < ss2.length; i++) {
			ss2[i] = replaceParam(ss[i]);
		}
		return ss2;
	}

	private String replaceParam(String string) {
		/*if (StringUtils.isEmpty(string)) {
			return string;
		}*/
		if(string==null||string.equals("")){
			return string;
		}
		String sb = new String(string);
		Enumeration en = props.propertyNames();
		while (en.hasMoreElements()) {
			String s = en.nextElement().toString();
			if (string.contains(s)) {
				sb = sb.replaceAll(s, props.getProperty(s));
			}
		}
		return sb;
	}
}
