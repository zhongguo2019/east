<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>

<c:set var="saveReportDataURL">
	<c:out value="${hostPrefix}" /><c:url value='/reportView.do?method=saveReportDataAJAX' />
</c:set>
<c:set var="getBuildFormulaURL">
	<c:out value="${hostPrefix}" /><c:url value='/reportView.do?method=getBuildFormulaAJAX' />
</c:set>
<c:set var="buildReportAsynURL">
	<c:out value="${hostPrefix}" /><c:url value='/reportView.do?method=saveAndBuildReportAJAX' />
</c:set>
<c:set var="getBuildFormulaAsynURL">
	<c:out value="${hostPrefix}" /><c:url value='/reportView.do?method=getBuildFormulaLeftExpAJAX' />
</c:set>
<c:set var="getReverseQueryFormulaURL">
	<c:out value="${hostPrefix}" /><c:url value='/reportView.do?method=reverseQueryFormulaAJAXdrill' />
</c:set>
<%if(request.getAttribute("carryOk")!=null){
	String str = (String)request.getAttribute("carryOk"); 
	if(str.equals("1")){%>
	<script type="text/javascript">
	<!--
		alert("<fmt:message key="reportView.carryOK"/>");
	//-->
	</script>
	<%
	}
	}%>
<html>
<head>
<title><fmt:message key="webapp.prefix" /></title>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
<link rel="stylesheet" href="<c:url value='${cssPath}/css.css" type="text/css'/>"/>
<script type="text/javascript" src="<c:url value='/scripts/global.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/prototype.js'/>"></script>
</head>

<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0"
	bgcolor="#EEEEEE" style="overflow-x:hidden;">
 <input type="hidden" id="repFormularNoBlc" value=""/>
 <table border="0" cellpadding="0" cellspacing="0" width="100%"
	height="100%">
	<tr>
		<td width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" width="33" height="16"></td>
          <td>
				<p style="margin-top: 3"><font class=b><b>
				<c:if test="${canEdit == '1'}">
				<fmt:message	key="navigation.rportfile.edit" />
				</c:if>
				<c:if test="${canEdit == '0'}">
				<fmt:message	key="navigation.rportfile.view" />
				</c:if></b></font></p>
				</td>
				<td></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td align="left" bgcolor="#EEEEEE">
		<p style="margin-right: 30; margin-top: 8; margin-bottom: 8">
		&nbsp;&nbsp;
		<c:if test="${showret == 'no'}">
		    <a href="#" class="bb" onClick="window.close();">
		      <font	style=font-size:13px>
		          <b>&lt;&lt;<fmt:message key="button.close" /></b>
		      </font> 
		    </a>&nbsp;&nbsp;
		</c:if>
		<c:if test="${showret != 'no'}">
			<c:if test="${canEdit == '1'}">
			    <c:set var="method" value="enterInputSearch" />
			</c:if>
			<c:if test="${canEdit == '0'}">
			    <c:set var="method" value="enterViewSearch" />
			</c:if>
			<a href="reportView.do?method=<c:out value='${method}'/>&dataDate=<c:out value='${reportViewForm.dataDate}'/>&organId=<c:out
			 value='${reportViewForm.organId}'/>&reportType=<c:out value='${reportViewForm.reportType}'/>&reportId=<c:out
			  value='${reportViewForm.reportId}'/>"	class="bb" id='c1'> 
			<font style=font-size:13px><b>&lt;&lt;<fmt:message key="button.back" /></b></font> 
			</a>&nbsp;&nbsp;
		</c:if>

		<c:if test="${canEdit == '1' && formatNull == '0'}">
			<c:if test="${saveupdate == 'yes'}">
				<a href="#" class="bb" onClick="SaveReport();"> <font
					style=font-size:13px><b>&lt;&lt;<fmt:message
					key="reportview.button.saveReport" /></b></font> </a>&nbsp;&nbsp;
			</c:if><c:if test="${saveall == 'yes'}">
				<a href="#" class="bb" onClick="SaveAll();"> <font
					style=font-size:13px><b>&lt;&lt;<fmt:message
					key="reportview.button.saveall" /></b></font> </a>&nbsp;&nbsp;
			</c:if><c:if test="${save == 'yes'}">
				<a href="#" id="saveData" class="bb" onClick="SaveAll();"> <font
					style=font-size:13px><b>&lt;&lt;<fmt:message
					key="button.save" /></b></font> </a>&nbsp;&nbsp;
			</c:if><c:if test="${clearBuildedData == 'yes'}">
				<a href="#" class="bb" onClick="clearBuildedData()"> <font
					style=font-size:13px><b>&lt;&lt;<fmt:message
					key="reportview.button.clearbuildeddata" /></b></font> </a>&nbsp;&nbsp;
			</c:if><c:if test="${isFlowTable == 'true'}">
				<a href="#" class="bb" onClick="addNewRow()"> <font
						style=font-size:13px><b>&lt;&lt;<fmt:message
						key="reportview.button.appendNewLine" /></b></font> </a>&nbsp;&nbsp;
						
				<a href="#" class="bb" onClick="addMoreNewRow()"> <font
						style=font-size:13px><b>&lt;&lt;<fmt:message
						key="reportview.button.appendMoreNewLine" /></b></font> </a>&nbsp;&nbsp;						
			</c:if>
		
	    </c:if>
		<c:if test="${showchart == '1'}">
		    <a href="#" class="bb" onClick="ShowChart();"> <font
					style=font-size:13px><b>&lt;&lt;<fmt:message
					key="reportchart.button.reportChart" /></b></font> 
		    </a>&nbsp;&nbsp;
		</c:if>
		<a href="#" class="bb" onClick="PreviewReport();"> <font
			style=font-size:13px><b>&lt;&lt;<fmt:message
			key="reportview.button.previewReport" /></b></font> </a>&nbsp;&nbsp;
		<a href="#" class="bb" onClick="SaveToExcel();"> <font
			style=font-size:13px><b>&lt;&lt;<fmt:message
			key="reportview.button.saveexcel" /></b></font> </a>&nbsp;&nbsp;
		<c:if test="${buildasinput == 'yes'}">
		    <a href="#" class="bb" onClick="buildReport();"> 
		         <font style=font-size:13px><b>&lt;&lt;<fmt:message
		          key="reportview.button.buildreport" /></b></font> </a>&nbsp;&nbsp;
		</c:if>
		<!-- zhaoyi add  by 20070321 --> 
		<c:if test="${needCarry == 'yes' }">
		    <a href="#" class="bb" onClick="dataCarry();"> 
		         <font style=font-size:13px><b>&lt;&lt;<fmt:message
		          key="reportview.button.datacarry" /></b></font> </a>&nbsp;&nbsp;
		</c:if>
		
		
		<div style="z-index:60" id="loadingDiv" align="center"><img src="<c:url value='/images/loading_1.gif'/>" align="middle"/>&nbsp;<span id="loadingMsg"><fmt:message key="reportview.msg.loadingmsg"/></span></div>  
		  
		</td>
	</tr>
	<tr>
		<td width="100%" bgcolor="#EEEEEE" valign="top">		
            <c:if test="${canEdit == '1' && formatNull == '0'}">
            <html:form action="reportView.do?method=enterInputSearch" method="post"	enctype="multipart/form-data" styleId="reportViewForm" >
				<input type="hidden" name="organId" value="<c:out value='${reportViewForm.organId}'/>" /> 
				<input type="hidden" name="reportId" value="<c:out value='${reportViewForm.reportId}'/>" /> 
				<input type="hidden" name="dataDate" value="<c:out value='${reportViewForm.dataDate}'/>" /> 
				<input type="hidden" name="frequency" value="<c:out value="${reportViewForm.frequency}"/>" /> 
				<input type="hidden" name="dellog" value="<c:out value='${dellog}'/>" />
				<input type="hidden" name="cellRowCol" value="" />
				<input type="hidden" name="cellFormula" value="" />
				<input type="hidden" name="cellValue" value="" />
				<input type="hidden" name="sumType" value="" />
				<c:if test="${outerdatasource == 'yes'}">
					&nbsp;&nbsp;&nbsp;&nbsp;<fmt:message key="datagather.label.txtFile" /><html:file styleClass="file" property="importFile" style="width:450" />&nbsp;&nbsp;<input type="button" value="<fmt:message key="button.copy"/>" style="width:80;height:22;" onclick="copyData();"><br>
				</c:if>
				<c:if test="${outerxlsdatasource == 'yes'}">
					<fmt:message key="datagather.label.xlsFile" /><html:file styleClass="file" property="importXlsFile" style="width:450" />&nbsp;&nbsp;<input type="button" value="<fmt:message key="button.copy"/>" style="width:80;height:22;" onclick="copyXlsData();">
				</c:if>
			</html:form>
            </c:if>
			<div id="divReport">
			<table width="100%" border="0" cellspacing="0">
				<tr>
					<c:set var="GRHeight" value="430" /><c:if test="${showret == 'no'}"><c:set var="GRHeight" value="520" /></c:if>
					<td><object id="GoldReport1" height="<c:out value='${GRHeight}'/>" width="100%"
						classid="clsid:D198BA37-8E16-4590-8311-46C58A1790B4"
						CODEBASE="<c:url value="common/GoldReport/"/><c:out value="${goldReportNamenVervion}"/>">
					</object></td>
				</tr>
			</table>
			</div>
		    <br>
		</td>
	</tr>
</table>

<div id="bf" style="position:relative;width:300px;heigh:200px;"></div>

</body>

<c:if test="${canEdit == '1' && formatNull == '0'}">
<SCRIPT LANGUAGE=javascript FOR=GoldReport1 EVENT=OnEndEdit>
	row = GoldReport1.GetCurRow();
	col = GoldReport1.GetCurCol();
	addValue(row,col);
</SCRIPT></c:if>

<SCRIPT LANGUAGE="JScript">
	var GoldReport1=document.getElementById("GoldReport1");
	var organId = "<c:out value='${reportViewForm.organId}'/>"
	var reportId = "<c:out value='${reportViewForm.reportId}'/>"
	var reportCode = "<c:out value="${report.code}"/>"
	var dataDate = "<c:out value='${reportViewForm.dataDate}'/>"
	var frequency = "<c:out value="${reportViewForm.frequency}"/>"
	var sumType = '<c:out value="${reportViewForm.sumType}"/>';
	var adhocReportId = "<c:out value="${adhocReport}"/>";
	var unitCode = "<c:out value='${reportViewForm.unitCode}'/>";
	
	LoadHttp();
	GoldReport1.EnterDirection=0
	
	var ei=document.getElementById("bf")
	var buildFormula=new Array()
	var theKey=''
	
	var submitting=false
	
	var colorSep1 = "@#!@#!";
	var colorSep2 = "@@";
	var colorSep3 = "##";
	
	var flowFlag = false;
	var flowReportID = "";
	var flowRowBase = 0;
	var flowColArray = new Array();
	var flowLimitRow = 0;
	
	function LoadHttp(){
		//GoldReport1.LoadFormatFile("<c:out value="${hostPrefix}"/><c:url value='/reportView.do?method=getReportXML&organId='/>"+organId+"&reportId="+reportId+"&dataDate="+dataDate+"&frequency="+frequency+"&sumType="+sumType);
		//use ajax for add custom color to cell, modify by safe at 2007.12.12
		var ajaxOption = {method:'get',onSuccess:loadFormatResponse};
		var url = "<c:out value='${hostPrefix}'/><c:url value='/reportView.do?method=getReportXML&organId='/>"+organId+"&reportId="+reportId+"&dataDate="+dataDate+"&frequency="+frequency+"&sumType="+sumType+"&unitCode="+unitCode+"&addColorFlag=1";
		var doAjax = new Ajax.Request(url,ajaxOption);
	}
	
	function loadFormatResponse(ajaxRequest) {
	 	var reportXML = ajaxRequest.responseText;
	 	var xmlUnit = reportXML.split(colorSep1);
	 	if (xmlUnit.length == 1) {
	 		GoldReport1.LoadFormat(reportXML);
	 	}
	 	else {
	 		GoldReport1.LoadFormat(xmlUnit[0]);
	 		setCellColor(xmlUnit[1]);
	 		GoldReport1.Refresh();
	 	}
	 	document.all.loadingDiv.style.display = "none";
	 	setFlowFlag();
	}
	
	function setCellColor(colorStr) {
		var colorInfoArray = colorStr.split(colorSep2);
		var colorInfo;
		var row;
		var col;
		
		for (var i = 0 ; i < colorInfoArray.length ; i++) {
			colorInfo = colorInfoArray[i].split(colorSep3);
			row = parseInt(colorInfo[0]);
			col = parseInt(colorInfo[1]);
			GoldReport1.SetItemFgColour(row,col,parseInt(colorInfo[2]));
			GoldReport1.SetItemBkColour(row,col,parseInt(colorInfo[3]));
		}
	}
	
	function ShowChart(){
		var d=dataDate.substring(0,6)+'00' //frequency
		var url="<c:url
		value='chartlist.do?method=showFrame&listtag=1&ch_format=${ch_format}'/>&organId="+organId+"&dataDate="+d
		window.open(url,'chartListForView','');
	}
	
	function PrintReport(){
		GoldReport1.Print();
	}
	function PreviewReport(){
		GoldReport1.PrintView();
	}
	function SetProtect(){
		GoldReport1.SetProtect(true);
		GoldReport1.Refresh();
	}
	function SetReadOnly(){
		GoldReport1.SetReadOnly(true);
		GoldReport1.Refresh();
	}
	function SaveToExcel(){
		var suc= GoldReport1.SaveExcelFile("");
		if(suc == true){
			doTimer();
		}
	}
	var the_count=0;
	var the_timeout;
	function doTimer(){
		if(the_count!=12){
			var printOver = GoldReport1.GetExcelThreadState();
			if(printOver==2){
				alert("<fmt:message key='reportSaveExcel.err'/>");
			}else if(printOver==1){
				the_timeout = setTimeout("doTimer();", 1000);
				the_count++;
			}else if(printOver==0){
				alert("<fmt:message key='reportSaveExcel.ok'/>");
			}
		}
	}
<c:if test="${canEdit == '1' && formatNull == '0'}">
	/************************************/
	var f=document.reportViewForm	
	
	function verifyLeave() {
		event.returnValue = "<fmt:message key="reportview.warn.beforeunload"/>"
	}
	
	function attachOnUnload() {
		window.onbeforeunload = verifyLeave
	}
	
	function detachOnUnload() {
		window.onbeforeunload = null
	}
	
	var sc_reg=/^\d+\.\w+\.\d+(\s*\/\s*100)?$/i;
	
	function addValue(row,col){
		var strFormula = GoldReport1.GetFormulaEx(row,col,1);
		var itemText = GoldReport1.GetItemText(row,col);
		var rc = GoldReport1.GetRowCount;
		
		//add by safe at 2008.3.21
		if (flowFlag){	
			var rowUnit = row - flowRowBase + 1;
			var colUnit = getFlowTargetIndex(col);
			
			if (colUnit == 'undefined' || isNaN(colUnit)){
				return;
			}
			
			strFormula = flowReportID + "." + rowUnit + "." + colUnit;
			
			if (rowUnit < 1 || (rc - row) < flowLimitRow){
				return;
			}
			
			if (itemText.length > <c:out value='${flowMaxLength}'/>){
				return "<fmt:message key='reportview.alert.maxlength'/>[" + row + " , " + col + "]";
			}
		}
		//alert(strFormula + "||" + itemText);
		
		if(strFormula && strFormula.length>0){			
			var sf=strFormula.substring(1)
			if(sf.length>13&&sf.substring(0,4)=='org:'){
				//sf=sf.substring(13)
				sf = sf.substring(sf.indexOf('$') + 1);
			}
			
			if(!(sf.match(sc_reg) || sf.substring(0,2).toLowerCase() == 'if')){  //modify by safe at 2007.11.27
				return
			}
			
			if(f.cellFormula.value==''){
				attachOnUnload()
			}
			if(!GoldReport1.GetIsMoney(row,col)){
				f.cellFormula.value += strFormula+"N;";
			}else{
				f.cellFormula.value += strFormula+";";
			}
			f.cellValue.value += itemText+"~@";
		}
	}
	
	function SaveReport(){
		if(submitting){
			return
		}
		if(f.cellFormula.value.length==0){
			alert("<fmt:message key="reportfile.saveresult.nochange"/>");
		}else{
			submitting=true
			doSave()
		}
	}
	function SaveAll(){
		if(submitting){
			return
		}
		submitting=true
		var rc=GoldReport1.GetRowCount
		var cc=GoldReport1.GetColumnCount
		
		f.cellFormula.value=''
		f.cellValue.value=''
		
		var rv;
		for(var r=0;r<rc;r++){
			for(var c=0;c<cc;c++){
				rv = addValue(r,c)
				if (rv != null && rv != ''){
					alert(rv);
					submitting=false;
					return;
				}
			}
		}
		doSave()
	}
	var saveHref = '';
	function doSave(){
		var fl=f.cellFormula.value.length
		var vl=f.cellValue.value.length
		var saveBtn = document.getElementById("saveData");
		saveHref = saveBtn.getAttribute("href");
		saveBtn.setAttribute("disabled", "disabled");
		saveBtn.setAttribute("href", "#");
		if(fl+vl<500){
			saveReportData()
		}else{
			f.action="reportView.do?method=saveReportFile&showret=<c:out value="${showret}"/>&unlockbutton=<c:out value='${unlockbutton}'/>&dellog=<c:out value="${ dellog }"/>";
			detachOnUnload()
			f.submit()
		}
	}
	
	function validateFile(value,fileExc){
		if(value.length==0){
			alert("<fmt:message key="importexport.file.invalid"/>"+fileExc);
		    return false;
		}else{
			var idx = value.lastIndexOf(".");
			if(idx < 0)	{
				alert("<fmt:message key="importexport.file.invalid"/>"+fileExc);
				return false;
			}else{
				var importFileExc =  value.substr(idx+1);
				if(importFileExc != fileExc){
					alert("<fmt:message key="importexport.file.invalid"/>"+fileExc);
					return false;
				}
			}
			return true;
		}
	}
	
	function copyData(){
		var value = document.reportViewForm.importFile.value;
		if (validateFile(value,'txt')) {
			document.reportViewForm.action = "reportView.do?method=copyData&showret=<c:out value='${showret}'/>";
			document.reportViewForm.submit();
		}
	}	
	function copyXlsData(){
		var value = document.reportViewForm.importXlsFile.value;
		if (validateFile(value,'xls')) {
			document.reportViewForm.action = "reportView.do?method=copyXlsData&showret=<c:out value='${showret}'/>";
			document.reportViewForm.submit();
		}
	}
	
	/* zhaoyi add by 20070321*/
	function dataCarry(){
		f.action = "reportView.do?method=carry&showret=<c:out value='${showret}'/>";
		f.submit();
	}
	
	function buildReport(){
		if(submitting){
			return
		}
		var fl=f.cellFormula.value.length
		var vl=f.cellValue.value.length
		if(fl+vl<500){
			submitting=true
			var url = "<c:out value="${buildReportAsynURL}"/>&organId="+f.organId.value
				+"&reportId="+f.reportId.value+"&dataDate="+f.dataDate.value+"&frequency="+f.frequency.value
				+"&cellFormula="+f.cellFormula.value+"&cellValue="+f.cellValue.value+"&dellog=<c:out value='${dellog}'/>";
			var callback = proBuildResponse;
			executeXhr(callback, url);
		}else{
			
			f.action="reportView.do?method=saveAndBuildReport&showret=<c:out
			 value='${showret}'/>&unlockbutton=<c:out value='${unlockbutton}'/>";
			detachOnUnload()
			f.submit()
		}
	}

<c:if test="${precheck == 'yes'}">
	function precheck(){
		var aRepId="<c:out value='${actReportId}'/>"
		if(aRepId==''||isNaN(aRepId)){
			alert('<fmt:message key="reportview.assrepname.error"/>')
			return
		}
		var urlparam="&selectedReport="+aRepId+"&selectedOrg=" + organId
			 + "&selectedDate=" + dataDate;
		var url = "blanAdjust.do?method=preCheck" + urlparam;
		var ic=window.open(url,"repviewic","status=no,toolbar=no,"
				+"menubar=no,location=no,resizable=yes,scrollbars=yes");
		ic.focus();
	}
</c:if>

<c:if test="${clearBuildedData == 'yes'}">
	var buildExpMap=new Object()
	buildExpMap.setted=false
	
	function clearBuildedData(){
		if(buildExpMap.setted){
			doClearBuildedData()
			return
		}		
		var aRepId="<c:out value='${actReportId}'/>"
		if(aRepId==''||isNaN(aRepId)){
			alert('<fmt:message key="reportview.assrepname.error"/>')
			return
		}
		var url = "<c:out value="${getBuildFormulaAsynURL}"/>&reportId="+aRepId + "&reportDate=" + dataDate;
		executeXhr(proClearBuildedDataResponse, url);
	}
	
	function proClearBuildedDataResponse(){
		if (req.readyState == 4&&req.status == 200){
			var res=req.responseText
			var exps=res.split('#')
			for(var i=0;i<exps.length;i++){
				buildExpMap[exps[i]]=true
			}
			buildExpMap.setted=true
			doClearBuildedData()
		}
	}
	
	function doClearBuildedData(){
		var rc=GoldReport1.GetRowCount
		var cc=GoldReport1.GetColumnCount
		for(var r=1;r<rc;r++){
			for(var c=1;c<cc;c++){
				var outerFormula = GoldReport1.GetFormulaEx(r,c,1);	
				if(outerFormula&&outerFormula.length>0){
					if(buildExpMap[outerFormula.substring(1)]){
						GoldReport1.SetItemText(r,c,'0.00')
						addValue(r,c)
					}
				}
			}
		}
	}	
</c:if>

<c:if test="${unlockbutton == 'yes'}">
	function unlockallcell(){
		var rc=GoldReport1.GetRowCount
		var cc=GoldReport1.GetColumnCount
		for(var r=2;r<rc;r++){
			for(var c=2;c<cc;c++){
				if(GoldReport1.IsLocked(r, c)){
					var outerFormula = GoldReport1.GetFormulaEx(r,c,1);	
					if(outerFormula&&outerFormula.length>0){
						var innerFormula = GoldReport1.GetFormulaEx(r,c,0);
						if(innerFormula&&innerFormula.length>0){
							;
						}else{
							GoldReport1.SetLocked(r, c, false);
							GoldReport1.SetItemBkColour(r,c,0x9CFFFF);
						}
					}
				}
			}
		}
	}
</c:if>

	/*******/

	function saveReportData(){
		var cf=f.cellFormula.value
		if(encodeURIComponent){
			cf=encodeURIComponent(cf)
		}
		var cv=f.cellValue.value
		if(encodeURIComponent){
			cv=encodeURIComponent(cv)
		}
		var url = "<c:out value="${saveReportDataURL}"/>&organId="+f.organId.value
			+"&reportId="+f.reportId.value+"&dataDate="+f.dataDate.value+"&frequency="+f.frequency.value
			+"&cellFormula="+cf+"&cellValue="
				+cv+"&dellog=<c:out value='${dellog}'/>";
				
		executeXhr(proSaveRDResponse, url);
	}

	function proSaveRDResponse(){
		if (req.readyState == 4){
			if(req.status == 200){
				var res=req.responseText
				if(res=='ok'){
					var saveBtn = document.getElementById("saveData");
					if (saveBtn.disabled){
						saveBtn.disabled = false;
					}
					f.cellFormula.value = ''
					f.cellValue.value = ''
					detachOnUnload()
					alert("<fmt:message key="reportfile.saveresult.message"/>")
					submitting=false
					return
				}
			}
			alert("<fmt:message key="reportfile.saveresult.errormsg"/>")
			submitting=false
		}
	}
	
	/************************************/
</c:if>

	var req;
	function executeXhr(callback, url) {
		if (window.XMLHttpRequest){
			req = new XMLHttpRequest();
		}else if (window.ActiveXObject){
			try{
				req = new ActiveXObject("Microsoft.XMLHTTP");
			}catch(e){ 
				req = new ActiveXObject( "Msxml2.XMLHTTP.5.0" );
			}
		}
		if (req){
			req.onreadystatechange = callback;
			req.open("GET", url, true);
			req.send();
		}
	}
	
	function getBuildFormula(cf){	
		var url = "<c:out value="${getBuildFormulaURL}"/>&cellFormula="+cf + "&reportDate=" + dataDate;
		var callback = proGetBFResponse;
		executeXhr(callback, url);
	}
	
	function proGetBFResponse(){
		if (req.readyState == 4){
			if(req.status == 200){
				var res=req.responseText
				buildFormula[theKey]=res
				alert(buildFormula[theKey])
			}
		}
	}
	
<c:if test="${canEdit == '1' && formatNull == '0'}">
	function proBuildResponse(){
		if (req.readyState == 4){
			if(req.status == 200){
				var res=req.responseText
				if(res=='savefailed'){
					alert("<fmt:message key="reportfile.savefail4build"/>");
				}else if(res=='buildfailed'){
					alert("<fmt:message key="reportfile.buildresult.errormsg"/>");
				}else{
					alert("<fmt:message key="reportfile.buildresult.ok"/>");
					LoadHttp()
					SetProtect()
				}
				submitting=false
			}
		}
	}
</c:if>

<c:if test="${formatNull == '1'}">
	alert("<fmt:message key="reportview.alert.noformat"/>");
</c:if><c:if test="${saveResult == '0'}">
	alert("<fmt:message key="reportfile.saveresult.message"/>");
</c:if><c:if test="${saveResult == '-1'}">
	alert("<fmt:message key="reportfile.saveresult.errormsg"/>");
</c:if><c:if test="${brildResult == '-1'}">
	alert("<fmt:message key="reportfile.buildresult.errormsg"/>");
</c:if>
<c:if test="${canEdit == '1'}">
	SetProtect();
</c:if><c:if test="${canEdit != '1' || empty canEdit}">
	SetReadOnly();
</c:if>
	
	function LocateByItem(code){
	
		code=trim(code)
		if(code==''){
			return null
		}
		var pat=eval('/\\d+\\.'+code+'\\.\\d+.*/g')
		var rc=GoldReport1.GetRowCount
		var cc=GoldReport1.GetColumnCount
		for(var r=2;r<rc;r++){
			for(var c=2;c<cc;c++){
				var outerFormula = GoldReport1.GetFormulaEx(r,c,1)
				if(outerFormula&&outerFormula.length>code.length){
					if(outerFormula.substring(1).match(pat)){
						return {row:r,col:c}
					}
				}
			}
		}
		return null
	}
	
	function findRowCol(){
		var f_exp=document.getElementById("f_exp")
		var location=LocateByItem(f_exp.value)
		if(location){
			GoldReport1.SetSelectCells(location.row,location.col,location.row,location.col)
			//GoldReport1.SetSelectCells(location.row,location.col,0,0)
		}
	}
	
	

	function viewItemData(){
		//alert(adhocReportId);
		//alert(reportId);
		var repId = adhocReportId.split(",");
		var isAdhoc = "false";
		for(var i = 0; i < repId.length; i++) {
			if(repId[i] == reportId) {
				isAdhoc = "true";
			}
		}
		var selectedRange=GoldReport1.GetSelectedRange()
		var ran=selectedRange.split(',')
		var rowFrom=ran[0];
		var colFrom=ran[1];
		var rowTo=ran[2];
		var colTo=ran[3];
		var isInt = (GoldReport1.GetNumberFormat(rowFrom,colFrom) == "####" ? "yes" : "no");
		var isMoney = (GoldReport1.GetIsMoney(rowFrom,colFrom) ? "yes" : "no");
		if (rowFrom != rowTo || colFrom != colTo){
			alert('<fmt:message key="reportview.viewItemData.msg.selectCell" />')
			return
		}
		var strFormula = GoldReport1.GetFormula(rowFrom,colFrom);
		if(strFormula&&strFormula.length>1){
			strFormula=strFormula.substring(1)
		}else{
			return
		}
		var url;
		if(!strFormula.match(/^\d+\.\w+\.\d+(\/100)?$/)){
			//return
		}
		//oldmethod report item data query
		//var urlparam="&organId="+organId+"&date=" + dataDate+"&orgLevel=1&cellFormula=" + strFormula + "&isAdhoc="+isAdhoc + "&isInt="+isInt + "&isMoney="+isMoney;
		//var url = "reportView.do?method=reportItemQuery" + urlparam;
		//newmethod report item data query  
		var urlparam="&organId="+organId+"&date=" + dataDate+"&orgLevel=1&cellFormula=" + strFormula + "&isAdhoc="+isAdhoc + "&isInt="+isInt + "&isMoney="+isMoney;
		var url = "reportView.do?method=reportItemView" + urlparam;
		var ic=window.open(url,"vid","status=no,toolbar=no,"
				+"menubar=no,location=no,resizable=yes,scrollbars=yes");
		ic.window.resizeTo(500, 600)
		ic.focus();
	}
	
	function setFlowFlag(){
		var rc=GoldReport1.GetRowCount;
		var cc=GoldReport1.GetColumnCount;
		var formula;
		for (var i = 0 ; i < rc ; i++){
			for (var j = 0 ; j < cc ; j++){
				formula = GoldReport1.GetFormulaEx(i, j, 1);
				if (formula.indexOf("@") >= 0 && formula.indexOf("(f)") >= 0 && 
					(formula.indexOf("(f)") + 3) != formula.length){
					flowFlag = true;
					setFlowCfgInfo(formula, i, j);
				}
				if (formula != null && formula != ""){
					flowLimitRow = rc - i;
				}
			}
		}
	}
	
	function setFlowCfgInfo(formula, row, col){
		var fUnit = formula.substring(formula.indexOf("(f)") + 3).split(".");
		if (flowReportID == ""){
			flowReportID = "A" + fUnit[0];
		}
		if (flowRowBase == 0){
			flowRowBase = row;
		}
		var colArrLen = flowColArray.length;
		flowColArray[colArrLen] = new Array();
		flowColArray[colArrLen][0] = col;
		flowColArray[colArrLen][1] = parseInt(fUnit[2]);	
	}
	
	function getFlowTargetIndex(col){
		for (var i = 0 ; i < flowColArray.length ; i++){
			if (flowColArray[i][0] == col){
				return "" + flowColArray[i][1];
			}
		}
	}
	
	var preChkWin;
	
	function preCheckData(){
		var rc=GoldReport1.GetRowCount;
		var cc=GoldReport1.GetColumnCount;
		f.cellFormula.value='';
		f.cellValue.value='';
		for(var r=0;r<rc;r++){
			for(var c=0;c<cc;c++){
				addCheckValue(r,c);
			}
		}
		
		winState = "top=" + ((window.screen.height - 600) / 2) + ",left=" + ((window.screen.width - 800) / 2) + ",";
		winState += "height=600,width=800,toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no";
		preChkWin = null;
		preChkWin = window.open("reportView.do?method=enterPreCheck","preCheckWindow",winState);
		submitPreCheckData();
	}
	
	function submitPreCheckData(){
		if (preChkWin.document.readyState == 'complete'){
			preChkWin.window.preCheckForm.organId.value = "<c:out value='${reportViewForm.organId}'/>";
			preChkWin.window.preCheckForm.reportId.value = "<c:out value='${reportViewForm.reportId}'/>";
			preChkWin.window.preCheckForm.dataDate.value = "<c:out value='${reportViewForm.dataDate}'/>";
			preChkWin.window.preCheckForm.cellFormula.value = f.cellFormula.value;
			preChkWin.window.preCheckForm.cellValue.value = f.cellValue.value;
			preChkWin.window.preCheckForm.submit();
		} else {
			setTimeout("submitPreCheckData()",200);
		}
	}
	
	function addCheckValue(row,col){
		var strFormula = GoldReport1.GetFormulaEx(row,col,1);
		var itemText = GoldReport1.GetItemText(row,col);
		var rc = GoldReport1.GetRowCount;
		
		if(strFormula && strFormula.length>0){			
			var sf=strFormula.substring(1)
			if(sf>13&&sf.substring(0,4)=='org:'){
				//sf=sf.substring(13)
				sf = sf.substring(sf.indexOf('$') + 1);
			}
			
			if(f.cellFormula.value==''){
				attachOnUnload()
			}
			if(!GoldReport1.GetIsMoney(row,col)){
				f.cellFormula.value += strFormula+"N;";
			}else{
				f.cellFormula.value += strFormula+";";
			}
			f.cellValue.value += itemText+"~@";
		}
	}
	
	function addNewRow(){
		var rc = GoldReport1.GetRowCount;
		var currRow = GoldReport1.GetCurRow();
		if (rc - currRow < flowLimitRow || currRow < flowRowBase){
			return;
		}
		
		var newRow = currRow + 1;
		GoldReport1.appendNewLine();
	}
	
	function addMoreNewRow(){
		 var num;//??????,????????
		 num = prompt("<fmt:message key='reportview.button.appendMoreNewLinePromptInfo'/>",1);
		//????????????????? 
		if(num == null){
			return false;
		}
		//?????????? 		
		var pattern=/^[0-9]+$/; 
	    flag=pattern.test(num); 
		if(!flag){
		alert("<fmt:message key='reportview.button.pleaseEnterNumber'/>");
		return addMoreNewRow();
		}
		for(var i = 0; i < num;i++){
			addNewRow();
		}		
	}	
	
</SCRIPT>


<SCRIPT LANGUAGE=javascript FOR=GoldReport1 EVENT=ReportFormula>
	nCurRow = GoldReport1.GetCurRow();
	nCurCol = GoldReport1.GetCurCol();
	//ei.innerHTML=nCurRow+'.'+nCurCol+' '+strFormula
	var key=nCurRow+'.'+nCurCol
	if(buildFormula[key]){
		alert(buildFormula[key])
	}else{
		var strFormula = GoldReport1.GetFormula(nCurRow,nCurCol);
		if(strFormula){
			theKey=key
			strFormula=strFormula.substring(1)
			if(strFormula.length>13&&strFormula.substring(0,4)=='org:'){
				//strFormula=strFormula.substring(13)
				sf = sf.substring(sf.indexOf('$') + 1);
			}
			getBuildFormula(strFormula)
		}
	}
</SCRIPT>


<script type="text/javascript">
	// change cell back color
	init();
	function init()
	{
		logger.debug("init page... highlight not balance cell...");
		highlightNoBlcCell();
	}
	
	function highlightNoBlcCell()
	{
		var formulars = "<c:out value="${ param.repFormularNoBlc }"/>";
		if (formulars == null || formulars == '')
		{
			logger.warn("has not found property [repFormularNoBlc] value in request params...");
			logger.debug("try to get value in input element with id [repFormularNoBlc]");
			formulars = document.getElementById("repFormularNoBlc").value;
		}
		if (formulars == null || formulars == '')
		{
			logger.warn("has not found value in input element!!!");
			return;
		}
		else
		{
			var fors = formulars.split(",");
			var length = fors.length;
			var location;
			for (var i = 0; i < length; i++)
			{
				location = findCell(fors[i]);
				if (location == null)
				{
					logger.warn("has not found cell which has the out formular [" + fors[i] + "]");
					continue;
				}
				else
				{
					logger.debug("set backcolor of the cell row ==== " + location.row + ", col ==== " + location.col);
					// SetItemBkColour BF9EA
					GoldReport1.SetItemBkColour(location.row, location.col, 0x6600FF);
				}
			}
		}
	}
	
	function findCell(outFormular){
		if (outFormular == null || outFormular == '')
		{
			logger.warn("no balance formular not found...");
			return null;
		}
		var rc=GoldReport1.GetRowCount
		var cc=GoldReport1.GetColumnCount
		for(var r=2;r<rc;r++){
			for(var c=2;c<cc;c++){
				var outerFormula = GoldReport1.GetFormulaEx(r,c,1)
				if(outerFormula.substring(1) == outFormular)
					return {row:r,col:c};
			}
		}
		return null;
	}
	
	function reverseQueryData()
	{
		logger.debug("enter js method [reverseQueryData]...");
		var curRow = GoldReport1.GetCurRow();
		var curCol =  GoldReport1.GetCurCol();
		logger.debug("the selected cell with row: [" + curRow + "] col:[" + curCol + "]");
		var curCellFormula = GoldReport1.GetFormula(curRow, curCol);
		logger.debug("the formula of the selected cell is [" + curCellFormula + "]...");
		var cf = GoldReport1.GetFormula(curRow,curCol);
		var key=curRow+'.'+curCol;
		//wangxin annotation
		/*if(buildFormula[key]){
			alert(buildFormula[key]);
		}else{*/
		var strFormula = GoldReport1.GetFormula(curRow,curCol);
			theKey=key;
			strFormula=strFormula.substring(1)
			if(strFormula.length>13&&strFormula.substring(0,4)=='org:'){
				//strFormula=strFormula.substring(13)
				sf = sf.substring(sf.indexOf('$') + 1);
			}
			var selectedRange=GoldReport1.GetSelectedRange()
			var ran=selectedRange.split(',')
			var rowFrom=ran[0];
			var colFrom=ran[1];
			var isInt = (GoldReport1.GetNumberFormat(rowFrom,colFrom) == "####" ? "yes" : "no");
			var isMoney = (GoldReport1.GetIsMoney(rowFrom,colFrom) ? "yes" : "no");
				var url = "<c:out value="${ getReverseQueryFormulaURL }"/>&cellFormula="+ strFormula + 
					  "&reportDate=" + dataDate + "&organId=" + organId + 
					  "&reportId=" + reportId + "&frequency=" + frequency+"&curRow="+curRow+"&curCol="+curCol+"&isInt="+isInt+"&isMoney="+isMoney;
			logger.debug("reverse query data with the url [" + url + "]");
	//window.showModalDialog(url,"sjfc","scrollbars=yes;resizable=yes;help=no;status=no;center=yes;dialogLeft=100;dialogTop=100;dialogHeight=700px;dialogWidth=1000px;");
	//var url = "reportView.do?method=reportItemView" + urlparam;
	var ic=window.open(url,"vid","status=no,toolbar=no,"
			+"menubar=no,location=no,resizable=yes,scrollbars=yes");
	ic.window.resizeTo(500, 600)
	ic.focus();
	 
		//}
	}
	
	//export report as pdf
	function SaveToPdf(){
		var url = "<c:out value='${hostPrefix}'/><c:url value='/reportView.do?method=expToPdf&organId='/>"+organId+"&reportId="+reportId+"&dataDate="+dataDate+"&frequency="+frequency+"&sumType="+sumType+"&unitCode="+unitCode+"&addColorFlag=1";
		//alert("0000");
		window.location.href=url;
	}
	
	
</script>
</html>
