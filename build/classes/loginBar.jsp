<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	String loggedIn =  (String)session.getAttribute("loggedIn");
	if(null == loggedIn){ 
		String html = "<div id='topLoginBar'><p>You are not logged in. <a href='Login'>Login or Register</a></p></div>";
		out.println(html);
	}else{  
		String html = "<div id='topLoginBar'><p>You are logged in as "+loggedIn+"</p></div>";
		out.println(html);  
	}  

%>