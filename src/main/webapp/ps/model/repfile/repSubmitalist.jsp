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
 

function  listReportRule(){
	var df=document.reportFormatForm;	
	df.action='<c:out value="${hostPrefix}" /><c:url value="/repFileAction.do" />?method=listRepJhgzZf';
	df.submit();
}
 

function exportData(){
	var df=document.reportFormatForm;	
	df.action='<c:out value="${hostPrefix}" /><c:url value="/repFileAction.do" />?method=exportTemplaterepSubmitalist';
	df.submit();
}
function exportDataPDf(){
	var df=document.reportFormatForm;	
	df.action='<c:out value="${hostPrefix}" /><c:url value="/repFileAction.do" />?method=exportPdfRepSubmitalist';
	df.submit();
}

function  listReportRuleOne(pkid){
	var df=document.reportFormatForm;	
	df.action='<c:out value="${hostPrefix}" /><c:url value="/repFileAction.do" />?method=listRepsubmitAListOne&urlpkid='+pkid ;
	df.submit();
}


</script>


<body   >
<div name="optionbar"  class="navbar">
					<form name="reportFormatForm" action="reporFormat.do?method=listRepJhgzZf" method="post" style="margin: 0">
				 
						 	
					<table >
						<tr>
						    
							 
							<td>
								<a href="javascript:void(0);" class="easyui-linkbutton buttonclass2" data-options="iconCls:'icon-exp'" onclick="exportData(); " style=" height:20px;width:60px;text-decoration:none;"><fmt:message key='cheacc.exportfile'/></a>&nbsp;&nbsp;&nbsp;
								<%-- <a href="javascript:void(0);" class="easyui-linkbutton buttonclass2" data-options="iconCls:'icon-exp'" onclick="exportDataPDf('_self','yes')" style=" height:20px;width:60px;text-decoration:none;"><fmt:message key="pdf.build"/></a> --%>
							</td>
						
						
						</tr>
					</table>
									
									
					</form>

				
</div>
				    <div align="center" style=" width:100%">
						<form name="logForm" action="" method="post">
						 	
						<display:table name="repconfig" cellspacing="0" cellpadding="0"  
							    requestURI="" id="rt" width="100%"  pagesize="60" 
							    styleClass="list reportsList" >
							    <display:column sort="true"  headerClass="sortable" width="2%"  titleKey="viewItemData.result.numColumn" align="center">
							      <c:out value="${rt_rowNum}"/>
							    </display:column>
							   <display:column sort="true" 
							    	headerClass="sortable" width="10%" 
							    	titleKey="view.bsqd.bm">
							    	<c:out value="${rt.reportname_cn}"/>
							    </display:column>
							   
							    
							      <display:column sort="true" 
							    	headerClass="sortable" width="5%" 
							    	titleKey="view.bsqd.jls">
							    	<c:out value="${rt.record_number}"/>
							    </display:column>
							    
							    <display:column sort="true" 
							    	headerClass="sortable" width="5%" 
							    	titleKey="view.bsqd.sfwzfyywqk">
							    	<c:out value="${rt.reflecting_business}"/>					    	
							    </display:column>
							    
							    
							    
							      <display:column sort="true" 
							    	headerClass="sortable" width="20%"   
							    	titleKey="view.bsqd.wjm">
							    	<c:out value="${rt.files_name}"/>
							    </display:column>
                                <display:column sort="true" 
							    	headerClass="sortable" width="5%" 
							    	titleKey="view.bsqd.wjdx">
							    	<c:out value="${rt.files_bytes}"/>
							    </display:column>
								
								 <display:column sort="true" headerClass="sortable" width="5%" 
							    	titleKey="view.bsqd.sfjy">
							    	<c:out value="${rt.ischecked}"/>	
								 </display:column>
								
								<display:column sort="true" headerClass="sortable" width="10%" 
							    	titleKey="view.bsqd.bz"  maxLength="27" style="title:<c:out value='${rt.remarks}'/>" >
							    	<c:out value="${rt.remarks}"/>	
								</display:column>
								
								<display:column sort="true" headerClass="sortable" width="5%"  titleKey="reportrule.edit">	
								      <a style="text-decoration: none;" href="javascript:void(0);" onclick="listReportRuleOne('<c:out value='${rt.pkid}'/>');" ><fmt:message key="button.edit"/></a>
								</display:column>
							 
						 	</display:table>
							</form>
				    </div>

</body>
</html>