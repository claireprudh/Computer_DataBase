/**
 * 
 */
package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.excilys.formation.cdb.model.Company;

/**
 * @author excilys
 *
 */
public class CompanyDAO {

	final static Logger logger  = Logger.getLogger(ComputerDAO.class);
	
	/**
	 * Columns c-
	 */
	private String cName = "name";
	
	/**
	 * Queries q-
	 */
	private String qlistCompanies = "SELECT name FROM company";
	private String qgetCompanyId = "SELECT name FROM company WHERE id = ? ;";
	
	
	/**
	 * instance, l'instance de ComputerDAO pour appliquer le pattern Singleton.
	 */
	private static CompanyDAO instance;
	
	/**
	 * Méthode permettant de récupérer l'instance du Singleton
	 * @return l'instance
	 */
	public static CompanyDAO getInstance() {
		
		if (instance == null) {
			instance = new CompanyDAO();
		}
		
		return instance;
	}
	
	/**
	 * Récupère un fabriquant à partir de son identifiant.
	 * @param id, l'identifiant du fabriquant à récupérer.
	 * @return le fabriquant récupéré
	 */
	public Optional<Company> getCompanyByID(int id) {
		Company company = null;
		

		
		try(Connection connection = Connexion.getInstance()) {
			PreparedStatement pstmt = connection.prepareStatement(qgetCompanyId);
			pstmt.setInt(1, id);
			ResultSet results = pstmt.executeQuery();
			
			//Parcours des résultats de la requête
			if(results.next()) {
				company = new Company();
				company.setId(id);
				company.setName(results.getString(cName));
			}
		
			results.close();
		}
		catch (SQLException e) {
			logger.error("Exception SQL à l'exécution de la requête : " + e.getMessage());
		}
		
		return Optional.ofNullable(company);
		
	}
	
	/**
	 * Récupère la liste des fabriquants.
	 * @return la liste des fabriquants.
	 */
	public List<String> getListCompanies(){
		
		List<String> listCompanies = new ArrayList<String>();
		
	
		try(Connection connection = Connexion.getInstance()) {
			Statement stmt = connection.createStatement();
			ResultSet results = stmt.executeQuery(qlistCompanies);
			
			//Parcours des résultats
			while(results.next()) {
				listCompanies.add(results.getString(cName));				
										
			}
			
			results.close();
			
		} catch (SQLException e) {
			logger.error("Exception SQL à l'exécution de la requête : " + e.getMessage());
		}
		
		return listCompanies;
	}
	

}
