<!-- /ps/model/datafill/reportGuide.jsp -->
<!DOCTYPE html>
<%@ page pageEncoding="UTF-8"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/adminlte.jsp"%>

<c:set var="flagOrgan" value="1"/>
<c:set var="flagReport" value="1"/>
<html>
<head>
<title><fmt:message key="webapp.prefix" /></title>
<link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>
 <script language="javascript" type="text/javascript" src="<c:url value='ps/uitl/My97DatePicker/WdatePicker.js'/>"></script>
 
<script type="text/javascript">
$(document).ready(function(){
	var flagOrgan = <c:out value='${flagOrgan}' />
	var flagReport =<c:out value='${flagReport}' />
	if(flagOrgan==2){
		$("#flagOrgan1").remove();
	}else{
		$("#flagOrgan2").remove();
	} 
	if(flagReport==2){//1单选，2多选
		$("#flagReport1").remove();
	}else{
		$("#flagReport2").remove();
	} 
	  getOrganTreeXML();
	  getrepTreeXML();
});  

 function getOrganTreeXML(){//机构树
	 $.post("treeAction.do?method=getOrganTreeXML",function(data){
			var treeXml = eval(data)[0].treeXml;
			$('#combotreeOrganTree').combotree('loadData', treeXml);
		}); 
 }
 
 function getrepTreeXML(){//报表树
	 $.post("reportView.do?method=repTreeAjax&levelFlag=1",function(data){
			var reportXml = eval(data)[0].reportXml;
			$('#combotreeRepTree').combotree('loadData', reportXml);
		}); 
 }
 
function setUnitCode(){
	var listObj = document.all.selUnit;
	document.all.unitCode.value = listObj.options[listObj.selectedIndex].value;
}

function repTree(){
    var oDate = document.reportViewForm.dataDate;
	var vDate = oDate.value;
	var oOrganId = document.reportViewForm.organId;
	var vOrganId = oOrganId.value;	
	var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/reportView.do?method=repTreeAjax&paramdate="+vDate+"&paramorgan="+vOrganId;
	if (window.XMLHttpRequest){
		req = new XMLHttpRequest();
	}else if (window.ActiveXObject){
		req = new ActiveXObject("Microsoft.XMLHTTP");
	}
	if (req){
		req.open("GET", url, true);
		req.send();
	}	
}

function changedate(){
	/* if(confirm("<fmt:message key='quedingyaoxiugaima'/>")){ */
		
	  	var req=new XMLHttpRequest();
		 
		req.open('POST', 'loginAction.do?method=updatedate');//struts
		req.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
		req.send("date97="+document.getElementById("dataDate").value);
		 if (req) {
		  
		      req.onreadystatechange=function() {
		      if (req.readyState == 4) 
		      {
		    	
		       if ( req.status==200) {
		    	  
		    	   
		    	/*    alert("<fmt:message key='repfile.statistics.suc'/>"); */
		    	 document.getElementById("date971").value=document.getElementById("dataDate").value ;
		     	}
		     }
		             
		  }
		  }  
			/* 	} 

	    else{ 
			document.getElementById("dataDate").value=document.getElementById("date971").value;
			return false;
			} */
}

function checkAll(){
	
		 var checklist = document.getElementsByName ("check1");
		// var checklist1 = document.getElementsByName ("check11");
		   if(document.getElementById("checkall").checked)
		   {
		   for(var i=0;i<checklist.length;i++)
		   {
		      checklist[i].checked = true;
		     // checklist1[i].checked = true;
		   } 
		 }else{
		  for(var j=0;j<checklist.length;j++)
		  {
		     checklist[j].checked = false;
		   //  checklist1[j].checked = false;
		  }
		 }
	}


function commitDataStatus1(){
	if(!confirm('<fmt:message key="view.qrtjm"/>')){ 
		return;
	}
	
	var checklist = document.getElementsByName ("check1");
	var aa=0;
	for(var i=0;i<checklist.length;i++){
		aa=aa+checklist[i].checked;
	}
	if(aa==0){
		alert('<fmt:message key="qingzhishaoxuanzeyige"/>');
		return;
	}
	var organId="";
	var reportId="";
	var dataDate=document.getElementById("dataDate").value;
	var targetTable="";
	for(var i=0;i<checklist.length;i++){
		if(checklist[i].checked==true){
			var aaa=checklist[i].value.split(",");
			for(var j=0;j<aaa.length;j++){
				if(j==0){
					organId+=aaa[0]+",";
				}
				if(j==1){
					reportId+=aaa[1]+",";
				}
				if(j==2){
					targetTable+=aaa[2]+",";
				}
				
			}
			
		}
	}
	
	 var url = "dataFill.do?method=commitDataStatus";
	var param = {"organId":organId, "reportId":reportId,"dataDate" : dataDate,"targettable" : targetTable,"aaaa":1};
	$.ajax({
		url : url,
		data : param,
		type : "post",
		success : function(data){
			location.href = location.href;
		},
		error : function(e){
			alert(e);
		}
	});  
	
	
}

</script>


<style type="text/css">
	#aaaa{margin: 0}
</style>

</head>
<!-- onclick="suggestclose()" -->
 <script type="text/javascript">
function forbiddon(){
	document.onkeydown=function(){if(event.keyCode==8)return false;};
}

</script>

<body onload="forbiddon();" >

	<html:form action="reportView.do?method=inputExtraCondition" method="post" styleId="reportViewForm">
		<table width="100%" class="navbar" cellSpacing="0" cellPadding="0">
			<c:if test="${param.method=='enterViewSearch'}">
				<input type="hidden" name="mode" value="display" />
			</c:if>
			<c:if test="${param.method=='enterInputSearch'}">
				<input type="hidden" name="mode" value="edit" />
			</c:if>
			<tr>
				<td width="50" align="left" nowrap class="TdBGColor1" align="right">
					<fmt:message key="reportview.form.date"/>
					<input type="hidden" name="reportId" id="reportId"
						<c:if test="${reportId == null || reportId == '' }">value="10000"</c:if>
						<c:if test="${reportId != null || reportId != '' }"> value="<c:out value='${reportId}' />"</c:if>/> 
					<input type="hidden" name="verifyFlag" id="verifyFlag" value="1" /> 
					<input type="hidden" name="canEdit"id="canEdit" value="0"/> 
					<input type="hidden" name="datapkids" id="datapkids" value="<c:out value='${datapkids}' />" /> 
					<input type="hidden" name="reportName" /> 
					<input type="hidden" name="organId" value="<c:out value='${organId}' />" /> 
					<input type="hidden" name="organName" /> 
					<input type="hidden" name="unitCode" value="<c:out value='${unitCode}' />" />
				</td>
				<td class="TdBGColor1" width="50px">
					<input type="hidden" name="showret" value="yes"> 
					<input type="hidden" onclick="WdatePicker();" class="Wdate" id="date971" value="<c:out value='${dataDate}'/>"> 
					<input id="dataDate" name="dataDate" type="text" value="<c:out value='${dataDate}'/>" style="width: 80px;"
						readonly="true" onClick="WdatePicker()" onchange="changedate();"/>
				</td>
				<td width="50" align="left" nowrap class="TdBGColor1">
					<fmt:message key="reportview.form.organ"/>
				</td>
				<td width="80" align="left" nowrap class="TdBGColor1">
					<div id="flagOrgan1">
						<input id="combotreeOrganTree" class="easyui-combotree" value="<c:out value='${organId}' />" style="width: 250px;" />
					</div>
					<div id="flagOrgan2">
						<input id="combotreeOrganTree" class="easyui-combotree" multiple value="<c:out value='${organId}' />"
							style="width: 250px;"/>
					</div>
				</td>
				<td width="50" align="left" nowrap class="TdBGColor1" align="right">
					<fmt:message key="reportview.form.report"/>
				</td>
				<td width="80" align="left" nowrap class="TdBGColor1">
					<div id="flagReport1">
						<input id="combotreeRepTree" class="easyui-combotree" style="width: 220px;"
							<c:if test="${reportId == null || reportId == '' }">value="10000"</c:if>
							<c:if test="${reportId != null || reportId != '' }">value="<c:out value='${reportId}' />"</c:if>
						/>
					</div>
					<div id="flagReport2">
						<input id="combotreeRepTree" class="easyui-combotree" multiple style="width: 220px;"
							<c:if test="${reportId == null || reportId == '' }">value="10000"</c:if>
							<c:if test="${reportId != null || reportId != '' }">value="<c:out value='${reportId}' />"</c:if>
						/>
					</div>
				</td>
				<td align="left" colspan="3">
					<div style="text-align: left; padding: 5px">
						<a href="#" class="easyui-linkbutton buttonclass1" data-options="iconCls:'icon-search'"
							onclick="viewReport('_self','yes')">
							<fmt:message key="reportview.button.look" />
						</a>
					</div>
				</td>
			</tr>
		</table>
	</html:form>


	<c:if test="${leveType == 1}"> 
				<a class="easyui-linkbutton buttonclass2" data-options="iconCls:'icon-selectall'"  href="javascript:commitDataStatus1()"><fmt:message key="plsh"/></a>
	</c:if>
				<table border="0" width="100%" cellSpacing=0 cellPadding=0>
					<tr>
						<td align="center">
							<div align="center" style="width: 100%">
								<form name="reportDataForm" action="dataFill.do?method=getDataByCondition"
									method="post">
									
									<input type="hidden" name="organId" value="<c:out value="${organId }"/>" id="organIds" />
									<input type="hidden" name="reportDate" value="<c:out value="${dataDate }"/>" id="reportDates" />
									<input type="hidden" name="reportIds" value="<c:out value="${reportIds}"/>" id="rIds" />
								    <input type="hidden" name="treportIds" value="<c:out value="${treportIds}"/>" id="tIds" />

						<table id="datatable" class="table table-striped table-bordered display table-hover" style="width:100%">
							<thead>
								<tr>
									<th>机构</th>
									<th>报表</th>
									<th>待补数量</th>
									<th>已补数量</th>
									<th>操作</th>
								</tr>
							</thead>

							<tbody>
								<c:forEach items="${reportList}" var="rt" varStatus="index">
									<tr>
										<c:if test="${leveType != 1}"> 	
											<td sortable="true" headerClass="sortable" titleKey="reportguideorgan">
											<c:out value="${organInfo.full_name}"/>
											</td>
											<td sortable="true" headerClass="sortable" titleKey="reportguide"><c:out value="${rt[3]}" /></td>
											<td sortable="true" headerClass="sortable" titleKey="reportguidejilu"><c:out value="${rt[2]}" /></td>
											<td sortable="true" headerClass="sortable" titleKey="reportguidejiluy"><c:out value="${rt[7]}" /></td>
											<td sortable="true" headerClass="sortable" titleKey="reportrule.edit">
												<c:if test="${rt[5]!=0}">
													<a style="text-decoration: none;"
														href='dataFill.do?method=getReportGuideDetailed&organId=<c:out value="${rt[1]}" />&reportId=<c:out value="${rt[0]}" />&targetTable=<c:out value="${rt[4]}" />&reportDate=<c:out value="${dataDate }"/>'>
														<fmt:message key="view.xxxx" /></a>&nbsp;&nbsp;								
												</c:if> 
												<c:if test="${rt[2]!=0}">
													<a style="text-decoration: none;"
														href='dataFill.do?method=editReportDataEntryForDataInput&openmode=_self&canEdit=1&levelFlag=0&organCode=<c:out value="${rt[1]}" />&reportDate=<c:out value="${dataDate }"/>&reportId=<c:out value="${rt[0]}" />&flag2=1&flagtap=<c:out value="${flagtap}" />'>
														<fmt:message key="view.sjbl" /></a>&nbsp;&nbsp;
												</c:if>
											</td>
										</c:if>

										<c:if test="${leveType == 1}"> 
										<td headerClass="sortable"
												title="<input type='checkbox' name='checklist' id='checkall'  onclick='checkAll()'>"
											><c:if test="${rt[2]==0 && rt[6] == 0}">
													<input type="checkbox" name="check1"
														value="<c:out value="${rt[1]}" />,<c:out value="${rt[0]}" />,<c:out value="${rt[4]}" />"
													>
												</c:if></td>
											<td headerClass="sortable" titleKey="reportguideorgan"><c:out value="${organInfo.full_name}" /></td>
											<td headerClass="sortable" titleKey="reportguide"><c:out value="${rt[3]}" /></td>
											<td sortable="true" headerClass="sortable" titleKey="reportrule.wCount"><c:out value="${rt[2]}" /></td>
											<td sortable="true" headerClass="sortable" titleKey="reportrule.tCount"><c:out value="${rt[7]}" /></td>
											<td sortable="true" headerClass="sortable" titleKey="reportrule.edit">
												<%-- <c:if test="${rt[5]!=0}">
												<a href='dataFill.do?method=getReportGuideDetailed&organId=<c:out value="${rt[1]}" />&reportId=<c:out value="${rt[0]}" />&targetTable=<c:out value="${rt[4]}" />&reportDate=<c:out value="${dataDate }"/>'><fmt:message key="view.xxxx"/></a>&nbsp;&nbsp;	 							
											</c:if>
											--%> <c:if test="${rt[2]!=0}">
													<a style="text-decoration: none;"
														href='dataFill.do?method=editReportDataEntryForDataInput&openmode=_self&canEdit=1&levelFlag=0&organCode=<c:out value="${rt[1]}" />&reportDate=<c:out value="${dataDate }"/>&reportId=<c:out value="${rt[0]}" />&flag2=1&flagtap=<c:out value="${flagtap}" />'
													><fmt:message key="view.sjbl" /></a>&nbsp;&nbsp;
											</c:if> <c:if test="${rt[2]==0 && rt[6] == 0}">
													<a style="text-decoration: none;"
														href="javascript:commitDataStatus('<c:out value="${rt[1]}" />','<c:out value="${rt[0]}" />','<c:out value="${rt[4]}" />')"
													><fmt:message key="view.sjsh" /></a>
												</c:if> <c:if test="${rt[2]==0 && rt[6] > 0}">
													<font style="color: green;"><fmt:message key="view.sjysh" /></font>
													<font style="margin-left: 10px;"><a style="text-decoration: none;"
														href="javascript:unLockDataStatus('<c:out value="${rt[1]}" />','<c:out value="${rt[0]}" />')"
													> <fmt:message key="report.lock.deLock" /></a></font>
												</c:if>
											</td>
										</c:if>
									</tr>
								</c:forEach>
							</tbody>
							<tfoot>
								<tr>
									<th>机构</th>
									<th>报表</th>
									<th>待补数量</th>
									<th>已补数量</th>
									<th>操作</th>
								</tr>
							</tfoot>
						</table>

					</form>
							</div>
						</td>
					</tr>
				
				<%-- 	<tr>
							<td height="30" align="left">
								&nbsp;&nbsp; <a href="dataFill.do?method=getReportGuide&levelFlag=1&leveType=1" style="color:#990000;un"><strong><fmt:message key="button.back"/></strong></a>
						    </td>
					</tr>
				--%>
				</table>

<script type="text/javascript">
	//var df = document.reportViewForm;
	var df = $('#reportViewForm');
	function viewReport(tg, sr) {
		var dataDate = $("#dataDate").val();
		var vReportId = $("#combotreeRepTree").combotree("getValue");
		var organCode = $("#combotreeOrganTree").combotree("getValue");
		if (vReportId == "" || vReportId == null) {
			alert("<fmt:message key="js.validate.report"/>");
			return;
		}
		if (vReportId >= 10000) {
			alert("<fmt:message key="js.validate.report"/>");
			return false;
		}
		df.prop("action","dataFill.do?method=getReportGuide&levelFlag=1&leveType=<c:out value='${leveType}'/>&reportId="
				+ vReportId + "&organCode=" + organCode);
		<c:if test="${unitCodeFlag == 1}">
		setUnitCode();
		</c:if>
		if (tg === "_blank") {
			var viewOrganId = $("#combotreeOrganTree").combotree("getValue");
			var viewDataDate = df.dataDate.value;
			var viewReportId = $("#combotreeRepTree").combotree("getValue");
			tg = viewReportId + viewDataDate + viewDataDate + "_window";
		}
		df.target = tg;
		//df.showret.value = sr;
		df.submit();
	}
	function commitDataStatus(organId, reportId, targetTable) {
		if (!confirm('<fmt:message key="view.qrtjm"/>')) {
			return;
		}

		var dataDate = $("#dataDate").val();
		var url = "dataFill.do?method=commitDataStatus";
		var param = {
			"organId" : organId,
			"reportId" : reportId,
			"dataDate" : dataDate,
			"targettable" : targetTable,
			"aaaa" : 0
		};
		$.ajax({
			url : url,
			data : param,
			type : "post",
			success : function(data) {
				location.href = location.href;
			},
			error : function(e) {
				alert(e);
			}
		});
	}
	function unLockDataStatus(organId, reportId) {
		if (!confirm('<fmt:message key="button.ratifypass"/><fmt:message key="report.lock.deLock"/>')) {
			return;
		}
		var dataDate = $("#dataDate").val();
		var url = "dataFill.do?method=unLockDataStatus";
		var param = {
			"organId" : organId,
			"reportId" : reportId,
			"dataDate" : dataDate
		};
		$.ajax({
			url : url,
			data : param,
			type : "post",
			success : function(data) {
				location.href = location.href;
			},
			error : function(e) {
				alert(e);
			}
		});
	}

	$(function() {
		$('#datatable').DataTable({
			"paging" : true,
			"lengthChange" : false,
			"searching" : false,
			"ordering" : true,
			"info" : true,
			"autoWidth" : true,
			"pageLength": 20,
			"lengthMenu": [ [20, 50, -1], [20, 50, "全部"] ],
			"pagingType": "first_last_numbers",
			"language": {
			      "emptyTable": "没有数据显示",
			      "loadingRecords": "加载数据中...",
			      "info": "_START_-_END_(共_TOTAL_)条记录,第_PAGE_页/共_PAGES_页",
			      "paginate": {
			          "first": "首页",
			          "last": "尾页",
			          "previous": "上一页",
			          "next": "下一页"
			        }
			 },
			 renderer: {
			        "header": "jqueryui",
			        "pageButton": "bootstrap"
			 },
			buttons: [
				'pdfHtml5',
		        'colvis',
		        'excel',
		        'print'
		    ]
		});
	});
</script>
</body>
</html>