/**
 * 
 */
package com.excilys.formation.cdb.services;

import java.util.ArrayList;
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
	
	
	public List<String> getListComputers(){
		
		List<Computer> lp = ComputerDAO.getInstance().getListComputer().orElse(new ArrayList<Computer>());
		List<String> ls = new ArrayList<String>();
		for(Computer c : lp) {
			
			ls.add(c.getName());
			
		}
		
		
		
		return ls;
	}
	
	public Computer getDetails(int id) {
		Computer c = ComputerDAO.getInstance().getComputerById(id).orElse(new Computer());
		return c;
	}


	public void createNewComputer(Computer c) {
		
		ComputerDAO.getInstance().createNewComputer(c);
		
	}
	
	public void updateComputer(Computer c) {
		ComputerDAO.getInstance().updateComputer(c);
	}

}
