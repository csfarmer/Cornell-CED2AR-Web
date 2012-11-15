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
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

/**
 * Servlet implementation class SimpleSearchViewVariable
 */

@WebServlet("/SimpleSearchViewVariable")
public class SimpleSearchViewVariable extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SimpleSearchViewVariable() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        String HTMLString = "";		
		// Get the info for a specific variable
		if (request.getParameter("variableName") != null) {
			response.setContentType("text/html");
			
			URL handle = new URL("http://localhost:8000/api/v1/codebooks/" + request.getParameter("codebook") + "/variables/" + request.getParameter("variableName") + ".xml");
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
				// Get data from the DOM to use for returning as HTML and  
				// output the HTML to return to the client
				HTMLString +=  "<table class=\"variableTable\">";
				HTMLString +=  "<tr>";
				HTMLString +=  "<td class=\"tdLeft\">Codebook:</td>";
				HTMLString +=  "<td class=\"tdRight\">" + request.getParameter("codebook") + "</td>";	
				HTMLString +=  "</tr>";
				HTMLString +=  "<tr>";
				HTMLString +=  "<td class=\"tdLeft\">Variable Name:</td>";
				HTMLString +=  "<td class=\"tdRight\">" + request.getParameter("variableName") + "</td>";	
				HTMLString +=  "</tr>";
				HTMLString +=  "<tr>";
				HTMLString +=  "<td class=\"tdLeft\">Label:</td>";
				if (element.getElementsByTagName("labl").item(0).getFirstChild().getNodeValue() != null) {
					HTMLString +=  "<td class=\"tdRight\">" + element.getElementsByTagName("labl").item(0).getFirstChild().getNodeValue() + "</td>";	
				}
				HTMLString +=  "</tr>";
				HTMLString +=  "<tr>";
				HTMLString +=  "<td class=\"tdLeft\">Var Type:</td>";
				if (element.getElementsByTagName("varFormat").item(0).getAttributes().getNamedItem("type").getNodeValue() != null) {
					HTMLString +=  "<td class=\"tdRight\">" + element.getElementsByTagName("varFormat").item(0).getAttributes().getNamedItem("type").getNodeValue() + "</td>";	
				}
				HTMLString +=  "</tr>";
				HTMLString +=  "</table>";
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			in.close();			
		}
		
		request.setAttribute("HTMLString", HTMLString);
		request.setAttribute("variableName", request.getParameter("variableName"));
		request.getRequestDispatcher("/SimpleSearchVariable.jsp").forward(request,response); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}

