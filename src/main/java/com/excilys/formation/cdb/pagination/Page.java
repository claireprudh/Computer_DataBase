/**
 * 
 */
package com.excilys.formation.cdb.pagination;

import java.util.List;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.services.ComputerService;

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
		this.listComputers = ComputerService.getInstance().getPage(livre.getNbComputer(), noPage, livre.getContenu());
		
	}

	public Page(int page) {

		noPage = page;
		listComputers = ComputerService.getInstance().getPage(livre.getNbComputer(), noPage);

	}
	
	public Page(int page, int max) {

		noPage = page;
		listComputers = ComputerService.getInstance().getPage(livre.getNbComputer(), noPage);

	}
	
	public Page(int page, int max, String part) {
		
		noPage = page;
		listComputers = ComputerService.getInstance().searchByName(livre.getNbComputer(), noPage, part);
		
	}
	

	public Page nextPage() {
		if (noPage < livre.getPageMax() - 1) {
			noPage++;
			listComputers = ComputerService.getInstance().getPage(livre.getNbComputer(), noPage);
		}

		return this;
	}

	public Page previousPage() {
		if (noPage > 0) {
			noPage--;
			listComputers = ComputerService.getInstance().getPage(livre.getNbComputer(), noPage);
		}

		return this;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
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
}
