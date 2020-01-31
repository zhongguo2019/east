<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<c:set var="colName">
	<fmt:message key="exportXML.tree.org.name"/>
</c:set>
<c:set var="orgTreeURL">
	<c:out value="${hostPrefix}"/><c:url value='/treeAction.do?method=getOrganTreeXML${conDate}'/>
</c:set>
<c:set var="orgButton">
	<fmt:message key="exportXML.button.orgChoose"/>
</c:set>
<html>
<head>
<title><fmt:message key="webapp.prefix"/></title>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${cssPath}/common.css'/>" />
		<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-win2k-1.css'/>" title="win2k-cold-1" /> 
		<script type="text/javascript" src="<c:url value='/ps/scripts/jquery.js'/>"></script>  
        <script type="text/javascript" src="<c:url value='/ps/scripts/Dialog.js'/>"  > </script>        
		<script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar.js'/>"></script> 
	<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/lang/zh-cn.js'/>"></script> 
	<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/jscalendar/calendar-setup.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/ps/uitl/krmtree/ActiveXTree/ActiveXTree.js'/>"></script>    
	<script src="<c:url value='/ps/framework/common/jqueryTab/jquery-1.2.4b.js'/>" type="text/javascript"></script>
	<script src="<c:url value='/ps/framework/common/jqueryTab/jquery-1.4.1.min.js'/>" type="text/javascript"></script>
</head>
<body onload="forbiddon()">
 <script type="text/javascript">
 
 $(document).ready(function(){
	 
	 if($("#reporNametstr").val()!=""){
		 alert($("#reporNametstr").val());
		 $("#reporNametstr").val("");
		 window.location.href="repFileAction.do?method=entercreateFileSerch&levelFlag=2" ;
	 }
	 
 });
 
function forbiddon(){
	document.onkeydown=function(){if(event.keyCode==8)return false;};
	
}
function showMore(){
	if($("#zengquan").css("display")=="block"){
		$("#zengquan").hide();
	}else{
		$("#zengquan").show();
	}
}
</script>
<div id="loading" style="position: absolute; display: none; color: black; background: #f0f0f0;">
			<table width="100%">
			<tr>
				<td><fmt:message key="repfile.attr.loading" /></td>
			</tr>
			<tr>
			<td align="center">
				<img src="<c:url value='${imgPath}/loading_2.gif'/>"/>
				</td>
				</tr>
				</table>
</div>
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%" id="show">
  <tr>
    <td width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td>
            <p style="margin-top: 3"><font class="b" color="#000000"><b><fmt:message key="navigation.repifle.createfile"/></b></font></p>                         
          </td>
		  <td></td> 
        </tr>
      </table>
    </td>
  </tr>
  <tr><td class="TdBGColor1">&nbsp;</td></tr>
  <tr><td align="center" class="TdBGColor1"><p>
    </p></td>
  </tr>
  <tr>
    <td width="100%" bgcolor="#eeeeee" valign="top">

 <form action="repFileAction.do?method=createFileSerch" method="post" name="repFlFomatForm" onsubmit="return sub();" target="_self">
<br>
<table cellSpacing=1 cellPadding=0 width="80%" align="center" border=0 class="TableBGColor">
 
 <input id="reporNametstr" type="hidden"  value="<c:out value="${reporNametstr}"/>" />
	<tr>
  	  <td height="21" class="TdBGColor1" width="25%" align="right"><fmt:message key="studentloan.report.date"/>:</td>
      <td height="21" class="TdBGColor1">
    	<input type="text" name="condates" id="condates" value="<c:out value="${condates}"/>" style="width:100" readonly = "true"  />
      </td>
    </tr>
	<tr>
  	  <td height="21" class="TdBGColor1" width="25%" align="right"><fmt:message key="view.qszsj"/></td>
      <td height="21" class="TdBGColor1">
    	<input type="text" name="begindate" id="begindate" value="<c:out value="${begindate}"/>" style="width:100" readonly = "true"  />&nbsp;-&nbsp;<input type="text" name="enddate" id="enddate" value="<c:out value="${enddate}"/>" style="width:100" readonly = "true"  />
      </td>
    </tr>
   	<script type="text/javascript">
			Calendar.setup({
				inputField     :    "begindate",  
				ifFormat       :    "%Y-%m-%d",     
				showsTime      :    false,
				align          :    "tl"
			});
			Calendar.setup({
				inputField     :    "enddate",  
				ifFormat       :    "%Y-%m-%d",     
				showsTime      :    false,
				align          :    "tl"
			});
			Calendar.setup({
				inputField     :    "condates",  
				ifFormat       :    "%Y-%m-%d",     
				showsTime      :    false,
				align          :    "tl"
			});
	</script>
	<tr>
		<td align="right" class="TdBGColor1"><fmt:message key="view.xzgs"></fmt:message></td>
		<td class="TdBGColor1"><select  name="batch">
			<option value="txt">TXT</option>
			<!-- <option value="xml">XML</option> -->
		</select></td>
	</tr>
	<tr>
										<td align="right" class="TdBGColor1">&nbsp;&nbsp;&nbsp;<fmt:message key="usermanage.organId"/></td>
											<input type="hidden" name="organId" value="<c:out value='${organId}' />"/>
								<input type="hidden" name="organName"/>
										<td width="80" align="left" nowrap class="TdBGColor1">
										 <slsint:ActiveXTree left="220" top="325" height="${activeXTreeHeight }"
												width="380"
												xml="${orgTreeURL}" 
												bgcolor="0xFFD3C0"
												rootid="${rootId}" 
												columntitle="${colNames}"
												columnwidth="380,0,0,0" 
												formname="repFlFomatForm"
												idstr="organId" 
												namestr="organName" 
												checkstyle="0" 
												filllayer="2"
												txtwidth="200" 
												buttonname="${orgButton}">
											</slsint:ActiveXTree></td>
	</tr>
		<tr>
			<td class="TdBGColor1" width="25%" align="right"></td>
			<td class="TdBGColor1">
				<input type="button" name="reportMode" value="<fmt:message key="reportchart.list.all"/>" onclick="selectall()"/>
			</td>
		</tr>
		
     <tr>
		<td class="TdBGColor1" width="25%" align="right">
	    <fmt:message key="datagather.label.report"/></td>
		<td class="TdBGColor1" id="rep">
			<select name="reportId" style="width:380;height:240px" multiple="true" >
		  	 <c:forEach items="${repList}" var="rep">
		  	  	<option value="<c:out value="${rep.pkid}" />"><c:out value="${rep.name}"></c:out></option>
		  	 </c:forEach>
			</select>&nbsp;&nbsp;&nbsp;&nbsp;
			<!-- 
			<input type="button" style="font-size:10px;" onclick="showMore()"  value=">>geng duo"/>
			 -->
		</td>
		</tr>	
		<!-- 
		 <tr id="zengquan" style="display:none;">
		<td class="TdBGColor1" width="25%" align="right">
	    shang chuan fang shi</td>
		<td class="TdBGColor1" id="liang">
			<input type="radio" id ="cstatus1" name="cstatus" value="0"   checked>zeng liang</input>
	      	<input type="radio" id ="cstatus2" name="cstatus" value="1"  > quan liang</input>
		</td>
		</tr>
		 -->
		</td>
		</tr>	
		
	<tr align="center">
		<td align="center" class="TdBgColor2" colspan=2>
			<input type="submit" name="but" value="<fmt:message key="loanDeviation.btn.file"/>" style="width:90px" id=but  />	
			<%-- <input type="button"  value="<fmt:message key="loanDeviation.btn.kettle"/>" style="width:110px"  onclick="openKettle();"/>	 --%>
			</td>
	</tr>
	<tr>
		<td class="FormBottom" colspan="2" height="17"></td>
	</tr>
</table>
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


 var f =document.repFlFomatForm;
function selectall(){
	for(var i=0;i<f.reportId.length;i++){
		f.reportId.options[i].selected=true;
	}
}


function openKettle(){
	f.action="repFileAction.do?method=openKettle";
	f.submit();
}


function download(repId){

	frm.action="repFileAction.do?method=downloadFile";
	frm.submit();	
}
function zOpenqdzx(){ 
	var diag = new Dialog('Diag1'); 
	diag.Width = 650; 
	diag.Height = 280; 
	diag.Title = '<fmt:message key="center.news.xxpl" />'; 
	diag.URL = 'repFileAction.do?method=createFileSerch'; 
	diag.ShowMessageRow = false; 
	diag.ShowButtonRow = false; 
	diag.MessageTitle = '<fmt:message key="center.news.xxplsm" />'; 
	diag.Message = '<fmt:message key="center.news.xxplmanage" />';	  
	diag.show(); 
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
function dochangedate(){
	var datevalue=f.condate.value;
	f.action ="repFileAction.do?method=entercreateFileSerch&datevalue="+datevalue;
	f.submit();
}
function changeOrg(){
    var oDate = f.condate;
	var vDate = oDate.value;
	var url = "<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/treeAction.do?method=getOrganTreeXML&date="+vDate;
	document.all.objTree1.SetXMLPath(url);
   	document.all.objTree1.Refresh();	
}
function changeTree1(){
	changeOrg();
}
</script>
</html>