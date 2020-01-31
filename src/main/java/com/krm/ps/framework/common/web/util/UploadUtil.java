package com.krm.ps.framework.common.web.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.upload.FormFile;
import org.springframework.context.i18n.LocaleContextHolder;

import com.krm.ps.framework.common.vo.Accessory;
import com.krm.ps.util.Constants;
import com.krm.ps.util.ConvertUtil;
import com.krm.ps.util.RandomGUID;
import com.krm.ps.util.StringUtil;

public class UploadUtil {
	private static Log log = LogFactory.getLog(UploadUtil.class);

	/**
	 * 根据请求中的附件类名创建附件对象，并用请求中的对应参数设置附件对象的属性
	 * 
	 * @param request
	 *            请求对象
	 * @return 附件对象
	 */
	public static Accessory getAccessory(HttpServletRequest request) {
		Accessory accessory = null;
		try {
			accessory = (Accessory) Class.forName(
					getAccessoryClassName(request)).newInstance();
			ConvertUtil.copyProperties(accessory, request, "getParameter");
		} catch (Exception e) {
			if (log.isDebugEnabled()) {
				log.debug(e.getMessage());
			}
		}
		return accessory;
	}

	/**
	 * 根据请求中的附件类名创建附件对象，用请求中的对应参数设置附件对象的属性，用指定的参数设置附件对象的相关属性
	 * 
	 * @param request
	 *            请求对象
	 * @param randomName
	 *            附件随机名
	 * @param originalName
	 *            附件原始名
	 * @param extName
	 *            附件扩展名
	 * @return 附件对象
	 */
	public static Accessory getAccessory(HttpServletRequest request,
			String randomName, String originalName, String extName) {
		Accessory accessory = getAccessory(request);
		if (accessory != null) {
			if (accessory.getDispOrder() == null) {
				accessory.setDispOrder(new Integer(0));
			}
			if (accessory.getTitle() == null) {
				accessory.setTitle("accessory");
			}
			accessory.setOriginalName(originalName);
			accessory.setRandomName(randomName);
			accessory.setExtName(extName);

			if (accessory.getStatus() == null) {
				accessory.setStatus(new Integer(9));
			}
			Date now = new Date();
			accessory.setCreateTime(now);
			accessory.setEditTime(now);
		}
		return accessory;
	}

	/**
	 * 从请求中获得附件类名
	 * 
	 * @param request
	 *            请求对象
	 * @return 附件类名
	 */
	public static String getAccessoryClassName(HttpServletRequest request) {
		String className = (String) request
				.getAttribute(Constants.ATTR_ACCESSORY_CLASSNAME_KEY);
		if (className == null || className.length() == 0) {
			className = request
					.getParameter(Constants.ATTR_ACCESSORY_CLASSNAME_KEY);
		}
		if (className == null || className.length() == 0) {
			className = (String) request.getSession().getAttribute(
					Constants.ATTR_ACCESSORY_CLASSNAME_KEY);
		}
		return className;
	}

	/**
	 * 根据文件URI获得附件类名（从资源文件中取）
	 * 
	 * @param fileURI
	 *            文件URI
	 * @return 附件类名
	 */
	public static String getAccessoryClassName(String fileURI) {
		Locale locale = LocaleContextHolder.getLocale();
		try {
			String fileDir = getFileDir(fileURI);
			return ResourceBundle.getBundle(Constants.BUNDLE_KEY, locale)
					.getString(
							fileDir.substring(fileDir
									.lastIndexOf(File.separator) + 1));
		} catch (MissingResourceException e) {
			return Constants.DEFAULT_ACCESSORY_CLASS_NAME;
		}
	}

	/**
	 * 获得附件相对于应用上下文的下载URL
	 * 
	 * @param accessoryClassName
	 *            附件类名
	 * @param accessoryId
	 *            附件唯一标识
	 * @return
	 */
	public static String getAccessoryRelativeURL(String accessoryClassName,
			String accessoryId) {
		return "/uploadFile.do?method=download&accessoryClassName="
				+ accessoryClassName + "&accessoryId=" + accessoryId;
	}

	/**
	 * 获得附件的URL
	 * 
	 * @param request
	 *            请求对象
	 * @param accessory
	 *            附件对象
	 * @return 附件的URL（包括应用的上下文路径）
	 * @throws IOException
	 */
	public static String getAccessoryURL(HttpServletRequest request,
			Accessory accessory) throws IOException {
		String dir = getUploadDir(accessory.getClass().getName());
		String fullFileName = getFullFileName(dir, accessory.getRandomName(),
				accessory.getExtName());
		return request.getContextPath() + fullFileName;
	}

	/**
	 * 从文件名中获得扩展名
	 * 
	 * @param fileName
	 *            文件名
	 * @return 扩展名
	 */
	public static String getExtension(String fileName) {
		int idx = fileName.lastIndexOf(".") + 1;
		if (idx > 0 && idx < fileName.length()) {
			return fileName.substring(idx);
		}
		return "";
	}

	/**
	 * 从文件名中获得文件目录名
	 * 
	 * @param fileName
	 *            文件名
	 * @return 目录名
	 */
	public static String getFileDir(String fileName) {
		fileName = fileName.replace('/', File.separatorChar);
		String fileDir = fileName.substring(0, fileName
				.lastIndexOf(File.separator));
		return fileDir;
	}

	/**
	 * 获得不带扩展名的文件名
	 * 
	 * @param fileName
	 *            文件名
	 * @return 不带扩展名的文件名
	 */
	public static String getFileNameWithoutExt(String fileName) {
		int idx = fileName.lastIndexOf(".");
		if (idx >= 0) {
			return fileName.substring(0, idx);
		}
		return fileName;
	}

	/**
	 * 获得完整的文件名
	 * 
	 * @param dir
	 *            目录名
	 * @param fileName
	 *            文件名（不包括目录和扩展名）
	 * @param extension
	 *            扩展名
	 * @return
	 */
	public static String getFullFileName(String dir, String fileName,
			String extension) {
		String fullFileName = fileName;
		if (dir != null && dir.trim().length() > 0) {
			fullFileName = dir + "/" + fullFileName;
		}
		if (extension != null && extension.trim().length() > 0) {
			fullFileName += "." + extension;
		}
		return fullFileName;
	}

	/**
	 * 获得短文件名（不包括目录）
	 * 
	 * @param filePath
	 *            文件路径
	 * @return
	 */
	public static String getShortFileName(String filePath) {
		int idx = filePath.lastIndexOf(File.separator) + 1;
		if (idx > 0 && idx < filePath.length()) {
			return filePath.substring(idx);
		}
		return filePath;
	}

	/**
	 * @return 唯一文件名
	 */
	public static String getUniqueFileName() {
		return new RandomGUID().valueAfterMD5;
	}

	/**
	 * 获得指定类型的附件的上传目录
	 * 
	 * @param accessoryClassName
	 *            附件类名
	 * @return 相对于应用上下文的路径
	 */
	public static String getUploadDir(String accessoryClassName) {
		String uploadDir = StringUtil.getResourceString(accessoryClassName,
				null);
		if (uploadDir == null) {
			return Constants.DEFAULT_ACCESSORY_UPLOAD_DIR;
		}
		return Constants.UPLOAD_ROOT_DIR + "/" + uploadDir;
	}

	/**
	 * 创建目录
	 * 
	 * @param path
	 *            要创建的目录
	 */
	public static void makedirs(String path) {
		File dir = new File(path);
		if (!dir.exists()) {
			if(dir.mkdirs())
			{
				log.debug("UploadUtil  [281] "+path + "文件夹创建成功!");
			}
			else
			{
				log.debug("UploadUtil  [281] " +path + "文件夹创建失败!");
			}
		}
	}
	
	/**
	 * 删除目录
	 * @param delFolder
	 * @return
	 */
	public static boolean deleteFolder(File delFolder)
	{
		// 目录是否已删除
		boolean hasDeleted = true;
		// 得到该文件夹下的所有文件夹和文件数组
		File[] allFiles = delFolder.listFiles();
		if(allFiles.length > 0)
		{
			for (int i = 0; i < allFiles.length; i++) {
				// 为true时操作
				if (hasDeleted) {
					if (allFiles[i].isDirectory()) {
						// 如果为文件夹,则递归调用删除文件夹的方法
						log.debug("the file is a folder, will delete it .....");
						hasDeleted = deleteFolder(allFiles[i]);
					} else if (allFiles[i].isFile()) {
						log.debug("the file is not a folder, delete it ..........");
						try {
							if (!allFiles[i].delete()) {
								// 删除失败,返回false
								hasDeleted = false;
								log.debug("fale in deleting the file.....");
							}
						} catch (Exception e) {
							log.debug("error occured when delete the file....");
							hasDeleted = false;
							e.printStackTrace();
						}
					}
				} else {
					// 为false,跳出循环
					break;
				}
			}
			if (hasDeleted) {
				// 该文件夹已为空文件夹,删除它
				delFolder.delete();
			} else {
				log.debug("最初的文件夹没有删除成功！！！");
			}
		}
		return hasDeleted;
	} 


	/**
	 * 删除指定目录下的指定文件
	 * 
	 * @param dirPath
	 *            目录名
	 * @param fileNames
	 *            要删除的文件列表
	 */
	public static void removeFiles(String dirPath, final List fileNames) {
		if (fileNames != null) {
			FilenameFilter filter = new FilenameFilter() {
				public boolean accept(File dir, String name) {
					for (Iterator itr = fileNames.iterator(); itr.hasNext();) {
						if (getFileNameWithoutExt(name).equalsIgnoreCase(
								(String) itr.next())) {
							return true;
						}
					}
					return false;
				}
			};

			File dir = new File(dirPath);
			if (dir.exists()) {
				File[] dirContents = dir.listFiles(filter);
				for (int i = 0; i < dirContents.length; i++) {
					// System.out.println(dirContents[i]);
					if (dirContents[i].isFile()) {
						dirContents[i].delete();
					}
				}
			}
		}
	}

	/**
	 * 保存上传的文件
	 * 
	 * @param file
	 *            上传的文件对象
	 * @param dir
	 *            目录
	 * @param fileName
	 *            文件名
	 * @param extension
	 *            扩展名
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void saveFile(FormFile file, String dir, String fileName,
			String extension) throws FileNotFoundException, IOException {
		makedirs(dir);
		InputStream in = file.getInputStream();
		OutputStream out = new FileOutputStream(getFullFileName(dir, fileName,
				extension));
		ConvertUtil.convertInStreamToOutStream(in, out,
				Constants.DEFAULT_BUFFER_SIZE);
		out.close();
		in.close();
		file.destroy();
	}

	/**
	 * 保存上传的文件
	 * 
	 * @param request
	 *            请求对象，用于取得附件类名，及默认的附件属性值
	 * @param file
	 *            上传的文件对象
	 * @param saveDir
	 *            文件保存的绝对路径
	 * @return 设置好相关属性的附件对象 *
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static Accessory saveFile(HttpServletRequest request, FormFile file,
			String saveDir) throws FileNotFoundException, IOException {
		String uniqueFileName = getUniqueFileName();
		String originalFileName = file.getFileName();
		String extension = getExtension(originalFileName);

		saveFile(file, saveDir, uniqueFileName, extension);

		return getAccessory(request, uniqueFileName, originalFileName,
				extension);
	}
}
