<!-- /ps/model/repfile/createfileSearch. -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.krm.ps.sysmanage.usermanage.vo.*"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
<%
   User user = (User)request.getSession().getAttribute("user");
   String orgId = user.getOrganId();   
 %>

<c:set var="flagOrgan" value="1"/>
<html>
<head>
<title><fmt:message key="webapp.prefix"/></title>

	 <script language="javascript" type="text/javascript" src="<c:url value='ps/uitl/My97DatePicker/WdatePicker.js'/>"></script>
	 
	 
<script type="text/javascript">
	
	
	$(document).ready(function(){
	      var dayradiochecked = $("#dayradiochecked").val(); 
		 if($("#reporNametstr").val()!=""){
			 alert($("#reporNametstr").val());
			 $("#reporNametstr").val("");
			 
			 window.location.href="repFileAction.do?method=entercreateFileSerch&levelFlag=2&dayradiochecked="+dayradiochecked ;
		 }
		 
	});
	
	function forbiddon(){
		document.onkeydown=function(){if(event.keyCode==8)return false;};
		
	}
	
	
	$(document).ready(function(){
		  getOrganTreeXML();
		  getOrganTreeXML2();
	});  
	
	function getOrganTreeXML(){//机构树
		 $.post("treeAction.do?method=getOrganTreeXML",function(data){
				var treeXml = eval(data)[0].treeXml;
				$('#combotreeOrganTree').combotree('loadData', treeXml);
			}); 
	}
	function getOrganTreeXML2(){//机构树
		 $.post("treeAction.do?method=getOrganTreeXML",function(data){
				var treeXml = eval(data)[0].treeXml;
				$('#combotreeOrganTree2').combotree('loadData', treeXml);
			}); 
	}
  

	function submitform(){
		if(f.reportId.value==null||f.reportId.value==''){
			alert('<fmt:message key="repfile.rep.alert1" />');
			return false;
		}
		$("#show").hide();
		$("#loading").show();
		var reportId="";
		$("#reportId option:selected").each(function(){
			reportId+=$(this).val()+",";
		});
		reportId=reportId.substring(0, reportId.lastIndexOf(","));
		var begindate=$("#begindate").val();
		var enddate=$("#begindate").val(); 
		var organId=$('#combotreeOrganTree').combotree('getValue');
		var condates=$("#condates").val();
		var batch=$("#batch option:selected").val();
	//	alert("reportId:"+reportId+",bdate:"+begindate+",enddate:"+enddate+",organId:"+organId+",condates:"+condates+",batch:"+batch);
		
		var url="<c:out value='${hostPrefix}' /><c:url value='/repFileAction.do?method=createFileSerch&levelFlag=2' />"+"&reportId="+reportId+"&organId="+organId+"&begindate="
				+begindate+"&enddate="+enddate+"&batch="+batch+"&condates="+condates+"&process_type=asynchronize";
		document.repFlFomatForm.action=url;
		document.repFlFomatForm.submit();
	
		/* var params={"levelFlag":"2","reportId":reportId,"organId":organId,"begindate":begindate,"enddate":enddate,"batch":batch,"condates":condates,"process_type":"asynchronize",
				"method":"createFileSerch"
		};
		alert(params);
		$.ajax({
			type:"get",
			dataType:"json",
			url:"repFileAction.do",
			data:jQuery.param(params),
			success:function(result){
				alert(result);
			}
		}); */
		
	}
	
	function submitform2(){
		if(f.reportId2.value==null||f.reportId2.value==''){
			alert('<fmt:message key="repfile.rep.alert1" />');
			return false;
		}
		$("#show").hide();
		$("#loading").show();
		var reportId="";
		$("#reportId2 option:selected").each(function(){
			reportId+=$(this).val()+",";
		});
		reportId=reportId.substring(0, reportId.lastIndexOf(","));
		var begindate=$("#begindate2").val();
		var enddate=$("#enddate2").val();
		var organId=$('#combotreeOrganTree2').combotree('getValue');
		var condates=$("#condates2").val();
		var batch=$("#batch2 option:selected").val();
	//	alert("reportId:"+reportId+",bdate:"+begindate+",enddate:"+enddate+",organId:"+organId+",condates:"+condates+",batch:"+batch);
		
		 
		var url="<c:out value='${hostPrefix}' /><c:url value='/repFileAction.do?method=createFileSerch&levelFlag=2' />"+"&reportId="+reportId+"&organId="+organId+"&begindate="
				+begindate+"&enddate="+enddate+"&batch="+batch+"&condates="+condates+"&process_type=asynchronize";
		document.repFlFomatForm.action=url;
		document.repFlFomatForm.submit();
	
		/* var params={"levelFlag":"2","reportId":reportId,"organId":organId,"begindate":begindate,"enddate":enddate,"batch":batch,"condates":condates,"process_type":"asynchronize",
				"method":"createFileSerch"
		};
		alert(params);
		$.ajax({
			type:"get",
			dataType:"json",
			url:"repFileAction.do",
			data:jQuery.param(params),
			success:function(result){
				alert(result);
			}
		}); */
		
	}
	
	
	 function viewProcess(){
		 	window.location.href="ps/model/repfile/createfileProgress.jsp?_=" + (new Date()).getMilliseconds();
		 } 

</script>
	 
</head>
<body onload="forbiddon()">
 
<div id="loading" style="position: absolute; display: none;padding-top: 40px;">
			<table width="100%" >
			<tr>
				<td  ><fmt:message key="repfile.attr.loading" /></td>
				<td align="left"  >
				<img src="<c:url value='/theme/default/skin01/imgs/loading_2.gif'/>"/>
				</td>
			</tr>
				</table>
</div>

<div class="navbar" height="60"><fmt:message key="navigation.repifle.createfile"/></div>
<table border="0" cellpadding="0" cellspacing="0" id="show">
  <tr>
  <td align="center" >
  	<p>
  	<c:if test="${sessionScope.createFileSchedule != null}">
  		<fmt:message key="repfile.create.schedule"/>:<a href="javascript:viewProcess()"><fmt:message key="button.view"/></a>
  	</c:if>
    </p>
  </td>
  <tr><td align="center" >
  
    <div id="offradio"><input type="radio" id="dayradio" name="radiostr" value="dayradio"  > <fmt:message key="common.list.frequency5"/> <input type="radio" id="monthradio"   name="radiostr" value="monthradio"  checked="checked" /><fmt:message key="common.list.frequency1"/></div>
</td>
  </tr>
  <tr>
    <td width="100%"  valign="top">

 <form action="" method="post" name="repFlFomatForm" onsubmit="return sub();" target="_self">
<br>
        <input id="reporNametstr" type="hidden"  value="<c:out value="${reporNametstr}"/>" />
        <input id="offdayradio"   type="hidden"  value="<c:out value="${offdayradio}"/>" />
        <input id="dayradiochecked"  name="dayradiochecked" type="hidden" value="<c:out value="${dayradiochecked}"/>" />

	<div id="dayreport"   style="display: none;">
	     
	   			<table cellSpacing=1 cellPadding=0 width="600"  border=0 class="TableBGColor">
				 
				 
					<tr>
				  	  <td height="21"  width="25%" align="right"><fmt:message key="studentloan.report.date"/>:&nbsp;&nbsp;</td>
				      <td height="21" >
				    	<%-- <input type="text" name="condates" id="condates" value="<c:out value="${condates}"/>" style="width:100" readonly = "true"  /> --%>
				    	<input id="condates" name="condates" type="text" value="<c:out value='${condates}'/>" style="width:140;" readonly="true" onClick="WdatePicker()"/>
				      </td>
				    </tr>
					<tr>
				  	  <td height="21"  width="25%" align="right"><fmt:message key="view.qszsj"/></td>
				      <td height="21" >
				      <input id="begindate" name="begindate" type="text" value="<c:out value='${begindate}'/>" style="width:140;" readonly="true" onClick="WdatePicker()"/>
				      
				      </td>
				    </tr>
				   	
					<tr>
						<td align="right" ><fmt:message key="view.xzgs"></fmt:message>:&nbsp;&nbsp;</td>
						<td ><select id="batch" name="batch" style="width: 140px">
							<option value="txt">TXT</option>
							<!-- <option value="xml">XML</option> -->
						</select></td>
					</tr>
					<tr>
							   <td align="right" >&nbsp;&nbsp;&nbsp;<fmt:message key="usermanage.organId"/></td>
												 <input type="hidden" name="organId" value="<c:out value='${organId}' />"/>
												<input type="hidden" name="organName"/>
								 <td width="80" align="left" nowrap >
									 <div id="flagOrgan1">
										<input id="combotreeOrganTree" class="easyui-combotree"  value="<%=orgId %>"  style="width:300px"/>
									</div>
								 </td>
					</tr>
					 
				     <tr>
						<td  width="25%" align="right">
					    <fmt:message key="datagather.label.report"/></td>
						<td  id="rep">
							<select id="reportId"  name="reportId" style="width:300px;height:240px" multiple="true" >
						  	 <c:forEach items="${dayrepList}" var="rep">
						  	  	<option value="<c:out value="${rep.pkid}" />"><c:out value="${rep.name}"></c:out></option>
						  	 </c:forEach>
							</select>&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
						</tr>	
						</td>
						</tr>	
						
					<tr align="center">
				
						<td  class="TdBgColor2" colspan=2>
						
						<a href="#" class="easyui-linkbutton" onclick="selectall();"><fmt:message key="reportchart.list.all"/></a>&nbsp;&nbsp;
						<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="submitform();"><fmt:message key="loanDeviation.btn.file"/></a>&nbsp;&nbsp;
						<%-- <a href="#" class="easyui-linkbutton" onclick="openKettle();"><fmt:message key="loanDeviation.btn.kettle"/></a>&nbsp;&nbsp; --%>
				
							<!--  <input type="submit" name="but" value="<fmt:message key="loanDeviation.btn.file"/>" style="width:90px" id=but  />	
							<input type="button"  value="<fmt:message key="loanDeviation.btn.kettle"/>" style="width:110px"  onclick="openKettle();"/>-->	
							</td>
					</tr>
					<tr>
						<td class="FormBottom" colspan="2" height="17"></td>
					</tr>
				</table>
	</div>
	
	<div id="monthreport" style="display: block;" >
	   		
	   			<table cellSpacing=1 cellPadding=0 width="600"  border=0 class="TableBGColor">
				
					<tr>
				  	  <td height="21"  width="25%" align="right"><fmt:message key="studentloan.report.date"/>:&nbsp;&nbsp;</td>
				      <td height="21" >
				    	<%-- <input type="text" name="condates" id="condates" value="<c:out value="${condates}"/>" style="width:100" readonly = "true"  /> --%>
				    	<input id="condates2" name="condates2" type="text" value="<c:out value='${condates}'/>" style="width:140;" readonly="true" onClick="WdatePicker()"/>
				      </td>
				    </tr>
					<tr>
				  	  <td height="21"  width="25%" align="right"><fmt:message key="view.qszsj"/></td>
				      <td height="21" >
				      <input id="begindate2" name="begindate2" type="text" value="<c:out value='${begindate}'/>" style="width:140;" readonly="true" onClick="WdatePicker()"/>&nbsp;--&nbsp;<input id="enddate2" name="enddate2" type="text" value="<c:out value='${enddate}'/>" style="width:140;" readonly="true" onClick="WdatePicker()"/>
				      </td>
				    </tr>
				   	
					<tr>
						<td align="right" ><fmt:message key="view.xzgs"></fmt:message>:&nbsp;&nbsp;</td>
						<td ><select id="batch2" name="batch2" style="width: 140px">
							<option value="txt">TXT</option>
							<!-- <option value="xml">XML</option> -->
						</select></td>
					</tr>
					<tr>
						 <td align="right" >&nbsp;&nbsp;&nbsp;<fmt:message key="usermanage.organId"/></td>
							 	<input type="hidden" name="organId" value="<c:out value='${organId}' />"/>
								<input type="hidden" name="organName"/>
						<td width="80" align="left" nowrap >
							   <div id="flagOrgan1">
									<input id="combotreeOrganTree2" class="easyui-combotree"  value="<%=orgId %>"  style="width:300px"/>
								</div>
						 </td>
					</tr>
						
						
				     <tr>
						<td  width="25%" align="right">
					    <fmt:message key="datagather.label.report"/></td>
						<td  id="rep">
							<select id="reportId2"  name="reportId2" style="width:300px;height:240px" multiple="true" >
						  	 <c:forEach items="${monthrepList}" var="rep">
						  	  	<option value="<c:out value="${rep.pkid}" />"><c:out value="${rep.name}"></c:out></option>
						  	 </c:forEach>
							</select>&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
						</tr>	
						</td>
						</tr>	
						
					<tr align="center">
				
						<td  class="TdBgColor2" colspan=2>
						<a href="#" class="easyui-linkbutton" onclick="selectall2();"><fmt:message key="reportchart.list.all"/></a>&nbsp;&nbsp;
						<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="submitform2();"><fmt:message key="loanDeviation.btn.file"/></a>&nbsp;&nbsp;
						<%-- <a href="#" class="easyui-linkbutton" onclick="openKettle();"><fmt:message key="loanDeviation.btn.kettle"/></a>&nbsp;&nbsp; --%>
				
							<!--  <input type="submit" name="but" value="<fmt:message key="loanDeviation.btn.file"/>" style="width:90px" id=but  />	
							<input type="button"  value="<fmt:message key="loanDeviation.btn.kettle"/>" style="width:110px"  onclick="openKettle();"/>-->	
							</td>
					</tr>
					<tr>
						<td class="FormBottom" colspan="2" height="17"></td>
					</tr>
				</table>
	</div>


<input type="hidden" name="choose" value="new" />
<input type="hidden" name="freq" value="1"/>
</form>
</td>
</tr>
</table>

	<form name="dataMergeForm" action="dataMergeAction.do?method=dataMergeBatch" method="post">
		<input name="date" type="hidden" value="" >
		<input type="hidden" name="repsId" value=""/>
		<input type="hidden" name="organ" value=""/>
	</form>
</body>

<script type="text/javascript">

if($("#offdayradio").val()=="off"){
	$("#offradio").hide();
}

$('input:radio[name="radiostr"]').change(function () {
    if ($("#dayradio").is(":checked")) {
        $("#dayreport").show();
        $("#monthreport").hide();
        $("#dayradiochecked").val("1"); //选中日报单选按钮
    }
    if ($("#monthradio").is(":checked")) {
    	 $("#dayreport").hide();
         $("#monthreport").show();
         $("#dayradiochecked").val("0");//没有选中日报单选按钮
    }
   
});
 
$(document).ready(function(){
	 if($("#dayradiochecked").val()=="1"){
       $("#dayreport").show();
       $("#monthreport").hide();
		 var xradio = document.getElementsByName("radiostr");
         for(var i=0;i<xradio.length;i++){
             if(xradio[i].value == "dayradio"){
                 xradio[i].checked = true;
                 break;
             }
         }
	 }
	 
});


 var f =document.repFlFomatForm;
function selectall(){
	for(var i=0;i<f.reportId.length;i++){
		f.reportId.options[i].selected=true;
	}
}
function selectall2(){
	for(var i=0;i<f.reportId2.length;i++){
		f.reportId2.options[i].selected=true;
	}
}


function openKettle(){
	f.action="repFileAction.do?method=openKettle";
	f.submit();
}


 
function sub(){
	if(f.reportId.value==null||f.reportId.value==''){
		alert('<fmt:message key="repfile.rep.alert1" />');
		return false;
	}
	$("#show").hide();
	$("#loading").show();
	return true;
}
 
</script>
</html>