<!-- /ps/model/reportdefine/targetManage.jsp -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">



<%@ page import="com.krm.ps.util.SysConfig"%>
<%@ page import="com.krm.ps.util.FuncConfig"%>


<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>


<c:set var="flagReport" value="1"/>
<c:set var="flagReport1" value="1"/>
<html>
<head>

<!--  <meta name="viewport" content="width=device-width, initial-scale=1" /> -->
<title><fmt:message key="webapp.prefix" /></title>
<link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>
<script type="text/javascript">

$(document).ready(function(){
	var flagReport =<c:out value='${flagReport}' />
	var flagReport11 =<c:out value='${flagReport1}' />
	if(flagReport==2){//1单选，2多选
		$("#flagReport1").remove();
	}else{
		$("#flagReport2").remove();
	} 
	if(flagReport11==2){//1单选，2多选
		$("#flagReport12").remove();
	}else{
		$("#flagReport22").remove();
	} 
	getrepTreeXML();
	getrepTreeXML1();
});  

function getrepTreeXML(){//报表树
	 $.post("reportView.do?method=repTreeAjax&levelFlag=0",function(data){
			var reportXml = eval(data)[0].reportXml;
			$('#combotreeRepTree').combotree('loadData', reportXml);
		}); 
}
 
 function getrepTreeXML1(){//报表树
	 $.post("reportView.do?method=repTreeAjax&levelFlag=1",function(data){
			var reportXml = eval(data)[0].reportXml;
			$('#combotreeRepTree1').combotree('loadData', reportXml);
		}); 
 }


$(function() {
	$('#one1').click(function() {
		var size = $('#left>option:selected').size();
		if (size != 0) {
			$('#left > option:selected').appendTo($('#right'));
			$('#right > option:selected').attr("selected", "");
		} else {
			$('#left>option:first-child').appendTo($('#right'));
		}
	});
	$('#all1').click(function() {
		$('#left > option').appendTo($('#right'));
	});
	$('#one2').click(function() {
		var size = $('#right>option:selected').size();
		if (size != 0) {
			$('#right > option:selected').appendTo($('#left'));
			$('#left > option:selected').attr("selected", "");
		} else {
			$('#right>option:first-child').appendTo($('#left'));
		}
	});
	$('#all2').click(function() {
		$('#right > option').appendTo($('#left'));
	});

	$("#left").dblclick(function() {
		$('#left > option:selected').appendTo($('#right'));
		$('#right > option:selected').attr("selected", "");
	});

	$("#right").dblclick(function() {
		$('#right > option:selected').appendTo($('#left'));
		$('#left > option:selected').attr("selected", "");
	});
});

function check_all(){
	   arr = document.getElementsByName('target2');
	   for(i=0;i<arr.length;i++){
	      arr[i].checked = true;
	   }
	   arr1 = document.getElementsByName('target1');
	   for(i=0;i<arr1.length;i++){
	      arr1[i].checked = true;
	   }
 }
 
function rel_all(){
	   arr = document.getElementsByName('target1');
	   for(i=0;i<arr.length;i++){
	      arr[i].checked = false;
	   }
	   arr2 = document.getElementsByName('target2');
	   for(i=0;i<arr2.length;i++){
	      arr2[i].checked = false;
	   }
} 

function saveTargets() {
	var arr1 = "";
	var arr2 = "";
	
	var arr3 = "";
	var arr4 = "";
/* 	$("#left option").each(function(){
		arr = arr + $(this).val() + ",";
	}); */
	/* $("input[name='target1']").each(function(){
		if(!$(this).attr("checked")) {
			arr1 = arr1 + $(this).val() + ",";
		}
	}); */
	
	/* $("input[name='target2']").each(function(){
		if($(this).checked) {
			arr2 = arr2 + $(this).val() + ",";
		}
	}); */
	var a = document.getElementsByName("target1");
	for(var i=0;i<a.length;i++){
		if(a[i].checked==false){
			arr1 =arr1+ a[i].value+ ",";
		}
	}
	
	var aa = document.getElementsByName("target2");
	for(var i=0;i<aa.length;i++){
		if(aa[i].checked==true){
			arr2 =arr2+ aa[i].value+ ",";
		}
	}
	var bb = document.getElementsByName("radio1");
	var cc = document.getElementsByName("radio2");
	for(var i=0;i<bb.length;i++){
		if(bb[i].checked==true){
			arr3 =bb[i].value;
		}
	}
	for(var i=0;i<cc.length;i++){
		if(cc[i].checked==true){
			arr4 =cc[i].value;
		}
	}
	
	/* arr3= $("input[name='radio1'][checked]").val();
	arr4= $("input[name='radio2'][checked]").val(); */
	
	$("#tTargets1").val(arr1);
	$("#tTargets2").val(arr2);
	$("#Rradio1").val(arr3);
	$("#Rradio2").val(arr4);
	
	
	$("#targetForm").submit();
}
    function delStrdreport(){
    	var reportId = $("#combotreeRepTree").combotree("getValue");
    	var reportId1 = $("#combotreeRepTree1").combotree("getValue");
    	df.action = '<c:out value="${hostPrefix}" /><c:url value="/reportTargetAction.do" />?method=delstrdReport&reportId='+reportId+'&reportId1='+reportId1;
    	df.submit();
    }

    function forbiddon(){
    	document.onkeydown=function(){if(event.keyCode==8)return false;};
    	
    }
</script>

</head>

<body onload="forbiddon()" class="easyui-layout" style="overflow: auto;"> 
<div  data-options="region:'north',border:false,title:' <fmt:message key="navigation.dataFill.buluTemplate"/> '" style="width:100%;padding:0px;h">
<div class="pbox">
<fmt:message key="report.tips3"/>
</div>
<html:form action="reportView.do?method=inputExtraCondition" method="post" onsubmit="return reportCheck();" >
		<table width="90%" border="0" align="center" cellSpacing="1" cellPadding="3" class="TableBGColor">
		<input type="hidden" name="reportId" id="reportId" value="<c:out value='${reportId}' />"/>
		<input type="hidden" name="reportId1" id="reportId1" value="<c:out value='${reportId1}' />"/>
		<input type="hidden" name="targetFlag" id="targetFlag" value="1"/>
		<input type="hidden" name="reportName"/>
		<input type="hidden" name="organId" value="<c:out value='${organId}' />"/>
		<input type="hidden" name="organName"/>
		<input type="hidden" name="unitCode" value="<c:out value='${unitCode}' />"/>
		<c:if test="${param.method=='enterViewSearch'}">
            <input type="hidden" name="mode" value="display"/>
		</c:if>
       	<c:if test="${param.method=='enterInputSearch'}">
	        <input type="hidden" name="mode" value="edit"/>
		</c:if>
		<tr>
				<td   width="50" align="right" nowrap ><fmt:message key="select.template"/>:</td>
				<td>
			      	<div id="flagReport1">
						<input id="combotreeRepTree" class="easyui-combotree"  value="<c:out value='${reportId}'/>"   style="width:380px"/>
					</div>
					<div id="flagReport2">
						<input id="combotreeRepTree" class="easyui-combotree" value="<c:out value='${reportId}'/>" multiple style="width:380px">
					</div>	
				</td>
 			<td></td>
 			</tr>
			<tr>
				<td   width="50" align="right" nowrap ><fmt:message key="select.model"/>:</td>
				<td>
			      	<div id="flagReport12">
					<input id="combotreeRepTree1" class="easyui-combotree"  value="<c:out value='${reportId1}'/>"   style="width:380px"/>
				</div>
				<div id="flagReport22">
					<input id="combotreeRepTree1" class="easyui-combotree" value="<c:out value='${reportId1}'/>" multiple style="width:380px">
				</div>	
				</td>
				<tr>
				<td   align="left" ></td>
				<td   align="left" >
				<a href="#" class="easyui-linkbutton  buttonclass3"  onclick="viewReport('_self','yes')"><fmt:message key="button.template.edit"/></a>
				<!--  <input  align="middle" type="button" value="<fmt:message key="button.template.edit"/>" style="width:100;" onclick="viewReport('_self','yes')">-->
				
				<a href="#" class="easyui-linkbutton "  data-options="iconCls:'icon-save'" onclick="saveTargets();"><fmt:message key='button.save' /></a>

                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-selectall'" onclick="check_all();return false;"><fmt:message key='button.selectall' /></a>
	          
                <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-unselectall'" onclick="rel_all();return false"><fmt:message key='button.releaseall' /></a>
				
				<c:if test="${template.name != model.name}">
			    	<c:if test="${Rradio1 !=t.targetField }">
			    	<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'" onclick="delStrdreport();"><fmt:message key='strdreport.del'/></a>
					  <!--  <input type="button" class="btn" id="del" value='<fmt:message key="strdreport.del"/>' onclick="delStrdreport();"/> -->
					</c:if>
			    </c:if>
	
				</td>
				</tr>
 			</tr>
		</table>
	</html:form>
		</div>
	<c:if test="${targetList==null}">
		 
	</c:if>
		   
		   
		    <c:if test="${targetList!=null}">

				<div data-options="region:'west',split:true,border:false"  style="width:20%; height: 60%; "></div>
				
				<div data-options="region:'center',split:true,border:false" style="width: 80%;height: 60%;">
				
				 <div class="easyui-layout" data-options="fit:true"> 
				
				<div data-options="region:'west',split:true"  style="width:40%; height: 60%; ">


	




<form action="<c:url value='reportTargetAction.do?method=saveTemplateTargets'></c:url>" method="post" name="targetForm" id="targetForm" style="margin: 0;">
				<input type="hidden" value="<c:out value='${reportId}'></c:out>" name="reportId" id="reportId"//>
				<input type="hidden" value="<c:out value='${reportId1}'/>" name="reportId1" id="reportId1"//>
				<input type="hidden" name="pkidupdate" id="pkidupdate" value="<c:out value='${pkidstrd}' />"/>
				<input type="hidden"  value="" name="tTargets1" id="tTargets1"/>
					<input type="hidden"  value="" name="tTargets2" id="tTargets2"/>
				<input  type="hidden" value="" name="Rradio1" id="Rradio1"/>
					<input  type="hidden" value="" name="Rradio2" id="Rradio2"/>
				<c:if test="${template.name != model.name}">
				   <input type="hidden" value="2" name="tableas" id="tableas"/>
				</c:if>
				<c:if test="${template.name == model.name}">
					<input type="hidden" value="1" name="tableas" id="tableas"/>
				</c:if>
		<div class="menucontent" >
		
			<table  width="100%" >

											<tr>

												<td width="100%"  valign="top" colspan="3"   align="center" ><label><font color="#0673DA"><c:if test="${template.name != model.name}"><fmt:message key="view.xzglzd"/></c:if> &nbsp;&nbsp;<c:out value="${template.name}"/></font></label>

													<c:forEach var="t" items="${templateTargets}">
														<tr>
														
															<c:if test="${template.name != model.name}">
															<td width="25%" align="right">
															  <input name="radio1" type="radio" value="<c:out value='${t.targetField}' />" <c:if test="${Rradio1==t.targetField }"><c:out value='checked="checked"'/></c:if>/></input>
															  </td>
															  <td width="30%" align="right">
															  		<c:out value='${t.targetName}'/>
															  </td>
															</c:if> 
															<c:if test="${template.name == model.name}">
															<td width="55%" align="right">
															    <c:out value='${t.targetName}'/>
															 </td>
														    </c:if> 
														
															<td width="45%" style="padding-left: 40" >
																<a href="<c:out value='reportTargetAction.do?method=delTemplateTarget&targetId=${t.targetField}&reportId=${reportId}&reportId1=${reportId1}'/>"><img src="<c:url value='/ps/framework/images/del.gif'/>" name="<c:out value='${t.pkid}' />" border="0" width="25" height="25"/></a>
															</td>
														</tr>
													</c:forEach>
												</td>
												</tr>
											</table>

		</div>
			
	</div>
	<div data-options="region:'center',split:true" style="width: 40%;height: 60%;">
						<table width="100%" >									

												<tr>
													<td align="center" colspan="2">
													<label><font color="#0673DA"><c:if test="${template.name != model.name}"><fmt:message key="view.xzglzd"/></c:if> &nbsp;&nbsp;&nbsp;<c:out value="${model.name}"/></font></label>
														<c:forEach var="t" items="${targetListChecked}">
															<tr>
																<td align="right" style="padding-left: 60px;" nowrap>
																	<c:if test="${template.name != model.name}">
																       <input name="radio2" type="radio" value="<c:out value='${t.targetField}'/>" <c:if test="${Rradio2==t.targetField }">checked="checked"</c:if> /></input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																    </c:if>
																	
																</td>
																<td align="left">
																	<input name="target1" type="checkbox" value="<c:out value='${t.targetField}' />"  checked="checked"/><c:out value='${t.targetName}'/></input>
																</td>
															</tr>
														</c:forEach>
														<c:forEach var="t" items="${targetList}">
															<tr>
																<td align="right" style="padding-left: 60px;" nowrap>
																<c:if test="${template.name != model.name}">
																    <input name="radio2" type="radio" value="<c:out value='${t.targetField}' />" <c:if test="${Rradio2==t.targetField }">checked="checked"</c:if> /></input>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
																</c:if> 
																	
																	<%-- <input type="hidden" value="<c:out value='${t.pkid}'/>"> --%>
																</td>
																<td align="left">
																	<input name="target2" type="checkbox" value="<c:out value='${t.targetField}'/>" ><c:out value="${t.targetName}" /></input>
																</td>
															</tr>
														</c:forEach>
												</td>
											</tr>
										</table>
	</div>	


	
<div data-options="region:'east',split:true" style="width: 20%; display:none;"></div>
<div data-options="region:'south',split:true" style="width: 40%; display:none;"></div>

</form>
</div>
</div >	
</c:if>

</body>

<script type="text/javascript">


var df=document.reportViewForm;

<c:if test="${reportBalance!=null}">
	alert("<c:out value='${reportBalance}'/>");
</c:if>

<c:if test="${viewResult == '1'}">
	alert("<fmt:message key="reportview.alert.noformat"/>");
</c:if>


function viewReport(tg,sr){
	var vReportId = $("#combotreeRepTree").combotree("getValue");
	var reportId1 = $("#combotreeRepTree1").combotree("getValue");
	if (vReportId==""||vReportId == null){
		alert("<fmt:message key="js.validate.modeltarget"/>");
		return;
	}

	if (reportId1==""||reportId1 == null){
		alert("<fmt:message key="js.validate.modeltarget"/>");
		return;
	} 
if((reportId1==""||reportId1 == null) && (vReportId !=""||vReportId != null)){
	alert("<fmt:message key="js.validate.model"/>");
	return;
} 
	 if((vReportId < 10000) &&(reportId1 >= 10000)){
		alert("<fmt:message key="js.validate.model"/>");
		return false;
	} 
    if (vReportId >= 10000 || reportId1 >= 10000){
    	alert("<fmt:message key="js.validate.modeltarget"/>");
		return false;
	}
 
    
	df.action="reportTargetAction.do?method=listTarget&openmode="+tg+"&reportId="+vReportId+"&reportId1="+reportId1;
	df.target=tg;
	df.submit();
}

/* function setUnitCode(){
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

function changeOrg(){
    var oDate = document.reportViewForm.dataDate;
	var vDate = oDate.value;
	var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/treeAction.do?method=getOrganTreeXML&date="+vDate;
	document.all.objTree1.SetXMLPath(url);
   	document.all.objTree1.Refresh();	
}


function changeRep(){
    // var oDate = document.reportViewForm.dataDate;
	// var vDate = oDate.value;
	var oOrganId = document.reportViewForm.organId;
	var vOrganId = oOrganId.value;
	var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/reportView.do?method=repTreeAjax&paramdate="+vDate+"&paramorgan="+vOrganId;	
	<c:if test="${systemName=='1104'}">
		var url = "<c:out value="${hostPrefix}"/>/slsint/reportView.do?method=repTreeAjax&paramdate="+vDate+"&paramorgan="+vOrganId;
	</c:if>
	<c:if test="${systemName=='billExchange'}">
		var url = "<c:out value="${hostPrefix}"/>/slspj/reportView.do?method=repTreeAjax&paramdate="+vDate+"&paramorgan="+vOrganId;
	</c:if>
	document.all.objTree2.SetXMLPath(url);
   	document.all.objTree2.Refresh();	
}

function reportCheck(){
	oReportId = df.reportId;
    vReportId = oReportId.value;
    var reportId1=df.reportId1.value;
	if (vReportId==""||vReportId == null){
       alert("<fmt:message key="js.validate.report"/>");
       return false;
	}
	if (vReportId >= 10000){
		return false;
	}
	
	if (vReportId >= 10000){
		return false;
	}

}

function change(){
	changeOrg();
	changeRep();
	setReportList();
}

function changeTree1(){
	changeRep();
}

function changeTree2(){}

function setReportUnit(){
	<c:if test="${unitCodeFlag == 1}">
	var ajaxOption = {method:'get',onSuccess:setReportUnitResponse};
	var url = "<c:out value='${hostPrefix}'/><c:url value='/reportView.do?method=getReportUnit&reportID='/>" + document.all.objTree2.getCurrentItem();
	var doAjax = new Ajax.Request(url,ajaxOption);
	</c:if>
}

function setReportUnitResponse(ajaxRequest){
	var unitCode = ajaxRequest.responseText;
	if (unitCode == "0"){
		return;
	}
	var selObj = document.all.selUnit;
	for (var i = 0 ; i < selObj.options.length ; i++){
		if (selObj.options[i].value == unitCode){
			selObj.selectedIndex = i;
			break;
		}
	}
}

var oldHide=Calendar.prototype.hide;
Calendar.prototype.hide = function () {oldHide.apply(this);	change()} */
</script>

<script type="text/javascript">
	//changeDate();  <!-- set checkbox -->

function changeDate(){
	var jdate = document.getElementById("dataDate").value;
	jdate = jdate.substring(5,7);
	setCheckbox(jdate);
}

function setCheckbox(month){
	var str = "";
	if(month == "03" || month == "09"){
		str += "<input type='checkbox' checked='checked' onclick='checkfeq(this);' name='frequency1' value='1' ><fmt:message key='common.list.frequency1'/>";
		str += "<input type='checkbox' checked='checked' onclick='checkfeq(this);' name='frequency2' value='2' ><fmt:message key='common.list.frequency2'/>";
	}else if(month == "06"){
		str += "<input type='checkbox' checked='checked' onclick='checkfeq(this);' name='frequency1' value='1' ><fmt:message key='common.list.frequency1'/>";
		str += "<input type='checkbox' checked='checked' onclick='checkfeq(this);' name='frequency2' value='2' ><fmt:message key='common.list.frequency2'/>";
		str += "<input type='checkbox' checked='checked' onclick='checkfeq(this);' name='frequency3' value='3' ><fmt:message key='common.list.frequency3'/>";
	}else if(month == "12"){
		str += "<input type='checkbox' checked='checked' onclick='checkfeq(this);' name='frequency1' value='1' ><fmt:message key='common.list.frequency1'/>";
		str += "<input type='checkbox' checked='checked' onclick='checkfeq(this);' name='frequency2' value='2' ><fmt:message key='common.list.frequency2'/>";
		str += "<input type='checkbox' checked='checked' onclick='checkfeq(this);' name='frequency3' value='3' ><fmt:message key='common.list.frequency3'/>";
		str += "<input type='checkbox' checked='checked' onclick='checkfeq(this);' name='frequency4' value='4' ><fmt:message key='common.list.frequency4'/>";
	}else{
		str += "<input type='checkbox' checked='checked' onclick='checkfeq(this);' name='frequency1' value='1' ><fmt:message key='common.list.frequency1'/>";
	}

	str += "<input type='checkbox' checked='checked' onclick='checkfeq(this);' name='frequency5' value='5' ><fmt:message key='common.list.frequency5'/>";
	
	var div = document.getElementById("freq");
	div.innerHTML = str;
}

</script>

<script language='javascript'><!--

	var arrreport = new Array();
	<c:forEach var="report" items="${reportList}" varStatus="status">
		arrreport[<c:out value='${status.index}'/>] = new Array("<c:out value='${report.pkid}'/>",
		"<c:out value='${report.name}'/>",
		"<c:out value='${report.code}'/>",
		"<c:out value='${report.reportType}'/>",		
		"<c:out value='${report.frequency}'/>")
	</c:forEach>	

	var arr1 = new Array();
	<c:forEach var="report" items="${reportList}" varStatus="status">
		arr1[<c:out value='${status.index}'/>] = new Array("<c:out value='${report.pkid}'/>",
		"<c:out value='${report.name}'/>",
		"<c:out value='${report.code}'/>",
		"<c:out value='${report.reportType}'/>",		
		"<c:out value='${report.frequency}'/>")
	</c:forEach>		
	

 function getPosition( obj )

     { 

     var top = 0,left = 0;

      do 

{

    top += obj.offsetTop;

        left += obj.offsetLeft;

     }

while ( obj = obj.offsetParent );      

      var arr = new Array();      

      arr[0] = top;

      arr[1] = left;       

      return arr;    

   }


function createMenu( textBox, menuid ){ 

       if( document.getElementById( menuid ) == null )

       {

         var left_new=getPosition( textBox )[1];

         var top_new=getPosition( textBox )[0];    

         var newControl = document.createElement("div"); //???      

         newControl.id = menuid;        

         document.body.appendChild( newControl );       

         newControl.style.position = "absolute"; 

         newControl.style.border = "solid 1px #000000";

         newControl.style.backgroundColor = "#FFFFFF";

         newControl.style.width = "360px"; //????

         newControl.style.left = "40px";           //??

         newControl.style.top =  "35px";  //????????2?????JS?????????   
		var res = createMenuBody(textBox);

		var newNode = document.createElement("p");

		newNode.innerHTML = res;

		newControl.appendChild(newNode);



        return newControl;

       } else {

		//document.all.suggest.style.display="block";

		document.getElementById(menuid).style.display="block";
		
		document.getElementById(menuid).innerHTML="";

//		var res = createMenuBody( document.getElementById(textBox).getAttribute("value"));
		var res = createMenuBody(textBox);

		var newNode1 = document.createElement("p");

		newNode1.innerHTML = res;

		document.getElementById(menuid).appendChild(newNode1);


			return document.getElementById( menuid );
	   }
    }

  function getSearchResult( textBox )
    {
    var key = document.getElementById(textBox).value;
	var arr = new Array();
    var k = 0;
    if(textBox == "txt1"){
    	for(i = 0; i < arr1.length; i++){
		if(arr1[i][1].indexOf(key) > -1 || arr1[i][1].indexOf(key.toUpperCase()) > -1  || arr1[i][1].indexOf(key.toLowerCase()) > -1){
			arr[k] = arr1[i];			
			k++;
		} else{
			continue;
		}	
	}
    }else{
    	for(i = 0; i < arr1.length; i++){
		if(arr1[i][2].indexOf(key) > -1 || arr1[i][2].indexOf(key.toUpperCase()) > -1  || arr1[i][2].indexOf(key.toLowerCase()) > -1){
			arr[k] = arr1[i];			
			k++;
		} else{
			continue;
		}	
	}
    }

 return arr;

}


  function createMenuBody( textBox )

    {

      var result = "";

      var j = 0;

      arr = getSearchResult( textBox ); //???????

       //????????

      if(arr.length > 10)

      {

        j = 10;

      }

      else

      {

        j = arr.length;

      }

	if(textBox == 'txt1'){
		for ( var i = 0; i < j; i++ ) {
			result += "<table border=\"0\" cellpadding=\"2\" cellspacing=\"0\" id=\"menuItem"+(i+1)+"\" onmouseover=\"forceMenuItem( "+(i+1)+");\" width=\"100%\" onclick=\"givNumberForMouse("+i+")\"><tr><td class=\"TdBGColor1\"  align=\"left\">" + arr[i][1] + "</td><td class=\"TdBGColor1\"  align=\"right\">" + (i+1) + " </td></tr></table>";
		}
	}else{
		for ( var i = 0; i < j; i++ ) {
			result += "<table border=\"0\" cellpadding=\"2\" cellspacing=\"0\" id=\"menuItem"+(i+1)+"\" onmouseover=\"forceMenuItem( "+(i+1)+");\" width=\"100%\" onclick=\"givNumberForMouse("+i+")\"><tr><td class=\"TdBGColor1\"  align=\"left\">" + arr[i][2] + "--" + arr[i][1] + "</td><td class=\"TdBGColor1\"  align=\"right\">" + (i+1) + " </td></tr></table>";
		}
	}
    return result;
 	}

var menuFocusIndex = 0;
function forceMenuItem(index){

//var menuFocusIndex = 0;

lastMenuItem = document.getElementById( "menuItem" + menuFocusIndex )

       if ( lastMenuItem != null )

       {

         //??????

         lastMenuItem.style.backgroundColor="white";       

         lastMenuItem.style.color="#000000";

       }

       var menuItem = document.getElementById( "menuItem" + index );

       if ( menuItem == null )

        {

          document.getElementById("txt1").focus(); 

        }

        else 

        {

         menuItem.style.backgroundColor = "#5555CC";

         menuItem.style.color = "#FFFFFF";

         menuFocusIndex = index;

      }

}


function forceMenuItem(index,textBox){

//var menuFocusIndex = 0;

lastMenuItem = document.getElementById( "menuItem" + menuFocusIndex )

       if ( lastMenuItem != null )

       {

         //??????

         lastMenuItem.style.backgroundColor="white";       

         lastMenuItem.style.color="#000000";

       }

       var menuItem = document.getElementById( "menuItem" + index );

       if ( menuItem == null )

        {
		if(textBox == "tet1"){
			document.getElementById("txt1").focus(); 
		}else{
			document.getElementById("txt2").focus(); 
		}

        }

        else 

        {

         menuItem.style.backgroundColor = "#5555CC";

         menuItem.style.color = "#FFFFFF";

         menuFocusIndex = index;

      }

}


function catchKeyBoard(e,textBox, menuid){

	var keyNumber = event.keyCode;
	var txtcontent = document.getElementById(textBox).value;
	if(keyNumber =='40'){ 
       if(menuFocusIndex == 10){
         return true;
       }else if (menuFocusIndex == null){     
           forceMenuItem( 1, textBox);
           givNumber( 0 );
       } else{
          forceMenuItem( menuFocusIndex+1, textBox); 
          givNumber(menuFocusIndex-1);
		}
	} else if(keyNumber == '38'){  
        if(menuFocusIndex == 1){
           forceMenuItem(menuFocusIndex-1, textBox); 
         } else{
          forceMenuItem(menuFocusIndex-1, textBox); 
          givNumber(menuFocusIndex-1);
        } 
   } else if(txtcontent == null || txtcontent == "" || keyNumber == '13' || keyNumber == '27'){
   		suggestclose();
   }else {
		createMenu(textBox, menuid); 
   } 
}

 function givNumber( index )

     {

       document.getElementById("txt1").value = arr[index][1];
       
       document.getElementById("txt2").value = arr[index][2];  
       
       document.getElementById("reportId").value = arr[index][0];

       document.getElementById("txtTree2").value = arr[index][1];       

       document.getElementById("txt1").focus();     
       
       changeRep();  
	   
	   //document.all.suggest.style.display="none";

     }

 function givNumberForMouse( index )

     {
     
       document.getElementById("txt1").value = arr[index][1];

       document.getElementById("txt2").value = arr[index][2];
       
       document.getElementById("reportId").value = arr[index][0];
       
       document.getElementById("txtTree2").value = arr[index][1];       

       document.getElementById("txt1").focus(); 

	   document.all.suggestName.style.display="none";
	   
	   document.all.suggestCode.style.display="none";	   
	   
	   
	   changeRep();
	   
//		document.getElementById("suggest").innerHTML="";

     }
     
    var typerep = new Array();
    
//	changeType();

function changeType(){

		var type = document.forms[0].reportType.value;
	
		typerep = new Array();
		var j = 0;
		for(i=0; i< arrreport.length; i++){
			if(arrreport[i][3] == type){
				typerep[j] = arrreport[i];
				j++;
			}else if(type == 0){
				typerep[j] = arrreport[i];
				j++;
			}else {
				continue;
			}
		}
		checkfeq(this);
	}    

 var freqArr = new Array();
 function checkfeq(freq){
 	var freqs = $("#freq > input");
	var cnt = 0;
	freqArr = new Array();
	freqs.each(function(i){
		if(this.checked){
			freqArr[i] = this.value;
			cnt++;
		}
	});
	if(cnt == 0){
		alert("<fmt:message key="batch.checkdata.form.checkfreq"/>");
		freq.checked = true;
		freqArr[0] = freq.value;
 	}
	filterReport(freqArr);
 }	 
 
function filterReport(freqArr_){
	arr2 = new Array();
	var k = 0;
	for(i = 0; i < freqArr_.length; i++){
		for(j = 0; j < typerep.length; j++){
			if(freqArr_[i] == typerep[j][4]){
				arr2[k] = typerep[j];
				k++;
			}else{
				continue;
			}
		}
	
	}
	var m = 0;
	arr1 = new Array();
	for(i = 0; i < arr2.length; i++){
			arr1[m] = arr2[i];
			m++;
	}
	return arr1;
} 
 
 function setReportList(){
    var oDate = document.reportViewForm.dataDate;
	var vDate = oDate.value;
    var oOrganId = document.reportViewForm.organId;
	var vOrganId = oOrganId.value;	
	var options={onSuccess:setReportUnitResponse}
	var url='<c:out value="${hostPrefix}" /><c:url value="/reportView.do" />?method=getReportListAjax&paramDate='+vDate+"&paramOrgan=" + vOrganId;
	pu = new Ajax.Updater($("users"),url,options);
}

var allRep = new Array();
var repLen = 0;
function setReportUnitResponse(ajaxRequest){
	var struts = ajaxRequest.responseText;
	var reports = eval(struts);
	arr1 = new Array();
	arrreport = new Array();
	for(i = 0; i < reports.length; i++){
		arr1[i] = new Array(reports[i].id,reports[i].name,reports[i].code,reports[i].reportType,reports[i].frequency);
		arrreport[i] = new Array(reports[i].id,reports[i].name,reports[i].code,reports[i].reportType,reports[i].frequency);
	}
	changeType();
}

 function darkSearch(){
 var displayStyle = document.all.darkSearch.style.display;
 if(displayStyle == 'none'){
 	document.all.darkSearch.style.display = "block";
 }else{
 	document.all.darkSearch.style.display = "none";
 }
 }

 function suggestclose(){
  var suggestNameStyle = document.all.suggestName.style.display;
  var suggestCodeStyle = document.all.suggestCode.style.display;  
  if(suggestName != 'none' || suggestCodeStyle != 'none'){
 	document.all.suggestName.style.display = "none";
 	document.all.suggestCode.style.display = "none"; 
 	}
 }

</script>



</html>
