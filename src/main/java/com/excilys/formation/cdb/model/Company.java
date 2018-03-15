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
	
	public Company(int id) {
		this.setId(id);		
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		String s = "\n\tCompany name : " + this.name + " \n\tid : " + this.id;
		return s;
	}
	
	
	public boolean equals(Company other) {
		
		return (this.getId() == other.getId() && this.getName().equals(other.getName())); 

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Company other = (Company) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}

	

	

}
