<!-- /ps/model/reportdefine/sortcommon. -->
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/meta.jsp" %>

<title><fmt:message key="webapp.prefix"/></title>
 <LINK href="${cssPath}/common.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
        <link rel="stylesheet" type="text/css" media="all" href="<c:url value='${cssPath}/helptip.css'/>" />
        <link rel="stylesheet" type="text/css" media="print" href="<c:url value='${cssPath}/print.css'/>" />
     	 <link rel="stylesheet" type="text/css" media="all" href="<c:url value='${eaUIPath}/themes/default/easyui.css'/>" />
	 <link rel="stylesheet" type="text/css" media="all" href="<c:url value='${eaUIPath}/themes/icon.css'/>" />
	 <link rel="stylesheet" type="text/css" href="<c:url value='${eaUIPath}/demo.css'/>"> 
	 <script type="text/javascript" src="<c:url value='${eaUIPath}/jquery.min.js'/>"></script> 
	 <script type="text/javascript" src="<c:url value='${eaUIPath}/jquery.easyui.min.js'/>"></script>  
	 <link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>   
   <script type="text/javascript">
	   //     window.parent.document.getElementById("ppppp").value= "<c:out value="${fileTitle }"/>";  
</script>       
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">


<table align=center border="0" align="center" cellpadding="0" cellspacing="1" >
	<tr>
		<td WIDTH=580 height=30 align="center" colspan="2" >
			
			<c:out value = "${sortTitle}"/>
			
		</td>
	</tr>
	<tr>
		<td WIDTH=580 colspan="2" align="left" >
			<select size = "25" id = "selOrder" name = "selOrder" MULTIPLE STYLE="WIDTH:580;height:375" onfocus = "selFocus()">
			<c:forEach var="sortItem" items="${sortList}">
			     <option value = "<c:out value="${sortItem[0]}"/>"><c:out value="${sortItem[1]}"/></option>
			</c:forEach>
			</select>
		</td>
	</tr>
	
	<tr>
		<td WIDTH=500 height=35 colspan="2" align="center" >
		
			 <a id = "btnMovePrevious" name = "btnMovePrevious" href="#" class="easyui-linkbutton buttonclass4"  data-options="disabled:true"   onclick = "MovePrevious()"><fmt:message key="button.moveup"/></a>&nbsp;
			<a id = "btnMoveNext" name = "btnMoveNext" href="#" class="easyui-linkbutton buttonclass4"   data-options="disabled:true"    onclick = "MoveNext()"><fmt:message key="button.movedown"/></a>&nbsp;
			<a id = "btnOK" name = "btnOK" href="#" class="easyui-linkbutton buttonclass4"   onclick = "SubForm('1')"><fmt:message key="button.save"/></a>&nbsp;
			<c:if test="${flag111==111 }">
			<a id = "btnNO" name = "btnNO" href="reportTypeAction.do?method=list" class="easyui-linkbutton buttonclass4"  onclick="aaa();" ><fmt:message key="button.cancel"/></a>&nbsp;
			</c:if>
			<c:if test="${flag111==000 }">
			<a id = "btnNO" name = "btnNO" href="reportAction.do?method=search&name=<c:out value='${ name}'/>&code=<c:out value='${ code}'/>&reportType=<c:out value='${ reportType}'/>&frequency=<c:out value='${ frequency}'/>&beginDate=<c:out value='${ beginDate}'/>&endDate=<c:out value='${ endDate}'/>" class="easyui-linkbutton buttonclass4"  onclick="aaa();" ><fmt:message key="button.cancel"/></a>&nbsp;
			</c:if>
			<!--  
			<input id = "btnMovePrevious" name = "btnMovePrevious" style="Width:70" type = "button" value = "<fmt:message key="button.moveup"/>" onclick = "MovePrevious()" disabled>&nbsp;
			<input id = "btnMoveNext" name = "btnMoveNext" style="Width:70" type = "button" value = "<fmt:message key="button.movedown"/>"   onclick = "MoveNext()" disabled>&nbsp;
			<input id = "btnOK" name = "btnOK" style="Width:70" type = "button" value = "<fmt:message key="button.save"/>" onclick = "SubForm('1')">&nbsp;
			<input id = "btnNO" name = "btnNO" style="Width:70" type = "button" value = "<fmt:message key="button.cancel"/>" onclick = "SubForm('0')">&nbsp;-->
		</td>
	</tr>
</table>
<script type="text/javascript">
	function selFocus(){
		//alert("11111");
		//alert(document.getElementById("selOrder").options.length);
		if(document.getElementById("selOrder").options.length>0){
			//alert("");
			//document.getElementById("btnMovePrevious").disabled=false;
			//document.getElementById("btnMoveNext").disabled=false;
			//document.getElementById("btnMovePrevious").linkbutton("disabled");
			$("#btnMoveNext").linkbutton("enable");
			$("#btnMovePrevious").linkbutton("enable");
			
		}
	}
	function aaa(){
		
	}
</script>
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