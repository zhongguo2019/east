<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>

<html>
<head>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
<link rel="stylesheet" href="<c:url value='${cssPath}/css.css" type="text/css'/>"/>
<script type="text/javascript">
		// window.parent.document.getElementById("ppppp").value= "<fmt:message key="usermanage.sysadmin.menu"/><fmt:message key="navigation.arrowhead"/><fmt:message key="usermanage.dic.maintain"/>";  
</script>
</head>
<body leftmargin="0" >
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
    <td width="100%"  valign="top">
        <p>
        <br>
        <font style=font-size:13px>&nbsp;&nbsp;&nbsp;
			<a href="e" onclick="history.back();return false">
				&lt;&lt;<fmt:message key="button.back"/></a>
        </font>
        </p>
        
    <br>
    <br>

  <form action="dicAction.do?method=save" method="post" onsubmit="return name.value!=''">
	<input type="hidden" name="pkid" value="<c:out value="${theDic.pkid}"/>"/>	
	<input type="hidden" name="parentid" value="<c:out value="${parentid}"/>"/>	
	<table cellSpacing=1 cellPadding=0 width="400" align="center" border=0 class="TableBGColor">
			    <tr>
			        <td width="150" align="right" class="TdBGColor1">
			            <fmt:message key="usermanage.dic.form.dicid"/>
			        </td>
			        <td width="250" class="TdBGColor2">
			        	<c:out value="${theDic.dicid}"/>		            
			        </td>
			    </tr>
			    <tr>
			        <td class="TdBGColor1" align="right">
			            <fmt:message key="usermanage.dic.form.name"/>
			        </td>
			        <td class="TdBGColor2">
				        <c:choose>
				        	<c:when test="${parentid eq '80001' }">
				        		<select name="name">
				        			<option value="openip" <c:if test="${theDic.dicname eq 'openip' }">selected</c:if> ><fmt:message key="usermanage.dic.form.ipopen"/></option>
				        			<option value="closeip" <c:if test="${theDic.dicname eq 'closeip' }">selected</c:if> ><fmt:message key="usermanage.dic.form.ipclose"/></option>
				        		</select>
				        	</c:when>
				        	<c:otherwise>
			        			<input type="text" name="name" value="<c:out value="${theDic.dicname}"/>">
				        	</c:otherwise>
				        </c:choose>
			        </td>
			    </tr>
			    
			    <tr>
			        <td class="TdBGColor1" align="right">&nbsp;</td>
			        <td class="TdBGColor2">
			        	<input type="submit" value="<fmt:message key="button.save"/>">
			        </td>
			    </tr>
			    <tr>
					<td class="FormBottom" colspan="2" height="17">&nbsp;</td>
				</tr>
			</table>

	</form>


</td>
</tr>
</table>
</body>
</html>
