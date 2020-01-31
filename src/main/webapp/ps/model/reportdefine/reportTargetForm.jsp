<!-- /ps/model/reportdefine/reportTargetForm.j -->
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/meta.jsp" %>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
        <title><fmt:message key="webapp.prefix"/></title>
        
     <link rel="stylesheet" type="text/css" media="all" href="<c:url value='${cssPath}/common.css'/>" />

<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
        <link rel="stylesheet" type="text/css" media="all" href="<c:url value='${cssPath}/helptip.css'/>" />
        <link rel="stylesheet" type="text/css" media="print" href="<c:url value='${cssPath}/print.css'/>" /> 
        <script type="text/javascript" src="<c:url value='/ps/scripts/prototype.js'/>"></script> 
        <script type="text/javascript" src="<c:url value='/ps/scripts/effects.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/ps/scripts/helptip.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>  
        
        <link rel="stylesheet" type="text/css" media="all" href="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-win2k-1.css'/>" title="win2k-cold-1" /> 

<link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>
			<script language = javascript>

			
				var bCancel = false
				function validate(){
					if(bCancel==true){
						return true
					}
				
					if(document.reportTargetForm.targetName.value==""){
						window.alert("<fmt:message key="js.validate.targetname"/>");
						return false;
					}
					
					if(document.reportTargetForm.endDate.value<document.reportTargetForm.beginDate.value){
						window.alert("<fmt:message key="js.validate.date2"/>");
						return false;
					}
					return true;
				}
			</script>

<script type="text/javascript">
//window.parent.document.getElementById("ppppp").value= "<fmt:message key="navigation.reportdefine.target"/>";
</script>

<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">

<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  
  <tr>
    <td width="100%" valign="top">
    <br>
    <br>


			<html:form action="reportTargetAction" method="post" onsubmit="return validate()">
			<table cellSpacing=1 cellPadding=0 width="380" align=center border=0 class="TableBGColor">
			<html:hidden property="pkid"/>
			<c:set var="pageButtons">
			    <tr align="center" class="BtnBgColor">
			       <td class="TdBGColor1" width=80 align="left"> </td>
			    	<td class="buttonBar" align="left">
			    		<a href="#" style="width: " class="easyui-linkbutton" data-options="iconCls:'icon-save'"> <fmt:message key="button.confirm"/></a>
			    		
			           <!--   <html:submit style="width:60;" property="method.save" onclick="bCancel=false">
			            	  <fmt:message key="button.confirm"/>
			            </html:submit>
			            
			             <html:cancel style="width:60;" onclick="bCancel=true">
			                <fmt:message key="button.cancel"/>
			            </html:cancel>-->
			        </td>
			    </tr>
			</c:set>
			    <tr>
			        <!--5--><td class="TdBGColor1" width=80 align="right">
			            <fmt:message key="reportdefine.reportTarget.targetName"/>
			        </td>
			        <td class="TdBGColor2"><!-- 2 -->
			            <html:text maxlength="15" property="targetName" style="width:240;"/>
			        </td>
			    </tr>
			    
			    <tr>
			        <!--5--><td class="TdBGColor1" width=80 align="right">
			            <fmt:message key="reportdefine.reportTarget.targetField"/>
			        </td>
			        <td class="TdBGColor2"><!-- 2 -->
			        	<c:if test="${fieldDefine == '0'}">
			            <html:select property="targetField" size="1" style="width:240;">
			        		<c:forEach var="lnum" begin="1" end="50" step="1">
			            		<html-el:option value="${lnum}">itemvalue<c:out value="${lnum}"/></html-el:option>
			            	</c:forEach>
			            </html:select>
			            </c:if>
			            <c:if test="${fieldDefine == '1'}">
			            	<html:select property="targetField" size="1" style="width:240;">
			            		<c:forEach var="field" items="${fields}">
			            			<c:if test="${reportTargetForm.targetField!=field.fieldNameStr}">
			            	   		<option value="<c:out value="${field.fieldNameStr}"/>" >
       									<c:out value="${field.fieldName}"/>
									</option>
									</c:if>
			            			<c:if test="${reportTargetForm.targetField==field.fieldNameStr}">
			            	   		<option value="<c:out value="${field.fieldNameStr}"/>" selected>
       									<c:out value="${field.fieldName}"/>
									</option>
									</c:if>
								</c:forEach>
			            	</html:select>
			            </c:if>
			        </td>
			    </tr>
			    
			    <tr>
			        <!--5--><td class="TdBGColor1" width=80 align="right">
			            <fmt:message key="reportdefine.reportTarget.beginDate"/>
			        </td>
			        <td class="TdBGColor2"><!-- 2 -->
			        	<html:text property="beginDate" styleId = "beginDate" readonly = "true" style="width:240;"/>
			            
			        </td>
			    </tr>
			    
			    <tr>
			        <!--5--><td class="TdBGColor1" width=80 align="right">
			            <fmt:message key="reportdefine.reportTarget.endDate"/>
			        </td>
			        <td class="TdBGColor2"><!-- 2 -->
			        	<html:text property="endDate" styleId = "endDate" readonly = "true" style="width:240;"/>
			            
			        </td>
			    </tr>
			    
			    <tr>
			        <!--5--><td class="TdBGColor1" width=80 align="right">
			            <fmt:message key="reportdefine.reportItem.dataType"/>
			        </td>
			        <td class="TdBGColor2"><!-- 2 -->
			        	<html:select property="dataType" size="1" style="width:240;">
			        	
			        		<html:option value="4"><fmt:message key="reportdefine.reportItem.dataType.scale"/></html:option>
        					<html:option value="1"><fmt:message key="reportdefine.reportItem.dataType.num"/></html:option>
        					<html:option value="2"><fmt:message key="reportdefine.reportItem.dataType.int"/></html:option>
        					<html:option value="3"><fmt:message key="reportdefine.reportItem.dataType.string"/></html:option>
        					
			            </html:select>
			            
			        </td>
			    </tr>
			    
			    <tr>
	  	  			<td class="TdBGColor1"><fmt:message key="repfile.rep.outtype"></fmt:message></td>
	  	  			<td class="TdBGColor2"><select name="outrule">
	  	  				<option value="0"><fmt:message key="repfile.rep.outtype0"></fmt:message></option>
	  	  				<option value="1"><fmt:message key="repfile.rep.outtype1"></fmt:message></option>
	  	  				<option value="2"><fmt:message key="repfile.rep.outtype2"></fmt:message></option>
	  	  				<option value="3"><fmt:message key="repfile.rep.outtype3"></fmt:message></option>
	  	  			</select></td>
	  	  		</tr>
	  	  		<tr>
	  	  			<td class="TdBGColor1"><fmt:message key="repfile.rep.outrule"></fmt:message></td>
	  	  			<td class="TdBGColor2"><input type="text" name="rulesize" /></td>
	  	  		</tr>
			    <c:out value="${pageButtons}" escapeXml="false" />
			    <tr>
					<td class="FormBottom" colspan="2" height="17"></td>
				</tr>
			</table>
			
			</html:form>

