/**
 * 
 */
package main.java.com.excilys.formation.cdb.ihm;

import java.util.List;


import main.java.com.excilys.formation.cdb.services.ComputerService;

/**
 * @author excilys
 *
 */
public class Page {

	public static int noPage = 0;
	public int nbComputer;
	public int pageMax;

	private List<String> listComputers;


	public Page(int nbComputer) {
		setNbComputer(nbComputer);

		pageMax = ComputerService.getInstance().getMaxPage(nbComputer);
		listComputers = ComputerService.getInstance().getPage(nbComputer, noPage);



	}

	public Page nextPage() {
		if (noPage < pageMax-1) {
			noPage++;
			listComputers = ComputerService.getInstance().getPage(nbComputer, noPage);
		}

		return this;
	}

	public Page previousPage() {
		if(noPage > 0) {
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
		for (String s : listComputers) {
			list.append(s + "\n");
		}

		list.append(noPage+1 + " / " + pageMax);

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
