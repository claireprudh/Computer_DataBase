/**
 * 
 */
package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariProxyConnection;




/**
 * @author excilys
 *
 */
public class Connexion {

	static final Logger LOGGER = LogManager.getLogger(Connexion.class);

	private static final  HikariConfig CONFIG = new HikariConfig("/hikari.properties");
	
	private static final  HikariDataSource DS = new HikariDataSource(CONFIG);
	
	private static HikariProxyConnection connection;


	/**
	 * Méthode permettant de récupérer l'instance du Singleton.
	 * @return l'instance
	 */
	public static Connection getInstance() {

		
		try {
			if (connection == null || connection.isClosed()) {
				

				connection = (HikariProxyConnection) DS.getConnection();

			}
		} catch (SQLException e) {
			LOGGER.error("Exception SQL à l\'ouverture de la session : " + e.getMessage());
			
		}

		return connection;
	}
	
	private Connexion() {
		
	}


}
