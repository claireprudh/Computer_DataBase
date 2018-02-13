/**
 * 
 */
package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.cdb.model.Computer;

/**
 * @author excilys
 *
 */
public class ComputerDAO {
	
	private static ComputerDAO instance;
	
	public static ComputerDAO getInstance() {
		ComputerDAO c;
		
		if (instance == null) {
			c = new ComputerDAO();
		}
		else {
			c = instance;
		}
		
		return c;
	}

	public List<Computer> getListComputer() {
		System.out.println(this.getClass().getSimpleName());
		List<Computer> lp = new ArrayList<Computer>();
		
		Connexion conn = Connexion.getInstance();
		conn.open();
		Connection c = conn.getConnection();
		
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM computer");
			
			while(rs.next()) {
				Computer computer = new Computer();
				computer.setId(rs.getInt("id"));
				computer.setName(rs.getString("name"));
				computer.setDateOfIntro(rs.getTimestamp("introduced"));
				computer.setDateOfDisc(rs.getTimestamp("discontinued"));
				computer.setCompanyID(CompanyDAO.getInstance().getCompanyByID(rs.getInt("company_id")));
				
				lp.add(computer);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		conn.close();
		
		return lp;
	}
	
	

}
