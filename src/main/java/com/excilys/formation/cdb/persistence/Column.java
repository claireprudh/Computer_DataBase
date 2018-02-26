/**
 * 
 */
package com.excilys.formation.cdb.persistence;

/**
 * @author excilys
 *
 */
public enum Column {

	
	CID("id"),
	CNAME("name"),
	CDATE_OF_INTRO("introduced"),
	CDATE_OF_DISC("discontinued"),
	CCOMPANY_ID("company_id"),
	CCOUNT("COUNT(*)");
	
	private String name;
	
	Column(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	
	
}
