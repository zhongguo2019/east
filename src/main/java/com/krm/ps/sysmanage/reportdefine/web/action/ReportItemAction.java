package com.krm.ps.sysmanage.reportdefine.web.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.krm.ps.framework.common.web.action.BaseAction;
import com.krm.ps.framework.common.web.util.RequestUtil;
import com.krm.ps.sysmanage.reportdefine.services.ReportDefineService;
import com.krm.ps.sysmanage.reportdefine.vo.Report;
import com.krm.ps.sysmanage.reportdefine.vo.ReportItem;
import com.krm.ps.sysmanage.reportdefine.web.form.ReportItemForm;
import com.krm.ps.sysmanage.usermanage.services.DictionaryService;
import com.krm.ps.util.Constants;
import com.krm.ps.util.ConvertUtil;

/**
*
* @struts.action name="reportItemForm" path="/reportItemAction" scope="request" 
*  validate="false" parameter="method" input="edit"
*  
* @struts.action-forward name="edit" path="/reportdefine/reportItemForm.jsp"
* @struts.action-forward name="list" path="/reportdefine/reportItemList.jsp"
* @struts.action-forward name="listUndeleteable" path="/formulaDefine.do?method=listUndeleteableFormulas"
* @struts.action-forward name="sort" path="/common/sortcommon.jsp"
* @struts.action-forward name="sort_list" path="/reportItemAction.do?method=list"
*/

public class ReportItemAction extends BaseAction{
	
	private static String REPORT_ID = "reportId";
	private static String ITEM_ID = "itemId";
	private static String ITEM_LIST = "itemList";
	private static String FREQUENCYS = "frequencys";
	private static String SUPERIDS = "superIds";
	
	private Long getReportId(HttpServletRequest request){
		String id = request.getParameter(REPORT_ID);
		if(null!=id){
			return new Long(id);
		}
		return null;
	}
	
	private Long getItemId(HttpServletRequest request){
		String id = request.getParameter(ITEM_ID);
		if(null!=id){
			return new Long(id);
		}else{
			if((Long)request.getAttribute(ITEM_ID) != null){
				return (Long)request.getAttribute(ITEM_ID);
			}
		}
		return null;
	}
	
	private String getItemCode(HttpServletRequest request){
		String code = request.getParameter("itemCode");
		if(null!=code){
			return new String(code);
		}
		return null;
	}
	
	private Long getSessionReportId(HttpServletRequest request)throws Exception{
		Object id = request.getSession().getAttribute(REPORT_ID);		
		if(null!=id){
			return (Long)id;
		}
		throw new Exception("session过期");
	}
	
	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception
		{
		if (log.isDebugEnabled()) {
			log.info("Entering 'cancel' method");
		}
		return list(mapping,form,request,response);
		}
	
	public ActionForward enter(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception{
		if (log.isDebugEnabled()) {
			log.info("Entering 'enter' method");
		}
		if(null!=getReportId(request)){
			request.getSession().removeAttribute(REPORT_ID);
			request.getSession().setAttribute(REPORT_ID,getReportId(request));
		}
		return list(mapping,form,request,response);
	}
	
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'list' method");
		}
		return list(mapping,form,request,response,getSessionReportId(request));
	}
	
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,Long reportId) {
		if (log.isDebugEnabled()) {
			log.info("Entering 'list' method");
		}
		ReportDefineService rs = getReportDefineService();
		List items = rs.getReportItems(reportId);
		if(items!=null){
			   for(int i=0;i<items.size();i++){
			       ReportItem item = (ReportItem)items.get(i);
			       String fdate = item.getBeginDate();
			       String tdate = item.getEndDate();
			       String s ="-";
			       if(fdate!=null){
			       item.setBeginDate(fdate.substring(0,4)+s+fdate.substring(4,6)+s+fdate.substring(6,8));
			       }
			       if(tdate!=null){
			       item.setEndDate(tdate.substring(0,4)+s+tdate.substring(4,6)+s+tdate.substring(6,8));
			       }
			   }
			}
		request.setAttribute(ITEM_LIST,items);
		request.setAttribute("reportId",reportId);
		return mapping.findForward("list");
	}
	
	public ActionForward searchItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'searchItem' method");
		}
		ReportDefineService rs = getReportDefineService();
		request.setAttribute(ITEM_LIST,rs.getItemByCode(getSessionReportId(request), getItemCode(request)));
		return mapping.findForward("list");
	}
	
	public ActionForward toEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		if (log.isDebugEnabled()) {
			log.info("Entering 'toEdit' method");
		}
		setToken(request);
		ReportDefineService rs = getReportDefineService();
		DictionaryService ds = getDictionaryService();
		String date =(String)request.getSession().getAttribute("logindate");
		request.setAttribute(FREQUENCYS,ds.getReportfrequency());
		request.setAttribute(SUPERIDS,rs.getCollectReportItems(getSessionReportId(request),date.replaceAll("-","")));
		request.setAttribute(REPORT_ID,getSessionReportId(request));
		return mapping.findForward("edit");
	}
	
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'edit' method");
		}
		ReportItemForm reportItemForm = (ReportItemForm)form;
		ReportItem item = new ReportItem();
		ReportDefineService rs = getReportDefineService();
		Long id = getItemId(request);
		if(null!=id){
			item = rs.getReportItem(id);
		}else {//新增科目时科目初始频度和报表频度一致，wsx 12-04
			Long reportId=getSessionReportId(request);
			if(reportId!=null) {
				Report r=rs.getReport(reportId);
				item.setFrequency(r.getFrequency());
			}
		}
		ConvertUtil.copyProperties(reportItemForm,item,true);
		String fdate = reportItemForm.getBeginDate();
		String tdate = reportItemForm.getEndDate();
		String s = "-";
		if(fdate!=null){
			reportItemForm.setBeginDate(fdate.substring(0,4)+s+fdate.substring(4,6)+s+fdate.substring(6,8));
		}
		else{
			reportItemForm.setBeginDate("1999-01-01");
		}
		if(tdate!=null){
			reportItemForm.setEndDate(tdate.substring(0,4)+s+tdate.substring(4,6)+s+tdate.substring(6,8));
		}
		else{
			reportItemForm.setEndDate("2015-01-01");
		}
		return toEdit(mapping,reportItemForm,request,response);
	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'save' method");
		}
		ReportItemForm itemForm = (ReportItemForm)form;
		if(!tokenValidatePass(request)){
			return list(mapping,form,request,response);
		}
		int isUpdate = 0;
		ReportDefineService rs = getReportDefineService();
		ReportItem item = new ReportItem();
		ConvertUtil.copyProperties(item, itemForm, true);
		item.setReportId(getSessionReportId(request));
		item.setStatus(Constants.STATUS_AVAILABLE);
		if(itemForm.getPkid()!=null){
			//更新
			isUpdate = 1;
		}
		if(item.getBeginDate()!=null){
			item.setBeginDate(item.getBeginDate().replaceAll("-",""));
		}
		if(item.getEndDate()!=null){
			item.setEndDate(item.getEndDate().replaceAll("-",""));
		}
		int operationResult = rs.saveReportItem(item,isUpdate);
		if(operationResult == -1){
			request.setAttribute("error1","-1");
			request.setAttribute(ITEM_ID,item.getPkid());
			ConvertUtil.copyProperties(itemForm,item,true);
			return edit(mapping,itemForm,request,response);
		}else{
			return list(mapping,form,request,response);
		}
	}
	
	public ActionForward del(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'del' method");
		}
		ReportDefineService rs = getReportDefineService();
		Long id = getItemId(request);
		Long reportId = getSessionReportId(request);
		if(null!=id){
			ReportItem item = rs.removeReportItem(id, reportId);
			if(item != null){
				//操作类型为“删除科目”
				request.setAttribute("operateType","0");
				String regExp = ".*\\b" + item.getReportId() + "\\." + item.getCode() + "\\.\\d+\\b.*";
				request.setAttribute("regExp", regExp);
				return mapping.findForward("listUndeleteable");
			}
		}
		return list(mapping,form,request,response);
	}
	
	public ActionForward sortReportItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'sortReportItem' method");
		}
		setToken(request);  //避免重复提交
		ReportDefineService rs = getReportDefineService();
		List ls = new ArrayList();
		List itemList = rs.getReportItems(getSessionReportId(request));
		for(int i=0;i<itemList.size();i++){
			Object[] o = new Object[2];
			o[0]=((ReportItem)itemList.get(i)).getPkid();
			o[1]=((ReportItem)itemList.get(i)).getItemName()+"("+((ReportItem)itemList.get(i)).getCode()+")";
			ls.add(o);
		}
		request.setAttribute("fileTitle","科目表维护－》报表科目排序");
		request.setAttribute("sortList",ls);
		request.setAttribute("serviceName","reportItemSortService");
		request.setAttribute("sortTitle","科目排序");
		ActionForward forward = mapping.findForward("sort_list");
		request.setAttribute("forwardURL",forward.getPath());
		return mapping.findForward("sort");
	}
	
	/**
	 * 进入插入新科目界面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward enterInsertItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'enterInsertItem' method");
		}
		request.setAttribute("insertItem","true");
		return edit(mapping,form,request,response);
	}
	
	/**
	 * 在当前科目前插入一个新的科目
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward insertItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (log.isDebugEnabled()) {
			log.info("Entering 'insertItem' method");
		}
		setToken(request);  //避免重复提交
		ReportItemForm itemForm = (ReportItemForm)form;
		Integer showOrder = RequestUtil.getInteger(request,"showOrder",999999);
		ReportItem item = new ReportItem();
		ConvertUtil.copyProperties(item, itemForm, true);
		item.setReportId(getSessionReportId(request));
		item.setStatus(Constants.STATUS_AVAILABLE);
		if(item.getBeginDate()!=null){
			item.setBeginDate(item.getBeginDate().replaceAll("-",""));
		}
		if(item.getEndDate()!=null){
			item.setEndDate(item.getEndDate().replaceAll("-",""));
		}
		int result = getReportDefineService().insertReportItem(item,showOrder);
		if(result != ReportDefineService.SAVE_OK){
			request.setAttribute("error1","-1");
			request.setAttribute(ITEM_ID,item.getPkid());
			ConvertUtil.copyProperties(itemForm,item,true);
			return edit(mapping,itemForm,request,response);
		}else{
			return list(mapping,form,request,response);
		}
	}
}
