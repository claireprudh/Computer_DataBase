/**
 * 
 */
package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;




/**
 * @author excilys
 *
 */
public class Connexion {
	
	private Connection connection;
	private static Connexion instance;
	
	public static Connexion getInstance() {
		
		Connexion c;
		
		if (instance == null) {
			c = new Connexion();
		}
		else {
			c = instance;
		}
		
		return c;
	}
	
	/**
	 * Implements connection with the database.
	 */
	private Connexion() {
	
	}
		
	public void open() {
	try {
				
		String url = "jdbc:mysql://127.0.0.1:3306/computer-database-db?useSSL=false";
		
		this.connection = DriverManager.getConnection(url, "admincdb", "qwerty1234");
	
	
	} catch (SQLException e) {
		
		e.printStackTrace();
		System.exit(1);
	}
	}
	
	
	
//	/**
//	 * Gets the data identified by attribut.
//	 * @param attribut
//	 * @throws SQLException 
//	 */
//	public ResultSet getData(String... attribut ) throws SQLException {
//
//		String myRequest = "SELECT * FROM computer;";
//
//		
//		Statement stmt;
//		//PreparedStatement pstmt;
//		
//			stmt = connection.createStatement();
//			//pstmt = conn.prepareStatement(myRequest);
//			
//			
//			ResultSet results = stmt.executeQuery(myRequest);
//			//ResultSet results2 = pstmt.executeQuery();
//		
//			
//		
//			return results;
//		
//		
//		
//	}
	
	/**
	 * Closes connexion with the database
	 */
	public void close() {
		try {
			this.connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public Connection getConnection() {
		return this.connection;
	}

}
