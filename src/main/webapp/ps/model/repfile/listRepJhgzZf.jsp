<!-- /ps/model/reportrule/list_basicrule. -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
<%@ page import="com.krm.ps.sysmanage.usermanage.vo.*"%>

 

<html>
<script type="text/javascript">
function forbiddon(){
	document.onkeydown=function(){if(event.keyCode==8)return false;};
	
}
</script>
    <head>
        <title><fmt:message key="webapp.prefix"/></title>

<link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>
 <link rel="stylesheet" type="text/css" media="all" href="<c:url value='${displaytag12Path}/displaytag.css'/>" />
  <script language="javascript" type="text/javascript" src="<c:url value='ps/uitl/My97DatePicker/WdatePicker.js'/>"></script>

 </head>					


<script language=JavaScript>
$(document).ready(function(){

	  getOrganTreeXML();
	  
	  changestocktype('<c:out value="${reportid}"/>');
});  

 function getOrganTreeXML(){
	 $.post("treeAction.do?method=getOrganTreeXML",function(data){
			var treeXml = eval(data)[0].treeXml;
			$('#combotreeOrganTree').combotree('loadData', treeXml);
		}); 
 }
 


function  listReportRule(){
	var df=document.reportFormatForm;	
	df.action='<c:out value="${hostPrefix}" /><c:url value="/repFileAction.do" />?method=listRepJhgzZf';
	df.submit();
}

function  listReportRuleOne(pkid,displaypage){
	var df=document.reportFormatForm;	
	df.action='<c:out value="${hostPrefix}" /><c:url value="/repFileAction.do" />?method=listRepJhgzZfOne&urlpkid='+pkid+'&displaypage='+displaypage;
	df.submit();
}


function add(pkid){
	 $("#urlpkid").val(pkid);
	 $("#urlreasons").val($("#reasons_"+pkid).val());
	 var df=document.reportFormatForm;
	 df.action='<c:out value="${hostPrefix}" /><c:url value="/repFileAction.do" />?method=listRepJhgzZfAdd';
	 df.submit();
}



function exportData(){
	$("#organ_name").val($("#combotreeOrganTree").combotree("getText"));
	var df=document.reportFormatForm;	
	df.action='<c:out value="${hostPrefix}" /><c:url value="/repFileAction.do" />?method=exportTemplateZf';
	df.submit();
}
function exportDataPDf(){
	$("#organ_name").val($("#combotreeOrganTree").combotree("getText"));
	var df=document.reportFormatForm;	
	df.action='<c:out value="${hostPrefix}" /><c:url value="/repFileAction.do" />?method=exportTemplatePdfzf';
	df.submit();
}

 
 
 
function changestocktype(val){
	$.post("<c:out value="${hostPrefix}" /><c:url value="/repFileAction.do" />?method=listRepTarget",{report_id:val},function(data){
		 $("#target_id").empty();//置空
		 $("#target_id").append($("<option>").val("0").html('<fmt:message key="place.select"/>'));		
		var obj = eval(data);  
		for(var key in obj){ //第一层循环取到各个list  
		     var List = obj[key].tarGetlist;  
		     for(var student in List){ //第二层循环取list中的对象  
		        //  alert(List[student][0]);  
		        //  alert(List[student][1]);  
		          $("#target_id").append($("<option>").val(List[student][0]).html(List[student][1]));	
		     }  
		 }
		/*  if('<c:out value="${target_id}"/>' != ''){
		   $("#target_id").val('<c:out value="${target_id}"/>');//设置value为xx的option选项为默认选中
		 } */
	},"json") ;

   
}

function importDataFromXls(tg,sr){
	 
	 url="repFileAction.do?method=importDataFromXlsZf" ;
	 
	 window.parent.window.OpenPanel(url,'600','200','<fmt:message key="historydata.filecount"/>');
	 
}

</script>


<body   >
<div name="optionbar"  class="navbar">
					<form name="reportFormatForm" action="reporFormat.do?method=listRepJhgzZf" method="post" style="margin: 0">
					        
					        <input type="hidden" id="urlpkid" name="urlpkid" />
						 	<input type="hidden" id="urlreasons" name="urlreasons" /> 
						 	 <input type="hidden" id="organ_name" name="organ_name" />
						 	
					<table >
						<tr>
						     <td   align="left" nowrap class="TdBGColor1" align="right"><fmt:message key="view.databatch"/>:
							 </td>
							 <td>
						        <input id="databatch" name="databatch" type="text"  value="<c:out value='${databatch}'/>"  style="width:80px;" readonly="true" onClick="WdatePicker()"  />&nbsp;
						    </td>
						    
							<td ><fmt:message key="select.organ"/></td>
							<td>
							   
							  <input id="combotreeOrganTree" name="organ_id" class="easyui-combotree"  value="<c:out value='${organ_id}'/>"  style="width:200px"/>
								
							</td>
							<%-- <td>
								&nbsp;&nbsp;<fmt:message key="datagather.label.report"/>
							</td>
							<td>
								<select  name="report_id"   name="report_id" onchange="changestocktype(this.value);">
										<option value="0"><fmt:message key="place.select"/></option>
										<c:forEach items="${reportList}" var="rep"><c:if
										 test="${reportid==rep.pkid}"><option value="<c:out
											 value="${rep.pkid}"/>" selected><c:out value="${rep.name}"/></option></c:if>
							     				<c:if test="${reportid!=rep.pkid}"><option value="<c:out
											 value="${rep.pkid}"/>"><c:out value="${rep.name}"/></option></c:if></c:forEach>
									</select>
							</td>
							<td>
								&nbsp;&nbsp;<fmt:message key="zhibiaomingcheng"/>
							</td>
							<td>
									<select  name="target_id"  id="target_id">
										<option value="0"><fmt:message key="place.select"/></option>
									</select>
							</td>
							  --%>
							
							 
							<td>
								<a href="javascript:void(0);" class="easyui-linkbutton buttonclass2" data-options="iconCls:'icon-search'" style="height:20px;  width:60px;text-decoration: none;" name="ss" onclick="listReportRule();" ><fmt:message key='button.search'/></a>&nbsp;&nbsp;&nbsp;
								<a href="javascript:void(0);" class="easyui-linkbutton buttonclass2" data-options="iconCls:'icon-exp'" onclick="exportData(); " style=" height:20px;width:60px;text-decoration:none;"><fmt:message key='cheacc.exportfile'/></a>&nbsp;&nbsp;&nbsp;
								<a href="javascript:void(0);" class="easyui-linkbutton buttonclass2" data-options="iconCls:'icon-import'" onclick="importDataFromXls('_self','yes')" style=" height:20px;width:60px;text-decoration:none;"><fmt:message key="button.reportformat.import"/></a>&nbsp;&nbsp;&nbsp;
								<%-- <a href="javascript:void(0);" class="easyui-linkbutton buttonclass2" data-options="iconCls:'icon-exp'" onclick="exportDataPDf('_self','yes')" style=" height:20px;width:60px;text-decoration:none;"><fmt:message key="pdf.build"/></a> --%>
							</td>
						
						
						</tr>
					</table>
									
									
					</form>

				
</div>
				    <div align="center" style=" width:100%">
						<form name="logForm" action="" method="post">
						 	
						<display:table name="repconfig" cellspacing="0" cellpadding="0"  
							    requestURI="" id="rt" width="100%"  pagesize="18" 
							    styleClass="list reportsList" >
							    <display:column sort="true"  headerClass="sortable" width="2%"  titleKey="viewItemData.result.numColumn" align="center">
							      <c:out value="${rt_rowNum}"/>
							    </display:column>
							   <display:column sort="true" 
							    	headerClass="sortable" width="12%" 
							    	titleKey="dataTrans.importError.th.name">
							    	<c:out value="${rt.organ_name}"/>
							    </display:column>
							   
							    
							      <display:column sort="true" 
							    	headerClass="sortable" width="3%" 
							    	titleKey="view.databatch">
							    	<c:out value="${rt.databatch}"/>
							    </display:column>
							    
							    <display:column sort="true" 
							    	headerClass="sortable" width="5%" 
							    	titleKey="view.jhgzlx">
							    	<c:out value="${rt.checkingrules}"/>					    	
							    </display:column>
							    
							    
							    
							      <display:column sort="true" 
							    	headerClass="sortable" width="10%"   
							    	titleKey="view.jhbmc">
							    	<c:out value="${rt.report_name}"/>
							    </display:column>
                                <display:column sort="true" 
							    	headerClass="sortable" width="6%" 
							    	titleKey="view.jhys">
							    	<c:out value="${rt.target_name}"/>
							    </display:column>
			                   
			                    
								
								<display:column sort="true" headerClass="sortable" width="10%" 
							    	titleKey="view.yjkmmc">
							    	<c:out value="${rt.ruledescription}"/>	
								</display:column>
								
								<display:column sort="true" headerClass="sortable" width="5%" 
							    	titleKey="view.fhzsz">
							    	<c:out value="${rt.errornumber}"/>	
								</display:column>
								
								<display:column sort="true" headerClass="sortable" width="5%" 
							    	titleKey="view.zzsz">
							    	<c:out value="${rt.total}"/>	
								</display:column>
								
								<display:column sort="true" headerClass="sortable" width="5%" 
							    	titleKey="view.jhce">
							    	<c:out value="${rt.errorrate}"/>
								</display:column>
								
								<display:column sort="true" headerClass="sortable" width="10%" 
							    	titleKey="view.cyyyjs">
							    	<input type="text" id="reasons_<c:out value='${rt.pkid}'/>" title="<c:out value='${rt.reasons}'/>"  value="<c:out value='${rt.reasons}'/>" disabled="disabled"/>	
								</display:column>
								
								<display:column sort="true" headerClass="sortable" width="5%"  titleKey="reportrule.edit">	
								    <c:if test="${rt.errorrate != '0' && rt.errorrate !=null  && rt.errornumber !='null'}">
								      <a style="text-decoration: none;" href="javascript:void(0);" onclick="listReportRuleOne('<c:out value='${rt.pkid}'/>','<c:out value='${displaypage}'/>');" ><fmt:message key="button.edit"/></a>
								    </c:if>
								</display:column>
							</display:table>
							</form>
				    </div>

</body>
</html>