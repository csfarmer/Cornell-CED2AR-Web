<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register for CEDDAR</title>
</head>
<script src="scripts/checkReg.js"></script>
<script src="http://code.jquery.com/jquery-latest.js"></script>
<body>
	<div id="errors"></div>
	<form action="Test" method="post">
		First Name<input type="text" name="fname"/><br />
		Last Name<input type="text" name="lname"/><br />
		Email<input type="text" name="emailAddress"/><br />
		Organization<input type="text" name="org"/><br />
		Department Field<input type="text" name="field"/><br />
		Password<input type="password" name="field"/><br /><!-- Implement type as you go error checking? -->
		Confirm Password<input type="password" name="field"/><br /><!-- Implement matching password error checking? -->
		<input type="submit" value="Register"/>
	</form>
</body>
</html>