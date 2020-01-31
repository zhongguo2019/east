<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<script language="javascript" type="text/javascript" src="<c:url value='/common/My97DatePicker/WdatePicker.js'/>"></script>
<script type="text/javascript" src="<c:url value='/scripts/jquery-1.4.2.js'/>"></script>
<script type="text/javascript" src="<c:url value='/common/jqueryui/jquery-ui-1.8.7.custom.min.js'/>"></script>
<link rel="stylesheet"	type="text/css"  href="<c:url value='/styles/all.css'/>" />
<link rel="stylesheet" type="text/css"  href="<c:url value='/common/jqueryui/base/jquery.ui.all.css'/>">
<link rel="stylesheet" type="text/css"  href="<c:url value='/common/jqueryui/redmond/jquery-ui-1.8.7.custom.css'/>">
<script>


function setstep(obj1,obj2)
{

	$(obj1).hide("slow");
	$(obj2).show("slow");
	

}

function doing(){  //备忘和日程安排设置为不同的日期存存储
	$("#rcdate").hide();
	$("#ndate").show();
	
}
function done(){  //备忘和日程安排设置为不同的日期存存储

	$("#ndate").hide();
	$("#rcdate").show();
}

function getote(ptId)
{
	//alert(ptId);
	var o ={};


	$.ajax({
		   type: "POST",
		   url: "/sjxt/notepadAction.shtml?method=setFormPtCom",
		   data: {ptid:ptId},
		   success: function(response){
	
			 if(response!="")
			 {

				 var jsondata=eval("("+response+")");
					// $.getJSON('jsonData.json?id',function(data){   
					 //遍历JSON中的每个entry   
					 $("#ptComname").empty();
					 $.each(jsondata,function(idx,item){   
						// alert("enname:"+item['enname']+",znmane:"+item['znname']);
						 $("#ptComname").append("<option value='"+item['id']+":"+item['enname']+"'>"+item['znname']+"</option>");
					 });
				
			 }
	
		   },
		   error : function(response,errText){
			   //alert(response.responseText);
			   alert('warning—step');
		   }
		});	


}

</script>



<body>
<table width="100%" height=100% border="0" align="center">
  <tr>
    <td colspan="2"  height="30px" class="TDTITLE">&nbsp;您坐在的位置：设置提醒和日程安排</td>
  </tr>
  <tr>
    <td width="200"  valign="top">
    
     <div style="margin:10px；width:100%:OVERFLOW-Y:auto;OVERFLOW-X:hidden;scroll;border: 1px #99c5ee solid;padding:1px;">
    <ul>
    <li>您今天没有备忘提醒</li>
     <li>您今天没有日程安排</li>
    </ul>
    </div>
    
   <div id="div1"  style="margin:10px">
   </div>
    
    <div style="margin:10px">
    <input type="button" value="添加备忘"  class="btn"/>
    <input type="button" value="添加日程"  class="btn"/>
    </div>
    </td>
    <td valign="top">
    
<div id="tableA" style="border: 0px;margin:0 0 .1em 0;width:100%;height:100%">
		<ul style="background: white;border: 0 white solid;">
			<li style="font-size:12px;"><a href="#tableA_tab1" onclick="doing();" >备忘提醒</a></li>
			<li><a href="#tableA_tab1" onclick="done();" >日程安排</a></li>
		</ul>
	
    <div id="tableA_tab1" name="Tab1" style="height:800; width:100%;OVERFLOW-Y:auto;OVERFLOW-X:hidden;scroll;border: 1px #99c5ee solid;padding:10px;">
    <!-- 备忘，日程安排表单 -->
   
   <div style="height:120px; width:100%;OVERFLOW-Y:auto;OVERFLOW-X:hidden;scroll;border: 1px #99c5ee solid;padding:1px;">
    <form  name="notepadForm" method="post" action="notepadAction.shtml" style="margin:0">
    
	
		<input type="hidden" name="type" value="0"></input>
		       <input type="hidden" name="title" value="无标题"></input>
			   <table width="700" border="0" cellpadding="0" cellspacing="0" >
			    <tr id="ndate">
			      <td nowrap="nowrap" align="center">备忘日期</td>
			      <td colspan="3"><input name="noteDate" type="text" id="noteDate" size="14"  class="Wdate" onClick="WdatePicker()" /></td>
			    </tr>
			    <tr  id="rcdate">
			      <td  nowrap="nowrap" align="center">开始时间</td>
			      <td width="111"><input name="startDate" type="text" id="startDate" size="14"  class="Wdate" onClick="WdatePicker()" /></td>
			      <td nowrap="nowrap" align="center">结束时间</td>
			      <td width="302"><input name="endDate" type="text" id="endDate" size="14" class="Wdate" onClick="WdatePicker()"  /></td>
			    </tr>
			    <tr>
			      <td align="center">内容</td>
			      <td colspan="3"><textarea name="content" id="content" cols="65" rows="5"></textarea>
			      <input type="submit" name="savebutton" id="savebutton" value="保存"  class="btn"/></td>
			    </tr>
			    
			    
			  </table>
	
	
	</form>
 </div>
     <div style="height:500; width:100%;OVERFLOW-Y:auto;OVERFLOW-X:hidden;scroll;border: 0px #99c5ee solid;margin:2px;">
    <iframe src="notelist.jsp"   height=100% width=100%  frameborder=0 style="border: 0px #99c5ee solid;padding:0px;"  ></iframe>
    </div>
    
    
    
    </div>
    	
	</div>
    
    </td>
  </tr>

</table>
<script>
 WdatePicker({eCont:'div1',onpicked:function(dp){alert('你选择的日期是:'+dp.cal.getDateStr())}});


 $(document).ready(function(){
 	$( "#tableA" ).tabs();		
 	$("#rcdate").hide();		
 	//$("#tableA_tab1").hide();
 });
 </script>
 
 </body>