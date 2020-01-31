<!-- /ps/model/datafill/reportDataInputEdit. -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>

<html>
<head>



<title><fmt:message key="webapp.prefix" /></title>

<script language="javascript" type="text/javascript" src="<c:url value='ps/uitl/My97DatePicker/WdatePicker.js'/>"></script>	
<script src="<c:url value='/ps/uitl/krmtree/JSrepfile/validateJson.js'/>" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>
<link rel="stylesheet" type="text/css" href="<c:url value='${tableCss}/tablecss.css'/>"/>
<script src="<c:url value='${tableCss}/jqueryTab/jquery-1.4.1.min.js'/>" type="text/javascript"></script>
<c:set  var="filenameUrl" > 
<c:out value='/ps/uitl/krmtree/jqueryValidata/'  /><c:out value='validator_'  /><c:out value='${reportId }'  /><c:out value='.js' />
</c:set>
<script src="<c:url value='${filenameUrl} '/>" type="text/javascript"></script>
<style>

</style>
</head>
<script language="JavaScript">

jsondata = <c:out value='${jsonObjectFrom}' escapeXml="false"/>;

init(jsondata);
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
		
	}
	
	
	function exportData(){
		var keystr="";
		var valstr="";
		var idarr="";
		
		var iskey = document.getElementsByName("iskey");
		for(i=0;i<iskey.length;i++){
			var id = iskey[i].value;//id
			idarr +=id+",";
			var idstr = document.getElementsByName(id+"_jilu");
			for(j=0;j<idstr.length;j++){
				keystr +=idstr[j].value.split(":")[1]+",";
				//keystr += idstr[j].value.split(":")[1].replace("是","yes")+",";
				//keystr.replace("否","no");
				
			}
			valstr +=keystr+"@";
			keystr="";
		} 
		 var url = "dataFill.do?method=exportTemplate&dataDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>";
		
		/* $.post(url,'',function(data){});  */
		document.studentLoanForm.action=url;
		document.studentLoanForm.submit();
		/* var params={"reportId":$("#reportId").val(),"valstr":valstr,"idarr":idarr,"method":"exportReportData"};
				$.ajax({ 
					type: "POST", 
					async: false, //ajax
					url: "dataFill.do", 
					data: jQuery.param(params), 
					success: function(result){}
					}); */
	}
	
	
	function importData(){
	var url = "dataFill.do?method=xlinputdata&dataDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>&resultablename1=<c:out value='${resultablename1}'/>";
		
		document.studentLoanForm.action=url;
		document.studentLoanForm.submit();
		
	}
	
    function buttongjcx (){
		var zhi1=$("#zhis1").val();
		$("#zhi1").val(zhi1);
		var zhi2=$("#zhis2").val();
		$("#zhi2").val(zhi2);
		var zhi3=$("#zhis3").val();
		$("#zhi3").val(zhi3);
		var openmode1="_self";
		var reportId=document.getElementById("reportId").value;
		var aaa=document.getElementById("aaa").value;
		var bbb=document.getElementById("bbb").value;
		var ccc=document.getElementById("ccc").value;
		document.studentLoanForm.action='<c:out value="${hostPrefix}" /><c:url value="/dataFill.do" />?method=getReportgjcx&openmode='+openmode1+'&orgid=<c:out value='${organId}'/>&reportId='+reportId+'&targetField1='+aaa+'&targetField2='+bbb+'&targetField3='+ccc;
		document.studentLoanForm.method = "post";
		document.studentLoanForm.submit();
}
    
    function fenye(pageSize,page){
		var openmode1="_self";
		document.studentLoanForm.action='dataFill.do?method=editReportDataEntryForDataInput&canEdit=1&openmode=_self&pageperno='+pageSize+'&page='+page;
		var ccc='dataFill.do?editReportDataEntryForDataInput&canEdit=1&openmode=_self&pageperno='+pageSize+'&page='+page;
		document.studentLoanForm.method = "post";
		document.studentLoanForm.submit();
}
    
</script>
<!-- <script type="text/javascript">
		 window.parent.document.getElementById("ppppp").value= "<fmt:message key="navigation.dataFill.dataFilledit"/>";  
</script> -->
<body onload="init();">

          <table border="0" width="100%" align="left" cellSpacing=0 cellPadding=0  >
            <tr> 
              <td align="left">
				<form action="/studentLoan.do?method=save" method="post" name="studentLoanForm" enctype="multipart/form-data" style="margin: 0;">
				        <input  type="hidden"  name="dataDate" value="<c:out value='${dataDate}'/>">
					    <input  type="hidden" id="reportId" name="reportId" value="<c:out value='${reportId}'/>"> 
						<input  type="hidden" id="organId"  name="organId" value="<c:out value='${organId}'/>">
					    <input  type="hidden" name="filledFlag" value="${filledFlag}" />
					    <input  type="hidden"  name="relateFlag" value="<c:out value='${relateFlag}'/>" />
						<input	type="hidden" name="levelFlag" value="<c:out value='${levelFlag}'/>" />
						
						<input	type="hidden"  id="zhi1" name="zhi1" value="<c:out value='${zhi1}'/>" />
						<input	type="hidden"  id="tField1" name="tField1" value="<c:out value='${tField1}'/>" />
						
						<input	type="hidden"  id="zhi2" name="zhi2" value="<c:out value='${zhi2}'/>" />
						<input	type="hidden"  id="tField2" name="tField2" value="<c:out value='${tField2}'/>" />
						
						<input	type="hidden"  id="zhi3" name="zhi3" value="<c:out value='${zhi3}'/>" />
						<input	type="hidden"  id="tField3" name="tField3" value="<c:out value='${tField3}'/>" />
				<div class="navbar2" name="optionbar">		
					<table  width="100%"  border="0" > 
						<tr >
							<td align="left" valign=middle nowrap >
								<fmt:message
									key="studentloan.report.organ" />:
							</td>
							<td  align="left" >		
									<input type="text"
								style="valign: top: 0;"
								value="<c:out value="${organInfo.short_name}"/>"
								name="organName" readonly />
							</td>
							 
							 
					    <td   align="right"  nowrap   >
					    	 <fmt:message key="view.zd1"/>
					    	 <html:form action="dataFill" method="post" styleId="reportDataForm" style="display: inline-block;" >
								 <html:select property="targetField1" styleId="aaa" style="line-height:20;">
										<html:option value="0"><fmt:message key="system.switch.hint" /></html:option>
			              				<html:options  collection="reportTargetList" property="targetField"  labelProperty="targetName"/>
			                 	</html:select>
			                 </html:form> 
					    </td>
						<td nowrap>
							 <fmt:message key="view.zhi1"/>
							 <input type="text" id="zhis1" value='<c:out value="${zhi1}"></c:out>' name="zhi1" />
						</td>	    
			            <td   align="right" nowrap>
			             <div>
			               <fmt:message key="view.zd2"/>
			               <html:form action="dataFill" method="post" styleId="reportDataForm"  style="display: inline-block;">
								 <html:select property="targetField2" styleId="bbb" style="line-height:20;"   >
										<html:option value="0"><fmt:message key="system.switch.hint" /></html:option>
			              				<html:options  collection="reportTargetList" property="targetField"  labelProperty="targetName"/>
			                 	</html:select>
			                 </html:form> 
			                 </div>
			            </td>       
			             <td width="100%"  nowrap >
			                 <fmt:message key="view.zhi2"/>
			                 <input type="text" id="zhis2" value='<c:out value="${zhi2}"></c:out>' name="zhi2" />
			             </td> 
			              
			             
			              </tr>
						<tr>	
							<td  align="left" valign=middle  nowrap >	
								<fmt:message key="studentloan.report.date" />:
							</td>
					 	<td   align="left" >
						     <input  type="text" style="valign: top: 0;"
								id="date1" 
								value="<c:out value='${dataDate}'/>" name="dataDate"
								readonly="readonly" /> 
						</td>	 
			           
			            <td  align="right" nowrap>
					    	 <fmt:message key="view.zd3"/>
					    	 
					    	 <html:form action="dataFill" method="post" styleId="reportDataForm"   style="display: inline-block;"  >
								 <html:select property="targetField3" styleId="ccc" style="line-height:20;" >
										<html:option value="0"><fmt:message key="system.switch.hint" /></html:option>
			              				<html:options  collection="reportTargetList" property="targetField"  labelProperty="targetName"/>
			                 	</html:select>
			                 </html:form> 
					    </td>
						<td   nowrap>
							 <fmt:message key="view.zhi3"/>
							 <input type="text" id="zhis3" value='<c:out value="${zhi3}"></c:out>' name="zhi3" />
						</td>	    
			             
						 
						 <td align="left" colspan="2">
						 	  <a href="#" class="easyui-linkbutton buttonclass2" data-options="iconCls:'icon-search'" name="ss" onclick="return buttongjcx();" ><fmt:message key='view.zhss'/></a>
						 </td>
						</tr>
						
					 </table>
		     	</div>	
			  </td>	
			  </tr>	 
			<tr>
			 <td>
			    <div class="navbar1" > 
					<table width="100%"  border="0" cellpadding="1" cellspacing="1" >
					<tr>
					<td align="left" width="180">
					<c:if test="${openMode=='_self'}">
									<c:if test="${flagtap!=2}">
										<c:if test="${flagtap!=1}">
											<!--  <input id="btnReturn" name="btnReturn" class="btn"
											type="button" value="<fmt:message key="button.back"/>"
											onclick="window.location.href='datafillreportView.do?method=enterInputSearch1&levelFlag=0' " />-->
											<a href="datafillreportView.do?method=enterInputSearch1&levelFlag=0" class="easyui-linkbutton buttonclass1" data-options="iconCls:'icon-back'"  ><fmt:message key="button.back"/></a>
										</c:if>
									</c:if>
										<c:if test="${flagtap==2}">
										<a href="dataFill.do?method=getReportGuide&levelFlag=1&leveType=1&flagtap=2" class="easyui-linkbutton buttonclass1" data-options="iconCls:'icon-back'" ><fmt:message key="button.back"/></a>
										</c:if>
										<c:if test="${flagtap==1}">
										<a href="dataFill.do?method=getReportGuide&levelFlag=1&flagtap=1" class="easyui-linkbutton buttonclass1" data-options="iconCls:'icon-back'" ><fmt:message key="button.back"/></a>
										</c:if>
										
								</c:if>
								
						 <a href="#" class="easyui-linkbutton c6 " data-options="iconCls:'icon-save'"  onclick="datasort();"><fmt:message key="standard.dataFill.databox"/></a>
					</td>
						
						<td align="left" width="1%" nowrap style="padding-left: 10px;">
								<fmt:message	key="studentloan.report.report" />:  	 
						<td align="left" width="2%">
							<input type="text" 
								style="valign: top;"
								value="<c:out value="${reportName}"/>"
								name="repName" readonly />
						</td>		
						<td align="left" width="2%">
							<input id="chaxundingwei" type="text" size="15">
						</td>
						<td align="left">
							 <a href="#" class="easyui-linkbutton buttonclass2" data-options="iconCls:'icon-search'"  name="ss" onclick="return findInPage();" ><fmt:message key='view.bycx'/></a>
						</td>		
						</tr>
					</table>
					 </div> 
				 </td>
				</tr>	
				<tr>
				  <td>	
				<!--sheng cheng biao tou  -->
						<c:if test="${not empty repDataMap}">
						
						<c:if test="${page.totalRecord >1}">
				<div class="navbar2" > 
					<table width="100%" align="center" cellpadding="1" cellspacing="1">
							<tr align="left">
								<td align="left" valign="top" width="42%">
								<fmt:message key="view.szymjls"/><%-- <input name="pageperno"  style="height: 20;" value="<c:out	value="${page.pageSize}" />" style="width:35px;"  disabled="disabled"   onblur="onSubmit(4,'<c:out value="${openMode}"/>',this.value)"/> --%>
								<c:out value="${page.pageSize}" />
								<fmt:message key="view.dqd"/><c:out	value="${page.pageNo}" /> <fmt:message key="view.ye"/>
							        	<a href="#" onclick="return fenye('<c:out value="${page.pageSize}"/>','1');" ><fmt:message key="chartpage.navigation.homepage"/></a>
									<c:if test="${page.pageNo>1 }">
										<a href="#" onclick="return fenye('<c:out value="${page.pageSize}"/>','<c:out value="${page.pageNo-1 }"/>');" ><fmt:message key="view.syy"/></a>
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
  					[ <a href="#" onclick="return fenye('<c:out value="${page.pageSize}"/>','<c:out value="${num}"/>');"><c:out
														value="${num }" /> </a> ]
  				</c:otherwise>
										</c:choose>
									</c:forEach> 
								    	<a href="#"  onclick="return fenye('<c:out value="${page.pageSize}"/>','<c:out value="${totalPage}"/>');"><fmt:message key="view.wy"/></a>
									<c:if test="${page.pageNo<totalPage}">
										<a href="#"   onclick="return fenye('<c:out value="${page.pageSize}"/>','<c:out value="${page.pageNo+1}"/>');"><fmt:message key="view.xyy"/></a>
									</c:if>
									    <fmt:message key="view.zg"/><c:out value="${totalPage}" />
									    <fmt:message key="view.ye"/>
									    <fmt:message key="view.zg"/>  <c:out value="${page.totalRecord }" /> 
									    <fmt:message key="view.jls"/> <fmt:message key="view.tzz"/>
										<input id="pageNo" type="text" style="width: 40px;height: 20;" />
										<fmt:message key="view.ye"/>
									  
									<!--  <input type="button" style="height: 19;" value="GO" onclick="go()" class="btn" />-->
								<a href="#" class="easyui-linkbutton c1"  style="height:20px" onclick="go();">GO</a></td>
							</tr>
							</table>
					     </div> 
						</c:if>
						</c:if>
				     </td>
				   </tr>
				   <tr>
				    <td>
					 <table id="budgetTbl" width="100%" align="center" cellpadding="1" cellspacing="1" class="table">
						<thead>
							<tr height="40px">
							<td align="center" nowrap class="color">
								<input type="checkbox"  onclick="databoxsort(this);" />
								</td>
								<td align="center" nowrap class="color" style="width:40px;" ><fmt:message
										key="studentloan.report.sortnum" /></td>
										<!--tian chong shu ju  -->
								<c:forEach var="reportTarget" items="${reportTargetList}">
										<td class="color" nowrap 
											id="titleName<c:out value='${reportTarget.targetOrder}' />"
											align="center" style="white-space:nowrap;overflow:hidden;word-break:break-all;"
											width="<c:out value='${reportTarget.editDate}'/>">
											<c:out value="${reportTarget.targetName}" />
											<input type="hidden" id="reporttargetdatatype_<c:out value="${reportTarget.targetField}" />"
											value="<c:out value="${reportTarget.dataType}" />" >
											<input type="hidden" id="reporttargettargetName_<c:out value="${reportTarget.targetField}" />"
											value="<c:out value="${reportTarget.targetName}" />" >		
											<input type="hidden" id="reporttargetrulesize_<c:out value="${reportTarget.targetField}" />"
											value="<c:out value="${reportTarget.rulesize}" />" >											
										</td>
								</c:forEach>
								<td align="center"  class="color" style="white-space:nowrap;overflow:hidden;word-break:break-all;">
								<fmt:message key="button.operation" />
								</td>
							</tr>
						</thead>
						<tbody >
						<c:if test="${not empty repDataMap}">
						
							<c:forEach var="itemSort" items="${repItemSort}">
								<tr>

								<td  align="center" >

									 <input type="checkbox" id="databox_<c:out value="${itemSort.key}"/>" name="databox" value="<c:out value="${itemSort.key}"/>_jilu"/>
									</td>
								

									<td align="center"  ><c:choose>

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
										<input  name="iskey" type="hidden"
										 value="<c:out value="${itemSort.key}"/>">
									</td>
									
									
									
									<c:forEach var="reportTarget" items="${reportTargetList}">
											<td  align="center" >
											    <c:set var="itemDataKey"> 
											       <c:out value="${itemSort.key}" />_<c:out	value="${reportTarget.targetField}" />
												</c:set> 
												<c:set var="targetField">
													<c:out value="${reportTarget.targetField}" />
												</c:set> 
												 <c:set var="itemvalueIndex">
													_<c:out value="${reportId}" /><c:out value="itemvalue" /><c:out value="${reportTarget.targetField}" />
												</c:set> 
												<input type="hidden"
														name="<c:out value="${itemSort.key}"/>_jiludatatype" id="<c:out value="${itemDataKey}"/>_jiludatatypes" align="left"
														 value="<c:out value="${itemDataKey}"/>_datatype:<c:out value="${dtypeMap[itemDataKey]}"/>"/>	
												 <input type="hidden"
														name="<c:out value="${itemSort.key}"/>_jilupflag" align="left"
														 value="<c:out value="${itemDataKey}"/>_pflag:<c:out value="${pflagMap[itemDataKey]}"/>"/>
												<input type="hidden"
														name="<c:out value="${itemSort.key}"/>_jilu"  id="<c:out value="${itemDataKey}"/>_jiluvalue" align="left"
														 value="<c:out value="${itemDataKey}"/>:<c:out value="${repDataMap[itemDataKey]}"/>"/>
												 <c:if test="${reportTarget.fieldType==0}">
												
													<input type="hidden"
														name="<c:out value="${itemDataKey}"/>_id" align="left"
														value="<c:out value="${itemSort.key}"/>" />
													<input id="<c:out value="${itemDataKey}"/>"
														name="<c:out value="${itemDataKey}"/>" align="left"
														style="height: 21;<c:if test="${dtypeMap[itemDataKey]!=null&&dtypeMap[itemDataKey]!=0}">background-color: #FF6666</c:if>" 
													    <c:if test="${ruleMap[itemDataKey]==null && (repDataMap[itemKey] != '' && repDataMap[itemKey] != '0')}">readonly="readonly"</c:if>
														<c:if test="${ruleMap[itemDataKey]!=null}"> dataType="<c:out  value='${itemvalueIndex}' />"  labelText="<c:out value='${reportTarget.targetName}'/>" </c:if>
														type="text"
														<%-- size="<c:choose><c:when test='${reportTarget.dataType!=3}'>7</c:when><c:otherwise>12</c:otherwise></c:choose>" --%>
														size="<c:if test="${reportTarget.dataType!=3}">8</c:if><c:if test="${reportTarget.fieldType==2}">11</c:if> "
														value="<c:out value="${repDataMap[itemDataKey]}"/>" />
												</c:if> 
												
												<c:if test="${reportTarget.fieldType==1||reportTarget.fieldType==3}">
										
													<input type="hidden"
														name="<c:out value="${itemDataKey}"/>_id" align="left"
														value="<c:out value="${itemSort.key}"/>" />
														
													<select id="<c:out value="${itemDataKey}"/>" name="<c:out value="${itemDataKey}"/>" 
													 <c:if test="${ruleMap[itemDataKey]!=null}">  dataType="<c:out  value='${itemvalueIndex}' />"  labelText="<c:out value='${reportTarget.targetName}'/>"
													  </c:if> 
													  style="height: 21;width:142px ; <c:if test="${dtypeMap[itemDataKey]!=null&&dtypeMap[itemDataKey]==1}">background-color: #FF6666</c:if>"  >
														<option selected="selected" value="">
															<fmt:message key="system.switch.hint" />
														<c:forEach items="${dicMap}" var="d">
															<c:if test="${d.key==reportTarget.pkid}">
															<c:forEach items="${d.value}" var="v" begin="0" varStatus="status">
															<%-- <input value="<c:out value="${v.dicName}" />___<c:out value="${repDataMap[itemDataKey]}" />"/> --%>
																	<option value="<c:out value="${v.dicId}" />"
																		<c:if test="${v.dicName==repDataMap[itemDataKey]}" >selected='selected'</c:if>>																		 
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
														<input id="<c:out value="${itemDataKey}"/>"  name="<c:out value="${itemDataKey}"/>"  onClick="WdatePicker();"
														 <c:if test="${ruleMap[itemDataKey]!=null}"> dataType="<c:out  value='${itemvalueIndex}' />"  labelText="<c:out value='${reportTarget.targetName}'/>" </c:if>   
														 align="left" style="height: 21;
														 <c:if test="${dtypeMap[itemDataKey]!=null&&dtypeMap[itemDataKey]==1}">background-color: #FF6666</c:if>" readonly="readonly" type="text" size="7" value="<c:out value="${repDataMap[itemDataKey]}"/>" />
															
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

									<td  align="center" style="white-space:nowrap;overflow:hidden;word-break:break-all;">

									<!--  <input
										type="button"
										value='<fmt:message key="pdf.imageExplain.submit"/>'
										class="btn" alt="<c:out value="${itemSort.key}"/>"
										onclick=" singleUpdate(this,1,'<c:out value='${dataDate}'/>','<c:out value="${itemSort.key}"/>','<c:out value='${reportId}'/>','<c:out value='${organInfo.code}'/>','<c:out value='${organId}'/>'); " />-->
										
										<a href="#"  class="easyui-linkbutton" data-options="iconCls:'icon-save'" style="height:22px;" onclick="singleUpdate(this,1,'<c:out value='${dataDate}'/>','<c:out value="${itemSort.key}"/>','<c:out value='${reportId}'/>','<c:out value='${organInfo.code}'/>','<c:out value='${organId}'/>'); "><fmt:message key="pdf.imageExplain.submit"/></a>
										</td>
								</tr>
							</c:forEach>
							
						</c:if>
						</tbody>
					</table>
					
						<%-- <c:if test="${relateFlag!= '2' }"> --%>
						

					<table width="99%" align="center" cellpadding="1" cellspacing="1">

						<tr>
							<td align="left"  height="30">&nbsp;&nbsp;
								 <c:if test="${openMode=='_blank'}">
								
									<!--  <input id="btnReturn" name="btnReturn" class="btn"
										type="button" value="<fmt:message key="button.close"/>"
										onclick="javascript:window.close() " />-->
								</c:if></td>
						</tr>
					</table> 
				</form>
				

	<c:forEach items="${ruleMap}" var="rule">
		<div id="hint<c:out value='${rule.key}'/>"
			style="position: absolute; display: none; color: black; background: #f0f0f0; border: 1px solid #979797;">
			<table width="150px" height="50px" cellspacing="0" cellpadding="0"
				border="1px solid #979797;" frame=above>
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
     </td>
     </tr>
     </table>
</body>
<script type="text/javascript">

	function databoxsort(cb){
		//alert("111111");
		//var options=$(":[name='databox']");
		var options = document.getElementsByName("databox");
		  	if(cb.checked==true){
				for(var i=0;i<options.length;i++){
					options[i].checked=true;	
				}
			}else{
				for(var i=0;i<options.length;i++){
					options[i].checked=false;
				}
			}  
		}


	function datasort(){
		//alert("111");
		//var options=$(":[name='databox']");
		//alert(options.val());
    	var aa = document.getElementsByName("databox");
    	var ids = "";
    	var valueurl="";
    	var valueurllist = "";
    	var valuestr="";
    	var datatypeurl="";
    	var pflagurl="";
    	var datatypeurl2="";
    	var pflagurl2="";
    	var dataId2 ="";
    	var dataId ="";
    	
    	
    	for(var i=0;i<aa.length;i++){
    		if(aa[i].checked==true){
    			var arra = aa[i].value.split(",");
    			ids += arra[0]+",";
    		}
    	}
    	if(ids==""){
        	alert('<fmt:message key="common.report.selreports1"/>');
        		return false;
        	}
    	
    	for(var j=0;j<ids.split(",").length;j++){
    		var fk = ids.split(",");
    		dataId2 = fk[j].split("_")[0];
    	valuestr =document.getElementsByName(fk[j]);
     	 for(i=0;i<valuestr.length;i++){
    	      	var ab=valuestr[i].value.split("_")[1];
    	      	 var arr=ab.split(":");
    	      	
    	      	if(!document.getElementById(dataId2+"_"+arr[0]).readOnly){
	    	      	if(!doRule(arr[0],arr[1])){	
	    				alert('<fmt:message key="view.tips0"/>'+(j+1)+'<fmt:message key="view.tips1"/>'+getMessages());
	    				return false;
	    			} 
    	      	}
    	      	if(arr[0]=="CJRQ"){
    	      		if(arr[1]!=""){
    	      			valueurl+=fk[j].split("_")[0]+"_"+arr[0]+":"+arr[1]+"@";
    	      		}else{
    	      			valueurl+=fk[j].split("_")[0]+"_"+arr[0]+":"+($("#dataDate").val()).replace("-","").substr(0,6)+"00@";
    	      		}
    	      	}else{
    	      		valueurl+=fk[j].split("_")[0]+"_"+arr[0]+":"+arr[1]+"@";
    	      	} 
    	      	
    	    } 
     	valueurllist+=valueurl+"#";
     	valueurl="";
     	var datatypestr=document.getElementsByName(fk[j].split("_")[0]+"_jiludatatype");
    	var pflagstr=document.getElementsByName(fk[j].split("_")[0]+"_jilupflag");
	    	for(k=0;k<datatypestr.length;k++){
	 	         var aaa=datatypestr[k].value;
	 	        var arrr=aaa.split(":");
	 	        datatypeurl2+=arrr[0]+":"+arrr[1]+",";
	 	      
	 	   }
	   		for(q=0;q<pflagstr.length;q++){
	 	      	var aaa=pflagstr[q].value;
	 	      var arrr=aaa.split(":");
	 	      pflagurl2+=arrr[0]+":"+arrr[1]+",";
	 	      
	 	   }
	   		dataId+=dataId2+"#";
	   		datatypeurl+=datatypeurl2+"#";
	   		pflagurl+=pflagurl2+"#";
	   		datatypeurl2="";
	   		pflagurl2="";
	   		dataId2="";
    	}   
    	valueurllist = valueurllist.substring(0, valueurllist.lastIndexOf("#"));
    	datatypeurl = datatypeurl.substring(0, datatypeurl.lastIndexOf("#"));
    	pflagurl = pflagurl.substring(0, pflagurl.lastIndexOf("#"));
    	dataId = dataId.substring(0, dataId.lastIndexOf("#"));
    	
    	
    	
    	var params={"stflag": 1,"reportDate":$("#dataDate").val(),"reportId":<c:out value='${reportId}'/>,"organCode":<c:out value='${organInfo.code}'/>,"organId":<c:out value='${organId}'/>,
   			 "valueurl":valueurllist,"datatypeurl":datatypeurl,"dataId":dataId,"pflagurl":pflagurl,"method":"singleSaveTEMP11"};
    	$.ajax({ 
			type: "POST", 
			async: false, //ajax
			url: "dataFill.do", 
			data: jQuery.param(params), 
			success: function(result){
				if(result==0){
					alert('<fmt:message key="view.sjbccg"/>');
					for(var i=0;i<ids.split(",").length;i++){
						var abc = ids.split(",");
						var val = abc[i].split("_")[0];
						var options =document.getElementById("databox_"+val);
			      			var tr = options.parentNode.parentNode;//当前行的tr id
			      			var tbody=tr.parentNode;
			      			tbody.removeChild(tr);
						}
				}else if(result==2){
					alert('<fmt:message key="view.sjbcsb"/>');
				}
			}
		});
    }  
	
	
	
	
	        function onSubmit(val,openmode,val2)
			{
	        	<c:if test="${ empty repDataMap}">
	        	alert('<fmt:message key="view.mybcsj"/>');
	        	return false;
	        	</c:if>
	        	var textFields=$("input[type=text]");
	    		for(i=0;i<textFields.length;i++){
	    			
	    			var tiaochuflag=false;
	     	         var aa=textFields[i].value;
	     	         var idstr=textFields[i].id;
	     	         if(idstr!=null&&""!=idstr&&idstr.indexOf("_")>=0){	
	     	        var feildstr=idstr.split("_");
	      	    	var tardatatype=$("#reporttargetdatatype_"+feildstr[1]).val(); //3 wei zi fu 1 wei shu zi
					var tarname=$("#reporttargettargetName_"+feildstr[1]).val();
					var tarlength=$("#reporttargetrulesize_"+feildstr[1]).val();//length
					var pcheck=$("#"+idstr+"_pcheck").val();  //huo de xu yao xiao yan de fang fa
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
					if(1==tardatatype&&aa!=null&&aa!=""){
						var reg = new RegExp("^(-?[0-9]+)(\\.[0-9]+)?$");
							 if(!reg.test(aa)){
						         alert('<fmt:message key="symbol.bracket.left"/>'+tarname+'<fmt:message key="symbol.bracket.right"/><fmt:message key="view.bwszfd"/>!<fmt:message key="view.cxtx"/>');
						         return;
						     }
						}
	     	   }
	    		}
	    		}
	    		for(i=0;i<textFields.length;i++){
	     	         var idstr=textFields[i].id;
	     	         if(idstr!=null&&""!=idstr&&idstr.indexOf("_")>=0){	
	     	        	var feildstr=idstr.split("_");
	    		     	if(val==1){
		        			var datasstr=document.getElementsByName(feildstr[0]+"_jiludatas");
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
		        	if(tiaochuflag){
		        		break;
		        	}
		           }
	     	     }
			   }
					var linkUrl = "<c:out value="${hostPrefix}"/><c:url value='/dataFill.do?method=saveTEMP&pageperno="+val2+"&stFlag="+val+"&openmode="+openmode+
					//	"&delItems=" + delItems + "'/>" +
						"&page=<c:out value='${page.pageNo}'/>&pageCnt=<c:out value='${totalPage}'/>&reportDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organCode=<c:out value='${organInfo.code}'/>&organId=<c:out value='${organId}'/>&dataId=<c:out value='${dataId}'/>";
					document.studentLoanForm.action = linkUrl;
					document.studentLoanForm.method = "post";
					document.studentLoanForm.submit();
			}

    function singleUpdate(obj,val,reportda,dataId,repid,organC,organI) {    	        
    	        var valuestr=document.getElementsByName(dataId+"_jilu");
	        	var datatypestr=document.getElementsByName(dataId+"_jiludatatype");
	        	var pflagstr=document.getElementsByName(dataId+"_jilupflag");
	        	var valueurl="";
	        	var datatypeurl="";
	        	var pflagurl="";
	        	for(i=0;i<valuestr.length;i++){
	      	      	var aa=valuestr[i].value;
	      	      	var arr=aa.split(":");
	      	        var fieldValue = document.getElementById(arr[0]).value;
	      	      	if(!document.getElementById(arr[0]).readOnly){
	      	      		var targetField = arr[0].split("_")[1];
			      	  	if(!doRule(targetField,fieldValue)){	
			      	  		
							alert(getMessages());
							return false;
						};	
	      	      	}
	      	    	valueurl+=arr[0]+":"+fieldValue+"@";
	      	    	var pcheck=$("#"+arr[0]+"_pcheck").val();  //huo de xu yao xiao yan de fang fa
	      	    	var tarstr=arr[0].split(":");
	      	    	var feildstr=tarstr[0].split("_");
	      	    	var tardatatype=$("#reporttargetdatatype_"+feildstr[1]).val(); //3 wei zi fu 1 wei shu zi
					var tarname=$("#reporttargettargetName_"+feildstr[1]).val();
					var tarlength=$("#reporttargetrulesize_"+feildstr[1]).val();//length	      	       
	      	   }
	        	if(val==1){
	        	var datasstr=document.getElementsByName(dataId+"_jiludatas");
	        	for(i=0;i<datasstr.length;i++){
	      	      	var aa=datasstr[i].value;
	      	      	//gai wei bu rang qiang zhi ti jiao
	      	      	if(aa==1){
		      	      		alert('<fmt:message key="view.xytjcw"/>');
		      	  			return;
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
	        			 "valueurl":valueurl,"datatypeurl":datatypeurl,"pflagurl":pflagurl,"dataId":dataId ,"method":"singleSaveTEMP"};
				$.ajax({ 
					type: "POST", 
					async: false, //ajax
					url: "dataFill.do", 
					data: jQuery.param(params), 
					success: function(result){
						if(result==1){
							//shan chu ben hang shu ju
							var tr=obj.parentNode.parentNode;
							var tbody=tr.parentNode;
							tbody.removeChild(tr);
							alert('<fmt:message key="view.sjtjcg"/>');
							//window.location.href=result;
						}else if(result==0){
							alert('<fmt:message key="view.sjbccg"/>');
						}else if(result==2){
							alert('<fmt:message key="view.sjbcsb"/>');
						}else if(result==3){
							alert('<fmt:message key="view.sjtjsb"/>');
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
	        	var aaa=document.getElementById("aaa").value;
	        	var bbb=document.getElementById("bbb").value;
	        	var ccc=document.getElementById("ccc").value;
	        	document.studentLoanForm.action='dataFill.do?method=editReportDataEntryForDataInput&canEdit=1&openmode=_self&page=' + pageNo+'&targetField1='+aaa+'&targetField2='+bbb+'&targetField3='+ccc;
	    		document.studentLoanForm.method = "post";
	    		document.studentLoanForm.submit();
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
									
										$(this).unbind("blur");
										$(this).bind("blur",function(e) {
											var strs = $(this).attr("name").split("_");
											var str= $(this).attr("name");
											var fieldValue = document.getElementById(str).value;
											var targetlow = strs[0];
											var targetField = strs[1];
											$("#"+str+"_jiluvalue").val(str+":"+fieldValue);
											if(!doRule(targetField,fieldValue)){
												alert(getMessages());
											}
											
										});
								});
									$("#refresh").unbind("click");
									$("#refresh").bind("click",function(){
										window.location.href= "dataFill.do?method=editReportDataEntryForDataInput&openmode=_self&levelFlag=<c:out value='${levelFlag}'/>&dataDate=<c:out value='${dataDate}'/>&reportId=<c:out value='${reportId}'/>&organId=<c:out value='${organId}'/>&page=<c:out value='${page.pageNo}'/>";
									});
								$("select").each(function(){
											$(this).unbind("mouseout");
											$(this).bind("mouseout",function(event) {
												var e = event || window.event;
									            var scrollX = document.documentElement.scrollLeft || document.body.scrollLeft;
									            var scrollY = document.documentElement.scrollTop || document.body.scrollTop;
									            var x = e.pageX || e.clientX + scrollX;
									            var y = e.pageY || e.clientY + scrollY;
												rMenu.position.x = x;
												rMenu.position.y =  y;
												var strs = $(this).attr("name").split("_");
												var str= $(this).attr("name");
												var fieldValue = document.getElementById(str).value;
												var targetlow = strs[0];
												var targetField = strs[1];
												
											//	alert(str);
												$("#"+str+"_jiluvalue").val(str+":"+fieldValue);
												if(!doRule(targetField,fieldValue)){
													alert(getMessages());
												} 
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
												var targetField = strs.split("_")[1];
												if(!doRule(targetField,newvalue)){
													alert(getMessages());
												} 
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
							
								
								});
	
								window.onload=function(){     
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


</html>
