/**
 * 
 */
package com.excilys.formation.cdb.persistence;


import java.sql.Date;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.formation.cdb.mappers.ComputerMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;

/**
 * @author excilys
 *
 */
@Repository
public class ComputerDAO {

	static final Logger LOGGER = LogManager.getLogger(ComputerDAO.class);

	@Autowired
	ComputerMapper computerMapper;

	@Autowired
	RequestCreator rqc;
	
	JdbcTemplate jdbcTemplate;

	@Autowired
	public ComputerDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	/*
	 * Columns c-
	 */
	private static final String cid = Column.CID.getName();
	private static final String cname = Column.CNAME.getName();
	private static final String cdateOfIntro = Column.CDATE_OF_INTRO.getName();
	private static final String cdateOfDisc = Column.CDATE_OF_DISC.getName();
	private static final String ccompanyID = Column.CCOMPANY_ID.getName();
	private static final String ccount = Column.CCOUNT.getName();

	/*
	 * Queries q-
	 */
	private final String allComputer = rqc.select(
			Column.CID, 
			Column.CNAME, 
			Column.CDATE_OF_INTRO, 
			Column.CDATE_OF_DISC,
			Column.CCOMPANY_ID,
			Column.CCNAME);

	private final String qlistComputers = allComputer
			+ "LEFT JOIN company ON " + ccompanyID + " = " + Column.CCID.getName() + ";";
	private final String qgetComputerById = allComputer
			+ "LEFT JOIN company ON " + ccompanyID + " = " + Column.CCID.getName() + " "
			+ "WHERE " + cid + " = ?;";
	private final String qcreateNewComputer = "INSERT INTO computer (" + cname + ", " + cdateOfIntro + ", " + cdateOfDisc + ", " + ccompanyID + ")"
			+ "  VALUES (?, ?, ?, ?)";
	private final String qupdateComputer = "UPDATE computer SET " 
			+ cname + " = ? , " 
			+ cdateOfIntro + " = ? , " 
			+ cdateOfDisc + " = ? , " 
			+ ccompanyID + " = ? "
			+ "WHERE " + cid + " = ? ;";
	private final String qdeleteComputer = "DELETE FROM computer WHERE " + cid + " = ? ;";
	private final String qgetPageOfComputers = allComputer
			+ "LEFT JOIN company ON " + ccompanyID + " = " + Column.CCID.getName() + " "
			+ "LIMIT ? , ? ;";
	private static final String qgetCount = "SELECT " + ccount + " FROM computer ;";
	private final String qgetSearchCount = rqc.select(
			Column.CCOUNT)
			+ "LEFT JOIN company ON " + ccompanyID + " = " + Column.CCID.getName() + " "
			+ "WHERE " + cname + " LIKE ? or " + Column.CCNAME.getName() + " LIKE ? ;";
	private final String qsearchByName = allComputer
			+ "LEFT JOIN company ON " + ccompanyID + " = " + Column.CCID.getName() + " "
			+ "WHERE " + cname + " LIKE ? or " + Column.CCNAME.getName() + " LIKE ? "
			+ "LIMIT ? , ? ;";
	

	/**
	 * Retourne la liste complète des ordinateurs.
	 * @return la liste des ordinateurs.
	 */
	public List<Computer> getList() {

		List<Computer> listComputers = new ArrayList<>();

		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(qlistComputers);
		computerMapper.map(rows);

		return listComputers;
	}

	/**
	 * Recherche en base le Computer ayant l'ID indiqué.
	 * @param id, l'id du Computer recherché.
	 * @return computer, le Computer à l'id recherché ou null 
	 */
	public Optional<Computer> getByID(int id) {

		Computer computer = getJdbcTemplate().queryForObject(
				qgetComputerById, new Object[] {id}, new ComputerMapper());

		return Optional.ofNullable(computer);
	}

	/**
	 * Création d'un ordinateur en base.
	 * @param computer, l'ordinateur à insérer dans la base.
	 */
	public void create(Computer computer) {

		List<Object> attributes = new ArrayList<>();

			if (computer.getName() != null) {
				attributes.add(computer.getName());
			} else {
				attributes.add(null);
			}

			Optional<LocalDate> date = computer.getDateOfIntro();
			if (date.isPresent()) {
				attributes.add(Date.valueOf(date.get()));
			} else {
				attributes.add(null);
			}

			date = computer.getDateOfDisc();
			if (date.isPresent()) {
				attributes.add(Date.valueOf(date.get()));
			} else {
				attributes.add(null);
			}

			Optional<Company> company = computer.getCompany();
			if (company.isPresent() && company.get().getId() != 0) {
				attributes.add(company.orElse(new Company()).getId());
			} else {
				attributes.add(Types.INTEGER);
			}

			getJdbcTemplate().update(qcreateNewComputer, attributes.toArray());

	}


	/**
	 * Modification d'un Computer.
	 * @param ucomputer, the updated computer
	 */
	public void update(Computer ucomputer) {

		if (this.getByID(ucomputer.getId()).isPresent()) { 

			List<Object> attributes = new ArrayList<>();

			attributes.add(ucomputer.getName());

				Optional<LocalDate> date = ucomputer.getDateOfIntro();
				if (date.isPresent()) {
					attributes.add(Date.valueOf(date.get()));	
				} else {
					attributes.add(null);
				}
				date = ucomputer.getDateOfDisc();
				if (date.isPresent()) {
					attributes.add(Date.valueOf(date.get()));	
				} else {
					attributes.add(null);
				}

				Optional<Company> company = ucomputer.getCompany();
				if (company.isPresent() && company.get().getId() != 0) {
					attributes.add(company.orElse(new Company()).getId());
				} else {
					attributes.add(Types.INTEGER);
				}
				
				attributes.add(ucomputer.getId());
				
				getJdbcTemplate().update(qupdateComputer, attributes.toArray());

		} else {
			LOGGER.error("Pas d'ordinateur reçu à mettre à jour");
		}

	}

	/**
	 * Suppression de l'ordinateur à l'ID spécifié.
	 * @param id, l'id de l'ordinateur concerné.
	 */
	public void delete(int id) {

			getJdbcTemplate().update(qdeleteComputer, (Object) id);

	}

	public List<Computer> getPage(int nbComputer, int offset) {

		List<Computer> listComputers;

		listComputers = getJdbcTemplate().query(qgetPageOfComputers, new Object[] {offset, nbComputer}, new ComputerMapper());

		return listComputers;
	}



	public List<Computer> searchByName(int nbComputer, int noPage, String part) {

		part = "%" + part + "%";

		int offset = (noPage - 1) * nbComputer;
		List<Computer> listComputers;

		listComputers = getJdbcTemplate().query(qsearchByName, new Object[] {part, part, offset, nbComputer}, new ComputerMapper());
		
		return listComputers;
	}

	public int getCount() {
		int count = 0;

		count = getJdbcTemplate().queryForObject(qgetCount, Integer.class);

		return count;
	}

	public int getSearchCount(String part) {
		int count = 0;
		part = "%" + part + "%";

		count = getJdbcTemplate().queryForObject(qgetSearchCount, new Object[] {part, part}, Integer.class);

		return count;
		
	}

	/**
	 * @return the jdbcTemplate
	 */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

}



