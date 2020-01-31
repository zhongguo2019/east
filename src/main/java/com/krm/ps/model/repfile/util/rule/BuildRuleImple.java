package com.krm.ps.model.repfile.util.rule;

import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.StringReader;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.Scriptable;

import com.krm.ps.model.repfile.util.BuildRule;

public class BuildRuleImple implements BuildRule {

	public Object doProcess(String reportid, String fieldname,
			String offsenrule, Object contras) {
		Scriptable scope = null;
		Context cx = null;
		Object result = null;
		try {
			cx = Context.enter();
			scope = cx.initStandardObjects();
			int num = 1;
			if (num == 0) {
				cx.evaluateReader(scope, adrScriptReader(reportid), null, 1,
						null);
			} else {
				cx.evaluateReader(scope, StringScriptReader(offsenrule), null,
						1, null);
			}
			String strfunname = "getPriValue";
			Object fObj = scope.get(strfunname, scope);
			Function f = (Function) fObj;
			Object[] contr = { contras };
			result = f.call(cx, scope, scope, new Object[] { contr });

		} catch (Exception o) {
			return null;
		}
		Context.exit();
		return result;
	}

	private static Reader StringScriptReader(String strfun) {
		StringReader s = new StringReader(strfun);
		return s;
	}

	private static Reader adrScriptReader(String reportid)
			throws FileNotFoundException {
		String filename = "web/common/JSrepfile/offsenrule" + reportid + ".js";// 读取系统中js文件
		java.io.FileReader reader = new java.io.FileReader(new java.io.File(
				filename));
		return reader;
	}

	public String doProcess(String offsenrule, String value,String colname) {
		return null;
	}

}
