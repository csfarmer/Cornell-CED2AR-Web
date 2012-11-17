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
	//TODO:Check Password requirements? Length, letters, numbers, special characters?
	/*
	 *Wrote regex for:  
	 * -Must start and end with letter. Can have space or hyphen in the middle. 
	 *
	 *Need to write regex for email address. Might use 
	 *http://www.oracle.com/technetwork/java/javamail/javamail144-1562675.html 
	 *to comply with RDF  standards
	 *Look at function testInput() in this class 
	 * 
	 * */		
	public void signup(String password, String fname, String lname, String org, String field, String Email) {
		DBhandle db = new DBhandle();
		
		//This block of code generates a new PersonID for user
		//The PersonID is simply the number of users + 1 (starting 
		ResultSet results = db.execSQL("SELECT Count(*) as c FROM public.\"Person\"");
		String personID = "-1";
		if(results != null){
			try {
					results.next();
					int pID = Integer.parseInt(results.getString("c"));
					pID++;
					personID = Integer.toString(pID);
				} 
				catch (SQLException e) {
					e.printStackTrace();;
				}
		   }

		
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
		
		//TODO: Finish SQL insert query
		String insertQuery = "INSERT INTO public.\"Person\" VALUES()";
		
		/*Fields for Person table:
			PersonID
			FirstName
			LastName
			OrganizationName
			DeptField
			Email
			PassHash
			Salt
			DateCreated
		 */
		
		/*Fields for SecurityQuestions table
		 		Question ID
		 		Question Text
		 */
		
		/*Fields for SecurityAnswer table
 			AnswerID
 			PersonID
 			QuestionID
 			AnswerHash
 			AnswerSalt
		*/
		
		
		db.execSQL(insertQuery);
		
	}
	/*Given email and inputed password, checks in password is correct. If email isn't found, returns false.*/
	public static boolean login(String email, String password) {
		String salt = "";
		String hash = "";
		Boolean authenicated = false;
		DBhandle db = new DBhandle();
		
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
		ResultSet saltResult = db.execSQL("SELECT \"Person\".\"Salt\" as s FROM public.\"Person\" where \"Person\".\"Email\"='"+email+"'");
		if(hashResult != null && saltResult != null){
			try {
					hashResult.next();
					saltResult.next();
					hash = hashResult.getString("h");
					salt = saltResult.getString("s");
				} 
				catch (SQLException e) {
					e.printStackTrace();;
				}
		   }
		
		//Generated Hash based off salt from the db + what user inputed
		String givenSalted = salt + password ;
		String givenHash = hash(givenSalted);

		//Compares given hash with stored db hash
		if(givenHash.equals(hash)){
			authenicated = true;
		}else{
			authenicated = false;
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
	/*Validates Email address input using java.mail package*/
	public static boolean testEmail(String email){
	        try {
	            new InternetAddress(email).validate();
	        } catch (AddressException ex) {
	            return false;
	        }
	        return true;
	}	
}