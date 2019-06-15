package models;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import objects.AppointmentObjects.AppointmentObj;
import daObjects.AppointmentDAL;
import daObjects.dbCon;

public class AppointmentManager {
	
	// =======For getting the current date=========================
			DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
			Date dateobj = new Date();
			
			public ArrayList<AppointmentObj> createAppointment( String user_id, String date, String time, String reason) throws Exception 
			{
				ArrayList<AppointmentObj> obj = null;
					
				try {
					dbCon database = new dbCon();
					Connection connection = database.Get_Conn_1();

					AppointmentDAL appointmentDAL = new AppointmentDAL();
					obj = appointmentDAL.createAppointment(connection, user_id, date, time, reason);
				} catch (Exception e) {
					System.out.println(df.format(dateobj) + " Exception Error at AppointmentManager, createAppointment " + e.getMessage());
					throw e;
				}
				return obj;
			}
			
public AppointmentObj getAppointmentDtls(String AppointmentNO) throws Exception {
			AppointmentObj obj = null;
				try {
					dbCon database = new dbCon();
					Connection connection = database.Get_Conn_1();

					AppointmentDAL appointmentDAL = new AppointmentDAL();
					obj = appointmentDAL.getAppointmentDtls(connection, AppointmentNO);
				} catch (Exception e) {
					System.out.println(df.format(dateobj) + " Exception Error at AppointmentManager, getAppointmentDtls " + e.getMessage());
					throw e;
				}
				return obj;
			}
			
			public ArrayList<AppointmentObj> getAppointments(String Status  ) throws Exception {
				ArrayList<AppointmentObj> obj = null;
				try {
					dbCon database = new dbCon();
					Connection connection = database.Get_Conn_1();

					AppointmentDAL appointmentDAL = new AppointmentDAL();
					obj = appointmentDAL.getAppointments(connection, Status);
				} catch (Exception e) {
					System.out.println(df.format(dateobj) + " Exception Error at AppointmentManager, getAppointments " + e.getMessage());
					throw e;
				}
				return obj;
			}
			
			public ArrayList<AppointmentObj> getAppointmentsbyUid(String userId  ) throws Exception {
				ArrayList<AppointmentObj> obj = null;
				try {
					dbCon database = new dbCon();
					Connection connection = database.Get_Conn_1();

					AppointmentDAL appointmentDAL = new AppointmentDAL();
					obj = appointmentDAL.getAppointmentsbyUid(connection, userId);
				} catch (Exception e) {
					System.out.println(df.format(dateobj) + " Exception Error at AppointmentManager, getAppointmentsbyUid " + e.getMessage());
					throw e;
				}
				return obj;
			}
			

}
