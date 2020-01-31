<%@ page import="com.krm.ps.sysmanage.usermanage.vo.*"%>
<%@ page import="com.krm.ps.util.SysConfig"%>
<%@ page import="com.krm.ps.util.FuncConfig"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%
   User user = (User)request.getSession().getAttribute("user");
   String orgId = user.getOrganId();   
%>

<c:set var="colName">
	<fmt:message key="common.list.reportname"/>
</c:set>
<c:set var="reportButton">
	<fmt:message key="place.select"/>
</c:set>
<c:set var="colNames">
	<fmt:message key="exportXML.tree.org.name"/>
</c:set>
<c:set var="reportTreeURL">
	<c:out value="${hostPrefix}"/><c:url value='/reportView.do?method=repTreeAjax${params}'/>
</c:set>
<c:set var="targetTreeURL">
	<c:out value="${hostPrefix}" /><c:url value='/integratedQuery.do?method=targetTree' />
</c:set>
<c:set var="targetButton">
	<fmt:message key="integratedQuery.button.targetSelect"/>
</c:set>
<c:set var="displaySumType">
	<%= FuncConfig.getProperty("reportview.showCollectType")%>
</c:set>
<c:set var="studentLoanReportId">
	<%= FuncConfig.getProperty("studentloan.reportid")%>
</c:set>
<html>
    <head>
        <title><fmt:message key="webapp.prefix"/></title>
        
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
<link rel="stylesheet" href="<c:url value='${cssPath}/css.css" type="text/css'/>"/>
    	<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-win2k-1.css'/>" title="win2k-cold-1" /> 
		<script type="text/javascript" src="<c:url value='/ps/scripts/calendar.js'/>"></script> 
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/lang/zh-cn.js'/>"></script>  
		<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-setup.js'/>"></script>
		
		<script src="<c:url value='/ps/framework/common/jqueryTab/jquery-1.2.4b.js'/>" type="text/javascript"></script>
		
		<script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
    </head>					


<script language=JavaScript>
var oldHide=Calendar.prototype.hide;
Calendar.prototype.hide = function () {oldHide.apply(this);	change();}

function  listReportRule(){
	var df=document.reportFormatForm;	
	df.action='<c:out value="${hostPrefix}" /><c:url value="/datavalidation.do" />?method=enterComLogicRule';
	df.submit();
}
function check_all(){
	   arr = document.getElementsByName('yy');
	   for(i=0;i<arr.length;i++){
	      arr[i].checked = true;
	   }
	  }
	  function rel_all(){
	   arr = document.getElementsByName('yy');
	   for(i=0;i<arr.length;i++){
	      arr[i].checked = false;
	   }
	  }
	  function dangeCheck(val1,val2,val3,val4,val5){
		  var dataDate= document.getElementById('dataDate').value;
		  	var myDate = new Date();
		  	var year=myDate.getFullYear();
		  	var month = myDate.getMonth() + 1;
		  	month = month < 10 ? ("0" + month) : month;
		  	var dt = myDate.getDate();
		  	dt = dt < 10 ? ("0" + dt) : dt;
		  	var currdate=year+"-"+month+"-"+dt;
			if(dataDate > currdate){
				alert('<fmt:message key="view.xzrryj"/>');
				return;
			}
		  var params={"rulecode": val1,"dataDate":dataDate,"cflag":val2,"targettable":val4,"method":"checkData"};
			$.ajax({ 
				type: "POST", 
				async: false, //ajax
				url: "datavalidation.do", 
				data: jQuery.param(params), 
				success: function(result){
					alert('<fmt:message key="symbol.bracket.left"/>'+val5+'<fmt:message key="symbol.bracket.right"/>'+result);
				}
			});
	  }
	  function zhihui(){
			document.getElementById('piliang').disabled =true; 
		}
	function piliangcheck(){
		zhihui();
		var dataDate= document.getElementById('dataDate').value;
	  	var myDate = new Date();
	  	var year=myDate.getFullYear();
	  	var month = myDate.getMonth() + 1;
	  	month = month < 10 ? ("0" + month) : month;
	  	var dt = myDate.getDate();
	  	dt = dt < 10 ? ("0" + dt) : dt;
	  	var currdate=year+"-"+month+"-"+dt;
		if(dataDate > currdate){
			alert('<fmt:message key="view.xzrryj"/>');
			document.getElementById('piliang').disabled=false; 
			return;
		}
		 var arr = document.getElementsByName('yy');
		 var arrayObj="" ;
		 for(var i=0;i<arr.length;i++){
			 if( arr[i].checked ){
			 arrayObj+=arr[i].value+",";
			 }
		 }
		 if(""==arrayObj){
			 alert('<fmt:message key="view.qxzzb"/>');
			 document.getElementById('piliang').disabled=false; 
			 return;
		 }
		 $("#loading").show();
			$("#loading").css({
				 "top" : document.body.clientHeight/2 -25 + "px",
				"left" :  document.body.clientWidth/2 -150+ "px", 
				"display" : "block"
			});
			setTimeout("hide()",2000);
		var dataDate=document.getElementById("dataDate").value;
		var params={"mm": arrayObj,"dataDate":dataDate,"method":"checkData"};
		$.ajax({ 
			type: "POST", 
			async: false, //ajax
			url: "datavalidation.do", 
			data: jQuery.param(params), 
			success: function(result){
				 $("#loading").hide();
				alert(result);
				document.getElementById('piliang').disabled=false; 
			}
		});
	}
</script>
<script type="text/javascript">
function forbiddon(){
	document.onkeydown=function(){if(event.keyCode==8)return false;};
	
}
</script>

<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0" onload="forbiddon();">

<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
     <td width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td>
				<p style="margin-top: 3"><font class=b color="#000000"><b><fmt:message key="view.sjxy"/> </b></font></p>     
          </td>
		  <td></td> 
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td width="100%" bgcolor="#EEEEEE" valign="top">
		<table border="0" width="98%" align="left" cellSpacing=0 cellPadding=0>
			<tr>
				<td height="30" align="left">
					<form name="reportFormatForm" action="reporFormat.do?method=listReportFormats" method="post">
					<input type="hidden" value="<c:out value="${levelFlag}"/>" name="levelFlag"/>
									&nbsp;&nbsp;<fmt:message key="view.gzlx"/>
									<select name="repruletype">
										<option value="0"><fmt:message key="view.sylx"/></option>
										<c:forEach items="${ruleType}" var="type"><c:if
										 test="${ruletypeid==type.pkid}"><option value="<c:out
											 value="${type.pkid}"/>" selected><c:out value="${type.typename}"/></option></c:if>
											<c:if test="${reportType_reportFormat!=type.pkid}"><option value="<c:out
											 value="${type.pkid}"/>"><c:out value="${type.typename}"/></option></c:if></c:forEach>
									</select>&nbsp;&nbsp;
									<fmt:message key="view.sjmx"/>
									<select name="reportn">
										<option value="0"><fmt:message key="view.sylx"/></option>
										<c:forEach items="${reportList}" var="rep"><c:if
										 test="${reportid==rep.pkid}"><option value="<c:out
											 value="${rep.pkid}"/>" selected><c:out value="${rep.name}"/></option></c:if>
											<c:if test="${reportid!=rep.pkid}"><option value="<c:out
											 value="${rep.pkid}"/>"><c:out value="${rep.name}"/></option></c:if></c:forEach>
									</select>&nbsp;&nbsp;
									<input type="button" value='<fmt:message key="button.search"/>' onClick="listReportRule()">&nbsp;&nbsp;
					</form>
				</td>
			</tr>
          <tr>
          <td class="TdBGColor1"><br>&nbsp;
         <input type="button" value="<fmt:message key="button.selectall"/>" onClick="check_all();return false"/>&nbsp;&nbsp;   
          <input type="button" value="<fmt:message key="button.releaseall"/>" onClick="rel_all();return false"/>&nbsp;&nbsp;
          <input type="button" value='<fmt:message key="view.plxy"/>' id="piliang"  onclick="piliangcheck();"/>&nbsp;&nbsp;
         <fmt:message key="view.xyrq"/> <input id="dataDate" name="dataDate" type="text" value="<c:out
					 value='${dataDate}'/>" style="width:100;" readonly="true" >
			<script type="text/javascript">
				Calendar.setup({
					inputField     :    "dataDate",  
					ifFormat       :    "%Y-%m-%d",   
					showsTime      :    false
				});
			</script>
          </td></tr>
			<tr>
				<td align="center">				
				    <div align="center" style=" width:98%">
						<form name="logForm" action="viseAction.do?method=saveLogs" method="post">
						 	<% 
						 	int i = 0;
						 	%>
						<display:table name="reporRule" cellspacing="0" cellpadding="0"  
							    requestURI="" id="rt" width="100%"  pagesize="80"
							    styleClass="list reportsList" >
							    <%-- Table columns --%>
							    <%
							    	i++;
							    %>
							     <display:column width="3%">
											<input type=checkbox name="yy"  
												value="<c:out value="${rt.rulecode}"/>%<c:out value="${rt.targettable}"/>" 
												 />
                                </display:column>
							   
							     <display:column sort="true" 
							    	headerClass="sortable" width="10%" 
							    	titleKey="reportrule.rulecode">
							    	<c:out value="${rt.rulecode}"/>
							    </display:column>
							      <display:column sort="true" 
							    	headerClass="sortable" width="35%"   
							    	titleKey="reportrule.modelname">
							    	<c:out value="${rt.reportname}"/>
							    </display:column>
                                <display:column sort="true" 
							    	headerClass="sortable" width="30%" 
							    	titleKey="reportrule.ruleitem">
							    	<c:out value="${rt.targetname}"/>
							    </display:column>
			                   
			                    <display:column sort="true" 
							    	headerClass="sortable" width="15%" 
							    	titleKey="reportrule.typeanme">
							    	<c:out value="${rt.typename}"/>					    	
							    </display:column>
								
								<display:column sort="true" headerClass="sortable" width="5%" 
							    	titleKey="reportrule.status">
							    	<c:if test="${rt.cstatus==0}"><fmt:message key="contrastrelation.start"/>	</c:if>								
								    <c:if test="${rt.cstatus==1}"><fmt:message key="contrastrelation.stop"/>	</c:if>
								</display:column>
								
								<display:column sort="true" headerClass="sortable" width="5%" 
							    	titleKey="reportrule.edit">							
								<a href="javascript:dangeCheck('<c:out value="${rt.rulecode }"/>','1','<c:out value="${dataDate }"/>','<c:out value="${rt.targettable }"/>','<c:out value="${rt.targetname}"/>')" ><fmt:message key="button.icheck"/></a>
								</display:column>
							</display:table>
							<script type="text/javascript">
								<!--
									highlightTableRows("rt");
								//-->
							</script>
							</form>
				    </div>
				</td>
			</tr>
			<tr height=17><td></td></tr>
			<tr height=17><td></td></tr>
		 <tr><td class="TdBGColor1" colspan="2">
	     <table style="border:0px solid black;" align="center"><tr><td>
			<c:if test="${message == '1'}"><img src="images/xiaolian.png"><font style="color:#990000;"><b><fmt:message key="view.zxcg"/></b></font></c:if>
 			<c:if test="${message == '2'}"><img src="images/kulian.png"><font style="color:#990000;"><b><fmt:message key="view.zxsb"/> </b></font></c:if>
			</td></tr></table>
			</td></tr>
	    
	    
</table> 
<div id="loading"
			style="position: absolute; display: none; color: black; background: #f0f0f0;">
			<table>
			<tr>
			<td>
				<img src="<c:url value='/images/loading1.gif'/>"/>
				</td>
				</tr>
				</table>
		</div>
</body>
</html>