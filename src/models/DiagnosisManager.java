package models;

import java.io.InputStream;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;







import objects.DiagnosisObjects.DiagnosisObj;
import daObjects.DiagnosisDAL;
import daObjects.dbCon;

public class DiagnosisManager {
	
	// =======For getting the current date=========================
			DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
			Date dateobj = new Date();
			
			public ArrayList<DiagnosisObj> performDiagnosis( String user_id, String date, String time, String morningstiff,
					String threeOmoreAjoints, String hanJointInvolved, String symetricA, String rNodules,
					String periodOpresence,String imagename
				   ) throws Exception 
			{
				ArrayList<DiagnosisObj> obj = null;
					
				try {
					dbCon database = new dbCon();
					Connection connection = database.Get_Conn_1();

					DiagnosisDAL diagnosisDAL = new DiagnosisDAL();
					obj = diagnosisDAL.performDiagnosis(connection, user_id, date, time, morningstiff, threeOmoreAjoints, hanJointInvolved, symetricA, rNodules, periodOpresence, imagename);
				} catch (Exception e) {
					System.out.println(df.format(dateobj) + " Exception Error at DiagnosisManager, performDiagnosis " + e.getMessage());
					throw e;
				}
				return obj;
			}
			
public DiagnosisObj getDiagnosisDtls( String diagnosisNO) throws Exception {
			DiagnosisObj obj = null;
				try {
					dbCon database = new dbCon();
					Connection connection = database.Get_Conn_1();

					DiagnosisDAL diagnosisDAL = new DiagnosisDAL();
					obj = diagnosisDAL.getDiagnosisDtls(connection, diagnosisNO);
				} catch (Exception e) {
					System.out.println(df.format(dateobj) + " Exception Error at DiagnosisManager, getDiagnosisDtls " + e.getMessage());
					throw e;
				}
				return obj;
			}
			
			public ArrayList<DiagnosisObj> getDiagnoses( String result ) throws Exception {
				ArrayList<DiagnosisObj> obj = null;
				try {
					dbCon database = new dbCon();
					Connection connection = database.Get_Conn_1();

					DiagnosisDAL diagnosisDAL = new DiagnosisDAL();
					obj = diagnosisDAL.getDiagnoses(connection, result);
				} catch (Exception e) {
					System.out.println(df.format(dateobj) + " Exception Error at DiagnosisManager, getDiagnoses " + e.getMessage());
					throw e;
				}
				return obj;
			}
			
			public ArrayList<DiagnosisObj> getDiagnosesbyUid( String userId ) throws Exception {
				ArrayList<DiagnosisObj> obj = null;
				try {
					dbCon database = new dbCon();
					Connection connection = database.Get_Conn_1();

					DiagnosisDAL diagnosisDAL = new DiagnosisDAL();
					obj = diagnosisDAL.getDiagnosesbyUid(connection, userId);
				} catch (Exception e) {
					System.out.println(df.format(dateobj) + " Exception Error at DiagnosisManager, getDiagnosesbyUid " + e.getMessage());
					throw e;
				}
				return obj;
			}
			

			
}
