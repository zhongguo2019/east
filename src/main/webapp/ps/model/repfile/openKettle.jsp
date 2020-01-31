<!-- /ps/model/repfile/openKettle.j -->
<%@ include file="/ps/framework/common/taglibs.jsp"%>
<%@ include file="/ps/framework/common/glbVariable.jsp"%>
<%@ include file="/ps/framework/common/easyUItag.jsp"%>


    <head>
        <title><fmt:message key="webapp.prefix"/></title>
        
			<script language="javascript" type="text/javascript" src="<c:url value='ps/uitl/My97DatePicker/WdatePicker.js'/>"></script>
		<script src="<c:url value='/ps/framework/common/jqueryTab/jquery-1.2.4b.js'/>" type="text/javascript"></script>
		<link rel="stylesheet" type="text/css" media="all" href="<c:url value='${displaytag12Path}/displaytag.css'/>" />
		<link rel="stylesheet" type="text/css" href="<c:url value='/ps/style/comm.css'/>"/>
		<script type="text/javascript" src="<c:url value='/ps/scripts/global.js'/>"></script>
    </head>					


<script language=JavaScript>

	function updateKtr(){
		window.location.href="repFileAction.do?method=openattach";
	}
	

	function queryktr(){
		document.ktrForm1.action="repFileAction.do?method=queryktr&ktrnamed="+$("#ktrnamed").val()+"&ktrremarked="+$("#ktrremarked").val()+"&ktrtimed="+$("#ktrtimed").val();
		document.ktrForm1.submit();
	}
	
	function fanhui(){
		document.ktrForm1.action="repFileAction.do?method=entercreateFileSerch&levelFlag=2";
		document.ktrForm1.submit();
	}
	
	function delktr(pkid){
		if(confirm("<fmt:message key="dicListPage.cfmdel"/>")){
			param={"pkid":pkid};
			$.post("repFileAction.do?method=delktr",param,function(data){
					var jsondata = eval(data);
				  var flag = jsondata[0].flag;
				  if(flag==4){
						alert("<fmt:message key="kettle.fileData.del"/>");
					}
				  if(flag==5){
					  var message = jsondata[0].message;
						alert(message);
					}
				window.location.href="repFileAction.do?method=openKettle";
			});
			
		}
	}
	
	function activdKtr(pkid,ktrPath){
		if(confirm("<fmt:message key="view.kettle.messa"/>")){
			param={"pkid":pkid,"ktrPath":ktrPath};
			$.post("repFileAction.do?method=activdKtr",param,function(data){
					var jsondata = eval(data);
				  var flag = jsondata[0].flag;
				  if(flag==2){
					  alert("<fmt:message key="kettle.fileData.actived"/>");
					}
				  if(flag==3){
					  var message = jsondata[0].message;
						alert(message);
					}
				window.location.href="repFileAction.do?method=openKettle";
			});
			
		}
		
	}
</script>
<!--  <script type="text/javascript">
	        window.parent.document.getElementById("ppppp").value= "<fmt:message key="view.kettle.list"/>";  
</script> -->
<body >

		<table border="0" width="100%"cellSpacing=0 cellPadding=0>
		<input id="flag" value="<c:out value='${flag}'/>" type="hidden"/>
		<input id="message" value="<c:out value='${message}'/>" type="hidden"/>
		<input type="hidden" name="org.apache.struts.taglib.html.TOKEN" value='<%=session.getAttribute("org.apache.struts.action.TOKEN")%>' />
			<tr>
				<td  align="left">
					<form name="ktrForm" action="repFileAction.do?method=openKettle" method="post" style="margin:0px">
					<div class="navbar2">
						<table>
							<tr>
								<td>
									<fmt:message key="kettle.name"/>
								</td>
								<td>
									<input type="text" value="" id="ktrnamed"/>
								</td>
								<td>
									<fmt:message key="kettle.remark1"/>
								</td>
								<td>
									<input type="text" value="" id="ktrremarked"/>
								</td>
								<td>
									<fmt:message key="view.xyrq"/>
								</td>
								<td>
									 <input id="ktrtimed" name="ktrtimed" type="text" value="" style="width:100;" readonly="true" onClick="WdatePicker();">
								</td>
								<td>
									

								<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'"text-decoration:none;" name="ss" onclick="queryktr();" ><fmt:message key='button.search'/></a>

								</td>
							</tr>
						</table>
					</div>			
						<div class="navbar3">

			<a id = "btnMovePrevious" name = "btnMovePrevious" href="#" data-options="iconCls:'icon-back'" class="easyui-linkbutton"  text-decoration:none;"  onclick = "fanhui();"><fmt:message key="button.back"/></a>
			<a id = "btnMoveNext" name = "btnMoveNext" href="#" data-options="iconCls:'icon-upload'" class="easyui-linkbutton"    onclick = "updateKtr();"><fmt:message key="gdextdata.uploadFile"/></a>&nbsp;&nbsp;

						</div>
					</form>
				</td>
			</tr>
       
			<form name="ktrForm1" action="#" method="post" style="margin:0px">
			<tr>
				<td>
				    <div align="center" style=" width:98%">
						<display:table name="kdlist" cellspacing="0" cellpadding="0"  
							    requestURI="" id="kdlist" width="100%" 
							    pagesize="30" styleClass="list reportFormatList" >

							    
							    <display:column sort="true" 
							    	headerClass="sortable"
							    	titleKey="repfile.download.filename">
							    	<c:out value="${kdlist.ktrName}"/>
							    </display:column>
							    
							    <display:column nowrap="no" titleKey="kettle.remark" maxLength="20">
							    		<c:out value="${kdlist.ktrRemark}"/>
							      </display:column>
							    
							     <display:column nowrap="no" titleKey="kettle.user">
							    		<c:out value="${kdlist.userName}"/>
							      </display:column>
							      
							      <display:column nowrap="no" titleKey="kettle.time">
							    		<c:out value="${kdlist.ktrTime}"/>_<c:out value='${kdlist.ktrPath}'/>
							      </display:column>
							      
							      <display:column nowrap="no" titleKey="log.list.status">
							      	<c:if test="${kdlist.attribute1==1}"><fmt:message key="kettle.fileData.successd" /></c:if>
							    	<c:if test="${kdlist.attribute1==2}"><fmt:message key="kettle.fileData.actived" /></c:if>
							    	<c:if test="${kdlist.attribute1==3}"><a title="<c:out value="${kdlist.attribute4}"/>"><fmt:message key="kettle.fileData.failed" /></a></c:if>	
							      </display:column>
							      
							      <display:column nowrap="no" titleKey="kettle.lasttime">
							    		<c:out value="${kdlist.attribute2}"/>
							      </display:column>
							      
							      <display:column nowrap="no" titleKey="kettle.lastuser">
							    		<c:out value="${kdlist.attribute3}"/>
							      </display:column>
							    
							
							  <display:column nowrap="no" titleKey="common.list.operate">
							      <a href="#" style="text-decoration: none;" onclick="activdKtr('<c:out value='${kdlist.pkid}'/>','<c:out value='${kdlist.ktrPath}'/>')" ><fmt:message key="loanDeviation.btn.file"/></a>
							      &nbsp;&nbsp;&nbsp;&nbsp;
							      <a href="#" style="text-decoration: none;" onclick="delktr('<c:out value='${kdlist.pkid}'/>')" ><fmt:message key="button.delete"/></a>
							  </display:column>   
							</display:table>
						
				    </div>
				</td>
			</tr>
			</form>
		</table>
</body>
</html>