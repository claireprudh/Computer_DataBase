/**
 * 
 */
package com.excilys.formation.cdb.ihm;

/**
 * @author excilys
 *
 */
public enum Command {
	
	//Commandes sans arguments
	LIST_COMPUTERS ("listcomputers"),
	LIST_COMPANIES ("listcompanies"),
	EXIT ("exit"),
	RETURN("return"),
	DEFAULT("default"),
	
	//Commandes avec arguments
	GET_COMPUTER ("getcomputer" ),
	GET_COMPANY ("getcompany"),
	CREATE_COMPUTER ("createcomputer"),
	UPDATE_COMPUTER ("updatecomputer"),
	DELETE_COMPUTER ("deletecomputer"),
	
	//arguments
	COMPANY_ID ("companyid"),
	COMPUTER_ID ("computerid"),
	NAME("name"),
	DATE_OF_INTRO("introduced"),
	DATE_OF_DISC("discontinued")
	
	;
	
	private String name;
	
	Command(String name) {
		this.name = name;
	}
	
	public String toString() {
		return this.name;
	}
	
	public static Command ValueOf(String s) {
		Command command = DEFAULT;
		for(Command c : Command.values()) {
			if(s.equals(c.name)) {
				command = c;
			}
			
		}
		return command;
		
	}
	

}
