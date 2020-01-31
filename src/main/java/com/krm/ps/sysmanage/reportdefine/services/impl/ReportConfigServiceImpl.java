package com.krm.ps.sysmanage.reportdefine.services.impl;

 
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.organmanage2.services.OrganService2;
import com.krm.ps.sysmanage.reportdefine.dao.ReportConfigDAO;

import java.util.ArrayList;
import java.util.List;
import com.krm.ps.sysmanage.reportdefine.services.ReportConfigService;
import com.krm.ps.sysmanage.usermanage.services.DictionaryService;
import com.krm.ps.sysmanage.usermanage.services.UserService;


import com.krm.ps.sysmanage.reportdefine.services.ReportDefineService;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportConfig;
import com.krm.ps.sysmanage.reportdefine.vo.ReportConfigFun;
import com.krm.ps.sysmanage.reportdefine.vo.ReportTarget;
import com.krm.ps.sysmanage.usermanage.vo.Dictionary;
import com.krm.ps.sysmanage.usermanage.vo.Role;
import com.krm.ps.util.Constant;
import com.krm.ps.util.FuncConfig;


public class ReportConfigServiceImpl implements ReportConfigService {
	
	private ReportConfigDAO dao;
	
	DictionaryService dictionaryService;
	ReportDefineService reportDefineService;
	OrganService2 organService2;
	UserService userService;
	
	public void setUserService(UserService userService)
	{
		this.userService = userService;
	}
	
	public void setOrganService2(OrganService2 organService2)
	{
		this.organService2 = organService2;
	}

	public DictionaryService getDictionaryService() {
		return dictionaryService;
	}
	
	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}
	
	public ReportDefineService getReportDefineService() {
		return reportDefineService;
	}
	
	public void setReportDefineService(ReportDefineService reportDefineService) {
		this.reportDefineService = reportDefineService;
	}
	
	public void setReportConfigDAO(ReportConfigDAO dao) {
		this.dao = dao;
	}
	
	
	public List getReportConfigs(Long reportId){
		return dao.getReportConfigs(reportId);
	}
	
	public List getReportConfigs(Long reportId,Long funcId){
		return dao.getReportConfigs(reportId,funcId);
	}
	
//	public List getReportConfigFun(List configs){
//		List configFun = new ArrayList();
//		if (configs.size()>0){
//			for (int i=0;i<configs.size();i++){
//				ReportConfig reportConfig = (ReportConfig)configs.get(i);
//				ReportConfigFun reportConfigFun = new ReportConfigFun();
//				reportConfigFun.setPkid(reportConfig.getPkid());
//				reportConfigFun.setReportId(reportConfig.getReportId());
//				Long funId = reportConfig.getFunId();
//				int funInt = funId.intValue();
//				if(funInt==14||funInt==17||funInt==18){
//					if(funInt==14) reportConfigFun.setFunName(FUNNAME_BZ);
//					if(funInt==17) reportConfigFun.setFunName(FUNNAME_SJSX_NEW);
//					if(funInt==18) reportConfigFun.setFunName(FUNNAME_SJSX_OLD);
//				}else{
//					Dictionary dic = (Dictionary)dictionaryService.getDictionary(PURPOSE_DIC,funInt);
//					reportConfigFun.setFunName(dic.getDicname());
//				}
//				reportConfigFun.setFunId(funId);				
//				reportConfigFun.setIdxId(reportConfig.getIdxId());
//				reportConfigFun.setDefInt(reportConfig.getDefInt());
//				
//				if (funInt == PURPOSE_BATCH||funInt == CONFIG_SJDW||funInt == CONFIG_SJJD||funInt == NEW_CONFIG_SJSX||funInt == OLD_CONFIG_SJSX||funInt == CONFIG_BZ){
//					Dictionary dict = new Dictionary();
//					int id = BATCH_DIC;
//					if (funInt == CONFIG_SJDW) id = SJDW_DIC;
//					if (funInt == CONFIG_SJJD) id = SJJD_DIC;
//					if(funInt == CONFIG_SJDW||funInt==CONFIG_SJJD || funInt==PURPOSE_BATCH)
//						dict = (Dictionary)dictionaryService.getDictionary(id,reportConfig.getDefInt().intValue());					
//					if (funInt == NEW_CONFIG_SJSX) id = NEW_SJSX_DIC;
//					if (funInt == OLD_CONFIG_SJSX) id = OLD_SJSX_DIC;
//					if(funInt == NEW_CONFIG_SJSX||funInt==OLD_CONFIG_SJSX)
//						dict = (Dictionary)dictionaryService.getDictionary(id,Integer.parseInt(reportConfig.getDefChar()));
//					if(funInt == CONFIG_BZ) 
//						reportConfigFun.setDefIntName(reportConfig.getDefChar());
//					else
//						reportConfigFun.setDefIntName(dict.getDicname());
//				}else{
//					List t = reportDefineService.getReportTarget(reportConfig.getReportId(),String.valueOf(reportConfig.getDefInt()));
//					if (t.size()>0) {
//						ReportTarget rt = (ReportTarget)t.get(0);
//						reportConfigFun.setDefIntName(rt.getTargetName());						
//					}
//				}
//				reportConfigFun.setDefChar(reportConfig.getDefChar());
//				reportConfigFun.setDescription(reportConfig.getDescription());
//				
//				configFun.add(reportConfigFun);
//			}
//		}
//		
//		return (configFun.size()>0?configFun:null);
//	}
	
	public List getReportConfigFun(List configs){
		List configFun = new ArrayList();
//		Long idxId = new Long(0);
		if (configs.size()>0){
//			for(Iterator itr = configs.iterator();itr.hasNext();){
//					ReportConfig reportConfig = (ReportConfig)itr.next();
//					if(reportConfig.getFunId().intValue()==12||reportConfig.getFunId().intValue()==13)
//						idxId = reportConfig.getIdxId();
//				}
			for (int i=0;i<configs.size();i++){
				ReportConfig reportConfig = (ReportConfig)configs.get(i);
				ReportConfigFun reportConfigFun = new ReportConfigFun();
				reportConfigFun.setPkid(reportConfig.getPkid());
				reportConfigFun.setReportId(reportConfig.getReportId());
				Long funId = reportConfig.getFunId();
				int funInt = funId.intValue();
				if(funInt==14||funInt==17||funInt==18){
					if(funInt==14) reportConfigFun.setFunName(Constant.FUNNAME_BZ);
					if(funInt==17) reportConfigFun.setFunName(Constant.FUNNAME_SJSX_NEW);
					if(funInt==18) reportConfigFun.setFunName(Constant.FUNNAME_SJSX_OLD);
				}else{
					
					Dictionary dic = (Dictionary)dictionaryService.getDictionary(Constant.PURPOSE_DIC,funInt);
					reportConfigFun.setFunName(dic.getDicname());
				}
				reportConfigFun.setFunId(funId);
				reportConfigFun.setIdxId(reportConfig.getIdxId());
				reportConfigFun.setDefInt(reportConfig.getDefInt());
				
				if (funInt == Constant.PURPOSE_BATCH||funInt == Constant.CONFIG_SJDW||funInt == Constant.CONFIG_SJJD||
						funInt == Constant.NEW_CONFIG_SJSX||funInt == Constant.OLD_CONFIG_SJSX||funInt == Constant.CONFIG_BZ||
						funInt==Constant.CONFIG_SUBAREA||funInt==Constant.CONFIG_PDF||funInt==Constant.CONFIG_EXTDATA){
					Dictionary dict = new Dictionary();
					int id = Constant.BATCH_DIC;
					if (funInt == Constant.CONFIG_SJDW) id =Constant.SJDW_DIC;
					if (funInt == Constant.CONFIG_SJJD) id = Constant.SJJD_DIC;
					if (funInt == Constant.CONFIG_PDF) id = Constant.PDF;
					if (funInt == Constant.CONFIG_EXTDATA) id = Constant.EXTDATA;
					if(funInt == Constant.CONFIG_SUBAREA) id = Constant.SUBAREA;
					if(funInt == Constant.CONFIG_SJDW||funInt==Constant.CONFIG_SJJD || funInt==Constant.PURPOSE_BATCH||funInt==Constant.CONFIG_SUBAREA||funInt==Constant.CONFIG_PDF||funInt==Constant.CONFIG_EXTDATA){
						//System.out.println(funInt);
						//System.out.println(id);
						//System.out.println(reportConfig.getDefInt().intValue());
						dict = (Dictionary)dictionaryService.getDictionary(id,reportConfig.getDefInt().intValue());	
					}
					if (funInt == Constant.NEW_CONFIG_SJSX) id = Constant.NEW_SJSX_DIC;
					if (funInt == Constant.OLD_CONFIG_SJSX) id = Constant.OLD_SJSX_DIC;
					if(funInt == Constant.NEW_CONFIG_SJSX||funInt==Constant.OLD_CONFIG_SJSX)
						dict = (Dictionary)dictionaryService.getDictionary(id,Integer.parseInt(reportConfig.getDefChar()));
					if(funInt == Constant.CONFIG_BZ) 
						reportConfigFun.setDefIntName(reportConfig.getDefChar());
					else
						reportConfigFun.setDefIntName(dict.getDicname());
				}else if(funInt == Constant.CURRENCY_CONFIG){
//					CurrencyInfo ci = currencyManagementService.queryCurrencyInfoByCode(reportConfig.getDefChar());
//					reportConfigFun.setDefIntName(ci.getFull_name()); //zh edit for currency
				}else if(funInt == Constant.CURRENCY_EXCHANGE || funInt == Constant.ITEMSETTING || funInt == Constant.CONFIG_REP_RELATIONSHIP  || funInt == Constant.SHARE_CONFIG){ // �������жϱ���Ҫ�ع��ñ�־SHARE_CONFIG
					Report r = reportDefineService.getReport(reportConfig.getDefInt());
					reportConfigFun.setDefIntName(r.getName()); //zh edit for currency
					// ��ѯ��Ӧ����reportId�ı������
					r = reportDefineService.getReport(reportConfig.getReportId());
					reportConfigFun.setReportName(r.getName());
				}else if(funInt == Constant.RISK_WARNING)
				{
					Report r = reportDefineService.getReport(reportConfig.getReportId());
					reportConfigFun.setReportName(r.getName());
					OrganInfo o = organService2.getOrganByCode(reportConfig.getDefChar());
					reportConfigFun.setDefCharName(o.getShort_name());
					Role ro = userService.getRole(reportConfig.getDefInt().toString());
					reportConfigFun.setDefIntName(ro.getName());
					reportConfigFun.setPkid(new Long(0));
				}
				else if(funInt == Integer.parseInt(FuncConfig.getProperty("rep.monitor", "99")))
				{
					Dictionary dic = (Dictionary)dictionaryService.getDictionary(reportConfig.getFunId().intValue(),reportConfig.getDefInt().intValue());
					reportConfigFun.setDefIntName(dic.getDicname());	
				}else
				{
					List t = reportDefineService.getReportTarget(reportConfig.getReportId(),String.valueOf(reportConfig.getDefInt()));
					if (t.size()>0) {
						ReportTarget rt = (ReportTarget)t.get(0);
						reportConfigFun.setDefIntName(rt.getTargetName());						
					}
				}
				reportConfigFun.setDefChar(reportConfig.getDefChar());
				reportConfigFun.setDescription(reportConfig.getDescription());
				
//				if(funInt==14||funInt==17||funInt==18){
//					if(reportConfig.getIdxId().equals(idxId))
//						configFun.add(reportConfigFun);
//				}else{
					configFun.add(reportConfigFun);
//				}
			}
		}
		
		return (configFun.size()>0?configFun:null);
	}
	
	public ReportConfig getReportConfig(Long configId){
		Object o = dao.getObject(ReportConfig.class,configId);
		if(null!=o){
			return (ReportConfig)o;
		}
		return null;
	}
	
	public ReportConfig getReportConfig(Long reportId,int funId,Long idxId){
		return dao.getReportConfig(reportId,funId,idxId);
	}
	
	public void removeReportConfig(Long pkid){
		dao.removeReportConfig(pkid);
	}
	
	public void saveReportConfig(ReportConfig config,int isUpdate){		
		//保存定义
		dao.saveConfig(config,isUpdate);
	}
	
	public void saveReportConfig(ReportConfig config,ReportConfig config1,int isUpdate){		
		//保存定义
		dao.saveConfig(config,config1,isUpdate);
	}
	
	public void saveReportConfig(ReportConfig config,ReportConfig config1,ReportConfig config2,ReportConfig config3,int isUpdate){		
		//保存定义
		dao.saveConfig(config,config1,config2,config3,isUpdate);
	}
	
	public List getReportConfigs(Long reportId,Long funcId,Long defInt){
		return dao.getReportConfigs( reportId, funcId, defInt);
	}
	
	public List getReportConfigs(Long pkId,Long reportId,Long funcId,Long defInt){
		return dao.getReportConfigs( pkId, reportId, funcId, defInt);
	}
	
	public List getReportConfigsByFunIdAndOrgIdsAndDate(String funId, String orgIds)
	{
		return dao.getReportConfigsByFunIdAndOrgIdsAndDate(funId, orgIds);
	}
	
	public void copyConfigs(Long newRepid, Long oldRepid){
		dao.copyConfigs(newRepid, oldRepid);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.reportdefine.services.ReportConfigService#getReportConfigsByFunc(java.lang.Long)
	 */
	public List getReportConfigsByFunc(Long funcId)
	{
		return dao.getReportConfigsByFunc(funcId);
	}

	/* (non-Javadoc)
	 * @see com.krm.slsint.reportdefine.services.ReportConfigService#removeReportConfig(java.lang.Long, java.lang.Long)
	 */
	public void removeReportConfig(Long repId, Long funId)
	{
		dao.removeReportConfig(repId, funId);
	}
	public String defChar(Long reportid,Long funid) {
		List<?> list=dao.getdefChar(reportid,funid);
	    ReportConfig rc=(ReportConfig)list.get(0);
		return rc.getDefChar();
		
	}
public List<ReportConfig> getdefCharByTem(Long reportid, Long funid) {
	// TODO Auto-generated method stub
	return dao.getdefCharByTem(reportid, funid);
}

public List getRepConfigList() {
	// TODO Auto-generated method stub
	return dao.getRepConfigList();
}

@Override
public void saveConfig(ReportConfig config) {
	dao.saveConfig(config);
	
}

@Override
public ReportConfig getReportConfigList(Long pkid) {
	
	return (ReportConfig)dao.getReportConfig( pkid );
}

@Override
public void setReportConfig(ReportConfig reportConfig) {
	dao.setReportConfig(reportConfig);
} 

}
