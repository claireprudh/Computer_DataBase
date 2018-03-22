package com.excilys.formation.cdb.validator;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.excilys.formation.cdb.dto.ComputerDTO;

@Component
public class ComputerDTOValidator implements Validator {

	public boolean validateName(String name) {

		Pattern pattern;
		Matcher matcher;
		boolean result = true;
		if (name == null) {
			result = false;
		} else {
			pattern = Pattern.compile("[<>|\\^*\\t\\n]");
			matcher = pattern.matcher(name);
			if (matcher.find()) {
				return false;
			}
		}
		
		return result;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return ComputerDTO.class.equals(arg0);
	}

	@Override
	public void validate(Object obj, Errors error) {
		ComputerDTO computer = (ComputerDTO) obj;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

		ValidationUtils.rejectIfEmpty(error, "name", "name.empty");
		
		if (!validateName(computer.getName())) {
			error.reject("name", "name.bad");
		}
		try {			
			new Date(formatter.parse(computer.getIntroduced()).getTime());
		}  catch (ParseException e) {
			if (!computer.getIntroduced().isEmpty()) {
				error.reject("introduced", "introduced.bad");
			}
		}

		try {
			new Date(formatter.parse(computer.getDiscontinued()).getTime());
		}  catch (ParseException e) {
			if (!computer.getDiscontinued().isEmpty()) {
				error.reject("discontinued", "discontinued.bad");
			}
		}
	}

}
