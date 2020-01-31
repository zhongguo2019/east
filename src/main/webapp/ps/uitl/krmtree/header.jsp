<c:choose>
<c:when test="${disabledNav}"><br/></c:when>
<c:otherwise>
<table border="0" width="99%" align="center" cellSpacing=0 cellPadding=0>
	<tr valign="bottom">
		<td height="20" align="left"><c:out value="${currentUser.user.userName}"/>
			<c:out value="${navigationInfo}"/>
		</td>
		<td height="20" align="right"><a href="#"><fmt:message key="navigation.password"/></a>&nbsp;</td>
	</tr>
</table>
<hr>
</c:otherwise>
</c:choose>