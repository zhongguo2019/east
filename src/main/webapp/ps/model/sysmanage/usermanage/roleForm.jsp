<!-- /ps/model/sysmanage/usermanage/roleForm. -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/meta.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
<c:if test="${error=='-1'}" >
<script type="text/javascript">
 alert("<fmt:message key="error.coderepeat"/>");
</script>
</c:if>	
<html>
<head>
<title><fmt:message key="webapp.prefix"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
<link rel="stylesheet" href="<c:url value='${cssPath}/css.css" type="text/css'/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>
        <script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
        
		<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/common/jscalendar/calendar-win2k-1.css'/>" title="win2k-cold-1" /> 
		<script type="text/javascript" src="<c:url value='/common/jscalendar/calendar.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/common/jscalendar/lang/zh-cn.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/common/jscalendar/calendar-setup.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/ps/scripts/jquery-1.4.2.js'/>"></script>
		 
		
		<script type="text/javascript">
		<!--
        function isexist(va){
          var ee = false;          
          for(var i=0;i<document.roleForm.target.options.length;i++)
            if(document.roleForm.target.options[i].value==va)
             { ee=true; break;}
          return ee;
        }
        
        function isexist1(va){
          var ee = false;          
          for(var i=0;i<document.roleForm.resource.options.length;i++){	          
	            if(document.roleForm.resource.options[i].value==va)
	             { ee=true; break;}
             }             
          return ee;
        }
        
		function addRole(){
		   for(var i=document.roleForm.resource.options.length-1;i>=0;i--)
		      if(document.roleForm.resource.options[i].selected) {
		        if(!isexist(document.roleForm.resource.options[i].value)){ 
			        document.roleForm.target.options[document.roleForm.target.options.length]
			        = new Option(document.roleForm.resource.options[i].text,document.roleForm.resource.options[i].value);
			        document.roleForm.resource.options[i] = null;
		        }
		      }
		}
		
		function delRole(){
		   for(var i=document.roleForm.target.options.length-1;i>=0;i--){
		      if(document.roleForm.target.options[i].selected){
		      	  if(isexist1(document.roleForm.target.options[i].value)){		      	  
		      		  document.roleForm.target.options[i] = null;
		      	  }else{		      	  
			          document.roleForm.resource.options[document.roleForm.resource.options.length]
				      = new Option(document.roleForm.target.options[i].text,document.roleForm.target.options[i].value);
			          document.roleForm.target.options[i] = null;
			      }
			   }
			}
		}
		//-->
		</script>
		<script type="text/javascript">
		<!--
			function selectAll(){
				var isSub = true;
				var menuId = "";				
				for(var i=0;i<document.roleForm.target.options.length;i++){
					document.roleForm.target.options[i].selected;
					if(menuId == ""){
						menuId = document.roleForm.target.options[i].value;						
					}else{
						menuId += (","+document.roleForm.target.options[i].value);						
					}
				}
				var flag = $("#flag1").val();
				document.roleForm.menuId.value = menuId;	
				var rType = document.roleForm.rType.value;
				var roleType = rType.split(",");
				if(flag==1){
					for(var j=0;j<roleType.length;j++){	
						if(document.roleForm.roleType.value == roleType[j]){
								alert("<fmt:message key="role.roleType.isAlready"/>");
								isSub = false;
							}
					}
				}
				if(document.roleForm.roleType.value.length == 0){
					alert("<fmt:message key="role.roleType.isNot"/>");
					isSub = false;
					return ;
				}				
				if(document.roleForm.name.value.length == 0){
					alert("<fmt:message key="role.name.isNot"/>");
					isSub = false;
					return ;
				}
			/* 	if(document.roleForm.target.options.length == 0){
					alert("<fmt:message key="role.target.isNot"/>");
					isSub = false;
					return ;
				} */
				sub(isSub);
			}
			function sub(isSub){
				if(isSub){
					var url="userRoleAction.do?method=addUserRole";
					document.roleForm.action=url;
					document.roleForm.submit();
				}
			}
			function aSelectAll(){
				var isSub = true;
				var menuId = "";
				for(var i=0;i<document.roleForm.target.options.length;i++){
					document.roleForm.target.options[i].selected;
					if(menuId == ""){
						menuId = document.roleForm.target.options[i].value;						
					}else{
						menuId += (","+document.roleForm.target.options[i].value);						
					}
				}
				document.roleForm.menuId.value = menuId;
				if(document.roleForm.roleType.value.length == 0){
					alert("<fmt:message key="role.roleType.isNot"/>");
					isSub = false;
					return ;
				}
				if(document.roleForm.name.value.length == 0){
					alert("<fmt:message key="role.name.isNot"/>");
					isSub = false;
					return  ;
				}
			/* 	if(document.roleForm.target.options.length == 0){
					alert("<fmt:message key="role.target.isNot"/>");
					isSub = false;
					return  ;
				} */
				sub(isSub);
			}
		//-->
		</script>
<script type="text/javascript">
			$(function(){
				$("#select1").unbind("change");
				$("#select1").bind("change",function(){
					var id = $("#select1>option:selected").val();
					var flag = $("#flag1").val();
					if(id==null||""==id) {
						id = "0";
					}
					/* if(""!=id && null!=id&&id!="#") {
						if(flag=="2") {
						window.location.href = "userRoleAction.do?method=getMenuBySystem&systemId="+ id + "&flag="+flag +"&pkid="+$("#pkid2").val()+"&roleType="+$("#roleType2").val();
						}else{
						window.location.href = "userRoleAction.do?method=getMenuBySystem&systemId="+ id + "&flag="+flag;
						} 
					}else{
						if(flag=="2") {
							window.location.href = "userRoleAction.do?method=entryUserRole&flag="+flag +"&pkid="+$("#pkid2").val()+"&roleType="+$("#roleType2").val();
						}else{
							window.location.href = "userRoleAction.do?method=entryUserRole&flag="+flag;
						}
					} */
					
					if(flag=="2") {
						window.location.href = "userRoleAction.do?method=entryUserRole&systemId="+ id + "&flag="+flag +"&pkid="+$("#pkid2").val()+"&roleType="+$("#roleType2").val();
						}else{
						window.location.href = "userRoleAction.do?method=entryUserRole&systemId="+ id + "&flag="+flag;
						} 
				});
			});
			
			//window.parent.document.getElementById("ppppp").value= "<fmt:message key="usermanage.sysadmin.menu"/><fmt:message key="navigation.arrowhead"/><fmt:message key="usermanage.role.main"/>";
</script>
</head>
<body leftmargin="0" >
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

<div class="navbar">
	<a href="userRoleAction.do?method=queryUserRole" class="easyui-linkbutton"   ><fmt:message key='role.channel'/></a>
	<!--  <a href="userRoleAction.do?method=queryUserRole" ><fmt:message key="role.channel"/></a>-->
	<c:if test="${flag == '1'}">
	 <a href="javascript:selectAll();" class="easyui-linkbutton"   data-options="iconCls:'icon-save'"><fmt:message key='role.submit' /></a>&nbsp;
    <!--   <a href="javascript:selectAll();"><fmt:message key="role.submit"/></a>-->
     </c:if>
     <c:if test="${flag == '2'}">
      <a href="javascript:selectAll();" class="easyui-linkbutton"   data-options="iconCls:'icon-save'" ><fmt:message key='role.submit' /></a>&nbsp;
    <!--   <a href="javascript:aSelectAll();"><fmt:message key="role.submit"/></a>-->
     </c:if>
    <a href="javascript:isNull();" class="easyui-linkbutton"   ><fmt:message key='role.reset'/></a>
   <!--   <a href="javascript:isNull();" ><fmt:message key="role.reset"/></a>-->
    
    
   
</div>
    
 <%--    <fmt:message key="system.switch.title"/>
    <select id="select1">
    	<option selected="selected" value=""><fmt:message key="system.switch.hint"/></option>
    	<c:forEach items="${sysMenus}" var="m">
    			<option value="<c:out value='${m.pkid}'/>"  <c:if test="${selectedSys eq m.pkid}">selected</c:if>><c:out value="${m.name}"/></option>
    	</c:forEach>
    </select> --%>
    
<form name="roleForm" id="roleForm" action="userRoleAction.do?method=addUserRole" method="post" style="margin: 0;">
<table  align="center" class="TableBGColor" width="100%" border="0" cellSpacing=1 cellPadding=5 >    

	<input type="hidden" value="<c:out value='${flag}'/>" id="flag1"/>
	<input type="hidden" value="<c:out value='${selectedSys}'/>" name="systemId"/>
	<c:if test="${flag == '1'}">
		<tr style="height:10">
			<input type="hidden" name="pkid" value="<c:out value="${roleForm.pkid}"/>"/>
	    	<input type="hidden" name="menuId" value=""/>
			<td class="TdBGColor1" width="12%" align="right"><fmt:message key="role.type"/></td>
			<td class="TdBGColor2" width="40%" ><input type="text" name="roleType" maxlength="1" value="<c:out value="${roleForm.roleType}"/>" style="width:200"/></td>
			<td class="TdBGColor1" width="12%" align="right" ><fmt:message key="role.name"/></td>
			<td class="TdBGColor2" width="40%" ><input type="text" name="name" maxlength="30" value="<c:out value="${roleForm.name}"/>" style="width:200"/></td>
		</tr>			
		<tr height="10">
			<td class="TdBGColor1" width="12%" align="right"><fmt:message key="role.description"/></td>
			<td class="TdBGColor2" width="40%" ><input type="text" name="description" maxlength="30" value="<c:out value="${roleForm.description}"/>" style="width:200"/></td>
			
			<td class="TdBGColor1" width="12%" align="right"><fmt:message key="role.level"/></td>
			<td class="TdBGColor2" width="40%" ><select name="roleLevel" maxlength="30" style="width:200">
													<option value="1" selected="selected"><fmt:message key="levelone" /></option>
													<option value="2"><fmt:message key="leveltwo" /></option>
													<option value="3"><fmt:message key="levelthree" /></option>
												</select>
<%-- 			<input type="text" name="rolelevel" maxlength="30" value="<c:out value="abc"/>" style="width:200"/></td>
 --%>		</tr>
	</c:if>
	<c:if test="${flag == '2'}">
		<tr style="height:10">
			<input type="hidden" name="pkid" value="<c:out value="${roleForm.pkid}"/>" id="pkid2"/>
	    	<input type="hidden" name="menuId" value=""/>
			<td class="TdBGColor1" width="12%" align="right"><fmt:message key="role.type"/></td>
			<td class="TdBGColor2" width="40%"><input type="text" name="roleType" maxlength="1" value="<c:out value="${roleForm.roleType}"/>" readOnly="true"  id="roleType2" style="width:155"/></td>
			<td class="TdBGColor1" width="12%" align="right" ><fmt:message key="role.name"/></td>
			<td class="TdBGColor2" width="40%"><input type="text" name="name" maxlength="30" value="<c:out value="${roleForm.name}"/>" style="width:155" /></td>
		</tr>			
		<tr height="10">
			<td class="TdBGColor1" width="12%" align="right"><fmt:message key="role.description"/></td>
			<td class="TdBGColor2" width="40%" ><input type="text" name="description" maxlength="30" value="<c:out value="${roleForm.description}" />" style="width:155"/></td>
		
			<td class="TdBGColor1" width="12%" align="right"><fmt:message key="role.level"/></td>
			<td class="TdBGColor2" width="40%" >
					
					<script type="text/javascript">	
							$(document).ready(function(){
								$("#roleLevel option[value='<c:out value='${roleForm.roleLevel}'/>']").attr("selected",true);
								
							});
					</script>
												<select name="roleLevel" maxlength="30" style="width:155" id="roleLevel">
														<option value="1"><fmt:message key="levelone" /></option>
														<option value="2"><fmt:message key="leveltwo" /></option>
														<option value="3"><fmt:message key="levelthree" /></option>
															<%-- <option value="1" <c:if test="${roleForm.roleLevel eq '1' }">selected='selected'</c:if>> <fmt:message key="levelone" /></option>
															<option value="2" <c:if test="${roleForm.roleLevel eq '2'}">selected='selected'</c:if>>  <fmt:message key="leveltwo" /></option>
															<option value="3" <c:if test="${roleForm.roleLevel eq '3'}">selected='selected'</c:if>> <fmt:message key="levelthree" /></option> --%>
												</select>
		</tr>
	</c:if>
</table>
<table  align="center" class="TableBGColor" width="100%" border="0" cellSpacing=1 cellPadding=5 height="200">
		<tr>
			<td class="TdBGColor1" width="40%" align="right" >
				<select name="resource" size="6" multiple style="width:300px;height:350px" id="lSelect">
    					<c:forEach items="${menu}" var="menu" varStatus="status">
								<option value="<c:out value="${menu.id}"/>">
								<c:out value="${menu.name}" /></option>								
						</c:forEach>
    				</select>
			</td>
			<td class="TdBGColor2" width="8%" align="center">
			
			 <a href="#" class="easyui-linkbutton"  data-options="iconCls:'icon-add'" style="text-decoration: none;" onclick="javascript:addRole();" ><fmt:message key='role.addRole'/></a><br/>
			 <br><br><br><br>
           <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" style="text-decoration: none;" onclick="javascript:delRole();"><fmt:message key='role.delRole'/></a>
				<!--  <input type="button" name="addBtn" value="<fmt:message key="role.addRole"/>" onClick="javascript:addRole();"/>
    			<br><br><br><br>
    			<input type="button" name="delBtn" value="<fmt:message key="role.delRole"/>" onClick="javascript:delRole();"/>-->
			</td>
			<td class="TdBGColor1" width="40%" align="left" >
				<select name="target" size="6" multiple style="width:300px;height:350px" id="rSelect">
    					<c:forEach items="${menuList}" var="menuList" varStatus="status">
    						<option value="<c:out value="${menuList.id}"/>">
    							<c:out value="${menuList.name}"/>
    						</option>
    					</c:forEach>
    			</select>
			</td>			
		</tr>		
		<tr height="10">
			<td class="TdBGColor1" width="100%" align="center" colspan="3">
				<input type="hidden" name="rType" value="<c:out value="${roleType}"/>"/>
				
			</td>			
		</tr>		
</table>
<script type="text/javascript">
function isNull(){
	$("#lSelect").append($("#rSelect>option"));
	document.roleForm.target.options.length = 0;
}
</script>
</form>

</body>
</html>
