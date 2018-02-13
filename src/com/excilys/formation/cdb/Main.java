/**
 * 
 */
package com.excilys.formation.cdb;



import java.time.LocalDate;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
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

		Company cp = CompanyService.getInstance().getCompanyDetails(12);
		Computer c = new Computer("MonPc", LocalDate.of(2018,  02,  13), null, cp);
		ComputerService.getInstance().createNewComputer(c);

	}

}
