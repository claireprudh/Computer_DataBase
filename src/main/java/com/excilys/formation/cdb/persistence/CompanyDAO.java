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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.persistence.mappers.CompanyMapper;

/**
 * @author excilys
 *
 */
public class CompanyDAO {

	static final Logger LOGGER  = LogManager.getLogger(ComputerDAO.class);
	
	/*
	 * Columns c-
	 */
	private final String cname = "name";
	
	/*
	 * Queries q-
	 */
	private final String qlistCompanies = "SELECT " + cname + " FROM company";
	private final String qgetCompanyId = "SELECT id, " + cname + "  FROM company WHERE id = ? ;";
	
	
	/**
	 * instance, l'instance de ComputerDAO pour appliquer le pattern Singleton.
	 */
	private static CompanyDAO instance;
	
	/**
	 * Méthode permettant de récupérer l'instance du Singleton.
	 * @return l'instance
	 */
	public static CompanyDAO getInstance() {
		
		if (instance == null) {
			instance = new CompanyDAO();
		}
		
		return instance;
	}
	
	private CompanyDAO() {
		
	}
	
	/**
	 * Récupère un fabriquant à partir de son identifiant.
	 * @param id, l'identifiant du fabriquant à récupérer.
	 * @return le fabriquant récupéré
	 */
	public Optional<Company> getByID(int id) {

		Company company = null;
		
		try (Connection connection = Connexion.getInstance()) {
			PreparedStatement pstmt = connection.prepareStatement(qgetCompanyId);
			pstmt.setInt(1, id);
			ResultSet results = pstmt.executeQuery();
			
			if (results.next()) {
				company = CompanyMapper.getInstance().map(results);
			}
		
			results.close();
		} catch (SQLException e) {
			LOGGER.error("Exception SQL à l'exécution de la requête : " + e.getMessage());
		}
		
		return Optional.ofNullable(company);
		
	}
	
	/**
	 * Récupère la liste des fabricants.
	 * @return la liste des fabricants.
	 */
	public List<String> getList() {
		
		List<String> listCompanies = new ArrayList<String>();
		
	
		try (Connection connection = Connexion.getInstance()) {
			Statement stmt = connection.createStatement();
			ResultSet results = stmt.executeQuery(qlistCompanies);
			
			while (results.next()) {
				listCompanies.add(results.getString(cname));				
										
			}
			
			results.close();
			
		} catch (SQLException e) {
			LOGGER.error("Exception SQL à l'exécution de la requête : " + e.getMessage());
		}
		
		return listCompanies;
	}
	

}
