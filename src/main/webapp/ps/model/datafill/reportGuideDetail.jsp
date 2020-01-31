<!-- /ps/model/datafill/reportGuideDetail. -->
<%@ page import="com.krm.ps.sysmanage.usermanage.vo.*"%>
<%@ page import="com.krm.ps.util.SysConfig"%>
<%@ page import="com.krm.ps.util.FuncConfig"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>

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
 <link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/> 


<script src="<c:url value='/ps/framework/common/jqueryTab/jquery-1.2.4b.js'/>" type="text/javascript"></script>
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

//var oldHide=Calendar.prototype.hide;
//Calendar.prototype.hide = function () {oldHide.apply(this);	change()}
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
/*table top type*/
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
/* dan yuan ge yang shi*/
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
<script type="text/javascript">
		// window.parent.document.getElementById("ppppp").value= "<fmt:message key="welcome.fileListTitle"/>";  
</script>
</head>
<!-- onclick="suggestclose()" -->
 <script type="text/javascript">
function forbiddon(){
	document.onkeydown=function(){if(event.keyCode==8)return false;};
}

</script>

<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0" onload="forbiddon();">
	<div class="navbar">
	<table>
		<tr>
			<td>
				 <a href="dataFill.do?method=getReportGuide&levelFlag=1" class="easyui-linkbutton" style="width:80; height: 20; text-decoration:none;"   ><fmt:message key="button.back"/></a>
			</td>
		</tr>
	</table>
		
	</div>
				<table border="0" width="98%" align="left" cellSpacing=0 cellPadding=0>
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
										<%-- Table columns --%>
										<%
											i++;
										%>
										<display:column sort="true" headerClass="sortable" width="30%"
											titleKey="reportguideorgan">
											<c:out value="${rt[3]}" />
										</display:column>
										<display:column sort="true" headerClass="sortable" width="30%"
											titleKey="reportguide">
											<c:out value="${rt[2]}" />
										</display:column>
										<display:column sort="true" headerClass="sortable" width="10%"
											titleKey="reportguidejilu">
											<c:out value="${rt[4]}" />
										</display:column>
										<display:column sort="true" headerClass="sortable" width="10%"
												titleKey="reportguidejiluy">
												<c:out value="${rt[7]}" />
											</display:column>
										<display:column sort="true" headerClass="sortable" width="30%"
											titleKey="reportrule.edit">
											<c:if test="${rt[6]!=0}">
											<a href='dataFill.do?method=getReportGuideDetailed&organId=<c:out value="${rt[1]}" />&reportId=<c:out value="${rt[0]}" />&reportDate=<c:out value="${dataDate }"/>&targetTable=<c:out value="${rt[5]}" />'><fmt:message key="view.xxxx"/></a>&nbsp;&nbsp;								
											</c:if>
											<c:if test="${rt[4]!=0}">
													<a href='dataFill.do?method=editReportDataEntryForDataInput&openmode=_self&canEdit=1&levelFlag=0&organId=<c:out value="${rt[1]}" />&reportDate=<c:out value="${dataDate }"/>&reportId=<c:out value="${rt[0]}" />&flag2=1'><fmt:message key="view.sjbl"/></a>&nbsp;&nbsp;
											</c:if>
										</display:column>
									</display:table>
									<script type="text/javascript">
										<!--
										//	highlightTableRows("rt");
										//-->
									</script>
								</form>
							</div>
						</td>
					</tr>
					<tr height=17>
						<td></td>
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
		df.target = tg
		df.showret.value = sr
		df.submit()
	}
</script>
</body>
</html>
