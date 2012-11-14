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
 * Servlet implementation class ViewBrowseCodebook
 */
@WebServlet("/ViewBrowseCodebook")
public class ViewBrowseCodebook extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewBrowseCodebook() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		// Get the list of all variables for browse by the selected codebook
		if (request.getParameter("codebook") != null) {
			response.setContentType("text/html");
			URL handle = new URL("http://localhost:8000/api/v1/codebooks/" + request.getParameter("codebook") + "/variables.xml");
			URLConnection cn = handle.openConnection();
	        BufferedReader in = new BufferedReader(
	                                new InputStreamReader(
	                                cn.getInputStream()));
	        String inputLine;
	        String xmlString = "";
	        while ((inputLine = in.readLine()) != null) {
				xmlString += inputLine;
			}
	        out.print(xmlString);
	         
			try {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				InputSource is = new InputSource();
				is.setCharacterStream(new StringReader(xmlString));

				Document doc = db.parse(is);

				NodeList variableNames = doc.getElementsByTagName("var");
				//out.print(doc.getElementsByTagName("var").getLength());
				out.print("<table class=\"codebookTable\">");
				for (int i = 0; i < variableNames.getLength(); i++) {
					out.print("<tr>");
					Element element = (Element) variableNames.item(i);
					out.print("<td class=\"tdLeft\"><a href=\"BrowseCodebookVariable?variableName=" + element.getAttributes().getNamedItem("name").getNodeValue() +  "&codebook=" + request.getParameter("codebook") + "\"id=\"" + element.getAttributes().getNamedItem("name").getNodeValue() + "\" class=\"variableName\">" + element.getAttributes().getNamedItem("name").getNodeValue() + "</a></td>");
					try { NodeList label = element.getElementsByTagName("labl");
						  out.print("<td class=\"tdRight\">" + label.item(0).getFirstChild().getNodeValue() + "</td>"); 
						  out.print("</tr>"); }
					catch (NullPointerException ne){ out.print("<td class=\"tdRight\"></td>");
													 out.print("</tr>");}
				}
				
				out.print("</table>");
			} catch (Exception e) {
				e.printStackTrace();
			}
			in.close();			
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
