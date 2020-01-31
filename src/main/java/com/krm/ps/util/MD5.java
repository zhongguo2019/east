package com.krm.ps.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class MD5 {
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	/**
	 * 转换字节数组为16进制字串
	 * 
	 * @param b
	 *            字节数组
	 * @return 16进制字串
	 */

	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin) {
		String resultString = null;

		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString.getBytes("UTF-8")));
		} catch (Exception ex) {

		}
		return resultString;
	}

   public static void main (String [] arge) throws Exception{	
	   
	   String s=new String("C测试中国结".getBytes("GBK"), "ISO-8859-1");
//       System.out.println("转换后的字符串是："+stringToAscii(s));
////	   System.out.println(MD5Encode("E12345678"));0x01
//       System.out.println(asciiToString("001"));
       
       String  str2 = new String("C测试中国结".getBytes("GBK"), "GBK");
        str2= "C测试中国结";
       System.out.println(str2);
       System.out.println(new String(str2.getBytes("UTF-8"), "UTF-8"));
		System.out.println(new String(s.getBytes("ISO-8859-1"), "UTF-8"));
   }
	
   
   private static String toHexUtil(int n){
       String rt="";
       switch(n){
       case 10:rt+="A";break;
       case 11:rt+="B";break;
       case 12:rt+="C";break;
       case 13:rt+="D";break;
       case 14:rt+="E";break;
       case 15:rt+="F";break;
       default:
           rt+=n;
       }
       return rt;
   }
   
   public static String toHex(int n){
       StringBuilder sb=new StringBuilder();
       if(n/16==0){
           return toHexUtil(n);
       }else{
           String t=toHex(n/16);
           int nn=n%16;
           sb.append(t).append(toHexUtil(nn));
       }
       return sb.toString();
   }
   
   public static String parseAscii(String str){
       StringBuilder sb=new StringBuilder();
       byte[] bs=str.getBytes();
       for(int i=0;i<bs.length;i++)
           sb.append(toHex(bs[i]));
       return sb.toString();
   }
   /**
    * 字符串转换为Ascii
    * @param value
    * @return
    */
   public static String stringToAscii(String value)  
   {  
       StringBuffer sbu = new StringBuffer();  
       char[] chars = value.toCharArray();   
       for (int i = 0; i < chars.length; i++) {  
           if(i != chars.length - 1)  
           {  
               sbu.append((int)chars[i]).append(",");  
           }  
           else {  
               sbu.append((int)chars[i]);  
           }  
       }  
       return sbu.toString();  

}
   /**
    * Ascii转换为字符串
    * @param value
    * @return
    */
   public static String asciiToString(String value)
   {
       StringBuffer sbu = new StringBuffer();
       String[] chars = value.split(",");
       for (int i = 0; i < chars.length; i++) {
           sbu.append((char) Integer.parseInt(chars[i]));
       }
       return sbu.toString();
   }
   
   public static String getUTF8StringFromGBKString(String gbkStr) {  
       try {  
           return new String(getUTF8BytesFromGBKString(gbkStr), "UTF-8");  
       } catch (UnsupportedEncodingException e) {  
           throw new InternalError();  
       }  
   }  
     
   public static byte[] getUTF8BytesFromGBKString(String gbkStr) {  
       int n = gbkStr.length();  
       byte[] utfBytes = new byte[3 * n];  
       int k = 0;  
       for (int i = 0; i < n; i++) {  
           int m = gbkStr.charAt(i);  
           if (m < 128 && m >= 0) {  
               utfBytes[k++] = (byte) m;  
               continue;  
           }  
           utfBytes[k++] = (byte) (0xe0 | (m >> 12));  
           utfBytes[k++] = (byte) (0x80 | ((m >> 6) & 0x3f));  
           utfBytes[k++] = (byte) (0x80 | (m & 0x3f));  
       }  
       if (k < utfBytes.length) {  
           byte[] tmp = new byte[k];  
           System.arraycopy(utfBytes, 0, tmp, 0, k);  
           return tmp;  
       }  
       return utfBytes;  
   } 
}