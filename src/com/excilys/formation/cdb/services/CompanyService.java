package com.excilys.formation.cdb.services;

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

}
