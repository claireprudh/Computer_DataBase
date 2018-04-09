/**
 * 
 */
package com.excilys.formation.cdb.persistence;

/**
 * @author excilys
 *
 */
public enum Column {

	
	CID("computer.id"),
	CCID("company.id"),
	CNAME("computer.name"),
	CCNAME("company.name"),
	CDATE_OF_INTRO("computer.introduced"),
	CDATE_OF_DISC("computer.discontinued"),
	CCOMPANY_ID("computer.company_id"),
	CCOUNT("COUNT(*)");
	
	private String name;
	
	Column(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	
	
}
