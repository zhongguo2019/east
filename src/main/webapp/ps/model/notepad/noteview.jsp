<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>


<script type="text/javascript" src="<c:url value='/ps/model/notepad/scripts/jquery.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/ps/model/notepad/jqueryui/jquery-ui-1.8.7.custom.min.js'/>"></script>

<link rel="stylesheet" type="text/css"  href="<c:url value='/ps/model/notepad/jqueryui/base/jquery.ui.all.css'/>">
<link rel="stylesheet" type="text/css"  href="<c:url value='/ps/model/notepad/jqueryui/redmond/jquery-ui-1.8.7.custom.css'/>">
<script type="text/javascript" src="<c:url value='/ps/model/notepad/notepad.js'/>"></script>
<link rel="stylesheet" type="text/css"  href="<c:url value='/ps/model/notepad/notepad.css'/>">
<link rel="stylesheet"	type="text/css"  href="<c:url value='/ps/model/notepad/all.css'/>" />
<style>
.butt{
 background-image: url(images/images/z_05.jpg);
width:100px;height:25px;
text-align:center;
vertical-align:middle;
border: 1px #99c5ee solid; 
margin: 0px;padding:4px
;cursor:hand;
float:left;
}
</style>
<body>			    
				        
						   <table width="100%" height=100%  border="0" cellpadding="0" cellspacing="2" >
							    
							    <c:if test="${type==0}"><!-- 备忘只显示备忘时间 -->
							    <tr id="ndate" height=25>
							      <td  width="60"  nowrap align="center">备忘日期：</td>
							      <td ><c:out value="${note.noteDate}" />
							      </td>
							    </tr>
							    </c:if>
							     <c:if test="${type==1}"><!-- 日程安排显示开始结束时间 -->
							    <tr  id="rcdate" height=25>
							      <td  width="60"   nowrap align="center">日程时间：</td>
							      <td><c:out value="${note.startDate}" />到<c:out value="${note.endDate}"/></td>
							     
							    </tr>
							    </c:if>
							    <tr>
							      <td  width="60"  align="center">记录内容：</td>
							      <td><c:out value="${note.content}"/>
							    
							    </tr>
							    <tr>
							    <td colspan=2 height=22>
							    <div onclick="noremind(<c:out value='${note.pkid}'/>);"  class="butt" style="width:100%;text-algin:center">
							    不再提醒
							    </div> 
							    <div><font color="orange"><span id="message"></span></font></div>
							    
							    </td>
							    </tr>
						     </table>
						     
	</body>					     
						     
<script type="text/javascript">
<!--
function noremind(pkid)
{
	//alert(ptId);
	//var o ={};


	$.ajax({
		   type: "POST",
		   url: "/sjxt/notepadAction.do?method=delete&from=ajax&status=2",
		   data: {pkidstr2:pkid},
		   success: function(response){
	
			 if(response!="")
			 {

					 $("#message").html(response);
				
			 }
	
		   },
		   error : function(response,errText){
			   //alert(response.responseText);
			   alert('warning—step');
		   }
		});	


}
//-->
</script>					     
			