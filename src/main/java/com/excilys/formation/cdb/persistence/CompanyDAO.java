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
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.formation.cdb.mappers.CompanyMapper;
import com.excilys.formation.cdb.mappers.ComputerMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;

/**
 * @author excilys
 *
 */
@Repository
public class CompanyDAO {

	static final Logger LOGGER  = LogManager.getLogger(CompanyDAO.class);
	@Autowired
	ComputerDAO computerDAO;
	@Autowired
	CompanyMapper companyMapper;

	/*
	 * Queries q-
	 */
	private final String qlistCompanies = "SELECT " + Column.CCID.getName() + " , " + Column.CCNAME.getName() + " FROM company";
	private final String qgetCompanyId = "SELECT " + Column.CCID.getName() + ", " + Column.CCNAME.getName() + "  FROM company WHERE id = ? ;";
	private final String qdeleteComputer = "DELETE FROM company WHERE " + Column.CCID.getName() + " = ? ;";

	
	
	JdbcTemplate jdbcTemplate;

	@Autowired
	public CompanyDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * instance, l'instance de ComputerDAO pour appliquer le pattern Singleton.
	 */
	//private static CompanyDAO instance;

	/**
	 * Méthode permettant de récupérer l'instance du Singleton.
	 * @return l'instance
	 */
	/*public static CompanyDAO getInstance() {

		if (instance == null) {
			instance = new CompanyDAO();
		}

		return instance;
	}

	private CompanyDAO() {

	}*/

	/**
	 * Récupère un fabriquant à partir de son identifiant.
	 * @param id, l'identifiant du fabriquant à récupérer.
	 * @return le fabriquant récupéré
	 */
	/*public Optional<Company> getByID(int id) {

		Company company = null;
		ResultSet results = null;

		try (Connection connection = Connexion.getInstance(); 
				PreparedStatement pstmt = connection.prepareStatement(qgetCompanyId)) {

			pstmt.setInt(1, id);
			results = pstmt.executeQuery();

			if (results.next()) {
				company = companyMapper.map(results);
			}

			results.close();

		} catch (SQLException e) {
			LOGGER.error("Exception SQL à l'exécution de la requête : " + e.getMessage());
		} 

		return Optional.ofNullable(company);

	}*/

	/**
	 * Récupère la liste des fabricants.
	 * @return la liste des fabricants.
	 */
	public List<Company> getList() {
		List<Company> listCompany = new ArrayList<>();

		listCompany = getJdbcTemplate().query(qlistCompanies, new CompanyMapper());

		return listCompany;
		
	}

/*	public void delete(int id) {
		Connection connection = null;

		boolean success = false;
		try {
			connection = Connexion.getInstance();
			PreparedStatement pstmt = connection.prepareStatement(qdeleteComputer);
			connection.setAutoCommit(false);



			success = computerDAO.deletecomputers(connection, id);

			pstmt.setInt(1, id);

			pstmt.executeUpdate();	


		} catch (SQLException e) {
			LOGGER.error("Exception SQL pendant la routine de suppression : " + e.getMessage());
			success = false;
		}

		if (success) {
			try {
				connection.commit();
			} catch (SQLException e) {
				LOGGER.error("Exception SQL au commit : " + e.getMessage());
			}
		} else {
			try {
				connection.rollback();
			} catch (SQLException e) {
				LOGGER.error("Exception SQL au rollback : " + e.getMessage());
			}
		}
		try {
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			LOGGER.error("Exception SQL à la remise en autocommit : " + e.getMessage());
		}
	}*/
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}


}