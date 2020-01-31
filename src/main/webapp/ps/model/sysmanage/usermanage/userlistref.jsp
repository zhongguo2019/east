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
					<input id="combotreeOrganTree" class="easyui-combotree"  value="<c:out value='${areaCode}'/>"  style="width:300px" />
				</div>
				</td>
				<td>
				<fmt:message key="groupmanage.del.displayinfo.username"/><fmt:message key="symbol.colon.fullwidth"/>
				</td>
				<td>
					<input type="text" name="username" id="username" value="<c:out value='${username1}'/>" width="150">
				</td>
				<td>
					<fmt:message key="logpage.loginname"/>
				</td>
				<td>
					<input type="text" name="loginname" id="loginname" value="<c:out value='${loginname1}'/>" width="150">
				</td>
				<td>
				<a href="#" class="easyui-linkbutton buttonclass2" data-options="iconCls:'icon-search'"  name="ss" onclick="javascript:changeTree1();" ><fmt:message key='fivegraderep.base.button.query'/></a>
				</td>
			
		</tr>
	</table>
</div>
		<div id="screen" align="left">
			<display:table name="userList" cellspacing="0" cellpadding="0"  
				    requestURI="" defaultsort="1" id="users" width="100%" 
				    pagesize="18" styleClass="list reportsList">
				    <%-- Table columns --%>
				    <display:column sort="true" titleKey="usermanage.sysadmin.list.username" headerClass="sortable" 
				    	width="15%">
				    	<c:out value="${users.name}"/>
				    </display:column>
				        
				    <display:column property="logonName" sort="true" titleKey="usermanage.sysadmin.list.logonname" 
				    	headerClass="sortable" width="12%"/>
				    	
				    <display:column property="roleName" sort="true" titleKey="usermanage.sysadmin.list.rolename"
				    	headerClass="sortable" width="12%"/>
	
				    
				    <display:column sort="true" headerClass="sortable" 
				    	width="6%">
				        <a href="userAction.do?method=refpassword&userid=<c:out value="${users.pkid}"/>">
				        	<fmt:message key="usermanage.sysadmin.refpassword"/>
				        </a>
				    </display:column>
				</display:table>
				
	    </div>


</form>

</body>

</html>
<script type="text/javascript">
function changeTree1(){
	var areaCode = $("#combotreeOrganTree").combotree("getValue");
	//var areaCode =document.getElementById("orgTree").value;
	var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/userAction.do?method=refreshpassword&areaCode="+areaCode;
	document.userForm.action = url;
	document.userForm.submit();
}

function qinchuText()
{
	document.getElementById("usernameid").value="";
}

function queryByname()
{
	var username = document.getElementById("usernameid").value;
	var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/userAction.do?method=queryUserByName";
	document.userForm.action = url;
	document.userForm.submit();
}
</script>


