<%@ page pageEncoding="UTF-8"%>
<%@ page import="com.krm.ps.sysmanage.usermanage.vo.*"%>
<%@ page import="com.krm.ps.util.SysConfig"%>
<%@ page import="com.krm.ps.util.FuncConfig"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>

<html>
<head>
<!-- reportDataEdit.jsp -->
<title><fmt:message key="webapp.prefix" /></title>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${cssPath}/common.css'/>" /> 
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-win2k-1.css'/>" title="win2k-cold-1" />
<script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar.js'/>"></script> 
<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/lang/zh-cn.js'/>"></script> 
<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-setup.js'/>"></script>
<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/ActiveXTree/ActiveXTree.js'/>"></script>    
<script src="<c:url value='/ps/framework/common/jqueryTab/jquery-1.2.4b.js'/>" type="text/javascript"></script>
<script src="<c:url value='/ps/framework/common/jqueryTab/jquery-1.4.1.min.js'/>"
	type="text/javascript"></script>
<script type="text/javascript" src="<c:url value='/ps/scripts/checkdata/checkfunction.js'/>"></script>


<%-- <script src="<c:url value='/common/jqueryValidata/validator.js' />"
	type="text/javascript"></script> --%>
<c:set  var="filenameUrl" > 
<c:out value='/ps/uitl/krmtree/jqueryValidata/'  /><c:out value='validator_'  /><c:out value='${reportId }'  /><c:out value='.js' />
</c:set>
<script src="<c:url value='${filenameUrl} '/>"
	type="text/javascript"></script>
<style type="text/css">
.table {
	width: 100%;
	padding: 0px;
	margin: 0px;
	font-family: Arial, Tahoma, Verdana, Sans-Serif, 宋体;
	border-left: 1px solid #ADD8E6;
	border-collapse: collapse;
	background-color: #EEEEEE
}
.table th {
	font-size: 12px;
	font-weight: 600;
	color: #303030;
	border-right: 1px solid #ADD8E6;
	border-bottom: 1px solid #ADD8E6;
	border-top: 1px solid #ADD8E6;
	letter-spacing: 2px;
	text-align: left;
	padding: 10px 0px 10px 0px;
	background: url(../images/tablehdbg.png);
	white-space: nowrap;
	text-align: center;
	overflow: hidden;
	
}
.table td {
	border-right: 1px solid #ADD8E6;
	border-bottom: 1px solid #ADD8E6;
	background: #EEEEEE;
	font-size: 12px;
	padding: 3px 3px 3px 6px;
	color: #303030;
	word-break: break-all;
	word-wrap: break-word;
	white-space: normal;
	
}
.table td.color {
	background: #edf7f9;
}
.table td a:link {
	font-weight: 400;
	color: #2259D7;
	text-decoration: none;
	word-break: break-all;
	word-wrap: break-word;
	white-space: normal;
}

.table td a:visited {
	font-weight: 400;
	color: #2259D7;
	text-decoration: none;
	word-break: break-all;
	word-wrap: break-word;
	white-space: normal;
}

.table td a:hover {
	font-weight: 400;
	text-decoration: underline;
	color: #303030;
	word-break: break-all;
	word-wrap: break-word;
	white-space: normal;
}

.table td a:active {
	font-weight: 400;
	text-decoration: none;
	color: #2259D7;
	word-break: break-all;
	word-wrap: break-word;
	white-space: normal;
}

.btn {
	BORDER-RIGHT: #7b9ebd 1px solid;
	PADDING-RIGHT: 2px;
	BORDER-TOP: #7b9ebd 1px solid;
	PADDING-LEFT: 2px;
	FONT-SIZE: 12px;
	FILTER: progid :   DXImageTransform.Microsoft.Gradient (   GradientType
		=   0, StartColorStr =   #ffffff, EndColorStr =   #cecfde );
	BORDER-LEFT: #7b9ebd 1px solid;
	CURSOR: hand;
	COLOR: #0D82AE;
	PADDING-TOP: 2px;
	BORDER-BOTTOM: #7b9ebd 1px solid;
	background-color: #FFFFFF
}
.InputStyle{
background-color:#FF2525
}
</style>
</head>
<script language="JavaScript">
	function findInPage() {
		var textFields=$("input[type=text]");
		var str=$("#chaxundingwei").val();
		for(i=0;i<textFields.length;i++){
 	         var aa=textFields[i].value;
 	         var idstr=textFields[i].id;
 	      if(str==aa&&idstr!="chaxundingwei"){
 	    	 var sizeX=document.body.clientWidth;
 	         var sizeY=document.body.clientHeight;
 	         var adWidth =  textFields[i].offsetLeft;
 	         var adHeight =  textFields[i].offsetTop;
 	         var destX = sizeX - adWidth;
 	         var destY = sizeY - adHeight;
 	         window.scrollBy(destX,destY); 
 	    	 textFields[i].focus();
 	    	 textFields[i].select();
 	    	 break;
 	      }
 	   }
		
	}//
</script>
<body leftmargin="0" topmargin="0" onload="init()">
	<div id="check"
		style="filter: alpha(opacity =   80); visibility: hidden; position: absolute; top: 100; left: 260; z-index: 10; width: 200; height: 74">
		<table WIDTH='100%' BORDER='0' CELLSPACING='0' CELLPADDING='0'>
			<tr>
				<td width='50%' bgcolor='#104A7B'>
					<table WIDTH='100%' height='70' BORDER='0' CELLSPACING='2'
						CELLPADDING='0'>
						<tr>
							<td bgcolor='#EEEEEE' align='center'>&nbsp;&nbsp;&nbsp; <fmt:message
									key="reportview.msg.loadingmsg" /> &nbsp;&nbsp;&nbsp;</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
	<table border="0" cellpadding="0" cellspacing="0" width="2000"
		height="100%">
		<tr>
			 <td width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td>
							<p style="margin-top: 3">
								<font class=b><b> <fmt:message
											key="navigation.dataFill.dataFilledit" /> </b> </font>
							</p>
						</td>
						<td></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td width="100%" bgcolor="#EEEEEE" valign="top"><br>
				<form action="/studentLoan.do?method=save" method="post" name="studentLoanForm" enctype="application/x-www-form-urlencoded">
					<input type="hidden" style="height: 19; name="dataDate"
						value="<c:out value='${dataDate}'/>"> <input id="reportId"
						type="hidden" style="height: 19; id="reportId" name="reportId"
						value="<c:out value='${reportId}'/>"> <input id="organId"
						type="hidden" style="height: 19; name="organId" value="<c:out value='${organId}'/>">
					<input type="hidden" style="height: 19; name="filledFlag" value="${filledFlag}" /><input
						type="hidden"  style="height: 19; name="relateFlag"
						value="<c:out value='${relateFlag}'/>" />
						<input	type="hidden" name="levelFlag"
				value="<c:out value='${levelFlag}'/>" />
					<table id="budgetTbl" width="99%" align="center" cellpadding="1"
						cellspacing="1" class="table">
						<thead>
							<tr height="40px">
								<td class="color" align="center" style="width:60px;" nowrap><fmt:message
										key="studentloan.report.sortnum" /></td>
										<!--填充数据  -->
								<c:forEach var="reportTarget" items="${reportTargetList}">
										<td
											id="titleName<c:out value='${reportTarget.targetOrder}' />"
											class="color" align="center"
											width="<c:out value="${reportTarget.editDate}"/>">
											<c:out value="${reportTarget.targetName}" />
											<input type="hidden" id="reporttargetdatatype_<c:out value="${reportTarget.targetField}" />"
											value="<c:out value="${reportTarget.dataType}" />" >
											<input type="hidden" id="reporttargettargetName_<c:out value="${reportTarget.targetField}" />"
											value="<c:out value="${reportTarget.targetName}" />" >		
											<input type="hidden" id="reporttargetrulesize_<c:out value="${reportTarget.targetField}" />"
											value="<c:out value="${reportTarget.rulesize}" />" >											
										</td>
								</c:forEach>
								<td class="color" align="center" nowrap style="width:200px;"><fmt:message
										key="button.operation" /></td>
							</tr>
						</thead>
					
						<c:if test="${not empty repDataMap}">
							<c:forEach var="itemSort" items="${repItemSort}">
								<tr>
									<td class="TdBGColor2" align="center" style="width:60px;"><c:choose>
											<c:when test="${sortnum==null }">
												<c:set var="sortnum">
													<c:out value="1" />
												</c:set>
											</c:when>
											<c:otherwise>
												<c:set var="sortnum">
													<c:out value="${sortnum+1}" />
												</c:set>
											</c:otherwise>
										</c:choose> <c:out value="${sortnum}" /><input type="hidden"
										name="dataSort" value="<c:out value="${itemSort.key}"/>">
									</td>
									<c:forEach var="reportTarget" items="${reportTargetList}">
											<td class="TdBGColor2" align="center">
											<c:set
													var="itemDataKey">
													<c:out value="${itemSort.key}" />_<c:out
														value="${reportTarget.targetField}" />
												</c:set> 
												<c:set var="targetField">
													<c:out value="${reportTarget.targetField}" />
													
													
												</c:set> 
												 <c:set var="itemvalueIndex">
													_<c:out value="${reportId}" /><c:out value="itemvalue" /><c:out value="${reportTarget.targetField}" />
													
												</c:set> 
														<input type="hidden"
																name="<c:out value="${itemDataKey}"/>_pcheck" align="left"
															id="<c:out value="${itemDataKey}"/>_pcheck"	value="<c:out value="${pcheckMap[itemDataKey]}"/>" />
														<input type="hidden"
														name="<c:out value="${itemDataKey}"/>_rvalue" align="left"
														id="<c:out value="${itemDataKey}"/>_rvalue" value="<c:out value="${rvalueMap[itemDataKey]}"/>" />
														<input type="hidden"
														name="<c:out value="${itemDataKey}"/>_pflag" align="left"
														id="<c:out value="${itemDataKey}"/>_pflag" value="<c:out value="${pflagMap[itemDataKey]}"/>" />
														<input type="hidden"
														name="<c:out value="${itemDataKey}"/>_value" align="left"
														id="<c:out value="${itemDataKey}"/>_value" value="<c:out value="${valueMap[itemDataKey]}"/>" />
														<input type="hidden"
														name="<c:out value="${itemDataKey}"/>_datatype" align="left"
														id="<c:out value="${itemDataKey}"/>_datatype" value="<c:out value="${dtypeMap[itemDataKey]}"/>"/>
														
														<input type="hidden"
														name="<c:out value="${itemSort.key}"/>_jilu"  id="<c:out value="${itemDataKey}"/>_jiluvalue" align="left"
														 value="<c:out value="${itemDataKey}"/>:<c:out value="${repDataMap[itemDataKey]}"/>"/>
												 <input type="hidden"
														name="<c:out value="${itemSort.key}"/>_jiludatatype" id="<c:out value="${itemDataKey}"/>_jiludatatypes" align="left"
														 value="<c:out value="${itemDataKey}"/>_datatype:<c:out value="${dtypeMap[itemDataKey]}"/>"/>
														  <input type="hidden"
														name="<c:out value="${itemSort.key}"/>_jilupflag" align="left"
														 value="<c:out value="${itemDataKey}"/>_pflag:<c:out value="${pflagMap[itemDataKey]}"/>"/>
												 
												  <input type="hidden"
														name="<c:out value="${itemSort.key}"/>_jiludatas" align="left"
														 id="<c:out value="${itemDataKey}"/>_jiludatas" value="<c:out value="${dtypeMap[itemDataKey]}"/>"/>
												 
												 <c:if test="${reportTarget.fieldType==0}">
													<input type="hidden"
														name="<c:out value="${itemDataKey}"/>_id" align="left"
														value="<c:out value="${itemSort.key}"/>" />
													<input id="<c:out value="${itemDataKey}"/>"
														name="<c:out value="${itemDataKey}"/>" align="left"
														style="height: 21;<c:if test="${dtypeMap[itemDataKey]!=null&&dtypeMap[itemDataKey]!=0}">background-color: #FF6666</c:if>" 
														<c:if test="${ruleMap[itemDataKey]!=null}"> dataType="<c:out  value='${itemvalueIndex}' />"  labelText="<c:out value='${reportTarget.targetName}'/>" </c:if>
														type="text"
														size="<c:choose><c:when test='${reportTarget.dataType!=3}'>7</c:when><c:otherwise>12</c:otherwise></c:choose>"
														value="<c:out value="${repDataMap[itemDataKey]}"/>" />
												</c:if> 
												<c:if test="${reportTarget.fieldType==1||reportTarget.fieldType==3}">
													<input type="hidden"
														name="<c:out value="${itemDataKey}"/>_id" align="left"
														value="<c:out value="${itemSort.key}"/>" />
														
													<select id="<c:out value="${itemDataKey}"/>" name="<c:out value="${itemDataKey}"/>"  <c:if test="${ruleMap[itemDataKey]!=null}">  dataType="<c:out  value='${itemvalueIndex}' />"  labelText="<c:out value='${reportTarget.targetName}'/>" </c:if> style="height: 21;width:100px ;<c:if test="${dtypeMap[itemDataKey]!=null&&dtypeMap[itemDataKey]==1}">background-color: #FF6666</c:if>"  >
														<option selected="selected" value="">
															<fmt:message key="system.switch.hint" />
														</option>
														<c:forEach items="${dicMap}" var="d">
															<c:if test="${d.key==reportTarget.pkid}">
															<c:forEach items="${d.value}" var="v" begin="0" varStatus="status">
																	<option value="<c:out value="${v.dicId}" />"
																		<c:if test="${v.dicId==repDataMap[itemDataKey]}" >selected='selected'</c:if>>																		 
																		<c:out value="${v.dicName}" />
																	</option>
																</c:forEach>
															</c:if>
														</c:forEach>
													</select>
													<c:if test="${ruleMap[itemDataKey]==null}">
														<input type="hidden" name="<c:out value="${itemDataKey}"/>" value="<c:out value="${repDataMap[itemDataKey]}"/>">
													</c:if>
												</c:if>
												<c:if test="${reportTarget.fieldType==2}">
													<input type="hidden"
														name="<c:out value="${itemDataKey}"/>_id" align="left"
														value="<c:out value="${itemSort.key}"/>" />
														<input id="<c:out value="${itemDataKey}"/>"  name="<c:out value="${itemDataKey}"/>"   <c:if test="${ruleMap[itemDataKey]!=null}"> dataType="<c:out  value='${itemvalueIndex}' />"  labelText="<c:out value='${reportTarget.targetName}'/>" </c:if>   
														 align="left" style="height: 21;<c:if test="${dtypeMap[itemDataKey]!=null&&dtypeMap[itemDataKey]==1}">background-color: #FF6666</c:if>" readonly="readonly" type="text" size="7" value="<c:out value="${repDataMap[itemDataKey]}"/>" />
													<c:if test="${ruleMap[itemDataKey]!=null}">
														<script type="text/javascript">
															Calendar.setup({
																inputField     :    "<c:out value="${itemDataKey}"/>",  
																ifFormat       :    "%Y%m%d",   
																showsTime      :    false
															});
														</script>
													</c:if>	
													<c:if test="${ruleMap[itemDataKey]==null}">
														<script type="text/javascript">
															Calendar.setup({
																inputField     :    "<c:out value="${itemDataKey}"/>",  
																ifFormat       :    "%Y%m%d",   
																showsTime      :    false
															});
														</script>
													</c:if>														
														
												</c:if>
											</td>
											<c:set var="itemDataKey">
												<c:out value="${itemSort.key}" />_<c:out
													value="${reportTarget.targetField}" />
											</c:set>
											<c:set var="targetField">
												<c:out value="${reportTarget.targetField}" />
											</c:set>
											<input type="hidden" name="<c:out value="${itemDataKey}"/>_id" align="left" value="<c:out value="${itemSort.key}"/>" />
											<input id="<c:out value="${itemDataKey}"/>" type="hidden" name="<c:out value="${itemDataKey}"/>" align="left" 	style="height: 21;" value="<c:out value="${repDataMap[itemDataKey]}"/>" />
									</c:forEach>
									<td class="TdBGColor2" align="center" >
										<input
										type="button"
										value="<fmt:message key='button.save'/>"
										class="btn" alt="<c:out value="${itemSort.key}"/>"
										onclick=" singleUpdate(this,1,'<c:out value='${dataDate}'/>','<c:out value="${itemSort.key}"/>','<c:out value='${reportId}'/>','<c:out value='${organInfo.code}'/>','<c:out value='${organId}'/>','<c:out value='${pager}'/>'); " />
									</td>
								</tr>
								
							</c:forEach>
						</c:if>
					</table>
				</form></td>
		</tr>
	</table>
	<c:forEach items="${ruleMap}" var="rule">
		<div id="hint<c:out value='${rule.key}'/>"
			style="position: absolute; display: none; color: black; background: #f0f0f0; border: 1px solid #979797;">
			<table width="150px" height="50px" cellspacing="0" cellpadding="0"
				border="1px solid #979797;" frame=above>
				<tr>
					<td id="modelThinking"
						style="align: left; padding-left: 10px; padding-right: 10px :     pointer;"><c:out
							value="${rule.value}" /></td>
				</tr>
			</table>
		</div>
		
	</c:forEach>
	<div id="tips"
		style="position: absolute; visibility: hidden; top: 0px; left: 0px; width: 150px; height: 60px; background-color: #d2e8ff; border: 1 solid black;">
	</div>
	<form id="pageForm" name="pageForm" method="post" action="">
		<input type="hidden" name="dataDate"
			value="<c:out value='${dataDate}'/>"> <input type="hidden"
			name="reportId" value="<c:out value='${reportId}'/>"> <input
			type="hidden" name="organId" value="<c:out value='${organId}'/>">
		<input type="hidden" name="refresh" id="refresh" />
	</form>
</body>
<script type="text/javascript">
	        function singleUpdate(obj,val,reportda,dataId,repid,organC,organI,pager) {
	        	var valuestr=document.getElementsByName(dataId+"_jilu");
	        	var datatypestr=document.getElementsByName(dataId+"_jiludatatype");
	        	var pflagstr=document.getElementsByName(dataId+"_jilupflag");
	        	var valueurl="";
	        	var datatypeurl="";
	        	var pflagurl="";
	        	for(i=0;i<valuestr.length;i++){
	      	      	var aa=valuestr[i].value;
	      	      	var arr=aa.split(":");
	      	    	valueurl+=arr[0]+":"+arr[1]+"@";
	      	    	var pcheck=$("#"+arr[0]+"_pcheck").val();  //
	      	    	var tarstr=arr[0].split(":");
	      	    	var feildstr=tarstr[0].split("_");
	      	    	var tardatatype=$("#reporttargetdatatype_"+feildstr[1]).val(); //
					var tarname=$("#reporttargettargetName_"+feildstr[1]).val();
					var tarlength=$("#reporttargetrulesize_"+feildstr[1]).val();//
					if(val==1){
						if(pcheck.indexOf("checkNull")>=0){
							//qu chu kong ge
							if(aa==null||aa.replace(/[ ]/g,"")==""){
								alert('<fmt:message key="symbol.bracket.left"/>'+tarname+'<fmt:message key="symbol.bracket.right"/><fmt:message key="view.bnwk"/>!<fmt:message key="view.cxtx"/>');
								return;
							}
						}
						if(pcheck.indexOf("checkXiaoDeng")>=0){
							if(aa.length>tarlength){
								alert('<fmt:message key="symbol.bracket.left"/>'+tarname+'<fmt:message key="symbol.bracket.right"/><fmt:message key="view.cdbndy"/>'+tarlength+'!<fmt:message key="view.cxtx"/>');
								return;
							}
						}
						if(pcheck.indexOf("checkDengYu")>=0&&aa!=null&&aa!=""){
							if(aa.length!=tarlength){
								alert('<fmt:message key="symbol.bracket.left"/>'+tarname+'<fmt:message key="symbol.bracket.right"/><fmt:message key="view.dcdw"/>'+tarlength+'!<fmt:message key="view.cxtx"/>');
								return;
							}
						}
			//		if(1==tardatatype&&arr[1]!=null&&arr[1]!=""){
			//			var reg = new RegExp("^(-?[0-9]+)(\\.[0-9]+)?$");
			//			 if(!reg.test(arr[1])){
			//				     alert('<fmt:message key="symbol.bracket.left"/>'+tarname+'<fmt:message key="symbol.bracket.right"/><fmt:message key="view.bwszfd"/>!<fmt:message key="view.cxtx"/>');
			//		         return;
			//		     }
			//		}
	        	}
	      	       
	      	   }
	        	if(val==1){
	        	var datasstr=document.getElementsByName(dataId+"_jiludatas");
	        	for(i=0;i<datasstr.length;i++){
	      	      	var aa=datasstr[i].value;
	      	      	if(aa==1){
	      	      	//if(!confirm("gai ye mian you xiao yan wei tong guo de shu ju ,xu yao qiang zhi ti jiao ma ?")){
  	      				alert('<fmt:message key="view.xytjcw"/>');
  	  					return;
  	  				//	}
  	      			tiaochuflag=true;
  	      			break;
	      	      	}
	      	      
	      	   }
	        }
	        	for(i=0;i<datatypestr.length;i++){
	      	         var aa=datatypestr[i].value;
	      	        var arr=aa.split(":");
	      	        datatypeurl+=arr[0]+":"+arr[1]+",";
	      	      
	      	   }
	        	for(i=0;i<pflagstr.length;i++){
	      	      	var aa=pflagstr[i].value;
	      	      var arr=aa.split(":");
	      	      pflagurl+=arr[0]+":"+arr[1]+",";
	      	      
	      	   }
	        	var params={"stflag": val,"reportDate":reportda,"reportId":repid,"organCode":organC,"organId":organI,
	        			 "valueurl":valueurl,"datatypeurl":datatypeurl,"pflagurl":pflagurl,"dataId":dataId ,"pager":pager,"method":"singleSaveOne"};
				$.ajax({ 
					type: "POST", 
					async: false, //ajax
					url: "dataFill.do", 
					data: jQuery.param(params), 
					success: function(result){
						if(result!=null && result!=2 && result!=3 && result!=0){
							var tr=obj.parentNode.parentNode;
							var tbody=tr.parentNode;
							tbody.removeChild(tr);
							alert('<fmt:message key="view.sjtjcg"/>');
							window.location.href=result;
						}else if(result==0){
							alert('<fmt:message key="view.sjbccg"/>');
							window.location.href="dataFill.do?method=getDataDetail&levelFlag=2&levelview=1";
						}else if(result==2){
							alert('<fmt:message key="view.sjbcsb"/>,<fmt:message key="view.qcxpzbsmb"/>');
							window.location.href="reportTargetAction.do?method=enterTargetSend";
						}else if(result==3){
							alert('<fmt:message key="view.sjtjsb"/>');
							window.location.href="dataFill.do?method=getDataDetail&levelFlag=2&levelview=1";
						}
					}
				});
	        }
	        
	        function go() {
	        	var pageNo = $("#pageNo").val();
	        	if(pageNo<1) {
	        		pageNo = 1;
	        	}else if(pageNo><c:out value='${totalPage}'/>){
	        		pageNo = <c:out value='${totalPage}'/>;
	        	}
	    		window.location.href = "dataFill.do?method=editReportDataEntryForDataInput&openmode=_self&levelFlag=<c:out value='${levelFlag}'/>&dataDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>&page=" + pageNo;
	    	}
						 function showDiv(targetField) {
							$("#hint" + "<c:out value='${reportId}'/>_"+targetField).show();
							$("#hint" + "<c:out value='${reportId}'/>_"+targetField).css({
								"top" : rMenu.position.y + "px",
								"left" : rMenu.position.x + "px",
								"display" : "block"
							});
						} 
						
						var rMenu = {
								position : {
									x : "",
									y : ""
								}
						}; 
						
						 $(function() {
								$("input[readonly!='readonly']").each(function(){
											$(this).unbind("mouseover");
											$(this).bind("mouseover",function(event) {
												var e = event || window.event;
									            var scrollX = document.documentElement.scrollLeft || document.body.scrollLeft;
									            var scrollY = document.documentElement.scrollTop || document.body.scrollTop;
									            var x = e.pageX || e.clientX + scrollX;
									            var y = e.pageY || e.clientY + scrollY;
												rMenu.position.x = x;
												rMenu.position.y =  y;
												var strs = $(this).attr("name").split("_");
												var targetField = strs[1];
												showDiv(targetField);
										});
									
										$(this).unbind("mouseout");
										$(this).bind("mouseout",function(e) {
											var strs = $(this).attr("name").split("_");
											var targetField = strs[1];
											$("#hint" +"<c:out value='${reportId}'/>_"+ targetField).hide();
										});
										$(this).blur(function(e){
											var strs=$(this).attr("id");
											var newvalue=$(this).val();
											$("#"+strs+"_jiluvalue").val(strs+":"+newvalue);
											var pflag=$("#"+strs+"_pflag").val();
											var pcheck=$("#"+strs+"_pcheck").val(); 
											var rcontent=$("#"+strs+"_rvalue").val();
											var rvalue=$("#"+strs+"_value").val();  
											//if(pflag.indexOf("2")>=0){
											//	getrvalue(this,pcheck,$(this).val(),rcontent,"<c:out value='${dataDate}'/>" );  
											//}else{
												//var rvalue=$("#"+strs+"_value").val();  
												var bflag=checkAll(pcheck,newvalue,rvalue); 
									      	    var feildstr=strs.split("_");
									      	    var tardatatype=$("#reporttargetdatatype_"+feildstr[1]).val(); 
												var tarname=$("#reporttargettargetName_"+feildstr[1]).val();
												var tarlength=$("#reporttargetrulesize_"+feildstr[1]).val();
												if(bflag ==true){
													if(pcheck.indexOf("checkXiaoDeng")>=0){
														if(newvalue.length>tarlength){
															alert('<fmt:message key="symbol.bracket.left"/>'+tarname+'<fmt:message key="symbol.bracket.right"/><fmt:message key="view.cdbndy"/>'+tarlength+'!<fmt:message key="view.cxtx"/>');
															return;
														}
													}
													if(pcheck.indexOf("checkDengYu")>=0&&newvalue!=null&&newvalue!=""){
														if(newvalue.length!=tarlength){
															alert('<fmt:message key="symbol.bracket.left"/>'+tarname+'<fmt:message key="symbol.bracket.right"/><fmt:message key="view.dcdw"/>'+tarlength+'!<fmt:message key="view.cxtx"/>');
															return;
														}
													}
													if(1==tardatatype&&newvalue!=null&&newvalue!=""){
														var reg = new RegExp("^(-?[0-9]+)(\\.[0-9]+)?$");
														// var zifu = new RegExp("^(-?\\d+)(\\.\\d+)?$");
														//var reg = new RegExp("^(\d*\.)?\d+$");
														 if(!reg.test(newvalue)){
															    alert('<fmt:message key="symbol.bracket.left"/>'+tarname+'<fmt:message key="symbol.bracket.right"/><fmt:message key="view.bwszfd"/>!<fmt:message key="view.cxtx"/>');
													         return;
													     }
													}
													$("#"+strs+"_datatype").val(0);
													$("#"+strs+"_jiludatatypes").val(strs+"_datatype:"+0);
													$("#"+strs+"_jiludatas").val(0);													
													$(this).css("background", "#fff"); 
												}
												if(pflag.indexOf("2")>=0){
													getrvalue(this,pcheck,$(this).val(),rcontent,"<c:out value='${dataDate}'/>",tarname );  
												}
										});
								});
									$("#refresh").unbind("click");
									$("#refresh").bind("click",function(){
										window.location.href= "dataFill.do?method=editReportDataEntryForDataInput&openmode=_self&levelFlag=<c:out value='${levelFlag}'/>&dataDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>&page=<c:out value='${page.pageNo}'/>";
									});
									
								$("select").each(function(){
											$(this).unbind("mouseover");
											$(this).bind("mouseover",function(event) {
												var e = event || window.event;
									            var scrollX = document.documentElement.scrollLeft || document.body.scrollLeft;
									            var scrollY = document.documentElement.scrollTop || document.body.scrollTop;
									            var x = e.pageX || e.clientX + scrollX;
									            var y = e.pageY || e.clientY + scrollY;
												rMenu.position.x = x;
												rMenu.position.y =  y;
												var strs = $(this).attr("name").split("_");
												var targetField = strs[1];
												showDiv(targetField);
											});
											$(this).unbind("mouseout");
											$(this).bind("mouseout",function(e) {
												var strs = $(this).attr("name").split("_");
												var targetField = strs[1];
												$("#hint" +"<c:out value='${reportId}'/>_"+ targetField).hide();
											});
											$(this).blur(function(e){
												var strs=$(this).attr("id");
												var newvalue=$(this).val();
												$("#"+strs+"_jiluvalue").val(strs+":"+newvalue);
												var strs=$(this).attr("id");
												var pflag=$("#"+strs+"_pflag").val();
												var pcheck=$("#"+strs+"_pcheck").val();  //
												var rcontent=$("#"+strs+"_rvalue").val();//
												var rvalue=$("#"+strs+"_value").val();  //
												if(pflag.indexOf("2")>=0){
													getrvalue(this,pcheck,newvalue,rcontent,"<c:out value='${dataDate}'/>" );  //
												}else{
													var rvalue=$("#"+strs+"_value").val();  //
													var bflag=checkAll(pcheck,newvalue,rvalue); //
													if(bflag ==true){
														$("#"+strs+"_datatype").val(0);
														$("#"+strs+"_jiludatatypes").val(strs+"_datatype:"+0);
														$("#"+strs+"_jiludatas").val(0);	
														$(this).css("background", "#fff");
													}
												}
												
											});
									}); 
							
									$("#exportData").unbind("click");
									$("#exportData").bind("click",function() {
										<c:if test="${ empty repDataMap}">
										alert('<fmt:message key="view.myydcsj"/>');
							        	return false;
							        	</c:if>
										//window.location.href = "dataFill.do?method=exportReportData&dataDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>&levelFlag=<c:out value='${levelFlag}'/>";
										var url = "dataFill.do?method=exportReportData&dataDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>&levelFlag=<c:out value='${levelFlag}'/>";
										$.post(url,'',function(data){
											if(data=="ok") {
												window.location.href = "dataFill.do?method=downloadExcel&dataDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>&levelFlag=<c:out value='${levelFlag}'/>";
											}
										});
									});
									
									$("#importData").unbind("click");
									$("#importData").bind("click",function() {
									
										//window.location.href = "dataFill.do?method=exportReportData&dataDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>&levelFlag=<c:out value='${levelFlag}'/>";
										//var url = "standard/datafill/dataUpload.jsp";
										var url="dataFill.do?method=enteryRepUpLoad&dataDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&levelFlag=<c:out value='${levelFlag}'/>";
										window.location.href = url;
									/* 	$.post(url,'',function(data){
											if(data=="ok") {
												window.location.href = "dataFill.do?method=downloadExcel&dataDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>&levelFlag=<c:out value='${levelFlag}'/>";
											}
										}); */
									});
								});
	
						 window.onload=function(){   
							    /****************************  
							     * ：q821424508@sina.com   *  
							     * ：2012-08-20            *  
							     * version：2.1              *  
							     *                          *  
							     ****************************/  
							    document.getElementsByTagName("body")[0].onkeydown =function(){   
							           
							        //huo de shi jian dui xiang   
							        var elem = event.relatedTarget || event.srcElement || event.target ||event.currentTarget;    
							           
							        if(event.keyCode==8){//pan duan an jian wei [backSpace]jian   
							           
							                //huo qu an jian an xia shi fuang biao zuo zhi xiang de [element]   
							                var elem = event.srcElement || event.currentTarget;    
							                   
							                //pan duan shi fou xu yao zu zhi an xia jian pan de shi jian mo ren chuan di   
							                var name = elem.nodeName;   
							                   
							                if(name!='INPUT' && name!='TEXTAREA'){   
							                    return _stopIt(event);   
							                }   
							                var type_e = elem.type.toUpperCase();   
							                if(name=='INPUT' && (type_e!='TEXT' && type_e!='TEXTAREA' && type_e!='PASSWORD' && type_e!='FILE')){   
							                        return _stopIt(event);   
							                }   
							                if(name=='INPUT' && (elem.readOnly==true || elem.disabled ==true)){   
							                        return _stopIt(event);   
							                }   
							            }   
							        };   
							    } ;  
							function _stopIt(e){   
							        if(e.returnValue){   
							            e.returnValue = false ;   
							        }   
							        if(e.preventDefault ){   
							            e.preventDefault();   
							        }                  
							  
							        return false;   
							}  

								

</script>
<script type="text/javascript">

function buttongjcx (){
	var zhi1=$("#zhi").val();
	var openmode1="_self";
	document.studentLoanForm.action='<c:out value="${hostPrefix}" /><c:url value="/dataFill.do" />?method=getReportgjcx&zhi='+zhi1+'&openmode='+openmode1+'&orgid=<c:out value='${organId}'/>';
	document.studentLoanForm.submit();
}
</script>

</html>
