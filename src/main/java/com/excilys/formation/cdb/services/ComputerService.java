/**
 * 
 */
package main.java.com.excilys.formation.cdb.services;

import java.util.List;

import main.java.com.excilys.formation.cdb.model.Computer;
import main.java.com.excilys.formation.cdb.persistence.ComputerDAO;

/**
 * @author excilys
 *
 */
public class ComputerService {
	
	/**
	 * instance, l'instance de ComputerService pour appliquer le pattern Singleton.
	 */
	private static ComputerService instance;
	
	/**
	 * Méthode permettant de récupérer l'instance du Singleton.
	 * @return l'instance
	 */
	public static ComputerService getInstance() {
		
		if (instance == null) {
			instance = new ComputerService();
		}		
		
		return instance;
	}
	
	private ComputerService() {
		
	}
	
	/**
	 * Retourne la liste complète des ordinateurs.
	 * @return la liste des ordinateurs.
	 */
	public List<String> getList() {
		
		return ComputerDAO.getInstance().getList();
		
			
	}
	
	/**
	 * Récupère l'ordinateur identifié par id et tous ses attributs.
	 * @param id, l'identifiant du Computer.
	 * @return le Computer avec tous ses détails.
	 */
	public Computer getDetails(int id) {
		return ComputerDAO.getInstance().getById(id).orElse(new Computer());
	}


	/**
	 * Crée un nouvel ordinateur en base.
	 * @param c, l'ordinateur à créer en base.
	 */
	public void createNew(Computer c) {
		
		ComputerDAO.getInstance().create(c);
		
	}
	
	/**
	 * Modifier un ordinateur en base.
	 * @param c, l'ordinateur modifié.
	 */
	public void update(Computer c) {
		ComputerDAO.getInstance().update(c);
	}
	
	/**
	 * Supprimer un ordinateur en base.
	 * @param c, l'ordinateur à supprimer.
	 */
	public void deleteComputer(int idComputer) {
		ComputerDAO.getInstance().delete(idComputer);
	}

	public List<String> getPage(int nbComputer, int noPage) {
		
		return ComputerDAO.getInstance().getPage(nbComputer, nbComputer * noPage);
		
		
	}

	public int getMaxPage(int nbComputer) {
		
		return ComputerDAO.getInstance().getMaxPage(nbComputer);
	}




}
