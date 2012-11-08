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
	    <li id="tab-advanced" class="selected"><a href="AdvancedSearch.jsp">Advanced Search</a></li>
	    <li id="tab-browse"><a href="BrowseDataCodebook.jsp">Browse Data</a></li>
	  </ul>
    </div>
     
    <div id="content">
    <form id="advanced_search" name="advanced_search" method="post">
	      <table id="advanced_table">
	        <tr>
	          <td>
	            <select name="searchParam1">
	              <option value="varName">Variable Name</option>
	              <option value="label">Label</option>
	              <option value="description">Full Description</option>
	              <option value="concept">Concept</option>
	              <option value="varType">Variable Type</option>
	            </select>
	          </td>
	          <td>
	            <input type="text" name="advanced_search1" size="30" maxlength="30" />
	          </td>
	          <td>
	            <select name="searchBool1">
	              <option value="and">And</option>
	              <option value="or">Or</option>
	              <option value="and_not">And Not</option>
	            </select><br />
	          </td>
	        </tr>
	        <tr>
	          <td>
	            <select name="searchParam2">
	              <option value="varName">Variable Name</option>
	              <option value="label">Label</option>
	              <option value="description">Full Description</option>
	              <option value="concept">Concept</option>
	              <option value="varType">Variable Type</option>
	            </select>
	          </td>
	          <td>
	            <input type="text" name="advanced_search2" size="30" maxlength="30" />
	          </td>
	          <td>
	            <select name="searchBool2">
	              <option value="and">And</option>
	              <option value="or">Or</option>
	              <option value="and_not">And Not</option>
	            </select><br />
	          </td>
	        </tr>
	        <tr>
	          <td>
	            <select name="searchParam3">
	              <option value="varName">Variable Name</option>
	              <option value="label">Label</option>
	              <option value="description">Full Description</option>
	              <option value="concept">Concept</option>
	              <option value="varType">Variable Type</option>
	            </select>
	          </td>
	          <td>
	            <input type="text" id="advanced_search3" name="advanced_search3" size="30" maxlength="30" />
	          </td>
	          <td>
	            <select name="searchBool3">
	              <option value="and">And</option>
	              <option value="or">Or</option>
	              <option value="and_not">And Not</option>
	            </select><br />
	          </td>
	        </tr>
	        <tr>
	          <td colspan="3" class="center">
	            <input type="submit" value="Search" />
	          </td>
	        </tr>
	      </table>
	    </form>
	    </div>
    </div>
       
  </div>
  <!-- JavaScript Files -->
</body>
</html>