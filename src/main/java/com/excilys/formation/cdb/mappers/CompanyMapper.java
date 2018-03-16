/**
 * 
 */
package com.excilys.formation.cdb.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.formation.cdb.dto.CompanyDTO;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.Column;

/**
 * @author excilys
 *
 */
@Component
public class CompanyMapper implements RowMapper<Company>{

	
//public static CompanyMapper instance;
	
	/*public static CompanyMapper getInstance() {
		
		if (instance == null) {
			
			instance = new CompanyMapper();
		}
		
		return instance;
	}
	
	private CompanyMapper() {
		
	}*/
	
	public Company mapRow(ResultSet results, int arg1) throws SQLException {
		
		Company company = new Company();
		company.setId(results.getInt(Column.CCID.getName()));
		company.setName(results.getString(Column.CCNAME.getName()));
		
		return company;
	
	}
	
	public CompanyDTO map(Company company) {
		
		CompanyDTO dto = new CompanyDTO();
		
		dto.id = company.getId();
		dto.name = company.getName();
		
		return dto;
		
	}
	
}
