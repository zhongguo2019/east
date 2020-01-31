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


<script type="text/javascript">
//window.parent.document.getElementById("ppppp").value= "业务指标规则配置 >> 指标基础规则配置 >>帮助";
</script>

<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">

<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  
  <tr>
    <td width="100%" bgcolor="#EEEEEE" valign="top">
     <table border="0" cellpadding="0" cellspacing="0" width="100%" align="center">
        <tr>
        <br></br>
          <td style="font-size:20px;font-family:<fmt:message key="huawenxinwei"/>;text-align:center"> 
				<strong><fmt:message key="zhibiaojichuguizepeizhibangzhu"/></strong>
          </td>
        </tr>
         <tr>
		    <td width="100%" valign="top" align="center">
				<img border="0" src="<c:url value="${imgPath}/line030.gif"/>" >
		     </td>
		</tr>
		<tr>
		    <td width="100%" valign="top" >
				<ul style="font-size:16px; font-family:huawenxinwei;">
	        		<li>
	        			<strong><font style="color:black;"><fmt:message key="guizebianma"/></font></strong><fmt:message key="weiyibiaoshiguize"/> 
	        		</li>
	        		<li>
	        			<strong><font style="color:black;"><fmt:message key="zhidingkey"/></font></strong><fmt:message key="shujujilu"/> 
	        		</li>
	        		<li>
	        			<strong><font style="color:black;"><fmt:message key="mubiaorizhi"/></font></strong><fmt:message key="yonglaichunfangbufuheguize"/>
	        		</li>
	        		<li>
	        			<strong><font style="color:black;"><fmt:message key="guizezhuangtai"/></font></strong><fmt:message key="biaoshileguizeshifoushengxiao"/>
	        		</li>
	        		<li>
	        			<strong><font style="color:black;"><fmt:message key="guizejiaoben"/></font></strong><fmt:message key="shezhisqlguize"/>
	        		</li>
	        		<li>
	        			<strong><font style="color:black;"><fmt:message key="guizeshuoming"/></font></strong><fmt:message key="duiguizejiaobenjinxingshuoming"/> 
	        		</li>
	        		<li>
	        			<strong><font style="color:black;"><fmt:message key="cuowutishi"/></font></strong><fmt:message key="shujuweifanguize"/> 
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