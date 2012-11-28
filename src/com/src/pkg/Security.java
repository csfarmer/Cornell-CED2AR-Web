package com.src.pkg;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class Security {

	/*Creates New User*/
	public static void signup(String password, String fname, String lname, String org, String field, String email,String qID, String qAnswer) {
		String personID = "-1";
		DBhandle db = new DBhandle();
		try{
			//This block of code generates a new PersonID for user
			//The PersonID is simply the number of users + 1 (starting 
			ResultSet results = db.execSQL("SELECT Count(*) as c FROM public.\"Person\"");
			try{
				if(results != null){
					try {
							results.next();
							int pID = Integer.parseInt(results.getString("c"));
							pID++;
							personID = Integer.toString(pID);
						} 
						catch (SQLException e) {
							e.printStackTrace();
						}
				   }
			}
			//Close ResultSet object. NOTE add these blocks around SQL functions to close once finished
			finally{
				try {
					results.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			//java.security.signedObject?
			//Creates random 20 byte salt for password
			Random r = new SecureRandom();
			byte[] s = new byte[20];
			r.nextBytes(s);
			String salt = s.toString();
			String hash = hash(salt + password);
			//Gets Current date
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			Date date = new Date();
			String dateStamp = dateFormat.format(date);
	
			
			String insertQuery = "INSERT INTO public.\"Person\" " +
			"VALUES('"+personID+"','"+fname+"','"+lname+"','"+org+"','"+field+"','"+email+"','"+hash+"','"+salt+"','"+dateStamp+"')";
			db.execSQL(insertQuery);
			challenge(personID,qID,qAnswer);
		}
		finally{
			db.close();
		}
	}
	/*Creates security questions*/
	public static void challenge(String personID, String qID, String answer){
		String answerID = "-1";
		DBhandle db = new DBhandle();
		try{
			//This block of code generates a new answerID
			ResultSet results = db.execSQL("SELECT Count(*) as c FROM public.\"SecurityAnswers\"");
			try{
				if(results != null){
					try {
							results.next();
							int aID = Integer.parseInt(results.getString("c"));
							aID++;
							answerID = Integer.toString(aID);
						} 
						catch (SQLException e) {
							e.printStackTrace();
						}
				   }
			}
			//Close ResultSet object. NOTE add these blocks around SQL functions to close once finished
			finally{
				try {
					results.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
			//java.security.signedObject?
			//Creates random 20 byte salt for password
			Random r = new SecureRandom();
			byte[] s = new byte[20];
			r.nextBytes(s);
			String salt = s.toString();
			String hash = hash(salt + answer);
			
			String insertQuery = "INSERT INTO public.\"SecurityAnswers\" VALUES"
			+"('"+answerID+"','"+personID+"','"+qID+"','"+hash+"','"+salt+"')";
			db.execSQL(insertQuery);
			//return insertQuery;
		}
		finally{
			db.close();
		}
		
		
	}
	
	/*Gets challenge question*/
	public static String getChallenge(String email){
		String question = "";
		String query ="select q.\"QuestionText\" as q from public.\"SecurityAnswers\" as a" 
						+" JOIN public.\"Person\" as p ON(p.\"PersonID\" = a.\"PersonID\")" 
						+" JOIN public.\"SecurityQuestions\" as q ON (q.\"QuestionID\" = a.\"QuestionID\")"
						+" WHERE p.\"Email\" = '"+email+"'";
		DBhandle db = new DBhandle();
		try{
			ResultSet results = db.execSQL(query);
			try{
				results.next();
				question =results.getString("q");
			} catch (SQLException e) {
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
		finally{
			db.close();
		}
		return question;
	
	}
	/*Resets password*/
	public static void reset(String email,String password){
		String salt = "";
		DBhandle db = new DBhandle();
		String query ="select p.\"Salt\" from public.\"Person\" as p" 
				+" WHERE p.\"Email\" = '"+email+"'";
		try{
			ResultSet results = db.execSQL(query);
			try{
				results.next();
				salt =results.getString("Salt");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			finally{
				try {
					results.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			/*
			 * UPDATE public."Person" as p SET 
			 * "PassHash"='10eeacaf8e8b3685564525247120afac4eb78824af20f654a31b7c8d36ac30420156d258cd1eccb9568ed4cee9b8da09ffadb00e80fd1fd1a7d4f41beb01d373' 
			 * WHERE p."Email" = 'bap63@cornell.edu'
			 * 
			 * 
			 * 
			 * */
			String hash = hash(salt + password) ;
			String query2 = "UPDATE public.\"Person\" as p"
					+" SET \"PassHash\"='"+hash+"'"
					+" WHERE p.\"Email\" = '"+email+"'";
			db.execSQL(query2);
		}
		finally{
			db.close();
		}
		

		
		
	}
	
	/*Given email and inputed password, checks in password is correct. If email isn't found, returns false.*/
	public static boolean login(String email, String password) {
		String salt = "";
		String hash = "";
		Boolean authenicated = false;
		DBhandle db = new DBhandle();
		try{
			//Checks to see if email address is valid. Will return false if bad.
			boolean validEmail = testEmail(email);
			if(!validEmail){
				return false;
			}
	
			//Checks to see if email address exists. If false, returns invalid username password combination"
			ResultSet emailExists = db.execSQL("SELECT Count(*) as c FROM public.\"Person\" where \"Person\".\"Email\"='"+email+"'");
			if(emailExists != null){
				try {
						emailExists.next();
						int pID = Integer.parseInt(emailExists.getString("c"));
						if(pID != 1)
						{
							return false;
						}
					} 
					catch (SQLException e) {
						e.printStackTrace();;
					}
			   }
			
			//Fetches Password and Salt from DB
			
			ResultSet hashResult = db.execSQL("SELECT \"Person\".\"PassHash\" as h FROM public.\"Person\" where \"Person\".\"Email\"='"+email+"'");
			try{
				ResultSet saltResult = db.execSQL("SELECT \"Person\".\"Salt\" as s FROM public.\"Person\" where \"Person\".\"Email\"='"+email+"'");
				try{
				if(hashResult != null && saltResult != null){
					try {
							hashResult.next();
							saltResult.next();
							hash = hashResult.getString("h");
							salt = saltResult.getString("s");
						} 
						catch (SQLException e) {
							e.printStackTrace();
						}
				   }
				}
				finally{
					try {
						saltResult.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
			}
			finally{
				try {
					hashResult.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			//Generated Hash based off salt from the db + what user inputed
			String givenSalted = salt + password ;
			String givenHash = hash(givenSalted);
		
			//Compares given hash with stored db hash
			if(givenHash.equals(hash)){
				authenicated = true;
			}
		}
		finally{
			db.close();
		}
		
		return authenicated;
		
	}
	
	public static boolean testQuestion(String answer,String email){
		String hash = "";
		String salt = "";
		Boolean authenicated = false;
		DBhandle db = new DBhandle();
		String query ="select a.\"AnswerHash\", a.\"AnswerSalt\" from public.\"SecurityAnswers\" as a" 
				+" JOIN public.\"Person\" as p ON(p.\"PersonID\" = a.\"PersonID\")" 
				+" JOIN public.\"SecurityQuestions\" as q ON (q.\"QuestionID\" = a.\"QuestionID\")"
				+" WHERE p.\"Email\" = '"+email+"'";
		try{
			ResultSet results = db.execSQL(query);
			try{
				results.next();
				hash =results.getString("AnswerHash");
				salt =results.getString("AnswerSalt");
			} catch (SQLException e) {
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
		finally{
			db.close();
		}

		String givenSalted = salt + answer;
		String givenHash = hash(givenSalted);
		
		//Compares given hash with stored db hash
		if(givenHash.equals(hash)){
			authenicated = true;
		}

		return authenicated;
		
	
	}
	//Creates a new hash given a string
	public static String hash(String password) {
		StringBuilder hash = new StringBuilder();
		try {
			MessageDigest sha = MessageDigest.getInstance("SHA-512");
			byte[] hashedBytes = sha.digest(password.getBytes());
			char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9','a', 'b', 'c', 'd', 'e', 'f' };
			for (int idx = 0; idx < hashedBytes.length; ++idx) {
				byte b = hashedBytes[idx];
				hash.append(digits[(b & 0xf0) >> 4]);
				hash.append(digits[b & 0x0f]);
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hash.toString();
	}
	
	/*Tests user input against regex. Provide string to test and regex. 
	 * Never allows all whitespace
	 * 
	 * Must start and end with letter. Can have space or hyphen in the middle 
	 * "^[a-zA-Z]+[- ]?[a-zA-Z]+$"
	 * 
	 * */
	public static boolean testInput(String input, String regex)
	{
		try{
			input = input.trim();
			if(input.length() == 0)
			{
				return false;
			}
			if(input.matches(regex)){
				return true;
			}else{
				return false;
			}
		}
		catch(NullPointerException ex){
			return false;
		}
	}
	
	/*Validates Email address input using java.mail package*/
	public static boolean testEmail(String email){
	        try {
	            new InternetAddress(email).validate();
	        } catch (AddressException ex) {
	            return false;
	        }
	        catch(NullPointerException ex){
	        	return false;
	        }
	        return true;
	}	
}