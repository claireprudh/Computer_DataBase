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
	public List<Computer> getList() {
		
		return ComputerDAO.getInstance().getList();
		
			
	}
	
	/**
	 * Récupère l'ordinateur identifié par id et tous ses attributs.
	 * @param id, l'identifiant du Computer.
	 * @return le Computer avec tous ses détails.
	 */
	public Computer getDetails(int id) {
		return ComputerDAO.getInstance().getByID(id).orElse(new Computer());
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

	public List<Computer> getPage(int nbComputer, int noPage) {
		
		return ComputerDAO.getInstance().getPage(nbComputer, nbComputer * (noPage - 1));
		
		
	}

	public int getMaxPage(int nbComputer) {
		
		return ComputerDAO.getInstance().getMaxPage(nbComputer);
	}
	
	public List<Computer> searchByName(int nbComputer, int noPage, String part) {
		return ComputerDAO.getInstance().searchByName(nbComputer, noPage, part);
	}

	public int getCount() {
		
		return ComputerDAO.getInstance().getCount();
	}

	public int getSearchCount(String part) {
		
		return ComputerDAO.getInstance().getSearchCount(part);
	}




}
