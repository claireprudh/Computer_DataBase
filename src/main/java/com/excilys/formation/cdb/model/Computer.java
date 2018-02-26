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
	
	public Computer(ComputerBuilder computerBuilder) {
		
		this.setId(computerBuilder.id);
		this.setName(computerBuilder.name);
		this.setDateOfIntro(computerBuilder.dateOfIntro);
		this.setDateOfDisc(computerBuilder.dateOfDisc);
		this.setCompany(computerBuilder.company);
		
	}
	
	
	/**
	 * Pattern Builder.
	 * @author excilys
	 *
	 */
	public static class ComputerBuilder {
		
		int id;
		String name;
		LocalDate dateOfIntro;
		LocalDate dateOfDisc;
		Company company;
		
		public ComputerBuilder() {
			this.name = "default";
			this.id = 0;
		}
		
		public ComputerBuilder withId(int id) {
			this.id = id;
			return this;
		}
		
		public ComputerBuilder withName(String name) {
			this.name = name;
			return this;
		}
		
		public ComputerBuilder withDateIntro(LocalDate introduced) {
			this.dateOfIntro = introduced;
			return this;
		}
		
		public ComputerBuilder withDateDisc(LocalDate discontinued) {
			this.dateOfDisc = discontinued;
			return this;
		}
		
		public ComputerBuilder withCompany(Company company) {
			this.company = company;
			return this;
		}
		
		public Computer build() {
			return new Computer(this);
		}
 		
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
	public Optional<LocalDate> getDateOfIntro() {
		return Optional.ofNullable(dateOfIntro);
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
	public Optional<LocalDate> getDateOfDisc() {
		return Optional.ofNullable(dateOfDisc);
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
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
		Computer other = (Computer) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}

	

}
