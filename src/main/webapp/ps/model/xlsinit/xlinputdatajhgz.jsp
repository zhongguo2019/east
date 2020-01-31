<!-- /ps/model/xlsinit/xlinputdata. -->
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>

<html>
<head>
<title><fmt:message key="webapp.prefix" /></title>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${cssPath}/common.css'/>" />
<script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>

<script type="text/javascript">
	function message(){
		if((document.getElementById("message").value)>0){
			alert("<fmt:message key='view.rkcg'/>");
			document.form5.submit();
			
		}else{
			alert("<fmt:message key='excel.log.button.e0'/>");
			document.form5.submit();
		}
	}
	
	
</script> 

</head>

<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0" onload="message();">
<form action="repFileAction.do?method=importDataFromXls" method="post" name="form5">
  <input type="hidden" id="message" name="message" value="<c:out value='${message}'/>" /> 
</form>
	
</body>
</html>
