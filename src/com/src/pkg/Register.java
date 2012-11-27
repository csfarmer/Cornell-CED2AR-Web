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
				+"	<link rel='stylesheet' type='text/css' href='styles/reset.css' />"
				+"	<script src='http://code.jquery.com/jquery-latest.js'></script>"
				+"	<title>Register</title>"
				+"</head><body>"
				+"<div id='errors'></div><form action='Register' method='post'>"
				+"	First Name<input type='text' name='fname'/><br />"
				+"	Last Name<input type='text' name='lname'/><br />"
				+"	Email<input type='text' name='email'/><br />"
				+"	Organization<input type='text' name='org'/><br />"
				+"	Department Field<input type='text' name='field'/><br />"
				+"	Password<input type='password' name='password1'/><br />"
				+"	Confirm Password<input type='password' name='password2'/><br />"
				+"<input type='submit' value='Register'/></form>"
				+"</body></html>";
				out.print(html); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		DBhandle db = new DBhandle();
		String regex = "^[a-zA-Z]+[- ]?[a-zA-Z]+$";
		String regex2 = "^[a-zA-Z -]+$";
		String regexPwd = "^[a-zA-Z0-9!@#$%^&*()-_+=~` ]+$";
		boolean error = false;
		
		//HTML page header
		String html = ""
				+"<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN' 'http://www.w3.org/TR/html4/loose.dtd'>"
				+"<html><head><meta http-equiv='Content-Type' content='text/html; charset=ISO-8859-1'>"
				+"	<link rel='stylesheet' type='text/css' href='styles/styles.css' />"
				+"	<link rel='stylesheet' type='text/css' href='styles/reset.css' />"
				+"	<script src='http://code.jquery.com/jquery-latest.js'></script>"
				+"	<title>Register</title>"
				+"</head><body>";
		out.write(html);
		
		//Checks if valid email
		String email = request.getParameter("email");
		if(!Security.testEmail(email) || email.length() > 100){
			out.write("<p class='regError'>Invalid Email</p>");
			error = true;
		}
		
		//Checks if valid first name
		String fname = request.getParameter("fname");
		if(!Security.testInput(fname, regex) || fname.length() > 100){
			out.write("<p class='regError'>Invalid First Name</p>");
			error = true;
		}
		
		//Checks if valid last name
		String lname = request.getParameter("lname");
		if(!Security.testInput(lname, regex) || lname.length() > 100){
			out.write("<p class='regError'>Invalid Last Name</p>");
			error = true;
		}
		
		//Checks if valid last org
		String org = request.getParameter("org");
		if(!Security.testInput(org, regex2) || org.length() > 100){
			out.write("<p class='regError'>Invalid Organization</p>");
			error = true;
		}
		
		//Checks if valid last org
		String field = request.getParameter("field");
		if(!Security.testInput(field, regex2) || field.length() > 100){
			out.write("<p class='regError'>Invalid Departments Field</p>");
			error = true;
		}		
		
		//Checks password
		String password1 = request.getParameter("password1");
		if(!Security.testInput(password1, regexPwd) || password1.length() > 100){
			out.write("<p class='regError'>Invalid Password</p>");
			error = true;
		}		
		
		//Checks password
		String password2 = request.getParameter("password2");
		if(password2 == null || !password2.equals(password1)){
			out.write("<p class='regError'>Passwords do not match</p>");
			error = true;
		}
		
		
		//Checks if account exists
		ResultSet results = db.execSQL("SELECT Count(*) as c FROM public.\"Person\" where \"Person\".\"Email\" ='"+email+"'");
		if(results != null){
			try {
					results.next();
					int pID = Integer.parseInt(results.getString("c"));
					if(pID != 0){
						out.write("<p class='regError'>Account Already Exists</p>");
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
			out.write("<p><a href='Register'>Try again</a></p></body></html>");
			return;
		}else{
			Security.signup(password1, fname, lname, org, field, email);
			request.setAttribute(email, "");
			request.setAttribute(fname, "");
			request.setAttribute(lname, "");
			request.setAttribute(org, "");
			request.setAttribute(field, "");
			request.setAttribute(password1, "");
			request.setAttribute(password2, "");
			out.write("<p'>Account created. Please return to the <a href='index.jsp'>homepage</a></p></body></html>");
		}
		
		
		
	}

}
