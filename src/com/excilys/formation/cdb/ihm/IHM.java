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
				+ Command.GET_COMPANY.toString() +" : affiche les détails du fabricant <id>\n"
				+ Command.CREATE_COMPUTER.toString() +" : crée un nouvel ordinateur\n"
				+ Command.UPDATE_COMPUTER.toString() +"\n"
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
				getComputer(scan, str, command);
			break;
			
			case GET_COMPANY : 
				getCompany(scan, str, command);
			break;
			
			case CREATE_COMPUTER : 
				createNewComputer(scan, str, command);
				
			break;
			
			case UPDATE_COMPUTER :
				updateComputer(scan, str, command);
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
			System.out.println("Création d'un ordinateur :\n"
					+ Command.NAME + " = <name> : Nom du pc\n"
					+ Command.DATE_OF_INTRO + " = <YYYY/MM/DD> : Date d'introduction de l'ordinateur\n"
					+ Command.DATE_OF_DISC + " = <YYYY/MM/DD> : Date de discontinuité de l'ordinateur\n"
					+ Command.COMPANY_ID + " = <company_id> : ID du fabricant\n"
					+ Command.RETURN + " : Sortir de la création d'ordinateur");
			
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
			
			case COMPANY_ID : 
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
		
		if(computer.getCompany() != null) {
			ComputerService.getInstance().createNewComputer(computer);
		}
		else {
			System.out.println("Vous n'avez pas renseigné le fabricant de l'ordinateur, annulation de l'opération.");
		}
	}
	
	private static void updateComputer(Scanner scan, StringBuilder str, Command command) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		String dateInString;
		
		Computer computer = new Computer();
		str.setLength(0);
		
		
		do {
			System.out.println("Veuillez caractériser l\'ordinateur à mettre à jour :\n"
					+ Command.COMPUTER_ID + " = <computer_id> : ID de l'ordinateur à modifier\n"
					+ Command.NAME + " = <name> : Nom du pc\n"
					+ Command.DATE_OF_INTRO + " = <YYYY/MM/DD> : Date d'introduction de l'ordinateur\n"
					+ Command.DATE_OF_DISC + " = <YYYY/MM/DD> : Date de discontinuité de l'ordinateur\n"
					+ Command.COMPANY_ID + " = <company_id> : ID du fabricant\n"
					+ Command.RETURN + " : Lancer la mise à jour");
			
			str.append(scan.nextLine());
			
			
			try {

				command = Command.ValueOf(str.substring(0,str.indexOf(" =")).trim());
				
			}catch(java.lang.StringIndexOutOfBoundsException e) {
				command = Command.ValueOf(str.toString());
			}
			
			switch(command) {
			
			case COMPUTER_ID : 
				computer.setId(Integer.valueOf(str.substring(str.indexOf("=")).replace('=', ' ').trim()));			
			break;	
			
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
			
			case COMPANY_ID : 
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
		
		if(computer.getId() != 0) {
			ComputerService.getInstance().updateComputer(computer);
		}
		else {
			System.out.println("ID non mentionné, pas de changement");
		}
		
	}
	
	private static void getComputer (Scanner scan, StringBuilder str, Command command) {

		str.setLength(0);
		
		
		do {
			System.out.println("Indiquez l'ID de l'ordinateur souhaité :\n"
					+ Command.COMPUTER_ID + " = <computer_id> : ID de l'ordinateur\n"
					+ Command.RETURN + " : Sortir");
			
			str.append(scan.nextLine());
			
			
			try {

				command = Command.ValueOf(str.substring(0,str.indexOf(" =")).trim());
				
			}catch(java.lang.StringIndexOutOfBoundsException e) {
				command = Command.ValueOf(str.toString());
			}
			
			switch(command) {
			
			case COMPUTER_ID : 
				System.out.println(
						ComputerService.getInstance().getDetails(
								Integer.valueOf(str.substring(str.indexOf("=")).replace('=', ' ').trim())));			
			break;			
			
			case RETURN :
				break;
			
			default : logger.error("commande non reconnue");
			break;
			}
			
			
			str.setLength(0);
			
			
		}while (command != Command.RETURN) ;
		
		
	}
	
	private static void getCompany (Scanner scan, StringBuilder str, Command command) {

		str.setLength(0);
		
		
		do {
			System.out.println("Indiquez l'ID du fabricant souhaité :\n"
					+ Command.COMPANY_ID + " = <company_id> : ID du fabricant\n"
					+ Command.RETURN + " : Sortir");
			
			str.append(scan.nextLine());
			
			
			try {

				command = Command.ValueOf(str.substring(0,str.indexOf(" =")).trim());
				
			}catch(java.lang.StringIndexOutOfBoundsException e) {
				command = Command.ValueOf(str.toString());
			}
			
			switch(command) {
			
			case COMPANY_ID : 
				System.out.println(
						CompanyService.getInstance().getCompanyDetails(
								Integer.valueOf(str.substring(str.indexOf("=")).replace('=', ' ').trim())));			
			break;			
			
			case RETURN :
				break;
			
			default : logger.error("commande non reconnue");
			break;
			}
			
			
			str.setLength(0);
			
			
		}while (command != Command.RETURN) ;
		
		
	}

}
