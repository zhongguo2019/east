<%@ page import="java.util.*,net.sf.json.JSONArray,com.krmsoft.platsso.project.vo.PlatssoSystem,com.krmsoft.mycas.bean.OSbean"%>
<%
String jessionid =(String)request.getSession(false).getAttribute("jsesstionId");
String passWord =(String)request.getSession(false).getAttribute("passWord");
System.out.println(request.getAttribute("listSystem"));
List<OSbean> oslist = new ArrayList();
	 List<PlatssoSystem> list = (List<PlatssoSystem>)request.getAttribute("listSystem");
	 for (PlatssoSystem platssoSystem : list) {
		OSbean os = new OSbean();
		platssoSystem.getName();
		platssoSystem.getIp();
		platssoSystem.getPort();
		platssoSystem.getPath();
		platssoSystem.getSystem();
		
		os.setOscode(platssoSystem.getSystem());
		os.setOsname(platssoSystem.getName());
		os.setContext(platssoSystem.getSystem());  
		os.setHost(platssoSystem.getIp());
		os.setPort(platssoSystem.getPort());
		os.setLoginurl("login.do?metod=login");
		os.setParam_name("username");
		os.setParam_pass("password");
		os.setParam_logdate("logdate");
		os.setIcon("");
		os.setIsuser(true);
		os.setPlat(true);
		oslist.add(os);
		
		//System.out.println("os info:"+platssoSystem.getName()+"	"+platssoSystem.getIp());
		
	 } 
	 JSONArray jc = JSONArray.fromObject(oslist);
	 response.getWriter().write(jc.toString());
	 %>
	


