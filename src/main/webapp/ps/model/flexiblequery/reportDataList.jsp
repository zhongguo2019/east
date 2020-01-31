<!-- /ps/model/flexiblequery/reportDataList. -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page pageEncoding="UTF-8"%>
<%@ page import="com.krm.ps.sysmanage.usermanage.vo.*"%>
<%@ page import="com.krm.ps.util.SysConfig"%>
<%@ page import="com.krm.ps.util.FuncConfig"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
<%
   User user = (User)request.getSession().getAttribute("user");
   String orgId = user.getOrganId();   
 %>
<c:set var="flagOrgan" value="1"/>
<c:set var="flagReport" value="1"/>

<script type="text/javascript">


function resetdata(obj,reportda,resulttablename,dataId,repid) {
	if(!confirm('<fmt:message key="view.qrcz" />')){
		return;
	}
	var params={"resulttablename": resulttablename,"reportDate":reportda,"reportId":repid,"dataId":dataId ,"method":"resetReportData"};
	$.ajax({ 
		type: "POST", 
		async: true, //ajax
		url: "flexiblequery.do", 
		data: jQuery.param(params), 
		success: function(result){
			if(result==1){
				//chan chu ben hang shu ju
				var tr=obj.parentNode.parentNode;
				var tbody=tr.parentNode;
				tbody.removeChild(tr);
				alert('<fmt:message key="view.sjczcg" />');
			}else if(result==2){
				alert('<fmt:message key="view.sjczsb" />');
			}else{
				alert('<fmt:message key="view.sjczsb" />');
			}
		}
	});
}

 function etirSar(dataId,page){
	   var url = "dataFill.do?method=geteditDataDetail&singleOrMore=<c:out value='${singleOrMore}' />&dataDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>&pager="+page+"&dataId="+dataId+"&levelFlag=<c:out value='${levelFlag}'/>"; 
        window.location.href=url;//zhe shi dang qian ye diao zhuan <c:out value='${page.pageNo}'/>

 }
 
 
 function delSar(obj,pkid,rId,reportda,organId){
	 if(pkid=="0"){
		 if(!confirm(reportda+'<fmt:message key="view.cyqrsc" />')){
				return;
			} 
	 }else{
		 if(!confirm('<fmt:message key="view.qrsc" />')){
				return;
			}
	 }
		var params={"pkid": pkid ,"reportId": rId ,"dataDate": reportda ,"organId":organId,"method":"delSingelData"};
		$.ajax({ 
			type: "POST", 
			async: false, //ajax
			url: "dataFill.do", 
			data: jQuery.param(params), 
			success: function(result){
				if(result==1){
						var tr=obj.parentNode.parentNode;
						var tbody=tr.parentNode;
						tbody.removeChild(tr);
					    alert('<fmt:message key="view.sjsccg" />');
				}else if(result==2){
					    alert('<fmt:message key="view.sjscsb" />');
				}
			}
		});
 }
 
/*  function buttongjcx(organId,levelFlag){
	   var zhi1=$("#zhi").val();
   	document.dataMergeForm.action='<c:out value="${hostPrefix}" /><c:url value="/dataFill.do" />?method=getDataDetailgjcx&levelview=1&zhi='+zhi1+"&organId="+organId+"&levelFlag="+levelFlag ;
  	document.dataMergeForm.submit();
 } */
 function buttongjcx(){
		var zhi1=$("#zhis1").val();
		$("#zhi1").val(zhi1);
		var zhi2=$("#zhis2").val();
		$("#zhi2").val(zhi2);
		var zhi3=$("#zhis3").val();
		$("#zhi3").val(zhi3);
		var aaa=document.getElementById("aaa").value;
		var bbb=document.getElementById("bbb").value;
		var ccc=document.getElementById("ccc").value;
	 	document.dataMergeForm.action='<c:out value="${hostPrefix}" /><c:url value="/flexiblequery.do" />?method=getDataDetailgjcx&targetField1='+aaa+'&targetField2='+bbb+'&targetField3='+ccc;
		document.dataMergeForm.submit();
	}
	
 function exportData(){
	   
		var zhi1=$("#zhis1").val();
		$("#zhi1").val(zhi1);
		var zhi2=$("#zhis2").val();
		$("#zhi2").val(zhi2);
		var zhi3=$("#zhis3").val();
		$("#zhi3").val(zhi3);
		var aaa=document.getElementById("aaa").value;
		var bbb=document.getElementById("bbb").value;
		var ccc=document.getElementById("ccc").value;
		
		var vReportId = $("#combotreeRepTree").combotree("getValue");
		var organCode = $("#combotreeOrganTree").combotree("getValue");
		var viewDataDate = document.dataMergeForm.reportDate.value;
			 if (vReportId==""||vReportId == null){
		       alert('<fmt:message key="js.validate.report"/>');
		       return false;
			}
            if (vReportId >= 10000){
				alert('<fmt:message key="js.validate.report"/>');
				return false;
			} 
		
	 	document.dataMergeForm.action='<c:out value="${hostPrefix}" /><c:url value="/flexiblequery.do" />?method=exportrepDataDeta&targetField1='+aaa+'&targetField2='+bbb+'&targetField3='+ccc+'&reportId='+vReportId+'&organId='+organCode+'&reportDate='+viewDataDate;
		document.dataMergeForm.submit();
	 
 }
</script>
<html>
<head>
<title><fmt:message key="webapp.prefix" /></title>
<link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>
	 <script language="javascript" type="text/javascript" src="<c:url value='ps/uitl/My97DatePicker/WdatePicker.js'/>"></script>
	<link rel="stylesheet" type="text/css" href="<c:url value='${tableCss}/tablecss.css'/>"/>
<script type="text/javascript">

$(document).ready(function(){
	var flagOrgan = <c:out value='${flagOrgan}' />
	var flagReport =<c:out value='${flagReport}' />
	if(flagOrgan==2){
		$("#flagOrgan1").remove();
	}else{
		$("#flagOrgan2").remove();
	} 
	if(flagReport==2){//1单选，2多选
		$("#flagReport1").remove();
	}else{
		$("#flagReport2").remove();
	} 
	  getOrganTreeXML();
	  getrepTreeXML();
});  

 function getOrganTreeXML(){//机构树
	 $.post("treeAction.do?method=getOrganTreeXML",function(data){
			var treeXml = eval(data)[0].treeXml;
			$('#combotreeOrganTree').combotree('loadData', treeXml);
		}); 
 }
 
 function getrepTreeXML(){//报表树
	 var leveflag;
	 if(<c:out value="${levelFlag}"/>==0){
		 leveflag="0";
	 }else if(<c:out value="${levelFlag}"/>==2){
		 leveflag="2";
	 }
	 $.post("reportView.do?method=repTreeAjax&levelFlag="+leveflag,function(data){
			var reportXml = eval(data)[0].reportXml;
			$('#combotreeRepTree').combotree('loadData', reportXml);
		}); 
 } 
 
 function datafilsub(){
		/* var oReportId = df.reportId;
	    var vReportId = oReportId.value; */
	    //qingkong zuhe  zhi
		$("#tField1").val(null);
		$("#tField2").val(null);
		$("#tField3").val(null);
		$("#zhi1").val(null);
		$("#zhi2").val(null);
		$("#zhi3").val(null);
		$("#aaa").val(null);
	    var vReportId = $("#combotreeRepTree").combotree("getValue");
		var organCode = $("#combotreeOrganTree").combotree("getValue");
		var viewDataDate = document.dataMergeForm.reportDate.value;
		 if (vReportId==""||vReportId == null){
	       alert('<fmt:message key="js.validate.report"/>');
	       return false;
		}else if (vReportId >= 10000){
			alert('<fmt:message key="js.validate.report"/>');
			return false;
		}else{
			var url="";
			var lxview =$("#lxvieww").val();
			
			if(lxview!=1){
				url="<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/flexiblequery.do?method=getDataDetail&reportId="+vReportId+"&organId="+organCode+"&reportDate="+viewDataDate;
			}else if(lxview==1){
				url="<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/flexiblequery.do?method=getDataDetail&levelview=1&reportId="+vReportId+"&organId="+organCode+"&reportDate="+viewDataDate;
			}
			document.dataMergeForm.action = url;
			document.dataMergeForm.submit();
		} 
		
	}
</script>

</head>
<body  >
				<table border="0" width="100%" align="left" cellSpacing=0 cellPadding=0>
					<tr>
					   <input type="hidden" id="lxvieww" value="<c:out value="${lxview }"/>"/>
						<td height="30"  align="left">
							<form name="dataMergeForm" action="flexiblequery.do?method=getDataDetail" method="post">
								<input type="hidden" name="reportId" value="<c:out value="${reportId }"/>"/>
								<input type="hidden" name="reportName" value="<c:out value="${reportName}"/>">
								<input type="hidden" name="levelFlag" value="<c:out value="${levelFlag}"/>">
								<input type="hidden" name="organId" value="<c:out value='${organId}' />"/>
								<input type="hidden" name="levelview" value="<c:out value='${lxview}' />"/>
								
<%-- 							<input type="hidden" name="cstatus" value="<c:out value='${lxhcstatus}' />"/>--%>								<input type="hidden" name="dataDate" value="<c:out value='${dataDate}' />"/>
								
								<input	type="hidden"  id="zhi1" name="zhi1" value="<c:out value='${zhi1}'/>" />
								<input	type="hidden"  id="tField1" name="tField1" value="<c:out value='${tField1}'/>" />
								
								<input	type="hidden"  id="zhi2" name="zhi2" value="<c:out value='${zhi2}'/>" />
								<input	type="hidden"  id="tField2" name="tField2" value="<c:out value='${tField2}'/>" />
								
								<input	type="hidden"  id="zhi3" name="zhi3" value="<c:out value='${zhi3}'/>" />
								<input	type="hidden"  id="tField3" name="tField3" value="<c:out value='${tField3}'/>" />
								<input type="hidden" name="organName"/>
								
									
								
						<div name="optionbar"  class="navbar2">
					<table width="100%"  border="0"  > 
						<tr >
							<td  width="40" height="20" align="left" 
								valign=middle nowrap>
								
								<fmt:message key="reportview.form.date" />
							</td>
							<td align="left" width="40">		
									 <input id="reportDate" name="reportDate" type="text" 
									value="<c:out value='${dataDate}'/>" 
									style="width:150;" readonly="true" onClick="WdatePicker()"/>

							</td>
							<td align="left" width="1%" nowrap>	
								<fmt:message key="reportview.form.organ" />
						</td>
						<td align="left" width="4%">
						<div id="flagOrgan1">
							<input id="combotreeOrganTree" class="easyui-combotree"  value="<c:out value='${organId}' />"  style="width:300px;"/>
						</div>
						<div id="flagOrgan2">
							<input id="combotreeOrganTree" class="easyui-combotree" multiple value="<c:out value='${organId}' />"  style="width:300px"/>
						</div>	 
								 
					    <td align="left" width="1%" nowrap>
					    	<fmt:message key="collect.reports" />
					    </td>
						<td width="3%" >
							<div id="flagReport1">
								<input id="combotreeRepTree" class="easyui-combotree"  value="<c:out value="${reportId }"/>"   style="width:220px"/>
							</div>
							<div id="flagReport2">
								<input id="combotreeRepTree" class="easyui-combotree" value="<c:out value="${reportId }"/>" multiple style="width:220px">
							</div>	
						</td>	    
			            		<c:if test="${lxhcstatus!=null&&lxhcstatus!=''}">
									      	<td style="width:40px;" align="left" nowrap 
											align="right"><fmt:message key="excel.import.status" /></td>
											<td width="80" align="left" nowrap >
												<select  name="cstatus">
														<option value="2" <c:if test="${lxhcstatus=='2'}" >selected='selected'</c:if> selected>
															<fmt:message key="reportrule.tCount" />
														</option>
														<option value="4" <c:if test="${lxhcstatus=='4'}" >selected='selected'</c:if> >
															<fmt:message key="srcdata.manager.issyn" />
														</option>
												</select>
											</td>

									      	</c:if>  	
			                     
						 <td align="left">
						 	  <a href="javascript:void(0);" onclick="datafilsub();" class="easyui-linkbutton buttonclass2" ><fmt:message key="reportview.button.look"/></a>
						 </td>
						 				 
						</tr>
						
					 </table>
					</div>
					<c:if test="${not empty reportTargetList}">
					<div class="navbar1">
						<table width="100%"  border="0" cellpadding="1" cellspacing="1" >
						    <c:if test="${lxview!='1'}" >
									<tr>
				                        <td width="1%" align="right" nowrap >
											<fmt:message key="view.zd1" />

								   	    </td>
								   	    
								    	<td width="2%" align="left" >
											   		 <html:form action="flexiblequery" method="post" styleId="flexibleQueryForm">
																	  <html:select property="targetField1"  styleId="aaa">
																	   <html:option value="0" ><fmt:message key="system.switch.hint" /></html:option>
																	    <html:options collection="reportTargetList" property="targetField"  labelProperty="targetName"/>
							                 						 </html:select> 
							                 		 </html:form>
				                        </td>

					                     <td width="50" align="right" nowrap 
										    align="right"><fmt:message key="view.zhi1" />

										 </td>
										 <td width="80" align="left"  >
										    <input type="text" value='<c:out value="${zhi1}"/>'  id="zhis1" />

										 </td>
										 
                                         <td width="1%" align="right" nowrap >
											<fmt:message key="view.zd2" />

								   	    </td>
								   	    
								    	<td width="2%" align="left" >
											   		 <html:form action="flexiblequery" method="post" styleId="flexibleQueryForm">
																	  <html:select property="targetField2" styleId="bbb" style="line-height:19;">
																	   <html:option value="0" ><fmt:message key="system.switch.hint" /></html:option>
																	    <html:options collection="reportTargetList" property="targetField"  labelProperty="targetName"/>
							                 						 </html:select> 
							                 		 </html:form>
				                        </td>

					                     <td width="50" align="right" nowrap 
										    align="right"><fmt:message key="view.zhi2" />

										 </td>
										 <td width="80" align="left" nowrap >
										    <input type="text" value='<c:out value="${zhi2}"/>'  id="zhis2" />

										 </td>
										 
										 <td width="1%" align="right" nowrap >
											<fmt:message key="view.zd3" />

								   	    </td>
								    	<td width="2%" align="left" >
											   		 <html:form action="flexiblequery" method="post" styleId="flexibleQueryForm">
																	  <html:select property="targetField3" styleId="ccc">
																	   <html:option value="0" ><fmt:message key="system.switch.hint" /></html:option>
																	    <html:options collection="reportTargetList" property="targetField"  labelProperty="targetName"/>
							                 						 </html:select> 
							                 		 </html:form>
				                        </td>

					                     <td width="50" align="right" nowrap 
										    align="right"><fmt:message key="view.zhi3" />

										 </td>
										 <td width="80" align="left" nowrap >
										    <input type="text" value='<c:out value="${zhi3}"/>'  id="zhis3"/>

										 </td>

										 <td  align="left" nowrap >
										  
										   <a href="javascript:void(0);" class="easyui-linkbutton buttonclass2" data-options="iconCls:'icon-search'"  name="ss" onclick="return buttongjcx();"  style=" height:20px;width:80px;text-decoration:none;"><fmt:message key='view.zhss'/></a>
										   <a href="javascript:void(0);" class="easyui-linkbutton buttonclass2" data-options="iconCls:'icon-exp'" onclick="exportData(); " style=" height:20px;width:80px;text-decoration:none;"><fmt:message key='cheacc.exportfile'/></a>&nbsp;&nbsp;&nbsp;
										 </td>
									</tr>
								</c:if>	
						</table>
					</div>
				</c:if>
			
							</form></td>
					</tr>
					<tr>
						<td align="center">
							<c:if test="${totalPage>0 }">
	          	<table width="100%" align="center" cellpadding="1" cellspacing="1" >
						<c:if test="${lxview!='1'}" >
						    <tr align="left">
								<td align="left" valign="top" class="TdBGColor2">
								<fmt:message key="view.dqd"/> <c:out value="${page.pageNo}" /> <fmt:message key="view.ye"/> <a
									 href="#" onclick="return fenye(1);" ><fmt:message key="chartpage.navigation.homepage"/></a>
									<c:if test="${page.pageNo>1 }">
										<a href="#" onclick="return fenye('<c:out value="${page.pageNo-1 }"/>');" ><fmt:message key="view.syy"/></a>
									</c:if> 
										
									<c:choose>
										<c:when test="${page.pageNo>2&&(page.pageNo+2)<=totalPage}">
											<c:set var="begin" value="${page.pageNo-2}" />
											<c:set var="end" value="${page.pageNo+2 }" />
										</c:when>
										<c:when test="${page.pageNo>2&&(page.pageNo+2)>totalPage}">
											<c:choose>
												<c:when test="${totalPage-2<=page.pageNo&&totalPage>5}">
													<c:set var="begin" value="${totalPage-4}" />
													<c:set var="end" value="${totalPage}" />
												</c:when>
												<c:otherwise>
													<c:set var="begin" value="${page.pageNo-2}" />
													<c:set var="end" value="${totalPage}" />
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:when test="${page.pageNo<=2&&(page.pageNo+2)>totalPage}">
											<c:set var="begin" value="1" />
											<c:set var="end" value="${totalPage}" />
										</c:when>
										<c:when test="${page.pageNo<=2&&(page.pageNo+2)<=totalPage}">
											<c:choose>
												<c:when test="${totalPage>5}">
													<c:set var="begin" value="1" />
													<c:set var="end" value="5" />
												</c:when>
												<c:otherwise>
													<c:set var="begin" value="1" />
													<c:set var="end" value="${totalPage}" />
												</c:otherwise>
											</c:choose>
										</c:when>
									</c:choose> <c:forEach var="num" begin="${begin}" end="${end}">
										<c:choose>
											<c:when test="${num==page.pageNo }">
  					[ <c:out value="${num }" /> ]
  				</c:when>
											<c:otherwise>
  					[ <a href="#" onclick="return fenye('<c:out value="${num}"/>');" ><c:out value="${num }" /> </a> ]
  				</c:otherwise>
										</c:choose>
									</c:forEach> <a href="#" onclick="return fenye('<c:out value="${totalPage}"/>');" ><fmt:message key="view.wy"/></a>
									<c:if test="${page.pageNo<totalPage}">
										<a href="#" onclick="return fenye('<c:out value="${page.pageNo+1}"/>');" ><fmt:message key="view.xyy"/></a>
									</c:if><fmt:message key="view.zg"/><c:out value="${totalPage}" /><fmt:message key="view.ye"/><fmt:message key="view.zg"/> <c:out
										value="${page.totalRecord }" /><fmt:message key="view.jls"/> <fmt:message key="view.tzz"/> <input id="pageNo"
									type="text" style="width: 40px;" /><fmt:message key="view.ye"/> 
									
									 <a href="#" class="easyui-linkbutton go"  onclick="go();">GO</a> &nbsp;&nbsp;&nbsp;&nbsp;
									 </td>
								</td>
							</tr>
						 
						</c:if>
	
					</table>
					</c:if>	
			<table id="budgetTbl" width="2000"  cellpadding="1" cellspacing="1" class="table" border="0">
						<c:if test="${not empty reportTargetList}">
						<thead>
							<tr height="40px">
								<td class="color" align="center" style="width:60px;" nowrap><fmt:message
										key="studentloan.report.sortnum" /></td>
								<c:forEach var="reportTarget" items="${reportTargetList}">
										<td
											id="titleName<c:out value='${reportTarget.targetOrder}' />"
											class="color" align="center" style="white-space:nowrap;overflow:hidden;word-break:break-all; "
											width="<c:out value="${reportTarget.editDate}"/>"><c:out
												value="${reportTarget.targetName}" />
										</td>
								</c:forEach>
								<c:if test="${lxhcstatus=='2' || lxhcstatus=='4' || lxview=='1'}" >
								<td class="color" align="center" style="width:75px;" nowrap><fmt:message key="button.operation" />
								</td>
								</c:if>
							</tr>
						</thead>
						</c:if>
						 <c:if test="${not empty repDataMap}">
							<c:forEach var="itemSort" items="${repItemSort}">
								<tr>
									<td class="TdBGColor2" align="center" style="width:60px;">
									<c:choose>
											<c:when test="${sortnum==null }">
												<c:set var="sortnum">
													<c:out value="1" />
												</c:set>
											</c:when>
											<c:otherwise>
												<c:set var="sortnum">
													<c:out value="${sortnum+1}" />
												</c:set>
											</c:otherwise>
									</c:choose> <c:out value="${sortnum}" />
									</td>
									<c:forEach var="reportTarget" items="${reportTargetList}">
											<td class="TdBGColor2" align="center">
											<c:set
													var="itemDataKey">
													<c:out value="${itemSort.key}" />_<c:out
														value="${reportTarget.targetField}" />
												</c:set> 
												<c:set var="targetField">
													<c:out value="${reportTarget.targetField}" />
												</c:set> 
												 <c:set var="itemvalueIndex">
													_<c:out value="${reportId}" /><c:out value="itemvalue" /><c:out value="${reportTarget.targetField}" />
													
												</c:set> 
													<input id="<c:out value="${itemDataKey}"/>"
														name="<c:out value="${itemDataKey}"/>" align="left"
														style="height: 21;" 
														type="text"
														size="<c:choose><c:when test='${reportTarget.dataType!=3}'>7</c:when><c:otherwise>12</c:otherwise></c:choose>"
														value="<c:out value="${repDataMap[itemDataKey]}"/>" />
										</td>
									</c:forEach>
									<c:if test="${lxhcstatus=='2' || lxhcstatus=='4'}" >
										<td class="TdBGColor2" align="center"  style="white-space:nowrap;overflow:hidden;word-break:break-all; ">
								        	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" style="width:80px" onclick="resetdata(this,'<c:out value='${dataDate}'/>','<c:out value="${lxhresulttablename}"/> ','<c:out value="${itemSort.key}"/>','<c:out value='${reportId}'/>'); "><fmt:message key="button.reset" /></a>	
								       </td>
									</c:if>
								<c:if test="${lxview=='1'}" >
									<td class="TdBGColor2" align="center" style="white-space:nowrap;overflow:hidden;word-break:break-all; " >
								    	<a href="#"  onclick="etirSar('<c:out value="${itemSort.key}" />','<c:out value="${page.pageNo}" />');"><fmt:message key="button.update" /></a>|
									    <a href="#"  onclick="delSar(this,'<c:out value="${itemSort.key}" />','<c:out value='${reportId}'/>','<c:out value='${dataDate}'/>','<c:out value='${organId}' />'); "><fmt:message key="button.delete" /></a> 
									 </td>
								</c:if>
								</tr>
							</c:forEach>
						</c:if>
							
					</table>
						</td>
					</tr>
					<tr height=17>
						<td></td>
					</tr>
				</table>

</body>

</html>

<script type="text/javascript">
var df=document.dataMergeForm;

function sub(){
	var vReportId = $("#combotreeRepTree").combotree("getValue");
	var viewOrganId = $("#combotreeOrganTree").combotree("getValue");
	var viewDataDate = df.reportDate.value;
	 if(vReportId==10000||vReportId==""||vReportId == null)
     {
      alert('<fmt:message key="js.validate.modeltarget"/>!');
      return false;
     }
    else if(vReportId>10000)
     {
     alert('<fmt:message key="place.select"/>'+document.getElementById('reportName').value+'<fmt:message key="view.xzjtmb"/>!');
     return false;
     }
	
	<c:if test="${lxview!='1'}">
		var url="flexiblequery.do?method=getDataDetail&dataDate="+viewDataDate+"&reportId="+vReportId+"&organId="+viewOrganId;
	</c:if>
	<c:if test="${lxview=='1'}" >
		var url="flexiblequery.do?method=getDataDetail&levelview=1&dataDate="+viewDataDate+"&reportId="+vReportId+"&organId="+viewOrganId;
	</c:if>
	df.action = url;
	df.submit();
}




function fenye(page){
	var aaa=document.getElementById("aaa").value;
	var bbb=document.getElementById("bbb").value;
	var ccc=document.getElementById("ccc").value;
	document.dataMergeForm.action='flexiblequery.do?method=getDataDetail&openmode=_self&levelview=2&page='+page+'&targetField1='+aaa+'&targetField2='+bbb+'&targetField3='+ccc;
	document.dataMergeForm.method = "post";
	document.dataMergeForm.submit();
}

function go() {
	var pageNo = $("#pageNo").val();
	if(pageNo<1) {
		pageNo = 1;
	}else if(pageNo><c:out value='${totalPage}'/>){
		pageNo = <c:out value='${totalPage}'/>;
	}
	var aaa=document.getElementById("aaa").value;
	var bbb=document.getElementById("bbb").value;
	var ccc=document.getElementById("ccc").value;
	document.dataMergeForm.action="flexiblequery.do?method=getDataDetail&openmode=_self&levelview=2&page=" + pageNo+'&targetField1='+aaa+'&targetField2='+bbb+'&targetField3='+ccc;
	document.dataMergeForm.method = "post";
	document.dataMergeForm.submit();
	//window.location.href = "flexiblequery.do?method=getDataDetail&openmode=_self&levelview=2&page=" + pageNo;
}
</script>