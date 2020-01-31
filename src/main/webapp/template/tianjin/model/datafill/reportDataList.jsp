<%@ page import="com.krm.ps.sysmanage.usermanage.vo.*"%>
<%@ page import="com.krm.ps.util.SysConfig"%>
<%@ page import="com.krm.ps.util.FuncConfig"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>

<c:set var="colName">
	<fmt:message key="common.list.reportname"/>
</c:set>
<c:set var="reportTreeURL">
	<c:out value="${hostPrefix}"/><c:url value='/reportView.do?method=repTreeAjax${params}'/>
</c:set>
<c:set var="reportButton">
	<fmt:message key="place.select"/>
</c:set>

<c:set var="colNames">
	<fmt:message key="exportXML.tree.org.name"/>
</c:set>
<c:set var="orgTreeURL">
	<c:out value="${hostPrefix}" /><c:url value="/treeAction.do?method=getOrganTreeXML${orgparam}"/>
</c:set>
<c:set var="orgButton">
	<fmt:message key="place.select"/>
</c:set>
<style type="text/css">
.table {
	width: 100%;
	padding: 0px;
	margin: 0px;
	font-family: Arial, Tahoma, Verdana, Sans-Serif, '<fmt:message key="view.st"/>';
	border-left: 1px solid #ADD8E6;
	border-collapse: collapse;
	background-color: #EEEEEE
}
/*table top to type*/
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
	white-space: nowrap;
	text-align: center;
	overflow: hidden;
}
/*dan yuan ge yang shi*/
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
/*lan se dan yuan ge yang shi , zhu yao yong yu ge hang bian se*/
.table td.color {
	background: #edf7f9;
}
/*biao ge zhong chao lian jie yang shi*/
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
<script type="text/javascript">

function resetdata(obj,reportda,resulttablename,dataId,repid) {
	if(!confirm('<fmt:message key="view.qrcz" />')){
		return;
	}
	var params={"resulttablename": resulttablename,"reportDate":reportda,"reportId":repid,"dataId":dataId ,"method":"resetReportData"};
	$.ajax({ 
		type: "POST", 
		async: true, //ajax
		url: "dataFill.do", 
		data: jQuery.param(params), 
		success: function(result){
			if(result==1){
				//chan chu ben hang shu ju
				var tr=obj.parentNode.parentNode;
				var tbody=tr.parentNode;
				tbody.removeChild(tr);
				alert('<fmt:message key="view.sjczcg" />');
			}else if(result==2){
				alert('<fmt:message key="view.sjczsb" />');
			}else{
				alert('<fmt:message key="view.sjczsb" />');
			}
		}
	});
}

 function etirSar(dataId,page){
	   var url = "dataFill.do?method=geteditDataDetail&singleOrMore=<c:out value='${singleOrMore}' />&dataDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>&pager="+page+"&dataId="+dataId+"&levelFlag=<c:out value='${levelFlag}'/>"; 
        window.location.href=url;//zhe shi dang qian ye diao zhuan <c:out value='${page.pageNo}'/>

 }
 
 
 function delSar(obj,pkid,rId,reportda,organId){
	 if(pkid=="0"){
		 if(!confirm(reportda+'<fmt:message key="view.cyqrsc" />')){
				return;
			} 
	 }else{
		 if(!confirm('<fmt:message key="view.qrsc" />')){
				return;
			}
	 }
		var params={"pkid": pkid ,"reportId": rId ,"dataDate": reportda ,"organId":organId,"method":"delSingelData"};
		$.ajax({ 
			type: "POST", 
			async: false, //ajax
			url: "dataFill.do", 
			data: jQuery.param(params), 
			success: function(result){
				if(result==1){
						var tr=obj.parentNode.parentNode;
						var tbody=tr.parentNode;
						tbody.removeChild(tr);
					    alert('<fmt:message key="view.sjsccg" />');
					    document.reportDataForm.action="dataFill.do?method=getDataDetail&levelview=1";
					    document.reportDataForm.submit();
					    
				}else if(result==2){
					    alert('<fmt:message key="view.sjscsb" />');
				}
			}
		});
 }
 
 /* function buttongjcx(organId,levelFlag){
	   var zhi1=$("#zhi").val();
   	document.reportDataForm.action='<c:out value="${hostPrefix}" /><c:url value="/dataFill.do" />?method=getDataDetailgjcx&levelview=1&zhi='+zhi1+"&organId="+organId+"&levelFlag="+levelFlag ;
  	document.reportDataForm.submit();
 } */
 function buttongjcx(){
 	document.reportDataForm.action='<c:out value="${hostPrefix}" /><c:url value="/dataFill.do" />?method=getDataDetailgjcx';
	document.reportDataForm.submit();
}
 
 function fuzhi(){
	 $("#zhi").val($("#zhi1").val());
 }
</script>
<html>
<head>
<title><fmt:message key="webapp.prefix" /></title>

<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${cssPath}/common.css'/>" />  
<script src="<c:url value='/ps/framework/common/jqueryTab/jquery-1.4.1.min.js'/>" type="text/javascript"></script>
<link rel="stylesheet" href="<c:url value='${cssPath}/css.css" type="text/css'/>"/>


<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-win2k-1.css'/>" title="win2k-cold-1" />
<script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar.js'/>"></script> 
<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/lang/zh-cn.js'/>"></script> 
<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-setup.js'/>"></script>
<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/ActiveXTree/ActiveXTree.js'/>"></script>    
<script type="text/javascript" src="<c:url value='/ps/scripts/prototype.js'/>"></script>
<script src="<c:url value='/ps/framework/common/jqueryTab/jquery-1.2.4b.js'/>" type="text/javascript"></script>


<script type="text/javascript">
function forbiddon(){
	document.onkeydown=function(){if(event.keyCode==8)return false;};
	
}
</script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0" onload="forbiddon();">
	<table border="0" cellpadding="0" cellspacing="0" width="100%"
		height="100%">
		<tr>
			 <td width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td>
							<p style="margin-top: 3">
								 <c:if test="${lxview!='1'}" >
								 	<c:if test="${lxhcstatus!=null&&lxhcstatus!=''}">
								      <font class=b color="#000000"><b><fmt:message key="view.vusjcx" /></b></font>
								    </c:if>
								    <c:if test="${lxhcstatus==null}">
								      <font class=b color="#000000"><b><fmt:message key="view.bssjcx" /></b></font>
								    </c:if>
								 </c:if>
								 <c:if test="${lxview=='1'}" >
									 <font class=b color="#000000"><b><fmt:message key="view.ywsjwh" /></b></font>
								 </c:if>
							</p>
						</td>
						<td></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td width="100%" bgcolor="#EEEEEE" valign="top">
				<table border="0" width="98%" align="left" cellSpacing=0 cellPadding=0>
					<tr>
					<input type="hidden" id="lxvieww" value="<c:out value="${lxview }"/>"/>
						<td height="30" align="left">
							<form name="reportDataForm" action="/dataFill.do?method=getDataDetailgjcx" method="post">
								<input type="hidden" name="reportId" value="<c:out value="${reportId }"/>"/>
								<input type="hidden" name="reportName" value="<c:out value="${reportName}"/>">
								<input type="hidden" name="levelFlag" value="<c:out value="${levelFlag}"/>">
								<input type="hidden" name="organId" value="<c:out value='${organId}' />"/>
								<input type="hidden" name="levelview" value="<c:out value='${lxview}' />"/>
								<input type="hidden" name="levelFlag" value="<c:out value='${levelFlag}' />"/>
								<input type="hidden" name="zhi" id="zhi" value=""/>
								<input type="hidden" name="organName"/>
								<table width="100%" border="0" align="left" cellSpacing="0"
									cellPadding="0">
									
									<tr>
										<td width="50" align="left" nowrap class="TdBGColor1"
											align="right">&nbsp;&nbsp;&nbsp;<fmt:message key="reportview.form.date" /></td>
										<td class="TdBGColor1" width="50px">
										<input  type="text" id="reportDate" name="reportDate" value="<c:out value='${dataDate}'/>" readonly="true" /></td>
										<script type="text/javascript">
											Calendar.setup({
												inputField : "reportDate",
												ifFormat : "%Y-%m-%d",
												showsTime : false
											});
										</script>
										<td width="50" align="left" nowrap class="TdBGColor1"
											align="right">&nbsp;&nbsp;&nbsp;<fmt:message key="reportview.form.organ" /></td>
										<td width="80" align="left" nowrap class="TdBGColor1">
										 <slsint:ActiveXTree left="220" top="325" height="${activeXTreeHeight }"
												width="380"
												xml="${orgTreeURL}" 
												bgcolor="0xFFD3C0"
												rootid="${rootId}" 
												columntitle="${colNames}"
												columnwidth="380,0,0,0" 
												formname="reportDataForm"
												idstr="organId" 
												namestr="organName" 
												checkstyle="0" 
												filllayer="2"
												txtwidth="200" 
												buttonname="${orgButton}">
											</slsint:ActiveXTree></td>
										<td width="90" align="left" nowrap class="TdBGColor1"
											align="right">&nbsp;&nbsp;&nbsp;<fmt:message key="view.bsmb" /></td>
										<td width="80" align="left" nowrap class="TdBGColor1">
										<slsint:ActiveXTree left="220" top="325" width="325" height="${activeXTreeHeight }"
									      		xml="${reportTreeURL}" 
									      		bgcolor="0xFFD3C0" 
									      		rootid="10000"
									      		columntitle="${colName}" 
									      		columnwidth="300,0,0,0" 
									      		formname="reportDataForm" 
									      		idstr="reportId" 
									      		namestr="reportName" 
									      		checkstyle = "0" 
									      		filllayer="2" 
									      		txtwidth="200"
									      		buttonname="${reportButton}">
									      	</slsint:ActiveXTree>	</td>
									      	<c:if test="${lxhcstatus!=null&&lxhcstatus!=''}">
									      	<td style="width:40px;" align="left" nowrap class="TdBGColor1"
											align="right"><fmt:message key="excel.import.status" /></td>
											<td width="80" align="left" nowrap class="TdBGColor1">
												<select  name="cstatus">
														<option value="2" <c:if test="${lxhcstatus=='2'}" >selected='selected'</c:if> selected>
															<fmt:message key="reportrule.tCount" />
														</option>
														<option value="5" <c:if test="${lxhcstatus=='5'}" >selected='selected'</c:if> >
															<fmt:message key="view.ybc" />
														</option>
														<option value="4" <c:if test="${lxhcstatus=='4'}" >selected='selected'</c:if> >
															<fmt:message key="srcdata.manager.issyn" />
														</option>
												</select>
											</td>
									      	</c:if>
											
											<td align="left" colspan="3"><input align="middle"
											onclick="datafilsub();" type="button"
											value="<fmt:message key="reportview.button.look"/>"
											style="width: 80;" 
											class="TdBGColor1"></td>
									</tr>
								<c:if test="${lxview=='1'}" >
									<tr>
										<td width="50" align="left" nowrap class="TdBGColor1"
											align="right">&nbsp;&nbsp;&nbsp;<fmt:message key="view.zd" />
								   	    </td>
								    	<td class="TdBGColor1" width="50px">
									   		 <html:form action="studentLoan" method="post" styleId="studentLoanForm">
															  <html:select property="targetField" style ="width:147;height:19">
															   <html:option value="0"><fmt:message key="system.switch.hint" /></html:option>
					              							   <html:options collection="reportTargetList" property="targetField"  labelProperty="targetName"/>
					                 						 </html:select> 
					                 		 </html:form>
				                        </td>
					                     <td width="50" align="left" nowrap class="TdBGColor1"
										    align="right">&nbsp;&nbsp;&nbsp;<fmt:message key="view.zhi" />
										 </td>
										 <td width="80" align="left" nowrap class="TdBGColor1">
										    <input type="text" style="height: 19;"value='<c:out value="${zhi}"/>' name="zhi1" id="zhi1" onchange="fuzhi();"/>
										 </td>
										 <td style="width:40px;" align="left" nowrap class="TdBGColor1"
											align="right">
										   <%-- <input type="submit" style="height: 19;" value='<fmt:message key="view.zhss"/>' onclick="return buttongjcx('<c:out value='${organId}' />','<c:out value='${levelFlag}'/>');" /> --%>
										   <input type="submit" style="height: 19;" value='<fmt:message key="view.zhss"/>' onclick="buttongjcx();"/>
										 </td>
									</tr>
								</c:if>	
								</table>
							</form></td>
					</tr>
					<tr>
						<td align="center">
							<c:if test="${totalPage>0 }">
		<table width="99%" align="center" cellpadding="1" cellspacing="1"
						class="table">
						<c:if test="${lxview=='1'}" >
						    <tr align="left">
								<td align="left" valign="top" class="TdBGColor2"><fmt:message key="view.dqd"/> <c:out
										value="${page.pageNo}" /> <fmt:message key="view.ye"/> <a
									href="dataFill.do?method=getDataDetail&openmode=_self&cstatus=<c:out value='${lxhcstatus}'/>&reportDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>&levelFlag=<c:out value='${levelFlag}'/>&page=1&levelview=1"><fmt:message key="chartpage.navigation.homepage"/></a>
									<c:if test="${page.pageNo>1 }">
										<a
											href="dataFill.do?method=getDataDetail&openmode=_self&cstatus=<c:out value='${lxhcstatus}'/>&reportDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>&levelFlag=<c:out value='${levelFlag}'/>&page=<c:out value='${page.pageNo-1 }'/>&levelview=1"><fmt:message key="view.syy"/></a>
									</c:if> 
										
									<c:choose>
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
  					[ <a href="dataFill.do?method=getDataDetail&openmode=_self&cstatus=<c:out value='${lxhcstatus}'/>&reportDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>&levelFlag=<c:out value='${levelFlag}'/>&page=<c:out value='${num}'/>&levelview=1"><c:out
														value="${num }" /> </a> ]
  				</c:otherwise>
										</c:choose>
									</c:forEach> <a
									href="dataFill.do?method=getDataDetail&openmode=_self&cstatus=<c:out value='${lxhcstatus}'/>&reportDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>&levelFlag=<c:out value='${levelFlag}'/>&page=<c:out value='${totalPage}'/>&levelview=1"><fmt:message key="view.wy"/></a>
									<c:if test="${page.pageNo<totalPage}">
										<a
											href="dataFill.do?method=getDataDetail&openmode=_self&cstatus=<c:out value='${lxhcstatus}'/>&reportDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>&levelFlag=<c:out value='${levelFlag}'/>&page=<c:out value='${page.pageNo+1 }'/>&levelview=1"><fmt:message key="view.xyy"/></a>
									</c:if><fmt:message key="view.zg"/><c:out value="${totalPage}" /><fmt:message key="view.ye"/><fmt:message key="view.zg"/> <c:out
										value="${page.totalRecord }" /><fmt:message key="view.jls"/> <fmt:message key="view.tzz"/> <input id="pageNo"
									type="text" style="width: 40px;" /><fmt:message key="view.ye"/> <input type="button"
									value="GO" onclick="go()" class="btn" />&nbsp;&nbsp;&nbsp;&nbsp;
							<%-- 	<c:if test="${lxview=='1'}" >	
									<input type="button" value='<fmt:message key="view.zbsc"/>' class="btn" onclick="delSar(this,'0','<c:out value='${reportId}'/>','<c:out value='${dataDate}'/>','<c:out value='${organId}' />'); "/>
								</c:if>	
								--%> 
									 </td>
								</td>
							</tr>
						 
						</c:if>
						<c:if test="${lxview!='1'}" >
						   <tr align="left">
								<td align="left" valign="top" class="TdBGColor2"><fmt:message key="view.dqd"/>  <c:out value="${page.pageNo}" /><fmt:message key="view.ye"/>  <a
									href="dataFill.do?method=getDataDetail&openmode=_self&cstatus=<c:out value='${lxhcstatus}'/>&reportDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>&levelFlag=<c:out value='${levelFlag}'/>&page=1"><fmt:message key="chartpage.navigation.homepage"/></a>
									<c:if test="${page.pageNo>1 }">
										<a
											href="dataFill.do?method=getDataDetail&openmode=_self&cstatus=<c:out value='${lxhcstatus}'/>&reportDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>&levelFlag=<c:out value='${levelFlag}'/>&page=<c:out value='${page.pageNo-1 }'/>"><fmt:message key="view.syy"/></a>
									</c:if> 
										
									<c:choose>
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
  					[ <a href="dataFill.do?method=getDataDetail&openmode=_self&cstatus=<c:out value='${lxhcstatus}'/>&reportDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>&levelFlag=<c:out value='${levelFlag}'/>&page=<c:out value='${num}'/>"><c:out
														value="${num }" /> </a> ]
  				</c:otherwise>
										</c:choose>
									</c:forEach> <a
									href="dataFill.do?method=getDataDetail&openmode=_self&cstatus=<c:out value='${lxhcstatus}'/>&reportDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>&levelFlag=<c:out value='${levelFlag}'/>&page=<c:out value='${totalPage}'/>"><fmt:message key="view.wy" /></a>
									<c:if test="${page.pageNo<totalPage}">
										<a
											href="dataFill.do?method=getDataDetail&openmode=_self&cstatus=<c:out value='${lxhcstatus}'/>&reportDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>&levelFlag=<c:out value='${levelFlag}'/>&page=<c:out value='${page.pageNo+1 }'/>"><fmt:message key="view.xyy" /></a>
									</c:if> <fmt:message key="view.zg"/><c:out value="${totalPage}" /><fmt:message key="view.ye"/><fmt:message key="view.zg"/> <c:out
										value="${page.totalRecord }" /> <fmt:message key="view.jls"/> <fmt:message key="view.tzz"/> <input id="pageNo"
									type="text" style="width: 40px;" /> <fmt:message key="view.ye"/><input type="button"
									value="GO" onclick="go()" class="btn" />&nbsp;&nbsp;&nbsp;&nbsp;
								<c:if test="${lxview=='1'}" >	
									<input type="button" value='<fmt:message key="view.zbsc" />' class="btn" onclick="delSar(this,'0','<c:out value='${reportId}'/>','<c:out value='${dataDate}'/>','<c:out value='${organId}' />'); "/>
								</c:if>	 
									 </td>
								</td>
							</tr>
						</c:if>
							
					</table>
					</c:if>	
			<table id="budgetTbl" width="2000"  cellpadding="1" cellspacing="1" class="table" border="0">
						<thead>
							<tr height="40px">
								<td class="color" align="center" style="width:60px;" nowrap><fmt:message
										key="studentloan.report.sortnum" /></td>
								<c:forEach var="reportTarget" items="${reportTargetList}">
										<td
											id="titleName<c:out value='${reportTarget.targetOrder}' />"
											class="color" align="center"
											width="<c:out value="${reportTarget.editDate}"/>"><c:out
												value="${reportTarget.targetName}" />
										</td>
								</c:forEach>
								<c:if test="${lxhcstatus=='2' || lxhcstatus=='4' || lxview=='1'}" >
								<td class="color" align="center" style="width:75px;" nowrap><fmt:message key="button.operation" />
								</td>
								</c:if>
							</tr>
						</thead>
						 <c:if test="${not empty repDataMap}">
							<c:forEach var="itemSort" items="${repItemSort}">
								<tr>
									<td class="TdBGColor2" align="center" style="width:60px;">
									<c:choose>
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
									</c:choose> <c:out value="${sortnum}" />
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
													<input id="<c:out value="${itemDataKey}"/>"
														name="<c:out value="${itemDataKey}"/>" align="left"
														style="height: 21;" 
														type="text"
														size="<c:choose><c:when test='${reportTarget.dataType!=3}'>7</c:when><c:otherwise>12</c:otherwise></c:choose>"
														value="<c:out value="${repDataMap[itemDataKey]}"/>" />
									<%--	</c:if>
										<c:if test="${reportTarget.targetField==80}"> 
											<c:set var="itemDataKey">
												<c:out value="${itemSort.key}" />_<c:out
													value="${reportTarget.targetField}" />
											</c:set>
											<c:set var="targetField">
												<c:out value="${reportTarget.targetField}" />
											</c:set>
											<input id="<c:out value="${itemDataKey}"/>" type="hidden"
												name="<c:out value="${itemDataKey}"/>" align="left"
												style="height: 21;"
												value="<c:out value="${repDataMap[itemDataKey]}"/>" />
										</c:if> --%>
										</td>
									</c:forEach>
									<c:if test="${lxhcstatus=='2' || lxhcstatus=='4'}" >
										<td class="TdBGColor2" align="center" >
								  <input
										type="button"
										value='<fmt:message key="button.reset" />'
										class="btn" 
										onclick="resetdata(this,'<c:out value='${dataDate}'/>','<c:out value="${lxhresulttablename}"/> ','<c:out value="${itemSort.key}"/>','<c:out value='${reportId}'/>'); "/>
								</td>
									</c:if>
								<c:if test="${lxview=='1'}" >
									<td class="TdBGColor2" align="center" >
								 	<a href="#"  onclick="etirSar('<c:out value="${itemSort.key}" />','<c:out value="${page.pageNo}" />');"><fmt:message key="button.update" /></a>|
									<a href="#"  onclick="delSar(this,'<c:out value="${itemSort.key}" />','<c:out value='${reportId}'/>','<c:out value='${dataDate}'/>','<c:out value='${organId}' />'); "><fmt:message key="button.delete" /></a> 
									 </td>
								</c:if>
								</tr>
							</c:forEach>
						</c:if>
							
					</table>
						</td>
					</tr>
					<tr height=17>
						<td></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</body>

</html>

<script type="text/javascript">
var df=document.reportDataForm;


function datafilsub(){
	var oReportId = df.reportId;
    var vReportId = oReportId.value;
    
	 if (vReportId==""||vReportId == null){
       alert('<fmt:message key="js.validate.report"/>');
       return false;
	}else if (vReportId >= 10000){
		alert('<fmt:message key="js.validate.report"/>');
		return false;
	}else{
		var url="";
		var lxview =$("#lxvieww").val();
		
		if(lxview!=1){
			url="<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/dataFill.do?method=getDataDetail";
		}else if(lxview==1){
			url="<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/dataFill.do?method=getDataDetail&levelview=1";
		}
		document.reportDataForm.action = url;
		document.reportDataForm.submit();
	} 
	
}


function changeOrg(){
    var oDate = document.reportDataForm.date;
	var vDate = oDate.value;
	var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/treeAction.do?method=getOrganTreeXML&date="+vDate;
	document.all.objTree1.SetXMLPath(url);
   	document.all.objTree1.Refresh();	
}
function changeRep(){
    var oDate = document.reportDataForm.date;
	var vDate = oDate.value;
	var oOrganId = document.reportDataForm.organ;
	var vOrganId = oOrganId.value;
	var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/reportView.do?method=repTreeAjax&levelFlag=2&paramdate="+vDate+"&paramorgan="+vOrganId;
	document.all.objTree2.SetXMLPath(url);
   	document.all.objTree2.Refresh();	
}

function reportCheck(){
	oReportId = df.report;
    vReportId = oReportId.value;
	if (vReportId==""||vReportId == null){
       alert("<fmt:message key="js.validate.report"/>");
       return;
	}else if (vReportId >= 10000){
		return;
	}else{
		var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/dataMergeAction.do?method=dataMerge";
		document.reportDataForm.action = url;
		document.reportDataForm.submit();
	}
}

function change(){
	changeOrg();
	changeRep();
}

function changeTree1(){
	changeRep();
}

function changeTree2(){}

var oldHide=Calendar.prototype.hide;
Calendar.prototype.hide = function () {oldHide.apply(this);	change();}

function go() {
	var pageNo = $("#pageNo").val();
	if(pageNo<1) {
		pageNo = 1;
	}else if(pageNo><c:out value='${totalPage}'/>){
		pageNo = <c:out value='${totalPage}'/>;
	}
	window.location.href = "dataFill.do?method=getDataDetail&openmode=_self&levelFlag=<c:out value='${levelFlag}'/>&dataDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>&page=" + pageNo;
}

</script>