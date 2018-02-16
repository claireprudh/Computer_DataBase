/**
 * 
 */
package com.excilys.formation.cdb.services;

import java.util.List;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.ComputerDAO;

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
	 * Méthode permettant de récupérer l'instance du Singleton
	 * @return l'instance
	 */
	public static ComputerService getInstance() {
		ComputerService c;
		
		if (instance == null) {
			c = new ComputerService();
		}
		else {
			c = instance;
		}
		
		return c;
	}
	
	/**
	 * Retourne la liste complète des ordinateurs.
	 * @return la liste des ordinateurs.
	 */
	public List<String> getListComputers(){
		
		return ComputerDAO.getInstance().getListComputer();
		
			
	}
	
	/**
	 * Récupère l'ordinateur identifié par id et tous ses attributs
	 * @param id, l'identifiant du Computer.
	 * @return le Computer avec tous ses détails.
	 */
	public Computer getDetails(int id) {
		return ComputerDAO.getInstance().getComputerById(id).orElse(new Computer());
	}


	/**
	 * Crée un nouvel ordinateur en base.
	 * @param c, l'ordinateur à créer en base.
	 */
	public void createNewComputer(Computer c) {
		
		ComputerDAO.getInstance().createNewComputer(c);
		
	}
	
	/**
	 * Modifier un ordinateur en base.
	 * @param c, l'ordinateur modifié.
	 */
	public void updateComputer(Computer c) {
		ComputerDAO.getInstance().updateComputer(c);
	}
	
	/**
	 * Supprimer un ordinateur en base.
	 * @param c, l'ordinateur à supprimer.
	 */
	public void deleteComputer(int idComputer) {
		ComputerDAO.getInstance().deleteComputer(idComputer);
	}

	public List<String> getPageComputers(int nbComputer, int noPage) {
		
		return ComputerDAO.getInstance().getPageComputer(nbComputer, nbComputer*noPage);
		
		
	}

	public int getMaxPage(int nbComputer) {
		
		return ComputerDAO.getInstance().getMaxPage(nbComputer);
	}




}
