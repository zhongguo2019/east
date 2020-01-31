<%@ page import="com.krmsoft.mycas.util.HttpUtil"%>
<%@ page import="java.util.*"%>
<%@ page import="com.krmsoft.mycas.util.FileUtil"%>
<%
String jessionid =(String)request.getSession(false).getAttribute("jsesstionId");
String userName =(String)request.getSession(false).getAttribute("userName");

//FileUtil fileUtil=new  FileUtil();
//List list=fileUtil.readLine("D:/workspace/platsso/WebRoot/mycas/conf/logoutList.txt");//解析文件
//HttpUtil httpUtil=new HttpUtil();
//	for (int k = 0; k < list.size(); k++) {
//		System.out.println(list.get(k).toString());
//		httpUtil.get(list.get(k).toString()); //发送URL请求
//	}
boolean login= false;
if(jessionid!=null&&!jessionid.equals("")&&userName!=null&&!userName.equals(""))
{
	request.getSession().removeAttribute("jsesstionId");
}
login = false;
response.sendRedirect("../login.jsp");
%>