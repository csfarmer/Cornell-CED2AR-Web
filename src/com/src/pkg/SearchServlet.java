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
		String xquery = "<xml>{ for $cedar in  collection('CED2AR') return ";
		String[] query  = request.getParameter("query").split(" ");
		for(int i = 0; i < query.length; i++){
			if(i == 0){
				xquery += "$cedar/codeBook/dataDscr/var[contains(labl, '"+query[i]+"')";
			}
			else{
				xquery += " and contains(labl, '"+query[i]+"')";
			}

		
		}
		
		xquery += "]}</xml>";
		String xml = Functions.getXML(xquery);
		PrintWriter out = response.getWriter();
		
		//out.write(xquery);
		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xml));

			Document doc = db.parse(is);

			NodeList variableNames = doc.getElementsByTagName("var");
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
		
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	

}
