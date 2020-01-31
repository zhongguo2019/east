<%@page import="com.krm.ps.util.FuncConfig"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%
String accessorySize = FuncConfig.getProperty("accessory.size", "10");
%>
<c:set var="colName">
	<fmt:message key="exportXML.tree.org.name"/>
</c:set>
<c:set var="orgTreeURL">
	<c:out value="${hostPrefix}"/><c:url value='/treeAction.do?method=getOrganTreeXML&allTree=1'/>
</c:set>
<c:set var="orgButton">
	<fmt:message key="exportXML.button.orgChoose"/>
</c:set>
<html>
<head>
<title><fmt:message key="webapp.prefix"/></title>
<link rel="stylesheet" type="text/css" media="all"
	href="<c:url value='/styles/default.css'/>" />
<link rel="stylesheet" href="images/css.css" type="text/css">
<script type="text/javascript" src="<c:url value='/scripts/global.js'/>"></script>
<script type="text/javascript" src="<c:url value='/wordEdit/fckeditor.js' />"></script>
<script type="text/javascript" src="<c:url value='/common/ActiveXTree/ActiveXTree.js'/>"></script>
<script type="text/javascript">
<!--	 
	function checkInput(){	  	
		var strTagType = "";
		for(i = 0; i < document.all.length; i++){
	    if(document.all(i).type == "file"){
			if(strTagType=="")
				strTagType = document.all(i).name;
			else
				strTagType += (","+document.all(i).name);
		}
		if(window.workMailForm.addressee.value.length > 0 && window.workMailForm.title.value.length > 0 ){
			if(window.workMailForm.title.value.length <40 ){				
				return true;
	  		}else{
	  			alert("<fmt:message key="sendMail.titleSize" />");
	  			return false;
	  		}
	  	}else{
	  		alert("<fmt:message key="epistoliza.alert.check" />");
	  		return false;
	  	}	  	
	}
}
-->
	
</script>
<script type="text/javascript">
<!--	
function saveOutlineBox1(){
		var strTagType = "";
		var isReturn=document.getElementsByName("isReturn");
		for(i = 0; i < document.all.length; i++){
	    if(document.all(i).type == "file"){
			if(strTagType=="")
				strTagType = document.all(i).name;
			else
				strTagType += (","+document.all(i).name);
		}
		if(window.workMailForm.title.value.length > 0 ){
			if(window.workMailForm.title.value.length <40 ){
				document.forms[0].action="/slsint/workMail.do?method=saveOutlineBox";
				document.forms[0].submit();				
	  		}else{
	  			alert("<fmt:message key="sendMail.titleSize" />");
	  			return false;
	  		}
	  	}else{
	  		alert("<fmt:message key="epistoliza.alert.title" />");
	  		return false;
	  	}	  	
	}
	}
	-->
</script>
<script type="text/javascript">
<!--
     function addUser(id,name){

     	if(window.workMailForm.addressee.value != "" ){
	     	var val=window.workMailForm.addressee.value;
	     	val=val.split(",");
	     	var a=0;
	     		for(i=0;i<val.length;i++){
					if(val[i]==name){
						a++;
					}
	     		}
	     		if(a<1){
					window.workMailForm.addressee.value += ( "," + name );
				}
     	}
     	if(window.workMailForm.addressee.value == "" ){
     		window.workMailForm.addressee.value = name ;
     	}
     	if(window.workMailForm.addresseeId.value != "" ){
     		var val=window.workMailForm.addresseeId.value;
     		val=val.split(",");
     		var a=0;
     		for(i=0;i<val.length;i++){
				if(val[i]==id){
					a++;
				}
     		}
     		if(a<1){
				window.workMailForm.addresseeId.value += ( "," + id );	
			} 
     	}
     	if(window.workMailForm.addresseeId.value == "" ){
     		window.workMailForm.addresseeId.value = id ;
     	}
     }
-->
</script>
<script type="text/javascript">
<!--
	function changeTree1(){
		var organId = document.all.organId.value;
		var layer=document.getElementById("layer");
		var Access=document.getElementById("Access");

		if(organId!=""){
			send_request('workMail.do?method=queryUser&organId='+organId+'&Access='+Access.value+'&layer='+layer.value);
		}		
	}
	function layerChange(){
		var organId = document.all.organId.value;
		var layer=document.getElementById("layer");
		var Access=document.getElementById("Access");

		if(organId!=""){
			send_request('workMail.do?method=queryUser&organId='+organId+'&Access='+Access.value+'&layer='+layer.value);
		}		
	}	
	function accessChange(){
		var organId = document.all.organId.value;
		var layer=document.getElementById("layer");
		var Access=document.getElementById("Access");

		if(organId!=""){
			send_request('workMail.do?method=queryUser&organId='+organId+'&Access='+Access.value+'&layer='+layer.value);
		}		
	}		
	var http_request = false;	
	function send_request(url){
		http_request = false;
		if(window.XMLHttpRequest) { 
			http_request = new XMLHttpRequest();
			if (http_request.overrideMimeType) {
				http_request.overrideMimeType("text/xml");
			}
		}
		else if (window.ActiveXObject) { 
			try {
				http_request = new ActiveXObject("Msxml2.XMLHTTP");
			} catch (e) {
				try {
					http_request = new ActiveXObject("Microsoft.XMLHTTP");
				} catch (e) {}
			}
		}
		if(!http_request){
			alert("http_request is ERROR!");
			return false;
		}
		http_request.onreadystatechange = processRequest;
		http_request.open("POST", url, true);
		http_request.send(null);
	}	
	function processRequest() {
      var strAll = '<div style="height:350; overflow:auto; ">';
        if (http_request.readyState == 4) {
            if (http_request.status == 200) {
                var userInfo = http_request.responseText;
                var users = userInfo.split(",");
                var userId = new Array();
                var userName = new Array();
                for(var i=0;i<(users.length/2);i++){
                	userId[userId.length] = users[i];
                }
                for(var i=(users.length/2);i<users.length;i++){ 
                	userName[userName.length] = users[i];
                }                
                for(var i=0;i<(users.length/2);i++){
                	<c:if test="${isReply==1 }"> 
                	strAll += ('&nbsp;&nbsp;<a href="#">' + userName[i]+'</a>'+'<br />');
                	</c:if>
                	<c:if test="${isReply!=1 }">                	
                	strAll += ('&nbsp;&nbsp;<a href="javascript:addUser(\''+userId[i]+'\',\''+userName[i]+'\')">' + userName[i]+'</a>'+'<br />');
                	</c:if>
                }                
                document.all.user.innerHTML = strAll+'</div>';
            } else { 
                alert("");
            }
        }
    }
-->
</script>
<script type="text/javascript">
<!--
var num = 0;
function addFile(){
	var str = '<input type="file" name="file' + num + '" value="" size="55"><input type="button" name="del' + num + '" value="<fmt:message key='workFile.delete'/>" onclick="delFile('+num+');">';
	var div = document.createElement('div');
	div.id = 'div' + num;
	div.innerHTML = str;
	a.appendChild(div);
	num++;
}
function delFile(num){
	var el = document.getElementById('div' + num);
	a.removeChild(el);
}

function delole(oleId){
	var url="workMail.do?method=sendMail&oleid="+oleId+"&del=1&isReturn=<c:out value='${isReply}' />&pkId=<c:out value='${accepid}' />";
	http_request = false;
		if(window.XMLHttpRequest) { 
			http_request = new XMLHttpRequest();
			if (http_request.overrideMimeType) {
				http_request.overrideMimeType("text/xml");
			}
		}
		else if (window.ActiveXObject) { 
			try {
				http_request = new ActiveXObject("Msxml2.XMLHTTP");
			} catch (e) {
				try {
					http_request = new ActiveXObject("Microsoft.XMLHTTP");
				} catch (e) {}
			}
		}
		if(!http_request){
			alert("http_request is ERROR!");
			return false;
		}
		http_request.open("POST", url, true);
		http_request.send(null);

}
//-->
</script>
<script type="text/javascript">
<!--
	function cleanValue(){
		document.workMailForm.addressee.value = "";
		document.workMailForm.addresseeId.value="";
	}
//-->
</script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0"
	bgcolor="EEEEEE">
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
					key="navigation.workfile.sendmail" /></b></font></p>
				</td>
				<td></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td width="100%" bgcolor="#EEEEEE" valign="top"><br>
		<table border="0" cellpadding="0" cellspacing="0" bgcolor="EEEEEE">
			<tr>
				<td width="70%" align="center"><html:form action="workMail.do?method=saveMail" styleId="workMailForm" enctype="multipart/form-data">
					<table border="0" align="center" cellSpacing="1" cellPadding="0"
						class="TableBGColor">
						<tr>
							<td class="TdBgColor1" align="right"><fmt:message
								key="epistoliza.addressee" /></td>
							<td class="TdBgColor1"><input type="text" name="addressee"
								value="<c:out value="${workMailForm.addressee}" />" style="width:450" readOnly="true" />
								<c:if test="${isReply!=1 }">
								<input type="button" name="btn" value="<fmt:message key="sendMail.clean" />" onClick="cleanValue();" />
								</c:if>
								<c:if test="${isReply==1 }">
								<input type="button" name="btn" value="<fmt:message key="sendMail.clean" />" onClick="cleanValue();" />
								</c:if>
								</td>
						</tr>
						<tr>
							<td class="TdBgColor1" align="right"><fmt:message
								key="epistoliza.title" /></td>
							<td class="TdBgColor1">
							<c:if test="${isReply!=1 }">
							<input type="text" name="title" value="<c:out value='${workMailForm.title}' />" style="width:500" />
							</c:if>
							<c:if test="${isReply==1 }">
							<input type="text" name="title" value="<c:out value='${workMailForm.title}' />" style="width:500" readOnly="true"/>
							</c:if>
							</td>
						</tr>
						<tr>
							<td class="TdBgColor1" align="right" valign="top" ><fmt:message	key="epistoliza.content" /></td>
							<td class="TdBgColor1">
							<input type="hidden" id="content" name="content" value="<c:out value='${workMailForm.content}'/>">
							  <input type="hidden" id="content___Config" value="">
							<div>
							  <iframe id="content___Frame" src="<c:url value='/wordEdit/editor/fckeditor.html?InstanceName=content&Toolbar=Default' />" width="100%" height="300" frameborder="no" scrolling="no"></iframe>
							</div>
							<script type="text/javascript">
								<!--
								if(window.workMailForm.content.value.length <1200){
									alert("eeee");
									return true;
								}else{
									alert("<fmt:message key="sendMail.contentSize" />");
									return false;
								}
								-->
							</script>
							</td>
						</tr>
						<c:forEach var="oles" items="${oles}" varStatus="ol">
						<div id="odiv<c:out value='${ol.index}'/>">
						<tr>
						   <td  class="TdBgColor1"></td>
						   <td  class="TdBgColor1" id=""><label><c:out value='${oles.fileName}'/></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="#" onclick="delole(<c:out value='${oles.pkid}'/>);"><fmt:message key='workFile.delete'/></a>
  						   </td>
						</tr>
						</c:forEach>
						<tr>
						   <td  class="TdBgColor1"></td>
						   <td  class="TdBgColor1" id="a">
  						   </td>
						</tr>
						<tr>
							<td class="TdBgColor1" align="right">
							<input type="hidden" name="fileNum" value="1" id="fileNum" />
							<input type="hidden" name="fileName" value="" id="fileName" /></td>
							<td class="TdBgColor1">
							<a href="#" onClick="addFile();">
							  <fmt:message key="sendMail.addFile" />
							</a>
							<fmt:message key="sendMail.fileSize" /><%=accessorySize %>M
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<c:if test="${isReturn==1 }">
							<input type="checkbox" name="isReturn" value="1" checked="checked"/>
							</c:if>
							<c:if test="${isReturn!=1 }">
							<input type="checkbox" name="isReturn" value="1"/>
							</c:if>
							 <fmt:message key="sendMail.isReturn" />
							</td>
						</tr>
						<tr>
							<td colspan="2" class="FormBottom" align="center">
							<input type="submit" name="submit" style="width:100"
								value="<fmt:message key="epistoliza.send" />"
								onclick="return checkInput();" />
								<input type="submit" name="saveOutlineBox" value="<fmt:message key="epistoliza.saveOutlineBox"/>" onclick="return saveOutlineBox1();"/>
								</td>
								
								
						</tr>
					</table>
					<c:if test="${isReply==1 }">
					<input type="hidden" name="isReply" value="<c:out value="${isReply}" />"/>
					</c:if>
					<input type="hidden" name="rmailId" value="<c:out value="${rmailId}" />"/>
					<input type="hidden" name="accepid" value="<c:out value="${accepid}" />"/>
					<input type="hidden" name="addresserId"
						value="<c:out value="${user.pkid}" /> ">
					<input type="hidden" name="addresseeId" value="<c:out value="${workMailForm.addresseeId}" />"></td>

				<td width="300" align="center" valign="top">
				<table border="0" align="left" cellSpacing="1" cellPadding="0"
					class="TableBGColor">
					<tr>
						<td class="TdBgColor1" align="center" height="30"><b><fmt:message
							key="epistoliza.addressList" /></b></td>
					</tr>
					<tr>
						<td>
							<input type="hidden" name="organId" value="<c:out value="${OrganId}"/>" />
					        <input type="hidden" name="organName" value="" />
					      	<slsint:ActiveXTree left="220" top="325" width="240" height="${activeXTreeHeight }"
							xml="${orgTreeURL}"
							bgcolor="0xFFD3C0" 
							rootid="${OrganId}" 
							columntitle="${colName}" 
							columnwidth="240,0,0,0" 
							formname="workMailForm" 
							idstr="organId" 
							namestr="organName" 
							checkstyle = "0" 
							filllayer="2" 
							txtwidth="240"
							buttonname="${orgButton}">
					      	</slsint:ActiveXTree>
						</td>
					</tr>
					<tr>
						<td>
						<select name="layer" id="layer" onchange="layerChange();">
                			<option value="0" selected="selected"><fmt:message key="riskWarning.bjjg" /></option>
                			<option value="1"><fmt:message key="riskWarning.xyj" /></option>
                			<option value="2"><fmt:message key="riskWarning.xlj" /></option>
                			<option value="3"><fmt:message key="riskWarning.all" /></option>
              			</select>
						</td>
					</tr>
					<tr>
						<td>
						
						<select name="Access" id="Access" onchange="accessChange();">
						<option value="all"><fmt:message key="formuladefine.filter.all" /></option>
						<c:forEach items="${AccessList}" var="Role">
                			<option value="<c:out value="${Role.roleType}" />"><c:out value="${Role.name}" /></option>
                		</c:forEach>
              			</select>
						</td>
					</tr>
					<tr id="userList">
						<td class="FormBottom" height="350" valign="top" id="user">
						<div style="height:350; overflow:auto; ">
						<c:forEach items="${userList}" var="user">
						&nbsp;&nbsp;
						<c:if test="${isReply!=1 }">
							<a href="#" onclick="addUser('<c:out value="${user.pkid}" />' , '<c:out value="${user.name}" />' )">
							<c:out value="${user.name}" /> </a>
							<br />
						</c:if>
						<c:if test="${isReply==1 }">
							<a href="#">
							<c:out value="${user.name}" /> </a>
							<br />
						</c:if>
						</c:forEach>
						</div>						
						</td>
					</tr>
				</table>
				</td>
			</tr>
		</table>
		</html:form>
	  </td>
	</tr>
</table>
</body>
</html>
<c:if test="${fileSize == '9'}" >
<script type="text/javascript">
	alert("<fmt:message key="file.size" />");
</script>
</c:if>
<c:if test="${content == '9'}" >
<script type="text/javascript">
	alert("<fmt:message key="sendMail.contentSize" />");
</script>
</c:if>