package com.src.pkg;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


/**
 * Servlet implementation class Test
 */
@WebServlet("/Test")
public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Test() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String test = "ba.p-63@cornell.ed.u";
		
		/**
		 * Need Java.mail
		 * http://www.oracle.com/technetwork/java/javamail/index.html
		try {
		    new InternetAddress(test).getAddress();
		    out.write("Good");
		} catch (AddressException e) {
		    // it's not valid
		}
		*/
		
		/*
		String regex = "";
		Boolean good = Security.testInput(test,regex);
	
		if(good){
			
			out.write("Good");
		}else{
			out.write("Bad");
		}*/	
		/*
		Boolean good = Security.login("bap63@cornell.edu", "password");
	
		if(good){
			
			out.write("Good");
		}else{
			out.write("Bad");
		}
		*/
		
		/*
		Boolean good = Security.testEmail("bap63@cornell.edu");
	
		if(good){
			
			out.write("Good email");
		}else{
			out.write("Bad email");
		}
		*/
		
		DBhandle db = new DBhandle();

		//This block of code generates a new PersonID for user
		//The PersonID is simply the number of users + 1 (starting 
		ResultSet results = db.execSQL("SELECT Count(*) as c FROM public.\"Person\"");

		if(results != null){
			try {
					results.next();
					int pID = Integer.parseInt(results.getString("c"));
					out.write(pID);
				} 
				catch (SQLException e) {
					out.write(e.toString());
				}
		   }
	
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
}
