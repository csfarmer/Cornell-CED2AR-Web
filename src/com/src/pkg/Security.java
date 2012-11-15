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

		
		//Creates random 32 byte for password
		Random r = new SecureRandom();
		byte[] s = new byte[32];
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
		db.execSQL(insertQuery);
		
	}
	//Given username and inputed password, checks in password is correct
	public static Boolean login(String email, String password) {
		Boolean authenicated = false;
		
		String salt = "";//TODO: Need to make DB call to fetch user's salt
		String givenSalted = salt + password ;
		String givenHash = hash(givenSalted);

		String hash = "";;//TODO: Need to make DB call to fetch user's hashed password
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
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
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
	public static Boolean testInput(String input, String regex)
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
}