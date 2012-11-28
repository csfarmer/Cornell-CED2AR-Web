package com.src.pkg;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Forgot
 */
@WebServlet("/Forgot")
public class Forgot extends HttpServlet {
	String errorMsg = " ";
	String question = "";
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Forgot() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String email = request.getParameter("email");
		
		if(email == null){
			String html = ""
					+"<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'>"
					+"<html><head><meta http-equiv='Content-Type' content='text/html; charset=ISO-8859-1'>"
					+"	<link rel='stylesheet' type='text/css' href='styles/styles.css' />"
					+"	<script src='http://code.jquery.com/jquery-latest.js'></script>"
					+"	<title>Reset Password</title>"
					+"</head><body>"
					+"	<div id='header'>"
					+"		<div id='navTop'><a href='About.jsp'>About CED<sup>2</sup>AR</a> | <a href='Login'>Login or Register</a></div>"
					+"		<h1><a href='index.jsp'>CED<sup>2</sup>AR</a> "
					+"			<span class='subhead'>The Comprehensive Extensible Data Documentation and Access Repository</span>"
					+"		</h1>"
					+"	</div><div id='passwordTable' class='forgot'>"
					+"<h2>Forgot Your Password?</h2>"
					+"<p>Step 1: Enter your registered email address.</p>"
					+"<p id='forgotError'>"+errorMsg+"</p>"
					+"<form action='Forgot' method='post'>"
					+"	<table><tr><td>Email Address</td><td><input type='text' name='email'/></td></tr>"
					+"			<tr><td></td><td><input type='submit' value='Continue'/></td></tr></table></form>"
					+"</div></body></html>";
					out.print(html); 
		}else{
			String html = ""
					+"<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'>"
					+"<html><head><meta http-equiv='Content-Type' content='text/html; charset=ISO-8859-1'>"
					+"	<link rel='stylesheet' type='text/css' href='styles/styles.css' />"
					+"	<script src='http://code.jquery.com/jquery-latest.js'></script>"
					+"	<title>Reset Password</title>"
					+"</head><body>"
					+"	<div id='header'>"
					+"		<div id='navTop'><a href='About.jsp'>About CED<sup>2</sup>AR</a> | <a href='Login'>Login or Register</a></div>"
					+"		<h1><a href='index.jsp'>CED<sup>2</sup>AR</a> "
					+"			<span class='subhead'>The Comprehensive Extensible Data Documentation and Access Repository</span>"
					+"		</h1>"
					+"	</div><div id='passwordTable' class='forgot'>"
					+"<h2>Forgot Your Password?</h2>"
					+"<p>Step 2: Answer your security challenge question and choose your new password.</p>"
					+"<p id='forgotError'>"+errorMsg+"</p>"
					+"<form action='Forgot' method='post'>"
					+"<table><tr><td>"+question+"</td>"
					+"<td><input type='text' name='answer'/></td></tr>"
					+"<tr><td>New Password</td><td><input type='password' name='password1'/></td></tr>"
					+"<tr><td>Retype new password</td><td><input type='password' name='password2'/></td></tr>"
					+"<tr><td></td><td><input type='hidden' value='"+email+"' name='email'/>"
					+"	<input type='submit' value='Continue'/></td></tr>"
					+"</table></form>"
					+"</div></body></html>";
					out.print(html); 
		}
		errorMsg = " ";
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		errorMsg = " ";
		PrintWriter out = response.getWriter();
		String email = request.getParameter("email");
		String answer = request.getParameter("answer");
		String password1 = request.getParameter("password1");
		String password2 = request.getParameter("password2");
		String regexPwd = "^[a-zA-Z0-9!@#$%^&*()-_+=~` ]+$";//Password characters allowed
		
		//Email entered, answer not entered
		if(email!=null && answer == null)
		{
			String q = Security.getChallenge(email);
			if(!q.equals("")){
				question = q;
				response.sendRedirect("Forgot?email="+email);
			}else{
				errorMsg+="Email not found <br />";
				response.sendRedirect("Forgot");
			}
		}else{
			
			//Checks if password is valid
			if(!Security.testInput(password1, regexPwd) || password1.length() > 100){
				errorMsg+="<p class='regError'>Invalid Password</p>";
				response.sendRedirect("Forgot?email="+email);
				return;
			}	
			
			//Checks if new passwords match
			if(password2 == null || !password2.equals(password1)){
				errorMsg+="<p class='regError'>Passwords do not match</p>";
				response.sendRedirect("Forgot?email="+email);
				return;
			}
			
			
			//Email entered and answer entered
			if(email!=null && answer != null){
				boolean auth = Security.testQuestion(answer, email);
				//If answer is correct
				if(auth){
					String html = ""
							+"<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'>"
							+"<html><head><meta http-equiv='Content-Type' content='text/html; charset=ISO-8859-1'>"
							+"	<link rel='stylesheet' type='text/css' href='styles/styles.css' />"
							+"	<script src='http://code.jquery.com/jquery-latest.js'></script>"
							+"	<title>Reset Password</title>"
							+"</head><body>"
							+"	<div id='header'>"
							+"		<div id='navTop'><a href='About.jsp'>About CED<sup>2</sup>AR</a> | <a href='Login'>Login or Register</a></div>"
							+"		<h1><a href='index.jsp'>CED<sup>2</sup>AR</a> "
							+"			<span class='subhead'>The Comprehensive Extensible Data Documentation and Access Repository</span>"
							+"		</h1>"
							+"	</div><div id='passwordTable' class='forgot'>";
					out.write(html);
					Security.reset(email, password1);
					out.write("Your Password has been reset. You can now <a href='Login'>login</a></div></body><html>");
				}else{
					errorMsg+="Security Question was answered incorrectly <br />";	
					response.sendRedirect("Forgot?email="+email);
					return;
				}
			}
			
		}
	}
}
