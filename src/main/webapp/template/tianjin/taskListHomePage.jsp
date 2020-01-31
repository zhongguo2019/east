<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>


<html>
<head>
<title><fmt:message key="webapp.prefix"/></title>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/default.css'/>" />
<link rel="stylesheet" href="/ps/framework/images/css.css" type="text/css">
<script type="text/javascript" src="<c:url value='/scripts/global.js'/>"></script>
<link rel="stylesheet" href="<c:url value='/ps/framework/common/jqueryTab/ui.tabs.css'/>" type="text/css" media="print, projection, screen">
<script src="<c:url value='/ps/framework/common/jqueryTab/jquery-1.2.4b.js'/>" type="text/javascript"></script>
<script src="<c:url value='/ps/framework/common/jqueryTab/ui.core.js'/>" type="text/javascript"></script>
<script src="<c:url value='/ps/framework/common/jqueryTab/ui.tabs.js'/>" type="text/javascript"></script>
</head>
<script type="text/javascript">
$(function() {
	init(window.location.href="dataFill.do?method=getReportGuide&levelFlag=1");
		$('#container-1 > ul').tabs({
			select: function(ui) {
				//alert('aaa');
			},
			fx : { opacity: 'toggle' }
		});
		
		$("ul a").click(function(){
		//changeTab($('#tabs > ul').data('selected.tabs'));
		});
	});
</script>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <c:if test="${sessionScope.system_id!='1' }">
  <tr>
    <td width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
  	<table border="0" cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td width="19"><img border="0" src="${imgPath}/f01.gif" width="19" height="36"></td>
				<td width="42"><img border="0" src="${imgPath}/f03.gif" width="33" height="16"></td>
				<td>
				<p style="margin-top: 3"><font class=b><b><fmt:message
					key="chartpage.navigation.homepage" /></b></font></p>
				</td>
				<td></td>
			</tr>
		</table>	  
    </td>
  </tr>
  </c:if>
  <tr>
    <td width="100%" bgcolor="#EEEEEE" valign="top">
    	<div align="center" >
    	<c:if test="${sessionScope.system_id=='3' }">
		    <img src="plat/khfx/img/dt.png" />
		  </c:if>
		  <c:if test="${sessionScope.system_id=='1' }">
		    <img src="plat/khfx/img/dt3.jpg" width="1399" />
		    </c:if>
		</div>
	</td>
  </tr>
</table>
</body>
</html>

