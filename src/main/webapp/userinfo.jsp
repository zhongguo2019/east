<!--userinfo-->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page pageEncoding="UTF-8"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />



<html>
<head>
<title><fmt:message key="webapp.prefix" /></title>

<link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/> 

<%-- <script type="text/javascript" src="<c:url value='/ps/wordEdit/fckeditor.js' />"></script> --%>
<link rel="stylesheet" type="text/css" href="<c:url value='/ps/model/dhtmlxtree/codebase/dhtmlxtree.css'/>"/>
<script type="text/javascript" src="<c:url value='/ps/model/dhtmlxtree/codebase/dhtmlxtree.js'/>"></script>
<script type="text/javascript" src="<c:url value='/ps/model/dhtmlxtree/codebase/dhtmlxcommon.js'/>"></script>

<script src="<c:url value='${tableCss}/jqueryTab/searchTree.js'/>" type="text/javascript"></script>




</head>

<body>

<table>
	<tr>
		<td>
			<fmt:message key="logpage.loginname"/>
		</td>
		
		<td>
			<c:out value="${loginname}" />
		</td>
	</tr>
	<tr>
		<td>
			<fmt:message key="zhenming"/>:
		</td>
		<td>
			<c:out value="${name}" />
		</td>
	</tr>

		<tr>
		<td>
			<fmt:message key="yonghusuoshujigou"/>:
		</td>
		<td>
			<c:out value="${fullname}" />
		</td>
		
	</tr>

</table>






</body>


</html>
