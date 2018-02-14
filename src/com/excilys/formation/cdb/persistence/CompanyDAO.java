/**
 * 
 */
package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.formation.cdb.model.Company;

/**
 * @author excilys
 *
 */
public class CompanyDAO {

	
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
		
		//Ouverture de la connexion
		Connexion conn = Connexion.getInstance();
		conn.open();
		Connection c = conn.getConnection();
		
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT name FROM company WHERE id = " + id + ";");
			
			//Parcours des résultats de la requête
			if(rs.next()) {
				company = new Company();
				company.setId(id);
				company.setName(rs.getString("name"));
			}
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Fermeture de la connexion
		conn.close();
		
		return Optional.ofNullable(company);
		
	}
	
	/**
	 * Récupère la liste des fabriquants.
	 * @return la liste des fabriquants.
	 */
	public List<Company> getListCompanies(){
		
		List<Company> lp = new ArrayList<Company>();
		
		//Ouverture de la connexion
		Connexion conn = Connexion.getInstance();
		conn.open();
		Connection c = conn.getConnection();
		
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, name FROM company");
			
			//Parcours des résultats
			while(rs.next()) {
				Company company = new Company();
				company.setId(rs.getInt("id"));
				company.setName(rs.getString("name"));				
				
				lp.add(company);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Fermeture de la connexion
		conn.close();
		
		return lp;
	}
	

}
