package com.src.pkg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
 * Servlet implementation class SearchServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String APIString = "http://rschweb.ciserrsch.cornell.edu:8080/CED2AR_Query/search?return=variables&where=";
		String[] query  = request.getParameter("query").split(" ");
		for(int i = 0; i < query.length; i++){
			if (i != query.length-1) {
				APIString += "allfields=*" + query[i] + "*,";
			} else {
				APIString += "allfields=*" + query[i] + "*";
			}
		}
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		URL handle = new URL(APIString);
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

			NodeList variableNames = doc.getElementsByTagName("var");
			NodeList codebookNames = doc.getElementsByTagName("titl");
			
		    // Header table HTML
	        out.print("You are searching for \"" + request.getParameter("query") + "\", " + variableNames.getLength() + " results returned.");
	        out.print("<span class=\"alignRight\"><span id=\"simpleSearchBack\">&lt;&lt;Search again.</span></span>");
	        out.print("<hr />");
	        out.print("<table class=\"simpleSearchTable\"><tr><td class=\"tdLeft\">Variable</td><td class=\"tdMiddle\">Label</td><td class=\"tdRight\">Codebook</td></tr></table>");
	        out.print("<hr />");
	        
			// Create a list of variables and info to be displayed, with the variable clickable to see more info
			out.print("<table class=\"simpleSearchTable\">");
			for (int i = 0; i < variableNames.getLength(); i++) {
				out.print("<tr>");
				Element element = (Element) variableNames.item(i);
				
				// Calculate the name of the codebook by using the location of the codebook tag and the variable node
				String codebookTitle = "";
				Element codebookTagLocation = (Element) element.getParentNode().getParentNode();

				// Loop through all codebook titles and find the one between the codebook tag and variable name
				for (int j=0; j < codebookNames.getLength(); j++) {
					Element codebookNode = (Element) codebookNames.item(j);
					if (codebookTagLocation.compareDocumentPosition(codebookNode) == 20)
						codebookTitle = codebookNode.getFirstChild().getNodeValue();
				}

				
				out.print("<td class=\"tdLeft\"><a href=\"Login?redirect=SimpleSearchViewVariable?variableName=" + element.getAttributes().getNamedItem("name").getNodeValue() + "&codebook=" + codebookTitle + "\" class=\"variableName\">" + element.getAttributes().getNamedItem("name").getNodeValue() + "</a></td>");
				try { NodeList label = element.getElementsByTagName("labl");
					  out.print("<td class=\"tdMiddle\">" + label.item(0).getFirstChild().getNodeValue() + "</td>"); 
					  out.print("<td class=\"tdRight\">" + codebookTitle + "</td>"); 
					  out.print("</tr>"); }
				catch (NullPointerException ne){ out.print("<td class=\"tdMiddle\"></td>");
												 out.print("<td class=\"tdRight\">" + codebookTitle + "</td>"); 
												 out.print("</tr>");}
			}
			out.print("</table>");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	

}
