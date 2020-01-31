<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>

<%@ page import="com.krm.slsint.util.FuncConfig"%>

<c:set var="showHelp">
	<%= FuncConfig.getProperty("showHelp","yes")%>
</c:set>

<html>
<head>
<link rel="stylesheet" href="<c:url value="/ps/framework/images/css.css"/>"  type="text/css">
<script type="text/javascript" src="<c:url value='/ps/scripts/prototype.js'/>"></script>
<STYLE type=text/css>
<!--
A:link {COLOR: #FFFFFF; TEXT-DECORATION: none}
A:visited {COLOR: #FFFFFF; TEXT-DECORATION: none}
A:hover {COLOR: #FFFF00; TEXT-DECORATION: underline}
.il {display: inline}
-->
</STYLE>

<script type="text/javascript" src="<c:url value='/plat/zhbs/pngfix.js'/>"></script>

<link href="<c:url value='/plat/zhbs/ty.css'/>" rel="stylesheet" type="text/css" />
<link href="<c:url value='/plat/zhbs/krm-hzjr.css'/>" rel="stylesheet" type="text/css" />
<script type="text/javascript">
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}
function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
</script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
				<div class="hzjr">
				  <div class="hzjr_top">
				    <div class="hzjr_top2">
				      <div class="hzjr_top2_r">
				      	<a href="<c:url value='/template/tianjin/fmDialog.jsp'/>" target="mainFrame" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image1','','plat/zhbs/krm-hzjr/hzjr_j02-.jpg',1)">
				      		<img src="<c:url value='/plat/zhbs/krm-hzjr/hzjr_j02.jpg'/>" alt="<fmt:message key="top.modify.pass"/>" name="Image1" width="30" height="34" border="0" id="Image1" />
				      	</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				      	<a href="javascript:logOut();" onmouseout="MM_swapImgRestore()" onmouseover="MM_swapImage('Image2','','plat/zhbs/krm-hzjr/hzjr_j03-.jpg',1)">
				      		<img src="<c:url value='/plat/zhbs/krm-hzjr/hzjr_j03.jpg'/>" alt="<fmt:message key="user.logout"/>" name="Image2" width="22" height="34" border="0" id="Image2" />
				      	</a>
				      </div>
				      <div class="hzjr_top2_l"><fmt:message key="logpage.welcome"/><span><c:out value="${user.name}"/></span></div>
				    </div>
				  </div>
				</div>
<div id="users" style="display:none"></div>
<script>
	top.defaultStatus='[<c:out value="${user.organId}"/>]<c:out value="${curorgan.full_name}"/>'
</script>
<script type="text/javascript">
	//wangxin annotation
	//window.onbeforeunload = function(){
		//window.open("loginAction.do?method=close","_self");
		//window.location.href="<c:url value="loginAction.do?method=close"/>";
		//observePro();
	//}
	function observePro(){
		var options={onSuccess:processRequest}
		var url='<c:out value="${hostPrefix}" /><c:url value="/loginAction.do" />?method=close';
		pu = new Ajax.Updater($("users"),url,options);
	}
	function processRequest(request) {
	}
	function logOut(){
	  if(confirm('<fmt:message key="msg.confirm.logout"/>')){
	  var platFlag = '<c:out value="${loginFromPlatFlag}"/>';
	  	if(platFlag=='true'){
	  		parent.location.href="<c:url value='/loginAction.do?method=logoutToPlat'/>";
	  	}else{
		  	parent.location.href="<c:url value='/loginAction.do?method=logout'/>";
	  	}
	  }else{
	   	return;
	  }
	}
	<c:if test="${topStyle=='sc' }">
	function getTopInfo(){
		var options={onSuccess:getInfo}
		var url='<c:out value="${hostPrefix}" /><c:url value="/loginAction.do" />?method=getTopInfo';
		pu = new Ajax.Updater($("users"),url,options);
	}
	function getInfo(request) {
		var topinfo = request.responseText;
		var info;
		eval('info='+topinfo);
		
		$('role').innerHTML = info[0].roleName;
		$('date').innerHTML = info[0].nowDate;
	}
	getTopInfo();
	function time()  
  	{  
        var now = new   Date();  
        var h = (now.getHours() > 9) ? now.getHours() : "0" + now.getHours();
        var m = (now.getMinutes() > 9) ? now.getMinutes() : "0" + now.getMinutes();
        var s = (now.getSeconds() > 9) ? now.getSeconds() : "0" + now.getSeconds();
        var noon = (now.getHours() >   12)? "PM" : "AM";
        var myclock = h+":"+m+":"+s+"  "+noon ;  
        $('time').innerHTML = myclock;
        setTimeout("time()",1000);
    }
    time(); 
    </c:if> 
</script>
<script type="text/javascript">
	function openHelpFile(){
		var options={method:'get',onSuccess:processRequest};
		var url='<c:out value="${hostPrefix}" /><c:url value="/mediaHelp.do" />?method=openHelpFile&url='+"s";
		var pu = new Ajax.Request(url,options);
	}
	function processRequest(){
	}
</script>
</body>
</html>
