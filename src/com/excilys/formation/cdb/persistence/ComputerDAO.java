/**
 * 
 */
package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

	public Optional<List<Computer>> getListComputer() {
		
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
		
		return Optional.ofNullable(lp);
	}

	public Optional<Computer> getComputerById(int id) {
		
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

		
		return Optional.ofNullable(computer);
	}

	public void createNewComputer(Computer computer) {
		
		Connexion conn = Connexion.getInstance();
		conn.open();
		Connection c = conn.getConnection();
			
		
		String query = "INSERT INTO computer (name, introduced, discontinued, company_id)  VALUES (?, ?, ?, ?)";
		try {
			PreparedStatement pstmt = c.prepareStatement(query);
			
			pstmt.setString(1, computer.getName());
			
			if ( computer.getDateOfIntro()!= null) {
			pstmt.setDate(2, Date.valueOf(computer.getDateOfIntro()));
			}
			else {
				pstmt.setDate(2, null);
			}
			
			if ( computer.getDateOfDisc()!= null) {
				pstmt.setDate(3, Date.valueOf(computer.getDateOfDisc()));
			}
			else {
				pstmt.setDate(3, null);
			}
			
			pstmt.setInt(4, computer.getCompanyID().getId());

			pstmt.executeUpdate();
			
			
			
			
			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		conn.close();
		
		
		
		
	}
	
	
	/*
	public Computer getComputerByName(String name) {
		Computer computer = new Computer();
		Date temp;
		
		Connexion conn = Connexion.getInstance();
		conn.open();
		Connection c = conn.getConnection();
		
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT id, name, introduced, discontinued, company_id  "
					+ "FROM computer WHERE name = " + name + ";");
			computer.setName(rs.getString("name"));
			
			
			if(rs.next()) {
				
				computer.setId(rs.getInt("id"));
				
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
		
	}*/
	

}
