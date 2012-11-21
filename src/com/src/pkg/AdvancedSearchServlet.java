package com.src.pkg;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
 * Servlet implementation class AdvancedSearchServlet
 */
@WebServlet("/AdvancedSearchServlet")
public class AdvancedSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdvancedSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		out.write("<h1>Nothing to see here...</h1>");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		String baseURL = "http://rschweb.ciserrsch.cornell.edu:8080/CED2AR_Query/search?return=variables&where=";
		String apiString = "";
		
		//Gets list of fields that are 
		List<String> queries = new ArrayList<String>();
		try{
			int i = 1;
			boolean not = false;//Used later to determine if previous statement contains and_not
			//Gets list of all entered fields, up to 50
			while(i <= 50){
				String temp = request.getParameter("advanced_search"+i);	
				if(temp == null){
					throw new NullPointerException();
				}
				String p = request.getParameter("searchParam"+i);
				String b = request.getParameter("searchBool"+i);
				//Filters out non-alphanumeric characters
				temp = temp.replaceAll("[^A-Za-z0-9 ]", "").trim();
				String[] tempArray = temp.split(" ");
				//Filters out duplicates for same field. So if user enters "label = war twice", will filter
				//Will not filter if user enters "Label = War" and "Variable Name = War"
				for(String t : tempArray){
					if(!t.isEmpty()){
						String cTemp = p + "_" + t;
						if(!queries.contains(cTemp)){
							//Check if previous predicate contains not, then marks current field as not containing
							String np = "";
							if(not == true){
								np="!";
							}else{	
								np = "";
							}
							queries.add(cTemp);
							if(b.equals("and")){
								not = false;
								apiString += p + np + "=*" + t + "*,";
							}else if(b.equals("or")){
								not = false;
								apiString += p + np + "=*" + t + "*|";
							}else if(b.equals("and_not")){
								not = true;
								apiString += p + np + "=*" + t + "*,";
							}
						}
					}
				}
				++i;
			}
		}
		catch(NullPointerException e){}
		URL handle = new URL(baseURL + apiString);
		URLConnection cn = handle.openConnection();
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                cn.getInputStream()));
        String inputLine;
        String xmlString = "";
        while ((inputLine = in.readLine()) != null) {
			xmlString += inputLine;
		}
		
		//out.print(apiString);
		
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xmlString));

			Document doc = db.parse(is);

			NodeList variableNames = doc.getElementsByTagName("var");
			NodeList codebookNames = doc.getElementsByTagName("titl");
			
		    // Header table HTML
	        out.print(variableNames.getLength() + " results returned.");
			out.print("<span class=\"alignRight\"><span id=\"advancedSearchBack\">&lt;&lt;Search again.</span></span><br />");
		    out.print("<hr />");
		    out.print("<table class=\"simpleSearchTable\"><tr><td class=\"tdLeft\">Variable</td><td class=\"tdMiddle\">Label</td><td class=\"tdRight\">Codebook</td></tr></table>");
		    out.print("<hr />");
		    
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
				//"&backInfo=" + request.getParameter("query") +
				String urlHTML = "<td class=\"tdLeft\">"
				+"<a href=\"Login?redirect=AdvancedSearchViewVariable?variableName=" 
				+ element.getAttributes().getNamedItem("name").getNodeValue() 
				+ "&backInfo=" + apiString
				+ "&codebook="+ codebookTitle + "\" class=\"variableName\">" 	
				+ element.getAttributes().getNamedItem("name").getNodeValue() + "</a></td>";
				
				out.print(urlHTML);
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
}
