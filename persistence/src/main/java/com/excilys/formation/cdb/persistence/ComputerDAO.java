/**
 * 
 */
package com.excilys.formation.cdb.persistence;


import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.excilys.formation.cdb.model.Computer;

/**
 * @author excilys
 *
 */
@Repository
public class ComputerDAO {

	static final Logger LOGGER = LogManager.getLogger(ComputerDAO.class);

	/*
	 * Queries q-
	 */
	private static final String SELECT = "SELECT ";
	private static final String FROM = " FROM Computer computer ";
	private static final String WHERE = " WHERE ";
	private static final String WHERE_ID = WHERE + Column.CID.getName() + " = :id";
	private static final String LIKE = " LIKE :search ";	
	
	
	private static final String SELECT_ALL = "SELECT computer FROM Computer computer ";	
	
	private static final String Q_GET_COMPUTER_BY_ID = SELECT_ALL + WHERE_ID;
	
	private static final String Q_DELETE_COMPUTER = "DELETE FROM Computer computer" + WHERE_ID;
	
	private static final String Q_GET_SEARCH_COUNT = SELECT + Column.CCOUNT.getName() + FROM
			+ WHERE + Column.CNAME.getName() + LIKE + " or " + Column.CCNAME.getName() + LIKE;
	private static final String Q_SEARCH_BY_NAME = SELECT_ALL
			+ WHERE + Column.CNAME.getName() + LIKE + " or " + Column.CCNAME.getName() + LIKE;
	

	/**
	 * Retourne la liste complète des ordinateurs.
	 * @return la liste des ordinateurs.
	 */
	public List<Computer> getList() {

		List<Computer> listComputers = new ArrayList<>();

		
		try (Session session = HibernateConfig.getSessionFactory().openSession();) {
			Query<Computer> q1 = session.createQuery(SELECT_ALL, Computer.class);
			
			listComputers = q1.list();
		}
		

		return listComputers;
	}

	/**
	 * Recherche en base le Computer ayant l'ID indiqué.
	 * @param id, l'id du Computer recherché.
	 * @return computer, le Computer à l'id recherché ou null 
	 */
	public Computer getByID(int id) {
		
		Computer computer;
		
		try (Session session = HibernateConfig.getSessionFactory().openSession();) {
			Query<Computer> q1 = session.createQuery(Q_GET_COMPUTER_BY_ID, Computer.class);
			q1.setParameter("id", id);
			
			computer = q1.uniqueResult();
		}

		return computer;
	}

	/**
	 * Création d'un ordinateur en base.
	 * @param computer, l'ordinateur à insérer dans la base.
	 */
	public void create(Computer computer) {

		try (Session session = HibernateConfig.getSessionFactory().openSession()) {
			session.save(computer);
		}
			
	}


	/**
	 * Modification d'un Computer.
	 * @param ucomputer, the updated computer
	 */
	public void update(Computer ucomputer) {

		if (this.getByID(ucomputer.getId()) != null) { 

			try (Session session = HibernateConfig.getSessionFactory().openSession()) {
				session.beginTransaction();
				session.update(ucomputer);
				session.getTransaction().commit();
			}

		} else {
			LOGGER.error("Pas d'ordinateur reçu à mettre à jour");
		}

	}

	/**
	 * Suppression de l'ordinateur à l'ID spécifié.
	 * @param id, l'id de l'ordinateur concerné.
	 */
	public void delete(int id) {

		try (Session session = HibernateConfig.getSessionFactory().openSession();) {
			Query<?> q = session.createQuery(Q_DELETE_COMPUTER);
			q.setParameter("id", id);
			
			session.beginTransaction();
			q.executeUpdate();
			session.getTransaction().commit();
			
			session.close();
		}
			
	}

	public List<Computer> searchByName(int nbComputer, int noPage, String part) {

		part = "%" + part + "%";

		int offset = (noPage - 1) * nbComputer;
		List<Computer> listComputers;
		

		try (Session session = HibernateConfig.getSessionFactory().openSession();) {
			Query<Computer> q1 = session.createQuery(Q_SEARCH_BY_NAME, Computer.class);
			q1.setParameter("search", part);
			q1.setFirstResult(offset);
			q1.setMaxResults(nbComputer);
		
			listComputers = q1.list();
		}
		
		
		return listComputers;
	}

	public int getSearchCount(String part) {
		Long count;
		part = "%" + part + "%";
		
		try (Session session = HibernateConfig.getSessionFactory().openSession();) {
			Query<Long> q1 = session.createQuery(Q_GET_SEARCH_COUNT, Long.class);
			q1.setParameter("search", part);
			count = q1.uniqueResult();
		}

		return count.intValue();
		
	}
	
}



