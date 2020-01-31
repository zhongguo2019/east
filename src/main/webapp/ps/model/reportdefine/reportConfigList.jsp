<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ page import="com.krm.ps.sysmanage.reportdefine.vo.*,java.util.*" %>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
    <head>
        <%-- Include common set of meta tags for each layout --%>
        <%@ include file="/ps/framework/common/meta.jsp" %>
        <title><fmt:message key="webapp.prefix"/></title>
        
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
        <link rel="stylesheet" type="text/css" media="all" href="<c:url value='${cssPath}/helptip.css'/>" />
        <link rel="stylesheet" type="text/css" media="print" href="<c:url value='${cssPath}/print.css'/>" />  
        <script type="text/javascript" src="<c:url value='/ps/scripts/prototype.js'/>"></script> 
        <script type="text/javascript" src="<c:url value='/ps/scripts/effects.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/ps/scripts/helptip.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
        
        <script type="text/javascript">
//window.parent.document.getElementById("ppppp").value= "<fmt:message key="navigation.reportdefine.configlist"/>";
</script>
    </head>
	<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
	
	<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  
  <tr>
    <td width="100%" valign="top">
    <br>
	
	<html:hidden property="again" value="0"/>
	<p style="margin-right: 30; margin-top: 8; margin-bottom: 8">
		&nbsp;&nbsp;
					<c:if test="${configSize == '1'}">
			    	<a href="#" disabled/>
			        	<fmt:message key="button.add"/>
			   			</a> | 
				    </c:if>
				    <c:if test="${configSize != '1'}">
				    	<a href="reportConfigAction.do?method=edit"/>
			        	<fmt:message key="button.add"/>
			   			</a> | 
				    </c:if>
			    <a href="reportAction.do?method=showSelf"/>
			        <fmt:message key="button.back"/>
			    </a> 
	</p>
	<center>
	    <div id="screen">
			<display:table name="configList" cellspacing="0" cellpadding="0"  
				    requestURI="" id="item" width="48%" 
				    pagesize="10" styleClass="list userList">
				    <%-- Table columns --%>
				    		<display:column property="funName" sort="true" 
						    	headerClass="sortable" width="12%" titleKey="reportDefine.funConfig"/>
						    <display:column property="idxId" sort="true" 
						    	headerClass="sortable" width="12%" titleKey="reportDefine.indexConfig"/>
					<c:choose>
						<%-- 
							42 one of new functions
							two new columns(baseReport and relateReport) were added here
							by piliang
						 --%>
				    	<c:when test="${funcId == '42' }">
				    		<display:column property="reportName" sort="true" 
						    	headerClass="sortable" width="12%" titleKey="reportDefine.sourceReport"/>
						    <display:column property="defIntName" sort="true" 
						    	headerClass="sortable" width="12%" titleKey="reportDefine.targetReport"/>
				    	</c:when>
				    	<c:when test="${funcId == '44' }">
				    		<display:column property="reportName" sort="true" 
						    	headerClass="sortable" width="12%" titleKey="riskwarning.reportname"/>
						    <display:column property="defCharName" sort="true"
						    	headerClass="sortable" width="12%" titleKey="riskwarning.organname"/>
						    <display:column property="defIntName" sort="true" 
						    	headerClass="sortable" width="12%" titleKey="riskwarning.rolename"/>
				    	</c:when>
				    	<c:otherwise>
						    <display:column property="defIntName" sort="true" 
						    	headerClass="sortable" width="12%" titleKey="reportDefine.defConfig"/>				    		
						</c:otherwise>
					</c:choose>
				    <c:if test="${!empty param.reportId }">
			    	<c:set var="repId" value="${param.reportId }"/>
				    </c:if>
				    <c:if test="${empty param.reportId }">
				    	<c:set var="repId" value="${reportId }" />
				    </c:if>
				    
				    <c:if test="${item.funId != 14 && item.funId != 17 && item.funId != 18}"> 
				    <c:if test="${item.funId != 20 }">
				    <display:column sort="true" headerClass="sortable" 
				    	width="6%">
				        <a href="reportConfigAction.do?method=edit&reportId=<c:out value="${repId}"/>&configId=<c:out value="${item.pkid}"/>&indexId=<c:out value="${item.idxId}"/>&funId=<c:out value="${item.funId }"/>">
				        	<fmt:message key="button.edit"/>
				        </a>
				    </display:column>
				    </c:if>
				    <c:if test="${item.funId != 41}"> 
				    <display:column sort="true" headerClass="sortable" 
				    	width="6%">
				        <a href="reportConfigAction.do?method=del&reportId=<c:out value="${repId}"/>&configId=<c:out value="${item.pkid}"/>&indexId=<c:out value="${item.idxId}"/>">
				        	<fmt:message key="button.delete"/>
				        </a>
				    </display:column>
				    </c:if>
				 	</c:if> 
				</display:table>
				
	    </div>
	    
	</center>

</body>
</html>