<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<html>
<head>
<title><fmt:message key="webapp.prefix"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
<link rel="stylesheet" href="<c:url value='${cssPath}/css.css" type="text/css'/>"/>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${cssPath}/common.css'/>" />
        <script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
        
		<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/ps/uitl/krmtree/common/jscalendar/calendar-win2k-1.css'/>" title="win2k-cold-1" /> 
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/common/jscalendar/calendar.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/common/jscalendar/lang/zh-cn.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/common/jscalendar/calendar-setup.js'/>"></script>
</head>
<body leftmargin="0" bgcolor=#eeeeee onload="forbiddon();">
<script type="text/javascript">
function forbiddon(){
	document.onkeydown=function(){if(event.keyCode==8)return false;};
}
</script>
<table border="0" cellpadding="1" cellspacing="1" width="100%" height="100%" >
  <tr>
    <td width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td>
            <p style="margin-top: 3"><font class=b><b><fmt:message key="usermanage.sysadmin.menu"/><fmt:message key="navigation.arrowhead"/><fmt:message key="groupmanage.title"/></b></font></p>                         
          </td>
		  
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td width="100%" background="<c:url value="${imgPath}/f05.gif"/>" valign="top">
    <br>
 
	<html:hidden property="again" value="0"/>
	<table>
	<tr>
	<td>
    <a href="groupReportAction.do?method=addGroupReport"/>
        <fmt:message key="groupmanage.main.fun.add"/>
    </a>
	</td>
	<td> | 
    <a href="groupReportAction.do?method=taxisGroup"/>
        <fmt:message key="groupmanage.main.fun.sort"/>
    </a>
    </td>
    </tr>
    </table>
	<center>
	    <div id="screen">
			<display:table name="groupList" cellspacing="0" cellpadding="0"  
				    requestURI="" defaultsort="1" id="groups" width="100%" 
				    pagesize="10" styleClass="list groupList">
				    <%-- Table columns --%>		
				    <display:column sort="true" titleKey="groupmanage.main.displaynumber" headerClass="sortable" 
				    	width="5%">
				    	<c:out value="${groups.disporder}"/>
				    </display:column>			    
				    <display:column sort="true" titleKey="groupmanage.main.groupid" headerClass="sortable" 
				    	width="5%">
				    	<c:out value="${groups.dicid}"/>
				    </display:column>	
				    
				    <display:column sort="true" titleKey="groupmanage.main.groupname" headerClass="sortable" 
				    	width="8%">
				    	<c:out value="${groups.dicname}"/>
				    </display:column>	

			 		<display:column sort="true" headerClass="sortable"  titleKey="groupmanage.main.fun.oper" 
			  	  	width="18%">
			    	<a href="groupReportAction.do?method=editGroupReport&dicid=<c:out value="${groups.dicid}"/>">
			        	<fmt:message key="groupmanage.main.fun.edit"/>
			        </a>&nbsp;
			        <a href="groupReportAction.do?method=deleteGroupReport&dicid=<c:out value="${groups.dicid}"/>">
			        	<fmt:message key="groupmanage.main.fun.del"/>
			        </a>&nbsp;			        
			        <a href="groupReportAction.do?method=showGroupPurview&dicid=<c:out value="${groups.dicid}"/>">
			        	<fmt:message key="groupmanage.main.fun.displayuser"/>
			        </a>&nbsp;
					<!-- 
						zuoshaojie 2007-12-17 enabe flowTip
					-->
	               <c:if test="${flowTip ==1}">
			       <a href="flowTip.do?method=enterFlowTipUsers&userGroupId=<c:out value="${groups.dicid}"/>" >
	                    <fmt:message key="flowtip.list.right"/>
	               </a>&nbsp;
	               </c:if>
			    </display:column>   
				    
				</display:table>
				<script type="text/javascript">
					<!--
						highlightTableRows("groups");
					//-->
				</script>
				
	    </div>
	    
	</center>

</td>
</tr>
</table>
</body>
</html>



