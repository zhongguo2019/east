<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ page
	import="com.krm.ps.sysmanage.reportdefine.vo.ReportType,org.apache.struts.util.LabelValueBean,java.util.*"%>
<html>
<head>
<%
String typeid = (String) request.getAttribute("typeid");
//Long userid =(Long)request.getAttribute("userid");
//String userids = String.valueOf(userid);
String dicidCount = (String)request.getAttribute("dicidCount");
%>
<title><fmt:message key="webapp.prefix"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
<link rel="stylesheet" href="<c:url value='${cssPath}/css.css" type="text/css'/>"/>
        <script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
</head>
<body leftmargin="0" bgcolor=#eeeeee  onload="backspaceForbiddon();">
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
            <p style="margin-top: 3"><font class=b><b><fmt:message key="usermanage.sysadmin.menu"/><fmt:message key="navigation.arrowhead"/><fmt:message key="groupmanage.edit.title"/></b></font></p>                         
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

	<br>
	<table>
	<tr><tr>
	</tr>
 <html:form  action="groupReportAction" method="post"> 
 <html:hidden property="userid"  />
 <html:hidden property="dicidCount" value="<%=dicidCount%>"  />
<table  align="center" class="TdBGColor2" width="100%" border="0" cellSpacing=1 cellPadding=5>
<P style="FONT-SIZE: 12px; COLOR: #0000ff"><html:errors/></P>

           	<tr><td class="TdBGColor1">&nbsp;<input type="submit" name="method.saveGroupReport" value="<fmt:message key="button.save"/>" style="width:80;" onClick="bCancel=false;return validate()">&nbsp;			        
			<input type="submit" name="method.list" value="<fmt:message key="button.back"/>" style="width:80;" onClick="bCancel=false"></td><td class="TdBGColor1"></td></tr>

         <tr>
           <td class="TdBGColor1" width="5%" align="left" colspan="2">
           &nbsp;&nbsp;<fmt:message key="groupmanage.edit.editname"/>&nbsp;&nbsp;
           <input type="text" name="dicname" style="width:240px" value="<fmt:message key="groupmanage.main.fun.default"/>"/>
           </td>
         </tr>
         <tr><td height="3px" colspan="2" class="TdBGColor1" ><hr/></td></tr>
         <tr align="center">
		    <td  class="TdBGColor1" width="5%" align="left" colspan="2">
		    &nbsp;&nbsp;<fmt:message key="groupmanage.fun.reporttype"/>&nbsp;&nbsp;
		    <html:select property="type"  style="width:240" value="<%=typeid%>">
                <html:options collection="types" property="pkid" 
                  labelProperty="name" />
		    </html:select>
		   &nbsp;<a href=# onClick="checktype_all();return false">
           <fmt:message key="button.selecttypeall"/></a>&nbsp;              
	       <a href=# onClick="reltype_all();return false">
           <fmt:message key="button.releasetypeall"/></a>&nbsp; | &nbsp;         
	       <a href=# onClick="check_all();return false">
           <fmt:message key="button.selectall"/></a>&nbsp;              
	       <a href=# onClick="rel_all();return false">
           <fmt:message key="button.releaseall"/></a></td>
	    </tr>
			<tr>
				<td align="center" colspan="2">			
				    <div align="center" style=" width:98%">
						<display:table name="userReports" cellspacing="0" cellpadding="0"  
							    requestURI="" id="userReports" width="100%" 
							    styleClass="list userReports" >
							    <%-- Table columns --%>				     			
							    <display:column width="3%"> 
                                	<input type=checkbox name="yy" value="<c:out value="${userReports.typeId}"/>@<c:out value="${userReports.reportId}"/>@<c:out value="${userid}"/>" 
                                	<c:if test="${userReports.status==1}">checked
								    </c:if> />
                                </display:column> 
						   		 <display:column sort="true" 
							    	headerClass="sortable" width="10%" 
							    	titleKey="reportdefine.reportType.title1">
							    	<c:out value="${userReports.typeName}"/>
							    </display:column>
							    <display:column sort="true" 
							    	headerClass="sortable" width="5%" 
							    	titleKey="reportdefine.reportType.reportcode">
							    	<c:out value="${userReports.reportId}"/>
							    </display:column>
							    <display:column sort="true" 
							    	headerClass="sortable" width="25%" 
							    	titleKey="reportview.list.report">
							    	<c:out value="${userReports.reportName}"/>
							    </display:column>
							</display:table>
							<script type="text/javascript">
								<!--
									highlightTableRows("userReports");
								//-->
							</script>
				    </div>
				</td>
			</tr>
	
<tr height=17><td colspan="4"></td></tr>
	 <tr>
		<td colspan="4" class="TdBGColor1">&nbsp;		
	<input type="submit" name="method.saveGroupReport" value="<fmt:message key="button.save"/>" style="width:80;" onClick="bCancel=false;return validate()">			        
	<input type="submit" name="method.list" value="<fmt:message key="button.back"/>" style="width:80;" onClick="bCancel=false">			        
	</td>
	</tr>
</html:form>
</table>
</td>
</tr>
</table>
<script>
  function validate(){
  	if(document.groupReportForm.dicname.value==null||document.groupReportForm.dicname.value==""){
  	alert('<fmt:message key="groupmanage.error.nonull"/>');
  	  	return false;
  	}
  	if(<%=dicidCount%>>88){
  	alert('<fmt:message key="groupmanage.error.more"/>');
  		return false;
  	}
	return true;
  	
  }
  function check_all(){
   arr = document.getElementsByName('yy');
   for(i=0;i<arr.length;i++){
      arr[i].checked = true;
   }
  }
  function rel_all(){
   arr = document.getElementsByName('yy');
   for(i=0;i<arr.length;i++){
      arr[i].checked = false;
   }
  }  
  function checktype_all(){
   arr = document.getElementsByName('yy');
   for(i=0;i<arr.length;i++){
      var type = arr[i].value.split("@");
      if(document.all.type.value==type[0]){
      arr[i].checked = true;
      }
      }
   }
  function reltype_all(){
   arr = document.getElementsByName('yy');
   for(i=0;i<arr.length;i++){
      var type = arr[i].value.split("@");
      if(document.all.type.value==type[0]){
      arr[i].checked = false;
      }
   }
}
</script>
</body>
</html>

