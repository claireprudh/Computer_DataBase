/**
 * 
 */
package com.excilys.formation.cdb.ihm;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.services.CompanyService;
import com.excilys.formation.cdb.services.ComputerService;

/**
 * @author excilys
 *
 */
public class IHM {

	static final Logger LOGGER = LogManager.getLogger(IHM.class);
	static final int COMPUTER_BY_PAGE = 500;

	public static void main(String[] args) {

		Scanner scan = new Scanner(System.in);

		System.out.println("Bienvenue dans l'application,\n");
		for (String s : Command.listcommands(TypeCommand.ARGUMENT_LESS)) {
			System.out.println(s);
		}
		for (String s : Command.listcommands(TypeCommand.ARGUMENT_NEEDED)) {
			System.out.println(s);
		}
		System.out.print("\n> ");

		StringBuilder str = new StringBuilder();
		Command command;

		do {


			str.append(scan.nextLine());
			command = Command.convert(str.toString());


			switch (command) {
			case LIST_COMPUTERS :
				for (Computer s : ComputerService.getInstance().getList()) {
					System.out.println(s);
				}
				break;

			case LIST_COMPUTERS_PAGED :
				displayPages(scan, str, command);

				break;

			case LIST_COMPANIES :
				for (String s : CompanyService.getInstance().getList()) {
					System.out.println(s);
				}
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

			case DELETE_COMPUTER :
				deleteComputer(scan, str, command);
				break;

			case HELP :
				for (String s : Command.listcommands(TypeCommand.ARGUMENT_LESS)) {
					System.out.println(s);
				}
				for (String s : Command.listcommands(TypeCommand.ARGUMENT_NEEDED)) {
					System.out.println(s);
				}

				break;

			case EXIT :
				break;

			default : LOGGER.error("commande non reconnue");
			break;
			}

			if (command != Command.EXIT) {
				System.out.print("\n>");
			}
			str.setLength(0);

		} while (command != Command.EXIT);



		scan.close();

	}

	/**
	 * 
	 * @param scan
	 * @param str
	 * @param command
	 */
	private static void displayPages(Scanner scan, StringBuilder str, Command command) {

		str.setLength(0);
		Page page = new Page(COMPUTER_BY_PAGE, 1);
		System.out.println(page.toString());

		do {

			str.append(scan.nextLine());
			command = Command.convert(str.toString());

			switch (command) {
			case NEXT :
				System.out.println(page.nextPage());

				break;

			case PREVIOUS :

				System.out.println(page.previousPage());

				break;

			case RETURN :
				break;

			default :
				LOGGER.error("commande non reconnue");
				break;
			}

			str.setLength(0);

		} while (command != Command.RETURN);

		Page.noPage = 1;

	}

	/**
	 *
	 * @param scan
	 * @param str
	 * @param command
	 */
	private static void createNewComputer(Scanner scan, StringBuilder str, Command command) {


		str.setLength(0);

		StringBuilder message = new StringBuilder();

		message.append("Création d'un ordinateur :\n");

		Computer computer = new Computer();
		fillComputer(scan, str, command, computer, message);

		if (computer.getName() != null) {
			ComputerService.getInstance().createNew(computer);

		} else {
			System.out.println("Le nom est obligatoire pour la création d'un nouvel ordinateur, sortie");
		}

	}
	
	/**
	 * 
	 * @param scan
	 * @param str
	 * @param command
	 * @param computer
	 * @param message
	 */
	private static void fillComputer(Scanner scan, StringBuilder str, Command command, Computer computer, StringBuilder message) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
		String dateInString;
		
		System.out.println("Commandes :");
		for (String s : Command.listcommands(TypeCommand.ARGUMENT)) {
			System.out.println(s + " \n");
		}
		
		do {
			
			System.out.println(message);
			
			str.setLength(0);
			str.append(scan.nextLine());

			
			

			try {
				command = Command.convert(str.substring(0, str.indexOf(" =")).trim());
			} catch (java.lang.StringIndexOutOfBoundsException e) {
				command = Command.convert(str.toString());
			}			
			

			switch (command) {
			case NAME : 
				String name = str.substring(str.indexOf("=")).replace('=', ' ').trim();
				computer.setName(name);
				message.append(Command.NAME + " : " + name + "\n");
				break;

			case DATE_OF_INTRO : 

				
				dateInString = str.substring(str.indexOf("=")).replace('=', ' ').trim();

				try {

					Date date = new Date(formatter.parse(dateInString).getTime());

					computer.setDateOfIntro(date.toLocalDate());
					message.append(Command.DATE_OF_INTRO + " : " + date + "\n");

				} catch (java.text.ParseException e) {
					LOGGER.error("Erreur de parsing de la date");

				}
				break;

			case DATE_OF_DISC : 

				dateInString = str.substring(str.indexOf("=")).replace('=', ' ').trim();

				try {

					Date date = new Date(formatter.parse(dateInString).getTime());

					computer.setDateOfDisc(date.toLocalDate());
					message.append(Command.DATE_OF_DISC + " : " + date + "\n");

				} catch (java.text.ParseException e) {
					LOGGER.error("Erreur de parsing de la date");

				}
				break;

			case COMPANY_ID : 
				Company company = CompanyService.getInstance().getDetails(
						Integer.valueOf(str.substring(str.indexOf("=")).replace('=', ' ').trim()));

				computer.setCompany(company);

				message.append(Command.COMPANY_ID + " : " + company + "\n");

				break;			

			case RETURN :
				break;

			default : LOGGER.error("commande non reconnue");
			break;

			}


			str.setLength(0);


		} while (command != Command.RETURN);
		
	}

	/**
	 * 
	 * @param scan
	 * @param str
	 * @param command
	 */
	private static void updateComputer(Scanner scan, StringBuilder str, Command command) {

		Computer computer = new Computer();
		

		StringBuilder message = new StringBuilder();

		message.append("Veuillez indiquer l\'id de l\'ordinateur à mettre à jour :\n");

		do {

			System.out.println(message);
			System.out.print("\n>");
			str.setLength(0);
			str.append(scan.nextLine());


			try {

				command = Command.convert(str.substring(0, str.indexOf(" =")).trim());

			} catch (java.lang.StringIndexOutOfBoundsException e) {
				command = Command.convert(str.toString());
			}

			switch (command) {

			case COMPUTER_ID : 
				int computerID = Integer.valueOf(str.substring(str.indexOf("=")).replace('=', ' ').trim());
				computer.setId(computerID);	
				message.append(Command.COMPUTER_ID + " : " + computerID + "\n");
				
				fillComputer(scan, str, command, computer, message);
				break;				

			case RETURN :
				break;

			default : LOGGER.error("commande non reconnue");
			break;
			}

		} while (command != Command.RETURN);

		if (computer.getId() != 0) {			
			ComputerService.getInstance().update(computer);			
		} else {
			System.out.println("ID non mentionné, pas de changement, sortie");
		}

	}

	/**
	 * 
	 * @param scan
	 * @param str
	 * @param command
	 */
	private static void getComputer(Scanner scan, StringBuilder str, Command command) {

		str.setLength(0);


		do {
			System.out.print("Indiquez l'ID de l'ordinateur souhaité :\n"
					+ Command.COMPUTER_ID + " = <computer_id> : ID de l'ordinateur\n"
					+ Command.RETURN + " : Sortir\n"
					+ ">");

			str.append(scan.nextLine());


			try {

				command = Command.convert(str.substring(0, str.indexOf(" =")).trim());

			} catch (java.lang.StringIndexOutOfBoundsException e) {
				command = Command.convert(str.toString());
			}

			switch (command) {

			case COMPUTER_ID : 
				System.out.println(
						ComputerService.getInstance().getDetails(
								Integer.valueOf(str.substring(str.indexOf("=")).replace('=', ' ').trim())));			
				break;			

			case RETURN :
				break;

			default : LOGGER.error("commande non reconnue");
			break;
			}


			str.setLength(0);


		} while (command != Command.RETURN);


	}

	/**
	 * 
	 * @param scan
	 * @param str
	 * @param command
	 */
	private static void getCompany(Scanner scan, StringBuilder str, Command command) {

		str.setLength(0);


		do {
			System.out.print("Indiquez l'ID du fabricant souhaité :\n"
					+ Command.COMPANY_ID + " = <company_id> : ID du fabricant\n"
					+ Command.RETURN + " : Sortir\n"
					+ ">");

			str.append(scan.nextLine());


			try {

				command = Command.convert(str.substring(0, str.indexOf(" =")).trim());

			} catch (java.lang.StringIndexOutOfBoundsException e) {
				command = Command.convert(str.toString());
			}

			switch (command) {

			case COMPANY_ID : 
				System.out.println(
						CompanyService.getInstance().getDetails(
								Integer.valueOf(str.substring(str.indexOf("=")).replace('=', ' ').trim())));			
				break;			

			case RETURN :
				break;

			default : LOGGER.error("commande non reconnue");
			break;
			}


			str.setLength(0);

		} while (command != Command.RETURN);


	}

	/**
	 * 
	 * @param scan
	 * @param str
	 * @param command
	 */
	private static void deleteComputer(Scanner scan, StringBuilder str, Command command) {

		int computerErased = 0;

		str.setLength(0);

		System.out.print("Indiquez l'ID de l'ordinateur souhaité :\n"
				+ Command.COMPUTER_ID + " = <computer_id> : ID de l'ordinateur\n"
				+ Command.RETURN + " : Sortir\n"
				+ ">");

		do {


			str.append(scan.nextLine());


			try {

				command = Command.convert(str.substring(0, str.indexOf(" =")).trim());

			} catch (java.lang.StringIndexOutOfBoundsException e) {
				command = Command.convert(str.toString());
			}

			switch (command) {

			case COMPUTER_ID : 
				ComputerService.getInstance().deleteComputer(
						Integer.valueOf(str.substring(str.indexOf("=")).replace('=', ' ').trim()));	
				computerErased++;
				break;			

			case RETURN :

				System.out.println(computerErased + "ordinateurs effacés !");
				break;

			default : LOGGER.error("commande non reconnue");
			break;
			}


			str.setLength(0);
			System.out.println(">");


		} while (command != Command.RETURN);


	}
	

}
