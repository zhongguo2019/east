/*
 * Copyright 2006 Pentaho Corporation.  All rights reserved. 
 * This software was developed by Pentaho Corporation and is provided under the terms 
 * of the Mozilla Public License, Version 1.1, or any later version. You may not use 
 * this file except in compliance with the license. If you need a copy of the license, 
 * please go to http://www.mozilla.org/MPL/MPL-1.1.txt. The Original Code is the Pentaho 
 * BI Platform.  The Initial Developer is Pentaho Corporation.
 *
 * Software distributed under the Mozilla Public License is distributed on an "AS IS" 
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to 
 * the license for the specific language governing your rights and limitations.
 *
 * @created Apr 20, 2005 
 * @author James Dixon
 * 
 */

package com.krm.ps.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {
	  public static InputStream getURLInputStream(String uri) {

	        try {
	            URL url = new URL(uri);
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.connect();
	            InputStream in = connection.getInputStream();
	            return in;
	        } catch (Exception e) {
	            return null;
	        }

	    }

	    public static Reader getURLReader(String uri) {

	        try {
	            URL url = new URL(uri);
	            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.connect();
	            InputStream in = connection.getInputStream();
	            return new InputStreamReader(in);
	        } catch (Exception e) {
	            return null;
	        }

	    }
	    
	    public static boolean connectoUrl(String uri){
			try
			{
				System.out.println(uri);
				URL url = new URL(uri);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	            connection.connect();
		    	if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
		    		return true;
		    	}else{
		    		return false;
		    	}
			}
			catch (Exception e)
			{
				return false;
			}
            
	    }
}
