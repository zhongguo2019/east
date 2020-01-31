<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/meta.jsp" %>

<title><fmt:message key="webapp.prefix"/></title>
 <LINK href="${cssPath}/common.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
        <link rel="stylesheet" type="text/css" media="all" href="<c:url value='${cssPath}/helptip.css'/>" />
        <link rel="stylesheet" type="text/css" media="print" href="<c:url value='${cssPath}/print.css'/>" /> 
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">

<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
    <td width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td>
				<p style="margin-top: 3"><font class=b><b><c:out value="${fileTitle }"/></b></font></p>
          </td>
		  <td></td> 
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td width="100%" bgcolor="#EEEEEE" valign="top">
    <br>
    
<table align=center border="0" align="center" cellpadding="0" cellspacing="1" bordercolor="#CCCCCC" bgcolor="#666666">
	<tr>
		<td WIDTH=580 height=30 align="center" colspan="2" bgcolor="#F6F6F6">
			
			<c:out value = "${sortTitle}"/>
			
		</td>
	</tr>
	<tr>
		<td WIDTH=580 colspan="2" align="left" bgcolor="#F6F6F6">
			<select size = "25" id = "selOrder" name = "selOrder" MULTIPLE STYLE="WIDTH:580;height:375" onfocus = "selFocus()">
			<c:forEach var="sortItem" items="${sortList}">
			     <option value = "<c:out value="${sortItem[0]}"/>"><c:out value="${sortItem[1]}"/></option>
			</c:forEach>
			</select>
		</td>
	</tr>
	
	<tr>
		<td WIDTH=500 height=35 colspan="2" align="center" bgcolor="#F6F6F6">
			<input id = "btnMovePrevious" name = "btnMovePrevious" style="Width:70" type = "button" value = "<fmt:message key="button.moveup"/>" onclick = "MovePrevious()" disabled>&nbsp;
			<input id = "btnMoveNext" name = "btnMoveNext" style="Width:70" type = "button" value = "<fmt:message key="button.movedown"/>"   onclick = "MoveNext()" disabled>&nbsp;
			<input id = "btnOK" name = "btnOK" style="Width:70" type = "button" value = "<fmt:message key="button.save"/>" onclick = "SubForm('1')">&nbsp;
			<input id = "btnNO" name = "btnNO" style="Width:70" type = "button" value = "<fmt:message key="button.cancel"/>" onclick = "SubForm('0')">&nbsp;
		</td>
	</tr>
</table>

<script language = "vbscript">
'===============================================
sub MovePrevious()
	Dim	vMidValue,vMidText
	if selOrder(0).selected then
		msgbox "<fmt:message key="usermanage.organ.sort.first"/>"
		exit sub
	end if

	dim i
	for i = 1 to selOrder.length-1
		if selOrder(i).selected then
			vMidValue = selOrder(i).value
			vMidText = selOrder(i).innerText
			selOrder(i).value = selOrder(i-1).value 
			selOrder(i).innerText = selOrder(i-1).innerText 
			selOrder(i-1).value = vMidValue
			selOrder(i-1).innerText = vMidText 
			selOrder(i-1).selected=true
			selOrder(i).selected=false
		end if
	next
end sub
'===============================================
sub MoveNext()
	Dim	vMidValue,vMidText

	if selOrder(selOrder.length - 1).selected then
		msgbox "<fmt:message key="usermanage.organ.sort.last"/>"
		exit sub
	end if
	
	dim i
	for i = selOrder.length-2 to 0 Step -1
		if selOrder(i).selected then
			vMidValue = selOrder(i).value
			vMidText = selOrder(i).innerText
			selOrder(i).value = selOrder(i+1).value
			selOrder(i).innerText = selOrder(i+1).innerText
			selOrder(i+1).value = vMidValue
			selOrder(i+1).innerText = vMidText
			selOrder(i+1).selected=true
			selOrder(i).selected=false
		end if
	next
end sub
'===============================================
sub selFocus()
	if selOrder.length > 0 then
		btnMovePrevious.disabled = false
		btnMoveNext.disabled = false
	end if

end sub
'===============================================
sub SubForm(sOrderFlag)

	'On Error Resume Next
	sortForm.orderList.value = ""
	dim i
	for i = 0 to selOrder.length-1
		if len(sortForm.orderList.value) > 0 then
			sortForm.orderList.value = sortForm.orderList.value & ","
		end if
		sortForm.orderList.value = sortForm.orderList.value & selOrder(i).value 
	next
	sortForm.orderFlag.value = sOrderFlag
	sortForm.submit
end sub
'===============================================
</script>
<html:form action="sortAction" method="post">
<html:hidden property = "orderList"/>
<html:hidden property = "orderFlag"/>
<html-el:hidden property = "serviceName" value="${serviceName}"/>
<html-el:hidden property = "forwardURL" value="${forwardURL}"/>
</html:form>