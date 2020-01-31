package com.krm.ps.model.reportdefine.services.impl;

import java.util.ArrayList;
import java.util.List;
import com.krm.ps.model.reportdefine.dao.ReportConfigDAO;
import com.krm.ps.model.reportdefine.services.CurrencyManagementService;
import com.krm.ps.model.reportdefine.services.DictionaryService;
import com.krm.ps.model.reportdefine.services.ReportConfigService;
import com.krm.ps.model.reportdefine.services.ReportDefineService;
import com.krm.ps.model.vo.CurrencyInfo;
import com.krm.ps.model.vo.ReportConfigFun;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.organmanage2.services.OrganService2;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportConfig;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;
import com.krm.ps.sysmanage.usermanage.services.UserService;
import com.krm.ps.sysmanage.usermanage.vo.Dictionary;
import com.krm.ps.sysmanage.usermanage.vo.Role;
import com.krm.ps.util.Constant;
import com.krm.ps.util.FuncConfig;

public class ReportConfigServiceImpl implements ReportConfigService {

	private ReportConfigDAO rdreportConfigDAO;

	DictionaryService dictionaryService;
	ReportDefineService rdreportDefineService;
	CurrencyManagementService currencyManagementService;
	OrganService2 organService2;
	UserService userService;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setOrganService2(OrganService2 organService2) {
		this.organService2 = organService2;
	}

	public void setCurrencyManagementService(
			CurrencyManagementService currencyManagementService) {
		this.currencyManagementService = currencyManagementService;
	}

	public DictionaryService getDictionaryService() {
		return dictionaryService;
	}

	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	public ReportDefineService getRdreportDefineService() {
		return rdreportDefineService;
	}

	public void setRdreportDefineService(
			ReportDefineService rdreportDefineService) {
		this.rdreportDefineService = rdreportDefineService;
	}

	public ReportConfigDAO getRdreportConfigDAO() {
		return rdreportConfigDAO;
	}

	public void setRdreportConfigDAO(ReportConfigDAO rdreportConfigDAO) {
		this.rdreportConfigDAO = rdreportConfigDAO;
	}

	public List getReportConfigFun(List configs) {
		List configFun = new ArrayList();
		if (configs.size() > 0) {
			for (int i = 0; i < configs.size(); i++) {
				ReportConfig reportConfig = (ReportConfig) configs.get(i);
				ReportConfigFun reportConfigFun = new ReportConfigFun();
				reportConfigFun.setPkid(reportConfig.getPkid());
				reportConfigFun.setReportId(reportConfig.getReportId());
				Long funId = reportConfig.getFunId();
				int funInt = funId.intValue();
				if (funInt == 14 || funInt == 17 || funInt == 18) {
					if (funInt == 14)
						reportConfigFun.setFunName(Constant.FUNNAME_BZ);
					if (funInt == 17)
						reportConfigFun.setFunName(Constant.FUNNAME_SJSX_NEW);
					if (funInt == 18)
						reportConfigFun.setFunName(Constant.FUNNAME_SJSX_OLD);
				} else {

					Dictionary dic = (Dictionary) dictionaryService
							.getDictionary(Constant.PURPOSE_DIC, funInt);
					reportConfigFun.setFunName(dic.getDicname());
				}
				reportConfigFun.setFunId(funId);
				reportConfigFun.setIdxId(reportConfig.getIdxId());
				reportConfigFun.setDefInt(reportConfig.getDefInt());

				if (funInt == Constant.PURPOSE_BATCH
						|| funInt == Constant.CONFIG_SJDW
						|| funInt == Constant.CONFIG_SJJD
						|| funInt == Constant.NEW_CONFIG_SJSX
						|| funInt == Constant.OLD_CONFIG_SJSX
						|| funInt == Constant.CONFIG_BZ
						|| funInt == Constant.CONFIG_SUBAREA
						|| funInt == Constant.CONFIG_PDF
						|| funInt == Constant.CONFIG_EXTDATA) {
					Dictionary dict = new Dictionary();
					int id = Constant.BATCH_DIC;
					if (funInt == Constant.CONFIG_SJDW)
						id = Constant.SJDW_DIC;
					if (funInt == Constant.CONFIG_SJJD)
						id = Constant.SJJD_DIC;
					if (funInt == Constant.CONFIG_PDF)
						id = Constant.PDF;
					if (funInt == Constant.CONFIG_EXTDATA)
						id = Constant.EXTDATA;
					if (funInt == Constant.CONFIG_SUBAREA)
						id = Constant.SUBAREA;
					if (funInt == Constant.CONFIG_SJDW
							|| funInt == Constant.CONFIG_SJJD
							|| funInt == Constant.PURPOSE_BATCH
							|| funInt == Constant.CONFIG_SUBAREA
							|| funInt == Constant.CONFIG_PDF
							|| funInt == Constant.CONFIG_EXTDATA) {
						// System.out.println(funInt);
						// System.out.println(id);
						// System.out.println(reportConfig.getDefInt().intValue());
						dict = (Dictionary) dictionaryService.getDictionary(id,
								reportConfig.getDefInt().intValue());
					}
					if (funInt == Constant.NEW_CONFIG_SJSX)
						id = Constant.NEW_SJSX_DIC;
					if (funInt == Constant.OLD_CONFIG_SJSX)
						id = Constant.OLD_SJSX_DIC;
					if (funInt == Constant.NEW_CONFIG_SJSX
							|| funInt == Constant.OLD_CONFIG_SJSX)
						dict = (Dictionary) dictionaryService.getDictionary(id,
								Integer.parseInt(reportConfig.getDefChar()));
					if (funInt == Constant.CONFIG_BZ)
						reportConfigFun
								.setDefIntName(reportConfig.getDefChar());
					else
						reportConfigFun.setDefIntName(dict.getDicname());
				} else if (funInt == Constant.CURRENCY_CONFIG) {
					CurrencyInfo ci = currencyManagementService
							.queryCurrencyInfoByCode(reportConfig.getDefChar());
					reportConfigFun.setDefIntName(ci.getFull_name());

				} else if (funInt == Constant.CURRENCY_EXCHANGE
						|| funInt == Constant.ITEMSETTING
						|| funInt == Constant.CONFIG_REP_RELATIONSHIP
						|| funInt == Constant.SHARE_CONFIG) {
					Report r = rdreportDefineService.getReport(reportConfig
							.getDefInt());
					reportConfigFun.setDefIntName(r.getName());

					r = rdreportDefineService.getReport(reportConfig
							.getReportId());
					reportConfigFun.setReportName(r.getName());
				} else if (funInt == Constant.RISK_WARNING) {
					Report r = rdreportDefineService.getReport(reportConfig
							.getReportId());
					reportConfigFun.setReportName(r.getName());
					OrganInfo o = organService2.getOrganByCode(reportConfig
							.getDefChar());
					reportConfigFun.setDefCharName(o.getShort_name());
					Role ro = userService.getRole(reportConfig.getDefInt()
							.toString());
					reportConfigFun.setDefIntName(ro.getName());
					reportConfigFun.setPkid(new Long(0));
				} else if (funInt == Integer.parseInt(FuncConfig.getProperty(
						"rep.monitor", "99"))) {
					Dictionary dic = (Dictionary) dictionaryService
							.getDictionary(reportConfig.getFunId().intValue(),
									reportConfig.getDefInt().intValue());
					reportConfigFun.setDefIntName(dic.getDicname());
				} else {
					List t = rdreportDefineService.getReportTarget(
							reportConfig.getReportId(),
							String.valueOf(reportConfig.getDefInt()));
					if (t.size() > 0) {
						ReportTarget rt = (ReportTarget) t.get(0);
						reportConfigFun.setDefIntName(rt.getTargetName());
					}
				}
				reportConfigFun.setDefChar(reportConfig.getDefChar());
				reportConfigFun.setDescription(reportConfig.getDescription());

				configFun.add(reportConfigFun);
			}
		}

		return (configFun.size() > 0 ? configFun : null);
	}

	public ReportConfig getReportConfig(Long configId) {
		Object o = rdreportConfigDAO.getObject(ReportConfig.class, configId);
		if (null != o) {
			return (ReportConfig) o;
		}
		return null;
	}

	public ReportConfig getReportConfig(Long reportId, int funId, Long idxId) {
		return rdreportConfigDAO.getReportConfig(reportId, funId, idxId);
	}

	public void removeReportConfig(Long pkid) {
		rdreportConfigDAO.removeReportConfig(pkid);
	}

	public void saveReportConfig(ReportConfig config, int isUpdate) {
		rdreportConfigDAO.saveConfig(config, isUpdate);
	}

	public void saveReportConfig(ReportConfig config, ReportConfig config1,
			int isUpdate) {
		rdreportConfigDAO.saveConfig(config, config1, isUpdate);
	}

	public void saveReportConfig(ReportConfig config, ReportConfig config1,
			ReportConfig config2, ReportConfig config3, int isUpdate) {
		// ���涨��
		rdreportConfigDAO.saveConfig(config, config1, config2, config3,
				isUpdate);
	}

	public List getReportConfigs(Long reportId, Long funcId, Long defInt) {
		return rdreportConfigDAO.getReportConfigs(reportId, funcId, defInt);
	}

	public List getReportConfigs(Long pkId, Long reportId, Long funcId,
			Long defInt) {
		return rdreportConfigDAO.getReportConfigs(pkId, reportId, funcId,
				defInt);
	}

	public List getReportConfigsByFunIdAndOrgIdsAndDate(String funId,
			String orgIds) {
		return rdreportConfigDAO.getReportConfigsByFunIdAndOrgIdsAndDate(funId,
				orgIds);
	}

	public void copyConfigs(Long newRepid, Long oldRepid) {
		rdreportConfigDAO.copyConfigs(newRepid, oldRepid);
	}

	public List getReportConfigsByFunc(Long funcId) {
		return rdreportConfigDAO.getReportConfigsByFunc(funcId);
	}

	public void removeReportConfig(Long repId, Long funId) {
		rdreportConfigDAO.removeReportConfig(repId, funId);
	}

	public String defChar(Long reportid, Long funid) {
		List<?> list = rdreportConfigDAO.getdefChar(reportid, funid);
		ReportConfig rc = (ReportConfig) list.get(0);
		return rc.getDefChar();

	}

	public List<ReportConfig> getdefCharByTem(Long reportid, Long funid) {
		return rdreportConfigDAO.getdefCharByTem(reportid, funid);
	}

	public List getRepConfigList() {
		return rdreportConfigDAO.getRepConfigList();
	}

	@Override
	public void saveConfig(ReportConfig config) {
		rdreportConfigDAO.saveConfig(config);

	}

	@Override
	public ReportConfig getReportConfigList(Long pkid) {

		return (ReportConfig) rdreportConfigDAO.getReportConfig(pkid);
	}

	@Override
	public void setReportConfig(ReportConfig reportConfig) {
		rdreportConfigDAO.setReportConfig(reportConfig);
	}

	@Override
	public List getReportConfigs(Long reportId) {
		return rdreportConfigDAO.getReportConfigs(reportId);
	}

	@Override
	public List getReportConfigs(Long reportId, Long funcId) {
		// TODO Auto-generated method stub
		return rdreportConfigDAO.getReportConfigs(reportId, funcId);
	}

}
