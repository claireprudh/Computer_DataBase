/**
 * 
 */
package com.excilys.formation.cdb.persistence;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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

	
	JdbcTemplate jdbcTemplate;

	@Autowired
	public ComputerDAO(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}


	/*
	 * Columns c-
	 */
	private final String cid = Column.CID.getName();
	private final String cname = Column.CNAME.getName();
	private final String cdateOfIntro = Column.CDATE_OF_INTRO.getName();
	private final String cdateOfDisc = Column.CDATE_OF_DISC.getName();
	private final String ccompanyID = Column.CCOMPANY_ID.getName();
	private final String ccount = Column.CCOUNT.getName();

	/*
	 * Queries q-
	 */
	private final String allComputer = RequestCreator.getInstance().select(
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
	private final String qgetCount = "SELECT " + ccount + " FROM computer ;";
	private final String qgetSearchCount = RequestCreator.getInstance().select(
			Column.CCOUNT)
			+ "LEFT JOIN company ON " + ccompanyID + " = " + Column.CCID.getName() + " "
			+ "WHERE " + cname + " LIKE ? or " + Column.CCNAME.getName() + " LIKE ? ;";
	private final String qsearchByName = allComputer
			+ "LEFT JOIN company ON " + ccompanyID + " = " + Column.CCID.getName() + " "
			+ "WHERE " + cname + " LIKE ? or " + Column.CCNAME.getName() + " LIKE ? "
			+ "LIMIT ? , ? ;";
	private final String qlistComputers2delete = allComputer
			+ "LEFT JOIN company ON " + ccompanyID + " = " + Column.CCID.getName() + " "
			+ "WHERE " + ccompanyID + " = ?;";

	/**
	 * Retourne la liste complète des ordinateurs.
	 * @return la liste des ordinateurs.
	 */
	public List<Computer> getList() {

		List<Computer> listComputers = new ArrayList<Computer>();

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

		Computer computer = (Computer) getJdbcTemplate().queryForObject(
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

			if (computer.getDateOfIntro().isPresent()) {
				attributes.add(Date.valueOf(computer.getDateOfIntro().get()));
			} else {
				attributes.add(null);
			}

			if (computer.getDateOfDisc().isPresent()) {
				attributes.add(Date.valueOf(computer.getDateOfDisc().get()));
			} else {
				attributes.add(null);
			}

			if (computer.getCompany().isPresent() && computer.getCompany().get().getId() != 0) {
				attributes.add(computer.getCompany().orElse(new Company()).getId());
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

				if (ucomputer.getDateOfIntro().isPresent()) {
					attributes.add(Date.valueOf(ucomputer.getDateOfIntro().get()));	
				} else {
					attributes.add(null);
				}
				if (ucomputer.getDateOfDisc().isPresent()) {
					attributes.add(Date.valueOf(ucomputer.getDateOfDisc().get()));	
				} else {
					attributes.add(null);
				}

				if (ucomputer.getCompany().isPresent() && ucomputer.getCompany().get().getId() != 0) {
					attributes.add(ucomputer.getCompany().orElse(new Company()).getId());
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

			getJdbcTemplate().update(qdeleteComputer, new Object[] {id});

	}

	public List<Computer> getPage(int nbComputer, int offset) {

		List<Computer> listComputers = new ArrayList<Computer>();

		listComputers = getJdbcTemplate().query(qgetPageOfComputers, new Object[] {offset, nbComputer}, new ComputerMapper());

		return listComputers;
	}



	public List<Computer> searchByName(int nbComputer, int noPage, String part) {

		part = "%" + part + "%";

		int offset = (noPage - 1) * nbComputer;
		List<Computer> listComputers = new ArrayList<Computer>();

		listComputers = getJdbcTemplate().query(qsearchByName, new Object[] {part, part, offset, nbComputer}, new ComputerMapper());
		
		return listComputers;
	}

	/*public boolean deletecomputers(Connection connection, int id) {

		List<Computer> listComputers = new ArrayList<Computer>();
		listComputers = getComputers2delete(connection, id);
		boolean success = true;

		for (Computer c : listComputers) {
			try (PreparedStatement pstmt = connection.prepareStatement(qdeleteComputer)) {


				pstmt.setInt(1, c.getId());

				pstmt.executeUpdate();

			} catch (SQLException e) {
				LOGGER.error("Exception SQL à l'exécution de la requête : " + e.getMessage());
				success = false;
			}
		}

		return success;

	}*/

	/*public List<Computer> getComputers2delete(Connection connection, int id) {

		List<Computer> listComputers = new ArrayList<Computer>();

		try (PreparedStatement pstmt = connection.prepareStatement(qlistComputers2delete)) {

			pstmt.setInt(1, id);

			ResultSet results = pstmt.executeQuery();

			while (results.next()) {
				listComputers.add(computerMapper.map(results));
			}

		} catch (SQLException e) {

		}
		return listComputers;
	}*/

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



