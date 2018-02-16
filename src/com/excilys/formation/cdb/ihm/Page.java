/**
 * 
 */
package com.excilys.formation.cdb.ihm;

import java.util.List;


import com.excilys.formation.cdb.services.ComputerService;

/**
 * @author excilys
 *
 */
public class Page {
	
	public static int noPage;
	public int nbComputer;
	public int pageMax;
	
	private List<String> listComputers;

	
	public Page(int nbComputer) {
		setNbComputer(nbComputer);
		
		pageMax = ComputerService.getInstance().getMaxPage(nbComputer);
		listComputers = ComputerService.getInstance().getPageComputers(nbComputer, noPage);
		
		noPage++;
		
	}
	
	public List<String> nextPage() {
		listComputers = ComputerService.getInstance().getPageComputers(nbComputer, noPage);
		noPage++;
		
		return listComputers;
	}
	
	public List<String> previousPage() {
		listComputers = ComputerService.getInstance().getPageComputers(nbComputer, noPage);
		noPage--;
		
		return listComputers;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		StringBuilder list = new StringBuilder();
		for (String s : listComputers) {
			list.append(s + "\n");
		}
		
		return list.toString();
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
}
