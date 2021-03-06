package com.src.pkg;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	boolean badPassword = false;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		
		//TODO: Add CAPTCHA to help prevent bruteforce attack
		//Sets the redirect header
		String redirect = request.getParameter("redirect");
		if(redirect == null){
			redirect = "index.jsp";	
		} 
		String varName = request.getParameter("variableName");
		if (varName != null && !varName.equals("null"))
			redirect += "?variableName=" + request.getParameter("variableName");
		if (request.getParameter("codebook") != null)
			redirect += "&codebook=" + request.getParameter("codebook");
		if (request.getParameter("backInfo") != null)
			redirect += "&backInfo=" + request.getParameter("backInfo");

		//Redirect if already logged in
		HttpSession userSession = request.getSession();
		try
		{
			String loggedIn = userSession.getAttribute("loggedIn").toString();
			response.sendRedirect(redirect);
		}
		catch(Exception e){}



		//Determines if previous attempt was invalid
		String loginError = " ";
		if(badPassword){
			loginError = "Invalid Username or Password";
			badPassword = false;
		}

		//If already logged in, forward to address
		String loggedIn = request.getParameter("loggedIn");
		if(loggedIn!=null)
		{
			response.sendRedirect(redirect);
		}


		//Print login for HTMl code
		String html = ""
		+"<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'>"
		+"<html><head><meta http-equiv='Content-Type' content='text/html; charset=ISO-8859-1'>"
		+"	<link rel='stylesheet' type='text/css' href='styles/styles.css' />"
		+"	<script src='http://code.jquery.com/jquery-latest.js'></script>"
		+"	<title>Please Login to Continue</title>"
		+"</head><body>"
		+"	<div id='header'>"
		+"		<div id='navTop'><a href='About.jsp'>About CED<sup>2</sup>AR</a> | <a href='Login'>Login or Register</a></div>"
		+"		<h1><a href='index.jsp'>CED<sup>2</sup>AR</a> "
		+"			<span class='subhead'>The Comprehensive Extensible Data Documentation and Access Repository</span>"
		+"		</h1>"
		+"	</div>"
		+"	<form action='Login?redirect="+ redirect+"' method='post'>"
		+"	<div id='passwordTable'><h2>Please Login</h2>"
		+"		<table><tr><td>Email</td><td><input name='email' type='text' /></td></tr>"
		+"		<tr><td>Password</td><td><input name='password' type='password' /></td></tr>" 
		+"		<tr><td></td><td><input type='submit' value='Login' /></td></tr></table>"
		+"		<p class='loginExtra' id='loginError'>"+loginError+"</p>"
		+"		<p class='loginExtra smaller'><a href='Forgot'>Forgot Your Password?</a></p>"
		+"		<p class='loginExtra'>Don't have an account? <a href='Register'>Register Now</a></p></div>"
		+"</form></body></html>";
		out.print(html); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String redirect = request.getParameter("redirect");
		if(redirect == null){
			redirect = "index.jsp";
		}
		
		String varName = request.getParameter("variableName");
		if (varName != null && !varName.equals("null"))
			redirect += "?variableName=" + request.getParameter("variableName");
		if (request.getParameter("codebook") != null)
			redirect += "&codebook=" + request.getParameter("codebook");
		if (request.getParameter("backInfo") != null)
			redirect += "&backInfo=" + request.getParameter("backInfo");
		
		String loggedIn = request.getParameter("loggedIn");
		if(loggedIn!=null)
		{
			response.sendRedirect(redirect);
		}

		String email = request.getParameter("email");  
		String password = request.getParameter("password");  
		boolean auth = Security.login(email, password);
		if(auth){
			HttpSession userSession = request.getSession();
			userSession.setAttribute("loggedIn", email);
			response.sendRedirect(redirect);
		}
		else{
			badPassword = true;
			response.sendRedirect("Login?redirect="  + redirect);
		}

	}

}