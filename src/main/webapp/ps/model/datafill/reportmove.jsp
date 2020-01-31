<!-- /ps/model/datafill/reportmove.jsp -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page pageEncoding="UTF-8"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>

<c:set var="flagOrgan" value="1"/>
<c:set var="flagReport" value="1"/>
<html>
<head>
<title><fmt:message key="webapp.prefix" /></title>
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${displaytag12Path}/displaytag.css'/>" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>
    <script language="javascript" type="text/javascript" src="<c:url value='ps/uitl/My97DatePicker/WdatePicker.js'/>"></script>
 
<script type="text/javascript">
  var str="<c:out value='${str}'/>";
  var snum="<c:out value='${snum}'/>";
  if(str=="a"){
	  alert(snum+"<fmt:message key="view.tbcg" />");
  }
  if(str=="b"){
	  alert(snum+"<fmt:message key="view.tbsb" />");
  }
  function reportmoves(){
	  window.location.href="dataFill.do?method=reportMove&levelFlag=1" ;
  }
</script>

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
	 $.post("reportView.do?method=repTreeAjax&levelFlag=1",function(data){
			var reportXml = eval(data)[0].reportXml;
			$('#combotreeRepTree').combotree('loadData', reportXml);
		}); 
 }
 
function setUnitCode(){
	var listObj = document.all.selUnit;
	document.all.unitCode.value = listObj.options[listObj.selectedIndex].value;
}

function repTree(){
    var oDate = document.reportViewForm.dataDate;
	var vDate = oDate.value;
	var oOrganId = document.reportViewForm.organId;
	var vOrganId = oOrganId.value;	
	var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/reportView.do?method=repTreeAjax&paramdate="+vDate+"&paramorgan="+vOrganId;
	if (window.XMLHttpRequest){
		req = new XMLHttpRequest();
	}else if (window.ActiveXObject){
		req = new ActiveXObject("Microsoft.XMLHTTP");
	}
	if (req){
		req.open("GET", url, true);
		req.send();
	}	
}

</script>

<script type="text/javascript">
		 /* window.parent.document.getElementById("ppppp").value= "<fmt:message key="view.blztcx"/>>><fmt:message key="view.ztcx"/>";  */ 
</script>

<style type="text/css">
	#aaaa{margin: 0}
</style>

<!-- onclick="suggestclose()" -->
 <script type="text/javascript">
function forbiddon(){
	document.onkeydown=function(){if(event.keyCode==8)return false;};
}

</script>

</head>
<body onload=" forbiddon()" class="easyui-layout">

  <html:form action="reportView.do?method=inputExtraCondition" method="post" styleId="aaaa">
								<table  width="100%" class="navbar"  cellSpacing="0"
									cellPadding="0">
									<c:if test="${param.method=='enterViewSearch'}">
										<input type="hidden" name="mode" value="display" />
									</c:if>
									<c:if test="${param.method=='enterInputSearch'}">
										<input type="hidden" name="mode" value="edit" />
									</c:if>
									<tr>
										<td width="50" align="left" nowrap class="TdBGColor1"
											align="right">&nbsp;&nbsp;&nbsp;<fmt:message key="reportview.form.date"/>
									<input type="hidden" name="reportId" id="reportId"
										<c:if test="${reportId == null || reportId == '' }">
											value="10000" 
										</c:if>
										<c:if test="${reportId != null || reportId != '' }">
											value="<c:out value='${reportId}' />" 
										</c:if>
										/>
									<input type="hidden" name="verifyFlag" id="verifyFlag"
										value="1" />
									<input type="hidden" name="canEdit" id="canEdit" value="0" />
									<input type="hidden" name="datapkids" id="datapkids"
										value="<c:out value='${datapkids}' />" />
									<input type="hidden" name="reportName"	/>
									<input type="hidden" name="organId"
										value="<c:out value='${organId}' />" />
									<input type="hidden" name="organName" />
									<input type="hidden" name="unitCode"
										value="<c:out value='${unitCode}' />" />
											</td>
										<td class="TdBGColor1" width="50px">
										<input type="hidden" name="showret" value="yes"> 
									<input id="dataDate" name="dataDate" type="text" 
									value="<c:out value='${dataDate}'/>" 
									style="width:150;" readonly="true" onClick="WdatePicker()"/>
										 
										 
									  </td>
										<td width="50" align="left" nowrap class="TdBGColor1"
											>&nbsp;&nbsp;&nbsp;<fmt:message key="reportview.form.organ"/></td>
										<td width="80" align="left" nowrap class="TdBGColor1">
											<div id="flagOrgan1">
												<input id="combotreeOrganTree" class="easyui-combotree"  value="<c:out value='${organId}' />" style="width:280px;"/>
											</div>
											<div id="flagOrgan2">
												<input id="combotreeOrganTree" class="easyui-combotree" multiple value="<c:out value='${organId}' />"  style="width:3280px;"/>
											</div>
										</td>
										<td width="50" align="left" nowrap class="TdBGColor1"
											align="right">&nbsp;&nbsp;&nbsp;<fmt:message key="reportview.form.report"/></td>
										<td width="80" align="left" nowrap class="TdBGColor1">
											<div id="flagReport1">
												<input id="combotreeRepTree" class="easyui-combotree"    style="width:280px;" 
													<c:if test="${reportId == null || reportId == '' }">
														value="10000" 
													</c:if>
													<c:if test="${reportId != null || reportId != '' }">
														value="<c:out value='${reportId}' />" 
													</c:if>
												/>
											</div>
											<div id="flagReport2">
												<input id="combotreeRepTree" class="easyui-combotree" multiple  style="width:280px;"
													<c:if test="${reportId == null || reportId == '' }">
														value="10000" 
													</c:if>
													<c:if test="${reportId != null || reportId != '' }">
														value="<c:out value='${reportId}' />" 
													</c:if>
												/>
											</div>	
										</td>
										<td align="left" colspan="3">
				<div style="text-align:left;padding:5px">
	    	 <a href="#" class="easyui-linkbutton buttonclass1" data-options="iconCls:'icon-search'"  onclick="viewReport('_self','yes')"><fmt:message key="reportview.button.look"/></a>
	    </div>
											
										</td>
									</tr>
								</table>
							</html:form>
	
					<div data-options="region:'center',border:false,title:' <fmt:message key="view.ywsjbu.sjtb"/> '" style="width:100%;padding:0px;overflow-y:hidden ">
					<div class="pbox">
					  <fmt:message key="report.tips4"/><a href="#" class="easyui-linkbutton c6" data-options="iconCls:'icon-import'" onclick="reportmoves();"><fmt:message key="view.sjtbtj"/></a>
					</div>
					</div>
	
				<table border="0" width="100%" cellSpacing=0 cellPadding=0>
		

					<tr>
						<td align="center">
							<div align="center" style="width: 100%">
								<form name="reportDataForm" action="dataFill.do?method=getDataByCondition"
									method="post">
									
									<input type="hidden" name="organId" value="<c:out value="${organId }"/>" id="organIds" />
									<input type="hidden" name="reportDate" value="<c:out value="${dataDate }"/>" id="reportDates" />
									<input type="hidden" name="reportIds" value="<c:out value="${reportIds}"/>" id="rIds" />
								    <input type="hidden" name="treportIds" value="<c:out value="${treportIds}"/>" id="tIds" />
							        <%
										int i = 0;
									%>
									<display:table name="reportList" cellspacing="0" cellpadding="0"  
							   		 	requestURI="" id="rt" width="100%"  pagesize="12" 
							    		styleClass="list reportsList" >
							    		
										<%-- Table columns --%>
										<%
											i++;
										%>
											<display:column sort="true" headerClass="sortable" width="30%"
												titleKey="reportguideorgan">
												<c:out value="${organInfo.full_name}" />
											</display:column>
											<display:column sort="true" headerClass="sortable" width="30%"
												titleKey="reportguide">
												<c:out value="${rt[3]}" />
											</display:column>
											<display:column sort="true" headerClass="sortable" width="10%"
												titleKey="reportguidewtb">
												<c:out value="${rt[7]}" />
											</display:column>
											<display:column sort="true" headerClass="sortable" width="10%"
												titleKey="reportguideytb">
												<c:out value="${rt[9]}" />
											</display:column>
											<display:column sort="true" headerClass="sortable" width="10%"
												titleKey="reportguidetbyc">
												<c:out value="${rt[10]}" />
											</display:column>
											<display:column sort="true" headerClass="sortable" width="10%"
												titleKey="reportrule.edit">
											<c:if test="${rt[7]!=0}">
													<a style="text-decoration: none;" href='dataFill.do?method=reportMove&reportName=<c:out value="${rt[3]}" />&reportId=<c:out value="${rt[0]}" />'><fmt:message key="view.sjtbtjone"/></a>&nbsp;&nbsp;
											</c:if>	
										
											</display:column>
									</display:table>
									
								</form>
							</div>
						</td>
					</tr>
				</table>

<script type="text/javascript">
	var df = document.reportViewForm;
	function viewReport(tg, sr) {
		var dataDate = $("#dataDate").val();
		var vReportId = $("#combotreeRepTree").combotree("getValue");
		var organCode = $("#combotreeOrganTree").combotree("getValue");
		if (vReportId == "" || vReportId == null) {
			alert("<fmt:message key="js.validate.report"/>");
			return;
		}
		if (vReportId >= 10000) {
			alert("<fmt:message key="js.validate.report"/>");
			return false;
		}
		df.action = "dataFill.do?method=getReportGuideMove&levelFlag=1&reportId="+vReportId+"&organCode="+organCode;
		<c:if test="${unitCodeFlag == 1}">
		setUnitCode();
		</c:if>
		if (tg === "_blank") {
			var viewOrganId = $("#combotreeOrganTree").combotree("getValue");
			var viewDataDate = df.dataDate.value;
			var viewReportId = $("#combotreeRepTree").combotree("getValue");
			tg = viewReportId+viewDataDate+viewDataDate+"_window";
		}
		df.target = tg ;
		df.showret.value = sr ;
		df.submit();
	}
	function commitDataStatus(organId, reportId,targetTable){
		if(!confirm('<fmt:message key="view.qrtjm"/>')){ 
			return;
		} 
		var dataDate = $("#dataDate").val();
		var url = "dataFill.do?method=commitDataStatus";
		var param = {"organId":organId, "reportId":reportId,"dataDate" : dataDate,"targettable" : targetTable};
		$.ajax({
			url : url,
			data : param,
			type : "post",
			success : function(data){
				location.href = location.href;
			},
			error : function(e){
				alert(e);
			}
		});
	}
function unLockDataStatus(organId, reportId){
	if(!confirm('<fmt:message key="button.ratifypass"/><fmt:message key="report.lock.deLock"/>')){ 
		return;
	} 
	var dataDate = $("#dataDate").val();
	var url = "dataFill.do?method=unLockDataStatus";
	var param = {"organId":organId, "reportId":reportId,"dataDate" : dataDate};
	$.ajax({
		url : url,
		data : param,
		type : "post",
		success : function(data){
			location.href = location.href;
		},
		error : function(e){
			alert(e);
		}
	});
}
</script>
</body>
</html>
