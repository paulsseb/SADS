package webServices;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.codehaus.jettison.json.JSONObject;

import com.google.gson.Gson;
import models.AuthManager;
import objects.AuthObjects.UserDetailsObj;
import objects.AuthObjects.UserObj;

@Path("/AuthWS")
public class AuthWS {

	// =======For getting the current date=========================
	DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
	Date dateobj = new Date();


	@GET
	@Path("/getLogin")
	@Produces("application/json")
	public JSONObject getLogin(@QueryParam("Username") String Username, @QueryParam("Password") String Password,
			@QueryParam("App") String App) {
		String response = null;
		JSONObject JSONobj = null;

		try {
			System.out.println(df.format(dateobj) + " We are getting user login. Actor:" + Username + "App:" + App);

			UserDetailsObj userDetails = null;
			AuthManager authManager = new AuthManager();
			userDetails = authManager.getLogin(Username, Password, App);

			Gson gson = new Gson();
			response = gson.toJson(userDetails);

			JSONObject json = new JSONObject();
			json.put("jsonResult", response);

			// Remove all slash characters in string
			response = json.toString().replace("\\", "");

			if (response.length() > 10) {
				int m = 15;
				int n = response.length() - 2;

				response = response.substring(0, m - 1) + response.substring(m);
				response = response.substring(0, n - 1) + response.substring(n);
			}

			JSONobj = new JSONObject(response);
		} catch (Exception e) {
			System.out.println(df.format(dateobj) + " Exception Error at AuthWS: getLogin " + e.getMessage());
		}
		return JSONobj;
	}


}
