<%@ page import="com.krm.ps.sysmanage.usermanage.vo.*"%>
<%@ page import="com.krm.ps.util.SysConfig"%>
<%@ page import="com.krm.ps.util.FuncConfig"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%
   User user = (User)request.getSession().getAttribute("user");
   String orgId = user.getOrganId();   
%>
<c:set var="colName">
	<fmt:message key="common.list.reportname"/>
</c:set>
<c:set var="reportTreeURL">
	<c:out value="${hostPrefix}"/><c:url value='/reportView.do?method=repTreeAjax&levelFlag=0'/>
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
	<%= FuncConfig.getProperty("reportview.showCollectType")%>
</c:set>
<c:set var="studentLoanReportId">
	<%= FuncConfig.getProperty("studentloan.reportid")%>
</c:set>
<html>
<head>
<title><fmt:message key="webapp.prefix" /></title>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${cssPath}/common.css'/>" />
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-win2k-1.css'/>" title="win2k-cold-1" />
<script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar.js'/>"></script> 
<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/lang/zh-cn.js'/>"></script> 
<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-setup.js'/>"></script>
<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/ActiveXTree/ActiveXTree.js'/>"></script>    
<script type="text/javascript" src="<c:url value='/ps/scripts/prototype.js'/>"></script>
<script src="<c:url value='/ps/framework/common/jqueryTab/jquery-1.2.4b.js'/>" type="text/javascript"></script>
</head>

<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0"  onload=" forbiddon()">
 <script type="text/javascript">
function forbiddon(){
	document.onkeydown=function(){if(event.keyCode==8)return false;};
	
}
</script>
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
    <td width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td>
				<p style="margin-top: 3"><font class=b><b><fmt:message key="view.zbdzcx"/></b></font></p>
          </td>
		  <td></td> 
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td width="100%" bgcolor="#EEEEEE" valign="top">
    <br>
    <br>
	<html:form action="querydz.do?method=queryorgandateList" method="post" >
		<table width="90%" border="0" align="center" cellSpacing="1" cellPadding="0" class="TableBGColor">
		<input type="hidden" name="reportId" id="reportId" value="10000"/>
		<input type="hidden" name="reportName"/>
		<input type="hidden" name="organId" value="<c:out value='${organId}' />"/>
		<input type="hidden" name="organName"/>
		<input type="hidden" name="unitCode" value="<c:out value='${unitCode}' />"/>
		<c:if test="${param.method=='enterViewSearch'}">
            <input type="hidden" name="mode" value="display"/>
		</c:if>
       	<c:if test="${param.method=='enterInputSearch'}">
	        <input type="hidden" name="mode" value="edit"/>
		</c:if>
		<tr>
				<td width="120" align="right" nowrap class="TdBgColor1">
					<fmt:message key="reportview.form.day"/><fmt:message key="reportview.form.qi"/>
				</td>
				<td class="TdBgColor2">
					<input type="hidden" name="showret" value="yes">
					<input id="dataDate" name="dataDate" type="text" value="<c:out
					 value='${dataDate}'/>" style="width:380;" readonly="true" onchange="changeDate();">
				</td>
		  </tr>
			<script type="text/javascript">
				Calendar.setup({
					inputField     :    "dataDate",  
					ifFormat       :    "%Y-%m-%d",   
					showsTime      :    false
				});
			</script>
			<tr>
				<td  class="TdBgColor1" align="right"><fmt:message key="select.organ"/></td>
				<td class="TdBgColor2">
		            <slsint:ActiveXTree left="220" top="325" width="380" 
						height="${activeXTreeHeight }" xml="${orgTreeURL}" bgcolor="0xFFD3C0"
						rootid="<%=orgId %>" columntitle="${colNames}"
						columnwidth="380,0,0,0" formname="queryDzFrom"
						idstr="organId" namestr="organName" checkstyle="0" filllayer="2"
						txtwidth="300" buttonname="${orgButton}">
					</slsint:ActiveXTree>
				</td>
			</tr>	
			<tr>
				<td height="22" align="right" nowrap class="TdBgColor1"></td>
					<td class="TdBgColor2">
				  		<input type="button" value="<fmt:message key="button.creditReporting.query"/>" style="width:80;" onclick="seleast();">
					</td>
			</tr>
			<tr>
				<td width="50" height="16" colspan="2" nowrap class="FormBottom"></td>
			</tr>			
		</table>
	</html:form>
	<div id="users" style="display:none"></div>	
</td>
</tr>
</table>
</body>

<script type="text/javascript">

var df=document.queryDzFrom;


function importDataOne(tg,sr){
	var oReportId = df.reportId;
    var vReportId = oReportId.value;
	if (vReportId==""||vReportId == null){
       alert("<fmt:message key="js.validate.modeltarget"/>");
       return false;
	}
	if (vReportId >= 10000){
	alert("<fmt:message key="js.validate.modeltarget"/>");
		return false;
	}
	
		//df.action="dataFill.do?method=importDataOne&xl="+$("#xl").val();
		df.action="dataFill.do?method=importDataOne";
		df.target=tg;
		df.showret.value=sr;
		df.submit();
	
}





function seleast(){
	df.action="querydz.do?method=queryorgandateList&organId="+df.organId.value+"&dataDate="+df.dataDate.value;
	df.submit();
}



function setUnitCode(){
	var listObj = document.all.selUnit;
	document.all.unitCode.value = listObj.options[listObj.selectedIndex].value;
}

function repTree(){
    var oDate = document.queryDzFrom.dataDate;
	var vDate = oDate.value;
	var oOrganId = document.queryDzFrom.organId;
	var vOrganId = oOrganId.value;	
	var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/reportView.do?method=repTreeAjax&paramdate="+vDate+"&paramorgan="+vOrganId+"&levelFlag=0";
	//1104 system use url:slsint...
	//var url = "<c:out value="${hostPrefix}"/>/slsint/reportView.do?method=repTreeAjax&paramdate="+vDate+"&paramorgan="+vOrganId;
	//bill exchange system use url: slspj...
	//var url = "<c:out value="${hostPrefix}"/>/slspj/reportView.do?method=repTreeAjax&paramdate="+vDate+"&paramorgan="+vOrganId;
	
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
    var oDate = document.queryDzFrom.dataDate;
	var vDate = oDate.value;
	var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/treeAction.do?method=getOrganTreeXML&date="+vDate;

	//1104 system use url:slsint...
	//var url = "<c:out value="${hostPrefix}"/>/slsint/reportView.do?method=orgTree&orgDate="+vDate;
	//bill exchange system use url: slspj...
	//var url = "<c:out value="${hostPrefix}"/>/slspj/reportView.do?method=orgTree&orgDate="+vDate;

	document.all.objTree1.SetXMLPath(url);
   	document.all.objTree1.Refresh();	
}



function reportCheck(){
	oReportId = df.reportId;
    vReportId = oReportId.value;
	if (vReportId==""||vReportId == null){
       alert("<fmt:message key="js.validate.report"/>");
       return false;
	}
	if (vReportId >= 10000){
		return false;
	}
}

function change(){
	changeOrg();
	changeRep();
	setReportList();
}

function changeTree1(){
	changeRep();
}

function changeRep(){
    var oDate = document.queryDzFrom.dataDate;
	var vDate = oDate.value;
	var oOrganId = document.queryDzFrom.organId;
	var vOrganId = oOrganId.value;
	var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/reportView.do?method=repTreeAjax&paramdate="+vDate+"&paramorgan="+vOrganId+"&levelFlag=0";	
	<c:if test="${systemName=='1104'}">
		var url = "<c:out value="${hostPrefix}"/>/slsint/reportView.do?method=repTreeAjax&paramdate="+vDate+"&paramorgan="+vOrganId+"&levelFlag=0";
	</c:if>
	<c:if test="${systemName=='billExchange'}">
		var url = "<c:out value="${hostPrefix}"/>/slspj/reportView.do?method=repTreeAjax&paramdate="+vDate+"&paramorgan="+vOrganId+"&levelFlag=0";
	</c:if>
	document.all.objTree2.SetXMLPath(url);
   	document.all.objTree2.Refresh();	
}

function changeTree2(){
}

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

</html>
