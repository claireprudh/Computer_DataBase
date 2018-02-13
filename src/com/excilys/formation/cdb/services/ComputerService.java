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
	

	private static ComputerService instance;
	
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
	
	
	public List<Computer> getListComputers(){
		System.out.println(this.getClass().getSimpleName());
		List<Computer> lp = ComputerDAO.getInstance().getListComputer();
		
		
		
		return lp;
	}

}
