<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<html>
<head>
<title><fmt:message key="webapp.prefix"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
<link rel="stylesheet" href="<c:url value='${cssPath}/css.css" type="text/css'/>"/>
        <script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
</head>
<body leftmargin="0" bgcolor=#eeeeee>
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
    <td colspan="2" width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td>
            <p style="margin-top: 3"><font class=b><b><fmt:message key="usermanage.sysadmin.menu"/><fmt:message key="navigation.arrowhead"/><fmt:message key="usermanage.role.main"/></b></font></p>                         
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
    <br></br>
    <a href="userRoleAction.do?method=entryUserRole&flag=1&systemId=0"><fmt:message key="unit.add"/></a>
    <!-- 
    &nbsp;|&nbsp;
    <a href="unitAction.do?method=taxisUnit" ><fmt:message key="unit.taxis"/></a>
    -->
	<center>
	    <div id="screen">
			<display:table name="role" cellspacing="0" cellpadding="0"  
				    requestURI="" defaultsort="1" id="roles" width="100%" 
				    pagesize="12" styleClass="list roles">
				    <%-- Table columns --%>
				    <display:column sort="true" titleKey="role.type" headerClass="sortable" 
				    	width="10%">
				    	<c:out value="${roles.roleType}"/>
				     </display:column>
				        
				    <display:column titleKey="role.name" sort="true" 
				    	headerClass="sortable" width="10%">
				    	<c:out value="${roles.name}"/>
				    </display:column>
				    
				   <display:column titleKey="role.description" sort="true" 
				    	headerClass="sortable" width="20%">
	                    <c:out value="${roles.description}"/>
				   </display:column>
				   
				    <display:column titleKey="role.level" sort="true" 
				    	headerClass="sortable" width="20%">
	                    <c:out value="${roles.roleLevel}"/>
				   </display:column>
				   
				   <display:column sort="true" headerClass="sortable"
			    		width="10%" nowrap="no" >
			       <a href="userRoleAction.do?method=entryUserRole&flag=2&roleType=<c:out value="${roles.roleType}"/>&pkid=<c:out value="${roles.pkid}"/>&systemId=0" >
	                    <fmt:message key="role.edit"/>
	               </a>&nbsp;
			       <a href="userRoleAction.do?method=delUserRole&roleType=<c:out value="${roles.roleType}"/>&pkid=<c:out value="${roles.pkid}"/>" onClick="if(confirm('<fmt:message key="usermanage.organ.form.confirmdel"/>')){ return true;} else{ return false;}">
			       
	                    <fmt:message key="role.delete"/>
	               </a>&nbsp;
				   </display:column>  
				</display:table>
				<script type="text/javascript">
					<!--
						highlightTableRows("roles");
					//-->
				</script>				
	    </div>	    
	</center>
</td>
</tr>
</table>
</body>
</html>