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
	 * Retourne la liste complète des ordinateurs.
	 * @return la liste des ordinateurs.
	 */
	public List<Computer> getListComputer() {
		
		//La liste à remplir
		List<Computer> lp = new ArrayList<Computer>();
		
		//Une variable de type Date pour manipuler les LocalDate
		Date temp;
		
		//Connection à la base de données
		Connexion conn = Connexion.getInstance();
		conn.open();
		Connection c = conn.getConnection();
		
		
		//Traitement
		try {
			//Création du statement
			Statement stmt = c.createStatement();
			//Création du ResultSet
			ResultSet rs = stmt.executeQuery("SELECT id, name, introduced, discontinued, company_id FROM computer");
			
			//Parcours du ResultSet
			while(rs.next()) {
				//Création d'une instance de Computer pour enregistrer les valeurs de l'objet en base
				Computer computer = new Computer();
				
				//Remplissage de l'instance
				computer.setId(rs.getInt("id"));
				
				computer.setName(rs.getString("name"));
				
				temp = rs.getDate("introduced"); //Récupération de la Date au format Date (API jdbc)
				if (temp != null) { //Vérification de la présence d'une information
					computer.setDateOfIntro(temp.toLocalDate()); //Conversion de la donnée et remplissage du champ
				}
				else {
					computer.setDateOfIntro(null); //Remplissage du champ si null
				}
				temp = rs.getDate("discontinued");			
				if (temp != null) {
					computer.setDateOfDisc(temp.toLocalDate());
				}
				else {
					computer.setDateOfDisc(null);
				}
	
				computer.setCompanyID(CompanyDAO.getInstance().getCompanyByID(rs.getInt("company_id")).orElse(new Company()));
				
				
				//Ajout du Computer dans la liste
				lp.add(computer);
				
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
			Statement stmt = c.createStatement();
			//Création du ResultSet
			ResultSet rs = stmt.executeQuery("SELECT name, introduced, discontinued, company_id  "
					+ "FROM computer WHERE id = " + id + ";");
			
			//Parcours du ResultSet
			if(rs.next()) {
				//le Computeur existe, on peut donc l'instancier
				computer = new Computer();
				
				//Remplissage des champs
				computer.setId(id);
				computer.setName(rs.getString("name"));
				temp = rs.getDate("introduced");
				if (temp != null) {
					computer.setDateOfIntro(temp.toLocalDate());
				}
				else {
					computer.setDateOfIntro(null);
				}
				temp = rs.getDate("discontinued");			
				if (temp != null) {
					computer.setDateOfDisc(temp.toLocalDate());
				}
				else {
					computer.setDateOfDisc(null);
				}
	
				computer.setCompanyID(CompanyDAO.getInstance().getCompanyByID(rs.getInt("company_id")).orElse(new Company()));
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
			
		//Création de la requête préparée
		String query = "INSERT INTO computer (name, introduced, discontinued, company_id)  VALUES (?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = c.prepareStatement(query);
			
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
			
			pstmt.setInt(4, computer.getCompanyID().getId());

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
		
		//Le Computer avec les anciennes valeurs pour la comparaison
		Computer oldComputer;
		
		//Ouverture de la connexion
		Connexion conn = Connexion.getInstance();
		conn.open();
		Connection connection = conn.getConnection();
		
		//La variable contenant la requête
		StringBuilder query = new StringBuilder();
		
		try {
			Statement stmt = connection.createStatement();
		
		
		if( this.getComputerById(ucomputer.getId()).isPresent()) { //Si le Computer à modifier existe
			
			//Récupération du Computer à modifier
			oldComputer = ComputerDAO.getInstance().getComputerById(ucomputer.getId()).get(); 
			
			//Ajout d'une requête par champ à modifier
			if (!oldComputer.getName().equals(ucomputer.getName())) {
				query.append("UPDATE computer SET name = \"" + ucomputer.getName() + "\" WHERE id = " + ucomputer.getId()+ " ;");
			}
			if (oldComputer.getDateOfIntro() != null &&
					ucomputer.getDateOfIntro() != null &&
					!oldComputer.getDateOfIntro().equals(ucomputer.getDateOfIntro())) {
				query.append("UPDATE computer SET introduced = \'" + ucomputer.getDateOfIntro().toString() + 
						"\' WHERE id = " + ucomputer.getId()+ " ;");
			}
			if (oldComputer.getDateOfDisc() != null &&
					ucomputer.getDateOfDisc() != null &&
					!oldComputer.getDateOfDisc().equals(ucomputer.getDateOfDisc())) {
				query.append("UPDATE computer SET discontinued = \'" + ucomputer.getDateOfDisc().toString() + 
						"\' WHERE id = " + ucomputer.getId()+ " ;");
			}
			if (!oldComputer.getCompanyID().equals(ucomputer.getCompanyID())) {
				query.append("UPDATE computer SET company_id = " + ucomputer.getCompanyID().getId() + 
						" WHERE id = " + ucomputer.getId()+ " ;");
			}
			
			if(query.length()!=0) { //Si il y a des changements
				System.out.println(query.toString());
				stmt.executeUpdate(query.toString());
			}
			else {
				System.out.println("Pas de changements");
				
			}
			
			
			
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
			Statement stmt = connection.createStatement();
			String query = "DELETE FROM computer WHERE id = " + id + " ;";
			
			stmt.executeUpdate(query);
			
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Fermeture de la connexion
		conn.close();
		
	}
	

}
