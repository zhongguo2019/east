<%@ page pageEncoding="UTF-8"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />


<html>
<head>
<title><fmt:message key="webapp.prefix" /></title>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/ps/style/common.css'/>" /> 
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-win2k-1.css'/>" title="win2k-cold-1" />

<script type="text/javascript">
function onload(){
	if(document.getElementById("flag").value==1){
		//alert("11111111111111");
		alert(document.getElementById("info").value);
		window.parent.parent.window.ClosePanel();
		
		window.parent.window.addPaneltab('<fmt:message key="peizhiwenjian"/>','funconfigAction.do?method=selectfunconfiglist');
		
		
	}else{
		alert(document.getElementById("info").value);
		//window.parent.parent.window.ClosePanel();
		
		document.form5.submit();
	}
	
}
</script>
</head>

<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0" >
		<input type="hidden" id="info" value="<c:out value='${info}'/>">
		<input type="hidden" id="flag" value="<c:out value='${flag}'/>">
	<form action="funconfigAction.do?method=selectfunconfiglist" method="post" name="form5">
		
	</form>

</body>
	

</html>
<script>
onload();
</script>