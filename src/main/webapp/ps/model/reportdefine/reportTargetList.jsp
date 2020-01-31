<!-- /ps/model/reportdefine/reportTargetList.-->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>
<c:if test="${error1=='-1'}" >
<script type="text/javascript">
 alert("<fmt:message key="pub.targetoccupy.tip"/>");
</script>
</c:if>

<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en">
    <head>
        <%@ include file="/ps/framework/common/meta.jsp" %>
        <title><fmt:message key="webapp.prefix"/></title>
 <link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>
 <link rel="stylesheet" type="text/css" media="all" href="<c:url value='${displaytag12Path}/displaytag.css'/>" />
        <script src="<c:url value='${tableCss}/jqueryTab/jquery-1.4.1.min.js'/>" type="text/javascript"></script>
  
<!--   <script type="text/javascript">
window.parent.document.getElementById("ppppp").value= "<fmt:message key="navigation.reportdefine.targetlist"/>";
</script> -->
    </head>
	<body >
	<div class="navbar">
		<table>
			<tr>
				<td>
					<a href="reportAction.do?method=showSelf" class="easyui-linkbutton "  data-options="iconCls:'icon-back'" ><fmt:message key='button.back'/></a>
					<!--  <a href="reportAction.do?method=showSelf"/>
			        <fmt:message key="button.back"/>
			    </a>-->
			    <a href="javascript:saveTargetAll('<c:out value="${repId}"/>');"  class="easyui-linkbutton " data-options="iconCls:'icon-save'" ><fmt:message key='reportview.button.saveall' /></a>
			
				</td>
				<td>
				<fmt:message key="navigation.reportdefine.targetlist"/><c:out value="${repName}"/>
					</td>
			</tr>
		</table>
	</div>
	
	<html:hidden property="again" value="0"/>
	<!--<p style="margin-right: 30; margin-top: 8; margin-bottom: 8">
		&nbsp;&nbsp;
			    <a href="reportAction.do?method=showSelf"/>
			        <fmt:message key="button.back"/>
			    </a> 
			     
			     <a href="javascript:saveTargetAll('<c:out value="${repId}"/>');"  style="margin-right:10px;">
					<fmt:message key="reportview.button.saveall"/>
				 </a>-->
			    <form name="importForm" method="post" action="">
			    	<input type="hidden" name="url" id="url"/>
			    </form>
	<!--  </p>-->
	   
	    	<table class="list userList" id="item" style="font-size: 14">
	    		<thead>
		    		<tr style="height:28px">
		    			<th class="sortable" style="width:40px;"><fmt:message key="groupmanage.main.displaynumber"/></th>
		    			<th class="sortable" style="width:160px;"><fmt:message key="common.list.targetfield"/></th>
		    			<th class="sortable" style="width:160px;"><fmt:message key="common.list.targetname"/></th>
		    			<th class="sortable" style="width:80px;"><fmt:message key="common.column.type"/></th>
		    			<th class="sortable" style="width:80px;"><fmt:message key="common.column.length"/></th>
		    			<th class="sortable" style="width:180px;"><fmt:message key="common.column.dic"/></th>
		    			<th class="sortable" style="width:80px;"><fmt:message key="fr.status"/></th>
		    			<th class="sortable"><fmt:message key="directorEnquiries.mapinfo.operation"/></th>
		    		</tr>
	    		</thead>
	    		<tbody>
	    			<c:forEach var="target" items="${ targetList }" varStatus="status">
	    				<tr id="edit<c:out value="${status.index+1 }"/>" <c:if test="${ status.index%2 != 0 }">class="even"</c:if><c:if test="${ status.index%2 == 0 }">class="odd"</c:if> style="height:28px;<c:if test="${ editpkid == target.pkid }">background-color:#FBCFD0;</c:if>">
	    					<td>
	    						<c:if test="${ target.status == '1' && editpkid != target.pkid }">
		    						<c:out value="${target.targetOrder }"/>
	    						</c:if>
	    						<c:if test="${ target.status == '0'  || editpkid == target.pkid }">
	    							<input type="hidden" name="editItem"  value="edit<c:out value="${status.index+1 }"/>"  />
	    							<input type="hidden" name="pkid"  value="<c:out value="${target.pkid }"/>"  />
	    							<input type="text"  name="targetOrder"  value="<c:out value="${target.targetOrder }"/>"  style="width:30px;" />
	    						</c:if>
	    					</td>
	    					<td>
	    						<c:if test="${ target.status == '1' && editpkid != target.pkid }">
		    						<c:out value="${target.targetField }"/>
	    						</c:if>
	    						<c:if test="${ target.status == '0'  || editpkid == target.pkid}">
	    							<input type="text"  name="targetField"  value="<c:out value="${target.targetField }"/>"  style="width:150px;" />
	    						</c:if>
	    					</td>
	    					<td>
	    						<c:if test="${ target.status == '1' && editpkid != target.pkid }">
		    						<c:out value="${target.targetName}"/>
	    						</c:if>
	    						<c:if test="${ target.status == '0'  || editpkid == target.pkid}">
	    							<input type="text"  name="targetName"  value="<c:out value="${target.targetName}"/>"  style="width:150px;" />
	    						</c:if>
	    					</td>
	    					<td>
	    						<select name="dataType" style="width:60px;" <c:if test="${ target.status == '1' && editpkid != target.pkid }">disabled="disabled"</c:if>>
		        					<option value="1"  <c:if test="${ target.dataType == '1' }">selected="selected"</c:if>><fmt:message key="reportdefine.reportItem.dataType.num"/></option>
		        					<option value="2" <c:if test="${ target.dataType == '2' }">selected="selected"</c:if>><fmt:message key="reportdefine.reportItem.dataType.int"/></option>
		        					<option value="3" <c:if test="${ target.dataType == '3' }">selected="selected"</c:if>><fmt:message key="reportdefine.reportItem.dataType.string"/></option>
					        		<option value="4" <c:if test="${ target.dataType == '4' }">selected="selected"</c:if>><fmt:message key="reportdefine.reportItem.dataType.scale"/></option>
			            		</select>
	    					</td>
	    					<td>
	    						<c:if test="${ target.status == '1' && editpkid != target.pkid }">
	    							<c:out value="${target.rulesize}"/><c:if test="${ target.rulesize != target.nowsize }"><font color="red">
	    								<c:if test="${target.nowsize != null && target.nowsize != ''}">
		    								[<c:out value="${target.nowsize}"/>]
	    								</c:if>
	    							</font></c:if>
	    						</c:if>
	    						<c:if test="${ target.status == '0'  || editpkid == target.pkid }">
	    							<input type="text"  name="rulesize"  value="<c:out value="${target.rulesize}"/>"  style="width:40px;" />
	    						</c:if>
	    					</td>
	    					<td>
	    						<select name="dicId" <c:if test="${ target.status == '1' && editpkid != target.pkid }">disabled="disabled"</c:if>>
	    							<option value = "0"><fmt:message key="file.noOleFile"/></option>
		    						<c:forEach var="dic" items="${ dicList }" >
		    							<option value="<c:out value='${dic.dicId }'/>"  <c:if test="${ target.dicPid + ''  == dic.dicId }">selected="selected"</c:if>> 
		    								<c:out value="${dic.dicName }" />
		    							</option>
		    						</c:forEach>
	    						</select>
	    					</td>
	    					<td>
	    						<c:if test="${ target.status == '1' && target.nowsize != null && target.nowsize != '' }">
		    						<font color="green"><fmt:message key="srcdata.manager.issyn"/></font>
	    						</c:if>
	    						<c:if test="${ target.status == '0' }">
	    							<font color="red"><fmt:message key="srcdata.manager.notsyn"/></font>
	    						</c:if>
	    						<c:if test="${ target.status == '1' && (target.nowsize == null || target.nowsize == '')}">
	    							<font color="red"><fmt:message key="srcdata.manager.notuse"/></font>
	    						</c:if>
	    					</td>
	    					<td>
	    						<c:if test="${editpkid != target.pkid && target.pkid != null}">
		    						 <a href="reportTargetAction.do?method=enter&reportId=<c:out value="${repId}"/>&editpkid=<c:out value="${target.pkid}"/>&tableName=<c:out value="${tableName}"/>" style="margin-right:10px;">
							        	<fmt:message key="button.edit"/>
							        </a>
							        <a href="reportTargetAction.do?method=del&reportId=<c:out value="${repId}"/>&targetId=<c:out value="${target.pkid}"/>&tableName=<c:out value="${tableName}"/>" onClick="if(confirm('<fmt:message key="pub.deltarget.tip"/>')){ return true;} else{ return false;}">
							        	<fmt:message key="button.delete"/>
							        </a>
	    						</c:if>
	    						<c:if test="${editpkid == target.pkid }">
		    						 <a href="javascript:saveTarget('edit<c:out value="${status.index+1 }"/>','<c:out value="${repId}"/>');"  style="margin-right:10px;">
							        	<fmt:message key="button.save"/>
							        </a>
							        <c:if test="${ target.status != '0' }">
								        <a href="reportTargetAction.do?method=enter&reportId=<c:out value="${repId}"/>&tableName=<c:out value="${tableName}"/>" >
								        	<fmt:message key="button.cancel"/>
							        	</a>
							        </c:if>
	    						</c:if>
	    					</td>
	    				</tr>
	    			</c:forEach>
	    		</tbody>
	    	</table>
				
	    
</body>
<script type="text/javascript">

	function saveTarget(element,reportId){
		var jsonStr = getJSON(element,1);
		// alert(jsonStr);
		if(!jsonStr){
			alert('<fmt:message key="srcdata.manager.targetname.isnull"/>');
			return;
		}
		saveAJAX(jsonStr,reportId);
	}
	function saveTargetAll(reportId){
		var flag = true;
		var editItem = $("input[name='editItem']");
		if(editItem.size() == 0){
			alert('<fmt:message key="srcdata.manager.nosave.item"/>');
			return;
		}
		var jsonStr = "{\"editsize\":\""+editItem.size()+"\",";
		editItem.each(function(i){
			var temp = getJSON(this.value);
			if(!temp){
				flag = false;
				return false;
			}
			jsonStr += "\"edititem"+i+"\":" + getJSON(this.value);
			if(editItem.size() != i+1){
				jsonStr += ",";
			}
		});
		jsonStr += "}";
		// alert(jsonStr);
		if(!flag){
			alert('<fmt:message key="srcdata.manager.targetname.isnull"/>');
			return;
		}
		saveAJAX(jsonStr,reportId);
	}
	function getJSON(element){
		var flag = true;
		var jsonStr = "{";
		$("#"+element + " td :input").each(function(i){
		
			if(this.name == "editItem") {
				return true;
			}
			if(this.name == "targetName" && this.value == '') {
				flag = false;
				return false;
			}
			jsonStr +="\"" + this.name + "\":\"" + this.value + "\"";
			if($("#"+element + " td :input").size() != i+1){
				jsonStr += ",";
			}
		});
		jsonStr += "}";
		if(!flag){
			return flag;
		}
		return jsonStr;
	}
	function saveAJAX(jsonStr,reportId){
		var url = "reportTargetAction.do?method=saveTarget&reportId=" + reportId;
		$.ajax({
			type: "post",
			url : url,
			data: eval("("+jsonStr+")"), 
			success : function(data){
				location.href="reportTargetAction.do?method=enter&reportId="+reportId+"&tableName=<c:out value='${tableName}'/>";
			},
			error : function(e){
				alert(e);
			}
		});
	}
</script>
</html>