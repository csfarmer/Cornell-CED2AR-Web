package com.src.pkg;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.*;

import org.xml.sax.InputSource;

public class XmlUtil {
	/*
	 * Takes the node name (ex: var) and the entire xml string
	 * returns the count
	 */
	public static String getNodeCount(String nodeName, String xmlString) {
		String response = "";
		XPathFactory  factory=XPathFactory.newInstance();
		XPath xPath=factory.newXPath();
		try {
			XPathExpression  xPathExpression=
				    xPath.compile("count(//" + nodeName + ")");
			InputSource is = new InputSource();
			is.setCharacterStream(new StringReader(xmlString)); 
			response = 
					  xPathExpression.evaluate(is);
			
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		
		return response;
	}

	/*
	 * Takes the entire xml string, the page of interest and the path to the xsl style sheet 
	 * returns the page of xml requested transformed slightly to optimize the results
	 */
	public static String getXmlPage(String xmlString, int page, String xslPath) {
		String styledResponse = "";
		StringReader rd = new StringReader(xmlString);
		StringWriter wrt = new StringWriter();
		TransformerFactory tFac = TransformerFactory.newInstance();
		try {
			File xsl = new File(xslPath);
			Transformer transformer = tFac.newTransformer(new StreamSource(xsl));
			transformer.setParameter("Page", String.format("%s", page));
			transformer.transform(new StreamSource(rd), new StreamResult(wrt));
			
			styledResponse = wrt.toString();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		
		return styledResponse;
	}
	
}
