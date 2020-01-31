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
	<c:set  var="filenameUrl" > 
	<c:out value='/common/jqueryValidata/'  /><c:out value='validator_'  /><c:out value='${reportId }'  /><c:out value='.js' />
	</c:set>
	<script src="<c:url value='${filenameUrl} '/>"
	type="text/javascript"></script>


<script type="text/javascript">
			<c:choose>
				<c:when test="${pageFlag==1 }">
				 window.parent.document.getElementById("ppppp").value= "<fmt:message key="navigation.dataEdit.dataEdit1" />";  
				</c:when>
				<c:when test="${pageFlag==2 }">
				 window.parent.document.getElementById("ppppp").value= "<fmt:message key="navigation.dataEdit.dataEdit2" />";  
				</c:when>
				<c:otherwise>
				 window.parent.document.getElementById("ppppp").value= "<fmt:message key="navigation.dataEdit.dataEdit" />";  
				</c:otherwise>
			</c:choose>
</script>
</head>
<body leftmargin="0" topmargin="0" onload="init()">
	<div id="check"
		style="filter: alpha(opacity =   80); visibility: hidden; position: absolute; top: 100; left: 260; z-index: 10; width: 200; height: 74">
		<table WIDTH='100%' BORDER='0' CELLSPACING='0' CELLPADDING='0'>
			<tr>
				<td width='50%'>
					<table WIDTH='100%' height='70' BORDER='0' CELLSPACING='2'
						CELLPADDING='0'>
						<tr>
							<td align='center'>&nbsp;&nbsp;&nbsp; <fmt:message
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
			<td width="100%" valign="top"><br>
				<form action="/studentLoan.do?method=save" method="post"
					name="studentLoanForm" enctype="application/x-www-form-urlencoded">
					<input type="hidden" name="dataDate"
						value="<c:out value='${dataDate}'/>"> <input id="reportId"
						type="hidden" id="reportId" name="reportId"
						value="<c:out value='${reportId}'/>"> <input id="organId"
						type="hidden" name="organId" value="<c:out value='${organId}'/>">
					<input type="hidden" name="filledFlag" value="${filledFlag}" /><input
						type="hidden" name="relateFlag"
						value="<c:out value='${relateFlag}'/>" />
						<input
						type="hidden" name="levelFlag"
				value="<c:out value='${levelFlag}'/>" />
				<input
						type="hidden" name="canEdit"
				value="<c:out value='${canEdit}'/>" />  
				<input
						type="hidden" name="singleOrMore"
				value="<c:out value='${singleOrMore}'/>" />  
						
					<table width="99%" align="center" cellpadding="1" cellspacing="1"
						class="table">
						<tr>
							<td class="TdBGColor1" width="100%" height="20" align="left"
								valign=middle nowrap>&nbsp;&nbsp;<fmt:message
									key="studentloan.report.organ" />:<input type="text"
								style="height: 19; valign: top;"
								value="<c:out value="${organInfo.short_name}"/>"
								name="organName" readonly />&nbsp;&nbsp;<fmt:message
									key="studentloan.report.date" />:<input type="text"
								style="height: 19;" id="date1"
								value="<c:out value='${dataDate}'/>" name="dataDate"
								readonly="readonly" />&nbsp;&nbsp; <input type="button"
								value="<fmt:message
									key="standard.dataFill.exportData" />"
								class="btn"  id="exportData"/>&nbsp;&nbsp; <input type="button"
								value="<fmt:message
									key="standard.dataFill.importData" />"
								class="btn"  id="importData"/></td>
						</tr>
					
					</table>
					<table width="99%" align="center" cellpadding="1" cellspacing="1"
						class="table">
						
						<c:if test="${not empty repDataMap}">
						<c:if test="${page.totalRecord>1 }">
							<tr align="left">
								<td align="left" valign="top" class="TdBGColor2"><fmt:message
									key="dangqian" /> <c:out
										value="${page.pageNo}" /> <fmt:message
									key="ye" /> <a
									href="dataFill.do?method=editReportDataEntry&canEdit=1&levelFlag=<c:out value='${levelFlag}'/>&openmode=_self&dataDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>&page=1">top.index</a>
									<c:if test="${page.pageNo>1 }">
										<a
											href="dataFill.do?method=editReportDataEntry&canEdit=1&levelFlag=<c:out value='${levelFlag}'/>&openmode=_self&dataDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>&page=<c:out value='${page.pageNo-1 }'/>">上一页</a>
									</c:if> <c:choose>
										<c:when test="${page.pageNo>2&&(page.pageNo+2)<=totalPage}">
											<c:set var="begin" value="${page.pageNo-2}" />
											<c:set var="end" value="${page.pageNo+2 }" />
										</c:when>
										<c:when test="${page.pageNo>2&&(page.pageNo+2)>totalPage}">
											<c:choose>
												<c:when test="${totalPage-2<=page.pageNo&&totalPage>5}">
													<c:set var="begin" value="${totalPage-4}" />
													<c:set var="end" value="${totalPage}" />
												</c:when>
												<c:otherwise>
													<c:set var="begin" value="${page.pageNo-2}" />
													<c:set var="end" value="${totalPage}" />
												</c:otherwise>
											</c:choose>
										</c:when>
										<c:when test="${page.pageNo<=2&&(page.pageNo+2)>totalPage}">
											<c:set var="begin" value="1" />
											<c:set var="end" value="${totalPage}" />
										</c:when>
										<c:when test="${page.pageNo<=2&&(page.pageNo+2)<=totalPage}">
											<c:choose>
												<c:when test="${totalPage>5}">
													<c:set var="begin" value="1" />
													<c:set var="end" value="5" />
												</c:when>
												<c:otherwise>
													<c:set var="begin" value="1" />
													<c:set var="end" value="${totalPage}" />
												</c:otherwise>
											</c:choose>
										</c:when>
									</c:choose> <c:forEach var="num" begin="${begin}" end="${end}">
										<c:choose>
											<c:when test="${num==page.pageNo }">
  					[ <c:out value="${num }" /> ]
  				</c:when>
											<c:otherwise>
  					[ <a
													href="dataFill.do?method=editReportDataEntry&canEdit=1&levelFlag=<c:out value='${levelFlag}'/>&openmode=_self&dataDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>&page=<c:out value='${num}'/>"><c:out
														value="${num }" /> </a> ]
  				</c:otherwise>
										</c:choose>
									</c:forEach> <a
									href="dataFill.do?method=editReportDataEntry&canEdit=1&levelFlag=<c:out value='${levelFlag}'/>&openmode=_self&dataDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>&page=<c:out value='${totalPage}'/>">尾页</a>
									<c:if test="${page.pageNo<totalPage}">
										<a
											href="dataFill.do?method=editReportDataEntry&canEdit=1&levelFlag=<c:out value='${levelFlag}'/>&openmode=_self&dataDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>&page=<c:out value='${page.pageNo+1 }'/>">下一页</a>
									</c:if> 总共<c:out value="${totalPage}" /> 页 总共 <c:out
										value="${page.totalRecord }" /> 条记录 跳转至 <input id="pageNo"
									type="text" style="width: 40px;" /> 页 <input type="button"
									value="GO" onclick="go()" class="btn" />
								</td>
							</tr>
						</c:if>
						</c:if>
							<tr>
						<td align="left">
								<fmt:message	key="studentloan.report.report" />:  <input type="text"
								style="height: 19; valign: top;"
								value="<c:out value="${reportName}"/>"
								name="repName" readonly /></td>
						</tr>
					</table>
					<table id="budgetTbl" width="99%" align="center" cellpadding="1"
						cellspacing="1" class="table">
						<thead>
							<tr height="40px">
								<td class="color" align="center" nowrap><fmt:message
										key="studentloan.report.sortnum" /></td>
								<c:forEach var="reportTarget" items="${reportTargetList}">
									<c:if test="${reportTarget.targetField!=80}">
										<td
											id="titleName<c:out value='${reportTarget.targetOrder}' />"
											class="color" align="center"
											width="<c:out value="${reportTarget.editDate}"/>"><c:out
												value="${reportTarget.targetName}" />
										</td>
									</c:if>
								</c:forEach>
								<td class="color" align="center" nowrap><fmt:message
										key="button.operation" /></td>
							</tr>
						</thead>
						<c:if test="${not empty repDataMap}">
							<c:forEach var="itemSort" items="${repItemSort}">
								<tr>
									<td class="TdBGColor2" align="center" width="40px"><c:choose>
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
										<c:if test="${reportTarget.targetField!=80}">
											<td class="TdBGColor2" align="center"><c:set
													var="itemDataKey">
													<c:out value="${itemSort.key}" />_<c:out
														value="${reportTarget.targetField}" />
												</c:set> <c:set var="targetField">
													<c:out value="${reportTarget.targetField}" />
												</c:set> 
												
												<c:set var="itemvalueIndex">
													_<c:out value="${reportId}" /><c:out value="itemvalue" /><c:out value="${reportTarget.targetField}" />
													
												</c:set> 
												<c:if test="${reportTarget.fieldType==0}">
													<input type="hidden"
														name="<c:out value="${itemDataKey}"/>_id" align="left"
														value="<c:out value="${itemSort.key}"/>" />
													<input id="<c:out value="${itemDataKey}"/>"
														name="<c:out value="${itemDataKey}"/>" align="left"
														style="height: 21;<c:if test="${ruleMap[itemDataKey]!=null}">background-color: #FF6666</c:if>"
														<c:if test="${ruleMap[itemDataKey]!=null}"> dataType="<c:out  value='${itemvalueIndex}' />"  labelText="<c:out value='${reportTarget.targetName}'/>" </c:if>
														<c:if test="${ruleMap[itemDataKey]==null}">readonly="readonly"</c:if>
														type="text"
														size="<c:choose><c:when test='${reportTarget.dataType!=3}'>7</c:when><c:otherwise>12</c:otherwise></c:choose>"
														value="<c:out value="${repDataMap[itemDataKey]}"/>" <%-- onmouseover="ontips('<c:out value="${reportRule[targetField]}"/>');" --%>/>
												</c:if> 
												
												<c:if test="${reportTarget.fieldType==1}">
													<input type="hidden"
														name="<c:out value="${itemDataKey}"/>_id" align="left"
														value="<c:out value="${itemSort.key}"/>" />
													<select name="<c:out value="${itemDataKey}"/>"   
													<c:if test="${ruleMap[itemDataKey]!=null}"> dataType="<c:out  value='${itemvalueIndex}' />"  labelText="<c:out value='${reportTarget.targetName}'/>" </c:if>
													 style="height: 21;width:100px ;<c:if test="${ruleMap[itemDataKey]!=null}">background-color: #FF6666</c:if>" <c:if test="${ruleMap[itemDataKey]==null}">disabled="disabled"</c:if> >
														<option selected="selected" value="">
															<fmt:message key="system.switch.hint" />
														</option>
														<c:forEach items="${dicMap}" var="d">
															<c:if test="${d.key==reportTarget.pkid}">
														<c:forEach items="${d.value}" var="v" begin="0" varStatus="status">
																	<option value="<c:out value="${v.dicId}" />"
																		<c:if test="${v.dicId==repDataMap[itemDataKey]}">selected='selected'</c:if>
																		<c:if test="${ruleMap[itemDataKey]!=null && ((repDataMap[itemDataKey]==null || repDataMap[itemDataKey]=='') && status.index == 0) }">selected='selected'</c:if>>
																		<c:out value="${v.dicName}" />
																	</option>
																</c:forEach>
															</c:if>
														</c:forEach> 
														
											<%-- 			<c:forEach items="${dicMap}" var="d">
															<c:if test="${d.key==reportTarget.pkid}">
																<c:forEach items="${d.value}" var="v">
																	<option value="<c:out value="${v.dicId}" />"
																		<c:if test="${v.dicId==repDataMap[itemDataKey]}">selected='selected'</c:if>>
																		<c:out value="${v.dicName}" />
																	</option>
																</c:forEach>
															</c:if>
														</c:forEach> --%>
													</select>
													<c:if test="${ruleMap[itemDataKey]==null}">
														<input type="hidden" name="<c:out value="${itemDataKey}"/>" value="<c:out value="${repDataMap[itemDataKey]}"/>">
													</c:if>
												</c:if>
												<c:if test="${reportTarget.fieldType==2}">
													<input type="hidden"
														name="<c:out value="${itemDataKey}"/>_id" align="left"
														value="<c:out value="${itemSort.key}"/>" />
														<input id="<c:out value="${itemDataKey}"/>"  name="<c:out value="${itemDataKey}"/>" align="left" style="height: 21;<c:if test="${ruleMap[itemDataKey]!=null}">background-color: #FF6666</c:if>" 
														<c:if test="${ruleMap[itemDataKey]!=null}"> dataType="<c:out  value='${itemvalueIndex}' />"  labelText="<c:out value='${reportTarget.targetName}'/>" </c:if>
														 readonly="readonly" type="text" size="10" value="<c:out value="${repDataMap[itemDataKey]}"/>" /><c:if test="${ruleMap[itemDataKey]!=null}">			<script type="text/javascript">
															Calendar.setup({
																inputField     :    "<c:out value="${itemDataKey}"/>",  
																ifFormat       :    "%Y-%m-%d",   
																showsTime      :    false
															});
														</script>	</c:if>													
												</c:if>
											</td>
										</c:if>
										<c:if test="${reportTarget.targetField==80}">
											<c:set var="itemDataKey">
												<c:out value="${itemSort.key}" />_<c:out
													value="${reportTarget.targetField}" />
											</c:set>
											<c:set var="targetField">
												<c:out value="${reportTarget.targetField}" />
											</c:set>
											<input type="hidden"
												name="<c:out value="${itemDataKey}"/>_id" align="left"
												value="<c:out value="${itemSort.key}"/>" />
											<input id="<c:out value="${itemDataKey}"/>" type="hidden"
												name="<c:out value="${itemDataKey}"/>" align="left"
												style="height: 21;"
												value="<c:out value="${repDataMap[itemDataKey]}"/>" />
										</c:if>
									</c:forEach>
									<c:forEach var="reportTarget" items="${reportTargetList2}">
										<c:if test="${reportTarget.targetField!=80}">
											<c:set
													var="itemDataKey">
													<c:out value="${itemSort.key}" />_<c:out
														value="${reportTarget.targetField}" />
												</c:set> <c:set var="targetField">
													<c:out value="${reportTarget.targetField}" />
												</c:set> <%-- <c:if test="${reportTarget.fieldType==0}"> --%>
													<input type="hidden"
														name="<c:out value="${itemDataKey}"/>_id" align="left"
														value="<c:out value="${itemSort.key}"/>" />
													<input id="<c:out value="${itemDataKey}"/>" type="hidden"
														name="<c:out value="${itemDataKey}"/>" align="left"
														style="height: 21;" 
														size="<c:choose><c:when test='${reportTarget.dataType!=3}'>7</c:when><c:otherwise>12</c:otherwise></c:choose>"
														value="<c:out value="${repDataMap[itemDataKey]}"/>"  />
										</c:if>
										<c:if test="${reportTarget.targetField==80}">
											<c:set var="itemDataKey">
												<c:out value="${itemSort.key}" />_<c:out
													value="${reportTarget.targetField}" />
											</c:set>
											<c:set var="targetField">
												<c:out value="${reportTarget.targetField}" />
											</c:set>
											<input type="hidden"
												name="<c:out value="${itemDataKey}"/>_id" align="left"
												value="<c:out value="${itemSort.key}"/>" />
											<input id="<c:out value="${itemDataKey}"/>" type="hidden"
												name="<c:out value="${itemDataKey}"/>" align="left"
												style="height: 21;"
												value="<c:out value="${repDataMap[itemDataKey]}"/>" />
										</c:if>
									</c:forEach>
									<td class="TdBGColor2" align="center" width="200px">
									 <%-- <input
										type="button"
										value="<fmt:message key='button.save'/>"
										class="btn" alt="<c:out value="${itemSort.key}"/>" 
										onclick=" if($.validator('studentLoanForm',1,false,this)){singleUpdate('<c:out value="${openMode}"/>','<c:out value="${itemSort.key}"/>');} " />  --%>
										
										   <input
										type="button"
										value="<fmt:message key='button.save'/>"
										class="btn" alt="<c:out value="${itemSort.key}"/>" 
										onclick=" singleUpdate('<c:out value="${openMode}"/>','<c:out value="${itemSort.key}"/>'); " />   
									<input
										type="button"
										value="<fmt:message key='standard.dataFill.allTargets'/>"
										class="btn" alt="<c:out value="${itemSort.key}"/>"
										name="getAllTargets" /></td>
								</tr>
							</c:forEach>
						</c:if>
					</table>
					<%-- 	<c:if test="${relateFlag!= '2' }"> --%>
					<table width="99%" align="center" cellpadding="1" cellspacing="1"
						class="table">
						<tr>
							<td align="left" class="FormBottom" height="30">&nbsp;&nbsp; <c:if
									test="${openMode=='_self'}">
									<c:if test="${singleOrMore!='single'}">
										<c:if test="${deptAuditOk==null}">

											<%-- <input type="button" class="btn"
											value="<fmt:message key="button.save.currentPage"/>"
											name="queding"
											onClick="if($.validator('studentLoanForm',1,false)){ onSubmit('<c:out value='${openMode}'/>');}">&nbsp;&nbsp;&nbsp;&nbsp;  --%>

											<input type="button" class="btn"
												value="<fmt:message key="button.save.currentPage"/>"
												name="queding"
												onClick="onSubmit('<c:out value='${openMode}'/>');" />&nbsp;&nbsp;&nbsp;&nbsp;  
						</c:if>
										<input id="btnReturn" name="btnReturn" class="btn"
											type="button" value="<fmt:message key="button.back"/>"
											onclick="window.location.href='reportView.do?method=enterInputSearch1&levelFlag=2&editFlag=1' " />
									</c:if>
								</c:if> <c:if test="${openMode=='_blank'}">
									<c:if test="${deptAuditOk==null}">
										<input type="button"
											value="<fmt:message key="button.save.currentPage"/>"
											name="queding"
											onClick="if($.validator('studentLoanForm',1,false)){ onSubmit('<c:out value='${openMode}'/>');}"
											class="btn">&nbsp;&nbsp;&nbsp;&nbsp;
						</c:if>
									  <input id="btnReturn" name="btnReturn" class="btn"
										type="button" value="<fmt:message key="button.close"/>"
										onclick="javascript:window.close() " />
								</c:if></td>
						</tr>
					</table>
				</form></td>
		</tr>
	</table>
	<c:forEach items="${ruleMap}" var="rule">
		<c:if test="${rule.value!=''&&rule.value!=null}">
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
		</c:if>
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
	     
	        function singleUpdate(openmode,dataId) {
	        	var linkUrl = "<c:out value="${hostPrefix}"/><c:url value='/dataFill.do?method=singleSave&openmode="+openmode+
				//"&delItems=" + delItems + "'/>" +
				"&page=<c:out value='${page.pageNo}'/>&pageCnt=<c:out value='${totalPage}'/>&reportDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&singleOrMore=<c:out value='${singleOrMore}'/>&organCode=<c:out value='${organInfo.code}'/>&canEdit=<c:out value='${canEdit}'/>&organId=<c:out value='${organId}'/>&dataId=" +dataId;
				document.studentLoanForm.action = linkUrl;
				document.studentLoanForm.method = "post";
				document.studentLoanForm.submit();
	        }
	        
	        function go() {
	        	var pageNo = $("#pageNo").val();
	        	if(pageNo<1) {
	        		pageNo = 1;
	        	}else if(pageNo><c:out value='${totalPage}'/>){
	        		pageNo = <c:out value='${totalPage}'/>;
	        	}
	    		window.location.href = "dataFill.do?method=editReportDataEntry&openmode=_self&levelFlag=<c:out value='${levelFlag}'/>&dataDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>&page=" + pageNo;
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
								});
									$("#refresh").unbind("click");
									$("#refresh").bind("click",function(){
                                     var url = "dataFill.do?method=editReportDataSingle"+
												"&reportId=<c:out value='${reportid1}'/>"+
												"&keyvalue=<c:out value='${keyvalue}'/>"+
												"&dataDate=<c:out value='${dataDate}'/>"+
												"&organId=<c:out value='${organId}'/>"+
												"&canEdit=<c:out value='${canEdit}'/>"+
												"&levelFlag=<c:out value='${levelFlag}'/>"+
												"&targetid=<c:out value='${targetid}'/>"+
												"&openmode=_self"+
												"&pageFlag=<c:out value='${pageFlag}'/>";
												// alert(url);
										window.location.href = url;
									});
									
								/* 	$("#querydata").unbind("click");
									$("#querydata").bind("click",function(){
										var state = $("#dataStatus").val();
										var beginDate = $("#beginDate").val();
										var endDate = $("#endDate").val();
										if(beginDate>endDate) {
											alert("开始时间必须小于结束时间！");
											return;
										}
										window.location.href= "dataFill.do?method=getDataByCondition&openmode=_self&dataDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>&page=<c:out value='${page.pageNo}'/>&state="+state+"&beginDate="+beginDate+"&endDate="+endDate;
									});
								});  */
									$("input[name='getAllTargets']").each(function(){
										$(this).unbind("click");
										$(this).bind("click",function(){ 
											var dataId = $(this).attr("alt");
												var ret = window.open('dataFill.do?method=editSingelData&singleOrMore=<c:out value='${singleOrMore}' />&dataDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>&dataId='+dataId+'&levelFlag=<c:out value='${levelFlag}'/>',
		    		  													 'newwin','height=700,width=500,left=450,top=0,scrollbars=yes,status =yes');
								 		});
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
									}); 
							
									$("#exportData").unbind("click");
									$("#exportData").bind("click",function() {
										<c:if test="${ empty repDataMap}">
							        	alert("没有要导出的数据！");
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
										var url = "dataFill.do?method=enteryRepUpLoad&dataDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&levelFlag=<c:out value='${levelFlag}'/>";
										window.location.href = url;
									});
								});
	
								window.onload=function(){   
								    /****************************  
								     * 作者：q821424508@sina.com   *  
								     * 时间：2012-08-20            *  
								     * version：2.1              *  
								     *                          *  
								     ****************************/  
								    document.getElementsByTagName("body")[0].onkeydown =function(){   
								           
								        //获取事件对象   
								        var elem = event.relatedTarget || event.srcElement || event.target ||event.currentTarget;    
								           
								        if(event.keyCode==8){//判断按键为backSpace键   
								           
								                //获取按键按下时光标做指向的element   
								                var elem = event.srcElement || event.currentTarget;    
								                   
								                //判断是否需要阻止按下键盘的事件默认传递   
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
</html>
