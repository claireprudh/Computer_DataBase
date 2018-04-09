/**
 * 
 */
package com.excilys.formation.cdb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * @author excilys
 *
 */
@Entity
@Table(name = "company")
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
	
	@Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
		
	}
	
	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "name", nullable = false)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "\n\tCompany name : " + this.name + " \n\tid : " + this.id;
		
	}
	
	
	public boolean equals(Company other) {
		
		return (this.getId() == other.getId() && this.getName().equals(other.getName())); 

	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj && this.getClass() == obj.getClass()) {
			Company other = (Company) obj;
			
			return (id == other.id);
		} else {
			return false;
		}
		
		
	}

	

	

}
