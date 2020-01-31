<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>

<head>
<title><fmt:message key="dictionary.list.title"/></title> 
<link rel="stylesheet" type="text/css" media="all" href="<c:url value='/styles/default.css'/>" />  
<script language=javaScript>
function showTree()
{
	
	document.all.objSubjectTree.style.display = "inline";			
    document.all.objSubjectTree.SetXMLPath("<c:out value="${hostPrefix}"/><c:url value='/dictionary.do?method=getTree'/>");
	document.all.objSubjectTree.BackColor = 0xFFD3C0;
	document.all.objSubjectTree.CheckStyle = 1;
	document.all.objSubjectTree.ColumnTitle = "<fmt:message key="dictionary.list.treeColumnTitle"/>";
	document.all.objSubjectTree.ColumnWidth= "300,100";
	document.all.objSubjectTree.FillLayer = 0;
	document.all.objSubjectTree.FreezeColumn = 2;
	document.all.objSubjectTree.CheckStyle=0;	
	document.all.objSubjectTree.RootID = "<c:out value="${roots}"/>";
	document.all.objSubjectTree.Show();
	document.all.objSubjectTree.Expand("<c:out value="${CurrentItem}"/>");

}

function AddSubItem()
{
	
		var GetCurrentItem = window.objSubjectTree.GetCurrentItem();
		var GetCurrentItemName =window.objSubjectTree.GetCurrentItemName();
		if (GetCurrentItem==""||GetCurrentItemName=="")
		{
			alert("<fmt:message key="dicListPage.alert"/>");
			return false;
		}
		window.location.assign("dictionary.do?method=addChild&GetCurrentItem="+GetCurrentItem+"&GetCurrentItemName="+GetCurrentItemName);
	

	return true;
}

function UpItem()
{
		var GetCurrentItem = window.objSubjectTree.GetCurrentItem();
		var GetCurrentItemName =window.objSubjectTree.GetCurrentItemName();
		if (GetCurrentItem==""||GetCurrentItemName=="")
		{
			alert("<fmt:message key="dicListPage.alert"/>");
			return false;
		}

		window.location.assign("dictionary.do?method=update&GetCurrentItem="+GetCurrentItem+"&GetCurrentItemName="+GetCurrentItemName);
	

	return true;
}

function DelItem()
{
		
		var GetCurrentItem = window.objSubjectTree.GetCurrentItem();
		var GetCurrentItemName = window.objSubjectTree.GetCurrentItemName();
		if (GetCurrentItem=="")
		{
			alert("<fmt:message key="dicListPage.alert"/>");
			return false;
		}
		if(confirm("<fmt:message key="dicListPage.cfmdel"/>"+GetCurrentItemName+"<fmt:message key="dicListPage.cfmtail"/>"))
		{
			window.location.assign("dictionary.do?method=setstatus&dotype=4&GetCurrentItem="+GetCurrentItem+"&Leaf="+window.EndChecked.checked);

			return true;
		}
		return false;
}

function FreezeItem()
{
		
		var GetCurrentItem = window.objSubjectTree.GetCurrentItem();
		var GetCurrentItemName = window.objSubjectTree.GetCurrentItemName();
		if (GetCurrentItem=="")
		{
			alert("<fmt:message key="dicListPage.alert"/>");
			return false;
		}
		if(confirm("<fmt:message key="dicListPage.cfmfreeze"/>"+GetCurrentItemName+"<fmt:message key="dicListPage.cfmtail"/>"))
		{
			window.location.assign("dictionary.do?method=setstatus&dotype=5&GetCurrentItem="+GetCurrentItem+"&Leaf="+window.EndChecked.checked);
		
			return true;
		}
		return false;
}

function UnFreezeItem()
{
		
		var GetCurrentItem = window.objSubjectTree.GetCurrentItem();
		var GetCurrentItemName = window.objSubjectTree.GetCurrentItemName();
		if (GetCurrentItem=="")
		{
			alert("<fmt:message key="dicListPage.alert"/>");
			return false;
		}
		if(confirm("<fmt:message key="dicListPage.cfmunfreeze"/>"+GetCurrentItemName+"<fmt:message key="dicListPage.cfmtail"/>"))

		{
			window.location.assign("dictionary.do?method=setstatus&dotype=6&GetCurrentItem="+GetCurrentItem+"&Leaf="+window.EndChecked.checked);

			return true;
		}
		return false;
}
function SetDisOrder()
{
	var GetCurrentItem = window.objSubjectTree.GetCurrentItem();
	if (confirm("<fmt:message key="dicListPage.cfmorder"/>"))
	{
		var MovedItems;
		MovedItems = window.objSubjectTree.GetMovedItems();
		if (MovedItems.length > 0 )
		{
			window.location.assign("dictionary.do?method=setdisorder&MovedItems="+MovedItems+"&GetCurrentItem="+GetCurrentItem);
		}
		return true;
	}
	else
		return false;

}
</Script>
</head>
<body leftmargin="0"   rightmargin="0"  topmargin="0"  bgcolor="#FFFFFF" topmargin="0" onload="showTree()">
<br>
<div align="center"> 
  <table width="90%" border="0" align="center" cellpadding="0" cellspacing="1" bordercolor="#CCCCCC" bgcolor="#CCCCFF" class="TableBgColor" >
    <tr align="center" valign="middle"> 
      <td height="24" colspan="2" class="Title"><font color="#990000">&nbsp;<strong><font style=""><fmt:message key="dicListPage.title"/></font></strong></font></td>
    </tr>
    
    <tr height="400" class="TdBgColor2"> 
     <td height="400" colspan="2"><OBJECT ID="objSubjectTree" WIDTH=100% HEIGHT=100%  onactivate = "return objSubjectTree.Show();"
			CLASSID="CLSID:B6DE0B41-91FE-41BC-9F4B-DC9428EA2B4B"
			codebase="<c:url value="common/ActiveXTree/ActiveX/GoldTree.ocx#Version=1,0,0,9"/>">
	</OBJECT>
     </td>    
    </tr>
     
    <tr bgcolor="#F6F6F6" style="FONT-SIZE: x-small; height : 29px;">
   <td height="29">
	  <input name="Submit2" type="button" class="button" onClick="window.location.assign('dictionary.do?method=addParent')" value="<fmt:message key="dicListPage.Button1"/>" width=40  border="1"  >
	  <input name="Submit2" type="button" class="button" onClick="javascript:AddSubItem()" value="<fmt:message key="dicListPage.Button2"/>" width=40  border="1"  > 
	  <input name="Submit2" type="button" class="button" onClick="javascript:UpItem()" value="<fmt:message key="button.update"/>" width=40  border="1"  >
	  <input name="Submit2" type="button" class="button" onClick="javascript:FreezeItem()" value="<fmt:message key="button.freeze"/>" width=40  border="1"  > 
	  <input name="Submit2" type="button" class="button" onClick="javascript:UnFreezeItem()" value="<fmt:message key="button.unfreeze"/>" width=40  border="1"  > 
	  <input name="Submit2" type="button" class="button" onClick="javascript:DelItem()" value="<fmt:message key="button.delete"/>" width=40  border="1"  > 
	  
	  <input type="checkbox" id ="EndChecked" name ="EndChecked" ><label for="EndChecked"><font color=Blue><fmt:message key="dicListPage.EndChecked"/></font>
   </td>
   <td width="1%" nowrap algin="center">
   	  &nbsp;
	  <input name="Submit2" type="button" class="button" OnClick="javascript:document.all.objSubjectTree.ForwardCurrentItem();" value="<fmt:message key="button.moveup"/>" width=40  border="1"  > 
	  <input name="Submit2" type="button" class="button" OnClick="javascript:document.all.objSubjectTree.BackwardCurrentItem();" value="<fmt:message key="button.movedown"/>" width=40  border="1"  > 
	  <input name="Submit2" type="button" class="button" onClick="javascript:SetDisOrder()" value="<fmt:message key="button.order"/>" width=40  border="1"  > 
	  &nbsp;
   </td>
    </tr>
   
    <tr align="center" class="FormBottom" > 
      <td height="17" colspan="2" class="FormBottom">&nbsp;</td>
    </tr>
   
  </table >
<br>
<table width="56%" align="center">
	<tr>
		<td><fmt:message key="dicListPage.help" /></td>
	</tr>
</table>

</body>
