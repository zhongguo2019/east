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
	 df.action='<c:out value="${hostPrefix}" /><c:url value="/repFileAction.do" />?method=listRepsubmitAListAdd';
	 df.submit();
}

function  listReportRule(){
	$("#urlreasons").val('');
 	var df=document.reportFormatForm;	
	df.action='<c:out value="${hostPrefix}" /><c:url value="/repFileAction.do" />?method=listRepSubmitalist';
	df.submit();
}

 

<c:if test="${isok=='1'}">
	alert("<fmt:message key="statExport.writeFlag"/>");
</c:if>
 
  


</script>


<body >
        
					<form name="reportFormatForm" action="reporFormat.do?method=listRepSubmitalist" method="post" style="margin: 0">
					        
					<table class="TdBGColor1" width="85%"   align="center" style="margin-top: 3%">
				
						 	<input type="hidden" id="urlpkid" name="urlpkid"  value="<c:out value='${submitalist.pkid}'/>" /> 
					     
					    <tr>
						     <td    class="TdBGColor1"   align="right" ><fmt:message key="view.bsqd.tbr"/>:
							 </td>
							 <td align="left" >
							   <input type="text" id="tbr" name="tbr"  value="<c:out value='${tbr}'/>"   maxlength="14" /> 
						    </td>
						</tr>
						<tr>
						     <td    class="TdBGColor1"   align="right" ><fmt:message key="view.bsqd.tbrlxfs"/>:
							 </td>
							 <td align="left" >
							  <input type="text" id="tbrlxfs" name="tbrlxfs"  value="<c:out value='${tbrlxfs}'/>"  maxlength="11" /> 
						          
						    </td>
						</tr>
						<tr>
						     <td    class="TdBGColor1"   align="right" ><fmt:message key="view.bsqd.bmfzr"/>:
							 </td>
							 <td align="left" >
							  <input type="text" id="bmfzr" name="bmfzr"  value="<c:out value='${bmfzr}'/>"   maxlength="14" /> 
						    </td>
						</tr>
						<tr>
						     <td    class="TdBGColor1"   align="right" ><fmt:message key="view.bsqd.bmfzrlxfs"/>:
							 </td>
							 <td align="left" >
							   <input type="text" id="bmfzrlxfs" name="bmfzrlxfs"  value="<c:out value='${bmfzrlxfs}'/>"  maxlength="11" /> 
						    </td>
						</tr>
						<tr>
						     <td    class="TdBGColor1"   align="right"  style="border: 0px;height: 2px;" colspan="2">
							 </td>
						</tr>
						<tr>
						     <td    class="TdBGColor1"   align="right" ><fmt:message key="view.bsqd.bm"/>:
							 </td>
							 <td align="left" >
						         <c:out value='${submitalist.reportname_cn}'/> 
						    </td>
						</tr>
						<tr>
						     <td    class="TdBGColor1" align="right"><fmt:message key="view.bsqd.jls"/>:
							 </td>
							 <td align="left" >
						         <c:out value='${submitalist.record_number}'/> 
						    </td>
						</tr>
						<tr>
						     <td    class="TdBGColor1" align="right"><fmt:message key="view.bsqd.sfwzfyywqk"/>:
							 </td>
							 <td align="left" >
						       <c:out value='${submitalist.reflecting_business}'/> 
						    </td>
						</tr>
						<tr>
						     <td    class="TdBGColor1" align="right"><fmt:message key="view.bsqd.wjm"/>:
							 </td>
							 <td align="left" >
						         <c:out value='${submitalist.files_name}'/> 
						    </td>
						</tr>
						<tr>
						     <td    class="TdBGColor1" align="right"><fmt:message key="view.bsqd.wjdx"/>:
							 </td>
							 <td align="left" >
						         <c:out value='${submitalist.files_bytes}'/> 
						    </td>
						</tr>
						<tr>
						     <td    class="TdBGColor1" align="right"><fmt:message key="view.bsqd.sfjy"/>:
							 </td>
							 <td align="left" >
						         <c:out value='${submitalist.ischecked}'/> 
						    </td>
						</tr>
						 
						<tr>
						     <td    class="TdBGColor1" align="right"><fmt:message key="view.bsqd.bz"/>:
							 </td>
							 <td align="left" >
						        <textarea id="urlreasons" name="urlreasons" rows="5" cols="136"   onkeyup="textAreaChange(this)" onkeydown="textAreaChange(this)"  ><c:out value='${submitalist.remarks}'/></textarea>
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