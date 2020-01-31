<%@ include file="/ps/framework/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<html>
<head>
<title><fmt:message key="webapp.prefix"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
<link rel="stylesheet" href="<c:url value='${cssPath}/css.css" type="text/css'/>"/>
        <script type="text/javascript" src="<c:url value='/scripts/global.js'/>"></script>
</head>
<body leftmargin="0" bgcolor=#eeeeee>
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
    <td colspan="2" width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td>
            <p style="margin-top: 3"><font class=b><b><fmt:message key="usermanage.sysadmin.menu"/><fmt:message key="navigation.arrowhead"/><fmt:message key="usermanage.unit.maintain"/></b></font></p>                         
          </td>
		  <td></td> 
        </tr>
      </table>
    </td>
  </tr>
  <tr>
  <td width="3%" background="<c:url value="${imgPath}/f05.gif"/>"></td>
    <td width="97%" background="<c:url value="${imgPath}/f05.gif"/>" valign="top">
    <p>
    </p>	
    <br>
    <a href="unitAction.do?method=entryUnit"><fmt:message key="unit.add"/></a>
     
    &nbsp;|&nbsp;
    <a href="unitAction.do?method=taxisUnit" ><fmt:message key="unit.taxis"/></a>
   
	<center>
	    <div id="screen">
			<display:table name="unitList" cellspacing="0" cellpadding="0"  
				    requestURI="" id="units" width="100%" 
				    pagesize="12" styleClass="list unitList">
				    <%-- Table columns --%>
				    <display:column sort="true" titleKey="usermanage.unit.code" headerClass="sortable" 
				    	width="6%">
				    	<c:out value="${units.code}"/>
				     </display:column>
				        
				    <display:column titleKey="usermanage.unit.name" sort="true" 
				    	headerClass="sortable" width="6%">
				    	<c:out value="${units.name}"/>
				    </display:column>
				    
				   <display:column titleKey="usermanage.unit.modulus" sort="true" 
				    	headerClass="sortable" width="6%">
	                    <c:out value="${units.modulus}"/>
				   </display:column>
				   
				   <display:column sort="true" headerClass="sortable"
			    width="10%" nowrap="no" >
			       <a href="unitAction.do?method=editUnit&pkid=<c:out value="${units.pkid}"/>" >
	                    <fmt:message key="unit.edit"/>
	               </a>
				   </display:column>
				   
				   <display:column sort="true" headerClass="sortable"
			    width="10%" nowrap="no" >
			       <a href="unitAction.do?method=deleteUnit&pkid=<c:out value="${units.pkid}"/>">
	                    <fmt:message key="unit.delete"/>
	               </a>
				   </display:column>  
				</display:table>
				<script type="text/javascript">
					<!--
						highlightTableRows("units");
					//-->
				</script>				
	    </div>	    
	</center>
</td>
</tr>
</table>
</body>
</html>