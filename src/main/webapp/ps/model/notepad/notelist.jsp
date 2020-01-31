<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>

<script type="text/javascript" src="<c:url value='/ps/model/notepad/scripts/jquery-1.4.2.js'/>"></script>
<script type="text/javascript" src="<c:url value='/ps/model/notepad/notepad.js'/>"></script>
<link rel="stylesheet" type="text/css"  href="<c:url value='/ps/model/notepad/notepad.css'/>">
<link rel="stylesheet"	type="text/css"  href="<c:url value='/ps/model/notepad/all.css'/>" />
<style>
.bottomtd{
 /*background-image: url(images/images/z_05.jpg);;*/

}
.mouseOver
		{
		    background-color:#DFD;
		}
		
.selected
		{
		    background-color:#B3E1A3;
        }
</style>
<body>
<form name="notePadForm"   method="post" action="notepadAction.do?method=delete" style="margin:0;"   onsubmit="return confirmcheck();">
<table border=0 cellspacing="0" cellpadding="0" width=100% height=100%>
	<tr>
		<td>
		    <div style="width:100%;height:100%;overflow:auto">
			<display:table style="width:99%" name="notePadList" cellspacing="0" cellpadding="0" defaultsort="0" id="note" pagesize="20" class="list" sort="page" requestURI="">
					<%-- Table columns --%>
					<display:column sortable="true" headerClass="sortable" style="width:1%;white-space:nowrap" title="序号" >
					<input type="checkbox" name="pkidstr"  value="<c:out value='${note.pkid}'/>"/>	<c:out value='${note_rowNum }'/>				
					</display:column>
					<c:if test="${type==0}">
					<display:column sortable="true" headerClass="sortable" style="width:1%;white-space:nowrap" title="日期">
						<c:out value="${note.noteDate}"/>
					</display:column>
					</c:if>
					<c:if test="${type==1}">
					
					<display:column sortable="true" headerClass="sortable" style="width:1%;white-space:nowrap" title="日程安排日期">
					      <c:out value="${note.startDate}"/>-<c:out value="${note.endDate}"/>
					      
					</display:column>
						</c:if>
					<display:column sortable="true" headerClass="sortable" style="width:20%" title="内容(*双击行可查看)">
						<c:out value="${note.content}"/>
					</display:column>
				
					
					<display:column sortable="true" headerClass="sortable" style="width:1%;white-space:nowrap" title="提醒状态">
						
						<c:if test="${note.status=='1'}">正常</c:if>
						<c:if test="${note.status=='2'}">不提醒</c:if>
					</display:column>
					<display:column style="width:1%;white-space:nowrap" title="操作">
						<a href="#" onclick="view(<c:out value='${note.pkid}'/>)" >查看</a>&nbsp;&nbsp;
						<c:if test="${note.status=='1'}"><a href="notepadAction.do?method=delete&pkidstr2=<c:out value='${note.pkid}'/>&status=2">不提醒</a></c:if><!--若果没有status参数则被视为直接删除 -->
						<c:if test="${note.status=='2'}"><a href="notepadAction.do?method=delete&pkidstr2=<c:out value='${note.pkid}'/>&status=1"">提醒</a></c:if>&nbsp;&nbsp;
						<a href="notepadAction.do?method=getNotePadById&type=<c:out value='${note.type}'/>&from=edit&pkid=<c:out value="${note.pkid}"/>" target="formframe">编辑</a>&nbsp;&nbsp;
						
					</display:column>
				</display:table>
			</div>
		</td>
	</tr>
	<tr>
		<td height=28  class="bottomtd">
		<input type="hidden"  name="pkidstr2"/><!-- 批量串，直接提交复选过去是String[] ,忘记直接提交为都好分隔的字符串了-->
				
				全选<input type="checkbox" name="checkall"  id="checkall" onclick="CheckAll();" />
			
				<!-- <input type="button"  class="btn" onclick="return deletenp()" value="删除"/>&nbsp;&nbsp;&nbsp; -->
				[正常<input type="radio" name="status"  value="1" checked/>]
				&nbsp;[不提醒<input type="radio" name="status" value="2" />]
				&nbsp;[删除：<input type="radio" name="status" value="" />]
				<input type="submit"  class="btn" value="设置"/>&nbsp;&nbsp;
				
		</td>
	</tr>
</table>

				
				
				
</form>
</body>
<script>

//highlightTableRows("note");
function deletenp()
{
   document.all.status.disabled=true;   //删除的话不需要通过状态位
   alert( document.all.status.value);
   getPkidStr();
   window.notePadForm.submit();
  // return false;
	}

function CheckAll()
{

  var value=document.all.checkall.checked;
  if(document.all.pkidstr.length)
	  {
	  for (var i=0;i<document.all.pkidstr.length;i++)
		  {
		      var check = document.all.pkidstr[i];
		      check.checked = value;
		  }
	  }
	  else
	  {
	  document.all.pkidstr.checked=value;
	  }

}


function getPkidStr()
{
	 var checkList="";
	  if(document.all.pkidstr.length)
	  {
			for (i=0; i<document.all.pkidstr.length; i++) 
			{
				if(document.all.pkidstr[i].checked==true)
				{
					checkList+=document.all.pkidstr[i].value+",";					
				}
			}
		checkList=checkList.substring(0,checkList.length-1);
	    document.all.pkidstr2.value=checkList;
		}
		
		//alert(document.all.pkidstr2.value);
	
		//return false;
}

function confirmcheck()
{
	getPkidStr();

    var status;
	var obj=document.getElementsByName("status");
	for (i=0;i<obj.length;i++){  //遍历Radio  
		if(obj[i].checked){
			status=obj[i].value;
			//alert(obj[i].value);//获取Radio的值
		} 
	}
	
	
  // alert(status);
    if(status=="2")
	 {
	     if(confirm("您确定要将信息设置为不提醒吗？")==true)
	     {
	         return true;
		 }return false;
	 }
   if(status=="")
	 {
	     if(confirm("删除操作不可恢复，您确定要删除选中信息吗？")==true)
	     {
	         return true;
		 }return false;
	 }
     

}

function view(pkid)
{
    var url;
    url="notepadAction.do?method=getNotePadById&from=view&pkid="+pkid;
    alert(url);
	if(window.parent)
	{
		//window.parent.dialog(url);
		window.parent.parent.OpenPanel(url,300,200,'提醒');
	}
}

$(document).ready(function(){   //页面进入家在
	$('tbody > tr', $('#note')).dblclick(function(){
		//$('.selected').removeClass('selected');					
		$(this).addClass('selected'); //this 表示引发事件的对象，但它不是 jquery 对象
		$('#note input[name=pkidstr]:checked').attr("checked","");
		$(this).find('input[name=pkidstr]').attr("checked",true);
		//alert($(this).find('input[name=pkidstr]').val());
		view($(this).find('input[name=pkidstr]').val());


	}).hover(		//注意这里的链式调用
		function(){
			$(this).addClass('mouseOver');
		},
		function(){
			$(this).removeClass('mouseOver');
		}
	);	
	/*$('tbody > tr', $('#note')).click(function(){
		//$('.selected').removeClass('selected');					
		$(this).addClass('selected'); //this 表示引发事件的对象，但它不是 jquery 对象
		//$('#content input[name=noticeSelect]:checked').attr("checked","");
		//$(this).find('input[name=noticeSelect]').attr("checked",true);

		if($(this).find('input[name=pkidstr]').attr('checked')==true)
		{
			$(this).find('input[name=pkidstr]').attr("checked","");
			$(this).removeClass('selected');
		}
		else
		{
		
		$(this).find('input[name=pkidstr]').attr("checked",true);
		}
		//alert($(this).find('input[name=noticeSelect]').attr('checked'));


	}).hover(		//注意这里的链式调用
		function(){
			$(this).addClass('mouseOver');
		},
		function(){
			$(this).removeClass('mouseOver');
		}
	);*/
	 
 });


</script>