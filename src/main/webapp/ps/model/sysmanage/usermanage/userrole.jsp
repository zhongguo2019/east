<!-- /ps/model/sysmanage/usermanage/userrole. -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<html>
<head>
<title><fmt:message key="webapp.prefix"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">

<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${displaytag12Path}/displaytag.css'/>" />
 <link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/> 
        
    <!--  <script type="text/javascript">   
        window.parent.document.getElementById("ppppp").value= "<fmt:message key="usermanage.sysadmin.menu"/><fmt:message key="navigation.arrowhead"/><fmt:message key="usermanage.role.main"/>";
     </script>  -->  
</head>
<body leftmargin="0" >
<div class="navbar">
	<a href="userRoleAction.do?method=entryUserRole&flag=1&systemId=0" class="easyui-linkbutton buttonclass2"  data-options="iconCls:'icon-add'"  ><fmt:message key='unit.add'/></a>
</div>
    
    <!--  <a href="userRoleAction.do?method=entryUserRole&flag=1&systemId=0"><fmt:message key="unit.add"/></a>-->
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
				   
				   <display:column sort="true" headerClass="sortable" titleKey="button.operation"
			    		width="10%" nowrap="no" >
			       <a href="userRoleAction.do?method=entryUserRole&flag=2&roleType=<c:out value="${roles.roleType}"/>&pkid=<c:out value="${roles.pkid}"/>&systemId=0" style="text-decoration: none;">
	                    <fmt:message key="role.edit"/>
	               </a>&nbsp;&nbsp;&nbsp;
			       <a href="userRoleAction.do?method=delUserRole&roleType=<c:out value="${roles.roleType}"/>&pkid=<c:out value="${roles.pkid}"/>" style="text-decoration: none;" onClick="if(confirm('<fmt:message key="usermanage.organ.form.confirmdel"/>')){ return true;} else{ return false;}">
			       
	                    <fmt:message key="role.delete"/>
	               </a>&nbsp;
				   </display:column>  
				</display:table>
								
	    </div>	    
	</center>

</body>
</html>