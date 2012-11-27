package com.src.pkg;

import java.io.PrintWriter;
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

public class DBhandle {
	Connection connection = null;
	//Connects to CEDDAR DB
	//TODO: Would be good to add transaction support
	public DBhandle(){
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://rschdata.ciserrsch.cornell.edu:5432/CED2AR_ADM","CED2AR_WEB", "BigredCU!");
			//connection.setAutoCommit(false);
		} catch (SQLException e) {
			Logger lgr = Logger.getLogger(DBhandle.class.getName());
            lgr.log(Level.SEVERE, e.getMessage(), e);
		}
		catch (ClassNotFoundException e) {
			Logger lgr = Logger.getLogger(DBhandle.class.getName());
            lgr.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	//Allows to exec an SQL query. Results are returned at a ResultSet instance
	public ResultSet execSQL(String query){
		ResultSet results = null;
		if (connection != null) {
	        try {
	        	PreparedStatement sql = connection.prepareStatement(query);
				results = sql.executeQuery();
			} catch (SQLException e) {
				Logger lgr = Logger.getLogger(DBhandle.class.getName());
	            lgr.log(Level.SEVERE, e.getMessage(), e);
			}
		}	
		return results;
	}
	
	public void insertSQL(String query){
		if (connection != null) {
	        try {
	        	PreparedStatement sql = connection.prepareStatement(query);
				sql.execute();
			} catch (SQLException e) {
				Logger lgr = Logger.getLogger(DBhandle.class.getName());
	            lgr.log(Level.SEVERE, e.getMessage(), e);
			}
		}	
	}
	
	public void close(){
		try {
			connection.close();
		} catch (SQLException e) {
			
		}
		
	}
}