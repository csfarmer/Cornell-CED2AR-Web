<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>CED2AR Demo Interface</title>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link type="text/css" rel="stylesheet" href="styles/styles.css" />
    <script src="http://code.jquery.com/jquery-latest.js"></script>
</head>
<body>
	<%@include file='loginBar.jsp'%>
	<p>Sandbox Page for Testing Stuff</p>
	<%
		java.text.DateFormat df = new java.text.SimpleDateFormat("MM/dd/yyyy"); 
		
	%>
	<p>Current Date: <%= df.format(new java.util.Date()) %> </p>
	<form action="Test">
	<input type="submit" value="Search"/>
	<input type="text" name="query"/>
	</form>
</body>
</html>