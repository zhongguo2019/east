<!-- /ps/model/reportdefine/reportTypeList. -->
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
    <head>
        <%-- Include common set of meta tags for each layout --%>
        <%@ include file="/ps/framework/common/meta.jsp" %>
        <title><fmt:message key="webapp.prefix"/></title>
		<link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>


        <link rel="stylesheet" type="text/css" media="all" href="<c:url value='${displaytag12Path}/displaytag.css'/>" />
         <script type="text/javascript">
	      //  window.parent.document.getElementById("ppppp").value= "<fmt:message  key="navigation.reportmanage.reporttype"/>";  
		</script> 
    </head>
<body >
	<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
    <td width="100%" valign="top">
    <div class="navbar">
    	
   
<!--  <a href="reportTypeAction.do?method=toEdit"/><fmt:message key="button.add"/></a>--> <a href="reportTypeAction.do?method=toEdit" class="easyui-linkbutton"  style="width:80; height: 20;text-decoration:none;" data-options="iconCls:'icon-add'"  ><fmt:message key='button.add'/></a>
		<a href="reportTypeAction.do?method=taxisType&flag111=111" class="easyui-linkbutton" style="width:80; height: 20; text-decoration:none;" ><fmt:message key="button.order"/></a>
		<!--  <a href="reportTypeAction.do?method=taxisType"/><fmt:message key="button.order"/></a>-->
	
    </div>
   
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
						//highlightTableRows("type");
					//-->
				</script>
				
	    </div>
	    
	</center>
</td>
</tr>
</table>
</body>
</html>