/**
 * 
 */
package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.excilys.formation.cdb.model.Company;

/**
 * @author excilys
 *
 */
public class CompanyDAO {

	private static CompanyDAO instance;
	
	public static CompanyDAO getInstance() {
		CompanyDAO c;
		
		if (instance == null) {
			c = new CompanyDAO();
		}
		else {
			c = instance;
		}
		
		return c;
	}
	
	public Company getCompanyByID(int id) {
		Company company = new Company();
		Connexion conn = Connexion.getInstance();
		conn.open();
		Connection c = conn.getConnection();
		try {
			Statement stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT name FROM company WHERE id = " + id + ";");
			company.setId(id);
			if(rs.next()) {
				company.setName(rs.getString("name"));
			}
			else company.setName(null);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		conn.close();
		
		return company;
		
	}
	

}
