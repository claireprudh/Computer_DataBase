/**
 * 
 */
package com.excilys.formation.cdb.ihm;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.services.CompanyService;
import com.excilys.formation.cdb.services.ComputerService;

/**
 * @author excilys
 *
 */
public class IHM {
	
	final static Logger logger = Logger.getLogger(IHM.class);
	
	public static void main(String[] args) {
	
		Scanner scan = new Scanner(System.in);
		System.out.print("Bienvenue dans l'application,\n"
				+ Command.LIST_COMPUTERS.toString() + " : afficher la liste des ordinateurs\n"
				+ Command.LIST_COMPANIES.toString() + " : afficher la liste des fabricants\n"
				+ Command.GET_COMPUTER.toString() +" : affiche les détails de l'ordinateur no <id> \n"
				+ "\tid = <id> : précise l'id\n"
				+ Command.GET_COMPANY.toString() +" : affiche les détails du fabricant <id>\n"
				+ "\tid = <id> : précise l'id\n"
				+ Command.CREATE_COMPUTER.toString() +" : crée un nouvel ordinateur\n"
				+ "name = <name> : le nom de l'ordinateur\n"
				+ "introduced = <introduced YYYY/MM/DD> : la date d'introduction de l'ordinateur\n"
				+ "discontinued = <discontinued YYYY/MM/DD> : la date de discontinuité de l'ordinateur \n"
				+ "company_id = <company_id> : l'id de son fabricant\n"
				+ Command.RETURN.toString() +" : sortir \n"
				+ Command.EXIT.toString() + " : fermer le programme"
				+ "\n> " );
		
		StringBuilder str = new StringBuilder();
		Command command;
		
		do {
			str.append(scan.nextLine());
			command = Command.ValueOf(str.toString());
		
			switch(command) {
			case LIST_COMPUTERS : 
				for(String s : ComputerService.getInstance().getListComputers())
				System.out.println(s);
			break;
			
			case LIST_COMPANIES : 
				for(String s : CompanyService.getInstance().getListCompanies())
				System.out.println(s);
			break;
			
			case GET_COMPUTER : 
				int idComputer =  Integer.valueOf(str.substring(str.indexOf(".")).replace('.', ' ').trim());
				System.out.println(ComputerService.getInstance().getDetails(Integer.valueOf(idComputer)));
			
			break;
			
			case GET_COMPANY : 
				int idCompany = Integer.valueOf(str.substring(str.indexOf(".")).replace('.', ' ').trim());
				System.out.println(CompanyService.getInstance().getCompanyDetails(Integer.valueOf(idCompany)));
			break;
			
			case CREATE_COMPUTER : 
				createNewComputer(scan, str, command);
				
			break;
			
			case EXIT :
				break;
			
			default : logger.error("commande non reconnue");
			break;
			}
			
			str.setLength(0);
			
		}while (command != Command.EXIT) ;
		
		
		
		scan.close();
		
	}
	
	private static void createNewComputer (Scanner scan, StringBuilder str, Command command) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		String dateInString;
		
		Computer computer = new Computer();
		str.setLength(0);
		
		
		do {
			str.append(scan.nextLine());
			
			
			try {

				command = Command.ValueOf(str.substring(0,str.indexOf(" =")).trim());
				
			}catch(java.lang.StringIndexOutOfBoundsException e) {
				command = Command.ValueOf(str.toString());
			}
			
			switch(command) {
			case NAME : 
				computer.setName(str.substring(str.indexOf("=")).replace('=', ' ').trim());
			break;
			
			case DATE_OF_INTRO : 
				
				dateInString = str.substring(str.indexOf("=")).replace('=', ' ').trim();

		        try {

		            Date date = new Date(formatter.parse(dateInString).getTime());
				
		            computer.setDateOfIntro(date.toLocalDate());
		        	
		        } catch (java.text.ParseException e) {
					logger.error("Erreur de parsing de la date");
					
				}
			break;
			
			case DATE_OF_DISC : 
				
				dateInString = str.substring(str.indexOf("=")).replace('=', ' ').trim();

		        try {

		            Date date = new Date(formatter.parse(dateInString).getTime());
				
		            computer.setDateOfDisc(date.toLocalDate());
		        	
		        } catch (java.text.ParseException e) {
					logger.error("Erreur de parsing de la date");
					
				}
			break;
			
			case ID : 
				computer.setCompany(CompanyService.getInstance().getCompanyDetails(
						Integer.valueOf(str.substring(str.indexOf("=")).replace('=', ' ').trim()))
						);
			
			break;			
			
			case RETURN :
				break;
			
			default : logger.error("commande non reconnue");
			break;
			}
			
			
			str.setLength(0);
			
			
		}while (command != Command.RETURN) ;
		
		ComputerService.getInstance().createNewComputer(computer);
		System.out.println("Ordinateur créé avec succès");
	}

}
