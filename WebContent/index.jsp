<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
  <!-- <base href="${pageContext.request.contextPath}"> -->
  <link rel="stylesheet" type="text/css" href="styles/styles.css" />
  <script type="text/javascript" src="scripts/jquery.js"></script>
<title>CED2AR</title>
</head>
<body>
  <div id="header">
    <h1 class="center">CED2AR</h1>
  </div>
  <div id="main">
	<div id="tabs-content">
	
	  <!-- Content div for the simple search option -->
	  <div id="tab-content-simple">
	    <form id="simple_search" name="simple_search" method="post">
	      <table id="simple_table">
	        <tr>
	          <td>
	            <input type="text" name="simple_search" size="40" maxlength="40" />
	          </td>
	          <td>
	            <input type="submit" value="Search" />
	          </td>
	        </tr>
	      </table>
	    </form>
	  </div>
	  
	  <!-- Content div for the advanced search option -->
	  <div id="tab-content-advanced">
	    <div id="advanced_form">
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
	  
	  <!-- Content div for browsing the data without searching -->
	  <div id="tab-content-browse">
	    <div id="browse-content">   
	      <!--  Content div for browsing by codebook -->
	      <div id="tab-content-codebook">
	        <form id="chooseCodebook" name="chooseCodebook">
	          <select name="codebook">
	            <option value="default">Choose One Codebook</option>
	          </select>
	        </form>
	      </div>
	      <!--  Content div for browsing alphabetically -->
	      <div id="tab-content-alphabet">
	        <p>
	          <% char[] ch = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	            for (int i=0; i<ch.length; i++) { %>
	              <a href="#<%= ch[i] %>"><span class="space"><%= ch[i] %></span></a> | 
	          <% } %>
	        </p>
	      </div>
	    </div>
	  </div>
	</div>
  </div>
  
  <!-- JavaScript Files -->
  <script type="text/javascript" src="scripts/indexTabs.js"></script>
  <script type="text/javascript" src="scripts/advancedSearch.js"></script>
  <script type="text/javascript" src="scripts/browseTabs.js"></script>
</body>
</html>