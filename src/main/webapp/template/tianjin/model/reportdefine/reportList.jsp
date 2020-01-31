<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
    <head>
        <%-- Include common set of meta tags for each layout --%>
       <%--  <%@ include file="/ps/framework/common/meta.jsp" %> --%>
        <title><fmt:message key="webapp.prefix"/></title>
        
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
     
        
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
				 key="navigation.reportdefine.reportlist"/></b></font></p>
          </td>
		  <td></td> 
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td width="100%" bgcolor="#EEEEEE" valign="top">
    <br>
	
	<html:hidden property="again" value="0"/>
	<p style="margin-right: 30; margin-top: 8; margin-bottom: 8">
		&nbsp;&nbsp;
			<a href="reportAction.do?method=edit"/>
			        <fmt:message key="button.add"/>
			    </a><c:if test="${user.isAdmin == 2 }">   | 
			    <a href="reportAction.do?method=sortReport"/>
			        <fmt:message key="button.sort"/>
			    </a></c:if>
			    <!-- 
			    |
			    <a href="importExport.do?method=exportSystemData&reportType=<c:out value="${ctype}"/>"/>
				<fmt:message key="importexport.label.export"/>
				</a>
			     -->
	</p>
	    <div id="screen">
			<display:table name="reportList" cellspacing="0" cellpadding="0"  
				    requestURI="" id="type" width="100%" 
				    pagesize="30" styleClass="list userList">
				    <%-- Table columns --%>
				    <c:if test="${user.pkid == type.createId && user.isAdmin != 2}">
				    <display:column  sort="true" headerClass="sortable"
				    	width="25%" titleKey="common.list.reportname">
				    	<c:out value="${type.name }"/>&nbsp;
				    	<a href="reportAction.do?method=reportAuthor&reportId=<c:out value="${type.pkid }"/>"><fmt:message key="report.author"/></a>
				    </display:column>
				    </c:if>
				    <c:if test="${user.pkid != type.createId || user.isAdmin == 2}">
				    <display:column  sort="true" headerClass="sortable"
				    	width="25%" titleKey="common.list.reportname">
				    	<c:out value="${type.name }"/>
				    </display:column>
				    </c:if>
				    <display:column  sort="true" headerClass="sortable"
				    	width="15%" titleKey="reportdefine.report.phyTable1">
				    	<c:out value="${type.phyTable }"/>
				    </display:column>
				    <display:column property="code"   sort="true" headerClass="sortable"
				    	width="9%" titleKey="common.list.reportcode"/>				    
				    <display:column property="repname"   sort="true" headerClass="sortable"
				    	width="15%" titleKey="common.list.reporttypename"/>
				    	
				    <c:if test="${user.pkid == type.createId||user.isAdmin==2}">
				    <display:column width="4%">
				        <a href="reportAction.do?method=edit&reportId=<c:out value="${type.pkid}"/>">
				        	<fmt:message key="button.edit"/>
				        </a>
				    </display:column >	
				    <display:column width="4%">
				        <a href="reportAction.do?method=del&reportId=<c:out value="${type.pkid}"/>" onClick="if(confirm('<fmt:message key="pub.delreport.tip"/>')){ return true;} else{ return false;}">
				        	<fmt:message key="button.delete"/>
				        </a>
				    </display:column>
				    				    
				    <display:column width="6%">
				        <a href="reportTargetAction.do?method=enter&reportId=<c:out value="${type.pkid}"/>&tableName=<c:out value="${type.phyTable }"/>">
				        	<fmt:message key="srcdata.manager.menu"/>
				        </a>
				    </display:column>
				  <%--
				    <display:column width="15%">
				        <select name="funId" id="funId<c:out value="${type.pkid}"/>" style="width:100;">
			            	<c:forEach var="func" items="${funIds}">
			            	   <option value="<c:out value="${func.dicid}"/>" >
       								<c:out value="${func.dicname}"/>
								</option>
							</c:forEach>
			            </select>
				    </display:column>			
				   <display:column width="8%">	
				    	<a href="javascript:onclick=repConfig(<c:out value="${type.pkid}"/>)">
				        	<fmt:message key="reportDefine.editConfig"/>
				        </a>   
				    </display:column>
				    --%> 
				    </c:if>
				    <c:if test="${!(user.pkid==type.createId||user.isAdmin==2)}">
				    <display:column width="4%">
				        
				    </display:column >	
				    <display:column width="4%">
				        
				    </display:column>				    
				    <display:column width="7%">
				        
				    </display:column>				    
				    <display:column width="6%">
				       
				    </display:column>
				    <display:column width="11%">
				       
				    </display:column>			
				    <display:column width="5%">	
				    	 
				    </display:column>
				    </c:if>
				</display:table>
				
	    </div>
	    

</body>
<script type="text/javascript">
	function repConfig(repId){
	    var funcId = document.getElementById("funId"+repId).value;	    
		var url = "reportConfigAction.do?method=enter&reportId="+repId+"&funcId="+funcId;
		window.location = url;
	}

</script>
</html>