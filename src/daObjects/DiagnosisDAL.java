package daObjects;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.sun.jersey.core.header.FormDataContentDisposition;
//import com.sun.jersey.multipart.FormDataParam;





import objects.DiagnosisObjects.DiagnosisObj;




public class DiagnosisDAL {

	// =======For getting the current date=========================
			DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
			DateFormat transDtDf = new SimpleDateFormat("dd/MM/yyyy");
			DateFormat currentYear = new SimpleDateFormat("yyyy");
			Date dateobj = new Date();
			DecimalFormat roundoff = new DecimalFormat("0.00");      
			//System.out.println(df.format(value));
			
			/** The path to the folder where we want to store the uploaded files */
			private static final String UPLOAD_FOLDER = "C:/xampp/htdocs/SadsWeb/uploadedFiles/";
			
			public ArrayList<DiagnosisObj> performDiagnosis(Connection connection,
					String user_id, String date, String time, String morningstiff,
					String threeOmoreAjoints, String hanJointInvolved, String symetricA, String rNodules,
					String periodOpresence,String imagename
				   ) throws SQLException, Exception 
			{
				//create a list that will be returned after diagnosis with results
				ArrayList<DiagnosisObj> obj = new ArrayList<DiagnosisObj>();
				    // declare and initiate variables to be used
					double percentage=0;
					double weight=0;
					int count=0;
					int records = 0;
				    CallableStatement stmt;
				    DiagnosisObj diagnosisObj;
				    String result = "Negative";
				    String mornnstiffRemedy = "";
				    String aO3JointsRemedy = "";
				    String handjtInvRemedy = "";
				    String symtrARemedy = "";
				    String raNodulesRemedy = "";
				   
				   
				 
				try
				 {	
						diagnosisObj = new DiagnosisObj();
						//return results
						diagnosisObj.setrNodules(rNodules);
						diagnosisObj.setsymetricA(symetricA);
						diagnosisObj.sethanJointInvolved(hanJointInvolved);
						diagnosisObj.setthreeOmoreAjoints(threeOmoreAjoints);
						diagnosisObj.setmorningstiff(morningstiff);
						diagnosisObj.setperiodOpresence(periodOpresence);
					
						
						// get Remedies for each sign and symptom
						// remedy for morning stiffness
						PreparedStatement pps1 = null;
						pps1 = connection.prepareStatement("SELECT * FROM remedies WHERE remedyId = ?");
						pps1.setInt(1, 1);
						
						ResultSet rs1 = pps1.executeQuery();
						while (rs1.next()) {
							mornnstiffRemedy = rs1.getString("summary");
						}
						
						// remedy for Arthritis of three or more joint areas
						PreparedStatement pps = null;
						pps = connection.prepareStatement("SELECT * FROM remedies WHERE remedyId = ?");
						pps.setInt(1, 2);
						
						ResultSet rs2 = pps.executeQuery();
						while (rs2.next()) {
							aO3JointsRemedy = rs2.getString("summary");
						}
						
						// remedy for Hand joint involvement
						PreparedStatement pps2 = null;
						pps2 = connection.prepareStatement("SELECT * FROM remedies WHERE remedyId = ?");
						pps2.setInt(1, 3);
						
						ResultSet rs3 = pps2.executeQuery();
						while (rs3.next()) {
							handjtInvRemedy = rs3.getString("summary");
						}
						
						// remedy for Symmetric arthritis
						PreparedStatement pps3 = null;
						pps3 = connection.prepareStatement("SELECT * FROM remedies WHERE remedyId = ?");
						pps3.setInt(1, 4);
						
						ResultSet rs4 = pps3.executeQuery();
						while (rs4.next()) {
							symtrARemedy = rs4.getString("summary");
						}
						
						// remedy for Rheumatoid nodules
						PreparedStatement pps4 = null;
						pps4 = connection.prepareStatement("SELECT * FROM remedies WHERE remedyId = ?");
						pps4.setInt(1, 5);
						
						ResultSet rs5 = pps4.executeQuery();
						while (rs5.next()) {
							raNodulesRemedy = rs5.getString("summary");
						}
						
	
						// call python to get some parameters for the diagnosis
						 	
					        System.out.println("getting image");
					        String dir=System.getProperty("user.dir");
					        //System.out.println(dir);
					        String pythonScriptPath = dir+"/tomcat/webapps/SADS/WEB-INF/classes/daObjects/raAlgo.py";
					        String[] cmd = new String[6];
					        cmd[0] = "python"; // check version of installed python: python -V
					        cmd[1] = pythonScriptPath;
					        cmd[2] = "--image";
					        cmd[3] = "C:/xampp/htdocs/SadsWeb/uploadedFiles/"+imagename;
					        cmd[4] = "--userid";
					        cmd[5] =  user_id;
					        System.out.println("Calling Python from java");
					        
					        // create runtime to execute external command
					        Runtime rt = Runtime.getRuntime();
					      
					        Process pr = rt.exec(cmd);
					       // String command= cmd[0]+ " "+cmd[1]+ " "+cmd[2]+ " "+cmd[3]+ " "+cmd[4]+ " "+cmd[5];

					        // retrieve output from python script
					        BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
					        String line = "";
					        while((line = bfr.readLine()) != null) {
					// display each output line form python script
					            System.out.println(line);
					        }
					        System.out.println("Out of python");
				
				
				
						
						
						if (morningstiff.equals("T"))
						{
							count+=1;
							weight+=1.9;
							diagnosisObj.setmornnstiffRemedy(mornnstiffRemedy);
							
						}
						if(threeOmoreAjoints.equals("T"))
						{
							count+=1;
							weight+=1.4;
							diagnosisObj.setaO3JointsRemedy(aO3JointsRemedy);
						}
						if(hanJointInvolved.equals("T"))
						{
							count+=1;
							weight+=1.5;
							diagnosisObj.sethandjtInvRemedy(handjtInvRemedy);
						}
						if(symetricA.equals("T"))
						{
							count+=1;
							weight+=1.2;
							diagnosisObj.setsymtrARemedy(symtrARemedy);
						}
						if(rNodules.equals("T"))
						{
							count+=1;
							weight+=3.0;
							diagnosisObj.setraNodulesRemedy(raNodulesRemedy);
						}
						
						if((count >4 || count==4)&&periodOpresence.equals("T"))
						{
							result="Positive";
						}
						diagnosisObj.setresult(result);
						//formula for diagnosis
						if(weight!=0)
						{   //compute percentage and roundoff to 2 dps
							percentage=(weight/28.4)*100;
							percentage=Double.parseDouble(roundoff.format(percentage));
							
						}else
						{
							percentage=0;
						}
						diagnosisObj.setpercentage(new Double(percentage).toString());
						
						
					   // this is where the computations shall take place and after return result
						// update Db with the results
						String queryOrder = "{call performDiagnosis(?,?,?,?,?,?,?,?,?,?,?)}";
						stmt = connection.prepareCall(queryOrder);
						stmt.setString(1, user_id);
						stmt.setString(2, date);
						stmt.setString(3, time);
						stmt.setString(4, morningstiff);
						stmt.setString(5, threeOmoreAjoints);
						stmt.setString(6, hanJointInvolved);
						stmt.setString(7, symetricA);
						stmt.setString(8, rNodules);
						stmt.setString(9, periodOpresence);
						stmt.setString(10, result);
						stmt.setString(11, new Double(percentage).toString());
						
						records = stmt.executeUpdate();
						if (records > 0)
						{
					
						//DiagnosisObj diagnosisObj = new DiagnosisObj();
						diagnosisObj.setSuccess("Y");
						diagnosisObj.setMsg("Diagnosis Completed Successfully! ");
						obj.add(diagnosisObj);

						} else {
							//DiagnosisObj diagnosisObj = new DiagnosisObj();
						diagnosisObj.setSuccess("N");
						diagnosisObj.setMsg("Record NOT saved");
						obj.add(diagnosisObj);
						}				
			 
			
				 
			} catch (SQLException sqle) {
				System.out.println(df.format(dateobj) + " Error at DiagnosisDAL: performdiagnosis SQLException " + sqle.getMessage());
			} catch (SecurityException se) {
				System.out.println(df.format(dateobj) + "  Error at DiagnosisDAL: performdiagnosis Exception Cannot create destination folder on server" + se.getMessage());
			}/*
				catch (IOException e) {
					System.out.println(df.format(dateobj) + "  Error at DiagnosisDAL: performdiagnosis Exception Cannot find file" + e.getMessage());
			}*/
				catch (Exception e) {
				System.out.println(df.format(dateobj) + "  Error at DiagnosisDAL: performdiagnosis Exception " + e.getMessage());
			}
				finally {
				connection.close();
			}
			return obj;
		}
			
			public ArrayList<DiagnosisObj> getDiagnoses(Connection connection, String result ) throws SQLException, Exception 
			{
				 ArrayList<DiagnosisObj> obj = new ArrayList<DiagnosisObj>();
				 try
				 {
				 
					 String query = "{ call getDiagnoses(?)}";
					 CallableStatement stmt = connection.prepareCall(query);
					 
					
					 stmt.setString(1, result);
					 ResultSet rs = stmt.executeQuery();
						
						while (rs.next()) {
							DiagnosisObj diagnosisObj = new DiagnosisObj();
							diagnosisObj.setdiagnosisId(rs.getString("diagnosisId"));
							diagnosisObj.setuser_id(rs.getString("user_id"));
							diagnosisObj.setresult(rs.getString("result"));
							diagnosisObj.setdate(rs.getString("date"));
							diagnosisObj.settime(rs.getString("time"));
							diagnosisObj.setpatientName(rs.getString("firstName") +" "+rs.getString("lastName"));
							diagnosisObj.setpercentage(rs.getString("percentage"));
							
							obj.add(diagnosisObj);
						}
				 }
				 catch(Exception e)
				 {
					 System.out.println(df.format(dateobj) + " Exception Error at DiagnosisDAL, getDiagnoses " + e.getMessage());
					
				 } finally 
				 {
					connection.close();
				 }
					
				return obj;
			}
			
			public DiagnosisObj getDiagnosisDtls(Connection connection, String diagnosisNO)
					throws SQLException, Exception {
				DiagnosisObj diagnosisObj= new DiagnosisObj();
				try {
					String query = "";
					 CallableStatement stmt = null;
					 ResultSet rs = null;
					query = "{ call getDiagnosisByDiagnosisNO(?)}";
				    stmt = connection.prepareCall(query);
					stmt.setString(1, diagnosisNO);			
					 rs = stmt.executeQuery();	
			      
					while (rs.next()) {
						
						diagnosisObj.setdiagnosisId(rs.getString("diagnosisId"));
						diagnosisObj.setuser_id(rs.getString("user_id"));
						diagnosisObj.setresult(rs.getString("result"));
						diagnosisObj.setdate(rs.getString("date"));
						diagnosisObj.settime(rs.getString("time"));
						
						//set nodules result and chk if remedy is needed
						diagnosisObj.setrNodules(rs.getString("rNodules"));
						if (diagnosisObj.getrNodules().equals("T"))
						{
							// remedy for Rheumatoid nodules
							PreparedStatement pps4 = null;
							pps4 = connection.prepareStatement("SELECT * FROM remedies WHERE remedyId = ?");
							pps4.setInt(1, 5);
							
							ResultSet rs5 = pps4.executeQuery();
							while (rs5.next()) {
						
								diagnosisObj.setraNodulesRemedy(rs5.getString("summary"));
							}
						}
						
						//set symetricA result and chk if remedy is needed
						diagnosisObj.setsymetricA(rs.getString("symetricA"));
						if (diagnosisObj.getsymetricA().equals("T"))
						{
							// remedy for Symmetric arthritis
							PreparedStatement pps3 = null;
							pps3 = connection.prepareStatement("SELECT * FROM remedies WHERE remedyId = ?");
							pps3.setInt(1, 4);
							
							ResultSet rs4 = pps3.executeQuery();
							while (rs4.next()) {
								diagnosisObj.setsymtrARemedy(rs4.getString("summary"));
							}
						}
						
						//set hanJointInvolved result and chk if remedy is needed
						diagnosisObj.sethanJointInvolved(rs.getString("hanJointInvolved"));
						if (diagnosisObj.gethanJointInvolved().equals("T"))
						{
							// remedy for Hand joint involvement
							PreparedStatement pps2 = null;
							pps2 = connection.prepareStatement("SELECT * FROM remedies WHERE remedyId = ?");
							pps2.setInt(1, 3);
							
							ResultSet rs3 = pps2.executeQuery();
							while (rs3.next()) {
								
								diagnosisObj.sethandjtInvRemedy(rs3.getString("summary"));
							}
						}
						
						//set threeOmoreAjoints result and chk if remedy is needed
						diagnosisObj.setthreeOmoreAjoints(rs.getString("threeOmoreAjoints"));
						if (diagnosisObj.getthreeOmoreAjoints().equals("T"))
						{
							// remedy for Arthritis of three or more joint areas
							PreparedStatement pps = null;
							pps = connection.prepareStatement("SELECT * FROM remedies WHERE remedyId = ?");
							pps.setInt(1, 2);
							
							ResultSet rs2 = pps.executeQuery();
							while (rs2.next()) {
							
								diagnosisObj.setaO3JointsRemedy(rs2.getString("summary"));
							}
						}
						
						//set morningstiff result and chk if remedy is needed
						diagnosisObj.setmorningstiff(rs.getString("morningstiff"));
						if (diagnosisObj.getmorningstiff().equals("T"))
						{
							// remedy for morning stiffness
							PreparedStatement pps1 = null;
							pps1 = connection.prepareStatement("SELECT * FROM remedies WHERE remedyId = ?");
							pps1.setInt(1, 1);
							
							ResultSet rs1 = pps1.executeQuery();
							while (rs1.next()) {
								diagnosisObj.setmornnstiffRemedy(rs1.getString("summary"));
							}
							
						}
						diagnosisObj.setpatientName(rs.getString("firstName") +" "+rs.getString("lastName"));
						diagnosisObj.setpercentage(rs.getString("percentage"));
					}
					
					
					
				} catch (Exception e) {
					System.out.println(df.format(dateobj) + " Exception Error at DiagnosisDAL, getdiagnosisDtls " + e.getMessage());
				} finally {
					connection.close();
				}
				return diagnosisObj;
			}

			public ArrayList<DiagnosisObj> getDiagnosesbyUid(Connection connection, String userid ) throws SQLException, Exception 
			{
				 ArrayList<DiagnosisObj> obj = new ArrayList<DiagnosisObj>();
				 try
				 {
				 
					 String query = "{ call getDiagnosesbyUid(?)}";
					 CallableStatement stmt = connection.prepareCall(query);
					 
					
					 stmt.setString(1, userid);
					 ResultSet rs = stmt.executeQuery();
						
						while (rs.next()) {
							DiagnosisObj diagnosisObj = new DiagnosisObj();
							diagnosisObj.setdiagnosisId(rs.getString("diagnosisId"));
							diagnosisObj.setuser_id(rs.getString("user_id"));
							diagnosisObj.setresult(rs.getString("result"));
							diagnosisObj.setdate(rs.getString("date"));
							diagnosisObj.settime(rs.getString("time"));
							diagnosisObj.setpatientName(rs.getString("firstName") +" "+rs.getString("lastName"));
							diagnosisObj.setpercentage(rs.getString("percentage"));
							
							obj.add(diagnosisObj);
						}
				 }
				 catch(Exception e)
				 {
					 System.out.println(df.format(dateobj) + " Exception Error at DiagnosisDAL, getDiagnosesbyUid " + e.getMessage());
					
				 } finally 
				 {
					connection.close();
				 }
					
				return obj;
			}
		
}
