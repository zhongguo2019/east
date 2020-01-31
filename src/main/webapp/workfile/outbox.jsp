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
					key="navigation.workfile.outbox" /></b></font></p>
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
					<fmt:message key="button.delete" /> 
				</a>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <!-- refresh --> 
					<a href="workMail.do?method=selectMail"> 
						<fmt:message key="inbox.refresh" /> 
					</a>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <!-- refresh --> 
					<a href="workMail.do?method=selectMail&zu=reply_1"> 
						<fmt:message key="outbox.waitfor" /> 
					</a>
				</td>
			</tr>
			
			<tr>				
		<center>
	   <table width="100%" border="0" cellspacing="0" cellpadding="0">
				      <tr bgcolor="#E9F2F9">
				    	<td width="4%" align="center">
				        <input type="checkbox" name="checkbox" value="checkbox" onclick="checkedAll(this);" /></td>  
				        <td width="4%" align="center"><img src="images/qbz.gif" width="23" height="16" /></td>
				        <td width="5%" align="center"><img title="<fmt:message key="recycle.itemType" />" src="images/lve1.gif"/></td>
				        <td width="42%" align="left"><a href="workMail.do?method=selectMail&zu=title&posinega=<c:out value="${posinega}" />" style="color:#000000;font-weight: bold" ><fmt:message key="inbox.title" /></a></td>
				        <td width="25%" align="left" style="color:#000000;font-weight: bold"><fmt:message key="outbox.addressee" /></td> 
				        <td width="10%" align="left"><a href="workMail.do?method=selectMail&pkId=0&zu=date&posinega=<c:out value="${posinega}" />" style="color:#000000;font-weight: bold" ><fmt:message key="outbox.date" /></a></td>
				        <td width="10%" align="left" style="color:#000000;font-weight: bold"><fmt:message key="outbox.delete"/></td>
				  </tr>
				  <c:choose>
		<c:when test="${zu=='date'}">
				 <c:forEach var="infoListTableBo" items="${tableBoList}">
				 <c:if test="${infoListTableBo.listSize!=0}">
				 <tr bgcolor="#FFFFFF">
				    <td colspan="7"><img title="<fmt:message key="inbox.pucker" />" id="ext<c:out value="${infoListTableBo.index}" />" src="images/minus.gif" width="9" height="9" onclick="extension(this.id);" /><label title="<fmt:message key="inbox.titles" />" onclick="Checked(<c:out value="${infoListTableBo.index}" />)"><c:out value="${infoListTableBo.titleHeader}" /></label> &nbsp;&nbsp;(<c:out value="${infoListTableBo.listSize}" /><fmt:message key="inbox.piece" />)</td>
				</tr>
				<tr>
				    <td colspan="7">
			   <div align="center" id="ext<c:out value="${infoListTableBo.index}" />div">
				<c:forEach var="infoItemBo" items="${infoListTableBo.infoList}" varStatus="iib">
				
				<table width="100%" border="0" cellpadding="0" cellspacing="0" style="border-bottom:0; border-left:0; border-right:0; border-top:0; border-collapse:0">
				<tr id="<c:out value="${iib.index}" />">
			        <td width="4%" align="center">
			          <input type="checkbox" name="cb<c:out value="${infoListTableBo.index}" />" value="<c:out value='${infoItemBo.pkId}' />" /></td>
			        <td width="4%" align="center">&nbsp;<c:if test="${infoItemBo.isAttachment == '1'}"><img title="<fmt:message key="common.upload.button.upload" />" src="images/qbz.gif" width="23" height="16" /></c:if></td>
			         <td width="5%" align="center">&nbsp;<c:if test="${infoItemBo.itemtype == '2'}"><img title="<fmt:message key="inbox.riskEarlyWarning" />" src="images/lve1.gif"/></c:if></td>
			        <td width="42%" align="left">
					<a href="#" onclick="javascript:openContent('<c:out value='${infoItemBo.pkId}'/>')" />
					<c:out value="${infoItemBo.title}" />
					</a>
					</td>
					<td width="25%" align="left"><c:out value="${infoItemBo.userName}" /></td>
			        <td width="10%" align="left"><c:out value="${infoItemBo.dateDate}" /></td>
			        <td width="10%" align="left" style="color:#000000;font-weight: bold"><a href="workMail.do?method=dMail&pkId=<c:out value="${infoItemBo.pkId}" />"><fmt:message key="outbox.delete"/></a></td>
			      </tr>
			      </table>
			     </c:forEach>
			     </div>
			     </td></tr>
			     </c:if>
				</c:forEach>
				</c:when>
				<c:otherwise>
    <tr>
    <td colspan="7">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" style="border-bottom:0; border-left:0; border-right:0; border-top:0; border-collapse:0">
	<c:forEach var="mailList" items="${mailList}" varStatus="ml">
     				<tr id="<c:out value="${ml.index}" />">
			        <td width="4%" align="center">
			          <input type="checkbox" name="cb<c:out value="${infoListTableBo.index}" />" value="<c:out value='${mailList.pkId}' />" /></td>
			        <td width="4%" align="center"><c:if test="${mailList.isAttachment == '1'}"><img src="images/qbz.gif" width="23" height="16" /></c:if></td>
			         <td width="5%" align="center"><c:if test="${mailList.itemtype == '2'}"><img title="<fmt:message key="inbox.riskEarlyWarning" />" src="images/lve1.gif"/></c:if></td>
			        <td width="42%" align="left">
					<a href="#" onclick="javascript:openContent('<c:out value='${mailList.pkId}'/>')" />
					<c:out value="${mailList.title}" />
					</a>
					</td>
					<td width="25%" align="left"><c:out value="${mailList.userName}" /></td>
			        <td width="10%" align="left"><c:out value="${mailList.dateDate}" /></td>
			        <td width="10%" align="left" style="color:#000000;font-weight: bold"><a href="workMail.do?method=dMail&pkId=<c:out value="${mailList.pkId}" />"><fmt:message key="outbox.delete"/></a></td>
			      </tr>
    </c:forEach>
    </table>
	</td>
  </tr>
  </c:otherwise>
				</c:choose>
				</table>
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
		window.location.href="workMail.do?method=dMail&status=1&str="+str; 
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
