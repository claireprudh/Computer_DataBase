/**
 * 
 */
package com.excilys.formation.cdb.mappers;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.exception.IDNotFoundException;
import com.excilys.formation.cdb.exception.InvalidStringDateException;
import com.excilys.formation.cdb.exception.NullException;
import com.excilys.formation.cdb.exception.TimeLineException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.Column;
import com.excilys.formation.cdb.validator.ComputerValidator;

/**
 * @author excilys
 *
 */
public class ComputerMapper {

	static final Logger LOGGER  = LogManager.getLogger(ComputerMapper.class);


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

		computer.setCompany(new Company(results.getInt(Column.CCOMPANY_ID.getName()), results.getString(Column.CCNAME.getName())));


		return computer;
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

	public Computer map(ComputerDTO dto) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date;

		Computer computer = new Computer();


		computer.setId(dto.getId());


		try {
			ComputerValidator.getInstance().validateName(dto.getName());

			computer.setName(dto.getName());

		} catch (NullException ne) {

			LOGGER.error("valeur null");
		} 

		try {
			ComputerValidator.getInstance().validateIntroduced(dto.getIntroduced());

			try {
				date = new Date(formatter.parse(dto.getIntroduced()).getTime());

				computer.setDateOfIntro(date.toLocalDate());

			} catch (ParseException e) {

			}

		} catch (InvalidStringDateException isde) {

			LOGGER.error("Date invalide");

		} catch (NullException ne) {
			LOGGER.error("Chaîne de caractères inexistante");
		} 

		try {

			ComputerValidator.getInstance().validateDiscontinued(dto.getDiscontinued(), dto.getIntroduced());



			try {
				date = new Date(formatter.parse(dto.getDiscontinued()).getTime());

				computer.setDateOfDisc(date.toLocalDate());

			} catch (ParseException e) {

			}
		} catch (TimeLineException tle) {

			LOGGER.error("Chronologie douteuse");

		} catch (InvalidStringDateException e1) {
			LOGGER.error("Date invalide");
		} catch (NullException e1) {
			LOGGER.error("valeur null");
		}

		try {
			ComputerValidator.getInstance().validateCompany(new Company(dto.getCompanyId()));

			Company c = new Company(dto.getCompanyId());

			computer.setCompany(c);
		} catch (IDNotFoundException e) {

			LOGGER.error("Fabricant inexistant");

		}

		return computer;
	}


}
