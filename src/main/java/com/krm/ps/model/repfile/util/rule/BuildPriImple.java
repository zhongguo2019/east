package com.krm.ps.model.repfile.util.rule;

import com.krm.ps.model.repfile.util.BuildRule;
import com.krm.ps.util.MD5;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;

public class BuildPriImple implements BuildRule {
	public String doProcess(String offsenrule, String value, String colname) {
		if(StringUtils.isBlank(value)){
			return value;
		}
		if (("checkName".equals(offsenrule)) && (value.length() <= 3)) {
			value = value.substring(value.length() - 1, value.length());
		}

		if ("checkIDcard".equals(offsenrule)) {
			value = getValue(value);
		}

		if ("checkNull".equals(offsenrule)) {
			value = "";
		}
		if ("checkCode".equals(offsenrule)) {
				value = getValue(value);
		}

		return value;
	}

	private static String getValue(String value) {
		if ("X".equals(value.substring(value.length() - 1, value.length()).toUpperCase())) {
			value = value.substring(0, value.length() - 1)
					+ value.substring(value.length() - 1, value.length()).toUpperCase();
		}

		if (value.contains("参字第")) {
			value = value.substring(0, 2) + MD5.MD5Encode(value);
		} else {
			if (value.length() < 6) {
				int n = 7;
				value = (value + String.format("%1$0" + (n - value.length()) + "d", 0)).substring(0, 6)
						+ MD5.MD5Encode(value);
			} else {
				value = value.substring(0, 6) + MD5.MD5Encode(value);
			}
		}
		return value;
	}

	public static String getUTF8StringFromGBKString(String gbkStr) {
		try {
			return new String(getUTF8BytesFromGBKString(gbkStr), "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		throw new InternalError();
	}

	public static byte[] getUTF8BytesFromGBKString(String gbkStr) {
		int n = gbkStr.length();
		byte[] utfBytes = new byte[3 * n];
		int k = 0;
		for (int i = 0; i < n; i++) {
			int m = gbkStr.charAt(i);
			if ((m < 128) && (m >= 0)) {
				utfBytes[(k++)] = ((byte) m);
			} else {
				utfBytes[(k++)] = ((byte) (0xE0 | m >> 12));
				utfBytes[(k++)] = ((byte) (0x80 | m >> 6 & 0x3F));
				utfBytes[(k++)] = ((byte) (0x80 | m & 0x3F));
			}
		}
		if (k < utfBytes.length) {
			byte[] tmp = new byte[k];
			System.arraycopy(utfBytes, 0, tmp, 0, k);
			return tmp;
		}
		return utfBytes;
	}

}
