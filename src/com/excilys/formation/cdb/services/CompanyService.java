package com.excilys.formation.cdb.services;


import java.util.List;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.persistence.CompanyDAO;

/**
 * @author excilys
 *
 */
public class CompanyService {

	
	/**
	 * instance, l'instance de CompanyService pour appliquer le pattern Singleton.
	 */
	private static CompanyService instance;
	
	/**
	 * Méthode permettant de récupérer l'instance du Singleton
	 * @return l'instance
	 */
	public static CompanyService getInstance() {
		
		if (instance == null) {
			instance = new CompanyService();
		}

		return instance;
	}

	/**
	 * Liste les fabricants.
	 * @return la liste des fabricants
	 */
	public List<String> getListCompanies() {
		
		return CompanyDAO.getInstance().getListCompanies();
		
	}
	
	/**
	 * Récupère un fabricant identifé par id.
	 * @param id, l'identifiant du fabricant.
	 * @return le fabricant.
	 */
	public Company getCompanyDetails(int id) {
		return CompanyDAO.getInstance().getCompanyByID(id).orElse(new Company());
	}

}
