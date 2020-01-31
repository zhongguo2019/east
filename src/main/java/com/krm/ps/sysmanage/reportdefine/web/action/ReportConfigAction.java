package com.krm.ps.sysmanage.reportdefine.web.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.ibm.db2.jcc.b.l;
import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.sysmanage.organmanage.services.OrganService;
import com.krm.ps.sysmanage.organmanage.vo.OrganInfo;
import com.krm.ps.sysmanage.organmanage2.services.OrganService2;
import com.krm.ps.sysmanage.reportdefine.services.ReportConfigService;
import com.krm.ps.sysmanage.reportdefine.services.ReportDefineService;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportConfig;
import com.krm.ps.sysmanage.reportdefine.vo.ReportConfigFun;
import com.krm.ps.sysmanage.reportdefine.web.form.ReportConfigForm;
import com.krm.ps.sysmanage.usermanage.services.DictionaryService;
import com.krm.ps.sysmanage.usermanage.services.UserService;
import com.krm.ps.sysmanage.usermanage.vo.Dictionary;
import com.krm.ps.sysmanage.usermanage.vo.User;
import com.krm.ps.util.Constant;
import com.krm.ps.util.FuncConfig;
import com.krm.ps.util.ConvertUtil;
 
/**
*
* @struts.action name="reportConfigForm" path="/reportConfigAction" scope="request" 
*  validate="false" parameter="method" input="edit"
*  
* @struts.action-forward name="edit" path="/reportdefine/reportConfigForm.jsp"
* @struts.action-forward name="list" path="/reportdefine/reportConfigList.jsp"
* @struts.action-forward name="editrisk" path="/reportdefine/editrisk.jsp"
* @struts.action-forward name="showorgan" path="/reportdefine/organ.jsp"
*/

public class ReportConfigAction extends BaseAction {
	private static String REPORT_ID = "reportId";
	private static String FUNC_ID = "funcId";
	private static String CONFIG_ID = "configId";
	private static String CONFIG_LIST = "configList";
	private static String INDEX_ID = "indexId";
	private static int FUN_DIC = 1203;
	private static int BATCH_DIC = 1202;
	private static int SJDW_DIC = 1204;
	private static int SJJD_DIC = 1205;
	private static int OLD_SJSX_DIC = 1208;
	private static int NEW_SJSX_DIC = 1207;
	private static int SUBAREA = 1230;
	private static int BUILD_PDF = 1240;
	private static String BTYPE_LIST = "btypeList";
	/**
	 * piliang add for reading 2010-3-15 下午07:36:31<br>
	 * 结转源列
	 */
	private static final int JZS_DIC = 21;
	/**
	 * piliang add for reading 2010-3-15 下午07:38:31<br>
	 * 结转目标列
	 */
	private static final int JZD_DIC = 22;
	/**
	 * piliang add for reading 2010-3-15 下午08:11:51<br>
	 * 上报批次
	 */
	private static int CONFIG_SBPC = 11;
	private static int CONFIG_SBL = 12;
	private static int CONFIG_BSL = 13;
	private static int CONFIG_SJDW = 15;
	private static int CONFIG_SJJD = 16;
	private static int CONFIG_BZ = 14;
	private static int CONFIG_NEW_SJSX = 17;
	private static int CONFIG_OLD_SJSX = 18;
	/**
	 * ydw add for reading 2012-1-9 下午08:11:51<br>
	 * 是否需要自动导入Excel
	 */
	private static int CONFIG_REP_MONITOR=Integer.parseInt(FuncConfig.getProperty("rep.monitor", "99"));
	/**
	 * piliang add for reading 2010-3-15 下午07:50:20<br>
	 * 值为41：定义报表币种的功能，每张报表只能有唯一的币种
	 */
	public static int CURRENCY_CONFIG = 41;
	public static int CURRENCY_EXCHANGE = 31;
	public static int CONFIG_SUBAREA = 33;
	public static int PDF = 32;
	public static int ITEMSETTING = 34;
	public static int RMB_CODE = 10;
	public static int CONFIG_EXTDATA = 35;
	public static int EXTDATA = 1250;
	/**
	 * 总分校验值为50： 定义用于将汇总机构数据与被汇总机构数据进行比对功能
	 */
	public static int CONFIG_MERGE = 50;
	public static int CONFIG_REP_RELATIONSHIP = 20;
	public static int CONFIG_RISKWARNING = 44;
	
	public ActionForward enter(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'enter' method");
		}
		if (null != getReportId(request)) {
			request.getSession().removeAttribute(REPORT_ID);
			request.getSession().setAttribute(REPORT_ID, getReportId(request));
		}
		
		if (null != getFuncId(request)) {
			request.getSession().removeAttribute(FUNC_ID);
			request.getSession().setAttribute(FUNC_ID, getFuncId(request));
		}
		return list(mapping, form, request, response);
	}
	
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		if (log.isDebugEnabled()) {
			log.info("Entering 'list' method");
		}
		return list(mapping,form,request,response,getSessionReportId(request),getSessionFuncId(request));
	}
	
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,Long reportId,Long funcId) {
		if (log.isDebugEnabled()) {
			log.info("Entering 'list' method");
		}
		ReportConfigService rs = (ReportConfigService)this.getBean("reportConfigService");
		List configs = rs.getReportConfigs(reportId,funcId);
		int csize = configs.size();
		configs = rs.getReportConfigFun(configs);
//		if(configs!=null){
//		for(Iterator itr = configs.iterator();itr.hasNext();){
//			ReportConfigFun reportConfigFun = (ReportConfigFun)itr.next();
//			System.out.println(reportConfigFun.getFunId());
//			System.out.println(reportConfigFun.getFunName());
//		}
//		}
		
		if(funcId.intValue() == CURRENCY_CONFIG){
			request.setAttribute("configSize","1");
			if(csize == 0){
				ReportConfigFun reportConfigFun = new ReportConfigFun();
				reportConfigFun.setDefIntName("人民币");
				reportConfigFun.setIdxId(new Long(1));
				reportConfigFun.setDefInt(new Long(RMB_CODE));
				reportConfigFun.setFunId(new Long(CURRENCY_CONFIG));
				reportConfigFun.setFunName("报表币种");
				reportConfigFun.setPkid(new Long(-1));
				reportConfigFun.setDescription("");
				reportConfigFun.setDefChar("");
				reportConfigFun.setReportId(reportId);
				configs = new ArrayList();
				configs.add(reportConfigFun);
				request.setAttribute("tof","1");
			}
		}else{
			request.setAttribute("configSize","0");
		}
		
		request.setAttribute(CONFIG_LIST,configs);
		return mapping.findForward("list");
	}
	
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'edit' method");
		}
		ReportConfigForm reportConfigForm = (ReportConfigForm)form;
		ReportConfig item = new ReportConfig();
		ReportConfigService rs = (ReportConfigService)this.getBean("reportConfigService");
		ReportDefineService rds = (ReportDefineService) getBean("reportDefineService");
		OrganService2 rs2 = (OrganService2)getBean("organService2");
		Long id = getConfigId(request);
		String flag = "0";
		if(null!=id){
			item = rs.getReportConfig(id);
		}else{
			flag = "1";
		}
		User user = getUser(request);
		Long funcId ;
		if (reportConfigForm.getFunId()==null){
			funcId = getSessionFuncId(request);
		}else{
			funcId = reportConfigForm.getFunId();
		}
		
		HttpSession session = request.getSession();
		String date = (String)session.getAttribute("logindate");
		date = date.replaceAll("-","");
		
		reportConfigForm.setFunId(funcId);
		request.setAttribute("flag",flag);
		DictionaryService ds = (DictionaryService)this.getBean("dictionaryService");
		List temp = ds.getDics(FUN_DIC);
		List funList = new ArrayList();
		for (int i=0;i<temp.size();i++){
			Dictionary dic = (Dictionary)temp.get(i);
			if (dic.getDicid().intValue()== funcId.intValue()){
				funList.add(dic);
			}
		}
		request.setAttribute("funIdList",funList);
		
		if(funcId.intValue() == 44)
		{
			Long reportId = getReportId(request)==null?getSessionReportId(request):getReportId(request);
			List configs = rs.getReportConfigs(reportId, funcId);
			String organs = "";
			String organNames = "";
			String roles = "";
			if(configs.size() > 0)
			{
				for(Iterator itr = configs.iterator(); itr.hasNext();)
				{
					ReportConfig rc = (ReportConfig)itr.next();
					OrganInfo o = rs2.getOrganByCode(rc.getDefChar());
					if(organs.length() == 0 && roles.length() == 0)
					{
						organs = rc.getDefChar();
						roles = rc.getDefInt().toString();
						organNames = o.getShort_name();
					}
					else
					{
						organs = organs.concat("," + rc.getDefChar());
						roles = roles.concat("," + rc.getDefInt().toString());
						organNames = organNames.concat("," + o.getShort_name());
					}
				}
			}
//			List organLevelList = ds.getDics( BatchCheckUtil.ORGAN_LEVEL_ID );
//			request.setAttribute("organLevel", organLevelList);
			request.setAttribute("organs", organs);
			request.setAttribute("roles", roles);
			request.setAttribute("organNames", organNames);
			Report r = rds.getReport(reportId);
			request.setAttribute("reportName", r.getName());
			UserService us = (UserService)getBean("userService");
			List roleList = us.getRolesAll();
			request.setAttribute("roleList", roleList);
			request.setAttribute("reportId", r.getPkid());
			request.setAttribute("funcId", funcId);
			return mapping.findForward("editrisk");
		}
		
//		设置用途
		if (funcId.intValue()!=CURRENCY_CONFIG){
			ConvertUtil.copyProperties(reportConfigForm,item,true);
		}
		
		//用途定义
		List defList = null;
		List newDataAttribute = null;
		List oldDataAttribute = null;
		Dictionary ci = null;
		if(funcId.intValue() == CONFIG_MERGE){
			
			Long repId = getReportId(request)==null?getSessionReportId(request):getReportId(request);
			defList = rds.getReportTargets(repId);
		}else if (funcId.intValue()==CONFIG_SBPC){
			//批次
			defList = ds.getDics(BATCH_DIC);
		}else if (funcId.intValue()==CONFIG_SJDW){
			//数据单位
			defList = ds.getDics(SJDW_DIC);
		}else if (funcId.intValue()==CONFIG_SJJD){
			//精度
			defList = ds.getDics(SJJD_DIC);
		}else if(funcId.intValue()==CONFIG_SUBAREA){
			defList = ds.getDics(SUBAREA);
		}else if(funcId.intValue()==PDF){
			defList = ds.getDics(BUILD_PDF);
		}else if(funcId.intValue()==CONFIG_EXTDATA){
			//二期数据采集  add by lxk 20080524
			defList = ds.getDics(EXTDATA);
		}else if(funcId.intValue() == ITEMSETTING) {
			//科目联动配置功能
			
			Long repId = getReportId(request)==null?getSessionReportId(request):getReportId(request);
			Report report = rds.getReport(repId);
			if(user.getIsAdmin().intValue() == 2) {
				defList = rds.getReportsByType(report.getReportType(), date, user.getPkid());
			}else {
				defList = rds.getReportByAuthor(date, user.getPkid(), report.getReportType());
			}
		}else if(funcId.intValue() == CURRENCY_CONFIG){ 
			//jone edit for currency
//			CurrencyManagementService cms = (CurrencyManagementService) getBean("currencyManagementService");
//			List tempList = cms.queryCurrencyInfos();
			defList = new ArrayList();
//			for(int i = 0; i <tempList.size(); i++){
//				CurrencyInfo dic = (CurrencyInfo)tempList.get(i);
//				ci = new Dictionary();
//				ci.setDicid(new Long(dic.getCode()));
//				ci.setDicname(dic.getFull_name());
//				defList.add(ci);
//			}
			
			//ReportConfigForm rcf = (ReportConfigForm)form;
			reportConfigForm.setPkid(id);
			
		}else if(funcId.intValue() == CURRENCY_EXCHANGE){ 
			//jone edit for currency
			
			
			
			List tempList = rds.getReports(date,user.getPkid());
			defList = new ArrayList();
			for(int i = 0; i <tempList.size(); i++){
				Report r = (Report)tempList.get(i);
				ci = new Dictionary();
				ci.setDicid(r.getPkid());
				ci.setDicname(r.getName());
				defList.add(ci);
			}
		}else if(funcId.intValue() == CONFIG_REP_RELATIONSHIP) {
			//System.out.println("CONFIG_REP_RELATIONSHIP");
			Long repId = getReportId(request)==null?getSessionReportId(request):getReportId(request);
			System.out.println(repId);
			List repConfigList = rs.getReportConfigs(repId, new Long(CONFIG_REP_RELATIONSHIP));
			String repsId = "";
			if(repConfigList.size() != 0) {
				for(Iterator itr = repConfigList.iterator(); itr.hasNext();) {
					ReportConfig rep = (ReportConfig)itr.next();
					repsId += rep.getDefInt().toString() + ",";
				}
				request.setAttribute("repId", repsId.substring(0, repsId.length() - 1));
			}
			//System.out.println(repsId);
		}else if (funcId.intValue()==CONFIG_REP_MONITOR){//by ydw ����Ƿ���Ҫ�Զ�����Excel����
			//是否需要自动导入Excel
			defList = ds.getDics(funcId.intValue());
		}else{
			
			Long repId = getReportId(request)==null?getSessionReportId(request):getReportId(request);
			defList = rds.getReportTargets(repId);
			if(funcId.intValue()==CONFIG_SBL||funcId.intValue()==CONFIG_BSL){
				List repConfig = rs.getReportConfigs(repId);
				for(Iterator itr = repConfig.iterator();itr.hasNext();){
					ReportConfig rep = (ReportConfig)itr.next();
					if(rep.getFunId().intValue()==CONFIG_BZ) reportConfigForm.setDefChar(rep.getDefChar());
					if(rep.getFunId().intValue()==CONFIG_NEW_SJSX) reportConfigForm.setNewDataAttribute(rep.getDefChar());
					if(rep.getFunId().intValue()==CONFIG_OLD_SJSX) reportConfigForm.setOldDataAttribute(rep.getDefChar());
				}
			}
			//增加导出列数据属性(新旧接口)
			//新接口数据属性
			newDataAttribute = ds.getDics(NEW_SJSX_DIC);
			//旧接口数据属性
			oldDataAttribute = ds.getDics(OLD_SJSX_DIC);
		}
		request.setAttribute("defList",defList);
		request.setAttribute("newDataAttribute",newDataAttribute);
		request.setAttribute("oldDataAttribute",oldDataAttribute);
		
		//设置索引字段
		request.getSession().setAttribute(INDEX_ID,request.getParameter(INDEX_ID));
		
		//设置目标列,源列
		if (funcId.intValue()==JZS_DIC||funcId.intValue()==JZD_DIC){
			if ("0".equals(flag)){
				ReportConfig rc = rs.getReportConfig(item.getReportId(),item.getFunId().intValue()==JZS_DIC?JZD_DIC:JZS_DIC,item.getIdxId());
				reportConfigForm.setDefInt1(rc.getDefInt());
				reportConfigForm.setPkid1(rc.getPkid());
			}
		} else if (funcId.intValue() == Constant.SHARE_CONFIG){ // ����Ҫ�ع�������
			List rcfList = rs.getReportConfigFun(rs.getReportConfigs(getReportId(request), funcId));
			String repIds = "";
			String repNames = "";
			ReportConfigFun rcf = null;
			int size = rcfList == null ? 0 : rcfList.size();
			for (int i = 0; i < size; i++){
				rcf = (ReportConfigFun)rcfList.get(i);
				repIds = ("".equals(repIds) ? "" : (repIds + ",")) + rcf.getDefInt();
				repNames = ("".equals(repNames) ? "" : (repNames + ",")) + rcf.getDefIntName();
			}
			request.setAttribute("repId", repIds);
			request.setAttribute("reportName", repNames);
		}
		request.setAttribute("funcId", funcId);
		return toEdit(mapping,reportConfigForm,request,response,funcId);
	}
	
	public ActionForward toEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,Long FuncId) {
		if (log.isDebugEnabled()) {
			log.info("Entering 'toEdit' method");
		}
		setToken(request);
		return mapping.findForward("edit");		
	}
	
	public ActionForward del(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'del' method");
		}
		ReportConfigService rs = (ReportConfigService)this.getBean("reportConfigService");		
		Long id = getConfigId(request);
		//删除目标列
		if (getSessionFuncId(request).intValue()==JZS_DIC||getSessionFuncId(request).intValue()==JZD_DIC){
			ReportConfig rc = new ReportConfig(); 
			rc = rs.getReportConfig(id);
			ReportConfig rc1 = rs.getReportConfig(rc.getReportId(),rc.getFunId().intValue()==JZS_DIC?JZD_DIC:JZS_DIC,rc.getIdxId());
			rs.removeReportConfig(rc1.getPkid());
		}
		//make over by zhaoPC _20070427
		if (getSessionFuncId(request).intValue()==CONFIG_SBL||getSessionFuncId(request).intValue()==CONFIG_BSL){
			ReportConfig rc = new ReportConfig();
			rc = rs.getReportConfig(id);
			if (rc.getFunId().intValue()==CONFIG_BZ) {ReportConfig rc1 = rs.getReportConfig(rc.getReportId(),CONFIG_BZ,rc.getIdxId());rs.removeReportConfig(rc1.getPkid());}
			if (rc.getFunId().intValue()==CONFIG_NEW_SJSX) {ReportConfig rc2 = rs.getReportConfig(rc.getReportId(),CONFIG_NEW_SJSX,rc.getIdxId());rs.removeReportConfig(rc2.getPkid());}
			if (rc.getFunId().intValue()==CONFIG_OLD_SJSX) {ReportConfig rc3 = rs.getReportConfig(rc.getReportId(),CONFIG_OLD_SJSX,rc.getIdxId());rs.removeReportConfig(rc3.getPkid());}
			if (rc.getFunId().intValue()==CONFIG_SBL||rc.getFunId().intValue()==CONFIG_BSL){
				ReportConfig rc1 = rs.getReportConfig(rc.getReportId(),CONFIG_BZ,rc.getIdxId());
				if(rc1!=null) rs.removeReportConfig(rc1.getPkid());
				ReportConfig rc2 = rs.getReportConfig(rc.getReportId(),CONFIG_NEW_SJSX,rc.getIdxId());
				if(rc2!=null) rs.removeReportConfig(rc2.getPkid());
				ReportConfig rc3 = rs.getReportConfig(rc.getReportId(),CONFIG_OLD_SJSX,rc.getIdxId());
				if(rc3!=null) rs.removeReportConfig(rc3.getPkid());
			}
		}
		if(getSessionFuncId(request).intValue()==CONFIG_RISKWARNING)
		{
			Long repId = getSessionReportId(request);
			Long funcId = getSessionFuncId(request);
			rs.removeReportConfig(repId, funcId);
		}
		rs.removeReportConfig(id);
		return list(mapping,form,request,response);
	}
	
	
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'save' method");
		}
		ReportConfigForm configForm = (ReportConfigForm)form;
		if(!tokenValidatePass(request)){
			return list(mapping,form,request,response);
		}
		int isUpdate = 0;
		Long funcId ;
		if (configForm.getFunId()==null){
			funcId = getSessionFuncId(request);
		}else{
			funcId = configForm.getFunId();
		}
		Long repId = getSessionReportId(request);
		ReportConfigService rs = (ReportConfigService)this.getBean("reportConfigService");
		ReportConfig config = new ReportConfig();
		ConvertUtil.copyProperties(config, configForm, true);
		configForm.setReportId(repId);
		config.setReportId(repId);
		
		if(configForm.getPkid()!=null){
			isUpdate = 1;
			//设置索引字段
			config.setIdxId(new Long((String)request.getSession().getAttribute(INDEX_ID)));
		}
		//数据检查
		boolean flag =false;
		if (funcId.intValue()==CONFIG_SBPC||funcId.intValue()==CONFIG_SJDW||funcId.intValue()==CONFIG_SJJD){
			if (isUpdate==0){if (rs.getReportConfigs(repId,funcId).size()>0) flag = true;}			
			}else if(funcId.intValue()==CONFIG_SBL||funcId.intValue()==CONFIG_BSL){
				if (dataCheck(rs,configForm)) flag = true; 
			}else if(funcId.intValue()==JZS_DIC||funcId.intValue()==JZD_DIC){
				if (dataExist(rs,configForm)) flag = true;
		}else if(funcId.intValue()==CONFIG_MERGE){
				if(rs.getReportConfigs(null, repId, funcId, config.getDefInt()).size() > 0){
					flag = true;
				}
		}
		
		if (flag) return list(mapping,form,request,response);
		
		if (funcId.intValue()==JZS_DIC||funcId.intValue()==JZD_DIC){
			ReportConfig config1 = new ReportConfig();
			if (isUpdate == 1){
				ReportConfig rc = rs.getReportConfig(config.getReportId(),config.getFunId().intValue()==JZS_DIC?JZD_DIC:JZS_DIC,config.getIdxId());
				config1.setPkid(rc.getPkid());
				config1.setReportId(rc.getReportId());
				config1.setFunId(rc.getFunId());
				config1.setIdxId(rc.getIdxId());
				config1.setDefInt(configForm.getDefInt1());
			}else{
				config1.setReportId(config.getReportId());
				config1.setFunId(config.getFunId().intValue()==JZS_DIC?new Long(JZD_DIC):new Long(JZS_DIC));				
				config1.setDefInt(configForm.getDefInt1());
			}
			rs.saveReportConfig(config,config1,isUpdate);
		}else{
//			make over by zhaoPC _20070427
			if(funcId.intValue()==CONFIG_SBL||funcId.intValue()==CONFIG_BSL){
				ReportConfig config1 = new ReportConfig();
				ReportConfig config2 = new ReportConfig();
				ReportConfig config3 = new ReportConfig();
				if (isUpdate == 1){
					ReportConfig rc = rs.getReportConfig(config.getReportId(),config.getFunId().intValue()==CONFIG_SBL?CONFIG_SBL:CONFIG_BSL,config.getIdxId());
					config1 = rs.getReportConfig(config.getReportId(),CONFIG_BZ,config.getIdxId());
					config1.setReportId(rc.getReportId());
					config1.setFunId(new Long(CONFIG_BZ));
					config1.setIdxId(rc.getIdxId());
					config1.setDefInt(new Long(0));
					if(configForm.getDefChar() == null||configForm.getDefChar().equals("")) config1.setDefChar("CNY0001");
					else config1.setDefChar(configForm.getDefChar());
					config2 = rs.getReportConfig(config.getReportId(),CONFIG_NEW_SJSX,config.getIdxId());
					config2.setReportId(rc.getReportId());
					config2.setFunId(new Long(CONFIG_NEW_SJSX));
					config2.setIdxId(rc.getIdxId());
					config2.setDefInt(new Long(0));
					if(configForm.getNewDataAttribute()==null||configForm.getNewDataAttribute().equals("")) config2.setDefChar("1");
					else config2.setDefChar(configForm.getNewDataAttribute());
					config3 = rs.getReportConfig(config.getReportId(),CONFIG_OLD_SJSX,config.getIdxId());
					config3.setReportId(rc.getReportId());
					config3.setFunId(new Long(CONFIG_OLD_SJSX));
					config3.setIdxId(rc.getIdxId());
					config3.setDefInt(new Long(0));
					if(configForm.getOldDataAttribute()==null||configForm.getOldDataAttribute().equals("")) config3.setDefChar("1");
					else config3.setDefChar(configForm.getOldDataAttribute());
					
				}else{
					config1.setReportId(config.getReportId());
					config1.setFunId(new Long(14));
					config1.setDefInt(new Long(0));
					if(configForm.getDefChar() == null||configForm.getDefChar().equals(""))
						config1.setDefChar("CNY0001");
					else config1.setDefChar(configForm.getDefChar());
					config2.setReportId(config.getReportId());
					config2.setFunId(new Long(17));	
					config2.setDefInt(new Long(0));
					if(configForm.getNewDataAttribute()==null||configForm.getNewDataAttribute().equals("")) 
						config2.setDefChar("1");
					else config2.setDefChar(configForm.getNewDataAttribute());
					config3.setReportId(config.getReportId());
					config3.setFunId(new Long(18));	
					config3.setDefInt(new Long(0));
					if(configForm.getOldDataAttribute()==null||configForm.getOldDataAttribute().equals("")) 
						config3.setDefChar("1");
					else config3.setDefChar(configForm.getOldDataAttribute());
				}
				rs.saveReportConfig(config,config1,config2,config3,isUpdate);
			}else if(funcId.intValue()==CURRENCY_CONFIG){
				//currency by zhouhao
				config.setDefChar(config.getDefInt().toString());
				config.setDefInt(null);
				if(configForm.getPkid().equals(new Long(-1))){
					if(configForm.getDefInt().equals(new Long(RMB_CODE))){
						
					}else{
						rs.saveReportConfig(config,0);
					}
				}else{
					if(configForm.getDefInt().equals(new Long(RMB_CODE))){
						rs.removeReportConfig(configForm.getPkid());
					}else{
						rs.saveReportConfig(config,1);
					}
				}
			}else if(funcId.intValue()==CONFIG_SUBAREA){
				DictionaryService ds = (DictionaryService)this.getBean("dictionaryService");
				config.setDefInt(ds.getDictionary(SUBAREA, configForm.getDefChar()).getDicid());
				config.setDefChar(configForm.getDefChar());
				rs.saveReportConfig(config, isUpdate);
			}else if(funcId.intValue() == CONFIG_REP_RELATIONSHIP || funcId.intValue() == Constant.SHARE_CONFIG) {
				//System.out.println(configForm.getRepId());
				List list = null;
				String repsId = configForm.getRepId();
				if(repsId != null || repsId.length() > 0) {
					String [] id = repsId.split(",");
					if (funcId.intValue() == CONFIG_REP_RELATIONSHIP || (funcId.intValue() == Constant.SHARE_CONFIG && isUpdate == 1))
						rs.removeReportConfig(repId, funcId);
					int isUpdateRC = 0;
					ReportConfig rc = null;
					for(int i = 0; i < id.length; i++) {
						isUpdateRC = 0;
						rc = new ReportConfig();
						// 目前共用配置只允许一张报表关联另一张报表只有一条数据
						// 这里判断如果有的话就更新数据
						if (funcId.intValue() == Constant.SHARE_CONFIG && isUpdate == 0){ // 共用配置增加
							list = rs.getReportConfigs(repId, funcId, new Long(id[i]));
							if (list != null && list.size() > 0 ){ // 有配置数据
								rc = (ReportConfig)list.get(0);
								isUpdateRC = 1;
							} 
						}
						rc.setReportId(repId);
						rc.setFunId(funcId);
						rc.setDefInt(new Long(id[i]));
						if (funcId.intValue() == Constant.SHARE_CONFIG){ // 如果是报表共用要素配置功能，则设置对应属性
							rc.setDefChar(configForm.getDefChar());
							rc.setDescription(configForm.getDescription());
						}
						rs.saveReportConfig(rc, isUpdateRC);
					}
				}
			}else {	
				rs.saveReportConfig(config,isUpdate);
			}
		}
		return list(mapping,form,request,response);
	}
	
	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception{
		if (log.isDebugEnabled()) {
			log.info("Entering 'cancel' method");
		}
		return list(mapping,form,request,response);
	}
	
	private Long getReportId(HttpServletRequest request){
		String id = request.getParameter(REPORT_ID);
		if(null!=id){
			return new Long(id);
		}
		return null;
	}
	
	private Long getFuncId(HttpServletRequest request){
		String id = request.getParameter(FUNC_ID);
		if(null!=id){
			return new Long(id);
		}
		return null;
	}
	
	private Long getSessionReportId(HttpServletRequest request)throws Exception{
		Object id = request.getSession().getAttribute(REPORT_ID);
		if(null!=id){
			return (Long)id;
		}
		throw new Exception("sccion过期");
	}
	
	private Long getSessionFuncId(HttpServletRequest request)throws Exception{
		Object id = request.getSession().getAttribute(FUNC_ID);
		if(null!=id){
			return (Long)id;
		}
		throw new Exception("sccion过期");
	}
	
	private Long getConfigId(HttpServletRequest request){
		String id = request.getParameter(CONFIG_ID);
		if(null!=id){
			return new Long(id);
		}else{
			if((Long)request.getAttribute(CONFIG_ID) != null){
				return (Long)request.getAttribute(CONFIG_ID); 
			}
		}
		return null;
	}
	
	private boolean dataCheck(ReportConfigService rs,ReportConfigForm configForm){
		boolean flag = false;
		
		if (rs.getReportConfigs(configForm.getReportId(),configForm.getFunId(),configForm.getDefInt()).size()>0){
			if (rs.getReportConfigs(configForm.getPkid(),configForm.getReportId(),configForm.getFunId(),configForm.getDefInt()).size()==0){
				flag = true;
			}
		}		
		return flag;
	}
	
	private boolean dataExist(ReportConfigService rs,ReportConfigForm configForm){
		boolean flag = false;
		Long funcId = configForm.getFunId();
		Long funcId1 ;
		if (funcId.intValue()==JZS_DIC){
			funcId1 = new Long(JZD_DIC);
		}else{
			funcId1 = new Long(JZS_DIC);
		}		
		if (rs.getReportConfigs(configForm.getReportId(),funcId,configForm.getDefInt()).size()>0&&
				rs.getReportConfigs(configForm.getReportId(),funcId1,configForm.getDefInt1()).size()>0){
			
			if (rs.getReportConfigs(configForm.getPkid(),
					configForm.getReportId(), funcId, configForm.getDefInt())
					.size() == 0
					&& rs.getReportConfigs(configForm.getPkid1(),
							configForm.getReportId(), funcId1,
							configForm.getDefInt1()).size() > 0)
				return true;
			if (rs.getReportConfigs(configForm.getPkid(),
					configForm.getReportId(), funcId, configForm.getDefInt())
					.size() > 0
					&& rs.getReportConfigs(configForm.getPkid1(),
							configForm.getReportId(), funcId1,
							configForm.getDefInt1()).size() == 0)
				return true;
			if (rs.getReportConfigs(configForm.getPkid(),
					configForm.getReportId(), funcId, configForm.getDefInt())
					.size() == 0
					&& rs.getReportConfigs(configForm.getPkid1(),
							configForm.getReportId(), funcId1,
							configForm.getDefInt1()).size() == 0)
				return true;
		}
		
		return flag;
	}
	
	public ActionForward showOrgan(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'showOrgan' method");
		}
			String style = request.getParameter("style");
		if(style != null)
		{
			request.setAttribute("mode", style);
		}
		String treeId = FuncConfig.getProperty("riskWarning.tree", String.valueOf(getUser(request).getOrganTreeIdxid()));
		request.setAttribute("treeId", treeId);
		return mapping.findForward("showorgan");
	}
	
	public ActionForward saveRiskConfig(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'saveRiskConfig' method");
		}
		ReportConfigForm rcf = (ReportConfigForm)form;
		//System.out.println(rcf.getFunId()+"--"+rcf.getRepId()+"--"+rcf.getOrganLevel()+"--"+rcf.getOrganMode()+"--"+rcf.getOrgans()+"--"+rcf.getRole());
		User user = getUser(request);
		int isUpdate = 0;
		String date = (String)request.getSession().getAttribute("logindate");
		Long funcId = new Long(44);
		if (rcf.getFunId() == null){
			funcId = getSessionFuncId(request);
		}else{
			funcId = rcf.getFunId();
		}
		Long repId = new Long(0);
		if(rcf.getRepId() == null){
			repId = getSessionReportId(request);
		}else{
			repId = Long.valueOf(rcf.getRepId());
		}
		
		String mode = rcf.getOrganMode();
		String organs = rcf.getOrgans();
		int [] role = rcf.getRole();
		
		ReportConfigService rcs = (ReportConfigService)getBean("reportConfigService");
		OrganService2 os2 = (OrganService2)getBean("organService2");
		rcs.removeReportConfig(repId, funcId);
		if(mode.equals("1"))//下拉模式
		{
			String level = rcf.getOrganLevel();
			List organList = getSubOrgan(os2, level, organs, user.getOrganTreeIdxid(), date.replaceAll("-", ""));
			for(Iterator itr = organList.iterator(); itr.hasNext();)
			{
				OrganInfo oi = (OrganInfo)itr.next();
				for(int i = 0; i < role.length; i++)
				{
					ReportConfig rc = new ReportConfig();
					rc.setFunId(funcId);
					rc.setReportId(repId);
					rc.setDefInt(new Long(role[i]));
					rc.setDefChar(oi.getCode());
					rcs.saveReportConfig(rc, isUpdate);
				}
			}
		}else if(mode.equals("2"))//多选模式
		{
			String [] organCode = organs.split(",");
			for(int i = 0; i < organCode.length; i++)
			{
				for(int j = 0; j < role.length; j++)
				{
					ReportConfig rc = new ReportConfig();
					rc.setFunId(funcId);
					rc.setReportId(repId);
					rc.setDefInt(new Long(role[j]));
					rc.setDefChar(organCode[i]);
					rcs.saveReportConfig(rc, isUpdate);
				}
			}
		}
		//保存之前先删除
//		rcs.saveReportConfig(config, isUpdate)
		return list(mapping,form,request,response);
	}
	private List getSubOrgan(OrganService2 os2, String level, String organs, int treeId, String date)
	{
		List organList = new ArrayList();
		if(level.equals("1"))
		{
			organList.add(os2.getOrganByCode(organs));
		}else if(level.equals("2"))
		{
			organList = os2.getSubOrgans(treeId, organs, date);
			organList.add(0, os2.getOrganByCode(organs));
		}else if(level.equals("3"))
		{
			organList = os2.getAllSubOrgans(treeId, organs, date);
		}
		return organList;
	}
	/**
	 * 展示报表配置
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward configlist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.info("Entering 'configlist' method");
		}
		ReportConfigService rs = (ReportConfigService)this.getBean("reportConfigService");
		
		List configList=rs.getRepConfigList();
		request.setAttribute("configList",configList);
		
		return mapping.findForward("config12");
	}
	
	

	/**
	 * 编辑报表配置
	 * 
	 */
	public ActionForward configEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.info("Entering 'configEdit' method");
		}
		setToken(request);
		String pkid=request.getParameter("pkid");
		ReportConfig reportConfig=new ReportConfig();
	    ReportConfigForm configForm = (ReportConfigForm)form;
	    DictionaryService ds = this.getDictionaryService();
	    
		ReportConfigService rs = (ReportConfigService)this.getBean("reportConfigService");
		   if( pkid != null ){
			reportConfig  =  rs.getReportConfigList(new Long(pkid));
	      
					try {
						ConvertUtil.copyProperties(configForm,reportConfig,true);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		     	request.setAttribute("areaCode", 2);
		     	
			
			}else {
		
				request.setAttribute("areaCode", 1);
				
			}  
		    //
			request.setAttribute(BTYPE_LIST,ds.getConfigType()); 
		return mapping.findForward("configedit");
	}
	
	/**
	 * 删除报表配置信息
	 * 
	 */
	public ActionForward configDelete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.info("Entering 'configDelete' method");
		}
		setToken(request);

		String pkid=request.getParameter("pkid");
		ReportConfigService rs = (ReportConfigService)this.getBean("reportConfigService");
	     rs.removeReportConfig(new Long(pkid));
		
		try {
			response.sendRedirect(request.getContextPath()
					+ "/reportConfigAction.do?method=configlist&pkid="+request.getParameter("pkid"));
		} catch (IOException e) {
			return  mapping.findForward("config12");
		}
		return null;
	}
	/**
	 * 新增报表配置信息
	 * 
	 */
	public ActionForward configsave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		if (log.isDebugEnabled()) {
			log.info("Entering 'configsave' method");
		}
		ReportConfigService rs = (ReportConfigService)this.getBean("reportConfigService");
		//  String reportId    = request.getParameter("reportId");
     //   String funId      	  = request.getParameter("funId");
        String areaCode       = request.getParameter("areaCode");
        String pkid			  = request.getParameter("pkid");
       //  String defChar     = request.getParameter("defChar");
       //  String description = request.getParameter("description");
        
        ReportConfigForm configForm = (ReportConfigForm)form;
		
		ReportConfig reportConfig=new ReportConfig();
//		reportConfig.setReportId(new Long( reportId ));//���
//		reportConfig.setFunId(new Long( funId ));//����
//		reportConfig.setDefChar( defChar );//������
//		reportConfig.setDescription( description );//����Ӧ�������
//		reportConfig.setIdxId(new Long( 1 ));//Ĭ��
//		reportConfig.setDefInt(new Long( 1 ));//Ĭ��
	
	//	if( funId != null ){
			//configForm.setFunId(  new Long(funId) );
			configForm.setIdxId(  new Long( 1 ) );
			configForm.setDefInt( new Long( 1 ) );
			try {
				
				ConvertUtil.copyProperties(reportConfig, configForm, true);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
//		}
		
		if( "1".equals(areaCode)){
			
			rs.saveConfig(reportConfig); //����
		
		}else{
			reportConfig.setPkid( new Long(pkid));
			rs.setReportConfig(reportConfig); //�޸�
			
		}
		
		List configList=rs.getRepConfigList();
		request.setAttribute("configList",configList);
		
		return mapping.findForward("config12");
//		return null;
	}
	
}
