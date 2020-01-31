<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>

<html>
<head>
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/ps/style/default.css'/>" />
<script type="text/javascript" src="<c:url value='/ps/scripts/prototype.js'/>"></script>

<style>
a {
    color: #183569;
}
a:visited {
    color: #183569;
}
a:hover {
    background-color: transparent;
    color: #cc0000 !important; 
    text-decoration: none;
}

*{
	margin:0;
	padding:0;
}
dl{
	width:350px;
}
dt{
	float:left;
	width:80px;
	text-align:right;
	height:20px;
}
dd{
	float:left;
	padding-left:10px;
	width:260px;
	height:20px;
	position:relative;
}

#pcc{
	margin-left:20px;
	margin-right:20px;
	BACKGROUND: #DDD;
	BORDER-RIGHT: #fefefe 1px solid;
	BORDER-BOTTOM: #AAAAAA 1px solid;
	BORDER-RIGHT: #AAAAAA 1px solid;
	HEIGHT: 150px;
	color: #222244;
    font-weight: bold;
}
#pc{
	margin-top:15px;
	BACKGROUND: #BCBCBC;
	BORDER-TOP: #fefefe 1px solid;
	BORDER-BOTTOM: #AAAAAA 1px solid;
	BORDER-RIGHT: #AAAAAA 1px solid;
	HEIGHT: 24px;
    font-size: 13px;
	TEXT-ALIGN: left;
}
#progress{
	BACKGROUND: #001116;
	OVERFLOW: hidden;
	WIDTH: 100%;
	POSITION: absolute;
	HEIGHT: 24px;
}

#prop{
	FONT-SIZE: 10px;
	COLOR: #ffffff;
	FONT-FAMILY: arial;
	POSITION: relative;
	TOP: 20%;
	WIDTH: 100%;
	HEIGHT: 24px;
	TEXT-ALIGN: center
}

#bproc{
	text-align: left;
	margin-left: 20px;
	margin-top: 10px;
	height: 20px;
	WIDTH: 90px;
	BORDER-BOTTOM: #222255 2px solid;
    font-size: 14px;
}
.count{
	color: #222244;
	text-decoration: underline;
}

</style>

</head>

<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0" onload="doInit()">

<table border="0" cellpadding="0" cellspacing="0" width="100%" height="100%">
  <tr>
    <td width="100%" bgcolor="#EEEEEE" valign="top">
    <br>
    <br>

<P>&nbsp;</P>

<DIV id="progressDiv" align="center" style="display:none">

	<DIV id=pcc>
		<DIV id=bpc style="text-align:left">
			<DIV id=bproc><fmt:message key="reprule.check.creating"/></DIV>
		</DIV>
		<DIV id=pc>
			<DIV id=progress style="FILTER:Alpha(Opacity=50,FinishOpacity=100,Style=1,StartX=0,StartY=0,FinishX=100,FinishY=0);">
			</DIV>
			<DIV id=prop></DIV>
		</DIV>
		
		<DIV id=buildInfo style="margin-top:30px;text-align:left">
			<dl>
				<dt><fmt:message key="reprule.check.compressing.total"/></dt><dd><span id="total" class="count"></span></dd>
				<dt><fmt:message key="reprule.check.success"/></dt><dd><span id="created" class="count"></span></dd>
			</dl>
		</DIV>
		
	</DIV>
	
</DIV>

  <DIV id=res style="display:none"></DIV>
  <form action="" method="post" style="display:none">
  </form>
  

</td>
</tr>
</table>

<script type="text/javascript">
var pro = 0.0;
var pcWidth = 500;
function setRate(v, el, abs) {
	pro=(abs)? v : pro + v
	if(pro<0.0){pro = 0.0}
	else if(pro>100.0){pro = 100.0}
	filterEl = el.children[0];
	valueEl = el.children[1];
	var w=parseInt(pcWidth*pro/100);
	filterEl.style.width = w + "px";
	valueEl.innerText = pro + "%";
}

	
var pu;
var intv;
function doInit(){	
	$("pc").style.width=pcWidth;
	$("progressDiv").style.display="block";
	setRate(0.0, pc, true);
	intv=setInterval(dynEff,1000);
	observePro();
}

function observePro(){	
	var options={onSuccess:suc,decay:1.5,frequency:1};
	var url='<c:out value="${hostPrefix}" /><c:url value="/datavalidation.do" />?method=createFilePercent&rulecheckPercen=<%=request.getParameter("rulecheckPercen") %>&_='+(new Date()).getMilliseconds();
	pu=new Ajax.PeriodicalUpdater($("res"),url,options);
}

function trim(str){
	return str.replace(/(^\s*)|(\s*$)/g,"");
}

function suc(request){
	var resp=trim(request.responseText);
	resp = eval("(" + resp + ")");
	if(resp.isfinished){
		$("total").innerText=resp.total;
		$("created").innerText=resp.compress;
	}
	if(resp.isfinished && resp.isfinished == "true"){
		clearInterval(intv);
		setRate(100.0, pc, true);
		$("bproc").style.width="110px";
		$("bproc").innerHTML='<fmt:message key="databuild.progress.complete"/>';
		pu.stop();
	} else if(resp.isfinished && resp.isfinished == "false"){
		setRate((resp.compress/resp.total * 100).toFixed(2), pc, true);
	}else if(resp.isfinished && resp.isfinished == "failed"){
		clearInterval(intv);
		setRate(0.0, pc, true);
		$("bproc").innerText='<fmt:message key="reprule.check..progress.failed"/>';
		pu.stop();
	}
}

var dn=0;
function dynEff(){
	dn=(dn)%6+1;
	var ds='';
	for(var i=0;i<dn;i++)ds+='.'
	$("bproc").innerText='<fmt:message key="reprule.check.creating"/>'+ds;
}

</script>

</body>
</html>
