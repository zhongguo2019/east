<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<c:set var="colName">
	<fmt:message key="exportXML.tree.org.name"/>
</c:set>
<c:set var="orgTreeURL">
	<c:out value="${hostPrefix}" /><c:url value="/treeAction.do?method=getOrganTreeXML${orgparam}"/>
</c:set>
<c:set var="orgButton">
	<fmt:message key="place.select"/>
</c:set>
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
        <script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
        <script type="text/javascript" src="/ps/scripts/validators.js"></script>
        <script src="<c:url value='/ps/framework/common/jqueryTab/jquery-1.2.4b.js'/>"
			type="text/javascript"></script>
		<script src="<c:url value='/ps/framework/common/jqueryTab/jquery-1.4.1.min.js'/>"
			type="text/javascript"></script>
	    <script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/lang/zh-cn.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-setup.js'/>"></script>
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/ActiveXTree/ActiveXTree.js'/>"></script>
	
</head>
<% String editFlag = (String)request.getAttribute("editFlag");
    
   String rp = (String)request.getAttribute("rp");
   String userid = (String)request.getParameter("userid");
   String userStatus = (String)request.getAttribute("userStatus");
%>
<body leftmargin="0" bgcolor=#eeeeee  >
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
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
    <td width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td>
            <p style="margin-top: 3"><font class=b><b><fmt:message key="usermanage.sysadmin.menu"/><fmt:message key="navigation.arrowhead"/><fmt:message key="usermanage.user.adduser"/></b></font></p>                         
          </td>
		  <td></td> 
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td width="100%" background="<c:url value="${imgPath}/f05.gif"/>" valign="top">
    <p>
    </p>

<table cellSpacing=1 cellPadding=0 width="380" align=center border=0 class="TableBGColor">
<html:form action="userAction" method="post">
            <html:hidden property="pkid"/>
			<c:set var="pageButtons">
			    <tr align="center" class="BtnBgColor">
			        <td class="TdBGColor1">
			        </td>
			    	<td class="buttonBar" align="left">
 			            <input name="method.save" type="submit" value="<fmt:message key="button.save"/>" style="width:60;" onClick="return validate()">
 					    <c:choose>
 					    	<c:when test="${editFlag ne null }">
 					  	 		 <input name="method.searchBeforeAdd" type="submit" value="<fmt:message key="button.cancel"/>" style="width:60;" onClick="bCancel=true">
 					    	</c:when>
 					    	<c:otherwise>
 					  	 		 <input name="method.list" type="submit" value="<fmt:message key="button.cancel"/>" style="width:60;" onClick="bCancel=true">
 					    	</c:otherwise>
 					    </c:choose>
			        </td>
			    </tr>
			</c:set>
			<br>
			<br>	
				
			    <tr>
			        <td class="TdBGColor1" width=80 align="right">
			        	<fmt:message key="usermanage.sysadmin.label.logonname" />
			        </td>
			        <td class="TdBGColor2">
			       
			          	 <html:text maxlength="10" property="logonName" style="width:240;" />
			             <html:hidden property="logonName"/>
			        </td>
			    </tr>
			    
			    <tr>
			    <td></td>
			    <td><fmt:message key="usermanage.user.logoning" /></td>
			    </tr>
				
			    <tr>
			    
			        <td class="TdBGColor1" width=80 align="right">
			            <fmt:message key="usermanage.sysadmin.label.username" />
			        </td>
			        <td class="TdBGColor2">
			        	<html:text maxlength="25" property="name" style="width:240;" />
			             <html:hidden property="name" />
			        </td>
			    </tr>
			  			    
			    <tr>
			    <td></td>
			    <td><fmt:message key="usermanage.user.realname" /></td>
			    </tr>
			    
                
                <%if(editFlag!=null) {%>
			    <tr>
			        <td class="TdBGColor1" width=80 align="right">
			        	<fmt:message key="usermanage.sysadmin.label.password" />
			        </td>
			        <td class="TdBGColor2">
			            <html:password maxlength="10" property="password" style="width:240;" onchange="checkpsd(this);" />
			            
			        </td>
			    </tr>
			    
			    			    			    <tr>
			    <td></td>
			    <td>&nbsp;</td>
			    </tr>
			    
			    <tr>
			        <td class="TdBGColor1" width=80 align="right">
			        	<fmt:message key="usermanage.sysadmin.label.cfnewpassword" />
			        </td>
			        <td class="TdBGColor2">
			            <input type="password" maxlength="10"  name="repassword" style="width:240;" onchange="checkpsd(this);" />
			            
			        </td>
			    </tr>
			    
			    			    			    <tr>
			    <td></td>
			    <td>&nbsp;</td>
			    </tr>
			    
			    <%} else{ %>
			    <html:hidden property="password"/>
			    <input type="hidden" name="repassword" value=<%=rp%>>
			    
			    <%} %>
			    
			   <!--  add by LC 2013.8.26 -->
			   <tr>
			    	<td class="TdBGColor1" width=80 align="right"><fmt:message key="usermanage.ip"/></td>
			    	<td class="TdBGColor2">
			    		<html:text maxlength="25" property="ipAddr" style="width:240;" />
			    	</td>
			    </tr>
			    <tr>
			    <td></td>
			    <td>&nbsp;</td>
			    </tr>
			   
			   
			   <%--   <tr>
			    	<td class="TdBGColor1" width=80 align="right"><fmt:message key="usermanage.idCard"/></td>
			    	<td class="TdBGColor2">
			    		<html:text maxlength="25" property="idCard" style="width:240;" />
			    	</td>
			    </tr>
			    <tr>
			    <td></td>
			    <td>&nbsp;</td>
			    </tr>
			    
			    
			     <tr>
			    	<td class="TdBGColor1" width=80 align="right"><fmt:message key="usermanage.phone"/></td>
			    	<td class="TdBGColor2">
			    		<html:text maxlength="25" property="phone" style="width:240;" />
			   	    </td>
			    </tr>
			    <tr>
			    <td></td>
			    <td>&nbsp;</td>
			    </tr>
			    
			    
			     <tr>
			    	<td class="TdBGColor1" width=80 align="right"><fmt:message key="usermanage.tellerId"/></td>
			    	<td class="TdBGColor2">
			    		<html:text maxlength="25" property="tellerId" style="width:240;" />
			   	    </td>
			    </tr>
			    <tr>
			    <td></td>
			    <td>&nbsp;</td>
			    </tr> --%>
			    
			    <tr>
			    	<td class="TdBGColor1" width=80 align="right"><fmt:message key="usermanage.organId"/></td>
			    	<td class="TdBGColor2">
			    	<!-- when editFlag is not null,it's addUser flag,when editFlag is null,it's edit flag-->
				    <%-- 	<c:choose>
				      		<c:when test="${editFlag!=null}">
				    			<select name="organId" style="width:240;">
				    		</c:when>
				      		<c:otherwise>
				      			<select name="organId" style="width:240;" disabled="disabled" id="organId">
				      		</c:otherwise>
				      	</c:choose> --%>
				      <%if(editFlag!=null) {%>
				    	 	<input type="hidden" name="organId" value="<c:out value="${organId}"/>"/>
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
			    <td><%-- 
			    		The part lead to <select>default value failed
			    		<input type="hidden" name="organId" value="<c:out value="${userForm.organId}"/>"/> --%></td>
			    <td>&nbsp;</td>
			    </tr>
			    <tr>
			        <td class="TdBGColor1" width=80 align="right">
			        	<fmt:message key="usermanage.sysadmin.label.rule" />
			        </td>
			        <td class="TdBGColor2">
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
			    <td></td>
			    <td>&nbsp;</td>
			    </tr>
			    <tr>
			        <td class="TdBGColor1" width=80 align="right">
			        	<fmt:message key="usermanage.sysadmin.grouppurview" />
			        </td>
			        <td class="TdBGColor2">
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
			    
			    
			    <tr>
			    <td></td>
			    <td>&nbsp;</td>
			    </tr>	 			    
			<c:out value="${pageButtons}" escapeXml="false" />
			    <tr>
					<td class="FormBottom" colspan="2" height="17"></td>
				</tr>
				<!--click the button transfer parameter  -->
				<input type="hidden" name="areaCode" value="<c:out value="${userForm.areaCode}"/>"/>
				
				
			</html:form>
</table>
</td>
</tr>
</table>
<!-- <script LANGUAGE='JavaScript'> -->
<script type="text/javascript">

	 function validate()
			{
		 debugger;
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
	           if(document.userForm.password.value != '' && !document.userForm.password.value.match(/^[\x21-\x7e]{1,14}$/ig)) {
			      
			         alert("<fmt:message key="error.invalidpassword"/>");
			         return false;
			      }
			   if(document.userForm.repassword.value != '' && !document.userForm.repassword.value.match(/^[\x21-\x7e]{1,14}$/ig)) {
			      
			         alert("<fmt:message key="error.invalidpassword"/>");
			         return false;
			      }
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
			
	           if(document.userForm.password.value != '' && !document.userForm.password.value.match(/^[\x21-\x7e]{1,14}$/ig)) {
			      
			         alert("<fmt:message key="error.invalidpassword"/>");
			         return false;
			      }
			   if(document.userForm.repassword.value != '' && !document.userForm.repassword.value.match(/^[\x21-\x7e]{1,14}$/ig)) {
			      
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