/**
 * 
 */
package com.excilys.formation.cdb.persistence;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.excilys.formation.cdb.model.Company;

/**
 * @author excilys
 *
 */
@Repository
public class CompanyDAO {

	static final Logger LOGGER  = LogManager.getLogger(CompanyDAO.class);
	
	/**
	 * Récupère la liste des fabricants.
	 * @return la liste des fabricants.
	 */
	public List<Company> getList() {
		List<Company> listCompany;

		try (Session session = HibernateConfig.getSessionFactory().openSession();) {
			Query<Company> q1 = session.createQuery("Select company From Company company", Company.class);
			
			listCompany = q1.list();
		}

		return listCompany;
		
	}
	


}