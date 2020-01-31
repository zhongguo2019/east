<!-- /ps/model/east1104dz/east1104list. -->
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
<c:set var="colName">
	<fmt:message key="organTree.colName"/>
</c:set>
<c:set var="orgTreeURL">
	<c:out value="${hostPrefix}"/><c:url value='/treeAction.do?method=getAreaTreeXML'/>
</c:set>
<c:set var="orgButton">
	<fmt:message key="organTree.area"/>
</c:set>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<html>
<head>
<title><fmt:message key="webapp.prefix"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${displaytag12Path}/displaytag.css'/>" />		
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${displaytag12Path}/displaytag.css'/>" />
	 <script language="javascript" type="text/javascript" src="<c:url value='ps/uitl/My97DatePicker/WdatePicker.js'/>"></script>
		
</head>
<body leftmargin="0"   onload="forbiddon();">
<script type="text/javascript">
function forbiddon(){
	document.onkeydown=function(){if(event.keyCode==8)return false;};
}
</script>
<script type="text/javascript">
	//	 window.parent.document.getElementById("ppppp").value= "<fmt:message key="usermanage.sysadmin.menu"/><fmt:message key="navigation.arrowhead"/><fmt:message key="usermanage.organ.config"/>";  
</script>
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
    <td width="100%"  valign="top">
    <p>
    </p>	
    <br>
	<html:hidden property="again" value="0"/>
	
   <form action="QueryDzAction" name="queryDzFrom" method="post">
    						
	<center>
	    <div id="screen">
	    <%int i=1; %>
			<display:table name="repList" cellspacing="0" cellpadding="0"  
				    requestURI="" id="repList" width="100%" 
				    pagesize="16" styleClass="list repList" >
				    <%-- Table columns --%>
				    <display:column sort="true" titleKey="groupmanage.main.displaynumber" headerClass="sortable" 
				    	width="2%">
						<%=i++ %>
				     </display:column>
				    
				    <display:column sort="true" titleKey="view.dz.zb" headerClass="sortable" 
				    	width="6%">
				    	<c:out value="${repList.zb}"/>
				     </display:column>
				        
				    <display:column titleKey="view.dz.eastze" sort="true" 
				    	headerClass="sortable" width="10%">
				    	<c:out value="${repList.eastzz}"/>
				    </display:column>
				    
				       
				    <display:column titleKey="view.dz.1104sj" sort="true" 
				    	headerClass="sortable" width="6%">
	                    <c:out value="${repList.ywzkbzz}"/>
				    </display:column>
				    
				    <display:column titleKey="view.dz.ce" sort="true" 
				    	headerClass="sortable" width="6%">
	                    <c:out value="${repList.ce}"/>
				    </display:column>				    				    
				</display:table>
				<script type="text/javascript">
					<!--
					//	highlightTableRows("configs");
					//-->
				</script>				
	    </div>	    
	</center>
	 <a href="#" class="easyui-linkbutton" style="width:80; height: 20;text-decoration:none;" onclick="window.location.href='querydz.do?method=queryorgandate' "><fmt:message key="button.back"/></a>
	<!-- <input id="btnReturn" name="btnReturn" class="btn"
											type="button" value="<fmt:message key="button.back"/>"
											onclick="window.location.href='querydz.do?method=queryorgandate' " /> -->
	</form>
</td>
</tr>
</table>
</body>

<script type="text/javascript">
<c:if test="${error2=='-1'}" >
 alert("<fmt:message key="usermanage.organ.form.organrepeat"/>");
</c:if>
<c:if test="${error=='-3'}" >		
alert("<fmt:message key="error.organused.delete"/>");
</c:if>	
<c:if test="${error=='-4'}" >		
alert("<fmt:message key="error.organused.inTree"/>\n<c:out value="treeNames"/>");
</c:if>	
	function addOrgan(){
		var areaId = 1;
		document.reportConfigForm.action = '<c:out value="${hostPrefix}" /><c:url value="/reportConfigAction.do" />?method=configEdit&areaCode='+areaId;
		document.reportConfigForm.submit();
	}

</script>
</html>