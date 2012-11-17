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
		
		//Sets the redirect header
		String redirect = request.getParameter("redirect");
		if(redirect == null){
			redirect = "index.jsp";
		}
				
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
		+"	<link rel='stylesheet' type='text/css' href='styles/reset.css' />"
		+"	<script src='http://code.jquery.com/jquery-latest.js'></script>"
		+"	<title>Please Login to Continue</title>"
		+"</head><body>"
		+"	<form action='Login' method='post'>"
		+"	<div id='passwordTable'><h2>Please Login</h2>"
		+"		<table><tr><td>Email</td><td><input name='email' type='text' method='post'/> </td></tr>"
		+"		<tr><td>Password</td><td><input name='password' size=15 type='password' /></td></tr>" 
		+"		<tr><td><input type='submit' value='Login' /></td></tr></table>"
		+"		<p class='loginExtra' id='loginError'>"+loginError+"</p>"
		+"		<p class='loginExtra'><a href='Forgot'>Forgot Your Password?</a></p>"
		+"		<p class='loginExtra'>Don't have an account? <a href='Register'>Register</a></p></div>"
		+"	<input type='hidden' name='redirect' value='"+redirect+"'/>"
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
			response.sendRedirect("Login");
		}

	}

}
