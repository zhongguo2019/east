<!-- /ps/model/reportdefine/configList. -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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


       <script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${displaytag12Path}/displaytag.css'/>" />
<link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>
</head>
<body leftmargin="0" onload="forbiddon();">
<script type="text/javascript">
function forbiddon(){
	document.onkeydown=function(){if(event.keyCode==8)return false;};
}

//window.parent.document.getElementById("ppppp").value= "<fmt:message key="usermanage.sysadmin.menu"/><fmt:message key="navigation.arrowhead"/><fmt:message key="usermanage.organ.config"/>";
</script>
	<html:hidden property="again" value="0"/>
	<div class="navbar">
		<a href="javascript:addOrgan();" class="easyui-linkbutton"  style="text-decoration:none;" data-options="iconCls:'icon-add'"  ><fmt:message key='usermanage.organ.config.add'/></a>
	</div>
	 
	           <!--  <a href="javascript:addOrgan();"/>
			        <fmt:message key="usermanage.organ.config.add"/>
			    </a> --><!--| 
			    <a href="organAction.do?method=sort"/>
			        <fmt:message key="button.sort"/>
			    </a> -->
			
			    <form action="reportConfigAction" name="reportConfigForm" method="post">
    						
	<center>
	    <div id="screen">
	    <%int i=1; %>
			<display:table name="configList" cellspacing="0" cellpadding="0"  
				    requestURI="" id="configs" width="100%" 
				    pagesize="18" styleClass="list configList" >
				    <%-- Table columns --%>
				    <display:column sort="true" titleKey="groupmanage.main.displaynumber" headerClass="sortable" 
				    	width="6%">
						<%=i++ %>
				     </display:column>
				    
				    <display:column sort="true" titleKey="endyeardatacarry.reportid" headerClass="sortable" 
				    	width="6%">
				    	<c:out value="${configs.reportId}"/>
				     </display:column>
				        
				    <display:column titleKey="balanreport.list.reportname" sort="true" 
				    	headerClass="sortable" width="10%">
				    	<c:out value="${configs.description}"/>
				    </display:column>
				    
				       
				    <display:column titleKey="config.databasename" sort="true" 
				    	headerClass="sortable" width="6%">
	                    <c:out value="${configs.defChar}"/>
				    </display:column>
				    
				   <display:column titleKey="config.type" sort="true" 
				    	headerClass="sortable" width="6%">
				    	<c:if test="${configs.funId ==33}"><fmt:message key="config.sansan"/></c:if>
				    	<c:if test="${configs.funId ==34}"><fmt:message key="config.sansi"/></c:if>
				    	<c:if test="${configs.funId ==39}"><fmt:message key="config.not"/></c:if>
				   </display:column>
				 
				              				    
				    <display:column sort="true" headerClass="sortable" 
				    	width="3%">
				        <a href="reportConfigAction.do?method=configEdit&pkid=<c:out value="${configs.pkid}"/>">
				        	<fmt:message key="button.edit"/>
				        </a>
				    </display:column>
				    
				    <display:column sort="true" headerClass="sortable" 
				    	width="3%">
				        <a href="reportConfigAction.do?method=configDelete&pkid=<c:out value="${configs.pkid}"/>">
				        	<fmt:message key="button.delete"/>
				        </a>
				    </display:column>				    				    
				</display:table>
						
	    </div>	    
	</center>
	</form>

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