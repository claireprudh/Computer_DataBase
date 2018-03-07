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
import com.excilys.formation.cdb.mappers.CompanyMapper;

/**
 * @author excilys
 *
 */
public class CompanyDAO {

	static final Logger LOGGER  = LogManager.getLogger(CompanyDAO.class);
	
	
	/*
	 * Queries q-
	 */
	private final String qlistCompanies = "SELECT " + Column.CCID.getName() + " , " + Column.CCNAME.getName() + " FROM company";
	private final String qgetCompanyId = "SELECT " + Column.CCID.getName() + ", " + Column.CCNAME.getName() + "  FROM company WHERE id = ? ;";
	
	
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
		ResultSet results = null;
		
		try (Connection connection = Connexion.getInstance()) {
			PreparedStatement pstmt = connection.prepareStatement(qgetCompanyId);
			pstmt.setInt(1, id);
			results = pstmt.executeQuery();
			
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
	public List<Company> getList() {
		
		List<Company> listCompanies = new ArrayList<Company>();
		
	
		try (Connection connection = Connexion.getInstance()) {
			Statement stmt = connection.createStatement();
			ResultSet results = stmt.executeQuery(qlistCompanies);
			
			while (results.next()) {
				listCompanies.add(new Company(results.getInt(Column.CCID.getName()), results.getString(Column.CCNAME.getName())));				
										
			}
			
			results.close();
			
		} catch (SQLException e) {
			LOGGER.error("Exception SQL à l'exécution de la requête : " + e.getMessage());
		}
		
		return listCompanies;
	}
	

}
