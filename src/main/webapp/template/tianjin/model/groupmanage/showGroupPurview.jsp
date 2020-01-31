<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
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
        <script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
</head>
<body leftmargin="0" bgcolor=#eeeeee onload="forbiddon();">
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
            <p style="margin-top: 3"><font class=b><b><fmt:message key="usermanage.sysadmin.menu"/><fmt:message key="navigation.arrowhead"/><fmt:message key="groupmanage.showgrouppurview.title"/></b></font></p>                         
          </td>
		  <td></td> 
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td width="100%" background="<c:url value="${imgPath}/f05.gif"/>" valign="top">
    <p>
    </p>

	<br><br>
	
	<table>
	<tr>
	<td>&nbsp;&nbsp;
	<a href="groupReportAction.do?method=list"/>
        <fmt:message key="groupmanage.error.return"/>
    </a>
	</td>
	<td> | 
    <a href="groupReportAction.do?method=showGroupUser&dicid=<%=dicid%>&dicname=<%=dicname%>"/>
        <fmt:message key="groupmanage.showgroupuser.button"/>
    </a>
    </td>
    <td> | 
    <a href="groupReportAction.do?method=showGroupPurview&dicid=<%=dicid%>&dicname=<%=dicname%>"/>
        <fmt:message key="groupmanage.showgrouppurview.button"/>
    </a>
	</td>
    </tr>
    </table>
    <br>
    
 <html:form  action="groupReportAction" method="post"> 
<table  align="center" class="TdBGColor2" width="100%" border="0" cellSpacing=1 cellPadding=5>
<P style="FONT-SIZE: 12px; COLOR: #0000ff"><html:errors/></P>


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
									highlightTableRows("userReports");
								//-->
							</script>
				    </div>
				</td>
			</tr>
	
<tr height=17><td colspan="4"></td></tr>

</html:form>
</table>
</td>
</tr>
</table>
</body>
</html>

