package webServices;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.codehaus.jettison.json.JSONObject;

import models.DiagnosisManager;
import objects.DiagnosisObjects.DiagnosisObj;
import objects.DiagnosisObjects.ImageObj;
import daObjects.Utilities;

import com.google.gson.Gson;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/DiagnosisWS")
public class DiagnosisWS {

	// =======For getting the current date=========================
				DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
				Date dateobj = new Date();
				ImageObj imageObj;
				

	@GET
	@Path("/performDiagnosis")
	@Produces("application/json")
	public JSONObject performDiagnosis(@QueryParam("user_id") String user_id,
	 @QueryParam("date") String date, @QueryParam("time") String time,@QueryParam("morningstiff") String morningstiff,
	 @QueryParam("threeOmoreAjoints") String threeOmoreAjoints, @QueryParam("hanJointInvolved") String hanJointInvolved,@QueryParam("symetricA") String symetricA,
	 @QueryParam("rNodules") String rNodules, @QueryParam("periodOpresence") String periodOpresence,@QueryParam("imagename") String imagename) {
		String response = null;
		JSONObject JSONobj = null;
		try {
			System.out.println(df.format(dateobj) + " A Diagnosis is being performed by  " + user_id);

			ArrayList<DiagnosisObj> hotelData = null;
			DiagnosisManager diagnosisManager = new DiagnosisManager();
			hotelData = diagnosisManager.performDiagnosis(user_id, date, time, morningstiff, threeOmoreAjoints, hanJointInvolved, symetricA, rNodules, periodOpresence,imagename);

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
			System.out.println(df.format(dateobj) + " Exception Error at DiagnosisWS: performDiagnosis " + e.getMessage());
		}
		return JSONobj;
	}
	@GET
	@Path("/getDiagnoses")
	@Produces("application/json")
	public JSONObject getDiagnoses(@QueryParam("result") String result) {
		String response = null;
		JSONObject JSONobj = null;
		try {
			System.out.println(df.format(dateobj) + " We are getting Diagnoses which were " + result);

			ArrayList<DiagnosisObj> hotelData = null;
			DiagnosisManager diagnosisManager = new DiagnosisManager();
			hotelData = diagnosisManager.getDiagnoses(result);

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
			System.out.println(df.format(dateobj) + " Exception Error at DiagnosisWS: getDiagnoses " + e.getMessage());
		}
		return JSONobj;
	}
	
	@GET
	@Path("/getDiagnosisDtls")
	@Produces("application/json")
	 public JSONObject getDiagnosisDtls(@QueryParam("diagnosisNO") String diagnosisNO)
    {	
	     String response = null;
	     JSONObject JSONobj = null;
	
	    try
	    {
		   System.out.println(df.format(dateobj)+" We are getting Diagnosis Details for Diagnosis No...."+diagnosisNO);
		
		   DiagnosisObj hotelData = null;
		   DiagnosisManager diagnosisManager = new DiagnosisManager();
			hotelData = diagnosisManager.getDiagnosisDtls(diagnosisNO);
		
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
		System.out.println(df.format(dateobj)+" Exception Error at DiagnosisWS:  getDiagnosisDtls. " + e.getMessage());
	}
	return JSONobj;
}
	
	@GET
	@Path("/getDiagnosesbyUid")
	@Produces("application/json")
	public JSONObject getDiagnosesbyUid(@QueryParam("userId") String userId) {
		String response = null;
		JSONObject JSONobj = null;
		try {
			System.out.println(df.format(dateobj) + " We are getting Diagnoses by user ID which were " + userId);

			ArrayList<DiagnosisObj> hotelData = null;
			DiagnosisManager diagnosisManager = new DiagnosisManager();
			hotelData = diagnosisManager.getDiagnosesbyUid(userId);

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
			System.out.println(df.format(dateobj) + " Exception Error at DiagnosisWS: getDiagnoses " + e.getMessage());
		}
		return JSONobj;
	}
	

}
