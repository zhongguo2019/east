<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
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
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
        <script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
        
		<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-win2k-1.css'/>" title="win2k-cold-1" /> 
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/lang/zh-cn.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-setup.js'/>"></script>
		<link rel="stylesheet" href="<c:url value='/theme/default/skin_01/style/css.css" type="text/css'/>"/>
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/ActiveXTree/ActiveXTree.js'/>"></script>
</head>
<body leftmargin="0" bgcolor=#eeeeee onload="forbiddon();">
<script type="text/javascript">
function forbiddon(){
	document.onkeydown=function(){if(event.keyCode==8)return false;};
}
</script>
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
    <td colspan="2" width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td>
            <p style="margin-top: 3"><font class=b><b><fmt:message key="usermanage.sysadmin.menu"/><fmt:message key="navigation.arrowhead"/><fmt:message key="usermanage.organ.menu"/></b></font></p>                         
          </td>
		  <td></td> 
        </tr>
      </table>
    </td>
  </tr>
  <tr>
  <td width="3%" bgcolor=#eeeeee></td>
    <td width="97%" bgcolor=#eeeeee valign="top">
    <p>
    </p>	
    <br>
	<html:hidden property="again" value="0"/>
	            <a href="javascript:addOrgan();"/>
			        <fmt:message key="usermanage.organ.addorgan"/>
			    </a><!--| 
			    <a href="organAction.do?method=sort"/>
			        <fmt:message key="button.sort"/>
			    </a> -->
			    <br><br>
			    <form action="" name="organForm" method="post">
				    <input type="hidden" name="areaId" value="<c:out value="${areaCode }"/>"/>
	      			<input type="hidden" name="areaName"/>
    						
	<center>
	    <div id="screen">
			<display:table name="organList" cellspacing="0" cellpadding="0"  
				    requestURI="organAction.do?method=list" id="organs" width="100%" 
				    pagesize="18" styleClass="list organList" >
				    <%-- Table columns --%>
				    <display:column sort="true" titleKey="usermanage.organ.form.code1" headerClass="sortable" 
				    	width="6%">
				    	<c:out value="${organs.code}"/>
				     </display:column>
				        
				    <display:column titleKey="usermanage.organ.form.short1" sort="true" 
				    	headerClass="sortable" width="10%">
				    	<c:out value="${organs.short_name}"/>
				    </display:column>
				    
				   <display:column titleKey="usermanage.organ.form.organtype1" sort="true" 
				    	headerClass="sortable" width="6%">
	                    <c:out value="${organs.organ_typename}"/>
				   </display:column>
				    
				    <display:column titleKey="usermanage.organ.form.businesstype1" sort="true" 
				    	headerClass="sortable" width="6%">
	                    <c:out value="${organs.business_typename}"/>
				    </display:column>
				    
				    <display:column titleKey="usermanage.organ.form.begindate1" sort="true" 
				    	headerClass="sortable" width="6%">
	                    <c:out value="${organs.begin_date}"/>
				    </display:column>
				     <display:column titleKey="usermanage.organ.form.enddate1" sort="true" 
				    	headerClass="sortable" width="6%">
	                    <c:out value="${organs.end_date}"/>
				    </display:column>
				              				    
				    <display:column titleKey="button.operation" sort="true" headerClass="sortable" 
				    	width="3%">
				        <a href="organAction.do?method=edit&organid=<c:out value="${organs.pkid}"/>">
				        	<fmt:message key="button.edit"/>
				        </a>
				    </display:column>
				    
				    <display:column sort="true" headerClass="sortable" 
				    	width="3%">
				    	<!-- 
				        <a href="organAction.do?method=del&organid=<c:out value="${organs.pkid}"/>&areaCode=<c:out value="${areaCode }"/>" onClick="if(confirm('<fmt:message key="usermanage.organ.form.confirmdel"/>')){ return true;} else{ return false;}">
				        	<fmt:message key="button.delete"/>
				        </a>
				         -->
				    </display:column>				    				    
				</display:table>
				<script type="text/javascript">
					<!--
						highlightTableRows("organs");
					//-->
				</script>				
	    </div>	    
	</center>
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
		areaId = document.organForm.areaId.value;
		document.organForm.action = '<c:out value="${hostPrefix}" /><c:url value="/organAction.do" />?method=edit&areaCode='+areaId;
		document.organForm.submit();
	}
	function changeTree1(){
		areaId = document.organForm.areaId.value;
		document.organForm.action = '<c:out value="${hostPrefix}" /><c:url value="/organAction.do" />?method=list&areaCode='+areaId;
		document.organForm.submit();
	}

</script>
</html>