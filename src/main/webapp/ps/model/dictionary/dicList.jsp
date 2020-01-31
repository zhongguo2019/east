<!-- /dictionary/dicList.jsp -->
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

        <script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
<!-- <script type="text/javascript">
		 window.parent.document.getElementById("ppppp").value= "<fmt:message key="usermanage.sysadmin.menu"/><fmt:message key="navigation.arrowhead"/><fmt:message key="usermanage.dic.maintain"/>";  
</script> -->
</head>
<body leftmargin="0"  onload="forbiddon();">
<script type="text/javascript">
function forbiddon(){
	document.onkeydown=function(){if(event.keyCode==8)return false;};
	
}

</script>
<table width="100%">
	    
			<display:table name="dicList" cellspacing="0"
				    requestURI="" defaultsort="1" id="dics" width="100%" 
				    pagesize="20" styleClass="list dicList">
				    <%-- Table columns --%>
				    <display:column titleKey="usermanage.dic.dicname" sort="true" 
				    	headerClass="sortable">
				    	<c:out value="${dics.dicname}"/>
				    </display:column>
				    <display:column titleKey="common.list.operate" sort="true" 
				    	headerClass="sortable">
				    	<a href="<c:url value="/dicAction.do?method=listSubDic&dictype=${dics.dicid}&editable=0"/>" style="text-decoration: none;"><fmt:message key="button.view"/></a>
					<c:if test="${dics.readonly=='1'}">
				    	&nbsp;&nbsp;<a href="<c:url value="/dicAction.do?method=listSubDic&dictype=${dics.dicid}&editable=1"/>"><fmt:message key="button.edit"/></a>
				    </c:if>
				    </display:column>
				    <display:column titleKey="usermanage.dic.description" sort="true" 
				    	headerClass="sortable">
	                    <c:out value="${dics.description}"/>
				    </display:column>
				</display:table>

</table>
</body>
</html>