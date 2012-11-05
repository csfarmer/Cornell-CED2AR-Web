package com.src.pkg;

import java.io.IOException;
import java.io.*;
import java.net.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServletFunctions
 */
@WebServlet("/ServletFunctions")
public class ServletFunctions extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletFunctions() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		PrintWriter out = response.getWriter();
		out.print(request.getQueryString()+"\n");
		URL handle = new URL("http://news.google.com/?output=rss");
        URLConnection cn = handle.openConnection();
        BufferedReader in = new BufferedReader(
                                new InputStreamReader(
                                cn.getInputStream()));
        String inputLine;

        while ((inputLine = in.readLine()) != null) 
        	out.print(inputLine);
        in.close();
		

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}