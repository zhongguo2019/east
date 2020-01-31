<!-- /ps/model/reportrule/list_basicrule. -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
<%@ page import="com.krm.ps.sysmanage.usermanage.vo.*"%>

<%
   User user = (User)request.getSession().getAttribute("user");
   String orgId = user.getOrganId();   
 %>


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
	df.action='<c:out value="${hostPrefix}" /><c:url value="/repFileAction.do" />?method=listRepConfigure';
	df.submit();
}



function deleteRepConfigure(pkid){
 
	if(confirm('<fmt:message key="view.delete"/>')){
		var df=document.reportFormatForm;	
		df.action='<c:out value="${hostPrefix}" /><c:url value="/repFileAction.do" />?method=delRepConfigure&pkid='+pkid;
		df.submit();
	}
}

	<c:if test="${isok=='1'}">
	  alert("<fmt:message key='kettle.fileData.del'/>");
	</c:if>
	<c:if test="${isok=='2'}">
	  alert("<fmt:message key='statExport.writeFlag'/>");
	</c:if>

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
	    if('<c:out value="${target_id}"/>' != ''){
		   $("#target_id").val('<c:out value="${target_id}"/>');//设置value为xx的option选项为默认选中
		 } 
	},"json") ;

   
}

 function saveRepConfigure(){
	 
	var reportid = $("#report_id").find("option:selected").val();
	if(reportid == '0'){
	 alert("<fmt:message key='statProcess.choiceReport'/>");
	 return  ;
	}
	var targetid = $("#target_id").find("option:selected").val();
	if(targetid == '0'){
	 alert("<fmt:message key='view.qxzzb'/>");
	 return  ;
	}
	
	var rtype = $("#rtype").find("option:selected").val();
	if(rtype == 'ALL'){
	 alert("<fmt:message key='place.select'/><fmt:message key='leixing'/>");
	 return  ;
	}
	
	var threshold = $("#threshold").val();
	if(rtype != 'ALL' && threshold == ''){
	 alert("<fmt:message key='view.yuzhi'/><fmt:message key='view.bnwk'/>");
	 return  ;
	}
	 
	 
	$("#organ_name").val($("#combotreeOrganTree").combotree("getText"));
	$("#report_name").val($("#report_id").find("option:selected").text());
	$("#target_name").val($("#target_id").find("option:selected").text());
    

	var df=document.reportFormatForm;	
	df.action='<c:out value="${hostPrefix}" /><c:url value="/repFileAction.do" />?method=saveRepConfigure';
	df.submit();
		
 }

</script>


<body  >
<div name="optionbar"  class="navbar">
					<form name="reportFormatForm" action="reporFormat.do?method=listRepConfigure" method="post" style="margin: 0">
					
					      <input type="hidden" id="organ_name" name="organ_name" />
						  <input type="hidden" id="target_name" name="target_name" /> 
						  <input type="hidden" id="report_name" name="report_name" /> 
						 	
					<table >
						<tr>
							<td ><fmt:message key="select.organ"/></td>
							<td>
							   
							  <input id="combotreeOrganTree" name="organ_id" class="easyui-combotree"  value="<%=orgId %>"  style="width:200px"/>
								
							</td>
							<td>
								&nbsp;&nbsp;<fmt:message key="datagather.label.report"/>
							</td>
							<td>
								<select  id="report_id" name="report_id" onchange="changestocktype(this.value);">
										<option value="0"><fmt:message key="place.select"/></option>
										<c:forEach items="${reportList}" var="rep">
											<c:if  test="${reportid==rep.pkid}">
											   <option value="<c:out  value="${rep.pkid}"/>" selected><c:out value="${rep.name}"/></option>
											</c:if>
								     		 <c:if test="${reportid!=rep.pkid}">
								     	    	 <option value="<c:out value="${rep.pkid}"/>"><c:out value="${rep.name}"/></option>
								     		 </c:if>
							     		 </c:forEach>
									</select>
							</td>
							<td>
								&nbsp;&nbsp;<fmt:message key="select.zhibiao"/>
							</td>
							<td>
									<select  name="target_id"  id="target_id" style="width: 130px;">
										<option value="0"><fmt:message key="place.select"/></option>
									</select>
							</td>
							<td>
								&nbsp;&nbsp;<fmt:message key="datagather.label.reporttype"/>
							</td>
							<td>
									<select  name="rtype" id="rtype" >
										<option value="ALL"><fmt:message key="place.select"/></option>
							     		<option value="<="  <c:if test= "${rtype == '>='}" > selected="selected"</c:if> > <=   </option>
							     	    <option value=">="  <c:if test= "${rtype == '>='}" > selected="selected"</c:if>  > >=   </option>
							     		<option value="="   <c:if test= "${rtype == '='}" > selected="selected"</c:if> >  =   </option>
							     		<option value="<"   <c:if test= "${rtype == '<'}" > selected="selected"</c:if> >  <   </option>
							     		<option value=">"   <c:if test= "${rtype == '>'}"> selected="selected"</c:if> >  >   </option> 
									</select>
							</td>
							
							<td>
								&nbsp;&nbsp;<fmt:message key="view.yuzhi"/>
							</td>
							<td>
								<input type="text" id="threshold"  name="threshold"  value="<c:out value="${threshold}"/>"   />
							</td>
							<td>
								<a href="#" class="easyui-linkbutton buttonclass2" data-options="iconCls:'icon-search'" style="height:20px;  width:80px;text-decoration: none;" name="ss" onclick="listReportRule();" ><fmt:message key='button.search'/></a>&nbsp;&nbsp;&nbsp;
								<a href="#" class="easyui-linkbutton buttonclass2"  data-options="iconCls:'icon-save'" onclick="saveRepConfigure(); " style=" height:20px;width:80px;text-decoration:none;"><fmt:message key='button.save'/></a>
							</td>
						
							
						</tr>
					</table>
									
					</form>

				
</div>
				    <div align="center" style=" width:100%">
						<form name="logForm" action="viseAction.do?method=saveLogs" method="post">
						 	<% 
						 	int i = 0;
						 	%>
						<display:table name="repconfig" cellspacing="0" cellpadding="0"  
							    requestURI="" id="rt" width="100%"  pagesize="18" 
							    styleClass="list reportsList" >
							    <%-- Table columns --%>
							    <%
							    	i++;
							    %>
							   
							    <display:column sort="true" 
							    	headerClass="sortable" width="15%" 
							    	titleKey="dataTrans.importError.th.name">
							    	<c:out value="${rt.organ_name}"/>
							    </display:column>
							      <display:column sort="true" 
							    	headerClass="sortable" width="30%"   
							    	titleKey="flowTip.reportName">
							    	<c:out value="${rt.report_name}"/>
							    </display:column>
                                <display:column sort="true" 
							    	headerClass="sortable" width="20%" 
							    	titleKey="reportrule.ruleitem">
							    	<c:out value="${rt.target_name}"/>
							    </display:column>
			                   
			                    <display:column sort="true" 
							    	headerClass="sortable" width="15%" 
							    	titleKey="recycle.itemType">
							    	<c:out value="${rt.rtype}"/>					    	
							    </display:column>
								
								<display:column sort="true" headerClass="sortable" width="5%" 
							    	titleKey="view.yuzhiA">
							    	<c:out value="${rt.threshold}"/>	
								</display:column>
								
								<display:column sort="true" headerClass="sortable" width="15%"  titleKey="reportrule.edit">	
								    <a style="text-decoration: none;" href="javascript:void(0);" onclick="deleteRepConfigure('<c:out value="${rt.pkid}"/>');" ><fmt:message key="groupmanage.main.fun.del"/></a>
							    	<%-- <a style="text-decoration: none;" href="#" onclick="" ><fmt:message key="groupmanage.main.fun.edit"/></a>&nbsp;&nbsp; --%>
								</display:column>
							</display:table>
							</form>
				    </div>

</body>
</html>