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
		
		System.out.println("Main\n");
		
		
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
		
		Company cp2 = CompanyService.getInstance().getCompanyDetails(5);
		ComputerService.getInstance().updateComputer(new Computer(504, "NomChange", LocalDate.of(2018,  03,  13), 
				LocalDate.of(2018,  12,  13), cp2));

		
		ComputerService.getInstance().deleteComputer(503);
	}

}
