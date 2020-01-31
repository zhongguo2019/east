<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/meta.jsp"%>
<c:set var="colName">
	<fmt:message key="organTree.colName"/>
</c:set>
<c:set var="orgTreeURL">
	<c:out value="${hostPrefix}"/><c:url value='/treeAction.do?method=getAreaTreeXML&areaCode=${rootId}'/>
</c:set>
<c:set var="orgButton">
	<fmt:message key="organTree.area"/>
</c:set>
<html>
<head>
<title><fmt:message key="webapp.prefix"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">

<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
<link rel="stylesheet" href="<c:url value='${cssPath}/css.css" type="text/css'/>"/>
       <script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/ActiveXTree/ActiveXTree.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/ps/scripts/prototype.js'/>"></script>
		
		<script type="text/javascript">
//window.parent.document.getElementById("ppppp").value= "<fmt:message key="usermanage.sysadmin.menu"/><fmt:message key="navigation.arrowhead"/><fmt:message key="usermanage.role.main"/>";
</script>
		
</head>
<body leftmargin="0" >
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  
  <tr>
    <td width="100%" background="<c:url value="${imgPath}/f05.gif"/>" valign="top">
    <p>
    </p>
    <br>
    &nbsp;&nbsp;
     <a href="javascript:saveAuthor();"><fmt:message key="role.submit"/></a>
    &nbsp;|&nbsp;
    <a href="javascript:isNull();" ><fmt:message key="role.reset"/></a>
    &nbsp;|&nbsp;
    <a href="reportAction.do?method=list" ><fmt:message key="role.channel"/></a>
<form name="authorForm" action="" method="post">
<table  align="center" class="TableBGColor" width="100%" border="0" cellSpacing=1 cellPadding=5 >    
<br>

	
		<tr style="height:10">
			<input type="hidden" name="reportId" value="<c:out value="${authorReport.pkid}"/>"/>
	    	<input type="hidden" name="typeId" value="<c:out value="${authorReportType.pkid }"/>"/>
			<td class="TdBGColor1" width="12%" align="right"><fmt:message key="reportdefine.report.name"/></td>
			<td class="TdBGColor2" width="40%"><input type="text" name="roleType"  value="<c:out value="${authorReport.name}"/>" readOnly="true" size="45"/></td>
			<td class="TdBGColor1" width="12%" align="right" ><fmt:message key="reportdefine.report.reportType"/></td>
			<td class="TdBGColor2" width="40%"><input type="text" name="name" value="<c:out value="${authorReportType.name}"/>" readOnly="true" size="35"/></td>
		</tr>			
		<tr height="10">
			<td class="TdBGColor1" width="12%" align="right"><fmt:message key="report.area"/></td>
			<td class="TdBGColor2" width="80%" colspan="3">
			<input type="hidden" name="areaId" value="<c:out value="${areaCode}"/>"/>
      		<input type="hidden" name="areaName"/>
      		<input type="hidden" name="rootId" value="<c:out value="${rootId }"/>"/>
      					<slsint:ActiveXTree left="220" top="325" width="260" height="${activeXTreeHeight }"
					      		xml="${orgTreeURL}" 
					      		bgcolor="0xFFD3C0" 
					      		rootid="${rootId}"
					      		columntitle="${colName}" 
					      		columnwidth="260,0,0,0" 
					      		formname="authorForm" 
					      		idstr="areaId" 
					      		namestr="areaName" 
					      		checkstyle = "0" 
					      		filllayer="2" 
					      		txtwidth="260"
					      		buttonname="${orgButton}">
    						</slsint:ActiveXTree>
			</td>
		</tr>

</table>
<table  align="center" class="TableBGColor" width="100%" border="0" cellSpacing=1 cellPadding=5 height="200">
		<tr>
			<td class="TdBGColor1" width="40%" align="right" >
				<select name="resource" size="6" multiple style="width:300px;height:350px">
    					<c:forEach items="${userList}" var="user" varStatus="status">								
								<option value="<c:out value="${user[0]}"/>"><c:out
									value="${user[1]}" /></option>								
						</c:forEach>
    				</select>
			</td>
			<td class="TdBGColor2" width="8%" align="center">
				<input type="button" name="addBtn" value="<fmt:message key="role.addRole"/>" onClick="javascript:addRole();"/><br/>
    			<br><br><br><br>
    			<input type="button" name="delBtn" value="<fmt:message key="role.delRole"/>" onClick="javascript:delRole();"/>
			</td>
			<td class="TdBGColor1" width="40%" align="left" >
				<select name="target" size="6" multiple style="width:300px;height:350px">
    					<c:forEach items="${repUserList}" var="repUser" varStatus="status">
    						<option value="<c:out value="${repUser[0]}"/>">
    							<c:out value="${repUser[1]}"/>
    						</option>
    					</c:forEach>
    			</select>
			</td>			
		</tr>
		<div id="users" style="display:none"></div>	
</table>
</form>
</td>
</tr>
</table>
</body>
</html>
<script type="text/javascript">
<!--

var userJson = '<c:out value="${userJson}" escapeXml="false"/>';
var repUserJson = '<c:out value="${repUserJson}" escapeXml="false"/>';
var userList;
var repUserList;
eval('userList='+userJson);
eval('repUserList='+repUserJson);
function changeTree1(){
	var area = document.authorForm.areaId.value;
		if(allAreaOfUsers[area]){
			updateUsers();
		}else{
			observePro();
		}
}
function observePro(){
	var areaCode = document.authorForm.areaId.value;
	var options={onSuccess:processRequest}
	var url='<c:out value="${hostPrefix}" /><c:url value="/reportAction.do" />?method=changeAreaTree&areaCode='+areaCode;
	pu = new Ajax.Updater($("users"),url,options);
}
var allAreaOfUsers = new Array();
var AreaOfUsersLength = 0;
function processRequest(request) {
		var userJson = request.responseText;
	  	var area = document.authorForm.areaId.value;
	  	var areaOfUsers;
	  	eval('areaOfUsers='+userJson);
	  	allAreaOfUsers[AreaOfUsersLength++] = allAreaOfUsers[area] = areaOfUsers;
	  	updateUsers();
}
function updateUsers(){
		var area = document.authorForm.areaId.value;
		var users = allAreaOfUsers[area];
		document.authorForm.resource.options.length = users.length;
	  	for(var i = 0; i < users.length; i++){
	  		document.authorForm.resource.options[i] = new Option(users[i].name,users[i].id);
	  	}
	}
//-->
</script>
<script type="text/javascript">
<!--
function isexist(va){
  var ee = false;          
  for(var i=0;i<document.authorForm.target.options.length;i++)
    if(document.authorForm.target.options[i].value==va)
     { ee=true; break;}
  return ee;
}
function addRole(){
   for(var i=document.authorForm.resource.options.length-1;i>=0;i--)
      if(document.authorForm.resource.options[i].selected) {
        if(!isexist(document.authorForm.resource.options[i].value)){ 
	        document.authorForm.target.options[document.authorForm.target.options.length]
	        = new Option(document.authorForm.resource.options[i].text,document.authorForm.resource.options[i].value);
        }
      }
}
function delRole(){
   for(var i=document.authorForm.target.options.length-1;i>=0;i--){
      if(document.authorForm.target.options[i].selected){
      		  document.authorForm.target.options[i] = null;
	   }
	}
}
function isNull(){
	document.authorForm.target.options.length = 0;
}
function saveAuthor(){
	var selectedUser = "";
	for(var i = 0; i < document.authorForm.target.options.length; i++){
		if(selectedUser == ""){
			selectedUser += document.authorForm.target.options[i].value;
		}else{
			selectedUser += (","+document.authorForm.target.options[i].value)
		}
	}
	//alert(selectedUser);
	var url='<c:out value="${hostPrefix}" /><c:url value="/reportAction.do" />?method=saveAuthor&authorUser='+selectedUser;
	document.authorForm.action = url;
	document.authorForm.submit();
}
//-->
</script>