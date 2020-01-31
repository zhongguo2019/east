<!-- /ps/model/sysmanage/usermanage/userForm. -->
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ page import="com.krm.ps.sysmanage.usermanage.vo.*"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
<%-- <c:set var="colName">
	<fmt:message key="exportXML.tree.org.name"/>
</c:set>
<c:set var="orgTreeURL">
	<c:out value="${hostPrefix}" /><c:url value="/treeAction.do?method=getOrganTreeXML${orgparam}"/>
</c:set>
<c:set var="orgButton">
	<fmt:message key="place.select"/>
</c:set> --%>
<c:if test="${error=='-1'}" >
<script type="text/javascript">
 alert("<fmt:message key="error.userrepeat"/>");
</script>
</c:if>
<html>

<head>
<title><fmt:message key="webapp.prefix"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
<link rel="stylesheet" href="<c:url value='${cssPath}/css.css" type="text/css'/>"/>


		

	
	<script type="text/javascript">
			
			function quxiao(){
				document.userForm.action="userAction.do?method=list";
 				document.userForm.submit();
			}
			function quxiao1(){
				document.userForm.action="userAction.do?method=list";
 				document.userForm.submit();
			}
	
	</script>
</head>
<% String editFlag = (String)request.getAttribute("editFlag");
    
   String rp = (String)request.getAttribute("rp");
   String userid = (String)request.getParameter("userid");
   String userStatus = (String)request.getAttribute("userStatus");
   User user = (User)request.getSession().getAttribute("user");
   String orgId = user.getOrganId();
%>
<c:set var="flagOrgan" value="1"/>
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
       
     //  window.parent.document.getElementById("ppppp").value= "<fmt:message key="usermanage.sysadmin.menu"/><fmt:message key="navigation.arrowhead"/><fmt:message key="usermanage.user.adduser"/>";
   </script> 

<table cellSpacing=1 cellPadding=0 width="430" align=center border=0 class="TableBGColor" style="margin: 0 auto;">
<html:form action="userAction" method="post">
            <html:hidden property="pkid"/>
			<c:set var="pageButtons">
			    <tr align="center" class="BtnBgColor">
			     
			    	<td class="buttonBar" align="center" colspan="3">
			    	 <a href="#" class="easyui-linkbutton"  data-options="iconCls:'icon-save'" onclick="return validate();"><fmt:message key='button.save' /></a>
			     	<!--  <a href="#" class="easyui-linkbutton"  iconCls="icon-cancel" onclick="quxiao();" ><fmt:message key='button.cancel'/></a> -->		         
 			            <!--  <input name="method.save" type="button" value="<fmt:message key="button.save"/>" style="width:60;" onClick="return validate()">-->
 					    <c:choose>
 					    	<c:when test="${editFlag ne null }">

 					    		<a href="#" class="easyui-linkbutton"  iconCls="icon-cancel" onclick="quxiao();" ><fmt:message key='button.cancel'/></a>
 					  	 		<!--   <input name="method.searchBeforeAdd" type="submit" value="<fmt:message key="button.cancel"/>" style="width:60;" onClick="bCancel=true">-->
 					    	</c:when>
 					    	<c:otherwise>
 					
 					    	<a href="#" class="easyui-linkbutton"  iconCls="icon-cancel" onclick="quxiao();" ><fmt:message key='button.cancel'/></a>
 					  	 		<!--   <input name="method.list" type="submit" value="<fmt:message key="button.cancel"/>" style="width:60;" onClick="bCancel=true">-->
 					    	</c:otherwise>
 					    </c:choose>
			        </td>
			    </tr>
			</c:set>
			<br>
			<br>	
				
			    <tr>
			        <td class="TdBGColor1" width=80 align="right" nowrap="nowrap">
			        	<fmt:message key="usermanage.sysadmin.label.logonname" />
			        </td>
			        <td class="TdBGColor2">
			       
			          	 <html:text maxlength="10" property="logonName" style="width:240;" />
			             <html:hidden property="logonName"/>
			        </td>
			        <td class="TdBGColor2" style="width:200;">
			        	<fmt:message key="usermanage.user.logoning" />
			        </td>
			    </tr>
			    
			  
				
			    <tr>
			    
			        <td class="TdBGColor1" width=80 align="right">
			            <fmt:message key="usermanage.sysadmin.label.username" />
			        </td>
			        <td class="TdBGColor2">
			        	<html:text maxlength="25" property="name" style="width:240;" />
			             <%-- <html:hidden property="name" /> --%>
			        </td>
			        <td class="TdBGColor2" style="width:200;">
			        	<fmt:message key="usermanage.user.realname" />
			        </td>
			    </tr>
			  			    
			   
			    
                
                <%if(editFlag!=null) {%>
			    <tr>
			        <td class="TdBGColor1" width=80 align="right">
			        	<fmt:message key="usermanage.sysadmin.label.password" />
			        </td>
			        <td class="TdBGColor2" colspan="2">
			            <html:password maxlength="10" property="password" style="width:240;" onchange="checkpsd(this);" />
			            
			        </td>
			    </tr>
			    

			    
			    <tr>
			        <td class="TdBGColor1" width=80 align="right">
			        	<fmt:message key="usermanage.sysadmin.label.cfnewpassword" />
			        </td>
			        <td class="TdBGColor2" colspan="2">
			            <input type="password" maxlength="10"  name="repassword" style="width:240;" onchange="checkpsd(this);" />
			            
			        </td>
			    </tr>
			    

			    
			    <%} else{ %>
			    <html:hidden property="password"/>
			    <input type="hidden" name="repassword" value=<%=rp%>>
			    
			    <%} %>
			    
			   <!--  add by LC 2013.8.26 -->
			   <tr>
			    	<td class="TdBGColor1" width=80 align="right"><fmt:message key="usermanage.ip"/></td>
			    	<td class="TdBGColor2" colspan="2">
			    		<html:text maxlength="25" property="ipAddr" style="width:240;" />
			    	</td>
			    </tr>
                
               	<td class="TdBGColor1" width=80 align="right"><fmt:message key="chart.visit.organLevel"/></td>
		    	<td class="TdBGColor2">
		    		  <select name="tellerId" style="width:240px">
       						<option   value="0"  <c:if test="${isAdmin=='0'}">selected="selected"</c:if> ><fmt:message key="visit.organLevel.zhihang"/></option>
       						<option   value="3"  <c:if test="${isAdmin=='3'}">selected="selected"</c:if> ><fmt:message key="visit.organLevel.zhonghang"/></option>
		               <select>
		   	    </td>
		   	    
			    <tr>
			    	<td class="TdBGColor1" width=80 align="right"><fmt:message key="usermanage.organId"/></td>
			    	<td class="TdBGColor2" colspan="2">
				      <%if(editFlag!=null) {%>
				    	 	<input type="hidden" name="organId" value="<c:out value="${organId}"/>"/>
   							<input type="hidden" name="organName"/>
				    		<%-- <slsint:ActiveXTree left="220" top="325" width="260" height="${activeXTreeHeight }" 
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
 						</slsint:ActiveXTree> --%>
 						
 						<div id="flagOrgan1">
					<input id="combotreeOrganTree" class="easyui-combotree"  value="<%=orgId %>"  style="width:240"/>
				</div>
				<div id="flagOrgan2">
					<input id="combotreeOrganTree" class="easyui-combotree" multiple value="<%=orgId %>"  style="width:240"/>
				</div>
				    	  <%} else{ %>
			   					<select name="organId" style="width:240;" disabled="disabled" id="organId">
					    		<c:forEach var="organ" items="${organList}">
					    			<option value="<c:out value="${organ.code}"/>">
					    				<c:out value="${organ.short_name}"/>
					    			</option>
					    		</c:forEach>
				    		</select>
				    		<input type="hidden" name="organId" value="<c:out value="${userForm.organId}"/>"/>		
			   			 <%} %>
			    	</td>
			    </tr>

			    <tr>
			        <td class="TdBGColor1" width=80 align="right">
			        	<fmt:message key="usermanage.sysadmin.label.rule" />
			        </td>
			        <td class="TdBGColor2" colspan="2">
			      	<html:select property="roleType" style="width:240;">
			            	<c:forEach var="role" items="${roles}">
        						<html-el:option value="${role.roleType}" >
       								<c:out value="${role.name}"/>
								</html-el:option>
        					</c:forEach>
			            </html:select>
			        </td>
			    </tr>		
			    
			    
			    <!-- add by zhaoyi _20070330 -->

			    <tr>
			        <td class="TdBGColor1" width=80 align="right">
			        	<fmt:message key="usermanage.sysadmin.grouppurview" />
			        </td>
			        <td class="TdBGColor2" colspan="2">
			             <select name="groupType" style="width:240px">
			            	<c:forEach var="groupList" items="${groupList}">
			            	<c:if test="${groupList.dicid==userStatus}">						
        						<option  
        						value="<c:out value="${groupList.dicid}"/>" selected >
       								<c:out value="${groupList.dicname}"/>
								</option>
							</c:if>
							<c:if test="${groupList.dicid!=userStatus}">	
        						<option         						
        						value="<c:out value="${groupList.dicid}"/>" >
       								<c:out value="${groupList.dicname}"/>
								</option>
							</c:if>
        					</c:forEach>
			            <select>
			        </td>
			    </tr>
			    
			    
		 			    
			<c:out value="${pageButtons}" escapeXml="false" />

				<!--click the button transfer parameter  -->
				<input type="hidden" name="areaCode" value="<c:out value="${userForm.areaCode}"/>"/>
				
				
			</html:form>
</table>

<!-- <script LANGUAGE='JavaScript'> -->
<script type="text/javascript">
	 function validate()
			{
				/* if(document.userForm.logonName.value == "" || document.userForm.logonName.value.length<2 || document.userForm.logonName.value.length>18)
				{
					alert("<fmt:message key="error.loginname"/>");
				    return false;
				} */
				if(document.userForm.name.value == "")
				{
					alert("<fmt:message key="error.username"/>");
				    return false;
				}
				
				if('<c:out value="${editFlag}"/>' != ""){
					if(document.userForm.password.value == "" || document.userForm.password.value.length<6 || document.userForm.password.value.length>10)
					{ 
						alert("<fmt:message key="error.password"/>");
					    return false;
					}
					if(document.userForm.password.value!=document.userForm.repassword.value)
					{
						alert("<fmt:message key="error.repassword"/>");
					    return false;
					}	
		           if(document.userForm.password.value != '' && !document.userForm.password.value.match("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}$")) {
				      
				         alert("<fmt:message key="error.invalidpassword"/>");
				         return false;
				      }
				   if(document.userForm.repassword.value != '' && !document.userForm.repassword.value.match("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}$")) {
				      
				         alert("<fmt:message key="error.invalidpassword"/>");
				         return false;
				   }
				}
			   if(document.userForm.ipAddr.value == "")
			    {
					alert("<fmt:message key="error.ipAddr"/>");
				    return false;
				} 
				 if(!document.userForm.ipAddr.value.match(/^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$/))
				{
					alert("<fmt:message key="error1.ipAddr"/>");
				    return false;
				}   
			   
			   
			   //var organCode = $("#combotreeOrganTree").combotree("getValue");
			   //document.userForm.action="userAction.do?method=save&OrganId="+organCode;
			   document.userForm.action="userAction.do?method=save";
			   document.userForm.submit();
			   
			   /*  if(document.userForm.ipAddr.value == "")
				{
					alert("<fmt:message key="error.ipAddr"/>");
				    return false;
				} 
				 // check ip format right or not
				 if(!document.userForm.ipAddr.value.match(/^((2[0-4]\d|25[0-5]|[01]?\d\d?)\.){3}(2[0-4]\d|25[0-5]|[01]?\d\d?)$/))
				{
					alert("<fmt:message key="error1.ipAddr"/>");
				    return false;
				}   
		 		 if(document.userForm.idCard.value == "")
				{
					alert("<fmt:message key="error.idCard"/>");
				    return false;
				}
				if(!document.userForm.idCard.value.match(/^(\d{15}$|^\d{18}$|^\d{17}(\d|X|x))$/))
				{
					alert("<fmt:message key="error1.idCard"/>");
				    return false;
				}
				if(document.userForm.phone.value == "")
				{
					alert("<fmt:message key="error.phone"/>");
				    return false;
				}
				if(document.userForm.tellerId.value == "")
				{
					alert("<fmt:message key="error.tellerId"/>");
				    return false;
				} */
				
			} 
			
	 function checkpsd()
	       {
			
	           if(document.userForm.password.value != '' && !document.userForm.password.value.match("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}$")) {
			      
			         alert("<fmt:message key="error.invalidpassword"/>");
			         return false;
			      }
			   if(document.userForm.repassword.value != '' && !document.userForm.repassword.value.match("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}$")) {
			      
			         alert("<fmt:message key="error.invalidpassword"/>");
			         return false;
			      }
	       }		
	/*  $(document).ready(function(){
		var organId = "<c:out value="${userForm.organId}"/>";
		if(organId != ""){
		$("#organId option[value='<c:out value='${userForm.organId}'/>']").attr("selected",true);
		} 
		
	});  */
		 	
    var organId = "<c:out value="${userForm.organId}"/>";
	if(organId != ""){
		document.getElementById("organId").value = organId;
	} 
	

</script>
</body>
</html>