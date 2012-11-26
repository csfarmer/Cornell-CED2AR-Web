package com.src.pkg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletContext;
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
 * Servlet implementation class BrowseServlet
 */
@WebServlet("/BrowseServlet")
public class BrowseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BrowseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();

		// Get the variable info for browse by the selected codebook
		if (request.getParameter("codebook") != null) {
			response.setContentType("text/html");
			URL handle = new URL("http://rschweb.ciserrsch.cornell.edu:8080/CED2AR_Query/codebooks/" + request.getParameter("codebook") + "/variables.xml");
			URLConnection cn = handle.openConnection();
	        BufferedReader in = new BufferedReader(
	                                new InputStreamReader(
	                                cn.getInputStream()));
	        String inputLine;
	        
	        StringBuilder sb = new StringBuilder();
	        String xmlString = "";
	        while ((inputLine = in.readLine()) != null) {
				sb.append(inputLine);
			}
	        
	        xmlString = sb.toString();
	         
			try {
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				ServletContext context = this.getServletContext();
				
				String tXML = XmlUtil.getXmlPage(xmlString, 0, context.getRealPath("/xsl/page.xsl"));
				InputSource is = new InputSource();
				is.setCharacterStream(new StringReader(tXML));
				
				Document doc = db.parse(is);

				NodeList variableNames = doc.getElementsByTagName("var");
				int count = Integer.parseInt(XmlUtil.getNodeCount("var", xmlString));
				out.print("<table class=\"codebookTable\">");
				for (int i = 0; i < variableNames.getLength(); i++) {
					out.print("<tr>");
					Element element = (Element) variableNames.item(i);
					out.print("<td class=\"tdLeft\">" + element.getAttributes().getNamedItem("name").getNodeValue() + "</td>");
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
			out.close();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
