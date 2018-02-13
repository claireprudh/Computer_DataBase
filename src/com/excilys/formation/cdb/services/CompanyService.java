package com.excilys.formation.cdb.services;

import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.persistence.CompanyDAO;

/**
 * @author excilys
 *
 */
public class CompanyService {

	private static CompanyService instance;
	
	public static CompanyService getInstance() {
		CompanyService c;
		
		if (instance == null) {
			c = new CompanyService();
		}
		else {
			c = instance;
		}
		
		return c;
	}

	public List<String> getListCompanies() {
		
		List<String> ls = new ArrayList<String>();
		List<Company> lp = CompanyDAO.getInstance().getListCompanies();
		
		for(Company c : lp) {
			
			ls.add(c.getName());
		}
		
		
		return ls;
	}

}
