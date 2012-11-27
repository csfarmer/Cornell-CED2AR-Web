package com.src.pkg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Servlet implementation class BrowseAlphabetVariable
 */
@WebServlet("/BrowseAlphabetVariable")
public class BrowseAlphabetVariable extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BrowseAlphabetVariable() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Redirects if not logged in
		HttpSession session=request.getSession(false);
		try{
			String loggedIn = session.getAttribute("loggedIn").toString();
		}
		catch(NullPointerException e){
			response.sendRedirect("index.jsp");
			return;
		}
        String HTMLString = "";		
		// Get the info for a specific variable
		if (request.getParameter("variableName") != null) {
			response.setContentType("text/html");
			
			URL handle = new URL("http://rschweb.ciserrsch.cornell.edu:8080/CED2AR_Query/search?return=variables&where=codebooktitle=" + request.getParameter("codebook") + ",variablename=" + request.getParameter("variableName"));
			URLConnection cn = handle.openConnection();
	        BufferedReader in = new BufferedReader(
	                                new InputStreamReader(
	                                cn.getInputStream()));
	        String inputLine;
	        String xmlString = "";
	        while ((inputLine = in.readLine()) != null) {
				xmlString += inputLine;
			}
			
			try {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				InputSource is = new InputSource();
				is.setCharacterStream(new StringReader(xmlString));

				Document doc = db.parse(is);

				NodeList variableNode = doc.getElementsByTagName("var");
				Element element = (Element) variableNode.item(0);
				
				NodeList documentNode = doc.getElementsByTagName("docDscr");
				Element documentInfo = (Element) documentNode.item(0);
				
				// Get data from the DOM to use for returning as HTML and  
				// output the HTML to return to the client
				HTMLString +=  "<table class=\"variableTable\">";
				HTMLString +=  "<tr>";
				HTMLString +=  "<td class=\"tdLeft\">Codebook:</td>";
				HTMLString +=  "<td class=\"tdRight\">" + request.getParameter("codebook") + "</td>";	
				HTMLString +=  "</tr>";
				HTMLString +=  "<tr>";
				HTMLString +=  "<td class=\"tdLeft\">Producer:</td>";
				try { 
					HTMLString +=  "<td class=\"tdRight\">" + documentInfo.getElementsByTagName("producer").item(0).getFirstChild().getNodeValue() + "</td>";	
				} catch (NullPointerException ne) {
					HTMLString += "<td class=\"tdRight\"></td>";
				}
				HTMLString +=  "</tr>";
				HTMLString +=  "<tr>";
				HTMLString +=  "<td class=\"tdLeft\">Variable Name:</td>";
				HTMLString +=  "<td class=\"tdRight\">" + request.getParameter("variableName") + "</td>";	
				HTMLString +=  "</tr>";
				HTMLString +=  "<tr>";
				HTMLString +=  "<td class=\"tdLeft\">Label:</td>";
				try {
					HTMLString +=  "<td class=\"tdRight\">" + element.getElementsByTagName("labl").item(0).getFirstChild().getNodeValue() + "</td>";	
				} catch (NullPointerException ne) { 
					HTMLString += "<td class=\"tdRight\"></td>";
				}
				HTMLString +=  "</tr>";
				try { 
					HTMLString +=  "<tr><td class=\"tdLeft\">Full Description:</td><td class=\"tdRight\">" + element.getElementsByTagName("txt").item(0).getFirstChild().getNodeValue() + "</td></tr>";
				} catch (NullPointerException ne) { }
				try { 
					HTMLString +=  "<tr><td class=\"tdLeft\">Concept:</td><td class=\"tdRight\">" + element.getElementsByTagName("concept").item(0).getFirstChild().getNodeValue() + "</td></tr>";
				} catch (NullPointerException ne) { }
				HTMLString +=  "<tr>";
				HTMLString +=  "<td class=\"tdLeft\">Var Type:</td>";
				try {
					HTMLString +=  "<td class=\"tdRight\">" + element.getElementsByTagName("varFormat").item(0).getAttributes().getNamedItem("type").getNodeValue() + "</td>";	
				} catch (NullPointerException ne) { 
					HTMLString += "<td class=\"tdRight\"></td>";
				}
				HTMLString +=  "</tr>";
				try {
					NodeList categoryNode = element.getElementsByTagName("catgry");
					
					HTMLString +=  "<tr><td class=\"tdLeft\">Values: </td>";	
					HTMLString +=  "<td class=\"tdRight\">";
					for (int i=0; i<categoryNode.getLength(); i++) {
						Element categories = (Element) categoryNode.item(i);
						HTMLString += categories.getElementsByTagName("catValu").item(0).getFirstChild().getNodeValue() + " ";
						try {
							HTMLString += categories.getElementsByTagName("labl").item(0).getFirstChild().getNodeValue() + "<br />";
						} catch (NullPointerException ne) {
							HTMLString += "<br />";
						}
					}
				    HTMLString += "</td></tr>";	
				} catch (NullPointerException ne) { 
					HTMLString += "<tr><td class=\"tdLeft\"><td class=\"tdRight\"></td></tr>";
				}
				HTMLString +=  "</table>";
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			in.close();			
		}
		
		request.setAttribute("HTMLString", HTMLString);
		request.setAttribute("variableName", request.getParameter("variableName"));
		request.setAttribute("backInfo", request.getParameter("backInfo"));
		request.getRequestDispatcher("/BrowseAlphabetVariable.jsp").forward(request,response); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
