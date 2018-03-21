/**
 * 
 */
package com.excilys.formation.cdb.persistence;


import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.formation.cdb.mappers.CompanyMapper;
import com.excilys.formation.cdb.model.Company;

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
	
	JdbcTemplate jdbcTemplate;

	@Autowired
	public CompanyDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * Récupère un fabriquant à partir de son identifiant.
	 * @param id, l'identifiant du fabriquant à récupérer.
	 * @return le fabriquant récupéré
	 */
	public Optional<Company> getByID(int id) {

		Company company = null;
		
		company = getJdbcTemplate().queryForObject(qgetCompanyId, new Object[] {id}, new CompanyMapper());

		return Optional.ofNullable(company);

	}

	/**
	 * Récupère la liste des fabricants.
	 * @return la liste des fabricants.
	 */
	public List<Company> getList() {
		List<Company> listCompany;

		listCompany = getJdbcTemplate().query(qlistCompanies, new CompanyMapper());

		return listCompany;
		
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}


}