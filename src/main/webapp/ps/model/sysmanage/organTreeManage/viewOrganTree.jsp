<!-- /organTreeManage/viewOrganTree.j -->
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>

<html>
    <head>
        <title><fmt:message key="webapp.prefix"/></title>
        <link rel="stylesheet" type="text/css" media="all" href="<c:url value='${displaytag12Path}/displaytag.css'/>" />

 <link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>   
    </head>
<body onload="forbiddon();">

<script type="text/javascript">
function forbiddon(){
	document.onkeydown=function(){if(event.keyCode==8)return false;};
}



//window.parent.document.getElementById("ppppp").value= "<fmt:message key="organTree.title" />";

</script>
  
<div class="navbar">
	<table>
		<tr>
			<td>
				<a href="organTreeAction.do?method=newTree" class="easyui-linkbutton buttonclass2"   data-options="iconCls:'icon-add'"  ><fmt:message key='organTree.newOrganTree'/></a>
			</td>
			<td>
						<a href="organTreeAction.do?method=sortOrganTree" class="easyui-linkbutton buttonclass2" data-options="iconCls:'icon-sort'" ><fmt:message key="organTree.sortOrganTree"/></a>
			</td>
		</tr>
	</table>
</div>
    	
	<center>
	<div style="width:100%">
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
				<display:column  sort="true" headerClass="sortable" titleKey="button.operation"
					width="20%" nowrap="no" >
					<c:if test="${userId == organSystem.creatorId}">
						<a href="organTreeAction.do?method=editTree&treeId=<c:out value="${organSystem.id}"/>" style="text-decoration: none;">
							<fmt:message key="organTree.editOrganTree"/>
						</a>
						&nbsp;&nbsp;&nbsp;
						<a href="organTreeAction.do?method=deleteTree&treeId=<c:out value="${organSystem.id}"/>" style="text-decoration: none;" onClick="if(confirm('<fmt:message key="usermanage.organ.form.confirmdel"/>')){ return true;} else{ return false;}">
							<fmt:message key="organTree.deleteOrganTree"/>
						</a>
					</c:if>
					<!--<a href="organTreeAction.do?method=copyTree&treeId=<c:out value="${organSystem.id }"/>"/><fmt:message key="organTree.copyOrganTree"/></a>-->
				</display:column>
			</display:table>
	</div>  



</body>
</html>

<script type="text/javascript">
var refreshOrganSystemList = "<c:out value="${refreshOrganSystemList}"/>";
if(refreshOrganSystemList == 'need'){
	var menuFrame = window.top.frames['leftFrame'];
	menuFrame.observePro();
}
</script>
