<%@ page pageEncoding="UTF-8"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Date"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>北京零壹智慧科技有限公司-报送平台</title>
<%
	String date=new SimpleDateFormat("yyyy-MM-dd").format(new Date());
%>
<script type="text/javascript" src="script/jquery.min.js"></script>
<script language="javascript" type="text/javascript" src="util/My97DatePicker/WdatePicker.js"></script>
<style type="text/css">
<!--
body {
	background:#3c7fb5 url(images/bg_login.jpg) repeat-x left top;
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
	
}
body,table,td,div {
	font-size: 14px;
	line-height: 24px;
}
.textfile{
 background:url('images/bg_login_textfile.gif') no-repeat left top;;
 border:0px solid #d3d3d3;
 color:#555;font-size:14px;
 padding: 4px;
 height: 29px;
 float:left;
 width:143px;
}
/*.textfile {background:url(images/bg_login_textfile.gif) no-repeat left top; padding: 0px 2px; height: 29px; width: 143px; border: 0; }*/
-->
</style></head>

<body>
<table width="95" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="images/top_login.jpg" width="596" height="331" /></td>
  </tr>
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="99"><img src="images/login_06.jpg" width="99" height="130" /></td>
        <td background="images/bg_form.jpg"><table width="250" border="0" align="center" cellpadding="0" cellspacing="0">
          <form method="post" action="portal.jsp" onsubmit="return getlogin();" ><tr>
            <td height="30" align="right">用户名：</td>
            <td>
              <label>
                <input name="logonname" id="user_name" type="text" class="textfile" />
                </label>            </td>
          </tr>
          <tr>
            <td height="30" align="right">密&nbsp;&nbsp;码：</td>
            <td><label>
              <input name="password" id="user_password"type="password"  class="textfile" />
            </label></td>
          </tr>
          <tr>
            <td height="30" align="right">登录日期：</td>
            <td><label>
              <input id="logindate" name="logindate" type="text"  value="<%=date%>" 
									style="width: 100; height: 25" readonly="true"
									onClick="WdatePicker()" class="textfile" />
									<input type="hidden" id="jsesstionId"/>
            </label></td>
          </tr>
          <tr>
            <td height="30">&nbsp;</td>
            <td><label>
              <input type="submit" name="Submit" value="登录"  style="width:80px"/>
              <input type="reset" name="Submit2" value="重置" style="width:80px"/>
            </label></td>
          </tr>
          </form>
        </table></td>
        <td width="98" align="right"><img src="images/login_08.jpg" width="98" height="137" /></td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td><img src="images/bottom_login.jpg" width="596" height="39" /></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
  <tr>
    <td align="center" style="color:#ffffff">版权所有 天津滨海商业银行 </td>
  </tr>
</table>
</body>
</html>
<%@ include file="session/checklogin.jsp" %>
<script type="text/javascript">
//var platloginurl = "session/creat.jsp";
var platloginurl = "/platsso/platlogin.shtml?method=getUserExistByAsync&mycas=true";
document.getElementById("user_name").focus(); 


</script>
<script  type="text/javascript" src="script/login.js"></script>