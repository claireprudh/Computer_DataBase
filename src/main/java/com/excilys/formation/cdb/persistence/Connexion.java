/**
 * 
 */
package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;




/**
 * @author excilys
 *
 */
public class Connexion {

	static final Logger LOGGER = LogManager.getLogger(Connexion.class);

	/**
	 * La connexion à la base de données.
	 */
	private static Connection connexion;



	/**
	 * Méthode permettant de récupérer l'instance du Singleton.
	 * @return l'instance
	 */
	public static Connection getInstance() {



		try {
			if (connexion == null || connexion.isClosed()) {
				
				ResourceBundle bundle = ResourceBundle.getBundle("config");
				String login = bundle.getString("login");
				String password = bundle.getString("password");
				
				String url = bundle.getString("url");

				connexion = DriverManager.getConnection(url, login, password);

			}
		} catch (SQLException e) {
			LOGGER.error("Exception SQL à l\'ouverture de la session");
			
		}




		return connexion;
	}
	
	private Connexion() {
		
	}


}
