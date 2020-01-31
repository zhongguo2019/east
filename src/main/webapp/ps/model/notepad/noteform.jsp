<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>

<script language="javascript" type="text/javascript" src="<c:url value='ps/uitl/My97DatePicker/WdatePicker.js'/>"></script>
<script type="text/javascript" src="<c:url value='/ps/model/notepad/scripts/jquery.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/ps/model/notepad/notepad.js'/>"></script>
<link rel="stylesheet" type="text/css"  href="<c:url value='/ps/model/notepad/notepad.css'/>">
<link rel="stylesheet"	type="text/css"  href="<c:url value='/ps/model/notepad/all.css'/>" />
<form  name="notePadForm" method="post" action="notepadAction.do?method=saveNotePad" style="margin:0;" target="notelistframe">
			    
				            <input type="hidden" name="pkid" id="pkid" value="${note.pkid}"></input>
					       <input type="hidden" name="type" id="notetype" value="${note.type}"></input>
					       <input type="hidden" name="title" value="无标题"></input>
						   <table width="700" border="0" cellpadding="0" cellspacing="0" >
							    <tr id="ndate">
							      <td nowrap="nowrap" align="center">备忘日期</td>
							      <td colspan="3"><input name="noteDate" type="text" id="noteDate" size="24"  class="Wdate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" value="<c:out value='${note.noteDate}'/>" />
							     
							      </td>
							    </tr>
							    <tr  id="rcdate">
							      <td  nowrap="nowrap" align="center">开始时间</td>
							      <td width="111"><input name="startDate" type="text" id="startDate" size="14"  class="Wdate" onClick="WdatePicker()" value="<c:out value='${note.startDate}'/>" /></td>
							      <td nowrap="nowrap" align="center">结束时间</td>
							      <td width="302"><input name="endDate" type="text" id="endDate" size="14" class="Wdate" onClick="WdatePicker()"  value="<c:out value='${note.endDate}'/>"/></td>
							    </tr>
							    <tr>
							      <td align="center">内容</td>
							      <td colspan="3"><textarea name="content" id="content" cols="65" rows="3"><c:out value="${note.content}"/></textarea>
							      <input type="submit" name="savebutton" id="savebutton" value="保存"  class="btn"/></td>
							    </tr>
						     </table>
						     </form>
<script type="text/javascript">
$("#rcdate").hide();

</script>

<c:if test="${form eq 'yes'  and  type==0}">
<script type="text/javascript">
window.parent.addform1();

</script>
</c:if>	
<c:if test="${form eq 'yes'  and  type==1}">
<script type="text/javascript">
window.parent.addform2();

</script>
</c:if>				