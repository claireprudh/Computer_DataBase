
package com.excilys.formation.cdb.spring;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;


@Configuration
@ComponentScan(basePackages = {"com.excilys.formation.cdb.pagination", 
		"com.excilys.formation.cdb.model", 
		"com.excilys.formation.cdb.dto",
		"com.excilys.formation.cdb.spring"})
public class CoreConfig {

		@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
		messageSource.setBasename("/WEB-INF/classes/messages");
		return messageSource;
	}
	
}