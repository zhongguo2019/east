<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/ps/framework/common/taglibs_plat.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ page import="com.krm.slsint.usermanage.vo.*"%>
<%@ page import="com.krm.slsint.util.SysConfig"%>
<%@ page import="com.krm.slsint.util.FuncConfig"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
System.out.println("basePath = " + basePath);


User user = (User)request.getSession().getAttribute("user");
String orgId = user.getOrganId();
%>

<c:set var="colNames">
	<fmt:message key="exportXML.tree.org.name"/>
</c:set>
<c:set var="orgTreeURL">
	<c:out value="${hostPrefix}" /><c:url value="/treeAction.do?method=getOrganTree"/>
</c:set>
<c:set var="orgButton">
	<fmt:message key="place.select"/>
</c:set>
<c:set var="displaySumType">
	<%= FuncConfig.getProperty("reportview.showCollectType")%>
</c:set>
<c:set var="systemName">
	<%= SysConfig.SYSTEM_ROOT_NAME %>
</c:set>
<c:set var="studentLoanReportId">
	<%= FuncConfig.getProperty("studentloan.reportid")%>
</c:set>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>经营监测日报系统</title>

<link href="ty.css" rel="stylesheet" type="text/css" />
<!--<link href="krm_jyjc.css" rel="stylesheet" type="text/css" />-->
<style type="text/css">
Body {
	margin: 0;
}

form {
	margin: 0;
}

.jyjc_top {
	background-image: url(<c:url value="/plat/images-jyjc/jyjc_g01.gif"/>);
	height: 63px;
	_width:expression((document.documentElement.clientWidth||document.body.clientWidth)<1000?"1000px":"auto");
}
.jyjc_top2 {
	background-image: url(<c:url value="/plat/images-jyjc/jyjc01.jpg"/>);
	background-repeat: repeat-y;
	background-position: left;
	height: 63px;
}
.jyjc_top3 {
	background-image: url(<c:url value="/plat/images-jyjc/jyjc_g02.gif"/>);
	background-repeat: repeat-x;
	background-position: top;
}
.jyjc_top_logo {
	background-image: url(<c:url value="/plat/images-jyjc/jyjc_logo.gif"/>);
	height: 63px;
	width: 235px;
}
.jyjc_top_r {
	background-image: url(<c:url value="/plat/images-jyjc/jyjc_g02.gif"/>);
	background-repeat: repeat-x;
	background-position: top;
	float: right;
	width: auto;
	padding-right: 8px;
	height: 31px;
}
.jyjc_top_r2 {
	float: right;
	clear: right;
	color: #FFF;
	margin-top: 2px;
	margin-right: 52px;
}
.jyjc_top_r2 input,.jyjc_top_r2 select {
	vertical-align: middle;
}
.line2 {
	font-size: 0px;
	background-image: url(<c:url value="/plat/images-jyjc/jyjc_g06.gif"/>);
	background-repeat: repeat-x;
	height: 4px;
}
.jyjc_foot {
	background-color: #25577f;
	font-size: 0px;
	height: 4px;
}


</style>

<style type="text/css">   
*{margin:0px;padding:0px}   
html{overflow:hidden}   
#sideBar{width:200px;height:100%;overflow:auto}   
#toggleBar,.div{   
width:7px;height:100%;   
overflow:hidden;background:#eee;   
cursor:e-resize;border-left:1px solid #ccc;border-right:1px solid #ccc;   
}   
td{display:block;overflow:auto;word-break:break-all;}   
</style>   
<script type="text/javascript" src="<c:url value='/scripts/jquery-1.4.2.js'/>"></script>
<script type="text/javascript" src="<c:url value='/My97DatePicker/WdatePicker.js'/>"></script>
<script type="text/javascript" src="<c:url value='/common/ActiveXTree/ActiveXTree.js'/>"></script>  
<script type="text/javascript">
function aaabbb()
		{

			//点击上面查询按钮，返回到后台从新请求一下。
			//时间的获得
			var date = document.getElementById("dataDate").value;
			if(date =="")
			{
				alert("请选择时间!");
				return false;
			}
			//机构号的获得
			var organ_id = document.getElementById("organId").value;
			if(organ_id == "")
			{
				alert("请选择机构!");
				return false;
			}
			//alert("organ_id=" + organ_id);
			
			//alert("date = " + date);
			//金额单位
			var moneyunit = document.getElementById("moneyunit").value;
			//获得报表列
			var report_id = window.parent.frames["tree"].document.getElementById("reportId").value;
			//alert(report_id);
		//	var reporttarget = document.getElementById("reporttarget").value;
			//alert("url = " + url);
			if(report_id == "")
			{
				alert("请选择报表!");
				return false;
			}
			var url="<c:url value='/searchReport.do?method=inputExtraCondition2&organ_id='/>"+organ_id+"&dataDate="+date+"&moneyunit="
			+moneyunit+"&report_id="+report_id;
			document.forms[0].action="";
			window.parent.frames["view"].location=url;
			return false;
		}
		
		//报表打印函数
		function PreviewReport()
		{
			var GoldReport1 = window.parent.frames["view"].document.getElementById("GoldReport1");
//			alert("GoldReport1" +GoldReport1);
			GoldReport1.PrintView();
		}
		//报表保存xls函数
		function SaveToExcel(){
		var GoldReport1 = window.parent.frames["view"].document.getElementById("GoldReport1");
	//	alert("GoldReport1" +GoldReport1);
		var suc= GoldReport1.SaveExcelFile("");
			if(suc == true){
				doTimer();
			}
		}
		
		var the_count=0;
		var the_timeout;
		function doTimer(){
		var GoldReport1 = parent.window.frames["view"].document.getElementById("GoldReport1");
			if(the_count!=12){
				var printOver = GoldReport1.GetExcelThreadState();
				if(printOver==2){
					alert("<fmt:message key='reportSaveExcel.err'/>");
				}else if(printOver==1){
					the_timeout = setTimeout("doTimer();", 1000);
					the_count++;
				}else if(printOver==0){
					alert("<fmt:message key='reportSaveExcel.ok'/>");
				}
			}
		}		

</script>

</head>
<body scroll="no" >
	<table width="100%" height="100%" cellspacing="0" cellpadding="0">
		<tr>
			<td style="vertical-align: top;">
					<html:form action="datacompare.do?method=dataCompare" method="post">
					<input type="hidden" name="organId" id="organ_id" value="<c:out value='${organId}' />"/>
					<input type="hidden" name="organName"/>
				<div class="jyjc_top">
				  <div class="jyjc_top2">
				    <div class="jyjc_top3">
				    <div class="jyjc_top_r"><img src="<c:url value='/plat/images-jyjc/jyjc_g03.gif'/>" width="201" height="31" border="0" usemap="#Map" />
				      <map name="Map" id="Map">
				        <area shape="rect" coords="32,8,49,23" href="#"  alt="导出表格" onclick="SaveToExcel();"/>
				        <area shape="rect" coords="72,6,92,25" href="#" alt="打印预览" onclick="PreviewReport();"/>
				        <area shape="rect" coords="112,5,129,23" href="#"alt="全屏" onclick="window.open(window.parent.document.frames['view'].location);" />
				        <c:if test="${loginFromPlatFlag=='true'}">
				        <area shape="rect" coords="149,5,171,24" href="<c:url value='/loginAction.do?method=logoutToPlat'/>" alt="退出" target="_parent"/>
				        </c:if>
				        <c:if test="${loginFromPlatFlag=='false'}">
				        <area shape="rect" coords="149,5,171,24" href="<c:url value='/loginAction.do?method=enterLogin'/>" alt="退出" target="_parent"/>
				        </c:if>
				      </map>
				    </div>
				    <div class="jyjc_top_r2">
				    <table>
					    <tr>
						    <td>
						   		 日期：
						    </td>
					    	<td>
					      		<label>
					        		<input id="dataDate" name="dataDate"  value="<c:out value="${logindate1}"/>" class="Wdate" type="text" size="12" onclick="WdatePicker()"/>
					      		</label>
					        </td>
					        <td>
					      	     机构：
					    	</td>
					        <td>
					    	<slsint:ActiveXTree left="220" top="325" width="200"
											height="200" xml="${orgTreeURL}" bgcolor="0xFFD3C0"
											rootid="<%=orgId %>" columntitle="${colNames}"
											columnwidth="200,0,0,0" formname="reportViewForm"
											idstr="organId" namestr="organName" checkstyle="0" filllayer="2"
											txtwidth="200" buttonname="${orgButton}">
										</slsint:ActiveXTree>
					        </td>
					        <td>
					    		金额单位：
					    	</td>
					    	<td>
							    <select name="moneyunit" id="moneyunit">
							      <option value="100000000">亿元</option>
							      <option value="1000000">百万</option>
							      <option value="100000">十万</option>
							      <option value="10000">万</option>
							      <option value="1000">千</option>
							      <option value="100">百</option>
							      <option value="1">元</option>
							    </select>
					   		 </td>
					   		 <td>
					    		<input type="image" src="<c:url value='/plat/images-jyjc/jyjc_g05.gif'/>" width="67" height="21" onclick="return aaabbb()"/>
					    	</td>
					    </tr>
				    </table>
				    </div>
				    <div class="jyjc_top_logo"></div>
				  </div>
				</div>
				</div>
				</html:form>
				<div class="line2"></div>
				<div class="jyjc_sj"></div>
			</td>
		</tr>
		<tr>
			<td height="100%" style="vertical-align: top">
				  <table  width="100%" height="100%" cellspacing="0" cellpadding="0">
					<tr width="100%" height="100%">
						<td width="1" height="100%" >
							<div id="sideBar" style="width:200px;">
								<div style="width: 100%; height: 100%">
									<iframe id="menuIframe" height="100%"  src="<%=basePath %>plat/report/reportTree.jsp" name="tree" width="100%" scrolling="auto">
									</iframe>
								</div>
							</div>
						</td>
						<td width="1" onmousedown="setDrag()" id="toggleBar" height="100%"></td> 
						<td height="100%"><div><iframe src="<%=basePath %>plat/report/viewReport1.jsp" id="reportViewIframe" name="view" width="100%" height="100%" scrolling="auto"></iframe></div></td>
					</tr>
					
				  </table>
			</td>
		</tr>
		<tr>
			<td>
					<div  class="jyjc_foot"></div>
			</td>
		</tr>
	</table>
</body>
<script type="text/javascript">   
$(document).ready(function(){   
//及时调整页面内容的高度   
setInterval(function(){   
var winH=(document.documentElement||document.body).clientHeight;   
$("#tbl,#toggleBar,#main").css("height",winH);   
$("td").each(function(){$(this).html()||$(this).html(" ")});   
},100)   
}   
);   
  
var begin_x;   
var drag_flag = false;
document.body.onmousemove = mouseDrag
document.body.onmouseup = mouseDragEnd
//半透明的拖动条（模拟）   
var alphaDiv="<div class='div' id='alphaDiv' style='position:absolute;height:2000px;top:0;z-index:10001;filter:alpha(opacity=50);opacity:0.5;left:200px'> </div>";   
var maskDIV="<div class='div' id='maskDIV' style='position:absolute;width: 2000px; height:2000px; left:0; top:0;z-index:10000;filter:alpha(opacity=0);opacity:0.5;'></div>";   
function setDrag(){   
drag_flag=true;   
begin_x=event.x;   
//添加蒙板   
createMask();   
//添加半透明拖动条   
$(alphaDiv).css("left",$("#toggleBar")[0].offsetLeft).appendTo("body");
$(maskDIV).appendTo("body");
}   
  
//关键部分   
function createMask(){   
//创建背景   
var rootEl=document.documentElement||document.body;   
var docHeight=((rootEl.clientHeight>rootEl.scrollHeight)?rootEl.clientHeight:rootEl.scrollHeight)+"px";   
var docWidth=((rootEl.clientWidth>rootEl.scrollWidth)?rootEl.clientWidth:rootEl.scrollWidth)+"px";
var shieldStyle="position:absolute;top:0px;left:0px;width:"+docWidth+";height:"+docHeight+";background:#000;z-index:9000;filter:alpha(opacity=0);opacity:0";   
$("<iframe id='shield' style=\""+shieldStyle+"\"></iframe>").appendTo("body");   
}   
//拖动时执行的函数   
function mouseDrag(){   
if(drag_flag==true){   
if (window.event.button==1){   
var now_x=event.x;   
var value=parseInt($("#alphaDiv")[0].style.left)+now_x-begin_x;   
$("#alphaDiv")[0].style.left=value+"px";   
begin_x=now_x;   
}   
$("body").css("cursor","e-resize"); //设定光标类型   
}else{   
try{   
$("#shield").remove();   
$("#sideBar")[0].style.pixelWidth=$("#alphaDiv")[0].style.left;   
$("#alphaDiv").remove();   
}catch(e){}   
}   
}   
  
function mouseDragEnd(){ 
//设置拖动条的位置   
if(drag_flag==true){   
//设定拖动条的位置（设定左侧的宽度）   
$("#sideBar")[0].style.pixelWidth=parseInt($("#alphaDiv")[0].style.left);   
$("#shield").remove(); //删除蒙板   
$("#alphaDiv").remove(); //删除半透明拖动条   
$("#maskDIV").remove(); 
$("body").css("cursor","normal"); //恢复光标类型   
drag_flag=false;
}   
}   
</script>   

		<script type="text/javascript">
					/*
					 * iframe的高度会直接影响到
					 */
					function makeContentAutoHeightInBody() {
						// 取得菜单的frame
						var menuIframe = document.all.menuIframe;
						// 取得展示报表的frame
						var reportViewIframe = document.all.reportViewIframe;
						
						menuIframe.height = "1px";
						reportViewIframe.height = "1px";
						
						document.all.menuIframe.height = document.all.menuIframe.parentNode.parentNode.parentNode.clientHeight;
						document.all.reportViewIframe.height = document.all.reportViewIframe.parentNode.clientHeight;
						
					}
					
					// window.onresize = makeContentAutoHeightInBody;
					
					// 初始时，要先自适应下
					//makeContentAutoHeightInBody();
					
					//setInterval("makeContentAutoHeightInBody()", 200);
					function changeTree1(){}
		</script>
</html>