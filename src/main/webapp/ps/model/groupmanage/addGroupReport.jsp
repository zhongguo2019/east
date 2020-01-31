<!-- /ps/model/groupmanage/addGroupReport. -->
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
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
<link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>
        <script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
        
 <script type="text/javascript">
 	function quxiao(){
 		document.groupReportForm.action="groupReportAction.do?method=list";
 		document.groupReportForm.submit();
 	}
 </script>       
</head>
<body >
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
 <html:form  action="groupReportAction" method="post"> 
 <html:hidden property="userid"  />
 <html:hidden property="dicidCount" value="<%=dicidCount%>"  />

<P><html:errors/></P>
<div class="navbar2">
 
           	<table  width="100%">
           		<tr>
           			<td>
           				 <a href="#" class="easyui-linkbutton "   data-options="iconCls:'icon-save'" onclick="return validate();"><fmt:message key='button.save' /></a>&nbsp;
           	 			<a href="#" class="easyui-linkbutton "    onclick="quxiao();" ><fmt:message key='button.cancel'/></a>
           			</td>
           		</tr>
           	</table>
           	          	
           	</div>
             <div class="navbar1">
           
           <table>
           		<tr>
           			
           				<td>
           					<fmt:message key="groupmanage.edit.editname"/>
           				</td>
           				<td>
           					<input type="text" name="dicname" value="<fmt:message key="groupmanage.main.fun.default"/>" style="width:240px"/>
           				</td>
           			<td>
      					<fmt:message key="groupmanage.fun.reporttype"/>
      				</td>
      				<td>
      					<html:select property="type"  style="width:240"
                value="<%=typeid%>">
                <html:options collection="types" property="pkid" 
                  labelProperty="name" />
		        </html:select>
      				</td>
      				<td>
      				<a href="#" class="easyui-linkbutton buttonclass3" onclick="checktype_all();return false"><fmt:message key="button.selecttypeall"/></a>
      					<!--  <a href=# onClick="checktype_all();return false">
           				<fmt:message key="button.selecttypeall"/></a>-->
      				</td>
      				<td>
      				<a href="#" class="easyui-linkbutton buttonclass3"  onclick="reltype_all();return false"><fmt:message key="button.releasetypeall"/></a>
      				<!--<a href=# onClick="reltype_all();return false">
           			<fmt:message key="button.releasetypeall"/></a>-->
      				</td>
      				<td>
      				<a href="#" class="easyui-linkbutton buttonclass2"  onclick="check_all();return false"><fmt:message key="button.selectall"/></a>
      				<!--<a href=# onClick="check_all();return false">
           				<fmt:message key="button.selectall"/></a>-->
      				</td>
      				<td>
      				<a href="#" class="easyui-linkbutton buttonclass2"  onclick="rel_all();return false"><fmt:message key="button.releaseall"/></a>
      					<!--  <a href=# onClick="rel_all();return false">
           					<fmt:message key="button.releaseall"/></a>-->
      				</td>
           		</tr>
           </table>
          </div> 
 
   <table  align="center"  width="100%" border="0" cellSpacing=1 cellPadding=5>        
    
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
									//highlightTableRows("userReports");
								//-->
							</script>
				    </div>
				</td>
			</tr>
	
<tr height=17><td colspan="4"></td></tr>
	 <tr>
		<td colspan="4" class="TdBGColor1">&nbsp;		
		 <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="return validate();"><fmt:message key='button.save' /></a>
			  <a href="#" class="easyui-linkbutton"   onclick="quxiao();" ><fmt:message key='button.cancel'/></a> 		         	
	<!--  <input type="submit" name="method.saveGroupReport" value="<fmt:message key="button.save"/>" style="width:80;" onClick="bCancel=false;return validate()">			        
	<input type="submit" name="method.list" value="<fmt:message key="button.back"/>" style="width:80;" onClick="bCancel=false">		-->	        
	</td>
	</tr>
</html:form>
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
	 $.post('groupReportAction.do?method=checkgroupName',{'dicname':document.groupReportForm.dicname.value},function(data){
  		 if(data>0){
  			alert('<fmt:message key="groupmanage.error.notdouble"/>');
  		 }else{
  			document.groupReportForm.action="groupReportAction.do?method=saveGroupReport";
  			document.groupReportForm.submit(); 
  		 }
 	});
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

