<%@ page pageEncoding="UTF-8" %>
<%@ page import="com.krm.ps.sysmanage.usermanage.vo.*"%>
<%@ page import="com.krm.ps.util.SysConfig"%>
<%@ page import="com.krm.ps.util.FuncConfig"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>

<%
   User user = (User)request.getSession().getAttribute("user");
   String orgId = user.getOrganId();
%>
<jsp:useBean id="now" class="java.util.Date" /> 
<c:set var="colName">
	<fmt:message key="common.list.reportname"/>
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
<c:set var="studentLoanReportId">
	<%= FuncConfig.getProperty("studentloan.reportid")%>
</c:set>
<html>
<head>
<title><fmt:message key="webapp.prefix"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
<link rel="stylesheet" href="<c:url value='${cssPath}/css.css" type="text/css'/>"/>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${cssPath}/common.css'/>" />
        <script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
		<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-win2k-1.css'/>" title="win2k-cold-1" /> 
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/lang/zh-cn.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-setup.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/ActiveXTree/ActiveXTree.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/ps/scripts/prototype.js'/>"></script>
		<script src="<c:url value='/ps/uitl/krmtree/jqueryTab/jquery-1.2.4b.js'/>" type="text/javascript"></script>
</head>
<script type="text/javascript">
function checkDate() {
	var paramstartDate = document.getElementById("startDate").value
			.toLocaleString().substring(0, 10);
	var paramstopDate = document.getElementById("stopDate").value
			.toLocaleString().substring(0, 10);
	var pro = document.getElementById("pro").value;   //提示信息文本内容
	if (paramstartDate > paramstopDate) {
		return false;
	}

}
function msg()
	{
		var msg = "<c:out value="${msg}"/>";
		if(msg=="success")
			{
				alert("机构同步成功！");
			}
		else if(msg=="fail")
			{
				alert("机构同步失败，请联系管理员！");
			}
	
	}
	
 	function validation()
 	{
 		var date = document.runModelDataForm.date;
 		if(document.runModelDataForm.date.value == "")
		{
			alert("请选择时间！");
			document.runModelDataForm.date.focus();
		    return false;
		}
 	}

</script>
<body leftmargin="0" bgcolor="#eeeeee" onload="msg()">
<input type="hidden" id="Startd" value="<c:out value='${startDate}'/>">
<input type="hidden" id="Stopd" value="<c:out value='${stopDate}'/>">
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
    <td colspan="2" width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td>
            <p style="margin-top: 3"><font class=b><b><fmt:message key="usermanage.sysadmin.menu"/><fmt:message key="navigation.arrowhead"/>机构同步</b></font></p>                         
          </td>
		  <td></td> 
        </tr>
      </table>
    </td>
  </tr>
  <tr>
  <td width="3%" background="<c:url value="${imgPath}/f05.gif"/>"></td>
    <td width="97%" background="<c:url value="${imgPath}/f05.gif"/>" valign="top">	
    <br>
<html:hidden property="again" value="0"/>
	<form name="runModelDataForm" action="<%=request.getContextPath() %>/organAction.do?method=syncOrgan" method="post">
<table border="0" style="width:100%">
	<tr>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
	</tr>
	<tr>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
	</tr>
  	<tr>
  		<td align="center">
	  		<fmt:message key="runmodel.date"></fmt:message>
			<fmt:formatDate value="${now}" type="both" dateStyle="long" pattern="yyyy-MM-dd" var="today"/> 
	  		<input id="date" name="date" type="text" style="width:80;"
	  			 readonly="readonly" value="<c:out value="${today }"/>">
										 <script
												type="text/javascript">
												Calendar.setup({
													inputField : "date",
													ifFormat : "%Y-%m-%d",
													showsTime : false
												});
											</script>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="submit" name="mitsearcher" style="width: 60px;height: 20px;margin: auto;" value="同步机构" onclick="return validation()">
  		
  	</td>
  	</tr>
  										
  	</table>
  	</form>	
	<center>
	
	    <div id="screen">
			<display:table name="rmd" cellspacing="0" cellpadding="0"  
				    requestURI="" defaultsort="1" id="rmd" width="100%" 
				    pagesize="18" styleClass="list logList">
				    <display:column sort="true" titleKey="runmodel.createPerson" headerClass="sortable" 
				    	width="10%">
				    	<c:out value="${rmd.createPerson}"/>
				    </display:column>
				    <display:column sort="true" titleKey="runmodel.createTime" 
				    	headerClass="sortable" width="12%">
				    	<c:out value="${rmd.creatTime}"/>
				    	</display:column>
					 <display:column sort="true" titleKey="runmodel.status" headerClass="sortable" width="12%">
						    <c:choose>
						    	<c:when test="${rmd.status==2 }">
								    	<c:out value="同步成功"/>
						    	</c:when>
						    	<c:when  test="${rmd.status==3 }">
						    			<c:out value="同步失败"/>
						    	</c:when>
						    </c:choose>
				   </display:column>
				</display:table>
				<script type="text/javascript">
					<!--
						highlightTableRows("reslog");
					//-->
				</script>
	    </div>
	</center>
</td>
</tr>
</table>
</body>

</html>