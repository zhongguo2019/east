<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
<html>
    <head>
        <title><fmt:message key="webapp.prefix"/></title>
        <link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>
	    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='${displaytag12Path}/displaytag.css'/>" />
	    <script type="text/javascript">
			   function OpenContent(pkId,dicId){
			      	window.open('workFile.do?method=view&pkId='+pkId+'&dicId='+dicId,'','toolbar=no,menubar=no,width=800,height=560,left='+(window.screen.width-800)/2+',top='+(window.screen.height-560)/2+',resizable=yes,scrollbars=yes');
			      	//var url="workFile.do?method=checkFlag&pkId="+pkId;
			   }
			   
			   function switchTable(workfileType)
				{
					var divId = "table_" + workfileType + "_0";
					var anotherDivId = "table_" + workfileType + "_1";
					if (document.getElementById(divId).style.display == "none")
					{
						 document.getElementById(divId).style.display = "block";
						 document.getElementById(anotherDivId).style.display = "none";
					}
					else
					{
						document.getElementById(divId).style.display = "none";
						document.getElementById(anotherDivId).style.display = "block";
					}
					// set other expand table hidden
					collapseOterTableDiv(workfileType);
				}
				
				function collapseOterTableDiv(workfileType)
				{
					var divs = document.getElementsByTagName("div");
					for (var i = 0; i < divs.length; i++)
					{
						var divObj = divs[i];
						if (divObj.id.match(/table_.*_[01]/g) != null
								&& divObj.id != "table_" + workfileType + "_0"
										&& divObj.id != "table_" + workfileType + "_1")
						{
							if (divObj.id.match(/table_.*_1/g) != null)
								divObj.style.display = "none";
							else
								divObj.style.display = "block";
						}	
					}
				}
		</script>
    </head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<table border="0" width="100%" align="left" cellSpacing=0 cellPadding=0>
  <tr >
	<td >
	    <c:if test="${param.method=='whFile'}">&nbsp;&nbsp;&nbsp;&nbsp;<a class="easyui-linkbutton" data-options="iconCls:'icon-selectall'" href="workFile.do?method=addList#">&lt;&lt;<fmt:message key="workFile.add" /></a></c:if>
	</td>
  </tr>
  <tr>
    <td >
	<div style="width:100%">
	<c:forEach var="workfileType" items="${workfileType}" varStatus="status">
		<div align="left" style="width:auto;background: #79E4BD">
			<img id="<c:out value="${ workfileType }"/>" title="<fmt:message key="inbox.pucker" />" 
				 src="images/minus.gif" width="9" height="9"
				onclick="extension(this.id);" /><label title="<c:out value="${ workfileType }"/>" 
				onclick="extension(this.previousSibling.id)"><c:out value="${ workfileType }"/></label>
		</div>
		<div id="table_<c:out value="${ workfileType }"/>">
			<div id="table_<c:out value="${ workfileType }"/>_0">
				<display:table id="object" htmlId="${workfileType}" name="${fileMap[workfileType]}" length="5" cellspacing="0" cellpadding="0" requestURI="" width="100%" pagesize="20" styleClass="list fileList">		
					<display:column  sort="true" headerClass="sortable" width="20%" nowrap="no" titleKey="workFile.dicName" >
						<c:out value="${object[2]}" />
					</display:column>
					<display:column  sort="true" headerClass="sortable" width="35%" nowrap="no" titleKey="workFile.title" >
						<a href="javascript:OpenContent('<c:out value="${object[0]}" />','<c:out value="${object[8]}" />');" >
						   <c:out value="${object[3]}" />
						</a>
						</display:column>
					<display:column  sort="true" headerClass="sortable" width="12%" nowrap="no" titleKey="workFile.operName" >
						<c:out value="${object[4]}" />
					</display:column>
					<display:column  sort="true" headerClass="sortable" width="20%" nowrap="no" titleKey="workFile.organName" >
						<c:out value="${object[5]}" />
					</display:column>
					<display:column sort="true" headerClass="sortable" width="12%" nowrap="no" titleKey="workFile.issDate" >
						<c:out value="${object[6]}" />
					</display:column>
					<c:if test="${param.method=='whFile'}">
						<display:column sort="true" headerClass="sortable" width="10%" nowrap="no" >			     
						     <a href="workFile.do?method=editWorkFile&pkId=<c:out value="${object[0]}"/>" >
						        <fmt:message key="workFile.edit" />
						     </a>&nbsp;&nbsp;			      
						     <a href="workFile.do?method=delWorkFile&pkId=<c:out value="${object[0]}"/>" onClick="return window.confirm('<fmt:message key="author.alert.delAlert"/>')">
						        <fmt:message key="workFile.delete" />
						     </a>
						</display:column>
					</c:if>
				</display:table>
				<c:if test="${sizeMap[workfileType] > 5}">
					<div style="margin-bottom:5px; vertical-align:bottom; padding-right:5px; text-align:right">
						<a href="#view more wordfiles" onclick='switchTable("<c:out value="${ workfileType }"/>")'><fmt:message key="workfile.view.more"/>>></a>
					</div>
				</c:if>
			</div>
			<div id="table_<c:out value="${ workfileType }"/>_1" style="display:none">
				<% String tableId = "table_" + (String)pageContext.getAttribute("workfileType") + "_1"; %>
				<display:table id="<%=tableId%>" htmlId="${workfileType}" name="${fileMap[workfileType]}" cellspacing="0" cellpadding="0" requestURI='<%="?tableId=" + tableId%>' width="100%" pagesize="50" styleClass="list fileList">
				<%pageContext.setAttribute("object", pageContext.getAttribute(tableId));%>
					<display:column  sort="true" headerClass="sortable"
						width="20%" nowrap="no" titleKey="workFile.dicName" >
						<c:out value="${object[2]}" />
					</display:column>
					<display:column  sort="true" headerClass="sortable"
						width="35%" nowrap="no" titleKey="workFile.title" >
						<a href="javascript:OpenContent('<c:out value="${object[0]}" />','<c:out value="${object[8]}" />');" >
						   <c:out value="${object[3]}" />
						</a>   
						</display:column>
					<display:column  sort="true" headerClass="sortable"
						width="12%" nowrap="no" titleKey="workFile.operName" >
						<c:out value="${object[4]}" />
					</display:column>
					<display:column  sort="true" headerClass="sortable"
						width="20%" nowrap="no" titleKey="workFile.organName" >
						<c:out value="${object[5]}" />
					</display:column>
					<display:column sort="true" headerClass="sortable"
						width="12%" nowrap="no" titleKey="workFile.issDate" >
						<c:out value="${object[6]}" />
					</display:column>
					<c:if test="${param.method=='whFile'}">
						<display:column sort="true" headerClass="sortable"
						    width="10%" nowrap="no" >			     
						     <a href="workFile.do?method=editWorkFile&pkId=<c:out value="${object[0]}"/>" >
						        <fmt:message key="workFile.edit" />
						     </a>&nbsp;&nbsp;			      
						     <a href="workFile.do?method=delWorkFile&pkId=<c:out value="${object[0]}"/>" onClick="return window.confirm('<fmt:message key="author.alert.delAlert"/>')">
						        <fmt:message key="workFile.delete" />
						     </a>
						</display:column>
					</c:if>
				</display:table>
				<c:if test="${sizeMap[workfileType] > 5}">
					<div style="margin-bottom:5px; vertical-align:bottom; padding-right:5px; text-align:right">
						<a href="#view more wordfiles" onclick='switchTable("<c:out value="${ workfileType }"/>")'><fmt:message key="usermanage.sysadmin.datarestore"/><<</a>
					</div>
				</c:if>
			</div>
		</div>
	</c:forEach>
		<script type="text/javascript">
				// highlightTableRows("object");
				var currentOperTableId = "<c:out value="${ param.tableId }"/>";
				if (currentOperTableId != "")
				{
					alert(currentOperTableId);
					switchTable(currentOperTableId.split("_")[1]);
				}
				
				function extension(id){
					var divId = "table_" + id;
					// alert(divId);
					if( document.getElementById(divId).style.display=="none"){
						document.getElementById(divId).style.display="";
						document.getElementById(id).src="images/minus.gif";
						document.getElementById(id).title="<fmt:message key="inbox.pucker" />"
					}else{
						document.getElementById(divId).style.display="none";
						document.getElementById(id).src="images/plus.gif";
						document.getElementById(id).title="<fmt:message key="inbox.tounfold" />"
					}
				}
		</script>
	</div>  
   </td>
</tr>
</table>
</body>
</html>