package com.excilys.formation.cdb.validator;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.excilys.formation.cdb.exception.InvalidNameException;
import com.excilys.formation.cdb.exception.InvalidStringDateException;
import com.excilys.formation.cdb.exception.NullException;
import com.excilys.formation.cdb.exception.TimeLineException;

@Component
public class ComputerValidator {
	
	public void validateName(String name) throws NullException, InvalidNameException {

		Pattern pattern;
		Matcher matcher;
		if (name == null) {
			throw new NullException();
		} else {
			pattern = Pattern.compile("[<>|\\^*\\t\\n]");
			matcher = pattern.matcher(name);
			if (matcher.find()) {
				throw new InvalidNameException();
			}
		}
	}

	public void validateIntroduced(Date date) throws NullException {
		if (date == null) {
			throw new NullException();
		}
	}

	public void validateIntroduced(String sdate) throws InvalidStringDateException, NullException  {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		if (sdate != null) {
		
		
		try {

			new Date(formatter.parse(sdate).getTime());

		} catch (ParseException e) {

			throw new InvalidStringDateException();
		}
		
		} else {
			throw new NullException();
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
		
		if (discontinued != null) {
		try {


			dDiscontinued = new Date(formatter.parse(discontinued).getTime());
			dIntroduced = new Date(formatter.parse(introduced).getTime());
			

		} catch (ParseException e) {

			throw new InvalidStringDateException();
		}
		
		validateDiscontinued(dIntroduced, dDiscontinued);
		
		} else {
			throw new NullException();
		}

	}
	
}
