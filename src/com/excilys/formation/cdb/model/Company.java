/**
 * 
 */
package com.excilys.formation.cdb.model;

/**
 * @author excilys
 *
 */
public class Company {
	
	
	int id;
	String name;
	
	public Company() {
		
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		String s = "\n\tCompany name : " + this.name + " \n\tid : " + this.id;
		return s;
	}


}
