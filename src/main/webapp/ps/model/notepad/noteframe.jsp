<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>


<html>
<script language="javascript" type="text/javascript" src="<c:url value='ps/uitl/My97DatePicker/WdatePicker.js'/>"></script>
<script type="text/javascript" src="<c:url value='/ps/model/notepad/scripts/jquery-1.4.2.js'/>"></script>
<script type="text/javascript" src="<c:url value='/ps/model/notepad/notepad.js'/>"></script>
<link rel="stylesheet" type="text/css"  href="<c:url value='/ps/model/notepad/notepad.css'/>">
<link rel="stylesheet"	type="text/css"  href="<c:url value='/ps/model/notepad/all.css'/>" />
<style>

</style>

<body>
<table width="100%" height=100% border="0" align="center" cellspacing=0 cellpadding=0  style="padding:10px;">
 
  <tr>
    <td style="width:200px"  valign="top">

		<div
			style="margin: 10px；width : 100% ;height:100px;OVERFLOW: hidden; border: 1px #99c5ee solid; padding: 1px;" title="每1分钟更新一次">
		<marquee direction="up" height="100" scrollamount="3"  onMouseOut="this.start()" onMouseOver="this.stop()">
			<span id="todaynote"></span>
		</marquee>
		</div>

		<div id="div1" style="margin: 10px;"></div>
		<div style="margin: 10px">
		<form  name="notePadForm" method="post" action="notepadAction.do?method=serachNotePadBy" style="margin:0;" target="notelistframe">
			    
				            <input type="hidden" name="pkid" id="pkid"></input>
					       <input type="hidden" name="type" id="notetype" value="0"></input>
					       <input type="hidden" name="searchtype" id="searchtype" value="0"></input>
					       <input type="hidden" name="title" value="无标题"></input>
					       <input type="hidden" name="notedate"  id="notedate"></input>
					     
						    <input type="buttom"  onclick="searchAllDate();" value="所有日期"  class="btn"/>
						</form>
		</div>
		<div style="margin: 10px"><input type="button" value="添加备忘" onclick="addform1();"
			class="btn" /> <input type="button" value="添加日程" class="btn"  onclick="addform2();"/></div>	
		<div id="msg" class="dlg"></div>	
		</td>
    <td valign="top" style="padding:10px;" >
    
			<table border=0 cellspacing=0 cellpadding=0 width=100% height=100% style="border: 1px #99c5ee solid;padding-left:3px;"  >
			<tr>
					<td height=42 background="images/images/z_05.jpg" valign="bottom">
							<table border=0 cellspacing=0 cellpadding=0 width=100% id="secTable">
								<tr height=35 align=center>
									<td class=sec2 width=10% id="secTable0" onclick="secBoard('0','sec1');doing();">备忘提醒</td>
									<td class=sec1 width=10% id="secTable1" onclick="secBoard('1','sec1');done();">日程安排</td>
									<td class=sec3 width=80% onclick=>&nbsp;</td>
								
								</tr>
							</table>
							
					</td>
			</tr>
			
			<tr id="formtd" >
					<td height=80 >
			
						  <iframe src="notepadAction.do?method=goPage&type=0"  name="formframe"  height=100% width=100%  frameborder=0 style="border: 0px #99c5ee solid;padding:0px;"  ></iframe>
					 </td>
				</tr>
			<tr>
				
				<td height="4">
				</td>
				</tr>
				<tr>
				
				<td>
				
				    <iframe src="notepadAction.do?method=serachNotePadBy&searchtype=0&type=0"  name="notelistframe"  height=100% width=100%  frameborder=0 style="border: 0px #99c5ee solid;padding:0px;"  ></iframe>
				</td>
			
			</tr>
			</table>
    
    </td>
  </tr>

</table>

</body>
 </html>
 <script>
$("#formtd").hide();	
 WdatePicker({eCont:'div1',onpicked:function(dp){setform(dp.cal.getDateStr())}});



 function setform(datestr)
 {
$(window.frames["formframe"].document).find("#noteDate").val(datestr+ " 08:30:00");	 
$("#notedate").val(datestr);
window.notePadForm.submit();//在框架页中执行查询
	 }

function searchAllDate()
{
	$("#notedate").val("");
	window.notePadForm.submit();//在框架页中执行查询
	}
 
function addform1()
{
	secBoard(0);doing();
	$("#formtd").show("slow");
	}
function addform2()
{
	secBoard(1);done();
	$("#formtd").show("slow");
	}


 $(document).ready(function(){   //页面进入家在
		getttoday(null);

		 setTimeout($("body").everyTime('300s',gettodayext),6000);
		 
	 });
 function gettodayext()
 {getttoday(null);
	 }
 </script>