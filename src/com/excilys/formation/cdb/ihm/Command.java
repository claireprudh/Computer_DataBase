/**
 * 
 */
package com.excilys.formation.cdb.ihm;

/**
 * @author excilys
 *
 */
public enum Command {
	
	LIST_COMPUTERS ("listcomputers"),
	LIST_COMPANIES ("listcompanies"),
	GET_COMPUTER ("getcomputer" ),
	GET_COMPANY ("getcompany"),
	CREATE_COMPUTER ("createcomputer"),
	UPDATE_COMPUTER ("updatecomputer"),
	DELETE_COMPUTER ("deletecomputer"),
	EXIT ("exit"),
	RETURN("return"),
	DEFAULT("default")
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
