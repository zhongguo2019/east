<%@ include file="/ps/framework/common/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
    <head>
        <%-- Include common set of meta tags for each layout --%>
        <%@ include file="/ps/framework/common/meta.jsp" %>
        <title><fmt:message key="webapp.prefix"/></title>
        
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
        <link rel="stylesheet" type="text/css" media="all" href="<c:url value='${cssPath}/helptip.css'/>" />
        <link rel="stylesheet" type="text/css" media="print" href="<c:url value='${cssPath}/print.css'/>" /> 
        <script type="text/javascript" src="<c:url value='/ps/scripts/prototype.js'/>"></script> 
        <script type="text/javascript" src="<c:url value='/ps/scripts/effects.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/ps/scripts/helptip.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>  
    </head>
<script language=JavaScript>
	function selectItems()
	{
		window.itemForm.action = "<c:url value="/reportItemAction.do?method=searchItem"/>"+"&itemCode="+itemCode.value;
		window.itemForm.submit();
	}
	function getUrl(){
		var url = window.location.href;
		document.getElementById("url").value=url;
	}
	window.onload=getUrl;
</script>	
	<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
	
	<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
   <td width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td>
				<p style="margin-top: 3"><font class=b><b><fmt:message
				 key="navigation.reportdefine.itemlist"/></b></font></p>
          </td>
		  <td></td> 
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td width="100%" bgcolor="#EEEEEE" valign="top">
    <br>
	
	<p style="margin-right: 30; margin-top: 8; margin-bottom: 8">
		&nbsp;&nbsp;
				<a href="reportItemAction.do?method=edit"/>
			        <fmt:message key="button.add"/>
			    </a> | 
			    <a href="reportAction.do?method=showSelf"/>
			        <fmt:message key="button.back"/>
			    </a> | 
			    <a href="reportItemAction.do?method=sortReportItem"/>
			        <fmt:message key="button.sort"/>
			    </a> | <input type="text" name="itemCode" value="" style="height:18" />
			    <a href=# onClick="selectItems();">
			        <fmt:message key="button.search"/>
			    </a> |
			    <c:if test="${!empty param.reportId }">
			    	<c:set var="repId" value="${param.reportId}"/>
			    </c:if>
			    <c:if test="${empty param.reprotId }">
			    	<c:set var="repId" value="${reportId}"/>
			    </c:if>
			    <a href="<c:url value="/importExport.do?method=exportReportItems&reportId=${repId}"/>">
			        <fmt:message key="importexport.itemExport"/>
			    </a> |			    
			    <a href="javaScript:enterImport()">
			        <fmt:message key="importexport.itemimport"/>
			    </a>
			    <form name="importForm" method="post" action="">
			    	<input type="hidden" name="url" id="url"/>
			    </form>
	</p>
	<center>
	    <div id="screen">
	    <form name="itemForm" action="reportItemAction.do?method=back" method="post">
			<display:table name="itemList" cellspacing="0" cellpadding="0"  
				    requestURI="" id="item" width="60%" 
				    pagesize="30" styleClass="list userList">
				    <%-- Table columns --%>
				    <display:column property="itemName" sort="true" 
				    	headerClass="sortable" width="18%" titleKey="common.list.itemname"/>
				    	
				    <display:column property="code" sort="true" 
				    	headerClass="sortable" width="9%" titleKey="common.list.itemcode"/>
				    
				    <display:column property="conCode" sort="true" 
				    	headerClass="sortable" width="9%" titleKey="reportdefine.reportItem.conCode"/>
				    	
				    <display:column property="beginDate" sort="true" 
				    	headerClass="sortable" width="12%" titleKey="common.list.begindate"/>
				    	
				    <display:column property="endDate" sort="true" 
				    	headerClass="sortable" width="12%" titleKey="common.list.enddate"/>
				    
				    <display:column width="12%" titleKey="reportview.list.operate">
				        <a href="reportItemAction.do?method=enterInsertItem&reportId=<c:out value="${repId}"/>&showOrder=<c:out value="${item.itemOrder}"/>">
				        	<fmt:message key="button.insertitem"/>
				        </a>
				        <a href="reportItemAction.do?method=edit&reportId=<c:out value="${repId}"/>&itemId=<c:out value="${item.pkid}"/>">
				        	<fmt:message key="button.edit"/>
				        </a>
				        <a href="reportItemAction.do?method=del&reportId=<c:out value="${repId}"/>&itemId=<c:out value="${item.pkid}"/>" onClick="if(confirm('<fmt:message key="pub.delitem.tip"/>')){ return true;} else{ return false;}">
				        	<fmt:message key="button.delete"/>
				        </a>
				    </display:column>
				</display:table>
				<script type="text/javascript">
					<!--
						highlightTableRows("item");
					//-->
				</script>
				</form>
	    </div>
	    
	</center>
	</td></tr></table>

</body>
<script type="text/javascript">
	function enterImport(){
		var url = "<c:url value="/importExport.do?method=enterImport&import=2&reportId=${repId}"/>";
		document.importForm.action=url;
		document.importForm.submit();
	}
</script>
</html>