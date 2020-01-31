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
	 * ���������еĸ������������������󣬲��������еĶ�Ӧ�������ø������������
	 * 
	 * @param request
	 *            �������
	 * @return ��������
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
	 * ���������еĸ��������������������������еĶ�Ӧ�������ø�����������ԣ���ָ���Ĳ������ø���������������
	 * 
	 * @param request
	 *            �������
	 * @param randomName
	 *            ���������
	 * @param originalName
	 *            ����ԭʼ��
	 * @param extName
	 *            ������չ��
	 * @return ��������
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
	 * �������л�ø�������
	 * 
	 * @param request
	 *            �������
	 * @return ��������
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
	 * �����ļ�URI��ø�������������Դ�ļ���ȡ��
	 * 
	 * @param fileURI
	 *            �ļ�URI
	 * @return ��������
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
	 * ��ø��������Ӧ�������ĵ�����URL
	 * 
	 * @param accessoryClassName
	 *            ��������
	 * @param accessoryId
	 *            ����Ψһ��ʶ
	 * @return
	 */
	public static String getAccessoryRelativeURL(String accessoryClassName,
			String accessoryId) {
		return "/uploadFile.do?method=download&accessoryClassName="
				+ accessoryClassName + "&accessoryId=" + accessoryId;
	}

	/**
	 * ��ø�����URL
	 * 
	 * @param request
	 *            �������
	 * @param accessory
	 *            ��������
	 * @return ������URL������Ӧ�õ�������·����
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
	 * ���ļ����л����չ��
	 * 
	 * @param fileName
	 *            �ļ���
	 * @return ��չ��
	 */
	public static String getExtension(String fileName) {
		int idx = fileName.lastIndexOf(".") + 1;
		if (idx > 0 && idx < fileName.length()) {
			return fileName.substring(idx);
		}
		return "";
	}

	/**
	 * ���ļ����л���ļ�Ŀ¼��
	 * 
	 * @param fileName
	 *            �ļ���
	 * @return Ŀ¼��
	 */
	public static String getFileDir(String fileName) {
		fileName = fileName.replace('/', File.separatorChar);
		String fileDir = fileName.substring(0, fileName
				.lastIndexOf(File.separator));
		return fileDir;
	}

	/**
	 * ��ò�����չ�����ļ���
	 * 
	 * @param fileName
	 *            �ļ���
	 * @return ������չ�����ļ���
	 */
	public static String getFileNameWithoutExt(String fileName) {
		int idx = fileName.lastIndexOf(".");
		if (idx >= 0) {
			return fileName.substring(0, idx);
		}
		return fileName;
	}

	/**
	 * ����������ļ���
	 * 
	 * @param dir
	 *            Ŀ¼��
	 * @param fileName
	 *            �ļ�����������Ŀ¼����չ����
	 * @param extension
	 *            ��չ��
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
	 * ��ö��ļ�����������Ŀ¼��
	 * 
	 * @param filePath
	 *            �ļ�·��
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
	 * @return Ψһ�ļ���
	 */
	public static String getUniqueFileName() {
		return new RandomGUID().valueAfterMD5;
	}

	/**
	 * ���ָ�����͵ĸ������ϴ�Ŀ¼
	 * 
	 * @param accessoryClassName
	 *            ��������
	 * @return �����Ӧ�������ĵ�·��
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
	 * ����Ŀ¼
	 * 
	 * @param path
	 *            Ҫ������Ŀ¼
	 */
	public static void makedirs(String path) {
		File dir = new File(path);
		if (!dir.exists()) {
			if(dir.mkdirs())
			{
				log.debug("UploadUtil  [281] "+path + "�ļ��д����ɹ�!");
			}
			else
			{
				log.debug("UploadUtil  [281] " +path + "�ļ��д���ʧ��!");
			}
		}
	}
	
	/**
	 * ɾ��Ŀ¼
	 * @param delFolder
	 * @return
	 */
	public static boolean deleteFolder(File delFolder)
	{
		// Ŀ¼�Ƿ���ɾ��
		boolean hasDeleted = true;
		// �õ����ļ����µ������ļ��к��ļ�����
		File[] allFiles = delFolder.listFiles();
		if(allFiles.length > 0)
		{
			for (int i = 0; i < allFiles.length; i++) {
				// Ϊtrueʱ����
				if (hasDeleted) {
					if (allFiles[i].isDirectory()) {
						// ���Ϊ�ļ���,��ݹ����ɾ���ļ��еķ���
						log.debug("the file is a folder, will delete it .....");
						hasDeleted = deleteFolder(allFiles[i]);
					} else if (allFiles[i].isFile()) {
						log.debug("the file is not a folder, delete it ..........");
						try {
							if (!allFiles[i].delete()) {
								// ɾ��ʧ��,����false
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
					// Ϊfalse,����ѭ��
					break;
				}
			}
			if (hasDeleted) {
				// ���ļ�����Ϊ���ļ���,ɾ����
				delFolder.delete();
			} else {
				log.debug("������ļ���û��ɾ���ɹ�������");
			}
		}
		return hasDeleted;
	} 


	/**
	 * ɾ��ָ��Ŀ¼�µ�ָ���ļ�
	 * 
	 * @param dirPath
	 *            Ŀ¼��
	 * @param fileNames
	 *            Ҫɾ�����ļ��б�
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
	 * �����ϴ����ļ�
	 * 
	 * @param file
	 *            �ϴ����ļ�����
	 * @param dir
	 *            Ŀ¼
	 * @param fileName
	 *            �ļ���
	 * @param extension
	 *            ��չ��
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
	 * �����ϴ����ļ�
	 * 
	 * @param request
	 *            �����������ȡ�ø�����������Ĭ�ϵĸ�������ֵ
	 * @param file
	 *            �ϴ����ļ�����
	 * @param saveDir
	 *            �ļ�����ľ���·��
	 * @return ���ú�������Եĸ������� *
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
