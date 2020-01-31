<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ page pageEncoding="UTF-8" %>
<%@ page import="com.krm.ps.util.FuncConfig"%>
<html>
    <head>
        <title><fmt:message key="webapp.prefix"/></title>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${otherPath}/default.css'/>" />
<link rel="stylesheet" href="<c:url value='${cssPath}/css.css" type="text/css'/>"/>
        <script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
    	
    	
		
    </head>					


<script language=JavaScript>
	
    
</script>

<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">

<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
   <td width="100%" background="<c:url value="${imgPath}/f02.gif"/>" height="36">
      <table border="0" cellpadding="0" cellspacing="0" width="100%">
        <tr>
          <td width="19"><img border="0" src="<c:url value="${imgPath}/f01.gif"/>" width="19" height="36"></td>
          <td width="42"><img border="0" src="<c:url value="${imgPath}/f03.gif"/>" height="16"></td>
          <td> 
				<p style="margin-top: 3"><font class=b><b>业务指标规则配置 >> 指标基础规则配置 >>帮助
				</b></font></p>     
          </td>
		  <td></td> 
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td width="100%" bgcolor="#EEEEEE" valign="top">
     <table border="0" cellpadding="0" cellspacing="0" width="100%" align="center">
        <tr>
        <br></br>
          <td style="font-size:20px;font-family:华文新魏;text-align:center"> 
				<strong>指标基础规则配置帮助</strong>
          </td>
        </tr>
         <tr>
		    <td width="100%" bgcolor="#EEEEEE" valign="top" align="center">
				<img border="0" src="<c:url value="${imgPath}/line030.gif"/>" >
		     </td>
		</tr>
		<tr>
		    <td width="100%" bgcolor="#EEEEEE" valign="top" >
				<ul style="font-size:16px;color:#566984;font-family:华文新魏;">
	        		<li>
	        			<strong><font style="color:black;">规则编码：</font></strong>唯一标识规则，不能重复，当定义规则时，系统会默认给定一编码，格式为：报表id_指标id_规则类型id; 
	        		</li>
	        		<li>
	        			<strong><font style="color:black;">指标对应key列：</font></strong>数据记录id，我们系统中默认为pkid; 
	        		</li>
	        		<li>
	        			<strong><font style="color:black;">目标日志表：</font></strong>用来存放不符合规则的数据; 
	        		</li>
	        		<li>
	        			<strong><font style="color:black;">规则状态：</font></strong>标志了规则是否失效; 
	        		</li>
	        		<li>
	        			<strong><font style="color:black;">规则脚本：</font></strong>写规则脚本时，我们可以利用提供的辅助功能，为会规则设置sql规则; 
	        		</li>
	        		<li>
	        			<strong><font style="color:black;">规则说明：</font></strong>对规则脚本进行说明; 
	        		</li>
	        		<li>
	        			<strong><font style="color:black;">错误提示：</font></strong>当数据违反规则时，系统会把这些文字展示，做为我修改数据的参照; 
	        		</li>
     			</ul>
		     </td>
		</tr>
      </table>
		
     </td>
</tr>

</table>
</body>
</html>