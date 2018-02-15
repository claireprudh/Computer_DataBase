/**
 * 
 */
package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;




/**
 * @author excilys
 *
 */
public class Connexion {

	final static Logger logger = Logger.getLogger(Connexion.class);

	/**
	 * La connexion à la base de données.
	 */
	private static Connection connexion;



	/**
	 * Méthode permettant de récupérer l'instance du Singleton
	 * @return l'instance
	 */
	public static Connection getInstance() {



		try {
			if (connexion == null || connexion.isClosed()) {

				String url = "jdbc:mysql://127.0.0.1:3306/computer-database-db?useSSL=false";

				connexion = DriverManager.getConnection(url, "admincdb", "qwerty1234");

			}
		} catch (SQLException e) {
			logger.error("Exception SQL à l\'ouverture de la session");
			System.exit(1);
		}




		return connexion;
	}


}
