<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ page
	import="com.krm.ps.sysmanage.reportdefine.vo.ReportType,org.apache.struts.util.LabelValueBean,java.util.*"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<html>
<head>
<title><fmt:message key="webapp.prefix"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
<link rel="stylesheet" href="<c:url value='${cssPath}/css.css" type="text/css'/>"/>
        <script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
        
		<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/ps/uitl/krmtree/common/jscalendar/calendar-win2k-1.css'/>" title="win2k-cold-1" /> 
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/common/jscalendar/calendar.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/common/jscalendar/lang/zh-cn.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/common/jscalendar/calendar-setup.js'/>"></script>
</head>
<body leftmargin="0" bgcolor=#eeeeee>
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
    <td width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td>
            <p style="margin-top: 3"><font class=b><b><fmt:message key="usermanage.sysadmin.menu"/><fmt:message key="navigation.arrowhead"/><fmt:message key="groupmanage.del.title"/></b></font></p>                         
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
	</td>
	<td>&nbsp;&nbsp;
	<a href="groupReportAction.do?method=list"/>
        <fmt:message key="groupmanage.error.return"/>
    </a>
	</td>
	</tr>
	<tr>
	<td>
        &nbsp;&nbsp;<b><a style="color:red"><fmt:message key="groupmanage.del.delinfo"/></a></b>
	</td>
	<td> 
	</tr>
	</table>


    	<center>
	    <div id="screen" style=" width:98%">
			<display:table name="userUnderGroup" cellspacing="0" cellpadding="0"  
				    requestURI="" defaultsort="1" id="userUnderGroup" width="100%" 
				    pagesize="18" styleClass="list userUnderGroup">
				    <%-- Table columns --%>
				    <display:column sort="true" titleKey="groupmanage.del.displayinfo.username" headerClass="sortable" 
				    	width="8%">
				    	<c:out value="${userUnderGroup.name}"/>
				    </display:column>	

				    <display:column sort="true" titleKey="groupmanage.del.displayinfo.logonname" headerClass="sortable" 
				    	width="8%">
				    	<c:out value="${userUnderGroup.logonName}"/>
				    </display:column>	  
				    
				    <display:column sort="true" titleKey="groupmanage.del.displayinfo.organid" headerClass="sortable" 
				    	width="8%">
				    	<c:out value="${userUnderGroup.organId}"/>
				    </display:column>	
				</display:table>
				<script type="text/javascript">
					<!--
						highlightTableRows("userUnderGroup");
					//-->
				</script>
				
	    </div>
	    
	</center>
	

</table>
</td>
</tr>
</table>

</body>
</html>

