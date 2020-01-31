<!-- /groupmanage/showGroupUsers.j -->
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
<%@ page
	import="com.krm.ps.sysmanage.reportdefine.vo.ReportType,org.apache.struts.util.LabelValueBean,java.util.*"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<html>
<head>
<%
String dicname = (String)request.getAttribute("dicname");
String dicid = (String)request.getAttribute("dicid");

%>
<title><fmt:message key="webapp.prefix"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
<link rel="stylesheet" href="<c:url value='${cssPath}/css.css" type="text/css'/>"/>
        <script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
        
		<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/common/jscalendar/calendar-win2k-1.css'/>" title="win2k-cold-1" /> 
		<script type="text/javascript" src="<c:url value='/common/jscalendar/calendar.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/common/jscalendar/lang/zh-cn.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/common/jscalendar/calendar-setup.js'/>"></script>
		<link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>   
<script type="text/javascript">
		 window.parent.document.getElementById("ppppp").value= "<fmt:message key="usermanage.sysadmin.menu"/><fmt:message key="navigation.arrowhead"/><fmt:message key="groupmanage.inneruser.title"/>";  
</script>		
</head>
<body leftmargin="0"  >
<div class="navbar">
	<table>
		<tr>
			<td>
			<a href="groupReportAction.do?method=list" class="easyui-linkbutton"  style="text-decoration: none;" ><fmt:message key='groupmanage.error.return'/></a>
			<!--  	<a href="groupReportAction.do?method=list"/>
        		<fmt:message key="groupmanage.error.return"/>-->
    			</a>
			</td>
			<td>
			<a href="groupReportAction.do?method=showGroupUser&dicid=<%=dicid%>&dicname=<%=dicname%>" class="easyui-linkbutton"  style="text-decoration: none;" ><fmt:message key='groupmanage.showgroupuser.button'/></a>
				<!--  <a href="groupReportAction.do?method=showGroupUser&dicid=<%=dicid%>&dicname=<%=dicname%>"/>
       				 <fmt:message key="groupmanage.showgroupuser.button"/>-->
			</td>
			<td>
			<a href="groupReportAction.do?method=showGroupPurview&dicid=<%=dicid%>&dicname=<%=dicname%>" class="easyui-linkbutton"  style="text-decoration: none" ><fmt:message key='groupmanage.showgrouppurview.button'/></a>
				<!-- <a href="groupReportAction.do?method=showGroupPurview&dicid=<%=dicid%>&dicname=<%=dicname%>"/>
       			 <fmt:message key="groupmanage.showgrouppurview.button"/>-->
			</td>
		</tr>
	</table>
</div>
    <table>
	<tr>
	<td>
        &nbsp;&nbsp;<fmt:message key="groupmanage.inneruser.displayinfo"/>
	</td>
	<td>
	</tr>
	</table>


	    <center>
	    <div id="screen" style=" width:98%">
			<display:table name="groupUsers" cellspacing="0" cellpadding="0"  
				    requestURI="" defaultsort="1" id="groupUsers" width="100%" 
				    pagesize="18" styleClass="list groupUsers">
				    <%-- Table columns --%>
				    <display:column sort="true" titleKey="groupmanage.del.displayinfo.username" headerClass="sortable" 
				    	width="8%">
				    	<c:out value="${groupUsers.name}"/>
				    </display:column>	

				    <display:column sort="true" titleKey="groupmanage.del.displayinfo.logonname" headerClass="sortable" 
				    	width="8%">
				    	<c:out value="${groupUsers.logonName}"/>
				    </display:column>	  
				    
				    <display:column sort="true" titleKey="groupmanage.del.displayinfo.organid" headerClass="sortable" 
				    	width="8%">
				    	<c:out value="${groupUsers.organId}"/>
				    </display:column>	
				    <display:column sort="true" titleKey="usermanage.sysadmin.list.rolename" headerClass="sortable" 
				    	width="8%">
				    	<c:out value="${groupUsers.roleName}"/>
				    </display:column>
				</display:table>
				<script type="text/javascript">
					<!--
					//	highlightTableRows("groupUsers");
					//-->
				</script>
				
	    </div>
	    
	</center>

</body>
</html>

