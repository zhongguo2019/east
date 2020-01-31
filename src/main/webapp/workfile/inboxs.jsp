<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>

<html>
<head>
<title><fmt:message key="webapp.prefix"/></title>
<link rel="stylesheet" type="text/css" media="all"
	href="<c:url value='/styles/default.css'/>" />
<link rel="stylesheet" href="images/css.css" type="text/css">
<script type="text/javascript" src="<c:url value='/scripts/global.js'/>"></script>
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
					key="navigation.workfile.inbox" /></b></font></p>
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
				<c:if test="${param.status == '9'}">
					<a href="workMail.do?method=delMail&pkId=0&status=9"> <fmt:message
						key="inbox.deleteAll" /> </a>
				</c:if> <c:if test="${empty param.status || param.status != '9'}">
					<a
						href="workMail.do?method=updateMail&pkId=0&status=<c:out value="${param.status}"/>">
					<fmt:message key="inbox.deleteAll" /> </a>

				</c:if>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <!-- refresh --> <a
					href="workMail.do?method=queryMail&status=<c:out value="${param.status }" />"> <fmt:message
					key="inbox.refresh" /> </a>
				</td>
			</tr>
			<tr>
				<!-- examine -->
				<td><fmt:message key="inbox.examine" /> <!-- all --> <a
					href="workMail.do?method=queryMail"> <fmt:message key="inbox.all" />
					</a> &nbsp;-&nbsp; <!-- overRead --> <a
					href="workMail.do?method=queryMail&status=1"> <fmt:message
					key="inbox.overRead" /> </a> &nbsp;-&nbsp; <!-- notRead --> <a
					href="workMail.do?method=queryMail&status=0"> <fmt:message
					key="inbox.notRead" /> </a>
					&nbsp;-&nbsp; <!-- notRead --> 
					<!-- Query "Waiting For" Mail -->
					<a href="workMail.do?method=queryMail&zu=reply&status=1"> 
						<fmt:message key="datacheck.result.expecting" /> 
					</a>  <!-- recycle --> 
				</td>
			</tr>

			<tr>				
				<center>
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
				      <tr bgcolor="#E9F2F9">
				    	<td width="5%" align="center">
				        <input type="checkbox" name="checkbox" value="checkbox" onclick="checkedAll(this);" /></td>  
						<td width="5%" align="center"><img title="<fmt:message key="inbox.notRead" />" src="images/xf.gif" width="14" height="16" /></td>
				        <td width="5%" align="center"><img title="<fmt:message key="taskList.enclosure" />" src="images/qbz.gif" width="23" height="16" /></td>
				        <td width="5%" align="center"><img title="<fmt:message key="sendMail.alreadyReturn" />" src="images/replied2.gif" width="23" height="16" /></td>
				        <td width="5%" align="center"><img title="<fmt:message key="recycle.itemType" />" src="images/lve1.gif"/></td>
				        <td width="12%" align="left"><a href="workMail.do?method=queryMail&zu=addresser&posinega=<c:out value="${posinega}" />" style="color:#000000;font-weight: bold" ><fmt:message key="inbox.addresser" /></a></td>
				        <td width="51%" align="left"><a href="workMail.do?method=queryMail&zu=title&posinega=<c:out value="${posinega}" />" style="color:#000000;font-weight: bold" ><fmt:message key="inbox.title" /></a></td>
				        <td width="12%" align="left"><a href="workMail.do?method=queryMail&pkId=0&zu=date&posinega=<c:out value="${posinega}" />" style="color:#000000;font-weight: bold" ><fmt:message key="inbox.date" /></a></td>
				  </tr>
				  <c:choose>
		<c:when test="${zu=='date'}">
				 <c:forEach var="infoListTableBo" items="${tableBoList}">
				 <c:if test="${infoListTableBo.listSize!=0}">
				 <tr bgcolor="#FFFFFF">
				    <td colspan="8"><img id="ext<c:out value="${infoListTableBo.index}" />" src="images/minus.gif" width="9" height="9" onclick="extension(this.id);" /><label onclick="Checked(<c:out value="${infoListTableBo.index}" />)"><c:out value="${infoListTableBo.titleHeader}" /></label> &nbsp;&nbsp;(<c:out value="${infoListTableBo.listSize}" /><fmt:message key="inbox.piece" />)</td>
				</tr>
				<tr>
				    <td colspan="8">
			    <div align="center" id="ext<c:out value="${infoListTableBo.index}" />div">
				<c:forEach var="infoItemBo" items="${infoListTableBo.infoList}" varStatus="iib">
				
				<table width="100%" border="0" cellpadding="0" cellspacing="0" style="border-bottom:0; border-left:0; border-right:0; border-top:0; border-collapse:0">
				<tr id="<c:out value="${iib.index}" />">
			        <td width="5%" align="center">
			         &nbsp;<input type="checkbox" name="cb<c:out value="${infoListTableBo.index}" />" value="<c:out value='${infoItemBo.pkId}' />" /></td>
					 <td width="5%" align="center">
					 &nbsp;<c:if test="${infoItemBo.isRead == '0'}">
					 <img title="<fmt:message key="inbox.notRead" />" src="images/xf.gif" width="14" height="16" />
					 </c:if>
					 </td>
			        <td width="5%" align="center">&nbsp;<c:if test="${infoItemBo.isAttachment == '1'}"><img title="<fmt:message key="taskList.enclosure" />" src="images/qbz.gif" width="23" height="16" /></c:if></td>
			       <td width="5%" align="center">&nbsp;<c:if test="${infoItemBo.isReply == '3'}"><img title="<fmt:message key="sendMail.alreadyReturn" />" src="images/replied2.gif" width="23" height="16" /></c:if><c:if test="${infoItemBo.isReply == '1'}"><img title="<fmt:message key="sendMail.ReturnMail" />" src="images/folder_go.png"/></c:if></td>
			       <td width="5%" align="center">&nbsp;<c:if test="${infoItemBo.itemtype == '2'}"><img title="<fmt:message key="inbox.riskEarlyWarning" />" src="images/lve1.gif"/></c:if></td>
			        <td width="12%" align="left"><c:out value="${infoItemBo.addresser}" /></td>
			        <td width="51%" align="left">
					<a href="#" onclick="window.open('workMail.do?method=viewMails&isReply=<c:out value='${infoItemBo.isReply}' />&pkId=<c:out value='${infoItemBo.pkId}' />&fPkId=<c:out value='${infoItemBo.fpkId}'/>','','toolbar=no,menubar=no,width=800,height=560,left='+(window.screen.width-800)/2+',top='+(window.screen.height-560)/2+',resizable=yes,scrollbars=yes')">
					<c:out value="${infoItemBo.title}" />
					</a>
					</td>
			        <td width="12%" align="left"><c:out value="${infoItemBo.date}" /></td>
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
    <td colspan="8">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" style="border-bottom:0; border-left:0; border-right:0; border-top:0; border-collapse:0">
	<c:forEach var="mailList" items="${mailList}" varStatus="ml">
     				<tr id="<c:out value="${ml.index}" />">
			        <td width="5%" align="center">
			          <input type="checkbox" name="checkbox" value="checkbox" /></td>
					 <td width="5%" align="center">
					 <c:if test="${mailList.isRead == '0'}">
					 <img title="<fmt:message key="inbox.notRead" />" src="images/xf.gif" width="14" height="16" />
					 </c:if>
					 </td>
			        <td width="5%" align="center"><c:if test="${mailList.isAttachment == '1'}"><img title="<fmt:message key="taskList.enclosure" />" src="images/qbz.gif" width="23" height="16" /></c:if></td>
			        <td width="5%" align="center">&nbsp;<c:if test="${infoItemBo.isReply == '3'}"><img title="<fmt:message key="sendMail.alreadyReturn" />" src="images/replied2.gif" width="23" height="16" /></c:if><c:if test="${infoItemBo.isReply == '1'}"><img title="<fmt:message key="sendMail.ReturnMail" />" src="images/folder_go.png"/></c:if></td>
			         <td width="5%" align="center">&nbsp;<c:if test="${mailList.itemtype == '2'}"><img title="<fmt:message key="inbox.riskEarlyWarning" />" src="images/lve1.gif"/></c:if></td>
			        <td width="12%" align="left"><c:out value="${mailList.addresser}" /></td>
			        <td width="51%" align="left">
					<a href="#" onclick="window.open('workMail.do?method=viewMails&pkId=<c:out value='${mailList.pkId}' />&fPkId=<c:out value='${mailList.fpkId}'/>','','toolbar=no,menubar=no,width=800,height=560,left='+(window.screen.width-800)/2+',top='+(window.screen.height-560)/2+',resizable=yes,scrollbars=yes')">
					<c:out value="${mailList.title}" />
					</a>
					</td>
			        <td width="12%" align="left"><c:out value="${mailList.date}" /></td>
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

		<script type="text/javascript">
					<!--
						// highlightTableRows("object");
					//-->
				</script>
		</td>
	</tr>
</table>
<script language="javascript">
	function extension(id){
		if( document.getElementById(id+"div").style.display=="none"){
			document.getElementById(id+"div").style.display="";
			document.getElementById(id).src="images/minus.gif";
		}else{
			document.getElementById(id+"div").style.display="none";
			document.getElementById(id).src="images/plus.gif";
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
		window.location.href="workMail.do?method=selsctUpdateMail&status=1&str="+str; 
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
