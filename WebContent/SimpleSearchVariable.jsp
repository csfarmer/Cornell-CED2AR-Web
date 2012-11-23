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
<%@include file='header.jsp'%>
  
  <div id="main">
  
  	<div id="navDiv">
  	  <ul id="navList">
  	    <li id="tab-simple" class="selected"><a href="index.jsp">Simple Search</a></li>
  	    <li id="tab-advanced"><a href="AdvancedSearch.jsp">Advanced Search</a></li>
  	    <li id="tab-browse"><a href="BrowseDataCodebook.jsp">Browse Data</a></li>
  	  </ul>
      <div class="clear"></div>
    </div>
     
    <div id="content">
      <p><a href="index.jsp#<%= request.getAttribute("backInfo") %>">&lt;&lt; Back To List</a><br /></p>
		
	        <div id="browseCodebookHeader">
	        <hr /> 
    			<h2><%= request.getAttribute("variableName") %></h2> 
    		<hr />
	        </div>
	        
	        <div id="browseCodebookContent">
	        <p>
	         <%= request.getAttribute("HTMLString") %>
	        </p>
	        </div>
    </div>
       
  </div>
  <!-- JavaScript Files -->
  <script type="text/javascript" src="scripts/BrowseCodebookFunctions.js"></script>
</body>
</html>