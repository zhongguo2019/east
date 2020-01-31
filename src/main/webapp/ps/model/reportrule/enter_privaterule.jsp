<!-- /ps/model/reportrule/enter_privaterule. -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>

<c:set var="flagReport" value="1"/>
<c:set var="flagAdo" value="2"/>

<html>

<head>
<title><fmt:message key="view.blgzpz"/></title>
	 
	 <script type="text/javascript">
function forbiddon(){
	document.onkeydown=function(){if(event.keyCode==8)return false;};
	
}

/* window.parent.document.getElementById("ppppp").value= "<fmt:message key="view.ywzbgzpi"/> >> <fmt:message key="view.yssjgzpz"/>";
 */
$(document).ready(function(){
	var flagReport =<c:out value='${flagReport}' />
	var flagAdo = <c:out value='${flagAdo}' />
	if(flagReport==2){//1单选，2多选
		$("#flagReport1").remove();
	}else{
		$("#flagReport2").remove();
	} 
	if(flagAdo==2){//1单选，2多选
		$("#flagAdo1").remove();
	}else{
		$("#flagAdo2").remove();
	} 
	  getrepTreeXML();
	  getadoTreeXML();
});  

 
 
 function getrepTreeXML(){//报表树
	 $.post("reportView.do?method=repTreeAjax&levelFlag=1",function(data){
			var reportXml = eval(data)[0].reportXml;
			$('#combotreeRepTree').combotree('loadData', reportXml);
		}); 
 }
 
 function getadoTreeXML(){//指标树
	 var reportId = $("#combotreeRepTree").combotree("getValue");
	 param={"reportId":reportId};
	 $.post("integratedQuery.do?method=targetTree",param,function(data){
			var treeXml = eval(data)[0].treeXml;
			$('#combotreeAdoTree').combotree('loadData', treeXml);
		}); 
 }
 
 function getadoTreeXML1(){//报表--指标树
		 var reportId = $("#combotreeRepTree").combotree("getValue");
		 $("#combotreeAdoTree").combotree("clear");
		 param={"reportId":reportId};
		 $.post("integratedQuery.do?method=targetTree",param,function(data){
				var treeXml = eval(data)[0].treeXml;
				$('#combotreeAdoTree').combotree('loadData', treeXml);
			}); 
	 }
 $(document).ready(function(){
	 $("#combotreeRepTree").combotree({
	 	onChange:function(){
	 		getadoTreeXML1();
	 	}    
	 });
 });
</script>
</head>

<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0"  onload="forbiddon();">

<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  
  <tr>
    <td width="100%" valign="top">
    <br>
    <br>
	<form action="reportrule.do?method=inputExtraCondition" method="post" name="reportViewForm" id="reportViewForm">
		<table width="90%" border="0" align="center" cellSpacing="1" cellPadding="0" >
		<input type="hidden" name="reportId" id="reportId" value="<c:out value='${reportId}' />"/>
		<input type="hidden" name="reportName" id="reportName" value="<c:out value='${reportName}' />"/>
		<input type="hidden" name="unitCode" value="<c:out value='${unitCode}' />"/>
			
			<tr>
				<td align="right" nowrap class="TdBgColor1"><fmt:message key="select.report"/></td>
				<td >
			      	<div id="flagReport1">
						<input id="combotreeRepTree" class="easyui-combotree"  value="<c:out value='${reportId}' />"   style="width:380"/>
					</div>
					<div id="flagReport2">
						<input id="combotreeRepTree" class="easyui-combotree" value="<c:out value='${reportId}' />"  multiple style="width:380"/>
					</div>	
				</td>
			</tr>
			<tr>
				<td align="right" nowrap class="TdBgColor1"><fmt:message key="select.zhibiao"/></td>
				<td >
					<input type="hidden" name="targetIds" id="targetIds" value="<c:out value='${targetId}'/>" width="300">
		            <input type="hidden" name="targetNames" id="targetNames" value="" width="300" />
					<div id="flagAdo1">
						<input id="combotreeAdoTree" class="easyui-combotree"  value="<c:out value='${targetId}'/>"  style="width:380"/>
					</div>
					<div id="flagAdo2">
						<input id="combotreeAdoTree" class="easyui-combotree" value="<c:out value='${targetId}'/>" multiple style="width:380"/>
					</div>	
				</td>
			</tr>
			<tr>
			<input type="hidden" value="2" name="flag"/>
 				<td height="22" align="right" nowrap class="TdBgColor1"></td>
 				<input type="hidden" value="<c:out value='${levelFlag}'/>" name="levelFlag" id="levelFlag"/>
					<!--  <td ><input type="button" value='<fmt:message key="view.pz"/>' style="width:80;" onclick="editrule()">-->
					 <td ><a href="#" class="easyui-linkbutton" style="width:80;height: 20px;" onclick="editrule()"><fmt:message key="view.pz"/></a>
			</tr>
			<tr>
				<td width="50" height="16" colspan="2" nowrap class="FormBottom"></td>
			</tr>			
		</table>
	</form>
	<div id="users" style="display:none"></div>	
</td>
</tr>
</table>
</body>

<script type="text/javascript">
var df=document.reportViewForm;	
/* function changeRep(){
	var repid=df.reportId.value;
	if(repid==null){
		return;
	}
	var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/reportView.do?method=repTreeAjax&levelFlag=2";	
	document.all.objTree1.SetXMLPath(url);
   	document.all.objTree1.Refresh();	
}

function changeTree1(){
	changeRep();
	changetarget();
}
	
function changetarget(){
	var repid=df.reportId.value;
	if(repid==null||repid.length>=5||repid==''){
		return;
	}
	var repname=df.reportName.value;
	var url = "<c:out value="${targetTreeURL}"/>&reportId="+repid+"&reportName="+repname;
	document.all.objTree2.SetXMLPath(url);
   	document.all.objTree2.Refresh();	
}
function changeTree2(){
	changetarget();
} */

function editrule(){
	var repid = $("#combotreeRepTree").combotree("getValue");
	var targetIds = $("#combotreeAdoTree").combotree("getValues");
 
	if(repid==null||""==repid){
		alert('<fmt:message key="flowTip.empty.report"/>');
		return;
	}else if(repid.length>=5){
		alert('<fmt:message key="view.ygxzbb"/>');
		return;
	}else if(targetIds==null||""==targetIds){
		alert('<fmt:message key="view.qxzzb"/>');
		return;
	}
	/* df.action='<c:out value="${hostPrefix}" /><c:url value="/reportrule.do" />?method=editPriRule&reportId='+repid+'&targetIds='+targetIds+'&targetNames='+targetNames;
	df.submit(); */
	window.parent.window.OpenPaneltab('<c:out value="${hostPrefix}" /><c:url value="/reportrule.do" />?method=editPriRule&reportId='+repid+'&targetIds='+targetIds+'&levelFlag='+$("#levelFlag").val()+'&flags=2');
	window.parent.window.ClosePanel();
}
function viewrule(){
	var df=document.reportViewForm;	
	df.action='<c:out value="${hostPrefix}" /><c:url value="/reportrule.do" />?method=listBasicRule';
	df.submit();
}
/* function change(){
	changeRep();
	changetarget();
}
var oldHide=Calendar.prototype.hide;
Calendar.prototype.hide = function () {oldHide.apply(this);	change()} */
</script>
</html>
