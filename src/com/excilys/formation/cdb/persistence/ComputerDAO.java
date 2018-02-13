/**
 * 
 */
package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.Date;
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
		
		List<Computer> lp = new ArrayList<Computer>();
		Date temp;
		
		Connexion conn = Connexion.getInstance();
		conn.open();
		Connection c = conn.getConnection();
		
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, name, introduced, discontinued, company_id FROM computer");
			
			while(rs.next()) {
				Computer computer = new Computer();
				computer.setId(rs.getInt("id"));
				computer.setName(rs.getString("name"));
				
				temp = rs.getDate("introduced");
				if (temp != null) {
					computer.setDateOfIntro(temp.toLocalDate());
				}
				else {
					computer.setDateOfIntro(null);
				}
				temp = rs.getDate("discontinued");			
				if (temp != null) {
					computer.setDateOfDisc(temp.toLocalDate());
				}
				else {
					computer.setDateOfDisc(null);
				}
	
				computer.setCompanyID(CompanyDAO.getInstance().getCompanyByID(rs.getInt("company_id")));
				
				lp.add(computer);
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		conn.close();
		
		return lp;
	}

	public Computer getComputerById(int id) {
		
		Computer computer = new Computer();
		Date temp;
		
		Connexion conn = Connexion.getInstance();
		conn.open();
		Connection c = conn.getConnection();
		
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT name, introduced, discontinued, company_id  "
					+ "FROM computer WHERE id = " + id + ";");
			computer.setId(id);
			
			if(rs.next()) {
				
				computer.setName(rs.getString("name"));
				temp = rs.getDate("introduced");
				if (temp != null) {
					computer.setDateOfIntro(temp.toLocalDate());
				}
				else {
					computer.setDateOfIntro(null);
				}
				temp = rs.getDate("discontinued");			
				if (temp != null) {
					computer.setDateOfDisc(temp.toLocalDate());
				}
				else {
					computer.setDateOfDisc(null);
				}
	
				computer.setCompanyID(CompanyDAO.getInstance().getCompanyByID(rs.getInt("company_id")));
			}
			
			else {
				computer = null;
				
				}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		conn.close();

		return computer;
	}
	
	

}
