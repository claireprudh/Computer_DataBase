/**
 * 
 */
package main.java.com.excilys.formation.cdb.persistence.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.com.excilys.formation.cdb.model.Company;
import main.java.com.excilys.formation.cdb.persistence.Column;

/**
 * @author excilys
 *
 */
public class CompanyMapper {

	
public static CompanyMapper instance;
	
	public static CompanyMapper getInstance() {
		
		if (instance == null) {
			
			instance = new CompanyMapper();
		}
		
		return instance;
	}
	
	private CompanyMapper() {
		
	}
	
	public Company map(ResultSet results) throws SQLException {
		
		Company company = new Company();
		company.setId(results.getInt(Column.CID.getName()));
		company.setName(results.getString(Column.CNAME.getName()));
		
		return company;
	
	}
}
