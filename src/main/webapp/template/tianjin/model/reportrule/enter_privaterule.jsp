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
<c:set var="reportButton">
	<fmt:message key="place.select"/>
</c:set>
<c:set var="colNames">
	<fmt:message key="exportXML.tree.org.name"/>
</c:set>
<c:set var="reportTreeURL">
	<c:out value="${hostPrefix}"/><c:url value='/reportView.do?method=repTreeAjax${params}'/>
</c:set>
<c:set var="targetTreeURL">
	<c:out value="${hostPrefix}" /><c:url value='/integratedQuery.do?method=targetTree' />
</c:set>
<c:set var="targetButton">
	<fmt:message key="integratedQuery.button.targetSelect"/>
</c:set>
<c:set var="displaySumType">
	<%= FuncConfig.getProperty("reportview.showCollectType")%>
</c:set>
<c:set var="studentLoanReportId">
	<%= FuncConfig.getProperty("studentloan.reportid")%>
</c:set>
<html>
<script type="text/javascript">
function forbiddon(){
	document.onkeydown=function(){if(event.keyCode==8)return false;};
	
}
</script>
<head>
<title><fmt:message key="view.blgzpz"/></title>
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
</head>

<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0" onclick="suggestclose()" onload="forbiddon();">

<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
    <td width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td> 
				<p style="margin-top: 3"><font class=b><b><fmt:message key="view.ywzbgzpi"/> >> <fmt:message key="view.yssjgzpz"/></b></font></p>
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
	<form action="reportrule.do?method=inputExtraCondition" method="post" name="reportViewForm" id="reportViewForm">
		<table width="90%" border="0" align="center" cellSpacing="1" cellPadding="0" class="TableBGColor">
		<input type="hidden" name="reportId" id="reportId" value="<c:out value='${reportId}' />"/>
		<input type="hidden" name="reportName" id="reportName" value="<c:out value='${reportName}' />"/>
		<input type="hidden" name="unitCode" value="<c:out value='${unitCode}' />"/>
			
			<tr>
				<td align="right" nowrap class="TdBgColor1"><fmt:message key="select.report"/></td>
				<td class="TdBgColor2">
					<slsint:ActiveXTree left="220" top="325" width="380" height="${activeXTreeHeight }"
			      		xml="${reportTreeURL}"
			      		bgcolor="0xFFD3C0" 
			      		rootid="10000" 
			      		columntitle="${colName}" 
			      		columnwidth="380,0,0,0" 
			      		formname="reportViewForm" 
			      		idstr="reportId" 
			      		namestr="reportName" 
			      		checkstyle = "0" 
			      		filllayer="2" 
			      		txtwidth="300"
			      		buttonname="${reportButton}"
			      		>
			      	</slsint:ActiveXTree>
				</td>
			</tr>
			<tr>
				<td align="right" nowrap class="TdBgColor1"><fmt:message key="select.zhibiao"/></td>
				<td class="TdBGColor2">
					<input type="hidden" name="targetIds" id="targetIds" value="<c:out value='${targetId}'/>" width="300">
		            <input type="hidden" name="targetNames" id="targetNames" value="" width="300">
					<slsint:ActiveXTree left="220" top="325"
						width="380" height="${activeXTreeHeight }"
						xml="${targetTreeURL}&reportId=${reportId}&reportName=${reportName}"
						bgcolor="0xFFD3C0"
						rootid="10000"
						columntitle="target"
						columnwidth="380,0,0,0"
						formname="reportViewForm"
						idstr="targetIds"
						namestr="targetNames"
						checkstyle="1"
						filllayer="2"
						txtwidth="350"
						buttonname="${reportButton}">
					</slsint:ActiveXTree>
				</td>
			</tr>
			<tr>
			<input type="hidden" value="2" name="flag"/>
 				<td height="22" align="right" nowrap class="TdBgColor1"></td>
 				<input type="hidden" value="<c:out value='${levelFlag}'/>" name="levelFlag"/>
					<td class="TdBgColor2"><input type="button" value='<fmt:message key="view.pz"/>' style="width:80;" onclick="editrule()">
					 <!-- &nbsp;&nbsp;&nbsp;&nbsp;<input type="button" value=''<fmt:message key="button.ratifypass"/>' ' style="width:80;" onclick="viewrule()">  --> </td>
			</tr>
			<tr>
				<td width="50" height="16" colspan="2" nowrap class="FormBottom"></td>
			</tr>			
		</table>
	</form>
	<div id="users" style="display:none"></div>	
</td>
</tr>
</table>
</body>

<script type="text/javascript">
var df=document.reportViewForm;	
function changeRep(){
	var repid=df.reportId.value;
	if(repid==null){
		return;
	}
	var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/reportView.do?method=repTreeAjax&levelFlag=2";	
	document.all.objTree1.SetXMLPath(url);
   	document.all.objTree1.Refresh();	
}

function changeTree1(){
	changeRep();
	changetarget();
}
	
function changetarget(){
	var repid=df.reportId.value;
	if(repid==null||repid.length>=5||repid==''){
		return;
	}
	var repname=df.reportName.value;
	var url = "<c:out value="${targetTreeURL}"/>&reportId="+repid+"&reportName="+repname;
	document.all.objTree2.SetXMLPath(url);
   	document.all.objTree2.Refresh();	
}
function changeTree2(){
	changetarget();
}

function editrule(){
	var repid=df.reportId.value;
	var targetIds=df.targetIds.value;
	if(repid==null||""==repid){
		alert('<fmt:message key="flowTip.empty.report"/>');
		return;
	}else if(repid.length>=5){
		alert('<fmt:message key="view.ygxzbb"/>');
		return;
	}else if(targetIds==null||""==targetIds){
		alert('<fmt:message key="view.qxzzb"/>');
		return;
	}
	df.action='<c:out value="${hostPrefix}" /><c:url value="/reportrule.do" />?method=editPriRule';
	df.submit();
}
function viewrule(){
	var df=document.reportViewForm;	
	df.action='<c:out value="${hostPrefix}" /><c:url value="/reportrule.do" />?method=listBasicRule';
	df.submit();
}
function change(){
	changeRep();
	changetarget();
}
var oldHide=Calendar.prototype.hide;
Calendar.prototype.hide = function () {oldHide.apply(this);	change()}
</script>
</html>
