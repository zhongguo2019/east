package com.krm.ps.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;


/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2008</p>
 *
 * <p>Company: KRM</p>
 *
 * @author zuoshaojie
 */
public class CryptionData
{
	private String EncryptionString;

	private final byte[] EncryptionIV = "abcdefgh".getBytes();

	private final static String DES = "DES/CBC/PKCS5Padding";

	/**
	 * @param EncryptionString String
	 */
	public CryptionData(){
		this.EncryptionString = "slsintmo1104";
	}

	/**
	 * @param SourceData byte[]
	 * @throws Exception
	 * @return byte[]
	 */
	public byte[] EncryptionByteData(byte[] SourceData)
	{
		byte[] retByte = null;
		try{
			byte[] EncryptionByte = EncryptionString.getBytes();
			DESKeySpec dks = new DESKeySpec(EncryptionByte);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(dks);
			IvParameterSpec spec = new IvParameterSpec(EncryptionIV);
			Cipher cipher = Cipher.getInstance(DES);
			cipher.init(Cipher.ENCRYPT_MODE, securekey, spec);
			retByte = cipher.doFinal(SourceData);
			return retByte;
		}
		catch (Exception e){
			return retByte;
		}
	}

	/**
	 * @param SourceData byte[]
	 * @throws Exception
	 * @return byte[]
	 */
	public byte[] DecryptionByteData(byte[] SourceData){
		byte[] retByte = null;
		try{
			byte[] EncryptionByte = EncryptionString.getBytes();
			DESKeySpec dks = new DESKeySpec(EncryptionByte);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(dks);
			IvParameterSpec spec = new IvParameterSpec(EncryptionIV);
			Cipher cipher = Cipher.getInstance(DES);
			cipher.init(Cipher.DECRYPT_MODE, securekey, spec);
			retByte = cipher.doFinal(SourceData);
			return retByte;
		}
		catch (Exception e){
			return retByte;
		}
	}

	/**
	 * @param SourceData String
	 * @throws Exception
	 * @return String
	 */
	public String EncryptionStringData(String SourceData){
		String retStr = null;
		byte[] retByte = null;
		byte[] sorData = SourceData.getBytes();
		if(sorData != null){
			retByte = EncryptionByteData(sorData);
			if(retByte != null)
				retStr = Base64.encode(retByte);
		}
		return retStr;
	}

	/**
	 * @param SourceData String
	 * @throws Exception
	 * @return String
	 */
	public String DecryptionStringData(String SourceData)
	{
		String retStr = null;
		byte[] retByte = null;
		byte[] sorData = Base64.decode(SourceData);
		if(sorData != null){
			retByte = DecryptionByteData(sorData);
			if(retByte != null)
				retStr = new String(retByte);
		}
		return retStr;
	}
	
	public String genChartVisitUrl(String server, String port, String userid, String password){
		if(server.trim().equals(""))
			server = "localhost";
		if(port.trim().equals(""))
			port = "9088";
		String defaultUrl = "http://"+server+":"+port+"/slsint/chart.do?method=enter&type=4";
		String euser = EncryptionStringData(userid);
		String epassword = EncryptionStringData(password);
		defaultUrl = defaultUrl + "&user=" + euser + "&pass=" + epassword;
		return defaultUrl;
	}

	public static final void main(String args[])
	{
		CryptionData cryptionData = new CryptionData();
		String e = cryptionData.EncryptionStringData("guest");
		System.out.println("Encoded = " + e);
		System.out.println("Decoded = " + cryptionData.DecryptionStringData(e));
		
		CryptionData cryptionData2 = new CryptionData();
		String e2 = cryptionData2.genChartVisitUrl("localhost","9088","admin", "1");
		System.out.println("Encoded = " + e2);
	}
}
