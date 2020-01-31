<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/meta.jsp"%>
<c:if test="${error=='-1'}" >
<script type="text/javascript">
 alert("<fmt:message key="error.coderepeat"/>");
</script>
</c:if>	
<html>
<head>
<title><fmt:message key="webapp.prefix"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
        <script type="text/javascript" src="<c:url value='/scripts/global.js'/>"></script>
        
		<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/common/jscalendar/calendar-win2k-1.css'/>" title="win2k-cold-1" /> 
		<script type="text/javascript" src="<c:url value='/common/jscalendar/calendar.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/common/jscalendar/lang/zh-cn.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/common/jscalendar/calendar-setup.js'/>"></script>
		<link rel="stylesheet" href="<c:url value='/images/css.css" type="text/css'/>"/>
		<script type="text/javascript">
		<!--
		function checkInput(){
				
			var pattern = /^[0-9]*[1-9][0-9]*$/;
			
    		var d = document.unitForm.modulus.value;
    		
    		if(!pattern.test(d)){    			
      			alert("<fmt:message key="unit.modulus.format"/>");
      			return false;
    		}
			if(document.unitForm.code.value.length == 0){
				alert("<fmt:message key="unit.code.notNull"/>");
				return false;
			}
			if(document.unitForm.name.value.length == 0){
				alert("<fmt:message key="unit.name.notNull"/>");
				return false;
			}
			if(document.unitForm.modulus.value.length == 0){
				alert("<fmt:message key="unit.modulus.notNull"/>");
				return false;
			}
			if(document.unitForm.modulus.value.length > 20){
				alert("<fmt:message key="unit.modulus.length"/>");
				return false;
			}
			if(document.unitForm.code.value.length >1){
				alert("<fmt:message key="unit.code.length"/>");
				return false;
			}
		}
		
		
	//	window.parent.document.getElementById("ppppp").value= "<fmt:message key="usermanage.sysadmin.menu"/><fmt:message key="navigation.arrowhead"/><fmt:message key="usermanage.unit.maintain"/>";

		</script>
</head>
<body leftmargin="0" >
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  
  <tr>
    <td width="100%" background="<c:url value="${imgPath}/f05.gif"/>" valign="top">
    <p>
    </p>
<form name="unitForm" action="unitAction.do?method=addUnit" method="post" onSubmit="return checkInput();">
<table  align="center" class="TableBGColor" width="380" border="0" cellSpacing=1 cellPadding=5 height="200">    
<br><br><br>
<tr style="height:18">
			<td class="TdBGColor1" width=90 align="right" ><fmt:message key="unit.code"/></td>
			<td class="TdBGColor2" style="height:20"  ><input type="text" name="code" maxlength="1" value="<c:out value="${unitForm.code}"/>" style="width:200"/></td>
		</tr>
			<input type="hidden" name="pkid" value="<c:out value="${unitForm.pkid}"/>"/>
		<tr height="18">
			<td class="TdBGColor1" width=90 align="right"><fmt:message key="unit.name"/></td>
			<td class="TdBGColor2"><input type="text" name="name" maxlength="6" value="<c:out value="${unitForm.name}"/>" style="width:200"/></td>
		</tr>

		<tr height="18">
			<td class="TdBGColor1" width=90 align="right"><fmt:message key="unit.modulus"/></td>
			<td class="TdBGColor2"><input type="text" name="modulus" maxlength="20" value="<c:out value="${unitForm.modulus}"/>" style="width:200"/></td>
		</tr>        
        <tr height="18">
			<td class="TdBGColor1" align="center" colspan="2">
				<input type="submit" name="submit" value="<fmt:message key="unit.submit"/>" />
				&nbsp;&nbsp;&nbsp;&nbsp;
				<input type="button" name="btn" value="<fmt:message key="unit.reset"/>" onClick="isNull();"/>
			</td>
        </tr>
</table>
</form>
<script type="text/javascript">
<!--
	function isNull(){
		document.unitForm.code.value = "";
		document.unitForm.name.value = "";
		document.unitForm.modulus.value = "";
	}
//-->
</script>
</td>
</tr>
</table>
</body>
</html>
