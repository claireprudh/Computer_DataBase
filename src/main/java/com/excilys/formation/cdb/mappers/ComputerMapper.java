/**
 * 
 */
package com.excilys.formation.cdb.mappers;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.Column;
import com.excilys.formation.cdb.persistence.CompanyDAO;

/**
 * @author excilys
 *
 */
public class ComputerMapper {
	
	
	
	
	public static ComputerMapper instance;
	
	public static ComputerMapper getInstance() {
		
		if (instance == null) {
			
			instance = new ComputerMapper();
		}
		
		return instance;
	}
	
	private ComputerMapper() {
		
	}
	
	public Computer map(ResultSet results) throws SQLException {
		
		Computer computer = new Computer();

		
		computer.setId(results.getInt(Column.CID.getName()));
		computer.setName(results.getString(Column.CNAME.getName()));
		Date temp = results.getDate(Column.CDATE_OF_INTRO.getName());
		if (temp != null) {
			computer.setDateOfIntro(temp.toLocalDate());
		} else {
			computer.setDateOfIntro(null);
		}
		temp = results.getDate(Column.CDATE_OF_DISC.getName());			
		if (temp != null) {
			computer.setDateOfDisc(temp.toLocalDate());
		} else {
			computer.setDateOfDisc(null);
		}

		computer.setCompany(new Company(results.getInt(Column.CCOMPANY_ID.getName()), ""));
		//CompanyDAO.getInstance().getByID(results.getInt(Column.CCOMPANY_ID.getName())).orElse(new Company())
		
		return computer;
	}
	
	public void mapCompany(List<Computer> listcomputers) {
		for (Computer c : listcomputers) {
			c.setCompany(CompanyDAO.getInstance().getByID(
					c.getCompany().get().getId()).orElse(new Company()));
		}
	}
	
	public ComputerDTO map(Computer computer) {
		
		ComputerDTO dto = new ComputerDTO();
		
		dto.id = computer.getId();
		dto.name = computer.getName();
		if (computer.getDateOfIntro().isPresent()) {
			dto.introduced = computer.getDateOfIntro().get().toString();
		} else {
			dto.introduced = null;
		}
		if (computer.getDateOfDisc().isPresent()) {
			dto.discontinued = computer.getDateOfDisc().get().toString();
		} else {
			dto.discontinued = null;
		}
		if (computer.getCompany().isPresent()) {
			dto.companyId = computer.getCompany().get().getId();
			dto.companyName = computer.getCompany().get().getName();
		} else {
			dto.companyId = 0;
		}
		
		
		return dto;
	}
	

}
