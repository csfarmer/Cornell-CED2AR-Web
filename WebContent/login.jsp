<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="http://code.jquery.com/jquery-latest.js"></script>
<title>Please Login to Continue</title>
<%
	
	//Checks if already logged in. Redirects if so.
	String redirect = "index.jsp";
	String loggedIn =  (String)session.getAttribute("loggedIn");
	if(loggedIn!=null)
	{
		response.sendRedirect("index.jsp");
	}



%>
</head>
<body>
	<form action="LoginServlet" method="post">
		<input type="hidden" name="redirect" value="<%out.print(redirect); %>"><!-- Page that login script should forward to after successful login -->
    	<table id="passwordTable">
        	<tr>
            	<td>Email</td><td> <input name="email" type="text" method="post"/> </td> 
            </tr>
            <tr>
            	<td>Password </td><td> <input name="password" size=15 type="password" /> </td> 
            </tr>
            <tr>
    			<td id="loginError"></td>
    		</tr>
        </table>
        <input type="submit" value="Login" />
</form>

</body>
</html>