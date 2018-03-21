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
	
	private int id;
	private String name;
	private LocalDate dateOfIntro;
	private LocalDate dateOfDisc;
	private Company company;
	
	
	private Computer() {
	}
	
	private Computer(ComputerBuilder computerBuilder) {
		
		this.setId(computerBuilder.id);
		this.setName(computerBuilder.name);
		this.setDateOfIntro(computerBuilder.dateOfIntro);
		this.setDateOfDisc(computerBuilder.dateOfDisc);
		this.setCompany(computerBuilder.company);
		
	}
	
	
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
	
	public Optional<LocalDate> getDateOfIntro() {
		return Optional.ofNullable(dateOfIntro);
	}
	
	public void setDateOfIntro(LocalDate dateOfIntro) {
		this.dateOfIntro = dateOfIntro;
	}
	
	public Optional<LocalDate> getDateOfDisc() {
		return Optional.ofNullable(dateOfDisc);
	}
	
	public void setDateOfDisc(LocalDate dateOfDisc) {
		this.dateOfDisc = dateOfDisc;
	}
	
	public Optional<Company> getCompany() {
		return Optional.ofNullable(company);
	}
	
	public void setCompany(Company companyID) {
		this.company = companyID;
	}
	
	@Override
	public String toString() {
		
		return "Computer name : " + this.name + " \nid : " + this.id + " \ndate of introduction : " + this.dateOfIntro
				+ " \ndate of discontinuity : " + this.dateOfDisc + " \nmanufacturer : " + this.company + "\n\n";
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
		if (this == obj && this.getClass() == obj.getClass()) {
			Computer other = (Computer) obj;
			
			return (id == other.id);
		} else {
			return false;
		}
	}

	

}
