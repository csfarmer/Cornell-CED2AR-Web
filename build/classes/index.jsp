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
  <script src="http://code.jquery.com/jquery-latest.js"></script>
  <script type="text/javascript" src="scripts/searchAJAX.js"></script>
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
     <div id="simpleSearchDiv">
			Enter keywords below to do a broad search of the available codebook metadata.  
      <br>
			<span class="smallText">(Hint: For a more refined search, use the <a href="AdvancedSearch.jsp">Advanced Search</a> form.)</span>
	    <form id="simple_search" name="simple_search" action="" method="get">
	      <table id="simple_table">
	        <tr>
	          <td>
	            <input type="text" name="query" size="40" maxlength="40" />
	          </td>
	          <td>
	            <input type="submit" value="Search" />
	          </td>
	        </tr>
	      </table>
	    </form>
	  </div>
	    <div id="simpleSearchHeader" class="searchResultHeader"></div>
	    <div id="results"></div>
  </div> 

</div>
  <!-- JavaScript Files -->
</body>
</html>