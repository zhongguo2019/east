<!-- /ps/model/sysmanage/usermanage/userReport. -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
<%@ page pageEncoding="UTF-8" %>
<%@ page
	import="com.krm.ps.sysmanage.reportdefine.vo.ReportType,org.apache.struts.util.LabelValueBean,java.util.*"%>
<html>
<head>
<%
String typeid = (String) request.getAttribute("typeid");
Long userid =(Long)request.getAttribute("userid");
%>
<title><fmt:message key="webapp.prefix"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
<link rel="stylesheet" href="<c:url value='${cssPath}/css.css" type="text/css'/>"/>
 <link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>     
	 <script language="javascript" type="text/javascript" src="<c:url value='ps/uitl/My97DatePicker/WdatePicker.js'/>"></script>
  
<script type="text/javascript">
	function aaa(){
		document.userReportForm.action="userReportAction.do?method=saveUserReport";
		document.userReportForm.submit();
		
	}
</script>        
        
</head>
<script type="text/javascript">  
       //å±è½é¡µé¢ä¸­ä¸å¯ç¼è¾çææ¬æ¡ä¸­çbackspaceæé®äºä»¶  
       function keydown(e) {  
           var isie = (document.all) ? true : false;  
           var key;  
           var ev;  
           if (isie){ //IEåè°·æ­æµè§å¨  
               key = window.event.keyCode;  
               ev = window.event;  
           } else {//ç«çæµè§å¨  
               key = e.which;  
               ev = e;  
           }  
  
           if (key == 8) {//IEåè°·æ­æµè§å¨  
               if (isie) {  
                   if (document.activeElement.readOnly == undefined || document.activeElement.readOnly == true) {  
                       return event.returnValue = false;  
                   }   
               } else {//ç«çæµè§å¨  
                   if (document.activeElement.readOnly == undefined || document.activeElement.readOnly == true) {  
                       ev.which = 0;  
                       ev.preventDefault();  
                   }  
               }  
           }  
       }  
  
       document.onkeydown = keydown;  
       
     //  window.parent.document.getElementById("ppppp").value= "<fmt:message key="usermanage.sysadmin.menu"/><fmt:message key="navigation.arrowhead"/><fmt:message key="usermanage.sysadmin.userreport"/>";

   </script> 
<body leftmargin="0" >

 <html:form action="userReportAction" method="post"> 
 <html:hidden property="userid"/>
<div class="navbar2">
 
           	<table  width="100%">
           		<tr>
           			<td>
           				 <a href="#" class="easyui-linkbutton buttonclass2"  onclick="check_all();return false"><fmt:message key="button.selectall"/></a>&nbsp;  
         				 <a href="#" class="easyui-linkbutton buttonclass2"  onclick="rel_all();return false"><fmt:message key="button.releaseall"/></a>
           			</td>
           		</tr>
           	</table>
           	          	
           	</div>
             <div class="navbar3">
           
           <table>
           		<tr>
           			
           				<td>
           					<fmt:message key="datagather.label.reporttype"/>
           				</td>
           				<td>
           					<html:select property="type"  style="width:240"
                value="<%=typeid%>" >
                <html:options collection="types" property="pkid" 
                  labelProperty="name" />
		        </html:select>
           				</td>
           			
           			<td>
           			 <a href="#" class="easyui-linkbutton buttonclass3"  onclick="checktype_all();return false"><fmt:message key="button.selecttypeall"/></a>  
 
           			</td>
           			<td>
           				 <a href="#" class="easyui-linkbutton buttonclass3"  onclick="reltype_all();return false"><fmt:message key="button.releasetypeall"/></a>
           				
           			</td>
           			<td>
           				<input type="radio" name="isshoworgan" value="0" <c:if test="${organlevel==0}">checked</c:if> checked/><fmt:message key="riskWarning.bjjg"/>
           			</td>
           			<td>
           				<input type="radio" name="isshoworgan" value="1" <c:if test="${organlevel==1}">checked</c:if>/><fmt:message key="benjijixiashujigou"/>
           			</td>
           			<td>
           				<a href="#" class="easyui-linkbutton buttonclass2" data-options="iconCls:'icon-save'" onclick=" aaa();" ><fmt:message key='button.save' /></a>&nbsp;
           			</td>
           			<td>
           					<a href="#" class="easyui-linkbutton buttonclass2"   onclick="backUserList();" ><fmt:message key='flowTip.reset'/></a>			        
           			</td>
           		</tr>
           </table>
          </div> 
      
<table  align="center" class="TdBGColor2" width="100%" border="0" cellSpacing=1 cellPadding=5>
<P><html:errors/></P>
    <input type="hidden" name="usersname" value="<c:out value="${usersname}"/>"/>

			<tr>
				<td align="center" colspan="2">			
				    <div align="center" style=" width:98%">
						<display:table name="userReports" cellspacing="0" cellpadding="0"  
							    requestURI="" id="userReports" width="100%" 
							    styleClass="list userReportList" >
							    <%-- Table columns --%>				     			
							    <display:column width="5%"> 
                                	<input type=checkbox name="yy" value="<c:out value="${userReports.typeId}"/>@<c:out value="${userReports.reportId}"/>@<c:out value="${userReports.operId}"/>" 
                                	<c:if test="${userReports.status==1}">checked
								    </c:if> />
                                </display:column> 
                                <display:column sort="true" 
							    	headerClass="sortable" width="5%" 
							    	titleKey="usermanage.sysadmin.list.username">
							    	<c:out value="${userReports.operName}"/>
							    </display:column>
							    <display:column sort="true" 
							    	headerClass="sortable" width="15%" 
							    	titleKey="reportdefine.reportType.title1">
							    	<c:out value="${userReports.typeName}"/>
							    </display:column>
							    <display:column sort="true" 
							    	headerClass="sortable" width="25%" 
							    	titleKey="reportview.list.report">
							    	<c:out value="${userReports.reportName}"/>
							    </display:column>
							</display:table>
							<script type="text/javascript">
							
							//highlightTableRows("userReports");
						
							</script>
				    </div>
				</td>
			</tr>
	
<tr height=17><td colspan="4"></td></tr>
	 <tr>
		<td colspan="4" >&nbsp;		
			<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick=" aaa();" ><fmt:message key='button.save' /></a>&nbsp;
           	<a href="#" class="easyui-linkbutton"   onclick="backUserList();" ><fmt:message key='flowTip.reset'/></a>
	<!--  <input type="submit" name="method.saveUserReport" value="<fmt:message key="button.save"/>" style="width:80;" onClick="bCancel=false">	-->		        
	<!--  <input type="submit" name="method.list" value="<fmt:message key="button.back"/>" style="width:80;" onClick="bCancel=false">	-->
	<!--        <input type="button" name="but" value="<fmt:message key="button.back"/>" style="width:80;" onClick="backUserList();">-->
		    
	</td>
	</tr>
	<c:if test="${message == '1'}">
	<tr>
		<td colspan="4" class="TdBGColor1" align="center">&nbsp;		
	<img src="images/xiaolian.png"><font style="color:#990000;"><b>保存成功！</b></font>
	</td>
	</tr>
	</c:if>
	<input type="hidden" name="areaCode" value="<c:out value="${areaCode}"/>">
</table>
</html:form>

<script>
  function backUserList(){
  	var areaCode = document.forms[0].areaCode.value;
  	var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/userAction.do?method=list&areaCode="+areaCode;
	document.forms[0].action = url;
	document.forms[0].submit();
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

