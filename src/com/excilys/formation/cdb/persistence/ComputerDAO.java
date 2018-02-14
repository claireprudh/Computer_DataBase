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

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;

/**
 * @author excilys
 *
 */
public class ComputerDAO {
	
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
		List<String> lp = new ArrayList<String>();
		
		//Connection à la base de données
		Connexion conn = Connexion.getInstance();
		conn.open();
		Connection c = conn.getConnection();
		
		
		//Traitement
		try {
			//Création du statement
			Statement stmt = c.createStatement();
			//Création du ResultSet
			ResultSet rs = stmt.executeQuery(qlistComputers);
			
			//Parcours du ResultSet
			while(rs.next()) {
				
				//Ajout du Computer dans la liste
				lp.add(rs.getString(cname));
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Fermeture de la connexion
		conn.close();
		
		return lp;
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
		
		//Ouverture de la connexion
		Connexion conn = Connexion.getInstance();
		conn.open();
		Connection c = conn.getConnection();
		
		try {
			
			//Création du statement 
			PreparedStatement pstmt = c.prepareStatement(qgetComputerById);
			pstmt.setInt(1, id);
			//Création du ResultSet
			ResultSet rs = pstmt.executeQuery();
			
			//Parcours du ResultSet
			if(rs.next()) {
				//le Computeur existe, on peut donc l'instancier
				computer = new Computer();
				
				//Remplissage des champs
				computer.setId(id);
				computer.setName(rs.getString(cname));
				temp = rs.getDate(cdateOfIntro);
				if (temp != null) {
					computer.setDateOfIntro(temp.toLocalDate());
				}
				else {
					computer.setDateOfIntro(null);
				}
				temp = rs.getDate(cdateOfDisc);			
				if (temp != null) {
					computer.setDateOfDisc(temp.toLocalDate());
				}
				else {
					computer.setDateOfDisc(null);
				}
	
				computer.setCompanyID(CompanyDAO.getInstance().getCompanyByID(rs.getInt(ccompanyID)).orElse(new Company()));
			}
			
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Fermeture de la connexion
		conn.close();

		
		return Optional.ofNullable(computer);
	}

	/**
	 * Création d'un ordinateur en base.
	 * @param computer, l'ordinateur à insérer dans la base.
	 */
	public void createNewComputer(Computer computer) {
		
		//Ouverture de la connexion
		Connexion conn = Connexion.getInstance();
		conn.open();
		Connection c = conn.getConnection();
			
		try {
			PreparedStatement pstmt = c.prepareStatement(qcreateNewComputer);
			
			//Remplissage de la requête
			pstmt.setString(1, computer.getName());
			
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
			
			pstmt.setInt(4, computer.getCompany().getId());

			//Exécution de la requête
			pstmt.executeUpdate();			
			
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Fermeture de la connexion
		conn.close();
		
		
		
		
	}
	
	
	/**
	 * Modification d'un Computer.
	 * @param ucomputer, the updated computer
	 */
	public void updateComputer(Computer ucomputer) {
		
		//Ouverture de la connexion
		Connexion conn = Connexion.getInstance();
		conn.open();
		Connection connection = conn.getConnection();
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(qupdateComputer);
		
		
		if( this.getComputerById(ucomputer.getId()).isPresent()) { //Si le Computer à modifier existe
			
			
			
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
			if (ucomputer.getCompany() != null) {
				pstmt.setInt(4, ucomputer.getCompany().getId());
			}
			
			
			pstmt.executeUpdate();
						
		}
		else {
			
			System.out.println("L'id spécifié ne correspond à aucun ordinateur de la base.");
			
			
		}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Fermeture de la connexion
		conn.close();
	}
	
	/**
	 * Suppression de l'ordinateur à l'ID spécifié.
	 * @param id, l'id de l'ordinateur concerné.
	 */
	public void deleteComputer(int id) {
		
		//Ouverture de la connexion
		Connexion conn = Connexion.getInstance();
		conn.open();
		Connection connection = conn.getConnection();
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(qdeleteComputer);
			
			pstmt.setInt(1, id);
			
			pstmt.executeUpdate();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Fermeture de la connexion
		conn.close();
		
	}
	

}
