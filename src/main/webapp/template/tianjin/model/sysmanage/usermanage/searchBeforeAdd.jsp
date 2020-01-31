<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
<c:set var="colName">
	<fmt:message key="exportXML.tree.org.name"/>
</c:set>
<c:set var="orgTreeURL">
	<c:out value="${hostPrefix}" /><c:url value="/treeAction.do?method=getOrganTreeXML${orgparam}"/>
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
        
		<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/common/jscalendar/calendar-win2k-1.css'/>" title="win2k-cold-1" /> 
		<script type="text/javascript" src="<c:url value='/common/jscalendar/calendar.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/common/jscalendar/lang/zh-cn.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/common/jscalendar/calendar-setup.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/common/ActiveXTree/ActiveXTree.js'/>"></script>
</head>
<body leftmargin="0" bgcolor=#eeeeee >
<script type="text/javascript">  
      
       function keydown(e) {  
           var isie = (document.all) ? true : false;  
           var key;  
           var ev;  
           if (isie){ //IE鍜岃胺姝屾祻瑙堝櫒  
               key = window.event.keyCode;  
               ev = window.event;  
           } else {//鐏嫄娴忚鍣� 
               key = e.which;  
               ev = e;  
           }  
  
           if (key == 8) {//IE鍜岃胺姝屾祻瑙堝櫒  
               if (isie) {  
                   if (document.activeElement.readOnly == undefined || document.activeElement.readOnly == true) {  
                       return event.returnValue = false;  
                   }   
               } else {//鐏嫄娴忚鍣� 
                   if (document.activeElement.readOnly == undefined || document.activeElement.readOnly == true) {  
                       ev.which = 0;  
                       ev.preventDefault();  
                   }  
               }  
           }  
       }  
  
       document.onkeydown = keydown;  
   </script> 
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
    <td colspan="2" width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td>
            <p style="margin-top: 3"><fmt:message key="usermanage.sysadmin.menu"/><fmt:message key="navigation.arrowhead"/><fmt:message key="usermanage.user.adduser"/></b></font></p>                         
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
	<a href="<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/userAction.do?method=list"/>
			        <fmt:message key="button.back"/>
     </a>
	<br><br>
	<form action="" method="post" name="userForm">
	<table>
	<tr>
	<td  class="TdBgColor1" align="right"><fmt:message key="usermanage.organId"/></td>
	<td width="40%">
				<input type="hidden" name="organId" value="<c:out value="${areaCode}"/>"/>
   				<input type="hidden" name="organName"/>
   				<slsint:ActiveXTree left="220" top="325" width="260" height="${activeXTreeHeight }" 
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
		      		buttonname="${orgButton}" >
 						</slsint:ActiveXTree>
  	</td>
  	<td  width="5%" align="right">
  	</td>
  	
  	<td><fmt:message key="usermanage.idCard"/>
  	<input type="text" name="idCard" id="idCard"  width="150">	
  	</td>
  	<td><fmt:message key="usermanage.tellerId"/>
  	<input type="text" name="tellerId" id="tellerId"  width="150">
  	<input type="button" value="<fmt:message key="fivegraderep.base.button.query"/>" onclick="javascript:fuzzySearch();"/>
  	</td> 	
  	</tr>
  	</table>
	<center>
	    <div id="screen">
			<display:table name="udlist" cellspacing="0" cellpadding="0"  
				    requestURI="" defaultsort="1" id="users" width="100%" 
				    pagesize="18" styleClass="list userList">
				    <%-- Table columns --%>
				  
				    <display:column sort="true" titleKey="usermanage.tellerId" headerClass="sortable" 
				    	width="10%">
				    	<c:out value="${users.tellerId}"/>
				    </display:column>
				    <display:column property="name" sort="true" titleKey="usermanage.sysadmin.list.username" headerClass="sortable" 
				    	width="10%">
				    	<%-- <c:out value="${users.name}"/> --%>
				    </display:column>
				        
				    <display:column property="idCard" sort="true" titleKey="usermanage.idCard" 
				    	headerClass="sortable" width="12%"/>
				    	
				    <display:column property="phone" sort="true" titleKey="usermanage.phone" 
				    	headerClass="sortable" width="12%"/>
	
				    <display:column sort="true" headerClass="sortable" 
				    	width="18%">
 				    	<a href="<c:url value="/userAction.do?method=toAdd&organId=${users.organId}&name=${users.name}&tellerId=${users.tellerId }&&areaCode=${organId}"/>">
				    	<!-- <a href="javascript:addUser()"> -->
 				        	<fmt:message key="button.add"/>
				        </a>
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
	var organId = document.userForm.organId.value;
}  
function fuzzySearch(){
  if(document.userForm.idCard.value != "")
	{
	  if(!document.userForm.idCard.value.match(/^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/))
		{
			alert("<fmt:message key="error1.idCard"/>");
		    return false;
		}
	}
	var idCard=document.getElementById("idCard").value;
	 idCard = encodeURI(idCard);
	 idCard = encodeURI(idCard);
	var tellerId=document.getElementById("tellerId").value;
	tellerId = encodeURI(tellerId);
	tellerId = encodeURI(tellerId);
    var organId=document.getElementById("organId").value;
	organId = encodeURI(organId); 
	var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/userAction.do?method=searchBeforeAdd&beforeadd=yes&idCard="+idCard+"&tellerId="+tellerId;
	document.userForm.action = url;
	document.userForm.submit();
}
</script>


