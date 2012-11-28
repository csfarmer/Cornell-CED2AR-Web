<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	String loggedIn =  (String)session.getAttribute("loggedIn");
	if(null == loggedIn){ 
		String html = "<div id='topLoginBar'><a href='Login'>Login or Register</a></div>";
		out.println(html);
	}else{  
		String html = "<div id='topLoginBar'>You are logged in as "+loggedIn+"</div>";
		out.println(html);  
	}  

%>