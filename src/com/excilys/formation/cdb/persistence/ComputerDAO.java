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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;

/**
 * @author excilys
 *
 */
public class ComputerDAO {

	final static Logger logger = Logger.getLogger(ComputerDAO.class);

	/**
	 * instance, l'instance de ComputerDAO pour appliquer le pattern Singleton.
	 */
	private static ComputerDAO instance;

	/**
	 * Méthode permettant de récupérer l'instance du Singleton
	 * @return l'instance
	 */
	public static ComputerDAO getInstance() {

		if (instance == null) {
			instance = new ComputerDAO();
		}

		return instance;
	}



	/**
	 * Columns c-
	 */
	private String cid = "id";
	private String cname = "name";
	private String cdateOfIntro = "introduced";
	private String cdateOfDisc = "discontinued";
	private String ccompanyID = "company_id";

	/**
	 * Queries q-
	 */
	private String qlistComputers = "SELECT name FROM computer";
	private String qgetComputerById = "SELECT name, introduced, discontinued, company_id FROM computer WHERE id = ?;";
	private String qcreateNewComputer = "INSERT INTO computer (name, introduced, discontinued, company_id)"
			+ "  VALUES (?, ?, ?, ?)";
	private String qupdateComputer = "UPDATE computer SET " 
			+ cname + " = ? , " 
			+ cdateOfIntro + " = ? , " 
			+ cdateOfDisc + " = ? , " 
			+ ccompanyID + " = ? "
			+ "WHERE " + cid + " = ? ;";
	private String qdeleteComputer = "DELETE FROM computer WHERE " + cid +" = ? ;";

	/**
	 * Retourne la liste complète des ordinateurs.
	 * @return la liste des ordinateurs.
	 */
	public List<String> getListComputer() {

		//La liste à remplir
		List<String> listComputers = new ArrayList<String>();

		//Traitement
		try(Connection connection = Connexion.getInstance()) {
			//Création du statement
			Statement stmt = connection.createStatement();
			//Création du ResultSet
			ResultSet results = stmt.executeQuery(qlistComputers);

			//Parcours du ResultSet
			while(results.next()) {

				//Ajout du Computer dans la liste
				listComputers.add(results.getString(cname));

			}

			results.close();

		} catch (SQLException e) {
			logger.error("Exception SQL à l'exécution de la requête" + e.getMessage());
		}

		return listComputers;
	}

	/**
	 * Recherche en base le Computer ayant l'ID indiqué.
	 * @param id, l'id du Computer recherché.
	 * @return computer, le Computer à l'id recherché ou null 
	 */
	public Optional<Computer> getComputerById(int id) {

		//Le Computer manipulé
		Computer computer = null;
		//La Date utilisée pour la conversion
		Date temp;


		try (Connection connection = Connexion.getInstance()) {

			//Création du statement 
			PreparedStatement pstmt = connection.prepareStatement(qgetComputerById);
			pstmt.setInt(1, id);
			//Création du ResultSet
			ResultSet results = pstmt.executeQuery();

			//Parcours du ResultSet
			if(results.next()) {
				//le Computeur existe, on peut donc l'instancier
				computer = new Computer();

				//Remplissage des champs
				computer.setId(id);
				computer.setName(results.getString(cname));
				temp = results.getDate(cdateOfIntro);
				if (temp != null) {
					computer.setDateOfIntro(temp.toLocalDate());
				}
				else {
					computer.setDateOfIntro(null);
				}
				temp = results.getDate(cdateOfDisc);			
				if (temp != null) {
					computer.setDateOfDisc(temp.toLocalDate());
				}
				else {
					computer.setDateOfDisc(null);
				}

				computer.setCompany(CompanyDAO.getInstance().getCompanyByID(results.getInt(ccompanyID)).orElse(new Company()));
			}

			results.close();

		}
		catch (SQLException e) {
			logger.error("Exception SQL à l'exécution de la requête : " + e.getMessage());
		}

		if(computer == null) {
			logger.error("Aucun ordinateur n'a l'ID spécifié" );
		}

		return Optional.ofNullable(computer);
	}

	/**
	 * Création d'un ordinateur en base.
	 * @param computer, l'ordinateur à insérer dans la base.
	 */
	public void createNewComputer(Computer computer) {


		try (Connection connection = Connexion.getInstance()){
			PreparedStatement pstmt = connection.prepareStatement(qcreateNewComputer);

			//Remplissage de la requête

			if (computer.getName() != null) {
				pstmt.setString(1, computer.getName());
			}
			else {
				pstmt.setString(1,  null);
			}

			if ( computer.getDateOfIntro()!= null) {
				pstmt.setDate(2, Date.valueOf(computer.getDateOfIntro()));
			}
			else {
				pstmt.setDate(2, null);
			}

			if ( computer.getDateOfDisc()!= null) {
				pstmt.setDate(3, Date.valueOf(computer.getDateOfDisc()));
			}
			else {
				pstmt.setDate(3, null);
			}

			if(computer.getCompany() != null) {
				pstmt.setInt(4, computer.getCompany().getId());
			}
			else {
				pstmt.setInt(4, 0);
			}

			//Exécution de la requête
			pstmt.executeUpdate();			


		}
		catch (SQLException e) {
			logger.error("Exception SQL à l'exécution de la requête : " + e.getMessage());
		}

	}


	/**
	 * Modification d'un Computer.
	 * @param ucomputer, the updated computer
	 */
	public void updateComputer(Computer ucomputer) {

		if( this.getComputerById(ucomputer.getId()).isPresent() 
				&& CompanyDAO.getInstance().getCompanyByID(ucomputer.getCompany().getId()).isPresent()) { 

			try (Connection connection = Connexion.getInstance()) {

				PreparedStatement pstmt = connection.prepareStatement(qupdateComputer);






				//Récupération du Computer à modifier

				pstmt.setInt(5, ucomputer.getId());

				pstmt.setString(1, ucomputer.getName());

				if (ucomputer.getDateOfIntro() != null ) {

					pstmt.setDate(2, Date.valueOf(ucomputer.getDateOfIntro()));	
				}
				else {
					pstmt.setDate(2, null);
				}
				if (ucomputer.getDateOfDisc() != null ) {
					pstmt.setDate(3, Date.valueOf(ucomputer.getDateOfDisc()));	
				}
				else {
					pstmt.setDate(3, null);
				}
				
				pstmt.setInt(4, ucomputer.getCompany().getId());

				pstmt.executeUpdate();


			}catch (SQLException e) {
				logger.error("Exception SQL à l'exécution de la requête : " + e.getMessage());
			}
		}
		else {
			logger.error("Pas d'ordinateur reçu à mettre à jour");
			logger.error("Pas de fabricant spécifié");
		}

	}

	/**
	 * Suppression de l'ordinateur à l'ID spécifié.
	 * @param id, l'id de l'ordinateur concerné.
	 */
	public void deleteComputer(int id) {


		try (Connection connection = Connexion.getInstance()) {
			PreparedStatement pstmt = connection.prepareStatement(qdeleteComputer);

			pstmt.setInt(1, id);

			pstmt.executeUpdate();

		}catch (SQLException e) {
			logger.error("Exception SQL à l'exécution de la requête : " + e.getMessage());
		}


	}


}
