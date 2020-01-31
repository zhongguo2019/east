<%@ page import="com.krm.ps.sysmanage.usermanage.vo.*"%>
<%@ page import="com.krm.ps.util.SysConfig"%>
<%@ page import="com.krm.ps.util.FuncConfig"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%
	User user = (User) request.getSession().getAttribute("user");
	String orgId = user.getOrganId();
%>

<c:set var="colName">
	<fmt:message key="common.list.reportname"/>
</c:set>
<c:set var="reportTreeURL">
	<c:out value="${hostPrefix}"/><c:url value='/reportView.do?method=repTreeAjax${params}&levelFlag=1'/>
</c:set>
<c:set var="reportButton">
	<fmt:message key="place.select"/>
</c:set>

<c:set var="colNames">
	<fmt:message key="exportXML.tree.org.name"/>
</c:set>
<c:set var="orgTreeURL">
	<c:out value="${hostPrefix}" /><c:url value="/treeAction.do?method=getOrganTreeXML${orgparam}"/>
</c:set>
<c:set var="orgButton">
	<fmt:message key="place.select"/>
</c:set>
<c:set var="displaySumType">
	<%=FuncConfig.getProperty("reportview.showCollectType")%>
</c:set>
<c:set var="studentLoanReportId">
	<%=FuncConfig.getProperty("studentloan.reportid")%>
</c:set>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" /> 
<html>
<head>
<title><fmt:message key="webapp.prefix" /></title>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
<link rel="stylesheet" href="<c:url value='${cssPath}/css.css" type="text/css'/>"/>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${cssPath}/common.css'/>" />
 <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-win2k-1.css'/>" title="win2k-cold-1" />
<script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar.js'/>"></script> 
<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/lang/zh-cn.js'/>"></script> 
<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-setup.js'/>"></script>
<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/ActiveXTree/ActiveXTree.js'/>"></script>    
<script type="text/javascript" src="<c:url value='/ps/scripts/prototype.js'/>"></script>
<script src="<c:url value='/ps/framework/common/jqueryTab/jquery-1.2.4b.js'/>" type="text/javascript"></script>
<script src="<c:url value='/ps/framework/common/jqueryTab/jquery-1.4.1.min.js'/>"
	type="text/javascript"></script>
<script type="text/javascript">
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

function changeOrg(){
    var oDate = document.reportViewForm.dataDate;
	var vDate = oDate.value;
	var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/treeAction.do?method=getOrganTreeXML&date="+vDate;
	document.all.objTree1.SetXMLPath(url);
   	document.all.objTree1.Refresh();	
}


function changeRep(){
    var oDate = document.reportViewForm.dataDate;
	var vDate = oDate.value;
	var oOrganId = document.reportViewForm.organId;
	var vOrganId = oOrganId.value;
	var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/reportView.do?method=repTreeAjax&paramdate="+vDate+"&paramorgan="+vOrganId+"&levelFlag=1";	
	<c:if test="${systemName=='1104'}">
		var url = "<c:out value="${hostPrefix}"/>/slsint/reportView.do?method=repTreeAjax&paramdate="+vDate+"&paramorgan="+vOrganId+"&levelFlag=1";
	</c:if>
	<c:if test="${systemName=='billExchange'}">
		var url = "<c:out value="${hostPrefix}"/>/slspj/reportView.do?method=repTreeAjax&paramdate="+vDate+"&paramorgan="+vOrganId+"&levelFlag=1";
	</c:if>
	document.all.objTree2.SetXMLPath(url);
   	document.all.objTree2.Refresh();	
}

function change(){
	changeOrg();
	changeRep();
	setReportList();
}

function changeTree1(){
	changeRep();
}

function changeTree2(){}

function setReportUnit(){
	<c:if test="${unitCodeFlag == 1}">
	var ajaxOption = {method:'get',onSuccess:setReportUnitResponse};
	var url = "<c:out value='${hostPrefix}'/><c:url value='/reportView.do?method=getReportUnit&reportID='/>" + document.all.objTree2.getCurrentItem();
	var doAjax = new Ajax.Request(url,ajaxOption);
	</c:if>
}

function setReportUnitResponse(ajaxRequest){
	var unitCode = ajaxRequest.responseText;
	if (unitCode == "0"){
		return;
	}
	var selObj = document.all.selUnit;
	for (var i = 0 ; i < selObj.options.length ; i++){
		if (selObj.options[i].value == unitCode){
			selObj.selectedIndex = i;
			break;
		}
	}
}

var oldHide=Calendar.prototype.hide;
Calendar.prototype.hide = function () {oldHide.apply(this);	change()}
</script>
<style type="text/css">
 .table {
	width: 100%;
	padding: 0px;
	margin: 0px;
	font-family: Arial, Tahoma, Verdana, Sans-Serif, '<fmt:message key="view.st" />';
	border-left: 1px solid #ADD8E6;
	border-collapse: collapse;
}
/*TABLE  top type*/
.table th {
	font-size: 12px;
	font-weight: 600;
	color: #303030;
	border-right: 1px solid #ADD8E6;
	border-bottom: 1px solid #ADD8E6;
	border-top: 1px solid #ADD8E6;
	letter-spacing: 2px;
	text-align: left;
	padding: 10px 0px 10px 0px;
	background: url(../images/tablehdbg.png);
	white-space: nowrap;
	text-align: center;
	overflow: hidden;
}
/*dan yuan ge yang shi*/
.table td {
	border-right: 1px solid #ADD8E6;
	border-bottom: 1px solid #ADD8E6;
	background: #fff;
	font-size: 12px;
	padding: 3px 3px 3px 6px;
	color: #303030;
	word-break: break-all;
	word-wrap: break-word;
	white-space: normal;
}

/*lan se dan yuan ge yang shi, zhu yao yong yu ge hang bian se*/
.table td.color {
	background: #edf7f9;
}
/*biao ge zhong chao lian jie yang shi*/
.table td a:link {
	font-weight: 400;
	color: #2259D7;
	text-decoration: none;
	word-break: break-all;
	word-wrap: break-word;
	white-space: normal;
}

.table td a:visited {
	font-weight: 400;
	color: #2259D7;
	text-decoration: none;
	word-break: break-all;
	word-wrap: break-word;
	white-space: normal;
}

.table td a:hover {
	font-weight: 400;
	text-decoration: underline;
	color: #303030;
	word-break: break-all;
	word-wrap: break-word;
	white-space: normal;
}

.table td a:active {
	font-weight: 400;
	text-decoration: none;
	color: #2259D7;
	word-break: break-all;
	word-wrap: break-word;
	white-space: normal;
}

.btn {
	BORDER-RIGHT: #7b9ebd 1px solid;
	PADDING-RIGHT: 2px;
	BORDER-TOP: #7b9ebd 1px solid;
	PADDING-LEFT: 2px;
	FONT-SIZE: 12px;
	FILTER: progid : DXImageTransform.Microsoft.Gradient ( GradientType = 0,
		StartColorStr = #ffffff, EndColorStr = #cecfde );
	BORDER-LEFT: #7b9ebd 1px solid;
	CURSOR: hand;
	COLOR: #0D82AE;
	PADDING-TOP: 2px;
	BORDER-BOTTOM: #7b9ebd 1px solid;
	background-color: #FFFFFF
} 
</style>
</head>
<!-- onclick="suggestclose()" -->
 <script type="text/javascript">
function forbiddon(){
	document.onkeydown=function(){if(event.keyCode==8)return false;};
}

</script>

<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0" onload="forbiddon();">
	<table border="0" cellpadding="0" cellspacing="0" width="100%"
		height="100%">
		<tr>
			 <td width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td>
							<p style="margin-top: 3">
								<font class=b><b><fmt:message key="view.blztcx"/>>><fmt:message key="view.ztcx"/></b></font>
							</p>
						</td>
						<td></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td width="100%" bgcolor="#EEEEEE" valign="top">
				<table border="0" width="98%" align="left" cellSpacing=0 cellPadding=0>
					<tr>
						<td height="30" align="left">
							<html:form action="reportView.do?method=inputExtraCondition" method="post" >
								<table width="100%" border="0" align="left" cellSpacing="0"
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
										<td class="TdBGColor1" width="50px"><input type="hidden"
											name="showret" value="yes"> <input id="dataDate"
											name="dataDate" type="text"
											value="<c:out value='${dataDate}'/>" readonly="true" /></td>
										<script type="text/javascript">
											Calendar.setup({
												inputField : "dataDate",
												ifFormat : "%Y-%m-%d",
												showsTime : false
											});
										</script>
										<td width="50" align="left" nowrap class="TdBGColor1"
											align="right">&nbsp;&nbsp;&nbsp;<fmt:message key="reportview.form.organ"/></td>
										<td width="80" align="left" nowrap class="TdBGColor1"><slsint:ActiveXTree
												left="220" top="325" width="380"
												height="${activeXTreeHeight }" xml="${orgTreeURL}"
												bgcolor="0xFFD3C0" rootid="<%=orgId %>"
												columntitle="${colNames}" columnwidth="380,0,0,0"
												formname="reportViewForm" idstr="organId"
												namestr="organName" checkstyle="0" filllayer="2"
												txtwidth="250" buttonname="${orgButton}">
											</slsint:ActiveXTree></td>
										<td width="50" align="left" nowrap class="TdBGColor1"
											align="right">&nbsp;&nbsp;&nbsp;<fmt:message key="reportview.form.report"/></td>
										<td width="80" align="left" nowrap class="TdBGColor1">
										<slsint:ActiveXTree
												left="220" top="325" width="380"
												height="${activeXTreeHeight }" xml="${reportTreeURL}"
												bgcolor="0xFFD3C0" rootid="10000" columntitle="${colName}"
												columnwidth="380,0,0,0" formname="reportViewForm"
												idstr="reportId" namestr="reportName" checkstyle="0"
												filllayer="2" txtwidth="250" buttonname="${reportButton}"
												onhide="setReportUnit()">
											</slsint:ActiveXTree></td>
										<td align="left" colspan="3"><input align="middle"
											type="button"
											value="<fmt:message key="reportview.button.look"/>"
											style="width: 80;" onclick="viewReport('_self','yes')"
											class="TdBGColor1"></td>
									</tr>
								</table>
							</html:form></td>
					</tr>
					<tr>
						<td align="center">
							<div align="center" style="width: 98%">
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
							   		 	requestURI="" id="rt" width="100%"  pagesize="18"
							    		styleClass="list reportsList" >
							    		
							    	<c:if test="${leveType != 1}"> 	
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
												titleKey="reportguidejilu">
												<c:out value="${rt[2]}" />
											</display:column>
											<display:column sort="true" headerClass="sortable" width="10%"
												titleKey="reportguidejiluy">
												<c:out value="${rt[7]}" />
											</display:column>
											<display:column sort="true" headerClass="sortable" width="30%"
												titleKey="reportrule.edit">
											<c:if test="${rt[5]!=0}">
												<a href='dataFill.do?method=getReportGuideDetailed&organId=<c:out value="${rt[1]}" />&reportId=<c:out value="${rt[0]}" />&targetTable=<c:out value="${rt[4]}" />&reportDate=<c:out value="${dataDate }"/>'><fmt:message key="view.xxxx"/></a>&nbsp;&nbsp;								
											</c:if> 
											<c:if test="${rt[2]!=0}">
													<a href='dataFill.do?method=editReportDataEntryForDataInput&openmode=_self&canEdit=1&levelFlag=0&organId=<c:out value="${rt[1]}" />&reportDate=<c:out value="${dataDate }"/>&reportId=<c:out value="${rt[0]}" />&flag2=1&flagtap=<c:out value="${flagtap}" />'><fmt:message key="view.sjbl"/></a>&nbsp;&nbsp;
											</c:if>	
										<%--
											<c:if test="${rt[2]==0 && rt[6] == 0}">
												<a href="javascript:commitDataStatus('<c:out value="${rt[1]}" />','<c:out value="${rt[0]}" />','<c:out value="${rt[4]}" />')"><fmt:message key="view.bltj"/></a>
											</c:if>		
											<c:if test="${rt[2]==0 && rt[6] > 0}">
												<font style="color:green;"><fmt:message key="view.blwc"/></font>
												<font style="margin-left:10px;"><a href="javascript:unLockDataStatus('<c:out value="${rt[1]}" />','<c:out value="${rt[0]}" />')">
													<fmt:message key="report.lock.deLock"/></a></font>
											</c:if>	
											 --%>
											</display:column>
									</c:if>	
									
											
							    	<c:if test="${leveType == 1}"> 	
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
												titleKey="reportrule.wCount">
												<c:out value="${rt[8]}" />
											</display:column>
											<display:column sort="true" headerClass="sortable" width="10%"
												titleKey="reportrule.tCount">
												<c:out value="${rt[9]}" />
											</display:column>
											<display:column sort="true" headerClass="sortable" width="30%"
												titleKey="reportrule.edit">
										<%-- <c:if test="${rt[5]!=0}">
												<a href='dataFill.do?method=getReportGuideDetailed&organId=<c:out value="${rt[1]}" />&reportId=<c:out value="${rt[0]}" />&targetTable=<c:out value="${rt[4]}" />&reportDate=<c:out value="${dataDate }"/>'><fmt:message key="view.xxxx"/></a>&nbsp;&nbsp;	 							
											</c:if>
											--%> 
											<c:if test="${rt[2]!=0}">
													<a href='dataFill.do?method=editReportDataEntryForDataInput&openmode=_self&canEdit=1&levelFlag=0&organId=<c:out value="${rt[1]}" />&reportDate=<c:out value="${dataDate }"/>&reportId=<c:out value="${rt[0]}" />&flag2=1&flagtap=<c:out value="${flagtap}" />'><fmt:message key="view.sjbl"/></a>&nbsp;&nbsp;
											</c:if>	
											<c:if test="${rt[8]==0 && rt[6] == 0}">
												<a href="javascript:commitDataStatus('<c:out value="${rt[1]}" />','<c:out value="${rt[0]}" />','<c:out value="${rt[4]}" />')"><fmt:message key="view.sjsh"/></a>
											</c:if>		
											<c:if test="${rt[8]==0 && rt[6] > 0}">
												<font style="color:green;"><fmt:message key="view.sjysh"/></font>
												<font style="margin-left:10px;"><a href="javascript:unLockDataStatus('<c:out value="${rt[1]}" />','<c:out value="${rt[0]}" />')">
													<fmt:message key="report.lock.deLock"/></a></font>
											</c:if>	
											</display:column>
									</c:if>	
									</display:table>
									<script type="text/javascript">
										<!--
											highlightTableRows("rt");
										//-->
									</script>
								</form>
							</div>
						</td>
					</tr>
					<tr height=17>
						<td></td>
					</tr>
				<%-- 	<tr>
							<td height="30" align="left">
								&nbsp;&nbsp; <a href="dataFill.do?method=getReportGuide&levelFlag=1&leveType=1" style="color:#990000;un"><strong><fmt:message key="button.back"/></strong></a>
						    </td>
					</tr>
				--%>
				</table>
			</td>
		</tr>
	</table>
<script type="text/javascript">
	var df = document.reportViewForm
	function viewReport(tg, sr) {
		oReportId = df.reportId;
		vReportId = oReportId.value;
		if (vReportId == "" || vReportId == null) {
			alert("<fmt:message key="js.validate.report"/>");
			return;
		}
		if (vReportId >= 10000) {
			alert("<fmt:message key="js.validate.report"/>");
			return false;
		}
		df.action = "dataFill.do?method=getReportGuide";
		<c:if test="${unitCodeFlag == 1}">
		setUnitCode();
		</c:if>
		if (tg === "_blank") {
			var viewOrganId = df.organId.value;
			var viewDataDate = df.dataDate.value;
			var viewReportId = df.reportId.value;
			tg = viewReportId + viewDataDate + viewDataDate + "_window";
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
