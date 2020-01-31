<%@ include file="/ps/framework/common/taglibs.jsp"%>
<html>
<head>
<%@ include file="/ps/framework/common/glbVariable.jsp" %>
<title><fmt:message key="dictionary.list.title"/></title> 
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/default.css'/>" />  

<script language="JavaScript" type="text/JavaScript">
function CheckSubmit()
{
		if (DictionaryForm.dicName.value=="")
		{
			alert("<fmt:message key="dic.alert"/>");
			return false;
		}
		return true;
}

</script>
<script type="text/javascript" src="<c:url value='/scripts/global.js'/>"></script>
</head>
<body>

<html:form action="/dictionary?method=Save" method="post"  onsubmit="return CheckSubmit()">
<%@ include file="/ps/uitl/krmtree/header.jsp"%>
<br>
<table width="300"  cellpadding="0" cellspacing="1" border="0" class="TableBGColor" align="center">

    <tr align="center" valign="middle"> 
	<c:if test="${dotype=='1'}">
      <td height="24" colspan="2" class="Title"><font color="#990000">&nbsp;<strong><font style=""><fmt:message key="dic.anddic"/></font></strong></font></td>
      <input type="hidden" name="dotype" value="<c:out value="${dotype}"/>">&nbsp;
	</c:if>
	<c:if test="${dotype=='2'}">
		<td height="24" colspan="2" class="Title"><font color="#990000">&nbsp;<strong><font style=""><fmt:message key="dic.andCdic"/></font></strong></font></td>
	</c:if>
	<c:if test="${dotype=='3'}">
		<td height="24" colspan="2" class="Title"><font color="#990000">&nbsp;<strong><font style=""><fmt:message key="dic.updic"/></font></strong></font></td>
	</c:if>
		<input type="hidden" name="dotype" value="<c:out value="${dotype}"/>">
		<input type="hidden" name="CurrentItem" value="<c:out value="${CurrentItem}"/>">&nbsp;
    </tr>	
  <tr height="20">
    <td class="TdBgColor1"><fmt:message key="dic.parent"/></td>
	<c:if test="${dotype=='1'}">
    	<td class="TdBgColor2">&nbsp;</td>
    </c:if>
    <c:if test="${dotype=='2'}">
    	<td class="TdBgColor2"><c:out value="${CurrentItemName}"/>&nbsp;</td>
    </c:if>
    <c:if test="${dotype=='3'}">
    	<td class="TdBgColor2"><c:out value="${parentName}"/>&nbsp;</td>
    </c:if>
  </tr>
  <tr height="20">
    <td class="TdBgColor1"><fmt:message key="dic.Name"/></td>
    <td class="TdBgColor2"><input type="text" name="dicName" maxlength="25" value="<c:out value="${parentItem}"/>" >&nbsp;</td>
  </tr>
  <tr height="20">
    <td class="TdBgColor1" ><fmt:message key="dic.Dsc"/></td>
    <td class="TdBgColor2"><input type="text" name="disc" maxlength="100" value="<c:out value="${dicdesc}"/>">&nbsp;</td>
  </tr>
  <tr>
    <td colspan="2" class="TdBgColor1" >
	<table width="100%">
  		<tr class="Title">
    	<td align="center" class="TdBgColor1"><input name=""  class="Btn" type="submit" value="<fmt:message key="button.submit"/>">&nbsp;&nbsp;&nbsp;&nbsp;
    	<input name="" type="reset"onclick="javascript:window.location.assign('dictionary.do?method=getroots&CurrentItem=<c:out value="${CurrentItem}"/>')" class="Btn" value="<fmt:message key="button.back"/>"></td>
    	</tr>
	</table>
	</td>
  </tr>
  <tr>
		<td class="FormBottom" colspan="2" height="17"></td>
	</tr>
</table>
</html:form>
</body>
</html>
