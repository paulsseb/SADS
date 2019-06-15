package objects;

import java.util.ArrayList;


public class AuthObjects {

public static class UserObj {
		private String userid;
		private String email;
		private String firstName;
		private String lastName;
		private String role;
		private String dob;
		private String gender;
		private String phoneNumber;
		private String username;
		private String password;
		

		public String getuserid() {
			return userid;
		}

		public void setuserid(String userid) {
			this.userid = userid;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getfirstName() {
			return firstName;
		}

		public void setfirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getlastName() {
			return lastName;
		}

		public void setlastName(String lastName) {
			this.lastName = lastName;
		}

		public String getrole() {
			return role;
		}

		public void setrole(String role) {
			this.role = role;
		}

		public String getdob() {
			return dob;
		}

		public void setdob(String dob) {
			this.dob = dob;
		}

		public String getgender() {
			return gender;
		}

		public void setgender(String gender) {
			this.gender = gender;
		}

		public String getphoneNumber() {
			return phoneNumber;
		}

		public void setphoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}

		public String getusername() {
			return username;
		}

		public void setusername(String username) {
			this.username = username;
		}

		public String getpassword() {
			return password;
		}

		public void setpassword(String password) {
			this.password = password;
		}

	
	}

public static class UserDetailsObj {

	ArrayList<UserObj> UserList = new ArrayList<UserObj>();

	public ArrayList<UserObj> getUserList() {
		return UserList;
	}

	public void setUserList(ArrayList<UserObj> userList) {
		UserList = userList;
	}

}


}
