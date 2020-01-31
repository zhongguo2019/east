<!-- /ps/model/sysmanage/organmanage/organList. -->
<!DOCTYPE html>
<%@ page pageEncoding="UTF-8"%>
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/adminlte.jsp"%>
<c:set var="colName">
	<fmt:message key="organTree.colName"/>
</c:set>
<c:set var="orgTreeURL">
	<c:out value="${hostPrefix}"/><c:url value='/treeAction.do?method=getAreaTreeXML'/>
</c:set>
<c:set var="orgButton">
	<fmt:message key="organTree.area"/>
</c:set>

<html>
<head>
<title><fmt:message key="webapp.prefix"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 <link rel="stylesheet" type="text/css" media="all" href="<c:url value='${displaytag12Path}/displaytag.css'/>" />
 <link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>         
        
		
		

</head>
<body leftmargin="0" onload="forbiddon();">
<script type="text/javascript">
function fuzzySearch(){
		var url = '<c:out value="${hostPrefix}"/>/<c:out value="${systemName}"/>/organAction.do?method=list';
	    document.organForm.action = url;
    	document.organForm.submit();
	
}
function forbiddon(){
	document.onkeydown=function(){if(event.keyCode==8)return false;};
}
</script>
    <form action="" name="organForm" method="post">
	<html:hidden property="again" value="0"/>
	<div class="navbar">
		<table>
			<tr>
				<td>
					<a href="javascript:addOrgan();" class="easyui-linkbutton buttonclass2"   data-options="iconCls:'icon-add'"  ><fmt:message key='usermanage.organ.addorgan'/></a> &nbsp;&nbsp;&nbsp;&nbsp;
				</td>
				<td>
					<fmt:message key="usermanage.organ.form.code1"/>:
				</td>
				<td>
					<input type="text" name="orgcode" id="orgcode" value="<%--<c:out value='${orgcode}'/>--%>" width="150px">
				</td>
				<td>
					<fmt:message key="usermanage.organ.form.short1"/>:
				</td>
				<td>
					<input type="text" name="short_name" id="short_name" value="<%--<c:out value='${short_name}'/>--%>" width="150px">
				</td>
				<td>
					<a href="#" class="easyui-linkbutton buttonclass2" data-options="iconCls:'icon-search'"  name="ss" onclick="javascript:fuzzySearch();" ><fmt:message key='fivegraderep.base.button.query'/></a>
				</td>
			</tr>
		</table>
	</div>
	
			
				    <input type="hidden" name="areaId" value="<c:out value="${areaCode }"/>"/>
	      			<input type="hidden" name="areaName"/>

		<table id="datatable" class="table table-striped table-bordered display table-hover" style="width: 100%">
			<thead>
				<tr>
					<th><fmt:message key="usermanage.organ.form.code1" /></th>
					<th><fmt:message key="usermanage.organ.form.short1" /></th>
					<th><fmt:message key="usermanage.organ.form.organtype1" /></th>
					<th><fmt:message key="usermanage.organ.form.businesstype1" /></th>
					<th><fmt:message key="usermanage.organ.form.begindate1" /></th>
					<th><fmt:message key="usermanage.organ.form.enddate1" /></th>
					<th><fmt:message key="button.operation" /></th>
				</tr>
			</thead>

			<tbody>
				<c:forEach items="${organList}" var="organs">
					<tr>
						<td sort="true" titleKey="usermanage.organ.form.code1" headerClass="sortable" width="6%"><c:out
								value="${organs.code}"
							/></td>

						<td titleKey="usermanage.organ.form.short1" sort="true" headerClass="sortable" width="10%"><c:out
								value="${organs.short_name}"
							/></td>

						<td titleKey="usermanage.organ.form.organtype1" sort="true" headerClass="sortable" width="6%"><c:out
								value="${organs.organ_typename}"
							/></td>

						<td titleKey="usermanage.organ.form.businesstype1" sort="true" headerClass="sortable" width="6%"><c:out
								value="${organs.business_typename}"
							/></td>

						<td titleKey="usermanage.organ.form.begindate1" sort="true" headerClass="sortable" width="6%"><c:out
								value="${organs.begin_date}"
							/></td>
						<td titleKey="usermanage.organ.form.enddate1" sort="true" headerClass="sortable" width="6%"><c:out
								value="${organs.end_date}"
							/></td>

						<td titleKey="button.operation" sort="true" headerClass="sortable" width="6%"><a
							href="organAction.do?method=edit&organid=<c:out value="${organs.pkid}"/>" style="text-decoration: none;"
							class="easyui-linkbutton buttonclass2" data-options="iconCls:'icon-edit'"
						> <fmt:message key="button.edit" />
						</a> <a style="text-decoration: none;" class="easyui-linkbutton buttonclass2" data-options="iconCls:'icon-remove'"
							href="organAction.do?method=del&organid=<c:out value="${organs.pkid}"/>&areaCode=<c:out value="${areaCode }"/>"
							onClick="if(confirm('<fmt:message key="usermanage.organ.form.confirmdel"/>')){ return true;} else{ return false;}"
						> <fmt:message key="button.delete" />
						</a></td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<th><fmt:message key="usermanage.organ.form.code1" /></th>
					<th><fmt:message key="usermanage.organ.form.short1" /></th>
					<th><fmt:message key="usermanage.organ.form.organtype1" /></th>
					<th><fmt:message key="usermanage.organ.form.businesstype1" /></th>
					<th><fmt:message key="usermanage.organ.form.begindate1" /></th>
					<th><fmt:message key="usermanage.organ.form.enddate1" /></th>
					<th><fmt:message key="button.operation" /></th>
				</tr>
			</tfoot>
		</table>

	</form>

</body>

<script type="text/javascript">
	<c:if test="${error2=='-1'}" >
	alert("<fmt:message key="usermanage.organ.form.organrepeat"/>");
	</c:if>
	<c:if test="${error=='-3'}" >
	alert("<fmt:message key="error.organused.delete"/>");
	</c:if>
	<c:if test="${error=='-4'}" >
	alert("<fmt:message key="error.organused.inTree"/>\n<c:out value="${treeNames}"/>");
	</c:if>
	function addOrgan() {
		areaId = document.organForm.areaId.value;
		document.organForm.action = '<c:out value="${hostPrefix}" /><c:url value="/organAction.do" />?method=edit&areaCode='
				+ areaId;
		document.organForm.submit();
	}
	function changeTree1() {
		areaId = document.organForm.areaId.value;
		document.organForm.action = '<c:out value="${hostPrefix}" /><c:url value="/organAction.do" />?method=list&areaCode='
				+ areaId;
		document.organForm.submit();
	}
	$(function() {
		$('#datatable').DataTable({
			"paging" : true,
			"lengthChange" : false,
			"searching" : false,
			"ordering" : true,
			"info" : true,
			"autoWidth" : true,
			"pageLength": 20,
			"lengthMenu": [ [20, 50, -1], [20, 50, "全部"] ],
			"pagingType": "first_last_numbers",
			"language": {
			      "emptyTable": "没有数据显示",
			      "loadingRecords": "加载数据中...",
			      "info": "_START_-_END_(共_TOTAL_)条记录,第_PAGE_页/共_PAGES_页",
			      "paginate": {
			          "first": "首页",
			          "last": "尾页",
			          "previous": "上一页",
			          "next": "下一页"
			        }
			 },
			 renderer: {
			        "header": "jqueryui",
			        "pageButton": "bootstrap"
			 },
			buttons: [
				'pdfHtml5',
		        'colvis',
		        'excel',
		        'print'
		    ]
		});
	});
</script>
</html>