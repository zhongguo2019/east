<!--subdictList.jsp-->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<html>
<head>
<title><fmt:message key="webapp.prefix"/></title>

<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${displaytag12Path}/displaytag.css'/>" />
 <link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>   
</head>
<body leftmargin="0"  onload="forbiddon();">
<script type="text/javascript">
function forbiddon(){
	document.onkeydown=function(){if(event.keyCode==8)return false;};
}
</script>
<script type="text/javascript">
		// window.parent.document.getElementById("ppppp").value= "<fmt:message key="usermanage.sysadmin.menu"/><fmt:message key="navigation.arrowhead"/><fmt:message key="usermanage.dic.maintain"/>";  
</script>
<div class="navbar">
	<table>
		<tr>
			<td>
			<a href="#" onclick="history.back();return false" class="easyui-linkbutton buttonclass2"  ><fmt:message key="button.back"/></a>
				
                <c:if test="${editable==1}">
                <a href="<c:url value="/dicAction.do?method=enterEdit&parentid=${parentid}"/>" class="easyui-linkbutton buttonclass2"  data-options="iconCls:'icon-add'"  ><fmt:message key='groupmanage.main.fun.add'/></a>
					 
				</c:if>
			</td>
		</tr>
	</table>
</div>

			
				
	
        
	
	    <div id="screen" style=" width:98%">
			<display:table name="subdicList" cellspacing="0" cellpadding="0"  
				    requestURI="" defaultsort="1" id="dics" width="100%" 
				    pagesize="12" styleClass="list dicList">
				    <%-- Table columns --%>
				    <display:column titleKey="usermanage.dic.dicname" sort="true" 
				    	headerClass="sortable">
				    	<c:out value="${dics.dicname}"/>
				    </display:column>
				    <display:column titleKey="common.list.operate" sort="true" 
				    	headerClass="sortable">
				    	
					<c:if test="${editable==1}">
						<a href="<c:url value="/dicAction.do?method=enterEdit&pkid=${dics.pkid}&parentid=${parentid}"/>"><fmt:message key="button.edit"/></a>&nbsp;&nbsp;
				    	<a href="<c:url value="/dicAction.do?method=delete&pkid=${dics.pkid}&parentid=${parentid}"/>"><fmt:message key="button.delete"/></a>
				    </c:if>
				    	
				    </display:column>
				    <display:column titleKey="usermanage.dic.description" sort="true" 
				    	headerClass="sortable">
	                    <c:out value="${dics.description}"/>
				    </display:column>				    				    
				</display:table>
	    </div>	    

</body>
</html>