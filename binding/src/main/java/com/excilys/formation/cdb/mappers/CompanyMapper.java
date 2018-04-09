/**
 * 
 */
package com.excilys.formation.cdb.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;


import org.springframework.stereotype.Component;

import com.excilys.formation.cdb.dto.CompanyDTO;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.persistence.Column;

/**
 * @author excilys
 *
 */
@Component
public class CompanyMapper  {
	
	public CompanyDTO map(Company company) {
		
		CompanyDTO dto = new CompanyDTO();
		
		dto.setId(company.getId());
		dto.setName(company.getName());
		
		return dto;
		
	}
	
}
