<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<html>
<head>
<title><fmt:message key="webapp.prefix"/></title>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/default.css'/>" /> 
<link rel="stylesheet" href="images/css.css" type="text/css">
<script type="text/javascript" src="<c:url value='/scripts/global.js'/>"></script>
<script type="text/javascript">
			<!--
			   function openContent(pkId){
			   		
			      	window.open('workMail.do?method=viewMail&pkId='+pkId,'','toolbar=no,menubar=no,width=800,height=560,left='+(window.screen.width-800)/2+',top='+(window.screen.height-560)/2+',resizable=yes,scrollbars=yes');
			   }
			-->
</script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">

<table border="0" cellpadding="0" cellspacing="0" width="100%"
	height="100%">
	<tr>
		<td width="100%" background="images/f02.gif" height="36">
		<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td width="19"><img border="0" src="images/f01.gif" width="19"
					height="36"></td>
				<td width="42"><img border="0" src="images/f03.gif" width="33"
					height="16"></td>
				<td>
				<p style="margin-top: 3"><font class=b><b><fmt:message
					key="navigation.workfile.recyclebin" /></b></font></p>
				</td>
				<td></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td width="100%" bgcolor="#EEEEEE" valign="top">
		<table>
			<tr>
				<td align="left"><!-- deleteAll --> 
				<a	href="#" onclick="selectDel();">
					<fmt:message key="button.delete" /> </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
					<a	href="#" onclick="selectRestore();">
					<fmt:message key="recyclebin.restore" /> </a>
				</td>
			</tr>
			<tr>	
		<center>
		<form action="" method="post">
	   <table width="100%" border="0" cellspacing="0" cellpadding="0">
				   <tr bgcolor="#E9F2F9">
				    	<td width="4%" align="center">
				        <input type="checkbox" name="checkbox" value="checkbox" onclick="checkedAll(this);" /></td>  
				        <td width="4%" align="center"><img src="images/qbz.gif" width="23" height="16" /></td>
				        <td width="5%" align="center"><img title="<fmt:message key="recycle.itemType" />" src="images/lve1.gif"/></td>
				        <td width="42%" align="left" style="color:#000000;font-weight: bold"><fmt:message key="inbox.title" /></td>
				        <td width="25%" align="left" style="color:#000000;font-weight: bold"><fmt:message key="inbox.addresser" /></td> 
				        <td width="10%" align="left" style="color:#000000;font-weight: bold"><fmt:message key="outbox.date" /></td>
				        <td width="10%" align="center" style="color:#000000;font-weight: bold"><fmt:message key="recyclebin.restore" /></td>
				  </tr>
    <tr>
    <td colspan="8">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" style="border-bottom:0; border-left:0; border-right:0; border-top:0; border-collapse:0">
	<c:forEach var="mailList" items="${mailList}" varStatus="ml">
     				<tr id="<c:out value="${ml.index}" />">
     				<input type="hidden" name="isPosition<c:out value='${ml.index}' />" value="<c:out value="${mailList.isRead}" />"/>
     				<input type="hidden" name="pkId<c:out value='${ml.index}' />" value="<c:out value='${mailList.pkId}' />"/>
     				<input type="hidden" name="fPkId<c:out value='${ml.index}' />" value="<c:out value='${mailList.fpkId}'/>"/>
			        <td width="4%" align="center">
			          <input type="checkbox" name="<c:out value="${ml.index}" />" value="<c:out value="${ml.index}" />" /></td>
			        <td width="4%" align="center">&nbsp;<c:if test="${mailList.isAttachment == '1'}"><img src="images/qbz.gif" width="23" height="16" /></c:if></td>
			        <td width="5%" align="center">&nbsp;<c:if test="${mailList.itemtype == '2'}"><img title="<fmt:message key="inbox.riskEarlyWarning" />" src="images/lve1.gif"/></c:if></td>
			        <td width="42%" align="left"><c:if test="${mailList.isRead == '1'}"><a href="#" onclick="window.open('workMail.do?method=viewMails&recycle=1&isReply=<c:out value='${mailList.isReply}' />&pkId=<c:out value='${mailList.pkId}' />&fPkId=<c:out value='${mailList.fpkId}'/>','','toolbar=no,menubar=no,width=800,height=560,left='+(window.screen.width-800)/2+',top='+(window.screen.height-560)/2+',resizable=yes,scrollbars=yes')">
			        <c:out value="${mailList.title}" />
					</a></c:if>
			        <c:if test="${mailList.isRead == '2'}"><a href="#" onclick="window.open('workMail.do?method=viewMails&recycle=0&pkId=<c:out value='${mailList.pkId}' />&fPkId=<c:out value='${mailList.fpkId}'/>','','toolbar=no,menubar=no,width=800,height=560,left='+(window.screen.width-800)/2+',top='+(window.screen.height-560)/2+',resizable=yes,scrollbars=yes')"><c:out value="${mailList.title}" />
					</a></c:if>
			        <c:if test="${mailList.isRead == '3'}"><a href="#" onclick="window.open('workMail.do?method=viewMails&recycle=2&pkId=<c:out value='${mailList.pkId}' />&fPkId=<c:out value='${mailList.fpkId}'/>','','toolbar=no,menubar=no,width=800,height=560,left='+(window.screen.width-800)/2+',top='+(window.screen.height-560)/2+',resizable=yes,scrollbars=yes')"><c:out value="${mailList.title}" />
					</a></c:if>
					</td>
			        <td width="25%" align="left"><c:out value="${mailList.addresser}" /></td>
			        <td width="10%" align="left"><c:out value="${mailList.date}" /></td>
			       	<td width="10%" align="center"><a href="workMail.do?method=restoreMail&isPosition=<c:out value="${mailList.isRead}" />&pkId=<c:out value='${mailList.pkId}' />&fPkId=<c:out value='${mailList.fpkId}'/>"><fmt:message key="recyclebin.restore" /></a></td>
			      </tr>
    </c:forEach>
    </table>
	</td>
  </tr>
				</table>
				</form>
	    </center>
   </tr>
</table>
<script language="javascript">
	function extension(id){
		if( document.getElementById(id+"div").style.display=="none"){
			document.getElementById(id+"div").style.display="";
			document.getElementById(id).src="images/minus.gif";
			document.getElementById(id).title="<fmt:message key="inbox.pucker" />"
		}else{
			document.getElementById(id+"div").style.display="none";
			document.getElementById(id).src="images/plus.gif";
			document.getElementById(id).title="<fmt:message key="inbox.tounfold" />"
		}
	}
	
	function checkedAll(cbHeader){
		var a=document.getElementsByTagName("input");
		for(i=0;i<a.length;i++){
			if(a[i].type=="checkbox"){
				a[i].checked=cbHeader.checked;
			}
		}
	}
	function Checked(id){
		var a=document.getElementsByName("cb"+id);
		for(i=0;i<a.length;i++){
			if(a[i].checked){
				a[i].checked=false;
			}else{
				a[i].checked=true;
			}
		}
	}
	
	function selectRestore(){
		var a=document.getElementsByTagName("input");
		var str="";
		for(i=0;i<a.length;i++){
			if(a[i].type=="checkbox"){
				if(a[i].checked&&a[i].name!="checkbox"){
					str+=a[i].value+",";
				}
			}
		}
		if(str==""){
			alert("<fmt:message key="inbox.selectdel" />");
		}else{
		//alert(str);
		//window.location.href="workMail.do?method=selsctUpdateMail&status=1&str="+str; 
		//window.location.href="workMail.do?method=batchRestoreRecycleBin&str="+str; 
		document.forms[0].action="/slsint/workMail.do?method=batchRestoreRecycleBin&str="+str;
		document.forms[0].submit();	
		}

	}
	function selectDel(){
		var a=document.getElementsByTagName("input");
		var str="";
		for(i=0;i<a.length;i++){
			if(a[i].type=="checkbox"){
				if(a[i].checked&&a[i].name!="checkbox"){
					str+=a[i].value+",";
				}
			}
		}
		if(str==""){
			alert("<fmt:message key="inbox.selectdel" />");
		}else{
		//alert(str);
		//window.location.href="workMail.do?method=selsctUpdateMail&status=1&str="+str; 
		//window.location.href="workMail.do?method=batchRestoreRecycleBin&str="+str; 
		document.forms[0].action="workMail.do?method=batchDelRecycleBin&str="+str;
		document.forms[0].submit();	
		}

	}
	function stylestr(){
			var tr=document.getElementsByTagName("tr");
			for(a=0;a<tr.length;a++){
			if(tr[a].id!=""){
				if(tr[a].id%2==1){
					tr[a].style.backgroundColor="#ffd";
				}
				if(tr[a].id%2==0)
				{
					tr[a].style.backgroundColor="#EEEEEE";
				}
			}
		}
		}
	stylestr();
</script>
</body>
</html>
