<!--/groupmanage/showGroupPurview.j  -->
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
<%@ page
	import="com.krm.ps.sysmanage.reportdefine.vo.ReportType,org.apache.struts.util.LabelValueBean,java.util.*"%>
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
        
<link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>        
</head>
<body leftmargin="0"  onload="forbiddon();">
<script type="text/javascript">
function forbiddon(){
	document.onkeydown=function(){if(event.keyCode==8)return false;};
}
</script>
<script type="text/javascript">
		 window.parent.document.getElementById("ppppp").value= "<fmt:message key="usermanage.sysadmin.menu"/><fmt:message key="navigation.arrowhead"/><fmt:message key="groupmanage.showgrouppurview.title"/>";  
</script>
<div class="navbar">
	<table>
		<tr>
			<td>
			<a href="groupReportAction.do?method=list" class="easyui-linkbutton"  style="text-decoration: none" ><fmt:message key='groupmanage.error.return'/></a>
			<!--  	<a href="groupReportAction.do?method=list"/>
        		<fmt:message key="groupmanage.error.return"/>-->
    			</a>
			</td>
			<td>
			<a href="groupReportAction.do?method=showGroupUser&dicid=<%=dicid%>&dicname=<%=dicname%>" class="easyui-linkbutton" style="text-decoration: none"  ><fmt:message key='groupmanage.showgroupuser.button'/></a>
				<!--  <a href="groupReportAction.do?method=showGroupUser&dicid=<%=dicid%>&dicname=<%=dicname%>"/>
       				 <fmt:message key="groupmanage.showgroupuser.button"/>-->
			</td>
			<td>
			<a href="groupReportAction.do?method=showGroupPurview&dicid=<%=dicid%>&dicname=<%=dicname%>" class="easyui-linkbutton" style="text-decoration: none"  ><fmt:message key='groupmanage.showgrouppurview.button'/></a>
				<!-- <a href="groupReportAction.do?method=showGroupPurview&dicid=<%=dicid%>&dicname=<%=dicname%>"/>
       			 <fmt:message key="groupmanage.showgrouppurview.button"/>-->
			</td>
		</tr>
	</table>
</div>
	

    
 <html:form  action="groupReportAction" method="post"> 
<table  align="center" class="TdBGColor2" width="100%" border="0" cellSpacing=1 cellPadding=5>
<P><html:errors/></P>


			<tr>
				<td align="center" colspan="2">			
				    <div align="center" style=" width:98%">
						<display:table name="userReports" cellspacing="0" cellpadding="0"  
							    pagesize="18" requestURI="" id="userReports" width="100%" 
							    styleClass="list userReports" >
							    <%-- Table columns --%>				     			
							    <display:column sort="true" 
							    	headerClass="sortable" width="10%" 
							    	titleKey="reportdefine.reportType.title1">
							    	<c:out value="${userReports.typeName}"/>
							    </display:column>
							    
							    <display:column sort="true" 
							    	headerClass="sortable" width="5%" 
							    	titleKey="reportdefine.reportType.reportcode">
							    	<c:out value="${userReports.reportId}"/>
							    </display:column>
							    <display:column sort="true" 
							    	headerClass="sortable" width="25%" 
							    	titleKey="reportview.list.report">
							    	<c:out value="${userReports.reportName}"/>
							    </display:column>
							</display:table>
							<script type="text/javascript">
								<!--
								//	highlightTableRows("userReports");
								//-->
							</script>
				    </div>
				</td>
			</tr>
	
<tr height=17><td colspan="4"></td></tr>

</html:form>
</table>

</body>
</html>

