/**
 * 
 */
package com.excilys.formation.cdb.model;

import java.time.LocalDate;
import java.util.Optional;

/**
 * @author excilys
 *
 */

public class Computer {
	
	int id;
	String name;
	LocalDate dateOfIntro;
	LocalDate dateOfDisc;
	Company company;
	
	
	public Computer() {
		
	}
	
	public Computer(int id) {
		this.setId(id);
	}
	
	public Computer(String name, LocalDate dateOfIntro, LocalDate dateOfDisc, Company company) {
		
		this.setName(name);
		this.setDateOfIntro(dateOfIntro);
		this.setDateOfDisc(dateOfDisc);
		this.setCompany(company);
	}
	
	public Computer(int id, String name, LocalDate dateOfIntro, LocalDate dateOfDisc, Company company) {
		
		this.setId(id);
		this.setName(name);
		this.setDateOfIntro(dateOfIntro);
		this.setDateOfDisc(dateOfDisc);
		this.setCompany(company);
	}
		
	
	/**
	 * @return
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @return
	 */
	public LocalDate getDateOfIntro() {
		return dateOfIntro;
	}
	
	/**
	 * @param dateOfIntro
	 */
	public void setDateOfIntro(LocalDate dateOfIntro) {
		this.dateOfIntro = dateOfIntro;
	}
	
	/**
	 * @return
	 */
	public LocalDate getDateOfDisc() {
		return dateOfDisc;
	}
	
	/**
	 * @param dateOfDisc
	 */
	public void setDateOfDisc(LocalDate dateOfDisc) {
		this.dateOfDisc = dateOfDisc;
	}
	
	/**
	 * @return
	 */
	public Optional<Company> getCompany() {
		return Optional.ofNullable(company);
	}
	
	/**
	 * @param companyID
	 */
	public void setCompany(Company companyID) {
		this.company = companyID;
	}
	
	@Override
	public String toString() {
		String s = "Computer name : " + this.name + " \nid : " + this.id + " \ndate of introduction : " + this.dateOfIntro
				+ " \ndate of discontinuity : " + this.dateOfDisc + " \nmanufacturer : " + this.company + "\n\n";
		return s;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		Computer other = (Computer) obj;
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
