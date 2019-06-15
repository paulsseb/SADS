package daObjects;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class dbCon {

	public Connection Get_Conn_1() throws Exception {
		try {
			String connectionURL = "jdbc:mysql://localhost:3306/SADS";
			Connection connection = null;
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(connectionURL, "root", "");
			return connection;
		} catch (SQLException e) {
			System.out.println("Error 1 at dbConnect:Get_Conn_1() " + e.getMessage());
			throw e;
		} catch (Exception e) {
			System.out.println("Error 2 at dbConnect:Get_Conn_1() " + e.getMessage());
			throw e;
		}
	}

	String readFile(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			return sb.toString();
		} finally {
			br.close();
		}
	}
}
