/**
 * 
 */
package com.excilys.formation.cdb.persistence;


import java.sql.Date;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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


	private final JdbcTemplate jdbcTemplate;
	private final ComputerMapper computerMapper;

	public ComputerDAO(JdbcTemplate jdbcTemplate, ComputerMapper computerMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.computerMapper = computerMapper;				
	}

	/*
	 * Queries q-
	 */
	private static final String SELECT = "SELECT ";
	private static final String FROM = " FROM computer ";
	private static final String EQUAL = " = ? ";
	private static final String LEFT_JOIN = " LEFT JOIN company ON " + Column.CCOMPANY_ID.getName() + " = " + Column.CCID.getName();
	private static final String WHERE = " WHERE ";
	private static final String WHERE_ID = WHERE + Column.CID.getName() + EQUAL;
	private static final String LIKE = " LIKE ? ";
	private static final String LIMIT = " LIMIT ? , ? ";
	private static final String TERMINATE = " ;";
	
	
	private static final List<Column> ALL_COMPUTER = Arrays.asList(
			Column.CID, 
			Column.CNAME, 
			Column.CDATE_OF_INTRO, 
			Column.CDATE_OF_DISC,
			Column.CCOMPANY_ID,
			Column.CCNAME
			);
	
	private static final String SELECT_ALL = ALL_COMPUTER.stream().map(s -> s.getName()).collect(Collectors.joining(" , ", SELECT, FROM));
	
	private static final List<Column> ALL_UPDATE = Arrays.asList(
			Column.CNAME, 
			Column.CDATE_OF_INTRO, 
			Column.CDATE_OF_DISC,
			Column.CCOMPANY_ID);
	
	private static final String INSERT = "INSERT INTO computer (";
	private static final String VALUES = ") VALUES (?, ?, ?, ?);";
	private static final String Q_CREATE_NEW_COMPUTER = ALL_UPDATE.stream()
			.map(s -> s.getName()).collect(Collectors.joining(", ", INSERT, VALUES));
	
	private static final String Q_LIST_COMPUTERS = SELECT_ALL
			+ LEFT_JOIN + TERMINATE;
	private static final String Q_GET_COMPUTER_BY_ID = SELECT_ALL
			+ LEFT_JOIN
			+ WHERE_ID + TERMINATE;
	
	private static final String UPDATE = "UPDATE computer SET ";
	
	private static final String Q_UPDATE_COMPUTER = ALL_UPDATE.stream()
			.map(s -> s.getName()).collect(Collectors.joining(EQUAL + " , ", UPDATE, EQUAL + WHERE_ID));
	private static final String Q_DELETE_COMPUTER = "DELETE FROM computer" + WHERE_ID + TERMINATE;
	private static final String Q_GET_PAGE_OF_COMPUTERS = SELECT_ALL
			+ LEFT_JOIN
			+ LIMIT + TERMINATE;
	private static final String Q_GET_COUNT = SELECT + Column.CCOUNT.getName() + FROM + TERMINATE;
	private static final String Q_GET_SEARCH_COUNT = SELECT + Column.CCOUNT.getName() + FROM
			+ LEFT_JOIN
			+ WHERE + Column.CNAME.getName() + LIKE + " or " + Column.CCNAME.getName() + LIKE + TERMINATE;
	private static final String Q_SEARCH_BY_NAME = SELECT_ALL
			+ LEFT_JOIN
			+ WHERE + Column.CNAME.getName() + LIKE + " or " + Column.CCNAME.getName() + LIKE
			+ LIMIT + TERMINATE;
	

	/**
	 * Retourne la liste complète des ordinateurs.
	 * @return la liste des ordinateurs.
	 */
	public List<Computer> getList() {

		List<Computer> listComputers = new ArrayList<>();

		List<Map<String, Object>> rows = getJdbcTemplate().queryForList(Q_LIST_COMPUTERS);
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
				Q_GET_COMPUTER_BY_ID, new Object[] {id}, computerMapper);

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

			getJdbcTemplate().update(Q_CREATE_NEW_COMPUTER, attributes.toArray());

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
				
				getJdbcTemplate().update(Q_UPDATE_COMPUTER, attributes.toArray());

		} else {
			LOGGER.error("Pas d'ordinateur reçu à mettre à jour");
		}

	}

	/**
	 * Suppression de l'ordinateur à l'ID spécifié.
	 * @param id, l'id de l'ordinateur concerné.
	 */
	public void delete(int id) {

			getJdbcTemplate().update(Q_DELETE_COMPUTER, (Object) id);

	}

	public List<Computer> getPage(int nbComputer, int offset) {

		List<Computer> listComputers;

		listComputers = getJdbcTemplate().query(Q_GET_PAGE_OF_COMPUTERS, new Object[] {offset, nbComputer}, computerMapper);

		return listComputers;
	}



	public List<Computer> searchByName(int nbComputer, int noPage, String part) {

		part = "%" + part + "%";

		int offset = (noPage - 1) * nbComputer;
		List<Computer> listComputers;

		listComputers = getJdbcTemplate().query(Q_SEARCH_BY_NAME, new Object[] {part, part, offset, nbComputer}, computerMapper);
		
		return listComputers;
	}

	public int getCount() {
		int count = 0;

		count = getJdbcTemplate().queryForObject(Q_GET_COUNT, Integer.class);

		return count;
	}

	public int getSearchCount(String part) {
		int count = 0;
		part = "%" + part + "%";

		count = getJdbcTemplate().queryForObject(Q_GET_SEARCH_COUNT, new Object[] {part, part}, Integer.class);

		return count;
		
	}

	/**
	 * @return the jdbcTemplate
	 */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

}



