<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="com.krm.slsint.workfile.vo.OleFileData" %>
<html>
<head>
<title><fmt:message key="webapp.prefix"/></title>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/default.css'/>" /> 
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/common/jscalendar/calendar-win2k-1.css'/>" title="win2k-cold-1" />
<link rel="stylesheet" href="images/css.css" type="text/css">
<script type="text/javascript" src="<c:url value='/scripts/global.js'/>"></script>
<script type="text/javascript" src="<c:url value='/common/jscalendar/calendar.js'/>"></script> 
<script type="text/javascript" src="<c:url value='/common/jscalendar/lang/zh-cn.js'/>"></script> 
<script type="text/javascript" src="<c:url value='/common/jscalendar/calendar-setup.js'/>"></script>
<script type="text/javascript">
<!--
function winClose(){
	window.close();
}
-->
</script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
	<center>
	<table border="0" align="center" cellSpacing="1" cellPadding="0" class="TableBGColor" width="800">
	   <tr>
	      <td class="FormBottom" colspan="2" width="100%" height="30"><p align="center"><font size="4"><b>
	          <a href="#" onclick="winClose();">
	               <fmt:message key="workFile.close" />
	          </a></b></font>
	      </td>
	   </tr>
	   <tr>
	      <td class="TdBgColor1" align="left" width="50%" height="30"><fmt:message key="workFile.dicName" /><c:out value="${dicName}" /></td>
	      <td class="TdBgColor1" align="left" width="50%" height="30"><fmt:message key="workFile.issDate" /><c:out value="${workDirectData.issDate}" /></td>
	   </tr>
	   <tr>
	      <td class="TdBgColor1" align="center" width="100%" height="30" colspan="2"><font size="4"><b><c:out value="${workDirectData.title}" /></b></font></td>
	   </tr>
	   <tr>
	      <td class="TdBgColor1" align="left" valign="top" width="100%" height="400" colspan="2"><c:out value="${workDirectData.content}" escapeXml="false" /></td>
	   </tr>
	   <tr>
	    <td class="TdBgColor1" align="left" height="30" width="100%" colspan="2">
	      <table border="0" align="center" cellSpacing="1" cellPadding="0" class="TableBGColor" width="100%">
	          <tr >
	           <%if(!((Long)request.getAttribute("fileCnt")).equals(new Long(0))){%>
	            <td class="TdBgColor1" align="left" height="30" width="20%">
	              <fmt:message key="inbox.content.fileName" />	
	            </td>
	            <td class="TdBgColor1" align="left" height="30" width="80%">
	              <%
	       		ArrayList al = (ArrayList)request.getAttribute("oleFileData");
	       		Iterator it = al.iterator();
	       		while(it.hasNext()){
	       			OleFileData ole = (OleFileData)it.next();
	       		%>
	       <a href="workFile.do?method=download&fileId=<%=ole.getPkId()%>">
	       		<%=ole.getSFileName() %><br>
	       </a>
	       	<%}%>	       	
	            </td >
	        <%}else{%>
	        	<td class="TdBgColor1" align="left" height="30" width="20%">
	              <fmt:message key="inbox.content.fileName" />	
	            </td>
	            <td class="TdBgColor1" align="left" height="30" width="80%">
	       		  <fmt:message key="file.noOleFile" />
	       		</td>	       
	        <%}%>	              
	          </tr>
	        </table>
	        </td>
	   </tr>
	   <tr>
	      <td class="FormBottom" colspan="2" height="30"><p align="center"><font size="4"><b>
	          <a href="#" onclick="winClose();">
	               <fmt:message key="workFile.close" />
	          </a></b></font>
	      </td>
	   </tr>
	</table>
	</center>
</body>
</html>