<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>

<html>
<head>
<title><fmt:message key="webapp.prefix"/></title>
    <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/default.css'/>" />  
    <script type="text/javascript" src="<c:url value='/scripts/global.js'/>"></script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
 
    	<br>
    	<div style="padding:10px">
    	<div style="background: #ffffff url('ps/framework/images/error.png') no-repeat 10px center;  padding: 20px; padding-left: 40px; margin:5px;border: 1px dashed #FF6666;font-size:14px">
					<b>TimeOut!</b>
		<fmt:message key="timeout.message"/>
		
		<a href="<c:url value="/index.jsp"/>" target="_top" href="#" class="easyui-linkbutton c5" data-options="iconCls:'icon-reload'"><fmt:message key="user.loginAgain"/></a>
		</div>
		</div>
		
			
	
</table>
</body>
</html>