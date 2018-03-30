/**
 * 
 */
package com.excilys.formation.cdb.mappers;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.validator.ComputerDTOValidator;

/**
 * @author excilys
 *
 */
@Component
public class ComputerMapper {

	static final Logger LOGGER  = LogManager.getLogger(ComputerMapper.class);

	ComputerDTOValidator computerValidator;

	public ComputerMapper(ComputerDTOValidator computerValidator) {
		this.computerValidator = computerValidator;
	}

	public ComputerDTO map(Computer computer) {

		ComputerDTO dto = new ComputerDTO();

		dto.setId(computer.getId());
		dto.setName(computer.getName());
		LocalDate date = computer.getDateOfIntro();
		if (date != null) {
		dto.setIntroduced(date.toString());
		}
		date = computer.getDateOfDisc();
		if (date != null) {
			dto.setDiscontinued(date.toString());
		}
		Company company = computer.getCompany();
		if (company != null) {
			dto.setCompanyId(company.getId());
			dto.setCompanyName(company.getName());
		}

		return dto;
	}

	public Computer map(ComputerDTO dto) {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date;

		Computer computer = new Computer.ComputerBuilder().build();

		computer.setId(dto.getId());

		computer.setName(dto.getName());

		try {
			if (!dto.getIntroduced().isEmpty()) {
			date = new Date(formatter.parse(dto.getIntroduced()).getTime());

			computer.setDateOfIntro(date.toLocalDate());
			} else {
				computer.setDateOfIntro(null);
			}

		} catch (ParseException e) {
			LOGGER.error("Erreur de parsing dans la date");
		} catch (NullPointerException npe) {
			LOGGER.error("Date non renseignée");
			computer.setDateOfIntro(null);
		}

		
		try {
			if (!dto.getIntroduced().isEmpty()) {
			date = new Date(formatter.parse(dto.getDiscontinued()).getTime());

			computer.setDateOfDisc(date.toLocalDate());
			} else {
				computer.setDateOfDisc(null);
			}

		} catch (ParseException e) {
			LOGGER.error("Erreur de parsing dans la date");
		} catch (NullPointerException npe) {
			LOGGER.error("Date non renseignée");
			computer.setDateOfDisc(null);
		}

		Company c = new Company(dto.getCompanyId());

		computer.setCompany(c);

		return computer;
	}


}
