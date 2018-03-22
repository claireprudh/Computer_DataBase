/**
 * 
 */
package com.excilys.formation.cdb.mappers;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.Computer.ComputerBuilder;
import com.excilys.formation.cdb.persistence.Column;
import com.excilys.formation.cdb.validator.ComputerDTOValidator;

/**
 * @author excilys
 *
 */
@Component
public class ComputerMapper implements RowMapper<Computer> {

	static final Logger LOGGER  = LogManager.getLogger(ComputerMapper.class);

	ComputerDTOValidator computerValidator;

	public ComputerMapper(ComputerDTOValidator computerValidator) {
		this.computerValidator = computerValidator;
	}

	public Computer mapRow(ResultSet results, int arg1) throws SQLException {

		Computer computer = new Computer.ComputerBuilder().build();

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

		dto.setId(computer.getId());
		dto.setName(computer.getName());
		Optional<LocalDate> date = computer.getDateOfIntro();
		if (date.isPresent()) {
			dto.setIntroduced(date.get().toString());
		} else {
			dto.setIntroduced(null);
		}
		date = computer.getDateOfDisc();
		if (date.isPresent()) {
			dto.setDiscontinued(date.get().toString());
		} else {
			dto.setDiscontinued(null);
		}
		Optional<Company> company = computer.getCompany();
		if (company.isPresent()) {
			dto.setCompanyId(company.get().getId());
			dto.setCompanyName(company.get().getName());
		} else {
			dto.setCompanyId(0);
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

	public List<Computer> map(List<Map<String, Object>> rows) {
		List<Computer> list = new ArrayList<>();

		for (Map row : rows) {
			ComputerBuilder computerBuilder = new Computer.ComputerBuilder();

			int id = (int) row.get(Column.CID.getName());

			computerBuilder.withId(id);
			String name = (String) row.get(Column.CNAME.getName());

			computerBuilder.withName(name);

			list.add(computerBuilder.build());
		}

		return list;

	}


}
