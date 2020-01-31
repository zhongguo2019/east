<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">

<c:set var="colName">
	<fmt:message key="organTree.colName"/>
</c:set>
<c:set var="orgTreeURL">
	<%-- <c:out value="${hostPrefix}"/><c:url value="/treeAction.do?method=getOrganTreeXML${params}"/> --%>
	<c:out value="${hostPrefix}"/><c:url value='/organTreeAction.do?method=tree'/>
</c:set>

<c:set var="orgButton">
	<fmt:message key="place.select"/>
</c:set>

<html>
<head>
<title><fmt:message key="webapp.prefix"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
<link rel="stylesheet" href="<c:url value='${cssPath}/css.css" type="text/css'/>"/>
        <script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
        
		<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-win2k-1.css'/>" title="win2k-cold-1" /> 
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/lang/zh-cn.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-setup.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/ActiveXTree/ActiveXTree.js'/>"></script>
</head>
<body leftmargin="0" bgcolor=#eeeeee >
<script type="text/javascript">  
       //屏蔽页面中不可编辑的文本框中的backspace按钮事件  
       function keydown(e) {  
           var isie = (document.all) ? true : false;  
           var key;  
           var ev;  
           if (isie){ //IE和谷歌浏览器  
               key = window.event.keyCode;  
               ev = window.event;  
           } else {//火狐浏览器  
               key = e.which;  
               ev = e;  
           }  
  
           if (key == 8) {//IE和谷歌浏览器  
               if (isie) {  
                   if (document.activeElement.readOnly == undefined || document.activeElement.readOnly == true) {  
                       return event.returnValue = false;  
                   }   
               } else {//火狐浏览器  
                   if (document.activeElement.readOnly == undefined || document.activeElement.readOnly == true) {  
                       ev.which = 0;  
                       ev.preventDefault();  
                   }  
               }  
           }  
       }  
  
       document.onkeydown = keydown;  
   </script> 
   <!-- <c:url value='/organTreeAction.do?method=tree'/> -->
<!--<fmt:message key="organTree.area"/>-->
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
    <td colspan="2" width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td>
            <p style="margin-top: 3"><font class=b><b><fmt:message key="usermanage.sysadmin.menu"/><fmt:message key="navigation.arrowhead"/><fmt:message key="usermanage.user.menu"/></b></font></p>                         
          </td>
		  <td></td> 
        </tr>
      </table>
    </td>
  </tr>
  <tr>
  <td width="3%" background="<c:url value="${imgPath}/f05.gif"/>"></td>
    <td width="97%" background="<c:url value="${imgPath}/f05.gif"/>" valign="top">
    <p>
    </p>	
    <br>
 
<html:hidden property="again" value="0"/>
	<a href="javascript:newUser();"/>
			        <fmt:message key="usermanage.user.adduser"/>
			    </a>
	<br><br>
	<form action="" method="post" name="userForm">
	<table>
	<tr>
	<td width="40%">
	<input type="hidden" name="organId" value="<c:out value='${areaCode}' />"/>
								<input type="hidden" name="organName"/>
      						<slsint:ActiveXTree left="220" top="325" width="260" height="200" 
					      		xml="${orgTreeURL}" 
					      		bgcolor="0xFFD3C0" 
					      		rootid="${rootId}"
					      		columntitle="${colName}" 
					      		columnwidth="260,0,0,0" 
					      		formname="userForm" 
					      		idstr="organId" 
					      		namestr="organName" 
					      		checkstyle = "0" 
					      		filllayer="2" 
					      		txtwidth="260"
					      		buttonname="${orgButton}">
    						</slsint:ActiveXTree>
  	</td>
  	<td  width="5%" align="right">
  	</td>
  	<td><fmt:message key="groupmanage.del.displayinfo.username"/><fmt:message key="symbol.colon.fullwidth"/>
  	<input type="text" name="username" id="username" value="<c:out value='${username}'/>" width="150">	
  	</td>
  <%-- 	<td><fmt:message key="usermanage.idCard"/>
  	<input type="text" name="idCard" id="idCard"  width="150">	
  	</td> --%>
  	<td><fmt:message key="logpage.loginname"/>
  	<input type="text" name="loginname" id="loginname" value="<c:out value='${loginname}'/>" width="150">
  	<input type="button" value="<fmt:message key="fivegraderep.base.button.query"/>" onclick="javascript:fuzzySearch();"/>
  	</td> 	
  <%-- 	<td><fmt:message key="usermanage.tellerId"/>
  	<input type="text" name="tellerId" id="tellerId"  width="150">
  	<input type="button" value="<fmt:message key="fivegraderep.base.button.query"/>" onclick="javascript:fuzzySearch();"/>
  	</td>  --%>	
  	</tr>
  	</table>
	<center>
	
	    <div id="screen">
			<display:table name="userList" cellspacing="0" cellpadding="0"  
				    requestURI="" defaultsort="1" id="users" width="100%" 
				    pagesize="18" styleClass="list userList">
				    <%-- Table columns --%>
				    <display:column sort="true" titleKey="usermanage.sysadmin.list.username" headerClass="sortable" 
				    	width="10%">
				    	<c:out value="${users.name}"/>
				    </display:column>
				        
				    <display:column property="logonName" sort="true" titleKey="usermanage.sysadmin.list.logonname" 
				    	headerClass="sortable" width="12%"/>
				    	
				    <display:column property="roleName" sort="true" titleKey="usermanage.sysadmin.list.rolename"
				    	headerClass="sortable" width="12%"/>
	
				    <display:column sort="true" headerClass="sortable" 
				    	width="18%">
				    	<c:if test="${users.roleType!=2}">
				    	<a href="javascript:editUserReport(<c:out value="${users.pkid}"/>)">
				        	<fmt:message key="button.editcontrast"/>
				        </a>&nbsp;
				        <a href="javascript:editUser(<c:out value="${users.pkid}"/>)">
				        	<fmt:message key="button.edit"/>
				        </a>&nbsp;				        
				        <a href="userAction.do?method=del&userid=<c:out value="${users.pkid}"/>" onClick="if(confirm('<fmt:message key="author.alert.delAlert"/>')){ return true;} else{ return false;}">
				        	<fmt:message key="button.delete"/>
				        </a>	
				        </c:if>		        
				    </display:column>
				    
				    
				</display:table>
				<script type="text/javascript">
					<!--
						highlightTableRows("users");
					//-->
				</script>
				
	    </div>
	    
	</center>
</form>
</td>
</tr>
</table>
</body>

</html>
<script type="text/javascript">
function changeTree1(){
	changeOrg();
	var areaCode = document.userForm.organId.value;
	var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/userAction.do?method=list&areaCode="+areaCode;
	document.userForm.action = url;
	document.userForm.submit();
}
function changeOrg(){
	var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/treeAction.do?method=getOrganTreeXML";
	document.all.objTree1.SetXMLPath(url);
   	document.all.objTree1.Refresh();	
}
function fuzzySearch(){
   
	 /* if(document.userForm.idCard.value == "")
		{
			alert("<fmt:message key="error.idCard"/>");
		    return false;
		}
		if(!document.userForm.idCard.value.match(/^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/))
		{
			alert("<fmt:message key="error1.idCard"/>");
		    return false;
		}
		if(document.userForm.tellerId.value == "")
		{
			alert("<fmt:message key="error.tellerId"/>");
		    return false;
		}
	 var idCard=document.getElementById("idCard").value;
		 idCard = encodeURI(idCard);
		 idCard = encodeURI(idCard);
		var tellerId=document.getElementById("tellerId").value;
		tellerId = encodeURI(tellerId);
		tellerId = encodeURI(tellerId);
		var areaCode = document.userForm.areaId.value;
		var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/userAction.do?method=list&fuzzy=yes&idCard="+idCard+"&tellerId="+tellerId+"&areaCode="+areaCode;
		 
	  */
	  var userName=document.getElementById("username").value;
		userName = encodeURI(userName);
		userName = encodeURI(userName);
		var loginName=document.getElementById("loginname").value;
		loginName = encodeURI(loginName);
		loginName = encodeURI(loginName);
		var areaCode = document.userForm.organId.value;
		var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/userAction.do?method=list&fuzzy=yes&username="+userName+"&loginname="+loginName+"&areaCode="+areaCode;
		 
	  
	document.userForm.action = url;
	document.userForm.submit();
	
}
function newUser(){
	  // var roleLevel = "<c:out value="${roleLevel}"/>";
	//if(roleLevel!='3')
	//	{   
		/* 	var areaId = document.userForm.areaId.value;
			var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/userAction.do?method=toEdit&areaCode="+areaId; */

			//window.location.href = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/usermanage/userForm.jsp";
		    var areaId = document.userForm.organId.value;
		//	var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/userAction.do?method=searchBeforeAdd";
			var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/userAction.do?method=toEdit&areaCode="+areaId;
		//	var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/userAction.do?method=save";
			document.userForm.action = url;
			document.userForm.submit(); 
	//   	}
	//else
	//	{
	//	
	//		alert("<fmt:message key="usermanage.adduser.right"/>");
	        return ;
	//	}   
}
function editUser(userId){
	var fuzzy="<c:out value="${fuzzy}"/>";
	if(fuzzy=='yes')
	{
		//var areaId = document.userForm.areaId.value;
		var areaId = document.userForm.organId.value;
		var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/userAction.do?method=edit&fuzzy="+fuzzy+"&userid="+userId+"&areaCode="+areaId;
		document.userForm.action = url;
		document.userForm.submit();
	}else{
		//var areaId = document.userForm.areaId.value;
		var areaId = document.userForm.organId.value;
		var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/userAction.do?method=edit&userid="+userId+"&areaCode="+areaId;
		document.userForm.action = url;
		document.userForm.submit();
	}
}
function editUserReport(userId){
	//var areaId = document.userForm.areaId.value;
	var areaId = document.userForm.organId.value;
	var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/userReportAction.do?method=editUserReport&showlevel=&userid="+userId+"&areaCode="+areaId;
	document.userForm.action = url;
	document.userForm.submit();
}
</script>


