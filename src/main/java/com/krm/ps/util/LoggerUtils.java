package com.krm.ps.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.organmanage2.services.OrganService2;

public class LoggerUtils {

	public static String formulaFiltCond(String log) {
		String condition = log;
		condition = condition.substring(condition.indexOf("@r") + 2);
		char[] c = condition.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == '@') {
				condition = condition.substring(0, i);
				condition = condition.trim();
				break;
			}
		}
		return condition;
	}

	
}
