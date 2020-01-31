<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ page import="com.krm.ps.util.FuncConfig"%>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<c:set var="colName">
	<fmt:message key="organTree.colName"/>
</c:set>
<c:set var="orgTreeURL">
	<c:out value="${hostPrefix}"/><c:url value='/organTreeAction.do?method=tree'/>
</c:set>
<c:set var="orgButton">
	<fmt:message key="organTree.area"/>
</c:set>

<c:set var="showQueryBtn">
	<%=FuncConfig.getProperty("useroper.showquerybtn","false") %>
</c:set>

<html>
<head>
<title><fmt:message key="webapp.prefix"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
<link rel="stylesheet" href="<c:url value='${cssPath}/css.css" type="text/css'/>"/>
        <script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
        
		<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-win2k-1.css'/>" title="win2k-cold-1" /> 
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/lang/zh-cn.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-setup.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/ActiveXTree/ActiveXTree.js'/>"></script>
		
</head>
<body leftmargin="0" bgcolor=#eeeeee  onload="forbiddon();">
<script type="text/javascript">
function forbiddon(){
	document.onkeydown=function(){if(event.keyCode==8)return false;};
}
</script>
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
    <td colspan="2" width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td>
            <p style="margin-top: 3"><font class=b><b><fmt:message key="usermanage.sysadmin.menu"/><fmt:message key="navigation.arrowhead"/><fmt:message key="usermanage.sysadmin.menu.resetpassword"/></b></font></p>                         
          </td>
		  <td></td> 
        </tr>
      </table>
    </td>
  </tr>
  <tr>
  <td width="3%" background="<c:url value="${imgPath}/f05.gif"/>"></td>
    <td width="97%" background="<c:url value="${imgPath}/f05.gif"/>" valign="top">
    <p>
    </p>	
    <br><br>
    <form action="" method="post" name="userRefForm" id="userRefFormid">
    <input type="hidden" name="areaId" value="<c:out value="${areaCode}"/>"/>
      						<input type="hidden" name="areaName"/>
      						<slsint:ActiveXTree left="220" top="325" width="260" height="${activeXTreeHeight }"
					      		xml="${orgTreeURL}" 
					      		bgcolor="0xFFD3C0" 
					      		rootid="${rootId}"
					      		columntitle="${colName}" 
					      		columnwidth="260,0,0,0" 
					      		formname="userRefForm" 
					      		idstr="areaId" 
					      		namestr="areaName" 
					      		checkstyle = "0" 
					      		filllayer="2" 
					      		txtwidth="260"
					      		buttonname="${orgButton}">
    						</slsint:ActiveXTree>	
    						
    <c:if test="${showQueryBtn == 'true'}">
        <input type="button"  value="&nbsp;&nbsp;&nbsp;<fmt:message key="button.search"/>&nbsp;&nbsp;&nbsp;" onclick="queryByname()"/><input type="text" onfocusin="qinchuText()" id="usernameid" name="username" value="<fmt:message key="useraction.userlistref.inputlogonname"/>" size="20" />
	</c:if>
	
	<center>
	    <div id="screen">
			<display:table name="userList" cellspacing="0" cellpadding="0"  
				    requestURI="" defaultsort="1" id="users" width="100%" 
				    pagesize="18" styleClass="list userList">
				    <%-- Table columns --%>
				    <display:column sort="true" titleKey="usermanage.sysadmin.list.username" headerClass="sortable" 
				    	width="15%">
				    	<c:out value="${users.name}"/>
				    </display:column>
				        
				    <display:column property="logonName" sort="true" titleKey="usermanage.sysadmin.list.logonname" 
				    	headerClass="sortable" width="12%"/>
				    	
				    <display:column property="roleName" sort="true" titleKey="usermanage.sysadmin.list.rolename"
				    	headerClass="sortable" width="12%"/>
	
				    
				    <display:column sort="true" headerClass="sortable" 
				    	width="6%">
				        <a href="userAction.do?method=refpassword&userid=<c:out value="${users.pkid}"/>">
				        	<fmt:message key="usermanage.sysadmin.refpassword"/>
				        </a>
				    </display:column>
				</display:table>
				<script type="text/javascript">
					<!--
						highlightTableRows("users");
					//-->
				</script>
				
	    </div>
	    
	</center>
</form>
</td>
</tr>
</table>
</body>

</html>
<script type="text/javascript">
function changeTree1(){
	var areaCode = document.userRefForm.areaId.value;
	var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/userAction.do?method=refreshpassword&areaCode="+areaCode;
	document.userRefForm.action = url;
	document.userRefForm.submit();
}

function qinchuText()
{
	document.getElementById("usernameid").value="";
}

function queryByname()
{
	var username = document.getElementById("usernameid").value;
	var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/userAction.do?method=queryUserByName";
	document.userRefForm.action = url;
	document.userRefForm.submit();
}

</script>
