/**
 * 
 */
package com.excilys.formation.cdb.pagination;

import java.util.List;

import com.excilys.formation.cdb.model.Computer;


/**
 * @author excilys
 *
 */
public class Page {

	/**
	 * Caractéristiques de la page.
	 */
	private int noPage;
	private List<Computer> listComputers;
	private Book livre;

	protected Page(Book livre, int noPage) {
		this.livre = livre;
		this.noPage = noPage;
		this.listComputers = null;
		
	}

	public Page(int page) {

		noPage = page;
		
	}

	public Page nextPage() {
		if (noPage < livre.getPageMax() - 1) {
			noPage++;
		}

		return this;
	}

	public Page previousPage() {
		if (noPage > 0) {
			noPage--;
		}

		return this;
	}
	
	@Override
	public String toString() {

		StringBuilder list = new StringBuilder();
		for (Computer s : listComputers) {
			list.append(s + "\n");
		}

		list.append(noPage + 1 + " / " + livre.getNbComputer());

		return list.toString();
	}

	
	/**
	 * @return the noPage
	 */
	public int getNoPage() {
		return noPage;
	}

	/**
	 * @param noPage the noPage to set
	 */
	public void setNoPage(int noPage) {
		this.noPage = noPage;
	}

	
	/**
	 * @return the listComputers
	 */
	public List<Computer> getListComputers() {
		return listComputers;
	}
	
	/**
	 * 
	 * @param list
	 */
	public void setListComputers(List<Computer> list) {
		this.listComputers = list;
	}

	/**
	 * @return the livre
	 */
	public Book getLivre() {
		return livre;
	}

	/**
	 * @param livre the livre to set
	 */
	public void setLivre(Book livre) {
		this.livre = livre;
	}
}
