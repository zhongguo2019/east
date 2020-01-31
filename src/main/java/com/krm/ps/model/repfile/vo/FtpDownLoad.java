package com.krm.ps.model.repfile.vo;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import sun.net.TelnetInputStream;
import sun.net.TelnetOutputStream;
import sun.net.ftp.FtpClient;
public class FtpDownLoad {
		private FtpClient ftpclient;

		private String ipAddress;

		private int ipPort;

		private String userName;

		private String PassWord;
		
		public FtpDownLoad(String ip, int port, String username, String password) throws Exception {
			ipAddress = new String(ip);
			ipPort = port;
			ftpclient = new FtpClient(ipAddress, ipPort);
			userName = new String(username);
			PassWord = new String(password);
		}

		public FtpDownLoad(String ip, String username, String password) throws Exception {
			ipAddress = new String(ip);
			ipPort = 21;
			ftpclient = new FtpClient(ipAddress, ipPort);
			userName = new String(username);
			PassWord = new String(password);
		}

		public void login() throws Exception {
			ftpclient.login(userName, PassWord);
		}

		public void logout() throws IOException {
			// 用ftpclient.closeServer()断开FTP出错时用下更语句退出
//			ftpclient.sendServer("QUIT\r\n");
			ftpclient.closeServer();
//			int reply = ftpclient.readServerResponse(); // 取得服务器的返回信息
		}

		public void buildList(String pathList) throws IOException {
			ftpclient.ascii();
			StringTokenizer s = new StringTokenizer(pathList, "/"); // sign
			int count = s.countTokens();
			String pathName = "";
			while (s.hasMoreElements()) {
				pathName = pathName + "/" + (String) s.nextElement();
				try {
					ftpclient.sendServer("XMKD " + pathName + "\r\n");
				} catch (Exception e) {
					e = null;
				}
				int reply = ftpclient.readServerResponse();
			}
			ftpclient.binary();
		}

		public ArrayList fileNames(String fullPath) throws IOException {
			ftpclient.ascii(); // 注意，使用字符模式
			TelnetInputStream list = ftpclient.nameList(fullPath);
			byte[] names = new byte[2048];
			int bufsize = 0;
			bufsize = list.read(names, 0, names.length); // 从流中读取
			list.close();
			ArrayList namesList = new ArrayList();
			int i = 0;
			int j = 0;
			while (i < bufsize /* names.length */) {
				// char bc = (char) names;
				// System.out.println(i + " " + bc + " : " + (int) names);
				// i = i + 1;
				if (names[i] == 10) { // 字符模式为10，二进制模式为13
					// 文件名在数据中开始下标为j,i-j为文件名的长度,文件名在数据中的结束下标为i-1
					// System.out.write(names, j, i - j);
					// System.out.println(j + " " + i + " " + (i - j));
					String tempName = new String(names, j, i - j);
					namesList.add(tempName);
					// System.out.println(temp);
					// 处理代码处
					// j = i + 2; //上一次位置二进制模式
					j = i + 1; // 上一次位置字符模式
				}
				i = i + 1;
			}
			return namesList;
		}

		public void upFile(String source, String destination) throws IOException {
			buildList(destination.substring(0, destination.lastIndexOf("/")));
			ftpclient.binary(); // 此行代码必须放在buildList之后
			TelnetOutputStream ftpOut = ftpclient.put(destination);
			TelnetInputStream ftpIn = new TelnetInputStream(new FileInputStream(source), true);
			byte[] buf = new byte[204800];
			int bufsize = 0;
			while ((bufsize = ftpIn.read(buf, 0, buf.length)) != -1) {
				ftpOut.write(buf, 0, bufsize);
			}
			ftpIn.close();
			ftpOut.close();

		}

		public void upFile(byte[] sourceData, String destination) throws Exception {
			buildList(destination.substring(0, destination.lastIndexOf("/")));
			ftpclient.binary(); // 此行代码必须放在buildList之后
			TelnetOutputStream ftpOut = ftpclient.put(destination);
			ftpOut.write(sourceData, 0, sourceData.length);
			// ftpOut.flush();
			ftpOut.close();
		}

		public void downFile(String SourceFileName, String destinationFileName) throws Exception {
			ftpclient.binary(); // 一定要使用二进制模式
			TelnetInputStream ftpIn = ftpclient.get(SourceFileName);
			byte[] buf = new byte[204800];
			int bufsize = 0;
			FileOutputStream ftpOut = new FileOutputStream(destinationFileName);
			while ((bufsize = ftpIn.read(buf, 0, buf.length)) != -1) {
				ftpOut.write(buf, 0, bufsize);
			}
			ftpOut.close();
			ftpIn.close();
		}

		public byte[] downFile(String SourceFileName) throws Exception {
			ftpclient.binary(); // 一定要使用二进制模式
			TelnetInputStream ftpIn = ftpclient.get(SourceFileName);
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			byte[] buf = new byte[204800];
			int bufsize = 0;

			while ((bufsize = ftpIn.read(buf, 0, buf.length)) != -1) {
				byteOut.write(buf, 0, bufsize);
			}
			byte[] return_arraybyte = byteOut.toByteArray();
			byteOut.close();
			ftpIn.close();
			return return_arraybyte;
		}

		public void delFilesFromFTP (String file) {
			ftpclient.sendServer("DELE " + file + "\r\n");
		}
		public void sendCommand(String path){
			try {
				ftpclient.cd(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public static void main(String[] args) throws Exception {
			String ip = "192.168.0.59";
			int port = 21;
			String userName = "qwk";
			String password = "qwk";
			String destinationFile = "C:\\ftpfile\\";
            
			String pp="E0001H263010001-YGB-20160831.txt";
			FtpDownLoad fUp = new FtpDownLoad(ip, port, userName, password);
			fUp.login();
			String remoteRoot = "\\ftp\\";
			fUp.sendCommand(remoteRoot);
			List fileList = fUp.fileNames(remoteRoot);
			for (int i = 0; i < fileList.size(); i++) {
				String fileName = fileList.get(i).toString();
				System.out.println(fileName);
				if (pp.equals(fileName)) {
					
					String tempFile = destinationFile + fileName;
					fUp.downFile(fileName, tempFile);
				}
			}
			fUp.logout();
		}
	}