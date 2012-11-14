package com.src.pkg;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Security {

	public void signup(String personID, String password, String fname, String lname, String org, String field, String Email) {
		
		//TODO:Check Password requirements? Length, letters, numbers, special characters?
		
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
		String query = "INSERT INTO public.\"Person\" VALUES()";
		
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
		DBhandle db = new DBhandle();
		db.execSQL(query);
		
	}
	//Given username and inputed password, checks in password is correct
	public static Boolean login(String username, String password) {
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

}