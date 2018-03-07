/**
 *
 */
package com.excilys.formation.cdb.ihm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author excilys
 *
 */
public enum Command {

	//Commandes sans arguments
	LIST_COMPUTERS(TypeCommand.ARGUMENT_LESS, "listcomputers", "afficher la liste des ordinateurs"),
	LIST_COMPUTERS_PAGED(TypeCommand.ARGUMENT_LESS, "pagecomputers", "afficher la liste des ordinateurs page par page"),
	LIST_COMPANIES(TypeCommand.ARGUMENT_LESS, "listcompanies", "afficher la liste des fabricants"),
	EXIT(TypeCommand.ARGUMENT_LESS, "exit", "fermer le programme"),
	RETURN(TypeCommand.ARGUMENT_LESS, "return", "Sortir"),
	DEFAULT(TypeCommand.ARGUMENT_LESS, "default", ""),
	HELP(TypeCommand.ARGUMENT_LESS, "help", "affiche les commandes"),

	//Commandes avec arguments
	GET_COMPUTER(TypeCommand.ARGUMENT_NEEDED, "getcomputer", "affiche les détails de l'ordinateur no <id>"),
	GET_COMPANY(TypeCommand.ARGUMENT_NEEDED, "getcompany", "affiche les détails du fabricant <id>"),
	CREATE_COMPUTER(TypeCommand.ARGUMENT_NEEDED, "createcomputer", "crée un nouvel ordinateur"),
	UPDATE_COMPUTER(TypeCommand.ARGUMENT_NEEDED, "updatecomputer", "applique des changements sur un ordinateur"),
	DELETE_COMPUTER(TypeCommand.ARGUMENT_NEEDED, "deletecomputer", "supprime un ordinateur"),
	DELETE_COMPANY(TypeCommand.ARGUMENT_NEEDED, "deletecompany", "supprime un fabricant"),

	//arguments
	COMPUTER_ID(TypeCommand.ARGUMENT, "computerid", "ID de l'ordinateur à modifier"),
	NAME(TypeCommand.ARGUMENT, "name", "Nom du pc"),
	DATE_OF_INTRO(TypeCommand.ARGUMENT, "introduced", "Date d'introduction de l'ordinateur <YYYY/MM/DD>"),
	DATE_OF_DISC(TypeCommand.ARGUMENT, "discontinued", "Date de discontinuité de l'ordinateur <YYYY/MM/DD>"),
	COMPANY_ID(TypeCommand.ARGUMENT, "companyid", "ID du fabricant"),

	//Commandes de page
	NEXT(TypeCommand.PAGE, "next", "Affiche la page suivante"),
	PREVIOUS(TypeCommand.PAGE, "previous", "Affiche la page précédente");

	private String name;
	private String details;
	private TypeCommand typeCommand;


	/**
	 * Constructeur de l'enum.
	 * @param typeCommand the type of the command
	 * @param name the name of the command
	 * @param details the details of the command
	 */
	Command(TypeCommand typeCommand, String name, String details) {
		this.typeCommand = typeCommand;
		this.name = name;
		this.details = details;
	}

	/**
	 * @return name, le nom de l'enum
	 */
	@Override
	public String toString() {
		return this.name;
	}

	/**
	 * .
	 * @param s the String to transform
	 * @return Command
	 */
	public static Command convert(String s) {
		Command command = DEFAULT;
		for (Command c : Command.values()) {
			if (s.equals(c.name)) {
				command = c;
			}

		}
		return command;

	}

	public String getDetails() {
		return this.details;
	}

	public TypeCommand getTypeCommand() {
		return this.typeCommand;
	}

	/**
	 * Lister les commandes d'un type.
	 * @param type le type des commandes à lister
	 * @return listCommands la list des commandes du type
	 */
	public static List<String> listcommands(TypeCommand type) {
		List<String> listCommands = new ArrayList<String>();

		for (Command c : values()) {
			if (c.typeCommand.equals(type)) {
				listCommands.add(c.name + " : " + c.details);
			}
		}

		return listCommands;

	}

}
