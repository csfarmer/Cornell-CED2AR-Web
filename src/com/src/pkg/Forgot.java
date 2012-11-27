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
					+"	<link rel='stylesheet' type='text/css' href='styles/reset.css' />"
					+"	<script src='http://code.jquery.com/jquery-latest.js'></script>"
					+"	<title>Reset Password</title>"
					+"</head><body>"
					+"<p id='forgotError'>"+errorMsg+"<p>"
					+"<form action='Forgot' method='post'>"
					+"	Email Address<input type='text' name='email'/><br />"
					+"	<input type='submit' value='Continue'/></form>"
					+"</body></html>";
					out.print(html); 
		}else{
			String html = ""
					+"<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'>"
					+"<html><head><meta http-equiv='Content-Type' content='text/html; charset=ISO-8859-1'>"
					+"	<link rel='stylesheet' type='text/css' href='styles/styles.css' />"
					+"	<link rel='stylesheet' type='text/css' href='styles/reset.css' />"
					+"	<script src='http://code.jquery.com/jquery-latest.js'></script>"
					+"	<title>Reset Password</title>"
					+"</head><body>"
					+"<p id='forgotError'>"+errorMsg+"<p>"
					+"<form action='Forgot' method='post'>"
					+"<p>"+question+"</p>"
					+"<input type='text' name='answer'/><br />"
					+"New Password<input type='password' name='password1'/><br />"
					+"Retype new password<input type='password' name='password2'/><br />"
					+"<input type='hidden' value='"+email+"' name='email'/>"
					+"	<input type='submit' value='Continue'/></form>"
					+"</body></html>";
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
							+"	<link rel='stylesheet' type='text/css' href='styles/reset.css' />"
							+"	<script src='http://code.jquery.com/jquery-latest.js'></script>"
							+"	<title>Reset Password</title>"
							+"</head><body>";
					out.write(html);
					//TODO:Actually change password in db
					out.write("Your Password has been reset. You can now <a href='Login'>login</a></body><html>");
				}else{
					errorMsg+="Security Question was answered incorrectly <br />";	
					response.sendRedirect("Forgot?email="+email);
					return;
				}
			}
			
		}
	}
}
