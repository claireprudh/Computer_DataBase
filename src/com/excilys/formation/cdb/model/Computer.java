/**
 * 
 */
package com.excilys.formation.cdb.model;

import java.sql.Timestamp;

/**
 * @author excilys
 *
 */

public class Computer {
	
	int id;
	String name;
	Timestamp dateOfIntro;
	Timestamp dateOfDisc;
	Company company;
	
	
	public Computer() {
		
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
	public Timestamp getDateOfIntro() {
		return dateOfIntro;
	}
	
	/**
	 * @param dateOfIntro
	 */
	public void setDateOfIntro(Timestamp dateOfIntro) {
		this.dateOfIntro = dateOfIntro;
	}
	
	/**
	 * @return
	 */
	public Timestamp getDateOfDisc() {
		return dateOfDisc;
	}
	
	/**
	 * @param dateOfDisc
	 */
	public void setDateOfDisc(Timestamp dateOfDisc) {
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
