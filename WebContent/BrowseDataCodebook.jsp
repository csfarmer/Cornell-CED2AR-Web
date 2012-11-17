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
      <a href="BrowseDataCodebook.jsp" class="selected-browse">Browse by Codebook</a> | <a href="BrowseDataAlphabet.jsp">Browse Alphabetically</a>
      <div class="separate"></div>
          <form id="chooseCodebook" name="chooseCodebook" action="" method="get">
	      	  <select id="chooseCodebookSelect" name="codebook">
	            <option value="default">Choose One Codebook</option>
						<%
							// Get the list of all codebooks and put them in a dropdown menu
							URL handle = new URL("http://rschweb.ciserrsch.cornell.edu:8080/CED2AR_Query/search?return=codebooks");
							URLConnection cn = handle.openConnection();
							BufferedReader in = new BufferedReader(new InputStreamReader(
									cn.getInputStream()));
							String xmlString = "\n";
							String inputLine;
							// Put the xml document into a string so that it can be parsed easily
							while ((inputLine = in.readLine()) != null) {
								xmlString += inputLine;
							}
							// Parse the xml into a DOM structure and fill in the dropdown list	         
							try {
								DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
								DocumentBuilder db = dbf.newDocumentBuilder();
								InputSource is = new InputSource();
								is.setCharacterStream(new StringReader(xmlString));

								Document doc = db.parse(is);

								NodeList titles = doc.getElementsByTagName("titl");
								for (int i = 0; i < titles.getLength(); i++) {
									%>
									<option
										value="<%=titles.item(i).getFirstChild().getNodeValue()%>">
										<%=titles.item(i).getFirstChild().getNodeValue()%></option>
									<%
								}

							} catch (Exception e) {
								e.printStackTrace();
							}
							in.close();
						%>
				</select>
	        </form>
	        
	        <div id="browseCodebookHeader"></div>
	        
	        <div id="browseCodebookContent"></div>
    </div>
       
  </div>
  <!-- JavaScript Files -->
  <script type="text/javascript" src="scripts/BrowseCodebookFunctions.js"></script>
</body>
</html>