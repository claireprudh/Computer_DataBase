/**
 * 
 */
package com.excilys.formation.cdb.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.pagination.Page;
import com.excilys.formation.cdb.persistence.ComputerDAO;

/**
 * @author excilys
 *
 */
@Service
public class ComputerService {

	/**
	 * instance, l'instance de ComputerService pour appliquer le pattern Singleton.
	 */
	//private static ComputerService instance;

	@Autowired
	private ComputerDAO computerDAO/* = ComputerDAO.getInstance()*/;

	/**
	 * Méthode permettant de récupérer l'instance du Singleton.
	 * @return l'instance
	 */
	/*public static ComputerService getInstance() {

		if (instance == null) {
			instance = new ComputerService();
		}		

		return instance;
	}

	private ComputerService() {

	}*/

	/**
	 * Retourne la liste complète des ordinateurs.
	 * @return la liste des ordinateurs.
	 */
	public List<Computer> getList() {

		return computerDAO.getList();

	}

	/**
	 * Récupère l'ordinateur identifié par id et tous ses attributs.
	 * @param id, l'identifiant du Computer.
	 * @return le Computer avec tous ses détails.
	 */
	public Computer getDetails(int id) {
		return computerDAO.getByID(id).orElse(new Computer.ComputerBuilder().build());
	}


	/**
	 * Crée un nouvel ordinateur en base.
	 * @param c, l'ordinateur à créer en base.
	 */
	public void createNew(Computer c) {

		computerDAO.create(c);

	}

	/**
	 * Modifier un ordinateur en base.
	 * @param c, l'ordinateur modifié.
	 */
	public void update(Computer c) {
		computerDAO.update(c);
	}

	/**
	 * Supprimer un ordinateur en base.
	 * @param c, l'ordinateur à supprimer.
	 */
	public void deleteComputer(int idComputer) {
		computerDAO.delete(idComputer);
	}

	public List<Computer> getPage(int nbComputer, int noPage) {

		return computerDAO.getPage(nbComputer, nbComputer * (noPage - 1));

	}

	public List<Computer> searchByName(int nbComputer, int noPage, String part) {
		return computerDAO.searchByName(nbComputer, noPage, part);
	}

	public int getCount() {

		return computerDAO.getCount();
	}

	public int getSearchCount(String part) {

		return computerDAO.getSearchCount(part);
	}

	public List<Computer> getPage(int nbComputer, int noPage, String contenu) {
		List<Computer> list = new ArrayList<Computer>();

		if (contenu == null || contenu.equals(" ")) {
			list = this.getPage(nbComputer, noPage);
		} else {
			list = this.searchByName(nbComputer, noPage, contenu);
		}

		return list;
	}

	public void fillPage(Page page) {

		page.setListComputers(getPage(page.getLivre().getNbComputer(), page.getNoPage(), page.getLivre().getContenu()));

	}


}
