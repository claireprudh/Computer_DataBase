/**
 * 
 */
package com.excilys.formation.cdb;



import com.excilys.formation.cdb.services.CompanyService;
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
		
		
		for(String s : ComputerService.getInstance().getListComputers()) {
			System.out.println(s);
		}
		
		
		System.out.println("\n\n\nCompanies");
		for(String s : CompanyService.getInstance().getListCompanies()) {
			System.out.println(s);
		}
		
		
		
		System.out.println("\n\n\n" + ComputerService.getInstance().getDetails(569));

	}

}
