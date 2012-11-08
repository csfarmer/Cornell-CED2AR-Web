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
  <div id="header">
    <h1 class="center">CED2AR</h1>
  </div>
  
  <div id="main">
  
  	<div id="navDiv">
	  <ul id="navList">
	    <li id="tab-simple"><a href="index.jsp">Simple Search</a></li>
	    <li id="tab-advanced"><a href="AdvancedSearch.jsp">Advanced Search</a></li>
	    <li id="tab-browse" class="selected"><a href="BrowseDataCodebook.jsp">Browse Data</a></li>
	  </ul>
    </div>
     
    <div id="content">
      <a href="BrowseDataCodebook.jsp">Browse by Codebook</a> | <a href="BrowseDataAlphabet.jsp" class="selected-browse">Browse Alphabetically</a><br />
      <div class="separate"></div>
    	<% char[] ch = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	       for (int i=0; i<ch.length; i++) { %>
	         <a href="BrowseServlet?browseByAlphabet=<%= ch[i] %>"><span class="space"><%= ch[i] %></span></a> | 
	      <% } %>
    </div>
       
  </div>
  <!-- JavaScript Files -->
</body>
</html>