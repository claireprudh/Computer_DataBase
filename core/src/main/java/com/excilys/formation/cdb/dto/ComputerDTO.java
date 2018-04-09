package com.excilys.formation.cdb.dto;

public class ComputerDTO {

	private int id;
	private String name;
	private String introduced;
	private String discontinued;
	private int companyId;
	private String companyName;
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
	/**
	 * @return the introduced
	 */
	public String getIntroduced() {
		return introduced;
	}
	/**
	 * @param introduced the introduced to set
	 */
	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}
	/**
	 * @return the discontinued
	 */
	public String getDiscontinued() {
		return discontinued;
	}
	/**
	 * @param discontinued the discontinued to set
	 */
	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}
	/**
	 * @return the companyId
	 */
	public int getCompanyId() {
		return companyId;
	}
	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	/**
	 * @return the companyName
	 */
	public String getCompanyName() {
		return companyName;
	}
	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}
