/**
 * 
 */
package com.excilys.formation.cdb.ihm;

import java.util.Scanner;

import com.excilys.formation.cdb.services.CompanyService;
import com.excilys.formation.cdb.services.ComputerService;

/**
 * @author excilys
 *
 */
public class IHM {
	
	public static void main(String[] args) {
	
		Scanner scan = new Scanner(System.in);
		System.out.print("Bienvenue dans l'application,\n"
				+ "computerslist. : afficher la liste des ordinateurs\n"
				+ "computer.<id> : affiche les détails de l'ordinateur no <id> \n"
				+ "company.<id> : affiche les détails du fabricant <id>\n"
				+ "> " );
		
		StringBuilder str = new StringBuilder();
		str.append(scan.nextLine());
		
		
		String str1 = str.substring(0, str.indexOf(".")).trim();
		System.out.println(str1);
		
		switch(str1) {
		case "computerslist" : 
			for(String s : ComputerService.getInstance().getListComputers())
			System.out.println(s);
		break;
		
		case "computer" : 
			String str2 = str.substring(str.indexOf(".")).replace('.', ' ').trim();
			System.out.println(ComputerService.getInstance().getDetails(Integer.valueOf(str2)));
		
		break;
		
		case "company" : 
			String str3 = str.substring(str.indexOf(".")).replace('.', ' ').trim();
			System.out.println(CompanyService.getInstance().getCompanyDetails(Integer.valueOf(str3)));
		break;
		default : System.out.println("je suis dans default");
		break;
		}
		
		
		
		scan.close();
		
	}

}
