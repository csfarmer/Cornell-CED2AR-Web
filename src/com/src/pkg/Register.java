package com.src.pkg;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Register
 */
@WebServlet("/Register")
public class Register extends HttpServlet {
	//Available challenge questions
	String q1= "What is your favorite color?";
	String q2 ="Where were you born?";
	String q3 ="What is your father's middle name?";

	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Register() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String html = ""
				+"<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'>"
				+"<html><head><meta http-equiv='Content-Type' content='text/html; charset=ISO-8859-1'>"
				+"	<link rel='stylesheet' type='text/css' href='styles/styles.css' />"
				+"	<script src='http://code.jquery.com/jquery-latest.js'></script>"
				+"	<title>Register</title>"
				+"</head><body>"
				+"	<div id='header'>"
				+"		<div id='navTop'><a href='About.jsp'>About CED<sup>2</sup>AR</a> | <a href='Login'>Login or Register</a></div>"
				+"		<h1><a href='index.jsp'>CED<sup>2</sup>AR</a> "
				+"			<span class='subhead'>The Comprehensive Extensible Data Documentation and Access Repository</span>"
				+"		</h1>"
				+"	</div>"
				+"<div id='passwordTable' class='register'>"
				+"	<div id='errors'></div>"
				+"	<form action='Register' method='post'>"
				+"		<table>"
				+"			<tr><td>First Name</td><td><input type='text' name='fname'/></td></tr>"
				+"			<tr><td>Last Name</td><td><input type='text' name='lname'/></td></tr>"
				+"			<tr><td>Email</td><td><input type='text' name='email'/></td></tr>"
				+"			<tr><td>Organization</td><td><input type='text' name='org'/></td></tr>"
				+"			<tr><td>Department Field</td><td><input type='text' name='field'/></td></tr>"
				+"			<tr><td>Password</td><td><input type='password' name='password1'/></td></tr>"
				+"			<tr><td>Confirm Password</td><td><input type='password' name='password2'/></td></tr>"
				+"			<tr><td>Select a Security Question</td><td><select name='question'>"
				+"				<option value='1'>"+q1+"</option>"
				+"				<option value='2'>"+q2+"</option>"
				+"				<option value='3'>"+q3+"</option></td></tr>"
				+"  		<tr><td>Answer the Security Question</td><td><input type='text' name='challenge'/></td></tr>"
				+"			<tr><td></td><td><input type='submit' value='Register'/></td></tr>"
				+"		</table>"
				+"	</form>"
				+"</div></body></html>";
				out.print(html); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		DBhandle db = new DBhandle();
		//These are the rules for input requirements
		//In additional, all fields must be <100 characters
		String regex = "^[a-zA-Z]+[- ]?[a-zA-Z]+$";//Letters, can contain spaces or hyphens in the middle
		String regex2 = "^[a-zA-Z0-9 -]+$";//Alphanumeric and hyphen
		String regexPwd = "^[a-zA-Z0-9!@#$%^&*()-_+=~` ]+$";//Password characters allowed
		boolean error = false;
		
		//HTML page header
		String html = ""
				+"<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'>"
				+"<html><head><meta http-equiv='Content-Type' content='text/html; charset=ISO-8859-1'>"
				+"	<link rel='stylesheet' type='text/css' href='styles/styles.css' />"
				+"	<script src='http://code.jquery.com/jquery-latest.js'></script>"
				+"	<title>Register</title>"
				+"</head><body>"
				+"	<div id='header'>"
				+"		<div id='navTop'><a href='About.jsp'>About CED<sup>2</sup>AR</a> | <a href='Login'>Login or Register</a></div>"
				+"		<h1><a href='index.jsp'>CED<sup>2</sup>AR</a> "
				+"			<span class='subhead'>The Comprehensive Extensible Data Documentation and Access Repository</span>"
				+"		</h1>"
				+"	</div><div id='passwordTable'>";
		out.write(html);
		
		//Checks if valid email
		String email = request.getParameter("email");
		if(!Security.testEmail(email) || email.length() > 100){
			out.write("<li class='regError'>Invalid Email</li>");
			error = true;
		}
		
		//Checks if valid first name
		String fname = request.getParameter("fname");
		if(!Security.testInput(fname, regex) || fname.length() > 100){
			out.write("<li class='regError'>Invalid First Name</li>");
			error = true;
		}
		
		//Checks if valid last name
		String lname = request.getParameter("lname");
		if(!Security.testInput(lname, regex) || lname.length() > 100){
			out.write("<li class='regError'>Invalid Last Name</li>");
			error = true;
		}
		
		//Checks if valid last org
		String org = request.getParameter("org");
		if(!Security.testInput(org, regex2) || org.length() > 100){
			out.write("<li class='regError'>Invalid Organization</li>");
			error = true;
		}
		
		//Checks if valid last org
		String field = request.getParameter("field");
		if(!Security.testInput(field, regex2) || field.length() > 100){
			out.write("<li class='regError'>Invalid Departments Field</li>");
			error = true;
		}		
		
		//Checks password
		String password1 = request.getParameter("password1");
		if(!Security.testInput(password1, regexPwd) || password1.length() > 100){
			out.write("<li class='regError'>Invalid Password</li>");
			error = true;
		}		
		
		//Checks password
		String password2 = request.getParameter("password2");
		if(password2 == null || !password2.equals(password1)){
			out.write("<li class='regError'>Passwords do not match</li>");
			error = true;
		}
		
		//Checks challenge question answer
		String challenge = request.getParameter("challenge");
		if(!Security.testInput(challenge, regexPwd) || challenge.length() > 100){
			out.write("<li class='regError'>Invalid Security Question Answer</li>");
			error = true;
		}	
		
		//Checks challenge question post data was manipulated
		String q = request.getParameter("question");
		if(!q.equals("1") && !q.equals("2") && !q.equals("3")){
					out.write("<li class='regError'>There was an error submiting the form</li>");
					error = true;
		}	

		//Checks if account exists
		ResultSet results = db.execSQL("SELECT Count(*) as c FROM public.\"Person\" where \"Person\".\"Email\" ='"+email+"'");
		if(results != null){
			try {
					results.next();
					int pID = Integer.parseInt(results.getString("c"));
					if(pID != 0){
						out.write("<li class='regError'>Account Already Exists</li>");
						error = true;
					}
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
			finally{
				try {
					results.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		  }
		//Pushes info into DB
		if(error){
			out.write("<p><a href='Register'>Try again</a></p></div></body></html>");
			return;
		}else{
			Security.signup(password1, fname, lname, org, field, email,q,challenge);
			request.setAttribute(email, "");
			request.setAttribute(fname, "");
			request.setAttribute(lname, "");
			request.setAttribute(org, "");
			request.setAttribute(field, "");
			request.setAttribute(password1, "");
			request.setAttribute(password2, "");
			out.write("<p'>Account created. Please return to the <a href='index.jsp'>homepage</a></p></div></body></html>");
		}
		
		
		
	}

}
