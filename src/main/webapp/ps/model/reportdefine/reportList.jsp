<!-- /ps/model/reportdefine/reportList. -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
    <head>
        <%@ include file="/ps/framework/common/meta.jsp" %>
        <title><fmt:message key="webapp.prefix"/></title>
		<link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>
        <link rel="stylesheet" type="text/css" media="all" href="<c:url value='${displaytag12Path}/displaytag.css'/>" />
       <!--  <script type="text/javascript">
window.parent.document.getElementById("ppppp").value= "<fmt:message key="navigation.reportdefine.reportlist"/>";
</script> -->
    </head>
<body >

<div class="navbar">
<%-- 	<table>
		<tr>
			<td>
			<a href="reportAction.do?method=edit" class="easyui-linkbutton"   data-options="iconCls:'icon-add'"  ><fmt:message key='button.add'/></a>
			</td>
			<c:if test="${user.isAdmin == 2 }">
			<td>
				<a href="reportAction.do?method=sortReport&name=<c:out value='${ name}'/>&code=<c:out value='${ code}'/>&reportType=<c:out value='${ reportType}'/>&frequency=<c:out value='${ frequency}'/>&beginDate=<c:out value='${ beginDate}'/>&endDate=<c:out value='${ endDate}'/>&flag111=000" class="easyui-linkbutton"  data-options="iconCls:'icon-sort'"  ><fmt:message key="button.sort"/></a>
			</td>		
			</c:if>
			
		</tr>
	</table>
		 --%>
		
		
		
		<!--	<a href="reportAction.do?method=edit"/>
			        <fmt:message key="button.add"/>
			    </a>
			    <a href="reportAction.do?method=sortReport"/>
			        <fmt:message key="button.sort"/>
			    </a>  -->
	
	</div>
	<table border="0" cellpadding="0" cellspacing="0" width="100%" height="90%" style="overflow-y:auto; overflow-x:hidden ">
  
  <tr>
    <td width="100%" valign="top">
	
	<html:hidden property="again" value="0"/>
	
	    <div id="screen">
			<display:table name="reportList" cellspacing="0" cellpadding="0"  
				    requestURI="" id="type" width="100%" 
				    pagesize="30" styleClass="list userList">
				    <%-- Table columns --%>
				    <c:if test="${user.pkid == type.createId && user.isAdmin != 2}">
				    <display:column  sort="true" headerClass="sortable"
				    	width="25%" titleKey="common.list.reportname">
				    	<c:out value="${type.name }"/>&nbsp;
				    <%-- 	<a href="reportAction.do?method=reportAuthor&reportId=<c:out value="${type.pkid }"/>"><fmt:message key="report.author"/></a> --%>
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
				   <%--  <display:column width="4%">
				        <a href="reportAction.do?method=edit&reportId=<c:out value="${type.pkid}"/>">
				        	<fmt:message key="button.edit"/>
				        </a>
				    </display:column >	
				    <display:column width="4%">
				        <a href="reportAction.do?method=del&reportId=<c:out value="${type.pkid}"/>" onClick="if(confirm('<fmt:message key="pub.delreport.tip"/>')){ return true;} else{ return false;}">
				        	<fmt:message key="button.delete"/>
				        </a>
				    </display:column> --%>
				    				    
				    <display:column width="6%">
				        <a href="reportTargetAction.do?method=enter&reportId=<c:out value="${type.pkid}"/>&tableName=<c:out value="${type.phyTable }"/>">
				        	<fmt:message key="srcdata.manager.menu"/>
				        </a>
				    </display:column>
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