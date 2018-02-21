/**
 * 
 */
package main.java.com.excilys.formation.cdb.persistence.mappers;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.java.com.excilys.formation.cdb.model.Company;
import main.java.com.excilys.formation.cdb.model.Computer;
import main.java.com.excilys.formation.cdb.persistence.Column;
import main.java.com.excilys.formation.cdb.persistence.CompanyDAO;

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

		computer.setCompany(CompanyDAO.getInstance().getByID(results.getInt(Column.CCOMPANY_ID.getName())).orElse(new Company()));
		
		return computer;
	}
	

}
