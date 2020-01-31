<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/meta.jsp" %>
        <title><fmt:message key="webapp.prefix"/></title>
        
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${cssPath}/common.css'/>" />
        <link rel="stylesheet" type="text/css" media="all" href="<c:url value='${cssPath}/helptip.css'/>" />
        <link rel="stylesheet" type="text/css" media="print" href="<c:url value='${cssPath}/print.css'/>" /> 
        <script type="text/javascript" src="<c:url value='/ps/scripts/prototype.js'/>"></script> 
        <script type="text/javascript" src="<c:url value='/ps/scripts/effects.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/ps/scripts/helptip.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>  
           
		<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-win2k-1.css'/>" title="win2k-cold-1" /> 
		<script type="text/javascript" src="<c:url value='/ps/scripts/calendar.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/lang/zh-cn.js'/>"></script>  
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-setup.js'/>"></script>

			<script language = "javascript">

			
				var bCancel = false
				function validate(){
					if(bCancel==true){
						return true
					}
				
					if(document.reportItemForm.itemName.value==""){
						window.alert("<fmt:message key="js.validate.itemname"/>");
						return false;
					}
					if(document.reportItemForm.code.value=="" || document.reportItemForm.code.value.length>10){
						window.alert("<fmt:message key="js.validate.itemcode"/>");
						return false;
					}
					
					if(document.reportItemForm.endDate.value<document.reportItemForm.beginDate.value){
						window.alert("<fmt:message key="js.validate.date2"/>");
						return false;
					}
					return true;
				}
				//alert error message when duplicate found
				<c:if test="${error1=='-1'}" >
				 alert("<fmt:message key="pub.itemtoccupy.tip"/>");
				</c:if>
		</script>

<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
   <td width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td>
				<p style="margin-top: 3"><font class=b><b><fmt:message
				 key="navigation.reportdefine.item"/></b></font></p>               
          </td>
		  <td></td> 
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td width="100%" bgcolor="#EEEEEE" valign="top">
    <br>
    <br>
			<html:form action="reportItemAction" method="post" onsubmit="return validate()">
			<table cellSpacing=1 cellPadding=0 width="380" align=center border=0 class="TableBGColor">
			<html:hidden property="pkid"/>
			
			<input type="hidden" name="showOrder" value="<c:out value="${param.showOrder}"/>"/>
			<c:set var="pageButtons">
			    <tr align="center" class="BtnBgColor">
			       <td class="TdBGColor1" width=80 align="left"> </td>
			    	<td class="buttonBar" align="left">
			    	<c:if test="${insertItem == 'true'}">
			            <html:submit style="width:60;" property="method.insertItem" onclick="bCancel=false">
			            	  <fmt:message key="button.confirm"/>
			            </html:submit>
			    	</c:if>
			    	<c:if test="${empty insertItem || insertItem != 'true'}">
			            <html:submit style="width:60;" property="method.save" onclick="bCancel=false">
			            	  <fmt:message key="button.confirm"/>
			            </html:submit>
			    	</c:if>
			             <html:cancel style="width:60;" onclick="bCancel=true">
			                <fmt:message key="button.cancel"/>
			            </html:cancel>
			        </td>
			    </tr>
			</c:set>			
			    <tr>
			        <td class="TdBGColor1" width=80 align="right">
			            <fmt:message key="reportdefine.reportItem.itemName"/>
			        </td>
			        <td class="TdBGColor2">
			        	<html:text maxlength="25" property="itemName" style="width:240;"/>			            
			        </td>
			    </tr>			    
			    <tr>
			        <td class="TdBGColor1" width=80 align="right">
			            <fmt:message key="reportdefine.reportItem.code"/>
			        </td>
			        <td class="TdBGColor2">
			        	<html:text maxlength="20" property="code" style="width:240;"/>
			            
			        </td>
			    </tr>			    
			    <tr>
			        <td class="TdBGColor1" width=80 align="right">
			            <fmt:message key="reportdefine.reportItem.conCode" />
			        </td>
			        <td class="TdBGColor2">
			        	<html:text maxlength="20" property="conCode" style="width:240;"/>			            
			        </td>
			    </tr>			    
			    <tr>
			        <td class="TdBGColor1" width=80 align="right">
			            <fmt:message key="reportdefine.reportItem.frequency"/>
			        </td>
			        <td class="TdBGColor2">
			        	<html:select property="frequency" size="1" style="width:240;">
			        		<c:forEach var="frequency" items="${frequencys}">
			            		<html-el:option value="${frequency.dicid}">
			            			<c:out value="${frequency.dicname}"/>
			            		</html-el:option>
			           		</c:forEach>
			            </html:select>
			            
			        </td>
			    </tr>			    
			    <tr>
			        <td class="TdBGColor1" width=80 align="right">
			            <fmt:message key="reportdefine.reportItem.isCollect"/>
			        </td>
			        <td class="TdBGColor2">
			            <html:select property="isCollect" size="1" style="width:240;">
        					<html:option value="0"><fmt:message key="select.no"/></html:option>
        					<html:option value="1"><fmt:message key="select.yes"/></html:option>
			            </html:select>
			        </td>
			    </tr>			    
			    <tr>
			        <td class="TdBGColor1" width=80 align="right">
			            <fmt:message key="reportdefine.reportItem.isOrgCollect"/>
			        </td>
			        <td class="TdBGColor2"><!-- 2 -->
			            <html:select property="isOrgCollect" size="1" style="width:240;">
        					<html:option value="0"><fmt:message key="select.no"/></html:option>
        					<html:option value="1"><fmt:message key="select.yes"/></html:option>
			            </html:select>
			        </td>
			    </tr>			    
			    <tr>
			        <td class="TdBGColor1" width=80 align="right">
			            <fmt:message key="reportdefine.reportItem.beginDate"/>
			        </td>
			        <td class="TdBGColor2"><!-- 2 -->
			        	<html:text property="beginDate" styleId = "beginDate" readonly = "true" style="width:240;"/>
			            
			        </td>
			    </tr>			    
			    <tr>
			        <td class="TdBGColor1" width=80 align="right">
			            <fmt:message key="reportdefine.reportItem.endDate"/>
			        </td>
			        <td class="TdBGColor2">
			        	<html:text property="endDate" styleId = "endDate" readonly = "true" style="width:240;"/>
			            
			        </td>
			    </tr>			    
			    <tr>
			        <td class="TdBGColor1" width=80 align="right">
			            <fmt:message key="reportdefine.reportItem.superId"/>
			        </td>
			        <td class="TdBGColor2">
			        	<html:select property="superId" size="5" style="width:240;">
			            	<html:option value="">
       							<fmt:message key="reportdefine.reportItem.none"/>
							</html:option>
			            	<c:forEach var="superId" items="${superIds}">
        						<html-el:option value="${superId.pkid}">
       								<c:out value="${superId.itemName}"/>
								</html-el:option>
        					</c:forEach>
			            </html:select>
			            
			        </td>
			    </tr>
			    
			    <tr>
			        <td class="TdBGColor1" width=80 align="right">
			            <fmt:message key="reportdefine.reportItem.dataType"/>
			        </td>
			        <td class="TdBGColor2">
			        	<html:select property="dataType" size="1" style="width:240;">			        	
        					<html:option value="1"><fmt:message key="reportdefine.reportItem.dataType.num"/></html:option>
        					<html:option value="2"><fmt:message key="reportdefine.reportItem.dataType.int"/></html:option>
        					<html:option value="3"><fmt:message key="reportdefine.reportItem.dataType.string"/></html:option>
			        		<html:option value="4"><fmt:message key="reportdefine.reportItem.dataType.scale"/></html:option>
			            </html:select>
			            
			        </td>
			    </tr>
			    
			    
			    <c:out value="${pageButtons}" escapeXml="false" />
			    <tr>
					<td class="FormBottom" colspan="2" height="17"></td>
				</tr>
			</table>
			
			</html:form>
		
		<script language = "javascript">
			Calendar.setup(
				{
					inputField     :    "beginDate",  
					ifFormat       :    "%Y-%m-%d",     
					showsTime      :    false
				}
			);
			Calendar.setup(
				{
					inputField     :    "endDate",  
					ifFormat       :    "%Y-%m-%d",     
					showsTime      :    false
				}
			);
		</script>