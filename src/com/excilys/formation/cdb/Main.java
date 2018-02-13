/**
 * 
 */
package com.excilys.formation.cdb;

import java.util.List;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.services.ComputerService;

/**
 * @author excilys
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("Main");
		List<Computer> l = ComputerService.getInstance().getListComputers();
		
		for(Computer c : l) {
			System.out.println(c);
		}

	}

}
