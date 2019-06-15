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


import objects.AppointmentObjects.AppointmentObj;




public class AppointmentDAL {

	// =======For getting the current date=========================
			DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
			DateFormat transDtDf = new SimpleDateFormat("dd/MM/yyyy");
			DateFormat currentYear = new SimpleDateFormat("yyyy");
			Date dateobj = new Date();
			
			public ArrayList<AppointmentObj> createAppointment(Connection connection,
					String user_id, String date, String time, String reason
				   ) throws SQLException, Exception 
			{
				ArrayList<AppointmentObj> obj = new ArrayList<AppointmentObj>();
				
					int records = 0;
				    CallableStatement stmt;
				    String status = "PENDING";
				   
				 
				try
				 {				    
					   
						String queryOrder = "{call createAppointment(?,?,?,?,?)}";
						stmt = connection.prepareCall(queryOrder);
						stmt.setString(1, user_id);
						stmt.setString(2, date);
						stmt.setString(3, time);
						stmt.setString(4, reason);
						stmt.setString(5, status);
						
						records = stmt.executeUpdate();
						if (records > 0)
						{
							
							AppointmentObj appointmentObj = new AppointmentObj();
						appointmentObj.setSuccess("Y");
						appointmentObj.setMsg("Appointment Created Successfully! ");
						obj.add(appointmentObj);

						} else {
							AppointmentObj appointmentObj = new AppointmentObj();
						appointmentObj.setSuccess("N");
						appointmentObj.setMsg("Record NOT saved");
						obj.add(appointmentObj);
						}				
			 
			
				 
			} catch (SQLException sqle) {
				System.out.println(df.format(dateobj) + " Error at AppointmentDAL: createAppointment SQLException " + sqle.getMessage());
			} catch (Exception e) {
				System.out.println(df.format(dateobj) + " Error at AppointmentDAL: createAppointment Exception " + e.getMessage());
			} finally {
				connection.close();
			}
			return obj;
		}
			
			public ArrayList<AppointmentObj> getAppointments(Connection connection, String Status ) throws SQLException, Exception 
			{
				 ArrayList<AppointmentObj> obj = new ArrayList<AppointmentObj>();
				 try
				 {
				 
					 String query = "{ call getAppointments(?)}";
					 CallableStatement stmt = connection.prepareCall(query);
					 
					
					 stmt.setString(1, Status);
						ResultSet rs = stmt.executeQuery();
						
						while (rs.next()) {
							AppointmentObj appointmentObj = new AppointmentObj();
							appointmentObj.setappointmentId(rs.getString("appointmentId"));
							appointmentObj.setuser_id(rs.getString("user_id"));
							appointmentObj.setstatus(rs.getString("status"));
							appointmentObj.setdate(rs.getString("date"));
							appointmentObj.settime(rs.getString("time"));
							appointmentObj.setpatientName(rs.getString("firstName") +" "+rs.getString("lastName"));
							appointmentObj.setreason(rs.getString("reason"));
							
							obj.add(appointmentObj);
						}
				 }
				 catch(Exception e)
				 {
					 System.out.println(df.format(dateobj) + " Exception Error at AppointmentDAL, getAppointments " + e.getMessage());
					
				 } finally 
				 {
					connection.close();
				 }
					
				return obj;
			}
			
			public AppointmentObj getAppointmentDtls(Connection connection, String AppointmentNO)
					throws SQLException, Exception {
				 AppointmentObj appointmentObj= new AppointmentObj();
				try {
					String query = "";
					 CallableStatement stmt = null;
					 ResultSet rs = null;
					query = "{ call getAppointmentByAppointmentNO(?)}";
				    stmt = connection.prepareCall(query);
					stmt.setString(1, AppointmentNO);			
					 rs = stmt.executeQuery();	
			      
					while (rs.next()) {
						
						appointmentObj.setappointmentId(rs.getString("appointmentId"));
						appointmentObj.setuser_id(rs.getString("user_id"));
						appointmentObj.setstatus(rs.getString("status"));
						appointmentObj.setdate(rs.getString("date"));
						appointmentObj.setpatientName(rs.getString("firstName") +" "+rs.getString("lastName"));
						appointmentObj.settime(rs.getString("time"));
						appointmentObj.setreason(rs.getString("reason"));
					}
					
					
					
				} catch (Exception e) {
					System.out.println(df.format(dateobj) + " Exception Error at AppointmentDAL, getAppointmentDtls " + e.getMessage());
				} finally {
					connection.close();
				}
				return appointmentObj;
			}
			
			public ArrayList<AppointmentObj> getAppointmentsbyUid(Connection connection, String userId ) throws SQLException, Exception 
			{
				 ArrayList<AppointmentObj> obj = new ArrayList<AppointmentObj>();
				 try
				 {
				 
					 String query = "{ call getAppointmentsbyUid(?)}";
					 CallableStatement stmt = connection.prepareCall(query);
					 
					
					 stmt.setString(1, userId);
						ResultSet rs = stmt.executeQuery();
						
						while (rs.next()) {
							AppointmentObj appointmentObj = new AppointmentObj();
							appointmentObj.setappointmentId(rs.getString("appointmentId"));
							appointmentObj.setuser_id(rs.getString("user_id"));
							appointmentObj.setstatus(rs.getString("status"));
							appointmentObj.setdate(rs.getString("date"));
							appointmentObj.settime(rs.getString("time"));
							appointmentObj.setpatientName(rs.getString("firstName") +" "+rs.getString("lastName"));
							appointmentObj.setreason(rs.getString("reason"));
							
							obj.add(appointmentObj);
						}
				 }
				 catch(Exception e)
				 {
					 System.out.println(df.format(dateobj) + " Exception Error at AppointmentDAL, getAppointmentsbyUid " + e.getMessage());
					
				 } finally 
				 {
					connection.close();
				 }
					
				return obj;
			}
			
}
