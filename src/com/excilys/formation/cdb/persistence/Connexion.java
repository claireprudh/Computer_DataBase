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
	
	/**
	 * La connexion à la base de données.
	 */
	private Connection connection;
	
	/**
	 * instance, l'instance de Connexion pour appliquer le pattern Singleton.
	 */
	private static Connexion instance;
	
	/**
	 * Méthode permettant de récupérer l'instance du Singleton
	 * @return l'instance
	 */
	public static Connexion getInstance() {
		
		if (instance == null) {
			instance = new Connexion();
		}
				
		return instance;
	}
	
	/**
	 * Constructeur.
	 */
	private Connexion() {
	
	}
	
	/**
	 * Implements connection with the database.
	 */	
	public void open() {
		try {
					
			String url = "jdbc:mysql://127.0.0.1:3306/computer-database-db?useSSL=false";
			
			this.connection = DriverManager.getConnection(url, "admincdb", "qwerty1234");
		
		
		} catch (SQLException e) {
			
			e.printStackTrace();
			System.exit(1);
		}
	}
	
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
	
	/**
	 * Donne l'accès à la base à l'appelant.
	 * @return la connexion à la base.
	 */
	public Connection getConnection() {
		return this.connection;
	}

}
