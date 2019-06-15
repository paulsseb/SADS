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

import models.AppointmentManager;
import objects.AppointmentObjects.AppointmentObj;

import com.google.gson.Gson;

@Path("/AppointmentWS")
public class AppointmentWS {

	// =======For getting the current date=========================
				DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
				Date dateobj = new Date();
				

	@GET
	@Path("/createAppointment")
	@Produces("application/json")
	public JSONObject createAppointment(@QueryParam("user_id") String user_id,
	 @QueryParam("date") String date, @QueryParam("time") String time,@QueryParam("reason") String reason) {
		String response = null;
		JSONObject JSONobj = null;
		try {
			System.out.println(df.format(dateobj) + " We are creating an Appointment " + user_id);

			ArrayList<AppointmentObj> hotelData = null;
			AppointmentManager appointmentManager = new AppointmentManager();
			hotelData = appointmentManager.createAppointment(user_id, date, time, reason);

			Gson gson = new Gson();
			response = gson.toJson(hotelData);

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
			System.out.println(df.format(dateobj) + " Exception Error at AppointmentWS: createAppointment " + e.getMessage());
		}
		return JSONobj;
	}
	@GET
	@Path("/getAppointments")
	@Produces("application/json")
	public JSONObject getAppointments(@QueryParam("Status") String Status) {
		String response = null;
		JSONObject JSONobj = null;
		try {
			System.out.println(df.format(dateobj) + " We are getting Appointments " + Status);

			ArrayList<AppointmentObj> hotelData = null;
			AppointmentManager appointmentManager = new AppointmentManager();
			hotelData = appointmentManager.getAppointments(Status);

			Gson gson = new Gson();
			response = gson.toJson(hotelData);

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
			System.out.println(df.format(dateobj) + " Exception Error at AppointmentWS: getAppointments " + e.getMessage());
		}
		return JSONobj;
	}
	
	@GET
	@Path("/getAppointmentDtls")
	@Produces("application/json")
	 public JSONObject getAppointmentDtls(@QueryParam("AppointmentNO") String AppointmentNO)
    {	
	     String response = null;
	     JSONObject JSONobj = null;
	
	    try
	    {
		   System.out.println(df.format(dateobj)+" We are getting Appointment by Appointment Number...."+AppointmentNO);
		
		   AppointmentObj hotelData = null;
			AppointmentManager appointmentManager = new AppointmentManager();
			hotelData = appointmentManager.getAppointmentDtls(AppointmentNO);
		
		    Gson gson = new Gson();
		    response = gson.toJson(hotelData);
		
		    JSONObject json = new JSONObject();
		    json.put("jsonResult", response);
		
		//Remove all slash characters in string 
		response = json.toString().replace("\\", "");
		
		if(response.length() > 10)
		{
			int m = 15;
			int n = response.length()-2;
			
			response = response.substring(0, m-1)+response.substring(m);
			response = response.substring(0, n-1)+response.substring(n);
		}
		
		JSONobj = new JSONObject(response);
	}	
	catch (Exception e)
	{
		System.out.println(df.format(dateobj)+" Exception Error at AppointmentWS:  by Appointment Number. " + e.getMessage());
	}
	return JSONobj;
}
	
	@GET
	@Path("/getAppointmentsbyUid")
	@Produces("application/json")
	public JSONObject getAppointmentsbyUid(@QueryParam("userId") String userId) {
		String response = null;
		JSONObject JSONobj = null;
		try {
			System.out.println(df.format(dateobj) + " We are getting Appointments by UserId " + userId);

			ArrayList<AppointmentObj> hotelData = null;
			AppointmentManager appointmentManager = new AppointmentManager();
			hotelData = appointmentManager.getAppointmentsbyUid(userId);

			Gson gson = new Gson();
			response = gson.toJson(hotelData);

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
			System.out.println(df.format(dateobj) + " Exception Error at AppointmentWS: getAppointments " + e.getMessage());
		}
		return JSONobj;
	}

}
