<!-- /ps/model/sysmanage/usermanage/searchBeforeAdd. -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page pageEncoding="UTF-8"%>
<%@ page import="com.krm.ps.sysmanage.usermanage.vo.*"%>
<%@ page import="com.krm.ps.util.SysConfig"%>
<%@ page import="com.krm.ps.util.FuncConfig"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
<%
   User user = (User)request.getSession().getAttribute("user");
   String orgId = user.getOrganId();   
 %>
 <c:set var="flagOrgan" value="1"/>
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

<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${displaytag12Path}/displaytag.css'/>" />
 <link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>
</head>
<body leftmargin="0" >
<script type="text/javascript">  
$(document).ready(function(){
	var flagOrgan = <c:out value='${flagOrgan}' />
	if(flagOrgan==2){
		$("#flagOrgan1").remove();
	}else{
		$("#flagOrgan2").remove();
	} 
	  getOrganTreeXML();
});  

 function getOrganTreeXML(){
	 $.post("treeAction.do?method=getOrganTreeXML",function(data){
			var treeXml = eval(data)[0].treeXml;
			$('#combotreeOrganTree').combotree('loadData', treeXml);
		}); 
 }     
       function keydown(e) {  
           var isie = (document.all) ? true : false;  
           var key;  
           var ev;  
           if (isie){ //IEéå²èºå§å±¾ç¥»çå æ«  
               key = window.event.keyCode;  
               ev = window.event;  
           } else {//éî¤å«å¨´å¿îé£ï¿½ 
               key = e.which;  
               ev = e;  
           }  
  
           if (key == 8) {//IEéå²èºå§å±¾ç¥»çå æ«  
               if (isie) {  
                   if (document.activeElement.readOnly == undefined || document.activeElement.readOnly == true) {  
                       return event.returnValue = false;  
                   }   
               } else {//éî¤å«å¨´å¿îé£ï¿½ 
                   if (document.activeElement.readOnly == undefined || document.activeElement.readOnly == true) {  
                       ev.which = 0;  
                       ev.preventDefault();  
                   }  
               }  
           }  
       }  
  
       document.onkeydown = keydown;  
       
       window.parent.document.getElementById("ppppp").value= "<fmt:message key="usermanage.sysadmin.menu"/><fmt:message key="navigation.arrowhead"/><fmt:message key="usermanage.user.adduser"/>";
   </script>  
<html:hidden property="again" value="0"/>
<div class="navbar2">
	<a href="<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/userAction.do?method=list" class="easyui-linkbutton" ><fmt:message key="button.back"/></a>
</div>
<div class="navbar3">
	<form action="" method="post" name="userForm">
	<table>
		<tr>
			<td>
				<fmt:message key="usermanage.organId"/>
			</td>
			<td>
				<div id="flagOrgan1">
					<input id="combotreeOrganTree" class="easyui-combotree"  value="<%=orgId %>"  style="width:380"/>
				</div>
			</td>
			<td>
				<div id="flagOrgan2">
					<input id="combotreeOrganTree" class="easyui-combotree" multiple value="<%=orgId %>"  style="width:380"/>
				</div>
			</td>
			<td>
				<fmt:message key="usermanage.idCard"/>
			</td>
			<td>
				<input type="text" name="idCard" id="idCard"  width="150">	
			</td>
			<td>
				<fmt:message key="usermanage.tellerId"/>
			</td>
			<td>
				<input type="text" name="tellerId" id="tellerId"  width="150">
			</td>
			<td>
				<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style=" width:80; height: 20;text-decoration:none;" name="ss" onclick="javascript:fuzzySearch();" ><fmt:message key='fivegraderep.base.button.query'/></a>
			</td>
		</tr>
	</table>
</div>
 

	
				<input type="hidden" name="organId" value="<c:out value="${areaCode}"/>"/>
   				<input type="hidden" name="organName"/>
   				

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
					
						//highlightTableRows("users");
					
				</script>
				
	    </div>
	    
	</center>
</form>

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

	
		var areaCode = $("#combotreeOrganTree").combotree("getValue");
		var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/userAction.do?method=searchBeforeAdd&beforeadd=yes&idCard="+idCard+"&tellerId="+tellerId;
		 
	  
	document.userForm.action = url;
	document.userForm.submit();
}
</script>


