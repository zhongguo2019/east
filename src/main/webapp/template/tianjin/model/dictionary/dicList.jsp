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
<body leftmargin="0" bgcolor=#eeeeee onload="forbiddon();">
<script type="text/javascript">
function forbiddon(){
	document.onkeydown=function(){if(event.keyCode==8)return false;};
	
}

</script>
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
     <td width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td>
            <p style="margin-top: 3"><font class=b><b><fmt:message
             key="usermanage.sysadmin.menu"/><fmt:message key="navigation.arrowhead"/><fmt:message key="usermanage.dic.maintain"/></b></font></p>                         
          </td>
		  <td></td> 
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td background="<c:url value="${imgPath}/f05.gif"/>" valign="top">
    <p>
    </p>	
    <br>
	<center>
	    <div align="center" style=" width:98%">
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
				    	<a href="<c:url value="/dicAction.do?method=listSubDic&dictype=${dics.dicid}&editable=0"/>"><fmt:message key="button.view"/></a>
					<c:if test="${dics.readonly=='1'}">
				    	&nbsp;&nbsp;<a href="<c:url value="/dicAction.do?method=listSubDic&dictype=${dics.dicid}&editable=1"/>"><fmt:message key="button.edit"/></a>
				    </c:if>
				    </display:column>
				    <display:column titleKey="usermanage.dic.description" sort="true" 
				    	headerClass="sortable">
	                    <c:out value="${dics.description}"/>
				    </display:column>
				</display:table>
				<script type="text/javascript">
					<!--
						highlightTableRows("dics");
					//-->
				</script>
	    </div>
	</center>
</td>
</tr>
</table>
</body>
</html>