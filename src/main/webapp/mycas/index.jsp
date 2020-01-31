<!DOCTYPE HTML>
<%@ page pageEncoding="UTF-8"%>
<html>
<head>
<title>plat login</title>
<link href="style/comm.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="script/jquery.min.js"></script>
<script type="text/javascript" src="script/execute.js"></script>
<script language="javascript" type="text/javascript" src="util/My97DatePicker/WdatePicker.js"></script>
</head>
<body style="overflow:hidden;" sroll="no">
	<div id="container">
		<div
			style="height: 50px; overflow-y: hidden; overflow-x: hidden;background-color: #1ea4d2;">
			<div style="padding-left: 10px; width: 100%;">
				<div style="float: left; width: 180px; color: #ffffff; height: 50px; line-height: 50px;">
				plat login	
				</div>
				<div id="logout" style="float: right; width: 80px; color: #ffffff; height: 50px; line-height: 50px;">
					<a href="session/clean.jsp">退出</a>
				</div>
			</div>

		</div>


		<!-- 平台登录-->
		<div id="platlog" style="font-size: 12px;">

			<div style="background-color: #1ea4d2; height: 200px; overflow-y: hidden; top: 0; left: 0; xoverflow-x: hidden;text-algin:center">
				
			</div>
		  
			<div style="width: 300px; background: #ffffff; border: 1px solid #d3d3d3; margin: 0 auto; margin-top: -70px; padding: 40px;">
				<form method="post" action="loginAction.do?method=login"
					name="form1">
					<div style="width: 400px; margin: 0 auto">

						<table>
							<Tr>
								<td align="right"><label for="user_name">用户名：</label></td>
								<td><input name="logonname" id="user_name" type="text"
									style="width: 100; height: 25" class="inputclass" /></td>

							</Tr>
							<tr>
								<td align="right"><label for="user_password">密码：</label></td>
								<td><input name="password" id="user_password"
									type="text" style="width: 100; height: 25"
									class="inputclass" /></td>
							</tr>
							<tr>
								<td align="right"><label for="user_password">登录日期：</label></td>
								<Td><input id="logindate" name="logindate" type="text"
									style="width: 100; height: 25" readonly="true"
									onClick="WdatePicker()" class="inputclass" />
									<input type="hidden" id="jsesstionId"/>
									</td>
							</tr>
							<tr>
								<td></td>
								<td><input type="button" onclick="getlogin();"
									value="登录"
									style="height: 30px; width: 120px"></td>
							</tr>
							<!-- <tr>
								<td></td>
								<td><input type="checkplatlog"> Remember name</td>
							</tr> -->
						</table>
					</div>
				</form>
			</div>
		</div>
		<!-- 平台系统权限选择 -->
		<div id="platchoose" style="height: 400px; visibility: hidden; display: none">
			
			<div style="font-size: 12px; width: 60%; background: #ffffff url('images/bubble.png') no-repeat 10px center; padding: 10px; padding-left: 40px; margin: 5px auto; border: 1px dashed #CAE1FF;">
				Welcome! <span id = "username"></span></div>
			<ul id="nav-shadow" style="list-style: none">

			</ul>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">

    //var platloginurl = "/platsso/platlogin.shtml?method=getUserExistByAsync";
    var platloginurl = "session/creat.jsp";
    //var platloginurl = "/platsso/platlogin.shtml?method=getUserExistByAsync";
    var oslisturl = "conf/osconf.json";
    
   // boolean creatsession = false;


</script>
<script  type="text/javascript" src="script/login.js"></script>
<%@ include file="session/checksession.jsp" %>