package models;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import daObjects.AuthDAL;
import daObjects.dbCon;
import objects.AuthObjects.UserDetailsObj;
import objects.AuthObjects.UserObj;

public class AuthManager {

	// =======For getting the current date=========================
	DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	Date dateobj = new Date();

	public UserDetailsObj getLogin(String Username, String Password, String App) throws Exception {
		UserDetailsObj inquiryDetails = null;
		try {
			dbCon database = new dbCon();
			Connection connection = database.Get_Conn_1();

			AuthDAL authDAL = new AuthDAL();
			inquiryDetails = authDAL.getLogin(connection, Username, Password, App);
		} catch (Exception e) {
			System.out.println(df.format(dateobj) + " Exception Error at AuthManager,getLogin " + e.getMessage());
			throw e;
		}
		return inquiryDetails;
	}

}
