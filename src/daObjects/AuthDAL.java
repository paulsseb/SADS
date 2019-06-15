package daObjects;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import objects.AuthObjects.UserObj;
import objects.AuthObjects.UserDetailsObj;


public class AuthDAL {

	// =======For getting the current date==========================
	DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	Date dateobj = new Date();

	DateFormat transdt = new SimpleDateFormat("dd/MM/yyyy");
	Date transdtobj = new Date();

	DateFormat tf = new SimpleDateFormat("HH:mm:ss");
	Date timeobj = new Date();

public UserDetailsObj getLogin(Connection connection, String Username, String Password, String App)
			throws SQLException, Exception {
		// TODO Auto-generated method stub
		UserDetailsObj userData = new UserDetailsObj();
		ArrayList<UserObj> UserList = new ArrayList<UserObj>();
		
	
		try {
			String encPassword = Utilities.MD5(Password);

			String query = "{ call getLogin(?,?)}";
			CallableStatement stmt = connection.prepareCall(query);

			stmt.setString(1, Username);
			stmt.setString(2, encPassword);

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {

				UserObj userObj = new UserObj();
				userObj.setuserid(rs.getString("userId"));
				userObj.setfirstName(rs.getString("firstName"));
				userObj.setlastName(rs.getString("lastName"));
				userObj.setrole(rs.getString("role"));
				userObj.setdob(rs.getString("dob"));
				userObj.setgender(rs.getString("gender"));
				userObj.setphoneNumber("phoneNumber");
				userObj.setEmail("email");
				userObj.setusername(rs.getString("username"));
				userObj.setpassword(rs.getString("password"));
				UserList.add(userObj);
				

		
			}
			
		

		} catch (Exception e) {
			System.out.println(df.format(dateobj) + " Exception Error at AuthDAL, getLogin " + e.getMessage());
		} finally {
			connection.close();
		}
		userData.setUserList(UserList);
		return userData;
	}
}
