<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
    <head>
        <%-- Include common set of meta tags for each layout --%>
        <%@ include file="/ps/framework/common/meta.jsp" %>
        <title><fmt:message key="webapp.prefix"/></title>
        <link rel="stylesheet" href="<c:url value='${cssPath}/css.css" type="text/css'/>"/>
		<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
        <link rel="stylesheet" type="text/css" media="all" href="<c:url value='${cssPath}/helptip.css'/>" />
        <link rel="stylesheet" type="text/css" media="print" href="<c:url value='${cssPath}/print.css'/>" /> 
        <script type="text/javascript" src="<c:url value='/ps/scripts/prototype.js'/>"></script> 
        <script type="text/javascript" src="<c:url value='/ps/scripts/effects.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/ps/scripts/helptip.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>  
    </head>
    <body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
	<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
    <td width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td>
				<p style="margin-top: 3"><font class=b><b><fmt:message
				 key="navigation.reportmanage.reporttype"/></b></font></p>
          </td>
		  <td></td> 
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td width="100%" bgcolor="#EEEEEE" valign="top">
    <p style="margin-right: 30; margin-top: 8; margin-bottom: 8">
		&nbsp;&nbsp;<a href="reportTypeAction.do?method=toEdit"/><fmt:message key="button.add"/></a>
		&nbsp;&nbsp;|
		&nbsp;&nbsp;<a href="reportTypeAction.do?method=taxisType"/><fmt:message key="button.order"/></a>
	</p>
	<center>
	    <div>
			<display:table name="typeList" cellspacing="0" cellpadding="0"  
				    requestURI="" id="type" width="96%" 
				    pagesize="20" styleClass="list userList">
				    <%-- Table columns --%>
				    <display:column sort="true" titleKey="reportdefine.reportType.name1" headerClass="sortable" 
				    	width="40%">
				    	<c:out value="${type.name}"/>
				    </display:column>
	
				    <c:if test="${type.createId == createId || isAdmin == 2 }">
				    <display:column sort="true" headerClass="sortable" 
				    	width="6%">
				        <a href="reportTypeAction.do?method=edit&typeid=<c:out value="${type.pkid}"/>">
				        	<fmt:message key="button.edit"/>
				        </a>
				    </display:column>
				    
				    <display:column sort="true" headerClass="sortable" 
				    	width="6%">
				        <a href="reportTypeAction.do?method=del&typeid=<c:out value="${type.pkid}"/>" onClick="if(confirm('<fmt:message key="author.alert.delAlert"/>')){ return true;} else{ return false;}">
				        	<fmt:message key="button.delete"/>
				        </a>
				    </display:column>
				    </c:if>
				    <c:if test="${!(type.createId == createId || isAdmin == 2) }">
				    <display:column sort="true" headerClass="sortable" 
				    	width="6%">
				       
				    </display:column>
				    
				    <display:column sort="true" headerClass="sortable" 
				    	width="6%">
				        
				    </display:column>
				    </c:if>
				</display:table>
				<script type="text/javascript">
					<!--
						highlightTableRows("type");
					//-->
				</script>
				
	    </div>
	    
	</center>
</td>
</tr>
</table>
</body>
</html>