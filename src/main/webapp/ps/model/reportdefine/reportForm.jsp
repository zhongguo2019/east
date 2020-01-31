<!-- /ps/model/reportdefine/reportForm. -->
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/meta.jsp"%>
<title><fmt:message key="webapp.prefix" /></title>

<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
      	<script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
       	
	
		
	 	<script src="<c:url value='/ps/framework/common/jqueryTab/jquery-1.2.4b.js'/>" type="text/javascript"></script>
		<script src="<c:url value='/ps/framework/common/jqueryTab/jquery-1.4.1.min.js'/>" type="text/javascript"></script>
		
	<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${eaUIPath}/themes/default/easyui.css'/>" />
	 <link rel="stylesheet" type="text/css" media="all" href="<c:url value='${eaUIPath}/themes/icon.css'/>" />
	 <link rel="stylesheet" type="text/css" href="<c:url value='${eaUIPath}/demo.css'/>"> 
	 <script type="text/javascript" src="<c:url value='${eaUIPath}/jquery.min.js'/>"></script> 
	 <script type="text/javascript" src="<c:url value='${eaUIPath}/jquery.easyui.min.js'/>"></script> 
	 <script language="javascript" type="text/javascript" src="<c:url value='ps/uitl/My97DatePicker/WdatePicker.js'/>"></script>

<script type="text/javascript">
//window.parent.document.getElementById("ppppp").value= "<fmt:message key="navigation.reportdefine.report" />";
</script>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">

<table border="0" cellpadding="0" cellspacing="0" width="100%"
	height="100%">
	
	<tr>
		<td width="100%" valign="top"><br>
		<br>

		<html:form action="reportAction?method=save" styleId="reportForm"
			method="post" onsubmit="return validate()">
			<table cellSpacing=1 cellPadding=0 width="460" align=center border=0
				class="TableBGColor">
				<html:hidden property="pkid" />
				<c:set var="pageButtons">
					<tr align="center" class="BtnBgColor">
						<td class="TdBGColor1" width=80 align="left"></td>
						<td class="buttonBar" align="left">
						<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="return validate();"><fmt:message key='button.save' /></a>
						<a href="#" class="easyui-linkbutton"  iconCls="icon-cancel" onclick="cancel();" ><fmt:message key='button.cancel'/></a> 
						<!--  <input name="method.save"
							type="submit" value="<fmt:message key="button.save" />"
							style="width:60;" onClick="return validate();"> <input
							name="method.list" type="button"
							value="<fmt:message key="button.cancel" />" style="width:60;"
							onClick="cancel()">--></td>
					</tr>
				</c:set>

				<tr>
					<td class="TdBGColor1" width=80 align="right"><fmt:message
						key="reportdefine.report.name" /></td>
					<td class="TdBGColor2"><html:text maxlength="30" property="name"
						style="width:240;" /></td>
				</tr>
				<tr>
					<td class="TdBGColor1" width=80 align="right"><fmt:message
						key="reportdefine.report.code" /></td>
					<td class="TdBGColor2"><html:text maxlength="10" property="code"
						style="width:240;" /></td>
				</tr>
				<tr>
					<td class="TdBGColor1" width=80 align="right"><fmt:message
						key="reportdefine.report.isSum" /></td>
					<td class="TdBGColor2"><html:select property="isSum" size="1"
						style="width:240;">
						<html:option value="1">
							<fmt:message key="select.collect" />
						</html:option>
						<html:option value="0">
							<fmt:message key="select.order" />
						</html:option>
					</html:select></td>
				</tr>

				<tr>
					<td class="TdBGColor1" width=80 align="right"><fmt:message
						key="reportdefine.report.reportType" /></td>
					<td class="TdBGColor2"><html:select property="reportType" size="1"
						style="width:240;">
						
						
						<c:forEach var="type" items="${types}">
							<html-el:option value="${type.pkid}">
								<c:out value="${type.name}" />
							</html-el:option>
						</c:forEach>
					</html:select></td>
				</tr>
				<tr>
					<td class="TdBGColor1" width=80 align="right"><fmt:message
						key="reportdefine.report.rol" /></td>
					<td class="TdBGColor2"><html:text maxlength="2" property="rol"
						style="width:240;" /></td>
				</tr>
				<tr>
					<td class="TdBGColor1" width=80 align="right"><fmt:message
						key="reportdefine.report.frequency" /></td>
					<td class="TdBGColor2"><c:forEach var="frequence"
						items="${frequencys}">
						<html-el:radio property="frequency" value="${frequence.dicid}" />
						<c:out value="${frequence.dicname}" />&nbsp;&nbsp;&nbsp;&nbsp;
				            </c:forEach></td>
				</tr>
				<tr>
					<td class="TdBGColor1" width=80 align="right"><fmt:message
						key="reportdefine.report.organType" /></td>
					<td class="TdBGColor2"><c:forEach var="organType"
						items="${organTypes}">
						<html:multibox property="organType">
							<c:out value="${organType.dicid}" />
						</html:multibox>
						<c:out value="${organType.dicname}" />
					</c:forEach></td>
				</tr>
				<tr>
					<td class="TdBGColor1" width=80 align="right"><fmt:message
						key="reportdefine.report.moneyunit" /></td>
					<td class="TdBGColor2"><html:select property="moneyunit" size="1"
						style="width:240;">
						<c:forEach var="type" items="${munits}">
							<html-el:option value="${type.pkid}">
								<c:out value="${type.name}" />
							</html-el:option>
						</c:forEach>
					</html:select></td>
				</tr>
				<tr>
					<td class="TdBGColor1" width=80 align="right"><fmt:message
						key="reportdefine.report.beginDate" /></td>
					<td class="TdBGColor2"><html:text property="beginDate"
						styleId="beginDate" readonly="true" style="width:240;" /></td>
				</tr>
				<tr>
					<td class="TdBGColor1" width=80 align="right"><fmt:message
						key="reportdefine.report.endDate" /></td>
					<td class="TdBGColor2"><html:text property="endDate"
						styleId="endDate" readonly="true" style="width:240;" /></td>
				</tr>

				<tr>
					<td class="TdBGColor1" width=80 align="right"><fmt:message
						key="reportdefine.report.phyTable" /></td>
					<td class="TdBGColor2">
					<input name="phyTable"  value="<c:out value="${repTable}"/>"  style="width:240;"/>
					<select name="phyTable1" style="width:240;display:none;">
			            	<c:forEach var="tabName" items="${tableName}">
			            		<c:if test="${repTable == tabName}">
				            	   	<option value="<c:out value="${tabName}"/>" selected >
	       								<c:out value="${tabName}"/>
									</option>
								</c:if>
								<c:if test="${repTable != tabName}">
				            	   	<option value="<c:out value="${tabName}"/>" >
	       								<c:out value="${tabName}"/>
									</option>
								</c:if>
							</c:forEach>
			            </select>
					</td>
				</tr>
				<tr>
					<td class="TdBGColor1" width=80 align="right"><fmt:message
						key="reportdefine.report.precision" /></td>
					<td class="TdBGColor2"><html:select property="precision"
						style="width:240;">
						<html:option value="10000">
							<fmt:message key="reportdefine.report.precision7" />
						</html:option>
						<html:option value="1">
							<fmt:message key="reportdefine.report.precision3" />
						</html:option>
						<html:option value="100">
							<fmt:message key="reportdefine.report.precision5" />
						</html:option>
						<html:option value="0">
							<fmt:message key="reportdefine.report.precision0" />
						</html:option>
					</html:select></td>
				</tr>
				<tr>
					<td class="TdBGColor1" width=80 align="right"><fmt:message key="view.sfzl"/></td>
					<td class="TdBGColor2"><html:select property="isIncrement"
						style="width:240;">
						<html:option value="0">
							<fmt:message key="view.zl"/>
						</html:option>
						<html:option value="1">
							<fmt:message key="view.ql"/>
						</html:option>
					</html:select></td>
				</tr>
				<tr>
					<td class="TdBGColor1" width=80 align="right"><fmt:message
						key="reportdefine.report.description" /></td>
					<td class="TdBGColor2"><html:text property="description"
						style="width:240;" /></td>
				</tr>

				<c:out value="${pageButtons}" escapeXml="false" />
				<tr>
					<td class="FormBottom" colspan="2" height="17"></td>
				</tr>
			</table>
			<input type="hidden" name="flag" value="<c:out value="${flag }"/>"/>
		</html:form>
		
		</td>
	</tr>
</table>
		
<script language=javascript>
	var f=document.reportForm	
	
	var bCancel = false	
		
	function validate(){
	var http_request = false;	
	var flag = f.flag.value;
	var code = document.reportForm.code.value;
	
		if(bCancel==true){
			return true
		}
		if(f.name.value==""){
		alert("<fmt:message key="js.validate.reportname"/>");
			f.name.focus();
			return false;
		}
		if(f.code.value=="" || f.code.value.length>40){
			alert("<fmt:message key="js.validate.code"/>");
			f.code.focus();
			return false;
		}
		if(f.rol.value==""){
			alert("<fmt:message key="js.validate.rol"/>");
			f.rol.focus();
			return false;
		}else{
			if( isNaN(f.rol.value)){
				alert("<fmt:message key="js.validate.rol"/>");
				f.rol.focus();
				return false;
			}					
		}
		
		var phytable = $("input[name='phyTable']").val();
		if(phytable == ""){
			alert("<fmt:message key="srcdata.manager.tablename.isnull"/>"); 
			return false;
		}
		var tableflag = false;
		$("select[name='phyTable1']").each(function(i){
			if(phytable == this.value){
				tableflag = true;
				return false;
			}
		});
		if(!tableflag){
			alert("<fmt:message key="srcdata.manager.tablename.exist"/>"); 
			return false;
		}
		checked = false; 

		for (i = 0; i < f.frequency.length; i++){ 
			if (f.frequency[i].checked == true) { 
				checked = true; 
				break; 
			} 
		} 
		if (!checked){ 
			alert("<fmt:message key="js.validate.frequency"/>"); 
			return false; 
		} 
					
		checked = false; 

		for (i = 0; i < f.organType.length; i++){ 
			if (f.organType[i].checked == true) { 
				checked = true; 
				break; 
			} 
		} 
		if (!checked){ 
			alert("<fmt:message key="js.validate.organtype"/>"); 
			return false; 
		}					
					
		if(f.endDate.value<f.beginDate.value){
			alert("<fmt:message key="js.validate.date2"/>");
			return false;
		}
		if(flag == "1"){
			if(code!=""){
				send_request('reportAction.do?method=checkReportCode&reportCode='+code);
				}
				return false;
			}
		return true;
	}
	
				
	function cancel(){	
		f.action = "<c:url value="/reportAction.do?method=list"/>&param=1";
		f.submit();
	}
	
	function send_request(url){
		http_request = false;
		if(window.XMLHttpRequest) { 
			http_request = new XMLHttpRequest();
			if (http_request.overrideMimeType) {
				http_request.overrideMimeType("text/xml");
			}
		}
		else if (window.ActiveXObject) { 
			try {
				http_request = new ActiveXObject("Msxml2.XMLHTTP");
			} catch (e) {
				try {
					http_request = new ActiveXObject("Microsoft.XMLHTTP");
				} catch (e) {}
			}
		}
		if(!http_request){
			alert("http_request is ERROR!");
			return false;
		}
		http_request.onreadystatechange = processRequest;
		http_request.open("POST", url, true);
		http_request.send(null);
	}
	function processRequest() {      
        if (http_request.readyState == 4) {
            if (http_request.status == 200) {
                var is = http_request.responseText;
                
                if(is == "true"){			
						alert("<fmt:message key="report.code.exist"/>");
										
						return false;	
                	}else{
                		f.submit();
                	}
                }
   		}
    }
	    


		
	</script>
	
	
</body>
	
