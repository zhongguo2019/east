<!-- /ps/model/reportrule/list_basicrule. -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
<%@ page import="com.krm.ps.sysmanage.usermanage.vo.*"%>
 

<html>
 
    <head>
        <title><fmt:message key="webapp.prefix"/></title>

   <link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>
 <link rel="stylesheet" type="text/css" media="all" href="<c:url value='${displaytag12Path}/displaytag.css'/>" />
  <script language="javascript" type="text/javascript" src="<c:url value='ps/uitl/My97DatePicker/WdatePicker.js'/>"></script>
    <style type="text/css">
	  table td{
	    border:1px solid
	  }
  </style>
 </head>					


<script language=JavaScript>
 
 
function add(){
	 var df=document.reportFormatForm;
	 df.action='<c:out value="${hostPrefix}" /><c:url value="/repFileAction.do" />?method=listRepJhgzZfAdd';
	 df.submit();
}

function  listReportRule(displaypage){
	$("#urlreasons").val('');
	var df=document.reportFormatForm;	
	df.action='<c:out value="${hostPrefix}" /><c:url value="/repFileAction.do" />?method=listRepJhgzZf&d-5480-p='+displaypage;
	df.submit();
}

 

<c:if test="${isok=='1'}">
	alert("<fmt:message key="statExport.writeFlag"/>");
</c:if>
 
  


</script>


<body >
        
					<form name="reportFormatForm" action="reporFormat.do?method=listRepJhgzZf" method="post" style="margin: 0">
					        
					<table class="TdBGColor1" width="80%"  align="center" style="margin-top: 3%">
				            <input type="hidden" id="organ_id" name="organ_id" value="<c:out value='${Forgan_id}'/>"/>
						 	<input type="hidden" id="report_id" name="report_id"  value="<c:out value='${Freportid}'/>" /> 
						 	<input type="hidden" id="databatch" name="databatch"  value="<c:out value='${Fdatabatch}'/>" /> 
						 	<input type="hidden" id="report_id" name="target_id"  value="<c:out value='${Ftarget_id}'/>" />
						 	<input type="hidden" id="urlpkid" name="urlpkid"  value="<c:out value='${codeRepJhgz2.pkid}'/>" />
						 	<input type="hidden" id="displaypage" name="displaypage"  value="<c:out value='${displaypage}'/>" />  
					     
						<tr>
						     <td    class="TdBGColor1" align="right" width="15%"><fmt:message key="dataTrans.importError.th.name"/>:
							 </td>
							 <td align="left" >
						         <c:out value='${codeRepJhgz2.organ_name}'/> 
						    </td>
						</tr>
					 
						<tr>
						     <td    class="TdBGColor1" align="right"><fmt:message key="view.databatch"/>:
							 </td>
							 <td align="left" >
						       <c:out value='${codeRepJhgz2.databatch}'/> 
						    </td>
						</tr>
						<tr>
						     <td    class="TdBGColor1" align="right"><fmt:message key="view.jhgzlx"/>:
							 </td>
							 <td align="left" >
						         <c:out value='${codeRepJhgz2.checkingrules}'/> 
						    </td>
						</tr>
						<tr>
						     <td    class="TdBGColor1" align="right"><fmt:message key="view.jhbmc"/>:
							 </td>
							 <td align="left" >
						         <c:out value='${codeRepJhgz2.report_name}'/> 
						    </td>
						</tr>
						<tr>
						     <td    class="TdBGColor1" align="right"><fmt:message key="view.jhys"/>:
							 </td>
							 <td align="left" >
						         <c:out value='${codeRepJhgz2.target_name}'/> 
						    </td>
						</tr>
						<tr>
						     <td    class="TdBGColor1" align="right"><fmt:message key="view.yjkmmc"/>:
							 </td>
							 <td align="left" >
						       <c:out value='${codeRepJhgz2.ruledescription}'/>  
						    </td>
						</tr>
						
						<tr>
						     <td    class="TdBGColor1" align="right"><fmt:message key="view.fhzsz"/>:
							 </td>
							 <td align="left"  >
						         <c:out value='${codeRepJhgz2.errornumber}'/> 
						    </td>
						</tr>
						
						<tr>
						     <td    class="TdBGColor1" align="right"><fmt:message key="view.zzsz"/>:
							 </td>
							 <td align="left" >
						         <c:out value='${codeRepJhgz2.total}'/> 
						    </td>
						</tr>
						
						<tr>
						     <td    class="TdBGColor1" align="right"><fmt:message key="view.jhce"/>:
							 </td>
							 <td align="left" >
						         <c:out value='${codeRepJhgz2.errorrate}'/>
						    </td>
						</tr>
						
						<tr>
						     <td    class="TdBGColor1" align="right"><fmt:message key="view.cyyyjs"/>:
							 </td>
							 <td align="left" >
						        <textarea id="urlreasons" name="urlreasons" rows="5" cols="130"  onkeyup="textAreaChange(this)" onkeydown="textAreaChange(this)"  ><c:out value='${codeRepJhgz2.reasons}'/></textarea>
						         <div><fmt:message key="view.bsqd.hnsr"/>:<em style='color:red'>800</em>/<span>800</span><fmt:message key="view.bsqd.zf"/></div>
						    </td>
						</tr>
						
						 
						
						<tr> 
						    
							<td class="TdBGColor1" align="right" >
							</td>
						     <td    class="TdBGColor1" align="left"> 
								<a   href="javascript:void(0);" class="easyui-linkbutton buttonclass2" data-options="iconCls:'icon-save'" style="height:20px;  width:80px;text-decoration: none;"  onclick="add();" ><fmt:message key="button.save"/></a>
								<a   href="javascript:void(0);" class="easyui-linkbutton buttonclass2" data-options="iconCls:'icon-clear'" style="height:20px;  width:80px;text-decoration: none;"  onclick="listReportRule('<c:out value="${displaypage}"/>');" ><fmt:message key="button.back"/></a>
							 </td>
						
						</tr>
					</table>
									
								
									
					</form>
  
			 

</body>
</html>