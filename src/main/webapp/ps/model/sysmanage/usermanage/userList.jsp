<!-- /ps/model/sysmanage/usermanage/userList. -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page pageEncoding="UTF-8"%>
<%@ page import="com.krm.ps.sysmanage.usermanage.vo.*"%>
<%@ page import="com.krm.ps.util.SysConfig"%>
<%@ page import="com.krm.ps.util.FuncConfig"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
 
<c:set var="flagOrgan" value="1"/>
<%-- <c:set var="colName">
	<fmt:message key="organTree.colName"/>
</c:set>
<c:set var="orgTreeURL">
	<c:out value="${hostPrefix}"/><c:url value="/treeAction.do?method=getOrganTreeXML${params}"/>
	<c:out value="${hostPrefix}"/><c:url value='/organTreeAction.do?method=tree'/>
</c:set>

<c:set var="orgButton">
	<fmt:message key="place.select"/>
</c:set> --%>

<html>
<head>
<title><fmt:message key="webapp.prefix"/></title>
<!-- <meta http-equiv="Content-Type" content="text/html; charset=GBK"> -->
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${displaytag12Path}/displaytag.css'/>" />
	 <script language="javascript" type="text/javascript" src="<c:url value='ps/uitl/My97DatePicker/WdatePicker.js'/>"></script>
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

 function getOrganTreeXML(){//机构树
	 $.post("treeAction.do?method=getOrganTreeXML",function(data){
			var treeXml = eval(data)[0].treeXml;
			$('#combotreeOrganTree').combotree('loadData', treeXml);
		}); 
 }
 
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
       
      // window.parent.document.getElementById("ppppp").value= "<fmt:message key="usermanage.sysadmin.menu"/><fmt:message key="navigation.arrowhead"/><fmt:message key="usermanage.user.menu"/>";
   </script> 

<html:hidden property="again" value="0"/>

<form action="" method="post" name="userForm">
	

<div class="navbar">
	<table>
		<tr>
			<td>
				<div id="flagOrgan1">
					<input id="combotreeOrganTree"  name="organ_id"  class="easyui-combotree"  value="<c:out value='${areaCode}'/>"  style="width:300px"/>
				</div>
			</td>
			<td>
				<div id="flagOrgan2">
					<input id="combotreeOrganTree" name="organ_id"  class="easyui-combotree" multiple value="<c:out value='${areaCode}'/>"  style="width:300px"/>
				</div>
			</td>
			<td>
				<fmt:message key="groupmanage.del.displayinfo.username"/><fmt:message key="symbol.colon.fullwidth"/>
			</td>
			<td>
				<input type="text" name="username" id="username" value="<%--<c:out value='${username}'/>--%>" width="150px">
			</td>
			<td>
				<fmt:message key="logpage.loginname"/>
			</td>
			<td>
				<input type="text" name="loginname" id="loginname" value="<%--<c:out value='${loginname}'/>--%>" width="150px">
			</td>
			<td>
				<a href="#" class="easyui-linkbutton buttonclass2" data-options="iconCls:'icon-search'"  name="ss" onclick="javascript:fuzzySearch();" ><fmt:message key='fivegraderep.base.button.query'/></a>
			</td>
		</tr>
	</table>
</div>
<div class="navbar2">	
<input type="hidden" name="organId" id="organId" value="<c:out value='${areaCode}'/>">
	
<a href="javascript:newUser();" class="easyui-linkbutton" data-options="iconCls:'icon-add'"  ><fmt:message key='usermanage.user.adduser'/></a>&nbsp;&nbsp;
<a href="javascript:void(0);" class="easyui-linkbutton buttonclass2" data-options="iconCls:'icon-exp'" onclick="exportData(); " style="height:20px;width:60px;text-decoration:none;"><fmt:message key='cheacc.exportfile'/></a>
</div>	

			<display:table name="userList" cellspacing="0" cellpadding="0"  
				    requestURI="" defaultsort="1" id="users" width="100%" 
				    pagesize="18" styleClass="list userList">
				    <%-- Table columns --%>
				    <display:column property="name" sort="true" titleKey="usermanage.sysadmin.list.username" headerClass="sortable" 
				    	width="10%">
				    	<%-- <c:out value="${users.name}"/> --%>
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


</form>

</body>

</html>
<script type="text/javascript">

function exportData(){
	var df=document.userForm;	
	var areaCode = $("#combotreeOrganTree").combotree("getValue");
	df.action='<c:out value="${hostPrefix}" /><c:url value="/userAction.do" />?method=exportTemplate&areaCode='+areaCode;
	df.submit();
}

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
	 
		var areaCode = $("#combotreeOrganTree").combotree("getValue");
		var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/userAction.do?method=list&fuzzy=yes&areaCode="+areaCode;
    	document.userForm.action = url;
     	document.userForm.submit();
	
}
function newUser(){
		    var areaId = $("#combotreeOrganTree").combotree("getValue");
			var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/userAction.do?method=toEdit&areaCode="+areaId;
			document.userForm.action = url;
			document.userForm.submit(); 
	        return ;
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


