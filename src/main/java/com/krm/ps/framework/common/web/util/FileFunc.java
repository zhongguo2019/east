// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   FileFunc.java
package com.krm.ps.framework.common.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileFunc {

	public FileFunc() {
	}

	public static byte[] file2buf(String fn) throws IOException {
		if (fn == null || fn.length() == 0) {
			return null;
		} else {
			File fobj = new File(fn);
			return file2buf(fobj);
		}
	}

	public static byte[] file2buf(File fobj) throws IOException {
		FileInputStream in;
		byte abyte0[];
		if (fobj == null || !fobj.exists() || !fobj.isFile()) {
			return null;
		}

		in = null;
		try {
			in = new FileInputStream(fobj);

			byte buf[] = new byte[in.available()];
			int r = in.read(buf);
			abyte0 = buf;
			in.close();
			return abyte0;
		} catch (FileNotFoundException ex) {

			Logger.getLogger(FileFunc.class.getName()).log(Level.SEVERE, null,
					ex);
		}
		return null;

	}

	public static String includePathSeparator(String s) {
		if (s == null || s.length() == 0) {
			return s;
		}
		if (s.charAt(s.length() - 1) != File.separatorChar) {
			return s + File.separatorChar;
		} else {
			return s;
		}
	}
}
