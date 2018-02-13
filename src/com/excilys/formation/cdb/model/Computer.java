/**
 * 
 */
package com.excilys.formation.cdb.model;

import java.time.LocalDate;

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
		this.setCompanyID(company);
	}
	
	public Computer(int id, String name, LocalDate dateOfIntro, LocalDate dateOfDisc, Company company) {
		
		this.setId(id);
		this.setName(name);
		this.setDateOfIntro(dateOfIntro);
		this.setDateOfDisc(dateOfDisc);
		this.setCompanyID(company);
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
	public Company getCompanyID() {
		return company;
	}
	
	/**
	 * @param companyID
	 */
	public void setCompanyID(Company companyID) {
		this.company = companyID;
	}
	
	@Override
	public String toString() {
		String s = "Computer name : " + this.name + " \nid : " + this.id + " \ndate of introduction : " + this.dateOfIntro
				+ " \ndate of discontinuity : " + this.dateOfDisc + " \nmanufacturer : " + this.company + "\n\n";
		return s;
	}

}
