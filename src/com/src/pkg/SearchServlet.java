package com.src.pkg;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
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
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

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
		String backInfo = "";
		String pageNo = request.getParameter("page");
		int page = pageNo == null ? 0 : Integer.parseInt(pageNo);

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

			String tXML = XmlUtil.getXmlPage(xmlString, page, context.getRealPath("/xsl/page.xsl"));
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(tXML));
			Document doc = db.parse(is);

			NodeList variableNames = doc.getElementsByTagName("var");
			int count = Integer.parseInt(XmlUtil.getNodeCount("var", xmlString));

		    // Header table HTML
	        out.print("<span class=\"searchResultHeader\">You searched for \"" + request.getParameter("query") + "\", " + String.format("%s", count) + " results returned.</span>");
	        out.print("<span class=\"alignRight\"><span id=\"simpleSearchBack\">&lt;&lt;Search again.</span></span>");
	        out.print("<table class=\"simpleSearchTable\">");
            out.print("<tr><th class=\"tdLeft\">Variable</th><th class=\"tdMiddle\">Label</th><th class=\"tdRight\">Codebook</th></tr>");

			// Create a list of variables and info to be displayed, with the variable clickable to see more info
			for (int i = 0; i < variableNames.getLength(); i++) {
				out.print("<tr>");
				Element element = (Element) variableNames.item(i);

				String codebookTitle = element.getAttribute("codeBook");

				out.print("<td class=\"tdLeft\"><a href=\"Login?redirect=SimpleSearchViewVariable?variableName=" + element.getAttributes().getNamedItem("name").getNodeValue() + "&backInfo=" + request.getParameter("query") + "&codebook=" + codebookTitle + "\" class=\"variableName\">" + element.getAttributes().getNamedItem("name").getNodeValue() + "</a></td>");

				try { NodeList label = element.getElementsByTagName("labl");
					  out.print("<td class=\"tdMiddle\">" + label.item(0).getFirstChild().getNodeValue() + "</td>"); 
					  out.print("<td class=\"tdRight\">" + codebookTitle + "</td>"); 
					  out.print("</tr>"); }
				catch (NullPointerException ne){ out.print("<td class=\"tdMiddle\"></td>");
												 out.print("<td class=\"tdRight\">" + codebookTitle + "</td>"); 
												 out.print("</tr>");}
			}

			out.print("</table>");

			if (count > 20) {
				String prevDisabled = (page == 0) ? "" : "\"<< Last 20\"";				
				String nextDisabled = (((page + 1) * 20) > count) ? "" : "\"Next 20 >>\"";

				out.print(String
						.format("<div class=\"pageContainer\">"
								+ "<input type=\"button\" class=\"pageButton\" name=\"prev\" onclick=\"queryRepository('%s')\" value=" + prevDisabled + "></input>"
								+ "<span class=\"page\">Results: %s - %s</span>"
								+ "<input type=\"button\" class=\"pageButton\" name=\"prev\" onclick=\"queryRepository('%s')\" value=" + nextDisabled + "></input></div>",
								page - 1, (page * 20) + 1, (page + 1) * 20, page + 1));
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}

		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}