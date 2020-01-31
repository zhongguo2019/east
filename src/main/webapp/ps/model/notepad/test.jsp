<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>

<html>
<head>

<script type="text/javascript" src="<c:url value='/ps/model/notepad/scripts/jquery-1.4.2.js'/>"></script>
<script type="text/javascript" src="<c:url value='/ps/model/notepad/scripts/jqueryui/jquery.timers-1.1.2.js'/>"></script>
<script type="text/javascript" src="<c:url value='/ps/model/notepad/notepad.js'/>"></script>
<link rel="stylesheet" type="text/css"  href="<c:url value='/ps/model/notepad/notepad.css'/>">
<link rel="stylesheet"	type="text/css"  href="<c:url value='/ps/model/notepad/all.css'/>" />
<style>
body{margin:0px;padding:3px}
</style>
</head>
<body>

       <div style="background:#00C5CD;height:45px;min-width:120px">
       <div style="width:40px;float:left;padding:5 10"><img width=32 src='ps/framework/images/user_48.png'/>
       </div> 
       <div style="width:80px;float:left;color:#ffffff;font-size:14px;padding-top:5px">
       <b> <c:out value="${user.userName}"/></b><br><font style="font-size:12px"><c:out value="${user.organName}"/></font>
       </div>
        </div>
        <div
			style="margin:0;height:30px;line-height:30px;OVERFLOW: hidden; border: 1px #cccccc solid;margin-top:5px">
			今天的提醒(每1分钟更新一次):
			<a href="notepadAction.do?method=goPage&type=0">设置</a>	
			</div>
		<div style="margin:0;margin-top:5px;height:100px;OVERFLOW: hidden; border: 1px #cccccc solid;">
		<marquee direction="up" height="100" scrollamount="3"  onMouseOut="this.start()" onMouseOver="this.stop()">
			<span id="todaynote"></span>
		</marquee>
		</div>

<div id="msg" class="dlg"></div>
</body>
</html>
 
 <script>

 $(document).ready(function(){   //页面进入家在
	
	    //getttoday(dp.cal.getDateStr())
		getttoday(null);
        //setTimeout($("body").everyTime('60s',gettodayext),6000);
		 
});
 function gettodayext()
 {getttoday(null);
	 }
 </script>