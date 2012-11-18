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
		}*/
		
		
		String query = "SELECT Count(*) as c FROM public.\"Person\"";
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:postgresql://rschdata.ciserrsch.cornell.edu:5432/CED2AR_ADM","CED2AR_WEB", "BigredCU!");
		} catch (SQLException e) {
			out.write(e.toString());
			Logger lgr = Logger.getLogger(DBhandle.class.getName());
            lgr.log(Level.SEVERE, e.getMessage(), e);
		}
		ResultSet results = null;
		if (connection != null) {
	        try {
	        	PreparedStatement sql = connection.prepareStatement(query);
				results = sql.executeQuery();
				out.write(results.toString());
			} catch (SQLException e) {
				out.write("2");
				out.write(e.toString());
				Logger lgr = Logger.getLogger(DBhandle.class.getName());
	            lgr.log(Level.SEVERE, e.getMessage(), e);
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
