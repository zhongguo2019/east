<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>

<html>
    <head>
        <title><fmt:message key="webapp.prefix"/></title>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
<link rel="stylesheet" href="<c:url value='${cssPath}/css.css" type="text/css'/>"/>
        <script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
    </head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%" onload="forbiddon();">
<script type="text/javascript">
function forbiddon(){
	document.onkeydown=function(){if(event.keyCode==8)return false;};
}
</script>
  <tr>
    <td colspan="2" width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td>
          	<p style="margin-top: 3"><font class=b><b><fmt:message
					key="organTree.title" /></b></font></p>
          </td><td></td> 
        </tr>
      </table>
    </td>
  </tr>
  <tr>
	<td align="left" bgcolor="#EEEEEE">
	</td>
  </tr>
  <tr>
    <td width="100%" bgcolor="#EEEEEE" valign="top">
    	&nbsp;&nbsp;
    	<a href="organTreeAction.do?method=newTree"><fmt:message key="organTree.newOrganTree"/></a>
    	&nbsp;&nbsp;|&nbsp;&nbsp;
    	<a href="organTreeAction.do?method=sortOrganTree"><fmt:message key="organTree.sortOrganTree"/></a>
	<center>
	<div style="width:98%">
		<!--user DisplayTag look over organTree-->
			<display:table name="organSystemList" cellspacing="0" cellpadding="0" requestURI="" 
			id="organSystem" width="100%" pagesize="30" styleClass="list organSystemList">
				<display:column  sort="true" headerClass="sortable"
				width="20%" nowrap="no" titleKey="organTree.treeName" >
					<c:out value="${organSystem.name }"/>
				</display:column>
				<display:column  sort="true" headerClass="sortable"
				width="20%" nowrap="no" titleKey="organTree.creatorId" >
					<c:out value="${organSystem.creatorName }"/>
				</display:column>
				<display:column  sort="true" headerClass="sortable"
				width="20%" nowrap="no" titleKey="organTree.beginDate" >
					<c:out value="${organSystem.beginDate }"/>
				</display:column>
				<display:column  sort="true" headerClass="sortable"
				width="20%" nowrap="no" titleKey="organTree.endDate" >
					<c:out value="${organSystem.endDate }"/>
				</display:column>
				<display:column  sort="true" headerClass="sortable"
					width="20%" nowrap="no" >
					<c:if test="${userId == organSystem.creatorId}">
						<a href="organTreeAction.do?method=editTree&treeId=<c:out value="${organSystem.id}"/>"/>
							<fmt:message key="organTree.editOrganTree"/>
						</a>
						<a href="organTreeAction.do?method=deleteTree&treeId=<c:out value="${organSystem.id}"/>" onClick="if(confirm('<fmt:message key="usermanage.organ.form.confirmdel"/>')){ return true;} else{ return false;}"/>
							<fmt:message key="organTree.deleteOrganTree"/>
						</a>
					</c:if>
					<!--<a href="organTreeAction.do?method=copyTree&treeId=<c:out value="${organSystem.id }"/>"/><fmt:message key="organTree.copyOrganTree"/></a>-->
				</display:column>
			</display:table>
	</div>  
   </td>
</tr>
</table>
<script type="text/javascript">
<!--
	highlightTableRows("organSystem");
//-->
</script>

</body>
</html>

<script type="text/javascript">
var refreshOrganSystemList = "<c:out value="${refreshOrganSystemList}"/>";
if(refreshOrganSystemList == 'need'){
	var menuFrame = window.top.frames['leftFrame'];
	menuFrame.observePro();
}
</script>
