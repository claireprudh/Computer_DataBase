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
	
	public Company(int id, String name) {
		this.setId(id);
		this.setName(name);
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
	
	
	public boolean equals(Company other) {
		
		if (this.getId() == other.getId() && this.getName().equals(other.getName())) {
			return true;
			
		}
		else {
		
			return false;
		
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		Company other = (Company) obj;
		if (this.getId() == other.getId()) {
			return true;
			
		}
		else {
		
			return false;
		
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}


}
