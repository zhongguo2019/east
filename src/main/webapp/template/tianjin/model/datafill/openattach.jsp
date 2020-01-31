<%@ page pageEncoding="UTF-8"%>
<%@ page import="com.krm.ps.util.SysConfig"%>
<%@ page import="com.krm.ps.util.FuncConfig"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>

<html>
<head>
<title><fmt:message key="webapp.prefix" /></title>

<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/theme/default/skin_01/style/default.css'/>" />
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/theme/default/skin_01/style/common.css'/>" /> 
<script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
<script src="<c:url value='/ps/framework/common/jqueryTab/jquery-1.2.4b.js'/>" type="text/javascript"></script>
<script src="<c:url value='/ps/framework/common/jqueryTab/jquery-1.4.1.min.js'/>" type="text/javascript"></script>

<script language="javascript">
function upload(obj)
{
 if(confirm("您现在选择的是XXX,您确定要导入吗？"))
 {
  var uploadfile = $("#excelfile").val();
  if((null == uploadfile) ||( "" == uploadfile))
  {
   alert("上传文件没有指定！");
   return false;
  }
  	url = "excelUpload.do?method=uploadExcel";
  	document.excelForm.action=url;
	document.excelForm.submit();
 }
}

</script>

</head>
<body leftmargin="0" topmargin="0" >

		
		
	<form name="excelForm" method="post" enctype="multipart/form-data">
		<input type="file"  id="excelfile"/> 
		
		
		<input type="button" value="submit" onclick="upload(this.form)"/>
		<input type="button" value="close" onclick="window.closed">
	</form>
</body>

</html> 











