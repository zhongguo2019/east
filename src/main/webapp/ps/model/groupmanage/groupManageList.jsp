<!-- /ps/model/groupmanage/groupManageList.jsp -->
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
   <link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>     
		
		

</head>
<body  onload="forbiddon();">
<script type="text/javascript">
function forbiddon(){
	document.onkeydown=function(){if(event.keyCode==8)return false;};
}
</script>
<!-- <script type="text/javascript">
		 window.parent.document.getElementById("ppppp").value= "<fmt:message key="usermanage.sysadmin.menu"/><fmt:message key="navigation.arrowhead"/><fmt:message key="groupmanage.title"/>";  
</script>	 -->
 
	<html:hidden property="again" value="0"/>
	<div class="navbar">
	<table>
		<tr>
			<td>
				<a href="groupReportAction.do?method=addGroupReport" class="easyui-linkbutton "   data-options="iconCls:'icon-add'"  ><fmt:message key='groupmanage.main.fun.add'/></a>
			</td>
			<td>
				<a href="groupReportAction.do?method=taxisGroup" data-options="iconCls:'icon-sort'" class="easyui-linkbutton"  ><fmt:message key="groupmanage.main.fun.sort"/></a>
			</td>
		</tr>
	</table>
		

	</div>
	

	<center>
	    <div id="screen">
			<display:table name="groupList" cellspacing="0" cellpadding="0"  
				    requestURI="" defaultsort="1" id="groups" width="100%" 
				    pagesize="10" styleClass="list groupList">
				    <%-- Table columns --%>		
				    <display:column sort="true" titleKey="groupmanage.main.displaynumber" headerClass="sortable" 
				    	width="5%">
				    	<c:out value="${groups.disporder}"/>
				    </display:column>			    
				    <display:column sort="true" titleKey="groupmanage.main.groupid" headerClass="sortable" 
				    	width="5%">
				    	<c:out value="${groups.dicid}"/>
				    </display:column>	
				    
				    <display:column sort="true" titleKey="groupmanage.main.groupname" headerClass="sortable" 
				    	width="8%">
				    	<c:out value="${groups.dicname}"/>
				    </display:column>	

			 		<display:column sort="true" headerClass="sortable"  titleKey="groupmanage.main.fun.oper" 
			  	  	width="18%">
			    	<a href="groupReportAction.do?method=editGroupReport&dicid=<c:out value="${groups.dicid}"/>" style="text-decoration: none;">
			        	<fmt:message key="groupmanage.main.fun.edit"/>
			        </a>&nbsp;&nbsp;&nbsp;
			        <a href="groupReportAction.do?method=deleteGroupReport&dicid=<c:out value="${groups.dicid}"/>" style="text-decoration: none;">
			        	<fmt:message key="groupmanage.main.fun.del"/>
			        </a>&nbsp;&nbsp;&nbsp;			        
			        <a href="groupReportAction.do?method=showGroupPurview&dicid=<c:out value="${groups.dicid}"/>" style="text-decoration: none;">
			        	<fmt:message key="groupmanage.main.fun.displayuser"/>
			        </a>&nbsp;&nbsp;&nbsp;
					
	               <c:if test="${flowTip ==1}">
			       <a href="flowTip.do?method=enterFlowTipUsers&userGroupId=<c:out value="${groups.dicid}"/>" style="text-decoration: none;">
	                    <fmt:message key="flowtip.list.right"/>
	               </a>&nbsp;&nbsp;&nbsp;
	               </c:if>
			    </display:column>   
				    
				</display:table>
				
				
	    </div>
	    
	</center>

</body>
</html>



