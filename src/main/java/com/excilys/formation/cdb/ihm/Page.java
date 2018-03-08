/**
 * 
 */
package com.excilys.formation.cdb.ihm;

import java.util.List;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.services.ComputerService;

/**
 * @author excilys
 *
 */
public class Page {

	private static int noPage = 1;
	private static int nbComputer = 50;
	private static int pageMax;

	private List<Computer> listComputers;


	public Page(int nbComputer, int page) {
		setNbComputer(nbComputer);

		pageMax = ComputerService.getInstance().getMaxPage(nbComputer);
		noPage = page;
		listComputers = ComputerService.getInstance().getPage(nbComputer, noPage);

	}
	
	public Page(int nbComputer, int page, String part) {
		setNbComputer(nbComputer);
		
		noPage = page;
		listComputers = ComputerService.getInstance().searchByName(nbComputer, page, part);
		
		if (listComputers.size() % Page.nbComputer == 0) {
			pageMax = listComputers.size() / Page.nbComputer;
		} else {
			pageMax = listComputers.size() / Page.nbComputer + 1;
		}

	}
	

	public Page nextPage() {
		if (noPage < pageMax - 1) {
			noPage++;
			listComputers = ComputerService.getInstance().getPage(nbComputer, noPage);
		}

		return this;
	}

	public Page previousPage() {
		if (noPage > 0) {
			noPage--;
			listComputers = ComputerService.getInstance().getPage(nbComputer, noPage);
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

		list.append(noPage + 1 + " / " + pageMax);

		return list.toString();
	}

	/**
	 * @return the nbComputer
	 */
	public static int getNbComputer() {
		return nbComputer;
	}
	/**
	 * @param nbComputer the nbComputer to set
	 */
	public static void setNbComputer(int nbComputer) {
		Page.nbComputer = nbComputer;
	}
	/**
	 * @return the noPage
	 */
	public static int getNoPage() {
		return noPage;
	}

	/**
	 * @param noPage the noPage to set
	 */
	public static void setNoPage(int noPage) {
		Page.noPage = noPage;
	}

	/**
	 * @return the pageMax
	 */
	public static int getPageMax() {
		return pageMax;
	}

	/**
	 * @param pageMax the pageMax to set
	 */
	public static void setPageMax(int pageMax) {
		Page.pageMax = pageMax;
	}
	
	/**
	 * @return the listComputers
	 */
	public List<Computer> getListComputers() {
		return listComputers;
	}
}
