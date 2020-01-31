package com.krm.slsint.fileRepositoryManage.services.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.krm.ps.framework.common.web.util.BeanUtilServlet;
import com.krm.ps.sysmanage.reportdefine.services.ReportFormatService;
import com.krm.ps.sysmanage.reportdefine.vo.ReportFormat;
import com.krm.ps.sysmanage.usermanage.dao.UserDAO;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.util.FuncConfig;
import com.krm.slsint.directorEnquiries.dao.FileShareDataDAO;
import com.krm.slsint.directorEnquiries.vo.FileShareData;
import com.krm.slsint.fileRepositoryManage.dao.FileRepositoryDAO;
import com.krm.slsint.fileRepositoryManage.services.FileRepositoryService;
import com.krm.slsint.fileRepositoryManage.vo.FileRepositoryRecord;
import com.krm.slsint.fileRepositoryManage.web.form.FileRepositoryCfgForm;

public class FileRepositoryServiceImpl implements FileRepositoryService {

	public FileRepositoryDAO fileRepositoryDAO;

	public UserDAO userDAO;

	public void setFileRepositoryDAO(FileRepositoryDAO fileRepositoryDAO) {
		this.fileRepositoryDAO = fileRepositoryDAO;
	}

	public FileRepositoryDAO getFileRepositoryDAO() {
		return fileRepositoryDAO;
	}

	public FileShareDataDAO fileShareDataDAO;

	public void setFileShareDataDAO(FileShareDataDAO fileShareDataDAO) {
		this.fileShareDataDAO = fileShareDataDAO;
	}

	//���ά������������ӵķ���   by 2012/9/5
		public void saveFileDataforjianguan(List saveList){
			//�������ϴ����ļ�����
			fileRepositoryDAO.batchSaveVO(saveList);
			if (FuncConfig.isOpenFun("report.module.docsharemanage")) {
				//�ڶ���Ƶ���ϴ���ͬ��Excelģ���ʱ��FileShareDataֻ��Ҫ����һ�ݼ���
				FileRepositoryRecord fileData = (FileRepositoryRecord) saveList.get(0);
				FileShareData fileShareData = new FileShareData();
				fileShareData.setMpkid(fileData.getPkid());
				fileShareData.setStatus(new Long(1));
				fileShareData.setUser_id(fileData.getUserId());
				Long order = fileShareDataDAO.getFileShareDataMaxOrder();
				fileShareData.setShow_order(new Long(order.longValue() + 1));
				fileRepositoryDAO.saveObject(fileShareData);
			}
			// ָ��ģ�鹦���Ƿ���
			if (FuncConfig.isOpenFun("report.module.jianguanrep")) {
				//����ģ���j����
				for(int i=0;i<saveList.size();i++){
					FileRepositoryRecord fileData = (FileRepositoryRecord) saveList.get(i);
					ReportFormat reportFormat = new ReportFormat();
					reportFormat.setReportId(new Long(999999));
					reportFormat
							.setReportFormat("<?xml version=\"1.0\" encoding=\"GB2312\"?><Workbook><ControlVersion ControlVersion=\"1.0.2.6\"/><XReportWorkbook><ProtectStructure>False</ProtectStructure><Calculation>AutomaticCalculation</Calculation><ActiveSheet>0</ActiveSheet></XReportWorkbook><DependencyTables/><Items MaxItemID=\"0\" MaxStringSequence=\"0\" MaxNumberSequence=\"0\"/><DataItems/><Styles><Style ID=\"Default\"><Alignment Horizontal=\"Automatic\" Vertical=\"VCenter\"/><Borders><Border Position=\"Top\" LineStyle=\"5\" Color=\"0\" Weight=\"0\"/><Border Position=\"Bottom\" LineStyle=\"5\" Color=\"0\" Weight=\"0\"/><Border Position=\"Left\" LineStyle=\"5\" Color=\"0\" Weight=\"0\"/><Border Position=\"Right\" LineStyle=\"5\" Color=\"0\" Weight=\"0\"/></Borders><Font FontName=\"����\" Size=\"-12\" Color=\"0\" Bold=\"400\" Italic=\"False\" Underline=\"False\"/><Interior Color=\"16777215\" Pattern=\"None\"/><NumberFormat Format=\"General\"/><Protection Protected=\"0\"/></Style><Style ID=\"1\"><Alignment Horizontal=\"Left\" Vertical=\"VCenter\"/><Font FontName=\"����\" Size=\"-12\" Color=\"4278190080\" Bold=\"400\" Italic=\"False\" Underline=\"False\"/><Interior Color=\"4278190080\" Pattern=\"None\"/></Style></Styles><PrintInfo PrintInfo=\"\"/><ComboBoxItemsValue Value=\"\"/><WorkbookOptions><Version>1.0</Version></WorkbookOptions><Worksheets><Worksheet Name=\"Sheet1\"><WorksheetOptions/><AuditFormulas Count=\"0\"/><Table ExpandedColumnCount=\"3\" ExpandedRowCount=\"3\" FixedColumnCount=\"1\" FixedRowCount=\"1\" DefaultColumnWidth=\"84\" DefaultRowHeight=\"18\" IsPrintBeginRow=\"0\" dmOrientation=\"0\"><Row><Cell StyleID=\"1\" DataType=\"0\" IsMoney=\"False\"><Data xml:space=\"preserve\" Type=\"String\"></Data></Cell><Cell StyleID=\"1\" DataType=\"0\" IsMoney=\"False\"><Data xml:space=\"preserve\" Type=\"String\"></Data></Cell><Cell StyleID=\"1\" DataType=\"0\" IsMoney=\"False\"><Data xml:space=\"preserve\" Type=\"String\"></Data></Cell></Row><Row><Cell StyleID=\"1\" DataType=\"0\" IsMoney=\"False\"><Data xml:space=\"preserve\" Type=\"String\"></Data></Cell><Cell StyleID=\"1\" DataType=\"0\" IsMoney=\"False\"><Data xml:space=\"preserve\" Type=\"String\"></Data></Cell><Cell StyleID=\"1\" DataType=\"0\" IsMoney=\"False\"><Data xml:space=\"preserve\" Type=\"String\"></Data></Cell></Row><Row><Cell StyleID=\"1\" DataType=\"0\" IsMoney=\"False\"><Data xml:space=\"preserve\" Type=\"String\"></Data></Cell><Cell StyleID=\"1\" DataType=\"0\" IsMoney=\"False\"><Data xml:space=\"preserve\" Type=\"String\"></Data></Cell><Cell StyleID=\"1\" DataType=\"0\" IsMoney=\"False\"><Data xml:space=\"preserve\" Type=\"String\"></Data></Cell></Row></Table></Worksheet></Worksheets></Workbook>");
					reportFormat.setBeginDate("00000000");
					reportFormat.setCreateDate("00000000");
					reportFormat.setEditDate("00000000");
					reportFormat.setEndDate("00000000");
					reportFormat.setFrequency("-");
			
					// ȥ��pkid���ѱ���ID��Ϊ���ļ��ֿ��б�����ļ�ID
					reportFormat.setReportId(fileData.getPkid());
					// ���汨��ģ��
					fileRepositoryDAO.saveObject(reportFormat);
				}
			}
		}
	public void saveFileData(FileRepositoryRecord fileData) {
		fileRepositoryDAO.saveObject(fileData);
		if (FuncConfig.isOpenFun("report.module.docsharemanage")) {
			FileShareData fileShareData = new FileShareData();
			fileShareData.setMpkid(fileData.getPkid());
			fileShareData.setStatus(new Long(1));
			fileShareData.setUser_id(fileData.getUserId());
			Long order = fileShareDataDAO.getFileShareDataMaxOrder();
			fileShareData.setShow_order(new Long(order.longValue() + 1));
			fileRepositoryDAO.saveObject(fileShareData);
		}
		// ָ��ģ�鹦���Ƿ���
		if (FuncConfig.isOpenFun("report.module.jianguanrep")) {
			// �õ�����ģ��Ĳ���service
			ReportFormatService reportFormatService = (ReportFormatService) BeanUtilServlet
					.getBean("reportFormatService");
			// ReportFormat reportFormat = reportFormatService.getReportFormat(
			// new Long(999999), "00000000", "-1");
			// ���ģ�岻���ڣ���ֱ�ӽ�bһ���µ�ģ��
			// if (reportFormat == null) {
			ReportFormat reportFormat = new ReportFormat();
			reportFormat.setReportId(new Long(999999));
			reportFormat
					.setReportFormat("<?xml version=\"1.0\" encoding=\"GB2312\"?><Workbook><ControlVersion ControlVersion=\"1.0.2.6\"/><XReportWorkbook><ProtectStructure>False</ProtectStructure><Calculation>AutomaticCalculation</Calculation><ActiveSheet>0</ActiveSheet></XReportWorkbook><DependencyTables/><Items MaxItemID=\"0\" MaxStringSequence=\"0\" MaxNumberSequence=\"0\"/><DataItems/><Styles><Style ID=\"Default\"><Alignment Horizontal=\"Automatic\" Vertical=\"VCenter\"/><Borders><Border Position=\"Top\" LineStyle=\"5\" Color=\"0\" Weight=\"0\"/><Border Position=\"Bottom\" LineStyle=\"5\" Color=\"0\" Weight=\"0\"/><Border Position=\"Left\" LineStyle=\"5\" Color=\"0\" Weight=\"0\"/><Border Position=\"Right\" LineStyle=\"5\" Color=\"0\" Weight=\"0\"/></Borders><Font FontName=\"����\" Size=\"-12\" Color=\"0\" Bold=\"400\" Italic=\"False\" Underline=\"False\"/><Interior Color=\"16777215\" Pattern=\"None\"/><NumberFormat Format=\"General\"/><Protection Protected=\"0\"/></Style><Style ID=\"1\"><Alignment Horizontal=\"Left\" Vertical=\"VCenter\"/><Font FontName=\"����\" Size=\"-12\" Color=\"4278190080\" Bold=\"400\" Italic=\"False\" Underline=\"False\"/><Interior Color=\"4278190080\" Pattern=\"None\"/></Style></Styles><PrintInfo PrintInfo=\"\"/><ComboBoxItemsValue Value=\"\"/><WorkbookOptions><Version>1.0</Version></WorkbookOptions><Worksheets><Worksheet Name=\"Sheet1\"><WorksheetOptions/><AuditFormulas Count=\"0\"/><Table ExpandedColumnCount=\"3\" ExpandedRowCount=\"3\" FixedColumnCount=\"1\" FixedRowCount=\"1\" DefaultColumnWidth=\"84\" DefaultRowHeight=\"18\" IsPrintBeginRow=\"0\" dmOrientation=\"0\"><Row><Cell StyleID=\"1\" DataType=\"0\" IsMoney=\"False\"><Data xml:space=\"preserve\" Type=\"String\"></Data></Cell><Cell StyleID=\"1\" DataType=\"0\" IsMoney=\"False\"><Data xml:space=\"preserve\" Type=\"String\"></Data></Cell><Cell StyleID=\"1\" DataType=\"0\" IsMoney=\"False\"><Data xml:space=\"preserve\" Type=\"String\"></Data></Cell></Row><Row><Cell StyleID=\"1\" DataType=\"0\" IsMoney=\"False\"><Data xml:space=\"preserve\" Type=\"String\"></Data></Cell><Cell StyleID=\"1\" DataType=\"0\" IsMoney=\"False\"><Data xml:space=\"preserve\" Type=\"String\"></Data></Cell><Cell StyleID=\"1\" DataType=\"0\" IsMoney=\"False\"><Data xml:space=\"preserve\" Type=\"String\"></Data></Cell></Row><Row><Cell StyleID=\"1\" DataType=\"0\" IsMoney=\"False\"><Data xml:space=\"preserve\" Type=\"String\"></Data></Cell><Cell StyleID=\"1\" DataType=\"0\" IsMoney=\"False\"><Data xml:space=\"preserve\" Type=\"String\"></Data></Cell><Cell StyleID=\"1\" DataType=\"0\" IsMoney=\"False\"><Data xml:space=\"preserve\" Type=\"String\"></Data></Cell></Row></Table></Worksheet></Worksheets></Workbook>");
			reportFormat.setBeginDate("00000000");
			reportFormat.setCreateDate("00000000");
			reportFormat.setEditDate("00000000");
			reportFormat.setEndDate("00000000");
			reportFormat.setFrequency("-");
			// }
			// // �½�һ���µı���ģ�壬���ݲ����Ѳ���ģ������
			// ReportFormat newRepFormat = new ReportFormat();
			// try {
			// BeanUtils.copyProperties(newRepFormat, reportFormat);
			// } catch (IllegalAccessException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// } catch (InvocationTargetException e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
			// ȥ��pkid���ѱ���ID��Ϊ���ļ��ֿ��б�����ļ�ID
			reportFormat.setReportId(fileData.getPkid());
			// ���汨��ģ��
			fileRepositoryDAO.saveObject(reportFormat);
		}
	}

	public Long getFileRepositoryMaxOrderNo(String fun_id) {
		return fileRepositoryDAO.getFileRepositoryMaxOrderNo(fun_id);
	}

	public List getFileList(Long user_id, String fun_id) {
		return fileRepositoryDAO.getFileList(user_id, fun_id);
	}

	public List getFileList(Long user_id, String funId, String editTime,
			String fileName, String organId, Long status) {
		//��Db �������޸ĵ�sql���  2012-04-01  ����
		List list = fileRepositoryDAO.getFileList(user_id, funId, editTime,
				fileName, organId, status);
		return list;
	}

	public List getFuns(String funId) {
		return fileRepositoryDAO.getfuns(funId);
	}

	public FileRepositoryRecord getRecordById(Long id) {
		return (FileRepositoryRecord) fileRepositoryDAO.getObject(
				FileRepositoryRecord.class, id);
	}

	public void delRecordById(Long id) {
		fileRepositoryDAO.removeObject(FileRepositoryRecord.class, id);
	}

	public Long getShowOrder(String funId) {
		return fileRepositoryDAO.getShowOrder(funId);
	}

	public Long getNextShowOrder(String funId) {
		return fileRepositoryDAO.getNextShowOrder(funId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.krm.slsint.fileRepositoryManage.services.FileRepositoryService#
	 * getRepostioryRecord(java.lang.String, java.lang.String)
	 */
	public List getRepostioryRecord(String funId, String filePath) {
		return fileRepositoryDAO.getRepostioryRecord(funId, filePath);
	}

	public List getRepostioryRecord(String funId) {
		return fileRepositoryDAO.getRepostioryRecord(funId);
	}

	// ����ļ�����ϸ��Ϣ�õ�����ļ�
	public FileRepositoryRecord getRecord(String funId, String prefix,
			String fileName, String postfix, String path) {
		return fileRepositoryDAO.getRecord(funId, prefix, fileName, postfix,
				path);
	}

	public List getfileRepository(String funId, String organ_id) {
		return fileRepositoryDAO.getfileRepository(funId, organ_id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.krm.slsint.fileRepositoryManage.services.FileRepositoryService#
	 * getRiskRecord(java.lang.String)
	 */
	public FileRepositoryRecord getRiskRecord(String pkid) {
		// TODO Auto-generated method stub
		return (FileRepositoryRecord) fileRepositoryDAO.getObject(
				FileRepositoryRecord.class, pkid);
	}

	public List getFileRecord(String organCode, String fun_id, String target) {
		if (target.equals("0")) {
			return fileRepositoryDAO.getFileRecord(organCode, fun_id, "sls");
		} else {
			return fileRepositoryDAO.getFileRecord(organCode, fun_id, "yjh");
		}
	}

	public List getMapFile(String funId, String postfix) {
		return fileRepositoryDAO.getMapFile(funId, postfix);

	}

	public List transferVoToForm(List list) {
		List result = new ArrayList();
		for (Iterator it = list.iterator(); it.hasNext();) {
			FileRepositoryRecord fr = (FileRepositoryRecord) it.next();
			FileRepositoryCfgForm ff = new FileRepositoryCfgForm();
			try {
				BeanUtils.populate(ff, BeanUtils.describe(fr));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			User user = (User) userDAO.getObject(User.class, ff.getUserId());
			ff.setUserName(user.getName());
			result.add(ff);
		}

		return result;
	}

	public boolean getAdminId(User user) {
		List rList = userDAO.getAdminUser();

		boolean b = false;
		for (Iterator it = rList.iterator(); it.hasNext();) {
			User u = (User) it.next();
			if (u.getPkid().longValue() == user.getPkid().longValue()) {
				b = true;
				break;
			}
		}

		return b;
	}

	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	/**
	 * @see com.krm.slsint.fileRepositoryManage.services.FileRepositoryService#getFileListByNamePrefix(java.lang.String,
	 *      java.lang.String)
	 */
	public List getFileListByNamePrefix(String funId, String namePrefix) {
		return fileRepositoryDAO.getFileListByNamePrefix(funId, namePrefix);
	}

}
