<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.net.URLConnection" %>
<%@ page import="javax.xml.parsers.*" %>
<%@ page import="org.xml.sax.InputSource" %>
<%@ page import="org.w3c.dom.*" %>
<%@ page import="java.io.StringReader" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <link rel="stylesheet" type="text/css" href="styles/styles.css" />
  <!--  <script type="text/javascript" src="scripts/jquery.js"></script>-->
  <script src="http://code.jquery.com/jquery-latest.js"></script>
<title>CED2AR</title>
</head>
<body>
<%@include file='loginBar.jsp'%>
  <div id="header">
    <h1 class="center">CED2AR</h1>
  </div>
  
  <div id="main">
  
  	<div id="navDiv">
	  <ul id="navList">
	    <li id="tab-simple"><a href="index.jsp">Simple Search</a></li>
	    <li id="tab-advanced"><a href="AdvancedSearch.jsp">Advanced Search</a></li>
	    <li id="tab-browse"><a href="BrowseDataCodebook.jsp" class="selected">Browse Data</a></li>
	  </ul>
    </div>
     
    <div id="content">
      <p><a href="javascript:history.back()">&lt;&lt; Back To List</a><br /></p>
		
	        <div id="browseAlphabetHeader">
	        <hr /> 
    			<h2><%= request.getAttribute("variableName") %></h2> 
    		<hr />
	        </div>
	        
	        <div id="browseAlphabetContent">
	        <p>
	         <%= request.getAttribute("HTMLString") %>
	        </p>
	        </div>
    </div>
       
  </div>
  <!-- JavaScript Files -->
</body>
</html>