/**
 * 
 */
package com.excilys.formation.cdb.persistence;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.mappers.ComputerMapper;

/**
 * @author excilys
 *
 */
public class ComputerDAO {

	static final Logger LOGGER = LogManager.getLogger(ComputerDAO.class);

	/**
	 * instance, l'instance de ComputerDAO pour appliquer le pattern Singleton.
	 */
	private static ComputerDAO instance;

	/**
	 * Méthode permettant de récupérer l'instance du Singleton.
	 * @return l'instance
	 */
	public static ComputerDAO getInstance() {

		if (instance == null) {
			instance = new ComputerDAO();
		}

		return instance;
	}

	private ComputerDAO() {

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

		ResultSet results = null;
		try (Connection connection = Connexion.getInstance()) {

			Statement stmt = connection.createStatement();

			results = stmt.executeQuery(qlistComputers);

			while (results.next()) {

				listComputers.add(ComputerMapper.getInstance().map(results));

			}

			results.close();

		} catch (SQLException e) {
			LOGGER.error("Exception SQL à l'exécution de la requête : " + e.getMessage());
		}

		return listComputers;
	}

	/**
	 * Recherche en base le Computer ayant l'ID indiqué.
	 * @param id, l'id du Computer recherché.
	 * @return computer, le Computer à l'id recherché ou null 
	 */
	public Optional<Computer> getByID(int id) {

		Computer computer = null;


		try (Connection connection = Connexion.getInstance()) {


			PreparedStatement pstmt = connection.prepareStatement(qgetComputerById);
			pstmt.setInt(1, id);

			ResultSet results = pstmt.executeQuery();


			if (results.next()) {

				computer = ComputerMapper.getInstance().map(results);
			}

			results.close();

		} catch (SQLException e) {
			LOGGER.error("Exception SQL à l'exécution de la requête : " + e.getMessage());
		}

		if (computer == null) {
			LOGGER.error("Aucun ordinateur n'a l'ID spécifié : " + id);
		}

		return Optional.ofNullable(computer);
	}

	/**
	 * Création d'un ordinateur en base.
	 * @param computer, l'ordinateur à insérer dans la base.
	 */
	public void create(Computer computer) {


		try (Connection connection = Connexion.getInstance()) {
			PreparedStatement pstmt = connection.prepareStatement(qcreateNewComputer);


			if (computer.getName() != null) {
				pstmt.setString(1, computer.getName());
			} else {
				pstmt.setString(1,  null);
			}

			if (computer.getDateOfIntro().isPresent()) {
				pstmt.setDate(2, Date.valueOf(computer.getDateOfIntro().get()));
			} else {
				pstmt.setDate(2, null);
			}

			if (computer.getDateOfDisc().isPresent()) {
				pstmt.setDate(3, Date.valueOf(computer.getDateOfDisc().get()));
			} else {
				pstmt.setDate(3, null);
			}

			if (computer.getCompany().isPresent() && computer.getCompany().get().getId() != 0) {
				pstmt.setInt(4, computer.getCompany().orElse(new Company()).getId());
			} else {
				pstmt.setNull(4, Types.INTEGER);
			}

			pstmt.executeUpdate();			


		} catch (SQLException e) {
			LOGGER.error("Exception SQL à l'exécution de la requête : " + e.getMessage());
		}

	}


	/**
	 * Modification d'un Computer.
	 * @param ucomputer, the updated computer
	 */
	public void update(Computer ucomputer) {

		if (this.getByID(ucomputer.getId()).isPresent()) { 

			try (Connection connection = Connexion.getInstance()) {

				PreparedStatement pstmt = connection.prepareStatement(qupdateComputer);

				pstmt.setInt(5, ucomputer.getId());

				pstmt.setString(1, ucomputer.getName());

				if (ucomputer.getDateOfIntro().isPresent()) {
					pstmt.setDate(2, Date.valueOf(ucomputer.getDateOfIntro().get()));	
				} else {
					pstmt.setDate(2, null);
				}
				if (ucomputer.getDateOfDisc().isPresent()) {
					pstmt.setDate(3, Date.valueOf(ucomputer.getDateOfDisc().get()));	
				} else {
					pstmt.setDate(3, null);
				}

				if (ucomputer.getCompany().isPresent() && ucomputer.getCompany().get().getId() != 0) {
					pstmt.setInt(4, ucomputer.getCompany().orElse(new Company()).getId());
				} else {
					pstmt.setNull(4, Types.INTEGER);
				}

				pstmt.executeUpdate();


			} catch (SQLException e) {
				LOGGER.error("Exception SQL à l'exécution de la requête : " + e.getMessage());
			}
		} else {
			LOGGER.error("Pas d'ordinateur reçu à mettre à jour");
		}

	}

	/**
	 * Suppression de l'ordinateur à l'ID spécifié.
	 * @param id, l'id de l'ordinateur concerné.
	 */
	public void delete(int id) {


		try (Connection connection = Connexion.getInstance(); PreparedStatement pstmt = connection.prepareStatement(qdeleteComputer)) {


			pstmt.setInt(1, id);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			LOGGER.error("Exception SQL à l'exécution de la requête : " + e.getMessage());
		}


	}

	public List<Computer> getPage(int nbComputer, int offset) {


		List<Computer> listComputers = new ArrayList<Computer>();


		try (Connection connection = Connexion.getInstance(); 
				PreparedStatement pstmt = connection.prepareStatement(qgetPageOfComputers)) {




			pstmt.setInt(1, offset);
			pstmt.setInt(2, nbComputer);

			ResultSet results = pstmt.executeQuery();


			while (results.next()) {


				listComputers.add(ComputerMapper.getInstance().map(results));

			}

			results.close();

		} catch (SQLException e) {
			LOGGER.error("Exception SQL à l'exécution de la requête : " + e.getMessage());
		}

		return listComputers;
	}

	public int getMaxPage(int nbComputer) {

		int maxPage = this.getCount();				

		maxPage = maxPage / nbComputer; 
		if (maxPage % nbComputer > 0) {
			maxPage++;
		}



		return maxPage;

	}

	public List<Computer> searchByName(int nbComputer, int offset, String part) {

		List<Computer> listComputers = new ArrayList<Computer>();

		try (Connection connection = Connexion.getInstance(); 
				PreparedStatement pstmt = connection.prepareStatement(qsearchByName)) {

			pstmt.setString(1, "%" + part + "%");
			pstmt.setString(2, "%" + part + "%");
			pstmt.setInt(3, offset);
			pstmt.setInt(4, nbComputer);

			ResultSet results = pstmt.executeQuery();
			while (results.next()) {
				Computer c = ComputerMapper.getInstance().map(results);
				listComputers.add(c);
			}
		} catch (SQLException e) {
			LOGGER.error("Exception SQL à l'exécution de la recherche : " + e.getMessage());
		}

		return listComputers;
	}

	public boolean deletecomputers(Connection connection, int id) {

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

	}

	public List<Computer> getComputers2delete(Connection connection, int id) {

		List<Computer> listComputers = new ArrayList<Computer>();

		try (PreparedStatement pstmt = connection.prepareStatement(qlistComputers2delete)) {

			pstmt.setInt(1, id);

			ResultSet results = pstmt.executeQuery();

			while (results.next()) {
				listComputers.add(ComputerMapper.getInstance().map(results));
			}

		} catch (SQLException e) {

		}
		return listComputers;
	}

	public int getCount() {
		int count = 0;

		try (Connection connection = Connexion.getInstance(); 
				PreparedStatement pstmt = connection.prepareStatement(qgetCount)) {

			ResultSet results = pstmt.executeQuery();

			if (results.next()) {
				count = results.getInt(ccount);
			}

			results.close();

		} catch (SQLException e) {
			LOGGER.error("Exception SQL à l'exécution de la requête : " + e.getMessage());
		}

		return count;
	}

	public int getSearchCount(String part) {
		int count = 0;

		try (Connection connection = Connexion.getInstance(); 
				PreparedStatement pstmt = connection.prepareStatement(qgetSearchCount)) {

			pstmt.setString(1, "%" + part + "%");
			pstmt.setString(2, "%" + part + "%");
			ResultSet results = pstmt.executeQuery();

			if (results.next()) {
				count = results.getInt(ccount);
			}

			results.close();

		} catch (SQLException e) {
			LOGGER.error("Exception SQL à l'exécution de la requête : " + e.getMessage());
		}

		return count;
	}




}
