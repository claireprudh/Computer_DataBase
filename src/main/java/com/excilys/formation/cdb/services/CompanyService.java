package com.excilys.formation.cdb.services;


import java.util.List;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.persistence.CompanyDAO;

/**
 * @author excilys
 *
 */
public class CompanyService {

	private CompanyDAO companyDAO = CompanyDAO.getInstance();
	/**
	 * instance, l'instance de CompanyService pour appliquer le pattern Singleton.
	 */
	private static CompanyService instance;
	
	/**
	 * Méthode permettant de récupérer l'instance du Singleton.
	 * @return l'instance
	 */
	public static CompanyService getInstance() {
		
		if (instance == null) {
			instance = new CompanyService();
		}

		return instance;
	}
	
	private CompanyService() {
		
	}

	/**
	 * Liste les fabricants.
	 * @return la liste des fabricants
	 */
	public List<Company> getList() {
		
		return companyDAO.getList();
		
	}
	
	/**
	 * Récupère un fabricant identifé par id.
	 * @param id, l'identifiant du fabricant.
	 * @return le fabricant.
	 */
	public Company getDetails(int id) {
		return companyDAO.getByID(id).orElse(new Company());
	}
	
	public void delete(int id) {
		companyDAO.delete(id);
	}

}
