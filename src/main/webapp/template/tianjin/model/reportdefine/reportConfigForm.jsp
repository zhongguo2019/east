<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/meta.jsp" %>

<c:set var="colName">
	<fmt:message key="common.list.reportname"/>
</c:set>
<c:set var="reportTreeURL">
	<c:out value="${hostPrefix}"/><c:url value='/reportView.do?method=repTreeAjax${params}'/>
</c:set>
<c:set var="reportButton">
	<fmt:message key="place.select"/>
</c:set>
<html>
<head>
        <title><fmt:message key="webapp.prefix"/></title>
        
        <link href="/theme/default/skin_01/style/common.css" rel="stylesheet" type="text/css">
        
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
        <link rel="stylesheet" type="text/css" media="all" href="<c:url value='${cssPath}/helptip.css'/>" />
        <link rel="stylesheet" type="text/css" media="print" href="<c:url value='${cssPath}/print.css'/>" />    
		<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-win2k-1.css'/>" title="win2k-cold-1" /> 
        <script type="text/javascript" src="<c:url value='/ps/scripts/prototype.js'/>"></script> 
        <script type="text/javascript" src="<c:url value='/ps/scripts/effects.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/ps/scripts/helptip.js'/>"></script>
        <script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
        
        <script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/lang/zh-cn.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-setup.js'/>"></script>
		
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/ActiveXTree/ActiveXTree.js'/>"></script> 
		
		<script src="<c:url value='/ps/framework/common/jqueryTab/jquery-1.2.4b.js'/>" type="text/javascript"></script>

			<script language = javascript>

			
				var bCancel = false
				var funcId = "<c:out value="${funcId}"/>";
				function validate(){
					if(bCancel==true){
						return true
					}
				
					if(document.reportConfigForm.funId.value==""){
						window.alert("<fmt:message key="js.validate.funconfig"/>");
						return false;
					}
					if (funcId == '42'){
						if (document.reportConfigForm.repId.value == ''){
							window.alert("<fmt:message key="js.validate.report"/>");
							return false;
						}
					} else {
						if(document.reportConfigForm.defInt.value==""){
							window.alert("<fmt:message key="js.validate.defconfig"/>");
							return false;
						}
					}
					var oDefChar = document.reportConfigForm.defChar;
					if (oDefChar){
						if (oDefChar.value==""){
							window.alert("<fmt:message key="reportDefine.nullConfig"/>");
							return false;
						}
						var vDefChar = oDefChar.value;
						<c:if test="${funcId != '50' && funcId != '42'}">
						if (vDefChar.length!=7){
							window.alert("<fmt:message key="reportDefine.sevenConfig"/>");
							return false;
						}
						</c:if>
					}
					return true;
				}
				
			</script>
</head>


<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">

<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
  <td width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td>
				<p style="margin-top: 3"><font class=b><b><fmt:message
				 key="navigation.reportdefine.config"/></b></font></p>
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


			<html:form action="reportConfigAction" method="post" onsubmit="return validate()">
			<table cellSpacing=1 cellPadding=0 width="380" align=center border=0 class="TableBGColor">
			<html:hidden property="pkid"/>
			<c:set var="pageButtons">
			    <tr align="center" class="BtnBgColor">
			       <td width=110 align="left" nowrap class="TdBGColor1"> </td>
			    	<td width="267" align="left" class="buttonBar">
			            <html:submit style="width:60;" property="method.save" onclick="bCancel=false;">
			            	  <fmt:message key="button.confirm"/>
			            </html:submit>
			             <html:cancel style="width:60;" onclick="bCancel=true">
			                <fmt:message key="button.cancel"/>
			            </html:cancel>
			        </td>
			    </tr>
			</c:set>			    
			    <tr>
			        <td width=110 align="right" nowrap class="TdBGColor1">
			            <fmt:message key="reportDefine.funConfigs"/>
			        </td>
			        <td class="TdBGColor2">			        	
			            <html:select property="funId" size="1" style="width:240;">
			        		<c:forEach var="fi" items="${funIdList}">
        						<html-el:option value="${fi.dicid}">
       								<c:out value="${fi.dicname}"/>
								</html-el:option>
							</c:forEach>
			            </html:select>
			        </td>
			    </tr>
			    <c:if test="${funcId != '33' && funcId != '42' }">
			   <tr>
			        <td width=110 align="right" nowrap class="TdBGColor1">
			            <fmt:message key="reportDefine.defConfigs"/>
			        </td>
			        <td class="TdBGColor2">
			        <c:if test="${ funcId == '20' }">
						<input type="hidden" name="reportName">
			        	<input type="hidden" name="repId" style="width:240" value="<c:out value="${ repId }"/>">
			        	<slsint:ActiveXTree left="220" top="325" width="240"
						height="${activeXTreeHeight }" xml="${reportTreeURL}"
						bgcolor="0xFFD3C0"
						rootid="10000"
						columntitle="${colName}"
						columnwidth="240,0,0,0"
						formname="reportConfigForm"
						idstr="repId" 
						namestr="reportName"
						checkstyle="2" 
						filllayer="2" 
						txtwidth="160" 
						buttonname="${reportButton}">
					</slsint:ActiveXTree>
			        </c:if>
			        <c:if test="${funcId != '20'}">
			        	<html:select property="defInt" size="1" style="width:240;" onchange="javascript:effectUnit();">
			        		<c:forEach var="di" items="${defList}">
			        		    <c:if test="${funcId=='11'}">
        						<html-el:option value="${di.dicid}">
       								<c:out value="${di.dicname}"/>
								</html-el:option>
								</c:if>
			        		    <c:if test="${funcId=='99'}">
        						<html-el:option value="${di.dicid}">
       								<c:out value="${di.dicname}"/>
								</html-el:option>
								</c:if>
								<c:if test="${funcId=='15'}">
        						<html-el:option value="${di.dicid}">
       								<c:out value="${di.dicname}"/>
								</html-el:option>
								</c:if>
								<c:if test="${funcId=='16'}">
        						<html-el:option value="${di.dicid}">
       								<c:out value="${di.dicname}"/>
								</html-el:option>
								</c:if>
								<c:if test="${funcId=='41'}">
        						<html-el:option value="${di.dicid}">
       								<c:out value="${di.dicname}"/>
								</html-el:option>
								</c:if>
								<c:if test="${funcId=='31'}">
        						<html-el:option value="${di.dicid}">
       								<c:out value="${di.dicname}"/>
								</html-el:option>
								</c:if>
								<c:if test="${funcId=='32'}">
        						<html-el:option value="${di.dicid}">
       								<c:out value="${di.dicname}"/>
								</html-el:option>
								</c:if>
								<c:if test="${funcId=='12'}">
        						<html-el:option value="${di.targetField}">
       								<c:out value="${di.targetName}"/>
								</html-el:option>
								</c:if>
								<c:if test="${funcId=='13'}">
        						<html-el:option value="${di.targetField}">
       								<c:out value="${di.targetName}"/>
								</html-el:option>
								</c:if>
								<c:if test="${funcId=='21'}">
        						<html-el:option value="${di.targetField}">
       								<c:out value="${di.targetName}"/>
								</html-el:option>
								</c:if>
								<c:if test="${funcId=='22'}">
        						<html-el:option value="${di.targetField}">
       								<c:out value="${di.targetName}"/>
								</html-el:option>
								</c:if>
								<c:if test="${funcId=='35'}">
        							<html-el:option value="${di.dicid}">
       									<c:out value="${di.dicname}"/>
									</html-el:option>
								</c:if>
								<c:if test="${funcId=='50'}">
								<option value="<c:out value="${di.targetField}"/>" dataType="<c:out value="${di.dataType}"/>">
       								<c:out value="${di.targetName}"/>
								</option>
								<!-- 
        						<html-el:option value="${di.targetField}" >
       								<c:out value="${di.targetName}"/>
								</html-el:option>
								 -->
								</c:if>
							</c:forEach>		     
			            </html:select>
			            </c:if>
			        </td>
		      </tr> 
		      </c:if>
			    <c:if test="${funcId=='33'}">
			    	 <tr>
				        <td width=110 align="right" nowrap class="TdBGColor1">	
				        	<fmt:message key="reportDefine.subarea"/>		            
				        </td>
				        <td class="TdBGColor2">
				        	<html:select property="defChar" size="1" style="width:240;">
			        		<c:forEach var="di" items="${defList}">
			        			<html-el:option value="${di.description}">
       								<c:out value="${di.dicname}"/>
								</html-el:option>
			        		</c:forEach>
			        	</html:select>	            
				        </td>
				    </tr>
			    </c:if>
			     <c:if test="${funcId=='12'}">
			     <tr>
			        <td width=110 align="right" nowrap class="TdBGColor1">	
			        	<fmt:message key="reportDefine.currencyConfig"/>		            
			        </td>
			        <td class="TdBGColor2">
			        	<html:text property="defChar" styleClass="textfield" style="width:240;"/>			            
			        </td>
			    </tr>
			    <tr>
			        <td width=110 align="right" nowrap class="TdBGColor1">	
			        	<fmt:message key="repConfig.dataAttributeNew"/>		            
			        </td>
			        <td class="TdBGColor2">
			        	<html:select property="newDataAttribute" size="1" style="width:240;">
			        		<c:forEach var="di" items="${newDataAttribute}">
			        			<html-el:option value="${di.dicid}">
       								<c:out value="${di.dicname}"/>
								</html-el:option>
			        		</c:forEach>
			        	</html:select>			            
			        </td>
			    </tr>
			    <tr>
			        <td width=110 align="right" nowrap class="TdBGColor1">	
			        	<fmt:message key="repConfig.dataAttributeOld"/>		            
			        </td>
			        <td class="TdBGColor2">
			        	<html:select property="oldDataAttribute" size="1" style="width:240;">
			        		<c:forEach var="di" items="${oldDataAttribute}">
			        			<html-el:option value="${di.dicid}">
       								<c:out value="${di.dicname}"/>
								</html-el:option>
			        		</c:forEach>
			        	</html:select>			            
			        </td>
			    </tr>
			    </c:if>
			     <c:if test="${funcId=='13'}">
			     <tr>
			        <td width=110 align="right" nowrap class="TdBGColor1">
			        	<fmt:message key="reportDefine.currencyConfig"/>			            
			        </td>
			        <td class="TdBGColor2">
			        	<html:text property="defChar" styleClass="textfield" style="width:240;"/>	            
			        </td>
			    </tr>
			    <tr>
			        <td width=110 align="right" nowrap class="TdBGColor1">	
			        	<fmt:message key="repConfig.dataAttributeNew"/>		            
			        </td>
			        <td class="TdBGColor2">
			        	<html:select property="newDataAttribute" size="1" style="width:240;">
			        		<c:forEach var="di" items="${newDataAttribute}">
			        			<html-el:option value="${di.dicid}">
       								<c:out value="${di.dicname}"/>
								</html-el:option>
			        		</c:forEach>
			        	</html:select>			            
			        </td>
			    </tr>
			    <tr>
			        <td width=110 align="right" nowrap class="TdBGColor1">	
			        	<fmt:message key="repConfig.dataAttributeOld"/>		            
			        </td>
			        <td class="TdBGColor2">
			        	<html:select property="oldDataAttribute" size="1" style="width:240;">
			        		<c:forEach var="di" items="${oldDataAttribute}">
			        			<html-el:option value="${di.dicid}">
       								<c:out value="${di.dicname}"/>
								</html-el:option>
			        		</c:forEach>
			        	</html:select>			            
			        </td>
			    </tr>
			    </c:if>
			    
			    <c:if test="${funcId=='21'}">
			     <tr>
			        <td width=110 align="right" nowrap class="TdBGColor1">			            
			        </td>
			        <td class="TdBGColor2">
			        	<html:select property="defInt1" size="1" style="width:240;">
			        		<c:forEach var="di" items="${defList}">        						
        						<html-el:option value="${di.targetField}">
       								<c:out value="${di.targetName}"/>
								</html-el:option>
							</c:forEach>		     
			            </html:select>
			            
			        </td>
			    </tr>     
			    </c:if>
			    <c:if test="${funcId=='22'}">
			     <tr>
			        <td width=110 align="right" nowrap class="TdBGColor1">			           
			        </td>
			        <td class="TdBGColor2">
			        	<html:select property="defInt1" size="1" style="width:240;">
			        		<c:forEach var="di" items="${defList}">			        		   
        						<html-el:option value="${di.targetField}">
       								<c:out value="${di.targetName}"/>
								</html-el:option>
							</c:forEach>		     
			            </html:select>
			            
			        </td>
			    </tr>     
			    </c:if>
			    <c:if test="${funcId=='50' }">
			    	<tr>
			        <td width=110 align="right" nowrap class="TdBGColor1">			           
			        </td>
			        <td class="TdBGColor2">
			        	<select name="defChar" id="defChar" size="1" style="width:240;">
        					<option value="1"><fmt:message key="reportdefine.reportItem.dataType.num"/></option>
        					<option value="2"><fmt:message key="reportdefine.reportItem.dataType.int"/></option>
        					<option value="3"><fmt:message key="reportdefine.reportItem.dataType.string"/></option>
        					<option value="4"><fmt:message key="reportdefine.reportItem.dataType.scale"/></option>
			            </select>
			        </td>
			    </tr>     
			    </c:if>
			    <c:if test="${funcId == '42' }">
			    	<tr>
			    		<td width=110 align="right" nowrap class="TdBGColor1">
			    			<fmt:message key="reportDefine.targetReportWithColon"></fmt:message>
			    		</td>
			    		<td class="TdBGColor2">
							<input type="hidden" name="reportName" value="<c:out value="${reportName }"/>"/>
				        	<input type="hidden" name="repId" style="width:240" value="<c:out value="${ repId }"/>"/>
				        	<slsint:ActiveXTree left="220" top="325" width="240"
								height="${activeXTreeHeight }" xml="${reportTreeURL}"
								bgcolor="0xFFD3C0"
								rootid="10000"
								columntitle="${colName}"
								columnwidth="240,0,0,0"
								formname="reportConfigForm"
								idstr="repId" 
								namestr="reportName"
								checkstyle="2" 
								filllayer="2" 
								txtwidth="160" 
								buttonname="${reportButton}">
							</slsint:ActiveXTree>
						</td>
					</tr>
					<tr>
						<td width=110 align="right" nowrap class="TdBGColor1">
							<fmt:message key="reportDefine.relatePatternWithColon"></fmt:message>
						</td>
						<td class="TdBGColor2" style="width:160">
							<select name="defChar">
								<option value="F"><fmt:message key="common.list.frequency"/></option>
								<option value="C"><fmt:message key="creditReport.input.caption.bz"/></option>
								<option value="M"><fmt:message key="reportDefine.sourceReport"/></option>
							</select>
						</td>
					</tr>
					<tr>
						<td width=110 align="right" nowrap class="TdBGColor1">
							<fmt:message key="currency.title.descriptionWithColon"></fmt:message>
						</td>
						<td class="TdBGColor2" style="width:160">
							<input type="text" name="description" />
						</td>
					</tr>
				</c:if>
			    <c:out value="${pageButtons}" escapeXml="false" />
			    <tr>
					<td class="FormBottom" colspan="2" height="17"></td>
				</tr>
			</table>	
			</html:form>
</td>
</tr>
</table>
</body>
</html>			
<script type="text/javascript">
var funcId = "<c:out value="${funcId}"/>";
function effectUnit(){
	if(funcId == '50'){
		var dataType = document.forms[0].defInt.options[document.forms[0].defInt.selectedIndex].dataType;
		var unit = $("#defChar > option");
		unit.each(function(i){
		if(this.value == dataType){
			this.selected = "selected";
		}
	});
	}
}
function changeTree1(){}
</script>