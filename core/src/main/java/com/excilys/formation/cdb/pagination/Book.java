package com.excilys.formation.cdb.pagination;

public class Book {

	/**
	 * Caractéristiques du "Livre" : nb de pages et taille d'une page.
	 */
	private int nbComputer = 50;
	private int pageMax;
	private String contenu;
	
	/**
	 * 
	 * @param nbComputer
	 * @param count
	 * @param contenu
	 */
	public Book(int nbComputer, int count, String contenu) {
		setNbComputer(nbComputer);
		setPageMax(getMaxPage(nbComputer, count));
		setContenu(contenu);
		
	}
	
	public Page getPage(int noPage) {
		return new Page(this, noPage);
	}
	
	private int getMaxPage(int nbComputer, int count) {

		int countPages = Math.floorDiv(count, nbComputer); 
		if (count % nbComputer > 0) {
			countPages++;
		}

		return countPages;

	}

	/**
	 * @return the nbComputer
	 */
	public int getNbComputer() {
		return nbComputer;
	}

	/**
	 * @param nbComputer the nbComputer to set
	 */
	public void setNbComputer(int nbComputer) {
		this.nbComputer = nbComputer;
	}

	/**
	 * @return the pageMax
	 */
	public int getPageMax() {
		return pageMax;
	}

	/**
	 * @param count the pageMax to set
	 */
	public void setPageMax(int count) {
		this.pageMax = getMaxPage(nbComputer, count);
	}

	/**
	 * @return the contenu
	 */
	public String getContenu() {
		return contenu;
	}

	/**
	 * @param contenu the contenu to set
	 */
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}
}
