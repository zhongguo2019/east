package com.krm.ps.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;
import org.apache.struts.upload.MultipartRequestHandler;

import webclient.subsys.util.SystemConfigReader;

import com.krm.ps.util.DateUtil;
import com.krm.ps.util.RandomGUID;
import com.krm.ps.util.ServerUtil;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * Title: Action一些常用的处理
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2006
 * </p>
 * 
 * <p>
 * Company:
 * </p>.
 *
 * @author
 */
public class ActionUtil {
	
	/** 记录日志信息. */
	private static Log log = LogFactory.getLog(ActionUtil.class);

	/**
	 * <p>
	 * 获取form中上传的文件内容及其信息
	 * </p>.
	 *
	 * @param form the form
	 * @param fileSizeLimit 表示上传的文件的最大限制，如：3 表示上传文件不得超过3M
	 * @return the parses the file result
	 * @throws Exception the exception
	 * @author 皮亮
	 * @version 创建时间：2010-4-13 下午04:01:16
	 */
	public static ParseFileResult parseActionFormFile(ActionForm form,
			int fileSizeLimit) throws Exception {
		ParseFileResult parseFileResult = new ActionUtil().new ParseFileResult();
		if (form == null)
			return parseFileResult;
		int size = 1024 * 1024 * fileSizeLimit;
		MultipartRequestHandler multipartHandler = form
				.getMultipartRequestHandler();
		Hashtable ht = multipartHandler.getFileElements();
		Enumeration e = ht.keys();
		int fileSize = 0;
		List fileContentList = new ArrayList();
		byte[] b = null;
		String fileName = "";
		String fileRandomName = "";
		while (e.hasMoreElements()) {
			Object k = e.nextElement();
			FormFile obj = (FormFile) ht.get(k);
			fileSize = obj.getFileSize();
			if (fileSize > 0 && fileSize <= size) {
				String name = obj.getFileName();
				String fileExtendName = "";
				int isSymbol = name.indexOf(".");
				if (isSymbol != -1) {
					fileExtendName = name.substring(name.indexOf("."));
				} else {
					fileExtendName = name;
				}
				RandomGUID myGUID = new RandomGUID();
				String rondomName = myGUID.toString() + fileExtendName;

				InputStream input = obj.getInputStream();
				b = new byte[input.available()];
				input.read(b);
				fileContentList.add(b);
				if (fileName.equals("")) {
					fileName += name;
				} else {
					fileName += ("," + name);
				}
				if (fileRandomName.equals("")) {
					fileRandomName += rondomName;
				} else {
					fileRandomName += ("," + rondomName);
				}
			} else {
				parseFileResult.alertFlag = true;
			}
		}
		parseFileResult.fileContentList = fileContentList;
		parseFileResult.fileNames = fileName.split(",");
		parseFileResult.filePaths = fileRandomName.split(",");
		return parseFileResult;
	}

	/**
	 * The Class ParseFileResult.
	 */
	public class ParseFileResult {
		
		/** The file content list. */
		public List fileContentList = new ArrayList();

		/** The file names. */
		public String[] fileNames = new String[] {};

		/** The file paths. */
		public String[] filePaths = new String[] {};

		/** 为true则表示文件内容为空或者是文件的大小超过了上传限制. */
		public boolean alertFlag;

	}

	/**
	 * <p>
	 * 文件下载
	 * </p>.
	 *
	 * @param response the response
	 * @param fileName 下载时显示的文件名称
	 * @param fileContent 要下载的文件内容
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @author 皮亮
	 * @version 创建时间：2010-4-30 下午04:04:52
	 */
	public static void downLoadFile(HttpServletResponse response,
			String fileName, byte[] fileContent)
			throws UnsupportedEncodingException, IOException {
		response.reset();
		response.setContentType("application/x-download; charset=GBK");
		fileName = URLEncoder.encode(fileName, "UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename="
				+ fileName);
		OutputStream out = response.getOutputStream();
		if (fileContent != null) {
			try {
				out.write(fileContent);
				out.flush();
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				if (out != null) {
					out.close();
					out = null;
				}
			}

		}
	}
	
	/**
	 * <p>
	 * 	    下载附件时，如果文件名称较长，则在IE6或IE7中出现部分名称乱码的问题，主要是由于这两
	 * 		个版本的IE对header的长度有一定的限制，一旦超过这个限制则会出现此种现在，这里把文件名以
	 * 		ISO-8859-1编码，让IE进行自动转码解决此问题
	 * </p>.
	 *
	 * @param response the response
	 * @param fileName 下载时显示的文件名称
	 * @param fileContent 要下载的文件内容
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @author 皮亮
	 * @version 创建时间：2010-4-30 下午04:04:52
	 * @version 修改时间：2011-10-20 下午08:04:52
	 */
	public static void downLoadFile(HttpServletResponse response,
			String fileName, byte[] fileContent, String fileNameEncoding)
			throws UnsupportedEncodingException, IOException {
		response.reset();
		response.setContentType("application/x-download; charset=ISO-8859-1");
		response.addHeader("Content-Disposition", "attachment;filename="
				+ new String(fileName.getBytes(fileNameEncoding), "ISO-8859-1"));
		OutputStream out = response.getOutputStream();
		if (fileContent != null) {
			try {
				out.write(fileContent);
				out.flush();
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				if (out != null) {
					out.close();
					out = null;
				}
			}
		}
	}

	/**
	 * 将字节数组写入响应流.
	 *
	 * @param response the response
	 * @param byteArray the file content
	 * @throws UnsupportedEncodingException the unsupported encoding exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void writeByteArrayToResponse(HttpServletResponse response,
			byte[] byteArray) throws UnsupportedEncodingException,
			IOException {
		response.reset();
		response.setContentType("charset=UTF-8");
		OutputStream out = response.getOutputStream();
		if (byteArray != null) {
			try {
				out.write(byteArray);
				out.flush();
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				if (out != null) {
					out.close();
					out = null;
				}
			}
		}
	}

	/**
	 * <p>
	 * 从对象列表中，获得每个对象指定字段值的字符串，以逗号分开
	 * </p>.
	 *
	 * @param objectList the object list
	 * @param field the field
	 * @return the specified field value str from object list
	 * @author 皮亮
	 * @version 创建时间：2010-6-18 上午05:19:27
	 */
	public static String getSpecifiedFieldValueStrFromObjectList(
			List objectList, String field) {
		int size = objectList.size();
		StringBuffer buffer = new StringBuffer();
		Object object = null;
		for (int i = 0; i < size; i++) {
			object = objectList.get(i);
			try {
				buffer.append(",").append(BeanUtils.getProperty(object, field));
			} catch (Exception e) {
				e.printStackTrace();
				log.error("获得指定对象[" + object.getClass() + "]的属性[" + field
						+ "]值时出现了问题！！！");
				throw new RuntimeException("获得指定对象[" + object.getClass()
						+ "]的属性[" + field + "]值时出现了问题！！！", e);
			}
		}
		return buffer.toString().replaceAll("^,", "");
	}

	/**
	 * 根据选择日期计算存数/取数日期.
	 *
	 * @param date 日期，格式：yyyyMMdd
	 * @param frequency 频度
	 * @return the string
	 */
	public static String mapDataDate(String date, String frequency) {

	 
		return date;
	}

	/**
	 * 向客户端写入警告信息（通过alert的方式进行警告）.
	 *
	 * @param response the response
	 * @param message the message
	 */
	public static void writeWarnMessageToResponse(HttpServletResponse response,
			String message) {
		try {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter printWriter = response.getWriter();
			printWriter.write("<script type=\"text/javascript\">alert(\""
					+ message + "\");</script>");
			response.flushBuffer();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	/**
	 * 将指定信息写入响应流.
	 *
	 * @param response the response
	 * @param message the message
	 */
	public static void writeMessageToResponse(HttpServletResponse response,
			String message) {
		try {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter printWriter = response.getWriter();
			printWriter.write(message);
			response.flushBuffer();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("向客户端写入响应信息时出错！", e);
		}
	}
	
	/**
	 * 通过此方法可获得当前子系统从平台登录要跳转的主页面
	 * 
	 * 获取规则为先查看URL链接中是否存在参数sys_flag，存在则按此参数的值获取子系统标识
	 * 否则，则通过funconfig中配置的plat.sysflag参数得到此标识，然后再得到要跳转的登录主页面
	 *
	 * @param request the request
	 * @return the sub system main page url str
	 * 2011-8-19 上午10:07:52 皮亮
	 */
	public static String getSubSystemMainPageURLStr(HttpServletRequest request) {
		try {
			String sysFlag = getSubSystemFlag(request);
			// 通过子系统标识获取主页面的登录链接
			SystemConfigReader ssocfg = SystemConfigReader.getInstance(sysFlag);
			String linkUrl =  ssocfg.getSubLoginLink(sysFlag);
			return linkUrl;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 通过此方法可获得当前子系统的标识
	 * 
	 * 获取规则为先查看URL链接中是否存在参数sys_flag，存在则按此参数的值获取子系统标识
	 * 否则，则通过funconfig中配置的plat.sysflag参数得到此标识
	 *
	 * @param request the request
	 * @return the sub system flag
	 * 2011-8-19 上午10:11:36 皮亮
	 */
	public static String getSubSystemFlag(HttpServletRequest request) {
		HttpSession session = request.getSession();
		// 从session中获取子系统标识
		String sysFlag = (String) session.getAttribute("report_plat_sys_flag");
		// 如果在session中没有子系统的标识
		if (sysFlag == null || "".equals(sysFlag)) {
			// 首先查看request中是否存在参数sys_flag
			// 如果存在，直接取出此参数值作为子系统标识
			sysFlag = request.getParameter("sys_flag");
			if (sysFlag == null || "".equals(sysFlag)) {
				// 取得ContextPath作为子系统的标识
				sysFlag = request.getContextPath().replaceAll("^.?", "");
			} //else // 否则通过funconfig中的参数plat.sysflag取得子系统标识
				// sysFlag = FuncConfig.getProperty("plat.sysflag");
			// 将得到的子系统标识放入session中
			session.setAttribute("report_plat_sys_flag", sysFlag);
		}
		return sysFlag;
	}
	
	/**
	 * 得到从子系统再次加入平台的URL.
	 *
	 * @param request the request
	 * @return the re longin plat url
	 * 2011-8-25 上午9:58:43 皮亮
	 */
	public static String getReLonginPlatURL(HttpServletRequest request) {
		// 拼装重新登录平台系统的URL
		String reLoginPlatURL = null;
		try {
			// 取得读取配置信息的实例
			SystemConfigReader ssocfg = SystemConfigReader.getInstance(getSubSystemFlag(request));
			// 从实例中读取平台的URL
			String platLoginURL = ssocfg.getSSOUrl();
			reLoginPlatURL = platLoginURL.replaceAll("(.*://[^/]*/[^/]*/).*", "$1") + "platlogin.shtml?method=login";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reLoginPlatURL;
	}

	/**
	 * 
	 * @param request
	 * @return
	 *
	 * 2011-10-22 上午2:49:48 PILIANG
	 */
	public static String getSessionID(HttpServletRequest request) {
		String sessionId = request.getSession().getId();
		if (ServerUtil.isWebSphere()) {
			log.warn("think current envionment is websphere, please ensure it!");
			return "0000" + request.getSession().getId();
		}
		else
			return sessionId;
	}

	// /**
	// * 把请求中的参数转入到实体模型中
	// *
	// * @param model
	// * 实体模型
	// * @param request
	// * 请求
	// */
	// public static void setRequestToModel(Object model,
	// HttpServletRequest request) {
	// try {
	// RequestReflect.setRequestToModel(model, request);
	// } catch (Exception e) {
	// throw new KnowledgeRuntimeException("将请求中的参数放入模型时出现异常！", e);
	// }
	// }
	

}
