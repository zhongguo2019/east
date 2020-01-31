<!-- /ps/model/reportdefine/reportSearchForm. -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/meta.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
<html>
<title><fmt:message key="webapp.prefix" /></title>
<head>

		
	 <script language="javascript" type="text/javascript" src="<c:url value='ps/uitl/My97DatePicker/WdatePicker.js'/>"></script>
	 <script type="text/javascript">
	 
	 function aaa(){
  	 document.reportForm.action="reportAction.do?method=search";
  	 document.reportForm.submit();
     }
	 	
	 </script>
</head>

<body class="easyui-layout">
<div data-options="region:'center',border:false,title:' <fmt:message key="navigation.reportdefine.search"/> '" style="width:100%;padding:0px;overflow-y:hidden ">

<script type="text/javascript">  
       function keydown(e) {  
           var isie = (document.all) ? true : false;  
           var key;  
           var ev;  
           if (isie){ 
               key = window.event.keyCode;  
               ev = window.event;  
           } else {  
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
   
   
  <!--  <script type="text/javascript">
window.parent.document.getElementById("ppppp").value= "<fmt:message key="navigation.reportdefine.search" />";
</script> -->
		<html:form action="reportAction" method="post" >
			<table cellSpacing=1 cellPadding=0 width="380" align=center border=0>
				<html:hidden property="pkid" />
				
				<c:set var="pageButtons">
					<tr  >
						<td  width=80 align="left"></td>
						<td style="padding-left: 50" >
						<a href="#" class="easyui-linkbutton " data-options="iconCls:'icon-search'"  name="ss" onclick="aaa();" ><fmt:message key='button.search'/></a>
						<!--<html:submit
							property="method.search" style="width:60;"
							onclick="bCancel=false">
							<fmt:message key="button.search" />
						</html:submit> -->
						</td>
					</tr>
				</c:set>

				<tr>
					<td  width=80 align="right"><fmt:message
						key="reportdefine.report.name" /></td>
					<td ><html:text property="name"
						style="width:240;" /></td>
				</tr>
				<tr>
					<td  width=80 align="right"><fmt:message
						key="reportdefine.report.code" /></td>
					<td ><html:text property="code"
						style="width:240;" /></td>
				</tr>

				<tr>
					<td  width=80 align="right"><fmt:message
						key="reportdefine.report.reportType" /></td>
					<td ><html:select property="reportType" size="1"
						style="width:240;">
						<html:option value="">
							<fmt:message key="reportdefine.reportType.all" />
						</html:option>
						<c:forEach var="type" items="${types}">
							<html-el:option value="${type.pkid}">
								<c:out value="${type.name}" />
							</html-el:option>
						</c:forEach>
					</html:select></td>
				</tr>
				
				<tr>
					<td  width=80 align="right"><fmt:message
						key="reportdefine.report.frequency" /></td>
					<td ><html:select property="frequency" size="1"
						style="width:240;">
						<html:option value="">
							<fmt:message key="reportdefine.reportItem.allfrequency" />
						</html:option>
						<c:forEach var="frequency" items="${frequencys}">
							<html-el:option value="${frequency.dicid}">
								<c:out value="${frequency.dicname}" />
							</html-el:option>
						</c:forEach>
					</html:select></td>
				</tr>								
				<tr>
				    <td  width=80 align="right"><fmt:message
						key="reportdefine.report.beginDate" /></td>
					<td >
					<%-- <html:text property="beginDate"
						style="width:240;" readonly="true" /> --%>
						
						<input id="beginDate" name="beginDate" type="text" 
									style="width:240;" readonly="true" onClick="WdatePicker()"/>
						</td>											
			    </tr>
			    <tr>
				    <td  width=80 align="right"><fmt:message
						key="reportdefine.report.endDate" /></td>
					<td >
					<%-- <html:text property="endDate"
						style="width:240;" readonly="true" /> --%>
						<input id="endDate" name="endDate" type="text" 
									style="width:240;" readonly="true" onClick="WdatePicker()"/>
						</td>											
			    </tr>
				<c:out value="${pageButtons}" escapeXml="false" />
			
			</table>

		</html:form>
	
</div>
</body>
</html>
