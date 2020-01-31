<!-- /ps/uitl/krmtree/sortcommon. -->
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/meta.jsp" %>
<%@ include file="/ps/framework/common/easyUItag.jsp" %>
<title><fmt:message key="webapp.prefix"/></title>

	<link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>


<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">

<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  
  <tr>
    <td width="100%" valign="top">
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
			     <option  value = "<c:out value="${sortItem[0]}"/>" ><c:out value="${sortItem[1]}"/></option>
			</c:forEach>
			</select>
		</td>
	</tr>
	
	<tr>
		<td WIDTH=500 height=35 colspan="2" align="center" bgcolor="#F6F6F6" id="aaa">
		 

			<a id = "btnMovePrevious" name = "btnMovePrevious" href="#" class="easyui-linkbutton"  data-options="disabled:true"   onclick = "MovePrevious()"><fmt:message key="button.moveup"/></a>&nbsp;
			<a id = "btnMoveNext" name = "btnMoveNext" href="#" class="easyui-linkbutton"   data-options="disabled:true"    onclick = "MoveNext()"><fmt:message key="button.movedown"/></a>&nbsp;

			<a id = "btnOK" name = "btnOK" href="#" class="easyui-linkbutton"   onclick = "SubForm('1')"><fmt:message key="button.save"/></a>&nbsp;
			<a id = "btnNO" name = "btnNO" href="#" class="easyui-linkbutton" onclick = "SubForm('0')"><fmt:message key="button.cancel"/></a>&nbsp;
		</td>
	</tr>
</table>

<script type="text/javascript">
	function MovePrevious(){
		var vMidValue;
		var vMidText;

		if(document.getElementById("selOrder").options[0].selected){
			alert("<fmt:message key='usermanage.organ.sort.first'/>");
			return;
		}else{
			for(var i=1;i<document.getElementById("selOrder").options.length;i++){
				if(document.getElementById("selOrder").options[i].selected){
					vMidValue=document.getElementById("selOrder").options[i].value;
					vMidText=document.getElementById("selOrder").options[i].innerText;
					document.getElementById("selOrder").options[i].value=document.getElementById("selOrder").options[i-1].value;
					document.getElementById("selOrder").options[i].innerText=document.getElementById("selOrder").options[i-1].innerText;
					document.getElementById("selOrder").options[i-1].value=vMidValue;
					document.getElementById("selOrder").options[i-1].innerText=vMidText;
					document.getElementById("selOrder").options[i-1].selected=true;
					document.getElementById("selOrder").options[i].selected=false;
				}
			}
		}
	}
	function MoveNext(){
		var vMidValue;
		var vMidText;
		if(document.getElementById("selOrder").options[document.getElementById("selOrder").options.length-1].selected){
			alert("<fmt:message key='usermanage.organ.sort.last'/>");
			return;
		}else{
			for(var i=document.getElementById("selOrder").options.length-2;i>=0;i--){
				if(document.getElementById("selOrder").options[i].selected){
					vMidValue=document.getElementById("selOrder").options[i].value;
					vMidText=document.getElementById("selOrder").options[i].innerText;
					document.getElementById("selOrder").options[i].value=document.getElementById("selOrder").options[i+1].value;
					document.getElementById("selOrder").options[i].innerText=document.getElementById("selOrder").options[i+1].innerText;
					document.getElementById("selOrder").options[i+1].value=vMidValue;
					document.getElementById("selOrder").options[i+1].innerText=vMidText;
					document.getElementById("selOrder").options[i+1].selected=true;
					document.getElementById("selOrder").options[i].selected=false;
				}
			}
		}
		
	}
 	
    	function SubForm(sOrderFlag){
  		for(var i=0;i<document.getElementById("selOrder").options.length;i++){
			if(document.getElementById("orderList").value>0){
				document.getElementById("orderList").value = document.sortForm.orderList.value +"&"+ ",";
			}else{
				var vMidValue = document.getElementById("selOrder").options[i].value;
				document.getElementById("orderList").value = document.sortForm.orderList.value +","+ vMidValue; 
			}
			
		}
   		document.getElementById("orderList").value = document.getElementById("orderList").value.substring(1,document.getElementById("orderList").value.length);
  // 		alert(document.getElementById("orderList").value)
   		document.sortForm.orderFlag.value = sOrderFlag;
   		document.sortForm.submit();
   		/* $.ajax({
    		"url" : "sortAction.do",
    		"type" : "post",
    		"data" : {
    			orderList:document.getElementById("orderList").value,
   			    orderFlag:sOrderFlag,
   			    serviceNam:document.getElementById("serviceName").value,
   			    forwardURL:document.getElementById("forwardURL").value
    		},
    		"error" : function(){
    			alert("error");
    		}
    	}); */
	} 
	
 	
 	function selFocus(){
		if(document.getElementById("selOrder").options.length>0){
			//$('#btnOK').linkbutton('disable');
			//$('#btnNO').linkbutton('disable');
			$('#btnMovePrevious').linkbutton('enable');
			$('#btnMoveNext').linkbutton('enable');

		}
	}
</script>

<script language = "vbscript">
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
<html:hidden styleId="orderList" property = "orderList"/>
<html:hidden styleId="orderFlag" property = "orderFlag"/>
<html-el:hidden styleId="serviceName" property = "serviceName" value="${serviceName}"/>
<html-el:hidden styleId="forwardURL" property = "forwardURL" value="${forwardURL}"/>
</html:form>