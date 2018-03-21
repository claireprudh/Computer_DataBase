package com.excilys.formation.cdb.services;


import java.util.List;

import org.springframework.stereotype.Service;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.persistence.CompanyDAO;

/**
 * @author excilys
 *
 */
@Service
public class CompanyService {

	private final CompanyDAO companyDAO;
	
	public CompanyService(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
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
	
}
