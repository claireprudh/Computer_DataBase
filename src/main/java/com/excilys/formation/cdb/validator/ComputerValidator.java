package com.excilys.formation.cdb.validator;

import java.sql.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.excilys.formation.cdb.exception.DuplicateIDException;
import com.excilys.formation.cdb.exception.IDNotFoundException;
import com.excilys.formation.cdb.exception.InvalidStringDateException;
import com.excilys.formation.cdb.exception.NullException;
import com.excilys.formation.cdb.exception.TimeLineException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.services.CompanyService;
import com.excilys.formation.cdb.services.ComputerService;

public class ComputerValidator {

	private static ComputerValidator instance;
	
	private ComputerValidator() {
		
	}
	
	public static ComputerValidator getInstance() {
		
		if (instance == null) {
			instance = new ComputerValidator();
		}
		
		return instance;
	}

	public void validateId(int id, String action) throws DuplicateIDException, IDNotFoundException {
		switch (action) {
		case "create" : 
			for (Computer c : ComputerService.getInstance().getList()) {
				if (c.getId() == id) {
					throw new DuplicateIDException();
				}

			}
			break;
		case "update" :

		case "delete" :
			LABEL : for (Computer c : ComputerService.getInstance().getList()) {
				if (c.getId() == id) {
					break LABEL;
				}

				throw new IDNotFoundException();

			}
		break;


		}
	}
	public void validateName(String name) throws NullException {

		if (name == null) {
			throw new NullException();
		}
	}

	public void validateIntroduced(Date date) throws NullException {
		if (date == null) {
			throw new NullException();
		}
	}

	public void validateIntroduced(String sdate) throws InvalidStringDateException  {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		
		
		try {

			new Date(formatter.parse(sdate).getTime());

		} catch (ParseException e) {

			throw new InvalidStringDateException();
		}

	}
	
	public void validateDiscontinued(Date introduced, Date discontinued) throws NullException, TimeLineException {
		if (discontinued == null) {
			throw new NullException();
		} else {
			if (discontinued.before(introduced)) {
				throw new TimeLineException();
			}
		}
	}

	public void validateDiscontinued(String discontinued, String introduced) throws InvalidStringDateException, NullException, TimeLineException  {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date dDiscontinued;
		Date dIntroduced;
		
		try {

			dDiscontinued = new Date(formatter.parse(discontinued).getTime());
			dIntroduced = new Date(formatter.parse(introduced).getTime());
			

		} catch (ParseException e) {

			throw new InvalidStringDateException();
		}
		
		validateDiscontinued(dIntroduced, dDiscontinued);

	}
	
	public void validateCompany(Company company) throws IDNotFoundException {
		
		int count = 0;
		List<Company> listCompanies = CompanyService.getInstance().getList();
		for (Company c : listCompanies) {
			
			if (c.getId() != company.getId()) {
				count++;
			}

			
		}
		if (count == listCompanies.size()) {
			throw new IDNotFoundException();
		}

	}

}