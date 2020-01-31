<!DOCTYPE HTML>
<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<title>报送平台-天津滨海银行</title>
<link href="style/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.min.js"></script>
</head>
<body sroll="no">
	<div id="container">
		<div
			style="height: 60px; overflow-y: hidden; overflow-x: hidden;background-color: #1ea4d2;">
			<div style="padding-left: 10px; width: 100%;">
			<!-- 	<div style="float: left; width: 60px; color: #ffffff; height: 60px; line-height: 60px;font-size:22px">
				logo
				</div> -->
				<div style="float: left; width: 300px; color: #ffffff; height: 60px; line-height: 60px;font-size:22px">
				<b>天津滨海银行数据报送平台	</b>
				</div>
				<div id="logout" style="float: right; width: 80px; color: #ffffff; height: 60px; line-height: 60px;">
					<a href="session/clean.jsp" style="font-size:12px;color:#ffffff">退出</a>
				</div>
			</div>

		</div>

<hr>
		<!-- 平台系统权限选择 -->
		<form name="osform" action="" method="post">
		<input type="hidden" name="jsesstionId" id="jsesstionId">
		<input type="hidden" name="logonname" id="userid">
		<input type="hidden" name="username" id="username">
		<input type="hidden" name="password" id="userpass">
		<input type="hidden" name="logindate" id="logindate">
		</form>
		<div id="platchoose"
			style="height: 400px; visibility: hidden; display: none">

			<div
				style="font-size: 12px; width: 60%; background: #ffffff url('images/bubble.png') no-repeat 10px center; padding: 10px; padding-left: 40px; margin: 5px auto; border: 1px dashed #CAE1FF;">
				<div style="width:300px;float:left">
				欢迎用户<b> <span style="color:#000000;margin:4px" id="usernamespan"></span></b>登录报送平台!
				</div>
				<div style="width:150px;float:right;background: #ffffff url('images/date.png') no-repeat 0px center;padding-left:20px" >
				登录日期:<span title="报表查询日期" style="color:#000000;margin:4px" id="logindatespan"></span>
				</div>
			</div>
			<div>
				<div id="nav-shadow" style="list-style: none"></div>
			</div>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">

var rnum = Math.floor(Math.random()*10000+1);
var oslisturl = "conf/osconf.json?run="+rnum;

</script>
<script  type="text/javascript" src="script/login.js"></script>
<%@ include file="session/checksession.jsp" %>